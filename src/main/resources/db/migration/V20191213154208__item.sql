create table seg_item
(
    id BIGINT IDENTITY (1,1)                                        NOT NULL PRIMARY KEY,
    produto_id BIGINT FOREIGN KEY REFERENCES seg_produtos(id)       NOT NULL,
    quantidade INT                                                  NOT NULL
)