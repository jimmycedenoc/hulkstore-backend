package com.todo1.hulkstore.service;

import com.todo1.hulkstore.entity.CategoryEntity;
import com.todo1.hulkstore.entity.ProductEntity;
import com.todo1.hulkstore.entity.SaleEntity;
import com.todo1.hulkstore.entity.UserEntity;
import com.todo1.hulkstore.repository.*;
import com.todo1.hulkstore.service.impl.CategoryService;
import com.todo1.hulkstore.service.impl.ProductService;
import com.todo1.hulkstore.service.impl.SaleService;
import com.todo1.hulkstore.service.impl.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SaleServiceTest {
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InventoryRepository inventoryRepository;

    @AfterEach
    void tearDown() {
        inventoryRepository.deleteAllInBatch();
        saleRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        categoryRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();

    }


    @Test
    void getAllSales() throws Exception {
        inventoryRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        UserService userService = new UserService(userRepository);
        UserEntity userEntity = new UserEntity("test", "12345");
        userService.save(userEntity);

        categoryRepository.deleteAllInBatch();
        CategoryService categoryService = new CategoryService(categoryRepository);
        CategoryEntity category = new CategoryEntity("Camisas", true);
        categoryService.save(category);

        productRepository.deleteAllInBatch();
        ProductService productService = new ProductService(productRepository);
        ProductEntity productEntity = new ProductEntity("Camisa rosada", 100L, category.getId(), 10.00);
        productService.save(productEntity);

        SaleEntity saleEntity = new SaleEntity(new Date(), 2L, userEntity.getId(), productEntity.getId(), 10.25);
        saleRepository.save(saleEntity);

        SaleService saleService = new SaleService(saleRepository, inventoryRepository, productRepository);
        SaleEntity firstSale = saleService.getAllSales().get(0);

        assertEquals(saleEntity.getId(), firstSale.getId());
        assertEquals(saleEntity.getQuantity(), firstSale.getQuantity());
        assertEquals(saleEntity.getUserCode(), firstSale.getUserCode());
        assertEquals(saleEntity.getUnitValue(), firstSale.getUnitValue());
        assertEquals(saleEntity.getProductCode(), firstSale.getProductCode());
    }

    @Test
    void save() throws Exception {
        inventoryRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        UserService userService = new UserService(userRepository);
        UserEntity userEntity = new UserEntity("test", "12345");
        userService.save(userEntity);

        categoryRepository.deleteAllInBatch();
        CategoryService categoryService = new CategoryService(categoryRepository);
        CategoryEntity category = new CategoryEntity("Camisas", true);
        categoryService.save(category);

        ProductService productService = new ProductService(productRepository);
        ProductEntity productEntity = new ProductEntity("Camisa rosada", 100L, category.getId(), 10.00);
        productService.save(productEntity);

        SaleService saleService = new SaleService(saleRepository, inventoryRepository, productRepository);
        SaleEntity sale = new SaleEntity(new Date(), 2L, userEntity.getId(), productEntity.getId(), 10.25);
        saleService.save(sale);

        assertEquals(1.0, saleRepository.count());
    }

    @Test
    void update() throws Exception {
        inventoryRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        UserService userService = new UserService(userRepository);
        UserEntity userEntity = new UserEntity("test", "12345");
        userService.save(userEntity);

        categoryRepository.deleteAllInBatch();
        CategoryService categoryService = new CategoryService(categoryRepository);
        CategoryEntity category = new CategoryEntity("Camisas", true);
        categoryService.save(category);

        ProductService productService = new ProductService(productRepository);
        ProductEntity productEntity = new ProductEntity("Camisa rosada", 100L, category.getId(), 10.00);
        productService.save(productEntity);

        SaleService saleService = new SaleService(saleRepository, inventoryRepository, productRepository);
        SaleEntity sale = new SaleEntity(new Date(), 2L, userEntity.getId(), productEntity.getId(), 10.25);
        saleService.save(sale);

        sale.setQuantity(10L);
        sale.setUnitValue(12.00);
        saleService.update(sale);

        SaleEntity firstSaleSaved = saleService.getAllSales().get(0);

        assertEquals(sale.getId(), firstSaleSaved.getId());
        assertEquals(sale.getQuantity(), firstSaleSaved.getQuantity());
        assertEquals(sale.getUserCode(), firstSaleSaved.getUserCode());
        assertEquals(sale.getUnitValue(), firstSaleSaved.getUnitValue());
        assertEquals(sale.getProductCode(), firstSaleSaved.getProductCode());
    }
}
