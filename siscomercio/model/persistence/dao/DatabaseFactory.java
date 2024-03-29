/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.siscomercio.model.persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.siscomercio.init.Config;
import com.siscomercio.standards.StringTable;
import com.siscomercio.utilities.SystemUtil;

/**
 * $Revision$ $Author$ $Date: 2013-03-21 19:34:43 -0300 (Thu,
 * 21 Mar 2013) $
 *
 * @author Rayan
 */
public class DatabaseFactory
{
    private static final Logger _log = Logger.getLogger(DatabaseFactory.class.getName());
    StringTable defaults = StringTable.getInstance();
    /**
     * Tipo do Provedor
     */
    public static enum ProviderType
    {
        /**
         * Provedor MySQL
         */
        MySql,
        /**
         * Provedor MsSql
         */
        MsSql
    }
    // =========================================================
    // Data Field
    private static DatabaseFactory _instance;
    private ProviderType _providerType;
    private ComboPooledDataSource _source;
    Config config = Config.getInstance();
    // =========================================================
    // Constructor

    /**
     *
     * @throws SQLException
     */
    public DatabaseFactory() throws SQLException
    {
        try
        {
            _log.log(Level.INFO, "Inicializando Database Engine C3P0... \n");
            _source = new ComboPooledDataSource();
            _source.setAutoCommitOnClose(true);
            _source.setInitialPoolSize(10);
            _source.setMinPoolSize(10);
            _source.setAcquireRetryAttempts(0); // try to obtain connections indefinitely (0 = never quit)
            _source.setAcquireRetryDelay(500); // 500 milliseconds wait before try to acquire connection again
            _source.setCheckoutTimeout(0); // 0 = wait indefinitely for new connection

            // if pool is exhausted
            _source.setAcquireIncrement(5); // if pool is exhausted, get 5 more connections at a time
            // cause there is a "long" delay on acquire connection
            // so taking more than one connection at once will make connection pooling
            // more effective.

            // this "connection_test_table" is automatically created if not already there
            _source.setAutomaticTestTable("connection_test_table");
            _source.setTestConnectionOnCheckin(false);

            // testing OnCheckin used with IdleConnectionTestPeriod is faster than  testing on checkout

            _source.setIdleConnectionTestPeriod(3600); // test idle connection every 60 sec
            //_source.setMaxIdleTime(Config.DATABASE_MAX_IDLE_TIME); // 0 = idle connections never expire
            // *THANKS* to connection testing configured above
            // but I prefer to disconnect all connections not used
            // for more than 1 hour

            // enables statement caching,  there is a "semi-bug" in c3p0 0.9.0 but in 0.9.0.2 and later it's fixed
            _source.setMaxStatementsPerConnection(100);

            _source.setBreakAfterAcquireFailure(false); // never fail if any way possible
            // setting this to true will make
            // c3p0 "crash" and refuse to work
            // till restart thus making acquire
            // errors "FATAL" ... we don't want that
            // it should be possible to recover
            _source.setDriverClass(config.getDatabaseDriver());
            _source.setJdbcUrl(config.getDatabaseUrl());
            _source.setUser(config.getDatabaseLogin());
            _source.setPassword(config.getDatabasePassword());

            /*
             * Test the connection
             */
            _source.getConnection().close();

            if (config.isEnableLog())
            {
                _log.fine("Database Connection Working");
            }

            if (config.getDatabaseDriver().toLowerCase().contains("microsoft"))
            {
                _providerType = ProviderType.MsSql;
            }
            else
            {
                _providerType = ProviderType.MySql;
            }
        }
        catch (SQLException x)
        {
            //   AppManager.setTema(getClass().getName());
            SystemUtil.getInstance().showErrorMsg("Impossível Conectar ao Banco de Dados! <br> detalhes do erro: " + x.getLocalizedMessage(), true);
            // re-throw the exception
            throw x;
        }
        catch (Exception e)
        {
            SystemUtil.getInstance().showErrorMsg("Nao Foi Possivel Conectar ao Banco de Dados: " + e.getMessage(), true);
        }
    }

    // =========================================================
    // Method - Public
    /**
     *
     * @param fields
     * @param tableName
     * @param whereClause
     * @param returnOnlyTopRecord
     * <p/>
     * @return query
     */
    public final String prepQuerySelect(String[] fields, String tableName, String whereClause, boolean returnOnlyTopRecord)
    {
        String msSqlTop1 = "";
        String mySqlTop1 = "";
        if (returnOnlyTopRecord)
        {
            if (getProviderType() == ProviderType.MsSql)
            {
                msSqlTop1 = " Top 1 ";
            }
            if (getProviderType() == ProviderType.MySql)
            {
                mySqlTop1 = " Limit 1 ";
            }
        }
        String query = "SELECT " + msSqlTop1 + safetyString(fields) + " FROM " + tableName + " WHERE " + whereClause + mySqlTop1;
        return query;
    }

    /**
     *
     */
    public void shutdown()
    {
        try
        {
            _source.close();
        }
        catch (Exception e)
        {
            _log.log(Level.INFO, "", e);
        }
        try
        {
            _source = null;
        }
        catch (Exception e)
        {
            _log.log(Level.INFO, "", e);
        }
    }

    /**
     *
     * @param whatToCheck
     * <p/>
     * @return sbResult
     */
    public final String safetyString(String... whatToCheck)
    {
        // NOTE: Use brace as a safty precaution just incase name is a reserved word
        final char braceLeft;
        final char braceRight;

        if (getProviderType() == ProviderType.MsSql)
        {
            braceLeft = '[';
            braceRight = ']';
        }
        else
        {
            braceLeft = '`';
            braceRight = '`';
        }

        int length = 0;

        for (String word : whatToCheck)
        {
            length += word.length() + 4;
        }

        final StringBuilder sbResult = new StringBuilder(length);

        for (String word : whatToCheck)
        {
            if (sbResult.length() > 0)
            {
                sbResult.append(", ");
            }

            sbResult.append(braceLeft);
            sbResult.append(word);
            sbResult.append(braceRight);
        }

        return sbResult.toString();
    }

    // =========================================================
    // Property - Public
    /**
     *
     * @return _instance = new DatabaseFactory()
     * <p/>
     * @throws SQLException
     */
    public static DatabaseFactory getInstance() throws SQLException
    {
        synchronized (DatabaseFactory.class)
        {
            if (_instance == null)
            {
                _instance = new DatabaseFactory();
            }
        }
        return _instance;
    }

    /**
     * Inicia A Conexao SQL
     *
     * @return connection (SQL)
     */
    public Connection getConnection() //throws SQLException
    {
        Connection con = null;

        while (con == null)
        {
            try
            {
                con = _source.getConnection();
                Banco.getInstance().setConStatus(defaults.getSTATUS_CONNECTED());
            }
            catch (SQLException e)
            {
                Banco.getInstance().setConStatus(defaults.getSTATUS_DISCONNECTED());
                SystemUtil.getInstance().showErrorMsg("DatabaseFactory: getConnection() failed, trying again {0}", true);
            }
        }
        return con;
    }
    /**
     *
     */
    private class ConnectionCloser implements Runnable
    {
        private Connection c;
        private RuntimeException exp;

        public ConnectionCloser(Connection con, RuntimeException e)
        {
            c = con;
            exp = e;
        }
        /*
         * (non-Javadoc)
         * @see java.lang.Runnable#run()
         */

        @Override
        public void run()
        {
            try
            {
                if (!c.isClosed())
                {
                    _log.warning("Unclosed connection! Trace:");
                }
            }
            catch (SQLException e)
            {
                SystemUtil.getInstance().showErrorMsg(e.getMessage(), true);
            }

        }
    }

    /**
     *
     * @return busy connections count
     * <p/>
     * @throws SQLException
     */
    public int getBusyConnectionCount() throws SQLException
    {
        return _source.getNumBusyConnectionsDefaultUser();
    }

    /**
     *
     * @return num of iddle connections
     * <p/>
     * @throws SQLException
     */
    public int getIdleConnectionCount() throws SQLException
    {
        return _source.getNumIdleConnectionsDefaultUser();
    }

    /**
     * @return _providerType
     */
    public final ProviderType getProviderType()
    {
        return _providerType;
    }
}
