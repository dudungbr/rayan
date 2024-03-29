package com.siscomercio.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Logger;

/**
 *
 * @author Rayan
 */
public class DiskUtil
{
    private static final Logger _log = Logger.getLogger(DiskUtil.class.getName());

    private DiskUtil()
    {
    }

    /**
     *
     * @param drive
     * <p/>
     * @return
     */
    public static String getSerialNumber(String drive)
    {
        String result = "";
        try
        {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                    + "Set colDrives = objFSO.Drives\n"
                    + "Set objDrive = colDrives.item(\"" + drive + "\")\n"
                    + "Wscript.Echo objDrive.SerialNumber";  // see note
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null)
            {
                result += line;
            }
            input.close();
        }
        catch (Exception e)
        {
            SystemUtil.getInstance().showErrorMsg(e.getMessage(), true);
        }
        return result.trim();
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args)
    {
        String sn = DiskUtil.getSerialNumber("C");
        javax.swing.JOptionPane.showConfirmDialog((java.awt.Component) null, sn, "Serial Number of C:",
                                                  javax.swing.JOptionPane.DEFAULT_OPTION);
    }
}
