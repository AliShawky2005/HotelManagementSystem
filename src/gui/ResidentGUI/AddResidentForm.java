package gui.ResidentGUI;

import controllers.DataStore;
import controllers.ResidentController;
import controllers.RoomStatus;
import gui.RoomAssignmentForm;
import models.Resident;
import models.Room;
import models.RoomFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static controllers.RoomStatus.*;
import static controllers.RoomStatus.availableRooms;

public class AddResidentForm {

    private JFrame frame;
    private JTextField nameField, emailField, contactField;

    private JComboBox roomNumberComboBox;
    private List<RoomAssignmentForm.RoomInfo> availableRooms;

    public AddResidentForm() {
        availableRooms = DataStore.loadRoomDataFromFile();
        // Frame setup
        frame = new JFrame("Add Resident");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 350);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setLayout(new BorderLayout(20, 20));

        // Input panel for resident details with titled border
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Resident Information"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Spacing around components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name input
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(new JLabel("Name:"), gbc);
        nameField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(nameField, gbc);

        // Email input
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Email:"), gbc);
        emailField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(emailField, gbc);

        // Contact info input
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Contact Info:"), gbc);
        contactField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(contactField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;

        inputPanel.add(new JLabel("Select Room Number:"), gbc);
        gbc.gridx = 1;
        roomNumberComboBox = new JComboBox<>();
        populateRoomComboBox(roomNumberComboBox);


        inputPanel.add(roomNumberComboBox, gbc);


        gbc.gridx = 1;
        inputPanel.add(roomNumberComboBox, gbc);

        frame.add(inputPanel, BorderLayout.CENTER);

        // Button panel for Add and Back buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        JButton addButton = new JButton("Add Resident");
        addButton.setPreferredSize(new Dimension(150, 40)); // Set button size
        addButton.setFont(new Font("Arial", Font.PLAIN, 14));
        buttonPanel.add(addButton);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 40)); // Set button size
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        buttonPanel.add(backButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Button Listeners
        addButton.addActionListener(e -> {
            try {
                // Validate input fields
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String contactText = contactField.getText().trim();
                int roomNumber = Integer.parseInt((String) roomNumberComboBox.getSelectedItem());

                if (name.isEmpty() || email.isEmpty() || contactText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate Email Format
                if (!ResidentController.getInstance().validateEmail(email)) {
                    JOptionPane.showMessageDialog(frame, "Invalid Email Format.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate Contact Info as number
                int contactInfo;
                try {
                    contactInfo = Integer.parseInt(contactText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Contact Info must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Add Resident
                Resident newResident = new Resident(name, email, contactInfo,roomNumber);
                boolean added = ResidentController.getInstance().addResident(newResident);

                if (added) {
                    JOptionPane.showMessageDialog(frame, "Resident added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Resident with this email already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                String roomType = RoomStatus.getRoomDescription(roomNumber);
                roomType = roomType.split(" ")[0];
                // Save the reservation to the file
                Room room = RoomFactory.createRoom(roomNumber,roomType,1, email);
                saveReservation(room);

                changeRoomStatus(roomNumber);

                updateRoomsFile("rooms.txt");
                populateRoomComboBox(roomNumberComboBox);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> frame.dispose());

        // Focus management: Automatically focus on the first field
        nameField.requestFocus();

        // Display the frame
        frame.setVisible(true);
    }





}


