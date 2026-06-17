package com.hotelbooking.service;

import com.hotelbooking.model.Booking;
import com.hotelbooking.model.Room;
import com.hotelbooking.model.RoomType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Hotel {
    private final String hotelName;
    private final Map<String, RoomType> roomTypes;
    private final List<Booking> bookingQueue;
    private int bookingCounter;

    public Hotel(String hotelName) {
        if (hotelName == null || hotelName.isEmpty()) {
            throw new IllegalArgumentException("hotelName must not be null or empty");
        }
        this.hotelName = hotelName;
        this.roomTypes = new HashMap<String, RoomType>();
        this.bookingQueue = new ArrayList<Booking>();
        this.bookingCounter = 0;
    }

    public void addRoomType(RoomType roomType) {
        if (roomType == null) {
            throw new IllegalArgumentException("roomType must not be null");
        }
        roomTypes.put(roomType.name(), roomType);
    }

    public RoomType getRoomType(String name) {
        return roomTypes.get(name);
    }

    public Map<String, RoomType> getAllRoomTypes() {
        return Collections.unmodifiableMap(roomTypes);
    }

    public Booking placeBooking(String roomTypeName, String guestName, int nights) {
        RoomType roomType = roomTypes.get(roomTypeName);
        if (roomType == null) {
            throw new IllegalArgumentException("Room type not found: " + roomTypeName);
        }
        List<Room> availableRooms = roomType.availableRooms();
        if (availableRooms.isEmpty()) {
            throw new IllegalArgumentException("No available rooms for type: " + roomTypeName);
        }
        Room room = availableRooms.get(0);
        room.setAvailability(false);
        Booking booking = new Booking(++bookingCounter, guestName, room, nights);
        bookingQueue.add(booking);
        return booking;
    }

    public Booking processNextBooking() {
        for (Booking booking : bookingQueue) {
            if (booking.status() == com.hotelbooking.model.BookingStatus.PENDING) {
                booking.updateStatus(com.hotelbooking.model.BookingStatus.IN_PROGRESS);
                checkIn(booking);
                booking.updateStatus(com.hotelbooking.model.BookingStatus.CONFIRMED);
                return booking;
            }
        }
        return null;
    }

    public List<Booking> bookingQueue() {
        return Collections.unmodifiableList(bookingQueue);
    }

    public String hotelName() {
        return hotelName;
    }

    protected abstract void checkIn(Booking booking);
}
