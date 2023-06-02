package client;

import org.json.JSONObject;
import lib.RequestType;

public class Request {

    public class Auth {

        /**
        * Generate an authentication request. This is used to authenticate a user against OpenHAB's authentication system
        * 
        * @param userID - The user ID of the user
        * @param pass - The password associated with the user
        * 
        * @return A JSON string that can be sent to the OpenHAB server for authentication or an error message if the request
        */
        public static String authInfo(int userID, String pass) {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Server.AUTHENTICATE.getValue());
            request.put("user_id", userID);
            request.put("password", pass);
            return request.toString();
        }
    
    }

    public class Room {

        /**
        * Create a room with the given name. This is a blocking call and will return immediately. You must have connected to the server before calling this method.
        * 
        * @param userID - The ID of the user to create the room with
        * @param Name - The name of the room
        * 
        * @return A JSON string representing the result of the create room request. See README for more details. Note that the name is limited to 255 characters
        */
        public static String createRoom(int userID, String Name) {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Client.CREATE_ROOM.getValue());
            request.put("with", userID);
            request.put("name", Name);
            return request.toString();
        }

        /**
        * Create a group on OpenNebula. Note that you must have the right to use this method
        * 
        * @param Name - The name of the group
        * 
        * @return JSON String of the request to send to OpenNebula to create a group on OpenNeb
        */
        public static String createGroup(String Name) {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Client.CREATE_GROUP.getValue());
            request.put("name", Name);
            return request.toString();
        }

        /**
        * Join a group to the game server. This is a blocking call and will return immediately. You can use this in conjunction with #joinPlayers ( int )
        * 
        * @param groupID - The ID of the group to join
        * 
        * @return A JSON string representing the join request. See RFC 6265 for details on the format of the JSON
        */
        public static String joinGroup(int groupID) {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Client.JOIN_GROUP.getValue());
            request.put("id", groupID);
            return request.toString();
        }
    
        public static String getAllGroupList(){
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Client.LIST_OF_GROUPS.getValue());
            return request.toString();
        }
    }

    public class Message {

        /**
        * Send a message to a room. This is a synchronous call and will return before the message has been sent.
        * 
        * @param roomID - The ID of the room to send the message to
        * @param Message - The message to send.
        * 
        * @return A JSON encoded String of the request. Never null but may contain HTML or JSONP encoded data as it is
        */
        public static String sendMessage(int roomID, String Message) {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Client.SEND_MESSAGE.getValue());
            request.put("id", roomID);
            request.put("message", Message);
            return request.toString();
        }

        /**
        * Get the messages in a room. This is a GET_ROOM_MESSAGES request. You need to be logged in to get the messages.
        * 
        * @param roomID - The ID of the room to get the messages for.
        * 
        * @return A JSON string representing the request. Example : { " type " : RequestType. Client. GET_ROOM_MESSAGES
        */
        public static String getMessage(int roomID) {
            JSONObject request = new JSONObject();
            request.put("type", RequestType.Client.GET_ROOM_MESSAGES.getValue());
            request.put("id", roomID);
            return request.toString();
        }
    
    }
}
