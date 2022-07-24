package com.todo1.hulkstore.repository;

import com.todo1.hulkstore.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("saleRepository")
public interface SaleRepository extends JpaRepository<SaleEntity, Long> {
}
