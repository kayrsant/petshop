create table pets (
                      id SERIAL PRIMARY KEY,
                      nome VARCHAR(100) NOT NULL,
                      idade INTEGER NOT NULL,
                      raca VARCHAR(100) NOT NULL,
                      tipo VARCHAR(100) NOT NULL,
                      id_cliente INT NOT NULL,
                      FOREIGN KEY (id_cliente) REFERENCES clientes(id)
);