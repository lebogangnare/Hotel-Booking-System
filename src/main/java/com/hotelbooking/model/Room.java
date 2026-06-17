package com.hotelbooking.model;

public class Room {
    private final String roomNumber;
    private double pricePerNight;
    private boolean available;

    public Room(String roomNumber, double pricePerNight) {
        if (roomNumber == null || roomNumber.isEmpty()) {
            throw new IllegalArgumentException("roomNumber must not be null or empty");
        }
        if (pricePerNight < 0) {
            throw new IllegalArgumentException("pricePerNight must not be negative");
        }
        this.roomNumber = roomNumber;
        this.pricePerNight = pricePerNight;
        this.available = true;
    }

    public String roomNumber() {
        return roomNumber;
    }

    public double pricePerNight() {
        return pricePerNight;
    }

    public boolean isAvailable() {
        return available;
    }

    public void updatePrice(double pricePerNight) {
        if (pricePerNight < 0) {
            throw new IllegalArgumentException("pricePerNight must not be negative");
        }
        this.pricePerNight = pricePerNight;
    }

    public void setAvailability(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        String availability = available ? "Available" : "Unavailable";
        return "Room " + roomNumber + ": R" + pricePerNight + "/night [" + availability + "]";
    }
}
