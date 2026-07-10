package com.logden.backend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.logden.backend.domain.Cart;
import com.logden.backend.domain.CartItem;
import com.logden.backend.domain.CartItemRepository;
import com.logden.backend.domain.CartRepository;
import com.logden.backend.domain.Product;
import com.logden.backend.domain.ProductRepository;
import com.logden.backend.exception.ResourceNotFoundException;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartService(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            ProductRepository productRepository) {

        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    public CartItem addItem(Long cartId, Long productId, Integer quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

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

    public void removeItem(Long cartItemId) {

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        cartItemRepository.delete(item);
    }

    public CartItem updateQuantity(Long cartItemId, Integer quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        item.setQuantity(quantity);

        return cartItemRepository.save(item);
    }
}
