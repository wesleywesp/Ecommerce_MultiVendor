package com.wesp.service.impl;

import com.wesp.infra.exception.CartException;
import com.wesp.model.CartItem;
import com.wesp.model.User;
import com.wesp.repository.CartItemRepository;
import com.wesp.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;


    @Override
    public CartItem UpdateCartItem(Long userId, Long cartItemId, CartItem cartItem) {
        // Verifica se o CartItem existe
        CartItem cartItem1 = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartException("Cart Item not found"));
        User cartItemUser = cartItem1.getCart().getUser();
        if(!cartItemUser.getId().equals(userId)) {
            throw new CartException("User not authorized to update this cart item");
        }
        cartItem1.setQuantity(cartItem.getQuantity());
        cartItem1.setSellingPrice(cartItem1.getProduct().getSellingPrice().multiply(BigDecimal.valueOf(cartItem1.getQuantity())));
        cartItem1.setMrpPrice(cartItem1.getProduct().getMrpPrice().multiply(BigDecimal.valueOf(cartItem1.getQuantity())));
        return cartItemRepository.save(cartItem1);
    }



    @Override
    public void removeCartItem(Long userId, Long cartItemId) {
        // Verifica se o CartItem existe
        CartItem cartItem1 = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartException("Cart Item not found"));

        // Valida o usuário do carrinho
        User cartItemUser = cartItem1.getCart().getUser();
        if(!cartItemUser.getId().equals(userId)) {
            throw new CartException("User not authorized to delete this cart item");
        }
        else cartItemRepository.deleteById(cartItemId);

    }

    @Override
    public CartItem findCartItemById(Long cartItemId) {
        // Verifica se o CartItem existe
        return cartItemRepository.findById(cartItemId).orElseThrow(()-> new CartException("Cart Item not found with id: "+cartItemId));

    }
}
