package com.tavisca.javatraining.home.exception;

public class UnknownDeviceTypeException extends Exception {
    public UnknownDeviceTypeException() {
        super("Unknown device type");
    }

    public UnknownDeviceTypeException(String message) {
        super(message);
    }
}
