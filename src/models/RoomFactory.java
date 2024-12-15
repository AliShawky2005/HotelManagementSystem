package models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RoomFactory {

    public static Room createRoom(int roomNumber, String roomType, int numberOfNights) {
        try {
            roomType = roomType.toLowerCase();
            // Validate room type
            if (!roomType.equals("single") && !roomType.equals("double") && !roomType.equals("triple")) {
                throw new IllegalArgumentException("Invalid room type: " + roomType);
            }

            // Create room based on type and number of nights
            Room room = null;
            switch (roomType.toLowerCase()) {
                case "single":
                    room = new singleRoom(roomNumber, numberOfNights);
                    break;
                case "double":
                    room = new doubleRoom(roomNumber, numberOfNights);
                    break;
                case "triple":
                    room = new tripleRoom(roomNumber, numberOfNights);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid room type: " + roomType);
            }

            // Save the room details to a file
            saveRoomToFile(room);

            return room;
        } catch (Exception e) {
            System.out.println("Error creating room: " + e.getMessage());
            return null;
        }
    }

    // Method to save room data to a file
    private static void saveRoomToFile(Room room) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("rooms.txt", true))) {
            // Save the room data in a comma-separated format
            writer.write(room.toFileString());
            writer.newLine(); // New line after each room entry
            System.out.println("Room details saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving room to file: " + e.getMessage());
        }
    }
}
