/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio.model.persistence;

import com.siscomercio.controller.managers.AppManager;
import com.siscomercio.init.Config;
import com.siscomercio.controller.managers.ExceptionManager;
import com.siscomercio.init.DatabaseFactory;
import com.siscomercio.model.entity.Endereco;
import com.siscomercio.model.entity.Entrada;
import com.siscomercio.model.entity.Funcionario;
import com.siscomercio.model.entity.Usuario;
import com.siscomercio.model.security.Criptografia;
import com.siscomercio.standards.StringTable;
import com.siscomercio.tables.UserTable;
import com.siscomercio.utilities.DiskUtil;
import com.siscomercio.utilities.MbUtil;
import com.siscomercio.utilities.NetworkUtil;
import com.siscomercio.utilities.SystemUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * $Revision: 688 $ $Author: rayanrpg $ $Date: 2013-03-21 19:34:43 -0300 (Thu,
 * 21 Mar 2013) $
 *
 * @author William Menezes
 *
 */
public class Banco
{
    private static final Logger _log = Logger.getLogger(Banco.class.getName());
    private Connection conexao;
    private Statement consulta;
    private PreparedStatement consultaPreparada;
    private ResultSet resultado;
    private String query;
    /**
     * Status Atual do Banco
     */
    static String _status = StringTable.STATUS_DISCONNECTED;
    /**
     * se o banco foi deletado
     */
    private boolean isDbDeleted;

    public boolean isIsDbDeleted()
    {
        return isDbDeleted;
    }
    /**
     * se o banco esta instalado
     */
    public int _installed;
    /**
     * se a apliacao esta Licenciada
     */
    private int _licensed;
    private String _registeredFor;
    private String _registeredMac;
    private String _registeredMBSN;
    private String _empresa;
    private String _licenceType;
    private String _registeredHDSN;
    private int _numStacoes;

    public int getLicensed()
    {
        return _licensed;
    }

    public void setLicensed(int _licensed)
    {
        this._licensed = _licensed;
    }

    public int getInstalled()
    {
        return _installed;
    }

    public void setInstalled(int installed)
    {
        this._installed = installed;
    }

    /**
     * Registra a Aplicação Baseada nos Dados Fornecidos.
     */
    public void registreAplicacao(String nomeEmpresa, int numEstacoes, String licenceType)
    {
        //"INSERT INTO `install`(bancoInstalado,stationMAC,StationMBSerial,Empresa,
        //stationHDSerial,NumEstacoes,licenseType,registeredFor) VALUES (?,?,?,?,?,?,?,?)";
        Connection con = null;
        try
        {
            //con = DatabaseFactory.getInstance().getConnection();
            PreparedStatement ps =
                    conexao.prepareStatement(StringTable.REGISTRE_APP);
            ps.setInt(1, 1);
            ps.setString(2, NetworkUtil.getMac());
            ps.setString(3, MbUtil.getMotherboardSN());
            ps.setString(4, nomeEmpresa);
            ps.setString(5, DiskUtil.getSerialNumber("C"));
            ps.setInt(6, numEstacoes);
            ps.setString(7, licenceType);
            ps.setString(8, nomeEmpresa);
            ps.setInt(9, 1);
            ps.execute();
            ps.close();
            con.close();
            _log.info("gravando dados do registro no Banco de Dados.");
        }
        catch (SQLException e)
        {
            SystemUtil.showErrorMsg(e.getMessage(), true);

        }
    }

    /**
     *
     * @param login
     * @param senha
     * <p/>
     * @return boolean
     */
    public boolean isAuthed(String login, String senha)
    {
        if (Config.isDebug())
        {
            _log.info("Banco: Checando Usuario e Senha ...\n");
        }

        //  Connection con = null;
        //boolean ok = false;
        try
        {
            if (Config.isDebug())
            //checa se os dados estao ok...
            {
                _log.log(Level.INFO, "Banco: Checando dados... \n Senha  = {0} \n User = {1}", new Object[]
                {
                    senha, login + "\n"
                });
            }


            senha = senha.toLowerCase();
            login = login.toLowerCase();

            // Criptografa a Senha do Usuario
            senha = Criptografia.criptografe(senha);

            // ---------------------------------
            // Le a Tabela de Usuarios da Database
            // ---------------------------------
            int userCode = Banco.getInstance().getUserCode(login, senha);
            String logindb = null;
            String senhadb = null;

            conexao = DatabaseFactory.getInstance().getConnection();
            PreparedStatement ps = conexao.prepareStatement(StringTable.CHECK_USER_PASS);
            ps.setInt(1, userCode);
            ps.setString(2, login);
            ps.setString(3, senha);
            ps.execute();
            ResultSet rset = ps.getResultSet();
            if (rset.next())
            {
                // pega os dados
                logindb = rset.getString("login");
                senhadb = rset.getString("password");
            }
            //devemos fecha todas as conxoes assim que terminado o procedimento.
//            DatabaseManager.closeConnections(ps, rset, con);

            // Compara as Senhas Digitadas Pelo Usuario com a DB
            if (login.equalsIgnoreCase(logindb) && (senha.equalsIgnoreCase(senhadb)))
            {

                UserTable.getInstance().setLastUser(login);
                return true;
            }
            else
            {
                SystemUtil.showErrorMsg("usuario ou senha incorretos!", true);
                return false;
            }

        }
        catch (SQLException ex)
        {
            SystemUtil.showErrorMsg("SQLException: " + ex.getMessage() + "\n SQLState: " + ex.getSQLState() + "\n VendorError: " + ex.getErrorCode(), true);

        }
        catch (Exception e)
        {
            SystemUtil.showErrorMsg("Problemas ao tentar conectar com o banco de dados" + e, true);
        }
        return true;
    }

    /**
     * ler eExecuta todos os Scripts SQL dentro da pasta SQL
     *
     */
    /*
     * public static void readAndExecuteDatabaseScripts()
     * {
     * File pasta = new File(com.siscomercio.tables.StringTable.SQL_PATH);
     * for (File f : pasta.listFiles())
     * {
     * if (f != null && f.getName().endsWith(".sql"))
     * {
     * try
     * {
     *
     * Connection con = null;
     * if (Config.isDebug())
     * {
     * _log.log(Level.INFO, "\n DatabaseManager: executando script {0} \n",
     * f.getName());
     * }
     * String thisLine, sqlQuery = null;
     *
     * con = DatabaseFactory.getInstance().getConnection();
     * BufferedReader d = new BufferedReader(new
     * FileReader(com.siscomercio.tables.StringTable.SQL_PATH + f.getName()));
     * sqlQuery = "";
     * Statement st = null;
     */
    //Now read line by line
    // while ((thisLine = d.readLine()) != null)
    // {
    //   //Skip comments <strong class="highlight">and</strong> empty lines
//                        if (thisLine.length() > 0 && thisLine.charAt(0) == '-' || thisLine.length() == 0 || thisLine.startsWith("/*") || thisLine.endsWith("*/"))
    //   {
    //       continue;
    //    }

    /*
     * sqlQuery = sqlQuery + " " + thisLine;
     *
     * //If one command complete
     * if (sqlQuery.charAt(sqlQuery.length() - 1) == ';')
     * {
     * sqlQuery = sqlQuery.replace(';', ' '); //Remove the ; since jdbc
     * complains
     * try
     * {
     * st = con.createStatement();
     * st.execute(sqlQuery);
     *
     * }
     * catch (SQLException ex)
     * {
     * SystemUtil.showErrorMsg("Erro" + ex, true);
     * }
     *
     * sqlQuery = "";
     * }
     *
     * }
     * closeConnections(st, con);
     *
     *
     * }
     * catch (Exception e)
     * {
     * SystemUtil.showErrorMsg("Falha ao Executar Script SQL: " + f.getName() +
     * " Erro: " + e.getMessage(), true);
     * }
     * }
     * }
     * }
     */
    /**
     * Executa uma query
     *
     * @param query
     * <p/>
     * @return o resultado dessa query como boolean
     */
    /*
     * public static boolean executeQuery(String query)
     * {
     * boolean result = false;
     * Connection con = null;
     * if (Config.isDebug())
     * {
     * _log.log(Level.INFO, "\n Executando Query: {0} \n", query);
     * }
     * try
     * {
     * con = DatabaseFactory.getInstance().getConnection();
     * Statement st = con.createStatement();
     * st.execute(query);
     * closeConnections(st, con);
     * result = true;
     * }
     * catch (SQLException e)
     * {
     * SystemUtil.showErrorMsg("" + e, true);
     * result = false;
     * }
     * return result;
     * }
     */
    /**
     * Cria Nova Database
     */
    /*
     * public static void createNewDatabase()
     * {
     * Connection con = null;
     *
     * if (Config.isDebug())
     * {
     * _log.info("\n criando novo banco. \n ");
     * }
     * try
     * {
     * Class.forName(Config.getDatabaseDriver()).newInstance();
     * con = DriverManager.getConnection("jdbc:mysql://" + Config.getHost() +
     * ":" + Config.getDatabasePort() + "/", Config.getDatabaseLogin(),
     * Config.getDatabasePassword());
     * Statement st = con.createStatement();
     * st.executeUpdate(com.siscomercio.tables.StringTable.CREATE_DB);
     * closeConnections(st, con);
     *
     * }
     * catch (ClassNotFoundException | InstantiationException |
     * IllegalAccessException | SQLException ex)
     * {
     * SystemUtil.showErrorMsg("Erro ao criar nova base de dados: " + ex, true);
     * }
     * }
     *
     * /**
     * Instala Novo Banco
     */
    public void instaleBanco()
    {
        if (Config.isDebug())
        {
            _log.info("\n tentando Instalar Database...");
        }
        //createNewDatabase();
        //readAndExecuteDatabaseScripts();
        //   executeQuery(StringTable.INSTALL);
        _installed = 1;
        SystemUtil.showMsg("Banco de Dados Instalado com Sucesso!", true);
    }

    /**
     * Deleta a Database Atual
     */
    public void dropDatabase()
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
     * <p/>
     * @return
     */
    public boolean valorExistente(String value, boolean showMsg)
    {
        if (Config.isDebug())
        {
            _log.info("checando se o login existe na Database...\n");
        }
        boolean result = false;
        try
        {
            String sql = "select `login` from users where `login` like '" + value
                    + "';";

            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.execute();
            ResultSet rset = ps.getResultSet();
            if (rset.next())
            {
                if (showMsg)
                {
                    SystemUtil.showErrorMsg("Login já Cadastrado.", true);
                }
                result = true;
            }
            else
            {
                result = false;
            }
        }
        catch (SQLException ex)
        {
            ExceptionManager.ThrowException("Erro: ", ex);
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
    public void addUser(String login, String senha, int nivelAcesso)
    {
        if (Config.isDebug())
        {
            _log.log(Level.INFO, "Adcionando Usuario: {0}", login);
        }

        try
        {
            //     conexao = DatabaseFactory.getInstance().getConnection();
            PreparedStatement ps = conexao.prepareStatement(""/*
                     * StringTable.INSERT_USER
                     */);
//      ps.setInt(1, getLastCode() + 1);
            ps.setString(2, login);
            ps.setString(3, senha);
            ps.setInt(4, nivelAcesso);
            ps.execute();
            // closeConnections(ps, con);
            SystemUtil.showMsg("usuario cadastrado com sucesso!", true);
        }
        catch (SQLException e)
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

//    /**
//     * Deleta Usuario do Banco
//     *
//     * @param login
//     */
//    public void delUser(String login)
//    {
//        // Connection con = null;
//        try
//        {
//            //con = DatabaseFactory.getInstance().getConnection();
//            PreparedStatement ps =
//                    conexao.prepareStatement("");//StringTable.DELETE_USER);
//            //  ps.setInt(1, getUserCodeByLogin(login));
//            ps.setString(2, login);
//            ps.execute();
//            SystemUtil.showMsg("usuário excluido com sucesso!", true);
//            // closeConnections(ps, con);
//        }
//        catch (SQLException e)
//        {
//            SystemUtil.showErrorMsg("Erro ao Deletar Usuario: " + login + " , " + e,
//                                    true);
//        }
//    }
    /**
     * pega o ultimo codigo usado
     *
     * @return codigo
     */
    /*
     * public static int getLastCode()
     * {
     *
     * Connection con = null;
     * if (Config.isDebug())
     * {
     * _log.info("Procurando ultimo codigo gerado na DB..");
     * }
     *
     * int codigo = -1;
     * try
     * {
     * con = DatabaseFactory.getInstance().getConnection();
     * PreparedStatement ps =
     * con.prepareStatement(com.siscomercio.tables.StringTable.GET_LAST_CODE);
     * ps.execute();
     * ResultSet rset = ps.getResultSet();
     * rset.next();
     * codigo = rset.getInt(1);
     * closeConnections(ps, con);
     * if (Config.isDebug())
     * {
     * _log.info("ultimo codigo : " + codigo + "\n");
     * }
     *
     * }
     * catch (SQLException ex)
     * {
     * _log.log(Level.WARNING, "Erro ao procurar ultimo codigo gerado na DB: ",
     * ex);
     * }
     * return codigo;
     * }
     *
     * /**
     * pega o codigo do usuario do banco
     *
     * @param login
     * @param senha
     * @return codigo
     */
    public int getUserCode(String login, String senha)
    {
        //  Connection con = null;
        if (Config.isDebug())
        {
            _log.info("Procurando codigo do Usuario.. \n");
        }
        int codigo = -1;
        try
        {
            //  conexao = DatabaseFactory.getInstance().getConnection();
            PreparedStatement ps = conexao.prepareStatement(StringTable.GET_USER_CODE);
            ps.setString(1, login);
            ps.setString(2, senha);
            ps.execute();
            ResultSet rset = ps.getResultSet();
            if (rset.next())
            {
                codigo = rset.getInt("codigo");
            }

            //  closeConnections(ps, con);

            if (Config.isDebug())
            {
                _log.log(Level.INFO, "O codigo do usuario {0} e {1}\n", new Object[]
                {
                    login, codigo
                });
            }
        }
        catch (SQLException ex)
        {
            _log.log(Level.WARNING, "Erro ao pegar codigo do usuario: {0}", ex);
        }
        return codigo;
    }

    /**
     * pega o codigo do usuario do banco
     *
     * @param login
     * <p/>
     * @return codigo
     */
    /*
     * public static int getUserCodeByLogin(String login)
     * {
     * Connection con = null;
     * if (Config.isDebug())
     * {
     * _log.info("Procurando codigo do Usuario.. \n");
     * }
     * int codigo = -1;
     * try
     * {
     * con = DatabaseFactory.getInstance().getConnection();
     * PreparedStatement ps =
     * con.prepareStatement(com.siscomercio.tables.StringTable.GET_USER_CODE_BY_LOGIN);
     * ps.setString(1, login);
     * ps.execute();
     * ResultSet rset = ps.getResultSet();
     * if (rset.next())
     * {
     * codigo = rset.getInt("codigo");
     * }
     *
     * closeConnections(ps, con);
     *
     * if (Config.isDebug())
     * {
     * _log.log(Level.INFO, "O codigo do usuario {0} e {1}\n", new Object[]
     * {
     * login, codigo
     * });
     * }
     * }
     * catch (SQLException ex)
     * {
     * _log.log(Level.WARNING, "Erro ao pegar codigo do usuario: {0}", ex);
     * }
     * return codigo;
     * }
     *
     * /**
     * Troca a Senha de um Usuario na base de dados
     *
     * @param newPass
     */
    /*
     * public static void changePassword(String newPass)
     * {
     * //converte p versao criptografada
     * newPass = Criptografia.criptografe(newPass);
     *
     * // pega o usuario na tabela.
     * String login = UserTable.getInstance().getLastUser();
     *
     * boolean ok = false;
     * Connection con = null;
     * try
     * {
     * // Conexão SQL
     * con = DatabaseFactory.getInstance().getConnection();
     * PreparedStatement ps =
     * con.prepareStatement(com.siscomercio.tables.StringTable.CHANGE_USER_PASS);
     * ps.setString(1, newPass);
     * ps.setString(2, login);
     * ps.execute();
     * closeConnections(ps, con);
     * ok = true;
     * }
     * catch (SQLException e)
     * {
     * ok = false;
     * SystemUtil.showErrorMsg("Erro ao Trocar Senha do Usuario: " + login + ","
     * + e, true);
     *
     * }
     * if (ok)
     * {
     * if (Config.isDebug())
     * {
     * _log.log(Level.INFO, "\n trocando senha do usuario: {0}para: {1}\n", new
     * Object[]
     * {
     * login, newPass
     * });
     * }
     * SystemUtil.showMsg("Senha Trocada com Sucesso!", true);
     * }
     * }
     *
     * /**
     * seta o nivel de acesso de um usuario
     *
     * @param lvl
     */
    /*
     * public void setAcessLevel(int lvl)
     * {
     * if (Config.isDebug())
     * {
     * _log.info("\n setando nivel de acesso para usuario \n");
     * }
     * Connection con = null;
     * try
     * {
     * con = DatabaseFactory.getInstance().getConnection();
     * PreparedStatement ps =
     * con.prepareStatement(com.siscomercio.tables.StringTable.UPDATE_USER_ACCESS_LVL);
     * ResultSet rset = ps.executeQuery();
     * closeConnections(ps, rset, con);
     *
     * }
     * catch (Exception e)
     * {
     * if (Config.isDebug())
     * {
     * _log.log(Level.SEVERE, "DatabaseManager: Error Updating Users Access
     * Level: " + e.getMessage(), e);
     * }
     * }
     * }
     *
     * /**
     * Checa o Nivel e Acesso do Usuario
     *
     * @param usr
     * @return o nivel de acesso desse usuario
     */
    public int getAccessLevel(String usr)
    {

        //Connection con = null;
        if (Config.isDebug())
        {
            _log.log(Level.INFO, "checando o nivel de acesso do usuario {0}\n", usr);
        }

        int level = 0;
        try
        {
            // conex = DatabaseFactory.getInstance().getConnection();
            PreparedStatement ps = conexao.prepareStatement(StringTable.GET_ACC_LVL);
            ps.setString(1, usr);
            ps.execute();
            ResultSet rset = ps.getResultSet();
            if (rset.next())
            {
                level = rset.getInt("accesslevel");
            }
            // Fecha as Conexoes
            //   closeConnections(ps, rset, con);
            if (Config.isDebug())
            {
                _log.log(Level.INFO, "nivel de acesso do usuario {0} e {1} \n", new Object[]
                {
                    usr, level
                });
            }
        }
        catch (Exception e)
        {
            _log.log(Level.SEVERE, "DatabaseManager: Error getting access level: " + e.getMessage(), e);
        }
        _log.log(Level.INFO, "nivel de accesso: {0}\n", level);
        return level;
    }

    /**
     * Close Connections
     *
     * @param ps
     * @param rset
     * @param con
     */
    /*
     * public static void closeConnections(PreparedStatement ps, ResultSet rset,
     * Connection con)
     * {
     * if (Config.isDebug())
     * {
     * System.out.println();
     * }
     * _log.info("Fechando conexoes c/ a database \n");
     * try
     * {
     * ps.close();
     * rset.close();
     * con.close();
     * setConStatus(com.siscomercio.tables.StringTable.STATUS_DISCONNECTED);
     * }
     * catch (SQLException ex)
     * {
     * SystemUtil.showErrorMsg("Erro ao fechar conexoes com o banco de dados!" +
     * ex, true);
     * }
     * }
     *
     * /**
     * Close Connections
     *
     * @param s
     * @param con
     */
    private void closeConnections(Statement s, Connection con)
    {
        if (Config.isDebug())
        {
            _log.info("Fechando conexoes c/ a database \n");
        }
        try
        {
            s.close();
            con.close();
            setConStatus(StringTable.STATUS_DISCONNECTED);
        }
        catch (SQLException ex)
        {
            SystemUtil.showErrorMsg("Erro ao fechar conexoes com o banco de dados!" + ex, true);
        }
    }

    /**
     * retorna o status atual dessa conexao
     *
     * @return _status
     */
    public String getConnectionStatus()
    {
        return _status;
    }

    /**
     * define o status dessa conexao
     *
     * @param state
     */
    public void setConStatus(String state)
    {
        _status = state;
    }

    /**
     * Le a Tabela de Instalacao Atual
     *
     */
    public void tryReadInstallData()
    {
        // Connection con = null;
        if (Config.isDebug())
        {
            _log.info("lendo tabela de estado da instalacao \n");
        }
        try
        {
            // con = DatabaseFactory.getInstance().getConnection();
            conexao.prepareStatement(StringTable.READ_INSTALL);
            ResultSet rset = consultaPreparada.executeQuery();
            while (rset.next())
            {
                _installed = rset.getInt("bancoInstalado");
            }

            //     closeConnections(consultaPreparada, rset, conexao);

            if (Config.isDebug())
            {
                _log.log(Level.INFO, "Estado da Instala\u00e7\u00e3o: {0} ok .....\n", _installed);
            }
        }
        catch (Exception e)
        {
            if (Config.isDebug())
            {
                _log.log(Level.SEVERE, "DatabaseManager: Error reading Install Table: {0}", e.getMessage());
            }
            ExceptionManager.ThrowException("Erro: ", e);
        }
    }

    /**
     * Le os dados da tabela e Instalacao
     *
     */
    public void readLicenseData()
    {

        if (Config.isDebug())
        {
            _log.info("Lendo dados da Licenca \n");
        }
        try
        {
            //conexao = DatabaseFactory.getInstance().getConnection();
            PreparedStatement ps = conexao.prepareStatement(StringTable.READ_APP_LICENSE_DATA);
            ResultSet rset = ps.executeQuery();
            while (rset.next())
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
            //closeConnections(ps, rset, con);
            if (Config.isDebug())
            {
                _log.log(Level.INFO, "Status da Licenca: {0}\n", _licensed);
            }
        }
        catch (Exception e)
        {
            if (Config.isDebug())
            {
                _log.log(Level.SEVERE, "DatabaseManager: Error reading License Data: "
                        + e.getMessage(), e);
            }
        }
    }

    private Banco()
    {
    }

    /**
     * atualiza as tabelas normais, e mantem as de dados do usuario
     */
    public void atualizaDatabase()
    {
        //tenta criar nova base Dados caso nao exista
        criaNovaBase();

        //conecta na base de dados Selecionada.
        conectaDatabaseSelecionada();

        //le e executa todas as tabelas.
        executaTabelasMySQL();

    }

    /**
     * Cria Nova Database
     *
     */
    public void criaNovaBase()
    {
        _log.info("verificando existencia da base de dados..");
        //String url2 = null;
        try
        {

            //regstra o driver
            Class.forName(Config.getDatabaseDriver()).newInstance();

            //conecta ao servidor mysql sem database selecionada
            // url2 = DriverManager.getConnection("jdbc:mysql://" + Config.getHost() + ":" + Config.getDatabasePort() + "/", Config.getDatabaseLogin(), Config.getDatabasePassword());
            // "jdbc:mysql://" + Config.getHost() + "/";

            conexao = DriverManager.getConnection("jdbc:mysql://" + Config.getHost() + ":" + Config.getDatabasePort() + "/", Config.getDatabaseLogin(), Config.getDatabasePassword()); //DriverManager.getConnection(url2, Config.getDatabaseLogin(), Config.getDatabasePassword());
            consulta = conexao.createStatement();
            consulta.executeUpdate(StringTable.CREATE_DB);
            consulta.close();
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex)
        {
            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Erro ao Criar Nova Base de Dados: ", ex);
        }
    }

    /**
     * ler eExecuta todos os Scripts SQL dentro da pasta SQL
     *
     */
    public void executaTabelasMySQL()
    {
        _log.info("carregando tabelas do banco...");

        File pasta = new File(StringTable.getSQL_PATH());
        for (File f : pasta.listFiles())
        {
            if (f != null && f.getName().endsWith(".sql"))
            {
                try
                {


                    String thisLine, sqlQuery;

                    BufferedReader d = new BufferedReader(new FileReader(StringTable.getSQL_PATH() + f.getName()));
                    sqlQuery = "";



                    //Now read line by line
                    while ((thisLine = d.readLine()) != null)
                    {
                        //Skip comments <strong class="highlight">and</strong> empty lines
                        if (thisLine.length() > 0 && thisLine.charAt(0) == '-' || thisLine.length() == 0 || thisLine.startsWith("/*") || thisLine.endsWith("*/"))
                        {
                            continue;
                        }

                        sqlQuery = sqlQuery + " " + thisLine;

                        //If one command complete
                        if (sqlQuery.charAt(sqlQuery.length() - 1) == ';')
                        {
                            sqlQuery = sqlQuery.replace(';', ' '); //Remove the ; since jdbc complains
                            try
                            {
                                consulta = conexao.createStatement();
                                consulta.execute(sqlQuery);

                            }
                            catch (SQLException ex)
                            {

                                _log.severe(ex.getMessage());
                                ExceptionManager.ThrowException("Falha ao Executar Script SQL:  ", f.getName(), ex);
                            }

                            sqlQuery = "";
                        }

                    }

                }
                catch (Exception ex)
                {

                    _log.severe(ex.getMessage());
                    ExceptionManager.ThrowException("Falha ao Executar Script SQL:  ", f.getName(), ex);

                }
                finally
                {
                    try
                    {
                        consulta.close();

                    }
                    catch (SQLException ex)
                    {
                        _log.severe(ex.getMessage());
                        ExceptionManager.ThrowException("Falha ao Executar Script SQL:  ", f.getName(), ex);
                    }
                }
            }
        }


    }

    /**
     * Conecta ao Banco
     *
     */
    public void conectaDatabaseSelecionada()
    {
        String url = null;
        try//A captura de exceções SQLException em Java é obrigatória para usarmos JDBC.
        {

            url = "jdbc:mysql://" + Config.getHost() + "/" + Config.getDatabase();

            // Este é um dos meios para registrar um driver
            Class.forName(Config.getDatabaseDriver()).newInstance();
            conexao = DriverManager.getConnection(url, Config.getDatabaseLogin(), Config.getDatabasePassword());
        }
        catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex)
        {
            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Erro ao Conectar na Base de Dados ", url, ex);
        }


    }

    /**
     *
     * @return
     */
    public boolean fechaConexoes()
    {
        _log.info("Fechando Conexoes com o Banco..");
        try
        {
            if (conexao != null)
            {
                conexao.close();
            }
            if (consulta != null)
            {
                consulta.close();
            }
            if (consultaPreparada != null)
            {
                consultaPreparada.close();
            }
            if (resultado != null)
            {
                resultado.close();
            }
            return true;
        }
        catch (SQLException ex)
        {
            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Erro ao Fechar Conexoes", "", ex);
        }
        return false;
    }

    /**
     *
     * @return
     */
    public Connection getConexao()
    {
        _log.info("Retornando conexao com o Banco De Dados...");

        return conexao;
    }

    /**
     * Executa uma query
     *
     * @param query
     */
    public void executeQuery(String query)
    {
        try
        {
            consulta = conexao.createStatement();
            consulta.executeQuery(query);
        }
        catch (SQLException ex)
        {
            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Nao Foi Possivel Executar a Query: ", query, ex);
        }

    }

    /**
     *
     * @param query
     * <p/>
     * @return
     */
    public ResultSet getResultSet(String query)
    {
        try
        {
            consulta = conexao.createStatement();
            resultado = consulta.executeQuery(query);

        }
        catch (SQLException ex)
        {
            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Erro ao Retornar ResultSet: ", query, ex);
        }
        return resultado;
    }

    /**
     *
     * @param user
     * <p/>
     * @return
     */
    public boolean validaLogin(Usuario user)
    {

        if (Config.isDebug())
        {
            _log.info("Checando Usuario e Senha ...\n");
        }


        try
        {

            // Criptografa a Senha do Usuario
            String senhaCrypto = Criptografia.criptografe(user.getSenhaAtual());

            // ---------------------------------
            // Le a Tabela de Usuarios da Database
            //--------------------------------------
            String logindb = null;
            String senhadb = null;

            //prepara consulta
            consultaPreparada = conexao.prepareStatement(StringTable.CHECK_USER_PASS);
            consultaPreparada.setString(1, user.getLogin());
            consultaPreparada.setString(2, senhaCrypto);
            consultaPreparada.execute();
            resultado = consultaPreparada.getResultSet();

            // pega os dados
            if (resultado.next())
            {
                logindb = resultado.getString("login");
                senhadb = resultado.getString("senha");
            }

            // Compara as Senhas Digitadas Pelo Usuario com a DB
            if (user.getLogin().equalsIgnoreCase(logindb) && (senhaCrypto.equalsIgnoreCase(senhadb)))
            {
                return true;
            }


        }
        catch (Exception ex)
        {
            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Erro ao Validar Senha Usuario: " + user.getLogin(), ex);

        }

        return false;
    }

//    /**
//     *
//     * @return
//     */
//    public boolean tabelaVazia()
//    {
//        try
//        {
//            String sql = "SELECT * FROM ranking ORDER BY pontuacao ASC";
//
//            consultaPreparada = conexao.prepareStatement(sql);
//
//            consultaPreparada.execute();
//            resultado = consultaPreparada.getResultSet();
//
//            if (!resultado.next())
//            {
//                Utilitarios.getInstance().showInfoMessage("Ranking Vazio! Aguarde Pontuacao ser Gerada.");
//                return true;
//            }
//        }
//        catch (SQLException e)
//        {
//            Utilitarios.showErrorMessage(e.getMessage());
//            _log.log(Level.WARNING, "Erro:{0}", e);
//
//        }
//        return false;
//    }
    /**
     *
     * @param nome
     * @param sobrenome
     * <p/>
     * @return
     */
    public boolean cadastraTecnico(String nome, String sobrenome)
    {
        query = StringTable.getINSERT_TECNICO();

        try
        {
            consultaPreparada = conexao.prepareStatement(query);
            consultaPreparada.setString(1, nome);
            consultaPreparada.setString(2, sobrenome);
            consultaPreparada.execute();

            return true;
        }
        catch (SQLException ex)
        {
            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Erro ao Inserir Tecnico: ", query, ex);
        }
        finally
        {
            try
            {
                consultaPreparada.close();

            }
            catch (SQLException ex)
            {
                _log.severe(ex.getMessage());
                ExceptionManager.ThrowException("Erro ao Fechar Conexao: ", ex);
            }
        }

        return false;

    }

    /**
     *
     * @param value
     * @param showMsg
     * <p/>
     * @return
     */
//    public boolean valorExiste(String value)
//    {
//
//        _log.info("checando se o login existe na Database...\n");
//
//        try
//        {
//            String query = "SELECT `login` FROM users WHERE `login` LIKE '" + value + "';";
//
//            consultaPreparada = conexao.prepareStatement(query);
//            consultaPreparada.execute();
//            resultado = consultaPreparada.getResultSet();
//            if (resultado.next())
//            {
//                //Utilitarios.getInstance().showWarningMessage("Login já Cadastrado.");
//                return true;
//            }
//
//        }
//        catch (SQLException ex)
//        {
//            Utilitarios.showErrorMessage("ERRO " + ex.getMessage());
//            _log.log(Level.WARNING, "Erro:{0}", ex);
//        }
//        return false;
//
//    }
    /**
     * Insere Novo Usuario na Base de dados
     *
     * @param f
     * @param e
     * <p/>
     * @return
     */
    public boolean addUser(Funcionario f, Endereco e)
    {
        _log.log(Level.INFO, "Adcionando Usuario: {0}", f.getNome() + f.getSenhaAtual());
        try
        {
            String senhaCripto = Criptografia.criptografe(f.getSenhaAtual());
            _log.log(Level.INFO, "Senha Cripto: {0}", senhaCripto);
            consultaPreparada = conexao.prepareStatement(StringTable.getINSERT_USER());
            consultaPreparada.setString(1, f.getLogin());
            consultaPreparada.setString(2, senhaCripto);
            consultaPreparada.setInt(3, f.getNivelAcesso());
            consultaPreparada.setString(4, f.getNome());
            consultaPreparada.setString(5, e.getRua());
            consultaPreparada.setString(6, f.getCargo());
            consultaPreparada.execute();
            return true;
        }
        catch (SQLException ex)
        {
            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Erro ao Adcionar Usuário: ", ex);
        }
        return false;
    }

    /**
     * Deleta Usuario do Banco
     *
     * @param login
     * <p/>
     * @return
     */
    public boolean delUser(String login)
    {
        try
        {
            consultaPreparada = conexao.prepareStatement(StringTable.DELETE_USER);
            consultaPreparada.setString(1, login);
            consultaPreparada.execute();
            return true;

        }
        catch (SQLException ex)
        {
            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Erro ao Deletar Usuário: ", ex);
        }
        return false;
    }

    /**
     * pega o ultimo codigo usado
     *
     * @return codigo
     */
    public int buscaUltimoId()
    {
        _log.info("Procurando ultimo codigo gerado na DB..");
        int codigo = -1;
        try
        {
            consultaPreparada = conexao.prepareStatement(StringTable.getGET_LAST_ENTRADA_ID());
            consultaPreparada.execute();
            resultado = consultaPreparada.getResultSet();
            resultado.next();
            codigo = resultado.getInt(1);

        }
        catch (SQLException ex)
        {
            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Erro ao Procurar Ultimno Registro: ", ex);
        }
        return codigo;
    }

    /**
     *
     * @param ent
     * <p/>
     * @return
     */
    public boolean salvaEntrada(Entrada ent)
    {
        if (Config.isDebug())
        {
            _log.info("salvando entrada para o banco...");
        }
        try
        {
            consultaPreparada = conexao.prepareStatement(StringTable.getINSERT_ENTRADA());
            consultaPreparada.setInt(1, ent.getNumEnt());
            consultaPreparada.setString(2, ent.getData());
            consultaPreparada.setString(3, ent.getUsuario());
            consultaPreparada.setString(4, ent.getStatus());
            consultaPreparada.setString(5, ent.getPerifericos());
            consultaPreparada.setString(6, ent.getDescricao());
            consultaPreparada.setString(7, ent.getMarca());
            consultaPreparada.setString(8, ent.getModelo());
            consultaPreparada.setString(9, ent.getQuantidade());
            consultaPreparada.setString(10, ent.getResponsavel());
            consultaPreparada.setString(11, ent.getDefeitos());
            consultaPreparada.setString(12, ent.getHora());
            consultaPreparada.execute();
            return true;
        }
        catch (SQLException ex)
        {
            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Erro ao Salvar Entrada: ", ex);
        }
        return false;
    }

    /**
     * Troca a Senha de um Usuario na base de dados
     *
     * @param newPass
     * <p/>
     * @return
     */
    public boolean changePassword(String newPass)
    {
        // pega o usuario na tabela.
        String login = "";// UserTable.getInstance().getLastUser().getLogin();

        if (Config.isDebug())
        {
            _log.log(Level.INFO, "trocando senha de Usuario: {0}", login);
        }
        //converte p versao criptografada
        String passCrypto = Criptografia.criptografe(newPass);

        try
        {
            consultaPreparada = conexao.prepareStatement(StringTable.CHANGE_USER_PASS);
            consultaPreparada.setString(1, passCrypto);
            consultaPreparada.setString(2, login);
            consultaPreparada.execute();
            return true;
        }
        catch (SQLException ex)
        {
            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Erro ao Trocar Senha do Usuario: ", ex);
            return false;
        }
    }

    /**
     *
     * @param login
     * <p/>
     * @return
     */
    public String buscaSenha(String login)
    {
        String senhadb = "";
        try
        {

            consultaPreparada = conexao.prepareStatement(StringTable.GET_USER_PASS);
            consultaPreparada.setString(1, login);
            consultaPreparada.execute();

            resultado = consultaPreparada.getResultSet();

            // pega os dados
            if (resultado.next())
            {
                senhadb = resultado.getString("senha");
            }
        }
        catch (SQLException ex)
        {
            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Erro ao Buscar senha do Usuario: " + login, ex);
        }
        return senhadb;
    }

    /**
     *
     * @return
     */
    public static Banco getInstance()
    {
        return SingletonHolder._instance;
    }
    private static class SingletonHolder
    {
        protected static final Banco _instance = new Banco();
    }
}
