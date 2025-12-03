package com.example.usersapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidade User que representa um usuário no sistema.
 * 
 * Annotations utilizadas:
 * @Entity: marca a classe como uma entidade JPA
 * @Table: especifica detalhes da tabela no banco de dados
 * @Data: gera automaticamente getters, setters, toString, equals e hashCode
 * @NoArgsConstructor: gera construtor sem parâmetros (necessário para JPA)
 * @AllArgsConstructor: gera construtor com todos os parâmetros
 */
@Entity
@Table(name = "users")  // Nome da tabela no banco de dados
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * Chave primária da entidade.
     * @Id: marca o campo como chave primária
     * @GeneratedValue: especifica como o valor é gerado automaticamente
     * GenerationType.IDENTITY: usa auto-incremento do banco de dados
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do usuário.
     * @Column: especifica detalhes da coluna no banco
     * @NotBlank: validação que garante que o campo não seja nulo, vazio ou apenas espaços
     * @Size: validação de tamanho mínimo e máximo
     */
    @Column(name = "name", nullable = false, length = 100)
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String name;

    /**
     * Email do usuário.
     * @Email: validação de formato de email
     * unique = true: garante que não haverá emails duplicados no banco
     */
    @Column(name = "email", nullable = false, unique = true, length = 150)
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter um formato válido")
    @Size(max = 150, message = "Email deve ter no máximo 150 caracteres")
    private String email;

    /**
     * Idade do usuário (campo opcional).
     */
    @Column(name = "age")
    private Integer age;

    /**
     * Telefone do usuário (campo opcional).
     */
    @Column(name = "phone", length = 20)
    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    private String phone;

    /**
     * Data de criação do registro.
     * @Column: updatable = false garante que este campo não será alterado em updates
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Data da última atualização do registro.
     */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Método executado antes de persistir a entidade no banco.
     * @PrePersist: annotation que marca métodos para execução antes da inserção
     */
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Método executado antes de atualizar a entidade no banco.
     * @PreUpdate: annotation que marca métodos para execução antes da atualização
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Construtor para criar usuário sem ID (usado na criação).
     */
    public User(String name, String email, Integer age, String phone) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.phone = phone;
    }
}