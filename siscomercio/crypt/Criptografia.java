package com.siscomercio.crypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.siscomercio.init.Config;

/**
 * $Revision$
 * $Author$
 * $Date$
 * <p/>
 * @author William Menezes
 *
 */
public class Criptografia
{
    private static final Logger _log = Logger.getLogger(Criptografia.class.getName());

    /**
     * Criptografa a Senha do Usuario P/ Versao MD5 de Criptografia
     * <p/>
     * @param password
     * <p/>
     * @return password
     */
    public static String criptografe(String password)
    {
        byte[] defaultBytes = password.getBytes();
        try
        {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(defaultBytes);
            byte messageDigest[] = algorithm.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
            {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            if (Config.getInstance().isDebug())
            {
                _log.log(Level.INFO, "Criptografando Senha:  {0} P / Versao  MD5 : {1}\n", new Object[]
                {
                    password, hexString.toString()
                });
            }
            password = hexString + "";
        }
        catch (NoSuchAlgorithmException nsae)
        {
            try
            {
                throw nsae;
            }
            catch (NoSuchAlgorithmException ex)
            {
                Logger.getLogger(Criptografia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return password;
    }
}
