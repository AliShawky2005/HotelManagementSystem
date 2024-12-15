import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
        viewWorkersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewWorkerDetails();
            }
        });        viewResidentsButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "View Residents clicked!"));
        trackIncomeButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Track Income clicked!"));
        monitorRoomsButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Monitor Rooms clicked!"));

        setVisible(true);
    }


    private void viewWorkerDetails() {
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
