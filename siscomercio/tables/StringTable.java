/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio.tables;

import com.siscomercio.Config;
import com.siscomercio.managers.DatabaseManager;

/**
 * $Revision$
 * $Author$
 * $Date$
 * @author Rayan
 *
 * Comandos SQL
 * executeQuery apenas para select os outros use o executeUpdate.
 */
public class StringTable
{
    /**
     * Pasta de Sons
     */
    public static String SOUND_PATH = "./sounds/";
    /**
     * Pasta de Imagens
     */
    public static String IMAGE_PATH = "./images/";
    /**
     * Pasta de Scripts SQL
     */
    public static String SQL_PATH = "./sql/";
    /**
     * Dropa a Database Atual
     */
    public static String DELETE_DB = "DROP DATABASE " + Config.DATABASE;
    /**
     * pega o nivel de acesso do usuario na DB
     */
    public static final String GET_ACC_LVL = "SELECT accesslevel FROM users WHERE login =?";
    /**
     * Insere Novo Usuario na DB
     */
    public static final String INSERT_USER = "INSERT INTO `users`(codigo,login,password,accesslevel) VALUES (?,?,?,?)";
    /**
     * Pega o Codigo do Usuario.
     */
    public static final String GET_USER_CODE = "SELECT `codigo` from users WHERE login=? AND password=?";

    /**
     * Pega o Codigo pelo Login
     */
    public static final String GET_USER_CODE_BY_LOGIN = "SELECT `codigo` from users WHERE login=?";

    /**
     * Pega o ultimo codigo cadastrado.
     */
    public static final String GET_LAST_CODE = "SELECT MAX(`codigo`) FROM users";

  
    /**
     * Checa a Senha do Usuario
     */
    public static final String CHECK_USER_PASS = "SELECT * from users WHERE codigo=? AND login=? AND password=?";
    /**
     * Deleta Usuario
     */
    public static final String DELETE_USER = "DELETE FROM users WHERE codigo=? AND login =?";
    /**
     * Troca Senha
     */
    public static final String CHANGE_USER_PASS = "UPDATE users SET password=? WHERE login=?";
    /**
     * Atualiza Dados
     */
    public static final String UPDATE_USER = "UPDATE users SET (codigo,login,password)WHERE codigo=? AND login=?";

    /**
     * Registra a App
     */
    public static final String REGISTRE_APP = "REPLACE INTO install VALUES (?,?,?,?,?,?,?,?,?)";
            // bancoInstalado=?,stationMAC=?,StationMBSerial=?,Empresa=?,stationHDSerial=?,NumEstacoes=?,licenseType=?,registeredFor=?";

        /**
     * `bancoInstalado` varchar(5) NOT NULL default 'false',
     * `statioMAC` varchar(50) NOT NULL,
     * `StationMBSerial` varchar(50) NOT NULL,
     * `Empresa` varchar(50) default NULL,
     * `stationHDSerial` varchar(50) NOT NULL,
     * `NumEstacoes` int(50) NOT NULL,
     * `licenseType` varchar(10) NOT NULL,
     * `registeredFor` varchar(30) NOT NULL,
     */


      /**
     * Registra a App
     */
    public static final String READ_APP_LICENSE_DATA = "SELECT `stationMAC`, `StationMBSerial`, `Empresa`, `stationHDSerial`, `NumEstacoes`, `licenseType`, `registeredFor`, `licenciado` FROM install";

    


    /**
     * Le a tabela de Usuarios
     */
    public static final String READ_USERS = "SELECT * FROM users";
    /**
     * Atualiza a Tabela de Instalacao
     */
    public static final String INSTALL = "UPDATE install SET bancoInstalado = '1'";
    /**
     * Le a Tabela de Instalacao
     */
    public static final String READ_INSTALL = "SELECT * FROM install";
    /**
     * Atualiza o Nivel de Acesso do Usuario
     */
    public static final String UPDATE_USER_ACCESS_LVL = "UPDATE users SET accesslvl=?  WHERE login=?";
    /**
     * Cria a Database
     */
    public static final String CREATE_DB = "CREATE DATABASE IF NOT EXISTS " + Config.DATABASE;
    // *************************** Status da Database **********************/
    /**                                          
     * Status da Database
     */
    public static final String STATUS_CONNECTED = "Conectado";
    /**
     * Status da Database
     */
    public static final String STATUS_DISCONNECTED = "Desconectado";
}
