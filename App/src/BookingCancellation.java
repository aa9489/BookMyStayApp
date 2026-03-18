import java.util.*;

class Reservation {
    String reservationId;
    String customerName;
    String roomType;
    String roomId;

    Reservation(String reservationId, String customerName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    InventoryService() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void increment(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void printInventory() {
        System.out.println("Inventory: " + inventory);
    }
}

class BookingService {
    private Map<String, Reservation> confirmedBookings = new HashMap<>();
    private Set<String> usedRoomIds = new HashSet<>();
    private InventoryService inventoryService;
    private int idCounter = 1;

    BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public Reservation book(String customerName, String roomType) {
        if (!inventoryService.isAvailable(roomType)) {
            System.out.println("No rooms available for " + customerName);
            return null;
        }

        String reservationId = "R" + idCounter;
        String roomId = roomType.substring(0, 1) + idCounter;
        idCounter++;

        usedRoomIds.add(roomId);
        inventoryService.decrement(roomType);

        Reservation reservation = new Reservation(reservationId, customerName, roomType, roomId);
        confirmedBookings.put(reservationId, reservation);

        System.out.println("Booked: " + reservationId + " | " + customerName + " | " + roomId);
        return reservation;
    }

    public Reservation getReservation(String reservationId) {
        return confirmedBookings.get(reservationId);
    }

    public void removeReservation(String reservationId) {
        confirmedBookings.remove(reservationId);
    }
}

class CancellationService {
    private BookingService bookingService;
    private InventoryService inventoryService;
    private Stack<String> rollbackStack = new Stack<>();

    CancellationService(BookingService bookingService, InventoryService inventoryService) {
        this.bookingService = bookingService;
        this.inventoryService = inventoryService;
    }

    public void cancel(String reservationId) {
        Reservation reservation = bookingService.getReservation(reservationId);

        if (reservation == null) {
            System.out.println("Cancellation failed: Invalid reservation ID " + reservationId);
            return;
        }

        rollbackStack.push(reservation.roomId);
        inventoryService.increment(reservation.roomType);
        bookingService.removeReservation(reservationId);

        System.out.println("Cancelled: " + reservationId + " | Room released: " + reservation.roomId);
    }

    public void printRollbackStack() {
        System.out.println("Rollback Stack: " + rollbackStack);
    }
}

public class BookingCancellation {
    public static void main(String[] args) {
        InventoryService inventoryService = new InventoryService();
        BookingService bookingService = new BookingService(inventoryService);
        CancellationService cancellationService = new CancellationService(bookingService, inventoryService);

        Reservation r1 = bookingService.book("Alice", "Single");
        Reservation r2 = bookingService.book("Bob", "Double");

        inventoryService.printInventory();

        if (r1 != null) {
            cancellationService.cancel(r1.reservationId);
        }

        cancellationService.cancel("R999");

        inventoryService.printInventory();
        cancellationService.printRollbackStack();
    }
}