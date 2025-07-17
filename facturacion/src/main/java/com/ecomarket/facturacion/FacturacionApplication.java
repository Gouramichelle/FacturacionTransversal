package com.ecomarket.facturacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FacturacionApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacturacionApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate() {return new RestTemplate();}
}
