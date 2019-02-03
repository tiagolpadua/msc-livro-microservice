package com.acme.livroservice;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AvaliacaoService {
	
	private final RestTemplate restTemplate;
	
	public AvaliacaoService(RestTemplateBuilder restTemplateBuilder) {
		restTemplate = restTemplateBuilder.build();
	}
	
	public void apagarAvaliacoesPorLivroId(Long livroId) {
		String avaliacaoResourceUrl = "http://localhost:8081/avaliacoes/livro/";
		restTemplate.delete(avaliacaoResourceUrl + livroId);
	}

}
