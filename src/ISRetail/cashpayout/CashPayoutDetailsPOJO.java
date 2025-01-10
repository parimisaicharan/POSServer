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
 * This is Cash Payout Line Item POJO representing the CashPayout Line Items
 * 
 * 
 * 
 */

package ISRetail.cashpayout;
public class CashPayoutDetailsPOJO {    
    
    private String instrumentno="";
    private int issuedate=0;
    private String bank="";
    private String branchname="";
    private String sapreferenceid="";
    private double amount=0;
    

    public String getInstrumentno() {
        return instrumentno;
    }

    public void setInstrumentno(String instrumentno) {
        this.instrumentno = instrumentno;
    }

    public int getIssuedate() {
        return issuedate;
    }

    public void setIssuedate(int issuedate) {
        this.issuedate = issuedate;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getSapreferenceid() {
        return sapreferenceid;
    }

    public void setSapreferenceid(String sapreferenceid) {
        this.sapreferenceid = sapreferenceid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
