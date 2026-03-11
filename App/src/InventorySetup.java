import java.util.HashMap;

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public void displayInventory() {
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " Available: " + inventory.get(roomType));
        }
    }
}

public class InventorySetup {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Hotel Booking System");
        System.out.println("Version: 3.1");

        RoomInventory inventory = new RoomInventory();

        System.out.println("\nCurrent Room Inventory:");
        inventory.displayInventory();

        System.out.println("\nUpdating Single Room Availability...");
        inventory.updateAvailability("Single Room", 4);

        System.out.println("\nUpdated Room Inventory:");
        inventory.displayInventory();
    }
}