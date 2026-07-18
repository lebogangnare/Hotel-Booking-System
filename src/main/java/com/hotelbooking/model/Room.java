package com.hotelbooking.model;

public class Room {
    // TODO: implement Room based on the workshop requirements
    private String roomNumber;
    private double pricePerNight;
    private boolean available;

    public Room(String roomNumber, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.pricePerNight = pricePerNight;
        this.available = true;
    }

    public String roomNumber(){
        return roomNumber;
    }

    public double pricePerNight(){
        return pricePerNight;
    }

    public void updatePrice(double price){
        if (price < 0 ){
            throw new IllegalArgumentException("Price per night cannot be negative");
        }
        this.pricePerNight = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber='" + roomNumber + '\'' +
                ", pricePerNight=" + pricePerNight +
                ", available=" + available +
                '}';
    }
}
