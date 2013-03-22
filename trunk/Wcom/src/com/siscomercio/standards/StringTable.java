/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siscomercio.standards;

import com.siscomercio.init.Config;

/**
 * $Revision: 237 $ $Author: rayan_rpg@hotmail.com $ $Date: 2011-02-14 11:03:12
 * -0300 (seg, 14 fev 2011) $
 *
 * @author Rayan
 *
 * Comandos SQL executeQuery apenas para select os outros use o executeUpdate.
 */
public class StringTable
{

    private static String NOT_AVAIBLE = "N/A";
    private static String JCOMBO_DEFAULT = "Selecione";
    private static String EMPTY_STRING = "";
    /**
     * Pasta de Sons
     */
    private static String SOUND_PATH = "./sound/";
    /**
     * Pasta de Imagens
     */
    private static String IMAGE_PATH = "./images/";
    /**
     * Pasta de Scripts SQL
     */
    private static String SQL_PATH = "./sql/";
    /**
     * caminho do arquivo de configuracao
     */
    private static String CONFIG_FILE = "./config/config.properties";
    private static String CONFIG_PATH = "./config/";
    //   public static String LOGO_FILE = "./config/logo.png";
    private static String UPDATE_FILE = "./update/update.properties";
    private static String LOG_FILE = "./log/meulog.log";

    /**
     * @return the NOT_AVAIBLE
     */
    public static String getNOT_AVAIBLE()
    {
        return NOT_AVAIBLE;
    }

    /**
     * @return the JCOMBO_DEFAULT
     */
    public static String getJCOMBO_DEFAULT()
    {
        return JCOMBO_DEFAULT;
    }

    /**
     * @return the EMPTY_STRING
     */
    public static String getEMPTY_STRING()
    {
        return EMPTY_STRING;
    }

    /**
     * @return the SOUND_PATH
     */
    public static String getSOUND_PATH()
    {
        return SOUND_PATH;
    }

    /**
     * @return the IMAGE_PATH
     */
    public static String getIMAGE_PATH()
    {
        return IMAGE_PATH;
    }

    /**
     * @return the SQL_PATH
     */
    public static String getSQL_PATH()
    {
        return SQL_PATH;
    }

    /**
     * @return the CONFIG_FILE
     */
    public static String getCONFIG_FILE()
    {
        return CONFIG_FILE;
    }

    /**
     * @return the CONFIG_PATH
     */
    public static String getCONFIG_PATH()
    {
        return CONFIG_PATH;
    }

    /**
     * @return the UPDATE_FILE
     */
    public static String getUPDATE_FILE()
    {
        return UPDATE_FILE;
    }

    /**
     * @return the LOG_FILE
     */
    public static String getLOG_FILE()
    {
        return LOG_FILE;
    }

    /**
     * @return the INSERT_USER
     */
    public static String getINSERT_USER()
    {
        return INSERT_USER;
    }

    /**
     * @return the INSERT_ENTRADA
     */
    public static String getINSERT_ENTRADA()
    {
        return INSERT_ENTRADA;
    }

    /**
     * @return the INSERT_TECNICO
     */
    public static String getINSERT_TECNICO()
    {
        return INSERT_TECNICO;
    }

    /**
     * @return the GET_LAST_ENTRADA_ID
     */
    public static String getGET_LAST_ENTRADA_ID()
    {
        return GET_LAST_ENTRADA_ID;
    }

    /**
     * @return the LOAD_CIDADES
     */
    public static String getLOAD_CIDADES()
    {
        return LOAD_CIDADES;
    }
    /**
     * Dropa a Database Atual
     */
    //  public static String DELETE_DB = "DROP DATABASE " + Config.DATABASE;
    /**
     * pega o nivel de acesso do usuario na DB
     */
    public final String GET_ACC_LVL = "SELECT accesslevel FROM users WHERE login =?";
    /**
     * Insere Novo Usuario na DB
     */
    private static String INSERT_USER = "INSERT INTO `usuarios`(login,senha,nivelAcesso,nomeCompleto,endereco,cargo) VALUES (?,?,?,?,?,?)";
    /**
     * Insere Nova Entrada
     */
    private static String INSERT_ENTRADA = "INSERT INTO `entradaequip`(numentrada,data,usuario,status,perifericos,descricao,marca,tipo,quantidade,responsavel,defeito,hora) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    /**
     * Pega o Codigo pelo Login
     */
    private static final String INSERT_TECNICO = "INSERT INTO `tecnicos` (nome,sobrenome) VALUES (?,?)";
    /**
     * Pega o ultimo id de cliente cadastrado.
     */
    private static final String GET_LAST_ENTRADA_ID = "SELECT MAX(`id`) FROM entradaequip";
    /**
     *
     */
    private static final String LOAD_CIDADES = "SELECT id,estado,uf,nome FROM cidades order by id";
    /**
     * 
     */
    public static final String LOAD_EQUIPAMENTO = "SELECT id,tipo FROM equipamentos ORDER BY id";
    /**
     * 
     */
    public static final String LOAD_ESTADOS = "SELECT id,uf,nome FROM estados order by id";
    /**
     * 
     */
    public static final String LOAD_MARCA = "SELECT id,marca,tipo FROM marcas order by id";
    /**
     * 
     */
    public static final String LOAD_TECNICOS = "SELECT id,nome FROM tecnicos order by id";
    /**
     * Checa a Senha do Usuario
     */
    public static final String CHECK_USER_PASS = "SELECT * from usuarios WHERE  login=? AND senha=?";
    /**
     * Deleta Usuario
     */
    public static final String DELETE_USER = "DELETE FROM usuarios WHERE id=? AND login =?";
    /**
     * Troca Senha
     */
    public static final String CHANGE_USER_PASS = "UPDATE usuarios SET senha=? WHERE login=?";
    /**
     * Troca Senha
     */
    public static final String GET_USER_PASS = "SELECT `senha` FROM usuarios WHERE login=?";
    /**
     * Atualiza Dados
     */
    public static final String UPDATE_USER = "UPDATE usuarios SET (login,senha)WHERE login=? AND senha=?";
  public static final String CREATE_DB = "CREATE DATABASE IF NOT EXISTS " + Config.getDatabase();
    //    /**
//     * Registra a App
//     */
//    public static final String REGISTRE_APP = "REPLACE INTO install VALUES (?,?,?,?,?,?,?,?,?)";
//            // bancoInstalado=?,stationMAC=?,StationMBSerial=?,Empresa=?,stationHDSerial=?,NumEstacoes=?,licenseType=?,registeredFor=?";
    /**
     * `bancoInstalado` varchar(5) NOT NULL default 'false', `statioMAC`
     * varchar(50) NOT NULL, `StationMBSerial` varchar(50) NOT NULL, `Empresa`
     * varchar(50) default NULL, `stationHDSerial` varchar(50) NOT NULL,
     * `NumEstacoes` int(50) NOT NULL, `licenseType` varchar(10) NOT NULL,
     * `registeredFor` varchar(30) NOT NULL,
     */
//
//      /**
//     * Registra a App
//     */
//    public static final String READ_APP_LICENSE_DATA = "SELECT `stationMAC`, `StationMBSerial`, `Empresa`, `stationHDSerial`, `NumEstacoes`, `licenseType`, `registeredFor`, `licenciado` FROM install";
//
//    
//
//
//    /**
//     * Le a tabela de Usuarios
//     */
//    public static final String READ_USERS = "SELECT * FROM users";
//    /**
//     * Atualiza a Tabela de Instalacao
//     */
//    public static final String INSTALL = "UPDATE install SET bancoInstalado = '1'";
//    /**
//     * Le a Tabela de Instalacao
//     */
//    public static final String READ_INSTALL = "SELECT * FROM install";
    /**
     * Atualiza o Nivel de Acesso do Usuario
     */
    public static final String UPDATE_USER_ACCESS_LVL = "UPDATE usuarioss SET accesslvl=?  WHERE login=?";
    /**
     * Cria a Database
     */
//    public static final String CREATE_DB = "CREATE DATABASE IF NOT EXISTS " + Config.DATABASE;
    // *************************** Status da Database **********************/
    /**
     * Status da Database
     */
    public static final String STATUS_CONNECTED = "Conectado";
    /**
     * Status da Database
     */
    public static final String STATUS_DISCONNECTED = "Desconectado";
}
