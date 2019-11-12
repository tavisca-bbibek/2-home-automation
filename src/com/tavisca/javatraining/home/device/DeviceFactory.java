package com.tavisca.javatraining.home.device;

import com.tavisca.javatraining.home.exception.UnknownDeviceTypeException;

public class DeviceFactory {

    public Device newInstance(DeviceType type) {
        switch (type) {
            case DOOR:
                return new Door();
            case LIGHT:
                return new Light();
            case SHOWER:
                return new Shower();
            case TELEVISION:
                return new Television();
            case AIR_CONDITIONER:
                return new AirConditioner();
            case FAN:
                return new Fan();
            case STEREO:
                return new Stereo();
            case THERMOSTAT:
                return new Thermostat();
            default:
                throw new IllegalArgumentException(type + " is not yet supported.");
        }
    }

    public Device newInstance(String type) throws UnknownDeviceTypeException {
        for (DeviceType d : DeviceType.values())
            if (d.name().replaceAll("_", "").equalsIgnoreCase(type))
                return newInstance(d);
        throw new UnknownDeviceTypeException("Unknown device type - " + type);
    }
}
