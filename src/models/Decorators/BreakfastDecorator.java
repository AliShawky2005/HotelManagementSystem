package models.Decorators;

import models.Decorators.RoomDecorator;
import models.Room;

// Breakfast Decorator
public class BreakfastDecorator extends RoomDecorator {
    private static final double BREAKFAST_COST = 20.0;

    public BreakfastDecorator(Room decoratedRoom) {
        super(decoratedRoom);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " - with Breakfast";
    }

    @Override
    public double getPrice() {
        return super.getPrice() + BREAKFAST_COST;
    }
}
