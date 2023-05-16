import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import module.SocketPacketHandler;

public class ClientDemo {
    private static Socket handler;
    public static void main(String[] args) {
        try {
            handler = new Socket("127.0.0.1", 8980);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        SocketPacketHandler msg = new SocketPacketHandler(handler);
        msg.messagePrefix = "Server: ";
        msg.asyncPacketReceiver();

        Scanner input = new Scanner(System.in);
        String message = "";
        while (!(message = input.nextLine()).equals("exit")){
            msg.sendMessage(message);
        }
        input.close();
    }
}
