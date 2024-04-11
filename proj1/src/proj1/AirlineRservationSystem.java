package proj1;

import java.util.HashMap;
import java.util.Scanner;

class User {
    String username;
    String mobileNumber;
    String password;
    Wallet wallet;

    public User(String username, String mobileNumber, String password) {
        this.username = username;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.wallet = new Wallet(10000);
    }
}

class Wallet {
    private double balance;

    public Wallet(double initialBalance) {
        this.balance = initialBalance;
    }

    public void updateBalance(double amount) {
        this.balance += amount;
    }

    public double getBalance() {
        return balance;
    }
}


class Flight {
    String flightId;
    String flightName;
    String source;
    String destination;
    String arrivalTime;
    String departureTime;
    String operationDate;

    public Flight(String flightId, String flightName, String source, String destination, String arrivalTime, String departureTime, String operationDate) {
        this.flightId = flightId;
        this.flightName = flightName;
        this.source = source;
        this.destination = destination;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.operationDate = operationDate;
    }
}

class Ticket {
    String ticketId;
    String flightId;
    String[] passengerNames;
    int[] passengerAges;
    String classType;
    int numPassengers;
    int totalAmount;

    public Ticket(String ticketId, String flightId, String[] passengerNames, int[] passengerAges, String classType, int numPassengers, int totalAmount) {
        this.ticketId = ticketId;
        this.flightId = flightId;
        this.passengerNames = passengerNames;
        this.passengerAges = passengerAges;
        this.classType = classType;
        this.numPassengers = numPassengers;
        this.totalAmount = totalAmount;
    }
}

public class AirlineRservationSystem {
    static Scanner scanner = new Scanner(System.in);
    static HashMap<String, User> users = new HashMap<>();
    static HashMap<String, Flight> flights = new HashMap<>();
    static HashMap<String, Ticket> tickets = new HashMap<>();

    public static void main(String[] args) {
        initializeFlights();
        boolean exit = false;

        while (!exit) {
            System.out.println("Welcome to the Airline Reservation System!");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                	registerUser();
				
                    break;
                case 2:
                    User loggedInUser = login();
                    if (loggedInUser != null) {
                        userMenu(loggedInUser);
                    }
                    break;
                case 3:
                    exit = true;
                    System.out.println("Thank you for using the Airline Reservation System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void initializeFlights() {
        flights.put("F001", new Flight("F001", "Flight 1", "New York     ", "Los Angeles", "10:00 AM", "1:30 PM", "2023-05-01"));
        flights.put("F002", new Flight("F002", "Flight 2", "Chicago      ", "Miami      ", "9:00 AM", "12:15 PM", "2023-05-02"));
        flights.put("F003", new Flight("F003", "Flight 3", "San Francisco", "Seattle    ", "11:30 AM", "2:00 PM", "2023-05-03"));
        flights.put("F004", new Flight("F004", "Flight 4", "India        ", "Pakistan   ", "9:00 AM", "12:15 PM", "2023-05-04"));
        
    }

    private static void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter mobile number: ");
        String mobileNumber = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Confirm password: ");
        String confirmPassword = scanner.nextLine();

        if (password.equals(confirmPassword)) {
            User user = new User(username, mobileNumber, password);
            users.put(username, user);
            System.out.println("Registration successful! You have been credited with 10000 rupees in your wallet.");
        } else {
            System.out.println("Password mismatch. Please try again.");
        }
    }

    private static User login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = users.get(username);
        if (user != null && user.password.equals(password)) {
            System.out.println("Login successful!");
            return user;
        } else {
            System.out.println("Invalid username or password. Please try again.");
            return null;
        }
    }

    private static void userMenu(User user) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\nUser Menu:");
            System.out.println("1. Book a flight");
            System.out.println("2. Cancel a ticket");
            System.out.println("3. View ticket");
            System.out.println("4. Upgrade your wallet");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    bookFlight(user);
                    break;
                case 2:
                    cancelTicket(user);
                    break;
                case 3:
                    viewTicket(user);
                    break;
                case 5:
                    exit = true;
                    System.out.println("Logout successful!");
                    break;
                case 4:
                	Upgrade(user);
                	break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    public static void Upgrade(User user) {
        System.out.println("Enter the amount to upgrade your wallet: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        if (amount > 0) {
            double currentBalance = user.wallet.getBalance();
            double updatedBalance = currentBalance + amount;
            user.wallet.updateBalance(amount);
            System.out.println("Your wallet balance has been updated.");
            System.out.println("Previous balance: " + currentBalance + " rupees");
            System.out.println("New balance: " + updatedBalance + " rupees");
        } else {
            System.out.println("Invalid amount. Please enter a positive value.");
        }
    }

    private static void bookFlight(User user) {
        displayFlights();
        System.out.print("Enter the flight ID to book: ");
        String flightId = scanner.nextLine().toUpperCase();

        if (flights.containsKey(flightId)) {
            System.out.print("Enter the class type (Economy or Business): ");
            String classType = scanner.nextLine().toLowerCase();
            System.out.print("Enter the number of passengers: ");
            int numPassengers = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            String[] passengerNames = new String[numPassengers];
            int[] passengerAges = new int[numPassengers];

            for (int i = 0; i < numPassengers; i++) {
                System.out.printf("Enter the name of passenger %d: ", i + 1);
                passengerNames[i] = scanner.nextLine();
                System.out.printf("Enter the age of passenger %d: ", i + 1);
                passengerAges[i] = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
            }

            int totalAmount = calculateTotalAmount(classType, numPassengers);
            System.out.println("Total amount to pay: " + totalAmount + " rupees");
            System.out.print("Confirm booking? (Y/N): ");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("Y")) {
                if (user.wallet.getBalance() >= totalAmount) {
                    String ticketId = generateTicketId();
                    Ticket ticket = new Ticket(ticketId, flightId, passengerNames, passengerAges, classType, numPassengers, totalAmount);
                    tickets.put(ticketId, ticket);
                    user.wallet.updateBalance(-totalAmount);
                    System.out.println("Ticket booked successfully! Ticket ID: " + ticketId);
                } else {
                    System.out.println("Insufficient wallet balance. Please recharge your wallet.");
                }
            } else {
                System.out.println("Booking canceled.");
            }
        } else {
            System.out.println("Invalid flight ID. Please try again.");
        }
    }

    private static void cancelTicket(User user) {
        System.out.print("Enter the ticket ID to cancel: ");
        String ticketId = scanner.nextLine();

        if (tickets.containsKey(ticketId)) {
            Ticket ticket = tickets.get(ticketId);
            user.wallet.updateBalance(ticket.totalAmount);
            tickets.remove(ticketId);
            System.out.println("Ticket canceled successfully! Amount refunded to your wallet.");
        } else {
            System.out.println("Invalid ticket ID. Please try again.");
        }
    }

    private static void viewTicket(User user) {
        System.out.print("Enter the ticket ID to view: ");
        String ticketId = scanner.nextLine();

        if (tickets.containsKey(ticketId)) {
            Ticket ticket = tickets.get(ticketId);
            Flight flight = flights.get(ticket.flightId);
            System.out.println("Ticket Details:");
            System.out.println("Ticket ID: " + ticket.ticketId);
            System.out.println("Flight ID: " + ticket.flightId);
            System.out.println("Flight Name: " + flight.flightName);
            System.out.println("Source: " + flight.source);
            System.out.println("Destination: " + flight.destination);
            System.out.println("Arrival Time: " + flight.arrivalTime);
            System.out.println("Departure Time: " + flight.departureTime);
            System.out.println("Operation Date: " + flight.operationDate);
            System.out.println("Class Type: " + ticket.classType);
            System.out.println("Number of Passengers: " + ticket.numPassengers);
            System.out.print("Passenger Names: ");
            for (String name : ticket.passengerNames) {
                System.out.print(name + ", ");
            }
            System.out.println("\nPassenger Ages: ");
            for (int age : ticket.passengerAges) {
                System.out.print(age + ", ");
            }
            System.out.println("\nTotal Amount: " + ticket.totalAmount + " rupees");
        } else {
            System.out.println("Invalid ticket ID. Please try again.");
        }
    }

    private static void displayFlights() {
        System.out.println("\nAvailable Flights:");
        printFlightTableHeader();
        for (Flight flight : flights.values()) {
            printFlightDetails(flight);
        }
    }

    private static void printFlightTableHeader() {
        System.out.println("┌───────────┬─────────────┬───────────────────┬─────────────────┬──────────────┬─────────────┬────────────────┐");
        System.out.println("│ Flight ID │ Flight Name │ Source            │ Destination     │ Arrival Time │DepartureTime│ Operation Date │");
        System.out.println("├───────────┼─────────────┼───────────────────┼─────────────────┼──────────────┼─────────────┼────────────────┤");
    }

    private static void printFlightDetails(Flight flight) {
        System.out.printf("│%-11s│%-13s│%-19s│%-17s│%-14s│%-13s│%-16s│%n", flight.flightId, flight.flightName, flight.source, flight.destination, flight.arrivalTime, flight.departureTime, flight.operationDate);
    }

    private static int calculateTotalAmount(String classType, int numPassengers) {
        int baseAmount = (classType.equals("economy")) ? 2000 : 6000;
        return baseAmount * numPassengers;
    }

    private static String generateTicketId() {
        return "TKT" + System.currentTimeMillis();
    }
}