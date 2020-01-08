package br.com.hbsis.pedidos;


import java.util.Set;

public class InvoiceDTO {
        private String cnpjFornecedor;
        private String employeeUuid;
        private Set<InvoiceItemDTOSet> invoiceItemDTOSet;
        private double totalValue;


    public InvoiceDTO(String cnpjFornecedor, String employeeUuid, Set<InvoiceItemDTOSet> invoiceItemDTOSet, double totalValue, PedidosService pedidosService) {
        this.cnpjFornecedor = cnpjFornecedor;
        this.employeeUuid = employeeUuid;
        this.invoiceItemDTOSet = invoiceItemDTOSet;
        this.totalValue = totalValue;
    }

    public InvoiceDTO() {

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

    public Set<InvoiceItemDTOSet> getInvoiceItemDTOSet() {
        return invoiceItemDTOSet;
    }

    public void setInvoiceItemDTOSet(Set<InvoiceItemDTOSet> invoiceItemDTOSet) {
        this.invoiceItemDTOSet = invoiceItemDTOSet;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }


}
