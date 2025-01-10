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
 * Utility Class for PlainDocument
 * 
 * 
 */

package ISRetail.components;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

 public class MaxLengthDocument extends PlainDocument {
  private int max;

  public MaxLengthDocument(int maxLength) {
    max = maxLength;
  }

  public void insertString(int offset, String str, AttributeSet at) throws BadLocationException {
   
      if (getLength() + str.length() > max)
      java.awt.Toolkit.getDefaultToolkit().beep();
      else
          super.insertString(offset, str,at);
   
      
  }
  
 

}