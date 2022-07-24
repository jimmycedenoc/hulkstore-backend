package com.todo1.hulkstore.service;

import com.todo1.hulkstore.entity.InventoryEntity;
import com.todo1.hulkstore.entity.ProductEntity;
import com.todo1.hulkstore.entity.SaleEntity;
import com.todo1.hulkstore.repository.InventoryRepository;
import com.todo1.hulkstore.repository.ProductRepository;
import com.todo1.hulkstore.repository.SaleRepository;
import com.todo1.hulkstore.service.impl.InventoryService;
import com.todo1.hulkstore.service.impl.ProductService;
import com.todo1.hulkstore.service.impl.SaleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class InventoryServiceTest {
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @AfterEach
    void tearDown() {
        inventoryRepository.deleteAllInBatch();
        saleRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
    }


    @Test
    void getAllInventories() throws Exception {
        ProductService productService = new ProductService(productRepository);
        ProductEntity productEntity = new ProductEntity("Camisa rosada", 0L, 1L, 10.00);
        productService.save(productEntity);

        SaleService saleService = new SaleService(saleRepository);
        SaleEntity saleEntity = new SaleEntity(new Date(), 2L, 1L, productEntity.getId(), 10.25);
        saleService.save(saleEntity);

        Logger LOG = LoggerFactory.getLogger("JIMM");
        LOG.warn(saleEntity.getId().toString());

        InventoryEntity inventoryEntity = new InventoryEntity(new Date(), 2L, saleEntity.getId(), productEntity.getId(), 10.25, "Compra");
        inventoryRepository.save(inventoryEntity);

        InventoryService inventoryService = new InventoryService(inventoryRepository);
        InventoryEntity firstInventory = inventoryService.getAllInventories().get(0);

        assertEquals(inventoryEntity.getId(), firstInventory.getId());
//        assertEquals(inventoryEntity.getQuantity(), firstInventory.getQuantity());
//        assertEquals(inventoryEntity.getSaleCode(), firstInventory.getSaleCode());
//        assertEquals(inventoryEntity.getUnitValue(), firstInventory.getUnitValue());
//        assertEquals(inventoryEntity.getProductCode(), firstInventory.getProductCode());
//        assertEquals(inventoryEntity.getDetail(), firstInventory.getDetail());
    }

    @Test
    void save() throws Exception {
        ProductService productService = new ProductService(productRepository);
        ProductEntity productEntity = new ProductEntity("Camisa rosada", 0L, 1L, 10.00);
        productService.save(productEntity);

        SaleService saleService = new SaleService(saleRepository);
        SaleEntity saleEntity = new SaleEntity(new Date(), 2L, 1L, productEntity.getId(), 10.25);
        saleService.save(saleEntity);

        InventoryService inventoryService = new InventoryService(inventoryRepository);
        InventoryEntity inventory = new InventoryEntity(new Date(), 2L, saleEntity.getId(), productEntity.getId(), 10.25, "Compra");
        inventoryService.save(inventory);

        assertEquals(1.0, inventoryRepository.count());
    }

    @Test
    void update() throws Exception {
        ProductService productService = new ProductService(productRepository);
        ProductEntity productEntity = new ProductEntity("Camisa rosada", 0L, 1L, 10.00);
        productService.save(productEntity);

        SaleService saleService = new SaleService(saleRepository);
        SaleEntity saleEntity = new SaleEntity(new Date(), 2L, 1L, productEntity.getId(), 10.25);
        saleService.save(saleEntity);

        InventoryService inventoryService = new InventoryService(inventoryRepository);
        InventoryEntity inventory = new InventoryEntity(new Date(), 2L, saleEntity.getId(), productEntity.getId(), 10.25, "Compra");
        inventoryService.save(inventory);

        inventory.setQuantity(10L);
        inventory.setUnitValue(12.00);
        inventoryService.update(inventory);

        InventoryEntity firstInventorySaved = inventoryService.getAllInventories().get(0);

        assertEquals(inventory.getId(), firstInventorySaved.getId());
        assertEquals(inventory.getQuantity(), firstInventorySaved.getQuantity());
        assertEquals(inventory.getSaleCode(), firstInventorySaved.getSaleCode());
        assertEquals(inventory.getUnitValue(), firstInventorySaved.getUnitValue());
        assertEquals(inventory.getProductCode(), firstInventorySaved.getProductCode());
        assertEquals(inventory.getDetail(), firstInventorySaved.getDetail());
    }
}
