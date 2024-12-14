import java.io.*;
import java.util.HashMap;

//for reading and using data from files (users,workers,residents,rooms)
public class DataStore {
    private static final String FILE_NAME = "users.txt";
    private static HashMap<String, User> users = new HashMap<>();

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
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
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
            System.out.println("User file not found. A new one will be created.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save users to file
    private static void saveUsersToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (User user : users.values()) {
                bw.write(user.getUsername() + "," + user.getPassword() + "," + user.getRole());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
