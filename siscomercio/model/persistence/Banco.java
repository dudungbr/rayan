/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio.model.persistence;



import com.siscomercio.Config;
import com.siscomercio.controller.managers.ExceptionManager;
import com.siscomercio.model.entity.Endereco;
import com.siscomercio.model.entity.Entrada;
import com.siscomercio.model.entity.Funcionario;
import com.siscomercio.model.entity.Usuario;
import com.siscomercio.model.security.Criptografia;
import com.siscomercio.standards.StringTable;
import com.siscomercio.tables.UserTable;
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
 *
 * @author William
 */
public class Banco
{

    private static final Logger _log = Logger.getLogger(Banco.class.getName());
    private Connection conexao;
    private Statement consulta;
    private PreparedStatement consultaPreparada;
    private ResultSet resultado;
    private String query;

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
        String url2 = null;
        try
        {

            //regstra o driver
            Class.forName(Config.getDatabaseDriver()).newInstance();

            //conecta ao servidor mysql sem database selecionada
            url2 = "jdbc:mysql://" + Config.getHost() + "/";

            conexao = DriverManager.getConnection(url2, Config.getDatabaseLogin(), Config.getDatabasePassword());
            consulta = conexao.createStatement();
            consulta.executeUpdate("CREATE DATABASE IF NOT EXISTS sat");
            consulta.close();
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex)
        {
            _log.severe(ex.getMessage());
            ExceptionManager.ThrowException("Erro ao Criar Nova Base de Dados: ", url2, ex);
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
            consultaPreparada.setString(1,user.getLogin());
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
     * @return
     */
    public boolean changePassword(String newPass)
    {
        // pega o usuario na tabela.
        String login ="";// UserTable.getInstance().getLastUser().getLogin();

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
