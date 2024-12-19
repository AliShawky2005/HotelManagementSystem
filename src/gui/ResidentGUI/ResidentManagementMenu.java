package gui.ResidentGUI;

import controllers.DataStore;
import gui.ManagerDashboard;
import gui.ReceptionistDashboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResidentManagementMenu {
    private JFrame frame;

    public ResidentManagementMenu()
    {
        frame = new JFrame("Resident Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 1, 10, 10));

        JButton addResidentButton = new JButton("Add Resident");
        addResidentButton.addActionListener(e -> new AddResidentForm());

        JButton deleteResidentButton = new JButton("Delete Resident");
        deleteResidentButton.addActionListener(e -> new DeleteResidentForm());

        JButton editResidentButton = new JButton("Edit Resident");
        editResidentButton.addActionListener(e -> new EditResidentForm());

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current frame
                new ReceptionistDashboard(DataStore.loggedInUser); // Navigate back to the dashboard
            }
        });

        frame.add(addResidentButton);
        frame.add(deleteResidentButton);
        frame.add(editResidentButton);
        frame.add(backButton);

        frame.setVisible(true);
    }
}
