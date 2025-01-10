/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ISRetail.GiftCardSelling;

/**
 *
 * @author Administrator
 */
public class GiftCardSellingPOJO {

    private String storecode;
    private int fiscalyear;
    private String giftcardno;
    private int giftcarddate;
    private String customercode;
    private double amount;
    private String cancelledstatus;
    private String createdby;
    private int createddate;
    private String createdtime;
    private String modifiedby;
    private int modifieddate;
    private String modifiedtime;
    private String refno;
    private String saprefo;
    private String accsaprefno;
    private String gcdocumentno;
    private String authorizationcode;

    //code added by dileep - 20.09.2013
    private String transactiontype;
    private String batchid;
    private String transactionid;
    private String giftcardinvoiceno;
    private int giftcardexpirydate;

    //End code added by dileep - 20.09.2013
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

    public String getGiftcardno() {
        return giftcardno;
    }

    public void setGiftcardno(String giftcardno) {
        this.giftcardno = giftcardno;
    }

    public int getGiftcarddate() {
        return giftcarddate;
    }

    public void setGiftcarddate(int giftcarddate) {
        this.giftcarddate = giftcarddate;
    }

    public String getCustomercode() {
        return customercode;
    }

    public void setCustomercode(String customercode) {
        this.customercode = customercode;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public String getCancelledstatus() {
        return cancelledstatus;
    }

    public void setCancelledstatus(String cancelledstatus) {
        this.cancelledstatus = cancelledstatus;
    }

    public String getGcdocumentno() {
        return gcdocumentno;
    }

    public void setGcdocumentno(String gcdocumentno) {
        this.gcdocumentno = gcdocumentno;
    }

    public String getAuthorizationcode() {
        return authorizationcode;
    }

    public void setAuthorizationcode(String authorizationcode) {
        this.authorizationcode = authorizationcode;
    }

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }

    public String getSaprefo() {
        return saprefo;
    }

    public void setSaprefo(String saprefo) {
        this.saprefo = saprefo;
    }

    public String getAccsaprefno() {
        return accsaprefno;
    }

    public void setAccsaprefno(String accsaprefno) {
        this.accsaprefno = accsaprefno;
    }

    public String getTransactiontype() {
        return transactiontype;
    }

    public void setTransactiontype(String transactiontype) {
        this.transactiontype = transactiontype;
    }

    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public String getGiftcardinvoiceno() {
        return giftcardinvoiceno;
    }

    public void setGiftcardinvoiceno(String giftcardinvoiceno) {
        this.giftcardinvoiceno = giftcardinvoiceno;
    }

    public int getGiftcardexpirydate() {
        return giftcardexpirydate;
    }

    public void setGiftcardexpirydate(int giftcardexpirydate) {
        this.giftcardexpirydate = giftcardexpirydate;
    }

}
