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

    private ServerSocket serverSocket;

    private ExecutorService clientThreadPool;

    private JTextArea textArea;

    public Server(int port, JTextArea textArea) throws IOException
    {
        this.clientMap = new ConcurrentHashMap<>();
        this.textArea = textArea;
        serverSocket = new ServerSocket(port);
        this.clientThreadPool = Executors.newCachedThreadPool();
    }

    public ServerSocket getServerSocket()
    {
        return serverSocket;
    }

    private void startServer()
    {
        boolean flag = true;
        while(!Thread.currentThread().isInterrupted() && flag)
        {

            try
            {
                Thread.sleep(1);
                Socket client = serverSocket.accept(); //accept a client

                Runnable clientThread = new ServerImpl(client, clientMap);
                clientThreadPool.submit(clientThread); //add to thread pool

                this.textArea.append("Client " + client.hashCode() + " has connected to the server, IP: " +
                    client.getInetAddress().toString() + " , port: " + client.getPort() + ".\n");
            }
            catch (IOException e)
            {
                this.textArea.append("An error occurred when a client trying to connect the server.\n");
                try
                {
                    serverSocket.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
                flag = false;
                //e.printStackTrace();
            }
            catch (InterruptedException e)
            {
                flag = false;
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
