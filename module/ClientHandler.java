package module;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends SocketMessageHandler{
    public String name = "Client";

    public ClientHandler(Socket client) {
        super(client);
        System.out.println("New Client Connected!");
        System.out.println("Starting new thread...");
        new Thread(this).start();

        Scanner keyboard = new Scanner(System.in);

        String message;
        while((message = keyboard.nextLine()) != "exit"){
            super.sendMessage(message);
            System.out.println("message send");
        }
    }
}
