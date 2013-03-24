/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio.model.persistence.dao;

import com.siscomercio.controller.managers.AppManager;
import com.siscomercio.init.Config;
import com.siscomercio.controller.managers.ExceptionManager;
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
    private String _status = StringTable.STATUS_DISCONNECTED;
    private boolean isDbDeleted;
    public int _installed;
    private int _licensed;
    private String _registeredFor;
    private String _registeredMac;
    private String _registeredMBSN;
    private String _empresa;
    private String _licenceType;
    private String _registeredHDSN;
    private int _numStacoes;

    private Banco()
    {
    }

    /**
     * Registra a Aplicação Baseada nos Dados Fornecidos.
     */
    public void registreAplicacao(String nomeEmpresa, int numEstacoes, String licenceType)
    {
        try (PreparedStatement ps = conexao.prepareStatement(StringTable.REGISTRE_APP))
        {
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
            if (Config.isDebug())
            {
                _log.info("gravando dados do registro no Banco de Dados.");
            }
        }
        catch (SQLException e)
        {
            if (Config.isDebug() || Config.isEnableLog())
            {
                _log.log(Level.SEVERE, "Erro ao Registrar Aplica\u00e7\u00e3o...{0}", e);
                ExceptionManager.ThrowException(e.getMessage(), e);
            }

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

        //checa se os dados estao ok...
        if (Config.isDebug())
        {
            _log.log(Level.INFO, "Banco: Checando dados... \n Senha  = {0} \n User = {1}", new Object[]
            {
                senha, login + "\n"
            });
        }

        //reduz para lower case.
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

        try
        {
            try (PreparedStatement ps = conexao.prepareStatement(StringTable.CHECK_USER_PASS); ResultSet rs = ps.getResultSet();)
            {
                ps.setInt(1, userCode);
                ps.setString(2, login);
                ps.setString(3, senha);
                ps.execute();

                if (rs.next())
                {
                    // pega os dados
                    logindb = rs.getString("login");
                    senhadb = rs.getString("password");
                }
            }

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
            if (Config.isDebug() || Config.isEnableLog())
            {
                _log.log(Level.SEVERE, "SQLException: {0}\n SQLState: {1}\n VendorError: {2}", new Object[]
                {
                    ex.getMessage(), ex.getSQLState(), ex.getErrorCode()
                });
            }
            SystemUtil.showErrorMsg("SQLException: " + ex.getMessage() + "\n SQLState: " + ex.getSQLState() + "\n VendorError: " + ex.getErrorCode(), true);

        }
        catch (Exception e)
        {
            if (Config.isDebug() || Config.isEnableLog())
            {
                _log.log(Level.SEVERE, "SQLException: {0}", e.getMessage());
            }
            SystemUtil.showErrorMsg("Problemas ao tentar conectar com o banco de dados" + e, true);
        }
        return true;
    }

    /**
     * Instala Novo Banco
     */
    public void instaleBanco()
    {
        if (Config.isDebug())
        {
            _log.info("\n tentando Instalar Database...");
        }
        criaNovaBase();
        executaTabelasMySQL();
        executeQuery(StringTable.INSTALL);
        setInstalled(true);
        SystemUtil.showMsg("Banco de Dados Instalado com Sucesso!", true);
    }

    /**
     * Deleta a Database Atual
     */
    public void dropDatabase()
    {
        executeQuery(StringTable.DELETE_DB);
        SystemUtil.showMsg("Banco de Dados Deletado!", true);
        setInstalled(false);
        setIsDbDeleted(true);
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
        try
        {
            String sql = "select `login` from users where `login` like '" + value + "';";

            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.execute();
            ResultSet rset = ps.getResultSet();
            if (rset.next())
            {
                if (showMsg)
                {
                    SystemUtil.showErrorMsg("Login já Cadastrado.", true);
                }
                return true;
            }
        }
        catch (SQLException ex)
        {
            if (Config.isDebug() || Config.isEnableLog())
            {
                _log.log(Level.SEVERE, "Erro:  {0}", ex);
            }
            ExceptionManager.ThrowException("Erro: ", ex);
        }
        return false;

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
        try (PreparedStatement ps = conexao.prepareStatement(StringTable.INSERT_USER))
        {
            ps.setString(1, login);
            ps.setString(2, senha);
            ps.setInt(3, nivelAcesso);
            ps.execute();
            //  SystemUtil.showMsg("usuario cadastrado com sucesso!", true);
        }
        catch (SQLException e)
        {
            if (Config.isDebug() || Config.isEnableLog())
            {
                _log.log(Level.SEVERE, "erro {0}", e);
            }
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
        if (Config.isDebug())
        {
            _log.info("Procurando codigo do Usuario.. \n");
        }
        int codigo = StringTable.DEFAULT_INT;

        try (PreparedStatement ps = conexao.prepareStatement(StringTable.GET_USER_CODE); ResultSet rset = ps.getResultSet();)
        {
            ps.setString(1, login);
            ps.setString(2, senha);
            ps.execute();

            if (rset.next())
            {
                codigo = rset.getInt("codigo");
            }
        }
        catch (SQLException ex)
        {
            if (Config.isDebug() || Config.isEnableLog())
            {
                _log.log(Level.WARNING, "Erro ao pegar codigo do usuario: {0}", ex);
            }
            ExceptionManager.ThrowException("Erro ao pegar codigo do usuario:", ex);
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
        if (Config.isDebug())
        {
            _log.log(Level.INFO, "checando o nivel de acesso do usuario {0}\n", usr);
        }

        int level = StringTable.DEFAULT_INT;
        try
        {
            PreparedStatement ps = conexao.prepareStatement(StringTable.GET_ACC_LVL);
            ps.setString(1, usr);
            ps.execute();
            ResultSet rset = ps.getResultSet();
            if (rset.next())
            {
                level = rset.getInt("accesslevel");
            }
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
            if (Config.isDebug() || Config.isEnableLog())
            {
                _log.log(Level.SEVERE, "DatabaseManager: Error getting access level: " + e.getMessage(), e);
            }
        }
        return level;
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
    public void readInstallationState()
    {
        if (Config.isDebug())
        {
            _log.info("lendo tabela de estado da instalacao \n");
        }
        try
        {
            if (conexao == null)
            {
                if (Config.isDebug())
                {
                    _log.info("conexao nula");
                }
                return;
            }
            consultaPreparada = conexao.prepareStatement(StringTable.READ_INSTALL);
            ResultSet rset = consultaPreparada.executeQuery();
            while (rset.next())
            {
                this.setInstalled(rset.getInt("bancoInstalado") == 1 ? true : false);
            }

            if (Config.isDebug())
            {
                _log.log(Level.INFO, "Estado da Instala\u00e7\u00e3o: {0} ok .....\n", _installed);
            }
        }
        catch (Exception e)
        {
            if (Config.isDebug() || Config.isEnableLog())
            {
                _log.log(Level.SEVERE, "DatabaseManager: Error reading Install Table: {0}", e.getMessage());
            }
            ExceptionManager.ThrowException(e.getMessage(), e);
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
        try (PreparedStatement ps = conexao.prepareStatement(StringTable.READ_APP_LICENSE_DATA); ResultSet rset = ps.executeQuery();)
        {
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
            setLicensed(_licensed);
            if (Config.isDebug())
            {
                _log.log(Level.INFO, "Status da Licenca: {0}\n", _licensed);
            }
        }
        catch (Exception e)
        {
            if (Config.isDebug() || Config.isEnableLog())
            {
                _log.log(Level.SEVERE, "DatabaseManager: Error reading License Data: "
                        + e.getMessage(), e);
            }
            ExceptionManager.ThrowException("Erro: ", e);
        }
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
        if (Config.isDebug())
        {
            _log.info("verificando existencia da base de dados..");
        }
        //String url2 = null;
        try
        {

            //regstra o driver
            Class.forName(Config.getDatabaseDriver()).newInstance();

            //conecta ao servidor mysql sem database selecionada
            // url2 = DriverManager.getConnection("jdbc:mysql://" + Config.getHost() + ":" + Config.getDatabasePort() + "/", Config.getDatabaseLogin(), Config.getDatabasePassword());
            // "jdbc:mysql://" + Config.getHost() + "/";

            conexao = DriverManager.getConnection("jdbc:mysql://" + Config.getHost() + ":" + Config.getDatabasePort() + "/", Config.getDatabaseLogin(), Config.getDatabasePassword()); //DriverManager.getConnection(url2, Config.getDatabaseLogin(), Config.getDatabasePassword());
            try (Statement st = conexao.createStatement())
            {
                st.executeUpdate(StringTable.CREATE_DB);
            }
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex)
        {
            if (Config.isDebug() || Config.isEnableLog())
            {
                _log.log(Level.SEVERE, "Erro: {0}", ex.getMessage());
            }
            ExceptionManager.ThrowException("Erro ao Criar Nova Base de Dados: ", ex);
        }
    }

    /**
     * ler eExecuta todos os Scripts SQL dentro da pasta SQL
     *
     */
    public void executaTabelasMySQL()
    {
        if (Config.isDebug())
        {
            _log.info("carregando tabelas do banco...");
        }

        File pasta = new File(StringTable.getSQL_PATH());
        for (File f : pasta.listFiles())
        {
            if (f != null && f.getName().endsWith(".sql"))
            {

                String thisLine, sqlQuery;
                try (BufferedReader br = new BufferedReader(new FileReader(StringTable.getSQL_PATH() + f.getName())))
                {
                    sqlQuery = "";

                    //Now read line by line
                    while ((thisLine = br.readLine()) != null)
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
                                if (Config.isEnableLog() || Config.isDebug())
                                {
                                    _log.log(Level.SEVERE, "Falha ao Executar Script SQL:  {0}", ex.getMessage());
                                }
                                ExceptionManager.ThrowException("Falha ao Executar Script SQL:  ", f.getName(), ex);
                            }

                            sqlQuery = "";
                        }

                    }
                }
                catch (Exception ex)
                {
                    if (Config.isEnableLog() || Config.isDebug())
                    {
                        _log.log(Level.SEVERE, "Falha ao Executar Script SQL:  {0}", ex.getMessage());
                    }
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
                        if (Config.isEnableLog() || Config.isDebug())
                        {
                            _log.log(Level.SEVERE, "Falha ao Executar Script SQL:  {0}", ex.getMessage());
                        }
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

            url = "jdbc:mysql://" + Config.getHost() + ":" + Config.getDatabasePort() + "/" + Config.getDatabase();

            if (Config.isDebug())
            {
                _log.info(url);
            }
            // Este é um dos meios para registrar um driver
            Class.forName(Config.getDatabaseDriver()).newInstance();
            conexao = DatabaseFactory.getInstance().getConnection();// DriverManager.getConnection(url, Config.getDatabaseLogin(), Config.getDatabasePassword());
            if (Config.isDebug())
            {
                _log.info("Conectado com Sucesso!!");
            }
        }
        catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex)
        {
            if (Config.isEnableLog() || Config.isDebug())
            {
                _log.log(Level.SEVERE, "Erro ao Conectar na Base de Dados {0}", ex.getMessage());
            }
            ExceptionManager.ThrowException("Erro ao Conectar na Base de Dados ", url, ex);
        }


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
            try (Statement st = conexao.createStatement())
            {
                st.executeQuery(query);
            }
        }
        catch (SQLException ex)
        {
            if (Config.isEnableLog() || Config.isDebug())
            {
                _log.log(Level.SEVERE, "Nao Foi Possivel Executar a Query: {0}", ex.getMessage());
            }
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


        try (Statement st = conexao.createStatement())
        {
            resultado = st.executeQuery(query);
        }
        catch (SQLException ex)
        {
            if (Config.isEnableLog() || Config.isDebug())
            {
                _log.log(Level.SEVERE, "Erro ao Retornar ResultSet: {0}", ex.getMessage());
            }
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
        // Criptografa a Senha do Usuario
        String senhaCrypto = Criptografia.criptografe(user.getSenhaAtual());

        // ---------------------------------
        // Le a Tabela de Usuarios da Database
        //--------------------------------------
        String logindb = null;
        String senhadb = null;
        try (PreparedStatement ps = conexao.prepareStatement(StringTable.CHECK_USER_PASS); ResultSet rset = ps.getResultSet();)
        {
            ps.setString(1, user.getLogin());
            ps.setString(2, senhaCrypto);
            ps.execute();

            // pega os dados
            if (rset.next())
            {
                logindb = rset.getString("login");
                senhadb = rset.getString("senha");
            }

            // Compara as Senhas Digitadas Pelo Usuario com a DB
            if (user.getLogin().equalsIgnoreCase(logindb) && (senhaCrypto.equalsIgnoreCase(senhadb)))
            {
                return true;
            }

        }
        catch (Exception ex)
        {
            if (Config.isEnableLog() || Config.isDebug())
            {
                _log.log(Level.SEVERE, "Erro ao Validar Senha Usuario: {0}", ex.getMessage());
            }
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
        try (PreparedStatement ps = conexao.prepareStatement(StringTable.getINSERT_TECNICO()))
        {
            ps.setString(1, nome);
            ps.setString(2, sobrenome);
            ps.execute();
            return true;
        }
        catch (SQLException ex)
        {
            if (Config.isEnableLog() || Config.isDebug())
            {
                _log.log(Level.SEVERE, "Erro ao Inserir Tecnico: {0}", ex.getMessage());
            }
            ExceptionManager.ThrowException("Erro ao Inserir Tecnico: ", query, ex);

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
        if (Config.isDebug())
        {
            _log.log(Level.INFO, "Adcionando Usuario: {0}", f.getNome() + f.getSenhaAtual());
        }

        String senhaCripto = Criptografia.criptografe(f.getSenhaAtual());

        if (Config.isDebug())
        {
            _log.log(Level.INFO, "Senha Cripto: {0}", senhaCripto);
        }


        try (PreparedStatement ps = conexao.prepareStatement(StringTable.getINSERT_USER()))
        {
            ps.setString(1, f.getLogin());
            ps.setString(2, senhaCripto);
            ps.setInt(3, f.getNivelAcesso());
            ps.setString(4, f.getNome());
            ps.setString(5, e.getRua());
            ps.setString(6, f.getCargo());
            ps.execute();
            return true;
        }
        catch (SQLException ex)
        {
            if (Config.isEnableLog() || Config.isDebug())
            {
                _log.log(Level.SEVERE, "Erro ao Adcionar Usu\u00e1rio: {0}", ex.getMessage());
            }
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
        try (PreparedStatement ps = conexao.prepareStatement(StringTable.DELETE_USER))
        {
            ps.setString(1, login);
            ps.execute();
            return true;
        }
        catch (SQLException ex)
        {
            if (Config.isEnableLog() || Config.isDebug())
            {
                _log.log(Level.SEVERE, "Erro ao Deletar Usu\u00e1rio: {0}", ex.getMessage());
            }
            ExceptionManager.ThrowException("Erro ao Deletar Usuário: ", ex);
        }
        return false;
    }

    /**
     * pega o ultimo codigo usado
     *
     * @return codigo
     */
    /*
     * public int buscaUltimoId()
     * {
     * _log.info("Procurando ultimo codigo gerado na DB..");
     * int codigo = -1;
     * try
     * {
     * consultaPreparada =
     * conexao.prepareStatement(StringTable.getGET_LAST_ENTRADA_ID());
     * consultaPreparada.execute();
     * resultado = consultaPreparada.getResultSet();
     * resultado.next();
     * codigo = resultado.getInt(1);
     *
     * }
     * catch (SQLException ex)
     * {
     * _log.severe(ex.getMessage());
     * ExceptionManager.ThrowException("Erro ao Procurar Ultimno Registro: ",
     * ex);
     * }
     * return codigo;
     * }
     */
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
        try (PreparedStatement ps = conexao.prepareStatement(StringTable.getINSERT_ENTRADA()))
        {
            ps.setInt(1, ent.getNumEnt());
            ps.setString(2, ent.getData());
            ps.setString(3, ent.getUsuario());
            ps.setString(4, ent.getStatus());
            ps.setString(5, ent.getPerifericos());
            ps.setString(6, ent.getDescricao());
            ps.setString(7, ent.getMarca());
            ps.setString(8, ent.getModelo());
            ps.setString(9, ent.getQuantidade());
            ps.setString(10, ent.getResponsavel());
            ps.setString(11, ent.getDefeitos());
            ps.setString(12, ent.getHora());
            ps.execute();
            return true;
        }
        catch (SQLException ex)
        {
            if (Config.isEnableLog() || Config.isDebug())
            {
                _log.log(Level.SEVERE, "Erro ao Salvar Entrada: {0}", ex.getMessage());
            }
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
        String login = UserTable.getInstance().getLastUser();

        if (Config.isDebug())
        {
            _log.log(Level.INFO, "trocando senha de Usuario: {0}", login);
        }
        //converte p versao criptografada
        String passCrypto = Criptografia.criptografe(newPass);

        try (PreparedStatement ps = conexao.prepareStatement(StringTable.CHANGE_USER_PASS))
        {
            ps.setString(1, passCrypto);
            ps.setString(2, login);
            ps.execute();
            return true;
        }
        catch (SQLException ex)
        {
            if (Config.isEnableLog() || Config.isDebug())
            {
                _log.log(Level.SEVERE, "Erro ao Trocar Senha do Usuario: {0}", ex.getMessage());
            }
            ExceptionManager.ThrowException("Erro ao Trocar Senha do Usuario: ", ex);

        }
        return false;
    }

    /**
     *
     * @param login
     * <p/>
     * @return
     */
    public String buscaSenha(String login)
    {
        String senhadb = null;

        try (PreparedStatement ps = conexao.prepareStatement(StringTable.GET_USER_PASS); ResultSet rset = ps.getResultSet();)
        {
            ps.setString(1, login);
            ps.execute();

            if (rset.next())
            {
                senhadb = rset.getString("senha");
            }
        }
        catch (SQLException ex)
        {
            if (Config.isEnableLog() || Config.isDebug())
            {
                _log.log(Level.SEVERE, "Erro ao Buscar Senha: {0}", ex.getMessage());
            }
            ExceptionManager.ThrowException("Erro ao Buscar senha do Usuario: " + login, ex);
        }
        return senhadb;
    }

    public String getRegisteredFor()
    {
        return _registeredFor;
    }

    public void setRegisteredFor(String _registeredFor)
    {
        this._registeredFor = _registeredFor;
    }

    public String getRegisteredMac()
    {
        return _registeredMac;
    }

    public void setRegisteredMac(String _registeredMac)
    {
        this._registeredMac = _registeredMac;
    }

    public String getRegisteredMBSN()
    {
        return _registeredMBSN;
    }

    public void setRegisteredMBSN(String _registeredMBSN)
    {
        this._registeredMBSN = _registeredMBSN;
    }

    public String getEmpresa()
    {
        return _empresa;
    }

    public void setEmpresa(String _empresa)
    {
        this._empresa = _empresa;
    }

    public String getLicenceType()
    {
        return _licenceType;
    }

    public void setLicenceType(String _licenceType)
    {
        this._licenceType = _licenceType;
    }

    public String getRegisteredHDSN()
    {
        return _registeredHDSN;
    }

    public void setRegisteredHDSN(String _registeredHDSN)
    {
        this._registeredHDSN = _registeredHDSN;
    }

    public int getNumStacoes()
    {
        return _numStacoes;
    }

    public void setNumStacoes(int _numStacoes)
    {
        this._numStacoes = _numStacoes;
    }

    public int getLicensed()
    {
        return _licensed;
    }

    public void setLicensed(int _licensed)
    {
        this._licensed = _licensed;
    }

    public boolean getInstalled()
    {
        if (_installed == 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public void setInstalled(boolean installed)
    {
        if (!installed)
        {
            this._installed = 0;
        }
        else
        {
            this._installed = 1;
        }
    }

    public boolean isIsDbDeleted()
    {
        return isDbDeleted;
    }

    public void setIsDbDeleted(boolean isDbDeleted)
    {
        this.isDbDeleted = isDbDeleted;
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