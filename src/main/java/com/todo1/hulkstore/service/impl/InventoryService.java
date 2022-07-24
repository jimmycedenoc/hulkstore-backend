package com.todo1.hulkstore.service.impl;

import com.todo1.hulkstore.entity.InventoryEntity;
import com.todo1.hulkstore.repository.InventoryRepository;
import com.todo1.hulkstore.service.IInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService implements IInventoryService {
    @Autowired
    @Lazy
    private InventoryRepository inventoryRepository;

    public InventoryService() {

    }
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
    @Override
    public List<InventoryEntity> getAllInventories() throws Exception {
        return inventoryRepository.findAll();
    }

    @Override
    public InventoryEntity save(InventoryEntity sale) throws Exception {
        return inventoryRepository.save(sale);
    }

    @Override
    public InventoryEntity update(InventoryEntity sale) throws Exception {
        InventoryEntity existingInventory = inventoryRepository.findById(sale.getId()).orElse(null);
        existingInventory.setDate(sale.getDate());
        existingInventory.setQuantity(sale.getQuantity());
        existingInventory.setUnitValue(sale.getUnitValue());
        existingInventory.setDetail(sale.getDetail());
        existingInventory.setSaleCode(sale.getSaleCode());
        existingInventory.setProductCode(sale.getProductCode());

        return inventoryRepository.save(existingInventory);
    }
}
