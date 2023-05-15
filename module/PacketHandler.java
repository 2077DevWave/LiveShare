package module;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class PacketHandler implements Runnable{
    // method to receive new messages
    public abstract byte[] receivedPacket() throws IOException;
    // method to send new packets
    public abstract void sendPacket(byte[] message);

    /**
     * running in other threads to receive new messages asynchronously
     */
    public void asyncReceiveMessage(){
        new Thread(this).start();
    }
    
    /**
     * a Runnable method to receive new messages asynchronously
     */
    @Override
    public void run() {
        System.out.println("Thread Successfully Created!");
        System.out.println("Listen into incoming Message ...");
        byte[] message;
        while (true) {
            try {
                message = receivedPacket();
            } catch (IOException e) {
                System.out.println("Connection Failed: " + e.getMessage());
                break;
            }
            System.out.println(new String(message,StandardCharsets.UTF_8));
        }
    }

    public void handlePacket(byte[] data){}
}
