package gui.WorkerGUI;

import controllers.WorkerController;
import models.Worker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateWorkerForm {
    private JFrame frame;
    private JTextField searchField, nameField, phoneField, salaryField, jobTitleField;

    public UpdateWorkerForm() {
        frame = new JFrame("Update Worker");
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Search Section
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        frame.add(new JLabel("Enter Worker Name to Update:"), gbc);

        gbc.gridy = 1; gbc.gridwidth = 1;
        searchField = new JTextField(15);
        frame.add(searchField, gbc);

        JButton searchButton = new JButton("Search");
        gbc.gridx = 1;
        frame.add(searchButton, gbc);

        // Worker Fields
        gbc.gridx = 0; gbc.gridy = 2;
        frame.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(15);
        frame.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        frame.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(15);
        frame.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        frame.add(new JLabel("Salary:"), gbc);
        gbc.gridx = 1;
        salaryField = new JTextField(15);
        frame.add(salaryField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        frame.add(new JLabel("Job Title:"), gbc);
        gbc.gridx = 1;
        jobTitleField = new JTextField(15);
        frame.add(jobTitleField, gbc);

        // Buttons
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        JButton updateButton = new JButton("Update Worker");
        JButton backButton = new JButton("Back");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(updateButton);
        buttonPanel.add(backButton);
        frame.add(buttonPanel, gbc);

        // Search and Update Logic
        searchButton.addActionListener(e -> {
            String searchName = searchField.getText();
            for (Worker worker : WorkerController.getInstance().getWorkers()) {
                if (worker.getName().equals(searchName)) {
                    nameField.setText(worker.getName());
                    phoneField.setText(worker.getPhoneNumber());
                    salaryField.setText(String.valueOf(worker.getSalary()));
                    jobTitleField.setText(worker.getJobTitle());
                    return;
                }
            }
            JOptionPane.showMessageDialog(frame, "Worker not found.");
        });

        updateButton.addActionListener(e -> {
            try {
                String oldName = searchField.getText();
                String name = nameField.getText();
                String phone = phoneField.getText();
                double salary = Double.parseDouble(salaryField.getText());
                String jobTitle = jobTitleField.getText();

                Worker updatedWorker = new Worker(name, phone, salary, jobTitle);
                if (WorkerController.getInstance().updateWorker(oldName, updatedWorker)) {
                    JOptionPane.showMessageDialog(frame, "Worker updated successfully!");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Worker not found.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid salary format.");
            }
        });

        backButton.addActionListener(e -> frame.dispose());

        frame.setVisible(true);
    }
}
