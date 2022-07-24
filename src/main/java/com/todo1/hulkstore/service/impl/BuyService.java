package com.todo1.hulkstore.service.impl;

import com.todo1.hulkstore.entity.BuyEntity;
import com.todo1.hulkstore.entity.InventoryEntity;
import com.todo1.hulkstore.repository.BuyRepository;
import com.todo1.hulkstore.repository.InventoryRepository;
import com.todo1.hulkstore.repository.ProductRepository;
import com.todo1.hulkstore.service.IBuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuyService implements IBuyService {
    @Autowired
    @Lazy
    private BuyRepository buyRepository;

    @Autowired
    @Lazy
    private InventoryRepository inventoryRepository;

    @Autowired
    @Lazy
    private ProductRepository productRepository;

    public BuyService() {

    }
    public BuyService(BuyRepository buyRepository, InventoryRepository inventoryRepository, ProductRepository productRepository) {
        this.buyRepository = buyRepository;
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }
    @Override
    public List<BuyEntity> getAllBuys() throws Exception {
        return buyRepository.findAll();
    }

    @Override
    public BuyEntity save(BuyEntity buy) throws Exception {
        ProductService productService = new ProductService(productRepository);
        BuyEntity buySaved = buyRepository.save(buy);

        InventoryService inventoryService = new InventoryService(inventoryRepository);
        InventoryEntity inventoryEntity = new InventoryEntity(buy.getDate(), buySaved.getQuantity(), null, buySaved.getId(), buySaved.getProductCode(), buySaved.getUnitValue(), "Compra");
        inventoryService.save(inventoryEntity);

        productService.refreshStock(buy.getQuantity(), ProductService.InventoryType.BUY, buy.getProductCode());
        return buySaved;
    }

    @Override
    public BuyEntity update(BuyEntity buy) throws Exception {
        BuyEntity existingBuy = buyRepository.findById(buy.getId()).orElse(null);
        existingBuy.setDate(buy.getDate());
        existingBuy.setQuantity(buy.getQuantity());
        existingBuy.setUnitValue(buy.getUnitValue());
        existingBuy.setUserCode(buy.getUserCode());
        existingBuy.setProductCode(buy.getProductCode());

        BuyEntity saleSaved = buyRepository.save(existingBuy);

        InventoryService inventoryService = new InventoryService(inventoryRepository);
        InventoryEntity inventoryEntity = inventoryService.getByBuyCode(buy.getId());
        inventoryEntity.setDate(buy.getDate());
        inventoryEntity.setQuantity(buy.getQuantity());
        inventoryEntity.setUnitValue(buy.getUnitValue());
        inventoryService.update(inventoryEntity);
        return saleSaved;
    }
}
