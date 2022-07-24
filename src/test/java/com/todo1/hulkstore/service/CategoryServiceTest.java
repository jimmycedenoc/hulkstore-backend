package com.todo1.hulkstore.service;

import com.todo1.hulkstore.entity.CategoryEntity;
import com.todo1.hulkstore.repository.CategoryRepository;
import com.todo1.hulkstore.service.impl.CategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach
    void tearDown() {
        categoryRepository.deleteAllInBatch();
    }

    @Test
    void getAllCategories() throws Exception {
        CategoryEntity categoryTest = new CategoryEntity("Camisetas",true);
        categoryRepository.save(categoryTest);

        CategoryService categoryService = new CategoryService(categoryRepository);
        CategoryEntity firstCategory = categoryService.getAllCategories().get(0);

        assertEquals(categoryTest.getId(), firstCategory.getId());
        assertEquals(categoryTest.getName(), firstCategory.getName());
        assertEquals(categoryTest.isActive(), firstCategory.isActive());
    }

    @Test
    void save() throws Exception {
        categoryRepository.deleteAllInBatch();
        CategoryService categoryService = new CategoryService(categoryRepository);
        CategoryEntity category = new CategoryEntity("Camisetas", true);
        categoryService.save(category);

        assertEquals(1.0, categoryRepository.count());
    }

    @Test
    void update() throws Exception {
        categoryRepository.deleteAllInBatch();
        CategoryService categoryService = new CategoryService(categoryRepository);
        CategoryEntity category = new CategoryEntity("Camisas", true);
        categoryService.save(category);

        category.setName("Otro");
        category.setActive(false);
        categoryService.update(category);

        CategoryEntity firstCategorySaved = categoryService.getAllCategories().get(0);


        assertEquals(1.0, categoryRepository.count());
        assertEquals(category.getName(), firstCategorySaved.getName());
        assertEquals(category.isActive(), firstCategorySaved.isActive());
    }
}
