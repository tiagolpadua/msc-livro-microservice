package com.acme.livroservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LivroServiceUnitTest {
	@Autowired
	private LivroService livroService;

	@MockBean
	private LivroRepository repository;
	
	@MockBean
	private AvaliacaoService avaliacaoService;

	public void criaListaLivrosVazia() {
		when(repository.findAll()).thenReturn(new ArrayList<Livro>());
	}

	public void criaListaLivrosDefault() {
		List<Livro> livros = new ArrayList<Livro>();
		livros.add(new Livro(1L, "Miguel de Cervantes", "Don Quixote", 144.0));
		livros.add(new Livro(2L, "J. R. R. Tolkien", "O Senhor dos Anéis", 123.0));
		livros.add(new Livro(3L, "Antoine de Saint-Exupéry", "O Pequeno Príncipe", 152.0));
		livros.add(new Livro(4L, "Charles Dickens", "Um Conto de Duas Cidades", 35.0));

		when(repository.findAll()).thenReturn(livros);
	}

	@Test
	public void getLivrosVazio() {
		criaListaLivrosVazia();
		List<Livro> livros = livroService.getLivros(Optional.empty(), Optional.empty());
		assertThat(livros).isEmpty();
	}

	@Test
	public void getLivrosComLivros() {
		criaListaLivrosDefault();
		List<Livro> livros = livroService.getLivros(Optional.empty(), Optional.empty());
		assertThat(livros.size()).isEqualTo(4);
	}

	@Test
	public void getLivroPorId() {
		when(repository.findById(1L))
				.thenReturn(Optional.of(new Livro(1L, "Miguel de Cervantes", "Don Quixote", 144.0)));
		Livro livro = livroService.getLivroPorId(1L);
		assertThat(livro).isEqualTo(new Livro(1L, "Miguel de Cervantes", "Don Quixote", 144.0));
	}

	@Test
	public void adicionarLivro() {
		when(repository.save(new Livro("Miguel de Cervantes", "Don Quixote", 144.0)))
				.thenReturn(new Livro(1L, "Miguel de Cervantes", "Don Quixote", 144.0));
		Livro livro = livroService.adicionarLivro(new Livro("Miguel de Cervantes", "Don Quixote", 144.0));
		assertThat(livro).isEqualTo(new Livro(1L, "Miguel de Cervantes", "Don Quixote", 144.0));
	}

	@Test
	public void atualizarLivro() {
		when(repository.findById(1L))
				.thenReturn(Optional.of(new Livro(1L, "Miguel de Cervantes", "Don Quixote", 144.0)));
		when(repository.save(new Livro(1L, "Foo Bar", "Don Quixote", 144.0)))
				.thenReturn(new Livro(1L, "Foo Bar", "Don Quixote", 144.0));
		Livro livro = livroService.atualizarLivro(new Livro(1L, "Foo Bar", "Don Quixote", 144.0), 1L);
		assertThat(livro).isEqualTo(new Livro(1L, "Foo Bar", "Don Quixote", 144.0));
	}

	// É correto testar o funcionamento interno da função?
	@Test
	public void excluirLivro() {
		livroService.excluirLivro(1L);

		verify(avaliacaoService, times(1)).apagarAvaliacoesPorLivroId(1L);

		verify(repository, times(1)).deleteById(1L);
	}
}
