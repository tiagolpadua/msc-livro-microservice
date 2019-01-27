package com.acme.livroservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

	Logger logger = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	public CommandLineRunner initDatabase(LivroRepository repository) {
		return args -> {
			logger.info("Preloading " + repository.save(new Livro("Miguel de Cervantes", "Don Quixote", 144.0)));
			logger.info("Preloading " + repository.save(new Livro("J. R. R. Tolkien", "O Senhor dos Anéis", 123.0)));
			logger.info("Preloading "
					+ repository.save(new Livro("Antoine de Saint-Exupéry", "O Pequeno Príncipe", 152.0)));
			logger.info(
					"Preloading " + repository.save(new Livro("Charles Dickens", "Um Conto de Duas Cidades", 35.0)));
		};
	}
}
