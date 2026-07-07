package com.logden.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.logden.backend.domain.Product;
import com.logden.backend.domain.ProductRepository;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }
}
