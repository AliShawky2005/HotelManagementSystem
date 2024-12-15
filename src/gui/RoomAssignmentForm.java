package gui;

import controllers.DataStore;
import models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomAssignmentForm {
    private JFrame frame;
    private JTextField roomNumberField;
    private JTextField nightsField;
    private JComboBox<String> roomTypeComboBox;
    private JTextArea resultArea;
    private JCheckBox wifiCheckBox;
    private JCheckBox breakfastCheckBox;
    private JCheckBox lateCheckoutCheckBox;

    public RoomAssignmentForm() {
        initializeUI();
    }

    private void initializeUI() {
        // Main Frame Setup
        frame = new JFrame("Hotel Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 450);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Top Section: Input Panel
        JPanel inputPanel = createInputPanel();
        frame.add(inputPanel, BorderLayout.NORTH);

        // Bottom Section: Result Panel
        JPanel resultPanel = createResultPanel();
        frame.add(resultPanel, BorderLayout.CENTER);

        // Display the Frame
        frame.setVisible(true);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Room Details"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // Row 1: Room Number
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Enter Room Number:"), gbc);

        gbc.gridx = 1;
        roomNumberField = new JTextField(12);
        inputPanel.add(roomNumberField, gbc);

        // Row 2: Room Type
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Select Room Type:"), gbc);

        gbc.gridx = 1;
        roomTypeComboBox = new JComboBox<>(new String[]{"Single", "Double", "Triple"});
        inputPanel.add(roomTypeComboBox, gbc);

        // Row 3: Number of Nights
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Enter Number of Nights:"), gbc);

        gbc.gridx = 1;
        nightsField = new JTextField(12);
        inputPanel.add(nightsField, gbc);

        // Row 4: Checkboxes
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JPanel checkboxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        wifiCheckBox = new JCheckBox("Include WiFi");
        breakfastCheckBox = new JCheckBox("Include Breakfast");
        lateCheckoutCheckBox = new JCheckBox("Include Late Checkout");
        checkboxPanel.add(wifiCheckBox);
        checkboxPanel.add(breakfastCheckBox);
        checkboxPanel.add(lateCheckoutCheckBox);
        inputPanel.add(checkboxPanel, gbc);

        // Row 5: Buttons
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));

        JButton createRoomButton = new JButton("Create Room");
        createRoomButton.setPreferredSize(new Dimension(150, 40));
        createRoomButton.setFont(new Font("Arial", Font.BOLD, 14));
        createRoomButton.setBackground(new Color(0, 123, 255));
        createRoomButton.setForeground(Color.WHITE);
        createRoomButton.addActionListener(new CreateRoomButtonListener());

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.addActionListener(e -> {
            frame.dispose();
            new ReceptionistDashboard(DataStore.loggedInUser);
        });

        buttonPanel.add(createRoomButton);
        buttonPanel.add(backButton);
        inputPanel.add(buttonPanel, gbc);

        return inputPanel;
    }

    private JPanel createResultPanel() {
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Room Information"));

        resultArea = new JTextArea(6, 40);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 13));
        resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        return resultPanel;
    }

    private class CreateRoomButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Input Parsing
                int roomNumber = Integer.parseInt(roomNumberField.getText());
                int numberOfNights = Integer.parseInt(nightsField.getText());
                String roomType = (String) roomTypeComboBox.getSelectedItem();

                // Create Room with Factory
                Room room = RoomFactory.createRoom(roomNumber, roomType.toLowerCase(), numberOfNights);

                // Apply Decorators
                if (wifiCheckBox.isSelected()) {
                    room = new WiFiDecorator(room);
                }
                if (breakfastCheckBox.isSelected()) {
                    room = new BreakfastDecorator(room);
                }
                if (lateCheckoutCheckBox.isSelected()) {
                    room = new LateCheckoutDecorator(room);
                }


                RoomFactory.saveRoomToFile(room);

                // Display Results
                resultArea.setText("Room Created Successfully!\n");
                resultArea.append("Room Description: " + room.getDescription() + "\n");
                resultArea.append("Price per Night: $" + room.getPrice() + "\n");
                resultArea.append("Total for " + room.getNumberOfNights() + " nights: $" + room.calculateTotalPrice() + "\n");
                resultArea.append(room.bookRoom());
            } catch (NumberFormatException ex) {
                resultArea.setText("Error: Please enter valid numbers for Room Number and Nights.");
            } catch (Exception ex) {
                resultArea.setText("Error: " + ex.getMessage());
            }
        }
    }
}
