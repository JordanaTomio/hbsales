package br.com.hbsis.item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemRepository extends JpaRepository<Item, Long> {
}
