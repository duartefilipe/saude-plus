package com.saudeplus.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Saúde+ API")
                .description("API da Plataforma de Saúde Integrada - Conecta pacientes, nutricionistas e educadores físicos")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Saúde+ Team")
                    .email("contato@saudeplus.com")
                    .url("https://saudeplus.com"))
                .license(new License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT")))
            .servers(List.of(
                new Server()
                    .url("http://localhost:8080")
                    .description("Servidor de Desenvolvimento"),
                new Server()
                    .url("https://api.saudeplus.com")
                    .description("Servidor de Produção")
            ));
    }
} 