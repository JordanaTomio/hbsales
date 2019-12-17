package br.com.hbsis.item;


public class ItemDTO {
    private Long id;
    private Long idProduto;
    private int quantidade;

    public ItemDTO(Long id, Long idProduto, int quantidade) {
        this.id = id;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    public ItemDTO() {

    }

    public static ItemDTO of(Item item) {
        return new ItemDTO(
                item.getId(),
                item.getProdutos().getId(),
                item.getQuantidade()
                );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "id=" + id +
                ", idProduto=" + idProduto +
                ", quantidade=" + quantidade +
                '}';
    }
}
