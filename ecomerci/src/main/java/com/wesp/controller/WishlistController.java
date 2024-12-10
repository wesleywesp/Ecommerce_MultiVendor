package com.wesp.controller;

import com.wesp.infra.exception.CustumerException;
import com.wesp.infra.exception.ProductException;
import com.wesp.model.Product;
import com.wesp.model.User;
import com.wesp.model.Wishlist;
import com.wesp.service.ProductService;
import com.wesp.service.UserService;
import com.wesp.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Wishlist> getWishlistByUserId(@RequestHeader("Authorization") String token) throws CustumerException {
        User user = userService.findByJwtToken(token);
        return ResponseEntity.ok(wishlistService.getWishlistByUserId(user));
    }
    @PostMapping("/add-product/{productId}")
    public ResponseEntity<Wishlist> addProductToWishlist(@RequestHeader("Authorization") String token,
                                                         @PathVariable Long productId) throws CustumerException, ProductException {
        User user = userService.findByJwtToken(token);
        Product product = productService.findProductById(productId);
        return ResponseEntity.ok(wishlistService.addProductToWishlist(user, product));
    }
}
