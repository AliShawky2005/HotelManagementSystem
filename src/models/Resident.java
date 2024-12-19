package models;

import java.util.regex.Pattern;

public class Resident {
    private String residentName;
    private String email;
    private int contactInfo;
    //private String[] services = {"message", "hotel trip", "hotel transportation", "carrying bags"};

    // private int servicesReceived;
//    private double cost;

    public Resident(String residentName, String email, int contactInfo) {
        this.residentName = residentName;
        this.email = email;
        this.contactInfo = contactInfo;
    }

    public String getResidentName() {
        return residentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (validateEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email format.");
        }
    }

    public int getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(int contactInfo) {
        int length = String.valueOf(contactInfo).length();
        if (length == 5 || length == 11) {
            this.contactInfo = contactInfo;
        } else {
            throw new IllegalArgumentException("Invalid phone number length.");
        }
    }

//    public double getCost() {
//        return cost;
//    }

    private boolean validateEmail(String email) {
        String emailRegex = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }
}
