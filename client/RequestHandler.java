package client;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.*;

import org.json.JSONObject;

import lib.Logger;
import lib.RequestType;
import lib.SocketPacketHandler;

public class RequestHandler extends SocketPacketHandler implements Runnable{
    public static RequestHandler spHandler;
    private ArrayList<JSONObject> bufferedResponsePacket = new ArrayList<JSONObject>();

    public RequestHandler(Socket socketHandler) {
        super(socketHandler);
        RequestHandler.spHandler = this;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            String packet;
            while((packet = super.receivedPacket()) != null){
                Logger.newLog("new packet received: " + packet);
                JSONObject json = new JSONObject(packet);
                if(json.getInt("type") == RequestType.Server.NEW_MESSAGE.getValue()){
                    newMessagePacket(json);
                }else{
                    bufferedResponsePacket.add(json);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Connection Failed: " + e.getMessage());
        }
    }

    public void newMessagePacket(JSONObject packet){
        for (Chatroom room : GroupList.openedRoom){
            if(room.getRoomID() == packet.getInt("room_id")){
                room.ShowMessage(packet.getInt("from"), packet.getString("message"));
            }
        }
    }

    public JSONObject lastResponsePacket() {
        while(bufferedResponsePacket.size() == 0){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
        return bufferedResponsePacket.remove(0);
    }
    
    public static String getErrorTypeMessage(int Type) {
        if (Type == RequestType.Server.USER_NOT_EXIST.getValue()) {
            return "User not exist";
        } else if (Type == RequestType.Server.ROOM_NOT_EXIST.getValue()) {
            return "Room not exist";
        } else if (Type == RequestType.Server.ROOM_ALREADY_EXIST.getValue()) {
            return "Room Already Exist";
        } else if (Type == RequestType.Server.OPERATION_FAILED.getValue()) {
            return "Operation Failed";
        } else if (Type == RequestType.Server.USER_NOT_ACCESS_INTO_ROOM.getValue()) {
            return "User not access into room";
        } else if (Type == RequestType.Server.PERMISSION_DENIED.getValue()) {
            return "Permission denied";
        } else {
            return "";
        }
    }

}
