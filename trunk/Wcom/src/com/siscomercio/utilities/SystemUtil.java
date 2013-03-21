package com.siscomercio.utilities;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import com.siscomercio.Config;
import com.siscomercio.DatabaseFactory;
import com.siscomercio.controller.managers.DatabaseManager;
import com.siscomercio.tables.StringTable;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.URI;

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
       /* SystemUtil.showMsg(Config.BUILD_AUTHOR.substring(1, 30) + "<br>"
                           + Config.BUILD_NUM.substring(1, 14) + "<br>"
                           + Config.BUILD_DATE.substring(1, 52) + "<br>"
                           + Config.BUILD_URL.substring(1, 75) + "<br>"
                           + "Versao do Sistema " + Config.SYSTEM_VERSION + "<br>", true);
*/
    }

    /**
     *
     * @return the Site of Config File
     */
    public static String getSite()
    {
        return Config.getSite();
    }

    /**
     *
     * @return the System Version
     */
    public static String getVersion()
    {
        return null;
    //    return String.valueOf(Config.SYSTEM_VERSION);
    }

    /**
     * Prints all Database Information
     * @throws SQLException
     */
    public static void printDbInfo() throws SQLException
    {
        showMsg("<br> Provedor do Banco: " + DatabaseFactory.getInstance().getProviderType() + "<br>"
                + "Status do Banco: " + DatabaseManager.getConnectionStatus() + "<br>"
                + "Conexoes Ativas: " + String.valueOf(DatabaseFactory.getInstance().getBusyConnectionCount()) + "<br>"
                + "Conexoes Criadas : " + String.valueOf(DatabaseFactory.getInstance().getIdleConnectionCount() + "<br><br>"), true);
    }

    /**
     * Print Processor Info
     */
    public static void printCpuInfo()
    {
        showMsg("Processadores: " + Runtime.getRuntime().availableProcessors() + "<br>"
                + "Arquitetura " + System.getenv("PROCESSOR_IDENTIFIER"), true);
    }

    /**
     * Print O.S Info
     */
    public static void printOSInfo()
    {
        showMsg("Sistema Operacional: " + System.getProperty("os.name") + "<br>"
                + "Build: " + System.getProperty("os.version") + "<br>"
                + "Arquitetura: " + System.getProperty("os.arch"), true);
    }

    /**
     * Printi all java info
     */
    public static void printJvmInfo()
    {
        showMsg("<br> == Virtual Machine Information (JVM) == <br>"
                + "Name: " + System.getProperty("java.vm.name") + "<br>"
                + "JRE Directory:  " + System.getProperty("java.home") + "<br>"
                + "Version:  " + System.getProperty("java.vm.version") + "<br>"
                + "Vendor:  " + System.getProperty("java.vm.vendor") + "<br>"
                + "Info:  " + System.getProperty("java.vm.info") + "<br><br>"
                + "== Java Platform Information == <br>"
                + "Name:  " + System.getProperty("java.runtime.name") + "<br>"
                + "Version: " + System.getProperty("java.version") + "<br>"
                + "Java Class Version: " + System.getProperty("java.class.version") + "<br>", true);
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
     * @param playSound
     */
    public static void showMsg(String msg, Boolean playSound)
    {
        if(Config.isDebug())
        {
            _log.info("enviando janela de mensagem... \n");
        }
        if(playSound && Config.isEnableSound())
        {
            try
            {
                File file = new File(StringTable.SOUND_PATH.concat("info.wav"));
                URI caminho = file.toURI();
                AudioClip sound;
                sound = Applet.newAudioClip(caminho.toURL());
                sound.play();
            }
            catch(MalformedURLException ex)
            {
                Logger.getLogger(SystemUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        JOptionPane.showMessageDialog(null, "<html><font color =black size=4 face = Times new Roman ><b> " + msg + "</b></font></html>", "Informacao", JOptionPane.INFORMATION_MESSAGE);

    }

    /**
     * mostra mensagem de erro ao usuario
     *
     * @param msg
     * @param playSound
     */
    public static void showErrorMsg(String msg, Boolean playSound)
    {
        if(Config.isDebug())
        {
            _log.info("enviando janela de mensagem de erro... \n");
        }
        if(playSound && Config.isEnableSound())
        {
            try
            {
                File file = new File(StringTable.SOUND_PATH.concat("error.wav"));
                URI caminho = file.toURI();
                AudioClip sound;
                sound = Applet.newAudioClip(caminho.toURL());
                sound.play();
            }
            catch(MalformedURLException ex)
            {
                Logger.getLogger(SystemUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        JOptionPane.showMessageDialog(null, "<html><font color =black size=4 face = Times new Roman ><b> " + msg + "</b></font></html>", "Erro", JOptionPane.ERROR_MESSAGE);
    }

}
