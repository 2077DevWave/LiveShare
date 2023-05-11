package module;

import java.net.Socket;

public class ClientHandler extends SocketMessageHandler{
    public String name = "Client";

    public ClientHandler(Socket client) {
        super(client);
    }

    public void asyncReceiveMessage(){
        MessageReceiver receiver = new MessageReceiver();
        new Thread(receiver).start();
    }
}
