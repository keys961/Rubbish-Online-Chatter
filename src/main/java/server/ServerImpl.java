package server;

import client.Client;
import common.ClientInfo;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerImpl implements Runnable
{
    private Map<ClientInfo, Socket> clientMap;

    private ClientInfo clientInfo;

    private Socket socket;

    private final BufferedReader in;

    private final PrintWriter out;

    public ServerImpl(Socket socket, Map<ClientInfo, Socket> clientMap) throws IOException
    {
        this.clientMap = clientMap;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void run()
    {
        boolean flag = true;
        while (flag)
        {
            try
            {
                String tag = null;
                String tline;
                while(!(tline = in.readLine()).equals("")) //get header
                {
                    tag = tline;
                }

                if(tag != null)
                {
                    switch (tag)
                    {
                        case "[REG]": addClient(); break;
                        case "[GET INFO]": sendClientId(); break;
                        case "[GET ONLINE LIST]": getOnlineList(); break;
                        case "[SEND MSG]":
                            String target = in.readLine();
                            //[UID&&USERNAME]
                            String[] targetInfo = target.substring(1, target.length() - 1).split("&&");
                            ClientInfo targetClientInfo = new ClientInfo(Integer.parseInt(targetInfo[0]), targetInfo[1]);
                            StringBuilder message = new StringBuilder();
                            String line;
                            while(!(line = in.readLine()).equals(""))
                            {
                                message.append(line);
                                message.append("\r\n");
                            }
                            message.append("\r\n");
                            sendMessage(targetClientInfo, message.toString());
                            break;
                        case "[EXIT]":
                            closeConnection();
                    }
                }
            }
            catch (IOException e)
            {
                //socket dead, close it
                closeConnection();
                flag = false;
            }
        }
    }

    private void addClient()
    {
        String line = "";
        String tag = "";

        try
        {
            while (!(tag = in.readLine()).equals(""))
                line = tag;
            String[] info = line.split("&&");
            clientInfo = new ClientInfo(Integer.parseInt(info[0]), info[1]);
            clientMap.put(clientInfo, socket);
        }
        catch (IOException e)
        {
            //
        }
    }


    private void getOnlineList()
    {
        /**
         * Format:
         * [ONLINE LIST]\r\n
         * \r\n
         * [ClientInfo_1]\r\n
         * [ClientInfo_2]\r\n
         * ...
         * [ClientInfo_n]\r\n
         * \r\n
         */
        StringBuilder infoList = new StringBuilder("[ONLINE LIST]\r\n\r\n");
        for(ClientInfo info : clientMap.keySet())
        {
            if(info.getId() != clientInfo.getId())
            {
                infoList.append(info.toString());
                infoList.append("\r\n");
            }
        }
        infoList.append("\r\n");
        synchronized (out)
        {
            out.print(infoList);
            out.flush();
        }
    }

    private void sendMessage(ClientInfo targetInfo, String message)
    {
        if(clientMap.containsKey(targetInfo))
        {
            Socket otherClient = clientMap.get(targetInfo);
            try
            {

                final PrintWriter otherOut = new PrintWriter(new OutputStreamWriter(otherClient.getOutputStream()));
                    /**
                     * Format:
                     * [SrcUID&&SrcUsername]\r\n
                     * \r\n
                     * msg..
                     * \r\n
                     */
                synchronized (otherOut)
                {
                    message = "[" + clientInfo.getId() + "&&" + clientInfo.getUsername() + "]\r\n\r\n" + message; //add header...
                    otherOut.print(message); //forwarding message...
                    otherOut.flush();
                }
            }
            catch (IOException e)
            {
                System.err.println("Sending/Forwarding message error");
            }
        }
    }

    private void closeConnection()
    {
        /**
         * Format:
         * [EXIT]\r\n
         * \r\n
         */
        //out.print("[EXIT]\r\n\r\n");
       // out.flush();
        clientMap.remove(clientInfo);
        try
        {
            in.close();
            out.close();
            socket.close();
        }
        catch (IOException e)
        {
            System.err.println("Close client connection error");
        }
    }

    @Deprecated
    private void sendClientId()
    {
        synchronized (out)
        {
            out.println(clientInfo.getId());
            out.flush();
        }
    }

}
