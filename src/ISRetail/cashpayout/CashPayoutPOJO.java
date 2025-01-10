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
 * This is Cash Payout Header POJO representing the CashPayout Entity
 * 
 * 
 * 
 */
package ISRetail.cashpayout;

import java.awt.font.NumericShaper;
import java.sql.Date;

public class CashPayoutPOJO {
    private String cashpayoutno="";
    private String storecode="";
    private int fiscalyear=0;
    private int dateofpayout=0;
    private String customercode="";
    private String refcreditnotenumber="";
    private String sapreferenceid="";
    private String reasonforpayout="";
    private double netamount=0;
    private String paymentmode="";
    private String createdby="";
    private int createddate=0;
    private String createdtime="";
    private String modifiedby=""; 
    private int modifieddate=0;
    private String modifiedtime="";
    private String saleorderno="";
    private int refcreditnotedate=0;//for printing purpose    
    private String customername="";//for search table purpose
    private String cancelOtp;
    public double getNetamount(){
        return netamount;
    }
    
    public void setNetamount(double netamount){
        this.netamount=netamount;
    }
    
    
    
    public String getCashpayoutno() {
        return cashpayoutno;
    }

    public void setCashpayoutno(String cashpayoutno) {
        this.cashpayoutno = cashpayoutno;
    }

    public String getStorecode() {
        return storecode;
    }

    public void setStorecode(String storecode) {
        this.storecode = storecode;
    }

    public int getDateofpayout() {
        return dateofpayout;
    }

    public void setDateofpayout(int dateofpayout) {
        this.dateofpayout = dateofpayout;
    }

    public String getRefcreditnotenumber() {
        return refcreditnotenumber;
    }

    public void setRefcreditnotenumber(String refcreditnotenumber) {
        this.refcreditnotenumber = refcreditnotenumber;
    }

    public String getCustomercode() {
        return customercode;
    }

    public void setCustomercode(String customercode) {
        this.customercode = customercode;
    }

   
    public String getReasonforPayout(){
           return  reasonforpayout;    
    }
    
    
    public void setReasonforpayout(String reasonforpayout){
        this.reasonforpayout=reasonforpayout;
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

    public String getSapreferenceid() {
        return sapreferenceid;
    }

    public void setSapreferenceid(String sapreferenceid) {
        this.sapreferenceid = sapreferenceid;
    }

    public String getPaymentmode() {
        return paymentmode;
    }

    public void setPaymentmode(String paymentmode) {
        this.paymentmode = paymentmode;
    }

    public int getFiscalyear() {
        return fiscalyear;
    }

    public void setFiscalyear(int fiscalyear) {
        this.fiscalyear = fiscalyear;
    }

    public String getSaleorderno() {
        return saleorderno;
    }

    public void setSaleorderno(String saleorderno) {
        this.saleorderno = saleorderno;
    }

    public int getRefcreditnotedate() {
        return refcreditnotedate;
    }

    public void setRefcreditnotedate(int refcreditnotedate) {
        this.refcreditnotedate = refcreditnotedate;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getCancelOtp() {
        return cancelOtp;
    }

    public void setCancelOtp(String cancelOtp) {
        this.cancelOtp = cancelOtp;
    }

}
