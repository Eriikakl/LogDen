package com.logden.backend.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

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
import com.logden.backend.exception.ResourceNotFoundException;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CurrentUserService currentUserService;

    public OrderService(OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            CurrentUserService currentUserService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.currentUserService = currentUserService;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public Order getUserOrderById(Long id) {
        User user = currentUserService.getCurrentUser();

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!order.getUser().equals(user)) {
            throw new ResourceNotFoundException("Order not found");
        }

        return order;
    }

    public Order createOrder() {

        User user = currentUserService.getCurrentUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
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

        order.setOrderNumber(generateOrderNumber());

        for (CartItem item : cart.getItems()) {

            OrderItem orderItem = new OrderItem(
                    order,
                    item.getProduct(),
                    item.getQuantity(),
                    item.getProduct().getPrice());

            order.getItems().add(orderItem);
        }

        order = orderRepository.save(order);
        cart.getItems().clear();
        cartRepository.save(cart);

        return order;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getUserOrders() {
        User user = currentUserService.getCurrentUser();

        return orderRepository.findByUser(user);
    }

    private String generateOrderNumber() {
        String orderNumber;

        do {
            orderNumber = "ORD-"
                    + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)
                    + "-"
                    + UUID.randomUUID()
                            .toString()
                            .substring(0, 6)
                            .toUpperCase();

        } while (orderRepository.existsByOrderNumber(orderNumber));

        return orderNumber;
    }
}
