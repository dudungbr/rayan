package com.siscomercio.network;

import com.siscomercio.utilities.SystemUtil;
import java.net.Socket;

/**
 * @author Rayan
 *
 *
 */
public class NetworkThread extends Thread
{
    private Socket socket = null;

    /**
     * $Revision$ $Author$ $Date: 2010-09-22
     * 14:43:39 -0300 (Qua, 22 Set 2010) $
     * @param socket
     */
    public NetworkThread(Socket socket)
    {
        super("NetworkThread");
        this.socket = socket;
    }

    /**
     *
     */
    @Override
    public void run()
    {

        try
        {
            System.out.println("aguardando conexoes");
            //socket.close();

        }
        catch (Exception e)
        {
            SystemUtil.showErrorMsg(e.getMessage(), true);
        }
    }
}
