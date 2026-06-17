package com.hotelbooking.service;

import com.hotelbooking.model.Booking;

public class BoutiqueHotel extends Hotel {

    public BoutiqueHotel(String hotelName) {
        super(hotelName);
    }

    @Override
    protected void checkIn(Booking booking) {
        System.out.println(getClass().getSimpleName() + " — Welcome, " + booking.guestName() + "! Your " + booking.room().roomNumber() + " has been personally prepared for your " + booking.nights() + "-night stay.");
    }
}
