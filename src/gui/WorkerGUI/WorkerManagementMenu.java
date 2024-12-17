package gui.WorkerGUI;

import controllers.DataStore;
import gui.ManagerDashboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WorkerManagementMenu {
    private JFrame frame;

    public WorkerManagementMenu() {
        // Main Frame Setup
        frame = new JFrame("Manage Workers");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 350);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Worker Management Menu", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 15, 15));
        JButton addWorkerButton = new JButton("Add Worker");
        JButton updateWorkerButton = new JButton("Update Worker");
        JButton deleteWorkerButton = new JButton("Delete Worker");
        JButton backButton = new JButton("Back");

        buttonPanel.add(addWorkerButton);
        buttonPanel.add(updateWorkerButton);
        buttonPanel.add(deleteWorkerButton);
        buttonPanel.add(backButton);

        frame.add(buttonPanel, BorderLayout.CENTER);

        // Action Listeners for Buttons
        addWorkerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddWorkerForm();
            }
        });

        updateWorkerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateWorkerForm();
            }
        });

        deleteWorkerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteWorkerForm();
            }
        });

        // Back Button Action Listener
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current frame
                new ManagerDashboard(DataStore.loggedInUser); // Navigate back to the dashboard
            }
        });

        frame.setVisible(true);
    }
}
