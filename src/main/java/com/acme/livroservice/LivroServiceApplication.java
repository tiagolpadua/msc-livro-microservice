package com.acme.livroservice;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LivroServiceApplication {
	
	@Bean
	public ArrayList<Livro> listaLivros() {
		
		ArrayList<Livro> livros = new ArrayList<Livro>();
		
		Livro l1 = new Livro(1l, "Miguel de Cervantes", "Don Quixote", 144.0);
		Livro l2 = new Livro(2l, "J. R. R. Tolkien", "O Senhor dos Anéis", 123.0);
		Livro l3 = new Livro(3l, "Antoine de Saint-Exupéry", "O Pequeno Príncipe", 152.0);
		Livro l4 = new Livro(4l, "Charles Dickens", "Um Conto de Duas Cidades", 35.0);
		
		livros.add(l1);
		livros.add(l2);
		livros.add(l3);
		livros.add(l4);
		
		return livros;
	}

	public static void main(String[] args) {
		SpringApplication.run(LivroServiceApplication.class, args);
	}

}

