package server;

import lib.Error;
import lib.Logger;
import lib.SocketPacketHandler;
import lib.Error.OperationFailedException;
import lib.Error.RoomAlreadyExistsException;
import lib.Error.RoomNotExistsException;

public class PremiumUser extends User{

    /**
     * Constructs a new PremiumUser object with the given ID and SocketPacketHandler.
     *
     * @param id The ID of the user.
     * @param handler The SocketPacketHandler for the user.
     */
    public PremiumUser(int id, SocketPacketHandler handler){
        super(id, handler);
    }
    
    /**
     * Joins a group with the given group ID.
     *
     * @param groupId The ID of the group to join.
     * @throws RoomNotExistsException If the group does not exist.
     * @throws OperationFailedException If the operation fails for any other reason.
     */
    public void joinGroup(int groupId) throws RoomNotExistsException, OperationFailedException {
        Group room = new Group(groupId);
        room.addUser(getId());
    }

    /**
     * Creates a new group with the given name.
     *
     * @param Name The name of the group to create.
     * @return true if the group was successfully created, false otherwise.
     * @throws RoomAlreadyExistsException if a group with the given name already exists.
     * @throws OperationFailedException if the operation fails for any other reason.
     */
    public void createGroup(String Name) throws RoomAlreadyExistsException, OperationFailedException {
        if (!LiveShareDB.isRoomExists(LiveShareDB.getRoomId(Name))) {
            LiveShareDB.newRoom(Name, Config.GROUP_MEMBER_LIMIT.getIntVal());
        } else {
            Logger.newWarning("failed to create room " + Name + ", room already exists!");
            throw new Error.RoomAlreadyExistsException();
        }

        if (LiveShareDB.getRoomId(Name) != -1) {
            Logger.newLog("Room created successfully with name " + Name);
        } else {
            throw new Error.OperationFailedException();
        }
    }

}
