package server;

import lib.Error;
import lib.Logger;
import lib.SocketPacketHandler;
import lib.Error.OperationFailedException;
import lib.Error.RoomAlreadyExistsException;
import lib.Error.RoomNotExistsException;

public class PremiumUser extends User{

    public PremiumUser(int id, SocketPacketHandler handler){
        super(id, handler);
    }
    
    public void joinGroup(int groupId) throws RoomNotExistsException, OperationFailedException {
        Group room = new Group(groupId);
        room.addUser(getId());
    }

    public Boolean createGroup(String Name) throws RoomAlreadyExistsException, OperationFailedException {
        if (!LiveShareDB.isRoomExists(LiveShareDB.getRoomId(Name))) {
            LiveShareDB.newRoom(Name, Config.GROUP_MEMBER_LIMIT.getIntVal());
        } else {
            Logger.newWarning("failed to create room " + Name + ", room already exists!");
            throw new Error.RoomAlreadyExistsException();
        }

        if (LiveShareDB.getRoomId(Name) != -1) {
            Logger.newLog("Room created successfully with name " + Name);
            return true;
        } else {
            throw new Error.OperationFailedException();
        }
    }

}
