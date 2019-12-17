package br.com.hbsis.produtos;

public class CategoriaDTO {
    Long id;
    String nome;
    Long id_fornecedor;

    CategoriaDTO(){

    }

    public CategoriaDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public CategoriaDTO(Long id, String nome, Long id_fornecedor) {
        this.id = id;
        this.nome = nome;
        this.id_fornecedor = id_fornecedor;
    }

    public static CategoriaDTO of(Categoria categoria) {
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getId_fornecedor()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId_fornecedor() {
        return id_fornecedor;
    }

    public void setId_fornecedor(Long id_fornecedor) {
        this.id_fornecedor = id_fornecedor;
    }

    @Override
    public String toString() {
        return "CategoriaDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", id_fornecedor=" + id_fornecedor +
                '}';
    }
}

