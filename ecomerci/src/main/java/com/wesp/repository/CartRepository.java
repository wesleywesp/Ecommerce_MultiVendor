package com.wesp.repository;

import com.wesp.model.Cart;

import com.wesp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long id);

}
