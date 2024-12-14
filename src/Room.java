public abstract class Room {
    protected int roomNumber;
    protected String description;
    protected int numberOfNights;

    public Room(int roomNumber, int numberOfNights) {
        this.roomNumber = roomNumber;
        this.numberOfNights = numberOfNights;
        this.description = "Unknown";
    }

    public abstract double getPrice();

    public String getDescription() {
        return description;
    }

    public String bookRoom() {
        return "Room number: " + roomNumber + " has been booked for " + numberOfNights + " nights.";
    }

    public double calculateTotalPrice() {
        return getPrice() * numberOfNights;
    }
    // Method to return room data as a string
    public String toFileString() {
        return roomNumber + "," + description + "," + numberOfNights + "," + getPrice() + "," + calculateTotalPrice();
    }
}

class singleRoom extends Room {
    public singleRoom(int roomNumber, int numberOfNights) {
        super(roomNumber, numberOfNights);
        description = "Single Room";
    }

    @Override
    public double getPrice() {
        return 100.0;
    }
}

class doubleRoom extends Room {
    public doubleRoom(int roomNumber, int numberOfNights) {
        super(roomNumber, numberOfNights);
        description = "Double Room";
    }

    @Override
    public double getPrice() {
        return 200.0;
    }
}

class tripleRoom extends Room {
    public tripleRoom(int roomNumber, int numberOfNights) {
        super(roomNumber, numberOfNights);
        description = "Triple Room";
    }

    @Override
    public double getPrice() {
        return 300.0;
    }
}
