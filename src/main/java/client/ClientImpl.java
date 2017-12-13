package client;

import common.ClientDBConfig;
import common.ClientInfo;
import common.DBConnectionFactory;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ClientImpl implements Runnable
{
    private boolean flag = true;

    private Map<ClientInfo, JTextArea> clientTextAreaMap; //info themselves

    private JList<String> onlineList;

    private ClientInfo srcInfo; //info myself

    private Socket clientSocket;

    private BufferedReader in;

    private PrintWriter out;

    private Connection clientDBConnection;

    public ClientImpl(Client client, Map<ClientInfo, JTextArea> clientTextAreaMap, JList<String> onlineList)
            throws IOException, SQLException, ClassNotFoundException
    {
        this.onlineList = onlineList;
        this.clientTextAreaMap = clientTextAreaMap;
        this.clientSocket = client.getSocket();
        this.srcInfo = client.getClientInfo();
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.out = new PrintWriter(clientSocket.getOutputStream());
        this.clientDBConnection = DBConnectionFactory.getClientConnection();
    }

    public void addTextArea(ClientInfo clientInfo, JTextArea textArea)
    {
        clientTextAreaMap.put(clientInfo, textArea);
    }

    public void removeTextArea(ClientInfo clientInfo)
    {
        clientTextAreaMap.remove(clientInfo);
    }

    /**
     * Receive main process
     */
    @Override
    public void run()
    {

        String line = "";
        String temp = "";
        while (flag)
        {
            try
            {
                while (!(temp = in.readLine()).equals(""))
                    line = temp;

                switch (line)
                {
                    case "[ONLINE LIST]": getOnlineList(); break;
                    //case "[EXIT]": closeConnection(); break;
                    default:
                        getMessage(line); break;
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
                flag = false;
            }
        }
    }

    private void getMessage(String line)
    {
        PreparedStatement statement;

        String update = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?,?,?,?,?)",
                ClientDBConfig.ClientMessage.TABLE_NAME,
                ClientDBConfig.ClientMessage.FROM_ID,
                ClientDBConfig.ClientMessage.TO_ID,
                ClientDBConfig.ClientMessage.MESSAGE,
                ClientDBConfig.ClientMessage.SENT_TIME,
                ClientDBConfig.ClientMessage.USER_ID);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            String temp;
            String[] info = line.substring(1, line.length() - 1).split("&&");
            ClientInfo targetInfo = new ClientInfo(Integer.parseInt(info[0]), info[1]); //from...
            StringBuilder message = new StringBuilder();
            while (!(temp = in.readLine()).equals(""))
            {
                message.append(temp);
                message.append("\r\n");
            }
            java.util.Date date = new java.util.Date();
            statement = clientDBConnection.prepareStatement(update);
            statement.setInt(1, targetInfo.getId());
            statement.setInt(2, srcInfo.getId());
            statement.setString(3, message.toString());
            statement.setString(4, sdf.format(date));
            statement.setInt(5, srcInfo.getId());
            statement.executeUpdate();
            if(clientTextAreaMap.containsKey(targetInfo))
            {
               if(clientTextAreaMap.get(targetInfo) != null)
               {
                   clientTextAreaMap.get(targetInfo).append("From " + targetInfo.getUsername() + " at " + sdf.format(date) + ": \r\n");
                   clientTextAreaMap.get(targetInfo).append(message.toString());
                   clientTextAreaMap.get(targetInfo).append("\r\n");
               }
            }

        }
        catch (IOException | SQLException e)
        {
            e.printStackTrace();
        }
    }


    private void closeConnection()
    {
        try
        {
            in.close();
            out.close();
            clientSocket.close();
            clientDBConnection.close();
            flag = false;
        }
        catch (IOException | SQLException e)
        {
            System.err.println("Client close connection error.");
        }

    }


    private void getOnlineList()
    {
        DefaultListModel<String> clientInfoListModel = new DefaultListModel<>();
        onlineList.removeAll();
        try
        {
            String info;
            int cnt = 0;
            while(!(info = in.readLine()).equals(""))
            {
                clientInfoListModel.add(cnt++, info);
            }
            onlineList.setModel(clientInfoListModel);
        }
        catch (IOException e)
        {
            //do nothing
        }
    }

    /**
     * Send msg to server, format:
     * [SEND MSG]\r\n
     * \r\n
     * [(uid)&&(username)]\r\n
     * msg..
     * \r\n
     */
    public void sendMessage(ClientInfo targetInfo, String message)
    {
        PreparedStatement statement;
        String update = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?,?,?,?,?)",
                ClientDBConfig.ClientMessage.TABLE_NAME,
                ClientDBConfig.ClientMessage.FROM_ID,
                ClientDBConfig.ClientMessage.TO_ID,
                ClientDBConfig.ClientMessage.MESSAGE,
                ClientDBConfig.ClientMessage.SENT_TIME,
                ClientDBConfig.ClientMessage.USER_ID);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(targetInfo != null)
        {
            Date date = new Date();

            try
            {
                statement = clientDBConnection.prepareStatement(update);
                statement.setInt(1, srcInfo.getId());
                statement.setInt(2, targetInfo.getId());
                statement.setString(3, message);
                statement.setString(4, sdf.format(date));
                statement.setInt(5, srcInfo.getId());
                statement.executeUpdate();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

            String head = "[SEND MSG]\r\n\r\n[" + targetInfo.getId() + "&&" + targetInfo.getUsername() + "]\r\n";
            String sendMsg = head + message + "\r\n";
            out.print(sendMsg);
        }
        else
        {
            if (message.equals("[EXIT]\r\n\r\n"))
            {
                out.print(message);
                out.flush();
                closeConnection();
                return;
            }
            out.print(message);//other type of message
        }
        out.flush();
    }

}
