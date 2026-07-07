package com.logden.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.logden.backend.domain.Order;
import com.logden.backend.domain.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
}
