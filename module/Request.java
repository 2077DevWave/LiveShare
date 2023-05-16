package module;

import java.time.LocalDateTime;
import org.json.JSONObject;
import module.PacketHandler.PacketType;

public class Request {

    /**
     * create a JSON object contain information about the message
     * 
     * @param Message the message you want to send
     * @return a JsonObject contain type, data, id
     */
    public static JSONObject createMessageRequest(int fromUser, int toUser, String Message) {
        JSONObject json = new JSONObject();
        json.put("request_type", PacketType.STRING.getValue());
        json.put("request_id", createUniqRequestId());
        json.put("from_user_id", 0);
        json.put("to_user_id", 0);
        json.put("message", Message);
        return json;
    }

    public static JSONObject createUserPermissionRequest(int fromUser, int toUser) {
        JSONObject json = new JSONObject();
        json.put("request_type", PacketType.PERMISSION.getValue());
        json.put("permission_type", PacketType.USER.getValue());
        json.put("request_id", createUniqRequestId());
        json.put("from_user_id", fromUser);
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
                + time.getSecond() + time.getNano() + time.hashCode();
        return id;
    }

}