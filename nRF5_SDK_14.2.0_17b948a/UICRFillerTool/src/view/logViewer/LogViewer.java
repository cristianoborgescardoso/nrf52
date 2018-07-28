/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.logViewer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import uicrfillertool.Device;
import util.Config;
import static view.logViewer.LogViewer.appendLog;
import xml.DeviceXmls;

/**
 *
 * @author Cristiano Borges Cardoso <cristiano.cardoso@bradar.com.br>
 */
public class LogViewer extends javax.swing.JFrame
{

    //    private CurrentTablePanel tablePanel;
    private GenericLabelPanel labelPanel;
    private final int labelTextFontSize;
    private final int labelTitleFontSize;
    private final int labelPanelColumNumber;
    public Map<Integer, Device> deviceMap = new HashMap<>();
    private static JTextArea TALOG;
    private static JScrollBar JSCROLLPANE;

    /**
     * Creates new form ChartAndTableVoltage
     *
     * @param labelTextFontSize
     * @param labelTitleFontSize
     * @param labelPanelColumNumber
     */
    public LogViewer(int labelTextFontSize, int labelTitleFontSize, int labelPanelColumNumber)
    {
        initComponents();
        this.labelTextFontSize = labelTextFontSize;
        this.labelTitleFontSize = labelTitleFontSize;
        this.labelPanelColumNumber = labelPanelColumNumber;

        createLabelPanel("Device Panel");
        //createChartAndPanel("BLA");
        //labelPanelContainer.setLayout(new java.awt.GridLayout(2, 1, 2, 2));
        //revalidate();
        TALOG = taLog;
        JSCROLLPANE = this.jScrollPane2.getVerticalScrollBar();
        JSCROLLPANE.setValue(JSCROLLPANE.getMaximum());
    }

    private void fillDeviceList()
    {

    }

    public Device getFalseNode(String ID)
    {
        return deviceMap.get(Integer.valueOf(ID));
    }

    public void createLabelPanel(String tableName)
    {
        //     internalDesktop.setLayout(new java.awt.BorderLayout());
        labelPanel = new GenericLabelPanel(this, labelTextFontSize, labelTitleFontSize, tableName);
        labelPanel.setLayout(new java.awt.GridLayout(6, labelPanelColumNumber, 2, 2));
//        add(labelPanel);

        jspNodes.setLeftComponent(new JScrollPane(labelPanel));
        labelPanel.setVisible(true);
        jspNodes.getLeftComponent().revalidate();
//        labelPanelContainer.revalidate();
    }

    public GenericLabelPanel getLabelPanel()
    {
        return labelPanel;
    }

    public JSplitPane getJSplitPane1()
    {
        return jspNodes;
    }

    public JSplitPane getJSplitPane2()
    {
        return jSplitPane2;
    }

    public static void appendLog(String log)
    {
        TALOG.append(log);
        TALOG.append("\n");
        JSCROLLPANE.setValue(JSCROLLPANE.getMaximum());
    }

    public static void appendLog(String log, StackTraceElement[] stackTraceElements)
    {
        appendLog(log);

        for (StackTraceElement element : stackTraceElements)
        {
            appendLog(element.toString());
        }
    }

    private void updateLabelPanel(List<Device> deviceNodes)
    {
        try
        {
            if (labelPanel == null)
            {
                createLabelPanel("Devices");
            }
            labelPanel.getPanellFiller().notifySerieUpdate(deviceNodes);
        }
        catch (Exception e)
        {
            e.printStackTrace();
//            Mensagem.error.log(e);
        }
    }

    private void flashBoard()
    {
        StringBuilder commandLines = new StringBuilder();
        appendLog("Encoding devices configuration...");
        fillRegistryBytes();

        appendLog("Erasing UICR...");
        runCommandLines(Config.eraseUICR);
        commandLines.append(Config.eraseUICR);

        appendLog("Writing UICR Registers...");
        int memoryAddress = Config.uicr_address_start;
        for (Device device : GeneriComboPanel.getDeviceList())
        {
            runCommandLines(String.format("nrfjprog  -f NRF52  --memwr 0x%x --val 0x%x", memoryAddress, device.getRegistryValueInHex()));
            memoryAddress += Config.uicr_address_offset;
        }

        appendLog("Reading UICR Registers...");
        memoryAddress = Config.uicr_address_start;
        for (int deviceNumber = 0; deviceNumber < 32; deviceNumber++)
        {
            runCommandLineSynchronized(String.format("nrfjprog -f NRF52 --memrd 0x%x --n 4", memoryAddress));
            memoryAddress += Config.uicr_address_offset;
        }
    }

    private void fillRegistryBytes()
    {
        LogViewer.appendLog(String.format("Generating command lines..."));
        Device.fillRegistryBytes(GeneriComboPanel.getDeviceList());
    }

    private void runCommandLines(String commandLine)
    {
        try
        {
            appendLog(String.format("Running command: '$ %s'", commandLine));
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(commandLine);
            appendLog(new BufferedReader(new InputStreamReader(pr.getErrorStream())).lines().collect(Collectors.joining("\n")));
            appendLog(new BufferedReader(new InputStreamReader(pr.getInputStream())).lines().collect(Collectors.joining("\n")));

        }
        catch (IOException ex)
        {
            Logger.getLogger(LogViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void runCommandLineSynchronized(String commandLine)
    {
        new RunCommand(commandLine).start();

//        try
//        {
//            appendLog(String.format("Running command: '$ %s'", commandLine));
//            Runtime rt = Runtime.getRuntime();
//            Process pr = rt.exec(commandLine);
//            appendLog(new BufferedReader(new InputStreamReader(pr.getErrorStream())).lines().collect(Collectors.joining("\n")));
//            appendLog(new BufferedReader(new InputStreamReader(pr.getInputStream())).lines().collect(Collectors.joining("\n")));
//
//        }
//        catch (IOException ex)
//        {
//            Logger.getLogger(LogViewer.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jSplitPane2 = new javax.swing.JSplitPane();
        jspNodes = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taLog = new javax.swing.JTextArea();
        tfFilePath = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cbInternalGridColums = new javax.swing.JComboBox();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setResizeWeight(0.9);

        jspNodes.setResizeWeight(0.6);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("NRF52 Development Kit"));
        jScrollPane1.setToolTipText("NRF52 Development Kit");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/nrf52_dk_pinout_armmbed_enabled_v2.png"))); // NOI18N
        jLabel2.setText("jLabel2");
        jScrollPane1.setViewportView(jLabel2);

        jspNodes.setRightComponent(jScrollPane1);

        jSplitPane2.setTopComponent(jspNodes);

        taLog.setColumns(20);
        taLog.setRows(5);
        jScrollPane2.setViewportView(taLog);

        jSplitPane2.setRightComponent(jScrollPane2);

        tfFilePath.setText("thirdyParty/devices.xml");
        tfFilePath.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                tfFilePathActionPerformed(evt);
            }
        });

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Open");
        jButton2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setText("Columns");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                jLabel4MouseReleased(evt);
            }
        });

        cbInternalGridColums.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "4", "5", "8" }));
        cbInternalGridColums.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                cbInternalGridColumsActionPerformed(evt);
            }
        });

        jButton3.setText("Flash Board");
        jButton3.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 872, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfFilePath, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbInternalGridColums, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(cbInternalGridColums, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3))
                    .addComponent(tfFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton2)))
                .addGap(2, 2, 2)
                .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.dir")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Files", "xml");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            tfFilePath.setText(chooser.getSelectedFile().getPath());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton2ActionPerformed
    {//GEN-HEADEREND:event_jButton2ActionPerformed
        try
        {
            openFile(tfFilePath.getText());
        }
        catch (FileNotFoundException ex)
        {
            appendLog("File not found: " + tfFilePath.getText());
            Logger.getLogger(LogViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (JAXBException ex)
        {
            appendLog("Error while parsing XML file ", ex.getStackTrace());

            Logger.getLogger(LogViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tfFilePathActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_tfFilePathActionPerformed
    {//GEN-HEADEREND:event_tfFilePathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfFilePathActionPerformed

    private void jLabel4MouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jLabel4MouseReleased
    {//GEN-HEADEREND:event_jLabel4MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel4MouseReleased

    private void cbInternalGridColumsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cbInternalGridColumsActionPerformed
    {//GEN-HEADEREND:event_cbInternalGridColumsActionPerformed

        updateInternalPanelsColumNumber();
    }//GEN-LAST:event_cbInternalGridColumsActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton3ActionPerformed
    {//GEN-HEADEREND:event_jButton3ActionPerformed
        flashBoard();
    }//GEN-LAST:event_jButton3ActionPerformed

//    public JSplitPane getReferencePanel()
//    {
//        return jspReferences;
//    }
    private void updateInternalPanelsColumNumber()
    {
        labelPanel.setLayout(new java.awt.GridLayout(0, Integer.valueOf(cbInternalGridColums.getSelectedItem().toString()), 2, 2));
        labelPanel.revalidate();
    }

    private void updateReferencePanelsColumNumber()
    {
//        labelPanel.setLayout(new java.awt.GridLayout(0, Integer.valueOf(cbReferenceGridColums.getSelectedItem().toString()), 2, 2));
//        labelPanel.revalidate();
//        chartAndLabelPanel.getLabelPanel().setLayout(new java.awt.GridLayout(0, getReferenceGridColumNumber(), 1, 1));
//        chartAndLabelPanel.getLabelPanel().revalidate();
    }

    public int getReferenceGridColumNumber()
    {
//        return Integer.valueOf(cbReferenceGridColums.getSelectedItem().toString());
        return 2;
    }

    private void openFile(String filePath) throws FileNotFoundException, JAXBException
    {
        //InputStream arquivoDeTexto = new FileInputStream(filePath);
        JAXBContext jaxbContext = JAXBContext.newInstance(DeviceXmls.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        DeviceXmls devices = (DeviceXmls) unmarshaller.unmarshal(new InputStreamReader(new FileInputStream("devices.xml"), StandardCharsets.UTF_8));

        Config.inputDevices = devices.getInputDevicesXml().getDeviceXml();
        Config.outputDevices = devices.getOutputDevicesXml().getDeviceXml();

        for (int sensorNumber = 0; sensorNumber < Config.maxSensorNumber; sensorNumber++)
        {

        }

        Device tempdevice;
        deviceMap = new HashMap<>();
        tempdevice = new Device(100, 0, 0);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(1, 1, 0);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(255, 2, 255);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(55, 3, 0);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(35, 4, 0);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(1, 5, 0);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(255, 6, 255);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(35, 7, 0);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(1, 8, 0);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(255, 9, 255);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(55, 10, 0);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(35, 11, 0);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(1, 12, 0);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(255, 13, 255);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(55, 14, 0);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(35, 15, 0);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(1, 16, 0);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(255, 17, 255);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(55, 18, 0);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(35, 19, 0);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(1, 20, 0);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(255, 21, 255);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(55, 22, 0);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(35, 23, 0);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(1, 24, 0);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(255, 25, 255);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(55, 26, 0);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(35, 27, 0);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(55, 28, 0);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(35, 29, 0);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(55, 30, 0);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);
        tempdevice = new Device(9, 31, 0);
        deviceMap.put(tempdevice.getPinNumber(), tempdevice);

        updateLabelPanel(new ArrayList<>(deviceMap.values()));
        jspNodes.getLeftComponent().revalidate();

    }

    private void sleep100()
    {
        try
        {
            Thread.sleep(100);
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
//                Mensagem.error.log(ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Windows".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(LogViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(LogViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(LogViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(LogViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    new LogViewer(12, 12, 4).setVisible(true);
                }
                catch (Exception ex)
                {
                    Logger.getLogger(LogViewer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbInternalGridColums;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jspNodes;
    private javax.swing.JTextArea taLog;
    private javax.swing.JTextField tfFilePath;
    // End of variables declaration//GEN-END:variables
}

class RunCommand extends Thread
{

    private static synchronized void runSinchronizedCommand(String commandLine)
    {
        try
        {
            appendLog(String.format("Running command: '$ %s'", commandLine));
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(commandLine);
            appendLog(new BufferedReader(new InputStreamReader(pr.getErrorStream())).lines().collect(Collectors.joining("\n")));
            appendLog(new BufferedReader(new InputStreamReader(pr.getInputStream())).lines().collect(Collectors.joining("\n")));
            Thread.sleep(1000);
        }
        catch (IOException ex)
        {
            Logger.getLogger(LogViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(RunCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private final String commandLine;

    public RunCommand(String commandLine)
    {
        this.commandLine = commandLine;
    }

    public void run()
    {
        runSinchronizedCommand(commandLine);
    }
}
