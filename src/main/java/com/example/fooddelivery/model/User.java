package com.example.fooddelivery.model;

public class User {
    private String id;
    private String username;
    private String password;
    private String role; // ADMIN or USER

    public User() {}

    public User(String id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String toFileString() {
        return id + "," + username + "," + password + "," + role;
    }

    public static User fromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 4) {
            return new User(parts[0], parts[1], parts[2], parts[3]);
        }
        return null;
    }
}