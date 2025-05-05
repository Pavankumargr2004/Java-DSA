import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

// Car class (unchanged)
class Car {
    private String carId;
    private String model;
    private boolean isRented;

    public Car(String carId, String model, boolean isRented) {
        this.carId = carId;
        this.model = model;
        this.isRented = isRented;
    }

    public String getCarId() {
        return carId;
    }

    public String getModel() {
        return model;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    @Override
    public String toString() {
        return "Car ID: " + carId + ", Model: " + model + ", Rented: " + (isRented ? "Yes" : "No");
    }
}

// Customer class (unchanged)
class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Customer: " + name;
    }
}

// RentalRecord class (unchanged)
class RentalRecord {
    private String customerName;
    private String carId;
    private String rentalStartTime;
    private String rentalEndTime;

    public RentalRecord(String customerName, String carId, String rentalStartTime, String rentalEndTime) {
        this.customerName = customerName;
        this.carId = carId;
        this.rentalStartTime = rentalStartTime;
        this.rentalEndTime = rentalEndTime;
    }

    @Override
    public String toString() {
        return "Customer: " + customerName + ", Car ID: " + carId + ", Start: " + rentalStartTime +
               ", End: " + (rentalEndTime != null ? rentalEndTime : "Not returned");
    }
}

// CarRentalSystem class with improved error handling
class CarRentalSystem {
    private Connection conn;

    public CarRentalSystem() {
        initializeDatabase();
        if (conn == null) {
            throw new RuntimeException("Failed to initialize database connection. Cannot proceed.");
        }
    }

    // Initialize SQLite database and create tables
    private void initializeDatabase() {
        try {
            // Ensure SQLite JDBC driver is loaded
            Class.forName("org.sqlite.JDBC");
            System.out.println("SQLite JDBC Driver loaded successfully.");

            // Connect to SQLite database (creates a new database file if it doesn't exist)
            conn = DriverManager.getConnection("jdbc:sqlite:car_rental.db");
            System.out.println("Connected to SQLite database.");

            Statement stmt = conn.createStatement();

            // Create Cars table
            stmt.execute("CREATE TABLE IF NOT EXISTS cars (car_id TEXT PRIMARY KEY, model TEXT, is_rented BOOLEAN)");
            // Create Customers table
            stmt.execute("CREATE TABLE IF NOT EXISTS customers (name TEXT PRIMARY KEY)");
            // Create Rental Records table
            stmt.execute("CREATE TABLE IF NOT EXISTS rentals (customer_name TEXT, car_id TEXT, start_time TEXT, end_time TEXT, PRIMARY KEY (customer_name, car_id))");

            stmt.close();
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC Driver not found. Ensure the driver is in the classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Add a car to the database
    public void addCar(String carId, String model) {
        String sql = "INSERT INTO cars (car_id, model, is_rented) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, carId);
            pstmt.setString(2, model);
            pstmt.setBoolean(3, false);
            pstmt.executeUpdate();
            System.out.println("Car " + model + " added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding car: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Rent a car
    public void rentCar(String carId, String customerName) {
        // Check if car exists and is available
        Car car = findCar(carId);
        if (car == null) {
            System.out.println("Car with ID " + carId + " not found.");
            return;
        }
        if (car.isRented()) {
            System.out.println("Car " + car.getModel() + " is already rented.");
            return;
        }

        // Check if customer already has a rented car
        if (hasActiveRental(customerName)) {
            System.out.println("Customer " + customerName + " already has a rented car.");
            return;
        }

        // Add customer if not exists
        addCustomerIfNotExists(customerName);

        // Update car status
        updateCarStatus(carId, true);

        // Record rental with timestamp
        String startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String sql = "INSERT INTO rentals (customer_name, car_id, start_time, end_time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customerName);
            pstmt.setString(2, carId);
            pstmt.setString(3, startTime);
            pstmt.setString(4, null); // End time is null until returned
            pstmt.executeUpdate();
            System.out.println("Car " + car.getModel() + " rented to " + customerName + " at " + startTime);
        } catch (SQLException e) {
            System.err.println("Error renting car: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Return a car
    public void returnCar(String customerName) {
        // Check if customer has an active rental
        String sql = "SELECT car_id, start_time FROM rentals WHERE customer_name = ? AND end_time IS NULL";
        String carId = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customerName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                carId = rs.getString("car_id");
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Error checking rental: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        if (carId == null) {
            System.out.println("No active rental found for " + customerName);
            return;
        }

        // Update car status
        updateCarStatus(carId, false);

        // Update rental record with end time
        String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        sql = "UPDATE rentals SET end_time = ? WHERE customer_name = ? AND car_id = ? AND end_time IS NULL";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, endTime);
            pstmt.setString(2, customerName);
            pstmt.setString(3, carId);
            pstmt.executeUpdate();
            System.out.println("Car with ID " + carId + " returned by " + customerName + " at " + endTime);
        } catch (SQLException e) {
            System.err.println("Error returning car: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Display all available cars
    public void displayAvailableCars() {
        String sql = "SELECT * FROM cars WHERE is_rented = 0";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Available Cars:");
            boolean found = false;
            while (rs.next()) {
                Car car = new Car(rs.getString("car_id"), rs.getString("model"), rs.getBoolean("is_rented"));
                System.out.println(car);
                found = true;
            }
            if (!found) {
                System.out.println("No cars available.");
            }
        } catch (SQLException e) {
            System.err.println("Error displaying cars: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Display rental history
    public void displayRentalHistory() {
        String sql = "SELECT * FROM rentals";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Rental History:");
            boolean found = false;
            while (rs.next()) {
                RentalRecord record = new RentalRecord(
                        rs.getString("customer_name"),
                        rs.getString("car_id"),
                        rs.getString("start_time"),
                        rs.getString("end_time")
                );
                System.out.println(record);
                found = true;
            }
            if (!found) {
                System.out.println("No rental records found.");
            }
        } catch (SQLException e) {
            System.err.println("Error displaying rental history: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper method to find a car
    private Car findCar(String carId) {
        String sql = "SELECT * FROM cars WHERE car_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, carId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Car(rs.getString("car_id"), rs.getString("model"), rs.getBoolean("is_rented"));
            }
        } catch (SQLException e) {
            System.err.println("Error finding car: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Helper method to update car status
    private void updateCarStatus(String carId, boolean isRented) {
        String sql = "UPDATE cars SET is_rented = ? WHERE car_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, isRented);
            pstmt.setString(2, carId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating car status: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper method to check if customer has an active rental
    private boolean hasActiveRental(String customerName) {
        String sql = "SELECT * FROM rentals WHERE customer_name = ? AND end_time IS NULL";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customerName);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error checking active rental: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Helper method to add customer if not exists
    private void addCustomerIfNotExists(String name) {
        String sql = "INSERT OR IGNORE INTO customers (name) VALUES (?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding customer: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Close database connection
    public void close() {
        try {
            if (conn != null) conn.close();
            System.out.println("Database connection closed.");
        } catch (SQLException e) {
            System.err.println("Error closing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

// Main class (unchanged except for error handling)
public class Main {
    public static void main(String[] args) {
        CarRentalSystem rentalSystem;
        try {
            rentalSystem = new CarRentalSystem();
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);

        // Pre-populate some cars for testing
        rentalSystem.addCar("C001", "Toyota Camry");
        rentalSystem.addCar("C002", "Honda Civic");
        rentalSystem.addCar("C003", "Ford Mustang");

        // Interactive menu
        while (true) {
            System.out.println("\nCar Rental System Menu:");
            System.out.println("1. Display Available Cars");
            System.out.println("2. Rent a Car");
            System.out.println("3. Return a Car");
            System.out.println("4. Display Rental History");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    rentalSystem.displayAvailableCars();
                    break;
                case 2:
                    System.out.print("Enter Car ID to rent: ");
                    String carId = scanner.nextLine();
                    System.out.print("Enter your name: ");
                    String customerName = scanner.nextLine();
                    rentalSystem.rentCar(carId, customerName);
                    break;
                case 3:
                    System.out.print("Enter your name: ");
                    customerName = scanner.nextLine();
                    rentalSystem.returnCar(customerName);
                    break;
                case 4:
                    rentalSystem.displayRentalHistory();
                    break;
                case 5:
                    rentalSystem.close();
                    scanner.close();
                    System.out.println("Exiting system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}