package com.guia.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    GroupedOpenApi api() {
        System.out.println("SwaggerConfig is initialized.");
        return GroupedOpenApi.builder()
            .group("Guia")
            .packagesToScan("com.guia") // Pacote onde estão os controladores
            .build();
    }
}