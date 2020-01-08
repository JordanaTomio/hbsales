package br.com.hbsis.fornecedor;

import javax.persistence.*;

@Entity
@Table (name = "seg_fornecedor")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "razao", unique = true, nullable = false, length = 100)
    private String razao;
    @Column(name = "cnpj", unique = true, nullable = false, length = 14)
    private String cnpj;
    @Column(name = "nome_fantasia", nullable = false, length = 100)
    private String nomeFantasia;
    @Column(name = "endereco", nullable = false, length = 100)
    private String endereco;
    @Column(name = "telefone", nullable = false, length = 12)
    private String telefone;
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazao() {
        return razao;
    }

    void setRazao(String razao) {
        this.razao = razao;
    }

    public String getCnpj() {
        return cnpj;
    }

    void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    String getNomeFantasia() {
        return nomeFantasia;
    }

    void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    String getEndereco() {
        return endereco;
    }

    void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    String getTelefone() {
        return telefone;
    }

    void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
                "id=" + id +
                ", razao='" + razao + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", nomefantasia='" + nomeFantasia + '\'' +
                ", endereco='" + endereco + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
