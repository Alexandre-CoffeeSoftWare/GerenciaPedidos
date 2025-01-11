package org.example.repository;

import org.example.model.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItensRepository extends JpaRepository<ItemEntity, Long> {
}
