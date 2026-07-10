package com.logden.backend.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.logden.backend.domain.Product;
import com.logden.backend.domain.ProductRepository;
import com.logden.backend.exception.ResourceAlreadyExistsException;
import com.logden.backend.exception.ResourceNotFoundException;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Adminille endpointit tuotteen lisäykseen, muokkaukseen ja poistoon

    public Product addProduct(Product product) {
        if (productRepository.findByName(product.getName()).isPresent()) {
            throw new ResourceAlreadyExistsException("Product name already exists");
        }
        if (product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        if (product.getStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (updatedProduct.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        if (updatedProduct.getStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setStock(updatedProduct.getStock());
        product.setActive(updatedProduct.getActive());
        product.setCategory(updatedProduct.getCategory());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        productRepository.delete(product);
    }

}
