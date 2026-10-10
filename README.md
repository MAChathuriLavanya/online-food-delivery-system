# 🍴 Online Food Delivery System (`OnlineFoodDeliverySystem`)

A dynamic Spring Boot web application for managing online food delivery operations. Built with Java, Spring Boot, Thymeleaf, and Bootstrap, featuring role-based dashboards, interactive food menus, real-time order timers, and file-based data storage.

---

## 🌟 Key Features

### 🍔 Customer Features
- **Interactive Food Menu**: Dynamic item list with category filtering (Fast Food, Italian, Snacks, Main Course, Beverages).
- **Quantity Controls & Selection**: Easy toggle switches and item-level quantity selectors for smooth cart calculation.
- **Order Modification & Live Timer**: 5-minute countdown timer on active orders allowing dynamic updates and order cancellation before locked confirmation.
- **Order Checkout**: Seamless checkout form capturing delivery address, phone, and payment preferences.

### 📈 Admin Features
- **Admin Dashboard**: Tabbed navigation structure for managing menu items, customer orders, and registered system users.
- **Item Management**: Add, update, and delete food items with custom or fallback dynamic image support.
- **Order & User Operations**: View all pending customer orders and manage platform user accounts.

---

## 🛠️ Tech Stack & Technologies

- **Backend**: Java 17+, Spring Boot (MVC Architecture)
- **Frontend**: Thymeleaf, HTML5, CSS3, JavaScript (ES6), Bootstrap 5
- **Data Persistence**: File Storage Service (`.txt` file storage)
- **Build Tool**: Maven
- **IDE**: IntelliJ IDEA / VS Code

---

## 📂 Project Structure

```text
food delivery/
├── data/                        # File storage (items.txt, orders.txt, users.txt)
├── src/
│   ├── main/
│   │   ├── java/com/example/fooddelivery/
│   │   │   ├── controller/      # MainController.java
│   │   │   ├── model/           # Admin, Customer, FoodItem, Order, User
│   │   │   └── service/         # FileStorageService
│   │   └── resources/
│   │       ├── templates/       # admin-dashboard, menu, my-orders, login, register
│   │       └── application.properties
└── pom.xml