package com.wesp.service;

import com.wesp.model.Deal;

import java.util.List;

public interface DealService{
    List<Deal> getDeals();
    Deal createDeal(Deal deal);
    Deal updateDeal(Deal deal);
    void deleteDeal(Long id);
}
