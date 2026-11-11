# 🍴 Online Food Delivery System

A dynamic, multi-tier **Spring Boot** web application designed for managing food ordering operations. Built using Java 17+, Spring Boot (MVC Architecture), Thymeleaf, and Bootstrap 5, this system features dynamic customer ordering with active order timers, an admin dashboard, and a custom file-based persistence engine.

---

## 🌟 Key Features

### 🍔 Customer Features
* **Interactive Menu & Category Filtering:** Browse food items categorized dynamically under **Fast Food**, **Beverages**, **Desserts**, and **Snacks**.
* **Multi-Item Order Basket:** Select items, specify quantities, and place multi-item orders seamlessly.
* **Live Order Timer & Status Tracking:** Real-time order listing showing calculated total amounts, timestamps, and live statuses (*Pending*, *Delivered*, *Cancelled*).
* **Order Cancellation:** Customers can cancel pending orders within the allowed time window.
* **User Authentication:** Self-registration and login with real-time user ID validation.

### 🛡️ Admin Features
* **Management Dashboard:** Integrated views to monitor system users, active food inventory, and customer orders.
* **Full Inventory CRUD:** Add, update (via Bootstrap Modal interface), and delete food items with custom image URL support.
* **Order Processing:** Update order fulfillment statuses in real time.
* **Hardcoded Admin Access:** Pre-configured secure admin authorization for platform maintenance.

---

## 🧬 Object-Oriented Programming (OOP) Architecture

This application strictly implements core Object-Oriented Principles:

1. **Inheritance (`extends`):**
    * Base class `User.java` encapsulates shared credentials (`id`, `username`, `password`, `role`).
    * `Customer.java` and `Admin.java` extend `User.java` to inherit common attributes while adding specialized functionality without code duplication.

2. **Encapsulation:**
    * Domain entities (`FoodItem`, `Order`, `User`) declare `private` attributes accessed strictly via `public` getters and setters to enforce data protection.

3. **Polymorphism (Method Overriding):**
    * The `getRoleDetails()` method defined in `User.java` is overridden by subclasses (`Customer` / `Admin`) to execute dynamic role-specific behavior.
    * Generic type references (`List<FoodItem>`, `User session objects`) manage polymorphic collections cleanly.

4. **Abstraction:**
    * Low-level file stream processing (`BufferedReader`, `BufferedWriter`, Exception handling) is fully abstracted within `FileStorageService.java`, presenting clean methods to `MainController.java`.

---

## 🛠️ Tech Stack & Technologies

* **Backend:** Java 17+, Spring Boot 3.x (MVC Architecture)
* **Frontend:** Thymeleaf Template Engine, HTML5, CSS3, JavaScript (ES6), Bootstrap 5
* **Data Persistence:** Custom File Storage Service (`items.txt`, `orders.txt`, `users.txt`)
* **Build Tool:** Apache Maven
* **IDE:** IntelliJ IDEA / VS Code

---

## 📂 Project Structure

```text
food delivery/
├── data/                                # File storage directory
│   ├── items.txt                        # Food inventory storage
│   ├── orders.txt                       # Customer orders record
│   └── users.txt                        # User accounts database
├── src/
│   ├── main/
│   │   ├── java/com/example/fooddelivery/
│   │   │   ├── controller/              # MainController.java
│   │   │   ├── model/                   # Admin, Customer, FoodItem, Order, User
│   │   │   ├── service/                 # FileStorageService.java
│   │   │   └── FoodDeliveryApplication.java
│   │   └── resources/
│   │       ├── templates/               # admin-dashboard, menu, my-orders, login, register
│   │       └── application.properties
└── pom.xml