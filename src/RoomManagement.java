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
 */

public class RoomManagement {
	
    private String number; // String number
    private boolean occupied; //yes or no occupied
    private AccountManagement occupant; //AccountManagement occupant
    private String type; // String type

    /**
     * Constructor - RoomManagement
     * @param roomnumber
     * @param type
     */
    public RoomManagement(String roomnumber, String type) {
        this.number = roomnumber;
        this.occupied = false;
        this.occupant = null;
        this.type = type;
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