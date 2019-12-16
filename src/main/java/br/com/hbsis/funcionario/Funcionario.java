package br.com.hbsis.funcionario;

import javax.persistence.*;

@Entity
@Table(name = "seg_funcionarios")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "nome_funcionario", nullable = false, length = 50)
    String nomeFuncionario;
    @Column(name = "email_funcionario", nullable = false, length = 50)
    String emailFuncionario;
    @Column(name = "uuid_funcionario", nullable = false, length = 36)
    String uuidFuncionario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getEmailFuncionario() {
        return emailFuncionario;
    }

    public void setEmailFuncionario(String emailFuncionario) {
        this.emailFuncionario = emailFuncionario;
    }

    public String getUuidFuncionario() {
        return uuidFuncionario;
    }

    public void setUuidFuncionario(String uuidFuncionario) {
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
