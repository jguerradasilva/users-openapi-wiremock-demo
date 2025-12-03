package com.example.usersapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Teste de integração usando WireMock para simular a API de usuários.
 * 
 * WireMock é uma biblioteca que permite criar um servidor HTTP mock
 * para simular APIs externas em testes.
 * 
 * Este teste demonstra:
 * - Como configurar um servidor WireMock
 * - Como mapear endpoints e respostas
 * - Como fazer requisições HTTP para testar os endpoints
 * - Como validar as respostas
 */
@SpringBootTest
public class WireMockUsersApiTest {

    private WireMockServer wireMockServer;
    private HttpClient httpClient;
    private ObjectMapper objectMapper;
    private static final int WIREMOCK_PORT = 9999;
    private static final String BASE_URL = "http://localhost:" + WIREMOCK_PORT;

    /**
     * Configuração executada antes de cada teste.
     * Inicia o servidor WireMock e configura o cliente HTTP.
     */
    @BeforeEach
    void setUp() {
        // Configura e inicia o servidor WireMock
        wireMockServer = new WireMockServer(options()
                .port(WIREMOCK_PORT)
                .bindAddress("localhost"));
        wireMockServer.start();
        
        // Configura o cliente WireMock
        WireMock.configureFor("localhost", WIREMOCK_PORT);
        
        // Inicializa cliente HTTP e ObjectMapper
        httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        objectMapper = new ObjectMapper();
        
        System.out.println("🚀 WireMock Server iniciado em: " + BASE_URL);
    }

    /**
     * Limpeza executada após cada teste.
     * Para o servidor WireMock.
     */
    @AfterEach
    void tearDown() {
        if (wireMockServer != null) {
            wireMockServer.stop();
            System.out.println("🔴 WireMock Server parado");
        }
    }

    /**
     * Teste do endpoint GET /users - Listar usuários.
     * 
     * Simula uma resposta de lista de usuários e faz uma requisição
     * para verificar se a resposta está correta.
     */
    @Test
    void testGetAllUsers() throws IOException, InterruptedException {
        System.out.println("\n📋 Testando GET /users - Listar usuários");
        
        // Configura a resposta mock do WireMock
        String mockResponse = """
                [
                    {
                        "id": 1,
                        "name": "João Silva",
                        "email": "joao@email.com",
                        "age": 30,
                        "phone": "(11) 99999-9999",
                        "createdAt": "2024-01-15T10:00:00",
                        "updatedAt": "2024-01-15T10:00:00"
                    },
                    {
                        "id": 2,
                        "name": "Maria Santos",
                        "email": "maria@email.com",
                        "age": 25,
                        "phone": "(11) 88888-8888",
                        "createdAt": "2024-01-15T11:00:00",
                        "updatedAt": "2024-01-15T11:00:00"
                    }
                ]
                """;
        
        // Mapeia o endpoint no WireMock
        stubFor(get(urlEqualTo("/users"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(mockResponse)));
        
        // Faz a requisição HTTP
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users"))
                .GET()
                .build();
        
        HttpResponse<String> response = httpClient.send(request, 
                HttpResponse.BodyHandlers.ofString());
        
        // Valida a resposta
        assertEquals(200, response.statusCode());
        assertNotNull(response.body());
        assertTrue(response.body().contains("João Silva"));
        assertTrue(response.body().contains("Maria Santos"));
        
        System.out.println("✅ Status: " + response.statusCode());
        System.out.println("📄 Resposta: " + response.body());
        
        // Verifica se o endpoint foi chamado
        verify(getRequestedFor(urlEqualTo("/users")));
    }

    /**
     * Teste do endpoint GET /users/{id} - Buscar usuário por ID.
     */
    @Test
    void testGetUserById() throws IOException, InterruptedException {
        System.out.println("\n🔍 Testando GET /users/1 - Buscar usuário por ID");
        
        String mockResponse = """
                {
                    "id": 1,
                    "name": "João Silva",
                    "email": "joao@email.com",
                    "age": 30,
                    "phone": "(11) 99999-9999",
                    "createdAt": "2024-01-15T10:00:00",
                    "updatedAt": "2024-01-15T10:00:00"
                }
                """;
        
        // Mapeia o endpoint com parâmetro
        stubFor(get(urlEqualTo("/users/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(mockResponse)));
        
        // Faz a requisição
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users/1"))
                .GET()
                .build();
        
        HttpResponse<String> response = httpClient.send(request, 
                HttpResponse.BodyHandlers.ofString());
        
        // Valida a resposta
        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("João Silva"));
        
        System.out.println("✅ Status: " + response.statusCode());
        System.out.println("📄 Resposta: " + response.body());
        
        verify(getRequestedFor(urlEqualTo("/users/1")));
    }

    /**
     * Teste do endpoint POST /users - Criar usuário.
     */
    @Test
    void testCreateUser() throws IOException, InterruptedException {
        System.out.println("\n➕ Testando POST /users - Criar usuário");
        
        // Dados do usuário a ser criado
        Map<String, Object> userData = Map.of(
                "name", "Pedro Oliveira",
                "email", "pedro@email.com",
                "age", 28,
                "phone", "(11) 77777-7777"
        );
        
        String requestBody = objectMapper.writeValueAsString(userData);
        
        String mockResponse = """
                {
                    "id": 3,
                    "name": "Pedro Oliveira",
                    "email": "pedro@email.com",
                    "age": 28,
                    "phone": "(11) 77777-7777",
                    "createdAt": "2024-01-15T12:00:00",
                    "updatedAt": "2024-01-15T12:00:00"
                }
                """;
        
        // Mapeia o endpoint POST
        stubFor(post(urlEqualTo("/users"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(containing("Pedro Oliveira"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody(mockResponse)));
        
        // Faz a requisição POST
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        
        HttpResponse<String> response = httpClient.send(request, 
                HttpResponse.BodyHandlers.ofString());
        
        // Valida a resposta
        assertEquals(201, response.statusCode());
        assertTrue(response.body().contains("Pedro Oliveira"));
        assertTrue(response.body().contains("\"id\":3"));
        
        System.out.println("✅ Status: " + response.statusCode());
        System.out.println("📤 Request: " + requestBody);
        System.out.println("📥 Response: " + response.body());
        
        verify(postRequestedFor(urlEqualTo("/users"))
                .withHeader("Content-Type", equalTo("application/json")));
    }

    /**
     * Teste do endpoint PUT /users/{id} - Atualizar usuário.
     */
    @Test
    void testUpdateUser() throws IOException, InterruptedException {
        System.out.println("\n✏️ Testando PUT /users/1 - Atualizar usuário");
        
        Map<String, Object> updateData = Map.of(
                "name", "João Silva Santos",
                "email", "joao.santos@email.com",
                "age", 31,
                "phone", "(11) 99999-0000"
        );
        
        String requestBody = objectMapper.writeValueAsString(updateData);
        
        String mockResponse = """
                {
                    "id": 1,
                    "name": "João Silva Santos",
                    "email": "joao.santos@email.com",
                    "age": 31,
                    "phone": "(11) 99999-0000",
                    "createdAt": "2024-01-15T10:00:00",
                    "updatedAt": "2024-01-15T13:00:00"
                }
                """;
        
        stubFor(put(urlEqualTo("/users/1"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(mockResponse)));
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users/1"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        
        HttpResponse<String> response = httpClient.send(request, 
                HttpResponse.BodyHandlers.ofString());
        
        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("João Silva Santos"));
        
        System.out.println("✅ Status: " + response.statusCode());
        System.out.println("📤 Request: " + requestBody);
        System.out.println("📥 Response: " + response.body());
        
        verify(putRequestedFor(urlEqualTo("/users/1")));
    }

    /**
     * Teste do endpoint DELETE /users/{id} - Remover usuário.
     */
    @Test
    void testDeleteUser() throws IOException, InterruptedException {
        System.out.println("\n🗑️ Testando DELETE /users/1 - Remover usuário");
        
        stubFor(delete(urlEqualTo("/users/1"))
                .willReturn(aResponse()
                        .withStatus(204)));
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users/1"))
                .DELETE()
                .build();
        
        HttpResponse<String> response = httpClient.send(request, 
                HttpResponse.BodyHandlers.ofString());
        
        assertEquals(204, response.statusCode());
        assertEquals("", response.body());
        
        System.out.println("✅ Status: " + response.statusCode());
        System.out.println("📄 Response: (vazio - esperado para DELETE)");
        
        verify(deleteRequestedFor(urlEqualTo("/users/1")));
    }

    /**
     * Teste de cenário de erro - Usuário não encontrado.
     */
    @Test
    void testUserNotFound() throws IOException, InterruptedException {
        System.out.println("\n❌ Testando cenário de erro - Usuário não encontrado");
        
        String errorResponse = """
                {
                    "timestamp": "2024-01-15T12:00:00",
                    "status": 404,
                    "error": "Not Found",
                    "message": "Usuário não encontrado com ID: 999"
                }
                """;
        
        stubFor(get(urlEqualTo("/users/999"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody(errorResponse)));
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users/999"))
                .GET()
                .build();
        
        HttpResponse<String> response = httpClient.send(request, 
                HttpResponse.BodyHandlers.ofString());
        
        assertEquals(404, response.statusCode());
        assertTrue(response.body().contains("não encontrado"));
        
        System.out.println("✅ Status: " + response.statusCode());
        System.out.println("📄 Response: " + response.body());
        
        verify(getRequestedFor(urlEqualTo("/users/999")));
    }

    /**
     * Demonstração das capacidades do WireMock.
     * Este método mostra informações sobre os testes executados.
     */
    @Test
    void demonstrateWireMockCapabilities() {
        System.out.println("\n🎯 DEMONSTRAÇÃO DAS CAPACIDADES DO WIREMOCK");
        System.out.println("================================================");
        System.out.println("✅ Simulação de endpoints HTTP (GET, POST, PUT, DELETE)");
        System.out.println("✅ Configuração de respostas personalizadas");
        System.out.println("✅ Validação de headers e corpo da requisição");
        System.out.println("✅ Simulação de diferentes status codes");
        System.out.println("✅ Verificação de que endpoints foram chamados");
        System.out.println("✅ Teste de cenários de sucesso e erro");
        System.out.println("\n🔧 Para rodar os testes:");
        System.out.println("mvn test -Dtest=WireMockUsersApiTest");
        System.out.println("\n📸 Screenshots dos testes estão sendo capturados no console!");
    }
}