package com.wesp.service.impl;

import com.wesp.infra.exception.DealException;
import com.wesp.infra.exception.HomeCategoryException;
import com.wesp.model.Deal;
import com.wesp.model.HomeCategory;
import com.wesp.repository.DealRepository;
import com.wesp.repository.HomeCategoryRepository;
import com.wesp.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {
    private final DealRepository dealRepository;
    private final HomeCategoryRepository homeCategoryRepository;
    @Override
    public List<Deal> getDeals() {
        return dealRepository.findAll();
    }

    @Override
    public Deal createDeal(Deal deal) {
        HomeCategory category = homeCategoryRepository.findById(deal.getCategory().getId()).orElseThrow(()-> new HomeCategoryException("Category not found"));
        Deal newDeal = dealRepository.save(deal);
        newDeal.setCategory(category);
        newDeal.setDiscount(deal.getDiscount());
        newDeal = dealRepository.save(newDeal);
        return newDeal;
    }

    @Override
    public Deal updateDeal(Deal deal) {
        Deal existingDeal = dealRepository.findById(deal.getId()).orElse(null);
        HomeCategory category = homeCategoryRepository.findById(deal.getCategory().getId()).orElseThrow(()-> new HomeCategoryException("Category not found"));
        if(existingDeal!=null){
            if(deal.getDiscount()!=null){
                existingDeal.setDiscount(deal.getDiscount());
            }
            if(category!=null){
            existingDeal.setCategory(category);
            }
        return dealRepository.save(existingDeal);
        }
        throw new DealException("Deal not found");
    }

    @Override
    public void deleteDeal(Long id) {
        Deal deal = dealRepository.findById(id).orElseThrow(()-> new DealException("Deal not found"));
        dealRepository.deleteById(id);

    }
}
