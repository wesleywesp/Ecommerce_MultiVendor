package com.wesp.service.impl;

import com.wesp.domain.HomeCategorySection;
import com.wesp.model.Deal;
import com.wesp.model.Home;
import com.wesp.model.HomeCategory;
import com.wesp.repository.DealRepository;
import com.wesp.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final DealRepository dealRepository;

    @Override
    public Home creatHomePageData(List<HomeCategory> categories) {
        // Filtra categorias por seções específicas
        List<HomeCategory> gridCategories = filterCategoriesBySection(categories, HomeCategorySection.GRID);
        List<HomeCategory> shopByCategories = filterCategoriesBySection(categories, HomeCategorySection.SHOP_BY_CATEGORIES);
        List<HomeCategory> electronicsCategories = filterCategoriesBySection(categories, HomeCategorySection.ELECTRONICS_CATEGORiES);
        List<HomeCategory> dealsCategories = filterCategoriesBySection(categories, HomeCategorySection.DEALS);

        // Gerencia os dados de ofertas (deals)
        List<Deal> deals;
        if (dealRepository.findAll().isEmpty()) {
            deals = dealsCategories.stream()
                    .map(category -> new Deal(null, BigDecimal.valueOf(10), category))
                    .collect(Collectors.toList());
            deals = dealRepository.saveAll(deals);
        } else {
            deals = dealRepository.findAll();
        }

        // Cria o objeto Home com os dados filtrados
        Home home = new Home();
        home.setGrid(gridCategories);
        home.setShopByCategory(shopByCategories);
        home.setElectronicsCategory(electronicsCategories);
        home.setDealsCategory(dealsCategories);
        home.setDeals(deals);

        return home;
    }

    // Método auxiliar para filtrar categorias por seção
    private List<HomeCategory> filterCategoriesBySection(List<HomeCategory> categories, HomeCategorySection section) {
        return categories.stream()
                .filter(category -> category.getSection() == section)
                .collect(Collectors.toList());
    }
}

