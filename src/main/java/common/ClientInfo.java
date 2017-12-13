package common;

import client.Client;

import java.io.Serializable;

public final class ClientInfo implements Serializable
{

    private int id;

    private String username;

    public ClientInfo(int id, String username)
    {
        this.username = username;
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public String getUsername()
    {
        return username;
    }

    @Override
    public String toString()
    {
        return "ID: " + this.id + ", Username: " + username;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof ClientInfo))
            return false;

        if(obj == this)
            return true;

        ClientInfo convertedObj = (ClientInfo)obj;
        return convertedObj.id == this.id && convertedObj.username.equals(this.username);
    }

    @Override
    public int hashCode()
    {
        return id;
    }
}

