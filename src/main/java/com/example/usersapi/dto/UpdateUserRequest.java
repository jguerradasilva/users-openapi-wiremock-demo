package com.example.usersapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para atualização de usuários.
 * 
 * Similar ao CreateUserRequest, mas usado especificamente
 * para operações de atualização (PUT).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    /**
     * Nome do usuário - obrigatório.
     */
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String name;

    /**
     * Email do usuário - obrigatório e deve ter formato válido.
     */
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter um formato válido")
    @Size(max = 150, message = "Email deve ter no máximo 150 caracteres")
    private String email;

    /**
     * Idade do usuário - opcional.
     */
    private Integer age;

    /**
     * Telefone do usuário - opcional.
     */
    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    private String phone;
}