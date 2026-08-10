package com.logden.backend.web;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logden.backend.domain.Order;
import com.logden.backend.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Get all orders of the current user
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public List<Order> getUserOrders() {
        return orderService.getUserOrders();
    }

    // Get an order by its ID
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);

    }

    // Get an order by ID if it belongs to the current user
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/{id}")
    public Order getUserOrder(@PathVariable Long id) {
        return orderService.getUserOrderById(id);
    }

    // Create an order using the items in the current user's cart
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public Order createOrder() {
        return orderService.createOrder();
    }
}
