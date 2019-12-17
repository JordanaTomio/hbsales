package br.com.hbsis.funcionario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IFuncionarioRepository extends JpaRepository <Funcionario, Long> {
}
