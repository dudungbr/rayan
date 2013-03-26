/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio.tables;

import com.siscomercio.init.Config;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * $Revision$
 * $Author$
 * $Date$
 * <p/>
 * @author Rayan
 */
public class UserTable
{
    // auth
    private int _id;
    private String _senha;
    private String _login;
    private String _lastLogin = "Desconhecido";

    /**
     *
     * @return AppManager _instance
     */
    public static UserTable getInstance()
    {
        return SingletonHolder._instance;
    }
    private static final Logger _log = Logger.getLogger(UserTable.class.getName());

    private UserTable()
    {
    }
    @SuppressWarnings ("synthetic-access")
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
     * @return _id
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
     * @return _login
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
        if (Config.getInstance().isDebug())
        {
            _log.log(Level.INFO, "setando ultimo usuario como {0}\n", usr);
        }
        _lastLogin = usr;


    }

    /**
     *
     * @return _lastLogin
     */
    public String getLastUser()
    {
        return _lastLogin;
    }

    /**
     *
     * @return _senha
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
