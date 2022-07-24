package com.todo1.hulkstore.service;

import com.todo1.hulkstore.entity.SaleEntity;

import java.util.List;

public interface ISaleService {
    List<SaleEntity> getAllSales() throws Exception;
    SaleEntity save(SaleEntity product) throws Exception;
    SaleEntity update(SaleEntity product) throws Exception;
}
