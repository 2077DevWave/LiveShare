package module;

import java.io.IOException;

public abstract class MessageHandler implements Runnable{
    // method to receive new messages
    public abstract String receiveMessage() throws IOException;
    // method to send new messages
    public abstract void sendMessage(String message);

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
        String message = "";
        while (message != null) {
            try {
                message = receiveMessage();
            } catch (IOException e) {
                System.out.println("Connection Failed: " + e.getMessage());
                break;
            }
            System.out.println(message);
        }
    }
}
