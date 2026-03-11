// File: UseCase5BookingRequestQueue.java

import java.util.*;

// Represents a guest's booking intent
class Reservation {
private String guestName;
private String roomType;
private int nights;

    public Reservation(String guestName, String roomType, int nights) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }

    public void displayReservation() {
        System.out.println("Guest Name: " + guestName);
        System.out.println("Requested Room: " + roomType);
        System.out.println("Nights: " + nights);
        System.out.println("---------------------------------");
    }
}

// Booking request queue using FIFO principle
class BookingRequestQueue {
private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add a booking request to the queue
    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Booking request added for guest: " + reservation.getGuestName());
    }

    // Display all requests in queue order
    public void displayRequests() {
        System.out.println("\nCurrent Booking Requests in Queue (FIFO):");
        System.out.println("---------------------------------");
        for (Reservation r : requestQueue) {
            r.displayReservation();
        }
    }

    // Poll next request for processing (simulated)
    public Reservation getNextRequest() {
        return requestQueue.poll(); // removes head of queue
    }

    public boolean isEmpty() {
        return requestQueue.isEmpty();
    }
}

// Main program
public class UseCase5BookingRequestQueue {
public static void main(String[] args) {
BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulate booking requests arriving
        bookingQueue.addRequest(new Reservation("Alice", "Single", 2));
        bookingQueue.addRequest(new Reservation("Bob", "Suite", 3));
        bookingQueue.addRequest(new Reservation("Charlie", "Double", 1));

        // Display queued requests
        bookingQueue.displayRequests();

        // Simulate processing requests
        System.out.println("\nProcessing booking requests in order:");
        while (!bookingQueue.isEmpty()) {
            Reservation next = bookingQueue.getNextRequest();
            System.out.println("Processing reservation for: " + next.getGuestName());
        }
    }
}
