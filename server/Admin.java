package server;

import org.json.JSONArray;

import lib.SocketPacketHandler;
import lib.Error.RoomNotExistsException;

public class Admin extends PremiumUser {
    public Admin(int Id, SocketPacketHandler handler) {
        super(Id, handler);
    }

    public JSONArray getRoomMessage(int Room) throws RoomNotExistsException {
        return new Room(Room).getAllMessages();
    }
}
