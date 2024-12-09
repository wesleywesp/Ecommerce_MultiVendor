package com.wesp.controller;

import com.wesp.infra.exception.CustumerException;
import com.wesp.infra.exception.ProductException;
import com.wesp.model.Cart;
import com.wesp.model.CartItem;
import com.wesp.model.Product;
import com.wesp.model.User;
import com.wesp.request.AddItemToCartRequestDTO;
import com.wesp.response.ApiResponse;
import com.wesp.service.CartItemService;
import com.wesp.service.CartService;
import com.wesp.service.ProductService;
import com.wesp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/cart")
public class CartController {
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Cart> findUserCarHolder(@RequestHeader("Authorization") String token) throws CustumerException {
        User user = userService.findByJwtToken(token);
        Cart cart = cartService.findUserCard(user);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestHeader("Authorization") String token,
        @RequestBody AddItemToCartRequestDTO request) throws CustumerException, ProductException {
        User user = userService.findByJwtToken(token);
        Product product = productService.findProductById(request.ProductId());
        CartItem cartItem = cartService.addProductToCart(user, product, request.Size(), request.Quantity());
        ApiResponse response = new ApiResponse("Product Added To Cart Successfully");

        return ResponseEntity.ok(cartItem);
    }

    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartItemId, @RequestHeader("Authorization") String token)throws CustumerException {
        User user = userService.findByJwtToken(token);
        cartItemService.removeCartItem(user.getId(), cartItemId);
        ApiResponse response = new ApiResponse("Product Removed From Cart Successfully");

        return ResponseEntity.ok(response);
    }

    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long cartItemId, @RequestHeader("Authorization") String token,
                                                   @RequestBody CartItem cartItem) throws CustumerException, ProductException {
        User user = userService.findByJwtToken(token);
        CartItem updatedCartItem = new CartItem();
        if(cartItem.getQuantity() >0) {
            updatedCartItem = cartItemService.UpdateCartItem(user.getId(), cartItemId, cartItem);
        }
        return ResponseEntity.ok(updatedCartItem);
    }

}
