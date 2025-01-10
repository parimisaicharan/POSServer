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
 * This is Quotation Line Item POJO representing the Quotation Line Item Table
 * 
 * 
 * 
 */
package ISRetail.Quotation;

public class QuotationDetailsPOJO {
    
    
private String storeCode;
private int fiscalYear ;
private String quotationNo;
private int lineitemNo;
private int quotationDate ;
private String materialCode;
private int Qty; 
private double netAmount;
private double taxableValue;

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

    public String getQuotationNo() {
        return quotationNo;
    }

    public void setQuotationNo(String quotationNo) {
        this.quotationNo = quotationNo;
    }

    public int getLineitemNo() {
        return lineitemNo;
    }

    public void setLineitemNo(int lineitemNo) {
        this.lineitemNo = lineitemNo;
    }

    public int getQuotationDate() {
        return quotationDate;
    }

    public void setQuotationDate(int quotationDate) {
        this.quotationDate = quotationDate;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int Qty) {
        this.Qty = Qty;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public double getTaxableValue() {
        return taxableValue;
    }

    public void setTaxableValue(double taxableValue) {
        this.taxableValue = taxableValue;
    }

   

}
