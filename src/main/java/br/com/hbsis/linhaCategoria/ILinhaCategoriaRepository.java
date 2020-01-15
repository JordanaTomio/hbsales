package br.com.hbsis.linhaCategoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ILinhaCategoriaRepository extends JpaRepository <LinhaCategoria, Long> {
    LinhaCategoria findByCodigoLinha(String codigoLinha);
    boolean existsByCodigoLinha(String codigoLinha);
}
