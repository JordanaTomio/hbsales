package br.com.hbsis.Pedidos;

import br.com.hbsis.item.invoiceItemDTOSet;

import java.util.List;
import java.util.Set;

public class PedidoSavingDTO {
        private String cnpjFornecedor;
        private String employeeUuid;
        private Set<br.com.hbsis.item.invoiceItemDTOSet> invoiceItemDTOSet;
        private double totalValue;

    public PedidoSavingDTO(String cnpjFornecedor, String employeeUuid, Set<br.com.hbsis.item.invoiceItemDTOSet> invoiceItemDTOSet, double totalValue) {
        this.cnpjFornecedor = cnpjFornecedor;
        this.employeeUuid = employeeUuid;
        this.invoiceItemDTOSet = invoiceItemDTOSet;
        this.totalValue = totalValue;
    }

    public PedidoSavingDTO() {

    }

    public String getCnpjFornecedor() {
        return cnpjFornecedor;
    }

    public void setCnpjFornecedor(String cnpjFornecedor) {
        this.cnpjFornecedor = cnpjFornecedor;
    }

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }

    public Set<br.com.hbsis.item.invoiceItemDTOSet> getInvoiceItemDTOSet() {
        return invoiceItemDTOSet;
    }

    public void setInvoiceItemDTOSet(Set<br.com.hbsis.item.invoiceItemDTOSet> invoiceItemDTOSet) {
        this.invoiceItemDTOSet = invoiceItemDTOSet;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }
}
