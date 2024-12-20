package controllers;

import gui.RoomAssignmentForm;
import models.Room;
import models.RoomFactory;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public  class RoomStatus {

    public static List<RoomAssignmentForm.RoomInfo> availableRooms;

    static {
        availableRooms = DataStore.loadRoomDataFromFile();

    }

    public static void populateRoomComboBox(JComboBox<String> roomNumberComboBox) {
        roomNumberComboBox.removeAllItems();
        for (RoomAssignmentForm.RoomInfo room : availableRooms) {
            if (room.isAvailable()) {
                roomNumberComboBox.addItem(String.valueOf(room.getRoomNumber()));
            }
        }
    }
    // Save reservation to file
    public static void saveReservation(Room room) {
        RoomFactory.saveReservationToFile(room);
    }

    // Update rooms.txt file after marking a room as unavailable
    public static void updateRoomsFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (RoomAssignmentForm.RoomInfo room : availableRooms) {
                writer.write(room.getRoomNumber() + "," + room.getDescription() + "," + room.isAvailable());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error updating room file: " + e.getMessage());
        }
    }

    public static String getRoomDescription(int selectedRoomNumber) {
        for (RoomAssignmentForm.RoomInfo room : availableRooms) {
            if (room.getRoomNumber() == selectedRoomNumber) {
                return room.getDescription();
            }
        }
        return null;
    }

    public static void changeRoomStatus(int selectedRoomNumber, boolean availability)
    {
        for (RoomAssignmentForm.RoomInfo r : availableRooms) {
            if (r.getRoomNumber() == selectedRoomNumber) {
                r.setAvailable(availability);
                break;
            }
        }
    }
}
