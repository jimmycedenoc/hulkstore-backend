package com.todo1.hulkstore.service.impl;

import com.todo1.hulkstore.entity.InventoryEntity;
import com.todo1.hulkstore.entity.ProductEntity;
import com.todo1.hulkstore.entity.SaleEntity;
import com.todo1.hulkstore.repository.InventoryRepository;
import com.todo1.hulkstore.repository.ProductRepository;
import com.todo1.hulkstore.repository.SaleRepository;
import com.todo1.hulkstore.service.ISaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService implements ISaleService {
    @Autowired
    @Lazy
    private SaleRepository saleRepository;

    @Autowired
    @Lazy
    private InventoryRepository inventoryRepository;

    @Autowired
    @Lazy
    private ProductRepository productRepository;

    public SaleService() {

    }
    public SaleService(SaleRepository saleRepository, InventoryRepository inventoryRepository, ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }
    @Override
    public List<SaleEntity> getAllSales() throws Exception {
        return saleRepository.findAll();
    }

    @Override
    public SaleEntity save(SaleEntity sale) throws Exception {
        ProductService productService = new ProductService(productRepository);
        SaleEntity saleSaved = saleRepository.save(sale);

        InventoryService inventoryService = new InventoryService(inventoryRepository);
        InventoryEntity inventoryEntity = new InventoryEntity(sale.getDate(), saleSaved.getQuantity(), saleSaved.getId(),null, saleSaved.getProductCode(), saleSaved.getUnitValue(), "Venta");
        inventoryService.save(inventoryEntity);

        productService.refreshStock(sale.getQuantity(), ProductService.InventoryType.SALE, sale.getProductCode());
        return saleSaved;
    }

    @Override
    public SaleEntity update(SaleEntity sale) throws Exception {
        SaleEntity existingSale = saleRepository.findById(sale.getId()).orElse(null);
        existingSale.setDate(sale.getDate());
        existingSale.setQuantity(sale.getQuantity());
        existingSale.setUnitValue(sale.getUnitValue());
        existingSale.setUserCode(sale.getUserCode());
        existingSale.setProductCode(sale.getProductCode());

        SaleEntity saleSaved = saleRepository.save(existingSale);

        InventoryService inventoryService = new InventoryService(inventoryRepository);
        InventoryEntity inventoryEntity = inventoryService.getBySaleCode(sale.getId());
        inventoryEntity.setDate(sale.getDate());
        inventoryEntity.setQuantity(sale.getQuantity());
        inventoryEntity.setUnitValue(sale.getUnitValue());
        inventoryService.update(inventoryEntity);
        return saleSaved;
    }
}
