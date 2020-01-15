package br.com.hbsis.categoria;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ICategoriaRepository extends JpaRepository<Categoria, Long> {

    Categoria findByNomeAndCodigo(String nome, String codigo);

    boolean existsByCodigo(String codigo);

    List<Categoria> findAllByFornecedor_IdIs(Long idFornecedor);

}
