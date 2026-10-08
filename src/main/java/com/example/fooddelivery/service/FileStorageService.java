package com.example.fooddelivery.service;

import com.example.fooddelivery.model.FoodItem;
import com.example.fooddelivery.model.User;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileStorageService {

    private final String USERS_FILE = "data/users.txt";
    private final String ITEMS_FILE = "data/items.txt";

    public FileStorageService() {
        File folder = new File("data");
        if (!folder.exists()) folder.mkdir();

        try {
            File uFile = new File(USERS_FILE);
            if (!uFile.exists()) {
                uFile.createNewFile();
                saveUser(new User("1", "admin", "admin123", "ADMIN"));
            }
            File iFile = new File(ITEMS_FILE);
            if (!iFile.exists()) iFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User u = User.fromFileString(line);
                if (u != null) users.add(u);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public synchronized void saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(user.toFileString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<FoodItem> getAllItems() {
        List<FoodItem> items = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ITEMS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                FoodItem item = FoodItem.fromFileString(line);
                if (item != null) items.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    public synchronized void saveItem(FoodItem item) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ITEMS_FILE, true))) {
            writer.write(item.toFileString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void deleteItem(String id) {
        List<FoodItem> items = getAllItems();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ITEMS_FILE, false))) {
            for (FoodItem item : items) {
                if (!item.getId().equals(id)) {
                    writer.write(item.toFileString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}