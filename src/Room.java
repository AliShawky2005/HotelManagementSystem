public abstract class Room {
    protected int roomNumber;
    protected   String description;
    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
        description = "Unknown";
    }
    public abstract double getPrice();

    public String getDescription() {
        return description;
    }


    public String bookRoom() {
        return "Room number: " + roomNumber + " has been booked.";
    }
}

class singleRoom extends Room{


    public singleRoom(int roomNumber) {
        super(roomNumber);
        description = "Single Room";
    }

    @Override
    public double getPrice() {
        return 100.0;
    }
}

class doubleRoom extends Room {
    public doubleRoom(int roomNumber) {
        super(roomNumber);
        description = "Double Room";

    }

    @Override
    public double getPrice() {
        return 200.0;
    }
}

class tripleRoom extends Room{

    public tripleRoom(int roomNumber) {
        super(roomNumber);
        description = "Triple Room";

    }

    @Override
    public double getPrice() {
        return 300.0;
    }
}