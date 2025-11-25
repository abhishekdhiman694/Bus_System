# **Bus Booking & Management System**

A Java-based Bus Reservation System using **Swing** for UI and **CSV files** for storage.
The system supports booking, cancellation, bus availability checking, and passenger record management.

## 📌 **Features**

### ✔ **Bus Management**

* View available buses
* See timing, route, and total seats
* Real-time seat availability updates

### ✔ **Ticket Booking**

* Book seats for passengers
* Validate bus ID and seat count
* Save all bookings to CSV

### ✔ **Ticket Cancellation**

* Cancel existing bookings
* Automatically update available seats

### ✔ **Record Management**

* Maintain booking history
* Store data in CSV files
* Reload cleanly when app restarts

### ✔ **Reports**

* Summary of buses
* Total bookings
* Passenger details

---

## 🛠 **Tech Stack**

* **Java (JDK 8+)**
* **Java Swing** (UI)
* **CSV File Storage**
* **Object-Oriented Programming**



## 📁 **Project Structure**

```
Bus_System/
│
├── buses.csv
├── bookings.csv
│
├── com/
│   └── bus/
│       ├── main/
│       │   └── BusApp.java
│       │
│       ├── model/
│       │   ├── Bus.java
│       │   ├── Booking.java
│       │   └── Passenger.java
│       │
│       ├── service/
│       │   ├── BusService.java
│       │   ├── FileService.java
│       │   └── ReportService.java
│       │
│       └── ui/
│           └── BusReservationUI.java

```
## ▶️ **How to Run**

1. Install **Java JDK 8+**
2. Open the project in IntelliJ / Eclipse / VS Code
3. Ensure `buses.csv` and `bookings.csv` are in the root folder
4. Run the following class:

```
com.bus.main.BusApp
```

5. The **Bus Reservation UI** will launch.

## 🧠 **System Architecture**

This project follows a **3-layer architecture**:

### **1️⃣ UI Layer (Swing)**

Handles user interaction via windows, buttons, forms, and tables.
File: `BusReservationUI.java`

### **2️⃣ Service Layer (Business Logic)**

Implements booking, cancellation, validation, and availability logic.
Files:

* `BusService.java`
* `ReportService.java`

### **3️⃣ Data Layer (CSV Storage)**

Reads and writes bus and booking data to CSV files using file handling.
File: `FileService.java`
