package br.com.hbsis.periodoVendas;

import org.springframework.data.jpa.repository.JpaRepository;

interface IPeriodoRepository extends JpaRepository<Periodo, Long> {
}
