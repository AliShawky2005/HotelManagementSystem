package gui.ResidentGUI;

import controllers.ResidentController;

import javax.swing.*;
import java.awt.*;

public class DeleteResidentForm {

    private JFrame frame;
    private JTextField emailField;

    public DeleteResidentForm() {
        // Frame Setup
        frame = new JFrame("Delete Resident");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 1: Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        frame.add(new JLabel("Enter the Email of the Resident to Delete:"), gbc);

        // Row 2: Text Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        emailField = new JTextField();
        frame.add(emailField, gbc);

        // Row 3: Delete Button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        JButton deleteButton = new JButton("Delete Resident");
        deleteButton.addActionListener(e -> {
            String email = emailField.getText().trim();

            // Check if the email field is empty
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Attempt to delete the resident
            boolean deleted = ResidentController.getInstance().deleteResident(email);
            if (deleted) {
                JOptionPane.showMessageDialog(frame, "Resident deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose(); // Close the form after successful deletion
            } else {
                JOptionPane.showMessageDialog(frame, "Resident not found. Please check the email and try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        frame.add(deleteButton, gbc);

        // Row 3: Back Button
        gbc.gridx = 1;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> frame.dispose());
        frame.add(backButton, gbc);

        // Display the frame
        frame.setVisible(true);
    }
}
