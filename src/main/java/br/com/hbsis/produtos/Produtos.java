package br.com.hbsis.produtos;

//iniciado 2:05
import br.com.hbsis.linhaCategoria.LinhaCategoria;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "seg_produtos")
public class Produtos {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @Column(name = "nome", nullable = false, length = 100)
   private String nome;
   @Column(name = "codigo", nullable = false, length = 10)
   private String codigo;
   @Column(name = "preco", nullable = false, length = 20)
   private double preco;
   @Column(name = "unidade_caixa", nullable = false)
   private Integer unidadeCaixa;
   @Column(name = "peso_unidade", nullable = false, length = 20)
   private double pesoUnidade;
   @Column(name = "validade", nullable = false, length = 10)
   private LocalDate validade;
   @Column(name = "unidade_medida", nullable = false, length = 3)
   private String unidadeMedida;


   @ManyToOne
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    private LinhaCategoria linha;

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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Integer getUnidadeCaixa() {
        return unidadeCaixa;
    }

    public void setUnidadeCaixa(Integer unidadeCaixa) {
        this.unidadeCaixa = unidadeCaixa;
    }

    public double getPesoUnidade() {
        return pesoUnidade;
    }

    public void setPesoUnidade(double pesoUnidade) {
        this.pesoUnidade = pesoUnidade;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public LinhaCategoria getLinha() {
        return linha;
    }

    public void setLinha(LinhaCategoria linha) {
        this.linha = linha;
    }

    @Override
    public String toString() {
        return "Produtos{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", codigo='" + codigo + '\'' +
                ", preco=" + preco +
                ", unidadeCaixa=" + unidadeCaixa +
                ", pesoUnidade=" + pesoUnidade +
                ", validade=" + validade +
                ", unidadeMedida='" + unidadeMedida + '\'' +
                ", linha=" + linha +
                '}';
    }
}

