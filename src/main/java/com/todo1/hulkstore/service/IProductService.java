package com.todo1.hulkstore.service;

import com.todo1.hulkstore.entity.ProductEntity;
import com.todo1.hulkstore.service.impl.ProductService;

import java.util.List;

public interface IProductService {
    List<ProductEntity> getAllProducts() throws Exception;
    void refreshStock(Long quantity, ProductService.InventoryType inventoryType, Long productCode) throws Exception;
    ProductEntity save(ProductEntity product) throws Exception;
    ProductEntity update(ProductEntity product) throws Exception;
}
