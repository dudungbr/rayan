package com.siscomercio.utilities;

import java.sql.SQLException;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import com.siscomercio.Config;
import com.siscomercio.DatabaseFactory;
import com.siscomercio.managers.DatabaseManager;
import com.siscomercio.managers.SoundManager;

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
    private static final Logger _log = Logger.getLogger(SystemUtil.class.getName());

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
   SoundManager.playSound("info.wav");
        JOptionPane.showMessageDialog(null, "<html><font color =black size=4 face = Times new Roman ><b> "+msg+ "</b></font></html>", "Informacao", JOptionPane.INFORMATION_MESSAGE);

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
        SoundManager.playSound("error.wav");
        JOptionPane.showMessageDialog(null, "<html><font color =black size=4 face = Times new Roman ><b> "+msg+ "</b></font></html>", "Erro", JOptionPane.ERROR_MESSAGE);
    }

}
