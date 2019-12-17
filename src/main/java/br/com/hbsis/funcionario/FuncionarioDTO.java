package br.com.hbsis.funcionario;

public class FuncionarioDTO {
    Long id;
    String nome;
    String email;
    String uuid;

    public FuncionarioDTO(Long id, String nome, String email, String uuid) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.uuid = uuid;
    }

    public static FuncionarioDTO of(Funcionario funcionario) {
        return new FuncionarioDTO(
                funcionario.getId(),
                funcionario.getNomeFuncionario(),
                funcionario.getEmailFuncionario(),
                funcionario.getUuidFuncionario()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "FuncionarioDTO{" +
                "id=" + id +
                ", nomeFuncionario='" + nome + '\'' +
                ", emailFuncionario='" + email + '\'' +
                ", uuidFuncionario='" + uuid + '\'' +
                '}';
    }
}
