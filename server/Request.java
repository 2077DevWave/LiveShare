package server;

import lib.RequestType;
import org.json.JSONArray;
import org.json.JSONObject;

public class Request {

    class Auth {

        public static String getInformation() {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Server.AUTHENTICATE.getValue());
            request.put("err", RequestType.Server.AUTHENTICATE_NO_ERROR.getValue());
            return request.toString();
        }

        public static String wrongPassword() {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Server.AUTHENTICATE.getValue());
            request.put("err", RequestType.Server.AUTHENTICATE_WRONG_PASSWORD.getValue());
            return request.toString();
        }

        public static String wrongUserName() {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Server.AUTHENTICATE.getValue());
            request.put("err", RequestType.Server.AUTHENTICATE_WRONG_USERNAME.getValue());
            return request.toString();
        }

        public static String Successfully() {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Server.AUTHENTICATE.getValue());
            request.put("err", RequestType.Server.AUTHENTICATE_SUCCESS.getValue());
            return request.toString();
        }

        public static String retryLimit() {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Server.AUTHENTICATE.getValue());
            request.put("err", RequestType.Server.AUTHENTICATE_LIMIT_RETRY.getValue());
            return request.toString();
        }
    
    }

    class Message{
        public static String allRoomMessage(int roomID, JSONArray Messages){
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Server.ALL_ROOM_MESSAGES.getValue());
            request.put("room_id", roomID);
            request.put("message", Messages.toString()); // from , message
            return request.toString();
        }
    
        public static String newMessage(int roomID, String Messages){
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Server.NEW_MESSAGE.getValue());
            request.put("room_id", roomID);
            request.put("message", Messages);
            return request.toString();
        }
    }

    class Other{
        public static String Exception(int ERR_CODE){
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Server.EXCEPTION.getValue());
            request.put("error", ERR_CODE);
            return request.toString();
        }
    }

}