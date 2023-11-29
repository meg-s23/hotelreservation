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
 */

import java.util.ArrayList;
import java.util.List;

public class Input {
    private String name; //data field name
    private List<RoomManagement> rooms; //array list <RoomMangement> 

    /**
     * Constructor - Input 
     * @param name
     * @param floors
     */
    public Input(String name, int floors) {
        this.name = name;
        this.rooms = new ArrayList<>();

        // Create rooms for each floor and type
        for (int floor = 1; floor <= floors; floor++) {
            createRoom(floor, "A");
            createRoom(floor, "B");
            createRoom(floor, "C");
        }
    }

    /**
     * void createRoom
     * @param floor
     * @param type
     */
    private void createRoom(int floor, String type) {
        String roomNumber = floor + type;
        RoomManagement room = new RoomManagement(roomNumber, type);
        rooms.add(room);
    }

    /**
     * List<RoomManagement> getRooms
     * @return rooms
     */
    public List<RoomManagement> getRooms() {
        return rooms;
    }

    /**
     * RoomManagement getRoomByNumber
     * @param roomNumber
     * @return room
     */
    public RoomManagement getRoomByNumber(String roomNumber) {
        for (RoomManagement room : rooms) {
            if (room.getNumber().equals(roomNumber)) {
                return room;
            }
        }
        return null;
    }
}