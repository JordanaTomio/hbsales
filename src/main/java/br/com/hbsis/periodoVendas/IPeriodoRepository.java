package br.com.hbsis.periodoVendas;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface IPeriodoRepository extends JpaRepository<Periodo, Long> {
    

    boolean existsByFornecedor(Long id);

    Optional<Periodo> findByFornecedor(Long id);
}
