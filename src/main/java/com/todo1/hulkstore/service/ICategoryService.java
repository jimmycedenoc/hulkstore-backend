package com.todo1.hulkstore.service;

import com.todo1.hulkstore.entity.CategoryEntity;

import java.util.List;

public interface ICategoryService {
    List<CategoryEntity> getAllCategories() throws Exception;
    CategoryEntity save(CategoryEntity category) throws Exception;
    List<CategoryEntity> saveAll(List<CategoryEntity> categoryEntityList) throws Exception;
    CategoryEntity update(CategoryEntity category) throws Exception;
}
