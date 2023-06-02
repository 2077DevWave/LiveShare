package server;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import lib.DataBase;
import lib.Logger;

public class LiveShareDB extends DataBase {

    /**
    * Adds a new user to the database. This is used to add new users to the database when they are created
    * 
    * @param Name - The name of the user
    * @param pass - Pass The word of the user ( can be blank )
    * @param Rule - The rule of the user ( 0 = admin 1 = admin
    */
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

    /**
    * Creates a new room with the given name and user limit. It does not check if the room already exists
    * 
    * @param Name - the name of the room
    * @param UserLimit - the number of users in the room ( 0 = unlimited
    */
    public static void newRoom(String Name, int UserLimit) {
        try {
            insertQuery("INSERT INTO `room`(`name`, `user_limit`) VALUES ('" + Name + "',"
                    + UserLimit + ")");
            Logger.newLog("Room Successfully Created with name " + Name);
        } catch (Exception e) {
            Logger.newWarning("failed to create new Room with name " + Name + " -> " + e.getMessage());
        }
    }

    /**
    * Adds a user to a group. Does not check if the user is already in the group. This is used for joining a group
    * 
    * @param userID - the id of the user to add
    * @param groupID - the id of the group to add the user
    */
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

    /**
    * Adds a message into a group. This is used to send messages from a user to a group.
    * 
    * @param groupID - ID of the group to add the message to
    * @param userID - ID of the user to add the message to
    * @param Message - Message to add to the user's group
    */
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

    /**
    * Get all messages from a group. This is used to send messages to the group that are in the room
    * 
    * @param groupID - the id of the group
    * 
    * @return a JSONArray of message from the group in the format { " from " : user_id " message " : message
    */
    public static JSONArray getRoomMessage(int groupID) throws SQLException {
        try {
            ResultSet res = selectQuery(
                    "SELECT * FROM `group_message` WHERE group_id = " + groupID + " ORDER BY date ASC");
            Logger.newLog("all message from group " + groupID + " received successfully");
            JSONArray result = new JSONArray();
            // Returns the next message from the response.
            do {
                JSONObject message = new JSONObject();
                message.put("from", res.getInt("user_id"));
                message.put("message", res.getString("message"));
                result.put(message);
            } while (res.next());
            return result;
        } catch (Exception e) {
            Logger.newWarning("failed to get message from group " + groupID + " -> " + e.getMessage());
            throw new SQLException(e);
        }
    }

    /**
    * Checks if a room exists in the database. This is used to determine if a group is in the database
    * 
    * @param groupID - the id of the group to check
    * 
    * @return true if the group exists false if it doesn't or an error occurs during the check ( in which case false is returned
    */
    public static boolean isRoomExists(int groupID) {
        try {
            ResultSet res = selectQuery("SELECT COUNT(*) != 0 AS room_exists FROM room WHERE id = " + groupID);
            return res.getBoolean("room_exists");
        } catch (Exception e) {
            Logger.newWarning("failed to get group " + groupID + " from DB -> " + e.getMessage());
            return false;
        }
    }

    /**
    * Checks if a user exists in the database. This is used to check if a user is logged in
    * 
    * @param userID - the id of the user to check
    * 
    * @return true if the user exists false if it doesn't or an error occurs during the check ( in which case false is returned
    */
    public static boolean isUserExists(int userID) {
        try {
            ResultSet res = selectQuery("SELECT COUNT(*) != 0 AS user_exists FROM users WHERE id = " + userID);
            return res.getBoolean("user_exists");
        } catch (Exception e) {
            Logger.newWarning("failed to get user " + userID + " from DB -> " + e.getMessage());
            return false;
        }
    }

    /**
    * Returns the number of members in a group. This is used to determine how many rooms are occupied by a group
    * 
    * @param groupID - ID of group to check
    * 
    * @return int number of members in the group or - 1 if something goes wrong during the check ( in which case the error will be logged
    */
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

    /**
    * Checks if a user is in a group. This is used to check if a user is part of a group
    * 
    * @param userID - the id of the user
    * @param groupID - the id of the group
    * 
    * @return true if the user is in the group false if not or if there is an error in the database
    */
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

    /**
    * Get the password associated with a user from the database. This is used to authenticate the user when logging in
    * 
    * @param userID - the id of the user
    * 
    * @return the password or null if not
    */
    public static String getUserPassword(int userID) {
        try {
            ResultSet res = selectQuery("SELECT pass FROM `users` WHERE id = " + userID);
            return res.getString("pass");
        } catch (Exception e) {
            Logger.newWarning("failed to get user " + userID + " password from DB -> " + e.getMessage());
            return null;
        }
    }

    /**
    * Get the rule associated with a user from the database. This is used to determine if a user is allowed to edit their account
    * 
    * @param userID - the id of the user
    * 
    * @return the rule associated with the
    */
    public static int getUserRule(int userID) {
        try {
            ResultSet res = selectQuery("SELECT rule FROM `users` WHERE id = " + userID);
            return res.getInt("rule");
        } catch (Exception e) {
            Logger.newWarning("failed to get user " + userID + " rule from DB -> " + e.getMessage());
            return -1;
        }
    }

    /**
    * Get the id of a room in the database. This is used to determine if a room is open
    * 
    * @param Name - the name of the room
    * 
    * @return the id of the room or - 1 if not found in the database or error in the name is
    */
    public static int getRoomId(String Name){
        try {
            ResultSet res = selectQuery("SELECT id FROM `room` WHERE name = '" + Name + "'");
            return res.getInt("id");
        } catch (Exception e) {
            Logger.newWarning("failed to get room id with name " + Name + " DB -> " + e.getMessage());
            return -1;
        }
    }

    /**
    * Gets the name of a room from the database. This is used to determine the name of the room
    * 
    * @param RoomID - the id of the room
    * 
    * @return the name of the room or null if there is an error in the database or the room doesn't
    */
    public static String getRoomName(int RoomID) {
        try {
            ResultSet res = selectQuery("SELECT name FROM `room` WHERE id = '" + RoomID + "'");
            return res.getString("name");
        } catch (Exception e) {
            Logger.newWarning("failed to get room name with id " + RoomID + " DB -> " + e.getMessage());
            return null;
        }
    }

    public static JSONArray allUserGroup(int userID) throws SQLException {
        try {
            ResultSet res = selectQuery(
                    "SELECT group_id FROM `group_member` WHERE user_id = " + userID + " ORDER BY group_id ASC");
            Logger.newLog("all group list for user " + userID + " find successfully");
            JSONArray result = new JSONArray();
            // Returns the next message from the response.
            do{
                JSONObject Groups = new JSONObject();
                Groups.put("id", res.getInt("group_id"));
                Groups.put("name", getRoomName(res.getInt("group_id")));
                result.put(Groups);
            }while (res.next());
            return result;
        } catch (Exception e) {
            Logger.newWarning("failed to get user Group list for user " + userID + " -> " + e.getMessage());
            throw new SQLException(e);
        }
    }
}
