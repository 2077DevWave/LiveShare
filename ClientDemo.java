import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import client.ClientPacketHandler;

public class ClientDemo {
    public static void main(String[] args) {
        Socket connection = null;
        try {
            connection = new Socket("127.0.0.1", 8980);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ClientPacketHandler handler = new ClientPacketHandler(connection);
        handler.asyncPacketReceiver();

        Scanner input = new Scanner(System.in);
        
        String message;
        while((message = input.nextLine()) != "exit") {
            String[] data = message.split(">");
            try{
                int destUserId = Integer.parseInt(data[0]);
                handler.sendPacket(destUserId, data[1]);
            }catch(NumberFormatException e){
                System.out.println("Id must be an Integer");
            }
            System.out.println("packet send!");
        }

        input.close();
        
    }
}
