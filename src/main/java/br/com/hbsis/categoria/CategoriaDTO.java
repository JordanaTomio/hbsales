package br.com.hbsis.categoria;

public class CategoriaDTO {
    private Long id;
    private String nome;
    private Long idFornecedor;
    private String codigo;

    public CategoriaDTO(Long id, String nome, Long idFornecedor, String codigo) {
        this.id = id;
        this.nome = nome;
        this.idFornecedor = idFornecedor;
        this.codigo = codigo;
    }

    public CategoriaDTO() {
    }

    public static CategoriaDTO of(Categoria categoria) {
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getFornecedor().getId(),
                categoria.getCodigo()
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

    public Long getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Long idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "CategoriaDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idFornecedor=" + idFornecedor +
                ", codigo='" + codigo + '\'' +
                '}';
    }
}


