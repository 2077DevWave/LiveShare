import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

import client.Request;
import client.RequestHandler;
import lib.SocketPacketHandler;
import server.Config;

public class ClientDemo {
    public static void main(String[] args) {
        Socket connection = null;
        try {
            connection = new Socket(Config.SERVER_IPV4.getStrVal(), Config.SERVER_PORT.getIntVal());
        } catch (IOException e) {
            e.printStackTrace();
        }

        SocketPacketHandler handler = new SocketPacketHandler(connection);

        RequestHandler req = new RequestHandler(handler.handler);

        String message;
        while ((message = JOptionPane.showInputDialog("command: ")) != "exit") {
            String[] option = message.split(":");
            if (option[0].equals("newgp")) {
                handler.sendPacket(Request.Room.createGroup(option[1]));
            } else if (option[0].equals("newroom")) {
                handler.sendPacket(Request.Room.createRoom(Integer.parseInt(option[1]), option[2]));
            } else if (option[0].equals("sendmsg")) {
                handler.sendPacket(Request.Message.sendMessage(Integer.parseInt(option[1]), option[2]));
            } else if (option[0].equals("joingp")) {
                handler.sendPacket(Request.Room.joinGroup(Integer.parseInt(option[1])));
            } else if (option[0].equals("getmsg")) {
                handler.sendPacket(Request.Message.getMessage(Integer.parseInt(option[1])));
            } else {
                handler.sendPacket(message);
            }
        }
    }
}
