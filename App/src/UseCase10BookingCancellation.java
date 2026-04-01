import java.util.*;

// Reservation Class
class ReservationUC10 {
    String guestName;
    String roomType;
    String roomId;

    public ReservationUC10(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

// Inventory Service
class InventoryServiceUC10 {

    Map<String, Integer> rooms = new HashMap<>();

    public InventoryServiceUC10() {
        rooms.put("Single", 2);
        rooms.put("Double", 2);
        rooms.put("Suite", 1);
    }

    public boolean isAvailable(String type) {
        return rooms.getOrDefault(type, 0) > 0;
    }

    public void allocateRoom(String type) {
        rooms.put(type, rooms.get(type) - 1);
    }

    public void releaseRoom(String type) {
        rooms.put(type, rooms.get(type) + 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory: " + rooms);
    }
}

// Booking Service
class BookingServiceUC10 {

    Map<String, ReservationUC10> bookings = new HashMap<>();
    Stack<String> rollbackStack = new Stack<>();
    InventoryServiceUC10 inventory;

    public BookingServiceUC10(InventoryServiceUC10 inventory) {
        this.inventory = inventory;
    }

    // Booking
    public void book(String guest, String type, String roomId) {
        if (inventory.isAvailable(type)) {
            ReservationUC10 r = new ReservationUC10(guest, type, roomId);
            bookings.put(roomId, r);
            inventory.allocateRoom(type);
            System.out.println("✅ Booked: " + guest + " (" + type + ") RoomID: " + roomId);
        } else {
            System.out.println("❌ No rooms available for " + type);
        }
    }

    // Cancellation with rollback
    public void cancel(String roomId) {

        if (!bookings.containsKey(roomId)) {
            System.out.println("❌ Invalid cancellation. Booking not found for RoomID: " + roomId);
            return;
        }

        ReservationUC10 r = bookings.remove(roomId);

        // rollback step
        rollbackStack.push(roomId);

        inventory.releaseRoom(r.roomType);

        System.out.println("🔁 Cancelled booking for " + r.guestName +
                " (" + r.roomType + ") RoomID: " + roomId);
    }

    public void showRollbackStack() {
        System.out.println("Rollback Stack (LIFO): " + rollbackStack);
    }
}

// MAIN CLASS
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        InventoryServiceUC10 inventory = new InventoryServiceUC10();
        BookingServiceUC10 service = new BookingServiceUC10(inventory);

        System.out.println("===== UC10: Booking + Cancellation =====");

        // BOOKINGS
        service.book("Arun", "Suite", "R1");
        service.book("Vijay", "Suite", "R2"); // no availability
        service.book("Kumar", "Single", "R3");

        inventory.displayInventory();

        // CANCELLATIONS
        service.cancel("R1"); // valid
        service.cancel("R5"); // invalid

        inventory.displayInventory();

        service.showRollbackStack();
    }
}