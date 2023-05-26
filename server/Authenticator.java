package server;

import java.io.IOException;
import java.net.Socket;

import org.json.JSONObject;

import lib.Logger;
import lib.SocketPacketHandler;

public class Authenticator {
    SocketPacketHandler handler;

    /**
     * Creates a new instance of the Authenticator class with the specified socket connection.
     *
     * @param connection The socket connection to use for authentication.
     */
    public Authenticator(Socket connection) {
        handler = new SocketPacketHandler(connection);
    }

    /**
    * Authenticate the user with the server. This is called when the server is trying to log in and we have a valid username and password
    * 
    * 
    * @return the user's
    */
    public int getAuth() {
        handler.sendPacket(Request.Auth.getInformation());
        // This method is used to check if the user has been authenticated.
        for (int i = 0; i < Config.MAX_AUTH_RETRY.getIntVal(); i++) {
            try {
                JSONObject packet = new JSONObject(new String(handler.receivedPacket()));
                int userID = packet.getInt("user_id");
                String userPass = packet.getString("password");
                Logger.newLog("new auth request for user " + userID);
                // Check if the user exists and if yes return the user s username
                if (LiveShareDB.isUserExists(userID)) {
                    String truePass = LiveShareDB.getUserPassword(userID);
                    // if user enter wrong password
                    if (truePass.equals(userPass)) {
                        handler.sendPacket(Request.Auth.Successfully());
                        Logger.newLog("user " + userID + " Authorized Successfully");
                        return userID;
                    }else{
                        Logger.newLog("user enter wrong password for user " + userID);
                        handler.sendPacket(Request.Auth.wrongPassword());
                    }
                }else{
                    Logger.newLog("user " + userID + " is not exist to authenticate");
                    handler.sendPacket(Request.Auth.wrongUserName());
                }
            } catch (IOException e) {
                Logger.newError("Failed to read authentication -> Stream : " + e.getMessage());
                return -1;
            }
        }
        Logger.newLog("authentication failed because user get retry limit");
        handler.sendPacket(Request.Auth.retryLimit());
        try {
            this.handler.handler.close();
        } catch (IOException e) {
            Logger.newWarning("failed to close authentication -> Socket : " + e.getMessage());
        }
        return -1;
    }

    /**
    * Creates a User based on the rules. It is possible to create more than one User for a given user ID
    * 
    * @param userID - the ID of the user
    * 
    * @return a User that has been
    */
    public User createUserHandler(int userID){
        // Returns a User object for the user.
        if(LiveShareDB.isUserExists(userID)){
            // Returns the user rule for the user.
            switch(LiveShareDB.getUserRule(userID)){
                case -1:
                    Logger.newWarning("failed to get user Rule for user " + userID);
                    return null;
                case 1:
                    return new User(userID, this.handler);
                case 2:
                    return new PremiumUser(userID, this.handler);
                case 3:
                    return new Admin(userID, this.handler);
                default:
                    Logger.newWarning("Unknown rule number!");
                    return null;
            }
        }else{
            Logger.newWarning("User Not Exists! to create Handler for user " + userID);
        }
        return null;
    }

    /**
    * Returns the user that is authenticated. This is a convenience method that calls #getAuth () and then passes the result to #createUserHandler ( int ).
    * 
    * 
    * @return the user or null if there is no authentification to the user or the user could not be
    */
    public User fullAuth(){
        int userID;
        // Returns the user handler for the current user.
        if((userID = getAuth()) != -1){
            return createUserHandler(userID);
        }
        return null;
    }

}
