package server;

import lib.Error;
import lib.Logger;
import lib.Error.OperationFailedException;
import lib.Error.RoomNotExistsException;

public class Group extends Room{

    /**
     * Constructs a new Group object with the given ID.
     *
     * @param id The ID of the group.
     * @throws RoomNotExistsException If the room does not exist.
     */
    public Group(int id) throws RoomNotExistsException {
        super(id);
    }

    /**
    * Adds a user to the group. This is a no - op if the user is already in the group
    * 
    * @param userID - the user's
    */
    public void addUser(int userID) throws OperationFailedException {
        LiveShareDB.addUserIntoRoom(userID, super.getId());

        // Add this user to the group.
        if (LiveShareDB.isUserInGroup(userID, this.getId())) {
            Logger.newWarning("user " + userID + " successfully added into group " + this.getId());
        } else {
            throw new Error.OperationFailedException();
        }
    }

}
