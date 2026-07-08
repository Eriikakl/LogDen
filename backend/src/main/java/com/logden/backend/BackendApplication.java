package com.logden.backend;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.logden.backend.domain.Cart;
import com.logden.backend.domain.CartItem;
import com.logden.backend.domain.CartItemRepository;
import com.logden.backend.domain.CartRepository;
import com.logden.backend.domain.Category;
import com.logden.backend.domain.CategoryRepository;
import com.logden.backend.domain.OrderItemRepository;
import com.logden.backend.domain.Order;
import com.logden.backend.domain.OrderItem;
import com.logden.backend.domain.OrderRepository;
import com.logden.backend.domain.Product;
import com.logden.backend.domain.ProductRepository;
import com.logden.backend.domain.User;
import com.logden.backend.domain.UserRepository;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner loadData(ProductRepository productRepository,
            CategoryRepository categoryRepository,
            UserRepository userRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository) {
        return args -> {

            User user = userRepository.save(new User(
                    "Erkki",
                    "Esimerkki",
                    "Testikatu 1, Helsinki",
                    "erkki@example.com",
                    "0401234567",
                    "salasana",
                    "CUSTOMER"));

            Cart cart = cartRepository.save(new Cart(user));

            Category decoration = new Category("Puukoristeet", "Koristeeksi luotuja puuesineitä");
            categoryRepository.save(decoration);

            Product product1 = productRepository.save(new Product(
                    "Puulehmä",
                    "Vuoltu puulehmä",
                    new BigDecimal("299.90"),
                    20,
                    true,
                    decoration));

            Product product2 = productRepository.save(new Product(
                    "Puusaapas",
                    "Pölkystä muotoiltu saapas",
                    new BigDecimal("499.90"),
                    15,
                    true,
                    decoration));

            cartItemRepository.save(new CartItem(cart, product1, 2));
            cartItemRepository.save(new CartItem(cart, product2, 1));

            Order order = orderRepository.save(new Order(user, "PAID", LocalDateTime.now(), new BigDecimal("1099.70")));
            orderItemRepository.save(new OrderItem(
                    order, product1, 2, product1.getPrice()));

            orderItemRepository.save(new OrderItem(
                    order, product2, 1, product2.getPrice()));

        };
    }

}
