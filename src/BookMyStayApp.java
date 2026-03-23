import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class BookingValidator {

    private static final List<String> validRoomTypes =
            Arrays.asList("Single", "Double", "Suite");

    public static void validateRoomType(String roomType) throws InvalidBookingException {
        if (!validRoomTypes.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    public static void validateAvailability(int availableRooms) throws InvalidBookingException {
        if (availableRooms <= 0) {
            throw new InvalidBookingException("No rooms available.");
        }
    }
}

class Reservation {
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

public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Map<String, Integer> roomInventory = new HashMap<>();
        roomInventory.put("Single", 1);
        roomInventory.put("Double", 1);
        roomInventory.put("Suite", 1);

        try {
            System.out.print("Enter Guest Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Room Type (Single/Double/Suite): ");
            String roomType = sc.nextLine();

            BookingValidator.validateRoomType(roomType);
            BookingValidator.validateAvailability(roomInventory.getOrDefault(roomType, 0));

            Reservation r = new Reservation(name, roomType);

            roomInventory.put(roomType, roomInventory.get(roomType) - 1);

            System.out.println("Booking successful for " + r.getGuestName());

        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }

        sc.close();
    }
}