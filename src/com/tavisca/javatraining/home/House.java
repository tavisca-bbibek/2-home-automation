package com.tavisca.javatraining.home;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class House {
    private List<Room> rooms;
    private Scanner sc;

    public House() {
        rooms = new ArrayList<>();
        sc = new Scanner(System.in);
    }

    public Optional<Room> findRoomById(long roomId) {
        return rooms.stream()
                .filter(r -> r.getId() == roomId)
                .findFirst();
    }

    public void addRoom(String roomName) {
        Room room = new Room(rooms.size() + 1, roomName);
        rooms.add(room);
    }

    public boolean addDevice(long roomId, String deviceName) {
        Optional<Room> maybeRoom = findRoomById(roomId);
        if (!maybeRoom.isPresent())
            return false;

        Room room = maybeRoom.get();
        int noOfDevices = room.getDevices().size();
        Device device = new Device(noOfDevices + 1, deviceName);
        room.addDevice(device);
        return true;
    }

    public String roomStatus(long roomId) {
        Optional<Room> maybeRoom = findRoomById(roomId);
        if (maybeRoom.isPresent())
            return maybeRoom.get().status();
        else
            return "Couldn't find the room " + roomId;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public String deviceStatus(long roomId, long deviceId) {
        Optional<Room> mayBeRoom = findRoomById(roomId);
        if (!mayBeRoom.isPresent())
            return "No such room " + roomId;

        Room room = mayBeRoom.get();
        Optional<Device> maybeDevice = room.findDeviceById(deviceId);
        if (!maybeDevice.isPresent())
            return "No such device" + deviceId;

        return maybeDevice.get().status();
    }

    public boolean toggleDeviceState(long roomId, long deviceId) {
        Optional<Room> mayBeRoom = findRoomById(roomId);
        if (!mayBeRoom.isPresent())
            return false;

        Room room = mayBeRoom.get();
        Optional<Device> maybeDevice = room.findDeviceById(deviceId);
        if (!maybeDevice.isPresent())
            return false;

        maybeDevice.get().toggleState();
        return true;
    }
}
