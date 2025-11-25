package com.bus.model;

/**
 * Bus Model Class
 * Represents a bus with route and seat information
 */
public class Bus {
    private String busNo;
    private String source;
    private String destination;
    private int seatsAvailable;
    private int totalSeats;
    private double farePerSeat;

    // Constructor
    public Bus(String busNo, String source, String destination, int totalSeats, double farePerSeat) {
        this.busNo = busNo;
        this.source = source;
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.seatsAvailable = totalSeats;
        this.farePerSeat = farePerSeat;
    }

    // Getters and Setters
    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public double getFarePerSeat() {
        return farePerSeat;
    }

    public void setFarePerSeat(double farePerSeat) {
        this.farePerSeat = farePerSeat;
    }

    // Business Methods
    public boolean bookSeat() {
        if (seatsAvailable > 0) {
            seatsAvailable--;
            return true;
        }
        return false;
    }

    public void cancelSeat() {
        if (seatsAvailable < totalSeats) {
            seatsAvailable++;
        }
    }

    @Override
    public String toString() {
        return String.format("Bus[%s] %s -> %s | Seats: %d/%d | Fare: ₹%.2f",
                busNo, source, destination, seatsAvailable, totalSeats, farePerSeat);
    }

    // CSV format conversion
    public String toCSV() {
        return String.format("%s,%s,%s,%d,%d,%.2f",
                busNo, source, destination, seatsAvailable, totalSeats, farePerSeat);
    }

    public static Bus fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        Bus bus = new Bus(parts[0], parts[1], parts[2], Integer.parseInt(parts[4]), Double.parseDouble(parts[5]));
        bus.setSeatsAvailable(Integer.parseInt(parts[3]));
        return bus;
    }
}