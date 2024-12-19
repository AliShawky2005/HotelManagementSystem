package gui.ResidentGUI;

import controllers.ResidentController;
import controllers.WorkerController;
import models.Resident;
import models.Worker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ResidentDetailsForm {



        public ResidentDetailsForm() {
            // Create the JFrame
            JFrame frame = new JFrame("Residents Details");
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Fetch worker data
            ResidentController residentManagement = ResidentController.getInstance();
            List<Resident> residents = residentManagement.getResidents();

            // Column names for the table
            String[] columnNames = {"Name", "Email", "Contact Info", "Room Number"};

            // Convert worker data into a 2D array for the table
            Object[][] data = new Object[residents.size()][4];
            for (int i = 0; i < residents.size(); i++) {
                Resident resident = residents.get(i);
                data[i][0] = resident.getResidentName();
                data[i][1] = resident.getEmail();
                data[i][2] = resident.getContactInfo();
                data[i][3] = resident.getRoomNumber();
            }

            // Create table model and JTable
            DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
            JTable table = new JTable(tableModel);
            table.setEnabled(false); // Make the table read-only

            // Add the table to a JScrollPane
            JScrollPane scrollPane = new JScrollPane(table);

            // Add the scroll pane to the frame
            frame.add(scrollPane, BorderLayout.CENTER);

            // Make the frame visible
            frame.setVisible(true);
        }

    }

