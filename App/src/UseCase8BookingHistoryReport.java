import java.util.*;

// 🔹 Reservation class (REUSE safely → rename to avoid duplicate issues)
class ReservationUC8 {
    String guestName;
    String roomType;

    public ReservationUC8(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName + " | Room: " + roomType);
    }
}


// 🔹 Booking History (stores confirmed bookings)
class BookingHistory {
    List<ReservationUC8> history = new ArrayList<>();

    public void addReservation(ReservationUC8 r) {
        history.add(r);
    }

    public List<ReservationUC8> getHistory() {
        return history;
    }
}


// 🔹 Report Service (separate logic – IMPORTANT CONCEPT)
class BookingReportService {

    public void showAllBookings(List<ReservationUC8> history) {
        System.out.println("\n===== BOOKING HISTORY =====");

        if (history.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (ReservationUC8 r : history) {
            r.display();
        }
    }

    public void generateSummary(List<ReservationUC8> history) {
        System.out.println("\n===== BOOKING SUMMARY =====");

        Map<String, Integer> countMap = new HashMap<>();

        for (ReservationUC8 r : history) {
            countMap.put(r.roomType,
                    countMap.getOrDefault(r.roomType, 0) + 1);
        }

        for (String type : countMap.keySet()) {
            System.out.println(type + " Rooms Booked: " + countMap.get(type));
        }
    }
}


// 🔹 Main Class
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings (from UC6/UC7)
        history.addReservation(new ReservationUC8("Aathish", "Single"));
        history.addReservation(new ReservationUC8("Rahul", "Double"));
        history.addReservation(new ReservationUC8("Priya", "Single"));
        history.addReservation(new ReservationUC8("Kiran", "Suite"));

        // Reporting
        BookingReportService reportService = new BookingReportService();

        reportService.showAllBookings(history.getHistory());
        reportService.generateSummary(history.getHistory());
    }
}