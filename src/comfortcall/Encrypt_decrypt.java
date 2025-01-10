/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comfortcall;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author ewitsupport
 */
public class Encrypt_decrypt {
    private SecretKeySpec secretKey ;
    private static byte[] key ;
    
    private static String decryptedString;
    private static String encryptedString;
    
     public SecretKeySpec setKey(String myKey){
        
   
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
//            System.out.println(key.length);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); // use only first 128 bit
//            System.out.println(key.length);
            System.out.println(new String(key,"UTF-8"));
            secretKey = new SecretKeySpec(key, "AES");
            
            
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return secretKey;
    
    }
    
    
     public String encryptText(String password,SecretKeySpec secretKey1) throws Exception{
       
         String encryptText = "";
         try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");        
            cipher.init(Cipher.ENCRYPT_MODE, secretKey1);    
            encryptText = org.apache.commons.codec.binary.Base64.encodeBase64String(cipher.doFinal(password.getBytes("UTF-8")));
        
        }
        catch (Exception e)
        {
           
            System.out.println("Error while encrypting: "+e.toString());
        }
        return encryptText;
        
    }
        
    public String decryptText(String encrypt_password,SecretKeySpec secretKey1) throws Exception{
        String decryptedString = "";
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");           
            cipher.init(Cipher.DECRYPT_MODE, secretKey1);
//            System.err.println("encrypt_password==== "+encrypt_password);
            decryptedString = new String(cipher.doFinal(org.apache.commons.codec.binary.Base64.decodeBase64(encrypt_password)));
//            System.out.println("decryptedString -- "+decryptedString);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
//            System.out.println("Error while decrypting: "+e.toString());
        }
        return decryptedString;
}
}
