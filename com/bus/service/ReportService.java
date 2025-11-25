package com.bus.service;

import com.bus.model.Bus;
import com.bus.model.Booking;

import java.util.List;

/**
 * ReportService Class
 * Generates various reports and prints ticket details
 */
public class ReportService {

    // Print ticket details
    public void printTicket(Booking booking, Bus bus) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                    BUS TICKET");
        System.out.println("=".repeat(60));
        System.out.println("  Booking ID    : " + booking.getBookingId());
        System.out.println("  Passenger Name: " + booking.getPassenger().getName());
        System.out.println("  Age           : " + booking.getPassenger().getAge());
        System.out.println("  Gender        : " + booking.getPassenger().getGender());
        System.out.println("  Phone         : " + booking.getPassenger().getPhoneNumber());
        System.out.println("-".repeat(60));
        System.out.println("  Bus Number    : " + bus.getBusNo());
        System.out.println("  Route         : " + bus.getSource() + " → " + bus.getDestination());
        System.out.println("  Seat Number   : " + booking.getSeatNo());
        System.out.println("  Fare          : ₹" + String.format("%.2f", booking.getFare()));
        System.out.println("  Booking Date  : " + booking.getBookingDate());
        System.out.println("  Status        : " + booking.getStatus());
        System.out.println("=".repeat(60));
        System.out.println("        Thank you for choosing our service!");
        System.out.println("=".repeat(60) + "\n");
    }

    // Display available buses
    public void displayAvailableBuses(List<Bus> buses) {
        if (buses.isEmpty()) {
            System.out.println("No buses available.");
            return;
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.println("                         AVAILABLE BUSES");
        System.out.println("=".repeat(80));
        System.out.printf("%-10s %-15s %-15s %-15s %-10s%n",
                "Bus No", "Source", "Destination", "Seats Available", "Fare");
        System.out.println("-".repeat(80));

        for (Bus bus : buses) {
            System.out.printf("%-10s %-15s %-15s %-15s ₹%-10.2f%n",
                    bus.getBusNo(),
                    bus.getSource(),
                    bus.getDestination(),
                    bus.getSeatsAvailable() + "/" + bus.getTotalSeats(),
                    bus.getFarePerSeat());
        }
        System.out.println("=".repeat(80) + "\n");
    }

    // Display all bookings
    public void displayBookings(List<Booking> bookings) {
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        System.out.println("\n" + "=".repeat(90));
        System.out.println("                              ALL BOOKINGS");
        System.out.println("=".repeat(90));
        System.out.printf("%-15s %-20s %-10s %-8s %-10s %-12s%n",
                "Booking ID", "Passenger", "Bus No", "Seat", "Fare", "Status");
        System.out.println("-".repeat(90));

        for (Booking booking : bookings) {
            System.out.printf("%-15s %-20s %-10s %-8d ₹%-9.2f %-12s%n",
                    booking.getBookingId(),
                    booking.getPassenger().getName(),
                    booking.getBusNo(),
                    booking.getSeatNo(),
                    booking.getFare(),
                    booking.getStatus());
        }
        System.out.println("=".repeat(90) + "\n");
    }

    // Display revenue report
    public void displayRevenueReport(List<Booking> bookings) {
        double totalRevenue = bookings.stream()
                .filter(b -> b.getStatus().equals("CONFIRMED"))
                .mapToDouble(Booking::getFare)
                .sum();

        long confirmedBookings = bookings.stream()
                .filter(b -> b.getStatus().equals("CONFIRMED"))
                .count();

        long cancelledBookings = bookings.stream()
                .filter(b -> b.getStatus().equals("CANCELLED"))
                .count();

        System.out.println("\n" + "=".repeat(50));
        System.out.println("              REVENUE REPORT");
        System.out.println("=".repeat(50));
        System.out.println("  Total Bookings     : " + bookings.size());
        System.out.println("  Confirmed Bookings : " + confirmedBookings);
        System.out.println("  Cancelled Bookings : " + cancelledBookings);
        System.out.println("  Total Revenue      : ₹" + String.format("%.2f", totalRevenue));
        System.out.println("=".repeat(50) + "\n");
    }
}