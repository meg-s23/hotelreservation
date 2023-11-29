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
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class ReservationManagement {
	private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double roomPrice;
    private RoomManagement reservedRoom;
    private RoomManagement room;
    
    /**
     * main (driver for sessionManagement)
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create a hotel with 9 rooms
            Input hotel = new Input("Sample Hotel", 9);

            // Create and display the GUI
            SessionManagement sessionManagement = new SessionManagement(hotel);
            sessionManagement.setVisible(true);
        });
        
    }

    /**
     * Constructor - ReservationMangement
     * @param room
     * @param occupant
     * @param checkInDate
     * @param checkOutDate
     */
    public ReservationManagement(RoomManagement room, AccountManagement occupant, LocalDate checkInDate, LocalDate checkOutDate) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.roomPrice = room.getPricePerNight();
        this.reservedRoom = room;
        this.room = room;

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
    
    public RoomManagement getReservedRoom() {
        return reservedRoom;
    }

    public double getRoomPrice() {
        return roomPrice;
    }
    
    public RoomManagement getRoom() {
        return room;
    }
}
