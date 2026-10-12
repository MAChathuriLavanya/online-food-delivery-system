package com.example.fooddelivery.service;

import com.example.fooddelivery.model.FoodItem;
import com.example.fooddelivery.model.Order;
import com.example.fooddelivery.model.User;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileStorageService {

    private final String ITEMS_FILE = "data/items.txt";
    private final String ORDERS_FILE = "data/orders.txt";
    private final String USERS_FILE = "data/users.txt";

    public List<FoodItem> getAllFoodItems() {
        List<FoodItem> items = new ArrayList<>();
        File file = new File(ITEMS_FILE);
        if (!file.exists()) return items;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    items.add(new FoodItem(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]), parts[4]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    public void saveAllFoodItems(List<FoodItem> items) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ITEMS_FILE))) {
            for (FoodItem item : items) {
                bw.write(item.getId() + "," + item.getName() + "," + item.getCategory() + "," + item.getPrice() + "," + item.getImageUrl());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        File file = new File(ORDERS_FILE);
        if (!file.exists()) return orders;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Order read logic
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(USERS_FILE);
        if (!file.exists()) return users;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // User read logic
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}