package com.todo1.hulkstore.service.impl;

import com.todo1.hulkstore.entity.ProductEntity;
import com.todo1.hulkstore.repository.ProductRepository;
import com.todo1.hulkstore.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {
    @Autowired
    @Lazy
    private ProductRepository productRepository;

    public ProductService() {

    }
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public List<ProductEntity> getAllProducts() throws Exception {
        return productRepository.findAll();
    }

    @Override
    public ProductEntity save(ProductEntity product) throws Exception {
        return productRepository.save(product);
    }

    @Override
    public ProductEntity update(ProductEntity product) throws Exception {
        ProductEntity existingProduct = productRepository.findById(product.getId()).orElse(null);
        existingProduct.setName(product.getName());
        existingProduct.setStock(product.getStock());
        existingProduct.setName(product.getName());
        existingProduct.setUnitValue(product.getUnitValue());

        return productRepository.save(existingProduct);
    }
}
