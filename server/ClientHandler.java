package server;

import java.net.Socket;

public class ClientHandler extends SocketPacketHandler {
    // secret based on hash to decode and encode message
    private int id;
    // client name
    public String Name = "Client: ";

    /**
     * its Receiving message with SocketMessageHandler and save client information
     * @param client - Socket object
     */
    public ClientHandler(Socket client) {
        super(client);
        this.id = Request.createUniqRequestId();
        asyncPacketReceiver();
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.Name;
    }
}
