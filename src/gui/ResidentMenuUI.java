package gui;

import controllers.ResidentController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResidentMenuUI {

    private JFrame frame;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField contactField;
    private JTextArea servicesArea;
    private JTextArea costArea;
    private JTextField OldEmailField;






    private void deleteResidentUI() {
        frame.getContentPane().removeAll(); // Clear previous UI components
        frame.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel emailLabel = new JLabel("Enter Email of Resident to Delete:");
        emailField = new JTextField();
        JButton deleteButton = new JButton("Delete Resident");

        deleteButton.addActionListener(new DeleteResidentListener());

        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(deleteButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> setupMainMenu());
        frame.add(backButton);

        frame.revalidate();
        frame.repaint();
    }

    private void editResidentUI() {
        frame = new JFrame("Resident Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(6, 4, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("please enter the old email"));

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Old Email:"));
        OldEmailField = new JTextField();
        inputPanel.add(OldEmailField);

        inputPanel.add(new JLabel("New Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Contact Info:"));
        contactField = new JTextField();
        inputPanel.add(contactField);

        JButton calculateButton = new JButton("Add Service");
        calculateButton.addActionListener(new CalculateCostListener());
        inputPanel.add(calculateButton);

        calculateButton = new JButton("remove Service");
        calculateButton.addActionListener(new ManagerCostListener());
        inputPanel.add(calculateButton);

        JButton viewButton = new JButton("view details Resident");
        viewButton.addActionListener(new retrieveResidentListener());
        inputPanel.add(viewButton);

        JButton addButton = new JButton("save changes");
        addButton.addActionListener(new saveButtonListener());
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



    private class saveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {

                // controllers.ResidentController resident = mode.getResident();
                //    controller.deleteResident(resident.getEmail());
                //  new AddResidentListener();

                String newName = nameField.getText();
                int newContactInfo = Integer.parseInt(contactField.getText());
                String newEmail = emailField.getText();
                String oldEmail = OldEmailField.getText();


                if (controller != null) {
                    controller.setResidentName(newName);
                    controller.setEmail(newEmail);
                    controller.setContactInfo(newContactInfo);
                    controller.deleteResident(oldEmail);
                    ResidentController.saveResidentToFile(controller);
                    JOptionPane.showMessageDialog(frame, "Resident details updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Resident not found!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }

        }

    }


    private class DeleteResidentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String email = emailField.getText();

                if (email.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Email field cannot be empty!");
                    return;
                }

                boolean success = controller.deleteResident(email);
                if (success) {
                    JOptionPane.showMessageDialog(frame, "Resident deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Resident not found!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        }
    }


    private class retrieveResidentListener implements ActionListener {

        ResidentController resident;

        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                nameField.setEnabled(false);
                contactField.setEnabled(false);
                emailField.setEnabled(false);
                String email = OldEmailField.getText();

                resident = controller.findResidentByEmail(email);

                if (resident != null) {
                    double cost = resident.getCost();
                    nameField.setText(resident.getResidentName());
                    contactField.setText(String.valueOf(resident.getContactInfo()));
                    costArea.setText(String.valueOf(cost));
                    // Enable editing fields
                    nameField.setEnabled(true);
                    contactField.setEnabled(true);
                    emailField.setEnabled(true);

                } else {

                    JOptionPane.showMessageDialog(frame, "Resident not found!");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }

        }

        public double getResidentCost() {
            return resident != null ? resident.getCost() : 0;
        }
    }

    private class CalculateCostListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String input = JOptionPane.showInputDialog(frame, "Enter service number to add:");
                int serviceIndex = Integer.parseInt(input);

                //   double residentCost = new retrieveResidentListener().getResidentCost();
                controller.addService(serviceIndex);

                costArea.setText("Total Cost: $" + controller.getCost());

                // controllers.ResidentController.saveResidentToFile(controller);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        }

    }

    private class ManagerCostListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                String input = JOptionPane.showInputDialog(frame, "Enter service number to remove:");
                int serviceIndex = Integer.parseInt(input);

                double cost = controller.getCost();
                cost += controller.reduceService(serviceIndex);
                costArea.setText("Total Cost: $" + controller.getCost());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ResidentMenuUI::new);
    }
}
