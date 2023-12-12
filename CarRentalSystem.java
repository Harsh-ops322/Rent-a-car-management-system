import java.util.Scanner;

class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;
    private double rating;
    private String feedback;

    public Car(String carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
        this.rating = 0.0;
        this.feedback = "";
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

public class CarRentalSystem {
    private static final int MAX_CARS = 20; // Maximum number of cars
    private static final int MAX_CUSTOMERS = 20; // Maximum number of customers
    private static final int MAX_RENTALS = 20; // Maximum number of rentals

    private Car[] cars;
    private Customer[] customers;
    private Rental[] rentals;

    private int carCount;
    private int customerCount;
    private int rentalCount;

    public CarRentalSystem() {
        cars = new Car[MAX_CARS];
        customers = new Customer[MAX_CUSTOMERS];
        rentals = new Rental[MAX_RENTALS];
        carCount = 0;
        customerCount = 0;
        rentalCount = 0;
    }

    public void addCar(Car car) {
        if (carCount < MAX_CARS) {
            cars[carCount++] = car;
        } else {
            System.out.println("Cannot add more cars. Maximum limit reached.");
        }
    }

    public void addCustomer(Customer customer) {
        if (customerCount < MAX_CUSTOMERS) {
            customers[customerCount++] = customer;
        } else {
            System.out.println("Cannot add more customers. Maximum limit reached.");
        }
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable() && rentalCount < MAX_RENTALS) {
            car.rent();
            rentals[rentalCount++] = new Rental(car, customer, days);
            System.out.println("Car rented successfully.");
        } else {
            System.out.println("Car is not available for rent or maximum rental limit reached.");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null;
        for (int i = 0; i < rentalCount; i++) {
            if (rentals[i].getCar() == car) {
                rentalToRemove = rentals[i];
                break;
            }
        }
        if (rentalToRemove != null) {
            // Remove the rental entry
            for (int i = 0; i < rentalCount; i++) {
                if (rentals[i] == rentalToRemove) {
                    for (int j = i; j < rentalCount - 1; j++) {
                        rentals[j] = rentals[j + 1];
                    }
                    rentalCount--;
                    break;
                }
            }
            System.out.println("Car returned successfully.");

        } else {
            System.out.println("Car was not rented.");
        }
    }

    public void provideFeedback(Car car, double rating, String feedback) {
        car.setRating(rating);
        car.setFeedback(feedback);
        System.out.println("Thank you for your feedback!");
    }

    public void readFeedback(Car car) {
        System.out.println("\n== Feedback for " + car.getBrand() + " " + car.getModel() + " ==");
        System.out.println("Rating: " + car.getRating());
        System.out.println("Feedback: " + car.getFeedback());
    }

    public void displayCarAvailability() {
        System.out.println("\n== Car Availability ==\n");
        for (int i = 0; i < carCount; i++) {
            Car car = cars[i];
            System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel()
                    + " (" + (car.isAvailable() ? "Available" : "Rented") + ")");
        }
    }

    public void displayBasicStatistics() {
        int totalCars = carCount;
        int rentedCars = 0;
        for (int i = 0; i < rentalCount; i++) {
            if (!rentals[i].getCar().isAvailable()) {
                rentedCars++;
            }
        }
        double totalRevenue = 0.0;
        for (int i = 0; i < rentalCount; i++) {
            totalRevenue += rentals[i].getCar().calculatePrice(rentals[i].getDays());
        }

        System.out.println("\n== Basic Statistics ==\n");
        System.out.println("Total Cars: " + totalCars);
        System.out.println("Available Cars: " + (totalCars - rentedCars));
        System.out.println("Rented Cars: " + rentedCars);
        System.out.printf("Total Revenue: $%.2f%n", totalRevenue);
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("MADE BY  HARSH,APOORVA,MANAV");
            System.out.println("===== (Car  Rental System) =====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Provide Feedback");
            System.out.println("4. Read Feedback");
            System.out.println("5. Display Car Availability");
            System.out.println("6. Display Basic Statistics");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.println("\n== Rent a Car ==\n");

                // Input customer name
                System.out.print("Enter your name: ");
                String customerName = scanner.nextLine();

                // Display available cars
                System.out.println("\nAvailable Cars:");
                for (int i = 0; i < carCount; i++) {
                    Car car = cars[i];
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel());
                    }
                }

                // Input car ID and rental days
                System.out.print("\nEnter the car ID you want to rent: ");
                String carId = scanner.nextLine();
                System.out.print("Enter the number of days for rental: ");
                int rentalDays = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                // Create new customer
                Customer newCustomer = new Customer("CUS" + (customerCount + 1), customerName);
                addCustomer(newCustomer);

                // Find and rent the selected car
                Car selectedCar = null;
                for (int i = 0; i < carCount; i++) {
                    Car car = cars[i];
                    if (car.getCarId().equals(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);

                    // Confirm rental
                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                } else {
                    System.out.println("\nInvalid car selection or car not available for rent.");
                }

            } else if (choice == 2) {
                System.out.println("\n== Return a Car ==\n");
                System.out.print("Enter the car ID you want to return: ");
                String carId = scanner.nextLine();

                Car carToReturn = null;
                for (int i = 0; i < carCount; i++) {
                    if (cars[i].getCarId().equals(carId) && !cars[i].isAvailable()) {
                        carToReturn = cars[i];
                        break;
                    }
                }

                if (carToReturn != null) {
                    Customer customer = null;
                    for (int i = 0; i < rentalCount; i++) {
                        if (rentals[i].getCar() == carToReturn) {
                            customer = rentals[i].getCustomer();
                            break;
                        }
                    }

                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by " + customer.getName());
                    } else {
                        System.out.println("Car was not rented or rental information is missing.");
                    }
                } else {
                    System.out.println("Invalid car ID or car is not rented.");
                }

            } else if (choice == 3) {
                System.out.println("\n== Provide Feedback ==\n");
                System.out.print("Enter the car ID for feedback: ");
                String carIdForFeedback = scanner.nextLine();

                Car carForFeedback = null;
                for (int i = 0; i < carCount; i++) {
                    if (cars[i].getCarId().equals(carIdForFeedback)) {
                        carForFeedback = cars[i];
                        break;
                    }
                }

                if (carForFeedback != null) {
                    System.out.print("Enter your rating (0.0 to 5.0): ");
                    double rating = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline

                    System.out.print("Enter your feedback: ");
                    String feedback = scanner.nextLine();

                    provideFeedback(carForFeedback, rating, feedback);
                } else {
                    System.out.println("Invalid car ID. Unable to provide feedback.");
                }

            } else if (choice == 4) {
                System.out.println("\n== Read Feedback ==\n");
                System.out.print("Enter the car ID for feedback: ");
                String carIdForFeedback = scanner.nextLine();

                Car carForFeedback = null;
                for (int i = 0; i < carCount; i++) {
                    if (cars[i].getCarId().equals(carIdForFeedback)) {
                        carForFeedback = cars[i];
                        break;
                    }
                }

                if (carForFeedback != null) {
                    readFeedback(carForFeedback);
                } else {
                    System.out.println("Invalid car ID. Unable to read feedback.");
                }

            } else if (choice == 5) {
                displayCarAvailability();

            } else if (choice == 6) {
                displayBasicStatistics();

            } else if (choice == 7) {
                break;

            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        System.out.println("\nThank you for using the Car Rental System!");
    }

    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        // Adding initial cars
        Car car1 = new Car("C001", "Toyota", "Camry", 60.0);
        Car car2 = new Car("C002", "Honda", "Accord", 70.0);
        Car car3 = new Car("C003", "Mahindra", "Thar", 150.0);
        Car car4 = new Car("C004", "Mahindra", "xuv700", 150.0);

        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);
        rentalSystem.addCar(car4);

        rentalSystem.menu();
    }
}
 