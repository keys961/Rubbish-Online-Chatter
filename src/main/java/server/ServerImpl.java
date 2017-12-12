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

    private Map<Integer, Socket> hashClientMap;

    private ClientInfo clientInfo;

    private Socket socket;

    private BufferedReader in;

    private PrintWriter out;

    public ServerImpl(ClientInfo info, Socket socket, Map<ClientInfo, Socket> clientMap, Map<Integer, Socket> hashClientMap) throws IOException
    {
        this.hashClientMap = hashClientMap;
        this.clientMap = clientMap;
        this.socket = socket;
        this.clientInfo = info;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void run()
    {
        while (true)
        {
            try
            {
                String tag = in.readLine();
                if(tag != null)
                {
                    switch (tag)
                    {
                        case "[GET INFO]": sendClientId(); break;
                        case "[GET ONLINE LIST]": getOnlineList(); break;
                        case "[SEND MSG]":
                            String target = in.readLine();
                            //[TARGET UID]
                            target = target.substring(1, target.length() - 1);
                            StringBuilder message = new StringBuilder();
                            String line;
                            while(!(line = in.readLine()).equals(""))
                            {
                                message.append(line);
                                message.append("\r\n");
                            }
                            message.append("\r\n");
                            sendMessage(Integer.parseInt(target), message.toString());
                            break;
                        case "[EXIT]":
                            closeConnection();
                    }
                }
            }
            catch (IOException e)
            {
                //do nothing
            }
        }
    }

    private void getOnlineList()
    {
        StringBuilder infoList = new StringBuilder();
        for(ClientInfo info : clientMap.keySet())
        {
            if(info.getId() != clientInfo.getId())
            {
                infoList.append(info.toString());
                infoList.append("\r\n");
            }
        }
        infoList.append("\r\n");
        out.print(infoList);
        out.flush();
    }

    private void sendMessage(int uid, String message)
    {
        if(hashClientMap.containsKey(uid))
        {
            Socket otherClient = hashClientMap.get(uid);
            try
            {
                PrintWriter otherOut = new PrintWriter(new OutputStreamWriter(otherClient.getOutputStream()));
                message = "[" + uid + "]\r\n" + message; //add header...
                otherOut.print(message); //forwarding message...
                otherOut.flush();
            }
            catch (IOException e)
            {
                System.err.println("Sending/Forwarding message error");
            }
        }
    }

    private void closeConnection()
    {
        try
        {
            in.close();
            out.close();
            socket.close();
            hashClientMap.remove(clientInfo.getId());
            clientMap.remove(clientInfo);
        }
        catch (IOException e)
        {
            System.err.println("Close client connection error");
        }
    }

    private void sendClientId()
    {
        out.println(clientInfo.getId());
        out.flush();
    }

}
