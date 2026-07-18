package com.hotelbooking.model;
import java.util.List;
import java.util.ArrayList;

public class RoomType {
    // TODO: implement RoomType based on the workshop requirements
    private String name;
    private List<Room> rooms;

    public RoomType(String name, List<Room> rooms) {
        this.name = name;
        this.rooms = rooms;
    }

    public String name(){
        return name;
    }

    public List<Room> rooms(){
        return rooms = new ArrayList<>(rooms);
    }

    public void addRoom(Room room){
        rooms.add(room);
    }

    public List<Room> availableRooms(){
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
        return "RoomType{" +
                "name='" + name + '\'' +
                ", rooms=" + rooms +
                '}';
    } 

}
