create table seg_pedidos
(
    id BIGINT IDENTITY (1,1)                                            NOT NULL PRIMARY KEY,
    codigo VARCHAR(10)                                                  NOT NULL,
    status INT                                                          NOT NULL,
    data_criacao DATE                                                   NOT NULL,
    valor_total DECIMAL(20, 2)                                          NOT NULL,
    id_fornecedor BIGINT FOREIGN KEY REFERENCES seg_fornecedor(id)      NOT NULL,
    id_periodo BIGINT FOREIGN KEY REFERENCES seg_periodo(id)            NOT NULL
)
create unique index ix_seg_pedidos_01 on seg_pedidos (codigo asc);