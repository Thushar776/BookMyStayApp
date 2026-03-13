/**
 * ==============================================================
 * Book My Stay App
 * Use Case 2: Basic Room Types & Static Availability
 * ==============================================================
 */

/* --------------------------------------------------------------
   ABSTRACT CLASS - Room
--------------------------------------------------------------*/
abstract class Room {

    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
    }
}

/* --------------------------------------------------------------
   CLASS - SingleRoom
--------------------------------------------------------------*/
class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

/* --------------------------------------------------------------
   CLASS - DoubleRoom
--------------------------------------------------------------*/
class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

/* --------------------------------------------------------------
   CLASS - SuiteRoom
--------------------------------------------------------------*/
class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}

/* --------------------------------------------------------------
   MAIN CLASS
--------------------------------------------------------------*/
public class BookMyStayApp {

    public static void main(String[] args) {

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("Hotel Room Initialization\n");

        System.out.println("Single Room:");
        single.displayRoomDetails();
        System.out.println("Available: " + singleAvailable);

        System.out.println();

        System.out.println("Double Room:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleAvailable);

        System.out.println();

        System.out.println("Suite Room:");
        suite.displayRoomDetails();
        System.out.println("Available: " + suiteAvailable);
    }
}