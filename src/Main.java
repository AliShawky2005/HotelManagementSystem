/*
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for room number
        System.out.println("Enter the room number: ");
        int roomNumber = Integer.parseInt(scanner.nextLine());

        // Create a room using RoomFactory (this will prompt for room type and number of nights)
        Room room = RoomFactory.createRoom(roomNumber);

        if (room != null) {
            // Apply decorators to the room (WiFi and Breakfast)
            room = new WiFiDecorator(room);
            room = new BreakfastDecorator(room);

            // Display room details
            System.out.println("\nRoom created successfully:");
            System.out.println("Room Price: $" + room.getPrice() + " per night");
            System.out.println("Room Description: " + room.getDescription());
            System.out.println("Total Price for " + room.numberOfNights + " nights: $" + room.calculateTotalPrice());
            System.out.println(room.bookRoom());
        } else {
            System.out.println("Sorry to inform you that, but no room was created.");
        }

        scanner.close(); // Close the scanner to avoid resource leaks
    }
}
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        // Launch the UI
        new HotelManagementUI();
    }
}
