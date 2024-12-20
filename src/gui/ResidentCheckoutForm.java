package gui;

import controllers.DataStore;
import controllers.ResidentController;
import controllers.RoomStatus;
import models.ReservationInfo;
import models.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ResidentCheckoutForm extends JFrame {
    private JTextField emailField;
    private JLabel roomNumberLabel;
    private JLabel roomDescriptionLabel;
    private JLabel numberOfNightsLabel;
    private JLabel reservationDateLabel;
    private JLabel totalCostLabel;
    private JButton checkoutButton;
    private JButton backButton;  // New Back button

    public ResidentCheckoutForm() {
        setTitle("Checkout System");
        setSize(400, 450);  // Increased size to fit all information comfortably
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up the layout
        setLayout(new BorderLayout());

        // Panel for user inputs and labels
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));  // Stack components vertically
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel emailLabel = new JLabel("Enter Resident Email:");
        emailLabel.setAlignmentX(CENTER_ALIGNMENT);  // Center the label
        emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(300, 25));
        emailField.setAlignmentX(CENTER_ALIGNMENT);

        roomNumberLabel = new JLabel("Room Number: ");
        roomNumberLabel.setAlignmentX(CENTER_ALIGNMENT);

        roomDescriptionLabel = new JLabel("Room Description: ");
        roomDescriptionLabel.setAlignmentX(CENTER_ALIGNMENT);

        numberOfNightsLabel = new JLabel("Number of Nights: ");
        numberOfNightsLabel.setAlignmentX(CENTER_ALIGNMENT);

        reservationDateLabel = new JLabel("Reservation Date: ");
        reservationDateLabel.setAlignmentX(CENTER_ALIGNMENT);

        // Style for Total Cost label to make it larger and more visible
        totalCostLabel = new JLabel("Total Cost: ");
        totalCostLabel.setFont(new Font("Arial", Font.BOLD, 20));  // Increased font size
        totalCostLabel.setAlignmentX(CENTER_ALIGNMENT);

        checkoutButton = new JButton("Checkout");
        checkoutButton.setEnabled(false);  // Disable initially
        checkoutButton.setAlignmentX(CENTER_ALIGNMENT);

        // New Back button to go back to the previous screen
        backButton = new JButton("Back");
        backButton.setAlignmentX(CENTER_ALIGNMENT);

        // Add components to the panel
        inputPanel.add(emailLabel);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));  // Spacer between components
        inputPanel.add(emailField);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        inputPanel.add(roomNumberLabel);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        inputPanel.add(roomDescriptionLabel);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        inputPanel.add(numberOfNightsLabel);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        inputPanel.add(reservationDateLabel);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        inputPanel.add(totalCostLabel);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));  // Spacer before checkout button
        inputPanel.add(checkoutButton);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));  // Spacer before back button
        inputPanel.add(backButton);

        // Add the input panel to the center of the frame
        add(inputPanel, BorderLayout.CENTER);

        // Add action listeners
        emailField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String email = emailField.getText().trim();
                if (!email.isEmpty()) {
                    ReservationInfo reservation = ResidentController.getInstance().getResidentReservation(email);
                    if (reservation != null) {
                        // Display reservation details in order
                        roomNumberLabel.setText("Room Number: " + reservation.getRoomNumber());
                        roomDescriptionLabel.setText("Room Description: " + reservation.getDescription());
                        numberOfNightsLabel.setText("Number of Nights: " + reservation.getNumberOfNights());
                        reservationDateLabel.setText("Reservation Date: " + reservation.getReservationDate());
                        totalCostLabel.setText("Total Cost: " + reservation.getTotalPrice());
                        checkoutButton.setEnabled(true);  // Enable checkout button
                    } else {
                        // If no reservation found
                        roomNumberLabel.setText("No reservation found.");
                        roomDescriptionLabel.setText("");
                        numberOfNightsLabel.setText("");
                        reservationDateLabel.setText("");
                        totalCostLabel.setText("");
                        checkoutButton.setEnabled(false);  // Disable checkout button
                    }
                }
            }
        });

        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Checkout logic (e.g., change room status, remove reservation, etc.)
                String email = emailField.getText().trim();
                ReservationInfo reservation = ResidentController.getInstance().getResidentReservation(email);
                if (reservation != null) {
                    int roomNumber = reservation.getRoomNumber();
                    RoomStatus.changeRoomStatus(roomNumber, true);
                    RoomStatus.updateRoomsFile("rooms.txt");
                    ResidentController.getInstance().deleteResident(email);
                    ResidentController.getInstance().removeResidentReservation(email);
                    DataStore.addReservationToPastReservations(reservation);

                    JOptionPane.showMessageDialog(null, "Checkout successful for " + email);

                    // Reset the form after checkout is successful
                    resetForm();  // Reset the form after checkout

                    // Disable checkout button after successful checkout
                    checkoutButton.setEnabled(false);
                }
            }
        });

        // Add action listener to the back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ReceptionistDashboard(DataStore.loggedInUser);
            }
        });

        setVisible(true);
    }

    private void resetForm() {
        // Reset all labels and text fields
        emailField.setText("");  // Clear the email field
        roomNumberLabel.setText("Room Number: ");
        roomDescriptionLabel.setText("Room Description: ");
        numberOfNightsLabel.setText("Number of Nights: ");
        reservationDateLabel.setText("Reservation Date: ");
        totalCostLabel.setText("Total Cost: ");
    }


}
