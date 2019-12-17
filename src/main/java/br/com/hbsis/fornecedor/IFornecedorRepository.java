package br.com.hbsis.fornecedor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFornecedorRepository extends JpaRepository <Fornecedor, Long> {

}
