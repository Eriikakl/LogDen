package com.logden.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phone;
    private String passwordHash;


    public User(String firstname, String lastname, String address, String email, String phone) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

     public User() {
       
    }

     public Long getUserId() {
         return userId;
     }

     public void setUserId(Long userId) {
         this.userId = userId;
     }

     public String getFirstName() {
         return firstName;
     }

     public void setFirstName(String firstname) {
         this.firstName = firstname;
     }

     public String getLastName() {
         return lastName;
     }

     public void setLastName(String lastname) {
         this.lastName = lastname;
     }

     public String getAddress() {
         return address;
     }

     public void setAddress(String address) {
         this.address = address;
     }

     public String getEmail() {
         return email;
     }

     public void setEmail(String email) {
         this.email = email;
     }

     public String getPhone() {
         return phone;
     }

     public void setPhone(String phone) {
         this.phone = phone;
     }

      public String getPasswordHash() {
         return passwordHash;
     }

     public void setPasswordHash(String passwordHash) {
         this.passwordHash = passwordHash;
     }

     @Override
     public String toString() {
        return "User [userId=" + userId + ", firstname=" + firstName + ", lastname=" + lastName + ", address=" + address
                + ", email=" + email + ", phone=" + phone + "]";
     }


}
