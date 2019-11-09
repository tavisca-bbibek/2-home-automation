package com.tavisca.javatraining.home;

import com.tavisca.javatraining.home.exception.InvalidRoomIdException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

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

    public long addRoom(String roomName) {
        int id = rooms.size() + 1;
        Room room = new Room(id, roomName);
        rooms.add(room);
        return id;
    }

    public long addDevice(long roomId, String deviceName) throws InvalidRoomIdException {
        Optional<Room> maybeRoom = findRoomById(roomId);
        if (!maybeRoom.isPresent())
            throw new InvalidRoomIdException(roomId + " is doesn't exist");

        Room room = maybeRoom.get();
        int id = room.getDevices().size() + 1;
        Device device = new Device(id, deviceName);
        room.addDevice(device);
        return id;
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
