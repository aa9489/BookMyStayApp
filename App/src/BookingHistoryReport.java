import java.util.*;

class Reservation {
    String reservationId;
    String customerName;
    String roomType;

    Reservation(String reservationId, String customerName, String roomType) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return history;
    }
}

class BookingReportService {
    public void printAllBookings(List<Reservation> reservations) {
        System.out.println("Booking History:");
        for (Reservation r : reservations) {
            System.out.println(r.reservationId + " | " + r.customerName + " | " + r.roomType);
        }
    }

    public void printSummary(List<Reservation> reservations) {
        Map<String, Integer> summary = new HashMap<>();
        for (Reservation r : reservations) {
            summary.put(r.roomType, summary.getOrDefault(r.roomType, 0) + 1);
        }
        System.out.println("Booking Summary by Room Type:");
        for (Map.Entry<String, Integer> entry : summary.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

public class BookingHistoryReport {
    public static void main(String[] args) {
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        history.addReservation(new Reservation("R101", "Alice", "Single"));
        history.addReservation(new Reservation("R102", "Bob", "Double"));
        history.addReservation(new Reservation("R103", "Charlie", "Single"));
        history.addReservation(new Reservation("R104", "David", "Suite"));

        List<Reservation> allReservations = history.getAllReservations();

        reportService.printAllBookings(allReservations);
        reportService.printSummary(allReservations);
    }
}