package client;

import javax.swing.*;

import org.json.JSONObject;

import lib.RequestType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame{
    private RequestHandler Connection;
    private JTextArea status;

    public LoginPage(RequestHandler Connection){
        this.Connection = Connection;

        setTitle("Login Page");
        setAlwaysOnTop(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int username = Integer.parseInt(usernameField.getText());
                String password = new String(passwordField.getPassword());
                Connection.sendPacket(Request.Auth.authInfo(username, password));
            }
        });

        status = new JTextArea("",1,20);
        status.setEditable(false);

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(status);

        setVisible(true);
    }

    public Boolean Auth(){

        do{
            JSONObject json = Connection.lastResponsePacket();
            int errCode = json.getInt("err");
            if (errCode == RequestType.Server.AUTHENTICATE_NO_ERROR.getValue()) {
            }
            else if (errCode == RequestType.Server.AUTHENTICATE_LIMIT_RETRY.getValue()) {
                setVisible(false);
                JOptionPane.showMessageDialog(null, "you have exceeded the limit!","Limit Error!", JOptionPane.ERROR_MESSAGE);
                return false;
            } else if (errCode == RequestType.Server.AUTHENTICATE_SUCCESS.getValue()) {
                setVisible(false);
                JOptionPane.showMessageDialog(null, "login succeeded!","Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else if (errCode == RequestType.Server.AUTHENTICATE_WRONG_PASSWORD.getValue()) {
                status.setText("wrong password!");
            } else if (errCode == RequestType.Server.AUTHENTICATE_WRONG_USERNAME.getValue()) {
                status.setText("wrong username!");
            } else {
                setVisible(false);
                JOptionPane.showMessageDialog(null, "unknown auth error! (try again to connect into server)","Unknown Error!", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }while(true);
    }
}
