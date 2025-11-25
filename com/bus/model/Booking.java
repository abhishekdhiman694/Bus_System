package com.bus.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Booking Model Class
 * Represents a bus seat booking with passenger and journey details
 */
public class Booking {
    private String bookingId;
    private Passenger passenger;
    private String busNo;
    private int seatNo;
    private String bookingDate;
    private double fare;
    private String status; // CONFIRMED, CANCELLED

    // Constructor
    public Booking(String bookingId, Passenger passenger, String busNo, int seatNo, double fare) {
        this.bookingId = bookingId;
        this.passenger = passenger;
        this.busNo = busNo;
        this.seatNo = seatNo;
        this.fare = fare;
        this.bookingDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.status = "CONFIRMED";
    }

    // Constructor for loading from CSV
    public Booking(String bookingId, Passenger passenger, String busNo, int seatNo, 
                   String bookingDate, double fare, String status) {
        this.bookingId = bookingId;
        this.passenger = passenger;
        this.busNo = busNo;
        this.seatNo = seatNo;
        this.bookingDate = bookingDate;
        this.fare = fare;
        this.status = status;
    }

    // Getters and Setters
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Business Methods
    public void cancelBooking() {
        this.status = "CANCELLED";
    }

    @Override
    public String toString() {
        return String.format("Booking ID: %s | Bus: %s | Seat: %d | Fare: ₹%.2f | Status: %s",
                bookingId, busNo, seatNo, fare, status);
    }

    // CSV format conversion
    public String toCSV() {
        return String.format("%s,%s,%d,%s,%s,%s,%s,%.2f,%s",
                bookingId,
                passenger.getName(),
                passenger.getAge(),
                passenger.getGender(),
                passenger.getPhoneNumber(),
                busNo,
                seatNo,
                fare,
                status);
    }

    public static Booking fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        Passenger passenger = new Passenger(
                parts[1],
                Integer.parseInt(parts[2]),
                parts[3],
                parts[4],
                ""
        );
        return new Booking(
                parts[0],
                passenger,
                parts[5],
                Integer.parseInt(parts[6]),
                parts[7],
                Double.parseDouble(parts[7]),
                parts[8]
        );
    }
}