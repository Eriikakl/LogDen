package com.logden.backend.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.logden.backend.domain.Cart;
import com.logden.backend.domain.CartItem;
import com.logden.backend.domain.CartItemRepository;
import com.logden.backend.domain.CartRepository;
import com.logden.backend.domain.Order;
import com.logden.backend.domain.OrderItem;
import com.logden.backend.domain.OrderItemRepository;
import com.logden.backend.domain.OrderRepository;
import com.logden.backend.domain.User;
import com.logden.backend.domain.UserRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public OrderService(OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            UserRepository userRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public Order createOrder(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (CartItem item : cart.getItems()) {
            BigDecimal itemTotal = item.getProduct()
                    .getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));

            totalPrice = totalPrice.add(itemTotal);
        }

        Order order = new Order(
                user,
                "PENDING",
                LocalDateTime.now(),
                totalPrice);

        order = orderRepository.save(order);

        for (CartItem item : cart.getItems()) {

            OrderItem orderItem = new OrderItem(
                    order,
                    item.getProduct(),
                    item.getQuantity(),
                    item.getProduct().getPrice());

            orderItemRepository.save(orderItem);
        }

        cartItemRepository.deleteAll(cart.getItems());

        return order;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
}
