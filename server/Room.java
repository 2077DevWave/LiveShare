package server;

import java.sql.SQLException;
import org.json.JSONArray;

import lib.Logger;
import lib.Error.RoomNotExistsException;

public class Room {
    private int Id;
    private int userLimit;

    public Room(int Id) throws RoomNotExistsException {
        if(LiveShareDB.isRoomExists(Id)){
            this.Id = Id;
        }else{
            throw new RoomNotExistsException();
        }
    }

    public int getId() {
        return Id;
    }

    public JSONArray getAllMessages() {
        try {
            return LiveShareDB.getRoomMessage(this.Id);
        } catch (SQLException e) {
            Logger.newWarning("failed to get room " + this.Id + " message! -> " + e.getMessage());
            return null;
        }
    }

    public Boolean isUserInRoom(int userID) {
        return LiveShareDB.isUserInGroup(userID, getId());
    }
}