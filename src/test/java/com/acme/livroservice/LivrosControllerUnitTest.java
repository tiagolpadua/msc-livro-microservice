package com.acme.livroservice;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LivrosControllerUnitTest {

	MockMvc mockMvc;

	@Autowired
	private LivrosController livrosController;

	@MockBean
	private RabbitTemplate rabbitTemplate;

	@MockBean
	private LivroService livroService;

	private List<Livro> livros;

	@Before
	public void setup() throws Exception {
		this.mockMvc = standaloneSetup(this.livrosController).build();

		livros = new ArrayList<Livro>();
		livros.add(new Livro(1L, "Miguel de Cervantes", "Don Quixote", 144.0));
		livros.add(new Livro(2L, "J. R. R. Tolkien", "O Senhor dos Anéis", 123.0));
		livros.add(new Livro(3L, "Antoine de Saint-Exupéry", "O Pequeno Príncipe", 152.0));
		livros.add(new Livro(4L, "Charles Dickens", "Um Conto de Duas Cidades", 35.0));
	}

	@Test
	public void getLivros() throws Exception {
		when(livroService.getLivros(Optional.empty(), Optional.empty())).thenReturn(livros);
		mockMvc.perform(get("/livros").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].titulo", is("Don Quixote")))
				.andExpect(jsonPath("$[1].titulo", is("O Senhor dos Anéis")))
				.andExpect(jsonPath("$[2].titulo", is("O Pequeno Príncipe")))
				.andExpect(jsonPath("$[3].titulo", is("Um Conto de Duas Cidades")));
	}

	@Test
	public void getLivro() throws Exception {
		when(livroService.getLivroPorId(1L)).thenReturn(new Livro(1L, "Miguel de Cervantes", "Don Quixote", 144.0));
		mockMvc.perform(get("/livros/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.titulo", is("Don Quixote")));
	}

	@Test
	public void adicionarLivro() throws Exception {
		when(livroService.adicionarLivro(new Livro("Miguel de Cervantes", "Don Quixote", 144.0)))
				.thenReturn(new Livro(1L, "Miguel de Cervantes", "Don Quixote", 144.0));
		mockMvc.perform(post("/livros").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new Livro("Miguel de Cervantes", "Don Quixote", 144.0)))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.titulo", is("Don Quixote")));
	}

	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	public void atualizarLivro() throws Exception {
		when(livroService.atualizarLivro(new Livro(1L, "Foo Bar", "Don Quixote", 144.0), 1L))
				.thenReturn(new Livro(1L, "Foo Bar", "Don Quixote", 144.0));
		mockMvc.perform(put("/livros/1").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new Livro(1L, "Foo Bar", "Don Quixote", 144.0)))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.autor", is("Foo Bar")));
	}
	
	@Test
	public void excluirLivro() throws Exception {
		mockMvc.perform(delete("/livros/1")).andExpect(status().isNoContent());
	}
}
