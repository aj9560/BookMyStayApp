import java.io.*;
import java.util.*;

// Booking class (Serializable)
class BookingUC12 implements Serializable {
    String guestName;
    String roomType;

    public BookingUC12(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String toString() {
        return guestName + " -> " + roomType;
    }
}

// Inventory + Booking Store (Serializable)
class SystemStateUC12 implements Serializable {
    Map<String, Integer> inventory;
    List<BookingUC12> bookings;

    public SystemStateUC12() {
        inventory = new HashMap<>();
        bookings = new ArrayList<>();
    }
}

// Persistence Service
class PersistenceServiceUC12 {

    private static final String FILE_NAME = "system_state.ser";

    // SAVE
    public static void save(SystemStateUC12 state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("✅ Data saved successfully!");

        } catch (IOException e) {
            System.out.println("❌ Error saving data: " + e.getMessage());
        }
    }

    // LOAD
    public static SystemStateUC12 load() {

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemStateUC12 state = (SystemStateUC12) ois.readObject();
            System.out.println("✅ Data restored successfully!");
            return state;

        } catch (FileNotFoundException e) {
            System.out.println("⚠️ No previous data found. Starting fresh...");
        } catch (Exception e) {
            System.out.println("❌ Error loading data. Starting fresh...");
        }

        return new SystemStateUC12(); // safe fallback
    }
}

// MAIN CLASS
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("===== UC12: Data Persistence & Recovery =====");

        // STEP 1: Load previous state
        SystemStateUC12 state = PersistenceServiceUC12.load();

        // STEP 2: If first run, initialize inventory
        if (state.inventory.isEmpty()) {
            state.inventory.put("Single", 2);
            state.inventory.put("Double", 2);
            state.inventory.put("Suite", 1);
        }

        // STEP 3: Simulate booking
        BookingUC12 booking = new BookingUC12("Aathish", "Suite");

        if (state.inventory.get("Suite") > 0) {
            state.inventory.put("Suite", state.inventory.get("Suite") - 1);
            state.bookings.add(booking);
            System.out.println("✅ Booking successful: " + booking);
        } else {
            System.out.println("❌ Booking failed: No rooms available");
        }

        // STEP 4: Save state before exit
        PersistenceServiceUC12.save(state);

        // Display state
        System.out.println("Current Inventory: " + state.inventory);
        System.out.println("Booking History: " + state.bookings);
    }
}