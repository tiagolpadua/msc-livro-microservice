package com.acme.livroservice;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public List<Livro> getLivros() {
		logger.info("getLivros");
		return listaLivros;
	}

	@GetMapping("/{livroId}")
	public Livro getLivroPorId(@PathVariable Long livroId) {
		logger.info("getLivroPorId: " + livroId);
		return listaLivros.stream().filter(l -> l.getId().equals(livroId)).findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));
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
}
