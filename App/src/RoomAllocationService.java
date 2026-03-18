import java.util.*;

class BookingRequest {
    String customerName;
    String roomType;

    BookingRequest(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    InventoryService() {
        inventory.put("Single", 3);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void printInventory() {
        System.out.println("Current Inventory: " + inventory);
    }
}

class RoomAllocationService {
    private Queue<BookingRequest> requestQueue = new LinkedList<>();
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();
    private Set<String> allRoomIds = new HashSet<>();
    private InventoryService inventoryService;
    private int idCounter = 1;

    RoomAllocationService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void addRequest(BookingRequest request) {
        requestQueue.offer(request);
    }

    private String generateRoomId(String roomType) {
        String id;
        do {
            id = roomType.substring(0, 1).toUpperCase() + idCounter++;
        } while (allRoomIds.contains(id));
        return id;
    }

    public void processRequests() {
        while (!requestQueue.isEmpty()) {
            BookingRequest request = requestQueue.poll();
            String roomType = request.roomType;

            if (!inventoryService.isAvailable(roomType)) {
                System.out.println("No rooms available for " + request.customerName + " (" + roomType + ")");
                continue;
            }

            String roomId = generateRoomId(roomType);

            allRoomIds.add(roomId);
            allocatedRooms.putIfAbsent(roomType, new HashSet<>());
            allocatedRooms.get(roomType).add(roomId);

            inventoryService.decrement(roomType);

            System.out.println("Reservation confirmed for " + request.customerName +
                    " | Room Type: " + roomType + " | Room ID: " + roomId);
        }
    }

    public void printAllocations() {
        System.out.println("Allocated Rooms: " + allocatedRooms);
    }
}

public class RoomAllocationService {
    public static void main(String[] args) {
        InventoryService inventoryService = new InventoryService();
        RoomAllocationService allocationService = new RoomAllocationService(inventoryService);

        allocationService.addRequest(new BookingRequest("Alice", "Single"));
        allocationService.addRequest(new BookingRequest("Bob", "Double"));
        allocationService.addRequest(new BookingRequest("Charlie", "Single"));
        allocationService.addRequest(new BookingRequest("David", "Suite"));
        allocationService.addRequest(new BookingRequest("Eve", "Single"));
        allocationService.addRequest(new BookingRequest("Frank", "Suite"));

        allocationService.processRequests();

        allocationService.printAllocations();
        inventoryService.printInventory();
    }
}