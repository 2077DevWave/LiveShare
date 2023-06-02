package lib;

public class RequestType {

    public enum Server{
        NEW_MESSAGE(10),
        ALL_ROOM_MESSAGES(11),
        USER_GROUP_LIST(12),
        AUTHENTICATE(20),
        AUTHENTICATE_SUCCESS(21),
        AUTHENTICATE_WRONG_USERNAME(22),
        AUTHENTICATE_WRONG_PASSWORD(23),
        AUTHENTICATE_LIMIT_RETRY(24),
        AUTHENTICATE_NO_ERROR(25),
        EXCEPTION(100),
        USER_NOT_EXIST(101),
        ROOM_NOT_EXIST(102),
        ROOM_ALREADY_EXIST(103),
        OPERATION_FAILED(104),
        USER_NOT_ACCESS_INTO_ROOM(105),
        PERMISSION_DENIED(106),
        SUCCESS(200);

    
        private int value;
    
        private Server(int value) {
            this.value = value;
        }
    
        public int getValue() {
            return value;
        }
    }

    public enum Client{
        AUTH_INFO(10),
        SEND_MESSAGE(20),
        CREATE_ROOM(30),
        GET_ROOM_MESSAGES(31),
        CREATE_GROUP(40),
        JOIN_GROUP(41),
        LIST_OF_GROUPS(42),
        GET_USER_NAME(50);

        private int value;
    
        private Client(int value) {
            this.value = value;
        }
    
        public int getValue() {
            return value;
        }
    }

}
