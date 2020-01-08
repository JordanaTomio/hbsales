package br.com.hbsis.pedidos;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.periodoVendas.Periodo;
import br.com.hbsis.produtos.Produtos;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "seg_pedidos")
public class Pedidos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = false, length = 10)
    private String codigo;

    @Column(name = "status", nullable = false, length = 50)
    private StatusName status;

    @Column(name = "data_criacao", nullable = false)
    private LocalDate dataCriacao;

    @ManyToOne
    @JoinColumn(name = "id_fornecedor", referencedColumnName = "id")
    private Fornecedor fornecedor;

    @ManyToOne
    @JoinColumn(name = "id_periodo", referencedColumnName = "id")
    private Periodo periodo;

    @Column(name = "valor_total", nullable = false)
    private double valorTotal;

    @ManyToOne
    @JoinColumn(name = "id_funcionario", referencedColumnName = "id")
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name = "produto_id", referencedColumnName = "id")
    private Produtos produto;

    @Column(name = "quantidade_produto", nullable = false, length = 5)
    private Integer quantidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public StatusName getStatus() {
        return status;
    }

    public void setStatus(StatusName status) {
        this.status = status;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }


    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Produtos getProduto() {
        return produto;
    }

    public void setProduto(Produtos produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Pedidos{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", status=" + status +
                ", dataCriacao=" + dataCriacao +
                ", fornecedor=" + fornecedor +
                ", periodo=" + periodo +
                ", valorTotal=" + valorTotal +
                '}';
    }
}
