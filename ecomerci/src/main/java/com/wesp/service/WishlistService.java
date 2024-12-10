package com.wesp.service;

import com.wesp.model.Product;
import com.wesp.model.User;
import com.wesp.model.Wishlist;

public interface WishlistService {
    Wishlist createWishlist(User user);
    Wishlist getWishlistByUserId(User user);
    Wishlist addProductToWishlist(User user, Product product);
}
