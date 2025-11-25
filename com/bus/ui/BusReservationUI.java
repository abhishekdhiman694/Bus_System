package com.bus.ui;

import com.bus.model.Bus;
import com.bus.model.Booking;
import com.bus.model.Passenger;
import com.bus.service.BusService;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Enhanced BusReservationUI - Modern GUI Application
 */
public class BusReservationUI extends JFrame {
    private BusService busService;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    // Color Scheme
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color WARNING_COLOR = new Color(243, 156, 18);
    private static final Color DARK_COLOR = new Color(44, 62, 80);
    private static final Color LIGHT_BG = new Color(236, 240, 241);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color HOVER_COLOR = new Color(52, 73, 94);

    // Components
    private JTable busTable;
    private DefaultTableModel busTableModel;
    private JTable bookingTable;
    private DefaultTableModel bookingTableModel;

    public BusReservationUI() {
        busService = new BusService();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Bus Reservation System - Professional Edition");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create main panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(LIGHT_BG);

        // Create different screens
        mainPanel.add(createHomeScreen(), "HOME");
        mainPanel.add(createViewBusesScreen(), "VIEW_BUSES");
        mainPanel.add(createSearchBusesScreen(), "SEARCH_BUSES");
        mainPanel.add(createBookTicketScreen(), "BOOK_TICKET");
        mainPanel.add(createCancelTicketScreen(), "CANCEL_TICKET");
        mainPanel.add(createViewBookingScreen(), "VIEW_BOOKING");
        mainPanel.add(createViewAllBookingsScreen(), "VIEW_ALL_BOOKINGS");
        mainPanel.add(createRevenueReportScreen(), "REVENUE_REPORT");

        add(mainPanel);
        cardLayout.show(mainPanel, "HOME");
    }

    // ==================== HOME SCREEN ====================
    private JPanel createHomeScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(LIGHT_BG);

        // Header with gradient effect
        JPanel headerPanel = createGradientHeader();
        panel.add(headerPanel, BorderLayout.NORTH);

        // Main content with cards
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(LIGHT_BG);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Row 1
        gbc.gridx = 0; gbc.gridy = 0;
        contentPanel.add(createMenuCard("🚍 View All Buses", "Browse available buses", PRIMARY_COLOR, 
            e -> { refreshBusTable(); cardLayout.show(mainPanel, "VIEW_BUSES"); }), gbc);
        
        gbc.gridx = 1;
        contentPanel.add(createMenuCard("🔍 Search Buses", "Find buses by route", SECONDARY_COLOR,
            e -> cardLayout.show(mainPanel, "SEARCH_BUSES")), gbc);
        
        gbc.gridx = 2;
        contentPanel.add(createMenuCard("🎫 Book Ticket", "Reserve your seat", SUCCESS_COLOR,
            e -> cardLayout.show(mainPanel, "BOOK_TICKET")), gbc);

        // Row 2
        gbc.gridx = 0; gbc.gridy = 1;
        contentPanel.add(createMenuCard("❌ Cancel Ticket", "Cancel booking", DANGER_COLOR,
            e -> cardLayout.show(mainPanel, "CANCEL_TICKET")), gbc);
        
        gbc.gridx = 1;
        contentPanel.add(createMenuCard("🔎 View Booking", "Check booking details", new Color(155, 89, 182),
            e -> cardLayout.show(mainPanel, "VIEW_BOOKING")), gbc);
        
        gbc.gridx = 2;
        contentPanel.add(createMenuCard("📋 All Bookings", "View booking history", WARNING_COLOR,
            e -> { refreshBookingTable(); cardLayout.show(mainPanel, "VIEW_ALL_BOOKINGS"); }), gbc);

        // Row 3
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        contentPanel.add(createMenuCard("💰 Revenue Report", "View financial summary", new Color(26, 188, 156),
            e -> cardLayout.show(mainPanel, "REVENUE_REPORT")), gbc);
        
        gbc.gridx = 2; gbc.gridwidth = 1;
        contentPanel.add(createMenuCard("🚪 Exit", "Close application", DARK_COLOR,
            e -> System.exit(0)), gbc);

        panel.add(contentPanel, BorderLayout.CENTER);

        // Footer
        JPanel footer = createFooter();
        panel.add(footer, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createGradientHeader() {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, PRIMARY_COLOR, getWidth(), 0, SECONDARY_COLOR);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(1200, 120));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("🚌 BUS RESERVATION SYSTEM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 42));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Your Journey Starts Here", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitleLabel.setForeground(new Color(255, 255, 255, 200));

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel);
        textPanel.add(subtitleLabel);

        headerPanel.add(textPanel, BorderLayout.CENTER);
        return headerPanel;
    }

    private JPanel createMenuCard(String title, String description, Color color, ActionListener action) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 30), 1, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Icon/Title area
        JLabel iconLabel = new JLabel(title, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        iconLabel.setForeground(color);

        JLabel descLabel = new JLabel(description, SwingConstants.CENTER);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descLabel.setForeground(new Color(127, 140, 141));

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        textPanel.setOpaque(false);
        textPanel.add(iconLabel);
        textPanel.add(descLabel);

        card.add(textPanel, BorderLayout.CENTER);

        // Hover effects
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(245, 247, 250));
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(color, 2, true),
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(CARD_BG);
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0, 0, 0, 30), 1, true),
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                action.actionPerformed(new ActionEvent(card, ActionEvent.ACTION_PERFORMED, null));
            }
        });

        return card;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel();
        footer.setBackground(DARK_COLOR);
        footer.setPreferredSize(new Dimension(1200, 40));
        JLabel footerLabel = new JLabel("© 2024 Bus Reservation System | All Rights Reserved");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerLabel.setForeground(Color.WHITE);
        footer.add(footerLabel);
        return footer;
    }

    // ==================== VIEW ALL BUSES SCREEN ====================
    private JPanel createViewBusesScreen() {
        JPanel panel = createStandardPanel("Available Buses", "Browse all available bus routes");

        // Table
        String[] columns = {"Bus No", "Source", "Destination", "Seats Available", "Total Seats", "Fare (₹)"};
        busTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        busTable = createStyledTable(busTableModel);

        JScrollPane scrollPane = new JScrollPane(busTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(CARD_BG);
        buttonPanel.add(createStyledButton("🔄 Refresh", PRIMARY_COLOR, e -> refreshBusTable()));
        buttonPanel.add(createStyledButton("← Back to Home", DARK_COLOR, e -> cardLayout.show(mainPanel, "HOME")));
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    // ==================== SEARCH BUSES SCREEN ====================
    private JPanel createSearchBusesScreen() {
        JPanel panel = createStandardPanel("Search Buses", "Find buses by source and destination");

        // Search Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(CARD_BG);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField sourceField = createStyledTextField(25);
        JTextField destField = createStyledTextField(25);

        addFormField(formPanel, gbc, 0, "🚏 Source:", sourceField);
        addFormField(formPanel, gbc, 1, "📍 Destination:", destField);

        // Results Table
        String[] columns = {"Bus No", "Source", "Destination", "Seats Available", "Fare (₹)"};
        DefaultTableModel searchTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable searchTable = createStyledTable(searchTableModel);
        JScrollPane scrollPane = new JScrollPane(searchTable);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(CARD_BG);

        JButton searchBtn = createStyledButton("🔍 Search", SUCCESS_COLOR, e -> {
            String source = sourceField.getText().trim();
            String dest = destField.getText().trim();
            
            if (source.isEmpty() || dest.isEmpty()) {
                showError("Please enter both source and destination!");
                return;
            }

            searchTableModel.setRowCount(0);
            List<Bus> buses = busService.searchBuses(source, dest);
            
            if (buses.isEmpty()) {
                showInfo("No buses found for the specified route!");
            } else {
                for (Bus bus : buses) {
                    searchTableModel.addRow(new Object[]{
                        bus.getBusNo(),
                        bus.getSource(),
                        bus.getDestination(),
                        bus.getSeatsAvailable(),
                        String.format("₹%.2f", bus.getFarePerSeat())
                    });
                }
            }
        });

        buttonPanel.add(searchBtn);
        buttonPanel.add(createStyledButton("🔄 Clear", WARNING_COLOR, e -> {
            sourceField.setText("");
            destField.setText("");
            searchTableModel.setRowCount(0);
        }));
        buttonPanel.add(createStyledButton("← Back", DARK_COLOR, e -> cardLayout.show(mainPanel, "HOME")));

        // Layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(CARD_BG);
        topPanel.add(formPanel, BorderLayout.NORTH);
        topPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(topPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    // ==================== BOOK TICKET SCREEN ====================
    private JPanel createBookTicketScreen() {
        JPanel panel = createStandardPanel("Book a Ticket", "Fill in passenger details to book your seat");

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(CARD_BG);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Input Fields
        JTextField nameField = createStyledTextField(25);
        JTextField ageField = createStyledTextField(25);
        JComboBox<String> genderCombo = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        styleComboBox(genderCombo);
        JTextField phoneField = createStyledTextField(25);
        JTextField emailField = createStyledTextField(25);
        JTextField busNoField = createStyledTextField(25);

        addFormField(formPanel, gbc, 0, "👤 Passenger Name:*", nameField);
        addFormField(formPanel, gbc, 1, "🎂 Age:*", ageField);
        addFormField(formPanel, gbc, 2, "⚧ Gender:*", genderCombo);
        addFormField(formPanel, gbc, 3, "📱 Phone Number:*", phoneField);
        addFormField(formPanel, gbc, 4, "📧 Email:", emailField);
        addFormField(formPanel, gbc, 5, "🚌 Bus Number:*", busNoField);

        panel.add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(CARD_BG);

        JButton bookBtn = createStyledButton("✓ Book Ticket", SUCCESS_COLOR, e -> {
            try {
                String name = nameField.getText().trim();
                String ageText = ageField.getText().trim();
                String gender = (String) genderCombo.getSelectedItem();
                String phone = phoneField.getText().trim();
                String email = emailField.getText().trim();
                String busNo = busNoField.getText().trim().toUpperCase();

                if (name.isEmpty() || ageText.isEmpty() || phone.isEmpty() || busNo.isEmpty()) {
                    showError("Please fill all required fields marked with *");
                    return;
                }

                int age = Integer.parseInt(ageText);
                if (age <= 0 || age > 120) {
                    showError("Please enter a valid age (1-120)");
                    return;
                }

                Passenger passenger = new Passenger(name, age, gender, phone, email);
                Booking booking = busService.bookSeat(busNo, passenger);
                Bus bus = busService.getBusByNumber(busNo);

                String message = String.format(
                    "<html><body style='width: 300px; padding: 10px;'>" +
                    "<h2 style='color: #27ae60;'>✓ Booking Successful!</h2>" +
                    "<hr>" +
                    "<p><b>Booking ID:</b> %s</p>" +
                    "<p><b>Passenger:</b> %s</p>" +
                    "<p><b>Bus:</b> %s</p>" +
                    "<p><b>Route:</b> %s → %s</p>" +
                    "<p><b>Seat Number:</b> %d</p>" +
                    "<p><b>Fare:</b> ₹%.2f</p>" +
                    "<hr>" +
                    "<p style='color: #7f8c8d;'>Please save your Booking ID for future reference.</p>" +
                    "</body></html>",
                    booking.getBookingId(), name, busNo, bus.getSource(), 
                    bus.getDestination(), booking.getSeatNo(), booking.getFare()
                );

                showSuccessMessage(message);

                // Clear fields
                nameField.setText("");
                ageField.setText("");
                phoneField.setText("");
                emailField.setText("");
                busNoField.setText("");
                genderCombo.setSelectedIndex(0);

            } catch (NumberFormatException ex) {
                showError("Please enter a valid age!");
            } catch (Exception ex) {
                showError("Booking Failed: " + ex.getMessage());
            }
        });

        buttonPanel.add(bookBtn);
        buttonPanel.add(createStyledButton("🔄 Clear", WARNING_COLOR, e -> {
            nameField.setText("");
            ageField.setText("");
            phoneField.setText("");
            emailField.setText("");
            busNoField.setText("");
            genderCombo.setSelectedIndex(0);
        }));
        buttonPanel.add(createStyledButton("← Back", DARK_COLOR, e -> cardLayout.show(mainPanel, "HOME")));
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    // ==================== CANCEL TICKET SCREEN ====================
    private JPanel createCancelTicketScreen() {
        JPanel panel = createStandardPanel("Cancel Ticket", "Enter your booking ID to cancel reservation");

        // Form Panel
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 80));
        formPanel.setBackground(CARD_BG);

        JLabel label = new JLabel("🔖 Booking ID:");
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setForeground(DARK_COLOR);
        
        JTextField bookingIdField = createStyledTextField(20);

        formPanel.add(label);
        formPanel.add(bookingIdField);
        panel.add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(CARD_BG);

        JButton cancelBtn = createStyledButton("✗ Cancel Booking", DANGER_COLOR, e -> {
            try {
                String bookingId = bookingIdField.getText().trim();
                if (bookingId.isEmpty()) {
                    showError("Please enter booking ID!");
                    return;
                }

                Booking booking = busService.getBookingById(bookingId);
                
                String confirmMsg = String.format(
                    "<html><body style='width: 300px;'>" +
                    "<h3>Confirm Cancellation</h3>" +
                    "<p><b>Booking ID:</b> %s</p>" +
                    "<p><b>Passenger:</b> %s</p>" +
                    "<p><b>Bus:</b> %s</p>" +
                    "<p><b>Refund Amount:</b> ₹%.2f</p>" +
                    "<hr>" +
                    "<p style='color: #e74c3c;'>Are you sure you want to cancel?</p>" +
                    "</body></html>",
                    bookingId, booking.getPassenger().getName(), 
                    booking.getBusNo(), booking.getFare()
                );

                int confirm = JOptionPane.showConfirmDialog(
                    this,
                    confirmMsg,
                    "Confirm Cancellation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    busService.cancelBooking(bookingId);
                    showSuccessMessage(String.format(
                        "<html><body style='width: 250px;'>" +
                        "<h3 style='color: #27ae60;'>✓ Cancellation Successful</h3>" +
                        "<p>Refund Amount: ₹%.2f</p>" +
                        "<p style='color: #7f8c8d;'>Amount will be credited within 5-7 business days.</p>" +
                        "</body></html>", 
                        booking.getFare()
                    ));
                    bookingIdField.setText("");
                }

            } catch (Exception ex) {
                showError("Cancellation Failed: " + ex.getMessage());
            }
        });

        buttonPanel.add(cancelBtn);
        buttonPanel.add(createStyledButton("← Back", DARK_COLOR, e -> cardLayout.show(mainPanel, "HOME")));
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    // ==================== VIEW BOOKING SCREEN ====================
    private JPanel createViewBookingScreen() {
        JPanel panel = createStandardPanel("View Booking Details", "Check your booking information");

        // Form Panel
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 50));
        formPanel.setBackground(CARD_BG);

        JLabel label = new JLabel("🔖 Booking ID:");
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setForeground(DARK_COLOR);
        
        JTextField bookingIdField = createStyledTextField(20);

        formPanel.add(label);
        formPanel.add(bookingIdField);

        // Details Panel
        JTextArea detailsArea = new JTextArea(15, 50);
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        detailsArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        JScrollPane scrollPane = new JScrollPane(detailsArea);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
        centerPanel.setBackground(CARD_BG);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 20, 50));
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(centerPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(CARD_BG);

        JButton searchBtn = createStyledButton("🔍 Search", PRIMARY_COLOR, e -> {
            try {
                String bookingId = bookingIdField.getText().trim();
                if (bookingId.isEmpty()) {
                    showError("Please enter booking ID!");
                    return;
                }

                Booking booking = busService.getBookingById(bookingId);
                Bus bus = busService.getBusByNumber(booking.getBusNo());
                Passenger p = booking.getPassenger();

                StringBuilder details = new StringBuilder();
                details.append("╔════════════════════════════════════════════════════╗\n");
                details.append("║          BOOKING DETAILS                           ║\n");
                details.append("╠════════════════════════════════════════════════════╣\n");
                details.append(String.format("║ Booking ID     : %-33s ║\n", booking.getBookingId()));
                details.append(String.format("║ Status         : %-33s ║\n", booking.getStatus()));
                details.append("╠════════════════════════════════════════════════════╣\n");
                details.append("║          PASSENGER INFORMATION                     ║\n");
                details.append("╠════════════════════════════════════════════════════╣\n");
                details.append(String.format("║ Name           : %-33s ║\n", p.getName()));
                details.append(String.format("║ Age            : %-33d ║\n", p.getAge()));
                details.append(String.format("║ Gender         : %-33s ║\n", p.getGender()));
                details.append(String.format("║ Phone          : %-33s ║\n", p.getPhoneNumber()));
                details.append(String.format("║ Email          : %-33s ║\n", p.getEmail()));
                details.append("╠════════════════════════════════════════════════════╣\n");
                details.append("║          BUS INFORMATION                           ║\n");
                details.append("╠════════════════════════════════════════════════════╣\n");
                details.append(String.format("║ Bus Number     : %-33s ║\n", booking.getBusNo()));
                details.append(String.format("║ Route          : %-15s → %-15s ║\n", bus.getSource(), bus.getDestination()));
                details.append(String.format("║ Seat Number    : %-33d ║\n", booking.getSeatNo()));
                details.append(String.format("║ Fare           : ₹%-32.2f ║\n", booking.getFare()));
                details.append("╚════════════════════════════════════════════════════╝\n");

                detailsArea.setText(details.toString());

            } catch (Exception ex) {
                detailsArea.setText("");
                showError("Booking not found: " + ex.getMessage());
            }
        });

        buttonPanel.add(searchBtn);
        buttonPanel.add(createStyledButton("🔄 Clear", WARNING_COLOR, e -> {
            bookingIdField.setText("");
            detailsArea.setText("");
        }));
        buttonPanel.add(createStyledButton("← Back", DARK_COLOR, e -> cardLayout.show(mainPanel, "HOME")));
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    // ==================== VIEW ALL BOOKINGS SCREEN ====================
    private JPanel createViewAllBookingsScreen() {
        JPanel panel = createStandardPanel("All Bookings", "Complete booking history");

        // Table
        String[] columns = {"Booking ID", "Passenger", "Age", "Bus No", "Seat", "Fare (₹)", "Status"};
        bookingTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bookingTable = createStyledTable(bookingTableModel);

        JScrollPane scrollPane = new JScrollPane(bookingTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(CARD_BG);
        buttonPanel.add(createStyledButton("🔄 Refresh", PRIMARY_COLOR, e -> refreshBookingTable()));
        buttonPanel.add(createStyledButton("← Back to Home", DARK_COLOR, e -> cardLayout.show(mainPanel, "HOME")));
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    // ==================== REVENUE REPORT SCREEN ====================
    private JPanel createRevenueReportScreen() {
        JPanel panel = createStandardPanel("Revenue Report", "Financial summary and statistics");

        // Stats Panel
        JPanel statsPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        statsPanel.setBackground(CARD_BG);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        panel.add(statsPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(CARD_BG);

        JButton generateBtn = createStyledButton("📊 Generate Report", SUCCESS_COLOR, e -> {
            statsPanel.removeAll();
            
            List<Booking> allBookings = busService.getAllBookings();
            List<Bus> allBuses = busService.getAllBuses();
            
            double totalRevenue = allBookings.stream()
                .filter(b -> "CONFIRMED".equals(b.getStatus()))
                .mapToDouble(Booking::getFare)
                .sum();
            
            long confirmedBookings = allBookings.stream()
                .filter(b -> "CONFIRMED".equals(b.getStatus()))
                .count();
            
            long cancelledBookings = allBookings.stream()
                .filter(b -> "CANCELLED".equals(b.getStatus()))
                .count();
            
            int totalSeatsBooked = (int) confirmedBookings;
            int totalSeatsAvailable = allBuses.stream()
                .mapToInt(Bus::getSeatsAvailable)
                .sum();
            int totalSeatsCapacity = allBuses.stream()
                .mapToInt(Bus::getTotalSeats)
                .sum();

            statsPanel.add(createStatCard("💰 Total Revenue", String.format("₹%.2f", totalRevenue), SUCCESS_COLOR));
            statsPanel.add(createStatCard("✓ Confirmed Bookings", String.format("%d", confirmedBookings), PRIMARY_COLOR));
            statsPanel.add(createStatCard("✗ Cancelled Bookings", String.format("%d", cancelledBookings), DANGER_COLOR));
            statsPanel.add(createStatCard("🚌 Total Buses", String.format("%d", allBuses.size()), SECONDARY_COLOR));
            statsPanel.add(createStatCard("💺 Seats Booked", String.format("%d / %d", totalSeatsBooked, totalSeatsCapacity), WARNING_COLOR));
            statsPanel.add(createStatCard("📈 Occupancy Rate", String.format("%.1f%%", totalSeatsCapacity > 0 ? (totalSeatsBooked * 100.0 / totalSeatsCapacity) : 0), new Color(155, 89, 182)));

            statsPanel.revalidate();
            statsPanel.repaint();
        });

        buttonPanel.add(generateBtn);
        buttonPanel.add(createStyledButton("← Back to Home", DARK_COLOR, e -> cardLayout.show(mainPanel, "HOME")));
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2, true),
            BorderFactory.createEmptyBorder(25, 20, 25, 20)
        ));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(DARK_COLOR);

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(color);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    // ==================== HELPER METHODS ====================
    private JPanel createStandardPanel(String title, String subtitle) {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBackground(LIGHT_BG);

        // Header
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setBackground(CARD_BG);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(DARK_COLOR);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(127, 140, 141));

        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(CARD_BG);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private JButton createStyledButton(String text, Color color, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(180, 45));
        button.addActionListener(action);

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            Color originalColor = color;
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }
        });

        return button;
    }

    private JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return field;
    }

    private void styleComboBox(JComboBox<String> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
    }

    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(35);
        table.setShowGrid(true);
        table.setGridColor(new Color(236, 240, 241));
        table.setSelectionBackground(new Color(52, 152, 219, 50));
        table.setSelectionForeground(DARK_COLOR);

        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(PRIMARY_COLOR);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));

        // Center align cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        return table;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent component) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        jLabel.setForeground(DARK_COLOR);
        panel.add(jLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(component, gbc);
    }

    private void refreshBusTable() {
        busTableModel.setRowCount(0);
        List<Bus> buses = busService.getAllBuses();
        for (Bus bus : buses) {
            busTableModel.addRow(new Object[]{
                bus.getBusNo(),
                bus.getSource(),
                bus.getDestination(),
                bus.getSeatsAvailable(),
                bus.getTotalSeats(),
                String.format("₹%.2f", bus.getFarePerSeat())
            });
        }
    }

    private void refreshBookingTable() {
        bookingTableModel.setRowCount(0);
        List<Booking> bookings = busService.getAllBookings();
        for (Booking booking : bookings) {
            bookingTableModel.addRow(new Object[]{
                booking.getBookingId(),
                booking.getPassenger().getName(),
                booking.getPassenger().getAge(),
                booking.getBusNo(),
                booking.getSeatNo(),
                String.format("₹%.2f", booking.getFare()),
                booking.getStatus()
            });
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    // ==================== MAIN METHOD ====================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new BusReservationUI().setVisible(true);
        });
    }
}