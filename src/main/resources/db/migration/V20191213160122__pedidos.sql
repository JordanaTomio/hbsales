create table seg_pedidos
(
    id BIGINT IDENTITY (1,1)                                            NOT NULL PRIMARY KEY,
    codigo VARCHAR(50)                                                  NOT NULL,
    status INT                                                          NOT NULL,
    data_criacao DATE                                                   NOT NULL,
    valor_total DECIMAL(20, 2)                                          NOT NULL,
    id_fornecedor BIGINT FOREIGN KEY REFERENCES seg_fornecedor(id)      NOT NULL,
    id_periodo BIGINT FOREIGN KEY REFERENCES seg_periodo(id)            NOT NULL,
    id_funcionario BIGINT FOREIGN KEY REFERENCES seg_funcionarios(id)   NOT NULL,
    produto_id BIGINT FOREIGN KEY REFERENCES seg_produtos(id)           NOT NULL,
    quantidade_produto INT                                              NOT NULL
)
create unique index ix_seg_pedidos_01 on seg_pedidos (codigo asc);