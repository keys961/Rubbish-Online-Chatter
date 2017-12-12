package client;

import common.ClientInfo;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientGetOnlineList implements Runnable
{

    private JList<String> list;

    private Socket clientSocket;

    private PrintWriter out;

    private BufferedReader in;

    public ClientGetOnlineList(JList<String> list, Socket clientSocket) throws IOException
    {
        this.list = list;
        this.clientSocket = clientSocket;
        out = new PrintWriter(clientSocket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    @Override
    public void run()
    {
        out.println("[GET ONLINE LIST]");
        out.flush();
        DefaultListModel<String> clientInfoListModel = new DefaultListModel<>();
        list.removeAll();
        try
        {
            //Thread.sleep(1000);
            String info;
            int cnt = 0;
            while(!(info = in.readLine()).equals(""))
            {
                clientInfoListModel.add(cnt++, info);
            }
            list.setModel(clientInfoListModel);
        }
        catch (IOException e)
        {
            //do nothing
        }

    }
}
