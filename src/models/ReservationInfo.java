package models;

public class ReservationInfo {
    private int roomNumber;
    private String residentEmail;
    private String description;
    private int numberOfNights;
    private double totalPrice;
    private String reservationDate;

    // Constructor to initialize ReservationInfo object
    public ReservationInfo(int roomNumber, String residentEmail, String description, int numberOfNights, double totalPrice, String reservationDate) {
        this.roomNumber = roomNumber;
        this.residentEmail = residentEmail;
        this.description = description;
        this.numberOfNights = numberOfNights;
        this.totalPrice = totalPrice;
        this.reservationDate = reservationDate;
    }

    // Getters for each attribute
    public int getRoomNumber() {
        return roomNumber;
    }

    public String getResidentEmail() {
        return residentEmail;
    }

    public String getDescription() {
        return description;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getReservationDate() {
        return reservationDate;
    }

}
