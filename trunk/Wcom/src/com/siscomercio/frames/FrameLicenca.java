/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 *
 * Created on 02/04/2010, 20:10:29
 */
package com.siscomercio.frames;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.siscomercio.Config;
import com.siscomercio.DatabaseFactory;
import com.siscomercio.controller.managers.AppManager;
import com.siscomercio.controller.managers.DatabaseManager;
import com.siscomercio.model.security.Serializer;
import com.siscomercio.tables.StringTable;
import com.siscomercio.utilities.DiskUtil;
import com.siscomercio.utilities.MbUtil;
import com.siscomercio.utilities.NetworkUtil;
import com.siscomercio.utilities.SystemUtil;
import com.siscomercio.utilities.UpperCaseLetter;
import java.awt.EventQueue;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 * $Revision$
 * $Author$
 * $Date$
 * @author Rayan
 */
@SuppressWarnings("serial")
public class FrameLicenca extends JFrame
{
    int numEstacoes = 0;
    private static final Logger _log = Logger.getLogger(FrameLicenca.class.getName());

    /** Creates new form FrameLicenca */
    public FrameLicenca()
    {
        initComponents();
        prepareFrame();
    }

    private void resetCampos()
    {
        campoEmpresa.setText("");
        campoSerial.setText("");
    }

    private void verifiqueSerial()
    {
        //Retorna o Tipo de Licenca Selecionado
        // ------------------------------------
        String tipoLicenca = dropDownTipoLicenca.getModel().getSelectedItem().toString();
        if(Config.isDebug())
            System.out.println("tipo de licenca:" + tipoLicenca);

        //Pega o Valor dao Campo Serial
        // ------------------------------------
        String valorCampoSerial = campoSerial.getText();
        if(Config.isDebug())
            System.out.println("valor digitado no campo Serial: " + valorCampoSerial);


        // Remove os "-" da String
        //-------------------------------
        StringBuilder dados = new StringBuilder(dadosCodAtivacao.getText());
        String remover = "-";

        for(int i = 0;i < (dados.length() - remover.length() + 1);i++)
        {
            String res = dados.substring(i, (i + remover.length()));
            if(res.equals(remover))
            {
                if(Config.isDebug())
                    System.out.println("removendo - do serial");
                int pos = dados.indexOf(remover);
                dados.delete(pos, pos + remover.length());
            }
        }
        if(Config.isDebug())
            System.out.println("valor variavel dados: " + dados.toString());
        //--------------------------------------------

        // Encripta a Variavel Dados
        //----------------------------------------
        String validSerial = encryptSerial(dados.toString());
        if(Config.isDebug())
            System.out.println("serial valido: " + validSerial);

        if(valorCampoSerial.equalsIgnoreCase(validSerial))
        {
            DatabaseManager._licensed = 1;
            dispose();
            registreAplicacao(campoEmpresa.getText(), numEstacoes, tipoLicenca);
            SystemUtil.showMsg("Obrigado por Registrar o Aplicativo ! ",true);
            AppManager.getInstance().restartApp();
        }
        else
        {
            SystemUtil.showErrorMsg("Numero de Série Inválido.",true);
        }
    }

    /**
     *
     * @param st
     * @return
     */
    public static String encryptSerial(String st)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(st.getBytes());
            BigInteger hash = new BigInteger(1, digest.digest());
            StringBuilder serial = new StringBuilder(hash.toString());

            // Reduz a String p/ 30 Caracteres. deletando os caracteres apos o index 30...
            //-------------------------------
            for(int i = 0;i < serial.length();i++)
            {
                if(serial.length() > 30)
                {
                    serial.deleteCharAt(serial.length() - 1); // deleta os ultimos caracteres.
                }
            }
            return serial.toString();

        }
        catch(NoSuchAlgorithmException ns)
        {
            return st;
        }
    }

    /**
     * 
     * @param licenceType
     * @param str
     * @return
     */
    public static String createEncriptedString(String licenceType, String str)
    {
        String tmp;
        tmp = encryptSerial(str);
        return (licenceType.toUpperCase().concat(tmp));
    }

    private void coleteDados()
    {

        //Captura o Nome da Empresa
        // ---------------------
        String nomeEmpresa = campoEmpresa.getText();
        if(nomeEmpresa.isEmpty())
        {
            SystemUtil.showErrorMsg("Nome da Empresa Inválido.",true);
            return;

        }
        //Captura o Texto do Label
        // ---------------------
        String codAtivacao = dadosCodAtivacao.getText();
        if(codAtivacao.isEmpty())
        {
            SystemUtil.showErrorMsg("Nao foi Possivel Gerar o Codigo de Ativação, Contacte o Suporte Tecnico.",true);
            resetCampos();
            return;
        }
        numEstacoes = Integer.parseInt(String.valueOf(spinnerContadorEstacoes.getModel().getValue()));

        if(numEstacoes <= 0)
        {
            SystemUtil.showErrorMsg("o Numero de Estações Deve Ser Maior que 0.",true);
            resetCampos();
            return;
        }
        //Captura o Texto do cmpoSerial
        // ---------------------
        String serial = campoSerial.getText();
        if(serial.isEmpty())
        {
            SystemUtil.showErrorMsg("Por Favor Digite o Numero de Serie.",true);
            resetCampos();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings(
    {
        "unused"
    })
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painel = new javax.swing.JPanel();
        labelCodAtivacao = new javax.swing.JLabel();
        campoEmpresa = new javax.swing.JTextField();
        labelEmpresa = new javax.swing.JLabel();
        dadosCodAtivacao = new javax.swing.JLabel();
        botaoRegistrar = new javax.swing.JButton();
        dropDownTipoLicenca = new javax.swing.JComboBox();
        labelTipoLicenca = new javax.swing.JLabel();
        labelNumEstacoes = new javax.swing.JLabel();
        spinnerContadorEstacoes = new javax.swing.JSpinner();
        labelNumSerie = new javax.swing.JLabel();
        labelInformacaoSuporte = new javax.swing.JLabel();
        campoSerial = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registro da Aplicação");
        setResizable(false);

        painel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados do Registro", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18), new java.awt.Color(0, 0, 0))); // NOI18N
        painel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        painel.setPreferredSize(new java.awt.Dimension(800, 600));

        labelCodAtivacao.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelCodAtivacao.setText("Código de Ativação:");

        labelEmpresa.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelEmpresa.setText("Empresa :");

        dadosCodAtivacao.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        dadosCodAtivacao.setText("              dadosDaAtivacao");

        botaoRegistrar.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        botaoRegistrar.setText("Registrar Sistema");
        botaoRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoRegistrarActionPerformed(evt);
            }
        });

        dropDownTipoLicenca.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mensal", "Semestral", "Anual" }));

        labelTipoLicenca.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelTipoLicenca.setText("Tipo de Licenca:");

        labelNumEstacoes.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelNumEstacoes.setText("Nº Estações:");

        labelNumSerie.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelNumSerie.setText("Numero de Série:");

        labelInformacaoSuporte.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelInformacaoSuporte.setText("Contacte o Suporte Tècnico Informando o Código de Ativação ");

        javax.swing.GroupLayout painelLayout = new javax.swing.GroupLayout(painel);
        painel.setLayout(painelLayout);
        painelLayout.setHorizontalGroup(
            painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelLayout.createSequentialGroup()
                .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(painelLayout.createSequentialGroup()
                            .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(painelLayout.createSequentialGroup()
                                    .addGap(12, 12, 12)
                                    .addComponent(labelNumSerie))
                                .addGroup(painelLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(labelNumEstacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(painelLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(labelTipoLicenca)))
                            .addGap(22, 22, 22)
                            .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(dropDownTipoLicenca, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(spinnerContadorEstacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(campoSerial)))
                        .addGroup(painelLayout.createSequentialGroup()
                            .addGap(12, 12, 12)
                            .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(painelLayout.createSequentialGroup()
                                    .addComponent(labelEmpresa)
                                    .addGap(68, 68, 68)
                                    .addComponent(campoEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(painelLayout.createSequentialGroup()
                                    .addComponent(labelCodAtivacao)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(dadosCodAtivacao, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(painelLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(labelInformacaoSuporte))
                    .addGroup(painelLayout.createSequentialGroup()
                        .addGap(142, 142, 142)
                        .addComponent(botaoRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        painelLayout.setVerticalGroup(
            painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelEmpresa)
                    .addComponent(campoEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCodAtivacao)
                    .addComponent(dadosCodAtivacao, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNumSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoSerial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNumEstacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinnerContadorEstacoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTipoLicenca, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dropDownTipoLicenca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addComponent(labelInformacaoSuporte, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botaoRegistrar)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        getRootPane().setDefaultButton(botaoRegistrar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painel, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painel, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(516, 476));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoRegistrarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_botaoRegistrarActionPerformed
    {//GEN-HEADEREND:event_botaoRegistrarActionPerformed
        coleteDados();
        verifiqueSerial();
    }//GEN-LAST:event_botaoRegistrarActionPerformed

    /**
     * Registra a Aplicação Baseada nos Dados Fornecidos.
     */
    private void registreAplicacao(String nomeEmpresa, int numEstacoes, String licenceType)
    {
        //"INSERT INTO `install`(bancoInstalado,stationMAC,StationMBSerial,Empresa,
        //stationHDSerial,NumEstacoes,licenseType,registeredFor) VALUES (?,?,?,?,?,?,?,?)";
        Connection con = null;
        try
        {
            con = DatabaseFactory.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(StringTable.REGISTRE_APP);
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
        catch(SQLException e)
        {
            SystemUtil.showErrorMsg(e.getMessage(),true);

        }
    }

    /**
     *
     */
    private void prepareFrame()
    {
        //Monitora o Campo e sempre insere caracteres em caixa alta
        campoSerial.setDocument(new UpperCaseLetter());

        //Seta o Valor 1 no Contador de estacoes
        spinnerContadorEstacoes.getModel().setValue(1);

        //gera o codigo de ativacao.
        Serializer serial = Serializer.getInstance();
        
        if(!serial.isGenerated())
           serial.generateActivationCode();
        
        //imprime no frame o codigo gerado pelos seriais
        dadosCodAtivacao.setText(serial.getGeneratedCode());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        //esse codigo do e eecutando quando o arquivo e rodado diretamente
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {

               AppManager.setTema(FrameLicenca.class.getSimpleName());
                new FrameLicenca().setVisible(true);
            }

        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoRegistrar;
    private javax.swing.JTextField campoEmpresa;
    private javax.swing.JTextField campoSerial;
    private javax.swing.JLabel dadosCodAtivacao;
    private javax.swing.JComboBox dropDownTipoLicenca;
    private javax.swing.JLabel labelCodAtivacao;
    private javax.swing.JLabel labelEmpresa;
    private javax.swing.JLabel labelInformacaoSuporte;
    private javax.swing.JLabel labelNumEstacoes;
    private javax.swing.JLabel labelNumSerie;
    private javax.swing.JLabel labelTipoLicenca;
    private javax.swing.JPanel painel;
    private javax.swing.JSpinner spinnerContadorEstacoes;
    // End of variables declaration//GEN-END:variables
}
