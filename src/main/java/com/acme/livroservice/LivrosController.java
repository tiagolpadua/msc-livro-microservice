package com.acme.livroservice;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LivrosController {

	@Resource
	private ArrayList<Livro> listaLivros;

	@RequestMapping("/livros")
	public List<Livro> getLivros() {
		return listaLivros;
	}

	@RequestMapping("/livros/{livroId}")
	public Livro getLivroPorId(@PathVariable Long livroId) {
		return listaLivros.stream().filter(l -> l.getId().equals(livroId)).findFirst()
				.orElseThrow(() -> new LivroNaoEncontradoException());
	}
}
