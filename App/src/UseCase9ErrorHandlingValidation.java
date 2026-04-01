import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation Class
class ReservationUC9 {
    String guestName;
    String roomType;

    public ReservationUC9(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Inventory Service
class InventoryServiceUC9 {
    Map<String, Integer> rooms = new HashMap<>();

    public InventoryServiceUC9() {
        rooms.put("Single", 2);
        rooms.put("Double", 2);
        rooms.put("Suite", 1);
    }

    public boolean isValidRoomType(String type) {
        return rooms.containsKey(type);
    }

    public boolean isAvailable(String type) {
        return rooms.getOrDefault(type, 0) > 0;
    }

    public void reduceRoom(String type) throws InvalidBookingException {
        int count = rooms.getOrDefault(type, 0);
        if (count <= 0) {
            throw new InvalidBookingException("No rooms available for " + type);
        }
        rooms.put(type, count - 1);
    }
}

// Booking Service
class BookingServiceUC9 {

    Queue<ReservationUC9> queue;
    InventoryServiceUC9 inventory;

    public BookingServiceUC9(Queue<ReservationUC9> queue, InventoryServiceUC9 inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void processBookings() {
        while (!queue.isEmpty()) {
            ReservationUC9 r = queue.poll();

            try {
                validate(r);

                if (inventory.isAvailable(r.roomType)) {
                    inventory.reduceRoom(r.roomType);
                    System.out.println("✅ Booking Confirmed for " + r.guestName + " (" + r.roomType + ")");
                } else {
                    System.out.println("❌ No availability for " + r.roomType);
                }

            } catch (InvalidBookingException e) {
                System.out.println("❌ ERROR: " + e.getMessage());
            }
        }
    }

    private void validate(ReservationUC9 r) throws InvalidBookingException {

        if (r.guestName == null || r.guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        if (r.roomType == null || r.roomType.trim().isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty");
        }

        if (!inventory.isValidRoomType(r.roomType)) {
            throw new InvalidBookingException("Invalid room type: " + r.roomType);
        }
    }
}

// MAIN CLASS (ONLY PUBLIC CLASS)
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        Queue<ReservationUC9> queue = new LinkedList<>();

        // Valid + Invalid Cases
        queue.add(new ReservationUC9("", "Single"));        // Invalid name
        queue.add(new ReservationUC9("Kumar", ""));         // Invalid room
        queue.add(new ReservationUC9("Arun", "Suite"));     // Valid
        queue.add(new ReservationUC9("Vijay", "Suite"));    // No availability
        queue.add(new ReservationUC9("Raj", "Deluxe"));     // Invalid type

        InventoryServiceUC9 inventory = new InventoryServiceUC9();
        BookingServiceUC9 service = new BookingServiceUC9(queue, inventory);

        System.out.println("===== UC9: Error Handling & Validation =====");
        service.processBookings();
    }
}