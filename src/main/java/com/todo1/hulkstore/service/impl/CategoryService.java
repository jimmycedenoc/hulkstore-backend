package com.todo1.hulkstore.service.impl;

import com.todo1.hulkstore.entity.CategoryEntity;
import com.todo1.hulkstore.repository.CategoryRepository;
import com.todo1.hulkstore.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("categoryService")
public class CategoryService implements ICategoryService {

    @Autowired
    @Lazy
    private CategoryRepository categoryRepository;

    public CategoryService() {

    }
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    public List<CategoryEntity> getAllCategories() throws Exception {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryEntity save(CategoryEntity category) throws Exception {
        return categoryRepository.save(category);
    }

    @Override
    public List<CategoryEntity> saveAll(List<CategoryEntity> categoryEntityList) throws Exception {
        return categoryRepository.saveAll(categoryEntityList);
    }

    @Override
    public CategoryEntity update(CategoryEntity category) throws Exception {
        CategoryEntity existingCategory = categoryRepository.findById(category.getId()).orElse(null);
        existingCategory.setName(category.getName());
        existingCategory.setActive(category.isActive());

        return categoryRepository.save(existingCategory);
    }
}
