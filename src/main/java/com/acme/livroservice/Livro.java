package com.acme.livroservice;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Livro implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private @Id @GeneratedValue Long id;
	private String autor;
	private String titulo;
	private Double preco;
	
	public Livro() {
		super();
	}
	
	public Livro(Long id, String autor, String titulo, Double preco) {
		super();
		this.id = id;
		this.autor = autor;
		this.titulo = titulo;
		this.preco = preco;
	}
	
	public Livro(String autor, String titulo, Double preco) {
		super();
		this.autor = autor;
		this.titulo = titulo;
		this.preco = preco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	@Override
	public String toString() {
		return "Livro [id=" + id + ", autor=" + autor + ", titulo=" + titulo + ", preco=" + preco + "]";
	}

}
