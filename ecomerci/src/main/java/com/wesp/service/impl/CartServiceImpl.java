package com.wesp.service.impl;

import com.wesp.model.Cart;
import com.wesp.model.CartItem;
import com.wesp.model.Product;
import com.wesp.model.User;
import com.wesp.repository.CartItemRepository;
import com.wesp.repository.CartRepository;
import com.wesp.service.CartService;
import com.wesp.service.ProductService;
import com.wesp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;



    @Override
    public CartItem addProductToCart(User user, Product product, String size, Integer quantity) {
        Cart cart = findUserCard(user);
        CartItem isPresent = cartItemRepository.findByCartIdAndProductAndSize(cart.getId(), product, size);
        if(isPresent == null){
            CartItem cartItem = new CartItem();

            cartItem.setProduct(product);
            cartItem.setSize(size);
            cartItem.setQuantity(quantity);
            cartItem.setUserId(user.getId());

            cart.getCarItems().add(cartItem);

            BigDecimal totalPrice = product.getSellingPrice().multiply(BigDecimal.valueOf(quantity));
            cartItem.setSellingPrice(totalPrice);


            cartItem.setCart(cart);
            cartItemRepository.save(cartItem);
            return cartItem;
        }
        return isPresent;
    }

    @Override
    public Cart findUserCard(User user) {
        Cart cart= cartRepository.findByUserId(user.getId());

        BigDecimal totalPrice = BigDecimal.ZERO;
        Integer totalitem = 0;
        BigDecimal totalDiscountedPrice = BigDecimal.ZERO;
        for(CartItem cartItem : cart.getCarItems()) {
            totalitem += cartItem.getQuantity();
            totalPrice = totalPrice.add(cartItem.getMrpPrice());
            totalDiscountedPrice = totalDiscountedPrice.add(cartItem.getSellingPrice());
        }
        cart.setTotalMrpPrice(totalPrice);
        cart.setTotalItems(totalitem);
        cart.setTotalSellingPrice(totalDiscountedPrice);
        cart.setDiscount(calculateDiscountPercentage(totalDiscountedPrice, totalPrice));
        cartRepository.save(cart);
        return cart;
    }

    private BigDecimal calculateDiscountPercentage(BigDecimal sellingPrice, BigDecimal mrpPrice) {
        if (mrpPrice.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("MRP price cannot be zero");
        }
        // Definir precisão e modo de arredondamento
        return mrpPrice.subtract(sellingPrice)
                .divide(mrpPrice, 2, RoundingMode.HALF_UP) // Precisão de 2 casas decimais
                .multiply(BigDecimal.valueOf(100));
    }
}
