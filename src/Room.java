public abstract class Room {
    protected int roomNumber;
    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    public abstract double getPrice();
    public String bookRoom() {
        return "Room number: " + roomNumber + " has been booked.";
    }
}

class singleRoom extends Room{

    public singleRoom(int roomNumber) {
        super(roomNumber);
    }

    @Override
    public double getPrice() {
        return 100.0;
    }
}

class doubleRoom extends Room {
    public doubleRoom(int roomNumber) {
        super(roomNumber);
    }

    @Override
    public double getPrice() {
        return 200.0;
    }
}

class tripleRoom extends Room{

    public tripleRoom(int roomNumber) {
        super(roomNumber);
    }

    @Override
    public double getPrice() {
        return 300.0;
    }
}