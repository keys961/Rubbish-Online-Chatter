package common;

/**
 * Please substitute info of the server's end DBMS
 */
public final class ServerDBConfig
{
    public static final String URL = "jdbc:mysql://localhost/server_online_chatter?serverTimezone=UTC&useSSL=true";

    public static final String USERNAME = "root";

    public static final String PASSWORD = "123456";

    public static final class ServerUser
    {
        public static final String UID = "uid";

        public static final String USERNAME = "username";

        public static final String PASSWORD = "password";
    }
}
