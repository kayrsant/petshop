-- Tabela para agendamento de consultas
CREATE TABLE agendamentos (
                              id SERIAL NOT NULL PRIMARY KEY,
                              id_cliente INT NOT NULL REFERENCES clientes(id),
                              id_pet INT NOT NULL REFERENCES pets(id),
                              id_funcionario INT NOT NULL REFERENCES funcionarios(id),
                              data TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              valor DECIMAL(10, 2) NOT NULL DEFAULT 0,
                              id_servico INT NOT NULL REFERENCES servicos(id),
                              status VARCHAR(10) NOT NULL DEFAULT 'aberto',
                              data_inicio TIMESTAMP NOT NULL,
                              data_fim TIMESTAMP NOT NULL
);

-- Tabela para os produtos usados no atendimento
CREATE TABLE produtos_agendamento (
                                      id SERIAL NOT NULL PRIMARY KEY,
                                      id_agendamento INT NOT NULL REFERENCES agendamentos(id),
                                      id_produto INT NOT NULL REFERENCES produtos(id),
                                      quantidade INT NOT NULL DEFAULT 1,
                                      valor DECIMAL(10, 2) NOT NULL DEFAULT 0
);
