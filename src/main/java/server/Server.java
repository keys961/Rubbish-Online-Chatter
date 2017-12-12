package server;

import client.Client;
import common.ClientInfo;
import server.ui.ServerFrame;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable
{
    private Map<ClientInfo, Socket> clientMap;

    private Map<Integer, Socket> hashClientMap;

    private int port;

    private ServerSocket serverSocket;

    private ExecutorService clientThreadPool;

    private JTextArea textArea;

    public Server(int port, JTextArea textArea) throws IOException
    {
        this.hashClientMap = new ConcurrentHashMap<>();
        this.clientMap = new ConcurrentHashMap<>();
        this.textArea = textArea;
        this.port = port;
        serverSocket = new ServerSocket(this.port);
        this.clientThreadPool = Executors.newCachedThreadPool();
    }

    public ServerSocket getServerSocket()
    {
        return serverSocket;
    }

    private void startServer()
    {
        while(true)
        {
            try
            {
                Socket client = serverSocket.accept(); //accept a client

                ClientInfo clientInfo = new ClientInfo(client.hashCode(), client.getInetAddress().toString(), client.getPort());
                Runnable clientThread = new ServerImpl(clientInfo, client, clientMap, hashClientMap);
                clientThreadPool.submit(clientThread); //add to thread pool
                clientMap.put(clientInfo, client); //put to map
                hashClientMap.put(client.hashCode(), client);
                this.textArea.append("Client " + client.hashCode() + " has connected to the server, IP: " +
                    client.getInetAddress().toString() + " , port: " + client.getPort() + ".\n");
            }
            catch (IOException e)
            {
                this.textArea.append("An error occurred when a client trying to connect the server.\n");
                //e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        ServerFrame frame = new ServerFrame();
        frame.setVisible(true);
    }

    public void run()
    {
        startServer();
    }
}
