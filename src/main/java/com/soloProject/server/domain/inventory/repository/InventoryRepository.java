package com.soloProject.server.domain.inventory.repository;

import com.soloProject.server.domain.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByName (String name);
}
