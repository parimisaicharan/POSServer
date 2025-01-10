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
 * POJO
 * 
 */

package ISRetail.salesreturns;

public class SalesReturnHeaderPOJO {
private String  storecode;
private int  fiscalyear;
private String  salereturnno;
private int  salereturndate;
private String  refacknumber;
private int  refackdate;
private String  createdby;
private int  createddate;
private String  createdtime;
private String  modifiedby;
private int  modifieddate;
private String  modifiedtime;
private String selectedstore;
private String cancelOtp;
private String sfccomplaintid;
        
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

    public String getSalereturnno() {
        return salereturnno;
    }

    public void setSalereturnno(String salereturnno) {
        this.salereturnno = salereturnno;
    }

    public int getSalereturndate() {
        return salereturndate;
    }

    public void setSalereturndate(int salereturndate) {
        this.salereturndate = salereturndate;
    }

    public String getRefacknumber() {
        return refacknumber;
    }

    public void setRefacknumber(String refacknumber) {
        this.refacknumber = refacknumber;
    }

    public int getRefackdate() {
        return refackdate;
    }

    public void setRefackdate(int refackdate) {
        this.refackdate = refackdate;
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

    public int getModifieddate() {
        return modifieddate;
    }

    public void setModifieddate(int modifieddate) {
        this.modifieddate = modifieddate;
    }

    public String getModifiedtime() {
        return modifiedtime;
    }

    public void setModifiedtime(String modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    public String getSelectedstore() {
        return selectedstore;
    }

    public void setSelectedstore(String selectedstore) {
        this.selectedstore = selectedstore;
    }

    public String getCancelOtp() {
        return cancelOtp;
    }

    public void setCancelOtp(String cancelOtp) {
        this.cancelOtp = cancelOtp;
    }
    public String getSfcComplaintID() {//VERSION 100
        return sfccomplaintid;
    }

    public void setSfcComplaintID(String sfccomplaintid) {
        this.sfccomplaintid = sfccomplaintid;
    }
  
}
