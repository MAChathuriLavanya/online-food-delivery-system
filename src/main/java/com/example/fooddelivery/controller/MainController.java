package com.example.fooddelivery.controller;

import com.example.fooddelivery.model.Admin;
import com.example.fooddelivery.model.Customer;
import com.example.fooddelivery.model.FoodItem;
import com.example.fooddelivery.model.Order;
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

    private final String PERMANENT_ADMIN_USER = "admin";
    private final String PERMANENT_ADMIN_PASS = "admin123";

    public MainController(FileStorageService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/")
    public String homePage() {
        return "register";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        if (PERMANENT_ADMIN_USER.equals(username) && PERMANENT_ADMIN_PASS.equals(password)) {
            return "redirect:/admin";
        }

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

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        String id = UUID.randomUUID().toString().substring(0, 5);
        Customer newCustomer = new Customer(id, username, password);
        fileService.saveUser(newCustomer);
        return "redirect:/login";
    }

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("items", fileService.getAllItems());
        model.addAttribute("users", fileService.getAllUsers());
        model.addAttribute("orders", fileService.getAllOrders());
        return "admin-dashboard";
    }

    @PostMapping("/admin/add-item")
    public String addItem(@RequestParam String name,
                          @RequestParam String category,
                          @RequestParam double price,
                          @RequestParam(defaultValue = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=500") String imageUrl) {
        if (price <= 0) return "redirect:/admin?error=invalid_price";
        String id = UUID.randomUUID().toString().substring(0, 5);
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=500";
        }
        FoodItem item = new FoodItem(id, name, category, price, imageUrl);
        fileService.saveItem(item);
        return "redirect:/admin";
    }

    @PostMapping("/admin/update-item")
    public String updateItem(@RequestParam String id,
                             @RequestParam String name,
                             @RequestParam String category,
                             @RequestParam double price,
                             @RequestParam(defaultValue = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=500") String imageUrl) {
        if (price <= 0) return "redirect:/admin?error=invalid_price";
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=500";
        }
        FoodItem item = new FoodItem(id, name, category, price, imageUrl);
        fileService.updateItem(item);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete-item/{id}")
    public String deleteItem(@PathVariable String id) {
        fileService.deleteItem(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete-user/{id}")
    public String deleteUser(@PathVariable String id) {
        fileService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete-order/{id}")
    public String deleteOrder(@PathVariable String id) {
        fileService.deleteOrder(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/update-order")
    public String updateOrder(@RequestParam String orderId,
                              @RequestParam String customerName,
                              @RequestParam String address,
                              @RequestParam String phone,
                              @RequestParam String itemName,
                              @RequestParam double totalPrice,
                              @RequestParam String paymentMethod) {

        List<Order> orders = fileService.getAllOrders();
        long originalTs = System.currentTimeMillis();
        for (Order o : orders) {
            if (o.getOrderId().equals(orderId)) {
                originalTs = o.getTimestamp();
                break;
            }
        }
        Order updatedOrder = new Order(orderId, customerName, address, phone, itemName, totalPrice, paymentMethod, originalTs);
        fileService.updateOrder(updatedOrder);
        return "redirect:/admin";
    }

    @PostMapping("/admin/add-admin")
    public String addAdmin(@RequestParam String username, @RequestParam String password) {
        String id = UUID.randomUUID().toString().substring(0, 5);
        Admin newAdmin = new Admin(id, username, password);
        fileService.saveUser(newAdmin);
        return "redirect:/admin";
    }

    @GetMapping("/menu")
    public String customerMenu(Model model) {
        model.addAttribute("items", fileService.getAllItems());
        return "menu";
    }

    @GetMapping("/my-orders")
    public String myOrders(Model model) {
        model.addAttribute("orders", fileService.getAllOrders());
        model.addAttribute("allItems", fileService.getAllItems()); // Customer ට Items Select කිරීමට
        return "my-orders";
    }

    @PostMapping("/order/checkout")
    public String checkoutOrder(@RequestParam String itemName,
                                @RequestParam double price,
                                @RequestParam String customerName,
                                @RequestParam String address,
                                @RequestParam String phone,
                                @RequestParam String paymentMethod) {

        String orderId = UUID.randomUUID().toString().substring(0, 5);
        long currentTime = System.currentTimeMillis();
        Order newOrder = new Order(orderId, customerName, address, phone, itemName, price, paymentMethod, currentTime);
        fileService.saveOrder(newOrder);

        return "redirect:/my-orders";
    }

    @PostMapping("/order/customer-update")
    public String customerUpdateOrder(@RequestParam String orderId,
                                      @RequestParam String customerName,
                                      @RequestParam String address,
                                      @RequestParam String phone,
                                      @RequestParam String paymentMethod,
                                      @RequestParam String itemName,
                                      @RequestParam double totalPrice) {
        List<Order> orders = fileService.getAllOrders();
        for (Order o : orders) {
            if (o.getOrderId().equals(orderId)) {
                if (System.currentTimeMillis() - o.getTimestamp() <= 300000) {
                    o.setCustomerName(customerName);
                    o.setAddress(address);
                    o.setPhone(phone);
                    o.setPaymentMethod(paymentMethod);
                    o.setItemName(itemName);
                    o.setTotalPrice(totalPrice);
                    fileService.updateOrder(o);
                } else {
                    return "redirect:/my-orders?error=expired";
                }
                break;
            }
        }
        return "redirect:/my-orders?updated=true";
    }

    @GetMapping("/order/delete/{id}")
    public String deleteMyOrder(@PathVariable String id) {
        List<Order> orders = fileService.getAllOrders();
        for (Order o : orders) {
            if (o.getOrderId().equals(id)) {
                if (System.currentTimeMillis() - o.getTimestamp() <= 300000) {
                    fileService.deleteOrder(id);
                } else {
                    return "redirect:/my-orders?error=expired";
                }
                break;
            }
        }
        return "redirect:/my-orders";
    }
}