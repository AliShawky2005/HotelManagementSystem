package gui.ResidentGUI;

import controllers.ResidentController;
import models.Resident;

import javax.swing.*;
import java.awt.*;

public class EditResidentForm {

    private JFrame frame;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField contactField;

    private JTextField roomNumberField;

    public EditResidentForm() {
        // Frame Setup
        frame = new JFrame("Edit Resident");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setLayout(new BorderLayout());

        // Input Panel with GridBagLayout for better control
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        inputPanel.setBorder(BorderFactory.createTitledBorder("Edit Resident Details"));

        // Setup GridBagConstraints for uniform padding and alignment
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Old Email
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Old Email:"), gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);
        inputPanel.add(emailField, gbc);

        // New Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("New Name:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        inputPanel.add(nameField, gbc);

        // New Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("New Email:"), gbc);

        gbc.gridx = 1;
        JTextField newEmailField = new JTextField(20);
        inputPanel.add(newEmailField, gbc);

        // New Contact Info
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("New Contact Info:"), gbc);

        gbc.gridx = 1;
        contactField = new JTextField(20);
        inputPanel.add(contactField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("New Room Number:"), gbc);

        gbc.gridx = 1;
        roomNumberField = new JTextField(20);
        inputPanel.add(roomNumberField, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton searchButton = new JButton("Search by Email");
        searchButton.addActionListener(e -> {
            String searchEmail = emailField.getText().trim();

            // Validate email field
            if (searchEmail.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter the email to search.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Resident resident = ResidentController.getInstance().findResidentByEmail(searchEmail);

            if (resident != null) {
                // Populate fields with current data
                nameField.setText(resident.getResidentName());
                newEmailField.setText(resident.getEmail());
                contactField.setText(String.valueOf(resident.getContactInfo()));
                roomNumberField.setText(String.valueOf(resident.getRoomNumber()));
            } else {
                JOptionPane.showMessageDialog(frame, "Resident not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            String oldEmail = emailField.getText().trim();
            String newName = nameField.getText().trim();
            String newEmail = newEmailField.getText().trim();
            String contactText = contactField.getText().trim();
            int roomNumber = Integer.parseInt(roomNumberField.getText());

            // Validate input fields
            if (oldEmail.isEmpty() || newName.isEmpty() || newEmail.isEmpty() || contactText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int newContact = Integer.parseInt(contactText);

                Resident updatedResident = new Resident(newName, newEmail, newContact,roomNumber);
                boolean isUpdated = ResidentController.getInstance().updateResident(oldEmail, updatedResident);

                if (isUpdated) {
                    JOptionPane.showMessageDialog(frame, "Resident updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Resident not found or could not be updated.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Contact info must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add buttons to the button panel
        buttonPanel.add(searchButton);
        buttonPanel.add(updateButton);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> frame.dispose());
        buttonPanel.add(backButton);

        // Add components to the frame
        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Show Frame
        frame.setVisible(true);
    }


}
