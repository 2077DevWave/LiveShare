package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lib.Logger;
import lib.RequestType;
import lib.SocketPacketHandler;

public class RequestHandler extends SocketPacketHandler implements Runnable {
    private Socket userHandler;
    public boolean isAuth = false;

    public RequestHandler(Socket socketHandler) {
        super(socketHandler);
        this.userHandler = socketHandler;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            String packet;
            while ((packet = super.receivedPacket()) != null) {
                Logger.newLog("new packet received: " + packet);
                handelIncomingRequest(packet);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Connection Failed: " + e.getMessage());
        }
    }

    public void handelIncomingRequest(String packet) {
        try {
            JSONObject json = new JSONObject(packet);
            int type = json.getInt("type");
            if (type == RequestType.Server.AUTHENTICATE.getValue()) {
                int errCode = json.getInt("err");
                if(errCode == RequestType.Server.AUTHENTICATE_NO_ERROR.getValue()) {}
                else if (errCode == RequestType.Server.AUTHENTICATE_LIMIT_RETRY.getValue()){
                    JOptionPane.showMessageDialog(null,"you have exceeded the limit!");
                }
                else if (errCode == RequestType.Server.AUTHENTICATE_SUCCESS.getValue()){
                    JOptionPane.showMessageDialog(null,"login succeeded!");
                    this.isAuth = true;
                }
                else if (errCode == RequestType.Server.AUTHENTICATE_WRONG_PASSWORD.getValue()){
                    JOptionPane.showMessageDialog(null,"wrong password!");
                }
                else if (errCode == RequestType.Server.AUTHENTICATE_WRONG_USERNAME.getValue()){
                    JOptionPane.showMessageDialog(null,"wrong user id!");
                }
                else{
                    JOptionPane.showMessageDialog(null,"unknown auth error!");
                }

                if(!isAuth){
                    int username = Integer.parseInt(JOptionPane.showInputDialog("username"));
                    String password = JOptionPane.showInputDialog("password");
                    super.sendPacket(Request.Auth.authInfo(username, password));
                }

            } else if (type == RequestType.Server.ALL_ROOM_MESSAGES.getValue()) {
                // room_id, message : String JSON Array with from , message
                int roomId = json.getInt("room_id");
                JSONArray messages = json.getJSONArray("messages");

                JOptionPane.showMessageDialog(null,"room " + roomId + "messages :");
                for (int i = 0; i < messages.length(); i++) {
                    JSONObject messageObj = messages.getJSONObject(i);
                    int from = messageObj.getInt("from");
                    String message = messageObj.getString("message");
                    JOptionPane.showMessageDialog(null,"user_" + from + " : " + message);
                }

            } else if (type == RequestType.Server.NEW_MESSAGE.getValue()) {
                // room_id, message
                int roomID = json.getInt("room_id");
                String message = json.getString("message");
                System.out.print("new message available at " + roomID + " content " + message);
            } else if (type == RequestType.Server.EXCEPTION.getValue()) {
                // error : int Error Code
                int errCode = json.getInt("error");
                if (errCode == RequestType.Server.OPERATION_FAILED.getValue()) {
                    JOptionPane.showMessageDialog(null,"error : operation failed");
                } else if (errCode == RequestType.Server.PERMISSION_DENIED.getValue()) {
                    JOptionPane.showMessageDialog(null,"error : permission denied");
                } else if (errCode == RequestType.Server.ROOM_ALREADY_EXIST.getValue()) {
                    JOptionPane.showMessageDialog(null,"error : room already exist");
                } else if (errCode == RequestType.Server.ROOM_NOT_EXIST.getValue()) {
                    JOptionPane.showMessageDialog(null,"error : room not exist");
                } else if (errCode == RequestType.Server.USER_NOT_ACCESS_INTO_ROOM.getValue()) {
                    JOptionPane.showMessageDialog(null,"error : user not access into room");
                } else if (errCode == RequestType.Server.USER_NOT_EXIST.getValue()) {
                    JOptionPane.showMessageDialog(null,"error : user not exist");
                } else {
                    JOptionPane.showMessageDialog(null,"error : unknown error id");
                }
            } else {
                JOptionPane.showMessageDialog(null,"unknown type " + type);
            }
        } catch (JSONException e) {
            Logger.newWarning("failed to convert into json, packet " + packet);
        }
    }
}
