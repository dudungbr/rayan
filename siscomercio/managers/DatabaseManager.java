package com.siscomercio.managers;

import com.siscomercio.DatabaseFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.siscomercio.Config;
import com.siscomercio.tables.SqlQueryTable;
import com.siscomercio.utilities.SystemUtil;

/**
 * $Revision$
 * $Author$
 * $Date$
 * @author William Menezes
 *
 */
public class DatabaseManager
{
    private static Logger _log = Logger.getLogger(DatabaseManager.class.getName());
    // Status Atual do Banco
    static String _status = SqlQueryTable.STATUS_DISCONNECTED;
    /**
     * se o banco foi deletado
     */
    public static boolean isDbDeleted;
    /**
     *se o banco esta instalado
     */
    public static boolean _installed;

    /**
     * Executa os SQL Scripts dentro da pasta sql
     * @param filename
     */
    public static void executeDBScripts(String filename)
    {
        Connection con = null;
        if(Config.DEBUG)
            _log.info("DatabaseManager: executando script " + filename + " \n");

        String thisLine, sqlQuery;
        try
        {
            con = DatabaseFactory.getInstance().getConnection();
            BufferedReader d = new BufferedReader(new FileReader("./sql/" + filename));
            sqlQuery = "";
            Statement st = null;
            //Now read line by line
            while((thisLine = d.readLine()) != null)
            {
                //Skip comments <strong class="highlight">and</strong> empty lines
                if(thisLine.length() > 0 && thisLine.charAt(0) == '-' || thisLine.length() == 0
                   || thisLine.startsWith("/*") || thisLine.endsWith("*/"))
                    continue;

                sqlQuery = sqlQuery + " " + thisLine;

                //If one command complete
                if(sqlQuery.charAt(sqlQuery.length() - 1) == ';')
                {
                    sqlQuery = sqlQuery.replace(';', ' '); //Remove the ; since jdbc complains
                    try
                    {
                        st = con.createStatement();
                        st.execute(sqlQuery);

                    }
                    catch(SQLException ex)
                    {
                        SystemUtil.showErrorMsg("Erro" + ex);
                    }

                    sqlQuery = "";
                }
            }
            closeConnections(st, con);
        }
        catch(Exception e)
        {
            SystemUtil.showErrorMsg("Falha ao Executar Script SQL:  " + filename + " Erro:  " + e.getMessage());
        }
    }

    /**
     * Executa uma query
     * @param query
     */
    public static void executeQuery(String query)
    {
        Connection con = null;
        if(Config.DEBUG)
            _log.info("Executando Query: " + query + "\n");
        try
        {
            con = DatabaseFactory.getInstance().getConnection();
            Statement st = con.createStatement();
            st.execute(query);
            closeConnections(st, con);
        }
        catch(SQLException e)
        {
            SystemUtil.showErrorMsg("" + e);

        }
    }

    /**
     * Cria Nova Database
     */
    public static void createNewDatabase()
    {
        Connection con = null;

        if(Config.DEBUG)
            _log.info("criando novo banco.");
        try
        {
            Class.forName(Config.DATABASE_DRIVER).newInstance();
            //jdbc:mysql://localhost:3306/wcom
            con = DriverManager.getConnection("jdbc:mysql://" + Config.DATABASE_HOST + ":" + Config.DATABASE_PORT + "/", Config.DATABASE_LOGIN, Config.DATABASE_PASSWORD);
            Statement st = con.createStatement();
            st.executeUpdate(SqlQueryTable.CREATE_DB);
            closeConnections(st, con);

        }
        catch(Exception ex)
        {
            Logger.getLogger(DatabaseManager.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Instala Novo Banco
     */
    public static void instaleBanco()
    {
        if(Config.DEBUG)
            _log.info("tentando Instalar Database...");
        createNewDatabase();
        executeDBScripts("users.sql");
        executeDBScripts("install.sql");
        executeDBScripts("auditoria.sql");
        executeQuery(SqlQueryTable.INSTALL);
        setConStatus(SqlQueryTable.STATUS_CONNECTED);
        _installed = true;
        SystemUtil.showMsg("banco instalado com sucesso!");
    }

    /**
     * Deleta a Database Atual
     */
    public static void deleTeBanco()
    {
        executeDBScripts("drop.sql");
        SystemUtil.showMsg("Banco de Dados Deletado!");
        _installed = false;
        setConStatus(SqlQueryTable.STATUS_DISCONNECTED);
        isDbDeleted = true;
    }

    /**
     *
     */
    public static void editUser()
    {
        AppManager.implementar();
    }

    /**
     * 
     */
    public static void deleteUser()
    {
        AppManager.implementar();
    }

    /**
     * Insere Novo Usuario no Banco
     * @param name
     * @param passwd
     */
    public static void insertUser(String name, String passwd)
    {
        Connection con = null;
        String commandLine = SqlQueryTable.INSERT_USER;
        try
        {
            con = DatabaseFactory.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(commandLine);
            ResultSet rset = ps.executeQuery();
            closeConnections(ps, rset, con);

            SystemUtil.showMsg("Usuario Cadastrado com Sucesso!");
        }
        catch(SQLException e)
        {
            SystemUtil.showErrorMsg(e.toString());

        }
    }

    /**
     * Close Connections
     * @param ps
     * @param rset
     * @param con
     */
    public static void closeConnections(PreparedStatement ps, ResultSet rset, Connection con)
    {
        _log.info("fechando conexoes c/ a database \n");
        try
        {
            ps.close();
            rset.close();
            con.close();
        }
        catch(SQLException ex)
        {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Close Connections
     * @param s
     * @param rset
     * @param con
     */
    private static void closeConnections(Statement s, Connection con)
    {
        _log.info("fechando conexoes c/ a database \n");
        try
        {
            s.close();
            con.close();
        }
        catch(SQLException ex)
        {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Deleta Usuario do Banco
     * @param user
     * @param pass
     */
    public static void deleteUsuario(String user, String pass)
    {
        Connection con = null;
        String commandLine = SqlQueryTable.DELETE_USER;
        try
        {
            con = DatabaseFactory.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(commandLine);
            ResultSet rset = ps.executeQuery();
            closeConnections(ps, rset, con);
        }
        catch(SQLException e)
        {
            SystemUtil.showErrorMsg("Erro ao Deletar Usuario: " + e);

        }
    }

    /**
     *
     * @return _status
     */
    public static String getConnectionStatus()
    {
        return _status;
    }

    /**
     *
     * @param state
     */
    public static void setConStatus(String state)
    {
        _status = state;
    }

    /**
     *
     * @return
     */
    private static int getConnections()
    {
        int count = 0;
        try
        {
            count = DatabaseFactory.getInstance().getBusyConnectionCount();
        }
        catch(SQLException ex)
        {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    /**
     * 
     **/
    public static void readInstallTable()
    {
        Connection con = null;
        if(Config.DEBUG)
            _log.info("lendo tabela de estado da instalacao \n");
        try
        {
            con = DatabaseFactory.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(SqlQueryTable.READ_INSTALL);
            ResultSet rset = ps.executeQuery();
            while(rset.next())
            {
                _installed = rset.getBoolean("bancoInstalado");
            }
            closeConnections(ps, rset, con);

            if(Config.DEBUG)
                _log.info("_installed = " + _installed + "\n");
        }
        catch(Exception e)
        {
            if(Config.DEBUG)
                _log.log(Level.SEVERE, "DatabaseManager: Error reading Install Table: " + e.getMessage(), e);
        }
    }

    /**
     * 
     * @param lvl
     */
    public void setAcessLevel(int lvl)
    {
        Connection con = null;
        try
        {
            con = DatabaseFactory.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(SqlQueryTable.UPDATE_USER_ACCESS_LVL);
            ResultSet rset = ps.executeQuery();
            closeConnections(ps, rset, con);

        }
        catch(Exception e)
        {
            if(Config.DEBUG)
                _log.log(Level.SEVERE, "DatabaseManager: Error Updating Users Access Level: " + e.getMessage(), e);
        }
    }

}
