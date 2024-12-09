package com.wesp.service;

import com.wesp.model.CartItem;

public interface CartItemService {

    CartItem UpdateCartItem(Long userId, Long cartItemId, CartItem cartItem);
    void removeCartItem(Long userId, Long cartItemId);
    CartItem findCartItemById(Long cartItemId);
}
