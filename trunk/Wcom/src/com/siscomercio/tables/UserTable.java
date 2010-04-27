/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio.tables;

import java.util.logging.Logger;

/**
 *$Revision$
 * $Author$
 * $Date$
 * @author Rayan
 */
public class UserTable
{
    // auth
    private int _id;
    private String _senha;
    private String _login;
    private String _lastLogin;

    /**
     *
     * @return AppManager _instance
     */
    public static UserTable getInstance()
    {
        return SingletonHolder._instance;
    }
    private static Logger _log = Logger.getLogger(UserTable.class.getName());

    private UserTable()
    {
      
    }

    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder
    {
        protected static final UserTable _instance = new UserTable();
    }

    /**
     *
     * @param id
     * @param user
     * @param pwd
     */
    public UserTable(int id, String user, String pwd)
    {
        _id = id;
        _senha = pwd;
        _login = user;
    }

    /**
     *
     * @param id
     * @param user
     */
    public UserTable(int id, String user)
    {
        this(id, user, null);
    }

    /**
     *
     * @param id
     */
    public void setId(int id)
    {
        _id = id;
    }

    /**
     *
     * @return
     */
    public int getId()
    {
        return _id;
    }

    /**
     *
     * @param login
     */
    public void setUserLogin(String login)
    {
        _login = login;
    }

    /**
     *
     * @return
     */
    public String getUser()
    {
        return _login;
    }

    /**
     *
     * @param usr
     */
    public void setLastUser(String usr)
    {
        _log.info("setando ultimo usuario como " + usr +"\n");
        _lastLogin = usr;

    }

    /**
     * 
     * @return
     */
    public String getLastUser()
    {
        return _lastLogin;
    }

    /**
     *
     * @return
     */
    public String getPassword()
    {
        return _senha;
    }

    /**
     *
     * @param pwd
     */
    public void setPassword(String pwd)
    {
        _senha = pwd;
    }

}
