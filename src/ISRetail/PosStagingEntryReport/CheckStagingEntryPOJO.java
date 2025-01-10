/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ISRetail.PosStagingEntryReport;

/**
 *
 * @author Administrator
 */
public class CheckStagingEntryPOJO {
    
    private String storecode;
    private String transactionid;
    private int createdDate;
    private String createdTime;
    private String smtpid;
    private String username;
    private String passwd;
    private String emailid;
    private String fromemailid;

    public String getStorecode() {
        return storecode;
    }

    public void setStorecode(String storecode) {
        this.storecode = storecode;
    }


    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }


    public int getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(int createdDate) {
        this.createdDate = createdDate;
    }


    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getSmtpid() {
        return smtpid;
    }

    public void setSmtpid(String smtpid) {
        this.smtpid = smtpid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getFromemailid() {
        return fromemailid;
    }

    public void setFromemailid(String fromemailid) {
        this.fromemailid = fromemailid;
    }
    

}
