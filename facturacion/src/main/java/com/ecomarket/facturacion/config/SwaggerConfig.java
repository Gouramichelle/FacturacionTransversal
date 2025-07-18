package com.ecomarket.facturacion.config;


import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Microservicio Facturacion")
                        .version("1.0")
                        .description("Documentacion de Apis para el microservicio de Facturacion"));
    }
}

