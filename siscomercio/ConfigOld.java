package com.siscomercio;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Logger;
import com.siscomercio.managers.PropertiesManager;
import com.siscomercio.utilities.SystemUtil;
import java.io.IOException;

/**
 *
 * $Revision$
 * $Date$
 * $Author$
 * $HeadURL$
 * 
 * @author William Menezes
 *
 */
public class ConfigOld
{
    private static final Logger _log = Logger.getLogger(Config.class.getName());

    /**
     * som tocado quando o sistema for finalizado
     */
    public static String QUESTION_SOUND;
    /**
     *
     */
    public static boolean LOG_DEBUG;
    /**
     * som tocado quando o sistema for finalizado
     */
    public static String EXIT_SOUND;
    /**
     * som de boas vindas
     */
    public static String WELCOME_SOUND;
    /**
     *  som tocado quando for solicitado o solicitado o shutdown
     */
    public static String PRE_EXIT_SOUND;
    /**
     * som tocado antes do login do usuario
     */
    public static String PRE_LOGIN_SOUND;
    /**
     * som tocado durante login
     */
    public static String LOGIN_SOUND;
    /**
     * som de funcoes nao implementadas
     */
    public static String UNIPLEMENTED_SOUND;
    /**
     * som de solicitacao de restart
     */
    public static String PRE_RESTART_SOUND;
    /**
     * ssom de restart
     */
    public static String RESTART_SOUND;
    /**
     * caminho do arquivo de configuracao
     */
    public static String path = "./config/config.properties";
    /**
     * site da empresa
     */
    public static String SITE;
    /**
     * logo da empresa
     */
    public static String LOGO;
    /**
     * nome da empresa
     */
    public static String EMPRESA;
    /**
     * build SVN
     */
    public static String BUILD_AUTHOR = "$Author$";
    /**
     * revisao SVN
     */
    public static String BUILD_NUM = "$Revision$";
    /**
     * Data de Compilacao
     */
    public static String BUILD_DATE = "$Date$";
    /**
     * Url SVN
     */
    public static String BUILD_URL = "$HeadURL$";
    /**
     * Database Login
     */
    public static String DATABASE_LOGIN;
    /**
     * host MySQL
     */
    public static String DATABASE_HOST;
    /**
     * porta Database
     */
    public static String DATABASE_PORT;
    /**
     *  senha Database
     */
    public static String DATABASE_PASSWORD;
    /**
     * Driver MySQL
     */
    public static String DATABASE_DRIVER;
    /**
     * URL Database
     */
    public static String DATABASE_URL;
    /**
     * Nome Database
     */
    public static String DATABASE;
    /**
     * Saida de Mensagens de Debug
     */
    public static boolean DEBUG;
    /**
     * habilita/ desabilita Som
     */
    public static boolean ENABLE_SOUND;
    /**
     * versao do sistema
     */
    public static double SYSTEM_VERSION;
    /**
     * porta do servidor do sistema
     */
    public static int SERVER_PORT;
    /**
     * nivel de acesso de admin
     */
    public static int ADMIN_LVL;
    /**
     * nivel de acesso de operador
     */
    public static int OPERADOR_LVL;
    /**
     * nivel de acesso de gerente
     */
    public static int GERENTE_LVL;
    /**
     * se a configuracao foi carregada ou nao.
     */
    public static boolean _loaded;
    /**
     * porta do cliente.
     */
    public static int CLIENT_PORT;
    /**
     * usuario mestre
     */
    public static String MASTER_USER = "admin";
    /**
     *  senha mestre
     */
    public static String MASTER_KEY = "sysdba";
    static boolean DEVELOPER = true;

    /**
     * Carrega Todas as Propriedades do config.properties
     */
    public static void load()
    {
        InputStream is = null;
            _log.info("Config: Carregando o Arquuivo de Configuração...\n");
        try
        {

            File f = new File(path);
            if(!f.exists())
            {
                SystemUtil.showErrorMsg("o arquivo: "+f.getName()+" nao existe!",true);
                System.exit(0);//finaliza o sistema.
            }

            is = new FileInputStream(f);
            PropertiesManager arquivo = new PropertiesManager();
            arquivo.load(is);

            /**
             * Configs
             */
            EMPRESA = arquivo.getProperty("empresa", "desconhecida");
            DATABASE_LOGIN = arquivo.getProperty("login", "root");
            DATABASE_PASSWORD = arquivo.getProperty("password", "root");
            DATABASE_DRIVER = arquivo.getProperty("driver", "com.mysql.jdbc.Driver");
            DATABASE_URL = arquivo.getProperty("URL", "jdbc:mysql://");
            DATABASE = arquivo.getProperty("database", "wcom");
            DATABASE_PORT = arquivo.getProperty("databasePort", "3306");
            DATABASE_HOST = arquivo.getProperty("databaseHost", "localhost");
            SYSTEM_VERSION = Float.parseFloat(arquivo.getProperty("systemVersion", "0.0"));
            SERVER_PORT = Integer.parseInt(arquivo.getProperty("serverPort", "7000"));
            CLIENT_PORT = Integer.parseInt(arquivo.getProperty("clientPort", "7000"));
            ADMIN_LVL = Integer.parseInt(arquivo.getProperty("adminLvl", "500"));
            OPERADOR_LVL = Integer.parseInt(arquivo.getProperty("operadorLvl", "100"));
            GERENTE_LVL = Integer.parseInt(arquivo.getProperty("gerenteLvl", "250"));
            SITE = arquivo.getProperty("site", "www.wcom.com.br");
            LOGO = arquivo.getProperty("logo", "desktop.jpg");

            // Configuracoes de Som
            ENABLE_SOUND = Boolean.valueOf(arquivo.getProperty("sound", "true "));
            EXIT_SOUND = arquivo.getProperty("exit", "exitok.wav");
            PRE_EXIT_SOUND = arquivo.getProperty("preExit", "exit.wav");
            LOGIN_SOUND = arquivo.getProperty("loginSound", "loginok.wav");
            PRE_LOGIN_SOUND = arquivo.getProperty("preLogin", "login.wav");
            UNIPLEMENTED_SOUND = arquivo.getProperty("uniplemented", "implementar.wav");
            PRE_RESTART_SOUND = arquivo.getProperty("preRestart", "restart.wav");
            RESTART_SOUND = arquivo.getProperty("restartSound", "restartok.wav");
            WELCOME_SOUND = arquivo.getProperty("welcomeSound", "welcome.wav");
            DEBUG = Boolean.valueOf(arquivo.getProperty("debug", "true"));
            LOG_DEBUG = Boolean.valueOf(arquivo.getProperty("logdebug", "false"));
            QUESTION_SOUND = arquivo.getProperty("questionSound", "question.wav");
            /**
             * Port Checks
             */
            if(SERVER_PORT <= 0 || SERVER_PORT > 65535)
            {
                SystemUtil.showErrorMsg("Erro na Configuracao as Portas do Programa.\n",true);
                System.exit(0);
            }

            _loaded = true;

        }
        catch(IOException | NumberFormatException e)
        {
            SystemUtil.showErrorMsg("Nao Foi Possivel Carregar o Arquivo:  de configuracao" + e.getMessage() + "\n",true);
        }
    }

}
