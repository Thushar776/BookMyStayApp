import java.util.*;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class BookingHistory {

    private Map<String, Reservation> reservations = new HashMap<>();

    public void addReservation(Reservation r) {
        reservations.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return reservations.get(id);
    }

    public void removeReservation(String id) {
        reservations.remove(id);
    }
}

class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String reservationId,
                              BookingHistory history,
                              Map<String, Integer> inventory) {

        Reservation r = history.getReservation(reservationId);

        if (r == null) {
            System.out.println("Cancellation Failed: Reservation not found");
            return;
        }

        rollbackStack.push(reservationId);

        String roomType = r.getRoomType();
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);

        history.removeReservation(reservationId);

        System.out.println("Booking cancelled for " + r.getGuestName());
    }

    public void showRollbackStack() {
        System.out.println("Rollback Stack: " + rollbackStack);
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Single", 1);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);

        BookingHistory history = new BookingHistory();

        Reservation r1 = new Reservation("R1", "Thushar", "Single");
        history.addReservation(r1);

        inventory.put("Single", inventory.get("Single") - 1);

        CancellationService service = new CancellationService();

        service.cancelBooking("R1", history, inventory);

        service.showRollbackStack();

        System.out.println("Updated Inventory: " + inventory);
    }
}