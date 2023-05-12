package module;

import java.io.IOException;

public abstract class MessageHandler implements Runnable{

    public abstract String receiveMessage() throws IOException;
    public abstract void sendMessage(String message);

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
            System.out.println("New Message:" + message);
            if (!message.equals("<<message received>>")) {
                sendMessage("<<message received>>");
            }
        }
    }
}
