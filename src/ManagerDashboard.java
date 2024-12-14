import javax.swing.*;
import java.awt.*;

public class ManagerDashboard extends JFrame {
    public ManagerDashboard(User user) {
        setTitle("Manager Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Header
        JLabel welcomeLabel = new JLabel("Welcome, Manager " + user.getUsername(), JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(welcomeLabel, BorderLayout.NORTH);

        // Center Panel with Buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 2, 10, 10));

        JButton manageWorkersButton = new JButton("Manage Workers");
        JButton viewWorkersButton = new JButton("View Workers");
        JButton viewResidentsButton = new JButton("View Residents");
        JButton trackIncomeButton = new JButton("Track Income");
        JButton monitorRoomsButton = new JButton("Monitor Rooms");
        JButton logoutButton = new JButton("Logout");

        // Add buttons to panel
        centerPanel.add(manageWorkersButton);
        centerPanel.add(viewWorkersButton);
        centerPanel.add(viewResidentsButton);
        centerPanel.add(trackIncomeButton);
        centerPanel.add(monitorRoomsButton);
        centerPanel.add(logoutButton);

        add(centerPanel, BorderLayout.CENTER);

        // Logout Action
        logoutButton.addActionListener(e -> {
            dispose(); // Close dashboard
            new LoginForm(); // Redirect to login
        });

        // Placeholder Actions
        manageWorkersButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Manage Workers clicked!"));
        viewWorkersButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "View Workers clicked!"));
        viewResidentsButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "View Residents clicked!"));
        trackIncomeButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Track Income clicked!"));
        monitorRoomsButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Monitor Rooms clicked!"));

        setVisible(true);
    }
}
