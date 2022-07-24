package com.todo1.hulkstore.service;

import com.todo1.hulkstore.entity.*;
import com.todo1.hulkstore.repository.*;
import com.todo1.hulkstore.service.impl.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class InventoryServiceTest {
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @AfterEach
    void tearDown() {
        inventoryRepository.deleteAllInBatch();
        saleRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        categoryRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }


    @Test
    void getAllInventories() throws Exception {
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
        SaleEntity saleEntity = new SaleEntity(new Date(), 2L, userEntity.getId(), productEntity.getId(), 10.25);
        saleService.save(saleEntity);

        InventoryEntity inventoryEntity = new InventoryEntity(new Date(), 2L, saleEntity.getId(), null, productEntity.getId(), 10.25, "Compra");
        inventoryRepository.save(inventoryEntity);

        InventoryService inventoryService = new InventoryService(inventoryRepository);
        List<InventoryEntity> inventoryEntityList = inventoryService.getAllInventories();
        InventoryEntity firstInventory = inventoryEntityList.get(inventoryEntityList.size() - 1);

        assertEquals(inventoryEntity.getId(), firstInventory.getId());
        assertEquals(inventoryEntity.getQuantity(), firstInventory.getQuantity());
        assertEquals(inventoryEntity.getSaleCode(), firstInventory.getSaleCode());
        assertEquals(inventoryEntity.getUnitValue(), firstInventory.getUnitValue());
        assertEquals(inventoryEntity.getProductCode(), firstInventory.getProductCode());
        assertEquals(inventoryEntity.getDetail(), firstInventory.getDetail());
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


        InventoryService inventoryService = new InventoryService(inventoryRepository);
        InventoryEntity inventory = new InventoryEntity(new Date(), 2L, null, null, productEntity.getId(), 10.25, "Compra");
        inventoryService.save(inventory);

        assertEquals(1.0, inventoryRepository.count());
    }

    @Test
    void update() throws Exception {
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
        SaleEntity saleEntity = new SaleEntity(new Date(), 2L, userEntity.getId(), productEntity.getId(), 10.25);
        saleService.save(saleEntity);

        InventoryService inventoryService = new InventoryService(inventoryRepository);
        InventoryEntity inventory = new InventoryEntity(new Date(), 2L, saleEntity.getId(), null, productEntity.getId(), 10.25, "Compra");
        inventoryService.save(inventory);

        inventory.setQuantity(10L);
        inventory.setUnitValue(12.00);
        inventoryService.update(inventory);

        List<InventoryEntity> inventoryEntityList = inventoryService.getAllInventories();
        InventoryEntity firstInventorySaved = inventoryEntityList.get(inventoryEntityList.size() - 1);


        assertEquals(inventory.getId(), firstInventorySaved.getId());
        assertEquals(inventory.getQuantity(), firstInventorySaved.getQuantity());
        assertEquals(inventory.getSaleCode(), firstInventorySaved.getSaleCode());
        assertEquals(inventory.getUnitValue(), firstInventorySaved.getUnitValue());
        assertEquals(inventory.getProductCode(), firstInventorySaved.getProductCode());
        assertEquals(inventory.getDetail(), firstInventorySaved.getDetail());
    }
}
