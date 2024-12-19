package gui.ResidentGUI;

import gui.ResidentMenuUI;

import javax.swing.*;
import java.awt.*;

public class AddResidentForm {

    private JFrame frame;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField contactField;
    public AddResidentForm()
    {
        frame = new JFrame("Resident Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 3, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Resident Details"));

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Contact Info:"));
        contactField = new JTextField();
        inputPanel.add(contactField);

        JButton calculateButton = new JButton("Add Service");
        calculateButton.addActionListener(new ResidentMenuUI.CalculateCostListener());
        inputPanel.add(calculateButton);

        calculateButton = new JButton("remove Service");
        calculateButton.addActionListener(new ResidentMenuUI.ManagerCostListener());
        inputPanel.add(calculateButton);

        JButton addButton = new JButton("Add Resident");
        addButton.addActionListener(new AddResidentListener());
        inputPanel.add(addButton);

        frame.add(inputPanel, BorderLayout.NORTH);

        JPanel servicePanel = new JPanel(new BorderLayout());
        servicePanel.setBorder(BorderFactory.createTitledBorder("Available Services"));

        servicesArea = new JTextArea();
        servicesArea.setText(controller.getServicesList());
        servicesArea.setEditable(false);
        servicePanel.add(new JScrollPane(servicesArea), BorderLayout.CENTER);

        costArea = new JTextArea(5, 40);
        costArea.setEditable(false);
        costArea.setBorder(BorderFactory.createTitledBorder("Total Cost"));
        servicePanel.add(new JScrollPane(costArea), BorderLayout.SOUTH);

        frame.add(servicePanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> setupMainMenu());
        frame.add(backButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
