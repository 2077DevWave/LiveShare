import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

import client.Dashboard;
import client.LoginPage;
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

        RequestHandler reqHandler = new RequestHandler(handler.handler);

        if (new LoginPage(reqHandler).Auth() == false) {
            JOptionPane.showMessageDialog(null, "login failed! Please login again or try later.", "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        };

        Dashboard Panel = new Dashboard(reqHandler);
        Panel.setVisible(true);
    }
}
