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
 * Utility Class for JTextField
 * 
 * 
 */

package ISRetail.components;

import java.awt.Color;
import javax.swing.JTextField;


public class JTextFieldFixedPrescriptionPlus extends JTextField 
{

 public JTextFieldFixedPrescriptionPlus(int length)
  {
     super(new MaxLengthPrescriptionPlusDocument(length), "",length);
   
    
  }
  
  public JTextFieldFixedPrescriptionPlus(String text, int length) {
   super(new MaxLengthPrescriptionPlusDocument(length), text,length);
  }
 public void setEditable(boolean b) {
        super.setEditable(b);
        if(!b)
            this.setBackground(new Color(235, 235, 228));
        else{
            this.setBackground(new Color(255, 255, 255));
        }
    }
}

