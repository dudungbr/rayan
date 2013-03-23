package com.siscomercio.network;

import java.io.IOException;
import java.net.ServerSocket;
import com.siscomercio.init.Config;

/**
 *
 * @author Rayan
 */
public class Server
{
    /**
     * $Revision$
     * $Author$
     * $Date$
     * <p/>
     * @param args
     * <p/>
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = null;
        boolean listening = true;

        try
        {
            Config.load();
            serverSocket = new ServerSocket(Config.getServerPort());
        }
        catch (IOException e)
        {
            System.err.println("Could not listen on port: " + Config.getServerPort());
            System.exit(-1);
        }

        while (listening)
        {
            new NetworkThread(serverSocket.accept()).start();
        }

        serverSocket.close();
    }
}
