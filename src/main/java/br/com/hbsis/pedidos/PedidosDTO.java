package br.com.hbsis.pedidos;

import java.time.LocalDate;

public class PedidosDTO {
    private  Long id;
    private String codigo;
    private StatusName status;
    private LocalDate dataCriacao;
    private Long idFornecedor;
    private Long idPeriodo;
    private double valorTotal;
    private Long idFuncionario;
    private Integer quantidade;
    private Long produto;


    public PedidosDTO(Long id, String codigo, StatusName status, LocalDate dataCriacao, Long idFornecedor, Long idPeriodo, double valorTotal, Long idFuncionario, Integer quantidade, Long produto) {
        this.id = id;
        this.codigo = codigo;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.idFornecedor = idFornecedor;
        this.idPeriodo = idPeriodo;
        this.valorTotal = valorTotal;
        this.idFuncionario = idFuncionario;
        this.quantidade = quantidade;
        this.produto = produto;
    }

    public PedidosDTO() {

    }

    public static PedidosDTO of(Pedidos pedidos) {
        return new PedidosDTO(
                pedidos.getId(),
                pedidos.getCodigo(),
                pedidos.getStatus(),
                pedidos.getDataCriacao(),
                pedidos.getFornecedor().getId(),
                pedidos.getPeriodo().getId(),
                pedidos.getValorTotal(),
                pedidos.getFuncionario().getId(),
                pedidos.getQuantidade(),
                pedidos.getProduto().getId()

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

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Long getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Long idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Long getProduto() {
        return produto;
    }

    public void setProduto(Long produto) {
        this.produto = produto;
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
                ", valorTotal=" + valorTotal +
                ", idFuncionario=" + idFuncionario +
                ", quantidade=" + quantidade +
                ", produto=" + produto +
                '}';
    }
}
