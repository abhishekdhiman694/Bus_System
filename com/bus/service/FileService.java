package com.bus.service;

import com.bus.model.Bus;
import com.bus.model.Booking;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FileService Class
 * Handles all file I/O operations for buses and bookings
 */
public class FileService {
    private static final String BUSES_FILE = "buses.csv";
    private static final String BOOKINGS_FILE = "bookings.csv";

    // Initialize CSV files if they don't exist
    public void initializeFiles() {
        try {
            File busesFile = new File(BUSES_FILE);
            if (!busesFile.exists()) {
                createBusesFile();
            }

            File bookingsFile = new File(BOOKINGS_FILE);
            if (!bookingsFile.exists()) {
                bookingsFile.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error initializing files: " + e.getMessage());
        }
    }

    // Create buses.csv with sample data
    private void createBusesFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BUSES_FILE))) {
            // Sample bus data
            writer.write("BUS101,Delhi,Mumbai,40,40,1200.00\n");
            writer.write("BUS102,Mumbai,Bangalore,35,35,1500.00\n");
            writer.write("BUS103,Delhi,Jaipur,30,30,800.00\n");
            writer.write("BUS104,Bangalore,Chennai,32,32,950.00\n");
            writer.write("BUS105,Mumbai,Pune,25,25,450.00\n");
        }
    }

    // Load all buses from CSV
    public List<Bus> loadBuses() throws IOException {
        List<Bus> buses = new ArrayList<>();
        File file = new File(BUSES_FILE);

        if (!file.exists()) {
            return buses;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(BUSES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    buses.add(Bus.fromCSV(line));
                }
            }
        }
        return buses;
    }

    // Save all buses to CSV
    public void saveBuses(List<Bus> buses) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BUSES_FILE))) {
            for (Bus bus : buses) {
                writer.write(bus.toCSV());
                writer.newLine();
            }
        }
    }

    // Load all bookings from CSV
    public List<Booking> loadBookings() throws IOException {
        List<Booking> bookings = new ArrayList<>();
        File file = new File(BOOKINGS_FILE);

        if (!file.exists()) {
            return bookings;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKINGS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    bookings.add(Booking.fromCSV(line));
                }
            }
        }
        return bookings;
    }

    // Save all bookings to CSV
    public void saveBookings(List<Booking> bookings) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKINGS_FILE))) {
            for (Booking booking : bookings) {
                writer.write(booking.toCSV());
                writer.newLine();
            }
        }
    }

    // Append a single booking to CSV
    public void appendBooking(Booking booking) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKINGS_FILE, true))) {
            writer.write(booking.toCSV());
            writer.newLine();
        }
    }
}