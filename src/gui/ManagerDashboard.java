package gui;

import controllers.WorkerController;
import gui.WorkerGUI.WorkerDetailsForm;
import gui.WorkerGUI.WorkerManagementMenu;
import models.User;
import models.Worker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ManagerDashboard extends JFrame {
    public ManagerDashboard(User user) {
        setTitle("Manager Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Header
        JLabel welcomeLabel = new JLabel("Welcome, Manager " + user.getUsername(), JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(welcomeLabel, BorderLayout.NORTH);

        // Center Panel with Buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 2, 10, 10));

        JButton manageWorkersButton = new JButton("Manage Workers");
        JButton viewWorkersButton = new JButton("View Workers");
        JButton viewResidentsButton = new JButton("View Residents");
        JButton trackIncomeButton = new JButton("Track Income");
        JButton monitorRoomsButton = new JButton("Monitor Rooms");
        JButton logoutButton = new JButton("Logout");

        // Add buttons to panel
        centerPanel.add(manageWorkersButton);
        centerPanel.add(viewWorkersButton);
        centerPanel.add(viewResidentsButton);
        centerPanel.add(trackIncomeButton);
        centerPanel.add(monitorRoomsButton);
        centerPanel.add(logoutButton);

        add(centerPanel, BorderLayout.CENTER);

        // Logout Action
        logoutButton.addActionListener(e -> {
            dispose(); // Close dashboard
            new LoginForm(); // Redirect to login
        });

        manageWorkersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new WorkerManagementMenu();
            }
        });

        viewWorkersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WorkerDetailsForm form = new WorkerDetailsForm();
                form.viewWorkerDetails();
            }
        });

        viewResidentsButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "View Residents clicked!"));

        trackIncomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new IncomeTrackerForm();
            }
        });
        monitorRoomsButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Monitor Rooms clicked!"));

        setVisible(true);
    }




}
