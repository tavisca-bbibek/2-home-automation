package com.tavisca.javatraining.console;

import com.tavisca.javatraining.home.House;
import com.tavisca.javatraining.home.Room;
import com.tavisca.javatraining.home.exception.InvalidRoomIdException;

import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class App {

    private Scanner sc;
    private House house;

    private App() {
        sc = new Scanner(System.in);
        house = new House();
    }

    public static void main(String[] args) {
        App app = new App();
        int choice;

        do {
            System.out.println("Welcome to command central");
            System.out.println("| 1. Add a Room");
            System.out.println("| 2. Add a Device");
            System.out.println("| 3. Room Status");
            System.out.println("| 4. Device status");
            System.out.println("| 5. Remote Control");
            System.out.println("| 6. List Rooms");
            System.out.println("| 7. List Devices");
            System.out.println("| 0. Exit");
            System.out.print("Your option: ");
            choice = app.sc.nextInt();

            switch (choice) {
                case 1:
                    String roomName = app.readRoomName();
                    long roomId = app.house.addRoom(roomName);
                    System.out.println("Id " + roomId + " is assigned to room '" + roomName + "'");
                    break;
                case 2:
                    roomId = app.readRoomId();
                    String deviceName = app.readDeviceName();
                    try {
                        long deviceId = app.house.addDevice(roomId, deviceName);
                        System.out.println("Id " + deviceId + " is assigned to room '" + deviceName + "'");
                    } catch (InvalidRoomIdException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println(app.house.roomStatus(app.readRoomId()));
                    break;
                case 4:
                    System.out.println(app.house.deviceStatus(
                            app.readRoomId(), app.readDeviceId())
                    );
                    break;
                case 5:
                    app.remoteControl();
                    break;
                case 6:
                    app.listRooms();
                    break;
                case 7:
                    app.listDevices(app.readRoomId());
                    break;
                default:
                    System.err.println("Invalid option Bye!");
            }
        } while (choice >= 1 && choice <= 7);
    }

    private void listDevices(long roomId) {
        Optional<Room> maybeRoom = house.findRoomById(roomId);
        if (!maybeRoom.isPresent())
            System.out.println("Invalid room " + roomId);

        String result = maybeRoom.get().getDevices().stream()
                .map(d -> d.getId() + "." + d.getName())
                .collect(Collectors.joining("\n"));
        System.out.println(result);
    }

    private void listRooms() {
        String result = house.getRooms().stream()
                .map(r -> r.getId() + "." + r.getName())
                .collect(Collectors.joining("\n"));
        System.out.println(result);
    }

    private void remoteControl() {
        System.out.println("Remote control");
        long roomId = readRoomId();
        long deviceId = readDeviceId();
        System.out.println(house.deviceStatus(roomId, deviceId));

        System.out.print("Toggle state(y/n): ");
        char choice = sc.next().charAt(0);
        if (choice == 'y')
            house.toggleDeviceState(roomId, deviceId);
    }

    private long readDeviceId() {
        System.out.print("Device id: ");
        return sc.nextLong();
    }

    private String readDeviceName() {
        System.out.print("Device name: ");
        return sc.next();
    }

    private String readRoomName() {
        System.out.print("Room name: ");
        return sc.next();
    }

    private long readRoomId() {
        System.out.print("Room id: ");
        return sc.nextLong();
    }
}
