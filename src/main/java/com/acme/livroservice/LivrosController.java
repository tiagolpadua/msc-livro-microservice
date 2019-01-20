package com.acme.livroservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LivrosController {

	@RequestMapping("/livros")
	public List<Livro> getLivros() {
		ArrayList<Livro> livros = new ArrayList<Livro>();
		
		Livro l1 = new Livro(1l, "Don Quixote", "Miguel de Cervantes", 144.0);
		Livro l2 = new Livro(2l, "O Senhor dos Anéis", "J. R. R. Tolkien", 123.0);
		Livro l3 = new Livro(3l, "O Pequeno Príncipe", "Antoine de Saint-Exupéry", 152.0);
		
		livros.add(l1);
		livros.add(l2);
		livros.add(l3);
		
		return livros;
	}
	
	@RequestMapping("/livros/{livroId}")
	public Livro getLivroPorId(@PathVariable Long livroId) {
		System.out.println("livroId: " + livroId);
		Livro l = new Livro(1l, "Don Quixote", "Miguel de Cervantes", 144.0);
		return l;
	}
}
