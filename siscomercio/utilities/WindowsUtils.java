/*
 * To change this template, choose SystemUtil | Templates
 * and open the template in the editor.
 */
package com.siscomercio.utilities;

import com.siscomercio.tables.ErrorTable;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.siscomercio.Config;

/**
 *$Revision$
 * $Author$
 * $Date$
 * @author Rayan
 */
public class WindowsUtils
{
    private static Logger _log = Logger.getLogger(WindowsUtils.class.getName());

    /**
     * Checa se um Processo esta em Execução.
     * @param namePart
     */
    public static void checkProcess(String namePart)
    {
        List<String> processes = listRunningProcesses();
        String result = "";

        // display the result
        Iterator<String> it = processes.iterator();

        int i = 0;

        while(it.hasNext())
        {
            result += it.next();
            i++;
        }

        if(!result.contains(namePart))
        {
            SystemUtil.showErrorMsg(ErrorTable.throwError(1) + "\n \n Detalhes: \n" + getProcess(namePart) + "\n" + getSuport());
            System.exit(0);
        }
        else
            if(Config.DEBUG)
                _log.info("processo ok \n");
    }

    /**
     * Nome do Suporte Técnico
     * @return
     */
    private static String getSuport()
    {
        return "Contacte o Suporte Técnico.";
    }

    /**
     * Pega um processo
     * @param s
     * @return
     */
    private static String getProcess(String s)
    {
        String value;
        if(s.startsWith("mysql"))
        {
            value = "mysqld.exe\n" + "mysqld-nt.exe\n";
            s = value;
        }
        else
            throw new UnsupportedOperationException("valor nao suportado");

        return s;
    }

    /**
     * Finaliza um Processo em Execucao na Máquina
     *
     * @param processo
     * @return false
     */
    public static boolean killProcess(String processo)
    {
        try
        {
            String line;
            Process p = Runtime.getRuntime().exec("tasklist.exe");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));


            while((line = input.readLine()) != null)
            {
                if(!line.trim().equals(""))
                    if(line.substring(1, line.indexOf("\"", 1)).equalsIgnoreCase(processo))
                    {
                        Runtime.getRuntime().exec("taskkill /F /IM " + line.substring(1, line.indexOf("\"", 1)));
                        _log.info("Matando Processo: " + processo);
                        return true;
                    }
            }
            input.close();

        }
        catch(Exception err)
        {
            err.printStackTrace();
        }
        return false;
    }

    /**
     * Retorna Todos os Processos em Execucao
     * @return processes
     */
    private static List<String> listRunningProcesses()
    {
        List<String> processes = new ArrayList<String>();
        try
        {
            String line;
            Process p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));


            while((line = input.readLine()) != null)
            {
                if(!line.trim().equals(""))
                {
                    // keep only the process name
                    line = line.substring(1);
                    processes.add(line.substring(0, line.indexOf("\"", 1)) + "\n");
                }

            }
            input.close();


        }
        catch(Exception err)
        {
            err.printStackTrace();


        }
        return processes;

    }

    /**
     * Pega o Nome dessa Maquina
     * @return the name of this Machine
     */
    public static String getPcName()
    {
        String pcName = null;
        try
        {
            pcName = InetAddress.getLocalHost().getHostName();

        }
        catch(Exception e)
        {
            SystemUtil.showErrorMsg("Error While Trying to get Pc Name =" + e.getMessage());
        }

        return pcName;
    }

    /**
     * Mostra Todos os Processos Em Execucao na Máquina
     */
    public static void showRunningProcesses()
    {
        List<String> processes = listRunningProcesses();
        String result = "";

        // display the result
        Iterator<String> it = processes.iterator();
        int i = 0;
        while(it.hasNext())
        {
            result += it.next();
            i++;
        }

        if(Config.DEBUG)
        {
            _log.info("Running processes : \n" + result);
            _log.info("Total de Processos: " + processes.size());
        }
    }

}
