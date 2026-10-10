package com.example.fooddelivery.model;

public class Order {
    private String orderId;
    private String username;
    private String itemName;
    private int quantity;
    private double totalPrice;

    public Order(String orderId, String username, String itemName, int quantity, double totalPrice) {
        this.orderId = orderId;
        this.username = username;
        this.itemName = itemName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    // Encapsulation - Getters and Setters
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}