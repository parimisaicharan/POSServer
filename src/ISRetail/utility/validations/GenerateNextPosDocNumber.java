/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.utility.validations;

/**
 *
 * @author Administrator
 */
public class GenerateNextPosDocNumber {

    public String generatenumber(String oldDocNo,int firstFixedStringLength) {
        String firstFixedString = oldDocNo.substring(0, firstFixedStringLength);
        String st = oldDocNo.substring(firstFixedStringLength, oldDocNo.length());
        String tpart = "";
        String numpart = "";
        for (int i = 0; i < st.length(); i++) {
            if (Character.isLetter(st.charAt(i))) {
                tpart = tpart + st.charAt(i);
            } else {
                numpart = numpart + st.charAt(i);
            }
        }
        if (ifAllNines(numpart)) {
            String temp = addTostring(tpart);
            numpart = PrefixZero(numpart.length() - (temp.length() - tpart.length()), "");
            tpart = temp;

        }
        numpart = addToNum(numpart);
        oldDocNo = firstFixedString + tpart + numpart;
        return oldDocNo;
    }

    public boolean ifAllNines(String s) {
        boolean f = true;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '9') {
                f = false;
                break;
            }
    }
        return f;
    }

    public String PrefixZero(int d, String s) {
        for (int i = 0; i < d; i++) {
            s = "0" + s;

        }
        return s;
    }

    public String addToNum(String s) {
        int l = s.length();
        int n = Integer.parseInt(s);
        n++;
        String st = String.valueOf(n);
        int d = l - st.length();
        return PrefixZero(d, st);
    }

    public String addTostring(String s) {
        if (s.length() == 0) {
            return "A";
        }
        StringBuffer sb = new StringBuffer(s);
        int c = 1;
        for (int i = s.length() - 1; i >= 0; i--) {
            int a = (int) s.charAt(i);
            a += c;
            if (a > (int) 'Z') {
                sb.setCharAt(i, 'A');
                c = 1;
            } else {
                sb.setCharAt(i, (char) a);
                c = 0;
                break;
            }
        }
        String st = sb.toString();
        if (c == 1) {
            st = "A" + st;
        }
        return st;
    }
}
