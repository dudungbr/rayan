/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 *
 * Created on 02/04/2010, 20:10:29
 */
package com.siscomercio.model.view.frames;

import com.siscomercio.init.Config;
import com.siscomercio.controller.managers.AppManager;
import com.siscomercio.model.persistence.dao.Banco;
import com.siscomercio.crypt.Serializer;
import com.siscomercio.utilities.SystemUtil;
import com.siscomercio.utilities.Utilitarios;
import com.siscomercio.utilities.document.MaxLengthUpperCaseDocument;
import java.awt.EventQueue;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 * $Revision$
 * $Author$
 * $Date$
 * <p/>
 * @author Rayan
 */
@SuppressWarnings ("serial")
public class FrameLicenca extends JFrame
{
    int numEstacoes = 0;
    private static final Logger _log = Logger.getLogger(FrameLicenca.class.getName());
    Config config = Config.getInstance();

    /**
     * Creates new form FrameLicenca
     */
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

    private void validaSerial()
    {
        //Retorna o Tipo de Licenca Selecionado
        // ------------------------------------
        String tipoLicenca = dropDownTipoLicenca.getModel().getSelectedItem().toString();
        String valorCampoSerial = campoSerial.getText();
        // Remove os "-" da String
        String dados = codigoDeAtivacao.getText().replaceAll("-", "");
        // Encripta a Variavel Dados
        String validSerial = Serializer.getInstance().encryptSerial(dados);
        if (config.isDebug())
        {
            System.out.println("Tipo de licenca:" + tipoLicenca);
            System.out.println("valor digitado no campo Serial: " + valorCampoSerial);
            System.out.println("valor variavel dados: " + dados);
            System.out.println("serial valido: " + validSerial);
        }
        else if (valorCampoSerial.equalsIgnoreCase(validSerial))
        {
            Banco.getInstance().setLicensed(1);
            dispose();
            Banco.getInstance().registreAplicacao(campoEmpresa.getText(), numEstacoes, tipoLicenca);
            SystemUtil.getInstance().showMsg("Obrigado por Registrar o Aplicativo ! ", true);
            AppManager.getInstance().restartApp();
        }
        else
        {
            SystemUtil.getInstance().showErrorMsg("Numero de Série Inválido.", true);
            campoSerial.setText("");
        }
    }

//    /**
//     *
//     * @param licenceType
//     * @param str
//     * <p/>
//     * @return
//     */
//    public static String createEncriptedString(String licenceType, String str)
//    {
//        String tmp;
//        tmp = Serializer.getInstance().encryptSerial(str);
//        return (licenceType.toUpperCase().concat(tmp));
//    }
    private boolean validaFrame()
    {
        String nomeEmpresa = campoEmpresa.getText();
        String codAtivacao = codigoDeAtivacao.getText();
        numEstacoes = Integer.parseInt(String.valueOf(spinnerContadorEstacoes.getModel().getValue()));
        String serial = campoSerial.getText();

        if (nomeEmpresa.isEmpty())
        {
            SystemUtil.getInstance().showErrorMsg("Preencha o Nome da Empresa.", true);
            return false;
        }
        else if (serial.length() < 30)
        {
            SystemUtil.getInstance().showErrorMsg("Numero de Série Incompleto.", true);
            campoSerial.setText("");
        }
        else if (codAtivacao.isEmpty())
        {
            SystemUtil.getInstance().showErrorMsg("Nao foi Possivel Gerar o Codigo de Ativação, Contacte o Suporte Tecnico.", true);
            resetCampos();
            return false;
        }
        else if (numEstacoes <= 0)
        {
            SystemUtil.getInstance().showErrorMsg("o Numero de Estações Deve Ser Maior que 0.", true);
            resetCampos();
            return false;
        }
        else if (serial.isEmpty())
        {
            SystemUtil.getInstance().showErrorMsg("Insira o Numero de Serie.", true);
            resetCampos();
            return false;
        }
        return true;
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings (
            {
        "unused"
    })
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        painel = new javax.swing.JPanel();
        labelCodAtivacao = new javax.swing.JLabel();
        campoEmpresa = new javax.swing.JTextField();
        labelEmpresa = new javax.swing.JLabel();
        botaoRegistrar = new javax.swing.JButton();
        dropDownTipoLicenca = new javax.swing.JComboBox();
        labelTipoLicenca = new javax.swing.JLabel();
        labelNumEstacoes = new javax.swing.JLabel();
        spinnerContadorEstacoes = new javax.swing.JSpinner();
        labelNumSerie = new javax.swing.JLabel();
        labelInformacaoSuporte = new javax.swing.JLabel();
        codigoDeAtivacao = new javax.swing.JTextField();
        campoSerial = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Registro da Aplicação");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                formWindowClosing(evt);
            }
        });

        painel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados do Registro", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18), new java.awt.Color(0, 0, 0))); // NOI18N
        painel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        painel.setPreferredSize(new java.awt.Dimension(800, 600));

        labelCodAtivacao.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelCodAtivacao.setText("Código de Ativação:");

        labelEmpresa.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelEmpresa.setText("Empresa :");

        botaoRegistrar.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        botaoRegistrar.setText("Registrar Sistema");
        botaoRegistrar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
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

        codigoDeAtivacao.setEditable(false);

        campoSerial.setDocument(new MaxLengthUpperCaseDocument(30));

        javax.swing.GroupLayout painelLayout = new javax.swing.GroupLayout(painel);
        painel.setLayout(painelLayout);
        painelLayout.setHorizontalGroup(
            painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelLayout.createSequentialGroup()
                .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                .addComponent(labelTipoLicenca))
                            .addGroup(painelLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelEmpresa)
                                    .addComponent(labelCodAtivacao))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(codigoDeAtivacao, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoSerial, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(painelLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(labelInformacaoSuporte))
                    .addGroup(painelLayout.createSequentialGroup()
                        .addGap(142, 142, 142)
                        .addComponent(botaoRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(painelLayout.createSequentialGroup()
                        .addGap(142, 142, 142)
                        .addComponent(spinnerContadorEstacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(painelLayout.createSequentialGroup()
                        .addGap(142, 142, 142)
                        .addComponent(dropDownTipoLicenca, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        painelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {campoEmpresa, campoSerial, codigoDeAtivacao});

        painelLayout.setVerticalGroup(
            painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelEmpresa)
                    .addComponent(campoEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(painelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCodAtivacao)
                    .addComponent(codigoDeAtivacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
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

        painelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {campoEmpresa, campoSerial, codigoDeAtivacao});

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
        if (validaFrame())
        {
            validaSerial();
        }
    }//GEN-LAST:event_botaoRegistrarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
        boolean reply = Utilitarios.getInstance().criaJanelaConfirmacao(this, false);

        if (reply)
        {
            System.out.println("ok");
            System.exit(0);
        }
        else
        {
            System.out.println("no");
        }

    }//GEN-LAST:event_formWindowClosing

    /**
     *
     */
    private void prepareFrame()
    {
        //Monitora o Campo e sempre insere caracteres em caixa alta
        ///campoSerial.setDocument(new UpperCaseLetter());

        //Seta o Valor 1 no Contador de estacoes
        spinnerContadorEstacoes.getModel().setValue(1);

        //gera o codigo de ativacao.
        Serializer serial = Serializer.getInstance();

        if (!serial.isGenerated())
        {
            serial.generateActivationCode();
        }
        //imprime no frame o codigo gerado pelos seriais
        codigoDeAtivacao.setText(serial.getGeneratedCode());
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
                new FrameLicenca().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoRegistrar;
    private javax.swing.JTextField campoEmpresa;
    private javax.swing.JFormattedTextField campoSerial;
    private javax.swing.JTextField codigoDeAtivacao;
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
