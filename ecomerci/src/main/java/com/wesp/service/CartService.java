package com.wesp.service;

import com.wesp.model.Cart;
import com.wesp.model.CartItem;
import com.wesp.model.Product;
import com.wesp.model.User;

public interface CartService {
    public CartItem addProductToCart(User user, Product product, String size,Integer quantity);

    public Cart findUserCard(User user);
}
