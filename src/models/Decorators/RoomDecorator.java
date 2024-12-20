package models;

public abstract class RoomDecorator extends Room {

    protected Room decoratedRoom;

    public RoomDecorator(Room decoratedRoom) {
        super(decoratedRoom.roomNumber,decoratedRoom.numberOfNights, decoratedRoom.residentEmail);  ;
        this.decoratedRoom = decoratedRoom;
    }

    @Override
    public String getDescription() {
        return decoratedRoom.getDescription();
    }

    @Override
    public double getPrice() {
        return decoratedRoom.getPrice();
    }
}


