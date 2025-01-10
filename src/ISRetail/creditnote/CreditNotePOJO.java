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
 * POJO Class for the Credit Note Entity Object
 * 
 * 
 */

package ISRetail.creditnote;

import java.util.Iterator;


public class CreditNotePOJO {

private String customercode;
private String customername;
    
private String storecode="";
private int fiscalyear=0;
private String creditnoteno="";
private int creditnotedate=0;
private String referencesapid="";
private double amount=0 ;
private String reftype="";
private String refno="";
private int refdate=0;
private String refsalesorderno="";
private int redeemedon=0;
private double redeemedamount=0;
private String status="";
private int expirydate=0;
private String createdby="";
private int createddate=0;
private String createdtime="";
private String modifiedby="";
private int modifieddate=0;
private String modifiedtime="";
private String AdvcancelStatus;

//Code added on May 26th 2010
private String category;

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

    public String getCreditnoteno() {
        return creditnoteno;
    }

    public void setCreditnoteno(String creditnoteno) {
        this.creditnoteno = creditnoteno;
    }

    public int getCreditnotedate() {
        return creditnotedate;
    }

    public void setCreditnotedate(int creditnotedate) {
        this.creditnotedate = creditnotedate;
    }

    public String getReferencesapid() {
        return referencesapid;
    }

    public void setReferencesapid(String referencesapid) {
        this.referencesapid = referencesapid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getReftype() {
        return reftype;
    }

    public void setReftype(String reftype) {
        this.reftype = reftype;
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

    public int getRedeemedon() {
        return redeemedon;
    }

    public void setRedeemedon(int redeemedon) {
        this.redeemedon = redeemedon;
    }

    public double getRedeemedamount() {
        return redeemedamount;
    }

    public void setRedeemedamount(double redeemedamount) {
        this.redeemedamount = redeemedamount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(int expirydate) {
        this.expirydate = expirydate;
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

    public String getCustomercode() {
        return customercode;
    }

    public void setCustomercode(String customercode) {
        this.customercode = customercode;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getRefsalesorderno() {
        return refsalesorderno;
    }

    public void setRefsalesorderno(String refsalesorderno) {
        this.refsalesorderno = refsalesorderno;
    }

    public String getAdvcancelStatus() {
        return AdvcancelStatus;
    }

    public void setAdvcancelStatus(String AdvcancelStatus) {
        this.AdvcancelStatus = AdvcancelStatus;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

 
    
    
}
