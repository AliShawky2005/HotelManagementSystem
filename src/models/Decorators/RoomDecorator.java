package models.Decorators;

import models.Room;

public abstract class RoomDecorator extends Room {

    protected Room decoratedRoom;

    public RoomDecorator(Room decoratedRoom) {
        super(decoratedRoom.getRoomNumber(), decoratedRoom.getNumberOfNights(), decoratedRoom.getResidentEmail());  ;
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


