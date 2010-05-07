/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio.tables;

import com.siscomercio.Config;

/**
 * $Revision$
 * $Author$
 * $Date$
 * @author Rayan
 *
 * Comandos SQL
 * executeQuery apenas para select os outros use o executeUpdate.
 */
public class StringTable {

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
    public static String DELETE_DB ="DROP DATABASE "+ Config.DATABASE;
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
     * Le a tabela de Usuarios
     */
    public static final String READ_USERS = "SELECT * FROM users";
    /**
     * Atualiza a Tabela de Instalacao
     */
    public static final String INSTALL = "UPDATE install SET bancoInstalado = 'true'";
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
    public static final String CREATE_DB = "CREATE DATABASE IF NOT EXISTS wcom";
    // *************************** Status da Database **********************/
    /**                                          
     *
     */
    public static final String STATUS_CONNECTED = "Conectado";
    /**
     *
     */
    public static final String STATUS_DISCONNECTED = "Desconectado";
}
