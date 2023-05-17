import module.*;

import java.io.IOException;
public class LiveShareDemo {
    public static void main(String[] args) {
        try{
            LiveShare server = new LiveShare(8980);
            new Thread(server).start();
            System.out.println("Run Successfully!");
        } catch (IOException e) {
            System.out.println("Server Error: " + e.getMessage());
        }
    }
}
