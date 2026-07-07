package com.logden.backend.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String status;
    private LocalDateTime createdAt;
    private BigDecimal totalPrice;

    public Order(User user, String status, LocalDateTime createdAt, BigDecimal totalPrice) {
        this.user = user;
        this.status = status;
        this.createdAt = createdAt;
        this.totalPrice = totalPrice;
    }

      public Order() {

    }

      public Long getOrderId() {
          return orderId;
      }

      public void setOrderId(Long orderId) {
          this.orderId = orderId;
      }

      public User getUser() {
          return user;
      }

      public void setUser(User user) {
          this.user = user;
      }

      public String getStatus() {
          return status;
      }

      public void setStatus(String status) {
          this.status = status;
      }

      public LocalDateTime getCreatedAt() {
          return createdAt;
      }

      public void setCreatedAt(LocalDateTime createdAt) {
          this.createdAt = createdAt;
      }

      public BigDecimal getTotalPrice() {
          return totalPrice;
      }

      public void setTotalPrice(BigDecimal totalPrice) {
          this.totalPrice = totalPrice;
      }

      @Override
      public String toString() {
        return "Order [orderId=" + orderId + ", user=" + user + ", status=" + status + ", createdAt=" + createdAt
                + ", totalPrice=" + totalPrice + "]";
      }

}
