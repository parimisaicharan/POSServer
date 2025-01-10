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
 * This class file is used to Fix the Decimal Length in the Number
 * 
 * 
 * 
 * 
 */

package ISRetail.Helpers;

import java.text.NumberFormat;
import java.text.ParseException;

public class Decimalvalue {
    
   /**
   * To fix the decimal values for two digits from double to double
   */    
      public static String numberFormat(double doubleval)
      {
          NumberFormat formater = null;
           try {
            formater = NumberFormat.getInstance();
            formater.setMaximumFractionDigits(2);
            formater.setMinimumFractionDigits(2);
            formater.setGroupingUsed(false);
            return formater.format(doubleval);
            }catch(Exception ex) {                                       
            }                 
            return formater.format(doubleval);               
            
      }
      
 
   /**
   * To fix the decimal values for two digits from String to double
   */    
       public static double numberFormatToDouble(String stringval) throws ParseException
      {
           
       NumberFormat formater;
       Number number = null;
       
            try {
            formater = NumberFormat.getInstance();
            formater.setGroupingUsed(false);
            number =formater.parse(stringval);            
            } catch(Exception ex) {                
                       
            } finally {
                formater = null;                
            }
            return number.doubleValue();
            
      }

}
