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
        inventory.put("Single", 2);
        inventory.put("Double", 1);
    }

    public synchronized boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public synchronized void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public synchronized void printInventory() {
        System.out.println("Inventory: " + inventory);
    }
}

class BookingProcessor implements Runnable {
    private Queue<BookingRequest> queue;
    private InventoryService inventoryService;
    private static int idCounter = 1;

    BookingProcessor(Queue<BookingRequest> queue, InventoryService inventoryService) {
        this.queue = queue;
        this.inventoryService = inventoryService;
    }

    private static synchronized String generateId() {
        return "R" + idCounter++;
    }

    public void run() {
        while (true) {
            BookingRequest request;

            synchronized (queue) {
                if (queue.isEmpty()) {
                    break;
                }
                request = queue.poll();
            }

            synchronized (inventoryService) {
                if (inventoryService.isAvailable(request.roomType)) {
                    inventoryService.decrement(request.roomType);
                    String id = generateId();
                    System.out.println(Thread.currentThread().getName() +
                            " confirmed booking for " + request.customerName +
                            " | " + request.roomType +
                            " | " + id);
                } else {
                    System.out.println(Thread.currentThread().getName() +
                            " failed for " + request.customerName +
                            " | No availability");
                }
            }
        }
    }
}

public class ConcurrentBookingSimulation {
    public static void main(String[] args) throws InterruptedException {
        Queue<BookingRequest> queue = new LinkedList<>();
        InventoryService inventoryService = new InventoryService();

        queue.add(new BookingRequest("Alice", "Single"));
        queue.add(new BookingRequest("Bob", "Single"));
        queue.add(new BookingRequest("Charlie", "Single"));
        queue.add(new BookingRequest("David", "Double"));
        queue.add(new BookingRequest("Eve", "Double"));

        Thread t1 = new Thread(new BookingProcessor(queue, inventoryService), "Thread-1");
        Thread t2 = new Thread(new BookingProcessor(queue, inventoryService), "Thread-2");
        Thread t3 = new Thread(new BookingProcessor(queue, inventoryService), "Thread-3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        inventoryService.printInventory();
    }
}