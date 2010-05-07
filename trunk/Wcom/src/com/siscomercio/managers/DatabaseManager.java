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
import com.siscomercio.security.Criptografia;
import com.siscomercio.tables.StringTable;
import com.siscomercio.tables.UserTable;
import com.siscomercio.utilities.SystemUtil;
import java.io.File;

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
    /**
     *  Status Atual do Banco
     */
    static String _status = StringTable.STATUS_DISCONNECTED;
    /**
     * se o banco foi deletado
     */
    public static boolean isDbDeleted;
    /**
     *se o banco esta instalado
     */
    public static boolean _installed;

    /**
     *  ler eExecuta todos os  Scripts SQL dentro da pasta SQL
     *
     */
    public static void readAndExecuteDatabaseScripts()
    {
        File pasta = new File(StringTable.SQL_PATH);
        for(File f : pasta.listFiles())
        {
            if(f != null && f.getName().endsWith(".sql"))
            {
                try
                {

                    Connection con = null;
                    if(Config.DEBUG)
                        _log.info("DatabaseManager: executando script " + f.getName() + " \n");
                    String thisLine, sqlQuery = null;

                    con = DatabaseFactory.getInstance().getConnection();
                    BufferedReader d = new BufferedReader(new FileReader(StringTable.SQL_PATH + f.getName()));
                    sqlQuery = "";
                    Statement st = null;


                    //Now read line by line
                    while((thisLine = d.readLine()) != null)
                    {
                        //Skip comments <strong class="highlight">and</strong> empty lines
                        if(thisLine.length() > 0 && thisLine.charAt(0) == '-' || thisLine.length() == 0 || thisLine.startsWith("/*") || thisLine.endsWith("*/"))
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
                    SystemUtil.showErrorMsg("Falha ao Executar Script SQL:  " + f.getName() + " Erro:  " + e.getMessage());
                }
            }
        }
    }

    /**
     * Executa uma query
     * @param query
     * @return o resultado dessa query como boolean
     */
    public static boolean executeQuery(String query)
    {
        boolean result = false;
        Connection con = null;
        if(Config.DEBUG)
            _log.info("Executando Query: " + query + "\n");
        try
        {
            con = DatabaseFactory.getInstance().getConnection();
            Statement st = con.createStatement();
            st.execute(query);
            closeConnections(st, con);
            result = true;
        }
        catch(SQLException e)
        {
            SystemUtil.showErrorMsg("" + e);
            result = false;
        }
        return result;
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
            con = DriverManager.getConnection("jdbc:mysql://" + Config.DATABASE_HOST + ":" + Config.DATABASE_PORT + "/", Config.DATABASE_LOGIN, Config.DATABASE_PASSWORD);
            Statement st = con.createStatement();
            st.executeUpdate(StringTable.CREATE_DB);
            closeConnections(st, con);

        }
        catch(Exception ex)
        {
            SystemUtil.showErrorMsg("Erro ao criar nova base de dados: " + ex);
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
        readAndExecuteDatabaseScripts();
        executeQuery(StringTable.INSTALL);
        _installed = true;
        SystemUtil.showMsg("banco instalado com sucesso!");
    }

    /**
     * Deleta a Database Atual
     */
    public static void dropDatabase()
    {
        executeQuery(StringTable.DELETE_DB);
        SystemUtil.showMsg("Banco de Dados Deletado!");
        _installed = false;
        isDbDeleted = true;
    }

    /**
     * Edita os Dados de um Usuario da Database
     */
    public static void editUser()
    {
        AppManager.implementar();
    }

    /**
     *  Deleta um Usuario da Databse
     */
    public static void deleteUser()
    {
        AppManager.implementar();
    }

    /**
     * Insere Novo Usuario na Base de dados
     *
     * @param name
     * @param passwd
     */
    public static void insertUser(String name, String passwd)
    {
        Connection con = null;
        String commandLine = StringTable.INSERT_USER;
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
     *
     * @param ps
     * @param rset
     * @param con
     */
    public static void closeConnections(PreparedStatement ps, ResultSet rset, Connection con)
    {
        if(Config.DEBUG)
            _log.info("fechando conexoes c/ a database \n");
        try
        {
            ps.close();
            rset.close();
            con.close();
            setConStatus(StringTable.STATUS_DISCONNECTED);
        }
        catch(SQLException ex)
        {
            SystemUtil.showErrorMsg("Erro ao fechar conexoes com o banco de dados!" + ex);
        }
    }

    /**
     * Close Connections
     *
     * @param s
     * @param con
     */
    private static void closeConnections(Statement s, Connection con)
    {
        if(Config.DEBUG)
            _log.info("fechando conexoes c/ a database \n");
        try
        {
            s.close();
            con.close();
            setConStatus(StringTable.STATUS_DISCONNECTED);
        }
        catch(SQLException ex)
        {
            SystemUtil.showErrorMsg("Erro ao fechar conexoes com o banco de dados!" + ex);
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
        try
        {
            con = DatabaseFactory.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(StringTable.DELETE_USER);
            ResultSet rset = ps.executeQuery();
            closeConnections(ps, rset, con);
        }
        catch(SQLException e)
        {
            SystemUtil.showErrorMsg("Erro ao Deletar Usuario: " + user + " , " + e);
        }
    }

    /**
     * pega o codigo do usuario do banco
     * @param login
     * @param senha
     * @return codigo
     */
    public static int getUserCode(String login, String senha)
    {
        Connection con = null;
        if(Config.DEBUG)
            _log.info("procurando codigo do Usuario.. \n");
        int codigo = -1;
        try
        {
            con = DatabaseFactory.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(StringTable.GET_USER_CODE);
            ps.setString(1, login);
            ps.setString(2, senha);
            ps.execute();
            ResultSet rset = ps.getResultSet();
            if(rset.next())
                codigo = rset.getInt("codigo");

            closeConnections(ps, con);

            if(Config.DEBUG)
                _log.info("o codigo do usuario " + login + " e " + codigo + "\n");
        }
        catch(SQLException ex)
        {
            _log.warning("Erro ao pegar codigo do usuario:  " + ex);
        }
        return codigo;
    }

    /**
     * Troca a Senha de um Usuario na base de dados

     * @param newPass
     */
    public static void changePassword(String newPass)
    {
        //converte p versao criptografada
        newPass = Criptografia.criptografe(newPass);

        // pega o usuario na tabela.
        String login = UserTable.getInstance().getLastUser();

        boolean ok = false;
        Connection con = null;
        try
        {
            // Conexão SQL
            con = DatabaseFactory.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(StringTable.CHANGE_USER_PASS);
            ps.setString(1, newPass);
            ps.setString(2, login);
            ps.execute();
            closeConnections(ps, con);
            ok = true;
        }
        catch(SQLException e)
        {
            ok = false;
            SystemUtil.showErrorMsg("Erro ao Trocar Senha do Usuario: " + login + "," + e);

        }
        if(ok)
        {
            if(Config.DEBUG)
                _log.info("trocando senha do usuario: " + login + "para: " + newPass + "\n");
            SystemUtil.showMsg("Senha Trocada com Sucesso!");
        }
    }

    /**
     * retorna o status atual dessa conexao
     * @return _status
     */
    public static String getConnectionStatus()
    {
        return _status;
    }

    /**
     * define o status dessa conexao
     * @param state
     */
    public static void setConStatus(String state)
    {
        _status = state;
    }

    /**
     * Le a Tabela de Instalacao Atual
     **/
    public static void readInstallTable()
    {
        Connection con = null;
        if(Config.DEBUG)
            _log.info("lendo tabela de estado da instalacao \n");
        try
        {
            con = DatabaseFactory.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(StringTable.READ_INSTALL);
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
     * seta o nivel de acesso de um usuario
     * @param lvl
     */
    public void setAcessLevel(int lvl)
    {
        Connection con = null;
        try
        {
            con = DatabaseFactory.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(StringTable.UPDATE_USER_ACCESS_LVL);
            ResultSet rset = ps.executeQuery();
            closeConnections(ps, rset, con);

        }
        catch(Exception e)
        {
            if(Config.DEBUG)
                _log.log(Level.SEVERE, "DatabaseManager: Error Updating Users Access Level: " + e.getMessage(), e);
        }
    }

    /**
     * Checa o Nivel e Acesso do Usuario
     * @param usr
     * @return o nivel de acesso desse usuario
     */
    public static int getAccessLevel(String usr)
    {
        Connection con = null;
        if(Config.DEBUG)
            _log.info("checando o nivel de acesso do usuario " + usr + "\n");

        int level = 0;
        try
        {
            con = DatabaseFactory.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(StringTable.GET_ACC_LVL);
            ps.setString(1, usr);
            ps.execute();
            ResultSet rset = ps.getResultSet();
            if(rset.next())
                level = rset.getInt("accesslevel");
            // Fecha as Conexoes
            closeConnections(ps, rset, con);
            if(Config.DEBUG)
                _log.info("nivel de acesso do usuario " + usr + " e " + level);
        }
        catch(Exception e)
        {
            _log.log(Level.SEVERE, "DatabaseManager: Error getting access level: " + e.getMessage(), e);
        }
        _log.info("nivel de accesso:  " + level);
        return level;
    }

}
