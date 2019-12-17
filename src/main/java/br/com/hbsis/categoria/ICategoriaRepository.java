package br.com.hbsis.categoria;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {

    public Categoria findByNomeAndCodigo(String nome, String codigo);

    public boolean existsByCodigo(String codigo);

}
