package com.example.usersapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para resposta de usuários.
 * 
 * Este DTO é usado para retornar dados do usuário nas respostas da API.
 * Inclui todos os campos que queremos expor, incluindo timestamps.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    /**
     * ID único do usuário.
     */
    private Long id;

    /**
     * Nome do usuário.
     */
    private String name;

    /**
     * Email do usuário.
     */
    private String email;

    /**
     * Idade do usuário.
     */
    private Integer age;

    /**
     * Telefone do usuário.
     */
    private String phone;

    /**
     * Data de criação do usuário.
     */
    private LocalDateTime createdAt;

    /**
     * Data da última atualização do usuário.
     */
    private LocalDateTime updatedAt;
}