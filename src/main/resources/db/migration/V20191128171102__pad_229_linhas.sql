create table seg_linhas
(
    id BIGINT IDENTITY (1,1)    NOT NULL PRIMARY KEY,
    categoria BIGINT FOREIGN KEY REFERENCES seg_categorias(id) NOT NULL,
    codigo VARCHAR(10)         NOT NULL,
    nome_linha VARCHAR(50)     NOT NULL
);
create unique index ix_seg_seg_linhas_01 on seg_linhas (codigo asc);