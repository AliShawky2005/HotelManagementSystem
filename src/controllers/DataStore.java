package controllers;



import gui.RoomAssignmentForm;
import models.Room;
import models.User;import models.Worker;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//for rading and using data from files (users,workers,residents,rooms)
public class DataStore {
    private static final String USERS_FILE = "users.txt";
    private static final String WORKERS_FILE = "workers.txt";

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
}
