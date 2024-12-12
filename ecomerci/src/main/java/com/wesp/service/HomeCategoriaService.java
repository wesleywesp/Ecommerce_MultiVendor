package com.wesp.service;

import com.wesp.model.HomeCategory;

import java.util.List;

public interface HomeCategoriaService {
    HomeCategory creteHomeCategory(HomeCategory homeCategory);
    List<HomeCategory> createCategories(List<HomeCategory> homeCategories);
    HomeCategory updateHomeCategory(HomeCategory homeCategory, Long id);
    List<HomeCategory> getAllHomecategories();
}
