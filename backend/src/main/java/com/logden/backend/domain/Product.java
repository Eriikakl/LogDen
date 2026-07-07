package com.logden.backend.domain;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Product(String name, String description, BigDecimal price, Integer stock, Boolean active,
            Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.active = active;
        this.category = category;
    }

       public Product() {
    }

       public Long getProductId() {
           return productId;
       }

       public void setProductId(Long productId) {
           this.productId = productId;
       }

       public String getName() {
           return name;
       }

       public void setName(String name) {
           this.name = name;
       }

       public String getDescription() {
           return description;
       }

       public void setDescription(String description) {
           this.description = description;
       }

       public BigDecimal getPrice() {
           return price;
       }

       public void setPrice(BigDecimal price) {
           this.price = price;
       }

       public Integer getStock() {
           return stock;
       }

       public void setStock(Integer stock) {
           this.stock = stock;
       }

       public Boolean getActive() {
           return active;
       }

       public void setActive(Boolean active) {
           this.active = active;
       }

       public Category getCategory() {
           return category;
       }

       public void setCategory(Category category) {
           this.category = category;
       }

       @Override
       public String toString() {
        return "Product [productId=" + productId + ", name=" + name + ", description=" + description + ", price="
                + price + ", stock=" + stock + ", active=" + active + ", category=" + category + "]";
       }

    

    

}
