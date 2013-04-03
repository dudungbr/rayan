package com.siscomercio.init;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import com.jtattoo.plaf.aero.AeroLookAndFeel;
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
    StringTable strings = StringTable.getInstance();
    private static Boolean enableLog;
    private static boolean developer;
    private static boolean debug;
    private static boolean _loaded;
    /**
     * Database
     */
    private String databaseLogin;
    private String databasePassword;
    private String database;
    private String host;
    private String databaseDriver;
    //levels
    private static int adminLevel;
    private static int operadorLevel;
    private static int gerenteLevel;
    private static int tecnicoLevel;
    //personalizacao
    private String site;
    private static double systemVersion;
    private String empresa;
    private String databaseUrl;
    private String databasePort;
    private String logo;
    private String logPath;
    /**
     *
     * Sons
     */
    private static boolean enableSound;

    /**
     *
     * @param enableSound
     */
    public void setEnableSound(boolean enableSound)
    {
        Config.enableSound = enableSound;
    }
    private String loginSound;
    private String questionSound;
    private String welcomeSound;
    private String exitSound;
    private String preExitSound;
    private String preLoginSound;
    private String unimplementedSound;
    private String preRestartSound;
    private String restartSound;
    private String infoSound;
    private String logoffSound;
    //rede
    private static int serverPort;
    private static int clientPort;
    private String serverIp;
    private static int testPort;
    private static int testWait;
    private String updateServer;
    private static int tempoOcioso;
    private static int THREAD_DEFAULT_SLEEP = 1000;

    /**
     *
     */
    public void loadPreferencies()
    {
        if (debug)
        {
            _log.info("Carregando Preferencias ...");
        }

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
        if (debug)
        {
            _log.info("setando Tema Visual");
        }
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
        if (debug)
        {
            _log.info("carregando arquivo de versao local");
        }
        File localFile = new File("./update/local.properties");//"C:\\Sat\\update\\local.properties");

        if (localFile.exists())
        {
            if (debug)
            {
                _log.info("alterando versao local...");
            }


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
            Utilitarios.getInstance().showErrorMessage("o Arquivo" + localFile.getPath() + " Nao Existe!");

        }

    }

    /**
     * Carrega Todas as Propriedades do config.properties
     */
    private void load()
    {

        if (debug)
        {
            _log.info("Config: Carregando o Arquuivo de Configuração...");
        }
        try
        {

            File arquivoConfig = new File(StringTable.CONFIG_FILE);
            if (!arquivoConfig.exists())
            {
                Utilitarios.getInstance().showErrorMessage("o arquivo de configuracao  " + arquivoConfig.getName() + " nao existe!");
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
                    Utilitarios.getInstance().showErrorMessage("Erro na Configuracao as Portas do Programa.\n");
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
    public String getDatabasePassword()
    {
        return databasePassword;
    }

    /**
     *
     * @return
     */
    public String getDatabaseLogin()
    {
        return databaseLogin;
    }

    /**
     *
     * @return
     */
    public String getDatabaseDriver()
    {
        return databaseDriver;
    }

    /**
     *
     * @return
     */
    public String getHost()
    {
        return host;
    }

    /**
     *
     * @return
     */
    public String getDatabase()
    {
        return database;
    }

    /**
     *
     * @return
     */
    public boolean isEnableSound()
    {
        return enableSound;
    }

    /**
     *
     * @return
     */
    private int getClientPort()
    {
        return clientPort;
    }

    /**
     *
     * @return
     */
    public String getServerIp()
    {
        return serverIp;
    }

    /**
     *
     * @return
     */
    public boolean isEnableLog()
    {
        return enableLog;
    }

    /**
     *
     * @return
     */
    public int getServerPort()
    {
        return serverPort;
    }

    /**
     *
     * @return
     */
    public boolean isDebug()
    {
        return debug;
    }

    public String getUpdateServer()
    {
        return updateServer;
    }

    /**
     *
     * @return
     */
    public boolean isLoaded()
    {
        return _loaded;
    }

    /**
     *
     * @return
     */
    public String getSite()
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
    private int getTestPort()
    {
        return testPort;
    }

    /**
     *
     * @return
     */
    private int getTestWait()
    {
        return testWait;
    }

    /**
     *
     * @return
     */
    public boolean isDeveloper()
    {
        return developer;
    }

    /**
     *
     * @return
     */
    private int getAdminLevel()
    {
        return adminLevel;
    }

    /**
     *
     * @return
     */
    private int getGerenteLevel()
    {
        return gerenteLevel;
    }

    /**
     *
     * @return
     */
    private int getOperadorLevel()
    {
        return operadorLevel;
    }

    /**
     *
     * @return
     */
    private int getTecnicoLevel()
    {
        return tecnicoLevel;
    }

    /**
     *
     * @return
     */
    public String getEmpresa()
    {
        return empresa;
    }

    /**
     *
     * @return
     */
    public String getDatabasePort()
    {
        return databasePort;
    }

    /**
     *
     * @return
     */
    public String getDatabaseUrl()
    {
        return databaseUrl;
    }

    /**
     *
     * @return
     */
    public String getExitSound()
    {
        return exitSound;
    }

    /**
     *
     * @return
     */
    public String getPreExitSound()
    {
        return preExitSound;
    }

    /**
     *
     * @return
     */
    public String getQuestionSound()
    {
        return questionSound;
    }

    /**
     *
     * @return
     */
    public String getWelcomeSound()
    {
        return welcomeSound;
    }

    /**
     *
     * @return
     */
    public String getLogo()
    {
        return logo;
    }

    /**
     *
     * @return
     */
    public String getLoginSound()
    {
        return loginSound;
    }

    /**
     *
     * @return
     */
    public String getPreLoginSound()
    {
        return preLoginSound;
    }

    /**
     *
     * @return
     */
    public String getPreRestartSound()
    {
        return preRestartSound;
    }

    /**
     *
     * @return
     */
    public String getRestartSound()
    {
        return restartSound;
    }

    /**
     *
     * @return
     */
    public String getUnimplementedSound()
    {
        return unimplementedSound;
    }

    /**
     *
     * @return
     */
    public String getInfoSound()
    {
        return infoSound;
    }

    /**
     *
     * @return
     */
    public String getLogoffSound()
    {
        return logoffSound;
    }

    /**
     *
     * @return
     */
    private int getTempoOcioso()
    {
        return tempoOcioso;
    }

    /**
     * @return the THREAD_DEFAULT_SLEEP
     */
    private int getTHREAD_DEFAULT_SLEEP()
    {
        return THREAD_DEFAULT_SLEEP;
    }

    /**
     * @param aTHREAD_DEFAULT_SLEEP the THREAD_DEFAULT_SLEEP to set
     */
    public void setTHREAD_DEFAULT_SLEEP(int aTHREAD_DEFAULT_SLEEP)
    {
        THREAD_DEFAULT_SLEEP = aTHREAD_DEFAULT_SLEEP;
    }

    /**
     *
     * @return
     */
    public String getLogPath()
    {
        return logPath;
    }

    private Config()
    {
        load();
    }

    /**
     *
     * @return
     */
    public static Config getInstance()
    {
        return SingletonHolder._instance;
    }
    private static class SingletonHolder
    {
        protected static final Config _instance = new Config();
    }
}
