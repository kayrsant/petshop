CREATE TABLE usuarios (
                          id SERIAL PRIMARY KEY,
                          login VARCHAR(100) NOT NULL,
                          senha VARCHAR(100) NOT NULL,
                          permissao VARCHAR(4)
);

CREATE TABLE clientes (
                          id SERIAL PRIMARY KEY,
                          nome VARCHAR(100) NOT NULL,
                          email VARCHAR(100) NOT NULL UNIQUE,
                          telefone VARCHAR(11) NOT NULL,
                          rua VARCHAR(100) NOT NULL,
                          bairro VARCHAR(100) NOT NULL,
                          cep VARCHAR(9) NOT NULL,
                          complemento VARCHAR(100),
                          numero VARCHAR(4),
                          uf VARCHAR(2) NOT NULL,
                          cidade VARCHAR(100) NOT NULL
);

CREATE TABLE pets (
                      id SERIAL PRIMARY KEY,
                      nome VARCHAR(100) NOT NULL,
                      idade INTEGER NOT NULL,
                      raca VARCHAR(100) NOT NULL,
                      tipo VARCHAR(100) NOT NULL,
                      id_cliente INT NOT NULL,
                      FOREIGN KEY (id_cliente) REFERENCES clientes(id)
);

CREATE TABLE produtos (
                          id SERIAL PRIMARY KEY,
                          nome VARCHAR(255) NOT NULL,
                          descricao VARCHAR(255) NOT NULL,
                          preco DECIMAL(10, 2) NOT NULL,
                          tipo VARCHAR(100) NOT NULL
);

CREATE TABLE servicos (
                          id SERIAL PRIMARY KEY,
                          nome VARCHAR(255) NOT NULL,
                          descricao TEXT,
                          preco DECIMAL(10, 2) DEFAULT 0
);

CREATE TABLE funcionarios (
                              id SERIAL PRIMARY KEY,
                              cargo VARCHAR(100) NOT NULL,
                              nome VARCHAR(100) NOT NULL,
                              email VARCHAR(100) NOT NULL UNIQUE,
                              telefone VARCHAR(11) NOT NULL,
                              rua VARCHAR(100) NOT NULL,
                              bairro VARCHAR(100) NOT NULL,
                              cep VARCHAR(9) NOT NULL,
                              complemento VARCHAR(100),
                              numero VARCHAR(4),
                              uf VARCHAR(2) NOT NULL,
                              cidade VARCHAR(100) NOT NULL
);

CREATE TABLE agendamentos (
                              id SERIAL PRIMARY KEY,
                              id_cliente INT NOT NULL REFERENCES clientes(id),
                              id_pet INT NOT NULL REFERENCES pets(id),
                              id_funcionario INT NOT NULL REFERENCES funcionarios(id),
                              data TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              valor DECIMAL(10, 2) NOT NULL DEFAULT 0,
                              status VARCHAR(10) NOT NULL DEFAULT 'aberto'
);

CREATE TABLE produtos_agendamento (
                                      id SERIAL PRIMARY KEY,
                                      id_agendamento INT NOT NULL REFERENCES agendamentos(id),
                                      id_produto INT NOT NULL REFERENCES produtos(id),
                                      quantidade INT NOT NULL DEFAULT 1,
                                      valor DECIMAL(10, 2) NOT NULL DEFAULT 0
);

CREATE TABLE servicos_agendamento (
                                      id SERIAL PRIMARY KEY,
                                      id_agendamento INT REFERENCES agendamentos(id) NOT NULL,
                                      id_servico INT REFERENCES servicos(id) NOT NULL,
                                      valor DOUBLE PRECISION NOT NULL
);

CREATE TABLE vendas (
                        id SERIAL PRIMARY KEY,
                        id_cliente INT NOT NULL,
                        data TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                        valor_total DOUBLE PRECISION NOT NULL,
                        FOREIGN KEY (id_cliente) REFERENCES clientes(id)
);

CREATE TABLE produtos_venda (
                                id SERIAL PRIMARY KEY,
                                id_venda INT NOT NULL,
                                produto_id INT NOT NULL,
                                quantidade INT NOT NULL,
                                FOREIGN KEY (id_venda) REFERENCES vendas(id),
                                FOREIGN KEY (produto_id) REFERENCES produtos(id)
);
