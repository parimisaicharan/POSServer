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
 * This is Billing Header POJO representing the Billing Header Table
 * 
 * 
 * 
 */
package ISRetail.billing;

public class BillingHeaderPOJO {

    private String storecode;
    private int fiscalyear;
    private String invoiceno;
    private int invoicedate;
    private String refno;
    private String customercode;
    private double invoicevalue;
    private String firstprint;
    private double roundoff;
    private String billedfrom;
    private String saprefno;
    private String cancelstatus;
    private String reasonforcancellation;
    private String createdby;
    private int createddate;
    private String createdtime;
    private String modifiedby;
    private int modifieddate;
    private String modifiedtime;
    private String saleorderno;
    private String customerfirstname;
    private String customerlastname;
    private String datasheetno;
    private String telephoneno;
    private int saleorderdate;
    private String saletype;
    private String creditsalereference;
    private String styleconsultant;
    private double amount;
    private String invoicecancelno;
    private String accsaprefno;
    private int lineitemno;
    private String matrialcode;
    private double excessamount;
    private String excessReftype;
    private String creditNoteno;
    private String orderStatus;
    private int creditnoteexpirydate;
//Code Added on 29-01-2010 to capture the free fields from table 
    private String freefield1;
    private String freefield2;
    private String freefield3;
//private int freefield4;
//private double freefield5;
//changed for ulp development in POS
    private String freefield4;
    private String freefield5;
    private String vistarefvalno;
    //Code added on July 7th 2010 for capturing NR credit note details
    private String nrcreditnoteno;
    private double nrexcessamt;
    // Code Added by BALA for  empid on 10.10.2017
    private String empid;
    // Code Added by BALA for  empid on 10.10.2017
    private String refname;
    private String refmobileno;
    private String refinvoiceno;
    private String ReferencePromoOrder;
    private String cancelOtp;
    private String zopperInsuraceId;
    private String custname;
    private String mobileno;
    private String gstn_no;

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

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno;
    }

    public int getInvoicedate() {
        return invoicedate;
    }

    public void setInvoicedate(int invoicedate) {
        this.invoicedate = invoicedate;
    }

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }

    public String getCustomercode() {
        return customercode;
    }

    public void setCustomercode(String customercode) {
        this.customercode = customercode;
    }

    public double getInvoicevalue() {
        return invoicevalue;
    }

    public void setInvoicevalue(double invoicevalue) {
        this.invoicevalue = invoicevalue;
    }

    public String getFirstprint() {
        return firstprint;
    }

    public void setFirstprint(String firstprint) {
        this.firstprint = firstprint;
    }

    public double getRoundoff() {
        return roundoff;
    }

    public void setRoundoff(double roundoff) {
        this.roundoff = roundoff;
    }

    public String getBilledfrom() {
        return billedfrom;
    }

    public void setBilledfrom(String billedfrom) {
        this.billedfrom = billedfrom;
    }

    public String getSaprefno() {
        return saprefno;
    }

    public void setSaprefno(String saprefno) {
        this.saprefno = saprefno;
    }

    public String getCancelstatus() {
        return cancelstatus;
    }

    public void setCancelstatus(String cancelstatus) {
        this.cancelstatus = cancelstatus;
    }

    public String getReasonforcancellation() {
        return reasonforcancellation;
    }

    public void setReasonforcancellation(String reasonforcancellation) {
        this.reasonforcancellation = reasonforcancellation;
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

    public String getSaleorderno() {
        return saleorderno;
    }

    public void setSaleorderno(String saleorderno) {
        this.saleorderno = saleorderno;
    }

    public String getCustomerfirstname() {
        return customerfirstname;
    }

    public void setCustomerfirstname(String customerfirstname) {
        this.customerfirstname = customerfirstname;
    }

    public String getCustomerlastname() {
        return customerlastname;
    }

    public void setCustomerlastname(String customerlastname) {
        this.customerlastname = customerlastname;
    }

    public String getDatasheetno() {
        return datasheetno;
    }

    public void setDatasheetno(String datasheetno) {
        this.datasheetno = datasheetno;
    }

    public String getTelephoneno() {
        return telephoneno;
    }

    public void setTelephoneno(String telephoneno) {
        this.telephoneno = telephoneno;
    }

    public int getSaleorderdate() {
        return saleorderdate;
    }

    public void setSaleorderdate(int saleorderdate) {
        this.saleorderdate = saleorderdate;
    }

    public String getSaletype() {
        return saletype;
    }

    public void setSaletype(String saletype) {
        this.saletype = saletype;
    }

    public String getCreditsalereference() {
        return creditsalereference;
    }

    public void setCreditsalereference(String creditsalereference) {
        this.creditsalereference = creditsalereference;
    }

    public String getStyleconsultant() {
        return styleconsultant;
    }

    public void setStyleconsultant(String styleconsultant) {
        this.styleconsultant = styleconsultant;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getInvoicecancelno() {
        return invoicecancelno;
    }

    public void setInvoicecancelno(String invoicecancelno) {
        this.invoicecancelno = invoicecancelno;
    }

    public String getAccsaprefno() {
        return accsaprefno;
    }

    public void setAccsaprefno(String accsaprefno) {
        this.accsaprefno = accsaprefno;
    }

    public int getLineitemno() {
        return lineitemno;
    }

    public void setLineitemno(int lineitemno) {
        this.lineitemno = lineitemno;
    }

    public String getMatrialcode() {
        return matrialcode;
    }

    public void setMatrialcode(String matrialcode) {
        this.matrialcode = matrialcode;
    }

    public double getExcessamount() {
        return excessamount;
    }

    public void setExcessamount(double excessamount) {
        this.excessamount = excessamount;
    }

    public String getExcessReftype() {
        return excessReftype;
    }

    public void setExcessReftype(String excessReftype) {
        this.excessReftype = excessReftype;
    }

    public String getCreditNoteno() {
        return creditNoteno;
    }

    public void setCreditNoteno(String creditNoteno) {
        this.creditNoteno = creditNoteno;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getCreditnoteexpirydate() {
        return creditnoteexpirydate;
    }

    public void setCreditnoteexpirydate(int creditnoteexpirydate) {
        this.creditnoteexpirydate = creditnoteexpirydate;
    }

    public String getFreefield1() {
        return freefield1;
    }

    public void setFreefield1(String freefield1) {
        this.freefield1 = freefield1;
    }

    public String getFreefield2() {
        return freefield2;
    }

    public void setFreefield2(String freefield2) {
        this.freefield2 = freefield2;
    }

    public String getFreefield3() {
        return freefield3;
    }

    public void setFreefield3(String freefield3) {
        this.freefield3 = freefield3;
    }
// Changed for ULP development in POS
//    public int getFreefield4() {
//        return freefield4;
//    }
//
//    public void setFreefield4(int freefield4) {
//        this.freefield4 = freefield4;
//    }
//
//    public double getFreefield5() {
//        return freefield5;
//    }
//
//    public void setFreefield5(double freefield5) {
//        this.freefield5 = freefield5;
//    }

    public String getFreefield4() {
        return freefield4;
    }

    public void setFreefield4(String freefield4) {
        this.freefield4 = freefield4;
    }

    public String getFreefield5() {
        return freefield5;
    }

    public void setFreefield5(String freefield5) {
        this.freefield5 = freefield5;
    }

    public void setVistaRefValNo(String vistarefvalno) {
        this.vistarefvalno = vistarefvalno;
    }

    public String getVistaRefValNo() {
        return vistarefvalno;
    }

    public String getNrcreditnoteno() {
        return nrcreditnoteno;
    }

    public void setNrcreditnoteno(String nrcreditnoteno) {
        this.nrcreditnoteno = nrcreditnoteno;
    }

    public double getNrexcessamt() {
        return nrexcessamt;
    }

    public void setNrexcessamt(double nrexcessamt) {
        this.nrexcessamt = nrexcessamt;
    }
    // Code Added by BALA for  empid on 10.10.2017

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }
    // Code Ended by BALA for  empid on 10.10.2017

    public String getVistarefvalno() {
        return vistarefvalno;
    }

    public void setVistarefvalno(String vistarefvalno) {
        this.vistarefvalno = vistarefvalno;
    }

    public String getRefname() {
        return refname;
    }

    public void setRefname(String refname) {
        this.refname = refname;
    }

    public String getRefmobileno() {
        return refmobileno;
    }

    public void setRefmobileno(String refmobileno) {
        this.refmobileno = refmobileno;
    }

    public String getRefinvoiceno() {
        return refinvoiceno;
    }

    public void setRefinvoiceno(String refinvoiceno) {
        this.refinvoiceno = refinvoiceno;
    }

    public String getReferencePromoOrder() {
        return ReferencePromoOrder;
    }

    public void setReferencePromoOrder(String ReferencePromoOrder) {
        this.ReferencePromoOrder = ReferencePromoOrder;
    }

    public String getCancelOtp() {
        return cancelOtp;
    }

    public void setCancelOtp(String cancelOtp) {
        this.cancelOtp = cancelOtp;
    }

    public String getZopperInsuraceId() {
        return zopperInsuraceId;
    }

    public void setZopperInsuraceId(String zopperInsuraceId) {
        this.zopperInsuraceId = zopperInsuraceId;
    }

    public String getcustname() {//added by surekha k
        return custname;
    }

    public void setcustname(String custname) {
        this.custname = custname;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getGst_no() {
        return gstn_no;
    }

    public void setGst_no(String gstn_no) {
        this.gstn_no = gstn_no;
    }
}
