package client;

import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lib.Logger;
import lib.RequestType;
import lib.SocketPacketHandler;

public class RequestHandler extends SocketPacketHandler implements Runnable {
    public boolean isAuth = false;

    public RequestHandler(Socket socketHandler) {
        super(socketHandler);
        new Thread(this).start();
    }

    @Override
    /**
     * The run method of the Thread. Receives and handel packets from the network
     * until there is no packet
     */
    public void run() {
        try {
            String packet;
            while ((packet = super.receivedPacket()) != null) {
                // This method is called when the server receives a new packet.
                Logger.newLog("new packet received: " + packet);
                handelIncomingRequest(packet);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Connection Failed: " + e.getMessage());
        }
    }

    /**
     * Handels an incoming request. This is called by JabRef when it receives a
     * packet that is to be handled by the server
     * 
     * @param packet - the packet to be
     */
    public void handelIncomingRequest(String packet) {
        try {
            JSONObject json = new JSONObject(packet);
            int type = json.getInt("type");
            if (type == RequestType.Server.AUTHENTICATE.getValue()) {
                int errCode = json.getInt("err");
                if (errCode == RequestType.Server.AUTHENTICATE_NO_ERROR.getValue()) {
                }
                // This method is called when the user is not authenticated.
                else if (errCode == RequestType.Server.AUTHENTICATE_LIMIT_RETRY.getValue()) {
                    // This method is called when the user is authenticated.
                    JOptionPane.showMessageDialog(null, "you have exceeded the limit!");
                } else if (errCode == RequestType.Server.AUTHENTICATE_SUCCESS.getValue()) {
                    // This method is called when the user is authenticated.
                    JOptionPane.showMessageDialog(null, "login succeeded!");
                    this.isAuth = true;
                } else if (errCode == RequestType.Server.AUTHENTICATE_WRONG_PASSWORD.getValue()) {
                    // This method is used to check if the request is valid.
                    JOptionPane.showMessageDialog(null, "wrong password!");
                } else if (errCode == RequestType.Server.AUTHENTICATE_WRONG_USERNAME.getValue()) {
                    // If the request type is not authentication error.
                    JOptionPane.showMessageDialog(null, "wrong user id!");
                } else {
                    JOptionPane.showMessageDialog(null, "unknown auth error!");
                }

                if (!isAuth) {
                    // Authenticates the user with the given username and password.
                    int username = Integer.parseInt(JOptionPane.showInputDialog("username"));
                    String password = JOptionPane.showInputDialog("password");
                    super.sendPacket(Request.Auth.authInfo(username, password));
                }

            } else if (type == RequestType.Server.ALL_ROOM_MESSAGES.getValue()) {
                // This method is used to display the message box
                // room_id, message : String JSON Array with from , message
                int roomId = json.getInt("room_id");
                JSONArray messages = json.getJSONArray("messages");

                JOptionPane.showMessageDialog(null, "room " + roomId + "messages :");
                for (int i = 0; i < messages.length(); i++) {
                    // Show all messages in the messages list
                    JSONObject messageObj = messages.getJSONObject(i);
                    int from = messageObj.getInt("from");
                    String message = messageObj.getString("message");
                    JOptionPane.showMessageDialog(null, "user_" + from + " : " + message);
                }

            } else if (type == RequestType.Server.NEW_MESSAGE.getValue()) {
                // This method is used to get the message from the server.
                // room_id, message
                int roomID = json.getInt("room_id");
                String message = json.getString("message");
                System.out.print("new message available at " + roomID + " content " + message);
            } else if (type == RequestType.Server.EXCEPTION.getValue()) {
                // This method is used to check if the type is one of the types of the request.
                // error : int Error Code
                int errCode = json.getInt("error");
                if (errCode == RequestType.Server.OPERATION_FAILED.getValue()) {
                    // This method is used to check if the error code is a valid error code.
                    JOptionPane.showMessageDialog(null, "error : operation failed");
                } else if (errCode == RequestType.Server.PERMISSION_DENIED.getValue()) {
                    // The user has been logged in.
                    JOptionPane.showMessageDialog(null, "error : permission denied");
                } else if (errCode == RequestType.Server.ROOM_ALREADY_EXIST.getValue()) {
                    // This method is used to check if the error code is a valid error code.
                    JOptionPane.showMessageDialog(null, "error : room already exist");
                } else if (errCode == RequestType.Server.ROOM_NOT_EXIST.getValue()) {
                    // Shows dialog box if error code is not valid
                    JOptionPane.showMessageDialog(null, "error : room not exist");
                } else if (errCode == RequestType.Server.USER_NOT_ACCESS_INTO_ROOM.getValue()) {
                    // This method is used to check if the error code is a valid error code.
                    JOptionPane.showMessageDialog(null, "error : user not access into room");
                } else if (errCode == RequestType.Server.USER_NOT_EXIST.getValue()) {
                    // if errCode RequestType. Server. USER_NOT_EXIST. getValue
                    JOptionPane.showMessageDialog(null, "error : user not exist");
                } else {
                    JOptionPane.showMessageDialog(null, "error : unknown error id");
                }
            } else {
                JOptionPane.showMessageDialog(null, "unknown type " + type);
            }
        } catch (JSONException e) {
            Logger.newWarning("failed to convert into json, packet " + packet);
        }
    }
}
