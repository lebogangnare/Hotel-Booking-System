package com.hotelbooking.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoomType {
    private final String name;
    private final List<Room> rooms;

    public RoomType(String name, List<Room> rooms) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name must not be null or empty");
        }
        if (rooms == null) {
            throw new IllegalArgumentException("rooms must not be null");
        }
        this.name = name;
        this.rooms = new ArrayList<>(rooms);
    }

    public String name() {
        return name;
    }

    public List<Room> rooms() {
        return new ArrayList<>(rooms);
    }

    public void addRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("room must not be null");
        }
        rooms.add(room);
    }

    public List<Room> availableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(name);
        for (Room room : rooms) {
            builder.append(System.lineSeparator()).append(room.toString());
        }
        return builder.toString();
    }
}
