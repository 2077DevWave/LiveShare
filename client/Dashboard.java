package client;

import javax.swing.*;

import org.json.JSONObject;

import lib.RequestType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Dashboard extends JFrame {
    private JFrame Frame;

    public Dashboard(RequestHandler Connection) {
        Frame = this;
        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Create buttons
        JButton newGroupButton = new JButton("New Group");
        JButton newRoomButton = new JButton("New Room");
        JButton JoinGroupButton = new JButton("Join Group");
        JButton groupMessageButton = new JButton("Group Message");
        JButton logoutButton = new JButton("Logout");

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1));
        buttonPanel.add(newGroupButton);
        buttonPanel.add(newRoomButton);
        buttonPanel.add(JoinGroupButton);
        buttonPanel.add(groupMessageButton);
        buttonPanel.add(logoutButton);

        // Add the button panel to the main frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buttonPanel, BorderLayout.CENTER);

        // Add action listeners to the buttons
        newGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String gName = JOptionPane.showInputDialog(Frame,"Group Name");
                if(gName != null) {
                    Connection.sendPacket(Request.Room.createGroup(gName));
                    JSONObject response = Connection.LastPacket();
                    if(response.getInt("type") == RequestType.Server.SUCCESS.getValue()){
                        JOptionPane.showMessageDialog(Frame, "Group SuccessFully Created!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }else if(response.getInt("type") == RequestType.Server.EXCEPTION.getValue()){
                        JOptionPane.showMessageDialog(Frame, RequestHandler.getErrorTypeMessage(response.getInt("error")), "Error", JOptionPane.ERROR_MESSAGE);
                    };
                }else{
                    JOptionPane.showMessageDialog(Frame, "Group Name Cant be Empty", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        newRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle new room button click
                String Name = JOptionPane.showInputDialog(Frame,"Room Name");
                String SecondUserId = JOptionPane.showInputDialog(Frame,"With User Id");
                if(Name != null && SecondUserId != null){
                    Connection.sendPacket(Request.Room.createRoom(Integer.parseInt(SecondUserId),Name));
                    JSONObject response = Connection.LastPacket();
                    if(response.getInt("type") == RequestType.Server.SUCCESS.getValue()){
                        JOptionPane.showMessageDialog(Frame, "Room SuccessFully Created!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }else if(response.getInt("type") == RequestType.Server.EXCEPTION.getValue()){
                        JOptionPane.showMessageDialog(Frame, RequestHandler.getErrorTypeMessage(response.getInt("error")), "Error", JOptionPane.ERROR_MESSAGE);
                    };
                }else{
                    JOptionPane.showMessageDialog(Frame, "Room Name or second user id Cant be Empty", "Warning", JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        JoinGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle new room button click
                String groupId = JOptionPane.showInputDialog(Frame,"Group Id");
                if(groupId != null){
                    Connection.sendPacket(Request.Room.joinGroup(Integer.parseInt(groupId)));
                    JSONObject response = Connection.LastPacket();
                    if(response.getInt("type") == RequestType.Server.SUCCESS.getValue()){
                        JOptionPane.showMessageDialog(Frame, "SuccessFully Join the Group", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }else if(response.getInt("type") == RequestType.Server.EXCEPTION.getValue()){
                        JOptionPane.showMessageDialog(Frame, RequestHandler.getErrorTypeMessage(response.getInt("error")), "Error", JOptionPane.ERROR_MESSAGE);
                    };
                }else{
                    JOptionPane.showMessageDialog(Frame, "Room Name or second user id Cant be Empty", "Warning", JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        groupMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        GroupList groupListGUI = new GroupList();
                        groupListGUI.setVisible(true);
                    }
                });
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    Connection.handler.close();
                } catch (IOException e1) {
                    if(Connection.handler.isClosed()){
                        Frame.dispose();
                        JOptionPane.showMessageDialog(Frame,"you are logged out successfully!", "Log out" , JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(Frame,"there is an problem to logout", "Log out" , JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                setVisible(false);
            }
        });
    }
}
