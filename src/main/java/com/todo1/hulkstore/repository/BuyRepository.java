package com.todo1.hulkstore.repository;

import com.todo1.hulkstore.entity.BuyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("buyRepository")
public interface BuyRepository extends JpaRepository<BuyEntity, Long> {
}
