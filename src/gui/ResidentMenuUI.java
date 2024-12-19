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











//    private class saveButtonListener implements ActionListener {
//        public void actionPerformed(ActionEvent e) {
//            try {
//
//                // controllers.ResidentController resident = mode.getResident();
//                //    controller.deleteResident(resident.getEmail());
//                //  new AddResidentListener();
//
//                String newName = nameField.getText();
//                int newContactInfo = Integer.parseInt(contactField.getText());
//                String newEmail = emailField.getText();
//                String oldEmail = OldEmailField.getText();
//
//
//                if (controller != null) {
//                    controller.setResidentName(newName);
//                    controller.setEmail(newEmail);
//                    controller.setContactInfo(newContactInfo);
//                    controller.deleteResident(oldEmail);
//                    ResidentController.saveResidentToFile(controller);
//                    JOptionPane.showMessageDialog(frame, "Resident details updated successfully!");
//                } else {
//                    JOptionPane.showMessageDialog(frame, "Resident not found!");
//                }
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
//            }
//
//        }
//
//    }




//    private class retrieveResidentListener implements ActionListener {
//
//        ResidentController resident;
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            try {
//
//                nameField.setEnabled(false);
//                contactField.setEnabled(false);
//                emailField.setEnabled(false);
//                String email = OldEmailField.getText();
//
//                resident = controller.findResidentByEmail(email);
//
//                if (resident != null) {
//                    double cost = resident.getCost();
//                    nameField.setText(resident.getResidentName());
//                    contactField.setText(String.valueOf(resident.getContactInfo()));
//                    costArea.setText(String.valueOf(cost));
//                    // Enable editing fields
//                    nameField.setEnabled(true);
//                    contactField.setEnabled(true);
//                    emailField.setEnabled(true);
//
//                } else {
//
//                    JOptionPane.showMessageDialog(frame, "Resident not found!");
//                }
//
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
//            }
//
//        }




//    private class ManagerCostListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            try {
//
//                String input = JOptionPane.showInputDialog(frame, "Enter service number to remove:");
//                int serviceIndex = Integer.parseInt(input);
//
//                double cost = controller.getCost();
//                cost += controller.reduceService(serviceIndex);
//                costArea.setText("Total Cost: $" + controller.getCost());
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
//            }
//        }
//    }

}
