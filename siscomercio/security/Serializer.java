package com.siscomercio.security;

import com.siscomercio.Config;
import com.siscomercio.utilities.DiskUtil;
import com.siscomercio.utilities.MbUtil;
import com.siscomercio.utilities.NetworkUtil;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rayan
 */
public class Serializer
{
    private static final Logger _log = Logger.getLogger(Serializer.class.getName());
    static String serialHD = DiskUtil.getSerialNumber("c");
    static String serialMB = MbUtil.getMotherboardSN();
    static String mac = NetworkUtil.getMac();
    static StringBuffer dados = new StringBuffer(mac + serialHD + serialMB);
    static String code ="";
    /**
     *
     */
    public static boolean generated = false;

    /**
     * Captura Seriais HD / Placa de Rede e Mobo
     * @return
     */
    public static String generateActivationCode()
    {
        int cont = 0;
        String remover = "-";

        // Remove os "-" dos Numeros.
        //-------------------------------
        for(int i = 0;i < (dados.length() - remover.length() + 1);i++)
        {
            String res = dados.substring(i, (i + remover.length()));
            if(res.equals(remover))
            {
                int pos = dados.indexOf(remover);
                dados.delete(pos, pos + remover.length());
                cont++;
            }
        }

        if(Config.DEBUG)
            _log.log(Level.INFO, "A frase contem "+ cont+" ocorrencias de - ");


        // Reduz a String p/ 30 Caracteres. deletando os caracteres apos o index 30...
        //-------------------------------
        for(int i = 0;i < dados.length();i++)
        {
            if(dados.length() > 30)
            {
                if(Config.DEBUG)
                    _log.info("deletando caracteres adicionais..  \n");
                dados.deleteCharAt(dados.length() - 1); // deleta os ultimos caracteres.
            }
        }
        if(Config.DEBUG)
            _log.log(Level.INFO, "Nova String Com: "+ dados.length()+ "Caracteres. \n");

        // Divide a String em 5 Partes e Organiza com "-"
        //----------------------------------------------------
        String p1 = "", p2 = "", p3 = "", p4 = "", p5 = "";

        for(int i = 0;i < dados.length();i++)
        {
            if(i == 5)
            {
                p1 = dados.substring(0, i);
                if(Config.DEBUG)
                    _log.info(p1);
            }
            if(i == 10)
            {
                p2 = dados.substring(5, i);
                if(Config.DEBUG)
                    _log.info(p2);
            }
            if(i == 15)
            {
                p3 = dados.substring(10, i);
                if(Config.DEBUG)
                    _log.info(p3);
            }
            if(i == 20)
            {
                p4 = dados.substring(15, i);
                if(Config.DEBUG)
                    _log.info(p4);
            }
            if(i == 25)
            {
                p5 = dados.substring(20, i);
                if(Config.DEBUG)
                    _log.info(p5);
            }
        }
        code = p1.concat("-" + p2).concat("-" + p3).concat("-" + p4).concat("-" + p5);
         if(Config.DEBUG)
        _log.info("Codigo de Ativacao: " + code);
         generated = true;
        return code;

    }
    /**
     * 
     * @return
     */
    public static String getGeneratedCode()
    {
        if(code.isEmpty())
            return "nao foi posivel gerar o codigo\n";

        return code;
    }
    /**
     *
     * @param args
     */
    public static void main(String args[])
    {
           generateActivationCode();
     }
}


