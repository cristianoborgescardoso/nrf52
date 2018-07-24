package view.logViewer;

import java.awt.Color;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import uicrfillertool.Device;

/**
 * @author Cristiano
 */
public class LabelFiller
{

    private final GenericLabelPanel parent;
    private final int labelTextFontSize;
    private final int labelTitleFontSize;
    DecimalFormat formatter = new DecimalFormat("0.00");

    public LabelFiller(GenericLabelPanel parent, int labelTextFontSize, int labelTitleFontSize) 
    {
        this.labelTextFontSize = labelTextFontSize;
        this.labelTitleFontSize = labelTitleFontSize;
        this.parent = parent;
    }
    private Map<String, JLabel> labelMap = new HashMap<>();
    private Map<String, String> oldValueMap = new HashMap<>();

    private final float threshold_10 = (float) 0.1; //10%
    private final float threshold_20 = (float) 0.2; //20%
    private final float threshold_40 = (float) 0.4; //30%
    private final float threshold_50 = (float) 0.5; //40%
    private final float threshold_60 = (float) 0.6; //50%
    private final float threshold_70 = (float) 0.7; //60%

    private final StringBuilder stringBuilder = new StringBuilder();

    public Map<String, JLabel> getLabelMap()
    {
        return labelMap;
    }

    public synchronized void notifySerieUpdate(List<Device> deviceList)
    {
        SwingUtilities.invokeLater(()
                ->
        {
            String pinNumber = "";
            String sensorValue = "";
            try
            {
                for (Device device : deviceList)
                {
                    System.out.println("adfasdfasdf");
                    pinNumber = String.valueOf(device.getPinNumber());
                    sensorValue = formatter.format(device.getPinNumber());
                    if (labelMap.get(pinNumber) == null)
                    {
                        JLabel label = new javax.swing.JLabel();

                        label.setMinimumSize(new Dimension(50, 12));
                        label.setPreferredSize(new Dimension(50, 25));
                        label.setMaximumSize(new Dimension(50, 25));
                        label.setBackground(new java.awt.Color(51, 204, 0));
                        label.setText(sensorValue);
//                        label.setBorder(javax.swing.BorderFactory.createTitledBorder(line[i].split(" ")[0]));
                        label.setBorder(javax.swing.BorderFactory.createTitledBorder(null, pinNumber, javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, labelTitleFontSize)));//8
                        label.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                        label.setOpaque(true);
                        label.setFont(new java.awt.Font("Tahoma", 1, labelTextFontSize));//11
                        parent.addComponent(label);
                        labelMap.put(pinNumber, label);
                        oldValueMap.put(pinNumber, sensorValue);
//                        label.setToolTipText(String.format("%s:'%s'\n%s", sensorName, sensorValue, getSensorDiscription(sensorName).replaceAll(";", "\n")));

//                                stringBuilder.setLength(0);
//                                stringBuilder.append(String.format("<html><p style='color:#0000ff;'>%s</p>", sensorName));
//                                stringBuilder.append(String.format("%s ", getSensorDiscription(sensorName).replaceAll(";", "<br/>")));
//                                stringBuilder.append("</html>");
//                                label.setToolTipText(stringBuilder.toString());
//                        parent.setLayout(new java.awt.GridLayout(0, 4, 1, 1));
//                        parent.revalidate();
                        label.addMouseListener(new java.awt.event.MouseAdapter()
                        {
                            public void mouseReleased(java.awt.event.MouseEvent evt)
                            {
                                labelMouseReleased(evt, label);
                            }
                        });

                    }

                    oldValueMap.put(pinNumber, labelMap.get(pinNumber).getText());
                    labelMap.get(pinNumber).setText(sensorValue);
//                    parent.setLayout(new java.awt.GridLayout(0, 4, 1, 1));
//                    parent.revalidate();

                    paintLabel(labelMap.get(pinNumber), pinNumber, device.getPinFunction());
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
//                        Mensagem.error.log(ex, sensorName, sensorValue);
            }
        });
    }


    public void paintLabel(JLabel label, String sensorName, double newValue)
    {
        try
        {

            if (newValue >= 50)
            {
                label.setBackground(java.awt.Color.RED);
//                    label.setFont(new java.awt.Font("Tahoma", 1, 16));//11
            }
            else if (newValue >= 30)
            {
                label.setBackground(java.awt.Color.MAGENTA);
            }
            else if (newValue >= 20)
            {
                label.setBackground(new Color(255, 75, 75));
            }
            else if (newValue >= 10)
            {
                label.setBackground(java.awt.Color.ORANGE);
            }
            else if (newValue > 5)
            {
                label.setBackground(java.awt.Color.YELLOW);
            }
            else
            {
                label.setBackground(java.awt.Color.GREEN);
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
            String sensorName;
            String sensorValue;
            StringBuilder positionBuilder = new StringBuilder();
            try
            {
                int referenceNumber = 1;
                for (Device device : deviceList)
                {
                    sensorName = String.valueOf(referenceNumber);
                    referenceNumber++;

                    sensorValue = String.valueOf(device.getDeviceName());

                    if (labelMap.get(sensorName) == null)
                    {
                        JLabel label = new javax.swing.JLabel();

                        label.setMinimumSize(new Dimension(50, 12));
                        label.setPreferredSize(new Dimension(50, 25));
                        label.setMaximumSize(new Dimension(50, 25));
                        label.setBackground(new java.awt.Color(51, 204, 0));
                        label.setText(sensorValue);
//                        label.setBorder(javax.swing.BorderFactory.createTitledBorder(line[i].split(" ")[0]));
                        label.setBorder(javax.swing.BorderFactory.createTitledBorder(null, sensorName, javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, labelTitleFontSize)));//8
                        label.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                        label.setOpaque(true);
                        label.setFont(new java.awt.Font("Tahoma", 1, labelTextFontSize));//11
                        parent.addComponent(label);
                        labelMap.put(sensorName, label);
                        oldValueMap.put(sensorName, sensorValue);
//                        label.setToolTipText(String.format("%s:'%s'\n%s", sensorName, sensorValue, getSensorDiscription(sensorName).replaceAll(";", "\n")));

//                                stringBuilder.setLength(0);
//                                stringBuilder.append(String.format("<html><p style='color:#0000ff;'>%s</p>", sensorName));
//                                stringBuilder.append(String.format("%s ", getSensorDiscription(sensorName).replaceAll(";", "<br/>")));
//                                stringBuilder.append("</html>");
//                                label.setToolTipText(stringBuilder.toString());
//                        parent.setLayout(new java.awt.GridLayout(0, 4, 1, 1));
//                        parent.revalidate();
                        label.addMouseListener(new java.awt.event.MouseAdapter()
                        {
                            @Override
                            public void mouseReleased(java.awt.event.MouseEvent evt)
                            {
                                labelReferenceMouseReleased(evt, label);
                            }
                        });
                    }

                    oldValueMap.put(sensorName, labelMap.get(sensorName).getText());
                    labelMap.get(sensorName).setText(sensorValue);

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

    private void labelMouseReleased(java.awt.event.MouseEvent evt, JLabel label)
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
