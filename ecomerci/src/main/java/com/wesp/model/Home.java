package com.wesp.model;

import lombok.Data;

import java.util.List;

@Data
public class Home {
    private List<HomeCategory> grid;
    private List<HomeCategory>ShopByCategory;
    private List<HomeCategory>electronicsCategory;
    private List<HomeCategory>dealsCategory;
    private List<Deal> deals;
}
