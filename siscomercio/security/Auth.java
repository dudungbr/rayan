package com.siscomercio.security;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import com.siscomercio.Config;
import com.siscomercio.frames.FramePrincipal;
import com.siscomercio.frames.LogonUsuario;
import com.siscomercio.DatabaseFactory;
import com.siscomercio.managers.AppManager;

import com.siscomercio.managers.DatabaseManager;
import com.siscomercio.managers.SoundManager;
import com.siscomercio.tables.StringTable;
import com.siscomercio.tables.UserTable;
import com.siscomercio.utilities.SystemUtil;

/**
 * $Revision$
 * $Author$
 * $Date$
 * @author Rayan
 */
public class Auth extends JFrame
{
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder
    {
        protected static final Auth _instance = new Auth();
    }

    /**
     * Apenas uma Instancia dessa Classe
     * @return SingletonHolder._instance
     */
    public static Auth getInstance()
    {
        return SingletonHolder._instance;
    }

    private static final Logger _log = Logger.getLogger(Auth.class.getName());
    /**
     *
     */
    public static boolean _autenticado = false;
    static int _accessLevel = 0;
    // Variaveis
    JTextField campoUsuario;
    JPasswordField campoSenha;
    JButton botaoLogin;
    JButton botaoCancelar;
    JButton botaoConfigurar;

    /**
     * Monta a Janela Principal da Aplicação
     *
     */
    public Auth()
    {
          if(Config.DEBUG)
            _log.info(" inicializando ...\n");
        if(Config.DEBUG)
            _log.info("Criando Janela de Autenticacao.... \n");

        if(Config.ENABLE_SOUND)
            SoundManager.playSound(Config.PRE_LOGIN_SOUND);

        if(_autenticado)
        {
            if(Config.DEBUG)
                _log.info("usuario ja autenticado nao ira criar janela");
            return;
        }

        // Campos Usuario e Senha
        campoUsuario = new JTextField();
        campoUsuario.setSize(40, 50);
        campoSenha = new JPasswordField();

        // Textos da Interface
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new java.awt.Font("Times New Roman", 1, 14));
        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setFont(new java.awt.Font("Times New Roman", 1, 14));
        // Botoes
        botaoLogin = new JButton("Login");
        botaoLogin.setFont(new java.awt.Font("Times New Roman", 1, 14));
        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.setFont(new java.awt.Font("Times New Roman", 1, 14));
        botaoConfigurar = new JButton("Configurar...");
        botaoConfigurar.setFont(new java.awt.Font("Times New Roman", 1, 14));
        if(Config.DEBUG)
            _log.info("Auth:  Aguardando Login... \n");

        // Desabilita o Botao Configurar caso a DB Ja tenha Sido Instalada Previamente.
        if(DatabaseManager._installed==1)
        {
            botaoConfigurar.setEnabled(false);
            botaoLogin.setEnabled(true);
        }
        else
            botaoLogin.setEnabled(false);

        // Design
        Container tela = getContentPane();
        BorderLayout layout = new BorderLayout();
        tela.setLayout(layout);

        // Paineis
        JPanel superior = new JPanel();
        superior.setLayout(new GridLayout(2, 2, 5, 5));
        superior.add(lblUsuario);
        superior.add(campoUsuario);
        superior.add(lblSenha);
        superior.add(campoSenha);

        //Painel 2
        JPanel superior2 = new JPanel();
        superior2.setLayout(new FlowLayout(FlowLayout.CENTER));
        superior2.add(superior);

        // Painel Inferior
        JPanel inferior = new JPanel();
        inferior.setLayout(new FlowLayout(FlowLayout.RIGHT));
        inferior.add(botaoLogin);
        inferior.add(botaoCancelar);
        inferior.add(botaoConfigurar);

        // Designs
        tela.add(BorderLayout.NORTH, superior2);
        tela.add(BorderLayout.SOUTH, inferior);

        // ================= Botoes ===================================
        botaoLogin.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(Config.DEBUG)
                    _log.info("Auth: Executando acao do botao login \n");
                String senha = new String(campoSenha.getPassword());
                String login = campoUsuario.getText();

                if(isAuthed(login, senha))
                    showConfirmWindow();
            }

        });

        //adiciona listener ao enter no login
        getRootPane().setDefaultButton(botaoLogin);

        // Acao Botao Cancelar
        botaoCancelar.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }

        });
        // Ação botao Configurar
        botaoConfigurar.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                java.awt.EventQueue.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        //fecha a tela.
                        dispose();

                        //Solicita Autenticacao.
                        new LogonUsuario().setVisible(true);
                    }

                });
            }

        });
        //===================================================

        FlowLayout layout2 = new FlowLayout(FlowLayout.LEFT);
        tela.setLayout(layout2);
        setSize(290, 160);
        setResizable(false);
        setTitle("Login no Sistema");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

    }

    /**
     * Limpa os Campos
     */
    private void resetCampos()
    {
          if(Config.DEBUG)
            _log.info("Auth:  resetando campos ...\n");
        campoUsuario.setText("");
        campoSenha.setText("");
    }

    /**
     *
     * @param login
     * @param senha
     * @return boolean
     */
    public boolean isAuthed(String login, String senha)
    {
         if(Config.DEBUG)
            _log.info("Auth: Checando Usuario e Senha ...\n");

        Connection con = null;
        boolean ok = false;
        try
        {
            if(Config.DEBUG)
                //checa se os dados estao ok...
                _log.log(Level.INFO, "Auth: Checando dados... \n Senha  = {0} \n User = {1}", new Object[]
                        {
                            senha, login+"\n"
                        });

            if(login.equalsIgnoreCase("") || senha.equalsIgnoreCase(""))
            {
                SystemUtil.showErrorMsg("<html><font color = black >Digite o nome do Usuario e a Senha.</font></html>",true);
                resetCampos();
                ok = false;
            }
            senha = senha.toLowerCase();
            login = login.toLowerCase();

            // Criptografa a Senha do Usuario
            senha = Criptografia.criptografe(senha);

            // ---------------------------------
            // Le a Tabela de Usuarios da Database
            // ---------------------------------
            int userCode = DatabaseManager.getUserCode(login, senha);
            String logindb = null;
            String senhadb = null;

            con = DatabaseFactory.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(StringTable.CHECK_USER_PASS);
            ps.setInt(1, userCode);
            ps.setString(2, login);
            ps.setString(3, senha);
            ps.execute();
            ResultSet rset = ps.getResultSet();
            if(rset.next())
            {
                // pega os dados
                logindb = rset.getString("login");
                senhadb = rset.getString("password");
            }
            //devemos fecha todas as conxoes assim que terminado o procedimento.
            DatabaseManager.closeConnections(ps, rset, con);

            // Compara as Senhas Digitadas Pelo Usuario com a DB
            if(login.equalsIgnoreCase(logindb) && (senha.equalsIgnoreCase(senhadb)))
            {
                ok = true;
                UserTable.getInstance().setLastUser(login);
            }
            else
            {
                SystemUtil.showErrorMsg("usuario ou senha incorretos!",true);
                ok = false;
            }

        }
        catch(SQLException ex)
        {
            SystemUtil.showErrorMsg("SQLException: " + ex.getMessage() + "\n SQLState: " + ex.getSQLState() + "\n VendorError: " + ex.getErrorCode(),true);

        }
        catch(Exception e)
        {
            SystemUtil.showErrorMsg("Problemas ao tentar conectar com o banco de dados" + e,true);
        }
        return ok;
    }

    /**
     * Cria um Pop Up de Confirmacao do Login.
     */
    public void showConfirmWindow()
    {
        if(Config.DEBUG)
            _log.info("Auth: Enviando janela de confirmacao...\n");
        JLabel optionLabel = new JLabel("<html>Voce logou como <font color = red>" + UserTable.getInstance().getLastUser() + "</font> proceder ?</html>");

        if(Config.ENABLE_SOUND)
            SoundManager.playSound(Config.LOGIN_SOUND);


        if(!LogonUsuario._reAuth)
        {
            int confirm = JOptionPane.showConfirmDialog(null, optionLabel);

            switch(confirm)
            {
                // Switch > Case
                case JOptionPane.YES_OPTION: // Attempt to Login user
                    setEnabled(true);
                    dispose(); //fecha a tela de login
                    EventQueue.invokeLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            AppManager.setTema(Auth.class.getSimpleName());
                            new FramePrincipal().setVisible(true);
                        }

                    });
                    break;

                case JOptionPane.NO_OPTION: // No Case.(Go back. Set text to 0)
                    botaoLogin.setEnabled(false);
                    campoUsuario.setText("");
                    campoSenha.setText("");
                    botaoLogin.setEnabled(true);
                    break;

                case JOptionPane.CANCEL_OPTION: // Cancel Case.(Go back. Set text to/0)
                    botaoLogin.setEnabled(false);
                    campoUsuario.setText("");
                    campoSenha.setText("");
                    botaoLogin.setEnabled(true);
                    break;

            } // End Switch > Case
        }
    }

    /**
     * Checa a Masterkey
     *
     * @param user
     * @param pass
     * @return result
     */
    public static boolean checkMasterKey(String user, String pass)
    {
        boolean result = false;
        _log.info("Auth: Checando senha mestre... \n");
        if(user.equalsIgnoreCase(Config.MASTER_USER) && pass.equalsIgnoreCase(Config.MASTER_KEY))
            result = true;
        else
        {
            SystemUtil.showErrorMsg("usuario invalido.",true);
            result = false;
        }
        return result;
    }

}
