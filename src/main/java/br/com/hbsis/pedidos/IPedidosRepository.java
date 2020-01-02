package br.com.hbsis.pedidos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface IPedidosRepository extends JpaRepository<Pedidos, Long> {

    List<Pedidos> findAllById(Long id);
}
