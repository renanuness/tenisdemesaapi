MERGE INTO users (name, email, password, ranking_points, created_at) KEY(email)
VALUES 
('João Silva', 'joao.silva@email.com', 'senha123', 1200, CURRENT_TIMESTAMP),
('Maria Santos', 'maria.santos@email.com', 'senha456', 950, CURRENT_TIMESTAMP),
('Pedro Costa', 'pedro.costa@email.com', 'senha789', 1100, CURRENT_TIMESTAMP);