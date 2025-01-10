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
 * This class file is used to convert the number into  English Words
 * 
 * 
 * 
 * 
 */

package ISRetail.Helpers;
import java.text.NumberFormat;
import java.util.StringTokenizer;

public class NumberToEnglish {
String string;
String a[]={"","one","two","three","four","five","six","seven","eight","nine",};

String b[]={"hundred","thousand","lakh","crore"};

String c[]={"ten","eleven","twelve","thirteen","fourteen","fifteen","sixteen","seventeen","eighteen","ninteen",};

String d[]={"twenty","thirty","fourty","fifty","sixty","seventy","eighty","ninty"};

/**
 * To Convert a double value to English Words
 */       
public  String convertNumToWord(double value){

   int c=1;
   int rm ;
   string="";
   
   int number = (int)value ;
   
   while ( number != 0 )  {
        switch ( c ) {
            case 1 :
                    rm = number % 100 ;
                    pass ( rm ) ;
                    if( number > 100 && number % 100 != 0 ) {
                        // here need to add and
                        display ( "" ) ;
                    }
                    number /= 100 ;
                    break ;

            case 2 :
                    rm = number % 10 ;
                    if ( rm != 0 ) {
                        display ( " " ) ;
                        display ( b[0] ) ;
                        display ( " " ) ;
                        pass ( rm ) ;
                    }
                    number /= 10 ;
                    break ;

            case 3 :
                    rm = number % 100 ;
                    if ( rm != 0 ) {
                        display ( " " ) ;
                        display ( b[1] ) ;
                        display ( " " ) ;
                        pass ( rm ) ;
                    }
                    number /= 100 ;
                    break ;

            case 4 :
                    rm = number % 100 ;
                    if ( rm != 0 ) {
                        display ( " " ) ;
                        display ( b[2] ) ;
                        display ( " " ) ;
                        pass ( rm ) ;
                    }
                    number /= 100 ;
                    break ;
           case 5 :
                    rm = number % 100 ;
                    if ( rm != 0 ) {
                        display ( " " ) ;
                        display ( b[3] ) ;
                        display ( " " ) ;
                        pass ( rm ) ;
                    }
                    number /= 100 ;
                    break ;

        }
        c++ ;
      }

return string;
}

/**
 * To Convert a double value to English Words
 */       
public static String  converToWord(double number){
    int decimal =0 ;  
    int digit = 0;
    String first = ""; 
    String second = "";
    try { 
        NumberFormat f = NumberFormat.getInstance();
        // getting the stigng // its containg , separator. so we need to remove the comma
        String num  =  f.format(number);
        String numberWithOutComa=""; 
        // here creating a string for removing the comma
        StringTokenizer token = new StringTokenizer(num,",");
        while(token.hasMoreTokens() ) { 
            // removing the comma
            numberWithOutComa = numberWithOutComa + token.nextToken() ;
        }
        decimal =(int)number ;  
        if(numberWithOutComa.contains(".")) { 
             int length = numberWithOutComa.indexOf(".");
             decimal = Integer.parseInt(numberWithOutComa.substring(0,length)) ; 
             digit = Integer.parseInt(numberWithOutComa.substring(length+1));
        }

        if (digit > 0) {
            if(decimal > 0) {
              first = new NumberToEnglish().convertNumToWord(decimal)+ " rupees and ";
            }  
            second = new NumberToEnglish().convertNumToWord(digit) + " paise Only" ;
            first = first + second;
        }else { 
             first = new NumberToEnglish().convertNumToWord(decimal)+ " rupees Only";
        }

    }catch (Exception e) { 
        
        e.printStackTrace();
        
    }
    return first;
}

/**
 * To Convert a int value to English Words
 */       
public void pass(int number) {
        int rm, q ;
        if ( number < 10 ) {
            display ( a[number] ) ;
        }

        if ( number > 9 && number < 20 ) {
            display ( c[number-10] ) ;
        }

        if ( number > 19 ) {
            rm = number % 10 ;
            if ( rm == 0 ) {
                q = number / 10 ;
                display ( d[q-2] ) ;
            } else {
                q = number / 10 ;
                display ( a[rm] ) ;
                display ( " " ) ;
                display ( d[q-2] ) ;
            }
        }
}

/**
 * To Display the String
 */       
public void display(String s) {
    String t ;
    t= string ;
    string= s ;
    string+= t ;
}

} 
