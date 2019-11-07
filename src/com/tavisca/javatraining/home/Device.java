package com.tavisca.javatraining.home;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;

public class Device implements Serializable {
    private long id;
    private String name;
    private boolean isActive;
    private Instant lastStateChange;

    public Device(long id, String name) {
        this.id = id;
        this.name = name;
        lastStateChange = Instant.now();
        isActive = false;
    }

    public void turnOn() {
        isActive = true;
        lastStateChange = Instant.now();
    }

    public void turnOff() {
        isActive = false;
        lastStateChange = Instant.now();
    }

    public String status() {
        return id + "." + name + "\n" +
                "Status: " + (isActive() ? "On" : "Off")  + "\n" +
                "For: " + Duration.between(lastStateChange, Instant.now()).toSeconds() + " seconds";
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }

    public Instant getLastStateChange() {
        return lastStateChange;
    }

    public void toggleState() {
        isActive = (!isActive);
    }
}
