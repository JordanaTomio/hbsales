package br.com.hbsis.categoria;

import br.com.hbsis.fornecedor.Fornecedor;

import javax.persistence.*;

@Entity
@Table(name = "seg_categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "codigo", nullable = false, length = 10)
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "id_fornecedor", referencedColumnName = "id")
    private Fornecedor fornecedor;


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

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", codigo='" + codigo + '\'' +
                ", fornecedor=" + fornecedor +
                '}';
    }
}
