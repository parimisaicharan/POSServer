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
 * POJO for POS Control Report
 * 
 * 
 * 
 */

package ISRetail.poscontrolreport;


public class POSControlReportPOJO {
 private int sno;
 private String transaction;
 private String lastNo;
 private String datafromisr;
 private String postingdate;
 private String transactiondate;
 private String transactiontime;

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getLastNo() {
        return lastNo;
    }

    public void setLastNo(String lastNo) {
        this.lastNo = lastNo;
    }

    public String getDatafromisr() {
        return datafromisr;
    }

    public void setDatafromisr(String datafromisr) {
        this.datafromisr = datafromisr;
    }

    public String getTransactiondate() {
        return transactiondate;
    }

    public void setTransactiondate(String transactiondate) {
        this.transactiondate = transactiondate;
    }

    public String getTransactiontime() {
        return transactiontime;
    }

    public void setTransactiontime(String transactiontime) {
        this.transactiontime = transactiontime;
    }

    public String getPostingdate() {
        return postingdate;
    }

    public void setPostingdate(String postingdate) {
        this.postingdate = postingdate;
    }
 
}
