package com.example.fooddelivery.controller;

import com.example.fooddelivery.model.FoodItem;
import com.example.fooddelivery.model.Order;
import com.example.fooddelivery.model.User;
import com.example.fooddelivery.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    private FileStorageService fileStorageService;

    // 0. Root URL (localhost:8080) එකට ආවම auto /menu එකට Redirect කරයි
    @GetMapping("/")
    public String home() {
        return "redirect:/menu";
    }

    // 1. Menu Page
    @GetMapping("/menu")
    public String showMenu(Model model) {
        List<FoodItem> items = fileStorageService.getAllFoodItems();
        model.addAttribute("items", items);
        return "menu";
    }

    // 2. Admin Dashboard Page
    @GetMapping("/admin")
    public String showAdminDashboard(Model model) {
        List<FoodItem> items = fileStorageService.getAllFoodItems();
        List<Order> orders = fileStorageService.getAllOrders();
        List<User> users = fileStorageService.getAllUsers();

        model.addAttribute("items", items);
        model.addAttribute("orders", orders);
        model.addAttribute("users", users);
        return "admin-dashboard";
    }

    // 3. Add Food Item Logic (Original Random UUID Code)
    @PostMapping("/admin/add-item")
    public String addFoodItem(@RequestParam("name") String name,
                              @RequestParam("category") String category,
                              @RequestParam("price") double price,
                              @RequestParam("imageUrl") String imageUrl) {

        List<FoodItem> items = fileStorageService.getAllFoodItems();

        String newId = UUID.randomUUID().toString().substring(0, 5);

        FoodItem newItem = new FoodItem(newId, name, category, price, imageUrl);
        items.add(newItem);
        fileStorageService.saveAllFoodItems(items);

        return "redirect:/admin";
    }

    // 4. Delete Food Item Logic
    @PostMapping("/admin/delete-item")
    public String deleteFoodItem(@RequestParam("id") String id) {
        List<FoodItem> items = fileStorageService.getAllFoodItems();
        items.removeIf(item -> item.getId().equals(id));
        fileStorageService.saveAllFoodItems(items);
        return "redirect:/admin";
    }

    // 5. My Orders Page
    @GetMapping("/my-orders")
    public String showMyOrders(Model model) {
        List<Order> orders = fileStorageService.getAllOrders();
        model.addAttribute("orders", orders);
        return "my-orders";
    }

    // 6. Login Page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // 7. Register Page
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }
}