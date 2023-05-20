package demo;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import client.ClientPacketHandler;
import server.Config;

public class ClientDemo {
    public static void main(String[] args) {
        Socket connection = null;
        try {
            connection = new Socket(Config.SERVER_IPV4.getStrVal(), Config.SERVER_PORT.getIntVal());
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
