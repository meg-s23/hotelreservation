package hotelreservation;

/**
 * File: AccountManagement.java
 * CMSC 495 Section 7380
 * Group 8
 * Author: Mario Bethancourt, Jules Torres, Megan Moore
 * Professor Hung Dao
 * Date: 11/21/2023
 * Description: This class represents an individual user account in the hotel reservation system.
 * Revisions
 * 11/21/2023 Everyone - Created the class, added methods.
 * 11/22/2023 Everyone - Implemented more method based on the other classes.
 * 11/23/2023 Everyone - Implemented methods related to storing user account information.
 * 11/24/2023 Everyone - Implemented error handling.
 * 11/25/2023 Jules, Mario - debugging.
 * 12/01/2023 Mario - Implemented more methods and modified existing ones
 * 12/02/2023 Jules - Worked on the methods implemented 
 * 12/03/2023 Everyone - debugging
 */

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class AccountManagement implements Serializable {
	private static final long serialVersionUID = 1L;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String streetNumber;
    private String streetName;
    private String email;
    private String state;
    private String city;
    private String zipCode;
    private String username;
    private String password;
    private String checkInID;
    private List<ReservationManagement> reservations;
    
    /**
     * COnstructor - AccountManagement
     * @param name
     * @param lastName
     * @param phoneNumber
     * @param address
     * @param username
     * @param password
     */
    public AccountManagement(String name, String lastName, String phoneNumber, String streetNumber, String streetName, String email,
            String state, String city, String zipCode, String username, String password) {
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.email = email;
        this.state = state;
        this.city = city;
        this.zipCode = zipCode;
        this.username = username;
        this.password = password;
        this.reservations = new ArrayList<>();
    }
    
    /**
     * Constructor - only takes username and password
     * @param username
     * @param password
     */
    public AccountManagement(String username, String password) {
    	this.username = username;
        this.password = password;
	}
    
    /**
     * updateUserInformation
     * @param name
     * @param lastName
     * @param phoneNumber
     * @param address
     */
	public void updateUserInfo(String name, String lastName, String phoneNumber, String streetNumber, String streetName) {
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
    }

	//Setters and getters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreetNumber() {
        return streetNumber;
    }
    
    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }
    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    public String getStreetName() {
        return streetName;
    }
    
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getCheckInID() {
        return checkInID;
    }

    public void setCheckInID(String checkInID) {
        this.checkInID = checkInID;
    }
    
    public List<ReservationManagement> getReservations() {
        return reservations;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void addReservation(ReservationManagement reservation) {
        reservations.add(reservation);
    }
    
    public void removeReservationByRoomNumber(String roomNumber) {
        reservations.removeIf(reservation -> reservation.getRoomNumber().equals(roomNumber));
    }
    
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }    
    
    /**
     * updatePassword
     * @param oldPassword
     * @param newPassword
     * @return
     */
    public boolean updatePassword(String oldPassword, String newPassword) {
        // Check if the old password is correct
        if (checkPassword(oldPassword)) {
            // Update the password
            this.password = newPassword;
            return true; // Password updated successfully
        } else {
            return false; // Incorrect old password
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}