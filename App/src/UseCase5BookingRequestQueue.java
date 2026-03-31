import java.util.*;

// Reservation class (Guest Request)
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

// Booking Queue Manager
class BookingQueue {
    Queue<Reservation> queue = new LinkedList<>();

    // Add request
    public void addRequest(Reservation r) {
        queue.add(r);
        System.out.println("Request added for " + r.guestName);
    }

    // View all requests
    public void showQueue() {
        if (queue.isEmpty()) {
            System.out.println("No booking requests.");
            return;
        }

        System.out.println("\n--- Booking Queue (FIFO Order) ---");
        for (Reservation r : queue) {
            r.display();
        }
    }
}

// Main Class
public class UseCase5BookingRequestQueue {
    public static void main(String[] args) {

        BookingQueue bookingQueue = new BookingQueue();

        // Simulating incoming requests
        bookingQueue.addRequest(new Reservation("Aathish", "Single"));
        bookingQueue.addRequest(new Reservation("Rahul", "Double"));
        bookingQueue.addRequest(new Reservation("Priya", "Suite"));
        bookingQueue.addRequest(new Reservation("Kiran", "Single"));

        // Display queue
        bookingQueue.showQueue();
    }
}