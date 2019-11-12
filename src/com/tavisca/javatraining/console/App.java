package com.tavisca.javatraining.console;

import com.tavisca.javatraining.home.House;
import com.tavisca.javatraining.home.exception.InvalidRoomIdException;
import com.tavisca.javatraining.home.exception.UnknownDeviceTypeException;
import com.tavisca.javatraining.home.room.Room;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class App {

    private Scanner sc;
    private House house;

    private App() {
        sc = new Scanner(System.in);
        house = House.getInstance();
    }

    public static void main(String[] args) {
        App app = new App();
        try {
            app.loadConfig("house-config.xml");
        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.err.println("Error: " + e.getMessage());
        }

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
                    String deviceType = app.readDeviceType();
                    try {
                        long deviceId = app.house.addDevice(deviceType, roomId, deviceName);
                        System.out.println("Id " + deviceId + " is assigned to room '" + deviceName + "'");
                    } catch (InvalidRoomIdException | UnknownDeviceTypeException e) {
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
                    System.out.println("Ops! Bye Bye!");
            }
        } while (choice >= 1 && choice <= 7);
        app.sc.close();
    }

    private void loadConfig(String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse("house-config.xml");

        NodeList roomNodes = document.getDocumentElement().getElementsByTagName("room");
        for (int i = 0; i < roomNodes.getLength(); i++) {
            Node roomNode = roomNodes.item(i);
            NamedNodeMap roomAttributes = roomNode.getAttributes();
            String name = roomAttributes.getNamedItem("name").getNodeValue();
            long roomId = house.addRoom(name);

            NodeList withDevicesNode = roomNode.getChildNodes();
            for (int j = 0; j < withDevicesNode.getLength(); j++) {
                Node children = withDevicesNode.item(j);
                if (!children.getNodeName().equals("devices"))
                    continue;

                NodeList withDeviceNode = children.getChildNodes();
                for (int k = 0; k < withDeviceNode.getLength(); k++) {
                    Node deviceNode = withDeviceNode.item(k);
                    if (!deviceNode.getNodeName().equals("device"))
                        continue;

                    NamedNodeMap deviceAttributes = deviceNode.getAttributes();
                    String deviceName = deviceAttributes.getNamedItem("name").getNodeValue();
                    String deviceType = deviceAttributes.getNamedItem("type").getNodeValue();
                    try {
                        house.addDevice(deviceType, roomId, deviceName);
                    } catch (InvalidRoomIdException | UnknownDeviceTypeException e) {
                        System.err.println("Config Error: " + e.getMessage());
                    }
                }
            }
        }
    }

    private String readDeviceType() {
        System.out.print("Device type: ");
        return sc.next();
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
