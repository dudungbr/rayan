package com.siscomercio.model.security;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import com.siscomercio.init.Config;
import com.siscomercio.model.view.frames.FramePrincipal;
import com.siscomercio.model.view.frames.LogonUsuario;
import com.siscomercio.controller.managers.SoundManager;
import com.siscomercio.model.persistence.dao.Banco;
import com.siscomercio.tables.UserTable;
import com.siscomercio.utilities.SystemUtil;

/**
 * $Revision$
 * $Author$
 * $Date$
 * <p/>
 * @author Rayan
 */
public class Auth extends JFrame
{
    private static final long serialVersionUID = 1L;
    private static final Logger _log = Logger.getLogger(Auth.class.getName());
    private boolean _autenticado = false;
    JTextField campoUsuario;
    JPasswordField campoSenha;
    JButton botaoLogin;
    JButton botaoCancelar;
    JButton botaoConfigurar;
    Config config = Config.getInstance();

    public boolean isAutenticado()
    {
        return _autenticado;
    }

    public void setAutenticado(boolean _autenticado)
    {
        this._autenticado = _autenticado;
    }

    /**
     * Monta a Janela Principal da Aplicação
     *
     */
    public Auth()
    {
        if (config.isDebug())
        {
            _log.info(" inicializando ...\n");
        }
        if (config.isDebug())
        {
            _log.info("Criando Janela de Autenticacao.... \n");
        }

        if (config.isEnableSound())
        {
            System.out.println("Som: " + config.getPreLoginSound());
        }
        SoundManager.getInstance().playSound(config.getPreLoginSound());

        if (_autenticado)
        {
            if (config.isDebug())
            {
                _log.info("usuario ja autenticado nao ira criar janela");
            }
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
        if (config.isDebug())
        {
            _log.info("Auth:  Aguardando Login... \n");
        }

        // Desabilita o Botao Configurar caso a DB Ja tenha Sido Instalada Previamente.
        if (Banco.getInstance().getInstalled())
        {
            botaoConfigurar.setEnabled(false);
            botaoLogin.setEnabled(true);
        }
        else
        {
            botaoLogin.setEnabled(false);
        }

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
                if (campoUsuario.getText().equalsIgnoreCase("") || String.valueOf(campoSenha.getPassword()).equalsIgnoreCase(""))
                {
                    SystemUtil.getInstance().showErrorMsg("<html><font color = black >Digite o nome do Usuario e a Senha.</font></html>", true);
                    resetCampos();

                }
                if (config.isDebug())
                {
                    _log.info("Auth: Executando acao do botao login \n");
                }
                String senha = new String(campoSenha.getPassword());
                String login = campoUsuario.getText();

                if (Banco.getInstance().isAuthed(login, senha))
                {
                    showConfirmWindow();
                }
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
        if (config.isDebug())
        {
            _log.info("Auth:  resetando campos ...\n");
        }
        campoUsuario.setText("");
        campoSenha.setText("");
    }

    /**
     * Cria um Pop Up de Confirmacao do Login.
     */
    public void showConfirmWindow()
    {
        if (config.isDebug())
        {
            _log.info("Auth: Enviando janela de confirmacao...\n");
        }
        JLabel optionLabel = new JLabel("<html>Voce logou como <font color = red>" + UserTable.getInstance().getLastUser() + "</font> proceder ?</html>");

        if (config.isEnableSound())
        {
            SoundManager.getInstance().playSound(config.getLoginSound());
        }


        if (!LogonUsuario._reAuth)
        {
            int confirm = JOptionPane.showConfirmDialog(null, optionLabel);

            switch (confirm)
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
                            // AppManager.setTema(Auth.class.getSimpleName());
                            new FramePrincipal().setVisible(true);
                            //FIXME: ta travando a app...
                            //  if(Config.ENABLE_SOUND)
                            //    SoundManager.playSound(Config.WELCOME_SOUND);
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
    @SuppressWarnings ("synthetic-access")
    private static class SingletonHolder
    {
        protected static final Auth _instance = new Auth();
    }

    /**
     * Apenas uma Instancia dessa Classe
     * <p/>
     * @return SingletonHolder._instance
     */
    public static Auth getInstance()
    {
        return SingletonHolder._instance;
    }
    /**
     * Checa a Masterkey
     *
     * @param user
     * @param pass
     * <p/>
     * @return result
     */
    /*
     * public static boolean checkMasterKey(String user, String pass)
     * {
     * boolean result = false;
     * _log.info("Auth: Checando senha mestre... \n");
     * if(user.equalsIgnoreCase(Config.MASTER_USER) &&
     * pass.equalsIgnoreCase(Config.MASTER_KEY))
     * result = true;
     * else
     * {
     * SystemUtil.getInstance().showErrorMsg("usuario invalido.", true);
     * result = false;
     * }
     * return result;
     * }
     */
}
