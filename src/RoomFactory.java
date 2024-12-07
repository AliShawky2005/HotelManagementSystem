import java.util.Scanner; // Scanner used for input
class RoomFactory {
    public static Room createRoom(int roomNumber) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Enter room type (Single, Double, Triple): ");
                String roomType = scanner.nextLine();

                switch (roomType.toLowerCase()) {
                    case "single":
                        return new singleRoom(roomNumber);
                    case "double":
                        return new doubleRoom(roomNumber);
                    case "triple":
                        return new tripleRoom(roomNumber);
                    default:
                        throw new IllegalArgumentException("Invalid room type: " + roomType);
                }
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("Would you like to try again? (yes/no): ");
                String retry = scanner.nextLine();

                if (retry.equalsIgnoreCase("no")) {
                    System.out.println("Exiting the room creation process.");
                    return null;
                }
            }
        }
    }
}
