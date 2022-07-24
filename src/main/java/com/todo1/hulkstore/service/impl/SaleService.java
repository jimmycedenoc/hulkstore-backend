package com.todo1.hulkstore.service.impl;

import com.todo1.hulkstore.entity.SaleEntity;
import com.todo1.hulkstore.repository.SaleRepository;
import com.todo1.hulkstore.service.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService implements ISaleService {
    @Autowired
    @Lazy
    private SaleRepository saleRepository;

    public SaleService() {

    }
    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }
    @Override
    public List<SaleEntity> getAllSales() throws Exception {
        return saleRepository.findAll();
    }

    @Override
    public SaleEntity save(SaleEntity sale) throws Exception {
        return saleRepository.save(sale);
    }

    @Override
    public SaleEntity update(SaleEntity sale) throws Exception {
        SaleEntity existingSale = saleRepository.findById(sale.getId()).orElse(null);
        existingSale.setDate(sale.getDate());
        existingSale.setQuantity(sale.getQuantity());
        existingSale.setUnitValue(sale.getUnitValue());
        existingSale.setUserCode(sale.getUserCode());
        existingSale.setProductCode(sale.getProductCode());

        return saleRepository.save(existingSale);
    }
}
