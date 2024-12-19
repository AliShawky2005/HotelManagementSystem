package controllers;

import models.Resident;
import models.Worker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;
import java.io.*;

public class ResidentController {

    private static ResidentController instance;

    private static List<Resident> residents = DataStore.loadResidentsFromFile();

    public List<Resident> getResidents() {
        return residents;
    }


    public static ResidentController getInstance() {
        if (instance == null) {
            instance = new ResidentController();
        }
        return instance;
    }


    public boolean addResident(Resident resident) {
        if (!isNewResident(resident.getEmail(),residents)) {
            return false;
        }
        residents.add(resident);
        DataStore.saveResidents(residents);
        return true;
    }

    public boolean validateEmail(String email) {
        String emailRegex = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    private static boolean isNewResident(String email,List<Resident> residents) {
        for (Resident resident : residents) {
            if (resident.getEmail().equalsIgnoreCase(email)) {
                return false;
            }
        }
        return true;
    }


    public Resident findResidentByEmail(String email) {

        for (Resident resident : residents) {
            if (resident.getEmail().equalsIgnoreCase(email)) {
                return resident;
            }
        }
        return null;
    }

    // delete resident function
    public  boolean deleteResident(String email) {
        Iterator<Resident> iterator = residents.iterator();
        while (iterator.hasNext()) {
            Resident resident = iterator.next();
            if (resident.getEmail().equalsIgnoreCase(email)) {
                iterator.remove();
                DataStore.saveResidents(residents);
                return true;
            }
        }
        return false;
    }


    public boolean updateResident(String email, Resident updatedResident) {
        for (int i = 0; i < residents.size(); i++) {
            if (residents.get(i).getEmail().equals(email)) {
                residents.set(i, updatedResident);
                DataStore.saveResidents(residents);
                return true;
            }
        }
        return false;
    }


}

