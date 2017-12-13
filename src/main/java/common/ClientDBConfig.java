package common;

/**
 * Please substitute info of the client's end DBMS
 */

public final class ClientDBConfig
{
    public static final String URL = "jdbc:mysql://localhost/client_online_chatter?serverTimezone=UTC&useSSL=true";

    public static final String USERNAME = "root";

    public static final String PASSWORD = "123456";

    public static final class ClientMessage
    {
        public static final String TABLE_NAME = "client_message";

        public static final String MSG_ID = "msg_id";

        public static final String FROM_ID = "from_id";

        public static final String TO_ID = "to_id";

        public static final String MESSAGE = "message";

        public static final String SENT_TIME = "sent_time";
    }
}
