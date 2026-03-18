import java.util.*;

class AddOnService {
    String name;
    double cost;

    AddOnService(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }
}

class AddOnServiceManager {
    private Map<String, List<AddOnService>> reservationServices = new HashMap<>();

    public void addService(String reservationId, AddOnService service) {
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);
    }

    public double calculateTotalCost(String reservationId) {
        double total = 0;
        List<AddOnService> services = reservationServices.get(reservationId);
        if (services != null) {
            for (AddOnService s : services) {
                total += s.cost;
            }
        }
        return total;
    }

    public void printServices(String reservationId) {
        List<AddOnService> services = reservationServices.get(reservationId);
        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services for reservation " + reservationId);
            return;
        }
        System.out.println("Services for reservation " + reservationId + ":");
        for (AddOnService s : services) {
            System.out.println("- " + s.name + " : " + s.cost);
        }
    }
}

public class AddOnServiceSelection {
    public static void main(String[] args) {
        AddOnServiceManager manager = new AddOnServiceManager();

        String res1 = "R101";
        String res2 = "R102";

        manager.addService(res1, new AddOnService("Breakfast", 500));
        manager.addService(res1, new AddOnService("Airport Pickup", 1200));
        manager.addService(res2, new AddOnService("Extra Bed", 800));

        manager.printServices(res1);
        System.out.println("Total Add-On Cost: " + manager.calculateTotalCost(res1));

        manager.printServices(res2);
        System.out.println("Total Add-On Cost: " + manager.calculateTotalCost(res2));
    }
}