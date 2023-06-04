package server;

import java.util.ArrayList;

import lib.Logger;

public class Clients {

    private ArrayList<User> allClients = new ArrayList<User>();

    /**
    * Adds a new client to the list of clients and starts the request handler. This is called when a new client is added
    * 
    * @param user - The user who added
    */
    public void newClient(User user) {
        allClients.add(user);
        Logger.newLog("New client added into Clients List: " + user.getId());
        new RequestHandler(user);
    }

    /**
    * Finds a user by id. This is used to determine if a user is logged in or not.
    * 
    * @param userId - The id of the user to look for.
    * 
    * @return The user or null if not found in the list of users or if there is no user with the given id
    */
    public User findUser(int userId) {
        for (User user : allClients) {
            // Returns the user who owns this user.
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }

    /**
    * Removes a user from the list of all users. This is useful when you want to remove a user from the list but don't want to know the user's ID.
    * 
    * @param userId - The user's ID to remove from the
    */
    public void removeUser(int userId) {
        allClients.remove(findUser(userId));
    }

    /**
    * Checks if a user is online. This is used to determine if an appointment has been made or not
    * 
    * @param userId - The user's id
    * 
    * @return true if the user is online false if it is not or if the user is not in the appointment
    */
    public boolean isUserOnline(int userId) {
        if (findUser(userId) == null) {
            return false;
        }else{
            return true;
        }
    }

    public ArrayList<User> getUsers() {
        return allClients;
    }
}
