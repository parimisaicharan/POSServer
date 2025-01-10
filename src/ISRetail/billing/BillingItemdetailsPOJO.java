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
 * This is Billing Line Item POJO representing the Billing Line Item
 * 
 * 
 * 
 */
package ISRetail.billing;

/**
 *
 * @author Rakesh This class is used to store billing item details
 */
public class BillingItemdetailsPOJO {

    private int lineitemno;
    private String foc;
    private String styleconsultant;
    private String materialcode;
    private String description;
    private int quantity;
    private double untiprice;
    private double amount;
    private String promotion;
    private String discountrefno;
    private double tax;
    private double totalAmount;
    private double netAmount;
    private String batchno;
    private int expirydate;
    private double taxablevalue;
    private String audioUniqueID;
    private String audioRPTA;
    private String audioLPTA;
    private String audioProvDiag;

    public String getStyleconsultant() {
        return styleconsultant;
    }

    public void setStyleconsultant(String styleconsultant) {
        this.styleconsultant = styleconsultant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUntiprice() {
        return untiprice;
    }

    public void setUntiprice(double untiprice) {
        this.untiprice = untiprice;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getFoc() {
        return foc;
    }

    public void setFoc(String foc) {
        this.foc = foc;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public int getLineitemno() {
        return lineitemno;
    }

    public void setLineitemno(int lineitemno) {
        this.lineitemno = lineitemno;
    }

    public String getMaterialcode() {
        return materialcode;
    }

    public void setMaterialcode(String materialcode) {
        this.materialcode = materialcode;
    }

    public int getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(int expirydate) {
        this.expirydate = expirydate;
    }

    public String getDiscountrefno() {
        return discountrefno;
    }

    public void setDiscountrefno(String discountrefno) {
        this.discountrefno = discountrefno;
    }

    public double getTaxablevalue() {
        return taxablevalue;
    }

    public void setTaxablevalue(double taxablevalue) {
        this.taxablevalue = taxablevalue;
    }

    public String getAudioUniqueID() {
        return audioUniqueID;
    }

    public void setAudioUniqueID(String AudioUniqueID) {
        this.audioUniqueID = AudioUniqueID;
    }

    public String getAudioRPTA() {
        return audioRPTA;
    }

    public void setAudioRPTA(String AudioRPTA) {
        this.audioRPTA = AudioRPTA;
    }

    public String getAudioLPTA() {
        return audioLPTA;
    }

    public void setAudioLPTA(String AudioLPTA) {
        this.audioLPTA = AudioLPTA;
    }

    public String getAudioProvDiag() {
        return audioProvDiag;
    }

    public void setAudioProvDiag(String AudioProvDiag) {
        this.audioProvDiag = AudioProvDiag;
    }//end by charan for audiology
}
