package br.com.hbsis.Pedidos;

import br.com.hbsis.item.Item;
import br.com.hbsis.produtos.Produtos;

import java.time.LocalDate;
import java.util.List;

public class PedidosDTO {
    private  Long id;
    private String codigo;
    private StatusName status;
    private LocalDate dataCriacao;
    private Long idFornecedor;
    private Long idPeriodo;
    private List<Produtos> produtos;

    public PedidosDTO(Long id, String codigo, StatusName status, LocalDate dataCriacao, Long idFornecedor, Long idPeriodo, List<Produtos> produtos) {
        this.id = id;
        this.codigo = codigo;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.idFornecedor = idFornecedor;
        this.idPeriodo = idPeriodo;
        this.produtos = produtos;
    }

    public static PedidosDTO of(Pedidos pedidos) {
        return new PedidosDTO(
                pedidos.getId(),
                pedidos.getCodigo(),
                pedidos.getStatus(),
                pedidos.getDataCriacao(),
                pedidos.getFornecedor().getId(),
                pedidos.getPeriodo().getId(),
                pedidos.getProdutos()
        );
    }

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

    public Long getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Long idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public Long getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Long idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public List<Produtos> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produtos> produtos) {
        this.produtos = produtos;
    }

    @Override
    public String toString() {
        return "PedidosDTO{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", status=" + status +
                ", dataCriacao=" + dataCriacao +
                ", idFornecedor=" + idFornecedor +
                ", idPeriodo=" + idPeriodo +
                ", produtos=" + produtos +
                '}';
    }
}
