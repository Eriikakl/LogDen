package com.logden.backend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.logden.backend.domain.Cart;
import com.logden.backend.domain.CartItem;
import com.logden.backend.domain.CartItemRepository;
import com.logden.backend.domain.CartRepository;
import com.logden.backend.domain.Product;
import com.logden.backend.domain.ProductRepository;
import com.logden.backend.domain.User;
import com.logden.backend.exception.ResourceNotFoundException;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CurrentUserService currentUserService;

    public CartService(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            ProductRepository productRepository,
            CurrentUserService currentUserService) {

        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.currentUserService = currentUserService;
    }
    
    // Get the cart of the currently logged-in user
    public Cart getCart() {
        User user = currentUserService.getCurrentUser();

        return cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    // Add item to the current user's cart
    public CartItem addItem(Long productId, Integer quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        User user = currentUserService.getCurrentUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Optional<CartItem> existingItem = cartItemRepository.findByCartAndProduct(cart, product);

        if (existingItem.isPresent()) {

            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);

            return cartItemRepository.save(item);
        }

        CartItem newItem = new CartItem(
                cart,
                product,
                quantity);

        return cartItemRepository.save(newItem);
    }

    // Remove item from the current user's cart
    public void removeItem(Long cartItemId) {

        Cart cart = getCart();

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (!item.getCart().equals(cart)) {
            throw new ResourceNotFoundException("Cart item not found");
        }

        cartItemRepository.delete(item);
    }

    // Update item quantity in the current user's cart
    public CartItem updateQuantity(Long cartItemId, Integer quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        Cart cart = getCart();

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (!item.getCart().equals(cart)) {
            throw new ResourceNotFoundException("Cart item not found");
        }

        item.setQuantity(quantity);

        return cartItemRepository.save(item);
    }

}
