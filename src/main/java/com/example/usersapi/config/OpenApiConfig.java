package com.example.usersapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuração do OpenAPI (Swagger).
 * 
 * Esta classe personaliza a documentação da API que será gerada
 * automaticamente pelo SpringDoc OpenAPI.
 * 
 * @Configuration: marca a classe como fonte de configurações do Spring
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configura as informações básicas da documentação OpenAPI.
     * 
     * @Bean: marca o método como um bean gerenciado pelo Spring
     * @return configuração OpenAPI personalizada
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Users API")
                        .description("""
                                API RESTful para gerenciamento de usuários desenvolvida com Spring Boot 3.
                                
                                Esta API permite:
                                - ✅ Listar todos os usuários
                                - ✅ Criar novos usuários
                                - ✅ Buscar usuários por ID
                                - ✅ Atualizar usuários existentes
                                - ✅ Remover usuários
                                
                                ### Tecnologias utilizadas:
                                - **Java 17**
                                - **Spring Boot 3**
                                - **Spring Web**
                                - **Spring Data JPA**
                                - **Spring Validation**
                                - **H2 Database (in-memory)**
                                - **Lombok**
                                - **SpringDoc OpenAPI**
                                
                                ### Banco de dados:
                                A aplicação utiliza H2 Database em memória para desenvolvimento.
                                Os dados são perdidos a cada reinicialização da aplicação.
                                
                                ### Console do H2:
                                Acesse o console do banco em: [/h2-console](http://localhost:8080/h2-console)
                                - **URL**: jdbc:h2:mem:testdb
                                - **Usuário**: sa
                                - **Senha**: (vazio)
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipe de Desenvolvimento")
                                .email("dev@example.com")
                                .url("https://example.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de Desenvolvimento Local"),
                        new Server()
                                .url("https://api.example.com")
                                .description("Servidor de Produção (Exemplo)")
                ));
    }
}