package com.bus.main;

import com.bus.model.Bus;
import com.bus.model.Booking;
import com.bus.model.Passenger;
import com.bus.service.BusService;
import com.bus.service.ReportService;

import java.util.List;
import java.util.Scanner;

/**
 * BusApp - Main Application Class
 * Entry point for the Bus Reservation System
 */
public class BusApp {
    private static BusService busService;
    private static ReportService reportService;
    private static Scanner scanner;

    public static void main(String[] args) {
        busService = new BusService();
        reportService = new ReportService();
        scanner = new Scanner(System.in);

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║   WELCOME TO BUS RESERVATION SYSTEM       ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        boolean running = true;
        while (running) {
            try {
                displayMenu();
                int choice = getIntInput("Enter your choice: ");

                switch (choice) {
                    case 1:
                        viewAllBuses();
                        break;
                    case 2:
                        searchBuses();
                        break;
                    case 3:
                        bookTicket();
                        break;
                    case 4:
                        cancelTicket();
                        break;
                    case 5:
                        viewBooking();
                        break;
                    case 6:
                        viewAllBookings();
                        break;
                    case 7:
                        viewRevenueReport();
                        break;
                    case 8:
                        running = false;
                        System.out.println("\nThank you for using Bus Reservation System!");
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n┌─────────────────────────────────────────┐");
        System.out.println("│              MAIN MENU                  │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("│  1. View All Buses                      │");
        System.out.println("│  2. Search Buses                        │");
        System.out.println("│  3. Book Ticket                         │");
        System.out.println("│  4. Cancel Ticket                       │");
        System.out.println("│  5. View Booking Details                │");
        System.out.println("│  6. View All Bookings                   │");
        System.out.println("│  7. View Revenue Report                 │");
        System.out.println("│  8. Exit                                │");
        System.out.println("└─────────────────────────────────────────┘");
    }

    private static void viewAllBuses() {
        List<Bus> buses = busService.getAllBuses();
        reportService.displayAvailableBuses(buses);
    }

    private static void searchBuses() {
        System.out.print("\nEnter source city: ");
        String source = scanner.nextLine().trim();
        System.out.print("Enter destination city: ");
        String destination = scanner.nextLine().trim();

        List<Bus> buses = busService.searchBuses(source, destination);
        if (buses.isEmpty()) {
            System.out.println("\nNo buses found for this route.");
        } else {
            reportService.displayAvailableBuses(buses);
        }
    }

    private static void bookTicket() {
        try {
            System.out.println("\n┌─────────────────────────────────────────┐");
            System.out.println("│          BOOK A TICKET                  │");
            System.out.println("└─────────────────────────────────────────┘");

            // Get passenger details
            System.out.print("Enter passenger name: ");
            String name = scanner.nextLine().trim();

            int age = getIntInput("Enter age: ");

            System.out.print("Enter gender (M/F/Other): ");
            String gender = scanner.nextLine().trim();

            System.out.print("Enter phone number: ");
            String phone = scanner.nextLine().trim();

            System.out.print("Enter email (optional): ");
            String email = scanner.nextLine().trim();

            // Show available buses
            viewAllBuses();

            // Get bus selection
            System.out.print("Enter bus number: ");
            String busNo = scanner.nextLine().trim().toUpperCase();

            // Create passenger
            Passenger passenger = new Passenger(name, age, gender, phone, email);

            // Book the ticket
            Booking booking = busService.bookSeat(busNo, passenger);
            Bus bus = busService.getBusByNumber(busNo);

            System.out.println("\n✓ Booking successful!");
            reportService.printTicket(booking, bus);

        } catch (Exception e) {
            System.out.println("\n✗ Booking failed: " + e.getMessage());
        }
    }

    private static void cancelTicket() {
        try {
            System.out.print("\nEnter booking ID to cancel: ");
            String bookingId = scanner.nextLine().trim();

            // Get booking details before cancellation
            Booking booking = busService.getBookingById(bookingId);

            if (booking.getStatus().equals("CANCELLED")) {
                System.out.println("\n✗ This booking is already cancelled.");
                return;
            }

            System.out.print("Are you sure you want to cancel? (Y/N): ");
            String confirm = scanner.nextLine().trim();

            if (confirm.equalsIgnoreCase("Y")) {
                busService.cancelBooking(bookingId);
                System.out.println("\n✓ Booking cancelled successfully!");
                System.out.println("Refund amount: ₹" + String.format("%.2f", booking.getFare()));
            } else {
                System.out.println("\nCancellation aborted.");
            }

        } catch (Exception e) {
            System.out.println("\n✗ Cancellation failed: " + e.getMessage());
        }
    }

    private static void viewBooking() {
        try {
            System.out.print("\nEnter booking ID: ");
            String bookingId = scanner.nextLine().trim();

            Booking booking = busService.getBookingById(bookingId);
            Bus bus = busService.getBusByNumber(booking.getBusNo());

            reportService.printTicket(booking, bus);

        } catch (Exception e) {
            System.out.println("\n✗ " + e.getMessage());
        }
    }

    private static void viewAllBookings() {
        List<Booking> bookings = busService.getAllBookings();
        reportService.displayBookings(bookings);
    }

    private static void viewRevenueReport() {
        List<Booking> bookings = busService.getAllBookings();
        reportService.displayRevenueReport(bookings);
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }
}