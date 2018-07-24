package view.logViewer;

import java.awt.Color;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import uicrfillertool.Device;

/**
 * @author Cristiano
 */
public class PanelFiller
{

    private final GenericLabelPanel parent;
    private final int labelTextFontSize;
    private final int labelTitleFontSize;
    DecimalFormat formatter = new DecimalFormat("0.00");

    public PanelFiller(GenericLabelPanel parent, int labelTextFontSize, int labelTitleFontSize)
    {
        this.labelTextFontSize = labelTextFontSize;
        this.labelTitleFontSize = labelTitleFontSize;
        this.parent = parent;
    }
    private Map<Integer, GeneriComboPanel> panellMap = new HashMap<>();
    private Map<Integer, Integer> oldValueMap = new HashMap<>();

    private final float threshold_10 = (float) 0.1; //10%
    private final float threshold_20 = (float) 0.2; //20%
    private final float threshold_40 = (float) 0.4; //30%
    private final float threshold_50 = (float) 0.5; //40%
    private final float threshold_60 = (float) 0.6; //50%
    private final float threshold_70 = (float) 0.7; //60%

    private final StringBuilder stringBuilder = new StringBuilder();

    public Map<Integer, GeneriComboPanel> getLabelMap()
    {
        return panellMap;
    }

    public synchronized void notifySerieUpdate(List<Device> deviceList)
    {
        SwingUtilities.invokeLater(()
                ->
        {
            int pinNumber;
            String sensorValue = "";
            try
            {
                for (Device device : deviceList)
                {
                    pinNumber = device.getPinNumber();
                    sensorValue = formatter.format(device.getPinNumber());
                    if (panellMap.get(pinNumber) == null)
                    {
                        GeneriComboPanel generiComboPanel = new GeneriComboPanel(parent.getParentLogViewer(), labelTextFontSize, labelTitleFontSize, pinNumber);

                        generiComboPanel.setMinimumSize(new Dimension(50, 12));
                        generiComboPanel.setPreferredSize(new Dimension(50, 25));
                        generiComboPanel.setMaximumSize(new Dimension(50, 25));
                        //generiComboPanel.setBackground(new java.awt.Color(51, 204, 0));
                        //ge.setText(sensorValue);
//                        label.setBorder(javax.swing.BorderFactory.createTitledBorder(line[i].split(" ")[0]));
                        generiComboPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "P." + pinNumber, javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, labelTitleFontSize)));//8
                        generiComboPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                        generiComboPanel.setOpaque(true);
                        generiComboPanel.setFont(new java.awt.Font("Tahoma", 1, labelTextFontSize));//11
                        parent.addComponent(generiComboPanel);
                        panellMap.put(pinNumber, generiComboPanel);
                        //oldValueMap.put(pinNumber, sensorValue);
//                        label.setToolTipText(String.format("%s:'%s'\n%s", sensorName, sensorValue, getSensorDiscription(sensorName).replaceAll(";", "\n")));

//                                stringBuilder.setLength(0);
//                                stringBuilder.append(String.format("<html><p style='color:#0000ff;'>%s</p>", sensorName));
//                                stringBuilder.append(String.format("%s ", getSensorDiscription(sensorName).replaceAll(";", "<br/>")));
//                                stringBuilder.append("</html>");
//                                label.setToolTipText(stringBuilder.toString());
//                        parent.setLayout(new java.awt.GridLayout(0, 4, 1, 1));
//                        parent.revalidate();
                        generiComboPanel.addMouseListener(new java.awt.event.MouseAdapter()
                        {
                            @Override
                            public void mouseReleased(java.awt.event.MouseEvent evt)
                            {
                                labelMouseReleased(evt, generiComboPanel);
                            }
                        });

                    }

                    //  oldValueMap.put(pinNumber, labelMap.get(pinNumber).getText());
                    //labelMap.get(pinNumber).setText(sensorValue);
//                    parent.setLayout(new java.awt.GridLayout(0, 4, 1, 1));
//                    parent.revalidate();
                    paintLabel(panellMap.get(pinNumber), String.valueOf(pinNumber), device.getPinFunction());
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
//                        Mensagem.error.log(ex, sensorName, sensorValue);
            }
        });
    }

    public void paintLabel(GeneriComboPanel generiComboPanel, String sensorName, double newValue)
    {
        if (true)
        {
            return;
        }
        try
        {

            if (newValue >= 50)
            {
                generiComboPanel.setBackground(java.awt.Color.RED);
//                    label.setFont(new java.awt.Font("Tahoma", 1, 16));//11
            }
            else if (newValue >= 30)
            {
                generiComboPanel.setBackground(java.awt.Color.MAGENTA);
            }
            else if (newValue >= 20)
            {
                generiComboPanel.setBackground(new Color(255, 75, 75));
            }
            else if (newValue >= 10)
            {
                generiComboPanel.setBackground(java.awt.Color.ORANGE);
            }
            else if (newValue > 5)
            {
                generiComboPanel.setBackground(java.awt.Color.YELLOW);
            }
            else
            {
                generiComboPanel.setBackground(java.awt.Color.GREEN);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
//            Mensagem.error.log(ex, parent.getTitle(), "sensorName");
        }
    }

    public synchronized void updateReferenceSet(List<Device> deviceList)
    {
        SwingUtilities.invokeLater(()
                ->
        {
            Integer sensorName;
            int pinNumber;
            StringBuilder positionBuilder = new StringBuilder();
            try
            {
                int referenceNumber = 1;
                for (Device device : deviceList)
                {
                    sensorName = referenceNumber;
                    referenceNumber++;

                    pinNumber = device.getPinNumber();

                    if (panellMap.get(sensorName) == null)
                    {
                        GeneriComboPanel generiComboPanel = new GeneriComboPanel(parent.getParentLogViewer(), labelTextFontSize, labelTitleFontSize, pinNumber);

                        generiComboPanel.setMinimumSize(new Dimension(50, 12));
                        generiComboPanel.setPreferredSize(new Dimension(50, 25));
                        generiComboPanel.setMaximumSize(new Dimension(50, 25));
                        //generiComboPanel.setBackground(new java.awt.Color(51, 204, 0));
                        //generiComboPanel.setText(pinNumber);
//                        label.setBorder(javax.swing.BorderFactory.createTitledBorder(line[i].split(" ")[0]));
                        generiComboPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "P." + sensorName, javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, labelTitleFontSize)));//8
                        generiComboPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                        generiComboPanel.setOpaque(true);
                        generiComboPanel.setFont(new java.awt.Font("Tahoma", 1, labelTextFontSize));//11
                        parent.addComponent(generiComboPanel);
                        panellMap.put(sensorName, generiComboPanel);
                        oldValueMap.put(sensorName, pinNumber);
//                        label.setToolTipText(String.format("%s:'%s'\n%s", sensorName, sensorValue, getSensorDiscription(sensorName).replaceAll(";", "\n")));

//                                stringBuilder.setLength(0);
//                                stringBuilder.append(String.format("<html><p style='color:#0000ff;'>%s</p>", sensorName));
//                                stringBuilder.append(String.format("%s ", getSensorDiscription(sensorName).replaceAll(";", "<br/>")));
//                                stringBuilder.append("</html>");
//                                label.setToolTipText(stringBuilder.toString());
//                        parent.setLayout(new java.awt.GridLayout(0, 4, 1, 1));
//                        parent.revalidate();
                        generiComboPanel.addMouseListener(new java.awt.event.MouseAdapter()
                        {
                            @Override
                            public void mouseReleased(java.awt.event.MouseEvent evt)
                            {
                                //labelReferenceMouseReleased(evt, generiComboPanel);
                            }
                        });
                    }

                    // oldValueMap.put(sensorName, labelMap.get(sensorName).getText());
                    //labelMap.get(sensorName).setText(pinNumber);
                    parent.setLayout(new java.awt.GridLayout(0, parent.getParentLogViewer().getReferenceGridColumNumber(), 1, 1));
                    parent.revalidate();

//                            paintLabel(labelMap.get(sensorName), sensorName, location.getLocalizationError());
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
//                        Mensagem.error.log(ex, sensorName, sensorValue);
            }
        });
    }

    private void labelMouseReleased(java.awt.event.MouseEvent evt, JPanel jPanel)
    {

        if (true)
        {
            return;
        }
        StringBuilder localStringBuilder = new StringBuilder();
        //FalseNode falseNode;
        //  falseNode = parent.getParentLogViewer().getFalseNode(((TitledBorder) label.getBorder()).getTitle());

        // positionMaxVol = testMaxVol3(falseNode.getMatrices().getMatrixA(), falseNode.getMatrices().getMatrixB());
        localStringBuilder.append("------------------------------------------");
        // localStringBuilder.append(String.valueOf(falseNode.getID()));
        localStringBuilder.append("------------------------------------------");

        localStringBuilder.append("-----Human Reader-----\n");

        localStringBuilder.append("Matrix A:   ");
        localStringBuilder.append("\n");
        // localStringBuilder.append(falseNode.getMatrices().getPrintableMatrixA(9, 6));
        localStringBuilder.append("\n");

        localStringBuilder.append("Matrix b:  ");
        localStringBuilder.append("\n");
        // localStringBuilder.append(falseNode.getMatrices().getPrintableMatrixB(9, 6));
        localStringBuilder.append("\n");

        localStringBuilder.append("-----Matlab Oriented-----\n");

        localStringBuilder.append("%Matrix A:");
        //  localStringBuilder.append(falseNode.getMatrices().getPrintableMatrixAToMatlab(9, 6));

        localStringBuilder.append("%Matrix b:   ");
        //  localStringBuilder.append(falseNode.getMatrices().getPrintableMatrixBToMatlab(9, 6));
        localStringBuilder.append("\n");

        localStringBuilder.append("Rererences:   ");
        localStringBuilder.append("\n");
        // localStringBuilder.append(falseNode.getReferenceSet().show());
        localStringBuilder.append("\n");

        localStringBuilder.append("realPos:   ");
        //  localStringBuilder.append(falseNode.getPosicaoPerfeita().getPosition().toString());
        localStringBuilder.append("\n");
        localStringBuilder.append("posCalc:   ");
        // localStringBuilder.append(falseNode.getPosCalc().getPosition().toString());
        localStringBuilder.append("\n");
        localStringBuilder.append("posMaxVol: ");
        /////////localStringBuilder.append(positionMaxVol.toString());

        localStringBuilder.append("\n");
        localStringBuilder.append("posReCalc: ");

        // falseNode.estimatePos(LocalizationNode.LocalizationMethod.LEAST_SQUARES_SVD);
        // falseNode.estimatePos(LocalizationNode.LocalizationMethod.LEAST_SQUARES);
        // localStringBuilder.append(falseNode.getPosRecalc().getPosition().toString());
        localStringBuilder.append("\n");

        localStringBuilder.append("distance: ");
        // localStringBuilder.append(formatter.format(falseNode.getPosCalc().getPosition().distanceTo(falseNode.getPosicaoPerfeita().getPosition())));
        localStringBuilder.append("\n");
        localStringBuilder.append("NEW distance: ");
        //  localStringBuilder.append(formatter.format(falseNode.getPosRecalc().getPosition().distanceTo(falseNode.getPosicaoPerfeita().getPosition())));
        localStringBuilder.append("\n");
        localStringBuilder.append("distance MaxVol: ");
        // localStringBuilder.append(formatter.format(positionMaxVol.distanceTo(falseNode.getPosicaoPerfeita().getPosition())));
        localStringBuilder.append("\n");
        localStringBuilder.append("NEW Condition number: ");
        // localStringBuilder.append(fixedWidthDoubletoString(falseNode.getMatrices().getMatrixA().cond(), 12, 3));
        localStringBuilder.append("\n");

        localStringBuilder.append("------------------------------------------");
        localStringBuilder.append("END");
        localStringBuilder.append("------------------------------------------");
        localStringBuilder.append("\n");

        parent.getParentLogViewer().appendLog(localStringBuilder.toString());

        //-----------------------RightPanel------------------------
        //   parent.getParentLogViewer().updateDeviceViewer(falseNode);
        //---------------------------------------------------------
//        parent.getParentLogViewer().appendLog(getMatrixString(falseNode));
    }

    private void labelReferenceMouseReleased(java.awt.event.MouseEvent evt, JLabel label)
    {
        if (true)
        {
            return;
        }
        if (label.getBackground() == java.awt.Color.GREEN)
        {
            label.setBackground(java.awt.Color.ORANGE);
        }
        else
        {
            label.setBackground(java.awt.Color.GREEN);
        }
    }
}
