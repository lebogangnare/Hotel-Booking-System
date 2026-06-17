package com.hotelbooking.model;

public class Booking {
    private final int bookingId;
    private final String guestName;
    private final Room room;
    private final int nights;
    private BookingStatus status;

    public Booking(int bookingId, String guestName, Room room, int nights) {
        if (bookingId <= 0) {
            throw new IllegalArgumentException("bookingId must be positive");
        }
        if (guestName == null || guestName.isEmpty()) {
            throw new IllegalArgumentException("guestName must not be null or empty");
        }
        if (room == null) {
            throw new IllegalArgumentException("room must not be null");
        }
        if (nights <= 0) {
            throw new IllegalArgumentException("nights must be positive");
        }
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.room = room;
        this.nights = nights;
        this.status = BookingStatus.PENDING;
    }

    public int bookingId() {
        return bookingId;
    }

    public String guestName() {
        return guestName;
    }

    public Room room() {
        return room;
    }

    public int nights() {
        return nights;
    }

    public double totalCost() {
        return room.pricePerNight() * nights;
    }

    public BookingStatus status() {
        return status;
    }

    public void updateStatus(BookingStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("status must not be null");
        }
        this.status = status;
    }

    @Override
    public String toString() {
        return "Booking " + bookingId + ": " + guestName + " - Room " + room.roomNumber() + " for " + nights + " night(s), total R" + totalCost() + " [" + status + "]";
    }
}
