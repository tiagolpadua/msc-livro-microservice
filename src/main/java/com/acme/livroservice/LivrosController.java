package com.acme.livroservice;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/livros")
public class LivrosController {

	Logger logger = LoggerFactory.getLogger(LivrosController.class);

	private final LivroRepository repository;
	
	LivrosController(LivroRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public List<Livro> getLivros(@RequestParam("autor") Optional<String> autor,
			@RequestParam("titulo") Optional<String> titulo) {
		logger.info("getLivros - autor: " + autor.orElse("Não informado") + " titulo: " + titulo.orElse("Não informado"));

		return repository.findAll();
	}

	@GetMapping("/{id}")
	public Livro getLivroPorId(@PathVariable Long id) {
		logger.info("getLivroPorId: " + id);
		return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado: " + id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Livro adicionarLivro(@RequestBody Livro livro) {
		logger.info("adicionarLivro: " + livro);
		return repository.save(livro);		
	}

	@PutMapping("/{id}")
	public Livro atualizarLivro(@RequestBody Livro livro, @PathVariable Long id) {
		logger.info("atualizarLivro: " + livro + " id: " + id);
		return repository.findById(id)
				.map(livroSalvo -> {
					livroSalvo.setAutor(livro.getAutor());
					livroSalvo.setTitulo(livro.getTitulo());
					livroSalvo.setPreco(livro.getPreco());
					return repository.save(livroSalvo);
				})
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado: " + id));		
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluirLivro(@PathVariable Long id) {
		logger.info("excluirLivro: " + id);		
		repository.deleteById(id);
	}
}
