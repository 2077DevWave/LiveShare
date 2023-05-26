package server;

import org.json.JSONArray;

import lib.SocketPacketHandler;
import lib.Error.RoomNotExistsException;

public class Admin extends PremiumUser {

    /**
     * Constructs an Admin object with the given ID and SocketPacketHandler.
     *
     * @param Id The ID of the Admin object.
     * @param handler The SocketPacketHandler to use for the Admin object.
     */
    public Admin(int Id, SocketPacketHandler handler) {
        super(Id, handler);
    }

    /**
    * Gets the messages in a room. This is a JSON array of JSON objects that are in the format { " message " : " text " }
    * 
    * @param Room - The ID of the room to get messages for
    * 
    * @return A JSON array of message
    */
    public JSONArray getRoomMessage(int Room) throws RoomNotExistsException {
        return new Room(Room).getAllMessages();
    }

}
