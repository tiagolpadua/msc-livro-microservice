package com.acme.livroservice;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LivroRepository extends JpaRepository<Livro, Long>, JpaSpecificationExecutor<Livro> {
	static Specification<Livro> autorContem(String autor) {
		return (livro, cq, cb) -> cb.like(cb.lower(livro.get("autor")), "%" + autor.toLowerCase() + "%");
	}

	static Specification<Livro> tituloContem(String titulo) {
		return (livro, cq, cb) -> cb.like(cb.lower(livro.get("titulo")), "%" + titulo.toLowerCase() + "%");
	}
}
