package gui;

import controllers.DataStore;
import models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginForm() {
        setTitle("Hotel Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridLayout(4, 1, 10, 10));

        // Username field
        JPanel usernamePanel = new JPanel(new FlowLayout());
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);

        // Password field
        JPanel passwordPanel = new JPanel(new FlowLayout());
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        // Add components to frame
        add(usernamePanel);
        add(passwordPanel);
        add(buttonPanel);

        // Login action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                HashMap<String, User> users = DataStore.getUsers();
                User user = users.get(username);

                if (user != null && user.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(null, "Login successful! Welcome, " + username);
                    dispose(); // Close login form
                    DataStore.loggedInUser = user;
                    // Open the appropriate dashboard based on user role
                    if (user.getRole() == 'm') {
                        new ManagerDashboard(user);
                    } else if (user.getRole() == 'r') {
                        new ReceptionistDashboard(user);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password.");
                }
            }
        });

        // Register action
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close login form
                new RegistrationForm();
            }
        });

        setVisible(true);
    }

}
