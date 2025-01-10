/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.CashReceipt;

/**
 *
 * @author naveenn
 */
public class CashReceiptPOJO {

    private String storecode;
    private int fiscalyear;
    private String documentno;
    private int documentdate;
    private String datasheetno;
    private String refno;
    private String refdate;
    private double amount;
    private String createdby;
    private int createddate;
    private String createdtime;
    private int modifieddate;
    private String modifiedtime;
    private String cancelledstatus;
    private String modifiedby;
    private String CompanyCode;
private String cancelOTP;
    public String getCompanyCode() {
        return CompanyCode;
    }

    public void setCompanyCode(String CompanyCode) {
        this.CompanyCode = CompanyCode;
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

    public String getDatasheetno() {
        return datasheetno;
    }

    public void setDatasheetno(String datasheetno) {
        this.datasheetno = datasheetno;
    }

    public int getDocumentdate() {
        return documentdate;
    }

    public void setDocumentdate(int documentdate) {
        this.documentdate = documentdate;
    }

    public String getDocumentno() {
        return documentno;
    }

    public void setDocumentno(String documentno) {
        this.documentno = documentno;
    }

    public int getFiscalyear() {
        return fiscalyear;
    }

    public void setFiscalyear(int fiscalyear) {
        this.fiscalyear = fiscalyear;
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

    public String getRefdate() {
        return refdate;
    }

    public void setRefdate(String refdate) {
        this.refdate = refdate;
    }

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }

    public String getStorecode() {
        return storecode;
    }

    public void setStorecode(String storecode) {
        this.storecode = storecode;
    }

    /**
     * @return the canceled status
     */
    public String getCancelledstatus() {
        return cancelledstatus;
    }

    /**
     * @param cancelledstatus the canceled status to set
     */
    public void setCancelledstatus(String cancelledstatus) {
        this.cancelledstatus = cancelledstatus;
    }

    /**
     * @return the modifiedby
     */
    public String getModifiedby() {
        return modifiedby;
    }

    /**
     * @param modifiedby the modifiedby to set
     */
    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }

    public String getCancelOTP() {
        return cancelOTP;
    }

    public void setCancelOTP(String cancelOTP) {
        this.cancelOTP = cancelOTP;
    }
    
}
