import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;


public class RegistrationForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    public RegistrationForm() {
        setTitle("Hotel Management System - Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridLayout(4, 1, 10, 10));

        // Username field
        JPanel usernamePanel = new JPanel(new FlowLayout());
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);

        // Password field
        JPanel passwordPanel = new JPanel(new FlowLayout());
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        // Role selection
        JPanel rolePanel = new JPanel(new FlowLayout());
        JLabel roleLabel = new JLabel("Role:");
        roleComboBox = new JComboBox<>(new String[]{"Manager", "Receptionist"});
        rolePanel.add(roleLabel);
        rolePanel.add(roleComboBox);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back to Login");
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);

        // Add components to frame
        add(usernamePanel);
        add(passwordPanel);
        add(rolePanel);
        add(buttonPanel);

        // Register action
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                char role = roleComboBox.getSelectedIndex() == 0 ? 'm' : 'r';

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                    return;
                }

                // Check if the username already exists
                if (DataStore.getUsers().containsKey(username)) {
                    JOptionPane.showMessageDialog(null, "Username already exists. Please choose another.");
                    return;
                }

                // Add user to DataStore and save to file
                DataStore.addUser(username, new User(username, password, role));
                JOptionPane.showMessageDialog(null, "Registration successful! You can now log in.");
                dispose(); // Close registration form
                new LoginForm();
            }
        });

        // Back to login action
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close registration form
                new LoginForm();
            }
        });

        setVisible(true);
    }
}



class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginForm() {
        setTitle("Hotel Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridLayout(4, 1, 10, 10));

        // Username field
        JPanel usernamePanel = new JPanel(new FlowLayout());
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);

        // Password field
        JPanel passwordPanel = new JPanel(new FlowLayout());
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        // Add components to frame
        add(usernamePanel);
        add(passwordPanel);
        add(buttonPanel);

        // Login action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                HashMap<String, User> users = DataStore.getUsers();
                User user = users.get(username);

                if (user != null && user.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(null, "Login successful! Welcome, " + username);
                    dispose(); // Close login form

                    // Open the appropriate dashboard based on user role
                    if (user.getRole() == 'm') {
                        new ManagerDashboard(user);
                    } else if (user.getRole() == 'r') {
                        new ReceptionistDashboard(user);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password.");
                }
            }
        });

        // Register action
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close login form
                new RegistrationForm();
            }
        });

        setVisible(true);
    }

}
