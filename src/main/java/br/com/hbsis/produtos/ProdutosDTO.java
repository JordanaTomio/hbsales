package br.com.hbsis.produtos;

import java.time.LocalDate;

public class ProdutosDTO {
    private Long id;
    private Long idLinha;
    private String nome;
    private String codigo;
    private double preco;
    private Integer unidadeCaixa;
    private double pesoUnidade;
    private LocalDate validade;
    private String unidadeMedida;

    public ProdutosDTO(Long id, Long idLinha, String nome, String codigo, double preco, Integer unidadeCaixa, double pesoUnidade, LocalDate validade, String unidadeMedida) {
        this.id = id;
        this.idLinha = idLinha;
        this.nome = nome;
        this.codigo = codigo;
        this.preco = preco;
        this.unidadeCaixa = unidadeCaixa;
        this.pesoUnidade = pesoUnidade;
        this.validade = validade;
        this.unidadeMedida = unidadeMedida;
    }

    public ProdutosDTO() {
    }


    public static ProdutosDTO of(Produtos produtos) {
        return new ProdutosDTO(
                produtos.getId(),
                produtos.getLinha().getId(),
                produtos.getNome(),
                produtos.getCodigo(),
                produtos.getPreco(),
                produtos.getUnidadeCaixa(),
                produtos.getPesoUnidade(),
                produtos.getValidade(),
                produtos.getUnidadeMedida()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdLinha() {
        return idLinha;
    }

    public void setIdLinha(Long idLinha) {
        this.idLinha = idLinha;
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

    @Override
    public String toString() {
        return "ProdutosDTO{" +
                "id=" + id +
                ", idLinha=" + idLinha +
                ", nome='" + nome + '\'' +
                ", codigo='" + codigo + '\'' +
                ", preco=" + preco +
                ", unidadeCaixa=" + unidadeCaixa +
                ", pesoUnidade=" + pesoUnidade +
                ", validade=" + validade +
                ", unidadeMedida='" + unidadeMedida + '\'' +
                '}';
    }
}
