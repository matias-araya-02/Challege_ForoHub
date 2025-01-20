
CREATE TABLE topicos(
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        titulo VARCHAR(100) NOT NULL,
                        mensaje VARCHAR(2000) NOT NULL,
                        fecha_creacion DATETIME NOT NULL,
                        status TINYINT DEFAULT 0,
                        complemento VARCHAR(100),
                        autor VARCHAR(100),
                        nombre_del_curso VARCHAR(100) NOT NULL,
                        PRIMARY KEY (id),
                        CONSTRAINT unique_titulo UNIQUE (titulo),
                        CONSTRAINT unique_mensaje UNIQUE (mensaje)
);