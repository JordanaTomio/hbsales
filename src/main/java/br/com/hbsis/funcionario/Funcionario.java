package br.com.hbsis.funcionario;

import javax.persistence.*;

@Entity
@Table(name = "seg_funcionarios")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;
    @Column(name = "email", nullable = false, length = 50)
    private String email;
    @Column(name = "uuid", nullable = false, length = 36)
    private String uuid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + id +
                ", nomeFuncionario='" + nome + '\'' +
                ", emailFuncionario='" + email + '\'' +
                ", uuidFuncionario='" + uuid + '\'' +
                '}';
    }
}
