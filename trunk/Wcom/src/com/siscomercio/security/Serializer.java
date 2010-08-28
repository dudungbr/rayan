package com.siscomercio.security;

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


 
}
