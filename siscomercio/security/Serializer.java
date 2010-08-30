package com.siscomercio.security;

import com.siscomercio.Config;
import com.siscomercio.utilities.DiskInfo;
import com.siscomercio.utilities.MotherBoardInfo;
import com.siscomercio.utilities.NetworkInfo;
import java.util.logging.Logger;

/**
 *
 * @author Rayan
 */
public class Serializer
{
    private static Logger _log = Logger.getLogger(Serializer.class.getName());
    String serialHD = DiskInfo.getSerialNumber("c");
    String serialMB = MotherBoardInfo.getMotherboardSN();
    String mac = NetworkInfo.getMac();
    StringBuffer dados = new StringBuffer(mac + serialHD + serialMB);

    /**
     * Captura Seriais HD / Placa de Rede e Mobo
     */
    public void gereCodigoAtivacao()
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
            _log.info("A frase contem " + cont + " ocorrencias de " + remover);


        // Reduz a String p/ 30 Caracteres. deletando os caracteres apos o index 30...
        //-------------------------------
        for(int i = 0;i < dados.length();i++)
        {
            if(dados.length() > 30)
            {
                if(Config.DEBUG)
                _log.info("deletando caracteres adicionais");
                dados.deleteCharAt(dados.length() - 1); // deleta os ultimos caracteres.
            }
        }
        if(Config.DEBUG)
            _log.info("Nova String Com: " + dados.toString() + "Caracteres.");

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


            //String com = dados.concat(str2);
            //     x.concat("-");
            // _log.info("concatenando apos o  caractere: " + x + " por - ");
            //dados.setCharAt(i,'-'); // troca o caractere da posição "i" por '-'
            //  _log.info("subistituindo o caractere: " + x + " por - ");
        }
        String code = p1.concat("-" + p2).concat("-" + p3).concat("-" + p4).concat("-" + p5);
        _log.info("Codigo de Ativação: " + code);

    }

    public Serializer()
    {
        gereCodigoAtivacao();
    }

    public static void main(String[] args) throws Exception
    {
        new Serializer();
    }

    public void checkDisk()
    {
    }

}


