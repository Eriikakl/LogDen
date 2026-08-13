package com.logden.backend.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.logden.backend.domain.Cart;
import com.logden.backend.domain.CartItem;
import com.logden.backend.service.CartService;

@RestController
@RequestMapping("/api/cart")
@PreAuthorize("hasRole('USER')")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public Cart getCart() {
        return cartService.getCart();

    }

    @PostMapping("/items")
    public CartItem addItem(@RequestParam Long productId, @RequestParam Integer quantity) {

        return cartService.addItem(productId, quantity);
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/items/{cartItemId}")
    public void removeItem(@PathVariable Long cartItemId) {
        cartService.removeItem(cartItemId);
    }

    @PutMapping("/items/{cartItemId}")
    public CartItem updateQuantity(@PathVariable Long cartItemId, @RequestParam Integer quantity) {

        return cartService.updateQuantity(cartItemId, quantity);
    }

}
