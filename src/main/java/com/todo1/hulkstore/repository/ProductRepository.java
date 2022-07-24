package com.todo1.hulkstore.repository;

import com.todo1.hulkstore.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("productRepository")
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
