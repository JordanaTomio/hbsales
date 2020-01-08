package br.com.hbsis.funcionario;

import org.springframework.data.jpa.repository.JpaRepository;

interface IFuncionarioRepository extends JpaRepository <Funcionario, Long> {

}
