package com.logden.backend.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank
    @Size(max = 25)
    private String firstName;

    @NotBlank
    @Size(max = 25)
    private String lastName;

    @NotBlank
    @Size(max = 100)
    private String address;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(max = 20)
    private String phone;

    @NotBlank
    @Size(min = 8, max = 100)
    private String password;

    public RegisterRequest() {
    }

    public RegisterRequest(@NotBlank @Size(max = 25) String firstName, @NotBlank @Size(max = 25) String lastName,
            @NotBlank @Size(max = 100) String address, @NotBlank @Email String email,
            @NotBlank @Size(max = 20) String phone, @NotBlank @Size(min = 8, max = 100) String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
