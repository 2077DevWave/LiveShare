package server;

import java.sql.SQLException;

import org.json.JSONArray;

import lib.Logger;
import lib.SocketPacketHandler;
import lib.Error;
import lib.Error.OperationFailedException;
import lib.Error.RoomAlreadyExistsException;
import lib.Error.RoomNotExistsException;
import lib.Error.UserNotAccessIntoRoomException;
import lib.Error.UserNotExistsException;

public class User {
    private int Id;
    private SocketPacketHandler handler;
    public String Name;

    /**
     * Constructs a User object with the given ID and SocketPacketHandler.
     *
     * @param Id      The ID of the user.
     * @param handler The SocketPacketHandler for the user.
     */
    public User(int Id, SocketPacketHandler handler) {
        this.Id = Id;
        this.handler = handler;
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
     * Returns the SocketPacketHandler object associated with this instance.
     *
     * @return The SocketPacketHandler object associated with this instance.
     */
    public SocketPacketHandler getHandler() {
        return handler;
    }

    /**
     * Creates a new room with the given name and adds the current user and another
     * user to it.
     *
     * @param Name The name of the room to be created.
     * @param With The ID of the user to be added to the room.
     * @throws RoomAlreadyExistsException if a room with the given name already
     *                                    exists.
     * @throws UserNotExistsException     if the user with the given ID does not
     *                                    exist.
     */
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

    /**
     * Sends a message to a specified room if the user is a member of that room.
     *
     * @param Room    The ID of the room to send the message to.
     * @param Message The message to send.
     * @throws UserNotAccessIntoRoomException if the user is not a member of the
     *                                        specified room.
     */
    public void SendMessage(int Room, String Message) throws UserNotAccessIntoRoomException {
        if (LiveShareDB.isUserInGroup(getId(), Room)) {
            Logger.newLog("send message into group " + Room + "from " + getId() + " as " + Message);
            LiveShareDB.addMessageIntoRoom(Room, getId(), Message);
            try {
                Room room = new Room(Room);
                room.sendMessageInRoomOnline(this.Id,Message);
            } catch (RoomNotExistsException e) {
            }
        } else {
            throw new Error.UserNotAccessIntoRoomException();
        }
    }

    /**
     * Returns all messages in a given room.
     *
     * @param Room The ID of the room to retrieve messages from.
     * @return A JSONArray containing all messages in the room.
     * @throws UserNotAccessIntoRoomException If the user does not have access to
     *                                        the room.
     * @throws RoomNotExistsException         If the room does not exist.
     */
    public JSONArray getRoomMessage(int Room) throws UserNotAccessIntoRoomException, RoomNotExistsException {
        if (LiveShareDB.isUserInGroup(getId(), Room)) {
            return new Room(Room).getAllMessages();
        } else {
            throw new Error.UserNotAccessIntoRoomException();
        }
    }

    public JSONArray getUserRoomList() throws UserNotExistsException,OperationFailedException{
        if(LiveShareDB.isUserExists(Id)){
            JSONArray Groups;
            try {
                Groups = LiveShareDB.allUserGroup(Id);
            } catch (SQLException e) {
                throw new OperationFailedException();
            }
            return Groups;
        }else{
            throw new UserNotExistsException();
        }
    }
}
