package com.siscomercio.controller.managers;

import com.siscomercio.init.Config;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * $Revision$ $Author$ $Date$
 *
 * @author Rayan
 */
public class LogManager
{
    private static final Logger _log = Logger.getLogger(LogManager.class.getName());
    Config config = Config.getInstance();

    /**
     *
     */
    public void init()
    {

        if (!config.isEnableLog())
        {
            return;
        }

        if (config.isDeveloper())
        {
            _log.info("Inicializando o Log Manager..;");
        }
        configureLogger();

    }

    private void configureLogger()
    {
        _log.info("Configurando Logger...");
        try
        {
            File pasta = new File("./log");

            if (pasta.exists())
            {
                pasta.delete();
                pasta.mkdir();


            }
            else
            {
                _log.info("A Pasta de Logs nao Existe, criando...");
                pasta.mkdir();

            }
            File arquivo = new File("./log/meulog.log");

            if (arquivo.exists())
            {
                //deleta log antigo
                arquivo.delete();

                //cria novo arquivo de log.
                arquivo.createNewFile();
                _log.info("Arquivo de log criado com sucesso.");

            }
            else
            {
                arquivo.createNewFile();
            }

            // Cria um alimentador de arquivo que adiciona os dados
            //------------------------------------------------
            boolean append = true;
            FileHandler handler = new FileHandler("./log/meulog.log", append);


            handler.setFormatter(new SimpleFormatter());

            // Adicione ao logger desejado
            //------------------------------------------------
            Logger logger = Logger.getLogger(config.getLogPath());
            logger.addHandler(handler);
        }
        catch (IOException | SecurityException ex)
        {
            Logger.getLogger(LogManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
