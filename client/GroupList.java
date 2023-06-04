package client;

import javax.swing.*;

import org.json.JSONArray;
import org.json.JSONObject;

import lib.RequestType;

import java.awt.*;
import java.util.ArrayList;

public class GroupList extends JFrame {

    private JList<String> groupList;
    public static ArrayList<Chatroom> openedRoom = new ArrayList<Chatroom>();

    public GroupList() {
        setTitle("Group List");
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Create a list of groups
        DefaultListModel<String> listModel = new DefaultListModel<>();

        JSONArray groups = allUserGroup();

        if(groups != null){
            groups.forEach(e -> {
                JSONObject group = new JSONObject(e.toString());
                listModel.addElement(group.getInt("id") + "-" + group.getString("name"));
            });
        }else{
            listModel.addElement("Empty!");
        }


        // Create the group list
        groupList = new JList<>(listModel);
        groupList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        groupList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedGroup = groupList.getSelectedValue();
                if (selectedGroup != null) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            String[] group = selectedGroup.split("-");
                            int roomID = Integer.parseInt(group[0]); 
                            Chatroom chatRoom = new Chatroom(roomID);
                            GroupList.openedRoom.add(chatRoom);
                            chatRoom.setVisible(true);
                        }
                    });
                }
            }
        });

        // Add the group list to a scroll pane
        JScrollPane scrollPane = new JScrollPane(groupList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add the scroll pane to the main frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private JSONArray allUserGroup(){
        RequestHandler.spHandler.sendPacket(Request.Room.getAllGroupList());
        JSONObject response = RequestHandler.spHandler.lastResponsePacket();
        if(response != null){
            if(response.getInt("type") == RequestType.Server.USER_GROUP_LIST.getValue()){
                return new JSONArray(response.getString("groups"));
            }else if(response.getInt("type") == RequestType.Server.EXCEPTION.getValue()){
                JOptionPane.showMessageDialog(this, RequestHandler.getErrorTypeMessage(response.getInt("error")), "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this, "Unknown Response!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }
}
