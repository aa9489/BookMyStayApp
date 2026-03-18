import java.util.*;

class InvalidBookingException extends Exception {
    InvalidBookingException(String message) {
        super(message);
    }
}

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
        inventory.put("Single", 2);
        inventory.put("Double", 1);
    }

    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    public void validateAvailability(String roomType) throws InvalidBookingException {
        int count = inventory.get(roomType);
        if (count <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }
    }

    public void decrement(String roomType) throws InvalidBookingException {
        int count = inventory.get(roomType);
        if (count - 1 < 0) {
            throw new InvalidBookingException("Inventory cannot be negative for: " + roomType);
        }
        inventory.put(roomType, count - 1);
    }
}

class BookingService {
    private InventoryService inventoryService;
    private int idCounter = 1;

    BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public String processBooking(BookingRequest request) throws InvalidBookingException {
        inventoryService.validateRoomType(request.roomType);
        inventoryService.validateAvailability(request.roomType);

        String reservationId = "R" + idCounter++;
        inventoryService.decrement(request.roomType);

        return reservationId;
    }
}

public class ErrorHandlingValidation {
    public static void main(String[] args) {
        InventoryService inventoryService = new InventoryService();
        BookingService bookingService = new BookingService(inventoryService);

        List<BookingRequest> requests = Arrays.asList(
                new BookingRequest("Alice", "Single"),
                new BookingRequest("Bob", "Suite"),
                new BookingRequest("Charlie", "Double"),
                new BookingRequest("David", "Double")
        );

        for (BookingRequest request : requests) {
            try {
                String reservationId = bookingService.processBooking(request);
                System.out.println("Booking confirmed for " + request.customerName +
                        " | Room Type: " + request.roomType +
                        " | Reservation ID: " + reservationId);
            } catch (InvalidBookingException e) {
                System.out.println("Booking failed for " + request.customerName +
                        " | Reason: " + e.getMessage());
            }
        }
    }
}