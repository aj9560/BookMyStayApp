import java.util.*;

// ROOM CLASS (RENAMED PROPERLY)
class RoomUC4 {
    String type;
    int price;
    String amenities;

    // FIXED CONSTRUCTOR NAME
    public RoomUC4(String type, int price, String amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Price: ₹" + price);
        System.out.println("Amenities: " + amenities);
        System.out.println("--------------------------");
    }
}

// INVENTORY (REUSED LOGIC)
class RoomInventoryUC4 {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

// SEARCH SERVICE
class RoomSearchService {

    private RoomInventoryUC4 inventory;
    private List<RoomUC4> rooms;

    public RoomSearchService(RoomInventoryUC4 inventory, List<RoomUC4> rooms) {
        this.inventory = inventory;
        this.rooms = rooms;
    }

    public void searchAvailableRooms() {

        System.out.println("\n===== AVAILABLE ROOMS =====");

        for (RoomUC4 room : rooms) {

            int available = inventory.getAvailability(room.type);

            if (available > 0) {
                room.displayDetails();
                System.out.println("Available Count: " + available);
                System.out.println();
            }
        }
    }
}

// MAIN CLASS
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        // Step 1: Inventory setup
        RoomInventoryUC4 inventory = new RoomInventoryUC4();
        inventory.addRoom("Single", 5);
        inventory.addRoom("Double", 0); // won't show
        inventory.addRoom("Suite", 2);

        // Step 2: Room objects
        List<RoomUC4> rooms = new ArrayList<>();
        rooms.add(new RoomUC4("Single", 2000, "WiFi, AC"));
        rooms.add(new RoomUC4("Double", 3500, "WiFi, AC, TV"));
        rooms.add(new RoomUC4("Suite", 5000, "WiFi, AC, TV, Jacuzzi"));

        // Step 3: Search
        RoomSearchService service =
                new RoomSearchService(inventory, rooms);

        service.searchAvailableRooms();
    }
}