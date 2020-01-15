package br.com.hbsis.pedidos;

import org.springframework.data.jpa.repository.JpaRepository;

interface IPedidosRepository extends JpaRepository<Pedidos, Long> {
}
