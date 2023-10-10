CREATE TABLE servicos (
                          id SERIAL PRIMARY KEY,
                          nome VARCHAR(255) NOT NULL,
                          descricao TEXT,
                          preco DECIMAL(10, 2) DEFAULT 0
);
