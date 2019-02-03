package com.acme.livroservice;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LivrosControllerIntegrationTest {

	MockMvc mockMvc;

	@Autowired
	private LivrosController livrosController;

	@Before
	public void setup() throws Exception {
		this.mockMvc = standaloneSetup(this.livrosController).build();
	}

	@Test
	public void getLivros() throws Exception {
		mockMvc.perform(get("/livros").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].titulo", is("Don Quixote")))
				.andExpect(jsonPath("$[1].titulo", is("O Senhor dos Anéis")))
				.andExpect(jsonPath("$[2].titulo", is("O Pequeno Príncipe")))
				.andExpect(jsonPath("$[3].titulo", is("Um Conto de Duas Cidades")));
	}

}
