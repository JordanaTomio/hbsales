package br.com.hbsis.produtos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface IProdutosRepository extends JpaRepository<Produtos, Long> {

}
