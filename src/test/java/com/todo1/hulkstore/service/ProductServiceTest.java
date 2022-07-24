package com.todo1.hulkstore.service;

import com.todo1.hulkstore.entity.CategoryEntity;
import com.todo1.hulkstore.entity.ProductEntity;
import com.todo1.hulkstore.repository.CategoryRepository;
import com.todo1.hulkstore.repository.ProductRepository;
import com.todo1.hulkstore.service.impl.CategoryService;
import com.todo1.hulkstore.service.impl.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
        categoryRepository.deleteAllInBatch();
    }

    @Test
    void getAllProducts() throws Exception {
        categoryRepository.deleteAllInBatch();
        CategoryService categoryService = new CategoryService(categoryRepository);
        CategoryEntity category = new CategoryEntity("Camisas", true);
        categoryService.save(category);

        ProductEntity productEntity = new ProductEntity("Camisa rosada", 0L, category.getId(), 10.00);
        productRepository.save(productEntity);

        ProductService productService = new ProductService(productRepository);
        ProductEntity firstProduct = productService.getAllProducts().get(0);

        assertEquals(productEntity.getId(), firstProduct.getId());
        assertEquals(productEntity.getName(), firstProduct.getName());
        assertEquals(productEntity.getStock(), firstProduct.getStock());
        assertEquals(productEntity.getUnitValue(), firstProduct.getUnitValue());
        assertEquals(productEntity.getCategoryCode(), firstProduct.getCategoryCode());
    }

    @Test
    void save() throws Exception {
        categoryRepository.deleteAllInBatch();
        CategoryService categoryService = new CategoryService(categoryRepository);
        CategoryEntity category = new CategoryEntity("Camisas", true);
        categoryService.save(category);

        ProductService productService = new ProductService(productRepository);
        ProductEntity product = new ProductEntity("Camisa rosada", 0L, category.getId(), 10.00);
        productService.save(product);

        assertEquals(1.0, productRepository.count());
    }

    @Test
    void update() throws Exception {
        categoryRepository.deleteAllInBatch();
        CategoryService categoryService = new CategoryService(categoryRepository);
        CategoryEntity category = new CategoryEntity("Camisas", true);
        categoryService.save(category);

        ProductService productService = new ProductService(productRepository);
        ProductEntity product = new ProductEntity("Camisa rosada", 0L, category.getId(), 10.00);
        productService.save(product);

        product.setName("Camisa negra");
        product.setStock(10L);
        product.setUnitValue(12.00);
        productService.update(product);

        ProductEntity firstProductSaved = productService.getAllProducts().get(0);

        assertEquals(1.0, productRepository.count());
        assertEquals(product.getId(), firstProductSaved.getId());
        assertEquals(product.getName(), firstProductSaved.getName());
        assertEquals(product.getStock(), firstProductSaved.getStock());
        assertEquals(product.getUnitValue(), firstProductSaved.getUnitValue());
    }
}
