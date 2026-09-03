package br.edu.ifsul.cstsi.tds_aulas.item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}