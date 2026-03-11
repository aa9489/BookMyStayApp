abstract class Room {

    String roomType;
    int beds;
    int size;
    double price;

    Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq.ft");
        System.out.println("Price: $" + price);
    }
}

class SingleRoom extends Room {
    SingleRoom() {
        super("Single Room", 1, 200, 100);
    }
}

class DoubleRoom extends Room {
    DoubleRoom() {
        super("Double Room", 2, 350, 180);
    }
}

class SuiteRoom extends Room {
    SuiteRoom() {
        super("Suite Room", 3, 500, 300);
    }
}

public class RoomInitialization {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Hotel Booking System");
        System.out.println("Version: 2.1");

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvailability = 5;
        int doubleAvailability = 3;
        int suiteAvailability = 2;

        System.out.println("\nSingle Room Details:");
        single.displayRoomDetails();
        System.out.println("Available: " + singleAvailability);

        System.out.println("\nDouble Room Details:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleAvailability);

        System.out.println("\nSuite Room Details:");
        suite.displayRoomDetails();
        System.out.println("Available: " + suiteAvailability);
    }
}