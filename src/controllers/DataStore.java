package controllers;

import gui.RoomAssignmentForm;
import models.*;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//for reading and using data from files (users,workers,residents,rooms)
public class DataStore {
    private static final String USERS_FILE = "users.txt";
    private static final String WORKERS_FILE = "workers.txt";
    private static final String RESIDENTS_FILE = "residents.txt";

    private static final String PAST_RESERVATIONS_FILE = "past_reservations.txt";
    private static final String CURRENT_RESERVATIONS_FILE = "current_reservations.txt";


    private static final String ROOMS_FILE = "rooms.txt";
    private static HashMap<String, User> users = new HashMap<>();

    public static User loggedInUser = null;

    // Load users from file at startup
    static {
        loadUsersFromFile();
    }

    public static HashMap<String, User> getUsers() {
        return users;
    }

    public static void addUser(String username, User user) {
        users.put(username, user);
        saveUsersToFile(); // Save updated users to file
    }

    // Load users from file
    private static void loadUsersFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    char role = parts[2].charAt(0);
                    users.put(username, new User(username, password, role));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("models.User file not found. A new one will be created.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save users to file
    private static void saveUsersToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users.values()) {
                bw.write(user.getUsername() + "," + user.getPassword() + "," + user.getRole());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<Worker> loadWorkers() {

        ArrayList<Worker> workers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(WORKERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String name = parts[0];
                    String phoneNumber = parts[1];
                    double salary = Double.parseDouble(parts[2]);
                    String jobTitle = parts[3];
                    workers.add(new Worker(name, phoneNumber, salary, jobTitle));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading workers from file: " + e.getMessage());
        }
        return workers;
    }

    public static List<RoomAssignmentForm.RoomInfo> loadRoomDataFromFile() {
        List<RoomAssignmentForm.RoomInfo> rooms = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ROOMS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int roomNumber = Integer.parseInt(parts[0].trim());
                    String description = parts[1].trim();
                    boolean available = Boolean.parseBoolean(parts[2].trim());
                    rooms.add(new RoomAssignmentForm.RoomInfo(roomNumber, description, available));
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading room data: " + e.getMessage());
        }
        return rooms;
    }

    public static void saveWorkers(ArrayList<Worker> workers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(WORKERS_FILE))) {
            for (Worker worker : workers) {
                writer.write(worker.getName() + "," +
                        worker.getPhoneNumber() + "," +
                        worker.getSalary() + "," +
                        worker.getJobTitle());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing workers to file: " + e.getMessage());
        }
    }

    public static List<Resident> loadResidentsFromFile() {

        List<Resident> residents = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(RESIDENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String name = parts[0];
                    String email = parts[1];
                    int contactInfo = Integer.parseInt(parts[2]);
                    int roomNumber = Integer.parseInt(parts[3]);
                    Resident resident = new Resident(name,email,contactInfo,roomNumber);
                    residents.add(resident);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading residents: " + e.getMessage());
        }

        return residents;
    }

    public static void saveResidents(List<Resident> residents) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESIDENTS_FILE))) {
            for (Resident resident : residents) {
                writer.write(resident.getResidentName() + "," +
                        resident.getEmail() + "," +
                        resident.getContactInfo() + "," +
                        resident.getRoomNumber());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing resident to file: " + e.getMessage());
        }
    }

    // Method to load reservations from a file
    public static ArrayList<ReservationInfo> loadReservationsFromFile() {
        ArrayList<ReservationInfo> reservations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CURRENT_RESERVATIONS_FILE))) {
            String line;

            while( (line = reader.readLine()) != null) {

                String[] parts = line.split(",");
                if (parts.length == 6) {
                    try {
                        // Parse reservation details from the file line
                        int roomNumber = Integer.parseInt(parts[0].trim());
                        String residentEmail = parts[1].trim();
                        String description = parts[2].trim();
                        int numberOfNights = Integer.parseInt(parts[3].trim());
                        double totalPrice = Double.parseDouble(parts[4].trim());
                        String reservationDate = parts[5].trim();

                        ReservationInfo reservation = new ReservationInfo(roomNumber, residentEmail,
                                description,numberOfNights,totalPrice,reservationDate);

                        reservations.add(reservation);

                    } catch (NumberFormatException e) {
                        // Handle any data conversion issues (e.g., invalid number format)
                        System.out.println("Error parsing reservation data: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading reservations from file: " + e.getMessage());
        }
        return reservations;
    }

    public static void saveReservationsToFile(List<ReservationInfo> reservations) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CURRENT_RESERVATIONS_FILE))) {
            // Loop through each reservation and write its details to the file
            for (ReservationInfo reservation : reservations) {
                writer.write(reservation.getRoomNumber() + "," +
                        reservation.getResidentEmail() + "," +
                        reservation.getDescription() + "," +
                        reservation.getNumberOfNights() + "," +
                        reservation.getTotalPrice() + "," +
                        reservation.getReservationDate());
                writer.newLine(); // Add a new line after each reservation
            }
            System.out.println("Reservations saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving reservations to file: " + e.getMessage());
        }
    }

    public static void addReservationToPastReservations(ReservationInfo reservation) {
        // Define the path for the past reservations file
        String pastReservationsFilePath = "past_reservations.csv";

        // Open file in append mode
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PAST_RESERVATIONS_FILE, true))) {
            // Write reservation details to the file in CSV format
            String reservationData = reservation.getRoomNumber() + "," +
                    reservation.getResidentEmail() + "," +
                    reservation.getDescription() + "," +
                    reservation.getNumberOfNights() + "," +
                    reservation.getTotalPrice() + "," +
                    reservation.getReservationDate();

            // Write the data as a single line in the file
            writer.write(reservationData);
            writer.newLine();  // Move to the next line for the next reservation

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
