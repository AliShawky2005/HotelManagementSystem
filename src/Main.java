
import java.util.Scanner; // Scanner used for input

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the room number: ");
        int roomNumber = Integer.parseInt(scanner.nextLine());
        Room room = RoomFactory.createRoom(roomNumber);
        room = new WiFiDecorator(room);


        if (room != null) {

            System.out.println("\nRoom created successfully:");
            System.out.println("Room Price: $" + room.getPrice());
            System.out.println("Room Description: " + room.getDescription());
            System.out.println(room.bookRoom());
        } else {

            System.out.println("Sorry to inform you that, but no room was created.");
        }

        scanner.close(); // Close the scanner to avoid resource leaks

    }
}