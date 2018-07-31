/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.List;
import xml.DeviceXml;

/**
 *
 * @author Cristiano
 */
public class Config
{

    public static int maxSensorNumber = 32;
    public static List<DeviceXml> inputDevices = new ArrayList<>();
    public static List<DeviceXml> outputDevices = new ArrayList<>();
    public static final int uicr_address_start = 0x10001080;
    public static final int uicr_address_offset = 0x04;
    public static final String eraseUICR = "nrfjprog -f NRF52 --eraseuicr";
    public static final String commandLineFileName = "writeUICR.sh";
    public static final String input = "input";
    public static final String output = "output";

    public static enum IOCLASS
    {
        INPUT,
        OUTPUT;
    }

}
