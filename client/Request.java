package client;

import org.json.JSONObject;
import lib.RequestType;

public class Request {

    public class Auth {

        public static String authInfo(int userID, String pass) {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Server.AUTHENTICATE.getValue());
            request.put("user_id", userID);
            request.put("password", pass);
            return request.toString();
        }
    
    }

    public class Room {

        public static String createRoom(int userID, String Name) {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Client.CREATE_ROOM.getValue());
            request.put("with", userID);
            request.put("name", Name);
            return request.toString();
        }

        public static String createGroup(String Name) {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Client.CREATE_GROUP.getValue());
            request.put("name", Name);
            return request.toString();
        }

        public static String joinGroup(int groupID) {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Client.JOIN_GROUP.getValue());
            request.put("id", groupID);
            return request.toString();
        }
    
    }

    public class Message {

        public static String sendMessage(int roomID, String Message) {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Client.SEND_MESSAGE.getValue());
            request.put("id", roomID);
            request.put("message", Message);
            return request.toString();
        }

        public static String getMessage(int roomID) {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Client.GET_ROOM_MESSAGES.getValue());
            request.put("id", roomID);
            return request.toString();
        }
    
    }
}
