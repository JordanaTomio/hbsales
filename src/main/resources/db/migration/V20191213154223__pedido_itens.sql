create table seg_pedido_itens
(
    pedido_id BIGINT FOREIGN KEY REFERENCES seg_pedidos(id) NOT NULL,
    item_id BIGINT FOREIGN KEY REFERENCES seg_item(id)      NOT NULL
)