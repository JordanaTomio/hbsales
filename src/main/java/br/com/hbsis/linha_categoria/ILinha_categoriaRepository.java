package br.com.hbsis.linha_categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILinha_categoriaRepository extends JpaRepository <Linha_categoria, Long> {
    public Linha_categoria findByCodigoLinha(String codigoLinha);
    public boolean existsByCodigoLinha(String codigoLinha);
}
