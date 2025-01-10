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

import java.awt.event.KeyEvent;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class MaxLengthPrescriptionMinusDocument extends PlainDocument {

    private int max;

    public MaxLengthPrescriptionMinusDocument(int maxLength) {
        max = maxLength;
    }

    public void insertString(int offset, String str, AttributeSet at) throws BadLocationException {

        boolean flag = true;

        if (str == null) {
            return;
        }
        char[] upper = str.toCharArray();


        for (int i = 0; i < upper.length; i++) {
            int j = (int) upper[i];

            if ((!Character.isDigit(upper[i]) && (j != 46) && (j != 45)) || (getLength() + str.length() > max)) {
                java.awt.Toolkit.getDefaultToolkit().beep();
                flag = false;
            }
        }
        if (flag) {
            super.insertString(offset, str, at);
        }



    }
}
