-- Script SQL para popular dados iniciais no banco H2
-- Este arquivo será executado automaticamente pelo Spring Boot na inicialização

-- Inserir usuários de exemplo
INSERT INTO users (id, name, email, age, phone, created_at, updated_at) VALUES 
(1, 'João Silva', 'joao@email.com', 30, '(11) 99999-9999', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
(2, 'Maria Santos', 'maria@email.com', 25, '(11) 88888-8888', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
(3, 'Pedro Oliveira', 'pedro@email.com', 28, '(11) 77777-7777', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
(4, 'Ana Costa', 'ana@email.com', 32, '(11) 66666-6666', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
(5, 'Carlos Mendes', 'carlos@email.com', 45, '(11) 55555-5555', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

-- Configurar sequence para próximos IDs
ALTER SEQUENCE users_seq RESTART WITH 6;