package com.logden.backend.service;

import org.springframework.stereotype.Service;

import com.logden.backend.domain.Cart;
import com.logden.backend.domain.CartRepository;

@Service
public class CartService {

   private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }
}
