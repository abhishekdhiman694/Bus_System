package com.bus.model;

/**
 * Passenger Model Class
 * Represents a passenger with personal information
 */
public class Passenger {
    private String name;
    private int age;
    private String gender;
    private String phoneNumber;
    private String email;

    // Constructor
    public Passenger(String name, int age, String gender, String phoneNumber, String email) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("Passenger: %s | Age: %d | Gender: %s | Phone: %s",
                name, age, gender, phoneNumber);
    }
}