package com.acme.livroservice;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LivroServiceApplication {

	static final String MATRICULA = "NNNNNNNN";
	static final String LIVRO_DIRECT_EXCHANGE_NAME = "livro-direct-exchange-" + MATRICULA;
	static final String LIVRO_FANOUT_EXCHANGE_NAME = "livro-fanout-exchange";
	static final String LIVRO_TOPIC_EXCHANGE_NAME = "livro-topic-exchange";
	static final String CADASTRAR_LIVRO_QUEUE_NAME = "cadastrar_livro_queue_" + MATRICULA;
	static final String CADASTRAR_LIVRO_ROUTING_KEY = "livro.cadastrar." + MATRICULA;

	static final String EXCLUIR_LIVRO_QUEUE_NAME = "excluir_livro_queue_" + MATRICULA;
	static final String EXCLUIR_LIVRO_ROUTING_KEY = "livro.excluir." + MATRICULA;

	@Bean
	public Queue cadastrarLivroQueue() {
		return new Queue(CADASTRAR_LIVRO_QUEUE_NAME);
	}

	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange(LIVRO_FANOUT_EXCHANGE_NAME);
	}

	@Bean
	public Binding fanoutBinding(Queue cadastrarLivroQueue, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(cadastrarLivroQueue).to(fanoutExchange);
	}

	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(LIVRO_TOPIC_EXCHANGE_NAME);
	}

	@Bean
	public Binding topicBinding(Queue cadastrarLivroQueue, TopicExchange topicExchange) {
		return BindingBuilder.bind(cadastrarLivroQueue).to(topicExchange).with(CADASTRAR_LIVRO_ROUTING_KEY);
	}

	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory,
			MessageConverter producerJackson2MessageConverter) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter);
		return rabbitTemplate;
	}

	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	public static void main(String[] args) {
		SpringApplication.run(LivroServiceApplication.class, args);
	}
}