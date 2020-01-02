package br.com.hbsis.fornecedor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface IFornecedorRepository extends JpaRepository <Fornecedor, Long> {

    public Fornecedor findByCnpjAndRazao(String cnpj, String razao);

}
