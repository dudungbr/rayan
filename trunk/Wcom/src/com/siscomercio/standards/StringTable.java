/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio.standards;

import com.siscomercio.init.Config;

/**
 * $Revision: 237 $ $Author: rayan_rpg@hotmail.com $ $Date: 2011-02-14 11:03:12
 * -0300 (seg, 14 fev 2011) $
 *
 * @author Rayan
 *
 * Comandos SQL executeQuery apenas para select os outros use o executeUpdate.
 */
public class StringTable
{
    /**
     * Pastas
     */
    private String IMAGE_PATH = "./images/";
    private String SQL_PATH = "./sql/";
    public static final String CONFIG_FILE = "./config/config.properties";
    private String CONFIG_PATH = "./config/";
    private String UPDATE_FILE = "./update/update.properties";
    private String LOG_FILE = "./log/meulog.log";
    /**
     * Querys
     */
    private String DELETE_DB = "DROP DATABASE " + Config.getDatabase();
    private String GET_ACC_LVL = "SELECT accesslevel FROM users WHERE login =?";
    private String INSERT_USER = "INSERT INTO `users`(login,password,accesslevel) VALUES (?,?,?)";
    private String GET_USER_CODE = "SELECT `codigo` from users WHERE login=? AND password=?";
    private String GET_USER_CODE_BY_LOGIN = "SELECT `codigo` from users WHERE login=?";
    private String GET_LAST_CODE = "SELECT MAX(`codigo`) FROM users";
    private String CHECK_USER_PASS = "SELECT * from users WHERE codigo=? AND login=? AND password=?";
    private String DELETE_USER = "DELETE FROM users WHERE codigo=? AND login =?";
    private String CHANGE_USER_PASS = "UPDATE users SET password=? WHERE login=?";
    private String UPDATE_USER = "UPDATE users SET (codigo,login,password)WHERE codigo=? AND login=?";
    private String REGISTRE_APP = "REPLACE INTO install VALUES (?,?,?,?,?,?,?,?,?)";
    private String READ_APP_LICENSE_DATA = "SELECT `stationMAC`, `StationMBSerial`, `Empresa`, `stationHDSerial`, `NumEstacoes`, `licenseType`, `registeredFor`, `licenciado` FROM install";
    private String READ_USERS = "SELECT * FROM users";
    private String INSTALL = "UPDATE install SET bancoInstalado = '1'";
    private String READ_INSTALL = "SELECT * FROM install";
    private String UPDATE_USER_ACCESS_LVL = "UPDATE users SET accesslvl=?  WHERE login=?";
    private String createDB = "CREATE DATABASE IF NOT EXISTS " + Config.getDatabase();
    public String GET_ACC_LVL2 = "SELECT accesslevel FROM users WHERE login =?";
    private String INSERT_USER2 = "INSERT INTO `usuarios`(login,senha,nivelAcesso,nomeCompleto,endereco,cargo) VALUES (?,?,?,?,?,?)";
    private String INSERT_ENTRADA = "INSERT INTO `entradaequip`(numentrada,data,usuario,status,perifericos,descricao,marca,tipo,quantidade,responsavel,defeito,hora) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    private String INSERT_TECNICO = "INSERT INTO `tecnicos` (nome,sobrenome) VALUES (?,?)";
    private String GET_LAST_ENTRADA_ID = "SELECT MAX(`id`) FROM entradaequip";
    private String LOAD_CIDADES = "SELECT id,estado,uf,nome FROM cidades order by id";
    public final String LOAD_EQUIPAMENTO = "SELECT id,tipo FROM equipamentos ORDER BY id";
    public final String LOAD_ESTADOS = "SELECT id,uf,nome FROM estados order by id";
    public final String LOAD_MARCA = "SELECT id,marca,tipo FROM marcas order by id";
    public final String LOAD_TECNICOS = "SELECT id,nome FROM tecnicos order by id";
    //  public final String CHECK_USER_PASS = "SELECT * from usuarios WHERE  login=? AND senha=?";
    // public final String DELETE_USER = "DELETE FROM usuarios WHERE id=? AND login =?";
    /// public final String CHANGE_USER_PASS = "UPDATE usuarios SET senha=? WHERE login=?";
    public final String GET_USER_PASS = "SELECT `senha` FROM usuarios WHERE login=?";
    /**
     * Defaults
     */
    private int DEFAULT_INT = -1;
    public static final String SOUND_PATH = "./sounds/";
    private String EMPTY_STRING = "";
    private String NOT_AVAIBLE = "N/A";
    private String JCOMBO_DEFAULT = "Selecione";
    /**
     * Status
     */
    private String STATUS_DISCONNECTED = "Desconectado";
    private String STATUS_CONNECTED = "Conectado";

    public String getDELETE_DB()
    {
        return DELETE_DB;
    }

    public String getGET_ACC_LVL()
    {
        return GET_ACC_LVL;
    }

    public String getGET_USER_CODE()
    {
        return GET_USER_CODE;
    }

    public String getGET_USER_CODE_BY_LOGIN()
    {
        return GET_USER_CODE_BY_LOGIN;
    }

    public String getGET_LAST_CODE()
    {
        return GET_LAST_CODE;
    }

    public String getCHECK_USER_PASS()
    {
        return CHECK_USER_PASS;
    }

    public String getDELETE_USER()
    {
        return DELETE_USER;
    }

    public String getCHANGE_USER_PASS()
    {
        return CHANGE_USER_PASS;
    }

    public String getUPDATE_USER()
    {
        return UPDATE_USER;
    }

    public String getREGISTRE_APP()
    {
        return REGISTRE_APP;
    }

    public String getREAD_APP_LICENSE_DATA()
    {
        return READ_APP_LICENSE_DATA;
    }

    public String getREAD_USERS()
    {
        return READ_USERS;
    }

    public String getINSTALL()
    {
        return INSTALL;
    }

    public String getREAD_INSTALL()
    {
        return READ_INSTALL;
    }

    public String getUPDATE_USER_ACCESS_LVL()
    {
        return UPDATE_USER_ACCESS_LVL;
    }

    public String getGET_ACC_LVL2()
    {
        return GET_ACC_LVL2;
    }

    public String getINSERT_USER2()
    {
        return INSERT_USER2;
    }

    public String getLOAD_EQUIPAMENTO()
    {
        return LOAD_EQUIPAMENTO;
    }

    public String getLOAD_ESTADOS()
    {
        return LOAD_ESTADOS;
    }

    public String getLOAD_MARCA()
    {
        return LOAD_MARCA;
    }

    public String getLOAD_TECNICOS()
    {
        return LOAD_TECNICOS;
    }

    public String getGET_USER_PASS()
    {
        return GET_USER_PASS;
    }

    public int getDEFAULT_INT()
    {
        return DEFAULT_INT;
    }

    public String getSTATUS_DISCONNECTED()
    {
        return STATUS_DISCONNECTED;
    }

    public String getSTATUS_CONNECTED()
    {
        return STATUS_CONNECTED;
    }

    /**
     * @return the NOT_AVAIBLE
     */
    public String getNOT_AVAIBLE()
    {
        return NOT_AVAIBLE;
    }

    /**
     * @return the JCOMBO_DEFAULT
     */
    public String getJCOMBO_DEFAULT()
    {
        return JCOMBO_DEFAULT;
    }

    /**
     * @return the EMPTY_STRING
     */
    public String getEMPTY_STRING()
    {
        return EMPTY_STRING;
    }

    /**
     * @return the SOUND_PATH
     */
    public String getSOUND_PATH()
    {
        return SOUND_PATH;
    }

    /**
     * @return the IMAGE_PATH
     */
    public String getIMAGE_PATH()
    {
        return IMAGE_PATH;
    }

    /**
     * @return the SQL_PATH
     */
    public String getSQL_PATH()
    {
        return SQL_PATH;
    }

    /**
     * @return the CONFIG_FILE
     */
    public String getCONFIG_FILE()
    {
        return CONFIG_FILE;
    }

    /**
     * @return the CONFIG_PATH
     */
    public String getCONFIG_PATH()
    {
        return CONFIG_PATH;
    }

    /**
     * @return the UPDATE_FILE
     */
    public String getUPDATE_FILE()
    {
        return UPDATE_FILE;
    }

    /**
     * @return the LOG_FILE
     */
    public String getLOG_FILE()
    {
        return LOG_FILE;
    }

    /**
     * @return the INSERT_USER
     */
    public String getINSERT_USER()
    {
        return INSERT_USER;
    }

    /**
     * @return the INSERT_ENTRADA
     */
    public String getINSERT_ENTRADA()
    {
        return INSERT_ENTRADA;
    }

    /**
     * @return the INSERT_TECNICO
     */
    public String getINSERT_TECNICO()
    {
        return INSERT_TECNICO;
    }

    /**
     * @return the GET_LAST_ENTRADA_ID
     */
    public String getGET_LAST_ENTRADA_ID()
    {
        return GET_LAST_ENTRADA_ID;
    }

    /**
     * @return the LOAD_CIDADES
     */
    public String getLOAD_CIDADES()
    {
        return LOAD_CIDADES;
    }

    public String getCreateDB()
    {
        return createDB;
    }

    private StringTable()
    {
    }

    /**
     *
     * @return
     */
    public static StringTable getInstance()
    {
        return StringTable.SingletonHolder._instance;
    }
    private static class SingletonHolder
    {
        protected static final StringTable _instance = new StringTable();
    }
}
