/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comfortcall;

import ISRetail.utility.validations.Validations;
import java.io.File;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author ewitsupport
 */
public class ComfortMailSend {

    public int mail(Connection con, String storecode, String custEmail, int dbdate, String date, String filepath) throws Exception {
        MailDo maildo;
        ComfortDO cfdo = new ComfortDO();
//        String status = "success";
        int status = 1;
        String[] maildetails = null;
        Encrypt_decrypt ED = null;
        final String strPssword = "POSS_EMAIL_TO_CUSTOMER_PASSWORD_ENCRYPTION";
        final String from;
        final String password;
        String dec_password = "";
        DateFormat dateFormat = new SimpleDateFormat("HHmmss");
            Date currenttime = new Date();
            System.out.println("current time : " + dateFormat.format(currenttime));
        try {
            maildo = new MailDo();
            maildetails = new String[2];
            maildetails = maildo.getMail_Password(con);
            

            if (Validations.isFieldNotEmpty(maildetails)) {
                System.out.println("Enter into mail");
                ED = new Encrypt_decrypt();
                SecretKeySpec secretKey = ED.setKey(strPssword);
                try {
                    dec_password = ED.decryptText(maildetails[1], secretKey);
                } catch (Exception e) {
                    e.printStackTrace();
                    status = 0;
                    return status;
                }
                maildetails[1] = dec_password;
                from = maildetails[0];
                String to = custEmail;
                password = maildetails[1];
                System.out.println("from : "+from+"to : "+to+" password : "+password);
                String host = "smtp.gmail.com";
                Properties props = System.getProperties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", host);
                props.put("mail.smtp.port", "587");
//                String javafilename = System.getProperty("java.home")
//                        + "/lib/security/cacerts".replace('/', File.separatorChar);
//                String temppassword = "changeit";
//                props.put("javax.net.ssl.trustStore", javafilename);
//                props.put("javax.net.ssl.trustStorePassword", temppassword);


                Session session = Session.getDefaultInstance(props,
                        new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(to));

                // Set Subject: header field
                message.setSubject("Comfort Call Details");

                // This mail has 2 part, the BODY and the embedded image
                MimeMultipart multipart = new MimeMultipart("related");

                // first part (the html)
                String fontclolor = "#00bfff";
                BodyPart messageBodyPart = new MimeBodyPart();

                String htmlText = "<p>Dear Store,</p><p></p><p>Comfort call details for the Previous day (Sales Order Billing) is attached with this mail.</p><br><p>Thank You,</p><p>Titan Eyeplus</p>";
                messageBodyPart.setContent(htmlText, "text/html");

                multipart.addBodyPart(messageBodyPart);

                messageBodyPart = new MimeBodyPart();
                String filename = "ComfortCallDetails";

                System.out.println("filename :-- " + filename);
//            String filename = invoiceno+".pdf";
                DataSource source = new FileDataSource(filepath);
                messageBodyPart.setDataHandler(new DataHandler(source));

                messageBodyPart.setFileName(filename + ".xls");

                multipart.addBodyPart(messageBodyPart);
                // put everything together
                message.setContent(multipart);
                //Send message
                Transport.send(message);
                System.out.println("Sent message successfully....");

                cfdo.updateMailStatus(con, "comfortcall", dbdate, dateFormat.format(currenttime), "Y");
            } else {
//                status = "configuremail";
                cfdo.updateMailStatus(con, "comfortcall", dbdate, dateFormat.format(currenttime), "N");
                status = 6;
                return status;
            }
        } catch (MessagingException e) {
//            status = "failed";
            cfdo.updateMailStatus(con, "comfortcall", dbdate, dateFormat.format(currenttime), "N");
            status = 0;
            System.out.println("*******Faild********");
            throw new RuntimeException(e);

        }
        return status;
    }

    

}
