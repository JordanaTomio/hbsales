package br.com.hbsis.produtos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface IProdutosRepository extends JpaRepository<Produtos, Long> {

}
