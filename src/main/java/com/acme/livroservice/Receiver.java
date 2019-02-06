package com.acme.livroservice;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
	Logger logger = LoggerFactory.getLogger(LivrosController.class);

	@RabbitListener(queues = LivroServiceApplication.CADASTRAR_LIVRO_QUEUE_NAME)
    public void receiveMessageCadastrarLivro(String message) throws InterruptedException {
    	logger.info("Recebeu <" + message + ">");
         TimeUnit.SECONDS.sleep(3);
         logger.info("Processou <" + message + ">");
    }
}