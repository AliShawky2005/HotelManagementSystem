package models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class RoomFactory {

    // Factory Method to Create Room Based on Room Type
    public static Room createRoom(int roomNumber, String roomType, int numberOfNights, String residentEmail) {
        try {
            roomType = roomType.toLowerCase();
            // Validate room type
            if (!roomType.equals("single") && !roomType.equals("double") && !roomType.equals("triple")) {
                throw new IllegalArgumentException("Invalid room type: " + roomType);
            }

            // Create room based on type and number of nights
            Room room = null;
            switch (roomType) {
                case "single":
                    room = new singleRoom(roomNumber, numberOfNights, residentEmail);
                    break;
                case "double":
                    room = new doubleRoom(roomNumber, numberOfNights, residentEmail);
                    break;
                case "triple":
                    room = new tripleRoom(roomNumber, numberOfNights, residentEmail);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid room type: " + roomType);
            }
            return room;

        } catch (Exception e) {
            System.out.println("Error creating room: " + e.getMessage());
            return null;
        }
    }

    // Method to Save Reservation Details to a File
    public static void saveRoomToFile(Room room) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reservations.txt", true))) {
            // Get current date for reservation
            String currentDate = LocalDate.now().toString();

            // Save reservation details with current date
            writer.write(room.getRoomNumber() + "," +
                    room.getResidentEmail() + "," +
                    room.getDescription() + "," +
                    room.getNumberOfNights() + "," +
                    room.calculateTotalPrice() + "," +
                    currentDate);
            writer.newLine(); // Add new line after each reservation
            System.out.println("Reservation details saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving reservation to file: " + e.getMessage());
        }
    }

    // Method to Update Room Availability in Rooms File
    public static void updateRoomAvailability(int roomNumber, String availabilityStatus) {
        // Placeholder logic: Reads from "rooms.txt" and updates availability.
        // Actual implementation would involve reading, modifying, and overwriting the file.
        System.out.println("Room availability updated for Room " + roomNumber + " to: " + availabilityStatus);
    }
}
