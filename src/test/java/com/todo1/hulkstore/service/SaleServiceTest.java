package com.todo1.hulkstore.service;

import com.todo1.hulkstore.entity.ProductEntity;
import com.todo1.hulkstore.entity.SaleEntity;
import com.todo1.hulkstore.repository.ProductRepository;
import com.todo1.hulkstore.repository.SaleRepository;
import com.todo1.hulkstore.service.impl.ProductService;
import com.todo1.hulkstore.service.impl.SaleService;
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

    @AfterEach
    void tearDown() {
        saleRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
    }


    @Test
    void getAllProducts() throws Exception {
        ProductService productService = new ProductService(productRepository);
        ProductEntity productEntity = new ProductEntity("Camisa rosada", 0L, 1L, 10.00);
        productService.save(productEntity);

        SaleEntity saleEntity = new SaleEntity(new Date(), 2L, 1L, productEntity.getId(), 10.25);
        saleRepository.save(saleEntity);

        SaleService saleService = new SaleService(saleRepository);
        SaleEntity firstSale = saleService.getAllSales().get(0);

        assertEquals(saleEntity.getId(), firstSale.getId());
        assertEquals(saleEntity.getQuantity(), firstSale.getQuantity());
        assertEquals(saleEntity.getUserCode(), firstSale.getUserCode());
        assertEquals(saleEntity.getUnitValue(), firstSale.getUnitValue());
        assertEquals(saleEntity.getProductCode(), firstSale.getProductCode());
    }

    @Test
    void save() throws Exception {
        ProductService productService = new ProductService(productRepository);
        ProductEntity productEntity = new ProductEntity("Camisa rosada", 0L, 1L, 10.00);
        productService.save(productEntity);

        SaleService saleService = new SaleService(saleRepository);
        SaleEntity sale = new SaleEntity(new Date(), 2L, 1L, productEntity.getId(), 10.25);
        saleService.save(sale);

        assertEquals(1.0, saleRepository.count());
    }

    @Test
    void update() throws Exception {
        ProductService productService = new ProductService(productRepository);
        ProductEntity productEntity = new ProductEntity("Camisa rosada", 0L, 1L, 10.00);
        productService.save(productEntity);

        SaleService saleService = new SaleService(saleRepository);
        SaleEntity sale = new SaleEntity(new Date(), 2L, 1L, productEntity.getId(), 10.25);
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
