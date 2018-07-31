/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Component;

/**
 *
 * @author Cristiano Borges Cardoso <cristiano.cardoso@bradar.com.br>
 */
public interface LabelFillerConteiner
{
    public void addComponent(Component label);
    public LogViewer getParentLogViewer();
}
