package br.com.hbsis.periodoVendas;

import java.time.LocalDate;

public class PeriodoDTO {
    private Long id;
    private LocalDate inicioVendas;
    private LocalDate fimVendas;
    private LocalDate retiradaPedido;
    private String descricao;
    private Long idFornecedor;

    public PeriodoDTO(Long id, LocalDate inicioVendas, LocalDate fimVendas, LocalDate retiradaPedido, String descricao, Long idFornecedor) {
        this.id = id;
        this.inicioVendas = inicioVendas;
        this.fimVendas = fimVendas;
        this.retiradaPedido = retiradaPedido;
        this.descricao = descricao;
        this.idFornecedor = idFornecedor;
    }

    public static PeriodoDTO of(Periodo periodo) {
        return new PeriodoDTO(
                periodo.getId(),
                periodo.getInicioVendas(),
                periodo.getFimVendas(),
                periodo.getRetiradaPedido(),
                periodo.getDescricao(),
                periodo.getFornecedor().getId()
        );

    }

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

    public Long getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Long idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    @Override
    public String toString() {
        return "PeriodoDTO{" +
                "id=" + id +
                ", inicioVendas=" + inicioVendas +
                ", fimVendas=" + fimVendas +
                ", retiradaPedido=" + retiradaPedido +
                ", descricao='" + descricao + '\'' +
                ", idFornecedor=" + idFornecedor +
                '}';
    }
}
