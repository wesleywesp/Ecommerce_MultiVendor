package com.wesp.repository;

import com.wesp.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<Wishlist,Long > {
    Wishlist findByUserId(Long userId);

}
