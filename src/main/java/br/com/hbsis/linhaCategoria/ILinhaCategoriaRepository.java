package br.com.hbsis.linhaCategoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ILinhaCategoriaRepository extends JpaRepository <LinhaCategoria, Long> {
    public LinhaCategoria findByCodigoLinha(String codigoLinha);
    public boolean existsByCodigoLinha(String codigoLinha);
}
