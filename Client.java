package module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends SocketMessageHandler{

    public Client(Socket client) {
        super(client);
    }
}
