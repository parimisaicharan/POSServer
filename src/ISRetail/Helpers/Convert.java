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
 */


package ISRetail.Helpers;


public class Convert {  
  static  int  i;
  static String s2;
  private static final String[] majorNames = {""," thousand"};
  private static final String[] tensNames = {"","ten"," twenty"," thirty"," fourty"," fifty"," sixty"," seventy"," eighty"," ninety"};
  private static final String[] numNames = {""," one"," two"," three"," four"," five"," six"," seven"," eight"," nine"," ten"," eleven"," twelve"," thirteen"," fourteen"," fifteen"," sixteen"," seventeen"," eighteen"," nineteen" };
  
  public void Convert() {
  }
  
  /**
   * To convert the number into english Words less than 1000
   */    

  public static  String convertLessThanOneThousand(int number) {
           String s;
           if (number % 100 < 20){
                      s = numNames[number % 100];
                      number /= 100;
           }else {
                      s = numNames[number % 10];
                      number /= 10;
                      s = tensNames[number % 10] + s;
                      number /= 10;
           }
           if (number == 0)
                      return s;
           if(s==""){
                      return  numNames[number] + " hundred " +s;
           }else{
                      return  numNames[number] + " hundred and" +s;
           }
}  

  /**
 * To convert any number into english Words less than 1000
 */
  
  public static String Convert(int number) {
    /* special case */
int lly=0;
int llz=0;
String ll="";
 if(number%100000>=0)
{
int e=number/100000;

switch(e)
{
case 1:
ll="One Lakh";
lly=1;
if(number%100000==0)
{
llz=1;
}
else
{
number=number%100000;
}
break;
case 2:
ll="Two Lakh";
lly=1;
if(number%100000==0)
{
llz=1;
}
else
{
number=number%100000;
}
break;
case 3:
ll="Three Lakh";
lly=1;
if(number%100000==0)
{
llz=1;
}
else
{
number=number%100000;
}
break;
case 4:
ll="Four Lakh";
lly=1;
if(number%100000==0)
{
llz=1;
}
else
{
number=number%100000;
}
break;
case 5:
ll="Five Lakh";
lly=1;
if(number%100000==0)
{
llz=1;
}
else
{
number=number%100000;
}
break;
case 6:
ll="Six Lakh";
lly=1;
if(number%100000==0)
{
llz=1;
}
else
{
number=number%100000;
}
break;
case 7:
ll="Seven Lakh";
lly=1;
if(number%100000==0)
{
llz=1;
}
else
{
number=number%100000;
}
break;
case 8:
ll="Eight Lakh";
lly=1;
if(number%100000==0)
{
llz=1;
}
else
{
number=number%100000;
}
break;
case 9:
ll="Nine Lakh";
lly=1;
if(number%100000==0)
{
llz=1;
}
else
{
number=number%100000;
}
break;
case 10:
ll="Ten Lakh";
lly=1;
if(number%100000==0)
{
llz=1;
}
else
{
number=number%100000;
}
break;
}
}


    if (number == 0) { return "zero"; }
    if (number < 0 || number>0) {
        //number = -number;
      }
if(llz==0){
    String s = "";
    int place = 0;

    do {
      int n = number % 1000;
      if (n != 0){
         String s1 = convertLessThanOneThousand(n);
         s = s1+ majorNames[place] +s;
        }
      place++;
      number /= 1000;
      } while (number > 0);
if(lly==1)
{
    return (ll+ s).trim();
}
else
{
 return s.trim();

}
}
else{
 return ll.trim();

}
}


}



