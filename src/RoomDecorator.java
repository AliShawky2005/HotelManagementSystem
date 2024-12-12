
public abstract class RoomDecorator extends Room {

    protected Room decoratedRoom;

    public RoomDecorator(Room decoratedRoom) {
        super(decoratedRoom.roomNumber);  ;
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

class WiFiDecorator extends RoomDecorator {
    private static final double WIFI_COST = 10.0;


    public WiFiDecorator(Room decoratedRoom) {
        super(decoratedRoom);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", with WiFi";
    }

    @Override
    public double getPrice() {
        return super.getPrice() + WIFI_COST;
    }
}

// Late Checkout Decorator
class LateCheckoutDecorator extends RoomDecorator {
    private static final double LATE_CHECKOUT_COST = 15.0;

    public LateCheckoutDecorator(Room decoratedRoom) {
        super(decoratedRoom);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", with Late Checkout";
    }

    @Override
    public double getPrice() {
        return super.getPrice() + LATE_CHECKOUT_COST;
    }
}


// Breakfast Decorator
class BreakfastDecorator extends RoomDecorator {
    private static final double BREAKFAST_COST = 20.0;

    public BreakfastDecorator(Room decoratedRoom) {
        super(decoratedRoom);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", with Breakfast";
    }

    @Override
    public double getPrice() {
        return super.getPrice() + BREAKFAST_COST;
    }
}
