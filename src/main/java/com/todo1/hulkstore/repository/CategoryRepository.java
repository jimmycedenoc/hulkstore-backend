package com.todo1.hulkstore.repository;

import com.todo1.hulkstore.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("categoryRepository")
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
