package com.wesp.repository;

import com.wesp.model.HomeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface HomeCategoryRepository extends JpaRepository<HomeCategory, Long> {

}
