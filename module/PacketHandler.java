package module;

import java.io.IOException;
import org.json.JSONObject;

public abstract class PacketHandler implements Runnable {
    // method to receive new messages
    public abstract byte[] receivedPacket() throws IOException;

    // method to send new packets
    public abstract void sendPacket(byte[] message);

    enum PacketType {
        STRING(1),
        FILE(2),
        FILE_SLICE(3),
        PERMISSION(4),
        AUTHENTICATE(5);

        private int value;

        private PacketType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * running in other threads to receive new messages asynchronously
     */
    public void asyncPacketReceiver() {
        new Thread(this).start();
    }

    /**
     * a Runnable method to receive new messages asynchronously
     */
    @Override
    public void run() {
        System.out.println("Thread Successfully Created!");
        System.out.println("Listen into incoming Message ...");
        JSONObject packet;
        while (true) {
            packet = null;
            try {
                packet = toJsonObject(receivedPacket());
            } catch (IOException e) {
                System.out.println("Failed to receive packet!");
            }

            if(packet != null) {
                switch(packet.getInt("type")){
                    case 1:
                        System.out.println(packet.getString("data"));
                        break;
                    case 2:
                        // TODO: start receiving new file
                        break;
                    case 3:
                        // TODO: receive new file slice
                        break;
                }
            }
        }
    }

    public JSONObject toJsonObject(byte[] data) {
        return new JSONObject(new String(data));
    }
}