package com.acme.livroservice;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
	Logger logger = LoggerFactory.getLogger(LivrosController.class);

	private final LivroRepository repository;

	Receiver(LivroRepository repository) {
		this.repository = repository;
	}

	@RabbitListener(queues = LivroServiceApplication.CADASTRAR_LIVRO_QUEUE_NAME)
	public void receiveMessageCadastrarLivro(Livro livro) throws InterruptedException {
		logger.info("Recebeu <" + livro.toString() + ">");
		TimeUnit.SECONDS.sleep(3);
		repository.save(livro);
		logger.info("Processou <" + livro.toString() + ">");
	}
	
	@RabbitListener(queues = LivroServiceApplication.EXCLUIR_LIVRO_QUEUE_NAME)
	public void receiveMessageExcluirLivro(Long id) throws InterruptedException {
		logger.info("Recebeu para exclusão id: <" + id + ">");
		TimeUnit.SECONDS.sleep(3);
		repository.deleteById(id);
		logger.info("Processou exclusão do id: <" + id + ">");
	}
}