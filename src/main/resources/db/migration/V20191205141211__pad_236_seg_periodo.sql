create table seg_periodo
(
        id BIGINT IDENTITY (1,1)        NOT NULL PRIMARY KEY,
        inicio_vendas DATE              NOT NULL,
        fim_vendas DATE                 NOT NULL,
        retirada_pedido DATE            NOT NULL,
        descricao VARCHAR(50)           NOT NULL,
        id_fornecedor BIGINT FOREIGN KEY REFERENCES seg_fornecedor(id) NOT NULL
)