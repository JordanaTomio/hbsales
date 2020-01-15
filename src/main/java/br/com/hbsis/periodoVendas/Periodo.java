package br.com.hbsis.periodoVendas;

import br.com.hbsis.fornecedor.Fornecedor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "seg_periodo")
public class Periodo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "inicio_vendas", nullable = false)
    private LocalDate inicioVendas;
    @Column(name = "fim_vendas", nullable = false)
    private LocalDate fimVendas;
    @Column(name = "retirada_pedido", nullable = false)
    private LocalDate retiradaPedido;
    @Column(name = "descricao", nullable = false, length = 50)
    private String descricao;
    @ManyToOne
    @JoinColumn(name = "id_fornecedor", referencedColumnName = "id")
    private Fornecedor fornecedor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInicioVendas() {
        return inicioVendas;
    }

    public void setInicioVendas(LocalDate inicioVendas) {
        this.inicioVendas = inicioVendas;
    }

    public LocalDate getFimVendas() {
        return fimVendas;
    }

    public void setFimVendas(LocalDate fimVendas) {
        this.fimVendas = fimVendas;
    }

    public LocalDate getRetiradaPedido() {
        return retiradaPedido;
    }

    public void setRetiradaPedido(LocalDate retiradaPedido) {
        this.retiradaPedido = retiradaPedido;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public String toString() {
        return "Periodo{" +
                "id=" + id +
                ", inicioVendas=" + inicioVendas +
                ", fimVendas=" + fimVendas +
                ", retiradaPedido=" + retiradaPedido +
                ", descricao='" + descricao + '\'' +
                ", fornecedor=" + fornecedor +
                '}';
    }
}

