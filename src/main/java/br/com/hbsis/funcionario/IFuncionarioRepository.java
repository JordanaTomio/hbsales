package br.com.hbsis.funcionario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface IFuncionarioRepository extends JpaRepository <Funcionario, Long> {

}
