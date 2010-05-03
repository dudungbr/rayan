/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio.tables;

/**
 * $Revision$
 * $Author$
 * $Date$
 * @author Rayan
 */
public class StringTable
{

    /**
     * 
     */
    public static String SOUND_PATH = "./sounds/";
    
    /**
     * 
     */
    public static String IMAGE_PATH = "./imagens/";
  /**
   *
   */
  public static String SQL_PATH = "./sql/";
    /**
     * Comandos SQL
     * executeQuery apenas para select os outros use o executeUpdate.
     */
    public static final String INSERT_USER = "INSERT INTO `users`(codigo,login,passwd, accesslevel) VALUES (?,?,?,?)";
    /**
     *
     */
    public static final String DELETE_USER = "DELETE FROM `users` WHERE codigo=? AND login =?";
    /**
     *
     */
    public static final String UPDATE_USER = "UPDATE `users` SET (codigo,login,password)WHERE codigo=? AND login =?";
    /**
     *
     */
    public static final String READ_USERS = "SELECT * FROM users";
    /**
     *
     */
    public static final String INSTALL = "UPDATE `install` SET `bancoInstalado` = 'true'";
    /**
     *
     */
    public static final String READ_INSTALL = "SELECT * FROM `install`";
    /**
     *
     */
    public static final String UPDATE_USER_ACCESS_LVL = "UPDATE `users` SET (accesslvl = ?  WHERE `login`= ?";
    
    /**                                          
     *
     */
    public static final String STATUS_CONNECTED = "Conectado";
    /**
     *
     */
    public static final String STATUS_DISCONNECTED = "Desconectado";
    /**
     *
     */
    public static final String CREATE_DB = "CREATE DATABASE IF NOT EXISTS wcom";
}
