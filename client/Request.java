package client;

import java.time.LocalDateTime;
import org.json.JSONObject;

import server.PacketHandler.PacketType;

public class Request {

    /**
     * create a JSON object contain information about the message
     * 
     * @param Message the message you want to send
     * @return a JsonObject contain type, data, id
     */
    public static JSONObject createMessageRequest(int toUser, String Message) {
        JSONObject json = new JSONObject();
        json.put("request_type", PacketType.MESSAGE.getValue());
        json.put("request_id", createUniqRequestId());
        json.put("to_user_id", toUser);
        json.put("message", Message);
        return json;
    }

    /**
     * create a JSON object contain information to get permissions from user
     * @param fromUser - user send this request
     * @return a JsonObject contain request_type, permission_type, request_id, from_user_id
     */
    public static JSONObject createUserPermissionRequest(int toUser) {
        JSONObject json = new JSONObject();
        json.put("request_type", PacketType.PERMISSION.getValue());
        json.put("permission_type", PacketType.USER.getValue());
        json.put("request_id", createUniqRequestId());
        json.put("to_user_id", toUser);
        return json;
    }

    /**
     * create uniq id based in time and hash
     * 
     * @return int uniq id
     */
    public static int createUniqRequestId() {
        LocalDateTime time = LocalDateTime.now();
        int id = time.getYear() + time.getMonthValue() + time.getDayOfMonth() + time.getHour() + time.getMinute()
                + time.getSecond() + time.getNano();
        return id;
    }
}