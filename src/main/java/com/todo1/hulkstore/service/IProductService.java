package com.todo1.hulkstore.service;

import com.todo1.hulkstore.entity.ProductEntity;

import java.util.List;

public interface IProductService {
    List<ProductEntity> getAllProducts() throws Exception;
    ProductEntity save(ProductEntity product) throws Exception;
    ProductEntity update(ProductEntity product) throws Exception;
}
