package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionFactory
{
    public static Connection getServerConnection() throws SQLException, ClassNotFoundException
    {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(ServerDBConfig.URL, ServerDBConfig.USERNAME, ServerDBConfig.PASSWORD);
    }

    public static Connection getClientConnection() throws SQLException, ClassNotFoundException
    {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(ClientDBConfig.URL, ClientDBConfig.USERNAME, ClientDBConfig.PASSWORD);
    }

    @Deprecated
    public static void main(String[] args)
    {
        try
        {
            Connection a = getServerConnection();
            a.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
