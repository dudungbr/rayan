/*
 * To change this template, choose SystemUtil | Templates
 * and open the template in the editor.
 */
package com.siscomercio.utilities;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import com.siscomercio.tables.ErrorTable;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.siscomercio.Config;
import java.io.File;
import java.io.FileFilter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.UIManager;
import javolution.util.FastList;

/**
 *$Revision$
 * $Author$
 * $Date$
 * @author Rayan
 */
public class WindowsUtil
{
    private static final Logger _log = Logger.getLogger(WindowsUtil.class.getName());

    /**
     * 
        // Remove os "-" dos Numeros.
        //-------------------------------
        /*for(int i = 0; i < (dados.length() - remover.length() + 1); i++)
        {
            String res = dados.substring(i, (i + remover.length()));
            if(res.equals(remover))
            {
                int pos = dados.indexOf(remover);
                dados.delete(pos, pos + remover.length());
                cont++;
            }
        }*/

       // if(Config.DEBUG)
          //  _log.log(Level.INFO, "A frase contem {0} ocorrencias de - ", cont);*/
    /**
     * Lista todos os arquivos de um diretorio
     *
     * @param path
     * @param extension
     * @return o nome de todos os arquivos da pasta e extensao especificada
     */
    public static String listDirFiles(String path, final String extension)
    {
        File pasta = new File(path);
        List<String> arquivos = new FastList<String>();

        for(File f : pasta.listFiles())
        {
            if(f != null && f.getName().endsWith(extension))
                arquivos.add(f.getName());
        }
        return arquivos.toString();
    }

    /**
     * Lista as Pastas
     *
     * @param path
     * @param extension
     * @return files
     */
    public static int countFiles(String path, final String extension)
    {
        File pasta = new File(path);//  diretório
        List<File> arquivos = new FastList<File>(); // lista de arquivos a ser criada

        for(File arq : pasta.listFiles())
        {
            //condição para pegar só os arquivos, e nao diretórios
            if(arq.isFile() && arq.getName().endsWith(extension))
            {
                if(arq != null)
                    arquivos.add(arq);
                // System.out.println("Arquivo " +
                // ++i;// + ": " + arq.getName() + "  Última modificação: " + new Date(arq.lastModified()));
                //aqui vc poderia formar uma lista com os arquivos
            }
        }
        if(Config.DEBUG)
            _log.log(Level.INFO, "Contando Arquivos do Diretorio SQL, Total: {0} Arquivos. \n", arquivos.size());

        return arquivos.size();
    }

    /**
     * Lista as Pastas
     *
     * @param path
     * @return files
     */
    public static File[] listFolders(String path)
    {
        File F = new File(path);
        File[] files = F.listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File pathname)
            {
                return pathname.getName().toLowerCase().endsWith("");
            }

        });

        return files;
    }

    /**
     * Imprime o Uso de Memória
     */
    public static void printMemUsage()
    {
        double max = Runtime.getRuntime().maxMemory() / 1024; // maxMemory ismthe upper limit the jvm can use
        double allocated = Runtime.getRuntime().totalMemory() / 1024; // totalMemory: the size of current allocation pool
        double nonAllocated = max - allocated; // non allocated memory till jvm limit
        double cached = Runtime.getRuntime().freeMemory() / 1024; // freeMemory: the unused memory in allocation pools
        double used = allocated - cached; // really used memory
        double useable = max - used; // allocated, but non-used and
        // non-allocated memory
        SimpleDateFormat sdf = new SimpleDateFormat("H:mm:ss");
        DecimalFormat df = new DecimalFormat(" (0.0000' %')");
        DecimalFormat df2 = new DecimalFormat("   'KB' ");

        SystemUtil.showMsg("Relatorio Gerado as "
                           + sdf.format(new Date()) + ": <br>" + "<br>"
                           + "|========= Memoria Livre =========" + "<br>"
                           + "|    |= Total:" + df2.format(max) + "<br>"
                           + "|    |= Memoria Total:" + df2.format(allocated)
                           + df.format(allocated / max * 100) + "<br>"
                           + "|    |= Memoria Nao Alocada" + df2.format(nonAllocated)
                           + df.format(nonAllocated / max * 100) + "<br>" + "<br>"
                           + " =========  Memoria Alocada ========== <br>"
                           + "|    |= Total:" + df2.format(allocated) + "<br>"
                           + "|    |= Memoria Usada:" + df2.format(used)
                           + df.format(used / max * 100) + "<br>"
                           + "|    |= Memoria Cacheada:" + df2.format(cached)
                           + df.format(cached / max * 100) + "<br>"
                           + "|    |= Memoria Disponivel:" + df2.format(useable)
                           + df.format(useable / max * 100),true);
    }

    /**
     * Checa se um Processo esta em Execução.
     * @param namePart
     */
    public static void checkProcess(String namePart)
    {
        _log.info("Checando Existencia do Servidor MySQL...\n");
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
            try
            {
                UIManager.setLookAndFeel(new AcrylLookAndFeel());
            }
            catch(Exception e)
            {
                SystemUtil.showErrorMsg("Nao Foi Possivel Carregar a Skin" + e.getMessage(),true);
            }
            SystemUtil.showErrorMsg(ErrorTable.throwError(1) + "<br><br> Processos: <br>" + getProcess(namePart) + "<br>" + getSuport()+"<br><br>",true);
            System.exit(0);
        }
        else
            if(Config.DEBUG)
                _log.info("processo mysql funcionando! ok \n");
    }

    /**
     * Nome do Suporte Técnico
     * @return "Contacte o Suporte Técnico."
     */
    private static String getSuport()
    {
        return "Contacte o Suporte Técnico.";
    }

    /**
     * Pega um processo
     * @param pName
     * @return  pName
     */
    private static String getProcess(String pName)
    {
        if(pName.startsWith("mysql"))
        {
            pName = "&#160 &#160 &#160 &#160 &#160  &#160 &#160 &#160 &#160 mysqld.exe<br>" 
                  + "&#160 &#160 &#160 &#160 &#160  &#160 &#160 &#160 &#160 mysqld-nt.exe<br>";

        }
        else
            throw new UnsupportedOperationException("valor nao suportado");

        return pName;
    }

    /**
     * Finaliza um Processo em Execucao na Máquina
     *
     * @param processToKill
     * @return false
     */
    public static boolean killProcess(String processToKill)
    {
        try
        {
            String line;
            Process p = Runtime.getRuntime().exec("tasklist.exe");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));


            while((line = input.readLine()) != null)
            {
                if(!line.trim().equals(""))
                    if(line.substring(1, line.indexOf("\"", 1)).equalsIgnoreCase(processToKill))
                    {
                        Runtime.getRuntime().exec("taskkill /F /IM " + line.substring(1, line.indexOf("\"", 1)));
                        _log.log(Level.INFO, "Matando Processo: {0}", processToKill);
                        return true;
                    }
            }
            input.close();

        }
        catch(Exception err)
        {
            SystemUtil.showErrorMsg(err.getMessage(),true);
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
            SystemUtil.showErrorMsg(err.getMessage(),true);
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
            SystemUtil.showErrorMsg("Error While Trying to get Pc Name =" + e.getMessage(),true);
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
            _log.log(Level.INFO, "Running processes : \n{0}", result);
            _log.log(Level.INFO, "Total de Processos: {0}", processes.size());
        }
    }

}
