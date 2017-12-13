package client;

import client.ui.ClientLoginFrame;
import common.ClientInfo;

import java.io.IOException;
import java.net.Socket;

public class Client
{
    private ClientInfo clientInfo;

    private String ip;

    private int port;

    private Socket socket;

    public Client(String ip, int port, ClientInfo clientInfo) throws IOException
    {
        this.clientInfo = clientInfo;
        this.ip = ip;
        this.port = port;
        socket = new Socket(ip, port);
    }

    public ClientInfo getClientInfo()
    {
        return clientInfo;
    }

    public Socket getSocket()
    {
        return socket;
    }

    public static void main(String[] args)
    {
        ClientLoginFrame frame = new ClientLoginFrame();
        frame.setVisible(true);
    }
}
