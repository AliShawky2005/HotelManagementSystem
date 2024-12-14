import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class RegistrationForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton registerButton;
    private JButton switchToLoginButton;

    private Map<String, User> users = new HashMap<>();


    public RegistrationForm() {
        setTitle("Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridLayout(5, 2, 10, 10));

        // Username
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        // Password
        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        // Role
        add(new JLabel("Role:"));
        roleComboBox = new JComboBox<>(new String[]{"Manager (m)", "Receptionist (r)"});
        add(roleComboBox);

        // Register Button
        registerButton = new JButton("Register");
        add(registerButton);

        // Switch to Login
        switchToLoginButton = new JButton("Switch to Login");
        add(switchToLoginButton);

        // Button Actions
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                char role = roleComboBox.getSelectedIndex() == 0 ? 'm' : 'r';

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username and Password cannot be empty!");
                    return;
                }

                if (users.containsKey(username)) {
                    JOptionPane.showMessageDialog(null, "User already exists!");
                } else {
                    users.put(username, new User(username, password, role));
                    JOptionPane.showMessageDialog(null, "User registered successfully!");
                }
            }
        });

        switchToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginForm(users);
            }
        });

        setVisible(true);
    }
}

class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton switchToRegisterButton;
    private Map<String, User> users;

    public LoginForm(Map<String, User> users) {
        this.users = users;

        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridLayout(3, 2, 10, 10));

        // Username
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        // Password
        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        // Login Button
        loginButton = new JButton("Login");
        add(loginButton);

        // Switch to Register
        switchToRegisterButton = new JButton("Switch to Register");
        add(switchToRegisterButton);

        // Button Action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                User user = users.get(username);
                if (user != null && user.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(null, "Welcome " + username + " (" + user.getRole() + ")");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials!");
                }
            }
        });

        switchToRegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RegistrationForm();
            }
        });

        setVisible(true);
    }
}
