package hotelreservation;

/**
 * CMSC 495 Section 7380
 * Group 8
 * Author: Mario Bethancourt, Jules Torres, Megan Moore
 * Professor Hung Dao
 * Date: 11/21/2023
 * Description: This class represents the initial configuration input for the hotel with multiple floors and rooms.
 * Contains the hotel's name and a list of RoomManagement instances representing the rooms in the hotel.
 * It acts a way to keep track of the hotel rooms that the user need to input into the program.
 * Revisions
 * 11/21/2023 Everyone - Created the class. 
 * 11/22/2023 Everyone - Implemented methods based on other classes.
 * 11/23/2023 Jules - added revisions
 * 11/23/2023 Everyone - Changed methods based on the rooms that can be reserved.
 * 11/25/2023 Mario, Jules    - debugging.
 * 12/03/2023 Everyone - debugging
 * 12/06/2023 Mario - Made adjustments to methods
 * 12/07/2023 Mario - Made adjustments to methods
 * 12/08/2023 Everyone - debugging
 */

import java.util.ArrayList;
import java.util.List;

public class Input {
    private List<RoomReservation> rooms; //array list <RoomMangement> 

    /**
     * Constructor - Input 
     * @param name
     * @param floors
     */
    public Input(String name, int floors) {
        this.rooms = new ArrayList<>();
        for (int floor = 1; floor <= floors; floor++) {
            createRoom(floor, "A");
            createRoom(floor, "B");
            createRoom(floor, "C");
            createRoom(floor, "D");
        }
    }

    /**
     * void createRoom
     * @param floor
     * @param type
     */
    private void createRoom(int floor, String type) {
        String roomNumber = String.format("%d%02d", floor, convertTypeToNumber(type));
        RoomReservation room = new RoomReservation(roomNumber, type, floor);
        rooms.add(room);
    }

    private int convertTypeToNumber(String type) {
        switch (type) {
            case "A":
                return 1;
            case "B":
                return 2;
            case "C":
                return 3;      
            case "D":
            	return 4;
            default:
                return 0;
        }
    }

    /**
     * List<RoomManagement> getRooms
     * @return rooms
     */
    public List<RoomReservation> getRooms() {
        return rooms;
    }

    /**
     * RoomManagement getRoomByNumber
     * @param roomNumber
     * @return room
     */
    public RoomReservation getRoomByNumber(String roomNumber) {
        for (RoomReservation room : rooms) {
            if (room.getNumber().equals(roomNumber)) {
                return room;
            }
        }
        return null;
    }
}