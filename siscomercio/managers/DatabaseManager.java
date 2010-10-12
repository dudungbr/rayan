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
    private static final Logger _log = Logger.getLogger(DatabaseManager.class.getName());
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
    public static int _installed;
    /**
     *se a apliacao esta Licenciada
     */
    public static int _licensed;
    /**
     *
     */
    /**
     *
     */
    public static String _registeredFor;
    /**
     *
     */
    /**
     * 
     */
    public static String _registeredMac;
    /**
     *
     */
    /**
     *
     */
    public static String _registeredMBSN;
    /**
     *
     */
    /**
     *
     */
    public static String _empresa;
    /**
     *
     */
    /**
     *
     */
    public static String _licenceType;
    /**
     *
     */
    /**
     *
     */
    public static String _registeredHDSN;
    /**
     *
     */
    /**
     *
     */
    public static int _numStacoes;

    // public static boolean _leuTabela;
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
                        _log.log(Level.INFO, "\n DatabaseManager: executando script {0} \n", f.getName());
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
                                SystemUtil.showErrorMsg("Erro" + ex, true);
                            }

                            sqlQuery = "";
                        }

                    }
                    closeConnections(st, con);


                }
                catch(Exception e)
                {
                    SystemUtil.showErrorMsg("Falha ao Executar Script SQL:  " + f.getName() + " Erro:  " + e.getMessage(), true);
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
            _log.log(Level.INFO, "\n Executando Query: {0} \n", query);
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
            SystemUtil.showErrorMsg("" + e, true);
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
            _log.info("\n criando novo banco. \n ");
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
            SystemUtil.showErrorMsg("Erro ao criar nova base de dados: " + ex, true);
        }
    }

    /**
     * Instala Novo Banco
     */
    public static void instaleBanco()
    {
        if(Config.DEBUG)
            _log.info("\n tentando Instalar Database...");
        createNewDatabase();
        readAndExecuteDatabaseScripts();
        executeQuery(StringTable.INSTALL);
        _installed = 1;
        SystemUtil.showMsg("Banco de Dados Instalado com Sucesso!", true);
    }

    /**
     * Deleta a Database Atual
     */
    public static void dropDatabase()
    {
        executeQuery(StringTable.DELETE_DB);
        SystemUtil.showMsg("Banco de Dados Deletado!", true);
        _installed = 0;
        isDbDeleted = true;
    }

    /**
     *
     * @param value
     * @param showMsg
     * @return
     */
    public static boolean valorExistente(String value,boolean showMsg)
    {
          if(Config.DEBUG)
            _log.info("checando se o login existe na Database...\n");
        Connection con = null;
        boolean result = false;
        try
        {
            String sql = "select `login` from users where `login` like '" + value + "';";
            con = DatabaseFactory.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            ResultSet rset = ps.getResultSet();
            if(rset.next())
            {
                if(showMsg)
                SystemUtil.showErrorMsg("Login já Cadastrado.",true);
                result = true;
            }
            else
            {
                result = false;
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            SystemUtil.showErrorMsg("ERRO " + ex.getMessage(), true);
        }
        return result;

    }

    /**
     * Insere Novo Usuario na Base de dados
     *
     * @param login
     * @param senha
     * @param nivelAcesso
     */
    public static void addUser(String login, String senha, int nivelAcesso)
    {
        if(Config.DEBUG)
            _log.info("Adcionando Usuario: " + login);
        Connection con = null;
        try
        {
            con = DatabaseFactory.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(StringTable.INSERT_USER);
            ps.setInt(1, getLastCode() + 1);
            ps.setString(2, login);
            ps.setString(3, senha);
            ps.setInt(4, nivelAcesso);
            ps.execute();
            closeConnections(ps, con);
            SystemUtil.showMsg("usuario cadastrado com sucesso!", true);
        }
        catch(SQLException e)
        {
            SystemUtil.showErrorMsg(e.toString(), true);
        }
    }

    /**
     * Edita os Dados de um Usuario da Database
     */
    public static void editUser()
    {
        AppManager.implementar();
    }

    /**
     * Deleta Usuario do Banco
     * @param login
     */
    public static void delUser(String login)
    {
        Connection con = null;
        try
        {
            con = DatabaseFactory.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(StringTable.DELETE_USER);
            ps.setInt(1, getUserCodeByLogin(login));
            ps.setString(2, login);
            ps.execute();
            SystemUtil.showMsg("usuário excluido com sucesso!", true);
            closeConnections(ps, con);
        }
        catch(SQLException e)
        {
            SystemUtil.showErrorMsg("Erro ao Deletar Usuario: " + login + " , " + e, true);
        }
    }

    /**
     * pega o ultimo codigo usado
     * @return codigo
     */
    public static int getLastCode()
    {

        Connection con = null;
        if(Config.DEBUG)
            _log.info("Procurando ultimo codigo gerado na DB..");

        int codigo = -1;
        try
        {
            con = DatabaseFactory.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(StringTable.GET_LAST_CODE);
            ps.execute();
            ResultSet rset = ps.getResultSet();
            rset.next();
            codigo = rset.getInt(1);
            closeConnections(ps, con);
            if(Config.DEBUG)
                _log.info("ultimo codigo : " + codigo + "\n");

        }
        catch(SQLException ex)
        {
            _log.log(Level.WARNING, "Erro ao procurar ultimo codigo gerado na DB: ", ex);
        }
        return codigo;
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
            _log.info("Procurando codigo do Usuario.. \n");
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
                _log.log(Level.INFO, "O codigo do usuario {0} e {1}\n", new Object[]
                        {
                            login, codigo
                        });
        }
        catch(SQLException ex)
        {
            _log.log(Level.WARNING, "Erro ao pegar codigo do usuario:  {0}", ex);
        }
        return codigo;
    }
/**
     * pega o codigo do usuario do banco
     * @param login
     * @param senha
     * @return codigo
     */
    public static int getUserCodeByLogin(String login)
    {
        Connection con = null;
        if(Config.DEBUG)
            _log.info("Procurando codigo do Usuario.. \n");
        int codigo = -1;
        try
        {
            con = DatabaseFactory.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(StringTable.GET_USER_CODE_BY_LOGIN);
            ps.setString(1, login);
            ps.execute();
            ResultSet rset = ps.getResultSet();
            if(rset.next())
                codigo = rset.getInt("codigo");

            closeConnections(ps, con);

            if(Config.DEBUG)
                _log.log(Level.INFO, "O codigo do usuario {0} e {1}\n", new Object[]
                        {
                            login, codigo
                        });
        }
        catch(SQLException ex)
        {
            _log.log(Level.WARNING, "Erro ao pegar codigo do usuario:  {0}", ex);
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
            SystemUtil.showErrorMsg("Erro ao Trocar Senha do Usuario: " + login + "," + e, true);

        }
        if(ok)
        {
            if(Config.DEBUG)
                _log.log(Level.INFO, "\n trocando senha do usuario: {0}para: {1}\n", new Object[]
                        {
                            login, newPass
                        });
            SystemUtil.showMsg("Senha Trocada com Sucesso!", true);
        }
    }

    /**
     * seta o nivel de acesso de um usuario
     * @param lvl
     */
    public void setAcessLevel(int lvl)
    {
        if(Config.DEBUG)
            _log.info("\n setando nivel de acesso para usuario \n");
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
            _log.log(Level.INFO, "checando o nivel de acesso do usuario {0}\n", usr);

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
                _log.log(Level.INFO, "nivel de acesso do usuario {0} e {1} \n", new Object[]
                        {
                            usr, level
                        });
        }
        catch(Exception e)
        {
            _log.log(Level.SEVERE, "DatabaseManager: Error getting access level: " + e.getMessage(), e);
        }
        _log.log(Level.INFO, "nivel de accesso:  {0}\n", level);
        return level;
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
            System.out.println();
        _log.info("Fechando conexoes c/ a database \n");
        try
        {
            ps.close();
            rset.close();
            con.close();
            setConStatus(StringTable.STATUS_DISCONNECTED);
        }
        catch(SQLException ex)
        {
            SystemUtil.showErrorMsg("Erro ao fechar conexoes com o banco de dados!" + ex, true);
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
            _log.info("Fechando conexoes c/ a database \n");
        try
        {
            s.close();
            con.close();
            setConStatus(StringTable.STATUS_DISCONNECTED);
        }
        catch(SQLException ex)
        {
            SystemUtil.showErrorMsg("Erro ao fechar conexoes com o banco de dados!" + ex, true);
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
     *
     */
    public static void tryReadInstallData()
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
                _installed = rset.getInt("bancoInstalado");
            }

            closeConnections(ps, rset, con);

            if(Config.DEBUG)
                _log.info("Estado da Instalação: " + _installed + " ok .....\n");
        }
        catch(Exception e)
        {
            if(Config.DEBUG)
                _log.severe("DatabaseManager: Error reading Install Table: " + e.getMessage());
        }
    }

    /**
     * Le os dados da tabela e Instalacao
     **/
    public static void readLicenseData()
    {
        Connection con = null;
        if(Config.DEBUG)
            _log.info("Lendo dados da Licenca \n");
        try
        {
            con = DatabaseFactory.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(StringTable.READ_APP_LICENSE_DATA);
            ResultSet rset = ps.executeQuery();
            while(rset.next())
            {
                _registeredMac = rset.getString("stationMAC");
                _registeredMBSN = rset.getString("stationMBSerial");
                _empresa = rset.getString("Empresa");
                _registeredHDSN = rset.getString("stationHDSerial");
                _numStacoes = rset.getInt("NumEstacoes");
                _licenceType = rset.getString("licenseType");
                _registeredFor = rset.getString("registeredFor");
                _licensed = rset.getInt("licenciado");

            }
            closeConnections(ps, rset, con);
            if(Config.DEBUG)
                _log.info("Status da Licenca: " + _licensed + "\n");
        }
        catch(Exception e)
        {
            if(Config.DEBUG)
                _log.log(Level.SEVERE, "DatabaseManager: Error reading License Data: " + e.getMessage(), e);
        }
    }

}
