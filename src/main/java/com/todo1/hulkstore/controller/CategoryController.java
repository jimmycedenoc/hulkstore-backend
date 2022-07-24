package com.todo1.hulkstore.controller;

import com.todo1.hulkstore.entity.CategoryEntity;
import com.todo1.hulkstore.service.ICategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("categoryController")
@RequestMapping("/api/category")
public class CategoryController {
    private HttpStatus status = HttpStatus.OK;

    private static Logger LOG = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    @Lazy
    ICategoryService categoryService;

    @GetMapping
    ResponseEntity<List<CategoryEntity>> getAllCategories() {
        List<CategoryEntity> result = null;

        try {
            result = categoryService.getAllCategories();
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            LOG.error("ERROR in getAllCategories",  e);
        }
        return new ResponseEntity<>(result, status);
    }

    @PostMapping
    ResponseEntity<CategoryEntity> save(@RequestBody CategoryEntity category) {
        CategoryEntity result = null;
        try {
            result = categoryService.save(category);
            status = HttpStatus.CREATED;
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            LOG.error("ERROR in save", e);
        }
        return new ResponseEntity<>(result, status);
    }

    @PutMapping
    ResponseEntity<CategoryEntity> update(@RequestBody CategoryEntity category) {
        CategoryEntity result = null;
        try {
            result = categoryService.update(category);
            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            LOG.error("ERROR in update", e);
        }
        return new ResponseEntity<>(result, status);
    }

}
