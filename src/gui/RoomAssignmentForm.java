package gui;

import models.BreakfastDecorator;
import models.Room;
import models.RoomFactory;
import models.WiFiDecorator;

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

    public RoomAssignmentForm() {
        // Create main frame
        frame = new JFrame("Hotel Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null); // Center the window on screen
        frame.getContentPane().setBackground(new Color(235, 235, 235)); // Light background color

        // Use GridBagLayout for better component positioning
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components

        // Panel for input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 10, 10)); // 4 rows, 2 columns
        inputPanel.setBackground(new Color(255, 255, 255)); // White background
        inputPanel.setBorder(BorderFactory.createTitledBorder("Room Details"));

        // Room Number Input
        JLabel roomNumberLabel = new JLabel("Enter Room Number:");
        roomNumberLabel.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(roomNumberLabel);
        roomNumberField = new JTextField(10);
        roomNumberField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(roomNumberField);

        // Room Type ComboBox
        JLabel roomTypeLabel = new JLabel("Select Room Type:");
        roomTypeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(roomTypeLabel);
        String[] roomTypes = {"Single", "Double", "Triple"};
        roomTypeComboBox = new JComboBox<>(roomTypes);
        roomTypeComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(roomTypeComboBox);

        // Nights Input
        JLabel nightsLabel = new JLabel("Enter Number of Nights:");
        nightsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(nightsLabel);
        nightsField = new JTextField(10);
        nightsField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(nightsField);

        // Create Room Button
        JButton createRoomButton = new JButton("Create Room");
        createRoomButton.setFont(new Font("Arial", Font.BOLD, 14));
        createRoomButton.setBackground(new Color(0, 123, 255)); // Blue button color
        createRoomButton.setForeground(Color.WHITE);
        createRoomButton.setFocusPainted(false); // Remove button focus outline
        inputPanel.add(createRoomButton);

        // Position the input panel in the frame
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(inputPanel, gbc);

        // Panel for result display
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        resultPanel.setBackground(new Color(255, 255, 255));
        resultPanel.setBorder(BorderFactory.createTitledBorder("Room Information"));

        resultArea = new JTextArea(6, 40);
        resultArea.setEditable(false);
        resultArea.setBackground(new Color(245, 245, 245)); // Light gray background
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
        resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // Position the result panel in the frame
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(resultPanel, gbc);

        // Action Listener for Button
        createRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get inputs from UI components
                    int roomNumber = Integer.parseInt(roomNumberField.getText());
                    int numberOfNights = Integer.parseInt(nightsField.getText());
                    String roomType = (String) roomTypeComboBox.getSelectedItem();

                    // Create room using RoomFactory
                    Room room = RoomFactory.createRoom(roomNumber, roomType.toLowerCase(), numberOfNights);

                    // Apply decorators
                    if (room != null) {
                        room = new WiFiDecorator(room);
                        room = new BreakfastDecorator(room);

                        // Display the result in the text area
                        resultArea.setText("Room Created!\n");
                        resultArea.append("Room Type: " + room.getDescription() + "\n");
                        resultArea.append("Price per Night: $" + room.getPrice() + "\n");
                        resultArea.append("Total Price for " + room.getNumberOfNights() + " nights: $" + room.calculateTotalPrice() + "\n");
                        resultArea.append(room.bookRoom());
                    }
                } catch (Exception ex) {
                    resultArea.setText("Error: " + ex.getMessage());
                }
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }

}
