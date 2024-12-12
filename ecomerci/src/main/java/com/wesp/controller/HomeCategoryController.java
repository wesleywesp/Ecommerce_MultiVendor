package com.wesp.controller;


import com.wesp.model.Home;
import com.wesp.model.HomeCategory;
import com.wesp.service.HomeCategoriaService;
import com.wesp.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class HomeCategoryController {
    private final HomeCategoriaService homeCategoriaService;
    private final HomeService homeService;


    @PostMapping("/home/categories")
    public ResponseEntity<Home> createHomeCategories(@RequestBody List<HomeCategory> homeCategor){
        List<HomeCategory> categories = homeCategoriaService.createCategories(homeCategor);
        Home home = homeService.creatHomePageData(categories);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin/home-categories")
    public ResponseEntity<List<HomeCategory>> getHomeCategory(@RequestBody List<HomeCategory> homeCategor){
        return ResponseEntity.ok(homeCategoriaService.getAllHomecategories());
    }
    @PutMapping("/admin/home/categories/{id}")
    public ResponseEntity<HomeCategory> updateHomeCategory(@RequestBody HomeCategory homeCategory, @PathVariable Long id){
        return ResponseEntity.ok(homeCategoriaService.updateHomeCategory(homeCategory, id));
    }
}
