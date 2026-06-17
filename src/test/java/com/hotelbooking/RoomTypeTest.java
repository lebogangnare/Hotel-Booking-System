package com.hotelbooking;

import com.hotelbooking.model.Room;
import com.hotelbooking.model.RoomType;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class RoomTypeTest {

    @Test
    public void shouldReturnDefensiveCopyOfRooms() {
        Room room = new Room("101", 650.0);
        RoomType type = new RoomType("Single", Arrays.asList(room));

        List<Room> rooms = type.rooms();
        rooms.add(new Room("102", 700.0));

        assertEquals(1, type.rooms().size());
    }

    @Test
    public void shouldReturnOnlyAvailableRooms() {
        Room availableRoom = new Room("101", 650.0);
        Room unavailableRoom = new Room("102", 700.0);
        unavailableRoom.setAvailability(false);

        RoomType type = new RoomType("Single", Arrays.asList(availableRoom, unavailableRoom));

        assertEquals(1, type.availableRooms().size());
        assertFalse(type.availableRooms().contains(unavailableRoom));
    }

    @Test
    public void shouldAppendRoomAndProduceString() {
        Room room = new Room("101", 650.0);
        RoomType type = new RoomType("Single", Arrays.asList(room));
        type.addRoom(new Room("102", 700.0));

        assertEquals(2, type.rooms().size());
        String text = type.toString();
        assertEquals(true, text.startsWith("Single"));
    }
}
