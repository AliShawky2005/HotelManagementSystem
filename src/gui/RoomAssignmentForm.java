package gui;

import javax.swing.*;
import models.*;
import controllers.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RoomAssignmentForm {
    private JFrame frame;
    private JComboBox<String> roomNumberComboBox;
    private JLabel roomDescriptionLabel;
    private JTextField nightsField;
    private JCheckBox wifiCheckBox;
    private JCheckBox breakfastCheckBox;
    private JCheckBox lateCheckoutCheckBox;
    private JButton assignRoomButton;
    private JTextArea resultArea;
    private JButton backButton;

    private List<RoomInfo> availableRooms;

    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;

    public RoomAssignmentForm() {
        availableRooms = DataStore.loadRoomDataFromFile();
        initializeUI();
    }

    // Initialize the user interface
    private void initializeUI() {
        // Frame setup
        frame = new JFrame("Room Assignment Form");
        frame.setSize(500, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = createInputPanel();
        frame.add(inputPanel, BorderLayout.NORTH);

        // Result Panel
        JPanel resultPanel = createResultPanel();
        frame.add(resultPanel, BorderLayout.CENTER);

        // Back Button
        JPanel backButtonPanel = new JPanel();
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new ReceptionistDashboard(DataStore.loggedInUser);
            }
        });
        backButtonPanel.add(backButton);
        frame.add(backButtonPanel, BorderLayout.SOUTH);

        // Display Frame
        frame.setVisible(true);
    }

    // Create Input Panel
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Assign Room"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Row 1: Resident Name
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Resident Name:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        panel.add(nameField, gbc);

        // Row 2: Resident Email
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Resident Email:"), gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);
        panel.add(emailField, gbc);

        // Row 3: Resident Phone Number
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Resident Phone Number:"), gbc);

        gbc.gridx = 1;
        phoneField = new JTextField(20);
        panel.add(phoneField, gbc);

        // Row 4: Room Selection
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Select Room Number:"), gbc);

        gbc.gridx = 1;
        roomNumberComboBox = new JComboBox<>();
        populateRoomComboBox();
        roomNumberComboBox.addActionListener(e -> updateRoomDescription());
        panel.add(roomNumberComboBox, gbc);

        // Row 5: Room Description
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Room Description:"), gbc);

        gbc.gridx = 1;
        roomDescriptionLabel = new JLabel("Not Selected");
        panel.add(roomDescriptionLabel, gbc);

        // Row 6: Number of Nights
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("Number of Nights:"), gbc);

        gbc.gridx = 1;
        nightsField = new JTextField(10);
        panel.add(nightsField, gbc);

        // Row 7: Checkboxes
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        JPanel checkboxPanel = new JPanel(new FlowLayout());
        wifiCheckBox = new JCheckBox("WiFi");
        breakfastCheckBox = new JCheckBox("Breakfast");
        lateCheckoutCheckBox = new JCheckBox("Late Checkout");
        checkboxPanel.add(wifiCheckBox);
        checkboxPanel.add(breakfastCheckBox);
        checkboxPanel.add(lateCheckoutCheckBox);
        panel.add(checkboxPanel, gbc);

        // Row 8: Assign Button
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        assignRoomButton = new JButton("Assign Room");
        assignRoomButton.addActionListener(new AssignRoomListener());
        panel.add(assignRoomButton, gbc);

        return panel;
    }

    // Create Result Panel
    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Result"));

        resultArea = new JTextArea(6, 40);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        return panel;
    }

    // Populate the room number combo box
    private void populateRoomComboBox() {
        roomNumberComboBox.removeAllItems();
        for (RoomInfo room : availableRooms) {
            if (room.isAvailable()) {
                roomNumberComboBox.addItem(String.valueOf(room.getRoomNumber()));
            }
        }
    }

    // Update room description when a room is selected
    private void updateRoomDescription() {
        if (roomNumberComboBox.getSelectedItem() != null) {
            int selectedRoomNumber = Integer.parseInt((String) roomNumberComboBox.getSelectedItem());
            for (RoomInfo room : availableRooms) {
                if (room.getRoomNumber() == selectedRoomNumber) {
                    roomDescriptionLabel.setText(room.getDescription());
                    break;
                }
            }
        }
    }



    // Save reservation to file
    private void saveReservation(Room room) {
        RoomFactory.saveRoomToFile(room);
    }

    // Update rooms.txt file after marking a room as unavailable
    private void updateRoomsFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (RoomInfo room : availableRooms) {
                writer.write(room.getRoomNumber() + "," + room.getDescription() + "," + room.isAvailable());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error updating room file: " + e.getMessage());
        }
    }

    // Listener for the "Assign Room" button
    private class AssignRoomListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Get resident input data
                String residentName = nameField.getText().trim();
                String residentEmail = emailField.getText().trim();
                int residentPhone = Integer.parseInt(phoneField.getText().trim());

                // Check if email is valid
                if (!ResidentController.getInstance().validateEmail(residentEmail)) {
                    JOptionPane.showMessageDialog(frame, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                // Create a new Resident object
                Resident resident = new Resident(residentName, residentEmail, residentPhone);

                // Add resident and proceed with room assignment if successful
                boolean added = ResidentController.getInstance().addResident(resident);
                if (!added) {
                    JOptionPane.showMessageDialog(frame, "Failed to add resident. Email already in use.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Get selected room and night details
                int selectedRoomNumber = Integer.parseInt((String) roomNumberComboBox.getSelectedItem());
                int nights = Integer.parseInt(nightsField.getText().trim());
                String roomType = roomDescriptionLabel.getText().split(" ")[0]; // Assuming room type is the first word in the description

                // Create the room
                Room room = RoomFactory.createRoom(selectedRoomNumber, roomType, nights, residentEmail);

                // Apply room decorators based on selected options
                if (wifiCheckBox.isSelected()) room = new WiFiDecorator(room);
                if (breakfastCheckBox.isSelected()) room = new BreakfastDecorator(room);
                if (lateCheckoutCheckBox.isSelected()) room = new LateCheckoutDecorator(room);

                // Save the reservation to the file
                saveReservation(room);

                // Mark the room as unavailable
                for (RoomInfo r : availableRooms) {
                    if (r.getRoomNumber() == selectedRoomNumber) {
                        r.setAvailable(false);
                        break;
                    }
                }
                updateRoomsFile("rooms.txt");
                populateRoomComboBox();

                // Show the reservation details in the result area
                resultArea.setText("Room " + selectedRoomNumber + " Assigned Successfully" + " for "
                + residentName + "!\n");
                resultArea.append(("Resident Email: ") + residentEmail + "\n");
                resultArea.append("Room Description: " + room.getDescription() + "\n");
                resultArea.append("Price Per Night: $" + room.getPrice() + "\n");
                resultArea.append("Total Price: $" + room.calculateTotalPrice() + "\n");

            } catch (Exception ex) {
                resultArea.setText("Error: " + ex.getMessage());
            }
        }
    }

    // RoomInfo helper class
    public static class RoomInfo {
        private int roomNumber;
        private String description;
        private boolean available;

        public RoomInfo(int roomNumber, String description, boolean available) {
            this.roomNumber = roomNumber;
            this.description = description;
            this.available = available;
        }

        public int getRoomNumber() { return roomNumber; }
        public String getDescription() { return description; }
        public boolean isAvailable() { return available; }
        public void setAvailable(boolean available) { this.available = available; }
    }

}
