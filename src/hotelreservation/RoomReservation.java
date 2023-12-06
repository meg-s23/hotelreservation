package hotelreservation;

/**
 * CMSC 495 Section 7380
 * Group 8
 * Author: Mario Bethancourt, Jules Torres, Megan Moore
 * Professor Hung Dao
 * Date: 11/21/2023
 * Description: This class manages hotel rooms and their availability.
 * Revisions
 * 11/21/2023 Everyone - Created the class.
 * 11/22/2023 Everyone - Implemented methods based on other classes.
 * 11/23/2023 Jules - added revisions
 * 11/23/2023 Everyone - Implemented more methods based on room pricing.
 * 11/25/2023 Mario, Jules  - debugging.
 * 12/01/2023 Mario - Implemented more methods and modified existing ones
 * 12/02/2023 Jules - Worked on the methods implemented 
 * 12/03/2023 Everyone - debugging
 */

public class RoomReservation {
	
    private String number; // String number
    private boolean occupied; //yes or no occupied
    private AccountManagement occupant; //AccountManagement occupant
    private String type; // String type

    /**
     * Constructor - RoomReservation
     * @param roomnumber
     * @param type
     */
    public RoomReservation(String roomnumber, String type) {
        this.number = roomnumber;
        this.occupied = false;
        this.occupant = null;
        this.type = type;
    }
    
    public RoomReservation(String roomNumber) {
        this.number = roomNumber;
        this.occupied = false;
        this.occupant = null;
        this.type = ""; // Initialize type as needed
    }

    //Setters and Getters
    public String getNumber() {
        return number;
    }
    
    public String getType() {
        return type;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public AccountManagement getOccupant() {
        return occupant;
    }

    public void occupy(AccountManagement occupant) {
        this.occupied = true;
        this.occupant = occupant;
    }

    public void vacate() {
        this.occupied = false;
        this.occupant = null;
    }

    /**
     * Getter for price per night
     * @return price per night
     */
    public double getPricePerNight() {
        switch (type) {
            case "A":
                return 120.00;
            case "B":
                return 150.00;
            case "C":
                return 190.00;
            default:
                return 0.00; // Handle invalid type 
                
        }
    }
    
}