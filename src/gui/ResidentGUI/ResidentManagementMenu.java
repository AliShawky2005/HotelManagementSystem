package gui.ResidentGUI;

import javax.swing.*;
import java.awt.*;

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

        frame.add(addResidentButton);
        frame.add(deleteResidentButton);
        frame.add(editResidentButton);

        frame.setVisible(true);
    }
}