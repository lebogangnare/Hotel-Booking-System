package com.hotelbooking.model;

public class Booking {
    // TODO: implement Booking based on the workshop requirements
    private int bookingId;
    private String guestName;
    private Room room;
    private int nights;
    private BookingStatus status;

    public Booking(int bookingId, String guestName, Room room, int nights) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.room = room;
        this.nights = nights;
        this.status = BookingStatus.PENDING;
    }

    // Getters and setters for all fields
  
}
