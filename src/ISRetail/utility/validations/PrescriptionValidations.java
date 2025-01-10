/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.utility.validations;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Properties;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author enteg
 */
public class PrescriptionValidations {

    public PrescriptionValidations() {

    }

    /***Prescription Specific START****************/
    //checks One Prescription(CH,PR and CL) Row is not empty- If any one field has value , it will return true
    public static boolean checkOneRowIsNotEmpty(JTextField sph, JTextField cyl, JTextField axis, JTextField prism, JTextField base, JTextField va) {
        boolean rowIsNotEmpty = false;
        try {
            if (sph != null && cyl != null && axis != null && prism != null && base != null && va != null) {
                if (Validations.isFieldNotEmpty(sph.getText()) || Validations.isFieldNotEmpty(cyl.getText()) || Validations.isFieldNotEmpty(axis.getText()) || Validations.isFieldNotEmpty(prism.getText()) || Validations.isFieldNotEmpty(base.getText()) || Validations.isFieldNotEmpty(va.getText())) {
                    rowIsNotEmpty = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowIsNotEmpty;
    }

    //List Generation Code for PR,CH and CL- will be called on key release of a textfield- will display the list of values- which will be filtered based on the value entered in the text field.
    public static void displayCellWithListOnkeyRelease(int keyCode, JScrollPane scrollPane, JList list, Vector listValues, JTextField textField) {
        try {
            boolean txtFieldEmpty = true;
            if (textField.getText() != null) {
                if (textField.getText().trim().length() > 0) {
                    txtFieldEmpty = false;
                }
            }//checked whether txt field is empty

            scrollPane.setBounds(textField.getX(), textField.getY() + textField.getHeight(), textField.getWidth() + 30, 120);//display the list with scrollpane , just below the text field
            scrollPane.setVisible(true);

            if (listModel != null) {
                listModel.removeAllElements();
                list.setModel(listModel);
            }//made the list empty before populating
            try {

                if (txtFieldEmpty && listValues != null) { // if txtfield empty   display the list with all values
                    list.setListData(listValues);
                    list.getModel().getSize();
                } else { // else filtering the list based on the value typed in the txt field
                    if (listModel != null && listValues != null) {
                        ListIterator<String> listIter = listValues.listIterator();
                        while (listIter.hasNext()) {
                            String valFromDb = String.valueOf(listIter.next());
                            if (valFromDb != null) {
                                if (valFromDb.toUpperCase().startsWith(textField.getText().toUpperCase())) {
                                    listModel.addElement(valFromDb);
                                } else if (valFromDb.startsWith("+")) { // for list with + sign,  those values should be in filtered list even if the user enters without plus sign also
                                    if (valFromDb.length() > 1) {
                                        if (valFromDb.substring(1).startsWith(textField.getText().toUpperCase())) {
                                            listModel.addElement(valFromDb);
                                        }
                                    }
                                }
                            }
                        }
                        list.setModel(listModel);
                        if (listModel.size() == 1) { //if only one value in the list set it as selected
                            if (listModel.get(0) != null) {
                                if (listModel.get(0).toString().trim().equalsIgnoreCase(textField.getText().trim())) {
                                    list.setSelectedIndex(0);
                                    list.requestFocus();
                                } else if (listModel.get(0).toString().startsWith("+")) {
                                    if (listModel.get(0).toString().length() > 1) {
                                        if (listModel.get(0).toString().substring(1).equalsIgnoreCase(textField.getText().toUpperCase())) {
                                            list.setSelectedIndex(0);
                                            list.requestFocus();
                                        }
                                    }
                                }

                            }
                        }


                        int listHeight = listModel.size() * 20; // setting physical size for the list
                        if (listHeight < 120) {
                            scrollPane.setBounds(textField.getX(), textField.getY() + textField.getHeight(), textField.getWidth() + 30, listHeight);
                        }
                    }
                }
                if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_ENTER)//if down arrow pressed display the  list 
                {
                    if (list != null) {

                        if (list.getModel() != null) {
                            if (list.getModel().getSize() > 0) {
                                list.setSelectedIndex(0);
                                list.requestFocus();
                            } else {
                                textField.requestFocus();
                            }

                        }
                    }

                }


            /*else {
            if (txtFieldEmpty) {
            scrollPane.setVisible(false);
            } else {
            scrollPane.setVisible(true);
            }
            }
             */
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //checks whether value entered in txt 
    public static boolean checkCellValueForValidEntriesInList(Component comp, FocusEvent evt, String listName, JScrollPane scrollPane, JList list, Vector listValues, JTextField textField) {
        boolean entryIsValid = true;
        try {
            if (evt != null) {
                if (!evt.isTemporary()) {
                    boolean isNextFocusedSphList = false;
                    if (list != null && scrollPane != null) {
                        if (evt.getOppositeComponent() != null) {
                            if (evt.getOppositeComponent().equals(list) || evt.getOppositeComponent().equals(scrollPane)) {
                                isNextFocusedSphList = true;
                            }

                        }
                    }
                    if (!isNextFocusedSphList) {
                        scrollPane.setVisible(false);
                        if (textField.getText() != null) {
                            if (textField.getText().length() > 0) {
                                if (listValues == null) {
                                    entryIsValid = false;
                                    textField.setText("");
                                    textField.requestFocus();
                                    JOptionPane.showMessageDialog(comp, "Invalid Entry : No value is maintained in " + listName, "Error", JOptionPane.ERROR_MESSAGE);
                                } else if (listValues.size() <= 0) {

                                    entryIsValid = false;
                                    textField.setText("");
                                    textField.requestFocus();
                                    JOptionPane.showMessageDialog(comp, "Invalid Entry : No value is maintained in  " + listName, "Error", JOptionPane.ERROR_MESSAGE);
                                } else if (!(checkStringValuePresent(listValues, textField.getText(), textField))) {
                                    entryIsValid = false;
                                    textField.setText("");
                                    textField.requestFocus();
                                    JOptionPane.showMessageDialog(comp, "Invalid Entry : Check the List of " + listName, "Error", JOptionPane.ERROR_MESSAGE);
                                }

                            }
                        }
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        return entryIsValid;
    }

    public static boolean checkTransposeGivesCorrectSph(Component comp, String rowName, Vector listValues, JTextField plusSphTextField, JTextField minusSphTextField) {
        boolean entryIsValid = true;
        try {
            if (minusSphTextField.getText() != null) {
                if (minusSphTextField.getText().length() > 0) {
                    if (listValues == null) {
                    } else if (listValues.size() <= 0) {
                    } else if (!(checkValuePresent(listValues, minusSphTextField.getText(), minusSphTextField))) {
                        entryIsValid = false;
                        minusSphTextField.setText("");
                        plusSphTextField.requestFocus();
                        JOptionPane.showMessageDialog(comp, "Invalid Sph-Cyl Combination for " + rowName, "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return entryIsValid;
    }

    public static boolean checkStringCellValueForValidEntriesInList(Component comp, FocusEvent evt, String listName, JScrollPane scrollPane, JList list, Vector listValues, JTextField textField) {
        boolean entryIsValid = true;
        try {

            boolean isNextFocusedSphList = false;
            if (list != null && scrollPane != null) {
                if (evt.getOppositeComponent() != null) {
                    if (evt.getOppositeComponent().equals(list) || evt.getOppositeComponent().equals(scrollPane)) {
                        isNextFocusedSphList = true;
                    }

                }
            }
            if (!isNextFocusedSphList) {
                scrollPane.setVisible(false);
                if (textField.getText() != null) {
                    if (textField.getText().length() > 0) {
                        if (listValues == null) {
                            entryIsValid = false;
                            textField.setText("");
                            textField.requestFocus();
                            JOptionPane.showMessageDialog(comp, "Invalid Entry : No value is maintained in " + listName, "Error", JOptionPane.ERROR_MESSAGE);
                        } else if (listValues.size() <= 0) {

                            entryIsValid = false;
                            textField.setText("");
                            textField.requestFocus();
                            JOptionPane.showMessageDialog(comp, "Invalid Entry : No value is maintained in" + listName, "Error", JOptionPane.ERROR_MESSAGE);
                        } else if (!checkStringValuePresent(listValues, textField.getText(),textField)) {
                            entryIsValid = false;
                            textField.setText("");
                            textField.requestFocus();
                            JOptionPane.showMessageDialog(comp, "Invalid Entry : Check the list of " + listName, "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return entryIsValid;
    }

    public static void setRepeatedCylAxisValues(AWTEvent evt, JTextField copyOnKeyReleasedOfSph, JTextField copyToCyl, JTextField copyToAxis, JTextField interSph, JTextField copyFromCyl1, JTextField copyFromAxis1, JTextField nearSph, JTextField copyFromCyl2, JTextField copyFromAxis2) {
        /**set Repeated Cyl and Axis Values For SphKeyReleased***/
        try {
            if (evt.getSource().equals(copyOnKeyReleasedOfSph)) {
                if (Validations.isFieldNotEmpty(copyOnKeyReleasedOfSph.getText())) {



                    if (copyFromCyl2 != null && copyToCyl != null) {
                        if (Validations.isFieldNotEmpty(copyFromCyl2.getText())) {
                            copyToCyl.setText(copyFromCyl2.getText());
                        }

                    }
                    if (copyFromAxis2 != null && copyToAxis != null) {
                        if (Validations.isFieldNotEmpty(copyFromAxis2.getText())) {
                            copyToAxis.setText(copyFromAxis2.getText());
                        }

                    }
                    if (copyFromCyl1 != null && copyToCyl != null) {
                        if (Validations.isFieldNotEmpty(copyFromCyl1.getText())) {
                            copyToCyl.setText(copyFromCyl1.getText());
                        }

                    }
                    if (copyFromAxis1 != null && copyToAxis != null) {
                        if (Validations.isFieldNotEmpty(copyFromAxis1.getText())) {
                            copyToAxis.setText(copyFromAxis1.getText());
                        }

                    }
                } else {
                //   copyToCyl.setText(null);
                //   copyToAxis.setText(null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean checkValuePresent(Vector listValues, String val, JTextField tf) {
        boolean valuePresent = false;
        try {
            if (listValues != null) {
                double valDouble = Double.parseDouble(val);
                double listVal = 0;
                for (int i = 0; i < listValues.size(); i++) {
                    if (Validations.isFieldNotEmpty(listValues.get(i))) {
                        listVal = Double.parseDouble(String.valueOf(listValues.get(i)));
                        if (valDouble == listVal) {
                            valuePresent = true;
                           // tf.setText(String.valueOf(listValues.get(i)));
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return valuePresent;
    }

    public static boolean checkStringValuePresent(Vector listValues,String val, JTextField tf) {
        boolean valuePresent = false;
        try {
            if (val != null && listValues != null) {
                for (int i = 0; i < listValues.size(); i++) {
                    if (listValues.get(i) != null) {
                        if ((String.valueOf(listValues.get(i))).equalsIgnoreCase(val)) {
                            valuePresent = true;
                            break;
                        }
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return valuePresent;
    }

    public static boolean checkValueIsNumeric(Component comp, JTextField textField) {
        boolean isNumeric = true;
        try {
            if (textField != null) {
                if (Validations.isFieldNotEmpty(textField.getText())) {
                    double val = Double.parseDouble(textField.getText());
                }

            }

        } catch (Exception e) {
            textField.setText("");
            textField.requestFocus();
            JOptionPane.showMessageDialog(comp, "Invalid Data !", "Error", JOptionPane.ERROR_MESSAGE);
            isNumeric =
                    false;
        //   e.printStackTrace();
        }

        return isNumeric;
    }

    public static boolean checkCylFieldHasMinusVal(FocusEvent evt, Component comp, JTextField textField) {
        boolean valid = true;
        try {
            if (evt != null) {
                if (!evt.isTemporary()) {
                    if (textField.getText() != null) {
                        if (textField.getText().length() > 0) {
                            if (!(textField.getText().startsWith("-"))) //right_dist_cyl.setText("-"+right_dist_cyl.getText());
                            {
                                float val = 0;
                                try {
                                    val = Float.parseFloat(textField.getText());
                                } catch (Exception e) {
                                    textField.setText("");
                                    textField.requestFocus();
                                    JOptionPane.showMessageDialog(comp, "Invalid Data !", "Error", JOptionPane.ERROR_MESSAGE);
                                    valid = false;
                                   // e.printStackTrace();
                                }
                                if (val != 0) {
                                    textField.setText("");
                                    textField.requestFocus();
                                    JOptionPane.showMessageDialog(comp, "Invalid 'Cyl': Only Minus Value Is Accepted ", "Error", JOptionPane.ERROR_MESSAGE);
                                    valid = false;
                                }

                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            valid = false;
        }
        return valid;
    }

    public static boolean checkPlusCylHasplusPrefix(Component comp, JTextField textField) {
        boolean valid = true;
        try {
            if (textField.getText() != null) {
                if (textField.getText().length() > 0) {
                    if (!(textField.getText().startsWith("+"))) {
                        float val = 0;
                        try {
                            val = Float.parseFloat(textField.getText());
                        } catch (Exception e) {
                            textField.setText("");
                            textField.requestFocus();
                            JOptionPane.showMessageDialog(comp, "Invalid Data !", "Error", JOptionPane.ERROR_MESSAGE);
                         //   e.printStackTrace();
                            valid = false;
                        }

                        if (val != 0) {
                            textField.setText("");
                            textField.requestFocus();
                            JOptionPane.showMessageDialog(comp, "Invalid 'Plus Cyl' : should be prefixed with '+' ", "Error", JOptionPane.ERROR_MESSAGE);
                            valid = false;
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valid;
    }

    public static boolean checkSphHasPlusOrMinusPrefix(FocusEvent evt, Component comp, JTextField textField, JList list, JScrollPane scrollPane) {
        boolean valid = true;
        try {
            boolean isNextFocusedSphList = false;
            if (list != null && scrollPane != null) {
                if (evt.getOppositeComponent() != null) {
                    if (evt.getOppositeComponent().equals(list) || evt.getOppositeComponent().equals(scrollPane)) {
                        isNextFocusedSphList = true;
                    }

                }
            }
            if (!isNextFocusedSphList) {
                if (textField.getText() != null) {
                    if (textField.getText().length() > 0) {
                        if (!(textField.getText().startsWith("+") || textField.getText().startsWith("-"))) {
                            float val = 0;
                            try {
                                val = Float.parseFloat(textField.getText());
                            } catch (Exception e) {
                                textField.setText("");
                                textField.requestFocus();
                                JOptionPane.showMessageDialog(comp, "Invalid Data !", "Error", JOptionPane.ERROR_MESSAGE);
                            //    e.printStackTrace();
                                valid = false;
                            }

                            if (val != 0) {
                                textField.setText("");
                                textField.requestFocus();
                                JOptionPane.showMessageDialog(comp, "Invalid 'Sph': should be prefixed with '+' or '-'", "Error", JOptionPane.ERROR_MESSAGE);
                                valid = false;
                            }

                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valid;
    }

    public static void validateSph(JTextField sph, JTextField cyl, JTextField axis, JTextField prism, JTextField base, JTextField va, JTextField add) {
        /*to validate Sphfield is empty when other fields in the same row has any value***/
        try {
            double sphVal = 0, cylVal = 0, axisVal = 0, prismVal = 0, addVal = 0;
            if (sph != null) {
                if (Validations.isFieldNotEmpty(sph.getText())) {
                    sphVal = Double.parseDouble(sph.getText());
                }

            }
            if (cyl != null) {
                if (Validations.isFieldNotEmpty(cyl.getText())) {
                    cylVal = Double.parseDouble(cyl.getText());
                }

            }
            if (axis != null) {
                if (Validations.isFieldNotEmpty(axis.getText())) {
                    axisVal = Double.parseDouble(axis.getText());
                }

            }
            if (prism != null) {
                if (Validations.isFieldNotEmpty(prism.getText())) {
                    prismVal = Double.parseDouble(prism.getText());
                }

            }
            if (add != null) {
                if (Validations.isFieldNotEmpty(add.getText())) {
                    addVal = Double.parseDouble(add.getText());
                }

            }
            if (!Validations.isFieldNotEmpty(sph.getText())) //if sph is empty
            {
                if (cyl != null && axis != null && prism != null && base != null && va != null) {
                    if ((Validations.isFieldNotEmpty(cyl.getText()) && cylVal != 0) || (Validations.isFieldNotEmpty(axis.getText()) && axisVal != 0) || (Validations.isFieldNotEmpty(prism.getText()) && prismVal != 0) || Validations.isFieldNotEmpty(base.getText()) || Validations.isFieldNotEmpty(va.getText())) {
                        sph.setText("0.00");
                    }

                } else if (cyl != null && axis != null && add != null) {
                    if ((Validations.isFieldNotEmpty(cyl.getText()) && cylVal != 0) || (Validations.isFieldNotEmpty(axis.getText()) && axisVal != 0) || (Validations.isFieldNotEmpty(add.getText()) && addVal != 0)) {
                        sph.setText("0.00");
                    }

                }
                if (cyl != null && axis != null && va != null) {
                    if ((Validations.isFieldNotEmpty(cyl.getText()) && cylVal != 0) || (Validations.isFieldNotEmpty(axis.getText()) && axisVal != 0) || Validations.isFieldNotEmpty(va.getText())) {
                        sph.setText("0.00");
                    }

                } else if (cyl != null && axis != null) {
                    if ((Validations.isFieldNotEmpty(cyl.getText()) && cylVal != 0) || (Validations.isFieldNotEmpty(axis.getText()) && axisVal != 0)) {
                        sph.setText("0.00");
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void validateCyl(JTextField cyl, JTextField sph, JTextField axis, JTextField base, JTextField prism) {
        /*to validate Sphfield is empty when other fields in the same row has any value***/
        try {
            double sphVal = 0, cylVal = 0, axisVal = 0, prismVal = 0;
            if (sph != null) {
                if (Validations.isFieldNotEmpty(sph.getText())) {
                    sphVal = Double.parseDouble(sph.getText());
                }

            }
            if (cyl != null) {
                if (Validations.isFieldNotEmpty(cyl.getText())) {
                    cylVal = Double.parseDouble(cyl.getText());
                }

            }
            if (axis != null) {
                if (Validations.isFieldNotEmpty(axis.getText())) {
                    axisVal = Double.parseDouble(axis.getText());
                }

            }
//            if (base != null) {
//                if (Validations.isFieldNotEmpty(base.getText())) {
//                    baseVal = Double.parseDouble(base.getText());
//                }
//            }
            if (prism != null) {
                if (Validations.isFieldNotEmpty(prism.getText())) {
                    prismVal = Double.parseDouble(prism.getText());
                }

            }

            if (!Validations.isFieldNotEmpty(cyl.getText())) //if cyl is empty
            {
                if (sph != null) {
                    if ((Validations.isFieldNotEmpty(sph.getText()) && sphVal != 0)) {
                        cyl.setText("0.00");
                        if (!Validations.isFieldNotEmpty(axis.getText())) {//if axis empty
                            axis.setText("" + 0);
                        }

                    }
                }
                if (base != null && prism != null) {
                    if ((Validations.isFieldNotEmpty(prism.getText())) && (Validations.isFieldNotEmpty(base.getText()))) {
                        cyl.setText("0.00");
                        if (!Validations.isFieldNotEmpty(axis.getText())) {//if axis empty
                            axis.setText("" + 0);
                        }

                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean isCylOrAxisSameAcrossDistInterNear_OnSave(Component comp, String cylOrAxis, String desc, JTextField distCylOrAxis, JTextField interCylOrAxis, JTextField nearCylOrAxis) {
        boolean valid = false;
        try {
            if (distCylOrAxis != null && interCylOrAxis != null && nearCylOrAxis != null) {
                if (Validations.isFieldNotEmpty(distCylOrAxis.getText()) && Validations.isFieldNotEmpty(interCylOrAxis.getText()) && Validations.isFieldNotEmpty(nearCylOrAxis.getText())) {
                    if (distCylOrAxis.getText().trim().equalsIgnoreCase(interCylOrAxis.getText().trim()) && interCylOrAxis.getText().trim().equalsIgnoreCase(nearCylOrAxis.getText().trim())) {
                        valid = true;
                    }
                } else if (Validations.isFieldNotEmpty(distCylOrAxis.getText()) && Validations.isFieldNotEmpty(interCylOrAxis.getText())) {
                    if (distCylOrAxis.getText().trim().equalsIgnoreCase(interCylOrAxis.getText().trim())) {
                        valid = true;
                    }
                } else if (Validations.isFieldNotEmpty(distCylOrAxis.getText()) && Validations.isFieldNotEmpty(nearCylOrAxis.getText())) {
                    if (distCylOrAxis.getText().trim().equalsIgnoreCase(nearCylOrAxis.getText().trim())) {
                        valid = true;
                    }
                } else if (Validations.isFieldNotEmpty(interCylOrAxis.getText()) && Validations.isFieldNotEmpty(nearCylOrAxis.getText())) {
                    if (interCylOrAxis.getText().trim().equalsIgnoreCase(nearCylOrAxis.getText().trim())) {
                        valid = true;
                    }
                } else {
                    valid = true;
                }

                if (!valid) {
                    JOptionPane.showMessageDialog(comp, "'" + cylOrAxis + "' ,if exists,should be same across Distance, Inter and Near \nPlz Check " + desc, "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (distCylOrAxis != null && nearCylOrAxis != null) {
                if (Validations.isFieldNotEmpty(distCylOrAxis.getText()) && Validations.isFieldNotEmpty(nearCylOrAxis.getText())) {
                    if (distCylOrAxis.getText().trim().equalsIgnoreCase(nearCylOrAxis.getText().trim())) {
                        valid = true;
                    }
                } else {
                    valid = true;
                }
                if (!valid) {
                    JOptionPane.showMessageDialog(comp, "'" + cylOrAxis + "' ,if exists ,should be same across Distance, and Near \nPlz Check " + desc, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valid;
    }

    public static boolean validateCylAxisOnFocusLost_showMessage(FocusEvent evt, JTextField nextFocusField, JList list1, JList list2, JScrollPane scroll1, JScrollPane scroll2, Component comp, String rowDesc, JTextField sph, JTextField cyl, JTextField axis) {
        /*to validate Sphfield is empty when other fields in the same row has any value***/
        boolean valid = true;
        try {
            boolean nextFocusIsNotNxtFocusField = true;
            if (evt != null) {
                if (!evt.isTemporary()) {
                    if (evt.getOppositeComponent() != null) {
                        if (nextFocusField != null && list1 != null && list2 != null && scroll1 != null && scroll2 != null) {
                            if (evt.getOppositeComponent().equals(nextFocusField) || evt.getOppositeComponent().equals(list1) || evt.getOppositeComponent().equals(list2) || evt.getOppositeComponent().equals(scroll1) || evt.getOppositeComponent().equals(scroll2)) {
                                nextFocusIsNotNxtFocusField = false;
                            }
                        }


                        if (nextFocusIsNotNxtFocusField && valid) {
                            // valid = validateCylAxisOnsave_showMessage(comp, rowDesc, sph, cyl, axis);
                            double cylVal = 0, axisVal = 0;
                            if (Validations.isFieldNotEmpty(cyl.getText())) {
                                try {
                                    cylVal = Double.parseDouble(cyl.getText());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (Validations.isFieldNotEmpty(axis.getText())) //if axis is not empty
                            {
                                try {
                                    axisVal = Double.parseDouble(axis.getText());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (Validations.isFieldNotEmpty(cyl.getText()) || Validations.isFieldNotEmpty(axis.getText())) {

                                if (Validations.isFieldNotEmpty(cyl.getText()) && Validations.isFieldNotEmpty(axis.getText())) {
                                    if (cylVal != 0 && axisVal == 0) {
                                        axis.requestFocus();
                                        JOptionPane.showMessageDialog(comp, "Invalid 'Axis' \n 'Axis' and 'Cyl' both should be either 'Zero' or Non-zero'  ", "Error", JOptionPane.ERROR_MESSAGE);
                                        valid = false;
                                    } else if (cylVal == 0 && axisVal != 0) {
                                        cyl.requestFocus();
                                        JOptionPane.showMessageDialog(comp, "Invalid 'Cyl' \n 'Axis' and 'Cyl' both should be either 'Zero' or Non-zero'  ", "Error", JOptionPane.ERROR_MESSAGE);
                                        valid = false;
                                    }
                                } else if (Validations.isFieldNotEmpty(cyl.getText())) {
                                    axis.requestFocus();
                                    JOptionPane.showMessageDialog(comp, "Invalid 'Axis' \n'Axis' can not be blank, When 'Cyl' has Value", "Error", JOptionPane.ERROR_MESSAGE);
                                    valid = false;
                                } else if (Validations.isFieldNotEmpty(axis.getText())) {
                                    cyl.requestFocus();
                                    JOptionPane.showMessageDialog(comp, "Invalid 'Cyl' \n'Cyl' can not be blank, When 'Axis' has Value", "Error", JOptionPane.ERROR_MESSAGE);
                                    valid = false;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valid;
    }

    public static boolean validateCylAxisOnsave_showMessage(Component comp, String rowDesc, JTextField sph, JTextField cyl, JTextField axis) {
        /*to validate Sphfield is empty when other fields in the same row has any value***/
        boolean valid = true;
        try {
            double cylVal = 0, axisVal = 0;
            if (Validations.isFieldNotEmpty(cyl.getText())) {
                try {
                    cylVal = Double.parseDouble(cyl.getText());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (Validations.isFieldNotEmpty(axis.getText())) //if axis is not empty
            {
                try {
                    axisVal = Double.parseDouble(axis.getText());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (Validations.isFieldNotEmpty(cyl.getText()) || Validations.isFieldNotEmpty(axis.getText())) {

                if (Validations.isFieldNotEmpty(cyl.getText()) && Validations.isFieldNotEmpty(axis.getText())) {
                    if (cylVal != 0 && axisVal == 0) {
                        axis.requestFocus();
                        JOptionPane.showMessageDialog(comp, "Invalid 'Axis : Cyl' for the row " + rowDesc + "\n 'Axis' and 'Cyl' both should be either 'Zero' or Non-zero'  ", "Error", JOptionPane.ERROR_MESSAGE);
                        valid = false;
                    } else if (cylVal == 0 && axisVal != 0) {
                        cyl.requestFocus();
                        JOptionPane.showMessageDialog(comp, "Invalid 'Axis : Cyl' for the row " + rowDesc + "\n 'Axis' and 'Cyl' both should be either 'Zero' or Non-zero'  ", "Error", JOptionPane.ERROR_MESSAGE);
                        valid = false;
                    }
                } else if (Validations.isFieldNotEmpty(cyl.getText())) {
                    axis.requestFocus();
                    JOptionPane.showMessageDialog(comp, "Invalid 'Axis : Cyl' for the row " + rowDesc + "\n'Axis' can not be blank, When 'Cyl' has Value ", "Error", JOptionPane.ERROR_MESSAGE);
                    valid = false;
                } else if (Validations.isFieldNotEmpty(axis.getText())) {
                    cyl.requestFocus();
                    JOptionPane.showMessageDialog(comp, "Invalid 'Axis : Cyl' for the row " + rowDesc + "\n'Cyl' can not be blank, When 'Axis' has Value", "Error", JOptionPane.ERROR_MESSAGE);
                    valid = false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return valid;
    }

    public static boolean validateBasePrismOnSave_showMessage(Component comp, String rowDesc, JTextField base, JTextField prism) {
        /*to validate Sphfield is empty when other fields in the same row has any value***/
        boolean valid = true;
        try {

            if (Validations.isFieldNotEmpty(base.getText()) && (!Validations.isFieldNotEmpty(prism.getText()))) {
                prism.requestFocus();
                JOptionPane.showMessageDialog(comp, "Invalid 'Prism : Base' for the row " + rowDesc + "\n 'Prism' can not be blank ,When 'Base' has Value ", "Error", JOptionPane.ERROR_MESSAGE);
                valid = false;

            } else if ((!Validations.isFieldNotEmpty(base.getText())) && Validations.isFieldNotEmpty(prism.getText())) {
                base.requestFocus();
                JOptionPane.showMessageDialog(comp, "Invalid 'Prism : Base' for the row " + rowDesc + "\n 'Base' can not be blank ,When 'Prism' has Value", "Error", JOptionPane.ERROR_MESSAGE);
                valid = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return valid;
    }

    public static boolean validateBasePrismonFocuslost_showMessage(FocusEvent evt, JTextField nextFocusField, JList list1, JScrollPane scroll1, Component comp, String rowDesc, JTextField base, JTextField prism) {
        /*to validate Sphfield is empty when other fields in the same row has any value***/
        boolean valid = true;
        boolean nextFocusIsNotNxtFocusField = true;
        try {
            if (evt != null) {
                if (!evt.isTemporary()) {
                    if (evt.getOppositeComponent() != null) {
                        if (nextFocusField != null && list1 != null && scroll1 != null) {
                            if (evt.getOppositeComponent().equals(nextFocusField) || evt.getOppositeComponent().equals(list1) || evt.getOppositeComponent().equals(scroll1)) {
                                nextFocusIsNotNxtFocusField = false;
                            }
                        }
                        if (nextFocusIsNotNxtFocusField && valid) {
                            // validateBasePrismOnSave_showMessage(comp, rowDesc, base, prism);
                            if (Validations.isFieldNotEmpty(base.getText()) && (!Validations.isFieldNotEmpty(prism.getText()))) {
                                prism.requestFocus();
                                JOptionPane.showMessageDialog(comp, "Invalid 'Prism '\n 'Prism' can not be blank ,When 'Base' has Value ", "Error", JOptionPane.ERROR_MESSAGE);
                                valid = false;

                            } else if ((!Validations.isFieldNotEmpty(base.getText())) && Validations.isFieldNotEmpty(prism.getText())) {
                                base.requestFocus();
                                JOptionPane.showMessageDialog(comp, "Invalid 'Base'\n 'Base' can not be blank ,When 'Prism' has Value ", "Error", JOptionPane.ERROR_MESSAGE);
                                valid = false;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valid;
    }

    public static boolean validateVA_DistanceNearOnSave_showMessage(Component comp, String rowDesc, JTextField distanceVA, JTextField nearVA, JTextField distSph, JTextField distCyl, JTextField distAxis, JTextField distPrism, JTextField distBase, JTextField nearSph, JTextField nearCyl, JTextField nearAxis, JTextField nearPrism, JTextField nearBase, JComboBox examinedBy, Properties examinedByKeyValues) {
        /*to validate Sphfield is empty when other fields in the same row has any value***/
        boolean valid = true, otherNearFieldsEmpty = false, otherDistFieldsEmpty = false, isExamedByOpto = false;
        try {
            if (examinedBy != null) {
                if (Validations.isFieldNotEmpty(examinedBy.getSelectedItem())) {
                    if (examinedBy.getSelectedItem() != null) {
                        String examinedByDesc = String.valueOf(examinedBy.getSelectedItem());
                        String examinedByKey = Validations.getKeyFromVal_ForCollection(examinedByKeyValues, examinedByDesc);

                        if (Validations.isFieldNotEmpty(examinedByKey)) {
                            if (!(examinedByKey.equalsIgnoreCase("1")) && !(examinedByKey.equalsIgnoreCase("2"))) {
                                isExamedByOpto = true;
                            }
                        }
                    }
                }

                if (isExamedByOpto) {
                    if (Validations.isFieldNotEmpty(distanceVA.getText()) && (!Validations.isFieldNotEmpty(nearVA.getText()))) {
                        if (nearSph != null && nearCyl != null && nearAxis != null && nearPrism != null && nearBase != null) {
                            if (!(Validations.isFieldNotEmpty(nearSph.getText()) || Validations.isFieldNotEmpty(nearCyl.getText()) || Validations.isFieldNotEmpty(nearAxis.getText()) || Validations.isFieldNotEmpty(nearPrism.getText()) || Validations.isFieldNotEmpty(nearBase.getText()))) {
                                otherNearFieldsEmpty = true; //all other fields empty
                            }

                        } else if (nearSph != null && nearCyl != null && nearAxis != null) {
                            if (!(Validations.isFieldNotEmpty(nearSph.getText()) || Validations.isFieldNotEmpty(nearCyl.getText()) || Validations.isFieldNotEmpty(nearAxis.getText()))) {
                                otherNearFieldsEmpty = true; //all other fields empty
                            }

                        }
                        if (!otherNearFieldsEmpty) {
                            nearVA.requestFocus();
                            JOptionPane.showMessageDialog(comp, "Invalid 'VA-Distance:VA-Near' for " + rowDesc + "\n  'VA-Near' can not be blank ,When 'VA-Distance' has Value", "Error", JOptionPane.ERROR_MESSAGE);
                            valid = false;
                        }

                    } /*else if ((!Validations.isFieldNotEmpty(distanceVA.getText())) && Validations.isFieldNotEmpty(nearVA.getText())) {
                if (distSph != null && distCyl != null && distAxis != null && distPrism != null && distBase != null) {
                if (!(Validations.isFieldNotEmpty(distSph.getText()) || Validations.isFieldNotEmpty(distCyl.getText()) || Validations.isFieldNotEmpty(distAxis.getText()) || Validations.isFieldNotEmpty(distPrism.getText()) || Validations.isFieldNotEmpty(distBase.getText()))) {
                otherDistFieldsEmpty = true; //all other fields empty
                }
                } else if (distSph != null && distCyl != null && distAxis != null) {
                if (!(Validations.isFieldNotEmpty(distSph.getText()) || Validations.isFieldNotEmpty(distCyl.getText()) || Validations.isFieldNotEmpty(distAxis.getText()))) {
                otherDistFieldsEmpty = true; //all other fields empty
                }
                }
                if (!otherDistFieldsEmpty) {
                distanceVA.requestFocus();
                JOptionPane.showMessageDialog(comp, " Invalid 'VA-Distance:VA-Near' for " + rowDesc + "\n  'VA-Distance' can not be blank ,When 'VA-Near' has Value ", "Error", JOptionPane.ERROR_MESSAGE);
                valid = false;
                }
                }*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return valid;
    }

    public static boolean validateVA_DistanceNearOnFocuLost_showMessage(FocusEvent evt, JTextField nextFocusField, Component comp, String rowDesc, JTextField distanceVA, JTextField nearVA, JTextField distSph, JTextField distCyl, JTextField distAxis, JTextField distPrism, JTextField distBase, JTextField nearSph, JTextField nearCyl, JTextField nearAxis, JTextField nearPrism, JTextField nearBase, JComboBox examinedBy, Properties examinedByKeyValues) {
        /*to validate Sphfield is empty when other fields in the same row has any value***/
        boolean valid = true, otherNearFieldsEmpty = false, otherDistFieldsEmpty = false, isExamedByOpto = false;
        try {
            boolean nextFocusIsNotNxtFocusField = true;
            if (evt != null) {
                if (!evt.isTemporary()) {
                    if (evt.getOppositeComponent() != null) {
                        if (nextFocusField != null) {
                            if (evt.getOppositeComponent().equals(nextFocusField)) {
                                nextFocusIsNotNxtFocusField = false;
                            }
                        }
                        if (nextFocusIsNotNxtFocusField && valid) {
                            if (examinedBy != null) {
                                if (Validations.isFieldNotEmpty(examinedBy.getSelectedItem())) {
                                    if (examinedBy.getSelectedItem() != null) {
                                        String examinedByDesc = String.valueOf(examinedBy.getSelectedItem());
                                        String examinedByKey = Validations.getKeyFromVal_ForCollection(examinedByKeyValues, examinedByDesc);

                                        if (Validations.isFieldNotEmpty(examinedByKey)) {
                                            if (!(examinedByKey.equalsIgnoreCase("1")) && !(examinedByKey.equalsIgnoreCase("2"))) {
                                                isExamedByOpto = true;
                                            }
                                        }
                                    }
                                }
                            }
                            if (examinedBy == null || isExamedByOpto) {
                                // valid = validateVA_DistanceNearOnSave_showMessage(comp, rowDesc, distanceVA, nearVA, distSph, distCyl, distAxis, distPrism, distBase, nearSph, nearCyl, nearAxis, nearPrism, nearBase);
                                if (Validations.isFieldNotEmpty(distanceVA.getText()) && (!Validations.isFieldNotEmpty(nearVA.getText()))) {
                                    if (nearSph != null && nearCyl != null && nearAxis != null && nearPrism != null && nearBase != null) {
                                        if (!(Validations.isFieldNotEmpty(nearSph.getText()) || Validations.isFieldNotEmpty(nearCyl.getText()) || Validations.isFieldNotEmpty(nearAxis.getText()) || Validations.isFieldNotEmpty(nearPrism.getText()) || Validations.isFieldNotEmpty(nearBase.getText()))) {
                                            otherNearFieldsEmpty = true; //all other fields empty
                                        }

                                    } else if (nearSph != null && nearCyl != null && nearAxis != null) {
                                        if (!(Validations.isFieldNotEmpty(nearSph.getText()) || Validations.isFieldNotEmpty(nearCyl.getText()) || Validations.isFieldNotEmpty(nearAxis.getText()))) {
                                            otherNearFieldsEmpty = true; //all other fields empty
                                        }

                                    }
                                    if (!otherNearFieldsEmpty) {
                                        nearVA.requestFocus();
                                        JOptionPane.showMessageDialog(comp, "Invalid 'VA-Near' \n  'VA-Near' can not be blank ,When 'VA-Distance' has Value.", "Error", JOptionPane.ERROR_MESSAGE);
                                        valid = false;
                                    }

                                } /*else if ((!Validations.isFieldNotEmpty(distanceVA.getText())) && Validations.isFieldNotEmpty(nearVA.getText())) {
                            if (distSph != null && distCyl != null && distAxis != null && distPrism != null && distBase != null) {
                            if (!(Validations.isFieldNotEmpty(distSph.getText()) || Validations.isFieldNotEmpty(distCyl.getText()) || Validations.isFieldNotEmpty(distAxis.getText()) || Validations.isFieldNotEmpty(distPrism.getText()) || Validations.isFieldNotEmpty(distBase.getText()))) {
                            otherDistFieldsEmpty = true; //all other fields empty
                            }
                            } else if (distSph != null && distCyl != null && distAxis != null) {
                            if (!(Validations.isFieldNotEmpty(distSph.getText()) || Validations.isFieldNotEmpty(distCyl.getText()) || Validations.isFieldNotEmpty(distAxis.getText()))) {
                            otherDistFieldsEmpty = true; //all other fields empty
                            }
                            }
                            if (!otherDistFieldsEmpty) {
                            distanceVA.requestFocus();
                            JOptionPane.showMessageDialog(comp, " Invalid 'VA-Distance'\n  'VA-Distance' can not be blank ,When 'VA-Near' has Value.", "Error", JOptionPane.ERROR_MESSAGE);
                            valid = false;
                            }
                            }*/
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return valid;
    }

    public static boolean validate_BC_showMessage(Component comp, String fieldDesc, JTextField bc) {
        /*to validate Sphfield is empty when other fields in the same row has any value***/
        boolean valid = true;
        try {
            double bcVal = 0;
            if (Validations.isFieldNotEmpty(bc.getText())) {
                bcVal = Double.parseDouble(bc.getText());
                double res = (bcVal * 5) % (0.05 * 5);

                if ((res > 0) || (bcVal < 6 || bcVal > 9.90)) {
                    bc.setText("");
                    bc.requestFocus();
                    //JOptionPane.showMessageDialog(comp, "Invalid BC Value :" + fieldDesc + "\nBC Value Range is 6.00 - 9.90 and should be a multiple of 0.05 ", "Error", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(comp, "Invalid BC  \nBC Value Range is 6.00 - 9.90 and should be a multiple of 0.05 ", "Error", JOptionPane.ERROR_MESSAGE);
                    valid = false;
                }

            }

        } catch (Exception e) {
            bc.setText("");
            bc.requestFocus();
            JOptionPane.showMessageDialog(comp, "Invalid BC  \nBC Value Range is 6.00 - 9.90 and should be a multiple of 0.05 ", "Error", JOptionPane.ERROR_MESSAGE);
            valid =
                    false;
        //   e.printStackTrace();
        }

        return valid;
    }

    public static boolean validate_Dia_showMessage(Component comp, String fieldDesc, JTextField dia) {
        /*to validate Sphfield is empty when other fields in the same row has any value***/
        boolean valid = true;
        try {
            double diaVal = 0;
            if (Validations.isFieldNotEmpty(dia.getText())) {
                diaVal = Double.parseDouble(dia.getText());

                double res = (diaVal * 10) % (0.10 * 10);
                if ((res > 0) || (diaVal < 6) || (diaVal > 16)) {
                    dia.setText("");
                    dia.requestFocus();
                    //   JOptionPane.showMessageDialog(comp, "Invalid Dia Value  : " + fieldDesc + "\nDia Value range is 6 to 16.0 and should be a multiple of 0.10  ", "Error", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(comp, "Invalid Dia   : \nDia Value range is 6 to 16.0 and should be a multiple of 0.10  ", "Error", JOptionPane.ERROR_MESSAGE);
                    valid =
                            false;
                }

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(comp, "Invalid Dia  :\nDia Value range is 6 to 16.0 and should be a multiple of 0.10  ", "Error", JOptionPane.ERROR_MESSAGE);
            dia.setText("");
            dia.requestFocus();
            valid =
                    false;
        //   e.printStackTrace();
        }

        return valid;
    }

    public static boolean validate_Add_showMessage(Component comp, String fieldDesc, JTextField add) {
        /*to validate Sphfield is empty when other fields in the same row has any value***/
        boolean valid = true;
        try {
            double addVal = 0;
            if (Validations.isFieldNotEmpty(add.getText())) {
                addVal = Double.parseDouble(add.getText());
                double res = (addVal % 0.25);

                if ((res > 0) || (addVal < 0.75 || addVal > 6)) {
                    add.setText("");
                    add.requestFocus();
                    //JOptionPane.showMessageDialog(comp, "Invalid Add  : " + fieldDesc + "\nAdd Value Range should be 0.75 to 6.0 and should be a multiple of 0.25  ", "Error", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(comp, "Invalid Add  : \nAdd Value Range should be 0.75 to 6.0 and should be a multiple of 0.25  ", "Error", JOptionPane.ERROR_MESSAGE);
                    valid =
                            false;
                }

            }

        } catch (Exception e) {
            add.setText("");
            add.requestFocus();

            JOptionPane.showMessageDialog(comp, "Invalid Add  : \nAdd Value Range should be 0.75 to 6.0  and should be a multiple of 0.25  ", "Error", JOptionPane.ERROR_MESSAGE);
            valid =
                    false;
        //  e.printStackTrace();
        }

        return valid;
    }

    public static boolean validate_OZ_showMessage(Component comp, String fieldDesc, JTextField oz) {
        /*to validate Sphfield is empty when other fields in the same row has any value***/
        boolean valid = true;
        try {
            double ozVal = 0;
            if (Validations.isFieldNotEmpty(oz.getText())) {
                ozVal = Double.parseDouble(oz.getText());
                double res = (ozVal * 10) % (0.10 * 10);
                if ((res > 0) || (ozVal < 4.50 || ozVal > 9.99)) {
                    oz.setText("");
                    oz.requestFocus();
                    // JOptionPane.showMessageDialog(comp, "Invalid OZ  " + fieldDesc + "\nOZ Value Range should be 4.50 to 9.99 and should be a multiple of 0.10  ", "Error", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(comp, "Invalid OZ : \nOZ Value Range should be 4.50 to 9.99 and should be a multiple of 0.10  ", "Error", JOptionPane.ERROR_MESSAGE);
                    valid =
                            false;
                }

            }

        } catch (Exception e) {
            oz.setText("");
            oz.requestFocus();
            JOptionPane.showMessageDialog(comp, "Invalid OZ : \nOZ Value Range should be 4.50 to 9.99 and should be a multiple of 0.10   ", "Error", JOptionPane.ERROR_MESSAGE);
            valid =
                    false;
        //   e.printStackTrace();
        }

        return valid;
    }

    public static boolean validate_IOP_showMessage(Component comp, String fieldDesc, JTextField iop) {
        /*to validate Sphfield is empty when other fields in the same row has any value***/
        boolean valid = true;
        try {
            double iopVal = 0;
            if (Validations.isFieldNotEmpty(iop.getText())) {
                iopVal = Double.parseDouble(iop.getText());
                if (iopVal < 0 || iopVal > 90.0) {
                    iop.setText("");
                    iop.requestFocus();
                    //JOptionPane.showMessageDialog(comp, "Invalid Value " + fieldDesc + "\nIOP in mmHg (NCT): value Range should be 0 to 90.0  ", "Error", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(comp, "Invalid IOP :\nIOP in mmHg (NCT): value Range should be 0 to 90.0  ", "Error", JOptionPane.ERROR_MESSAGE);
                    valid =
                            false;
                }

            }

        } catch (Exception e) {
            iop.setText("");
            iop.requestFocus();
            JOptionPane.showMessageDialog(comp, "Invalid IOP  :\nIOP in mmHg (NCT): value Range should be 0 to 90.0  ", "Error", JOptionPane.ERROR_MESSAGE);
            valid =
                    false;
        //    e.printStackTrace();
        }

        return valid;
    }

    public static boolean validate_AR_IPD_showMessage(Component comp, JTextField arIpd) {
        /*to validate Sphfield is empty when other fields in the same row has any value***/
        boolean valid = true;
        try {
            double arIpdVal = 0;
            if (Validations.isFieldNotEmpty(arIpd.getText())) {
                arIpdVal = Double.parseDouble(arIpd.getText());
                double res = (arIpdVal % 0.5);

                if ((res > 0) || (arIpdVal < 30) || (arIpdVal > 90)) {
                    arIpd.setText("");
                    arIpd.requestFocus();
                    JOptionPane.showMessageDialog(comp, "Invalid entry : AR IPD  ! \n The Value range is  30mm - 90mm and should be a multiple of 0.5  ", "Error", JOptionPane.ERROR_MESSAGE);
                    valid =
                            false;
                }

            }

        } catch (Exception e) {
            arIpd.setText("");
            arIpd.requestFocus();
            JOptionPane.showMessageDialog(comp, "Invalid entry : AR IPD  !\n The Value range is  30mm - 90mm and should be a multiple of 0.5   ", "Error", JOptionPane.ERROR_MESSAGE);
            valid =
                    false;
        //  e.printStackTrace();
        }

        return valid;
    }

    public static boolean validate_SpectacleAddition(Component comp, FocusEvent evt, JTextField oppComp1, JTextField oppComp2, JTextField oppComp3, JTextField oppComp4, JTextField oppComp5, JTextField sphRightAdd, JTextField sphLeftAdd, String sideIndicator) {
        /*to validate Sphfield is empty when other fields in the same row has any value***/
        boolean valid = true;
        try {
            boolean nextFocusIsNotNxtFocusField = true;
            if (evt != null) {
                if (evt.getOppositeComponent() != null && oppComp1 != null && oppComp2 != null && oppComp3 != null && oppComp4 != null && oppComp5 != null) {
                    if (evt.getOppositeComponent().equals(oppComp1) || evt.getOppositeComponent().equals(oppComp2) || evt.getOppositeComponent().equals(oppComp3) || evt.getOppositeComponent().equals(oppComp4) || evt.getOppositeComponent().equals(oppComp5)) {
                        nextFocusIsNotNxtFocusField = false;
                    }
                    try {
                        JList list1 = (JList) evt.getOppositeComponent();
                        nextFocusIsNotNxtFocusField = false;
                    } catch (Exception e) {
                    }
                    try {
                        JScrollPane pane1 = (JScrollPane) evt.getOppositeComponent();
                        nextFocusIsNotNxtFocusField = false;
                    } catch (Exception e) {
                    }
                }
            }
            if (nextFocusIsNotNxtFocusField) {
                double rightAddVal = 0, leftAddVal = 0;
                if (Validations.isFieldNotEmpty(sphRightAdd.getText())) {
                    rightAddVal = Double.parseDouble(sphRightAdd.getText());
                }
                if (Validations.isFieldNotEmpty(sphLeftAdd.getText())) {
                    leftAddVal = Double.parseDouble(sphLeftAdd.getText());
                }
                if (sideIndicator != null) {
                    if (sideIndicator.trim().equalsIgnoreCase("B")) {
                        if (Validations.isFieldNotEmpty(sphRightAdd.getText()) && Validations.isFieldNotEmpty(sphLeftAdd.getText())) {
                            if ((rightAddVal < 0.75 || rightAddVal > 6) && (leftAddVal < 0.75 || leftAddVal > 6)) {
                                //   JOptionPane.showMessageDialog(comp, "Enter Sph values correctly for " + fieldDesc + "\nIt should be ('Near Sph' > 'Inter Sph' > 'Dist Sph') ,Add Value Range should be 0.75 to 6.0 !!", "Error", JOptionPane.ERROR_MESSAGE);
                                JOptionPane.showMessageDialog(comp, "Invalid Addition Values : Both Right and Left Eye ! \n Addition Value Range is 0.75 to 6.0 !!", "Error", JOptionPane.ERROR_MESSAGE);
                                valid = false;
                            } else if (rightAddVal < 0.75 || rightAddVal > 6) {
                                JOptionPane.showMessageDialog(comp, " Invalid Addition Values: Right Eye  ! \n Addition Value Range is 0.75 to 6.0 !!", "Error", JOptionPane.ERROR_MESSAGE);
                                valid = false;
                            } else if (leftAddVal < 0.75 || leftAddVal > 6) {
                                JOptionPane.showMessageDialog(comp, "Invalid Addition Value : Left Eye   ! \n Addition Value Range is 0.75 to 6.0 !!", "Error", JOptionPane.ERROR_MESSAGE);
                                valid = false;
                            }

                        } else if (Validations.isFieldNotEmpty(sphRightAdd.getText())) {
                            if (rightAddVal < 0.75 || rightAddVal > 6) {
                                JOptionPane.showMessageDialog(comp, "Invalid Addition Value : Right Eye   ! \n Addition Value Range is 0.75 to 6.0 !!", "Error", JOptionPane.ERROR_MESSAGE);
                                valid = false;
                            }

                        } else if (Validations.isFieldNotEmpty(sphLeftAdd.getText())) {
                            if (leftAddVal < 0.75 || leftAddVal > 6) {
                                JOptionPane.showMessageDialog(comp, "Invalid Addition Value: Left Eye:  ! \n Addition Value Range is 0.75 to 6.0 !!", "Error", JOptionPane.ERROR_MESSAGE);
                                valid = false;
                            }

                        }
                    } else if (sideIndicator.trim().equalsIgnoreCase("R")) {
                        if (Validations.isFieldNotEmpty(sphRightAdd.getText())) {
                            if (rightAddVal < 0.75 || rightAddVal > 6) {
                                JOptionPane.showMessageDialog(comp, "Invalid Addition Value : Right Eye  ! \n Addition Value Range is 0.75 to 6.0 !!", "Error", JOptionPane.ERROR_MESSAGE);
                                valid = false;
                            }
                        }
                    } else if (sideIndicator.trim().equalsIgnoreCase("L")) {
                        if (Validations.isFieldNotEmpty(sphLeftAdd.getText())) {
                            if (leftAddVal < 0.75 || leftAddVal > 6) {
                                JOptionPane.showMessageDialog(comp, "Invalid Addition Value : Left Eye  ! \n Addition Value Range is 0.75 to 6.0 !!", "Error", JOptionPane.ERROR_MESSAGE);
                                valid = false;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(comp, "Invalid Addition Values  ! \n Addition Value Range is 0.75 to 6.0 !!", "Error", JOptionPane.ERROR_MESSAGE);
            valid = false;
        }

        return valid;
    }

    //va distance or near
    public static boolean validate_VA_ExaminedBy_onSave(Component comp, String fieldDesc, JComboBox examinedBy, Properties examinedByKeyValues, JTextField va, JTextField sph, JTextField cyl, JTextField axis, JTextField prism, JTextField base, boolean checkOtherFieldsAlso) {
        /*to validate Sphfield is empty when other fields in the same row has any value***/
        boolean valid = true;
        try {
            if (examinedBy.getSelectedItem() != null) {
                String examinedByDesc = String.valueOf(examinedBy.getSelectedItem());
                String examinedByKey = Validations.getKeyFromVal_ForCollection(examinedByKeyValues, examinedByDesc);
                if (Validations.isFieldNotEmpty(examinedByKey)) {
                    if (!(examinedByKey.equalsIgnoreCase("1")) && !(examinedByKey.equalsIgnoreCase("2"))) {
                        if (checkOtherFieldsAlso) {
                            if (sph != null && cyl != null && axis != null && prism != null && base != null && va != null) {
                                if (!Validations.isFieldNotEmpty(va.getText()) && (Validations.isFieldNotEmpty(sph.getText()) || Validations.isFieldNotEmpty(cyl.getText()) || Validations.isFieldNotEmpty(axis.getText()) || Validations.isFieldNotEmpty(prism.getText()) || Validations.isFieldNotEmpty(base.getText()))) {
                                    va.requestFocus();
                                    JOptionPane.showMessageDialog(comp, "The Field " + fieldDesc + " cannot be balnk when Examined By a Titan Optrometrist ", "Error", JOptionPane.ERROR_MESSAGE);
                                    valid = false;
                                }
                            } else if (sph != null && cyl != null && axis != null && va != null) {
                                if (!Validations.isFieldNotEmpty(va.getText()) && (Validations.isFieldNotEmpty(sph.getText()) || Validations.isFieldNotEmpty(cyl.getText()) || Validations.isFieldNotEmpty(axis.getText()))) {
                                    va.requestFocus();
                                    JOptionPane.showMessageDialog(comp, "The Field " + fieldDesc + " cannot be blank when Examined By a Titan Optrometrist ", "Error", JOptionPane.ERROR_MESSAGE);
                                    valid = false;
                                }
                            }
                        } else {
                            if (!Validations.isFieldNotEmpty(va.getText())) {
                                va.requestFocus();
                                JOptionPane.showMessageDialog(comp, "The Field " + fieldDesc + " cannot be balnk when Examined By a Titan Optrometrist ", "Error", JOptionPane.ERROR_MESSAGE);
                                valid = false;
                            }
                        }
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return valid;
    }
//va distance or near
    public static boolean validate_VA_ExaminedBy_onFocusLost(FocusEvent evt, Component comp, String fieldDesc, JComboBox examinedBy, Properties examinedByKeyValues, JTextField va, JTextField sph, JTextField cyl, JTextField axis, JTextField prism, JTextField base, boolean checkOtherFieldsAlso) {
        /*to validate Sphfield is empty when other fields in the same row has any value***/
        boolean valid = true;
        try {
            boolean nextFocusIsNotNxtFocusField = true;
            if (evt != null) {
                if (!evt.isTemporary()) {
                    if (evt.getOppositeComponent() != null) {
                        if (examinedBy != null) {
                            if (evt.getOppositeComponent().equals(examinedBy)) {
                                nextFocusIsNotNxtFocusField = false;
                            }
                        }
                        if (nextFocusIsNotNxtFocusField && valid) {
                            if (examinedBy.getSelectedItem() != null) {
                                String examinedByDesc = String.valueOf(examinedBy.getSelectedItem());
                                String examinedByKey = Validations.getKeyFromVal_ForCollection(examinedByKeyValues, examinedByDesc);

                                if (Validations.isFieldNotEmpty(examinedByKey)) {
                                    if (!(examinedByKey.equalsIgnoreCase("1")) && !(examinedByKey.equalsIgnoreCase("2"))) {

                                        if (checkOtherFieldsAlso) {
                                            if (sph != null && cyl != null && axis != null && prism != null && base != null && va != null) {
                                                if (!Validations.isFieldNotEmpty(va.getText()) && (Validations.isFieldNotEmpty(sph.getText()) || Validations.isFieldNotEmpty(cyl.getText()) || Validations.isFieldNotEmpty(axis.getText()) || Validations.isFieldNotEmpty(prism.getText()) || Validations.isFieldNotEmpty(base.getText()))) {
                                                    va.requestFocus();
                                                    JOptionPane.showMessageDialog(comp, "The Field " + fieldDesc + " cannot be balnk when Examined By a Titan Optrometrist ", "Error", JOptionPane.ERROR_MESSAGE);
                                                    valid = false;
                                                }
                                            } else if (sph != null && cyl != null && axis != null && va != null) {
                                                if (!Validations.isFieldNotEmpty(va.getText()) && (Validations.isFieldNotEmpty(sph.getText()) || Validations.isFieldNotEmpty(cyl.getText()) || Validations.isFieldNotEmpty(axis.getText()))) {
                                                    va.requestFocus();
                                                    JOptionPane.showMessageDialog(comp, "The Field " + fieldDesc + " cannot be blank when Examined By a Titan Optrometrist ", "Error", JOptionPane.ERROR_MESSAGE);
                                                    valid = false;
                                                }
                                            }
                                        } else {
                                            if (!Validations.isFieldNotEmpty(va.getText())) {
                                                va.requestFocus();
                                                JOptionPane.showMessageDialog(comp, "The Field " + fieldDesc + " cannot be balnk when Examined By a Titan Optrometrist ", "Error", JOptionPane.ERROR_MESSAGE);
                                                valid = false;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return valid;
    }

    //Right eye va-(ch and Cl)
    public static boolean validate_VA_CH_CL_ExaminedBy_onSave(Component comp, String fieldDesc, JComboBox examinedBy, Properties examinedByKeyValues, JTextField va, JTextField sph, JTextField cyl, JTextField axis, JTextField add, JTextField brand, JTextField bc, JTextField dia, JTextField tint, JTextField oz, boolean checkOtherFieldsAlso) {
        /*to validate Sphfield is empty when other fields in the same row has any value***/
        boolean valid = true;
        try {
            if (examinedBy.getSelectedItem() != null) {
                String examinedByDesc = String.valueOf(examinedBy.getSelectedItem());
                String examinedByKey = Validations.getKeyFromVal_ForCollection(examinedByKeyValues, examinedByDesc);
                if (Validations.isFieldNotEmpty(examinedByKey)) {
                    if (!(examinedByKey.equalsIgnoreCase("1")) && !(examinedByKey.equalsIgnoreCase("2"))) {
                        if (checkOtherFieldsAlso) {
                            if (sph != null && cyl != null && axis != null && add != null && brand != null && bc != null && dia != null && tint != null && oz != null && va != null) {
                                if (!Validations.isFieldNotEmpty(va.getText()) && (Validations.isFieldNotEmpty(sph.getText()) || Validations.isFieldNotEmpty(cyl.getText()) || Validations.isFieldNotEmpty(axis.getText()) || Validations.isFieldNotEmpty(add.getText()) || Validations.isFieldNotEmpty(brand.getText()) || Validations.isFieldNotEmpty(bc.getText()) || Validations.isFieldNotEmpty(dia.getText()) || Validations.isFieldNotEmpty(tint.getText()) || Validations.isFieldNotEmpty(oz.getText()))) {
                                    va.requestFocus();
                                    JOptionPane.showMessageDialog(comp, "The Field " + fieldDesc + " cannot be balnk when Examined By a Titan Optrometrist ", "Error", JOptionPane.ERROR_MESSAGE);
                                    valid = false;
                                }
                            }
                        } else {
                            if (!Validations.isFieldNotEmpty(va.getText())) {
                                va.requestFocus();
                                JOptionPane.showMessageDialog(comp, "The Field " + fieldDesc + " cannot be balnk when Examined By a Titan Optrometrist ", "Error", JOptionPane.ERROR_MESSAGE);
                                valid = false;
                            }
                        }
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return valid;
    }
    //Right eye va-(ch and Cl)
    public static boolean validate_VA_CH_CL_ExaminedBy_onFocusLost(FocusEvent evt, Component comp, String fieldDesc, JComboBox examinedBy, Properties examinedByKeyValues, JTextField va, JTextField sph, JTextField cyl, JTextField axis, JTextField add, JTextField brand, JTextField bc, JTextField dia, JTextField tint, JTextField oz, boolean checkOtherFieldsAlso) {
        /*to validate Sphfield is empty when other fields in the same row has any value***/
        boolean valid = true;
        try {
            boolean nextFocusIsNotNxtFocusField = true;
            if (evt != null) {
                if (!evt.isTemporary()) {
                    if (evt.getOppositeComponent() != null) {
                        if (examinedBy != null) {
                            if (evt.getOppositeComponent().equals(examinedBy)) {
                                nextFocusIsNotNxtFocusField = false;
                            }
                        }
                        if (nextFocusIsNotNxtFocusField && valid) {
                            if (examinedBy.getSelectedItem() != null) {
                                String examinedByDesc = String.valueOf(examinedBy.getSelectedItem());
                                String examinedByKey = Validations.getKeyFromVal_ForCollection(examinedByKeyValues, examinedByDesc);

                                if (Validations.isFieldNotEmpty(examinedByKey)) {
                                    if (!(examinedByKey.equalsIgnoreCase("1")) && !(examinedByKey.equalsIgnoreCase("2"))) {

                                        if (checkOtherFieldsAlso) {
                                            if (sph != null && cyl != null && axis != null && add != null && brand != null && bc != null && dia != null && tint != null && oz != null && va != null) {
                                                if (!Validations.isFieldNotEmpty(va.getText()) && (Validations.isFieldNotEmpty(sph.getText()) || Validations.isFieldNotEmpty(cyl.getText()) || Validations.isFieldNotEmpty(axis.getText()) || Validations.isFieldNotEmpty(add.getText()) || Validations.isFieldNotEmpty(brand.getText()) || Validations.isFieldNotEmpty(bc.getText()) || Validations.isFieldNotEmpty(dia.getText()) || Validations.isFieldNotEmpty(tint.getText()) || Validations.isFieldNotEmpty(oz.getText()))) {
                                                    va.requestFocus();
                                                    JOptionPane.showMessageDialog(comp, "The Field " + fieldDesc + " cannot be balnk when Examined By a Titan Optrometrist ", "Error", JOptionPane.ERROR_MESSAGE);
                                                    valid = false;
                                                }
                                            }
                                        } else {
                                            if (!Validations.isFieldNotEmpty(va.getText())) {
                                                va.requestFocus();
                                                JOptionPane.showMessageDialog(comp, "The Field " + fieldDesc + " cannot be balnk when Examined By a Titan Optrometrist ", "Error", JOptionPane.ERROR_MESSAGE);
                                                valid = false;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return valid;
    }

    public static boolean validate_hvid_showMessage(Component comp, String fieldDesc, JTextField hvid) {
        /*to validate Sphfield is empty when other fields in the same row has any value***/
        boolean valid = true;
        try {
            double hvidVal = 0;
            if (Validations.isFieldNotEmpty(hvid.getText())) {
                hvidVal = Double.parseDouble(hvid.getText());
                double res = (hvidVal * 10) % (0.10 * 10);
                if ((res > 0) || (hvidVal < 6) || (hvidVal > 15)) {
                    hvid.setText("");
                    hvid.requestFocus();
                    JOptionPane.showMessageDialog(comp, "Invalid entry : \nHVID Value range is 6 to 15  and should be a multiple of 0.10  ", "Error", JOptionPane.ERROR_MESSAGE);
                    valid =
                            false;
                }

            }

        } catch (Exception e) {
            hvid.setText("");
            hvid.requestFocus();
            JOptionPane.showMessageDialog(comp, "Invalid entry : \nHVID Value range is 6 to 15  and should be a multiple of 0.10  ", "Error", JOptionPane.ERROR_MESSAGE);
            valid =
                    false;
        //  e.printStackTrace();
        }

        return valid;
    }

    public static void setFolloupPeriodFromdate(JComboBox followUpPeriod, JFormattedTextField followUpDate) {
        dateformat.setLenient(false);
        try {
            if (Validations.isFieldNotEmpty(followUpDate.getText())) {
                java.util.Date fDate = dateformat.parse(followUpDate.getText());

                Calendar cal_fDate = Calendar.getInstance();
                cal_fDate.setTime(fDate);

                Calendar today = Calendar.getInstance();


                int diffMonths = Validations.getDiffInMonths(today, cal_fDate);
                boolean periodSet = false;
                for (int i = 0; i <
                        followUpPeriod.getItemCount(); i++) {
                    if (followUpPeriod.getItemAt(i) != null) {
                        if (String.valueOf(followUpPeriod.getItemAt(i)).startsWith("" + diffMonths)) {
                            followUpPeriod.setSelectedIndex(i);
                            periodSet =
                                    true;
                            break;
                        }

                    }
                }
                if (!periodSet) {
                    followUpPeriod.setSelectedItem(null);
                }

            } else {
                followUpPeriod.setSelectedItem(null);
            }
        } catch (Exception e) {
        // e.printStackTrace();
        }
    }

    public static Date setFollowupDate(
            JTextField followupdate_val, int monthsToAdd) {
        Date resultedDate = null;
        try {

            Calendar followdate = Calendar.getInstance();
            followdate.add(Calendar.MONTH, monthsToAdd);
            resultedDate = new Date(followdate.getTimeInMillis());
            followupdate_val.setText(dateformat.format(resultedDate));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultedDate;
    }

    public static boolean validateExaminedBy_showMsg(Component comp, JComboBox examinedBy) {
        boolean isPresent = false;
        try {
            if (Validations.isFieldNotEmpty(examinedBy.getSelectedItem())) {
                isPresent = true;
            } else {
                examinedBy.requestFocus();
                JOptionPane.showMessageDialog(comp, "Select 'Examined By' field.", "Error", JOptionPane.ERROR_MESSAGE);
                isPresent = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return isPresent;

    }

    public static Vector setKeysToVectorFrmProperties(Properties prop) {
        Vector values = null;
        try {
            Enumeration enumVals = prop.keys();
            if (enumVals != null) {
                values = new Vector();
                while (enumVals.hasMoreElements()) {
                    values.add(enumVals.nextElement());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

    public static Vector setValuesToVectorFrmProperties(Properties prop) {
        Vector values = null;
        try {
            if (prop != null) {
                if (prop.values() != null) {
                    Iterator enumVals = prop.values().iterator();
                    if (enumVals != null) {
                        values = new Vector();
                        while (enumVals.hasNext()) {
                            values.add(enumVals.next());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }
    /***Prescription Specific END****************/
    private static DefaultListModel listModel = new DefaultListModel();
    private static DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
}
