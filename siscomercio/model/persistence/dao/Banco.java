/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio.model.persistence.dao;

import com.siscomercio.controller.managers.AppManager;
import com.siscomercio.controller.managers.ExceptionManager;
import com.siscomercio.init.Config;
import com.siscomercio.model.entity.Endereco;
import com.siscomercio.model.entity.Entrada;
import com.siscomercio.model.entity.Funcionario;
import com.siscomercio.model.entity.Usuario;
import com.siscomercio.crypt.Criptografia;
import com.siscomercio.standards.StringTable;
import com.siscomercio.tables.UserTable;
import com.siscomercio.utilities.DiskUtil;
import com.siscomercio.utilities.MbUtil;
import com.siscomercio.utilities.NetworkUtil;
import com.siscomercio.utilities.SystemUtil;
import com.siscomercio.utilities.Utilitarios;
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
    private Connection conexaoBanco;
    private Connection conexaoMySQL;
    private Statement consulta;
    private PreparedStatement consultaPreparada;
    private ResultSet resultado;
    private String query;
    private StringTable stringTable;
    private Config config;
    private SystemUtil util;
    private String _status = "N/A";
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
        this.stringTable = StringTable.getInstance();
        this.config = Config.getInstance();
        this.util = SystemUtil.getInstance();
    }

    private boolean isConnected()
    {
        if (conexaoBanco != null)
        {
            this._status = stringTable.getSTATUS_CONNECTED();
            return true;
        }
        this._status = stringTable.getSTATUS_DISCONNECTED();
        return false;
    }

    /**
     * Registra a Aplicação Baseada nos Dados Fornecidos.
     */
    public void registreAplicacao(String nomeEmpresa, int numEstacoes, String licenceType)
    {
        try (PreparedStatement ps = conexaoBanco.prepareStatement(stringTable.getREGISTRE_APP()))
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
            if (config.isDebug())
            {
                _log.info("gravando dados do registro no Banco de Dados.");
            }
        }
        catch (SQLException e)
        {
            if (config.isDebug() || config.isEnableLog())
            {
                _log.log(Level.SEVERE, "Erro ao Registrar Aplica\u00e7\u00e3o...{0}", e);
                ExceptionManager.ThrowException(e.getMessage(), e);
            }

        }
        setLicensed(1);
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
        if (config.isDebug())
        {
            _log.info("Banco: Checando Usuario e Senha ...\n");
        }

        //checa se os dados estao ok...
        if (config.isDebug())
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
            try (PreparedStatement ps = conexaoBanco.prepareStatement(stringTable.getCHECK_USER_PASS()))
            {
                ps.setInt(1, userCode);
                ps.setString(2, login);
                ps.setString(3, senha);
                ps.execute();
                try (ResultSet rs = ps.getResultSet())
                {
                    if (rs.next())
                    {
                        // pega os dados
                        logindb = rs.getString("login");
                        senhadb = rs.getString("password");
                    }
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
                util.showErrorMsg("usuario ou senha incorretos!", true);
                return false;
            }

        }
        catch (SQLException ex)
        {
            if (config.isDebug() || config.isEnableLog())
            {
                _log.log(Level.SEVERE, "SQLException: {0}\n SQLState: {1}\n VendorError: {2}", new Object[]
                {
                    ex.getMessage(), ex.getSQLState(), ex.getErrorCode()
                });
            }
            util.showErrorMsg("SQLException: " + ex.getMessage() + "\n SQLState: " + ex.getSQLState() + "\n VendorError: " + ex.getErrorCode(), true);

        }
        catch (Exception e)
        {
            if (config.isDebug() || config.isEnableLog())
            {
                _log.log(Level.SEVERE, "SQLException: {0}", e.getMessage());
            }
            util.showErrorMsg("Problemas ao tentar conectar com o banco de dados" + e, true);
        }
        return true;
    }

    /**
     * Deleta a Database Atual
     */
    public void dropDatabase()
    {
        executeQuery(stringTable.getDELETE_DB());
        util.showMsg("Banco de Dados Deletado!", true);
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
        if (config.isDebug())
        {
            _log.info("checando se o login existe na Database...\n");
        }
        try
        {
            String sql = "select `login` from users where `login` like '" + value + "';";

            PreparedStatement ps = conexaoBanco.prepareStatement(sql);
            ps.execute();
            ResultSet rset = ps.getResultSet();
            if (rset.next())
            {
                if (showMsg)
                {
                    util.showErrorMsg("Login já Cadastrado.", true);
                }
                return true;
            }
        }
        catch (SQLException ex)
        {
            if (config.isDebug() || config.isEnableLog())
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
        if (config.isDebug())
        {
            _log.log(Level.INFO, "Adcionando Usuario: {0}", login);
        }
        try (PreparedStatement ps = conexaoBanco.prepareStatement(stringTable.getINSERT_USER()))
        {
            ps.setString(1, login);
            ps.setString(2, senha);
            ps.setInt(3, nivelAcesso);
            ps.execute();
            // util.showMsg("usuario cadastrado com sucesso!", true);
        }
        catch (SQLException e)
        {
            if (config.isDebug() || config.isEnableLog())
            {
                _log.log(Level.SEVERE, "erro {0}", e);
            }
            util.showErrorMsg(e.toString(), true);
        }
    }

    /**
     * Edita os Dados de um Usuario da Database
     */
    public static void editUser()
    {
        AppManager.getInstance().implementar();
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
//                    conexao.prepareStatement("");//defaults.getDELETE_USER);
//            //  ps.setInt(1, getUserCodeByLogin(login));
//            ps.setString(2, login);
//            ps.execute();
//           util.showMsg("usuário excluido com sucesso!", true);
//            // closeConnections(ps, con);
//        }
//        catch (SQLException e)
//        {
//           util.showErrorMsg("Erro ao Deletar Usuario: " + login + " , " + e,
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
     * if (config.isDebug())
     * {
     * _log.info("Procurando ultimo codigo gerado na DB..");
     * }
     *
     * int codigo = -1;
     * try
     * {
     * con = DatabaseFactory.getInstance().getConnection();
     * PreparedStatement ps =
     * con.prepareStatement(com.siscomercio.tables.defaults.getGET_LAST_CODE);
     * ps.execute();
     * ResultSet rset = ps.getResultSet();
     * rset.next();
     * codigo = rset.getInt(1);
     * closeConnections(ps, con);
     * if (config.isDebug())
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
        if (config.isDebug())
        {
            _log.info("Procurando codigo do Usuario.. \n");
        }
        int codigo = stringTable.getDEFAULT_INT();

        try (PreparedStatement ps = conexaoBanco.prepareStatement(stringTable.getGET_USER_CODE()))
        {
            ps.setString(1, login);
            ps.setString(2, senha);
            ps.execute();
            try (ResultSet rset = ps.getResultSet())
            {
                if (rset.next())
                {
                    codigo = rset.getInt("codigo");
                }
            }
        }
        catch (SQLException ex)
        {
            if (config.isDebug() || config.isEnableLog())
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
     * if (config.isDebug())
     * {
     * _log.info("Procurando codigo do Usuario.. \n");
     * }
     * int codigo = -1;
     * try
     * {
     * con = DatabaseFactory.getInstance().getConnection();
     * PreparedStatement ps =
     * con.prepareStatement(com.siscomercio.tables.defaults.getGET_USER_CODE_BY_LOGIN);
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
     * if (config.isDebug())
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
     * con.prepareStatement(com.siscomercio.tables.defaults.getCHANGE_USER_PASS);
     * ps.setString(1, newPass);
     * ps.setString(2, login);
     * ps.execute();
     * closeConnections(ps, con);
     * ok = true;
     * }
     * catch (SQLException e)
     * {
     * ok = false;
     * util.showErrorMsg("Erro ao Trocar Senha do Usuario: " + login + ","
     * + e, true);
     *
     * }
     * if (ok)
     * {
     * if (config.isDebug())
     * {
     * _log.log(Level.INFO, "\n trocando senha do usuario: {0}para: {1}\n", new
     * Object[]
     * {
     * login, newPass
     * });
     * }
     * util.showMsg("Senha Trocada com Sucesso!", true);
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
     * if (config.isDebug())
     * {
     * _log.info("\n setando nivel de acesso para usuario \n");
     * }
     * Connection con = null;
     * try
     * {
     * con = DatabaseFactory.getInstance().getConnection();
     * PreparedStatement ps =
     * con.prepareStatement(com.siscomercio.tables.defaults.getUPDATE_USER_ACCESS_LVL);
     * ResultSet rset = ps.executeQuery();
     * closeConnections(ps, rset, con);
     *
     * }
     * catch (Exception e)
     * {
     * if (config.isDebug())
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
        if (config.isDebug())
        {
            _log.log(Level.INFO, "checando o nivel de acesso do usuario {0}\n", usr);
        }

        int level = stringTable.getDEFAULT_INT();
        try
        {
            PreparedStatement ps = conexaoBanco.prepareStatement(stringTable.getGET_ACC_LVL());
            ps.setString(1, usr);
            ps.execute();
            ResultSet rset = ps.getResultSet();
            if (rset.next())
            {
                level = rset.getInt("accesslevel");
            }
            if (config.isDebug())
            {
                _log.log(Level.INFO, "nivel de acesso do usuario {0} e {1} \n", new Object[]
                {
                    usr, level
                });
            }
        }
        catch (Exception e)
        {
            if (config.isDebug() || config.isEnableLog())
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
    public boolean readInstallationState()
    {
        int code = stringTable.getDEFAULT_INT();

//        if (_status.equalsIgnoreCase(stringTable.getSTATUS_DISCONNECTED()))
//        {
//            conectaBanco();
//        }
        if (config.isDebug())
        {
            _log.info("lendo tabela de estado da instalacao \n");
            _log.log(Level.INFO, "parametro code = {0}", code);
        }
        try
        {
            consultaPreparada = conexaoBanco.prepareStatement(stringTable.getREAD_INSTALL());
            ResultSet rset = consultaPreparada.executeQuery();
            while (rset.next())
            {
                code = rset.getInt("bancoInstalado");
            }
//            else
//            {
//                _log.log(Level.INFO, "parametro nao encontrado! Install  = {0}", code);
//
//            }

            _log.log(Level.INFO, "Banco Instalado = {0}", String.valueOf(code));
            this.setInstalled(code == 1 ? true : false);
            if (config.isDebug())
            {
                _log.log(Level.INFO, "Estado da Instala\u00e7\u00e3o: {0} ok .....\n", _installed);
            }
            return true;
        }
        catch (Exception e)
        {
            if (config.isDebug() || config.isEnableLog())
            {
                _log.log(Level.SEVERE, "DatabaseManager: Error reading Install Table: {0}", e.getMessage());
            }
            ExceptionManager.ThrowException(e.getMessage(), e);
        }
        return false;
    }

    /**
     * Le os dados da tabela e Instalacao
     *
     */
    public int readLicenseData()
    {

        if (config.isDebug())
        {
            _log.info("Lendo dados da Licenca \n");
        }
        try (PreparedStatement ps = conexaoBanco.prepareStatement(stringTable.getREAD_APP_LICENSE_DATA()))
        {
            try (ResultSet rset = ps.executeQuery())
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
            }
            setLicensed(_licensed);
            if (config.isDebug())
            {
                _log.log(Level.INFO, "Status da Licenca: {0}\n", _licensed);
            }
        }
        catch (Exception e)
        {
            if (config.isDebug() || config.isEnableLog())
            {
                _log.log(Level.SEVERE, "DatabaseManager: Error reading License Data: "
                        + e.getMessage(), e);
            }
            ExceptionManager.ThrowException("Erro: ", e);
        }
        return _licensed;
    }

    /**
     * atualiza as tabelas normais, e mantem as de dados do usuario
     */
    public boolean atualizaDatabase()
    {
        if (!isConnected())
        {
            conectaBanco();
        }
        //tenta criar nova base Dados caso nao exista
        if (criaNovaBase())
        {
            //le e executa todas as tabelas.
            if (executaTabelasMySQL(true))
            {
                //se todas as operacoes foram concluidas com exito, seta o banco como Instalado.
                setInstalled(true);
                executeUpdateQuery(stringTable.getInstallQuery());
                return true;

            }

        }
        return false;
    }

    /**
     * Conexao Idependente do Objeto Global
     * <p/>
     * @return
     */
    public boolean verificaExistencia()
    {

        conexaoMySQL = conectaMySQL();

        try
        {
            PreparedStatement ps = conexaoMySQL.prepareStatement(stringTable.getSELECT_DB());
            ps.execute();
            ResultSet rset = ps.getResultSet();
            if (rset.next())
            {
                return true;
            }
        }
        catch (Exception e)
        {
        }

        return false;
    }

    /**
     * Conexao Idependente do Objeto Global
     * <p/>
     * @return
     */
    public boolean tabelaExiste(String tabela)
    {
        System.out.println("Verificando Tabela : " + tabela);
        String table = tabela.replace(".sql", "");

        try
        {
            PreparedStatement ps = conexaoBanco.prepareStatement("SHOW TABLES LIKE '" + table + "'");
            ps.execute();
            ResultSet rset = ps.getResultSet();
            if (rset.next())
            {
                System.out.println("tabela existe");
                return true;
            }
            else
            {
                System.out.println("tabela Nao existe");
            }
        }
        catch (Exception e)
        {
        }

        return false;
    }

    /**
     * Conexao Idependente do Objeto Global
     * <p/>
     * @return
     */
    private Connection conectaMySQL()
    {
        Connection con = null;
        try
        {
            //conecta ao servidor mysql sem database selecionada
            String url = "jdbc:mysql://" + config.getHost() + ":" + config.getDatabasePort() + "/";

            //regstra o driver
            Class.forName(config.getDatabaseDriver()).newInstance();

            System.out.println("URL = " + url);
            System.out.println("Query = " + stringTable.getSELECT_DB());

            //Captura a Conexão.
            con = DriverManager.getConnection(url, config.getDatabaseLogin(), config.getDatabasePassword());

        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex)
        {
            Utilitarios.getInstance().showErrorMessage("Erro ao Conectar Banco: " + ex.getCause());
            _log.log(Level.INFO, "Erro {0}", ex.getMessage());
            Logger.getLogger(Banco.class.getName()).log(Level.SEVERE, null, ex.getCause());
        }
        return con;

    }

    /**
     * Cria Nova Database
     * <p/>
     * C3P0 Não Aceita o Seu Uso sem Selecao, Devemos Usar o DriverManager.
     *
     */
    public boolean criaNovaBase()
    {
        if (config.isDebug())
        {
            _log.info("verificando existencia da base de dados..");
        }

        try
        {
            Statement st = conexaoBanco.createStatement();
            st.executeUpdate(stringTable.getCreateDB());
            setConStatus(stringTable.getSTATUS_CONNECTED());
            return true;

        }
        catch (SQLException ex)
        {
            if (config.isDebug() || config.isEnableLog())
            {
                _log.log(Level.SEVERE, "Erro ao Criar Nova Base de Dados: {0}", ex.getMessage());
            }
            ExceptionManager.ThrowException("Erro ao Criar Nova Base de Dados: ", ex.getCause());
        }
        return false;
    }

    /**
     * ler eExecuta todos os Scripts SQL dentro da pasta SQL
     *
     */
    public boolean executaTabelasMySQL(boolean update)
    {
        if (config.isDebug())
        {
            _log.info("carregando tabelas do banco...");
        }

        File pasta = new File(stringTable.getSQL_PATH());
        if (config.isDebug())
        {
            _log.log(Level.INFO, "Caminho: {0}", stringTable.getSQL_PATH());
        }
        for (File f : pasta.listFiles())
        {
            if (f != null && f.getName().endsWith(".sql"))
            {
                System.out.println("Executando Arquivo: " + f.getName());
                if (update && !tabelaExiste(f.getName()))
                {
                    String thisLine, sqlQuery;
                    try (BufferedReader br = new BufferedReader(new FileReader(stringTable.getSQL_PATH() + f.getName())))
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
                                    consulta = conexaoBanco.createStatement();
                                    consulta.execute(sqlQuery);

                                }
                                catch (SQLException ex)
                                {
                                    if (config.isEnableLog() || config.isDebug())
                                    {
                                        _log.log(Level.SEVERE, "Falha ao Executar Script SQL:  {0}", "Arquivo: " + f.getName() + "Exception: " + ex.getMessage());
                                    }
                                    ExceptionManager.ThrowException("Falha ao Executar Script SQL:  ", "Arquivo: " + f.getName(), ex);
                                }

                                sqlQuery = "";
                            }

                        }
                    }
                    catch (Exception ex)
                    {
                        if (config.isEnableLog() || config.isDebug())
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
                            if (config.isEnableLog() || config.isDebug())
                            {
                                _log.log(Level.SEVERE, "Falha ao Executar Script SQL:  {0}", ex.getMessage());
                            }
                            ExceptionManager.ThrowException("Falha ao Executar Script SQL:  ", f.getName(), ex);
                        }
                    }

                }
                else
                {
                    System.out.println("Tabela: " + f.getName() + "Será Mantida.");
                }
            }
        }
        return true;
    }

    /**
     * Conecta ao Banco
     *
     */
    public boolean conectaBanco()
    {
        String url = null;
        try//A captura de exceções SQLException em Java é obrigatória para usarmos JDBC.
        {

            url = "jdbc:mysql://" + config.getHost() + ":" + config.getDatabasePort() + "/" + config.getDatabase();

            if (config.isDebug())
            {
                _log.info(url);
            }
            // Este é um dos meios para registrar um driver
            Class.forName(config.getDatabaseDriver()).newInstance();
            conexaoBanco = DatabaseFactory.getInstance().getConnection();// DriverManager.getConnection(url, config.getDatabaseLogin(), config.getDatabasePassword());
            if (config.isDebug())
            {
                _log.info("Conectado com Sucesso!!");
            }
            setConStatus(stringTable.getSTATUS_CONNECTED());
            return true;
        }
        catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException ex)
        {
            if (config.isEnableLog() || config.isDebug())
            {
                _log.log(Level.SEVERE, "Erro ao Conectar na Base de Dados {0}", ex.getMessage());
            }
            ExceptionManager.ThrowException("Erro ao Conectar na Base de Dados ", url, ex);
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

        return conexaoBanco;
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
            try (Statement st = conexaoBanco.createStatement())
            {
                st.executeQuery(query);
            }
        }
        catch (SQLException ex)
        {
            if (config.isEnableLog() || config.isDebug())
            {
                _log.log(Level.SEVERE, "Nao Foi Possivel Executar a Query: {0}", ex.getMessage());
            }
            ExceptionManager.ThrowException("Nao Foi Possivel Executar a Query: ", query, ex);
        }

    }

    /**
     * Executa uma query
     *
     * <p/>
     * @param query
     */
    private void executeUpdateQuery(String query)
    {
        try
        {
            try (Statement st = conexaoBanco.createStatement())
            {
                st.executeUpdate(query);
            }
        }
        catch (SQLException ex)
        {
            if (config.isEnableLog() || config.isDebug())
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


        try (Statement st = conexaoBanco.createStatement())
        {
            resultado = st.executeQuery(query);
        }
        catch (SQLException ex)
        {
            if (config.isEnableLog() || config.isDebug())
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

        if (config.isDebug())
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
        try (PreparedStatement ps = conexaoBanco.prepareStatement(stringTable.getCHECK_USER_PASS()))
        {
            ps.setString(1, user.getLogin());
            ps.setString(2, senhaCrypto);
            ps.execute();
            try (ResultSet rset = ps.getResultSet())
            {
                if (rset.next())
                {
                    logindb = rset.getString("login");
                    senhadb = rset.getString("senha");
                }
            }
            // Compara as Senhas Digitadas Pelo Usuario com a DB
            if (user.getLogin().equalsIgnoreCase(logindb) && (senhaCrypto.equalsIgnoreCase(senhadb)))
            {
                return true;
            }

        }
        catch (Exception ex)
        {
            if (config.isEnableLog() || config.isDebug())
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
        try (PreparedStatement ps = conexaoBanco.prepareStatement(stringTable.getINSERT_TECNICO()))
        {
            ps.setString(1, nome);
            ps.setString(2, sobrenome);
            ps.execute();
            return true;
        }
        catch (SQLException ex)
        {
            if (config.isEnableLog() || config.isDebug())
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
        if (config.isDebug())
        {
            _log.log(Level.INFO, "Adcionando Usuario: {0}", f.getNome() + f.getSenhaAtual());
        }

        String senhaCripto = Criptografia.criptografe(f.getSenhaAtual());

        if (config.isDebug())
        {
            _log.log(Level.INFO, "Senha Cripto: {0}", senhaCripto);
        }


        try (PreparedStatement ps = conexaoBanco.prepareStatement(stringTable.getINSERT_USER()))
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
            if (config.isEnableLog() || config.isDebug())
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
        try (PreparedStatement ps = conexaoBanco.prepareStatement(stringTable.getDELETE_USER()))
        {
            ps.setString(1, login);
            ps.execute();
            return true;
        }
        catch (SQLException ex)
        {
            if (config.isEnableLog() || config.isDebug())
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
     * conexao.prepareStatement(defaults.getgetGET_LAST_ENTRADA_ID());
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
        if (config.isDebug())
        {
            _log.info("salvando entrada para o banco...");
        }
        try (PreparedStatement ps = conexaoBanco.prepareStatement(stringTable.getINSERT_ENTRADA()))
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
            if (config.isEnableLog() || config.isDebug())
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

        if (config.isDebug())
        {
            _log.log(Level.INFO, "trocando senha de Usuario: {0}", login);
        }
        //converte p versao criptografada
        String passCrypto = Criptografia.criptografe(newPass);

        try (PreparedStatement ps = conexaoBanco.prepareStatement(stringTable.getCHANGE_USER_PASS()))
        {
            ps.setString(1, passCrypto);
            ps.setString(2, login);
            ps.execute();
            return true;
        }
        catch (SQLException ex)
        {
            if (config.isEnableLog() || config.isDebug())
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

        try (PreparedStatement ps = conexaoBanco.prepareStatement(stringTable.getGET_USER_PASS()))
        {
            ps.setString(1, login);
            ps.execute();
            try (ResultSet rset = ps.getResultSet())
            {
                if (rset.next())
                {
                    senhadb = rset.getString("senha");
                }
            }
        }
        catch (SQLException ex)
        {
            if (config.isEnableLog() || config.isDebug())
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

    public boolean getLicensed()
    {
        if (this._licensed != 1)
        {
            return false;
        }
        else
        {
            return true;
        }

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
