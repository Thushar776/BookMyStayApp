import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class SystemState implements Serializable {
    List<Reservation> reservations;
    Map<String, Integer> inventory;

    public SystemState(List<Reservation> reservations, Map<String, Integer> inventory) {
        this.reservations = reservations;
        this.inventory = inventory;
    }
}

class PersistenceService {

    private static final String FILE_NAME = "system_state.ser";

    public static void save(SystemState state) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            out.writeObject(state);
            out.close();
            System.out.println("State saved successfully");
        } catch (Exception e) {
            System.out.println("Error saving state");
        }
    }

    public static SystemState load() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME));
            SystemState state = (SystemState) in.readObject();
            in.close();
            System.out.println("State loaded successfully");
            return state;
        } catch (Exception e) {
            System.out.println("No previous state found, starting fresh");
            return new SystemState(new ArrayList<>(), new HashMap<>());
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        SystemState state = PersistenceService.load();

        List<Reservation> reservations = state.reservations;
        Map<String, Integer> inventory = state.inventory;

        if (inventory.isEmpty()) {
            inventory.put("Single", 1);
            inventory.put("Double", 1);
            inventory.put("Suite", 1);
        }

        Reservation r = new Reservation("Thushar", "Single");

        if (inventory.get("Single") > 0) {
            reservations.add(r);
            inventory.put("Single", inventory.get("Single") - 1);
            System.out.println("Booking done for " + r.getGuestName());
        } else {
            System.out.println("No rooms available");
        }

        PersistenceService.save(new SystemState(reservations, inventory));
    }
}