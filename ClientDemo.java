import java.net.Socket;
import java.util.Scanner;

import module.Client;

class ClientDemo{
    public static Socket client;
    public static void main(String[] args) {
        try{
            client = new Socket("127.0.0.1",8980);
            System.out.println("connected!");
        }catch(Exception e){
            System.out.println("faild to connect");
        }

        Client controller = new Client(client);

        Scanner keyboard = new Scanner(System.in);

        String message;
        while((message = keyboard.nextLine()) != null){
            controller.sendMessage(message);
        }
    }
}