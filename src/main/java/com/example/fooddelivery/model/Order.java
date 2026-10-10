package com.example.fooddelivery.model;

public class Order {
    private String orderId;
    private String customerName;
    private String address;
    private String phone;
    private String itemName;
    private double totalPrice;
    private String paymentMethod;

    public Order(String orderId, String customerName, String address, String phone, String itemName, double totalPrice, String paymentMethod) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.address = address;
        this.phone = phone;
        this.itemName = itemName;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
    }

    // Encapsulation - Getters and Setters
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}