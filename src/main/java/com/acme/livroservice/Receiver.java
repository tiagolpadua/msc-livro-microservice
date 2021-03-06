package com.acme.livroservice;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
// @Profile("disabled")
public class Receiver {

	Logger logger = LoggerFactory.getLogger(LivrosController.class);

	private final LivroRepository repository;

	Receiver(LivroRepository repository) {
		this.repository = repository;
	}

//    public void receiveMessage(String message) throws InterruptedException {
//    	logger.info("Recebeu <" + message + ">");
//         TimeUnit.SECONDS.sleep(3);
//         logger.info("Processou <" + message + ">");
//    }

//	public void receiveMessage(Livro livro) throws InterruptedException {
//		logger.info("Recebeu <" + livro.toString() + ">");
//		TimeUnit.SECONDS.sleep(3);
//		repository.save(livro);
//		logger.info("Processou <" + livro.toString() + ">");
//	}
	
	@RabbitListener(queues = LivroServiceApplication.QUEUE_NAME)
    public void receiveMessage(final Livro livro) throws InterruptedException {
		logger.info("Recebeu <" + livro.toString() + ">");
		TimeUnit.SECONDS.sleep(3);
		repository.save(livro);
		logger.info("Processou <" + livro.toString() + ">");
    }

}
