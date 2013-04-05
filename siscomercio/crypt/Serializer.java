package com.siscomercio.crypt;

import com.siscomercio.controller.managers.ExceptionManager;
import com.siscomercio.init.Config;
import com.siscomercio.utilities.DiskUtil;
import com.siscomercio.utilities.MbUtil;
import com.siscomercio.utilities.NetworkUtil;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rayan
 */
public class Serializer
{
    private static final Logger _log = Logger.getLogger(Serializer.class.getName());
    private String code = "";
    private boolean generated = false;
    Config config = Config.getInstance();

    private Serializer()
    {
    }

    /**
     *
     * @return
     */
    public boolean isGenerated()
    {
        return generated;
    }

    /**
     * Captura Seriais HD / Mobo e MAC da Placa de Rede Divide em um Serial de 6
     * Partes de 5 Caracteres Fechando um Total de 30.
     *
     * @return
     */
    public String generateActivationCode()
    {
        //Coleta os Dados
        String serialHD = DiskUtil.getSerialNumber("c");
        String serialMB = MbUtil.getMotherboardSN();
        String mac = NetworkUtil.getMac();
        StringBuilder dados = new StringBuilder();
        dados.append(mac);
        dados.append(serialHD);
        dados.append(serialMB);

        if (config.isDebug())
        {
            _log.log(Level.INFO, "[DADOS]String Atual:{0}", dados);
            _log.log(Level.INFO, "[DADOS] Quantidade: {0}", dados.length());
            _log.info("------------------------------------------------");
            _log.info("Removido os Caracteres: :  - ");
        }
        /*
         * Expressores Regulares
         * \\W vai retirar todo e qualquer caracter que não seja número , letra
         * ou underscope
         * usamos duas barras invertidas, pois caso contrário ela será
         * reconhecida apenas como caractere de escape
         */
        StringBuilder temp = new StringBuilder();
        temp.append(dados.toString().replaceAll("\\W", ""));

        if (config.isDebug())
        {
            _log.log(Level.INFO, "[Temps]String Atual:{0}", temp);
            _log.log(Level.INFO, "[Temps]Quantidade: {0}", temp.length());
        }
        // Reduz a String p/ 30 Caracteres. deletando os caracteres apos o index 30...
        //-------------------------------
        System.out.println("[Temps] Reduzindo String P/ 30 Caracteres");


        while (temp.length() > 30)
        {
            if (config.isDebug())
            {
                System.out.println("[Temps] Deletando Index :" + temp.length() + " , ");
            }
            temp.deleteCharAt(temp.length() - 1); // deleta os ultimos caracteres.
        }

        if (config.isDebug())
        {
            _log.log(Level.INFO, "Nova String Com: {0} Caracteres:  " + temp, temp.length());
        }

        // Divide a String em 5 Partes e Organiza com "-"
        //----------------------------------------------------
        String p1 = "", p2 = "", p3 = "", p4 = "", p5 = "", p6 = "";

        for (int i = 0; i <= temp.length(); i++)
        {
            switch (i)
            {
                case 5:
                {
                    p1 = temp.substring(0, i);
                    if (config.isDebug())
                    {
                        _log.info(p1);
                    }
                    break;
                }
                case 10:
                {
                    p2 = temp.substring(5, i);
                    if (config.isDebug())
                    {
                        _log.info(p2);
                    }
                    break;
                }
                case 15:
                {
                    p3 = temp.substring(10, i);
                    if (config.isDebug())
                    {
                        _log.info(p3);
                    }
                    break;
                }
                case 20:
                {
                    p4 = temp.substring(15, i);
                    if (config.isDebug())
                    {
                        _log.info(p4);
                    }
                    break;
                }
                case 25:
                {
                    p5 = temp.substring(20, i);
                    if (config.isDebug())
                    {
                        _log.info(p5);
                    }
                    break;
                }
                case 30:
                {
                    p6 = temp.substring(25, i);
                    if (config.isDebug())
                    {
                        _log.info(p6);
                    }
                    break;
                }
            }
        }
        code = p1.concat("-" + p2).concat("-" + p3).concat("-" + p4).concat("-" + p5).concat("-" + p6);
        if (config.isDebug())
        {
            _log.log(Level.INFO, "Codigo de Ativacao: {0}", code);
        }
        generated = true;
        return code;

    }

    /**
     *
     * @return
     */
    public String getGeneratedCode()
    {
        if (code.isEmpty())
        {
            return "Erro: nao foi posivel gerar o codigo\n";
        }

        return code;
    }

    /**
     * Retorna Apenas 1 Instancia dessa Classe
     *
     * @return AppManager _instance
     */
    public static Serializer getInstance()
    {
        return SingletonHolder._instance;
    }
    @SuppressWarnings ("synthetic-access")
    private static class SingletonHolder
    {
        protected static final Serializer _instance = new Serializer();
    }

    /**
     *
     * @param st
     * <p/>
     * @return
     */
    public String encryptSerial(String st)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(st.getBytes());
            BigInteger hash = new BigInteger(1, digest.digest());
            StringBuilder serial = new StringBuilder(hash.toString());

            // Reduz a String p/ 30 Caracteres. deletando os caracteres apos o index 30...
            //-------------------------------
            for (int i = 0; i < serial.length(); i++)
            {
                if (serial.length() > 30)
                {
                    serial.deleteCharAt(serial.length() - 1); // deleta os ultimos caracteres.
                }
            }
            return serial.toString();

        }
        catch (NoSuchAlgorithmException ns)
        {
            ExceptionManager.ThrowException("Erro ao Verificar Serial:", ns);
            return st;
        }
    }

    /**
     *
     * @param args
     */
    public static void main(String args[])
    {
        new Serializer().generateActivationCode();
    }
}
