package com.example.fooddelivery.service;

import com.example.fooddelivery.model.Admin;
import com.example.fooddelivery.model.Customer;
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

    // Users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(USERS_FILE);
        if (!file.exists()) return users;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";");
                if (parts.length >= 4) {
                    if ("ADMIN".equalsIgnoreCase(parts[3].trim())) {
                        users.add(new Admin(parts[0].trim(), parts[1].trim(), parts[2].trim()));
                    } else {
                        users.add(new Customer(parts[0].trim(), parts[1].trim(), parts[2].trim()));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(user.getId() + ";" + user.getUsername() + ";" + user.getPassword() + ";" + user.getRole());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String id) {
        List<User> users = getAllUsers();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                if (!user.getId().equals(id)) {
                    writer.write(user.getId() + ";" + user.getUsername() + ";" + user.getPassword() + ";" + user.getRole());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Food Items
    public List<FoodItem> getAllItems() {
        List<FoodItem> items = new ArrayList<>();
        File file = new File(ITEMS_FILE);
        if (!file.exists()) return items;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";");
                if (parts.length >= 4) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    String category = parts[2].trim();
                    double price = Double.parseDouble(parts[3].trim());

                    String imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=500";
                    if (parts.length >= 5 && !parts[4].trim().isEmpty()) {
                        imageUrl = parts[4].trim();
                    }
                    items.add(new FoodItem(id, name, category, price, imageUrl));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    public void saveItem(FoodItem item) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ITEMS_FILE, true))) {
            writer.write(item.getId() + ";" + item.getName() + ";" + item.getCategory() + ";" + item.getPrice() + ";" + item.getImageUrl());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateItem(FoodItem updatedItem) {
        List<FoodItem> items = getAllItems();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ITEMS_FILE))) {
            for (FoodItem item : items) {
                if (item.getId().equals(updatedItem.getId())) {
                    writer.write(updatedItem.getId() + ";" + updatedItem.getName() + ";" + updatedItem.getCategory() + ";" + updatedItem.getPrice() + ";" + updatedItem.getImageUrl());
                } else {
                    writer.write(item.getId() + ";" + item.getName() + ";" + item.getCategory() + ";" + item.getPrice() + ";" + item.getImageUrl());
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteItem(String id) {
        List<FoodItem> items = getAllItems();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ITEMS_FILE))) {
            for (FoodItem item : items) {
                if (!item.getId().equals(id)) {
                    writer.write(item.getId() + ";" + item.getName() + ";" + item.getCategory() + ";" + item.getPrice() + ";" + item.getImageUrl());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Orders with Timestamp Logic
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        File file = new File(ORDERS_FILE);
        if (!file.exists()) return orders;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";");
                if (parts.length >= 7) {
                    long ts = System.currentTimeMillis();
                    if (parts.length >= 8) {
                        try { ts = Long.parseLong(parts[7].trim()); } catch (Exception e) {}
                    }
                    orders.add(new Order(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim(), parts[4].trim(), Double.parseDouble(parts[5].trim()), parts[6].trim(), ts));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public void saveOrder(Order order) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDERS_FILE, true))) {
            writer.write(order.getOrderId() + ";" + order.getCustomerName() + ";" + order.getAddress() + ";" + order.getPhone() + ";" + order.getItemName() + ";" + order.getTotalPrice() + ";" + order.getPaymentMethod() + ";" + order.getTimestamp());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateOrder(Order updatedOrder) {
        List<Order> orders = getAllOrders();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDERS_FILE))) {
            for (Order o : orders) {
                if (o.getOrderId().equals(updatedOrder.getOrderId())) {
                    writer.write(updatedOrder.getOrderId() + ";" + updatedOrder.getCustomerName() + ";" + updatedOrder.getAddress() + ";" + updatedOrder.getPhone() + ";" + updatedOrder.getItemName() + ";" + updatedOrder.getTotalPrice() + ";" + updatedOrder.getPaymentMethod() + ";" + updatedOrder.getTimestamp());
                } else {
                    writer.write(o.getOrderId() + ";" + o.getCustomerName() + ";" + o.getAddress() + ";" + o.getPhone() + ";" + o.getItemName() + ";" + o.getTotalPrice() + ";" + o.getPaymentMethod() + ";" + o.getTimestamp());
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(String orderId) {
        List<Order> orders = getAllOrders();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDERS_FILE))) {
            for (Order o : orders) {
                if (!o.getOrderId().equals(orderId)) {
                    writer.write(o.getOrderId() + ";" + o.getCustomerName() + ";" + o.getAddress() + ";" + o.getPhone() + ";" + o.getItemName() + ";" + o.getTotalPrice() + ";" + o.getPaymentMethod() + ";" + o.getTimestamp());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}