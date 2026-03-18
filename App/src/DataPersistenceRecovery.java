import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    String reservationId;
    String customerName;
    String roomType;

    Reservation(String reservationId, String customerName, String roomType) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

class SystemState implements Serializable {
    Map<String, Integer> inventory;
    List<Reservation> reservations;

    SystemState(Map<String, Integer> inventory, List<Reservation> reservations) {
        this.inventory = inventory;
        this.reservations = reservations;
    }
}

class PersistenceService {
    private static final String FILE_NAME = "system_state.dat";

    public void save(SystemState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(state);
            System.out.println("State saved successfully");
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    public SystemState load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            SystemState state = (SystemState) ois.readObject();
            System.out.println("State loaded successfully");
            return state;
        } catch (Exception e) {
            System.out.println("No previous state found or file corrupted. Starting fresh.");
            return null;
        }
    }
}

public class DataPersistenceRecovery {
    public static void main(String[] args) {
        PersistenceService persistenceService = new PersistenceService();

        SystemState state = persistenceService.load();

        Map<String, Integer> inventory;
        List<Reservation> reservations;

        if (state == null) {
            inventory = new HashMap<>();
            inventory.put("Single", 2);
            inventory.put("Double", 1);

            reservations = new ArrayList<>();
            reservations.add(new Reservation("R1", "Alice", "Single"));
            reservations.add(new Reservation("R2", "Bob", "Double"));

            System.out.println("Initialized new system state");
        } else {
            inventory = state.inventory;
            reservations = state.reservations;

            System.out.println("Recovered Inventory: " + inventory);
            System.out.println("Recovered Reservations:");
            for (Reservation r : reservations) {
                System.out.println(r.reservationId + " | " + r.customerName + " | " + r.roomType);
            }
        }

        SystemState newState = new SystemState(inventory, reservations);
        persistenceService.save(newState);
    }
}