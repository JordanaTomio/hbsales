create table seg_fornecedor
(
    id BIGINT IDENTITY (1,1)    NOT NULL PRIMARY KEY,
    razao VARCHAR(100)          NOT NULL,
    cnpj VARCHAR(14)            NOT NULL,
    nomefantasia VARCHAR(100)   NOT NULL,
    endereco VARCHAR(100)       NOT NULL,
    telefone VARCHAR(12)        NOT NULL,
    email VARCHAR(50)           NOT NULL
);

create unique index ix_seg_fornecedor_01 on seg_fornecedor (cnpj asc);

