package com.siscomercio.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.siscomercio.Config;

/**
 * $Revision$
 * $Author$
 * $Date$
 * @author William Menezes
 *
 */
public class Criptografia
{
    private static Logger _log = Logger.getLogger(Criptografia.class.getName());

    /**
     *
     * @param password
     * @return
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
            for(int i = 0;i < messageDigest.length;i++)
            {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            if(Config.DEBUG)
                _log.info("Criptografando Senha:  " + password + " P / Versao  MD5 : " + hexString.toString() + "\n");
            password = hexString + "";
        }
        catch(NoSuchAlgorithmException nsae)
        {
            try
            {
                throw nsae;
            }
            catch(NoSuchAlgorithmException ex)
            {
                Logger.getLogger(Criptografia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return password;
    }

}
