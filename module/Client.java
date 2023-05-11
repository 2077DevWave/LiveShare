package module;

import java.net.Socket;

public class Client extends SocketMessageHandler{

    public Client(Socket client) {
        super(client);
        asyncReceiveMessage();
    }
}
