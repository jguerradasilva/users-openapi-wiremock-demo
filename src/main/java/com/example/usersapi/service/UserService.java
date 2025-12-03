package com.example.usersapi.service;

import com.example.usersapi.dto.CreateUserRequest;
import com.example.usersapi.dto.UpdateUserRequest;
import com.example.usersapi.dto.UserResponse;
import com.example.usersapi.model.User;
import com.example.usersapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service para lógica de negócio relacionada a usuários.
 * 
 * @Service: marca a classe como um componente de serviço do Spring
 * @RequiredArgsConstructor: gera construtor com campos final (injeção por construtor)
 * @Slf4j: gera automaticamente um logger para a classe
 * @Transactional: garante que métodos rodem em transação de banco
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;

    /**
     * Lista todos os usuários.
     * 
     * @return lista de UserResponse
     */
    @Transactional(readOnly = true)  // Otimização para operações de leitura
    public List<UserResponse> getAllUsers() {
        log.info("Buscando todos os usuários");
        
        List<User> users = userRepository.findAll();
        log.info("Encontrados {} usuários", users.size());
        
        // Converte lista de User para lista de UserResponse
        return users.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca usuário por ID.
     * 
     * @param id ID do usuário
     * @return UserResponse
     * @throws RuntimeException se usuário não for encontrado
     */
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        log.info("Buscando usuário com ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Usuário não encontrado com ID: {}", id);
                    return new RuntimeException("Usuário não encontrado com ID: " + id);
                });
        
        log.info("Usuário encontrado: {}", user.getName());
        return convertToResponse(user);
    }

    /**
     * Cria um novo usuário.
     * 
     * @param request dados do usuário a ser criado
     * @return UserResponse do usuário criado
     * @throws RuntimeException se email já existir
     */
    public UserResponse createUser(CreateUserRequest request) {
        log.info("Criando novo usuário com email: {}", request.getEmail());
        
        // Verifica se email já existe
        if (userRepository.existsByEmail(request.getEmail())) {
            log.error("Email já existe: {}", request.getEmail());
            throw new RuntimeException("Email já está em uso: " + request.getEmail());
        }
        
        // Converte DTO para entidade
        User user = convertToEntity(request);
        
        // Salva no banco
        User savedUser = userRepository.save(user);
        log.info("Usuário criado com sucesso - ID: {}", savedUser.getId());
        
        return convertToResponse(savedUser);
    }

    /**
     * Atualiza um usuário existente.
     * 
     * @param id ID do usuário a ser atualizado
     * @param request novos dados do usuário
     * @return UserResponse do usuário atualizado
     * @throws RuntimeException se usuário não for encontrado ou email já existir
     */
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        log.info("Atualizando usuário com ID: {}", id);
        
        // Busca usuário existente
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Usuário não encontrado com ID: {}", id);
                    return new RuntimeException("Usuário não encontrado com ID: " + id);
                });
        
        // Verifica se email já existe em outro usuário
        userRepository.findByEmail(request.getEmail())
                .filter(user -> !user.getId().equals(id))  // Ignora o próprio usuário
                .ifPresent(user -> {
                    log.error("Email já existe em outro usuário: {}", request.getEmail());
                    throw new RuntimeException("Email já está em uso por outro usuário: " + request.getEmail());
                });
        
        // Atualiza dados
        existingUser.setName(request.getName());
        existingUser.setEmail(request.getEmail());
        existingUser.setAge(request.getAge());
        existingUser.setPhone(request.getPhone());
        
        // Salva no banco (o @PreUpdate será chamado automaticamente)
        User updatedUser = userRepository.save(existingUser);
        log.info("Usuário atualizado com sucesso - ID: {}", updatedUser.getId());
        
        return convertToResponse(updatedUser);
    }

    /**
     * Remove um usuário.
     * 
     * @param id ID do usuário a ser removido
     * @throws RuntimeException se usuário não for encontrado
     */
    public void deleteUser(Long id) {
        log.info("Removendo usuário com ID: {}", id);
        
        // Verifica se usuário existe
        if (!userRepository.existsById(id)) {
            log.error("Usuário não encontrado com ID: {}", id);
            throw new RuntimeException("Usuário não encontrado com ID: " + id);
        }
        
        userRepository.deleteById(id);
        log.info("Usuário removido com sucesso - ID: {}", id);
    }

    /**
     * Converte CreateUserRequest para User entity.
     */
    private User convertToEntity(CreateUserRequest request) {
        return new User(
                request.getName(),
                request.getEmail(),
                request.getAge(),
                request.getPhone()
        );
    }

    /**
     * Converte User entity para UserResponse DTO.
     */
    private UserResponse convertToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge(),
                user.getPhone(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}