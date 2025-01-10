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
 * This is a POJO class for SaleOrder Billing Header
 * 
 * 
 * 
 */
package ISRetail.SalesOrderBilling;

import java.sql.Date;

public class SalesOrderBillingPOJO {

    private String salesorderno;
    private String customercode;
    private String customername;
    private String telephoneno;
    private String invoiceno;
    private String modeofpayment;
    private String styleconsult;
    private String creditreference;
    private double netbillamount;
    private String datasheetno;
    private String storecode;
    private int saleorderdate;
    private String saletype;
    //private String documentno;
    private String customerlastname;
    private int invoicedate;
    private String invoicecancellationno;
    private String saprefno;
    private int lineitemno;
    private String matrialcode;
    private double roundoff;
    private double excessamount;
    private String excessReftype;
    private String creditNoteNo;
    private String createdby;
    private int createddate;
    private String createdtime;
    private int fiscalyear;
    private String orderStatus;
    private int creditnoteexpirydate;
    //Code Added on 03-02-2010 to capture the free fields from table 
    private String freefield1;
    private String freefield2;
    private String freefield3;
    // added for ULP development in POS
    //private int freefield4;
    //private double freefield5;
    private String freefield4;
    private String freefield5;
    private String vistarefvalno;
    //Code added on July 7th 2010 for capturing NR credit note details
    private String nrcreditnoteno;
    private double nrexcessamt;
    //COde Added by Bala on 10.10.2017 for Comfort call date and time
    private int comfortdate;
    private String comforttime;
    private String empid;
    //Code Ended by Bala on 10.10.2017 for Comfort call date and time
    private String refname;
    private String refmobileno;
    private String refinvoiceno;
    private String zopperInsuranceId;

    public String getSalesorderno() {
        return salesorderno;
    }

    public void setSalesorderno(String salesorderno) {
        this.salesorderno = salesorderno;
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

    public String getTelephoneno() {
        return telephoneno;
    }

    public void setTelephoneno(String telephoneno) {
        this.telephoneno = telephoneno;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno;
    }

    public String getModeofpayment() {
        return modeofpayment;
    }

    public void setModeofpayment(String modeofpayment) {
        this.modeofpayment = modeofpayment;
    }

    public String getStyleconsult() {
        return styleconsult;
    }

    public void setStyleconsult(String styleconsult) {
        this.styleconsult = styleconsult;
    }

    public String getCreditreference() {
        return creditreference;
    }

    public void setCreditreference(String creditreference) {
        this.creditreference = creditreference;
    }

    public double getNetbillamount() {
        return netbillamount;
    }

    public void setNetbillamount(double netbillamount) {
        this.netbillamount = netbillamount;
    }

    public String getDatasheetno() {
        return datasheetno;
    }

    public void setDatasheetno(String datasheetno) {
        this.datasheetno = datasheetno;
    }

    public String getStorecode() {
        return storecode;
    }

    public void setStorecode(String storecode) {
        this.storecode = storecode;
    }

    public String getSaletype() {
        return saletype;
    }

    public void setSaletype(String saletype) {
        this.saletype = saletype;
    }

    public int getSaleorderdate() {
        return saleorderdate;
    }

    public void setSaleorderdate(int saleorderdate) {
        this.saleorderdate = saleorderdate;
    }

    public String getCustomerlastname() {
        return customerlastname;
    }

    public void setCustomerlastname(String customerlastname) {
        this.customerlastname = customerlastname;
    }

    public int getInvoicedate() {
        return invoicedate;
    }

    public void setInvoicedate(int invoicedate) {
        this.invoicedate = invoicedate;
    }

    public String getInvoicecancellationno() {
        return invoicecancellationno;
    }

    public void setInvoicecancellationno(String invoicecancellationno) {
        this.invoicecancellationno = invoicecancellationno;
    }

    public String getSaprefno() {
        return saprefno;
    }

    public void setSaprefno(String saprefno) {
        this.saprefno = saprefno;
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

    public double getRoundoff() {
        return roundoff;
    }

    public void setRoundoff(double roundoff) {
        this.roundoff = roundoff;
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

    public String getCreditNoteNo() {
        return creditNoteNo;
    }

    public void setCreditNoteNo(String creditNoteNo) {
        this.creditNoteNo = creditNoteNo;
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

    public int getFiscalyear() {
        return fiscalyear;
    }

    public void setFiscalyear(int fiscalyear) {
        this.fiscalyear = fiscalyear;
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
// added for ULP development in POS
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

    public String getNrcreditnoteno() {
        return nrcreditnoteno;
    }

    public void setVistaRefValNo(String vistarefvalno) {
        this.vistarefvalno = vistarefvalno;
    }

    public String getVistaRefValNo() {
        return vistarefvalno;
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

    public String getVistarefvalno() {
        return vistarefvalno;
    }

    public void setVistarefvalno(String vistarefvalno) {
        this.vistarefvalno = vistarefvalno;
    }

    public int getComfortdate() {
        return comfortdate;
    }

    public void setComfortdate(int comfortdate) {
        this.comfortdate = comfortdate;
    }

    public String getComforttime() {
        return comforttime;
    }

    public void setComforttime(String comforttime) {
        this.comforttime = comforttime;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
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

    public String getZopperInsuranceId() {
        return zopperInsuranceId;
    }

    public void setZopperInsuranceId(String zopperInsuranceId) {
        this.zopperInsuranceId = zopperInsuranceId;
    }

}
