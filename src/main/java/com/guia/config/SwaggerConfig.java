package com.guia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
import org.springdoc.core.GroupedOpenApi;
//import org.springdoc.core.SwaggerUiConfigParameters;

@Configuration
//@Import(SwaggerUiConfigParameters.class)
public class SwaggerConfig {

    @Bean
    GroupedOpenApi api() {
        System.out.println("SwaggerConfig is initialized.");
        return GroupedOpenApi.builder()
            .group("Guia")
            .packagesToScan("com.guia.controller") // Pacote onde estão os controladores
            .build();
    }
}