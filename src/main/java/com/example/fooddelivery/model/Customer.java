package com.example.fooddelivery.model;

public class Customer extends User {
    public Customer(String id, String username, String password) {
        super(id, username, password, "USER");
    }

    @Override
    public String getRoleDetails() {
        return "Customer: " + getUsername() + " [Access to Menu & Checkout]";
    }
}