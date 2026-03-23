import java.util.*;

class BookingRequest {
    private String guestName;
    private String roomType;

    public BookingRequest(String guestName, String roomType) {
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

class BookingProcessor {

    private Queue<BookingRequest> queue;
    private Map<String, Integer> inventory;

    public BookingProcessor(Queue<BookingRequest> queue, Map<String, Integer> inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void processBookings() {
        while (true) {
            BookingRequest request;

            synchronized (queue) {
                if (queue.isEmpty()) {
                    break;
                }
                request = queue.poll();
            }

            processRequest(request);
        }
    }

    private void processRequest(BookingRequest request) {
        synchronized (inventory) {
            int available = inventory.getOrDefault(request.getRoomType(), 0);

            if (available > 0) {
                inventory.put(request.getRoomType(), available - 1);
                System.out.println("Booking successful for " + request.getGuestName() +
                        " (" + request.getRoomType() + ")");
            } else {
                System.out.println("Booking failed for " + request.getGuestName() +
                        " (" + request.getRoomType() + ")");
            }
        }
    }
}

class BookingThread extends Thread {

    private BookingProcessor processor;

    public BookingThread(BookingProcessor processor) {
        this.processor = processor;
    }

    public void run() {
        processor.processBookings();
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        Queue<BookingRequest> queue = new LinkedList<>();

        queue.add(new BookingRequest("A", "Single"));
        queue.add(new BookingRequest("B", "Single"));
        queue.add(new BookingRequest("C", "Double"));
        queue.add(new BookingRequest("D", "Double"));
        queue.add(new BookingRequest("E", "Suite"));

        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Single", 1);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);

        BookingProcessor processor = new BookingProcessor(queue, inventory);

        BookingThread t1 = new BookingThread(processor);
        BookingThread t2 = new BookingThread(processor);
        BookingThread t3 = new BookingThread(processor);

        t1.start();
        t2.start();
        t3.start();
    }
}