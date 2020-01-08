package br.com.hbsis.linhaCategoria;

public class LinhaCategoriaDTO {
    private Long id;
    private Long categoria;
    private String nomeLinha;
    private String codigoLinha;

    public LinhaCategoriaDTO(Long id, Long categoriaId, String nomeLinha, String codigoLinha) {
        this.id = id;
        this.categoria = categoriaId;
        this.nomeLinha = nomeLinha;
        this.codigoLinha = codigoLinha;
    }

    public LinhaCategoriaDTO() {
    }

    public static LinhaCategoriaDTO of(LinhaCategoria linha_categoria) {
        return new LinhaCategoriaDTO(
                linha_categoria.getId(),
                linha_categoria.getCategoria().getId(),
                linha_categoria.getNomeLinha(),
                linha_categoria.getCodigoLinha()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoria() {
        return categoria;
    }

    public void setCategoria(Long categoria) {
        this.categoria = categoria;
    }

    public String getNomeLinha() {
        return nomeLinha;
    }

    public void setNomeLinha(String nomeLinha) {
        this.nomeLinha = nomeLinha;
    }

    public String getCodigoLinha() {
        return codigoLinha;
    }

    public void setCodigoLinha(String codigoLinha) {
        this.codigoLinha = codigoLinha;
    }

    @Override
    public String toString() {
        return "Linha_categoriaDTO{" +
                "id=" + id +
                ", categoriaId=" + categoria +
                ", nomeLinha='" + nomeLinha + '\'' +
                ", codigoLinha='" + codigoLinha + '\'' +
                '}';
    }
}

