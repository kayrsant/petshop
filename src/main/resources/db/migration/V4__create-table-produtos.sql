CREATE TABLE produtos (
                          id SERIAL PRIMARY KEY,
                          nome VARCHAR(255) NOT NULL,
                          descricao VARCHAR(255) NOT NULL,
                          preco DECIMAL(10, 2) NOT NULL,
                          tipo VARCHAR(100) NOT NULL
);

