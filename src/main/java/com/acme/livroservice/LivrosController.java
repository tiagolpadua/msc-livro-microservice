package com.acme.livroservice;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/livros")
public class LivrosController {

	@Resource
	private ArrayList<Livro> listaLivros;

	@GetMapping
	public List<Livro> getLivros() {
		return listaLivros;
	}

	@GetMapping("/{livroId}")
	public Livro getLivroPorId(@PathVariable Long livroId) {
		return listaLivros.stream().filter(l -> l.getId().equals(livroId)).findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));
	}
	
	@PostMapping
	public void adicionarLivro(@RequestBody Livro livro) {
		System.out.println("Função adicionarLivro acionada");
	}
}
