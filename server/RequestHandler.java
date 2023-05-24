package server;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import lib.Error;
import lib.Logger;
import lib.RequestType;
import lib.SocketPacketHandler;
import lib.Error.OperationFailedException;
import lib.Error.PermissionDeniedException;
import lib.Error.RoomAlreadyExistsException;
import lib.Error.RoomNotExistsException;
import lib.Error.UserNotAccessIntoRoomException;
import lib.Error.UserNotExistsException;

public class RequestHandler extends SocketPacketHandler implements Runnable{
    private User userHandler;

    public RequestHandler(User user) {
        super(user.getHandler().handler);
        userHandler = user;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            String packet;
            while((packet = super.receivedPacket()) != null) {
                Logger.newLog("new packet from " + userHandler.getId() + " has been received " + packet);
                handelIncomingRequest(packet);
            }
        } catch (IOException e) {
            Logger.newWarning("connection closed for client " + userHandler.getId());
            LiveShare.clientsHandler.removeUser(userHandler.getId());
        }
    }

    public void handelIncomingRequest(String packet) {
        try{
            JSONObject json = new JSONObject(packet);
            int type = json.getInt("type");
            if(type == RequestType.Client.SEND_MESSAGE.getValue()){
                // id , message
                int RoomId = json.getInt("id");
                String message = json.getString("message");
                userHandler.SendMessage(RoomId, message);
            }
            else if(type == RequestType.Client.GET_ROOM_MESSAGES.getValue()){
                // id
                int roomID = json.getInt("id");
                super.sendPacket(Request.Message.allRoomMessage(roomID, userHandler.getRoomMessage(roomID)));
            }
            else if(type == RequestType.Client.CREATE_ROOM.getValue()){
                // with , name
                int userID = json.getInt("with");
                String name = json.getString("name");
                userHandler.CreateRoom(name, userID);
            }
            else if(type == RequestType.Client.CREATE_GROUP.getValue()){
                // name
                String name = json.getString("name");
                if(LiveShareDB.getUserRule(userHandler.getId()) < 3){
                    PremiumUser user = (PremiumUser) userHandler;
                    user.createGroup(name);
                }else{
                    throw new Error.PermissionDeniedException();
                }
            }
            else if(type == RequestType.Client.JOIN_GROUP.getValue()){
                // id
                int id = json.getInt("id");
                if(LiveShareDB.getUserRule(userHandler.getId()) < 3){
                    PremiumUser user = (PremiumUser) userHandler;
                    user.joinGroup(id);
                }else{
                    throw new Error.PermissionDeniedException();
                }
            }
            else{
                Logger.newWarning("unknown type " + type);
            }
        }catch(JSONException e){
            Logger.newWarning("failed to convert into json, packet " + packet);
        } catch (UserNotAccessIntoRoomException e) {
            Logger.newWarning("user " + userHandler.getId() + " don`t Access into Room");
            super.sendPacket(Request.Other.Exception(RequestType.Server.USER_NOT_ACCESS_INTO_ROOM.getValue()));
        } catch (RoomNotExistsException e) {
            Logger.newWarning("Room is not exist! user " + userHandler.getId());
            super.sendPacket(Request.Other.Exception(RequestType.Server.ROOM_NOT_EXIST.getValue()));
        } catch (RoomAlreadyExistsException e) {
            Logger.newWarning("Room Already Exist! user " + userHandler.getId());
            super.sendPacket(Request.Other.Exception(RequestType.Server.ROOM_ALREADY_EXIST.getValue()));
        } catch (UserNotExistsException e) {
            Logger.newWarning("user not Exist! user " + userHandler.getId());
            super.sendPacket(Request.Other.Exception(RequestType.Server.USER_NOT_EXIST.getValue()));
        } catch (OperationFailedException e) {
            Logger.newWarning("Operation Failed! user " + userHandler.getId());
            super.sendPacket(Request.Other.Exception(RequestType.Server.OPERATION_FAILED.getValue()));
        } catch (PermissionDeniedException e) {
            Logger.newWarning("Permission Denied! user " + userHandler.getId());
            super.sendPacket(Request.Other.Exception(RequestType.Server.PERMISSION_DENIED.getValue()));
        }
    }
}
