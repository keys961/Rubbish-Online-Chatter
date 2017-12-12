package client;

import common.ClientInfo;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientMessage implements Runnable
{
    private JTextArea textArea;

    private int targetUid;

    private int currentId;

    private Socket clientSocket;

    private BufferedReader in;

    private PrintWriter out;

    public ClientMessage(int targetUid, Socket clientSocket, JTextArea textArea, int currentId) throws IOException
    {
        this.textArea = textArea;
        this.targetUid = targetUid;
        this.clientSocket = clientSocket;
        this.currentId = currentId;
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.out = new PrintWriter(clientSocket.getOutputStream());
    }

    @Override
    public void run()
    {
        while(true)
        {
            String line;
            try
            {
                line = in.readLine();
                if(line.length() == 0)
                    continue;
                if(currentId == Integer.parseInt(line.substring(1, line.length() - 1)))
                {
                    textArea.append("From " + targetUid + ":\n");
                    while(!(line = in.readLine()).equals(""))
                        textArea.append(line + "\r\n");
                }
            }
            catch (IOException e)
            {
                //
            }
        }
    }

    /**
     * Send msg to server
     */
    public void sendMessage(String message)
    {
        String head = "[SEND MSG]\r\n[" + targetUid + "]\r\n";
        String sendMsg = head + message + "\r\n\r\n";
        out.print(sendMsg);
        out.flush();
    }
}
