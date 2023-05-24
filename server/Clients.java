package server;

import java.util.ArrayList;
import lib.Logger;

public class Clients {

    private ArrayList<User> allClients = new ArrayList<User>();

    public void newClient(User user) {
        allClients.add(user);
        Logger.newLog("New client added into Clients List: " + user.getId());
        new RequestHandler(user);
    }

    public User findUser(int userId) {
        for (User user : allClients) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }

    public void removeUser(int userId) {
        allClients.remove(findUser(userId));
    }

    public boolean isUserOnline(int userId) {
        if (findUser(userId) == null) {
            return false;
        }else{
            return true;
        }
    }

}
