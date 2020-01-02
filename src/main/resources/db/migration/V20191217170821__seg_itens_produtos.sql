create table seg_produtos_itens
(
    id_produto BIGINT FOREIGN KEY REFERENCES seg_produtos(id) NOT NULL,
    id_item BIGINT FOREIGN KEY REFERENCES seg_item(id)        NOT NULL
)

create table pedido_produtos(
    pedido_id BIGINT FOREIGN KEY REFERENCES seg_pedidos(id),
    produto_id BIGINT FOREIGN KEY REFERENCES seg_produtos(id)
)

create table pedido_item(
    pedido_id BIGINT FOREIGN KEY REFERENCES seg_pedidos(id),
    id_item BIGINT FOREIGN KEY REFERENCES seg_item(id)
)