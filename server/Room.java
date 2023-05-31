package server;

import java.sql.SQLException;
import org.json.JSONArray;

import lib.Logger;
import lib.Error.RoomNotExistsException;

public class Room {
    private int Id;

    /**
     * Constructs a Room object with the given ID if it exists in the LiveShare database.
     *
     * @param Id The ID of the room to be constructed.
     * @throws RoomNotExistsException If the room with the given ID does not exist in the LiveShare database.
     */
    public Room(int Id) throws RoomNotExistsException {
        if(LiveShareDB.isRoomExists(Id)){
            this.Id = Id;
        }else{
            throw new RoomNotExistsException();
        }
    }

    /**
     * Returns the ID of this object.
     *
     * @return The ID of this object.
     */
    public int getId() {
        return Id;
    }

    /**
     * Retrieves all messages for the current room from the database.
     *
     * @return A JSONArray containing all messages for the current room, or null if an error occurs.
     * @throws SQLException if there is an error retrieving the messages from the database.
     */
    public JSONArray getAllMessages() {
        try {
            return LiveShareDB.getRoomMessage(this.Id);
        } catch (SQLException e) {
            Logger.newWarning("failed to get room " + this.Id + " message! -> " + e.getMessage());
            return null;
        }
    }

    /**
     * Checks if a user is in the current room.
     *
     * @param userID The ID of the user to check.
     * @return True if the user is in the room, false otherwise.
     */
    public Boolean isUserInRoom(int userID) {
        return LiveShareDB.isUserInGroup(userID, getId());
    }
}