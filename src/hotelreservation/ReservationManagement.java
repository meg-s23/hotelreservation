package hotelreservation;

/**
 * File: ReservationManagement.java
 * CMSC 495 Section 7380
 * Group 8
 * Author: Mario Bethancourt, Jules Torres, Megan Moore
 * Professor Hung Dao
 * Date: 11/21/2023
 * Description: This class manages user reservations in the hotel reservation system.
 * Revisions
 * 11/21/2023 Everyone - Created the class.
 * 11/22/2023 Everyone - Implemented methods based on the other classes.
 * 11/23/2023 Jules - Revisions 
 * 11/25/2023 Mario    - debugging.
 * 12/01/2023 Mario - Implemented more methods and modified existing ones
 * 12/02/2023 Jules - Worked on the methods implemented 
 * 12/03/2023 Everyone - debugging
 * 12/06/2023 Mario - Made adjustments to methods
 * 12/07/2023 Mario - Made adjustments to methods
 * 12/08/2023 Everyone - debugging
 */

import java.time.LocalDate;

public class ReservationManagement {
	private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double roomPrice;
    private RoomReservation reservedRoom;
    private RoomReservation room;
    private int numberOfPeople;


    /**
     * Constructor - ReservationMangement
     * @param room
     * @param occupant
     * @param checkInDate
     * @param checkOutDate
     */
    public ReservationManagement(RoomReservation room, AccountManagement occupant, LocalDate checkInDate, LocalDate checkOutDate, int numberOfPeople) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.roomPrice = room.getPricePerNight();
        this.reservedRoom = room;
        this.numberOfPeople = numberOfPeople;

    }
    
    // Setters and getters 
    public String getRoomNumber() {
        return reservedRoom.getNumber();
    }
    
    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }
    
    public RoomReservation getReservedRoom() {
        return reservedRoom;
    }

    public double getRoomPrice() {
        return roomPrice;
    }
    
    public RoomReservation getRoom() {
        return room;
    }
    
    public int getNumberOfPeople() {
        return numberOfPeople;
    }

}
