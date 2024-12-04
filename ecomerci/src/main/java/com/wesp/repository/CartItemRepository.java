package com.wesp.repository;

import com.wesp.model.CartItem;
import com.wesp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndProductAndSize(Long id, Product product, String size);
}
