create table seg_produtos
(
    id BIGINT IDENTITY (1,1)                                        NOT NULL PRIMARY KEY,
    nome VARCHAR(200)                                               NOT NULL,
    codigo VARCHAR(10)                                              NOT NULL,
    preco DECIMAL(20, 2)                                            NOT NULL,
    unidade_caixa INTEGER                                           NOT NULL,
    peso_unidade DECIMAL(20,3)                                      NOT NULL,
    unidade_medida VARCHAR(3)                                       NOT NULL,
    validade DATE                                                   NOT NULL,
    id_categoria BIGINT FOREIGN KEY REFERENCES seg_linhas(id)       NOT NULL
);
create unique index ix_seg_produtos_01 on seg_produtos (codigo asc);
