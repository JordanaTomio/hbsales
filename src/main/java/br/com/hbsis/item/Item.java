package br.com.hbsis.item;

import br.com.hbsis.produtos.Produtos;

import javax.persistence.*;

@Entity
@Table(name = "seg_item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @Column(name = "produto_id", nullable = false)
    private Produtos produtos;

    @Column(name = "quantidade", nullable = false, length = 10)
    private int quantidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", produtos=" + produtos +
                ", quantidade=" + quantidade +
                '}';
    }
}
