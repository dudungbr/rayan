package com.siscomercio.utilities;

import java.io.File;
import java.io.FileFilter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import com.siscomercio.Config;
import com.siscomercio.DatabaseFactory;
import com.siscomercio.managers.DatabaseManager;

/**
 * $Revision$
 * $Author$
 * $Date$
 * @author Rayan
 * 
 */
public class SystemUtil
{
    private static final long serialVersionUID = 1L;
    private static Logger _log = Logger.getLogger(SystemUtil.class.getName());

    /**
     * Imprimve a Versoa e a Build do Sistema
     */
    public static void printVersion()
    {
        SystemUtil.showMsg(Config.BUILD_AUTHOR.substring(1, 30) + "\n"
                           + Config.BUILD_NUM.substring(1, 14) + "\n"
                           + Config.BUILD_DATE.substring(1, 52) + "\n"
                           + Config.BUILD_URL.substring(1, 75) + "\n"
                           + "Versao do Sistema " + Config.SYSTEM_VERSION);

    }

    /**
     *
     * @return the Site of Config File
     */
    public static String getSite()
    {
        return Config.SITE;
    }

    /**
     *
     * @return the System Version
     */
    public static String getVersion()
    {
        return String.valueOf(Config.SYSTEM_VERSION);
    }

    /**
     * Prints all Database Information
     * @throws SQLException
     */
    public static void printDbInfo() throws SQLException
    {
        showMsg("\n Provedor do Banco: " + DatabaseFactory.getInstance().getProviderType() + "\n"
                + "Status do Banco: " + DatabaseManager.getConnectionStatus() + "\n"
                + "Conexoes Ativas: " + String.valueOf(DatabaseFactory.getInstance().getBusyConnectionCount()) + "\n"
                + "Conexoes Criadas : " + String.valueOf(DatabaseFactory.getInstance().getIdleConnectionCount() + "\n \n"));
    }

    /**
     * Print Processor Info
     */
    public static void printCpuInfo()
    {
        showMsg("Processadores: " + Runtime.getRuntime().availableProcessors() + "\n"
                + "Arquitetura " + System.getenv("PROCESSOR_IDENTIFIER"));
    }

    /**
     * Print O.S Info
     */
    public static void printOSInfo()
    {
        showMsg("Sistema Operacional: " + System.getProperty("os.name") + "\n"
                + "Build: " + System.getProperty("os.version") + "\n"
                + "Arquitetura: " + System.getProperty("os.arch"));
    }

    /**
     * Printi all java info
     */
    public static void printJvmInfo()
    {
        showMsg("\n == Virtual Machine Information (JVM) == \n"
                + "Name: " + System.getProperty("java.vm.name") + "\n"
                + "JRE Directory:  " + System.getProperty("java.home") + "\n"
                + "Version:  " + System.getProperty("java.vm.version") + "\n"
                + "Vendor:  " + System.getProperty("java.vm.vendor") + "\n"
                + "Info:  " + System.getProperty("java.vm.info") + "\n \n"
                + "== Java Platform Information == \n"
                + "Name:  " + System.getProperty("java.runtime.name") + "\n"
                + "Version: " + System.getProperty("java.version") + "\n"
                + "Java Class Version: " + System.getProperty("java.class.version") + "\n");
    }
    /**
     * Converte segundos em milisegundos
     *
     * @param secondsToConvert
     * @return miliseconds
     */
    public static int convertSecondsToMiliseconds(int secondsToConvert)
    {
        return secondsToConvert * 1000;
    }

    /**
     * converte minutos em Milisegundos
     *
     * @param minutesToConvert
     * @return miliseconds
     */
    public static int convertMinutesToMiliseconds(int minutesToConvert)
    {
        return minutesToConvert * 60000;
    }

    /**
     * converte minutos em Segundos
     *
     * @param minutesToConvert
     * @return seconds
     */
    public static int convertMinutesToSeconds(int minutesToConvert)
    {
        return minutesToConvert * 60;
    }

    /**
     * converte uma percentagem por multiplo
     *
     * @param multiplerX100
     * @return 100-multiplerX100;
     * 
     */
    public double convertPercentageByMultipler(double multiplerX100)
    {
        return 100 - multiplerX100;
    }

    /**
     * calcula percentagem
     *
     * @param number
     * @param percentage
     * @return thepercentage
     */
    public double calculatePercentage(double number, double percentage)
    {
        double values = number * percentage;
        double tmp = values / 100;

        return tmp;
    }

    /**
     * mostra mensagem de informacao ao usuario
     *
     * @param msg
     */
    public static void showMsg(String msg)
    {
        if(Config.DEBUG)
            _log.info("enviando janela de mensagem... \n");
        JOptionPane.showMessageDialog(null, msg, "Informacao", JOptionPane.INFORMATION_MESSAGE);

    }

    /**
     * mostra mensagem de erro ao usuario
     *
     * @param msg
     */
    public static void showErrorMsg(String msg)
    {
        if(Config.DEBUG)
            _log.info("enviando janela de mensagem de erro... \n");
        JOptionPane.showMessageDialog(null, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Lista todos os arquivos de um diretorio
     * 
     * @param path
     * @param extension
     * @return files
     */
    public File[] listDirFiles(String path, final String extension)
    {
        File F = new File(path);
        File[] files = F.listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File pathname)
            {
                return pathname.getName().toLowerCase().endsWith(extension);
            }

        });

        return files;
    }

    /**
     * Lista as Pastas
     *
     * @param path
     * @return files
     */
    public File[] listFolders(String path)
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
                           + sdf.format(new Date()) + ": \n" + "\n"
                           + "|========= Memoria Livre =========" + "\n"
                           + "|    |= Total:" + df2.format(max) + "\n"
                           + "|    |= Memoria Total:" + df2.format(allocated)
                           + df.format(allocated / max * 100) + "\n"
                           + "|    |= Memoria Nao Alocada" + df2.format(nonAllocated)
                           + df.format(nonAllocated / max * 100) + "\n" + "\n"
                           + " =========  Memoria Alocada ========== \n"
                           + "|    |= Total:" + df2.format(allocated) + "\n"
                           + "|    |= Memoria Usada:" + df2.format(used)
                           + df.format(used / max * 100) + "\n"
                           + "|    |= Memoria Cacheada:" + df2.format(cached)
                           + df.format(cached / max * 100) + "\n"
                           + "|    |= Memoria Disponivel:" + df2.format(useable)
                           + df.format(useable / max * 100));
    }

}
