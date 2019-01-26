package com.acme.livroservice;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

@Component
public class Receiver {

    private final LivroRepository repository;

    Receiver(LivroRepository repository) {
		this.repository = repository;
	}

    public void receiveMessage(String message) throws InterruptedException {
        System.out.println("Recebeu <" + message + ">");
         TimeUnit.SECONDS.sleep(3);
        // repository.save(livro);
        System.out.println("Processou <" + message + ">");
    }
    

}
