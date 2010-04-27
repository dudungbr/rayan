package com.siscomercio;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Logger;
import com.siscomercio.managers.PropertiesManager;
import com.siscomercio.utilities.SystemUtil;

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
public class Config {

    private static Logger _log = Logger.getLogger(Config.class.getName());
    /**
     *
     */
    public static String path = "./config/config.properties";
    /**
     *
     */
    public static String SITE;
    /**
     * 
     */
    public static String EMPRESA;
    /**
     *
     */
    public static String BUILD_AUTHOR = "$Author$";
    /**
     *
     */
    public static String BUILD_NUM = "$Revision$";
    /**
     *
     */
    public static String BUILD_DATE = "$Date$";
    /**
     *
     */
    public static String BUILD_URL = "$HeadURL$";
    /**
     * Database
     */
    public static String DATABASE_LOGIN;
    /**
     *
     */
    public static String DATABASE_HOST;
    /**
     *
     */
    public static String DATABASE_PORT;
    /**
     * 
     */
    public static String DATABASE_PASSWORD;
    /**
     *
     */
    public static String DATABASE_DRIVER;
    /**
     *
     */
    public static String DATABASE_URL;
    /**
     *
     */
    public static String DATABASE;
    /**
     *
     */
    public static boolean DEBUG = true;
    /**
     * 
     */
    public static boolean SOUND = false;
    /**
     *
     */
    public static double SYSTEM_VERSION;
    /**
     *
     */
    public static int SERVER_PORT;
    /**
     * 
     */
    public static int ADMIN_LVL;
    /**
     *
     */
    public static int OPERADOR_LVL;
    /**
     *
     */
    public static int GERENTE_LVL;
    /**
     *
     */
    public static boolean _loaded;
    /**
     *
     */
    public static int CLIENT_PORT;
    /**
     *
     */
    public static String MASTER_USER = "admin";
    /**
     *
     */
    public static String MASTER_KEY = "sysdba";

    /**
     *
     */
    public static void load()
    {
        InputStream is = null;
        if (Config.DEBUG)
            _log.info("Carregando o Arquuivo de Configuração...\n");
        try
        {
           
            File f = new File(path);
            if (!f.exists())
            {
                SystemUtil.showErrorMsg("o arquivo de configuração nao existe");
                System.exit(0);//finaliza o sistema.
            }

            is = new FileInputStream(f);
            PropertiesManager arquivo = new PropertiesManager();
            arquivo.load(is);
            EMPRESA = arquivo.getProperty("Empresa", "desconhecida");
            DATABASE_LOGIN = arquivo.getProperty("Login", "root");
            DATABASE_PASSWORD = arquivo.getProperty("Password", "root");
            DATABASE_DRIVER = arquivo.getProperty("Driver", "com.mysql.jdbc.Driver");
            DATABASE_URL = arquivo.getProperty("URL", "jdbc:mysql://");
            DATABASE = arquivo.getProperty("Database", "wcom");
            DATABASE_PORT = arquivo.getProperty("DatabasePort", "3306");
            DATABASE_HOST = arquivo.getProperty("DatabaseHost", "localhost");
            SYSTEM_VERSION = Float.parseFloat(arquivo.getProperty("SystemVersion", "0.0"));
            SERVER_PORT = Integer.parseInt(arquivo.getProperty("ServerPort", "7000"));
            CLIENT_PORT = Integer.parseInt(arquivo.getProperty("ClientPort", "7000"));
            ADMIN_LVL = Integer.parseInt(arquivo.getProperty("AdminLvl", "500"));
            OPERADOR_LVL = Integer.parseInt(arquivo.getProperty("OperadorLvl", "100"));
            GERENTE_LVL = Integer.parseInt(arquivo.getProperty("GerenteLvl", "250"));
            SITE = arquivo.getProperty("Site", "www.wcom.com.br");

            if (SERVER_PORT <= 0 || SERVER_PORT > 65535)
            {
                SystemUtil.showErrorMsg("Erro na Configuracao as Portas do Programa.\n");
                return;
            }

            _loaded = true;

        } catch (Exception e)
        {
            _log.warning(e.getMessage());
            throw new Error("Nao Foi Possivel Carregar o Arquivo:  de configuracao\n");
        }
    }
}
