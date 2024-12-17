package gui.WorkerGUI;

import controllers.WorkerController;
import models.Worker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddWorkerForm {
    private JFrame frame;
    private JTextField nameField, phoneField, salaryField, jobTitleField;

    public AddWorkerForm() {
        frame = new JFrame("Add Worker");
        frame.setSize(400, 350);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels and Input Fields
        gbc.gridx = 0; gbc.gridy = 0;
        frame.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(15);
        frame.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        frame.add(new JLabel("Phone Number:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(15);
        frame.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        frame.add(new JLabel("Salary:"), gbc);
        gbc.gridx = 1;
        salaryField = new JTextField(15);
        frame.add(salaryField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        frame.add(new JLabel("Job Title:"), gbc);
        gbc.gridx = 1;
        jobTitleField = new JTextField(15);
        frame.add(jobTitleField, gbc);

        // Buttons
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JButton addButton = new JButton("Add Worker");
        JButton backButton = new JButton("Back");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);
        frame.add(buttonPanel, gbc);

        // Button Listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    String phone = phoneField.getText();
                    double salary = Double.parseDouble(salaryField.getText());
                    String jobTitle = jobTitleField.getText();

                    Worker newWorker = new Worker(name, phone, salary, jobTitle);
                    WorkerController.getInstance().addWorker(newWorker);

                    JOptionPane.showMessageDialog(frame, "Worker added successfully!");
                    frame.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid salary format.");
                }
            }
        });

        backButton.addActionListener(e -> frame.dispose());

        frame.setVisible(true);
    }
}
