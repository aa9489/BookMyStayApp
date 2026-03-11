// File: UseCase4RoomSearch.java

import java.util.*;

// Domain Model for Room
class Room {
    private String roomType;
    private double price;
    private String amenities;

    public Room(String roomType, double price, String amenities) {
        this.roomType = roomType;
        this.price = price;
        this.amenities = amenities;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public String getAmenities() {
        return amenities;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Price per Night: $" + price);
        System.out.println("Amenities: " + amenities);
        System.out.println("---------------------------------");
    }
}

// Inventory class holding room availability
class Inventory {
    private Map<String, Integer> roomAvailability;

    public Inventory() {
        roomAvailability = new HashMap<>();
    }

    public void addRoom(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }

    // Read-only access
    public int getAvailability(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }

    // Get all room types
    public Set<String> getAllRoomTypes() {
        return roomAvailability.keySet();
    }
}

// Search service for read-only room search
class SearchService {
    private Inventory inventory;
    private Map<String, Room> roomDetails;

    public SearchService(Inventory inventory, List<Room> rooms) {
        this.inventory = inventory;
        roomDetails = new HashMap<>();
        for (Room room : rooms) {
            roomDetails.put(room.getRoomType(), room);
        }
    }

    public void searchAvailableRooms() {
        System.out.println("Available Rooms:");
        System.out.println("---------------------------------");
        for (String roomType : inventory.getAllRoomTypes()) {
            int available = inventory.getAvailability(roomType);
            if (available > 0) {  // Only show rooms with availability
                Room room = roomDetails.get(roomType);
                if (room != null) {
                    room.displayDetails();
                    System.out.println("Rooms Available: " + available);
                    System.out.println();
                }
            }
        }
    }
}

// Main program
public class RoomSearch {
    public static void main(String[] args) {
        // Step 1: Initialize Inventory
        Inventory inventory = new Inventory();
        inventory.addRoom("Single", 5);
        inventory.addRoom("Double", 0); // Unavailable
        inventory.addRoom("Suite", 2);

        // Step 2: Initialize Room Details
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("Single", 100.0, "WiFi, TV, AC"));
        rooms.add(new Room("Double", 180.0, "WiFi, TV, AC, Mini Bar"));
        rooms.add(new Room("Suite", 350.0, "WiFi, TV, AC, Mini Bar, Kitchen"));

        // Step 3: Search Available Rooms
        SearchService searchService = new SearchService(inventory, rooms);
        searchService.searchAvailableRooms();
    }
}