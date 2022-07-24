package com.todo1.hulkstore.service;

import com.todo1.hulkstore.entity.BuyEntity;

import java.util.List;

public interface IBuyService {
    List<BuyEntity> getAllBuys() throws Exception;
    BuyEntity save(BuyEntity buy) throws Exception;
    BuyEntity update(BuyEntity buy) throws Exception;
}
