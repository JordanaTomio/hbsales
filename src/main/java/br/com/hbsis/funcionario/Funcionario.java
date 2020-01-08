package br.com.hbsis.funcionario;

import javax.persistence.*;

@Entity
@Table(name = "seg_funcionarios")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", nullable = false, length = 50)
    private String nomeFuncionario;
    @Column(name = "email", nullable = false, length = 50)
    private String emailFuncionario;
    @Column(name = "uuid", nullable = false, length = 36)
    private String uuidFuncionario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getEmailFuncionario() {
        return emailFuncionario;
    }

    void setEmailFuncionario(String emailFuncionario) {
        this.emailFuncionario = emailFuncionario;
    }

    public String getUuidFuncionario() {
        return uuidFuncionario;
    }

    void setUuidFuncionario(String uuidFuncionario) {
        this.uuidFuncionario = uuidFuncionario;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + id +
                ", nomeFuncionario='" + nomeFuncionario + '\'' +
                ", emailFuncionario='" + emailFuncionario + '\'' +
                ", uuidFuncionario='" + uuidFuncionario + '\'' +
                '}';
    }
}
