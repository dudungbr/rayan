/*
 * RegistryInfo.java
 *
 * Created on 23/09/2010, 08:45:32
 */

package com.siscomercio.frames;

import com.siscomercio.managers.AppManager;
import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 *
 * @author Usuario
 */
public class RegistryInfo extends JFrame
{
    private static final long serialVersionUID = 1L;
    /** Creates new form RegistryInfo */
    public RegistryInfo()
    {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelRegisteredFor = new javax.swing.JLabel();
        dadosRegisteredFor = new javax.swing.JLabel();
        labelNumEstacoes = new javax.swing.JLabel();
        dadosEstacoes = new javax.swing.JLabel();
        labelTipoLicenca = new javax.swing.JLabel();
        dadosValidade = new javax.swing.JLabel();
        dadosLicenca = new javax.swing.JLabel();
        labelValidade = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Informações do Registro");

        labelRegisteredFor.setFont(new java.awt.Font("Times New Roman", 1, 14));
        labelRegisteredFor.setText("Registrado Para :");

        dadosRegisteredFor.setText("jLabel1");

        labelNumEstacoes.setFont(new java.awt.Font("Times New Roman", 1, 14));
        labelNumEstacoes.setText("Número de Estações :");

        dadosEstacoes.setText("jLabel2");

        labelTipoLicenca.setFont(new java.awt.Font("Times New Roman", 1, 14));
        labelTipoLicenca.setText("Tipo da Licença :");

        dadosValidade.setText("jLabel4");

        dadosLicenca.setText("jLabel3");

        labelValidade.setFont(new java.awt.Font("Times New Roman", 1, 14));
        labelValidade.setText("Valido Até: ");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(38, 38, 38)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(labelTipoLicenca, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 110, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(dadosLicenca))
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1Layout.createSequentialGroup()
                                .add(labelValidade)
                                .add(92, 92, 92)
                                .add(dadosValidade)))
                        .add(100, 100, 100))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1Layout.createSequentialGroup()
                                .add(labelRegisteredFor, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(dadosRegisteredFor))
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(labelNumEstacoes, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(dadosEstacoes)))
                        .add(165, 165, 165))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(25, 25, 25)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(labelRegisteredFor)
                    .add(dadosRegisteredFor))
                .add(18, 18, 18)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(labelNumEstacoes)
                    .add(dadosEstacoes))
                .add(18, 18, 18)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(dadosLicenca)
                    .add(labelTipoLicenca))
                .add(18, 18, 18)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(labelValidade)
                    .add(dadosValidade))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-408)/2, (screenSize.height-334)/2, 408, 334);
    }// </editor-fold>//GEN-END:initComponents

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
                new RegistryInfo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel dadosEstacoes;
    private javax.swing.JLabel dadosLicenca;
    private javax.swing.JLabel dadosRegisteredFor;
    private javax.swing.JLabel dadosValidade;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelNumEstacoes;
    private javax.swing.JLabel labelRegisteredFor;
    private javax.swing.JLabel labelTipoLicenca;
    private javax.swing.JLabel labelValidade;
    // End of variables declaration//GEN-END:variables

}