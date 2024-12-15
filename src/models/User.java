package models;

public class User {
    private String username;
    private String password;
    private char role;

    public User(String username, String password, char role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public char getRole() {
        return role;
    }
}
