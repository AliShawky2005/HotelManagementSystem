package models;

import java.util.regex.Pattern;

public class Resident {
    private String residentName;
    private String email;
    private int contactInfo;

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


    public int getContactInfo() {
        return contactInfo;
    }




}
