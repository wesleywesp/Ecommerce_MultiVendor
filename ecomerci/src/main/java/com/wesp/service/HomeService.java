package com.wesp.service;

import com.wesp.model.Home;
import com.wesp.model.HomeCategory;

import java.util.List;

public interface HomeService {
    public Home creatHomePageData(List<HomeCategory> categories);
}
