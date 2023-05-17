package server;

import java.net.Socket;
import java.util.ArrayList;

public class Clients {

    private ArrayList<ClientHandler> allClients = new ArrayList<ClientHandler>();

    public void newClient(Socket clientHandler) {
        ClientHandler client = new ClientHandler(clientHandler);
        allClients.add(client);
        Logger.newLog("New client: " + client.getId());
    }

    public ClientHandler findUser(int userId) {
        for (ClientHandler clientHandler : allClients) {
            if (clientHandler.getId() == userId) {
                return clientHandler;
            }
        }
        return null;
    }

    public void removeUser(int userId) {
        allClients.remove(findUser(userId));
    }

    public void sendMessageToAll(String Message){
        for (ClientHandler clientHandler : allClients) {
            clientHandler.sendPacket(Message);
        }
    }

    public void sendPacketToAll(byte[] Packet){
        for (ClientHandler clientHandler : allClients) {
            clientHandler.sendPacket(Packet);
        }
    }

    public boolean userExist(int userId) {
        if (findUser(userId) == null) {
            return false;
        }else{
            return true;
        }
    }


}
