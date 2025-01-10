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
 * Utility Class for the Limiting the Lenght of Text in JTextField
 * 
 * 
 */
package ISRetail.components;

import java.awt.Color;
import javax.swing.JTextField;

/**
 *
 * @author Administrator
 */
public class JTextFieldFixed extends JTextField {

    public JTextFieldFixed(int length) {
        super(new MaxLengthDocument(length), "", length);
  

    }

    public JTextFieldFixed(String text, int length) {
        super(new MaxLengthDocument(length), text, length);

    }

    @Override
    public void setEditable(boolean b) {
        super.setEditable(b);
        if(!b)
        {
            this.setBackground(new Color(235, 235, 228));
    }else {
            this.setBackground(new Color(255, 255, 255));
        }
    }

}

