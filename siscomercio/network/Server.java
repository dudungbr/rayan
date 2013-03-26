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

            serverSocket = new ServerSocket(Config.getInstance().getServerPort());
        }
        catch (IOException e)
        {
            System.err.println("Could not listen on port: " + Config.getInstance().getServerPort());
            System.exit(-1);
        }

        while (listening)
        {
            new NetworkThread(serverSocket.accept()).start();
        }

        serverSocket.close();
    }
}
