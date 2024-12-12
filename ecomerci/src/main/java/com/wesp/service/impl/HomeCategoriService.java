package com.wesp.service.impl;

import com.wesp.infra.exception.HomeCategoryException;
import com.wesp.model.HomeCategory;
import com.wesp.repository.HomeCategoryRepository;
import com.wesp.service.HomeCategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HomeCategoriService implements HomeCategoriaService {
    private final HomeCategoryRepository homeCategoryRepository;
    @Override
    public HomeCategory creteHomeCategory(HomeCategory homeCategory) {

        return homeCategoryRepository.save(homeCategory);
    }

    @Override
    public List<HomeCategory> createCategories(List<HomeCategory> homeCategories) {
        if(homeCategoryRepository.findAll().isEmpty()){
            return homeCategoryRepository.saveAll(homeCategories);
        }
        return homeCategoryRepository.findAll();
    }

    @Override
    public HomeCategory updateHomeCategory(HomeCategory homeCategory, Long id) {
        HomeCategory homeCategory1 = homeCategoryRepository.findById(id).orElseThrow(()->
                new HomeCategoryException("Home category not found"));
        if(homeCategory.getImage()!=null){
            homeCategory1.setImage(homeCategory.getImage());
        }
        if(homeCategory.getCategoryId()!=null){
            homeCategory1.setCategoryId(homeCategory.getCategoryId());
        }
        return homeCategoryRepository.save(homeCategory1);
    }

    @Override
    public List<HomeCategory> getAllHomecategories() {
        return homeCategoryRepository.findAll();
    }
}
