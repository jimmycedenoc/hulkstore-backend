package com.todo1.hulkstore.service;

import com.todo1.hulkstore.entity.InventoryEntity;

import java.util.List;

public interface IInventoryService {
    List<InventoryEntity> getAllInventories() throws Exception;
    InventoryEntity save(InventoryEntity product) throws Exception;
    InventoryEntity update(InventoryEntity product) throws Exception;
    InventoryEntity getBySaleCode(Long saleCode) throws Exception;
    InventoryEntity getByBuyCode(Long buyCode) throws Exception;
}
