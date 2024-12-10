package com.wesp.service.impl;

import com.wesp.model.Product;
import com.wesp.model.User;
import com.wesp.model.Wishlist;
import com.wesp.repository.WishListRepository;
import com.wesp.service.ProductService;
import com.wesp.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final WishListRepository wishListRepository;
    private final ProductService productService;
    @Override
    public Wishlist createWishlist(User user) {
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        return wishListRepository.save(wishlist);
    }

    @Override
    public Wishlist getWishlistByUserId(User user) {
         Wishlist wishlist= wishListRepository.findByUserId(user.getId());
         if (wishlist == null) {
             wishlist=createWishlist(user);
         }
            return wishlist;
    }

    @Override
    public Wishlist addProductToWishlist(User user, Product product) {
        Wishlist wishlist = getWishlistByUserId(user);
        if(wishlist.getProducts().contains(product)){
            wishlist.getProducts().remove(product);
        }else {
            wishlist.getProducts().add(product);
        }

        return wishListRepository.save(wishlist);
    }
}
