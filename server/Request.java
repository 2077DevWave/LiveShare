package server;

import lib.RequestType;
import org.json.JSONArray;
import org.json.JSONObject;

public class Request {

    class Auth {

        /**
         * Returns a JSON string containing information about the authentication
         * request.
         *
         * @return A JSON string containing information about the authentication
         *         request.
         */
        public static String getInformation() {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Server.AUTHENTICATE.getValue());
            request.put("err", RequestType.Server.AUTHENTICATE_NO_ERROR.getValue());
            return request.toString();
        }

        /**
         * Generates a JSON string representing an authentication request with a wrong
         * password error.
         *
         * @return A JSON string representing an authentication request with a wrong
         *         password error.
         */
        public static String wrongPassword() {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Server.AUTHENTICATE.getValue());
            request.put("err", RequestType.Server.AUTHENTICATE_WRONG_PASSWORD.getValue());
            return request.toString();
        }

        /**
         * Generates a JSON string representing an authentication request with an error
         * message indicating that the username is incorrect.
         *
         * @return A JSON string representing the authentication request with an error
         *         message indicating that the username is incorrect.
         */
        public static String wrongUserName() {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Server.AUTHENTICATE.getValue());
            request.put("err", RequestType.Server.AUTHENTICATE_WRONG_USERNAME.getValue());
            return request.toString();
        }

        /**
         * Returns a JSON string indicating a successful authentication request.
         *
         * @return A JSON string indicating a successful authentication request.
         */
        public static String Successfully() {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Server.AUTHENTICATE.getValue());
            request.put("err", RequestType.Server.AUTHENTICATE_SUCCESS.getValue());
            return request.toString();
        }

        /**
         * Generates a JSON string representing a request to limit retries for
         * authentication.
         *
         * @return A JSON string representing the request.
         */
        public static String retryLimit() {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Server.AUTHENTICATE.getValue());
            request.put("err", RequestType.Server.AUTHENTICATE_LIMIT_RETRY.getValue());
            return request.toString();
        }

    }

    class Message {
        
        /**
         * Returns a JSON string containing all messages in a given room.
         *
         * @param roomID   The ID of the room to retrieve messages from.
         * @param Messages A JSONArray containing all messages in the room.
         * @return A JSON string containing the request to retrieve all messages in a
         *         room.
         */
        public static String allRoomMessage(int roomID, JSONArray Messages) {
            if(Messages == null){
                Messages = new JSONArray();
            }
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Server.ALL_ROOM_MESSAGES.getValue());
            request.put("room_id", roomID);
            request.put("message", Messages.toString()); // from , message
            return request.toString();
        }

        /**
         * Creates a new message request in JSON format to be sent to the server.
         *
         * @param roomID   The ID of the room where the message is being sent.
         * @param Messages The message being sent.
         * @return A JSON string representing the new message request.
         */
        public static String newMessage(int roomID, int From, String Messages) {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Server.NEW_MESSAGE.getValue());
            request.put("from", From);
            request.put("room_id", roomID);
            request.put("message", Messages);
            return request.toString();
        }
    
    }

    class Group {

        public static String userGroupList(JSONArray groups) {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Server.USER_GROUP_LIST.getValue());
            request.put("groups", groups.toString());
            return request.toString();
        }
    
    }

    class Other {

        /**
         * Creates a JSON string representing an exception request with the given error
         * code.
         *
         * @param ERR_CODE The error code to include in the request.
         * @return A JSON string representing the exception request.
         */
        public static String Exception(int ERR_CODE) {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Server.EXCEPTION.getValue());
            request.put("error", ERR_CODE);
            return request.toString();
        }

        public static String Success() {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Server.SUCCESS.getValue());
            return request.toString();
        }
    }

}