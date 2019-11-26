create table seg_produtos
(
    id BIGINT IDENTITY (1,1)    NOT NULL PRIMARY KEY,
    nome VARCHAR(100)           NOT NULL,
    id_fornecedor BIGINT FOREIGN KEY REFERENCES seg_fornecedor(id)
);