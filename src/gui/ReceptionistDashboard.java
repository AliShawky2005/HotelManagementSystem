package gui;

import gui.LoginForm;
import models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReceptionistDashboard extends JFrame {
    public ReceptionistDashboard(User user) {
        setTitle("Receptionist Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Header
        JLabel welcomeLabel = new JLabel("Welcome, Receptionist " + user.getUsername(), JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(welcomeLabel, BorderLayout.NORTH);

        // Center Panel with Buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 2, 10, 10));

        JButton manageResidentsButton = new JButton("Manage Residents");
        //JButton calculateCostsButton = new JButton("Calculate Costs");
        JButton assignRoomsButton = new JButton("Assign Rooms");
        JButton logoutButton = new JButton("Logout");

        // Add buttons to panel
        centerPanel.add(manageResidentsButton);
       // centerPanel.add(calculateCostsButton);
        centerPanel.add(assignRoomsButton);
        centerPanel.add(logoutButton);

        add(centerPanel, BorderLayout.CENTER);

        // Logout Action
        logoutButton.addActionListener(e -> {
            dispose(); // Close dashboard
            new LoginForm(); // Redirect to login
        });

        // Placeholder Actions
        manageResidentsButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Manage Residents clicked!"));
        //calculateCostsButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Calculate Costs clicked!"));
        assignRoomsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RoomAssignmentForm();
            }
        });
        setVisible(true);
    }
}
