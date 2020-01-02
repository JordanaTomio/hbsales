package br.com.hbsis.categoria;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ICategoriaRepository extends JpaRepository<Categoria, Long> {

    public Categoria findByNomeAndCodigo(String nome, String codigo);

    public Categoria findByCodigo(String codigo);

    public boolean existsByCodigo(String codigo);

    public List<Categoria> findAllByFornecedor_IdIs(Long idFornecedor);

}
