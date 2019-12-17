create table seg_categorias
(
    id BIGINT IDENTITY (1,1)    NOT NULL PRIMARY KEY,
    nome VARCHAR(50)           NOT NULL,
    codigo VARCHAR(10)          NOT NULL,
    id_fornecedor BIGINT FOREIGN KEY REFERENCES seg_fornecedor(id) NOT NULL
);
create unique index ix_seg_seg_categorias_01 on seg_categorias (codigo asc);