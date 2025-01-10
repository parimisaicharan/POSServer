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
 * POJO for SiteMaster
 * 
 * 
 * 
 */
package ISRetail.quotations;

import ISRetail.salesorder.*;

public class QuotationsHeaderPOJO {    

private String storeCode;
private int fiscalYear;
private  String  quotationno;
private  int  quotationdate;
private  String  customercode;
private  String  datasheetno;
private int validito;
private  String  printordertype;
private String division;
private  double  amount;
private String orderStatus;
private String createdBy;
private int createdDate;
private String createdTime;
private String modifiedBy;
private int modifiedDate;
private String modifiedTime;
private String reasonforcancellation;
private String reasonforcancellationDesc;
private String cancelledby;
private String cancelledTime;
private int saleordercancellationdate;
private String parameterId;
  
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

   //    public String getPrintoption() {
//        return printoption;
//    }
//
//    public void setPrintoption(String printoption) {
//        this.printoption = printoption;
//    }

   
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
   
   

    public String getPrintordertype() {
        return printordertype;
    }

    public void setPrintordertype(String printordertype) {
        this.printordertype = printordertype;
    }


    public String getOrderStatus() {
        return orderStatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public int getCreatedDate() {
        return createdDate;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public int getModifiedDate() {
        return modifiedDate;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public int getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(int fiscalYear) {
        this.fiscalYear = fiscalYear;
    }


    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedDate(int createdDate) {
        this.createdDate = createdDate;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public void setModifiedDate(int modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }


    public String getReasonforcancellation() {
        return reasonforcancellation;
    }

    public void setReasonforcancellation(String reasonforcancellation) {
        this.reasonforcancellation = reasonforcancellation;
    }

    public String getCancelledby() {
        return cancelledby;
    }

    public void setCancelledby(String cancelledby) {
        this.cancelledby = cancelledby;
    }

    public String getCancelledTime() {
        return cancelledTime;
    }

    public void setCancelledTime(String cancelledTime) {
        this.cancelledTime = cancelledTime;
    }

    public int getSaleordercancellationdate() {
        return saleordercancellationdate;
    }

    public void setSaleordercancellationdate(int saleordercancellationdate) {
        this.saleordercancellationdate = saleordercancellationdate;
    }

    public String getParameterId() {
        return parameterId;
    }

    public void setParameterId(String parameterId) {
        this.parameterId = parameterId;
    }

    public String getReasonforcancellationDesc() {
        return reasonforcancellationDesc;
    }

    public void setReasonforcancellationDesc(String reasonforcancellationDesc) {
        this.reasonforcancellationDesc = reasonforcancellationDesc;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getQuotationno() {
        return quotationno;
    }

    public void setQuotationno(String quotationno) {
        this.quotationno = quotationno;
    }

    public int getQuotationdate() {
        return quotationdate;
    }

    public void setQuotationdate(int quotationdate) {
        this.quotationdate = quotationdate;
    }

    public int getValidito() {
        return validito;
    }

    public void setValidito(int validito) {
        this.validito = validito;
    }


}
