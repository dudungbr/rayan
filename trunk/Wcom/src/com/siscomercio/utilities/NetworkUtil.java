package com.siscomercio.utilities;

import java.net.InetAddress;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
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
 
    private static String selectOs() throws IOException
    {
        String os = System.getProperty("os.name");

        try
        {
            //windowsRunIpConfigCommand(
            if(os.startsWith("Windows"))
            {
                 InetAddress ia = InetAddress.getLocalHost();
                return getWindowsMacAddress();
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
            if(isValidMacAddress(macAddressCandidate))
            {
                lastMacAddress = macAddressCandidate;
                continue;
            }
        }

        ParseException ex = new ParseException("cannot read MAC address for " + localHost + " from [" + ipConfigResponse + "]", 0);
           SystemUtil.showErrorMsg(ex.getMessage(),true);
        throw ex;
    }


    private static String linuxRunIfConfigCommand() throws IOException
    {
        Process p = Runtime.getRuntime().exec("ifconfig");
        String outputText;
        try (InputStream stdoutStream = new BufferedInputStream(p.getInputStream())) {
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
            outputText = buffer.toString();
        }

        return outputText;
    }

    /*
     * Windows stuff
     */
    private static String getWindowsMacAddress()
    {
       StringBuilder sb = new StringBuilder("");
       try {
         
            InetAddress address = InetAddress.getLocalHost();

            /*
             * Get NetworkInterface for the current host and then read
             * the hardware address.
             */
            NetworkInterface ni =
                    NetworkInterface.getByInetAddress(address);
            if (ni != null) {
                byte[] mac = ni.getHardwareAddress();
                if (mac != null) {
                    /*
                     * Extract each array of mac address and convert it 
                     * to hexa with the following format 
                     * 08-00-27-DC-4A-9E.
                     */
                    for (int i = 0; i < mac.length; i++) 
                    {
                   
                 sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : ""));
                
                    }
                } else {
                    System.out.println("Address doesn't exist or is not "
                            + "accessible.");
                }
            } else {
                System.out.println("Network Interface for the specified "
                        + "address is not found.");
            }
        } catch (UnknownHostException | SocketException e) {
        }
      
       return sb.toString();
    }
    public static String[] retornaArrayString(String[] valor){  
      String[] string = new String[valor.length];  
      
      return string;  
    }  
    private static boolean isValidMacAddress(String macAddressCandidate)
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
        String outputText;
        try (InputStream stdoutStream = new BufferedInputStream(p.getInputStream())) {
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
            outputText = buffer.toString();
        }

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
           _log.log(Level.INFO, "  MAC Address: {0}", getWindowsMacAddress());
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
       String  mac;
        mac = getWindowsMacAddress();
        
       
        return mac;
    }

}
