create table clientes(
                         id serial not null primary key,
                         nome varchar(100) not null,
                         email varchar(100) not null unique,
                         telefone varchar(11) not null,
                         rua varchar(100) not null,
                         bairro varchar(100) not null,
                         cep varchar(9) not null,
                         complemento varchar(100),
                         numero varchar(4),
                         uf varchar(2) not null,
                         cidade varchar(100) not null
);