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
import com.siscomercio.frames.LogonFrame;
import com.siscomercio.DatabaseFactory;

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
public class Auth extends JFrame {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder {

        protected static final Auth _instance = new Auth();
    }

    /**
     * 
     * @return
     */
    public static Auth getInstance()
    {
        return SingletonHolder._instance;
    }
    private static Logger _log = Logger.getLogger(Auth.class.getName());
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
        if (Config.DEBUG)
            _log.info("Criando Janela de Autenticacao.... \n");

        if (Config.SOUND)
            SoundManager.playSound(Config.PRE_LOGIN_SOUND);

        if (_autenticado)
        {
            if (Config.DEBUG)
                _log.info("usuario ja autenticado nao ira criar janela");
            return;
        }

        // Campos Usuario e Senha
        campoUsuario = new JTextField();
        campoUsuario.setSize(40, 50);
        campoSenha = new JPasswordField();

        // Textos da Interface
        JLabel lblUsuario = new JLabel("Usuario:");
        JLabel lblSenha = new JLabel("Senha:");

        // Botoes
        botaoLogin = new JButton("Login");
        botaoCancelar = new JButton("Cancelar");
        botaoConfigurar = new JButton("Configurar...");

        if (Config.DEBUG)
            _log.info("Auth() ... install = " + DatabaseManager._installed + "\n");

        // Desabilita o Botao Configurar caso a DB Ja tenha Sido Instalada Previamente.
        if (DatabaseManager._installed)
        {
            botaoConfigurar.setEnabled(false);
            botaoLogin.setEnabled(true);
        } else
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
        botaoLogin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (Config.DEBUG)
                    _log.info("executando acao do botao login \n");
                String senha = new String(campoSenha.getPassword());
                String login = new String(campoUsuario.getText());

                if (isAuthed(login, senha))
                    showConfirmWindow();
            }
        });

        getRootPane().setDefaultButton(botaoLogin);

        // Acao Botao Cancelar
        botaoCancelar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });
        // Ação botao Configurar
        botaoConfigurar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                java.awt.EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run()
                    {
                        //fecha a tela.
                        dispose();

                        //Solicita Autenticacao.
                        new LogonFrame().setVisible(true);
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
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

    }

    /**
     * Limpa os Campos
     */
    private void resetCampos()
    {
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
        Connection con = null;
        boolean ok = false;
        try
        {
            if (Config.DEBUG)
                //checa se os dados estao ok...
                _log.info("checando dados: senha  = " + senha + " user = " + login);

            if (login.equalsIgnoreCase("") || senha.equalsIgnoreCase(""))
            {
                SystemUtil.showErrorMsg("<html><font color = black >Digite o nome do Usuario e a Senha.</font></html>");
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
            if (rset.next())
            {
                // pega os dados
                logindb = rset.getString("login");
                senhadb = rset.getString("password");
            }
            //devemos fecha todas as conxoes assim que terminado o procedimento.
            DatabaseManager.closeConnections(ps, rset, con);

            // Compara as Senhas Digitadas Pelo Usuario com a DB
            if (login.equalsIgnoreCase(logindb) && (senha.equalsIgnoreCase(senhadb)))
            {
                ok = true;
                UserTable.getInstance().setLastUser(login);
            } else
            {
                SystemUtil.showErrorMsg("usuario ou senha incorretos!");
                ok = false;
            }

        } catch (SQLException ex)
        {
            SystemUtil.showErrorMsg("SQLException: " + ex.getMessage() + "\n SQLState: " + ex.getSQLState() + "\n VendorError: " + ex.getErrorCode());

        } catch (Exception e)
        {
            SystemUtil.showErrorMsg("Problemas ao tentar conectar com o banco de dados" + e);
        }
        return ok;
    }
    /**
     * Cria um Pop Up de Confirmacao do Login.
     */
    public void showConfirmWindow()
    {
        JLabel optionLabel = new JLabel("<html>Voce logou como <font color = red>" + UserTable.getInstance().getLastUser() + "</font> proceder ?</html>");

        if (Config.SOUND)
            SoundManager.playSound(Config.LOGIN_SOUND);


        if (!LogonFrame._reAuth)
        {
            int confirm = JOptionPane.showConfirmDialog(null, optionLabel);

            switch (confirm)
            {
                // Switch > Case
                case JOptionPane.YES_OPTION: // Attempt to Login user
                    setEnabled(true);
                    dispose(); //fecha a tela de login
                    EventQueue.invokeLater(new Runnable() {

                        @Override
                        public void run()
                        {
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
     *
     * @return
     */
    public int getAcessLevel()
    {
        return _accessLevel;
    }

    /**
     * Checa a Masterkey
     *
     * @param user
     * @param pass
     * @return
     */
    public static boolean checkMasterKey(String user, String pass)
    {
        boolean ok = false;
        _log.info("checando senha mestre \n");
        if (user.equalsIgnoreCase(Config.MASTER_USER) && pass.equalsIgnoreCase(Config.MASTER_KEY))
            ok = true;
        else
        {
            SystemUtil.showErrorMsg("usuario invalido.");
            ok = false;
        }
        return ok;
    }
}
