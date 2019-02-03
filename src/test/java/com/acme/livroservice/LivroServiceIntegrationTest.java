package com.acme.livroservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LivroServiceIntegrationTest {
	@Autowired
	private LivroService livroService;

	@Test
	public void getLivros() {
		List<Livro> livros = livroService.getLivros(Optional.empty(), Optional.empty());
		assertThat(livros).isNotEmpty();
	}
}
