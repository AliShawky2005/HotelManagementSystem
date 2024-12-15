package models;

public class WiFiDecorator extends RoomDecorator {
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
