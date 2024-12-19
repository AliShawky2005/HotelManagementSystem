package models;

import java.util.regex.Pattern;

public class Resident {
    private String residentName;
    private String email;
    private int contactInfo;


    private int roomNumber;

    public Resident(String residentName, String email, int contactInfo, int roomNumber) {
        this.residentName = residentName;
        this.email = email;
        this.contactInfo = contactInfo;
        this.roomNumber = roomNumber;
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

    public int getRoomNumber() {
        return roomNumber;
    }



}
