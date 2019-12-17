package br.com.hbsis.item;

public class invoiceItemDTOSet {
    private int amount;
    private String itemName;

    public invoiceItemDTOSet(int amount, String itemName) {
        this.amount = amount;
        this.itemName = itemName;
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
