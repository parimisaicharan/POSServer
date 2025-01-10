
/*
 * Copyright Titan Industries Limited  All Rights Reserved. * 
 * This code is written by Enteg Info tech Private Limited for the Titan Eye+ Project * 
 *
 * 
 * 
 * VERSION
 * Initial Version
 * Date of Release
 * 
 * 
 * Change History
 * Version <vvv>
 * Date of Release <dd/mm/yyyy>
 * To be filled by the code Developer in Future
 * 
 * 
 * Utility Class for the Limiting the Lenght of Text in JTextAres
 * 
 * 
 */
package ISRetail.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextArea;

/**
 *
 * @author enteg
 */
public class JTextAreaFixed extends JTextArea {

    public JTextAreaFixed(int length) {

        super.setDocument(new MaxLengthDocument(length));

    }

    @Override
    public void setEditable(boolean b) {
        super.setEditable(b);
        if (!b) {
            this.setBackground(new Color(235, 235, 228));
        }else{
            this.setBackground(new Color(255, 255, 255));
        }
        
    }
 
}
