package com.example.fooddelivery.controller;

import com.example.fooddelivery.model.FoodItem;
import com.example.fooddelivery.model.User;
import com.example.fooddelivery.service.FileStorageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class MainController {

    private final FileStorageService fileService;

    public MainController(FileStorageService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        List<User> users = fileService.getAllUsers();
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                if ("ADMIN".equals(u.getRole())) {
                    return "redirect:/admin";
                } else {
                    return "redirect:/menu";
                }
            }
        }
        model.addAttribute("error", "Invalid Username or Password!");
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        String id = UUID.randomUUID().toString().substring(0, 5);
        User newUser = new User(id, username, password, "USER");
        fileService.saveUser(newUser);
        return "redirect:/";
    }

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("items", fileService.getAllItems());
        model.addAttribute("users", fileService.getAllUsers());
        return "admin-dashboard";
    }

    @PostMapping("/admin/add-item")
    public String addItem(@RequestParam String name, @RequestParam String category, @RequestParam double price) {
        String id = UUID.randomUUID().toString().substring(0, 5);
        FoodItem item = new FoodItem(id, name, category, price);
        fileService.saveItem(item);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete-item/{id}")
    public String deleteItem(@PathVariable String id) {
        fileService.deleteItem(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/add-admin")
    public String addAdmin(@RequestParam String username, @RequestParam String password) {
        String id = UUID.randomUUID().toString().substring(0, 5);
        User newAdmin = new User(id, username, password, "ADMIN");
        fileService.saveUser(newAdmin);
        return "redirect:/admin";
    }

    @GetMapping("/menu")
    public String customerMenu(Model model) {
        model.addAttribute("items", fileService.getAllItems());
        return "menu";
    }
}