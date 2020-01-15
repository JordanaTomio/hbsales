package br.com.hbsis.pedidos;


public class InvoiceItemDTOSet {
    private int amount;
    private String itemName;

    public InvoiceItemDTOSet(int amount, String itemName) {
        this.amount = amount;
        this.itemName = itemName;
    }

    public InvoiceItemDTOSet() {
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
