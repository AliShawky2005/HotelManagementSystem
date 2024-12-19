package controllers;

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
            for (controllers.ResidentController resident : residents) {
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

