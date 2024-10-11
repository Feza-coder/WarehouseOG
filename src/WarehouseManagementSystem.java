import java.util.*;

public class WarehouseManagementSystem {

    static class Product {
        static int idCounter = 1; // Unique identifier of the product
        public int quantity; // Added quantity field
        int id;
        String name;
        int sellingPrice;
        String manufacturer;
        int expiringYear;
        String model;
        String productType;
        int purchasingPrice;

        public Product(String productType, String name, String model, int sellingPrice, int expiringYear, int purchasingPrice, String manufacturer, int quantity) {
            this.id = idCounter++; // Automatically assign a unique ID
            this.expiringYear = expiringYear;
            this.manufacturer = manufacturer;
            this.model = model;
            this.name = name;
            this.productType = productType;
            this.purchasingPrice = purchasingPrice;
            this.sellingPrice = sellingPrice;
            this.quantity = quantity; // Initialize quantity
        }

        public void displayProductDetails() {
            System.out.println("Product ID: " + id);
            System.out.println("Product Type: " + productType);
            System.out.println("Name: " + name);
            System.out.println("Model: " + model);
            System.out.println("Selling Price: " + sellingPrice);
            System.out.println("Expiring Year: " + expiringYear);
            System.out.println("Purchasing Price: " + purchasingPrice);
            System.out.println("Manufacturer: " + manufacturer);
            System.out.println("Quantity: " + quantity); // Display quantity
        }

        public void reduceQuantity(int amount) {
            if (amount <= quantity) {
                quantity -= amount;
            } else {
                System.out.println("Not enough stock for " + name);
            }
        }

        public void increaseQuantity(int amount) {
            quantity += amount;
        }
    }

    static class Order {
        Date date;
        List<Product> products;
        double totalCost;

        public Order(List<Product> products, double totalCost) {
            this.date = new Date(); // Set the order date to the current date
            this.products = new ArrayList<>(products); // Make a copy of the product list
            this.totalCost = totalCost;
        }

        public void displayOrderDetails() {
            System.out.println("Order Date: " + date);
            System.out.println("Products Purchased:");
            for (Product product : products) {
                product.displayProductDetails();
            }
            System.out.println("Total Cost: " + totalCost);
            System.out.println("-------------------------");
        }
    }

    static class User {
        String username;
        String password;
        String type;
        List<Order> orderHistory;
        double balance; // New field for user balance

        public User(String username, String password, String type) {
            this.username = username;
            this.password = password;
            this.type = type;
            this.orderHistory = new ArrayList<>();
            this.balance = 0.0; // Initialize balance to zero
        }

        public void addOrder(Order order) {
            orderHistory.add(order);
        }

        public void addBalance(double amount) {
            if (amount > 0) {
                balance += amount; // Increase balance
                System.out.println("Balance added successfully. New balance: " + balance);
            } else {
                System.out.println("Amount must be positive.");
            }
        }

        public void viewBalance() {
            System.out.println("Current balance: " + balance);
        }
        private static void addBalance() {
            System.out.print("Enter the amount to add to your balance: ");
            double amount = scanner.nextDouble();
            loggedInUser.addBalance(amount); // Call the method in User class
        }


        public void viewOrderHistory() {
            if (orderHistory.isEmpty()) {
                System.out.println("No order history found.");
                return;
            }
            for (Order order : orderHistory) {
                order.displayOrderDetails();
            }
        }
    }


    static List<Product> products = new ArrayList<>();
    static List<Product> cart = new ArrayList<>();
    static List<User> users = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static double balance = 25000.0;
    static double totalMoneyMade = 0.0;
    static User loggedInUser = null;
    public static void viewItems() {
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        for (Product product : products) {
            product.displayProductDetails();
            System.out.println("-------------------------");
        }
    }

    public static void initializeProducts() {
        products.add(new Product("Notebook", "Dell Inspiron", "Inspiron 15", 700, 2026, 500, "Dell", 10));
        products.add(new Product("Smartphone", "Samsung Galaxy S21", "Galaxy S21", 1200, 2025, 800, "Samsung", 15));
        products.add(new Product("Tablet", "Apple iPad", "iPad Pro", 1000, 2024, 700, "Apple", 8));
        products.add(new Product("Tablet", "XMM", "New Model", 2000, 2028, 1800, "Notepors", 5));
    }

    public static void initializeDemoUsers() {
        users.add(new User("Feza", "Melissa", "Manager"));
        users.add(new User("Mundes", "Daniel", "User"));
        users.add(new User("Chuol", "Jack", "User"));
    }

    public void login() {
        System.out.println("Enter your username:");
        String enteredUsername = scanner.nextLine();
        System.out.println("Enter your password:");
        String enteredPassword = scanner.nextLine();

        for (User user : users) {
            if (user.username.equals(enteredUsername) && user.password.equals(enteredPassword)) {
                System.out.println("Login successful! Welcome, " + user.username + " (" + user.type + ")");
                loggedInUser = user; // Set the logged-in user
                if (user.type.equalsIgnoreCase("Manager")) {
                    managementMenu(scanner);
                } else {
                    userMenu(scanner);
                }
                return;
            }
        }
        System.out.println("Invalid username or password. Please try again.");
    }

    public static void registerUser() {
        System.out.println("Enter username for registration:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        System.out.println("Enter user type (Manager/User):");
        String type = scanner.nextLine();

        if (!type.equalsIgnoreCase("Manager") && !type.equalsIgnoreCase("User")) {
            System.out.println("Invalid user type. Registration failed.");
            return;
        }

        users.add(new User(username, password, type));
        System.out.println("Registration successful. You can now login.");
    }

    public static void updateProduct() {
        System.out.println("Enter the product ID to update:");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Product productToUpdate = null;
        for (Product product : products) {
            if (product.id == productId) {
                productToUpdate = product;
                break;
            }
        }

        if (productToUpdate == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.println("Product found. Enter the field to update (name, sellingPrice, manufacturer, expiringYear, model, productType, purchasingPrice, quantity):");
        String fieldToUpdate = scanner.nextLine();

        switch (fieldToUpdate.toLowerCase()) {
            case "name":
                System.out.println("Enter new name:");
                productToUpdate.name = scanner.nextLine();
                break;
            case "sellingprice":
                System.out.println("Enter new selling price:");
                productToUpdate.sellingPrice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                break;
            case "manufacturer":
                System.out.println("Enter new manufacturer:");
                productToUpdate.manufacturer = scanner.nextLine();
                break;
            case "expiringyear":
                System.out.println("Enter new expiring year:");
                productToUpdate.expiringYear = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                break;
            case "model":
                System.out.println("Enter new model:");
                productToUpdate.model = scanner.nextLine();
                break;
            case "producttype":
                System.out.println("Enter new product type:");
                productToUpdate.productType = scanner.nextLine();
                break;
            case "purchasingprice":
                System.out.println("Enter new purchasing price:");
                productToUpdate.purchasingPrice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                break;
            case "quantity":
                System.out.println("Enter new quantity:");
                productToUpdate.quantity = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                break;
            default:
                System.out.println("Invalid field.");
                return;
        }
        System.out.println("Product updated successfully.");
    }

    public static void main(String[] args) {
        initializeDemoUsers();
        initializeProducts();
        WarehouseManagementSystem system = new WarehouseManagementSystem();

        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Please choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    system.login();
                    break;
                case 2:
                    registerUser();
                    break;
                case 3:
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void userMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. View Products");
            System.out.println("2. Add Item to Cart");
            System.out.println("3. Remove Item from Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Purchase Items");
            System.out.println("6. View Order History");
            System.out.println("7. Add Balance");
            System.out.println("8. View Balance");
            System.out.println("9. Logout");
            System.out.print("Please choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewItems();
                    break;
                case 2:
                    addItemToCart();
                    break;
                case 3:
                    removeItemFromCart();
                    break;
                case 4:
                    viewCart();
                    break;
                case 5:
                    purchaseItems();
                    break;
                case 6:
                    loggedInUser.viewOrderHistory();
                    break;
                case 7:
                    User.addBalance();
                    break;
                case 8:
                    loggedInUser.viewBalance();
                    break;
                case 9:
                    System.out.println("Logging out...");
                    exit = true; // Set exit to true to exit the loop
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    private static void managementMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- Management Menu ---");
            System.out.println("1. View Items");
            System.out.println("2. Add Item to Cart");
            System.out.println("3. Remove Item from Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Purchase Items");
            System.out.println("6. Add New Product");
            System.out.println("7. Update Product");
            System.out.println("8. View Order History");
            System.out.println("9. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    displayProducts();
                    break;
                case 2:
                    addItemToCart();
                    break;
                case 3:
                    removeItemFromCart();
                    break;
                case 4:
                    viewCart();
                    break;
                case 5:
                    purchaseItems();
                    break;
                case 6:
                    addNewProduct();
                    break;
                case 7:
                    updateProduct();
                    break;
                case 8:
                    if (loggedInUser != null) {
                        loggedInUser.viewOrderHistory();
                    } else {
                        System.out.println("You need to be logged in to view your order history.");
                    }
                    break;
                case 9:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void displayProducts() {
        for (Product product : products) {
            product.displayProductDetails();
        }
    }

    public static void addItemToCart() {
        System.out.println("Enter the product ID to add to the cart:");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Product productToAdd = null;
        for (Product product : products) {
            if (product.id == productId) {
                productToAdd = product;
                break;
            }
        }

        if (productToAdd == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.println("Enter the quantity to add to the cart:");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (quantity <= productToAdd.quantity) {
            for (int i = 0; i < quantity; i++) {
                cart.add(productToAdd);
            }
            System.out.println("Added " + quantity + " units of " + productToAdd.name + " to the cart.");
        } else {
            System.out.println("Not enough stock available.");
        }
    }

    public static void removeItemFromCart() {
        System.out.println("Enter the product ID to remove from the cart:");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Product productToRemove = null;
        for (Product product : cart) {
            if (product.id == productId) {
                productToRemove = product;
                break;
            }
        }

        if (productToRemove != null) {
            cart.remove(productToRemove);
            System.out.println("Removed " + productToRemove.name + " from the cart.");
        } else {
            System.out.println("Product not found in the cart.");
        }
    }

    public static void viewCart() {
        System.out.println("Current Cart:");
        for (Product product : cart) {
            product.displayProductDetails();
        }
    }

    public static void purchaseItems() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        double totalCost = 0;
        for (Product product : cart) {
            totalCost += product.sellingPrice;
        }

        if (balance >= totalCost) {
            balance -= totalCost;
            totalMoneyMade += totalCost;

            for (Product product : cart) {
                product.reduceQuantity(1);
            }

            System.out.println("Purchase successful! Total Cost: $" + totalCost);
            System.out.println("Remaining balance: $" + balance);

            // Save order to the user's history
            if (loggedInUser != null) {
                loggedInUser.addOrder(new Order(cart, totalCost));
            }

            // Clear the cart after purchase
            cart.clear();
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public static void addNewProduct() {
        System.out.println("Enter product type:");
        String productType = scanner.nextLine();

        System.out.println("Enter product name:");
        String name = scanner.nextLine();

        System.out.println("Enter product model:");
        String model = scanner.nextLine();

        System.out.println("Enter selling price:");
        int sellingPrice = scanner.nextInt();

        System.out.println("Enter expiring year:");
        int expiringYear = scanner.nextInt();

        System.out.println("Enter purchasing price:");
        int purchasingPrice = scanner.nextInt();

        scanner.nextLine(); // Consume newline
        System.out.println("Enter manufacturer:");
        String manufacturer = scanner.nextLine();

        System.out.println("Enter quantity:");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        products.add(new Product(productType, name, model, sellingPrice, expiringYear, purchasingPrice, manufacturer, quantity));
        System.out.println("Product added successfully.");
    }
}

//I need to add to add a functionality where each user has her own balance and she can be able to check for balance and also to add on balance in case she wants to add.