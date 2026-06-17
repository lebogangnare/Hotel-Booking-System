package com.hotelbooking.service;

import com.hotelbooking.model.Booking;

public class ChainHotel extends Hotel {

    public ChainHotel(String hotelName) {
        super(hotelName);
    }

    @Override
    protected void checkIn(Booking booking) {
        System.out.println(getClass().getSimpleName() + " — Booking #" + booking.bookingId() + " confirmed for " + booking.guestName() + ". Room " + booking.room().roomNumber() + " assigned for " + booking.nights() + " night(s). Total: R" + booking.totalCost() + ".");
    }
}
