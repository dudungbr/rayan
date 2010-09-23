package com.siscomercio.managers;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * $Revision$
 * $Author$
 * $Date$
 *
 * @author Rayan
 */
public class LogMonitor
{
    private static final Logger _log = Logger.getLogger(LogMonitor.class.getName());

    /**
     *
     * @throws IOException
     */
    public static void init() throws IOException
    {
        _log.info("Inicializando o Log Monitor...\n");
        try
        {
            File arquivo = new File("./log/meulog.log");

            // Cria o arquivo se este não existe ainda
            boolean ok = arquivo.createNewFile();

            if(ok)
            {
               _log.info("Arquivo de log criado com sucesso.");
            }
            else
            {
                _log.warning("Nao foi possivel criar o arquivo de log .");
            }


            // Cria um alimentador de arquivo que adiciona os dados
            //------------------------------------------------
            boolean append = true;
            FileHandler handler = new FileHandler("./log/meulog.log", append);
            handler.setFormatter(new SimpleFormatter());
            
            // Adicione ao logger desejado
            //------------------------------------------------
            Logger logger = Logger.getLogger("com.siscomercio");
            logger.addHandler(handler);
        }
        catch(IOException e)
        {
            _log.severe("Erro: "+e.getLocalizedMessage());
        }
    }

}
