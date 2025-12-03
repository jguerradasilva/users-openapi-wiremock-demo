package com.example.usersapi.repository;

import com.example.usersapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface para operações de dados da entidade User.
 * 
 * @Repository: marca a interface como um componente de acesso a dados
 * JpaRepository<User, Long>: 
 * - User: tipo da entidade
 * - Long: tipo da chave primária
 * 
 * JpaRepository já fornece métodos básicos como:
 * - save(user): salvar/atualizar usuário
 * - findById(id): buscar por ID
 * - findAll(): listar todos
 * - deleteById(id): deletar por ID
 * - existsById(id): verificar se existe
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Método para buscar usuário por email.
     * O Spring Data JPA gera automaticamente a implementação
     * baseada no nome do método (findBy + nome do campo).
     * 
     * @param email email do usuário
     * @return Optional contendo o usuário se encontrado
     */
    Optional<User> findByEmail(String email);

    /**
     * Método para verificar se existe usuário com determinado email.
     * Útil para validar duplicidade de email.
     * 
     * @param email email a ser verificado
     * @return true se existir, false caso contrário
     */
    boolean existsByEmail(String email);

    /**
     * Método customizado usando @Query para buscar usuários por nome
     * ignorando case (maiúsculas/minúsculas).
     * 
     * @Query: permite escrever JPQL customizada
     * LOWER(): função SQL para converter para minúsculas
     * LIKE %: busca por substring
     * 
     * @param name nome ou parte do nome a ser buscado
     * @return lista de usuários que contêm o nome especificado
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    java.util.List<User> findByNameContainingIgnoreCase(@Param("name") String name);

    /**
     * Método para buscar usuários por idade.
     * Exemplo de método derivado com relacionamento simples.
     * 
     * @param age idade a ser buscada
     * @return lista de usuários com a idade especificada
     */
    java.util.List<User> findByAge(Integer age);

    /**
     * Método para buscar usuários em uma faixa de idade.
     * Exemplo de método derivado com condições Between.
     * 
     * @param minAge idade mínima
     * @param maxAge idade máxima
     * @return lista de usuários na faixa de idade especificada
     */
    java.util.List<User> findByAgeBetween(Integer minAge, Integer maxAge);
}