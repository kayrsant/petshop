-- Tabela para agendamento de consultas
CREATE TABLE agendamentos (
                              id SERIAL NOT NULL PRIMARY KEY,
                              id_cliente INT NOT NULL REFERENCES clientes(id),
                              id_pet INT NOT NULL REFERENCES pets(id),
                              id_funcionario INT NOT NULL REFERENCES funcionarios(id),
                              data TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              valor DECIMAL(10, 2) NOT NULL DEFAULT 0,
                              status VARCHAR(10) NOT NULL DEFAULT 'aberto'
);

-- Tabela para os produtos usados no atendimento
CREATE TABLE produtos_agendamento (
                                      id SERIAL NOT NULL PRIMARY KEY,
                                      id_agendamento INT NOT NULL REFERENCES agendamentos(id),
                                      id_produto INT NOT NULL REFERENCES produtos(id),
                                      quantidade INT NOT NULL DEFAULT 1,
                                      valor DECIMAL(10, 2) NOT NULL DEFAULT 0
);

CREATE TABLE servicos_agendamento (
                                      id SERIAL PRIMARY KEY,
                                      id_agendamento BIGINT REFERENCES agendamentos(id) NOT NULL,
                                      id_servico BIGINT REFERENCES servicos(id) NOT NULL,
                                      valor DOUBLE PRECISION NOT NULL
);

