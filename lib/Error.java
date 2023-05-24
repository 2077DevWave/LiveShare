package lib;

public class Error {
    public static class RoomNotExistsException extends Exception{}

    public static class RoomAlreadyExistsException extends Exception{}

    public static class UserNotExistsException extends Exception{}

    public static class OperationFailedException extends Exception{}

    public static class UserNotAccessIntoRoomException extends Exception{}

    public static class PermissionDeniedException extends Exception{}
}