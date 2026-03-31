import java.util.*;

// Service Class (Add-On)
class Service {
    String name;
    int price;

    public Service(String name, int price) {
        this.name = name;
        this.price = price;
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // Map<ReservationID, List<Service>>
    Map<String, List<Service>> serviceMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, Service service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    // Display services
    public void displayServices(String reservationId) {
        List<Service> services = serviceMap.get(reservationId);

        if (services == null) {
            System.out.println("No services added.");
            return;
        }

        System.out.println("Services for Reservation ID: " + reservationId);

        for (Service s : services) {
            System.out.println("- " + s.name + " (₹" + s.price + ")");
        }
    }

    // Calculate total cost
    public int calculateTotalCost(String reservationId) {
        List<Service> services = serviceMap.get(reservationId);

        if (services == null) return 0;

        int total = 0;
        for (Service s : services) {
            total += s.price;
        }
        return total;
    }
}

// Main Class
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        // Assume these reservation IDs came from UC6
        String r1 = "R101";
        String r2 = "R102";

        // Add services
        manager.addService(r1, new Service("Breakfast", 200));
        manager.addService(r1, new Service("Airport Pickup", 500));

        manager.addService(r2, new Service("Extra Bed", 300));

        // Display services
        System.out.println("\n--- SERVICES ---");
        manager.displayServices(r1);
        manager.displayServices(r2);

        // Total cost
        System.out.println("\n--- TOTAL COST ---");
        System.out.println("R101: ₹" + manager.calculateTotalCost(r1));
        System.out.println("R102: ₹" + manager.calculateTotalCost(r2));
    }
}