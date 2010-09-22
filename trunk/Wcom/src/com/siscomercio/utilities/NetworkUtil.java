package com.siscomercio.utilities;

import java.net.InetAddress;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rayan
 */
public final class NetworkUtil
{
    private static final Logger _log = Logger.getLogger(NetworkUtil.class.getName());

    private static String getMacAddress() throws IOException
    {
        String os = System.getProperty("os.name");

        try
        {
            if(os.startsWith("Windows"))
            {
                return windowsParseMacAddress(windowsRunIpConfigCommand());
            }
            else if(os.startsWith("Linux"))
                {
                    return linuxParseMacAddress(linuxRunIfConfigCommand());
                }
                else
                {
                    throw new IOException("unknown operating system: " + os);
                }
        }
        catch(ParseException ex)
        {
             SystemUtil.showErrorMsg(ex.getMessage(),true);
            throw new IOException(ex.getMessage());
        }
    }


    /*
     * Linux stuff
     */
    private static String linuxParseMacAddress(String ipConfigResponse) throws ParseException
    {
        String localHost = null;
        try
        {
            localHost = InetAddress.getLocalHost().getHostAddress();
        }
        catch(java.net.UnknownHostException ex)
        {
                SystemUtil.showErrorMsg(ex.getMessage(),true);
            throw new ParseException(ex.getMessage(), 0);
        }

        StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
        String lastMacAddress = null;

        while(tokenizer.hasMoreTokens())
        {
            String line = tokenizer.nextToken().trim();
            boolean containsLocalHost = line.indexOf(localHost) >= 0;

            // see if line contains IP address
            if(containsLocalHost && lastMacAddress != null)
            {
                return lastMacAddress;
            }

            // see if line contains MAC address
            int macAddressPosition = line.indexOf("HWaddr");
            if(macAddressPosition <= 0)
            {
                continue;
            }

            String macAddressCandidate = line.substring(macAddressPosition + 6).trim();
            if(linuxIsMacAddress(macAddressCandidate))
            {
                lastMacAddress = macAddressCandidate;
                continue;
            }
        }

        ParseException ex = new ParseException("cannot read MAC address for " + localHost + " from [" + ipConfigResponse + "]", 0);
           SystemUtil.showErrorMsg(ex.getMessage(),true);
        throw ex;
    }

    private static boolean linuxIsMacAddress(String macAddressCandidate)
    {
      
        if(macAddressCandidate.length() != 17)
        {
            return false;
        }
        return true;
    }

    private static String linuxRunIfConfigCommand() throws IOException
    {
        Process p = Runtime.getRuntime().exec("ifconfig");
        InputStream stdoutStream = new BufferedInputStream(p.getInputStream());

        StringBuilder buffer = new StringBuilder();
        for(;;)
        {
            int c = stdoutStream.read();
            if(c == -1)
            {
                break;
            }
            buffer.append((char) c);
        }
        String outputText = buffer.toString();

        stdoutStream.close();

        return outputText;
    }

    /*
     * Windows stuff
     */
    private static String windowsParseMacAddress(String ipConfigResponse) throws ParseException
    {
        String localHost = null;
        try
        {
            localHost = InetAddress.getLocalHost().getHostAddress();
        }
        catch(java.net.UnknownHostException ex)
        {
                SystemUtil.showErrorMsg(ex.getMessage(),true);
           // ex.printStackTrace();
            throw new ParseException(ex.getMessage(), 0);
        }

        StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
        String lastMacAddress = null;

        while(tokenizer.hasMoreTokens())
        {
            String line = tokenizer.nextToken().trim();

            // see if line contains IP address
            if(line.endsWith(localHost) && lastMacAddress != null)
            {
                return lastMacAddress;
            }

            // see if line contains MAC address
            int macAddressPosition = line.indexOf(":");
            if(macAddressPosition <= 0)
            {
                continue;
            }

            String macAddressCandidate = line.substring(macAddressPosition + 1).trim();
            if(windowsIsMacAddress(macAddressCandidate))
            {
                lastMacAddress = macAddressCandidate;
                continue;
            }
        }

        ParseException ex = new ParseException("cannot read MAC address from [" + ipConfigResponse + "]", 0);
            SystemUtil.showErrorMsg(ex.getMessage(),true);
        //ex.printStackTrace();
        throw ex;
    }

    private static boolean windowsIsMacAddress(String macAddressCandidate)
    {
       
        if(macAddressCandidate.length() != 17)
        {
            return false;
        }

        return true;
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public static String windowsRunIpConfigCommand() throws IOException
    {
        Process p = Runtime.getRuntime().exec("ipconfig /all");
        InputStream stdoutStream = new BufferedInputStream(p.getInputStream());

        StringBuilder buffer = new StringBuilder();
        for(;;)
        {
            int c = stdoutStream.read();
            if(c == -1)
            {
                break;
            }
            buffer.append((char) c);
        }
        String outputText = buffer.toString();

        stdoutStream.close();

        return outputText;
    }

    /*
     * Main
     */
    /**
     *
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            _log.info("Network infos");

           _log.log(Level.INFO, "  Operating System: {0}", System.getProperty("os.name"));
            _log.log(Level.INFO, "  IP/Localhost: {0}", InetAddress.getLocalHost().getHostAddress());
           _log.log(Level.INFO, "  MAC Address: {0}", getMacAddress());
        }
        catch(Throwable t)
        {
                SystemUtil.showErrorMsg(t.getMessage(),true);
          //  t.printStackTrace();
        }
    }
    /**
     * 
     * @return
     */
    public static String getMac()
    {
       String mac = "";
        try
        {
          mac =  getMacAddress();
        }
        catch(IOException ex)
        {
            SystemUtil.showErrorMsg(ex.getMessage(),true);
        }

        return mac;
    }

}
