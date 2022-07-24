package com.todo1.hulkstore.controller;

import com.todo1.hulkstore.entity.InventoryEntity;
import com.todo1.hulkstore.service.IInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("inventaryController")
@RequestMapping("/inventory")
public class InventoryController {
    private HttpStatus status = HttpStatus.OK;

    private static Logger LOG = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    @Lazy
    IInventoryService inventoryService;
    @GetMapping
    ResponseEntity<List<InventoryEntity>> getAllInventories() {
        List<InventoryEntity> result = null;

        try {
            result = inventoryService.getAllInventories();
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            LOG.error("ERROR in getAllInventoriess",  e);
        }
        return new ResponseEntity<>(result, status);
    }

    @PostMapping
    ResponseEntity<InventoryEntity> save(@RequestBody InventoryEntity inventory) {
        InventoryEntity result = null;
        try {
            result = inventoryService.save(inventory);
            status = HttpStatus.CREATED;
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            LOG.error("ERROR in save", e);
        }
        return new ResponseEntity<>(result, status);
    }

    @PutMapping
    ResponseEntity<InventoryEntity> update(@RequestBody InventoryEntity inventory) {
        InventoryEntity result = null;
        try {
            result = inventoryService.update(inventory);
            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            LOG.error("ERROR in update", e);
        }
        return new ResponseEntity<>(result, status);
    }
}
