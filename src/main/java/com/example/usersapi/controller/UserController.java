package com.example.usersapi.controller;

import com.example.usersapi.dto.CreateUserRequest;
import com.example.usersapi.dto.UpdateUserRequest;
import com.example.usersapi.dto.UserResponse;
import com.example.usersapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para operações de usuários.
 * 
 * Annotations importantes:
 * @RestController: combina @Controller + @ResponseBody (retorna JSON automaticamente)
 * @RequestMapping: define o path base para todos os endpoints (/users)
 * @RequiredArgsConstructor: injeção de dependência por construtor
 * @Slf4j: logger automático
 * @Tag: documentação OpenAPI para agrupar endpoints
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Users", description = "API para gerenciamento de usuários")
public class UserController {

    private final UserService userService;

    /**
     * GET /users - Lista todos os usuários
     * 
     * @Operation: documentação OpenAPI do endpoint
     * @ApiResponses: documentação das possíveis respostas
     * @GetMapping: mapeia requisições GET para este método
     */
    @Operation(
            summary = "Lista todos os usuários",
            description = "Retorna uma lista com todos os usuários cadastrados no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de usuários retornada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        log.info("Requisição recebida: GET /users");
        
        List<UserResponse> users = userService.getAllUsers();
        
        log.info("Retornando {} usuários", users.size());
        return ResponseEntity.ok(users);
    }

    /**
     * GET /users/{id} - Busca usuário por ID
     * 
     * @PathVariable: extrai valor da URL
     * @Parameter: documentação do parâmetro
     */
    @Operation(
            summary = "Busca usuário por ID",
            description = "Retorna os detalhes de um usuário específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable Long id) {
        
        log.info("Requisição recebida: GET /users/{}", id);
        
        try {
            UserResponse user = userService.getUserById(id);
            log.info("Usuário encontrado: {}", user.getName());
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            log.error("Erro ao buscar usuário: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST /users - Cria novo usuário
     * 
     * @RequestBody: converte JSON do corpo da requisição para objeto
     * @Valid: valida o objeto usando Bean Validation
     */
    @Operation(
            summary = "Cria novo usuário",
            description = "Cadastra um novo usuário no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário criado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou email já existe",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Parameter(description = "Dados do usuário a ser criado", required = true)
            @Valid @RequestBody CreateUserRequest request) {
        
        log.info("Requisição recebida: POST /users - Email: {}", request.getEmail());
        
        try {
            UserResponse createdUser = userService.createUser(request);
            log.info("Usuário criado com sucesso - ID: {}", createdUser.getId());
            
            // Retorna status 201 (Created) com o usuário criado
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException e) {
            log.error("Erro ao criar usuário: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * PUT /users/{id} - Atualiza usuário existente
     * 
     * Combina @PathVariable (ID da URL) com @RequestBody (dados no corpo)
     */
    @Operation(
            summary = "Atualiza usuário existente",
            description = "Atualiza todos os dados de um usuário específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário atualizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou email já existe",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable Long id,
            @Parameter(description = "Novos dados do usuário", required = true)
            @Valid @RequestBody UpdateUserRequest request) {
        
        log.info("Requisição recebida: PUT /users/{} - Email: {}", id, request.getEmail());
        
        try {
            UserResponse updatedUser = userService.updateUser(id, request);
            log.info("Usuário atualizado com sucesso - ID: {}", updatedUser.getId());
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            log.error("Erro ao atualizar usuário: {}", e.getMessage());
            
            // Determina o status code baseado na mensagem de erro
            if (e.getMessage().contains("não encontrado")) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
    }

    /**
     * DELETE /users/{id} - Remove usuário
     * 
     * Retorna apenas status code (sem corpo)
     */
    @Operation(
            summary = "Remove usuário",
            description = "Remove um usuário do sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Usuário removido com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json")
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable Long id) {
        
        log.info("Requisição recebida: DELETE /users/{}", id);
        
        try {
            userService.deleteUser(id);
            log.info("Usuário removido com sucesso - ID: {}", id);
            
            // Retorna status 204 (No Content) - sucesso sem corpo de resposta
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Erro ao remover usuário: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}