package br.com.hbsis.linha_categoria;


import br.com.hbsis.categoria.Categoria;

import javax.persistence.*;

@Entity
@Table (name = "seg_linhas")
public class Linha_categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    //foreign key  -- referenced column
    @JoinColumn(name = "categoria", referencedColumnName = "id")
    private Categoria categoria;
    @Column (name = "nome_linha", nullable = false, length = 50)
    private String nomeLinha;
    @Column (name = "codigo", nullable = false, length = 10)
    private String codigoLinha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
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
        return "Linha_categoria{" +
                "id=" + id +
                ", categoria=" + categoria +
                ", nomeLinha='" + nomeLinha + '\'' +
                ", codigoLinha='" + codigoLinha + '\'' +
                '}';
    }
}
