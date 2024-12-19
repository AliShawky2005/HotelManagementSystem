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

   // private int servicesReceived;
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
      //  this.servicesReceived = builder.servicesReceived;
        this.cost = builder.cost;
    }

    public static class Builder {
        private String residentName;
        private String email;
        private int contactInfo;
     //   private int servicesReceived;
private double cost;
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
        public Builder setCost(double cost) {
            this.cost = cost;
            return this;
        }

       /*public double getCost() {
            return cost;
        }


        */
   /*    // public Builder setServicesReceived(int servicesReceived) {
            this.servicesReceived = servicesReceived;
            return this;
        }


    */
        public ResidentController build() {
            return new ResidentController(this);
        }
    }

/*    public int getServicesReceived() {
        return servicesReceived;
    }


 */
/*
    public void setCost(double cost) {
        this.cost = cost;
    }



    public String getEmail() {
        return email;
    }
*/
    public String getResidentName() {
        return residentName;
    }


    public int getContactInfo() {
        return contactInfo;
    }
    public double addService(int serviceIndex) {
        if (serviceIndex >= 1 && serviceIndex <= services.length) {
            double Ncost=0.0;
            cost += calculateCost(serviceIndex,Ncost);
        } else {
            throw new IllegalArgumentException("Invalid service index.");
        }
   return cost;
    }

    public double reduceService(int serviceIndex) {
        if (serviceIndex >= 1 && serviceIndex <= services.length) {
            Double NCost=0.0;
           cost -= calculateCost(serviceIndex,NCost);
           if (cost <0)
           {
              return cost =0;
           }
        } else {
            throw new IllegalArgumentException("Invalid service index.");
        }
        return cost;
    }

    //services requested by resident

    public double calculateCost(int servicesReceived,double cost) {

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

    public String getServicesList() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < services.length; i++) {
            sb.append((i + 1)).append(". ").append(services[i]).append("\n");
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

    private boolean validateEmail(String email) {
        String emailRegex = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }
    private static void loadResidentsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    ResidentController resident = new Builder()
                            .setResidentName(parts[0])
                            .setEmail(parts[1])
                            .setContactInfo(Integer.parseInt(parts[2]))
                            .setCost(Double.parseDouble(parts[3]))
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
/*    public static boolean isNewResident( String email) {
        for (ResidentController resident : residents) {
            if (resident.email.equalsIgnoreCase(email)) {
                return false;
            }
        }
        return true;
    }


 */
    public ResidentController findResidentByEmail(String email) {

        for (ResidentController resident : ResidentController.residents) {
            if (resident.email.equalsIgnoreCase(email)) {
                return resident;
            }
        }
        return null;
    }



    public static void saveResidentToFile(ResidentController resident) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(resident.residentName + "," + resident.email + "," + resident.contactInfo + "," + resident.cost + "\n");
        } catch (IOException e) {
            System.err.println("Error saving resident: " + e.getMessage());
        }
    }
    // delete resident function
    public  boolean deleteResident(String email) {
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
                writer.write(resident.residentName + "," + resident.email + "," + resident.contactInfo +","+resident.cost);
            }
        } catch (IOException e) {
            System.err.println("Error saving residents: " + e.getMessage());
        }
    }
}

class ResidentMenuUI {

    private ResidentController controller;
    private JFrame frame;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField contactField;
    private JTextArea servicesArea;
    private JTextArea costArea;
    private JTextField OldEmailField;

    public ResidentMenuUI() {

        controller = new ResidentController.Builder().build();
        setupMainMenu();
    }

    private void setupMainMenu() {
        frame = new JFrame("Resident Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 1, 10, 10));

        JButton addResidentButton = new JButton("Add Resident");
        addResidentButton.addActionListener(e -> addResidentUI());

        JButton deleteResidentButton = new JButton("Delete Resident");
        deleteResidentButton.addActionListener(e -> deleteResidentUI());

        JButton editResidentButton = new JButton("Edit Resident");
        editResidentButton.addActionListener(e -> editResidentUI());

        frame.add(addResidentButton);
        frame.add(deleteResidentButton);
        frame.add(editResidentButton);

        frame.setVisible(true);
    }

    private void addResidentUI() {
        frame = new JFrame("Resident Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 3, 10, 10));
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

        JButton calculateButton = new JButton("Add Service");
        calculateButton.addActionListener(new CalculateCostListener());
        inputPanel.add(calculateButton);

        calculateButton = new JButton("remove Service");
        calculateButton.addActionListener(new ManagerCostListener());
        inputPanel.add(calculateButton);

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

        costArea = new JTextArea(5, 40);
        costArea.setEditable(false);
        costArea.setBorder(BorderFactory.createTitledBorder("Total Cost"));
        servicePanel.add(new JScrollPane(costArea), BorderLayout.SOUTH);

        frame.add(servicePanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> setupMainMenu());
        frame.add(backButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void deleteResidentUI() {
        frame.getContentPane().removeAll(); // Clear previous UI components
        frame.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel emailLabel = new JLabel("Enter Email of Resident to Delete:");
        emailField = new JTextField();
        JButton deleteButton = new JButton("Delete Resident");

        deleteButton.addActionListener(new DeleteResidentListener());

        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(deleteButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> setupMainMenu());
        frame.add(backButton);

        frame.revalidate();
        frame.repaint();
    }

    private void editResidentUI() {
        frame = new JFrame("Resident Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(6, 4, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("please enter the old email"));

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Old Email:"));
        OldEmailField = new JTextField();
        inputPanel.add(OldEmailField);

        inputPanel.add(new JLabel("New Email:"));
      emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Contact Info:"));
        contactField = new JTextField();
        inputPanel.add(contactField);

        JButton calculateButton = new JButton("Add Service");
        calculateButton.addActionListener(new CalculateCostListener());
        inputPanel.add(calculateButton);

        calculateButton = new JButton("remove Service");
        calculateButton.addActionListener(new ManagerCostListener());
        inputPanel.add(calculateButton);

        JButton viewButton = new JButton("view details Resident");
        viewButton.addActionListener(new retrieveResidentListener());
        inputPanel.add(viewButton);

        JButton addButton = new JButton("save changes");
        addButton.addActionListener(new saveButtonListener());
        inputPanel.add(addButton);

        frame.add(inputPanel, BorderLayout.NORTH);

        JPanel servicePanel = new JPanel(new BorderLayout());
        servicePanel.setBorder(BorderFactory.createTitledBorder("Available Services"));

        servicesArea = new JTextArea();
        servicesArea.setText(controller.getServicesList());
        servicesArea.setEditable(false);
        servicePanel.add(new JScrollPane(servicesArea), BorderLayout.CENTER);

        costArea = new JTextArea(5, 40);
        costArea.setEditable(false);
        costArea.setBorder(BorderFactory.createTitledBorder("Total Cost"));
        servicePanel.add(new JScrollPane(costArea), BorderLayout.SOUTH);

        frame.add(servicePanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> setupMainMenu());
        frame.add(backButton, BorderLayout.SOUTH);

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


    private class saveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {

               // ResidentController resident = mode.getResident();
                //    controller.deleteResident(resident.getEmail());
                //  new AddResidentListener();

                String newName = nameField.getText();
                int newContactInfo = Integer.parseInt(contactField.getText());
                String newEmail = emailField.getText();
                String oldEmail = OldEmailField.getText();


                if (controller != null) {
                    controller.setResidentName(newName);
                    controller.setEmail(newEmail);
                    controller.setContactInfo(newContactInfo);
                    controller.deleteResident(oldEmail);
                    ResidentController.saveResidentToFile(controller);
                    JOptionPane.showMessageDialog(frame, "Resident details updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Resident not found!");
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }

        }

    }




    private class DeleteResidentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String email = emailField.getText();

                if (email.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Email field cannot be empty!");
                    return;
                }

                boolean success = controller.deleteResident(email);
                if (success) {
                    JOptionPane.showMessageDialog(frame, "Resident deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Resident not found!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        }
    }


    private class retrieveResidentListener implements ActionListener {

        ResidentController resident;
        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                nameField.setEnabled(false);
                contactField.setEnabled(false);
                emailField.setEnabled(false);
                String email = OldEmailField.getText();

                resident = controller.findResidentByEmail(email);

                if (resident != null) {
                    double cost = resident.getCost();
                    nameField.setText(resident.getResidentName());
                    contactField.setText(String.valueOf(resident.getContactInfo()));
                    costArea.setText(String.valueOf(cost));
                    // Enable editing fields
                    nameField.setEnabled(true);
                    contactField.setEnabled(true);
                    emailField.setEnabled(true);

                } else {

                    JOptionPane.showMessageDialog(frame, "Resident not found!");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }

        }

        public double getResidentCost() {
            return resident != null ? resident.getCost() : 0;
        }
    }

    private class CalculateCostListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String input = JOptionPane.showInputDialog(frame, "Enter service number to add:");
                int serviceIndex = Integer.parseInt(input);

             //   double residentCost = new retrieveResidentListener().getResidentCost();
                controller.addService(serviceIndex);

                costArea.setText("Total Cost: $" + controller.getCost());

                // ResidentController.saveResidentToFile(controller);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        }

    }

    private class ManagerCostListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                String input = JOptionPane.showInputDialog(frame, "Enter service number to remove:");
                int serviceIndex = Integer.parseInt(input);

               double cost = controller.getCost();
                cost+=controller.reduceService(serviceIndex);
                costArea.setText("Total Cost: $" + controller.getCost());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        }
    }

        public static void main(String[] args) {
        SwingUtilities.invokeLater(ResidentMenuUI::new);
    }
}
