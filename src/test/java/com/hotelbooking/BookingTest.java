package com.hotelbooking;

import com.hotelbooking.model.Booking;
import com.hotelbooking.model.BookingStatus;
import com.hotelbooking.model.Room;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BookingTest {

    @Test
    public void shouldCreateBookingAndComputeTotalCost() {
        Room room = new Room("101", 600.0);
        Booking booking = new Booking(1, "Alice", room, 3);

        assertEquals(1, booking.bookingId());
        assertEquals("Alice", booking.guestName());
        assertEquals(3, booking.nights());
        assertEquals(1800.0, booking.totalCost(), 0.001);
        assertEquals(BookingStatus.PENDING, booking.status());
    }

    @Test
    public void shouldUpdateStatusAndFormatToString() {
        Room room = new Room("101", 600.0);
        Booking booking = new Booking(2, "Bob", room, 2);
        booking.updateStatus(BookingStatus.CONFIRMED);

        assertEquals(BookingStatus.CONFIRMED, booking.status());
        String text = booking.toString();
        assertEquals(true, text.contains("Booking 2:"));
    }
}
