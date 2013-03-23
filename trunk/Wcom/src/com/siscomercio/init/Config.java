package com.siscomercio.init;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import com.jtattoo.plaf.aero.AeroLookAndFeel;
import com.siscomercio.controller.managers.AppManager;
import com.siscomercio.controller.managers.ExceptionManager;
import com.siscomercio.controller.managers.PropertiesManager;
import com.siscomercio.standards.StringTable;
import com.siscomercio.utilities.Utilitarios;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author William
 */
public class Config
{
    private static final Logger _log = Logger.getLogger(Config.class.getName());
    private static Boolean enableLog;
    private static boolean _loaded;
    /**
     * Database
     */
    private static String databaseLogin;
    private static String databasePassword;
    private static String database;
    private static String host;
    private static String databaseDriver;
    //levels
    private static int adminLevel;
    private static int operadorLevel;
    private static int gerenteLevel;
    private static int tecnicoLevel;
    //personalizacao
    private static String site;
    private static double systemVersion;
    private static String empresa;
    private static String databaseUrl;
    private static String databasePort;
    private static String logo;
    private static String logPath;
    /**
     *
     * Sons
     */
    private static boolean enableSound;

    /**
     *
     * @param enableSound
     */
    public static void setEnableSound(boolean enableSound)
    {
        Config.enableSound = enableSound;
    }
    private static String loginSound;
    private static String questionSound;
    private static String welcomeSound;
    private static String exitSound;
    private static String preExitSound;
    private static String preLoginSound;
    private static String unimplementedSound;
    private static String preRestartSound;
    private static String restartSound;
    private static String infoSound;
    private static String logoffSound;
    //developer
    private static boolean debug;
    private static boolean developer;
    //rede
    private static int serverPort;
    private static int clientPort;
    private static String serverIp;
    private static int testPort;
    private static int testWait;
    private static String updateServer;
    private static int tempoOcioso;
    private static int THREAD_DEFAULT_SLEEP = 1000;

    /**
     *
     */
    public static void loadPreferencies()
    {
        _log.info("Carregando Preferencias ...");

        ///JOption pane
        UIManager.put("OptionPane.font", new Font("Tahoma", Font.BOLD, 14));
        UIManager.put("OptionPane.foreground", Color.red);
        UIManager.put("OptionPane.messageDialogTitle", "Mensagem");
        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
        UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "Não");

        //File Chooser
        UIManager.put("OptionPane.titleText", "Selecione um opção");
        UIManager.put("FileChooser.lookInLabelMnemonic", new Integer('E'));
        UIManager.put("FileChooser.lookInLabelText", "Examinar");
        UIManager.put("FileChooser.saveInLabelText", "Salvar em");
        UIManager.put("FileChooser.fileNameLabelMnemonic", new Integer('N'));  // N
        UIManager.put("FileChooser.fileNameLabelText", "Nome do arquivo");
        UIManager.put("FileChooser.filesOfTypeLabelMnemonic", new Integer('T'));  // T
        UIManager.put("FileChooser.filesOfTypeLabelText", "Tipo");
        UIManager.put("FileChooser.upFolderToolTipText", "Um nível acima");
        UIManager.put("FileChooser.upFolderAccessibleName", "Um nível acima");
        UIManager.put("FileChooser.homeFolderToolTipText", "Desktop");
        UIManager.put("FileChooser.homeFolderAccessibleName", "Desktop");
        UIManager.put("FileChooser.newFolderToolTipText", "Criar nova pasta");
        UIManager.put("FileChooser.newFolderAccessibleName", "Criar nova pasta");
        UIManager.put("FileChooser.listViewButtonToolTipText", "Lista");
        UIManager.put("FileChooser.listViewButtonAccessibleName", "Lista");
        UIManager.put("FileChooser.detailsViewButtonToolTipText", "Detalhes");
        UIManager.put("FileChooser.detailsViewButtonAccessibleName", "Detalhes");
        UIManager.put("FileChooser.fileNameHeaderText", "Nome");
        UIManager.put("FileChooser.fileSizeHeaderText", "Tamanho");
        UIManager.put("FileChooser.fileTypeHeaderText", "Tipo");
        UIManager.put("FileChooser.fileDateHeaderText", "Data");
        UIManager.put("FileChooser.fileAttrHeaderText", "Atributos");
        UIManager.put("FileChooser.openButtonToolTipText", "Abrir");
        UIManager.put("FileChooser.openButtonAccessibleName", "Abrir");
        UIManager.put("FileChooser.openButtonText", "Abrir");
        UIManager.put("FileChooser.cancelButtonText", "Cancelar");
        UIManager.put("FileChooser.cancelButtonToolTipText", "Cancela esta Janela");
        UIManager.put("FileChooser.cancelButtonAccessibleName", "Cancelar");
        //  setTema();
    }

    /**
     *
     * @param requesterClass
     */
    private static void setTema()
    {
        _log.info("setando Tema Visual");
        try
        {
            //AppManager.setTema(Boot.class.getSimpleName());
            //Define O Tema Visual e o Texto do Pop UP
            Properties props = new Properties();
            props.put("logoString", "SISCOM");
            props.put("licenseKey", "SISCOM");
            AeroLookAndFeel.setCurrentTheme(props);
            UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
            UIManager.setLookAndFeel(new AcrylLookAndFeel());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex)
        {
            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Erro ao Carregar Skin ", ex);
        }




    }

    private static void loadLocalVersion()
    {
        _log.info("carregando arquivo de versao local");
        File localFile = new File("./update/local.properties");//"C:\\Sat\\update\\local.properties");

        if (localFile.exists())
        {
            _log.info("alterando versao local...");


            try
            {
                InputStream is = new FileInputStream(localFile);
                PropertiesManager arquivo = new PropertiesManager();
                arquivo.load(is);
                systemVersion = Double.parseDouble(arquivo.getProperty("version", "0.0"));
            }
            catch (IOException ex)
            {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            Utilitarios.showErrorMessage("o Arquivo" + localFile.getPath() + " Nao Existe!");

        }

    }

    /**
     * Carrega Todas as Propriedades do config.properties
     */
    public static void load()
    {

        _log.info("Config: Carregando o Arquuivo de Configuração...");
        try
        {

            File arquivoConfig = new File(StringTable.getCONFIG_FILE());
            if (!arquivoConfig.exists())
            {
                Utilitarios.showErrorMessage("o arquivo de configuracao  " + arquivoConfig.getName() + " nao existe!");
                System.exit(0);//finaliza o sistema.
            }
            else
            {

                InputStream is = new FileInputStream(arquivoConfig);
                PropertiesManager arquivo = new PropertiesManager();
                arquivo.load(is);

                /**
                 * Configs
                 */
                empresa = arquivo.getProperty("empresa", "desconhecida");
                databaseLogin = arquivo.getProperty("login", "root");
                databasePassword = arquivo.getProperty("password", "root");
                databaseDriver = arquivo.getProperty("driver", "com.mysql.jdbc.Driver");
                databaseUrl = arquivo.getProperty("URL", "jdbc:mysql://");
                database = arquivo.getProperty("database", "sat");
                databasePort = arquivo.getProperty("databasePort", "3306");
                host = arquivo.getProperty("databaseHost", "localhost");
                serverPort = Integer.parseInt(arquivo.getProperty("serverPort", "7000"));
                clientPort = Integer.parseInt(arquivo.getProperty("clientPort", "7000"));
                adminLevel = Integer.parseInt(arquivo.getProperty("adminLvl", "500"));
                operadorLevel = Integer.parseInt(arquivo.getProperty("operadorLvl", "100"));
                gerenteLevel = Integer.parseInt(arquivo.getProperty("gerenteLvl", "250"));
                site = arquivo.getProperty("site", "www.sat.com.br");
                logo = arquivo.getProperty("logo", "logo.png");
                testPort = Integer.parseInt(arquivo.getProperty("TestPort", "80"));
                testWait = Integer.parseInt(arquivo.getProperty("TestWait", "2"));//2 seconds
                enableLog = Boolean.valueOf(arquivo.getProperty("log", "false"));
                // Configuracoes de Som
                enableSound = Boolean.valueOf(arquivo.getProperty("sound", "true "));
                exitSound = arquivo.getProperty("exit", "exitok.wav");
                preExitSound = arquivo.getProperty("preExit", "exit.wav");
                loginSound = arquivo.getProperty("loginSound", "loginok.wav");
                preLoginSound = arquivo.getProperty("preLogin", "login.wav");
                unimplementedSound = arquivo.getProperty("uniplemented", "implementar.wav");
                preRestartSound = arquivo.getProperty("preRestart", "restart.wav");
                restartSound = arquivo.getProperty("restartSound", "restartok.wav");
                welcomeSound = arquivo.getProperty("welcomeSound", "welcome.wav");
                questionSound = arquivo.getProperty("questionSound", "question.wav");
                infoSound = arquivo.getProperty("infoSound", "info.wav");
                logoffSound = arquivo.getProperty("logoffSound", "logoff.wav");
                //-------------------------------------------------------------------
                debug = Boolean.valueOf(arquivo.getProperty("debug", "true"));
                enableLog = Boolean.valueOf(arquivo.getProperty("log", "false"));

                developer = Boolean.valueOf(arquivo.getProperty("developer", "true"));
//levels
                adminLevel = Integer.parseInt(arquivo.getProperty("NivelAdmin", "500"));//2 seconds
                logPath = arquivo.getProperty("logPath", "com.siscomercio");
                gerenteLevel = Integer.parseInt(arquivo.getProperty("NivelGerente", "400"));//2 seconds
                tecnicoLevel = Integer.parseInt(arquivo.getProperty("NivelTecnico", "300"));//2 seconds
                operadorLevel = Integer.parseInt(arquivo.getProperty("NivelFuncionario", "200"));//2 seconds
                updateServer = arquivo.getProperty("updateServer", "74.125.159.82");
                tempoOcioso = Integer.parseInt(arquivo.getProperty("tempoOcioso", "60"));//60 seconds
                /**
                 * Port Checks
                 */
                if (serverPort <= 0 || serverPort > 65535)
                {
                    //   Utilitarios u = new Utilitarios();
                    Utilitarios.showErrorMessage("Erro na Configuracao as Portas do Programa.\n");
                    System.exit(0);
                }

                _loaded = true;
            }

        }
        catch (IOException | NumberFormatException e)
        {
            _log.severe(e.getMessage());
            ExceptionManager.ThrowException("Nao Foi Possivel Carregar o Arquivo:  de configuracao: ", e);
        }

        loadPreferencies();
        loadLocalVersion();


    }

    /**
     *
     * @return
     */
    public static String getDatabasePassword()
    {
        return databasePassword;
    }

    /**
     *
     * @return
     */
    public static String getDatabaseLogin()
    {
        return databaseLogin;
    }

    /**
     *
     * @return
     */
    public static String getDatabaseDriver()
    {
        return databaseDriver;
    }

    /**
     *
     * @return
     */
    public static String getHost()
    {
        return host;
    }

    /**
     *
     * @return
     */
    public static String getDatabase()
    {
        return database;
    }

    /**
     *
     * @return
     */
    public static boolean isEnableSound()
    {
        return enableSound;
    }

    /**
     *
     * @return
     */
    public static int getClientPort()
    {
        return clientPort;
    }

    /**
     *
     * @return
     */
    public static String getServerIp()
    {
        return serverIp;
    }

    /**
     *
     * @return
     */
    public static Boolean isEnableLog()
    {
        return enableLog;
    }

    /**
     *
     * @return
     */
    public static int getServerPort()
    {
        return serverPort;
    }

    /**
     *
     * @return
     */
    public static boolean isDebug()
    {
        return debug;
    }

//    public static String getUpdateServer()
//    {
//        return updateServer;
//    }
    /**
     *
     * @return
     */
    public static boolean isLoaded()
    {
        return _loaded;
    }

    /**
     *
     * @return
     */
    public static String getSite()
    {
        return site;
    }

    /**
     *
     * @return
     */
    public static double getSystemVersion()
    {
        return systemVersion;
    }

    /**
     *
     * @return
     */
    public static int getTestPort()
    {
        return testPort;
    }

    /**
     *
     * @return
     */
    public static int getTestWait()
    {
        return testWait;
    }

    /**
     *
     * @return
     */
    public static boolean isDeveloper()
    {
        return developer;
    }

    /**
     *
     * @return
     */
    public static int getAdminLevel()
    {
        return adminLevel;
    }

    /**
     *
     * @return
     */
    public static int getGerenteLevel()
    {
        return gerenteLevel;
    }

    /**
     *
     * @return
     */
    public static int getOperadorLevel()
    {
        return operadorLevel;
    }

    /**
     *
     * @return
     */
    public static int getTecnicoLevel()
    {
        return tecnicoLevel;
    }

    /**
     *
     * @return
     */
    public static String getEmpresa()
    {
        return empresa;
    }

    /**
     *
     * @return
     */
    public static String getDatabasePort()
    {
        return databasePort;
    }

    /**
     *
     * @return
     */
    public static String getDatabaseUrl()
    {
        return databaseUrl;
    }

    /**
     *
     * @return
     */
    public static String getExitSound()
    {
        return exitSound;
    }

    /**
     *
     * @return
     */
    public static String getPreExitSound()
    {
        return preExitSound;
    }

    /**
     *
     * @return
     */
    public static String getQuestionSound()
    {
        return questionSound;
    }

    /**
     *
     * @return
     */
    public static String getWelcomeSound()
    {
        return welcomeSound;
    }

    /**
     *
     * @return
     */
    public static String getLogo()
    {
        return logo;
    }

    /**
     *
     * @return
     */
    public static String getLoginSound()
    {
        return loginSound;
    }

    /**
     *
     * @return
     */
    public static String getPreLoginSound()
    {
        return preLoginSound;
    }

    /**
     *
     * @return
     */
    public static String getPreRestartSound()
    {
        return preRestartSound;
    }

    /**
     *
     * @return
     */
    public static String getRestartSound()
    {
        return restartSound;
    }

    /**
     *
     * @return
     */
    public static String getUnimplementedSound()
    {
        return unimplementedSound;
    }

    /**
     *
     * @return
     */
    public static String getInfoSound()
    {
        return infoSound;
    }

    /**
     *
     * @return
     */
    public static String getUpdateServer()
    {
        return updateServer;
    }

    /**
     *
     * @return
     */
    public static String getLogoffSound()
    {
        return logoffSound;
    }

    /**
     *
     * @return
     */
    public static int getTempoOcioso()
    {
        return tempoOcioso;
    }

    /**
     * @return the THREAD_DEFAULT_SLEEP
     */
    public static int getTHREAD_DEFAULT_SLEEP()
    {
        return THREAD_DEFAULT_SLEEP;
    }

    /**
     * @param aTHREAD_DEFAULT_SLEEP the THREAD_DEFAULT_SLEEP to set
     */
    public static void setTHREAD_DEFAULT_SLEEP(int aTHREAD_DEFAULT_SLEEP)
    {
        THREAD_DEFAULT_SLEEP = aTHREAD_DEFAULT_SLEEP;
    }

    /**
     *
     * @return
     */
    public static String getLogPath()
    {
        return logPath;
    }
}
