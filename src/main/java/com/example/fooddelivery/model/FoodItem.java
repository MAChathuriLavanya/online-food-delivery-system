package com.example.fooddelivery.model;

public class FoodItem {
    private String id;
    private String name;
    private String category;
    private double price;

    public FoodItem() {}

    public FoodItem(String id, String name, String category, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String toFileString() {
        return id + "," + name + "," + category + "," + price;
    }

    public static FoodItem fromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 4) {
            return new FoodItem(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]));
        }
        return null;
    }
}