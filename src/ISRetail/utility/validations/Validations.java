/*
 * Validations.java
 *
 * Created on April 12, 2008, 9:33 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package ISRetail.utility.validations;

import java.awt.Component;
import java.util.*;
import java.text.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class Validations {

    /** Creates a new instance of Validations */
    public Validations() {
    }

    public static boolean validateName(String name) {
        int length = name.length();


        for (int i = 0; i < length; i++) {
            int x = name.charAt(i);

            if (!((x > 64 && x < 92) || (x > 96 && x < 123) || x == 46 || x == 32)) {


                return true;
            }
        }
        return false;
    }

    public static boolean validateEmail(String email) {
        if (email.contains(" ")) {
            return false;
        }
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        //    Pattern p = Pattern.compile("/^[\\w\\-\\.\\+]+\\@[a-zA-Z0-9\\.\\-]+\\.[a-zA-z0-9]{2,4}$/");
        //   Pattern p = Pattern.compile("[A-Z]\\[0-9]\\.\\_]+@\\[A-Z]\\[0-9].+\\.[A-Z]{2,4}]");
        //  Pattern p = Pattern.compile("/^(\".*\"|[A-Za-z]\w*)@(\[\d{1,3}(\.\d{1,3}){3}]|[A-Za-z]\w*(\.[A-Za-z]\w*)+)$/");

        //Match the given string with the pattern
        Matcher m = p.matcher(email);

        //check whether match is found 
        boolean matchFound = m.matches();

        if (matchFound) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean noofcharactersinfield(String fieldval, int maxlen) {
        if (fieldval.length() <= maxlen) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isFieldNotEmpty(Object val) {
        boolean fieldIsNotEmpty = false;//ie.fieldISEmpty=true; setting  field is empty
        try {
            if (val != null) {
                if (String.valueOf(val).trim().length() > 0) {
                    if (!(String.valueOf(val).trim().equalsIgnoreCase("null"))) {
                        fieldIsNotEmpty = true;
                    }
                }
            }//field has some value -field is not empty
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fieldIsNotEmpty;
    }

    public static String convertToTitleCase(String val) {
        String resVal = "";
        try {
            if (val != null) {
                StringTokenizer st = new StringTokenizer(val);
                String word = null;
                while (st.hasMoreTokens()) {
                    word = st.nextToken();
                    if (word != null) {
                        word = word.toLowerCase();
                        if (word.length() > 0) {
                            if (Character.isLetter(word.charAt(0))) {
                                word = word.replaceFirst(("" + word.charAt(0)), ("" + word.charAt(0)).toUpperCase());
                            }
                        }
                        resVal = resVal + word + " ";
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return resVal;
    }

    public static int getDiffInMonths(Calendar cal1, Calendar cal2) {
        try {
            int monthCount = -1;
            while (!(cal1.after(cal2))) {
                cal1.add(Calendar.MONTH, 1);
                monthCount++;
            }
            if ((cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) && (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) && (cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE))) {
                monthCount++;
                return monthCount;
            } else {
                return -1;
            }
        } catch (Exception e) {
            return -1;
        }
    }

    public static boolean showMessageIfPastDate(Component comp, String fieldDesc, JFormattedTextField dateField) {
        boolean dateValid = true;
        try {
            java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
            today = new java.sql.Date(dateformat.parse(dateformat.format(today)).getTime());
            java.sql.Date dateVal = new java.sql.Date((dateformat.parse(dateField.getText())).getTime());
            if (dateVal.before(today)) {
                JOptionPane.showMessageDialog(comp, fieldDesc + " should not be a past Date.", "Error", JOptionPane.ERROR_MESSAGE);
                dateField.setText("");
                dateField.requestFocus();
                dateValid = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateValid;
    }

    public static boolean showMessageIfDateBefore100Years(Component comp, String fieldDesc, JFormattedTextField dateField) {
        boolean dateValid = true;
        try {
            Calendar today = Calendar.getInstance();
            Calendar dateVal1 = Calendar.getInstance();
            dateVal1.setTime(dateformat.parse(dateField.getText()));
            if ((today.get(Calendar.YEAR) - dateVal1.get(Calendar.YEAR)) > 100) {
                JOptionPane.showMessageDialog(comp, fieldDesc + "Invalid Date.", "Error", JOptionPane.ERROR_MESSAGE);
                dateField.setText("");
                dateField.requestFocus();
                dateValid = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateValid;
    }

    public static String getKeyFromVal_ForCollection(Properties keyValues, String val) {
        String key = null;
        try {
            if (keyValues != null && val != null) {
                Iterator itr = keyValues.entrySet().iterator();
                if (itr != null) {
                    while (itr.hasNext()) {
                        Entry entry = (Entry) itr.next();
                        if (entry != null) {
                            if (entry.getValue().toString().equalsIgnoreCase(val)) {
                                key = entry.getKey().toString();
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }

    public static boolean isItemPresentInJCombo(JComboBox combo, String item) {
        try {
            if (item != null) {
                int size = combo.getModel().getSize();
                for (int i = 0; i < size; i++) {
                    Object obj = combo.getModel().getElementAt(i);
                    if (obj != null) {
                        if (obj.equals(item)) {
                            return true;
                        }
                    }
                }
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static String validateAndSetDate(String datestr) {
        String formattedDate = null;
        SimpleDateFormat format1, format2;
        Date date;
        try {
            if (datestr != null) {
                if (!datestr.contains("/") && datestr.length() != 8) {
                    formattedDate = null;
                } else if (datestr.contains("/") && datestr.length() != 10) {
                    formattedDate = null;
                } else {
                    format1 = new SimpleDateFormat("ddMMyyyy");
                    format2 = new SimpleDateFormat("dd/MM/yyyy");
                    format1.setLenient(false);
                    format2.setLenient(false);
                    try {
                        date = format1.parse(datestr);
                        formattedDate = format2.format(date);
                    } catch (ParseException e) {
                        formattedDate = null;
                    }
                    if (formattedDate == null) {
                        try {
                            date = format2.parse(datestr);
                            formattedDate = format2.format(date);
                        } catch (ParseException e) {
                            formattedDate = null;
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            format1 = null;
            format2 =
                    null;
            date =
                    null;
        }

        return formattedDate;
    }

    public static boolean validateDate(String datestr) {//old
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat format1 = new SimpleDateFormat(pattern);
        format1.setLenient(false);
        try {
            if (datestr.length() == 10) {
                Date date = format1.parse(datestr);
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }

    }

    public static boolean isToDateAfterFromDate(String frm_Date, String to_Date) {
        boolean toDate_isAfter_FrmDate = true;
        try {
            if (isFieldNotEmpty(frm_Date) && isFieldNotEmpty(to_Date)) {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                format.setLenient(false);
                Date frmDate = null, toDate = null;
                try {
                    frmDate = format.parse(frm_Date);
                    toDate = format.parse(to_Date);
                } catch (Exception e) {
                }
                if (frmDate != null && toDate != null) {
                    if (frmDate.after(toDate)) {
                        toDate_isAfter_FrmDate = false;
                    }
                }

            }
        } catch (Exception e) {
        }
        return toDate_isAfter_FrmDate;
    }

    public static boolean Datevalidation(String date, String month, String year) {
        if (Numbervalidation(date) && Numbervalidation(month) && Numbervalidation(year)) {
            return false;
        } else {
            int date1 = Integer.parseInt(date);
            int month1 = Integer.parseInt(month);
            int year1 = Integer.parseInt(year);
            switch (month1) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    if (date1 < 1 || date1 > 31) {
                        return true;
                    } else {
                        return false;
                    }

                case 4:
                case 6:
                case 9:
                case 11:
                    if (date1 < 1 || date1 > 30) {
                        return true;
                    } else {
                        return false;
                    }
                case 2:
                    if (year1 % 4 == 0) {
                        if (date1 < 1 || date1 > 29) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        if (date1 < 1 || date1 > 28) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                default:
                    return true;
            }
        }


    }

    public static boolean Numbervalidation(String fieldval) {
        int length = fieldval.length();


        for (int i = 0; i < length; i++) {
            int x = fieldval.charAt(i);

            if (!(x > 47 && x < 58)) {
                return true;
            }
        }
        return false;
    }

    public static boolean presentInArrayList(ArrayList<String> list, String value) {
        boolean presentInList = false;
        try {
            if (list != null && value != null) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    String listVal = (String) it.next();
                    if (value.equalsIgnoreCase(listVal)) {
                        return true;
                    }

                }
            }

        } catch (Exception e) {
        }
        return presentInList;
    }

    public static long getDiffbtweendates(String date1, String date2) {



        int currdate = Integer.parseInt(date2.substring(0, 2));
        int currentmonth = Integer.parseInt(date2.substring(3, 5));
        int curryear = Integer.parseInt(date2.substring(6, 10));

        int issuedate = Integer.parseInt(date1.substring(0, 2));
        int issuemonth = Integer.parseInt(date1.substring(3, 5));
        int issueyear = Integer.parseInt(date1.substring(6, 10));




        GregorianCalendar da1 = new GregorianCalendar(issueyear, issuemonth - 1, issuedate);
        GregorianCalendar da2 = new GregorianCalendar(curryear, currentmonth - 1, currdate);
        long d1 = da1.getTime().getTime();
        long d2 = da2.getTime().getTime();
        long difMil = d2 - d1;
        long milPerDay = 1000 * 60 * 60 * 24;


        long days = difMil / milPerDay;

        return days;

    }
    private static DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

    public static String validateAndSetDate(String datestr, Component comp) {
        String formattedDate = null;
        SimpleDateFormat format1, format2;
        Date date;
        try {
            if (datestr != null) {
                if (!datestr.contains("/") && datestr.length() != 8) {
                    formattedDate = null;
                    JOptionPane.showMessageDialog(comp, "Invalid date format.\nEnter in 'DD/MM/YYYY' or 'DDMMYYYY'!", "Error Message", JOptionPane.ERROR_MESSAGE);
                } else if (datestr.contains("/") && datestr.length() != 10) {
                    formattedDate = null;
                    JOptionPane.showMessageDialog(comp, "Invalid date format.\nEnter in 'DD/MM/YYYY' or 'DDMMYYYY'!", "Error Message", JOptionPane.ERROR_MESSAGE);
                } else {
                    format1 = new SimpleDateFormat("ddMMyyyy");
                    format2 = new SimpleDateFormat("dd/MM/yyyy");
                    format1.setLenient(false);
                    format2.setLenient(false);
                    try {
                        date = format1.parse(datestr);
                        formattedDate = format2.format(date);
                    } catch (ParseException e) {
                        formattedDate = null;
                    }
                    if (formattedDate == null) {
                        try {
                            date = format2.parse(datestr);
                            formattedDate = format2.format(date);
                        } catch (ParseException e) {
                            formattedDate = null;
                        }
                    }
                    if (formattedDate == null && comp != null) {
                        JOptionPane.showMessageDialog(comp, "Invalid date!", "Error Message", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            format1 = null;
            format2 =
                    null;
            date =
                    null;
        }

        return formattedDate;
    }

// start : added by ravi thota on 18.11.2011 for checking manual date should be in the same month
    public static int getDiffInMonths(String dateFrom, String dateTo) {
        SimpleDateFormat dateformat;
        Date formDate;
        Date toDate;
        Calendar calFromDate;
        Calendar calToDate;
        try {
            dateformat = new SimpleDateFormat("dd/MM/yyyy");
            formDate = dateformat.parse(dateFrom);
            toDate = dateformat.parse(dateTo);
            calFromDate = Calendar.getInstance();
            calFromDate.setTime(formDate);
            calToDate = Calendar.getInstance();
            calToDate.setTime(toDate);
            if (calToDate.get(Calendar.YEAR) - calFromDate.get(Calendar.YEAR) == 0) {
                return calToDate.get(Calendar.MONTH) - calFromDate.get(Calendar.MONTH);
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
// end : added by ravi thota on 18.11.2011 for checking manual date should be in the same month
    
//Added by jyoti for  passWord Encription 25.07.2017           
// encrypt User passWord
    public static String passWordEncription(String password) {     
        return new String(org.apache.commons.codec.binary.Base64.encodeBase64(password.getBytes()));        
    }

    // encrypt User passWord
    public static String passWordDecrypt(String password) { //Added by jyoti for passWord Decrypt 25.07.2017  
         return new String(org.apache.commons.codec.binary.Base64.decodeBase64(password.getBytes()));
    }
//End : Added by jyoti for  passWord Encription 25.07.2017               
    
}
