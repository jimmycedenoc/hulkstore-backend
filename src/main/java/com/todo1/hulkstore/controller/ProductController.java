package com.todo1.hulkstore.controller;

import com.todo1.hulkstore.entity.ProductEntity;
import com.todo1.hulkstore.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("productController")
@RequestMapping("/product")
public class ProductController {
    private HttpStatus status = HttpStatus.OK;

    private static Logger LOG = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    @Lazy
    IProductService productService;

    @GetMapping
    ResponseEntity<List<ProductEntity>> getAllProducts() {
        List<ProductEntity> result = null;

        try {
            result = productService.getAllProducts();
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            LOG.error("ERROR in getAllProducts",  e);
        }
        return new ResponseEntity<>(result, status);
    }

    @PostMapping
    ResponseEntity<ProductEntity> save(@RequestBody ProductEntity product) {
        ProductEntity result = null;
        try {
            result = productService.save(product);
            status = HttpStatus.CREATED;
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            LOG.error("ERROR in save", e);
        }
        return new ResponseEntity<>(result, status);
    }

    @PutMapping
    ResponseEntity<ProductEntity> update(@RequestBody ProductEntity product) {
        ProductEntity result = null;
        try {
            result = productService.update(product);
            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            LOG.error("ERROR in update", e);
        }
        return new ResponseEntity<>(result, status);
    }
}
