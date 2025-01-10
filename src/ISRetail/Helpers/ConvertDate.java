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
 * USAGE
 * This class file is used to convert the various date formats 
 * Conversion from string to date
 * Conversion from date text to numeric etc
 * 
 * 
 * 
 */
package ISRetail.Helpers;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ConvertDate {

    /**
     * To convert the String date to Numeric date format (yyyymmdd)
     */
    public static int getDateNumeric(String d, String pattern) {

        int dateToInteger = 0;
        SimpleDateFormat sdf1;
        SimpleDateFormat sdf;
        Date date = null;
        try {
            sdf1 = new SimpleDateFormat(pattern);
            date = sdf1.parse(d);
            sdf = new SimpleDateFormat("yyyyMMdd");
            dateToInteger = Integer.parseInt(sdf.format(date));
        } catch (Exception e) {

        } finally {
            sdf = null;
            sdf1 = null;
        }
        return dateToInteger;
    }

    /**
     * To convert the java Date to Numeric date format (yyyymmdd)
     */
    public static int getDateNumeric(Date d) {
        int dateToInteger = 0;
        SimpleDateFormat sdf;
        try {
            sdf = new SimpleDateFormat("yyyyMMdd");
            dateToInteger = Integer.parseInt(sdf.format(d));
        } catch (Exception e) {
        } finally {
            sdf = null;
        }
        return dateToInteger;
    }

    /**
     * To get the time from the java Date to Numeric date format (HHmmss)
     */
    public static String getTimeString(Date d) {
        String dateToString = "";
        SimpleDateFormat sdf;
        try {
            sdf = new SimpleDateFormat("HHmmss");
            dateToString = sdf.format(d);
        } catch (Exception e) {
        } finally {
            sdf = null;
        }
        return dateToString;
    }

    /**
     * To get the date from the java Date into numeric format yyyyMMdd
     */
    public static int getDateNumeric() {
        int dateToInteger = 0;
        Date date = null;
        SimpleDateFormat sdf;
        try {
            date = new Date(System.currentTimeMillis());
            sdf = new SimpleDateFormat("yyyyMMdd");
            dateToInteger = Integer.parseInt(sdf.format(date));
        } catch (Exception e) {
        } finally {
            sdf = null;
        }
        return dateToInteger;
    }

    /**
     * To Convert a String to the java Date Object
     */
    public static Date getStringToDate(String d, String pattern) {
        Date date = null;
        SimpleDateFormat sdf1;
        try {
            sdf1 = new SimpleDateFormat(pattern);
            date = sdf1.parse(d);
        } catch (Exception e) {
        } finally {
            sdf1 = null;
        }

        return date;
    }

    /**
     * To Convert a Numneric Date(yyyyMMdd) to Standard Date(dd/MM/yyyy)
     */
    public static String getNumericToDate(int dateInNumeric) {

        Date d = null;
        String dateVal = null;
        String dateInString;
        SimpleDateFormat sdf;
        SimpleDateFormat sdf1;
        try {
            dateInString = Integer.toString(dateInNumeric);
            sdf = new SimpleDateFormat("yyyyMMdd");
            d = sdf.parse(dateInString);
            sdf1 = new SimpleDateFormat("dd/MM/yyyy");
            dateVal = sdf1.format(d);
        } catch (Exception e) {
        } finally {
            dateInString = null;
            sdf = null;
            sdf1 = null;
            d = null;
        }
        return dateVal;

    }

    /**
     * To Convert a Time Format(HHmmss) to (h:mm:ss a") Format(AM/PM)
     */
    public static String getformattedTimeFromTime(String timeInString) {

        Date timeVal = null;
        String timeString = null;
        SimpleDateFormat sdfTime;
        SimpleDateFormat formattedTime;
        try {
            sdfTime = new SimpleDateFormat("HHmmss");
            timeVal = sdfTime.parse(timeInString);
            formattedTime = new SimpleDateFormat("h:mm:ss a");
            timeString = formattedTime.format(timeVal);
        } catch (Exception e) {
        } finally {
            sdfTime = null;
            formattedTime = null;
            timeVal = null;
        }
        return timeString;
    }

    /**
     * To Get the Expiry Date Format (MM/yy")
     */
    public static String getNumericToExpDate(int dateInNumeric) {
        Date d = null;
        String dateVal = null;
        String dateInString;
        SimpleDateFormat sdf;
        SimpleDateFormat sdf1;
        try {
            dateInString = Integer.toString(dateInNumeric);
            sdf = new SimpleDateFormat("yyyyMMdd");
            d = sdf.parse(dateInString);
            sdf1 = new SimpleDateFormat("MM/yy");
            dateVal = sdf1.format(d);
        } catch (Exception e) {
        } finally {
            sdf = null;
            sdf1 = null;
            dateInString = null;
        }
        return dateVal;
    }

    /**
     * To Convert the Current Date Time to Numeric Format(HHmmss)
     */
    public static int getCurrentTimeToNumeric() {

        //Begin Original code for Windows XP
        int timeToInteger = 0;
        Date d;
        SimpleDateFormat sdf;
        try {
            d = new Date(System.currentTimeMillis());
            sdf = new SimpleDateFormat("HHmmss");
            timeToInteger = Integer.parseInt(sdf.format(d));
        } catch (Exception e) {
        } finally {
            sdf = null;
            d = null;
        }
        return timeToInteger;
        //End Original code for Windows XP

        //Begin New code for Windows 7
        /*   int timeToInteger = 0;
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+530"));
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        String ss="";
        if(hour<=9) {
            ss="0"+String.valueOf(hour);
        }else{
            ss=String.valueOf(hour);
        }
                
        int min = cal.get(Calendar.MINUTE);
        if(hour <=9) {
            ss=ss+"0"+String.valueOf(min);
        }else{
            ss=ss+String.valueOf(min);
        }
                
        int secc = cal.get(Calendar.SECOND);
        if(secc<=9) {
            ss=ss+"0"+String.valueOf(secc);
        }else{
            ss=ss+String.valueOf(secc);
        }
        
        timeToInteger = Integer.parseInt(ss);
        return timeToInteger;*/
        //end New code for Windows7
    }

    /**
     * To Convert the Current Date Time to String Format("HHmmss")
     */
    public static String getCurrentTimeToString() {
        String timeToInteger = "";
        Date d;
        SimpleDateFormat sdf;
        try {
            d = new Date(System.currentTimeMillis());
            sdf = new SimpleDateFormat("HHmmss");
            timeToInteger = sdf.format(d);
        } catch (Exception e) {
        } finally {
            sdf = null;
            d = null;
        }
        return timeToInteger;
    }

    /**
     * To Convert the Numeric Date to java Util.Date
     */
    public static Date getUtilDateFromNumericDate(int dateInNumeric) {
        Date d = null;
        String dateInString;
        SimpleDateFormat sdf;
        try {
            dateInString = Integer.toString(dateInNumeric);
            sdf = new SimpleDateFormat("yyyyMMdd");
            d = sdf.parse(dateInString);
        } catch (Exception e) {

        } finally {
            sdf = null;
            dateInString = null;
        }
        return d;
    }

    public static Date getCurrentDateWithFormat(String dateString) {
        Date result = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // Parse the input string into a Date object
            java.util.Date date = sdf.parse(dateString);
            // Convert Date object to LocalDate
            result = date;
        } catch (Exception e) {
            // Handle parsing exceptions
            e.printStackTrace();
        }
        return result;
    }

    /**
     * To Convert the String Time(HHmmss) to Calendar Date
     */
    public static Calendar getSqlTimeFromString(String timeInString) {
        Calendar time = null;
        SimpleDateFormat sdf;
        int val = 0;
        try {
            sdf = new SimpleDateFormat("HHmmss");
            time = Calendar.getInstance();
            time.setTime(sdf.parse(timeInString));
            try {
                // Fixed the time issue while sending the payload
                val = Integer.parseInt(timeInString.substring(0, 2));
            } catch (Exception e) {
            }
            if (val >= 12) {
                time.set(Calendar.AM_PM, Calendar.PM);
            } else {
                time.set(Calendar.AM_PM, Calendar.AM);
            }

        } catch (Exception e) {
        } finally {
            sdf = null;
        }
        return time;
    }

    /**
     * To Get the Day from the Date in any given Format
     */
    public static int getDayByDate(String strDate, String pattern) {
        int dayDate = -1;
        Calendar time = null;
        SimpleDateFormat sdf1;
        try {

            sdf1 = new SimpleDateFormat(pattern);
            time = Calendar.getInstance();
            time.setTime(sdf1.parse(strDate));
            dayDate = time.get(Calendar.DAY_OF_WEEK);
        } catch (Exception e) {
        } finally {
            sdf1 = null;
        }
        return dayDate;
    }
}
