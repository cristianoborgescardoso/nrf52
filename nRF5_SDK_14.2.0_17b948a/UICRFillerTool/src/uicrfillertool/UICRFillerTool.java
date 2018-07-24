/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uicrfillertool;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Cristiano
 */
public class UICRFillerTool
{
    private List<Device> deviceList = new ArrayList<>();
    private final static int hexZero = 0x00000000;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        UICRFillerTool uicrFillerTool = new UICRFillerTool();
        uicrFillerTool.deviceList.add(new Device(1, 1, 0));
        uicrFillerTool.deviceList.add(new Device(255, 255, 255));
        uicrFillerTool.deviceList.add(new Device(55, 22, 0));
        uicrFillerTool.deviceList.add(new Device(35, 27, 0));
        Device.fillRegistryBytes(uicrFillerTool.deviceList);

        for (Device device : uicrFillerTool.deviceList)
        {
            System.out.printf("0x%x\n", device.getRegistryValueInHex());
        }

        //long hex = 0x2222222222222222L;
        int hex = 0x00000000;

        System.out.printf("0x%x\n", replaceDigit(hex, 6, 255));
    }

    public static int replaceDigit(int originalValue, int digitPosition, int replacementDigit)
    {
        // Clear the 4 bits (i.e. 1 digit) at the position requested
        originalValue &= ~(hexZero << digitPosition * 4);

        // Now put the replacement value at the position requested
        originalValue |= replacementDigit << digitPosition * 4;

        return originalValue;
    }
}


