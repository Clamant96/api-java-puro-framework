CREATE DATABASE lista_pessoas;

USE lista_pessoas;

CREATE TABLE usuario (
	id INT(11) AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE produto (
	id INT(11) AUTO_INCREMENT,
	titulo VARCHAR(255) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    estoque varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE usuario_produto (
	id_usuario INT(11),
    id_produto INT(11),
    CONSTRAINT fk_usuario_id FOREIGN KEY ( id_usuario ) REFERENCES usuario ( id ),
	CONSTRAINT fk_produto_id FOREIGN KEY ( id_produto ) REFERENCES produto ( id )
);