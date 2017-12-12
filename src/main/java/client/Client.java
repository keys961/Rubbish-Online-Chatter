package client;

import client.ui.ClientLoginFrame;

import java.io.IOException;
import java.net.Socket;

public class Client
{
    private String ip;

    private int port;

    //private int uid;

    private Socket socket;

    public Client(String ip, int port) throws IOException
    {
        this.ip = ip;
        this.port = port;
        socket = new Socket(ip, port);
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
