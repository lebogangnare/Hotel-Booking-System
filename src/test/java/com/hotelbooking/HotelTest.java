package com.hotelbooking;

import com.hotelbooking.model.Booking;
import com.hotelbooking.model.Room;
import com.hotelbooking.model.RoomType;
import com.hotelbooking.service.BoutiqueHotel;
import com.hotelbooking.service.ChainHotel;
import com.hotelbooking.service.Hotel;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class HotelTest {

    private ByteArrayOutputStream output;
    private PrintStream originalOut;

    @Before
    public void setUp() {
        output = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(output));
    }

    @Test
    public void shouldPlaceBookingAndProcessBoutiqueHotel() {
        Hotel boutique = new BoutiqueHotel("Boutique");
        Room room = new Room("101", 500.0);
        boutique.addRoomType(new RoomType("Single", Arrays.asList(room)));

        Booking booking = boutique.placeBooking("Single", "Alice", 2);
        assertEquals(1, booking.bookingId());
        assertEquals(false, booking.room().isAvailable());

        Booking result = boutique.processNextBooking();
        assertEquals(booking, result);
        assertEquals("BoutiqueHotel — Welcome, Alice! Your 101 has been personally prepared for your 2-night stay." + System.lineSeparator(), output.toString());
    }

    @Test
    public void shouldProcessChainHotelAndProduceConfirmationMessage() {
        Hotel chain = new ChainHotel("Chain");
        Room room = new Room("102", 750.0);
        chain.addRoomType(new RoomType("Double", Arrays.asList(room)));

        chain.placeBooking("Double", "Bob", 1);
        chain.processNextBooking();
        assertEquals("ChainHotel — Booking #1 confirmed for Bob. Room 102 assigned for 1 night(s). Total: R750.0." + System.lineSeparator(), output.toString());
    }
}
