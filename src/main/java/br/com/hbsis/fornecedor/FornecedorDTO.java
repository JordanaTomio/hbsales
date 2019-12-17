package br.com.hbsis.fornecedor;

public class FornecedorDTO {
    private Long id;
    private String razao;
    private String cnpj;
    private String nomefantasia;
    private String endereco;
    private String telefone;
    private String email;

    public FornecedorDTO(){
    }

    public FornecedorDTO(Long id, String razao, String cnpj, String nomefantasia, String endereco, String telefone, String email) {
        this.id = id;
        this.razao = razao;
        this.cnpj = cnpj;
        this.nomefantasia = nomefantasia;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }

    public static FornecedorDTO of(Fornecedor fornecedor) {
        return new FornecedorDTO(
                fornecedor.getId(),
                fornecedor.getRazao(),
                fornecedor.getCnpj(),
                fornecedor.getNomefantasia(),
                fornecedor.getEndereco(),
                fornecedor.getTelefone(),
                fornecedor.getEmail()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazao() {
        return razao;
    }

    public void setRazao(String razao) {
        this.razao = razao;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomefantasia() {
        return nomefantasia;
    }

    public void setNomefantasia(String nomefantasia) {
        this.nomefantasia = nomefantasia;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "FornecedorDTO{" +
                "id=" + id +
                ", razao='" + razao + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", nomefantasia='" + nomefantasia + '\'' +
                ", endereco='" + endereco + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
