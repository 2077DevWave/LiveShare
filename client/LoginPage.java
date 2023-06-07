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
        setSize(300, 500);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        setIconImage(new ImageIcon("D:\\Projects\\JAVA learn\\LiveShare\\client\\Image\\3256783.png").getImage());
        // setBackground(new Color(ERROR, HEIGHT, ALLBITS, ABORT));

        // create items
        JLabel topImage = new JLabel(new ImageIcon("D:\\Projects\\JAVA learn\\LiveShare\\client\\Image\\2847017.png"));
        JLabel usernameLabel = new JLabel("نام کاربری:");
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("رمز عبور:");
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton();
        status = new JTextArea("",1,20);
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int username = Integer.parseInt(usernameField.getText());
                String password = new String(passwordField.getPassword());
                Connection.sendPacket(Request.Auth.authInfo(username, password));
            }
        });

        // customize items
        status.setEditable(false);
        usernameField.setMinimumSize(new Dimension(200, 50));
        usernameField.setToolTipText("نام کاربری فقط شامل اعداد است.");
        passwordField.setMinimumSize(new Dimension(200, 50));
        usernameLabel.setFont(new Font("Andalus",1, 17));
        passwordLabel.setFont(new Font("Andalus",1, 17));
        loginButton.setMaximumSize(new Dimension(20, 20));
        loginButton.setIcon(new ImageIcon("D:\\Projects\\JAVA learn\\LiveShare\\client\\Image\\3518736.png"));
        topImage.setHorizontalAlignment(SwingConstants.CENTER);
        

        GridBagConstraints gbs = new GridBagConstraints();
        gbs.gridx = 0;
        gbs.insets = new Insets(10,0,10,0);

        // set position of items
        gbs.gridy = 0;
        add(topImage,gbs);
        gbs.gridy = 1;
        add(usernameLabel,gbs);
        gbs.gridy = 2;
        add(usernameField,gbs);
        gbs.gridy = 3;
        add(passwordLabel,gbs);
        gbs.gridy = 4;
        add(passwordField,gbs);
        gbs.gridy = 5;
        add(loginButton,gbs);
        gbs.gridy = 6;
        add(status,gbs);

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
