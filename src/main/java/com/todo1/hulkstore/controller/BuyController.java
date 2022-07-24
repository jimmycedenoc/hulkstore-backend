package com.todo1.hulkstore.controller;

import com.todo1.hulkstore.entity.BuyEntity;
import com.todo1.hulkstore.entity.SaleEntity;
import com.todo1.hulkstore.service.IBuyService;
import com.todo1.hulkstore.service.ISaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("buyController")
@RequestMapping("/api/byu")
public class BuyController {
    private HttpStatus status = HttpStatus.OK;

    private static Logger LOG = LoggerFactory.getLogger(BuyController.class);

    @Autowired
    @Lazy
    IBuyService buyService;

    @GetMapping
    ResponseEntity<List<BuyEntity>> getAllBuys() {
        List<BuyEntity> result = null;

        try {
            result = buyService.getAllBuys();
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            LOG.error("ERROR in getAllBuys",  e);
        }
        return new ResponseEntity<>(result, status);
    }

    @PostMapping
    ResponseEntity<BuyEntity> save(@RequestBody BuyEntity buy) {
        BuyEntity result = null;
        try {
            result = buyService.save(buy);
            status = HttpStatus.CREATED;
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            LOG.error("ERROR in save", e);
        }
        return new ResponseEntity<>(result, status);
    }

    @PutMapping
    ResponseEntity<BuyEntity> update(@RequestBody BuyEntity buy) {
        BuyEntity result = null;
        try {
            result = buyService.update(buy);
            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            LOG.error("ERROR in update", e);
        }
        return new ResponseEntity<>(result, status);
    }
}
