package com.siscomercio.utilities;

import com.siscomercio.init.Config;
import com.siscomercio.controller.managers.ExceptionManager;
import com.siscomercio.controller.managers.SoundManager;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javolution.util.FastList;

/**
 * $Revision: 233 $ $Author: rayan_rpg@hotmail.com $ $Date: 2010-10-11 20:01:33
 * -0300 (seg, 11 out 2010) $
 *
 * @author William Menezes
 *
 */
public class Utilitarios
{
    private static final Logger _log = Logger.getLogger(Utilitarios.class.getName());

    //previne de instanciar a classe
    private Utilitarios()
    {
    }
    private static final long serialVersionUID = 1L;

    /**
     *
     * @param path
     * <p/>
     * @return
     */
    public static ImageIcon loadIcon(String path)
    {
        _log.log(Level.INFO, "Carregando Icone: {0}", path);
        File f = new File(path);
        ImageIcon image = null;
        if (!f.exists())
        {
            showErrorMessage("O Arquivo: " + path + "nao existe!");

        }
        else
        {
            image = new ImageIcon(path);

            //manter o loop enquanto a imagem estiver sendo carregada
            while (image.getImageLoadStatus() == MediaTracker.LOADING)
            {
                _log.info("Carregando Icone...");
            }
        }
        //neste momento a imagem já foi totalmente carregada
        return image;
    }

    /**
     *
     * @param image
     * @param url
     * <p/>
     * @return
     */
    public static ImageIcon loadIcon(ImageIcon image, String url)
    {
        _log.log(Level.INFO, "Carregando Icone: {0}", url);
        ImageIcon imageurl = null;
        try
        {
            imageurl = new ImageIcon(new URL(url));
        }
        catch (MalformedURLException ex)
        {
            Logger.getLogger(Utilitarios.class.getName()).log(Level.SEVERE, null, ex);
        }

        //manter o loop enquanto a imagem estiver sendo carregada
        while (image.getImageLoadStatus() == MediaTracker.LOADING)
        {
            _log.info("Carregando Icone...");
        }

        //neste momento a imagem já foi totalmente carregada

        return imageurl;
    }

    /**
     *
     * @param tarefa
     * @param intervalo
     */
    public static void agendaTarefa(TimerTask tarefa, int intervalo)
    {
        _log.info("agendando tarefa...");

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(tarefa, 0, Utilitarios.convertSecondsToMiliseconds(intervalo));


    }

    /**
     * // File file = new File(StringTable.SOUND_PATH + soundName); // if
     * (file.exists()) // { // try // { // URI caminho = file.toURI(); //
     * AudioClip som; // som = Applet.newAudioClip(caminho.toURL()); //
     * som.play(); // if (Config.isDebug()) // { // _log.log(Level.INFO,
     * "Tocando Arquivo: {0}\n", file); // } // return true; // } // catch
     * (MalformedURLException ex) // { // _log.severe(ex.getMessage()); //
     * ExceptionManager.ThrowException("Erro ", ex); // } // } // else // { //
     * String msg = "Arquivo de Som: " + soundName + " nao Encontrado !"; //
     * _log.severe(msg); // Utilitarios.showErrorMessage(msg); // } //
     *
     *
     * @param label
     * <p/>
     * @return
     */
//    public static void agendaTarefa()
//    {
//            //Data do dia corrente
//    Calendar dataIni = new GregorianCalendar();
//
//    //Seta a data inicial para o dia seguinte, ou seja, a próxima 00hs
//    dataIni.add(Calendar.DAY_OF_MONTH, 1);//1 dia após a data corrente
//
//    //Seta a hora = 00:00 (Meia noite)
//    dataIni.set(Calendar.HOUR_OF_DAY, 0);
//    dataIni.set(Calendar.MINUTE, 0);
//    dataIni.set(Calendar.SECOND, 0);
//
//    //Instancia o timer
//    Timer timer= new Timer();
//
//    //Intervalo para executar a classe novamente
//    //86400 = qtd segundos em 24hs
//    long periodo = 86400*1000;
//
//    //Agenda a tarefa
//    timer.scheduleAtFixedRate(new MinhaClasseTimerTask(), dataIni, periodo);
//    }
    public static int createQuestionPopUp(JLabel label)
    {
        SoundManager.getInstance().playSound(Config.getQuestionSound());
        return JOptionPane.showConfirmDialog(null, label, "Confirmar Requisicao", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    /**
     *
     * @param msg
     * <p/>
     * @return
     */
    public static int createQuestionPopUp(String msg)
    {
        _log.info("criando Janela de Confirmacao");
        SoundManager.getInstance().playSound(Config.getQuestionSound());
        return JOptionPane.showConfirmDialog(null,
                                             msg,
                                             "Confirmar Solicitção",
                                             JOptionPane.YES_NO_OPTION,
                                             JOptionPane.QUESTION_MESSAGE);
    }

    /**
     *
     * @param msg
     * <p/>
     * @return
     */
    public static int createWarningPopUp(
            String msg)
    {

        _log.info("criando Janela de Aviso");
        SoundManager.getInstance().playSound(Config.getQuestionSound());
        return JOptionPane.showConfirmDialog(null,
                                             msg,
                                             "Aviso",
                                             JOptionPane.OK_CANCEL_OPTION,
                                             JOptionPane.WARNING_MESSAGE);
    }

    /**
     *
     * @param lb
     * <p/>
     * @return
     */
    public static int createWarningPopUp(
            JLabel lb)
    {

        _log.info("criando Janela de Aviso");
        SoundManager.getInstance().playSound(Config.getQuestionSound());
        return JOptionPane.showConfirmDialog(null,
                                             lb,
                                             "Aviso",
                                             JOptionPane.YES_NO_OPTION,
                                             JOptionPane.WARNING_MESSAGE);
    }

    /**
     *
     * @param objetoPai
     * @param msg
     * @param extraString
     * @param posMsg
     * @param title
     * @param opcoes
     * @param icone
     * <p/>
     * @return
     */
    public int createQuestionPopUp(Component objetoPai,
                                   String msg,
                                   String extraString,
                                   String posMsg,
                                   String title,
                                   String[] opcoes,
                                   ImageIcon icone)
    {
        SoundManager.getInstance().playSound("question.wav");
        int option = JOptionPane.showOptionDialog(objetoPai,
                                                  msg,
                                                  title,
                                                  JOptionPane.OK_CANCEL_OPTION,
                                                  JOptionPane.QUESTION_MESSAGE,
                                                  icone,
                                                  opcoes,
                                                  opcoes[1]);

        return option;

    }

    /**
     *
     * @param p
     */
    public static void resetarFrame(JPanel p)
    {
        for (Component componente : p.getComponents())
        {
            if (componente instanceof JTextField)
            {
                ((JTextField) componente).setText("");
            }
            else if (componente instanceof JComboBox)
            {
                ((JComboBox) componente).setSelectedItem("Selecione");
            }
            else if (componente instanceof JSpinner)
            {
                ((JSpinner) componente).getModel().setValue(0);
            }
            //FIXME: nao funciona. com jtextarea
//            else if (componente instanceof JTextArea)
//            {
//                ((JTextArea) componente).setText("");
//            }
        }
    }

    /**
     *
     * @param p
     */
    public static void resetarFrame(Container p)
    {
        for (Component componente : p.getComponents())
        {
            if (componente instanceof JTextField)
            {
                ((JTextField) componente).setText("");
            }
            else if (componente instanceof JComboBox)
            {
                ((JComboBox) componente).setSelectedItem("Selecione");
            }
            else if (componente instanceof JSpinner)
            {
                ((JSpinner) componente).getModel().setValue(0);
            }
            //FIXME: nao funciona...
//            if (componente instanceof JTextArea)
//            {
//                ((JTextArea) componente).setText("");
//            }
        }
    }

    /**
     * Checa se um Processo esta em Execução.
     *
     * @param namePart
     * <p/>
     * @return
     */
    public static Boolean checkIfProcessIsRunning(String namePart)
    {

        List<String> processes = listRunningProcesses();
        String result = "";

        // display the result
        Iterator<String> it = processes.iterator();

        int i = 0;

        while (it.hasNext())
        {
            result += it.next();
            i++;
        }

        if (!result.contains(namePart))
        {
            return false;
        }


        return true;

    }

    /**
     * Retorna Todos os Processos em Execucao
     *
     * @return processes
     */
    private static List<String> listRunningProcesses()
    {
        List<String> processes = new ArrayList<>();
        try
        {
            String line;
            Process p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
            try (BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream())))
            {
                while ((line = input.readLine()) != null)
                {
                    if (!line.trim().equals(""))
                    {
                        // keep only the process name
                        line = line.substring(1);
                        processes.add(line.substring(0, line.indexOf("\"", 1)) + "\n");
                    }

                }
            }


        }
        catch (Exception ex)
        {
            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Erro ", ex);
        }
        return processes;

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

        showInfoMessage("Relatorio Gerado as " + "\n"
                + sdf.format(new Date()) + "\n"
                + "|========= Memoria Livre =========" + "\n"
                + "|    |= Total:" + df2.format(max) + "\n"
                + "|    |= Memoria Total:" + df2.format(allocated)
                + df.format(allocated / max * 100) + "\n"
                + "|    |= Memoria Nao Alocada" + df2.format(nonAllocated)
                + df.format(nonAllocated / max * 100) + "\n" + "\n"
                + " =========  Memoria Alocada ========== " + "\n"
                + "|    |= Total:" + df2.format(allocated) + "\n"
                + "|    |= Memoria Usada:" + df2.format(used)
                + df.format(used / max * 100) + "\n"
                + "|    |= Memoria Cacheada:" + df2.format(cached)
                + df.format(cached / max * 100) + "\n"
                + "|    |= Memoria Disponivel:" + df2.format(useable)
                + df.format(useable / max * 100));
    }

    /**
     *
     * @return
     */
    public static String getData()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date();
        return dateFormat.format(data);
    }

    /**
     *
     * @param data
     * @param mascara
     * <p/>
     * @return
     */
    public static String retornaDataComoString(String data, String mascara)
    {
        Date date = null;
        try
        {
            date = new SimpleDateFormat(mascara).parse(data);
        }
        catch (ParseException ex)
        {
            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Erro ", ex);
        }

        return date.toString();
    }

    /**
     *
     * @return
     */
    public static String getHora()
    {
        Date date = new Date();
        DateFormat formato = new SimpleDateFormat("HH:mm:ss");
        String formattedHour = formato.format(date);


        return formattedHour;

    }

    /**
     * Print Processor Info
     */
    public static void printCpuInfo()
    {
        showInfoMessage("Processadores: " + Runtime.getRuntime().availableProcessors() + "\n"
                + "Arquitetura " + System.getenv("PROCESSOR_IDENTIFIER"));
    }

    /**
     * Print O.S Info
     */
    public static void printOSInfo()
    {
        showInfoMessage("Sistema Operacional: " + System.getProperty("os.name") + "\n"
                + "Build: " + System.getProperty("os.version") + "\n"
                + "Arquitetura: " + System.getProperty("os.arch"));
    }

    /**
     * Printi all java info
     */
    public static void printJvmInfo()
    {
        showInfoMessage(" == Virtual Machine Information (JVM) == " + "\n"
                + "Name: " + System.getProperty("java.vm.name") + "\n"
                + "JRE Directory:  " + System.getProperty("java.home") + "\n"
                + "Version:  " + System.getProperty("java.vm.version") + "\n"
                + "Vendor:  " + System.getProperty("java.vm.vendor") + "\n"
                + "Info:  " + System.getProperty("java.vm.info") + "\n\n"
                + "== Java Platform Information == _" + "\n"
                + "Name:  " + System.getProperty("java.runtime.name") + "\n"
                + "Version: " + System.getProperty("java.version") + "\n"
                + "Java Class Version: " + System.getProperty("java.class.version") + "\n");
    }

    /**
     * Converte milisegundos em segundos
     *
     * @param milisecondsToConvert
     * <p/>
     * @return seconds
     */
    public static int convertMilisecondsToSeconds(long milisecondsToConvert)
    {
        return (int) milisecondsToConvert / 1000;
    }

    /**
     * Converte segundos em milisegundos
     *
     * @param secondsToConvert
     * <p/>
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
     * <p/>
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
     * <p/>
     * @return seconds
     */
    public int convertMinutesToSeconds(int minutesToConvert)
    {
        return minutesToConvert * 60;
    }

    /**
     * converte uma percentagem por multiplo
     *
     * @param multiplerX100
     * <p/>
     * @return 100-multiplerX100;
     *
     */
    public static double convertPercentageByMultipler(double multiplerX100)
    {
        return 100 - multiplerX100;
    }

    /**
     * calcula percentagem
     *
     * @param number
     * @param percentage
     * <p/>
     * @return thepercentage
     */
    public static double calculatePercentage(double number, double percentage)
    {
        double values = number * percentage;
        double tmp = values / 100;

        return tmp;
    }

    /**
     *
     * @param msg
     */
    public static void showInfoMessage(String msg)
    {
        try
        {
            Thread t1 = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    if (Config.isEnableSound())
                    {
                        SoundManager.getInstance().playSound("info.wav");
                    }
                }
            });
            t1.start();


            JOptionPane.showMessageDialog(null, msg, "Informação", JOptionPane.INFORMATION_MESSAGE);

        }
        catch (Exception ex)
        {
            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Erro ", ex);
        }
    }

    /**
     *
     * @param msg
     */
    public static void showErrorMessage(String msg)
    {
        try
        {
            Thread t1 = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    if (Config.isEnableSound())
                    {
                        SoundManager.getInstance().playSound("error.wav");
                    }
                }
            });
            t1.start();

            JOptionPane.showMessageDialog(null, msg, "Erro", JOptionPane.ERROR_MESSAGE);


        }
        catch (Exception ex)
        {
            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Erro ", ex);
        }



    }

    /**
     *
     * @param msg
     */
    public static void showWarningMessage(String msg)
    {
        SoundManager.getInstance().playSound("warning.wav");
        JOptionPane.showMessageDialog(null, msg, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    /**
     *
     * @param msg
     */
    public static void showQuestionMessage(String msg)
    {
        try
        {
            Thread t1 = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    if (Config.isEnableSound())
                    {
                        SoundManager.getInstance().playSound("question.wav");
                    }
                }
            });
            t1.start();

            JOptionPane.showMessageDialog(null, msg, "Confirmação", JOptionPane.QUESTION_MESSAGE);

        }
        catch (Exception ex)
        {
            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Erro ", ex);
        }
    }

    /**
     *
     * @param msg
     */
    public static void showPlainMessage(String msg)
    {
        try
        {
            Thread t1 = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    if (Config.isEnableSound())
                    {
                        SoundManager.getInstance().playSound("info.wav");
                    }
                }
            });
            t1.start();

            JOptionPane.showMessageDialog(null, msg, "Informação", JOptionPane.PLAIN_MESSAGE);

        }
        catch (Exception ex)
        {
            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Erro ", ex);
        }
    }

    /**
     * Lista todos os arquivos de um diretorio
     *
     * @param path
     * @param extension
     * <p/>
     * @return o nome de todos os arquivos da pasta e extensao especificada
     */
    public static String listDirFiles(String path, final String extension)
    {
        File pasta = new File(path);
        List<String> arquivos = new FastList<>();

        for (File f : pasta.listFiles())
        {
            if (f != null && f.getName().endsWith(extension))
            {
                arquivos.add(f.getName());
            }
        }
        return arquivos.toString();
    }

    /**
     *
     * @param path
     * @param extension
     * <p/>
     * @return
     */
    public File[] listFiles(String path, final String extension)
    {
        File dir = new File(path);

        // filtro pela extensão procurada
        FileFilter filter = null;

        if (extension != null)
        {
            filter = new FileFilter()
            {
                @Override
                public boolean accept(File pathname)
                {
                    return pathname.getAbsolutePath().endsWith(extension);
                }
            };
        }

        // lista os arquivos que correspondem ao match
        return dir.listFiles(filter);
    }

    /**
     * Lista as Pastas
     *
     * @param path
     * @param extension
     * <p/>
     * @return files
     */
    public static int countFiles(String path, final String extension)
    {
        File pasta = new File(path);//  diretório
        List<File> arquivos = new FastList<>(); // lista de arquivos a ser criada

        for (File arq : pasta.listFiles())
        {
            //condição para pegar só os arquivos, e nao diretórios
            if (arq.isFile() && arq.getName().endsWith(extension))
            {
                if (arq != null)
                {
                    arquivos.add(arq);
                }
                // _log.info("Arquivo " +
                // ++i;// + ": " + arq.getName() + "  Última modificação: " + new Date(arq.lastModified()));
                //aqui vc poderia formar uma lista com os arquivos
            }
        }
        if (Config.isDebug())
        {
            _log.log(Level.INFO, "Contando Arquivos do Diretorio SQL, Total: {0} Arquivos. \n", arquivos.size());
        }

        return arquivos.size();
    }

    /**
     * Lista as Pastas
     *
     * @param path
     * <p/>
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
     * Nome do Suporte Técnico
     *
     * @return "Contacte o Suporte Técnico."
     */
    private static String getSuport()
    {
        return "Contacte o Suporte Técnico.";
    }

    /**
     * Pega um processo
     *
     * @param pName
     * <p/>
     * @return pName
     */
    private static String getProcess(String pName)
    {
        if (pName.startsWith("mysql"))
        {
            pName = "&#160 &#160 &#160 &#160 &#160  &#160 &#160 &#160 &#160 mysqld.exe<br>"
                    + "&#160 &#160 &#160 &#160 &#160  &#160 &#160 &#160 &#160 mysqld-nt.exe<br>";

        }
        else
        {
            throw new UnsupportedOperationException("valor nao suportado");
        }

        return pName;
    }

    /**
     * Finaliza um Processo em Execucao na Máquina
     *
     * @param processToKill
     * <p/>
     * @return false
     */
    public static boolean killProcess(String processToKill)
    {
        try
        {
            String line;
            Process p = Runtime.getRuntime().exec("tasklist.exe");
            try (BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream())))
            {
                while ((line = input.readLine()) != null)
                {
                    if (!line.trim().equals(""))
                    {
                        if (line.substring(1, line.indexOf("\"", 1)).equalsIgnoreCase(processToKill))
                        {
                            Runtime.getRuntime().exec("taskkill /F /IM " + line.substring(1, line.indexOf("\"", 1)));
                            _log.log(Level.INFO, "Matando Processo: {0}", processToKill);
                            return true;
                        }
                    }
                }
            }

        }
        catch (Exception ex)
        {
            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Erro ", ex);
        }
        return false;
    }

    /**
     * Pega o Nome dessa Maquina
     *
     * @return the name of this Machine
     */
    public static String getPcName()
    {
        String pcName = null;
        try
        {
            pcName = InetAddress.getLocalHost().getHostName();

        }
        catch (Exception ex)
        {

            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Error While Trying to get Pc Name", ex);
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
        while (it.hasNext())
        {
            result += it.next();
            i++;
        }

        if (Config.isDebug())
        {
            _log.log(Level.INFO, "Running processes : \n{0}", result);
            _log.log(Level.INFO, "Total de Processos: {0}", processes.size());
        }
    }

    /**
     * @param HttpAddr
     * <p/>
     * @return
     */
    //checks for connection to the internet through dummy request
    public static boolean checkInternetConnection(String HttpAddr)
    {
        _log.info("verificando conexao com a internet");
        try
        {

            //make a URL to a known source
            URL url = new URL(HttpAddr);

            //open a connection to that source
            HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
            /*
             * in my instance when testing with one address the method would
             * return true, but take 10 seconds or longer to get a response. In
             * this case the server was reachable, but not for any useful
             * purposes since the connection was so slow. This occurs because
             * the default timeout for HttpURLConnection is 0, or infinite.
             *
             * For this reason I'd recommend you do the checking off the UI
             * thread, and add
             */
            urlConnect.setConnectTimeout(Utilitarios.convertSecondsToMiliseconds(10));


            int responseCode = -1;
            responseCode = urlConnect.getResponseCode();

            if (responseCode != -1)
            {
                _log.info("internet ok");
                //desconecta, ja foi verificada a conectividade
                urlConnect.disconnect();
                return true;
            }


        }
        catch (UnknownHostException e)
        {
            _log.info(e.getMessage());

            return false;
        }
        catch (IOException e)
        {
            _log.info(e.getMessage());

            return false;
        }
        return false;
    }

    /**
     *
     * @param jarPath
     */
    public static void lauchJar(String jarPath)
    {
        _log.log(Level.INFO, "executando jar: {0}", jarPath);
        File jar = new File(jarPath);

        if (jar.exists())
        {

            try
            {
                Runtime.getRuntime().exec("cmd /c start /B " + jarPath); //depois do parâmetro /B vc deve digitar o caminho do .jar que será executado
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "O Arquivo: " + jar.getPath() + " nao existe!");
        }
    }

    /**
     * calcula o centro da tela, para que a janela fique centralizada.
     *
     * @param alturaFrame
     * @param larguraFrame
     * <p/>
     * @return
     */
    public Point getScreenCenter(int alturaFrame, int larguraFrame)
    {

        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        Point center = new Point((tela.width - larguraFrame) / 2, (tela.height - alturaFrame) / 2);
        return center;
    }

    /**
     *
     * @param janela
     * <p/>
     * @return
     */
    public static boolean criaJanelaConfirmacao(JFrame janela)
    {
        int option = Utilitarios.createQuestionPopUp("Deseja Realmente Cancelar?");

        switch (option)
        {
            case JOptionPane.OK_OPTION:
            {
                janela.dispose();
                return true;
            }
            default:
            {
                break;
            }
        }
        return false;
    }

    /**
     *
     * @param f
     */
    public static void zeraArquivo(File f)
    {
        if (!f.exists())
        {
            showErrorMessage("O Arquivo: " + f.getPath() + " Nao existe!");
        }
        else
        {
            _log.log(Level.INFO, "Zerando Conte\u00fado do Arquivo: {0}", f.getPath());
            try (RandomAccessFile raf = new RandomAccessFile(f, "rw"))
            {
                raf.setLength(0);
            }
            catch (IOException ex)
            {
                Logger.getLogger(FrameLog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
