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

    private final String USERS_FILE = "data/users.txt";
    private final String ITEMS_FILE = "data/items.txt";
    private final String ORDERS_FILE = "data/orders.txt";

    public FileStorageService() {
        createDataFolder();
    }

    private void createDataFolder() {
        File folder = new File("data");
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    // User Operations
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(USERS_FILE);
        if (!file.exists()) return users;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    users.add(new User(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(user.getId() + "," + user.getUsername() + "," + user.getPassword() + "," + user.getRole());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Item Operations
    public List<FoodItem> getAllItems() {
        List<FoodItem> items = new ArrayList<>();
        File file = new File(ITEMS_FILE);
        if (!file.exists()) return items;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    items.add(new FoodItem(parts[0], parts[1], parts[2], Double.parseDouble(parts[3])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    public void saveItem(FoodItem item) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ITEMS_FILE, true))) {
            writer.write(item.getId() + "," + item.getName() + "," + item.getCategory() + "," + item.getPrice());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteItem(String id) {
        List<FoodItem> items = getAllItems();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ITEMS_FILE))) {
            for (FoodItem item : items) {
                if (!item.getId().equals(id)) {
                    writer.write(item.getId() + "," + item.getName() + "," + item.getCategory() + "," + item.getPrice());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Order Operations
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        File file = new File(ORDERS_FILE);
        if (!file.exists()) return orders;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    orders.add(new Order(parts[0], parts[1], parts[2], parts[3], parts[4], Integer.parseInt(parts[5]), Double.parseDouble(parts[6]), parts[7]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public void saveOrder(Order order) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDERS_FILE, true))) {
            writer.write(order.getOrderId() + "," + order.getCustomerName() + "," + order.getAddress() + "," + order.getPhone() + "," + order.getItemName() + "," + order.getQuantity() + "," + order.getTotalPrice() + "," + order.getPaymentMethod());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}