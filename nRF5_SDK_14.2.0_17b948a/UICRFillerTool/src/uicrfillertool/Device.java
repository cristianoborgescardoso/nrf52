/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uicrfillertool;

import java.util.List;
import view.logViewer.LogViewer;

/**
 *
 * @author Cristiano
 */
public class Device
{

    private final int pinNumber;
    private int deviceName, pinFunction;
    private int registryValueInHex;
    private static final int eightHexAllZero = 0x00000000;

    public Device(int deviceName, int pinNumber, int pinFunction)
    {
        this.deviceName = deviceName;
        this.pinNumber = pinNumber;
        this.pinFunction = pinFunction;
    }

    public int getDeviceName()
    {
        return deviceName;
    }

    public int getPinNumber()
    {
        return pinNumber;
    }

    public int getPinFunction()
    {
        return pinFunction;
    }

    private void replaceDigit(int digitPosition, int replacementDigit)
    {
        // Clear the 4 bits (i.e. 1 digit) at the position requested
        //this.registryValueInHex &= ~(0 << digitPosition * 4);

        // Now put the replacement value at the position requested
        this.registryValueInHex |= replacementDigit << digitPosition * 4;
    }

    private void fillRegistryBytes()
    {
        replaceDigit(6, pinNumber); //0xXX000000
        replaceDigit(4, pinFunction); //0x00X0000 just 4 bits, becouse only 0 or 1 will be used
        replaceDigit(2, deviceName);//0x0000XX000
        //replaceDigit(1, ???);//0x00000X00 - Not used, free for future work
    }

    public static void fillRegistryBytes(List<Device> deviceList)
    {

        LogViewer.appendLog(String.format("Number of configurated devices: '%d'", deviceList.size()));
        if (deviceList.isEmpty())
        {
            return;
        }

        deviceList.forEach((device) ->
        {
            device.registryValueInHex = eightHexAllZero;//in case of this method be called more than once; 
            device.fillRegistryBytes();
            LogViewer.appendLog(String.format("P.%d |function='%d'|name='%d'|  = 0x%x\n", device.getPinNumber(), device.getPinFunction(), device.getDeviceName(), device.getRegistryValueInHex()));
        });
        deviceList.get(0).replaceDigit(0, deviceList.size());//0x000000XX - The first value specify how many devices will be configurated        
        LogViewer.appendLog(String.format("P.%d |function='%d'|name='%d'|  = 0x%x\n", deviceList.get(0).getPinNumber(), deviceList.get(0).getPinFunction(), deviceList.get(0).getDeviceName(), deviceList.get(0).getRegistryValueInHex()));
    }

    public int getRegistryValueInHex()
    {
        return registryValueInHex;
    }

    public void setDeviceName(int deviceName)
    {
        this.deviceName = deviceName;
    }

    public void setPinFunction(int pinFunction)
    {
        this.pinFunction = pinFunction;
    }

}
