/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 *
 * Created on 02/04/2010, 20:10:29
 */
package com.siscomercio.frames;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import com.siscomercio.Boot;
import com.siscomercio.Config;
import com.siscomercio.managers.AppManager;
import com.siscomercio.security.Serializer;
import com.siscomercio.utilities.SystemUtil;
import com.siscomercio.utilities.UpperCaseLetter;
import java.awt.EventQueue;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * $Revision$
 * $Author$
 * $Date$
 * @author Rayan
 */
@SuppressWarnings("serial")
public class LicenseFrame extends JFrame
{
    /** Creates new form LicenseFrame */
    public LicenseFrame()
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
        if(Config.DEBUG)
            System.out.println("tipo de licenca:" + tipoLicenca);

        //Pega o Valor dao Campo Serial
        // ------------------------------------
        String valorCampoSerial = campoSerial.getText();
        if(Config.DEBUG)
            System.out.println("valor digitado no campo Serial: " + valorCampoSerial);


        // Remove os "-" da String
        //-------------------------------
        StringBuilder dados = new StringBuilder(labelCodigoAtivacao.getText());
        String remover = "-";

        for(int i = 0;i < (dados.length() - remover.length() + 1);i++)
        {
            String res = dados.substring(i, (i + remover.length()));
            if(res.equals(remover))
            {
                if(Config.DEBUG)
                    System.out.println("removendo - do serial");
                int pos = dados.indexOf(remover);
                dados.delete(pos, pos + remover.length());
            }
        }
        if(Config.DEBUG)
            System.out.println("valor variavel dados: " + dados.toString());
        //--------------------------------------------

        // Encripta a Variavel Dados
        //----------------------------------------
        String validSerial = encryptSerial(dados.toString());
        if(Config.DEBUG)
            System.out.println("serial valido: " + validSerial);

        if(valorCampoSerial.equalsIgnoreCase(validSerial))
        {
            Boot.isRegistrado = true;
            SystemUtil.showMsg("Obrigado por Registrar o Aplicativo ! ");
            //SystemUtil.showMsg("O Sistema Esta Sendo Reiniciado...");
            dispose();
            //registreAplicacao();
           AppManager.getInstance().restartApp();
        }
        else
        {
            SystemUtil.showErrorMsg("Numero de Série Inválido.");
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
            SystemUtil.showErrorMsg("Nome da Empresa Inválido.");
            return;

        }
        //Captura o Texto do Label
        // ---------------------
        String codAtivacao = labelCodigoAtivacao.getText();
        if(codAtivacao.isEmpty())
        {
            SystemUtil.showErrorMsg("Nao foi Possivel Gerar o Codigo de Ativação, Contacte o Suporte Tecnico.");
            resetCampos();
            return;
        }


        Object numEstacoes = spinnerContadorEstacoes.getModel().getValue();

        if(Integer.parseInt(numEstacoes.toString()) <= 0)
        {
            SystemUtil.showErrorMsg("o Numero de Estações Deve Ser Maior que 0.");
            resetCampos();
            return;
        }
        //Captura o Texto do cmpoSerial
        // ---------------------
        String serial = campoSerial.getText();
        if(serial.isEmpty())
        {
            SystemUtil.showErrorMsg("Por Favor Digite o Numero de Serie.");
            resetCampos();
            return;
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

        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        campoEmpresa = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        labelCodigoAtivacao = new javax.swing.JLabel();
        botaoRegistrar = new javax.swing.JButton();
        dropDownTipoLicenca = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        labelNumEstacoes = new javax.swing.JLabel();
        spinnerContadorEstacoes = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        campoSerial = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registro da Aplicação");
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados do Registro", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18), new java.awt.Color(0, 0, 0))); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(800, 600));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14));
        jLabel7.setText("Código de Ativação:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14));
        jLabel3.setText("Empresa :");

        labelCodigoAtivacao.setFont(new java.awt.Font("Times New Roman", 1, 14));
        labelCodigoAtivacao.setText("              dadosDaAtivacao");

        botaoRegistrar.setFont(new java.awt.Font("Times New Roman", 1, 14));
        botaoRegistrar.setText("Registrar Sistema");
        botaoRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoRegistrarActionPerformed(evt);
            }
        });

        dropDownTipoLicenca.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mensal", "Semestral", "Anual" }));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14));
        jLabel5.setText("Tipo de Licenca:");

        labelNumEstacoes.setFont(new java.awt.Font("Times New Roman", 1, 14));
        labelNumEstacoes.setText("Nº Estações:");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14));
        jLabel2.setText("Numero de Série:");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 14));
        jLabel8.setText("Contacte o Suporte Tècnico Informando o Código de Ativação ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel3)
                        .addGap(68, 68, 68)
                        .addComponent(campoEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel7)
                        .addGap(8, 8, 8)
                        .addComponent(labelCodigoAtivacao, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel2))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(labelNumEstacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel5)))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dropDownTipoLicenca, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spinnerContadorEstacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoSerial, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(140, Short.MAX_VALUE)
                .addComponent(botaoRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(campoEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(labelCodigoAtivacao, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoSerial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNumEstacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinnerContadorEstacoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dropDownTipoLicenca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botaoRegistrar)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        getRootPane().setDefaultButton(botaoRegistrar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                .addContainerGap())
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-450)/2, (screenSize.height-476)/2, 450, 476);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoRegistrarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_botaoRegistrarActionPerformed
    {//GEN-HEADEREND:event_botaoRegistrarActionPerformed
        coleteDados();
        verifiqueSerial();
    }//GEN-LAST:event_botaoRegistrarActionPerformed
    private void prepareFrame()
    {
        //Monitora o Campo e sempre insere caracteres em caixa alta
        campoSerial.setDocument(new UpperCaseLetter());

        //Seta o Valor 1 no Contador de estacoes
        spinnerContadorEstacoes.getModel().setValue(1);

        if(!Serializer.generated)
            Serializer.generateActivationCode();
        //imprime no frame o codigo gerado pelos seriais
        labelCodigoAtivacao.setText(Serializer.getGeneratedCode());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                // PlasticLookAndFeel.setPlasticTheme(new Silver());
                try
                {
                    /**
                     * #  com.jgoodies.looks.windows.WindowsLookAndFeel
                    # com.jgoodies.looks.plastic.PlasticLookAndFeel
                    # com.jgoodies.looks.plastic.Plastic3DLookAndFeel
                    # com.jgoodies.looks.plastic.PlasticXPLookAndFeel
                     */
                    //BlackBusiness subistantce
                    //Luna jtoo
                    //acryl - jato
                    // UIManager.setLookAndFeel(new PlasticLookAndFeel());
                    UIManager.setLookAndFeel(new AcrylLookAndFeel());
                }
                catch(Exception e)
                {
                    SystemUtil.showErrorMsg("Nao Foi Possivel Carregar a Skin");
                }
                new LicenseFrame().setVisible(true);

            }

        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoRegistrar;
    private javax.swing.JTextField campoEmpresa;
    private javax.swing.JTextField campoSerial;
    private javax.swing.JComboBox dropDownTipoLicenca;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labelCodigoAtivacao;
    private javax.swing.JLabel labelNumEstacoes;
    private javax.swing.JSpinner spinnerContadorEstacoes;
    // End of variables declaration//GEN-END:variables

    private void registreAplicacao()
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
