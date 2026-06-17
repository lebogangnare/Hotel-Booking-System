package com.hotelbooking;

import com.hotelbooking.model.Room;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RoomTest {

    @Test
    public void shouldCreateRoomWithDefaultAvailability() {
        Room room = new Room("101", 850.0);

        assertEquals("101", room.roomNumber());
        assertEquals(850.0, room.pricePerNight(), 0.001);
        assertTrue(room.isAvailable());
    }

    @Test
    public void shouldUpdatePriceAndAvailability() {
        Room room = new Room("102", 750.0);
        room.updatePrice(800.0);
        room.setAvailability(false);

        assertEquals(800.0, room.pricePerNight(), 0.001);
        assertFalse(room.isAvailable());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNegativePriceUpdate() {
        Room room = new Room("103", 600.0);
        room.updatePrice(-1.0);
    }

    @Test
    public void shouldFormatToString() {
        Room room = new Room("104", 900.0);
        assertEquals("Room 104: R900.0/night [Available]", room.toString());
    }
}
