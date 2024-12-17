package gui.WorkerGUI;

import controllers.WorkerController;
import models.Worker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class WorkerDetailsForm {

    public void viewWorkerDetails() {
        // Create the JFrame
        JFrame frame = new JFrame("Worker Details");
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Fetch worker data
        WorkerController workerManagement = WorkerController.getInstance();
        ArrayList<Worker> workers = workerManagement.getWorkers();

        // Column names for the table
        String[] columnNames = {"Name", "Phone Number", "Salary", "Job Title"};

        // Convert worker data into a 2D array for the table
        Object[][] data = new Object[workers.size()][4];
        for (int i = 0; i < workers.size(); i++) {
            Worker worker = workers.get(i);
            data[i][0] = worker.getName();
            data[i][1] = worker.getPhoneNumber();
            data[i][2] = worker.getSalary();
            data[i][3] = worker.getJobTitle();
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
