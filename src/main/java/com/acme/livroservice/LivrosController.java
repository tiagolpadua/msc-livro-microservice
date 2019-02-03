package com.acme.livroservice;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/livros")
public class LivrosController {

	Logger logger = LoggerFactory.getLogger(LivrosController.class);

	private final RabbitTemplate rabbitTemplate;
	
	private final LivroService livroService;

	LivrosController(RabbitTemplate rabbitTemplate, LivroService livroService) {
		this.rabbitTemplate = rabbitTemplate;
		this.livroService = livroService;
	}

	@GetMapping
	public List<Livro> getLivros(@RequestParam("autor") Optional<String> autor,
			@RequestParam("titulo") Optional<String> titulo) {
		logger.info(
				"getLivros - autor: " + autor.orElse("Não informado") + " titulo: " + titulo.orElse("Não informado"));

		return livroService.getLivros(autor, titulo);
	}

	@GetMapping("/{id}")
	@Cacheable(value = "livros", key = "#id")
	public Livro getLivroPorId(@PathVariable Long id) {
		logger.info("getLivroPorId: " + id);
		return livroService.getLivroPorId(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Livro adicionarLivro(@RequestBody Livro livro) {
		logger.info("adicionarLivro: " + livro);
		return livroService.adicionarLivro(livro);
	}

	@PostMapping("/demorado")
	@ResponseStatus(HttpStatus.CREATED)
	public Livro adicionarLivroDemorado(@RequestBody Livro livro) throws InterruptedException {
		logger.info("adicionarLivroDemorado iniciou: " + livro);
		TimeUnit.SECONDS.sleep(3);
		Livro livroSalvo = livroService.adicionarLivro(livro);
		logger.info("adicionarLivroDemorado terminou: " + livroSalvo);
		return livroSalvo;
	}

//	@PostMapping("/assincrono")
//	@ResponseStatus(HttpStatus.CREATED)
//	public void adicionarLivroAssincrono(@RequestBody Livro livro) throws InterruptedException {
//		logger.info("adicionarLivroAssincrono iniciou: " + livro);
//		rabbitTemplate.convertAndSend(LivroServiceApplication.TOPIC_EXCHANGE_NAME, LivroServiceApplication.ROUTING_KEY, livro.toString());
//		logger.info("adicionarLivroAssincrono terminou");
//	}

	@PostMapping("/assincrono")
	@ResponseStatus(HttpStatus.CREATED)
	public void adicionarLivroAssincrono(@RequestBody Livro livro) throws InterruptedException {
		logger.info("adicionarLivroAssincrono iniciou: " + livro);
		rabbitTemplate.convertAndSend(LivroServiceApplication.TOPIC_EXCHANGE_NAME, LivroServiceApplication.ROUTING_KEY,
				livro);
	}

	@PutMapping("/{id}")
	@CachePut(value = "livros", key = "#livro.id")
	public Livro atualizarLivro(@RequestBody Livro livro, @PathVariable Long id) {
		logger.info("atualizarLivro: " + livro + " id: " + id);
		return livroService.atualizarLivro(livro, id);
	}

	@DeleteMapping("/{id}")
	@CacheEvict(value = "livros", allEntries = true)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluirLivro(@PathVariable Long id) {
		logger.info("excluirLivro: " + id);
		livroService.excluirLivro(id);
	}
}
