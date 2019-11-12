package com.tavisca.javatraining.home.room;

import com.tavisca.javatraining.home.device.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Room {
    private long id;
    private String name;
    private List<Device> devices;

    public Room(long id) {
        this.id = id;
        devices = new ArrayList<>();
    }

    public Room(long id, String name) {
        this(id);
        this.name = name;
    }

    public void addDevice(Device device) {
        devices.add(device);
    }

    public String status() {
        return id + "." + name + " :\n\t " +
                devices.stream()
                        .map(d -> "[" + d.getId() + "." + d.getName() + ": " +
                                (d.isOn() ? "on" : "off") + "]")
                        .collect(Collectors.joining("\n\t "));
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public Optional<Device> findDeviceById(long id) {
        return devices.stream()
                .filter(d -> d.getId() == id)
                .findFirst();
    }
}
