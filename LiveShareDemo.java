package demo;
import java.io.IOException;

import server.*;
public class LiveShareDemo {
    public static void main(String[] args) {
        try{
            LiveShare server = new LiveShare(Config.SERVER_PORT.getIntVal());
            new Thread(server).start();
            System.out.println("Run Successfully!");
        } catch (IOException e) {
            System.out.println("Server Error: " + e.getMessage());
        }
    }
}
