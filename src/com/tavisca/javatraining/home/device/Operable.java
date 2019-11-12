package com.tavisca.javatraining.home.device;

import java.time.Instant;

public interface Operable {
    String getName();

    void setName(String name);

    void turnOn();

    void turnOff();

    String status();

    boolean isOn();

    Instant getLastStateChange();
}
