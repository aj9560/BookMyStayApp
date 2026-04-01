import java.util.*;

// Booking Request
class BookingRequestUC11 {
    String guestName;
    String roomType;

    public BookingRequestUC11(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Inventory Service (SHARED RESOURCE)
class InventoryServiceUC11 {

    private Map<String, Integer> rooms = new HashMap<>();

    public InventoryServiceUC11() {
        rooms.put("Single", 1);
        rooms.put("Double", 1);
        rooms.put("Suite", 1);
    }

    // CRITICAL SECTION
    public synchronized boolean allocateRoom(String type) {

        if (rooms.getOrDefault(type, 0) > 0) {
            System.out.println(Thread.currentThread().getName() +
                    " allocating " + type);

            rooms.put(type, rooms.get(type) - 1);

            return true;
        }

        return false;
    }

    public void displayInventory() {
        System.out.println("Final Inventory: " + rooms);
    }
}

// Booking Processor (THREAD)
class BookingProcessorUC11 extends Thread {

    BookingRequestUC11 request;
    InventoryServiceUC11 inventory;

    public BookingProcessorUC11(BookingRequestUC11 request,
                                InventoryServiceUC11 inventory) {
        this.request = request;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        boolean success = inventory.allocateRoom(request.roomType);

        if (success) {
            System.out.println("✅ " + request.guestName +
                    " successfully booked " + request.roomType);
        } else {
            System.out.println("❌ " + request.guestName +
                    " failed to book " + request.roomType);
        }
    }
}

// MAIN CLASS (FORMAL NAME)
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        System.out.println("===== UC11: Concurrent Booking Simulation =====");

        InventoryServiceUC11 inventory = new InventoryServiceUC11();

        // Multiple requests (same room type → conflict)
        BookingRequestUC11 r1 = new BookingRequestUC11("Arun", "Suite");
        BookingRequestUC11 r2 = new BookingRequestUC11("Vijay", "Suite");
        BookingRequestUC11 r3 = new BookingRequestUC11("Kumar", "Suite");

        // Threads
        BookingProcessorUC11 t1 = new BookingProcessorUC11(r1, inventory);
        BookingProcessorUC11 t2 = new BookingProcessorUC11(r2, inventory);
        BookingProcessorUC11 t3 = new BookingProcessorUC11(r3, inventory);

        // Start concurrently
        t1.start();
        t2.start();
        t3.start();

        // Wait for all threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.displayInventory();
    }
}