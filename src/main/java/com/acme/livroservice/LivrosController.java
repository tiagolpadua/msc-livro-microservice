package com.acme.livroservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;

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

	@Resource
	private ArrayList<Livro> listaLivros;

	@GetMapping
	public List<Livro> getLivros(@RequestParam("autor") Optional<String> autor,
			@RequestParam("titulo") Optional<String> titulo) {
		logger.info("getLivros - autor: " + autor.orElse("Não informado") + " titulo: " + titulo.orElse("Não informado"));

		List<Livro> listaRetorno = listaLivros;

		if (autor.isPresent()) {
			listaRetorno = listaRetorno.stream()
					.filter(l -> l.getAutor().toUpperCase().contains(autor.get().toUpperCase()))
					.collect(Collectors.toList());
		}
		
		if (titulo.isPresent()) {
			listaRetorno = listaRetorno.stream()
					.filter(l -> l.getTitulo().toUpperCase().contains(titulo.get().toUpperCase()))
					.collect(Collectors.toList());
		}

		return listaRetorno;
	}

	@GetMapping("/{id}")
	public Livro getLivroPorId(@PathVariable Long id) {
		logger.info("getLivroPorId: " + id);
		return listaLivros.stream().filter(l -> l.getId().equals(id)).findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado: " + id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Livro adicionarLivro(@RequestBody Livro livro) {
		logger.info("adicionarLivro: " + livro);
		Long max = listaLivros.stream().mapToLong(l -> l.getId()).max().orElse(0);
		livro.setId(max + 1);
		listaLivros.add(livro);

		logger.info("adicionarLivro: " + livro + " adicionado com sucesso");
		return livro;
	}

	@PutMapping("/{id}")
	public Livro atualizarLivro(@RequestBody Livro livro, @PathVariable Long id) {
		logger.info("atualizarLivro: " + livro + " id: " + id);
		Livro livroSalvo = listaLivros.stream().filter(l -> l.getId().equals(id)).findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado: " + id));

		livroSalvo.setAutor(livro.getAutor());
		livroSalvo.setPreco(livro.getPreco());
		livroSalvo.setTitulo(livro.getTitulo());

		return livroSalvo;
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluirLivro(@PathVariable Long id) {
		logger.info("excluirLivro: " + id);

		Livro livro = listaLivros.stream().filter(l -> l.getId().equals(id)).findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado: " + id));

		listaLivros.remove(livro);
	}
}
