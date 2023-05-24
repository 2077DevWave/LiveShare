package server;

import lib.Error;
import lib.Logger;
import lib.Error.OperationFailedException;
import lib.Error.RoomNotExistsException;

public class Group extends Room{

    public Group(int id) throws RoomNotExistsException {
        super(id);
    }

    public void addUser(int userID) throws OperationFailedException {
        LiveShareDB.addUserIntoRoom(userID, super.getId());

        if (LiveShareDB.isUserInGroup(userID, this.getId())) {
            Logger.newWarning("user " + userID + " successfully added into group " + this.getId());
        } else {
            throw new Error.OperationFailedException();
        }
    }

}
