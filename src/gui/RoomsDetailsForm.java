package gui;

import controllers.DataStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class RoomsDetailsForm {

    public  void showDetails() {
        // Create the main frame
        JFrame frame = new JFrame("Room Status Monitoring");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout()); // Use BorderLayout for better positioning

        // Panel for Room Status
        JPanel roomStatusPanel = new JPanel();
        roomStatusPanel.setLayout(new BoxLayout(roomStatusPanel, BoxLayout.Y_AXIS));

        // Read room data from file
        List<RoomAssignmentForm.RoomInfo> roomList = DataStore.loadRoomDataFromFile();

        // Convert list to array for the JTable
        String[][] roomData = new String[roomList.size()][3]; // 3 columns: Room Number, Description, Status
        for (int i = 0; i < roomList.size(); i++) {
            RoomAssignmentForm.RoomInfo roomInfo = roomList.get(i);
            roomData[i][0] = String.valueOf(roomInfo.getRoomNumber()); // Room Number (converted to String)
            roomData[i][1] = roomInfo.getDescription();               // Description
            roomData[i][2] = roomInfo.isAvailable() ? "Available" : "Not Available"; // Status
        }

        String[] roomColumns = {"Room Number", "Description", "Status"};

        JTable roomTable = new JTable(new DefaultTableModel(roomData, roomColumns));
        JScrollPane roomScrollPane = new JScrollPane(roomTable);
        roomStatusPanel.add(new JLabel("Rooms Details:"));
        roomStatusPanel.add(roomScrollPane);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            frame.dispose(); // Close the current form
            new ManagerDashboard(DataStore.loggedInUser); // Go back to Manager Dashboard
        });

        // Add components to the frame
        frame.add(roomStatusPanel, BorderLayout.CENTER); // Room status in the center
        frame.add(backButton, BorderLayout.SOUTH);       // Back button at the bottom

        // Make the frame visible
        frame.setVisible(true);
    }
}
