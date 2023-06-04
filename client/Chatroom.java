package client;

import javax.swing.*;

import org.json.JSONArray;
import org.json.JSONObject;

import lib.RequestType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Chatroom extends JFrame {

    private int roomID;
    private JTextArea chatArea;
    private JTextField messageField;

    public Chatroom(int roomID) {
        this.roomID = roomID;
        setTitle("Chatroom - Room #" + roomID);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Create the chat area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JSONArray messages = getRoomMessage();
        if (messages != null) {
            ShowMessage(messages);
        } else {
            chatArea.append("Failed to get room messages!");
        }

        // Create the message input field
        messageField = new JTextField();
        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // Create the send button
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // Create the bottom panel to hold the message field and send button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        // Add the components to the main frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }

    private void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            messageField.setText("");
            RequestHandler.spHandler.sendPacket(Request.Message.sendMessage(roomID, message));
            JSONObject response = RequestHandler.spHandler.lastResponsePacket();
            if (response.getInt("type") == RequestType.Server.SUCCESS.getValue()) {
            } else if (response.getInt("type") == RequestType.Server.EXCEPTION.getValue()) {
                RequestHandler.getErrorTypeMessage(response.getInt("error"));
            } else {
                JOptionPane.showMessageDialog(this, "Unknown Response!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private JSONArray getRoomMessage() {
        RequestHandler.spHandler.sendPacket(Request.Message.getMessage(roomID));
        JSONObject response = RequestHandler.spHandler.lastResponsePacket();
        if (response.getInt("type") == RequestType.Server.ALL_ROOM_MESSAGES.getValue()) {
            return new JSONArray(response.getString("message"));
        } else if (response.getInt("type") == RequestType.Server.EXCEPTION.getValue()) {
            RequestHandler.getErrorTypeMessage(response.getInt("error"));
        } else {
            JOptionPane.showMessageDialog(this, "Unknown Response!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        return null;
    }

    private void ShowMessage(JSONArray roomMessage) {
        chatArea.setText("");
        roomMessage.forEach(e -> {
            JSONObject message = new JSONObject(e.toString());
            int senderID = message.getInt("from");
            String content = message.getString("message");
            chatArea.append("User_" + senderID + ": " + content + "\n");
        });
    }

    public void ShowMessage(int From, String Message) {
        chatArea.append("User_" + From + ": " + Message + "\n");
    }

    public int getRoomID() {
        return roomID;
    }

}