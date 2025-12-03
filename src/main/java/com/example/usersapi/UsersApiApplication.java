package com.example.usersapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Spring Boot.
 * 
 * @SpringBootApplication é uma annotation que combina:
 * - @Configuration: marca a classe como fonte de definições de bean
 * - @EnableAutoConfiguration: habilita a configuração automática do Spring Boot
 * - @ComponentScan: habilita o escaneamento de componentes no pacote atual e sub-pacotes
 */
@SpringBootApplication
public class UsersApiApplication {

    /**
     * Método principal que inicia a aplicação Spring Boot.
     * 
     * @param args argumentos da linha de comando
     */
    public static void main(String[] args) {
        // SpringApplication.run() inicia o contexto do Spring e o servidor web embarcado
        SpringApplication.run(UsersApiApplication.class, args);
    }
}