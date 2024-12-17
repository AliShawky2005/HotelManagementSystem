package gui.WorkerGUI;

import controllers.WorkerController;

import javax.swing.*;
import java.awt.*;

public class DeleteWorkerForm {
    private JFrame frame;
    private JTextField nameField;

    public DeleteWorkerForm() {
        frame = new JFrame("Delete Worker");
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL; // Allow stretching horizontally
        gbc.weightx = 1.0; // Add weight to stretch components in x-axis

        // Row 1: Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        frame.add(new JLabel("Enter Worker Name to Delete:"), gbc);

        // Row 1: Text Field
        gbc.gridx = 1;
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 25)); // Initial size
        frame.add(nameField, gbc);

        // Row 2: Buttons
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Span across two columns
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel();
        JButton deleteButton = new JButton("Delete Worker");
        JButton backButton = new JButton("Back");

        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        frame.add(buttonPanel, gbc);

        // Action Listeners
        deleteButton.addActionListener(e -> {
            String name = nameField.getText();
            if (WorkerController.getInstance().deleteWorker(name)) {
                JOptionPane.showMessageDialog(frame, "Worker deleted successfully!");
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Worker not found.");
            }
        });

        backButton.addActionListener(e -> frame.dispose());

        frame.setVisible(true);
    }
}
