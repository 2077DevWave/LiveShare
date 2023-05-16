package module;

import java.net.Socket;
import java.util.ArrayList;

public class Clients {

    public ArrayList<ClientHandler> allClients = new ArrayList<ClientHandler>();

    public void newClient(Socket clientHandler) {
        ClientHandler client = new ClientHandler(clientHandler);
        allClients.add(client);
    }
}
