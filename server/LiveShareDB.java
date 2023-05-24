package server;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import lib.DataBase;
import lib.Logger;

public class LiveShareDB extends DataBase {

    public static void newUser(String Name, String pass, int Rule) {
        try {
            insertQuery("INSERT INTO `users`(`pass`, `name`, `rule`) VALUES ('" + pass + "','" + Name + "','" + Rule
                    + "')");
            Logger.newLog("User " + Name + " Successfully Added with rule " + Rule);
        } catch (Exception e) {
            Logger.newWarning(
                    "failed to add new user with name " + Name + " and rule " + Rule + " -> " + e.getMessage());
        }
    }

    public static void newRoom(String Name, int UserLimit) {
        try {
            insertQuery("INSERT INTO `room`(`name`, `user_limit`) VALUES ('" + Name + "',"
                    + UserLimit + ")");
            Logger.newLog("Room Successfully Created with name " + Name);
        } catch (Exception e) {
            Logger.newWarning("failed to create new Room with name " + Name + " -> " + e.getMessage());
        }
    }

    public static void addUserIntoRoom(int userID, int groupID) {
        try {
            insertQuery("INSERT INTO `group_member`(group_id,user_id) select " + groupID + "," + userID
                    + " WHERE NOT EXISTS(select 1 from group_member where group_id=" + groupID + " and user_id="
                    + userID + " );");
            Logger.newLog("User " + userID + " Successfully Added into Group " + groupID);
        } catch (Exception e) {
            Logger.newWarning("failed to add User " + userID + " into group " + groupID + " -> " + e.getMessage());
        }
    }

    public static void addMessageIntoRoom(int groupID, int userID, String Message) {
        try {
            insertQuery("INSERT INTO `group_message`(`user_id`, `group_id`, `message`) VALUES (" + userID + ","
                    + groupID + ",'" + Message + "')");
            Logger.newLog("new Message Successfully send into group " + groupID + " from " + userID);
        } catch (Exception e) {
            Logger.newWarning(
                    "failed to send message into group " + groupID + " from user " + userID + " -> " + e.getMessage());
        }
    }

    public static JSONArray getRoomMessage(int groupID) throws SQLException {
        try {
            ResultSet res = selectQuery(
                    "SELECT * FROM `group_message` WHERE group_id = " + groupID + " ORDER BY date ASC");
            Logger.newLog("all message from group " + groupID + " received successfully");
            JSONArray result = new JSONArray();
            while (res.next()) {
                JSONObject message = new JSONObject();
                message.put("from", res.getInt("user_id"));
                message.put("message", res.getString("message"));
                result.put(message);
            }
            return result;
        } catch (Exception e) {
            Logger.newWarning("failed to get message from group " + groupID + " -> " + e.getMessage());
            throw new SQLException(e);
        }
    }

    public static boolean isRoomExists(int groupID) {
        try {
            ResultSet res = selectQuery("SELECT COUNT(*) != 0 AS room_exists FROM room WHERE id = " + groupID);
            return res.getBoolean("room_exists");
        } catch (Exception e) {
            Logger.newWarning("failed to get group " + groupID + " from DB -> " + e.getMessage());
            return false;
        }
    }

    public static boolean isUserExists(int userID) {
        try {
            ResultSet res = selectQuery("SELECT COUNT(*) != 0 AS user_exists FROM users WHERE id = " + userID);
            return res.getBoolean("user_exists");
        } catch (Exception e) {
            Logger.newWarning("failed to get user " + userID + " from DB -> " + e.getMessage());
            return false;
        }
    }

    public static int roomMembersCount(int groupID) {
        try {
            ResultSet res = selectQuery(
                    "SELECT COUNT(*) AS user_count FROM `group_member` WHERE group_id = " + groupID);
            return res.getInt("user_count");
        } catch (Exception e) {
            Logger.newWarning("failed to get group " + groupID + " member count from DB -> " + e.getMessage());
            return -1;
        }
    }

    public static boolean isUserInGroup(int userID, int groupID) {
        try {
            ResultSet res = selectQuery("SELECT COUNT(*) != 0 AS room_exists FROM `group_member` WHERE group_id = "
                    + groupID + " AND user_id = " + userID);
            return res.getBoolean("room_exists");
        } catch (Exception e) {
            Logger.newWarning(
                    "failed to get user " + userID + " from group " + groupID + " in DB -> " + e.getMessage());
            return false;
        }
    }

    public static String getUserPassword(int userID) {
        try {
            ResultSet res = selectQuery("SELECT pass FROM `users` WHERE id = " + userID);
            return res.getString("pass");
        } catch (Exception e) {
            Logger.newWarning("failed to get user " + userID + " password from DB -> " + e.getMessage());
            return null;
        }
    }

    public static int getUserRule(int userID) {
        try {
            ResultSet res = selectQuery("SELECT rule FROM `users` WHERE id = " + userID);
            return res.getInt("rule");
        } catch (Exception e) {
            Logger.newWarning("failed to get user " + userID + " rule from DB -> " + e.getMessage());
            return -1;
        }
    }

    public static int getRoomId(String Name){
        try {
            ResultSet res = selectQuery("SELECT id FROM `room` WHERE name = '" + Name + "'");
            return res.getInt("id");
        } catch (Exception e) {
            Logger.newWarning("failed to get room id with name " + Name + " DB -> " + e.getMessage());
            return -1;
        }
    }

    public static String getRoomName(int RoomID) {
        try {
            ResultSet res = selectQuery("SELECT name FROM `room` WHERE id = '" + RoomID + "'");
            return res.getString("name");
        } catch (Exception e) {
            Logger.newWarning("failed to get room name with id " + RoomID + " DB -> " + e.getMessage());
            return null;
        }
    }
}
