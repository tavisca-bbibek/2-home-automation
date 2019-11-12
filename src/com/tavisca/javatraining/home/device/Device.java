package com.tavisca.javatraining.home.device;

import java.time.Duration;
import java.time.Instant;

public abstract class Device implements Operable {
    private long id;
    private String name;
    private boolean isOn;
    private Instant lastStateChange;

    protected Device() {
        this.name = "Unnamed";
    }

    protected Device(long id, String name) {
        this.id = id;
        this.name = name;
        lastStateChange = Instant.now();
        isOn = false;
    }

    public void turnOn() {
        isOn = true;
        lastStateChange = Instant.now();
    }

    public void turnOff() {
        isOn = false;
        lastStateChange = Instant.now();
    }

    public String status() {
        return id + "." + name + "\n" +
                "Status: " + (isOn() ? "On" : "Off ") +
                "for: " + Duration.between(lastStateChange, Instant.now()).toSeconds() + " seconds";
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOn() {
        return isOn;
    }

    public Instant getLastStateChange() {
        return lastStateChange;
    }

    public void toggleState() {
        isOn = (!isOn);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setOn(boolean on) {
        isOn = on;
    }
}
