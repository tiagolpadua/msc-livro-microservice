package com.acme.livroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LivroServiceApplication {

	static final String MATRICULA = "NNNNNNNN";
	static final String LIVRO_DIRECT_EXCHANGE_NAME = "livro-direct-exchange-" + MATRICULA;
	static final String CADASTRAR_LIVRO_QUEUE_NAME = "cadastrar_livro_queue_" + MATRICULA;	
	static final String CADASTRAR_LIVRO_ROUTING_KEY = "livro.cadastrar." + MATRICULA;
	
//    @Bean
//    public Queue queue() {
//        return new Queue(LIVRO_QUEUE_NAME, false);
//    }
//
//    @Bean
//    public DirectExchange exchange() {
//        return new DirectExchange(LIVRO_DIRECT_EXCHANGE_NAME);
//    }
//
//    @Bean
//    public Binding binding(Queue queue, DirectExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(LIVRO_CADASTRAR_ROUTING_KEY);
//    }

//    @Bean
//    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
//            MessageListenerAdapter listenerAdapter) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(CADASTRO_LIVRO_QUEUE_NAME);
//        container.setMessageListener(listenerAdapter);
//        return container;
//    }
//
//    @Bean
//    public MessageListenerAdapter cadastrarLivroListenerAdapter(Receiver receiver) {
//        return new MessageListenerAdapter(receiver, "receiveMessageCadastrarLivro");
//    }
    
	public static void main(String[] args) {
		SpringApplication.run(LivroServiceApplication.class, args);
	}
}