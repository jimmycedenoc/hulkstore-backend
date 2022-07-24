package com.todo1.hulkstore.controller;

import com.todo1.hulkstore.entity.SaleEntity;
import com.todo1.hulkstore.service.ISaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("saleController")
@RequestMapping("/sale")
public class SaleController {
    private HttpStatus status = HttpStatus.OK;

    private static Logger LOG = LoggerFactory.getLogger(SaleController.class);

    @Autowired
    @Lazy
    ISaleService saleService;

    @GetMapping
    ResponseEntity<List<SaleEntity>> getAllSales() {
        List<SaleEntity> result = null;

        try {
            result = saleService.getAllSales();
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            LOG.error("ERROR in getAllSales",  e);
        }
        return new ResponseEntity<>(result, status);
    }

    @PostMapping
    ResponseEntity<SaleEntity> save(@RequestBody SaleEntity sale) {
        SaleEntity result = null;
        try {
            result = saleService.save(sale);
            status = HttpStatus.CREATED;
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            LOG.error("ERROR in save", e);
        }
        return new ResponseEntity<>(result, status);
    }

    @PutMapping
    ResponseEntity<SaleEntity> update(@RequestBody SaleEntity product) {
        SaleEntity result = null;
        try {
            result = saleService.update(product);
            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            LOG.error("ERROR in update", e);
        }
        return new ResponseEntity<>(result, status);
    }
}
