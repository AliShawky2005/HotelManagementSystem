package models.Decorators;

import models.Decorators.RoomDecorator;
import models.Room;

// Late Checkout Decorator
public class LateCheckoutDecorator extends RoomDecorator {
    private static final double LATE_CHECKOUT_COST = 15.0;

    public LateCheckoutDecorator(Room decoratedRoom) {
        super(decoratedRoom);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " - with Late Checkout";
    }

    @Override
    public double getPrice() {
        return super.getPrice() + LATE_CHECKOUT_COST;
    }
}
