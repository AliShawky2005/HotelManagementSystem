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
    private JCheckBox earlyCheckoutCheckBox;
    private JButton assignRoomButton;
    private JTextArea resultArea;
    private JButton backButton;

    private List<RoomInfo> availableRooms;

    public RoomAssignmentForm() {
        availableRooms = loadRoomDataFromFile("rooms.txt");
        initializeUI();
    }

    // Initialize the user interface
    private void initializeUI() {
        // Frame setup
        frame = new JFrame("Room Assignment Form");
        frame.setSize(500, 500);
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

        // Row 1: Room Selection
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Select Room Number:"), gbc);

        gbc.gridx = 1;
        roomNumberComboBox = new JComboBox<>();
        populateRoomComboBox();
        roomNumberComboBox.addActionListener(e -> updateRoomDescription());
        panel.add(roomNumberComboBox, gbc);

        // Row 2: Room Description
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Room Description:"), gbc);

        gbc.gridx = 1;
        roomDescriptionLabel = new JLabel("Not Selected");
        panel.add(roomDescriptionLabel, gbc);

        // Row 3: Number of Nights
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Number of Nights:"), gbc);

        gbc.gridx = 1;
        nightsField = new JTextField(10);
        panel.add(nightsField, gbc);

        // Row 4: Checkboxes
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JPanel checkboxPanel = new JPanel(new FlowLayout());
        wifiCheckBox = new JCheckBox("WiFi");
        breakfastCheckBox = new JCheckBox("Breakfast");
        earlyCheckoutCheckBox = new JCheckBox("Early Checkout");
        checkboxPanel.add(wifiCheckBox);
        checkboxPanel.add(breakfastCheckBox);
        checkboxPanel.add(earlyCheckoutCheckBox);
        panel.add(checkboxPanel, gbc);

        // Row 5: Assign Button
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
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

    // Load room data from file
    private List<RoomInfo> loadRoomDataFromFile(String fileName) {
        List<RoomInfo> rooms = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int roomNumber = Integer.parseInt(parts[0].trim());
                    String description = parts[1].trim();
                    boolean available = Boolean.parseBoolean(parts[2].trim());
                    rooms.add(new RoomInfo(roomNumber, description, available));
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading room data: " + e.getMessage());
        }
        return rooms;
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
                int selectedRoomNumber = Integer.parseInt((String) roomNumberComboBox.getSelectedItem());
                int nights = Integer.parseInt(nightsField.getText().trim());

                // Room creation
                Room room = RoomFactory.createRoom(selectedRoomNumber, "single", nights);

                // Apply decorators
                if (wifiCheckBox.isSelected()) room = new WiFiDecorator(room);
                if (breakfastCheckBox.isSelected()) room = new BreakfastDecorator(room);
                if (earlyCheckoutCheckBox.isSelected()) room = new LateCheckoutDecorator(room);

                saveReservation(room);

                // Mark room unavailable
                for (RoomInfo r : availableRooms) {
                    if (r.getRoomNumber() == selectedRoomNumber) {
                        r.setAvailable(false);
                        break;
                    }
                }
                updateRoomsFile("rooms.txt");
                populateRoomComboBox();

                // Display result in the result area
                resultArea.setText("Room Assigned Successfully!\n");
                resultArea.append("Room Description: " + room.getDescription() + "\n");
                resultArea.append("Price Per Night: $" + room.getPrice() + "\n");
                resultArea.append("Total Price: $" + room.calculateTotalPrice() + "\n");

            } catch (Exception ex) {
                resultArea.setText("Error: " + ex.getMessage());
            }
        }
    }


    // RoomInfo helper class
    private static class RoomInfo {
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

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(RoomAssignmentForm::new);
//    }
}
