package server;

import org.json.JSONArray;

import lib.Logger;
import lib.SocketPacketHandler;
import lib.Error;
import lib.Error.RoomAlreadyExistsException;
import lib.Error.RoomNotExistsException;
import lib.Error.UserNotAccessIntoRoomException;
import lib.Error.UserNotExistsException;

public class User {
    private int Id;
    private SocketPacketHandler handler;
    public String Name;

    public User(int Id, SocketPacketHandler handler) {
        this.Id = Id;
        this.handler = handler;
    }

    public int getId() {
        return Id;
    }

    public SocketPacketHandler getHandler() {
        return handler;
    }

    public void CreateRoom(String Name, int With) throws RoomAlreadyExistsException, UserNotExistsException {
        if (LiveShareDB.isUserExists(With)) {
            if (!LiveShareDB.isRoomExists(LiveShareDB.getRoomId(Name))) {
                LiveShareDB.newRoom(Name, Config.ROOM_USER_LIMIT.getIntVal());
                int RoomId = LiveShareDB.getRoomId(Name);
                LiveShareDB.addUserIntoRoom(getId(), RoomId);
                LiveShareDB.addUserIntoRoom(With, RoomId);
            } else {
                throw new Error.RoomAlreadyExistsException();
            }
        } else {
            throw new Error.UserNotExistsException();
        }
    }

    public void SendMessage(int Room, String Message) throws UserNotAccessIntoRoomException {
        if (LiveShareDB.isUserInGroup(getId(), Room)) {
            Logger.newLog("send message into group " + Room + "from " + getId() + " as " + Message);
            LiveShareDB.addMessageIntoRoom(Room, getId(), Message);
        } else {
            throw new Error.UserNotAccessIntoRoomException();
        }
    }

    public JSONArray getRoomMessage(int Room) throws UserNotAccessIntoRoomException, RoomNotExistsException {
        if (LiveShareDB.isUserInGroup(getId(), Room)) {
            return new Room(Room).getAllMessages();
        } else {
            throw new Error.UserNotAccessIntoRoomException();
        }
    }
}
