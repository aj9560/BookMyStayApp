import java.util.*;

// ⚠️ DO NOT create Reservation class here
// It should already exist from UC5

// Inventory Service
class InventoryService {
    Map<String, Integer> rooms = new HashMap<>();

    public InventoryService() {
        rooms.put("Single", 2);
        rooms.put("Double", 2);
        rooms.put("Suite", 1);
    }

    public boolean isAvailable(String type) {
        return rooms.getOrDefault(type, 0) > 0;
    }

    public void reduceRoom(String type) {
        rooms.put(type, rooms.get(type) - 1);
    }
}

// Booking Service
class BookingService {

    Queue<Reservation> queue;
    InventoryService inventory;

    Set<String> allocatedRooms = new HashSet<>();
    Map<String, Set<String>> roomMap = new HashMap<>();

    public BookingService(Queue<Reservation> queue, InventoryService inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void processBookings() {

        while (!queue.isEmpty()) {

            Reservation r = queue.poll();

            if (inventory.isAvailable(r.roomType)) {

                String roomId = generateRoomId(r.roomType);

                allocatedRooms.add(roomId);

                roomMap.putIfAbsent(r.roomType, new HashSet<>());
                roomMap.get(r.roomType).add(roomId);

                inventory.reduceRoom(r.roomType);

                System.out.println("Booking Confirmed → " + r.guestName +
                        " | " + r.roomType +
                        " | Room ID: " + roomId);

            } else {
                System.out.println("Booking Failed → " + r.guestName +
                        " | No " + r.roomType + " rooms available");
            }
        }
    }

    private String generateRoomId(String type) {
        String id;
        do {
            id = type.substring(0, 1).toUpperCase() + (int)(Math.random() * 1000);
        } while (allocatedRooms.contains(id));
        return id;
    }
}

// Main Class
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        Queue<Reservation> queue = new LinkedList<>();

        // Same structure as UC5
        queue.add(new Reservation("Aathish", "Single"));
        queue.add(new Reservation("Rahul", "Double"));
        queue.add(new Reservation("Priya", "Suite"));
        queue.add(new Reservation("Kiran", "Single"));
        queue.add(new Reservation("Arun", "Suite"));

        InventoryService inventory = new InventoryService();

        BookingService bookingService = new BookingService(queue, inventory);

        bookingService.processBookings();
    }
}