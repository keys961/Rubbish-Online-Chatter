package common;

import client.Client;

import java.io.Serializable;

public final class ClientInfo implements Serializable
{
    private int id;

    private String ip;

    private int port;

    public ClientInfo(int id, String ip, int port)
    {
        this.id = id;
        this.ip = ip;
        this.port = port;
    }

    public int getId()
    {
        return id;
    }

    public String getIp()
    {
        return ip;
    }

    public int getPort()
    {
        return port;
    }

    @Override
    public String toString()
    {
        return "ID: " + this.id + ", IP: " + ip + ", Port: " + port;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof ClientInfo))
            return false;

        if(obj == this)
            return true;

        ClientInfo convertedObj = (ClientInfo)obj;
        return convertedObj.id == this.id && convertedObj.port == this.port
                && convertedObj.ip.equals(this.ip);
    }
}

