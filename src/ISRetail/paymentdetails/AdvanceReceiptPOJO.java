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
 * POJO for AdvanceReceipt
 * 
 * 
 * 
 */

package ISRetail.paymentdetails;
public class AdvanceReceiptPOJO {

    private String documentno="";
    private String storecode="";
    private int fiscalyear=0;
    private String refno="";
    private int refdate=0;
    private int dateofpayment=0;
    private double amount=0.00;
    private double totalrecvdamount=0.00;
    private String customercode="";
    private  String datasheetno="";
    private String reasonforcancellation="";
    private String sapreferenceid="";
    private String cancelledstatus="";
    private String creditnotenopos="";
    private String createdby="";
    private int createddate=0;
    private String createdtime="";
    private String modifiedby=""; 
    private int modifieddate=0;
    private String modifiedtime="";
    
    /********saleorder status********/
    private boolean flagadv=false;
    private boolean flagforcereleae=false;
    private String saletype="";
    private String releasestatus="";
    private String creditsalereference="";
    private String cancelOtp;
    /********End od sale order status***/
    
    private int releasedate=0;
    //added on 9 Feb 2012 for Division tag isue in payload
    private String division="";

    public String getDocumentno() {
        return documentno;
    }

    public void setDocumentno(String documentno) {
        this.documentno = documentno;
    }

    public String getStorecode() {
        return storecode;
    }

    public void setStorecode(String storecode) {
        this.storecode = storecode;
    }

    public int getFiscalyear() {
        return fiscalyear;
    }

    public void setFiscalyear(int fiscalyear) {
        this.fiscalyear = fiscalyear;
    }

  

    public int getDateofpayment() {
        return dateofpayment;
    }

    public void setDateofpayment(int dateofpayment) {
        this.dateofpayment = dateofpayment;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCustomercode() {
        return customercode;
    }

    public void setCustomercode(String customercode) {
        this.customercode = customercode;
    }

    public String getDatasheetno() {
        return datasheetno;
    }

    public void setDatasheetno(String datasheetno) {
        this.datasheetno = datasheetno;
    }

  

    public String getSapreferenceid() {
        return sapreferenceid;
    }

    public void setSapreferenceid(String sapreferenceid) {
        this.sapreferenceid = sapreferenceid;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public int getCreateddate() {
        return createddate;
    }

    public void setCreateddate(int createddate) {
        this.createddate = createddate;
    }

    public String getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }

    public String getModifiedtime() {
        return modifiedtime;
    }

    public void setModifiedtime(String modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    public int getModifieddate() {
        return modifieddate;
    }

    public void setModifieddate(int modifieddate) {
        this.modifieddate = modifieddate;
    }

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }

    public int getRefdate() {
        return refdate;
    }

    public void setRefdate(int refdate) {
        this.refdate = refdate;
    }

    public String getCancelledstatus() {
        return cancelledstatus;
    }

    public void setCancelledstatus(String cancelledstatus) {
        this.cancelledstatus = cancelledstatus;
    }

    public String getCreditnotenopos() {
        return creditnotenopos;
    }

    public void setCreditnotenopos(String creditnotenopos) {
        this.creditnotenopos = creditnotenopos;
    }

    public String getReasonforcancellation() {
        return reasonforcancellation;
    }

    public void setReasonforcancellation(String reasonforcancellation) {
        this.reasonforcancellation = reasonforcancellation;
    }

    public double getTotalrecvdamount() {
        return totalrecvdamount;
    }

    public void setTotalrecvdamount(double totalrecvdamount) {
        this.totalrecvdamount = totalrecvdamount;
    }

    public String getSaletype() {
        return saletype;
    }

    public void setSaletype(String saletype) {
        this.saletype = saletype;
    }

    public String getReleasestatus() {
        return releasestatus;
    }

    public void setReleasestatus(String releasestatus) {
        this.releasestatus = releasestatus;
    }

    public String getCreditsalereference() {
        return creditsalereference;
    }

    public void setCreditsalereference(String creditsalereference) {
        this.creditsalereference = creditsalereference;
    }

    public boolean isFlagadv() {
        return flagadv;
    }

    public void setFlagadv(boolean flagadv) {
        this.flagadv = flagadv;
    }

    public boolean isFlagforcereleae() {
        return flagforcereleae;
    }

    public void setFlagforcereleae(boolean flagforcereleae) {
        this.flagforcereleae = flagforcereleae;
    }

    public int getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(int releasedate) {
        this.releasedate = releasedate;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getCancelOtp() {
        return cancelOtp;
    }

    public void setCancelOtp(String cancelOtp) {
        this.cancelOtp = cancelOtp;
    }
 
   
    
}
