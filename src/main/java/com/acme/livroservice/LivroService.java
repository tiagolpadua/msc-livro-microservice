package com.acme.livroservice;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LivroService {
	Logger logger = LoggerFactory.getLogger(LivroService.class);

	private final LivroRepository repository;
	
	private final AvaliacaoService avaliacaoService;

	LivroService(LivroRepository repository, AvaliacaoService avaliacaoService) {
		this.repository = repository;
		this.avaliacaoService = avaliacaoService;
	}

	public List<Livro> getLivros(Optional<String> autor, Optional<String> titulo) {
		logger.info("getLivros - autor: " + autor.orElse("Não informado") + " titulo: " + titulo.orElse("Não informado"));

		if (autor.isPresent()) {
			return repository.findAll(LivroRepository.autorContem(autor.get()));
		} else if (titulo.isPresent()) {
			return repository.findAll(LivroRepository.tituloContem(titulo.get()));
		} else if (autor.isPresent() && titulo.isPresent()) {
			return repository.findAll(Specification.where(LivroRepository.autorContem(autor.get()))
					.and(LivroRepository.tituloContem(titulo.get())));
		} else {
			return repository.findAll();
		}
	}

	public Livro getLivroPorId(Long id) {
		logger.info("getLivroPorId: " + id);
		return repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado: " + id));
	}

	public Livro adicionarLivro(@RequestBody Livro livro) {
		logger.info("adicionarLivro: " + livro);
		return repository.save(livro);
	}

	public Livro atualizarLivro(Livro livro, Long id) {
		logger.info("atualizarLivro: " + livro + " id: " + id);
		return repository.findById(id).map(livroSalvo -> {
			livroSalvo.setAutor(livro.getAutor());
			livroSalvo.setTitulo(livro.getTitulo());
			livroSalvo.setPreco(livro.getPreco());
			return repository.save(livroSalvo);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado: " + id));
	}

	public void excluirLivro(Long livroId) {
		logger.info("excluirLivro: " + livroId);
		
		try {
			this.avaliacaoService.apagarAvaliacoesPorLivroId(livroId);
			logger.info("Avaliações vinculadas excluídas com sucesso");
		} catch (ResourceAccessException | HttpClientErrorException ex) {
			logger.error("Ocorreu um erro na comunicação com o serviço de avaliações", ex);
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
					"Ocorreu um erro não esperado na comunicação com o serviço de livros: " + ex.getMessage());
		}

		repository.deleteById(livroId);
	}
}
