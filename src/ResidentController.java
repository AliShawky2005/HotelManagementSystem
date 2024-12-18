import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;
import java.io.*;

public class ResidentController {

    private String residentName;
    private String email;
    private int contactInfo;
    private String[] services = {"message", "hotel trip", "hotel transportation", "carrying bags"};

    private int servicesReceived;
    private double cost;
    private static final String FILE_PATH = "residents.txt";
    private static List<ResidentController> residents = new ArrayList<>();

    static {
        loadResidentsFromFile();
    }

    private ResidentController(Builder builder) {
        this.residentName = builder.residentName;
        this.email = builder.email;
        this.contactInfo = builder.contactInfo;
        this.servicesReceived = builder.servicesReceived;
    }

    public static class Builder {
        private String residentName;
        private String email;
        private int contactInfo;
        private int servicesReceived;

        public Builder setResidentName(String residentName) {
            this.residentName = residentName;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setContactInfo(int contactInfo) {
            this.contactInfo = contactInfo;
            return this;
        }

        public Builder setServicesReceived(int servicesReceived) {
            this.servicesReceived = servicesReceived;
            return this;
        }

        public ResidentController build() {
            return new ResidentController(this);
        }
    }

    public void addService(int serviceIndex) {
        if (serviceIndex >= 1 && serviceIndex <= services.length) {
            cost += calculateCost(serviceIndex);
        } else {
            throw new IllegalArgumentException("Invalid service index.");
        }
    }

    public String getServicesList() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < services.length; i++) {
            sb.append((i + 1) + ". " + services[i] + "\n");
        }
        return sb.toString();
    }

    public double getCost() {
        return cost;
    }

    public void setResidentName(String name) {
        this.residentName = name;
    }

    public void setEmail(String email) {
        if (validateEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email format.");
        }
    }

    public void setContactInfo(int contactInfo) {
        int length = String.valueOf(contactInfo).length();
        if (length == 5 || length == 11) {
            this.contactInfo = contactInfo;
        } else {
            throw new IllegalArgumentException("Invalid phone number length.");
        }
    }
//manager edit the services
    private double ManagerServices(int servicesReceived) {
        double cost = 0;
        switch (servicesReceived) {
            case 1:
                cost -= 100.00;
                break;
            case 2:
                cost -= 1500.00;
                break;
            case 3:
                cost -= 2500.00;
                break;
            case 4:
                cost -= 10.30;
                break;
        }
        return cost;
    }
//services requested by resident
    private double calculateCost(int servicesReceived) {
        double cost = 0;
        switch (servicesReceived) {
            case 1:
                cost += 100.00;
                break;
            case 2:
                cost += 1500.00;
                break;
            case 3:
                cost += 2500.00;
                break;
            case 4:
                cost += 10.30;
                break;
        }
        return cost;
    }

    private boolean validateEmail(String email) {
        String emailRegex = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    private static void loadResidentsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    ResidentController resident = new Builder()
                            .setResidentName(parts[0])
                            .setEmail(parts[1])
                            .setContactInfo(Integer.parseInt(parts[2]))
                            .build();
                    residents.add(resident);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading residents: " + e.getMessage());
        }
    }
// validates if resident is new
    public static boolean isNewResident(String name, String email) {
        for (ResidentController resident : residents) {
            if (resident.residentName.equalsIgnoreCase(name) || resident.email.equalsIgnoreCase(email)) {
                return false;
            }
        }
        return true;
    }

    public static void saveResidentToFile(ResidentController resident) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(resident.residentName + "," + resident.email + "," + resident.contactInfo + "\n");
        } catch (IOException e) {
            System.err.println("Error saving resident: " + e.getMessage());
        }
    }
// trials for edit and delete resident functions
    public static boolean editResident(String oldEmail, String newName, String newEmail, int newContactInfo) {
        for (ResidentController resident : residents) {
            if (resident.email.equalsIgnoreCase(oldEmail)) {
                resident.setResidentName(newName);
                resident.setEmail(newEmail);
                resident.setContactInfo(newContactInfo);
                saveAllResidentsToFile();
                return true;
            }
        }
        return false;
    }

    public static boolean deleteResident(String email) {
        Iterator<ResidentController> iterator = residents.iterator();
        while (iterator.hasNext()) {
            ResidentController resident = iterator.next();
            if (resident.email.equalsIgnoreCase(email)) {
                iterator.remove();
                saveAllResidentsToFile();
                return true;
            }
        }
        return false;
    }

    private static void saveAllResidentsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (ResidentController resident : residents) {
                writer.write(resident.residentName + "," + resident.email + "," + resident.contactInfo + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error saving residents: " + e.getMessage());
        }
    }
}

class ResidentControllerUI {

    private ResidentController controller;
    private JFrame frame;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField contactField;
    private JTextArea servicesArea;
    private JTextArea costArea;

    public ResidentControllerUI() {
        controller = new ResidentController.Builder().build();
        setupUI();
    }

    private void setupUI() {
        frame = new JFrame("Resident Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Resident Details"));

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Contact Info:"));
        contactField = new JTextField();
        inputPanel.add(contactField);

        JButton addButton = new JButton("Add Resident");
        addButton.addActionListener(new AddResidentListener());
        inputPanel.add(addButton);

        frame.add(inputPanel, BorderLayout.NORTH);

        JPanel servicePanel = new JPanel(new BorderLayout());
        servicePanel.setBorder(BorderFactory.createTitledBorder("Available Services"));

        servicesArea = new JTextArea();
        servicesArea.setText(controller.getServicesList());
        servicesArea.setEditable(false);
        servicePanel.add(new JScrollPane(servicesArea), BorderLayout.CENTER);

        JButton calculateButton = new JButton("Calculate Cost");
        calculateButton.addActionListener(new CalculateCostListener());
        servicePanel.add(calculateButton, BorderLayout.SOUTH);

        frame.add(servicePanel, BorderLayout.CENTER);

        costArea = new JTextArea(5, 40);
        costArea.setEditable(false);
        costArea.setBorder(BorderFactory.createTitledBorder("Total Cost"));
        frame.add(new JScrollPane(costArea), BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private class AddResidentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String name = nameField.getText();
                String email = emailField.getText();
                int contact = Integer.parseInt(contactField.getText());

                if (!ResidentController.isNewResident(name, email)) {
                    JOptionPane.showMessageDialog(frame, "Resident already exists!");
                    return;
                }

                controller.setResidentName(name);
                controller.setEmail(email);
                controller.setContactInfo(contact);

                ResidentController.saveResidentToFile(controller);

                JOptionPane.showMessageDialog(frame, "Resident added successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        }
    }

    private class CalculateCostListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String input = JOptionPane.showInputDialog(frame, "Enter service number to add:");
                int serviceIndex = Integer.parseInt(input);

                controller.addService(serviceIndex);
                costArea.setText("Total Cost: $" + controller.getCost());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ResidentControllerUI::new);
    }
}
