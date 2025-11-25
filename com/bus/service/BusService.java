package com.bus.service;

import com.bus.model.Bus;
import com.bus.model.Booking;
import com.bus.model.Passenger;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * BusService Class
 * Handles business logic for bus booking operations
 */
public class BusService {
    private FileService fileService;
    private List<Bus> buses;
    private List<Booking> bookings;
    private Random random;

    public BusService() {
        this.fileService = new FileService();
        this.random = new Random();
        loadData();
    }

    // Load data from files
    private void loadData() {
        try {
            fileService.initializeFiles();
            buses = fileService.loadBuses();
            bookings = fileService.loadBookings();
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
            throw new RuntimeException("Failed to initialize system");
        }
    }

    // Get all available buses
    public List<Bus> getAllBuses() {
        return buses;
    }

    // Search buses by source and destination
    public List<Bus> searchBuses(String source, String destination) {
        return buses.stream()
                .filter(bus -> bus.getSource().equalsIgnoreCase(source) &&
                        bus.getDestination().equalsIgnoreCase(destination) &&
                        bus.getSeatsAvailable() > 0)
                .collect(Collectors.toList());
    }

    // Get bus by bus number
    public Bus getBusByNumber(String busNo) throws Exception {
        return buses.stream()
                .filter(bus -> bus.getBusNo().equalsIgnoreCase(busNo))
                .findFirst()
                .orElseThrow(() -> new Exception("Bus not found: " + busNo));
    }

    // Book a seat
    public Booking bookSeat(String busNo, Passenger passenger) throws Exception {
        Bus bus = getBusByNumber(busNo);

        if (bus.getSeatsAvailable() <= 0) {
            throw new Exception("No seats available on this bus");
        }

        // Book the seat
        bus.bookSeat();

        // Generate booking ID
        String bookingId = generateBookingId();

        // Calculate seat number
        int seatNo = bus.getTotalSeats() - bus.getSeatsAvailable();

        // Create booking
        Booking booking = new Booking(bookingId, passenger, busNo, seatNo, bus.getFarePerSeat());
        bookings.add(booking);

        // Save changes
        saveData();

        return booking;
    }

    // Cancel a booking
    public void cancelBooking(String bookingId) throws Exception {
        Booking booking = bookings.stream()
                .filter(b -> b.getBookingId().equals(bookingId) && 
                        b.getStatus().equals("CONFIRMED"))
                .findFirst()
                .orElseThrow(() -> new Exception("Booking not found or already cancelled: " + bookingId));

        // Cancel the booking
        booking.cancelBooking();

        // Release the seat
        Bus bus = getBusByNumber(booking.getBusNo());
        bus.cancelSeat();

        // Save changes
        saveData();
    }

    // Get booking by ID
    public Booking getBookingById(String bookingId) throws Exception {
        return bookings.stream()
                .filter(b -> b.getBookingId().equals(bookingId))
                .findFirst()
                .orElseThrow(() -> new Exception("Booking not found: " + bookingId));
    }

    // Get all bookings
    public List<Booking> getAllBookings() {
        return bookings;
    }

    // Get active bookings
    public List<Booking> getActiveBookings() {
        return bookings.stream()
                .filter(b -> b.getStatus().equals("CONFIRMED"))
                .collect(Collectors.toList());
    }

    // Generate unique booking ID
    private String generateBookingId() {
        String id;
        do {
            id = "BK" + System.currentTimeMillis() + random.nextInt(1000);
        } while (bookingExists(id));
        return id;
    }

    // Check if booking ID exists
    private boolean bookingExists(String bookingId) {
        return bookings.stream().anyMatch(b -> b.getBookingId().equals(bookingId));
    }

    // Save data to files
    private void saveData() throws IOException {
        fileService.saveBuses(buses);
        fileService.saveBookings(bookings);
    }
}