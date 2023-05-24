package server;

import java.io.IOException;
import java.net.Socket;

import org.json.JSONObject;

import lib.Logger;
import lib.SocketPacketHandler;

public class Authenticator {
    SocketPacketHandler handler;

    public Authenticator(Socket connection) {
        handler = new SocketPacketHandler(connection);
    }

    public int getAuth() {
        handler.sendPacket(Request.Auth.getInformation());
        for (int i = 0; i < Config.MAX_AUTH_RETRY.getIntVal(); i++) {
            try {
                JSONObject packet = new JSONObject(new String(handler.receivedPacket()));
                int userID = packet.getInt("user_id");
                String userPass = packet.getString("password");
                Logger.newLog("new auth request for user " + userID);
                if (LiveShareDB.isUserExists(userID)) {
                    String truePass = LiveShareDB.getUserPassword(userID);
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

    public User createUserHandler(int userID){
        if(LiveShareDB.isUserExists(userID)){
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

    public User fullAuth(){
        int userID;
        if((userID = getAuth()) != -1){
            return createUserHandler(userID);
        }
        return null;
    }

}
