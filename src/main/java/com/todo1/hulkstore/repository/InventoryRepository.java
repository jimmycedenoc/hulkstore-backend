package com.todo1.hulkstore.repository;

import com.todo1.hulkstore.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("inventoryRepository")
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
    InventoryEntity getBySaleCode(Long saleCode);
    InventoryEntity getByBuyCode(Long buyCode);
}
