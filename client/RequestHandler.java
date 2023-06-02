package client;

import java.io.IOException;
import java.net.Socket;

import javax.swing.*;

import org.json.JSONObject;

import lib.Logger;
import lib.RequestType;
import lib.SocketPacketHandler;

public class RequestHandler extends SocketPacketHandler {
    public boolean isAuth = false;
    public static RequestHandler spHandler;

    public RequestHandler(Socket socketHandler) {
        super(socketHandler);
        RequestHandler.spHandler = this;
    }

    public JSONObject LastPacket() {
        try {
            String packet;
            packet = super.receivedPacket();
            Logger.newLog("new packet received: " + packet);
            JSONObject json = new JSONObject(packet);
            return json;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Connection Failed: " + e.getMessage());
            return null;
        }
    }

    public static String getErrorTypeMessage(int Type){
        if (Type == RequestType.Server.USER_NOT_EXIST.getValue()){
            return "User not exist";
        }else if (Type == RequestType.Server.ROOM_NOT_EXIST.getValue()){
            return "Room not exist";
        }else if (Type == RequestType.Server.ROOM_ALREADY_EXIST.getValue()){
            return "Room Already Exist";
        }else if (Type == RequestType.Server.OPERATION_FAILED.getValue()){
            return "Operation Faild";
        }else if (Type == RequestType.Server.USER_NOT_ACCESS_INTO_ROOM.getValue()){
            return "User not access into room";
        }else if (Type == RequestType.Server.PERMISSION_DENIED.getValue()){
            return "Permission denied";
        }else{
            return "";
        }
    }

}
