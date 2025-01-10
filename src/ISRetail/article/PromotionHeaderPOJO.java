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
 * POJO Class for the Promotion Header to hold the Promotion Header Values
 * 
 * 
 * 
 */
package ISRetail.article;

public class PromotionHeaderPOJO {

    private String storecode;
    private String condtypeid;
    private String promotionid;
    private String description;
    private int fromdate;
    private int todate;
    private int opttype;
    private String sun;
    private String mon;
    private String tue;
    private String wed;
    private String thu;
    private String fri;
    private String sat;
    private String active;
    private int createddate;
    private String createdtime;
    private String createduser;
    private int modifieddate;
    private String modifiedtime;
    private String modifieduser;
    private String updatestatus;
    private String isComboOffer;
    private double promMaxValue;

    //Code Added on Jan 28th 2010 for promotion applicabitlity download in promotion header
    private String applicable_to;
    //Code Added By Mr. Thangaraj on 29.02.2016 for Promotion Approval

    private String approved;
    private String usage_limit;
    //Code Added By Mr. Thangaraj on 25.05.2017 for unique Promotion 
    private String unique_promo_ind;
    private String salereturn_ind;
    //End of Code Added By Mr. Thangaraj on 25.05.2017 for unique Promotion 
    private double maxInvoiceVal; //Added by Thangaraj for IGST on 20.1.2018
    private String customercode;//Added by Balachander V on 18.12.2018
    private String restrictLineItem;//Added by Balachander V on 10.10.2019 to validate promo applied line item count
    private String combineArticles;//Added by Balachander V on 10.10.2019 to validate promo combination for allowed Articles for the applied promotion
    private String eligibleNoOfDays;//Added by Balachander V on 10.10.2019 to get customer previous orders values
    private String customerItemAllowed;//Added by Balachander V on 14.01.2020 to validate customer item allowed in current order
    private String ucpValueCheck;//Added by Balachander V on 14.01.2020 to validate UCP based on Frame or lens or contact lens 
    private double promomaxcap;

    public String getStorecode() {
        return storecode;
    }

    public void setStorecode(String storecode) {
        this.storecode = storecode;
    }

    public String getCondtypeid() {
        return condtypeid;
    }

    public void setCondtypeid(String condtypeid) {
        this.condtypeid = condtypeid;
    }

    public String getPromotionid() {
        return promotionid;
    }

    public void setPromotionid(String promotionid) {
        this.promotionid = promotionid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFromdate() {
        return fromdate;
    }

    public void setFromdate(int fromdate) {
        this.fromdate = fromdate;
    }

    public int getTodate() {
        return todate;
    }

    public void setTodate(int todate) {
        this.todate = todate;
    }

    public int getOpttype() {
        return opttype;
    }

    public void setOpttype(int opttype) {
        this.opttype = opttype;
    }

    public String getSun() {
        return sun;
    }

    public void setSun(String sun) {
        this.sun = sun;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public String getTue() {
        return tue;
    }

    public void setTue(String tue) {
        this.tue = tue;
    }

    public String getWed() {
        return wed;
    }

    public void setWed(String wed) {
        this.wed = wed;
    }

    public String getThu() {
        return thu;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

    public String getFri() {
        return fri;
    }

    public void setFri(String fri) {
        this.fri = fri;
    }

    public String getSat() {
        return sat;
    }

    public void setSat(String sat) {
        this.sat = sat;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
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

    public String getCreateduser() {
        return createduser;
    }

    public void setCreateduser(String createduser) {
        this.createduser = createduser;
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

    public String getModifieduser() {
        return modifieduser;
    }

    public void setModifieduser(String modifieduser) {
        this.modifieduser = modifieduser;
    }

    public String getUpdatestatus() {
        return updatestatus;
    }

    public void setUpdatestatus(String updatestatus) {
        this.updatestatus = updatestatus;
    }

    public String getIsComboOffer() {
        return isComboOffer;
    }

    public void setIsComboOffer(String isComboOffer) {
        this.isComboOffer = isComboOffer;
    }

    public double getPromMaxValue() {
        return promMaxValue;
    }

    public void setPromMaxValue(double promMaxValue) {
        this.promMaxValue = promMaxValue;
    }

    public double getPromMaxCap() {
        return promomaxcap;
    }

    public void setPromMaxCap(double promomaxcap) {
        this.promomaxcap = promomaxcap;
    }

    public String getApplicable_to() {
        return applicable_to;
    }

    public void setApplicable_to(String applicable_to) {
        this.applicable_to = applicable_to;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getUsage_limit() {
        return usage_limit;
    }

    public void setUsage_limit(String usage_limit) {
        this.usage_limit = usage_limit;
    }

    public String getUnique_promo_ind() {
        return unique_promo_ind;
    }

    public void setUnique_promo_ind(String unique_promo_ind) {
        this.unique_promo_ind = unique_promo_ind;
    }

    public String getSalereturn_ind() {
        return salereturn_ind;
    }

    public void setSalereturn_ind(String salereturn_ind) {
        this.salereturn_ind = salereturn_ind;
    }

    public double getMaxInvoiceVal() {
        return maxInvoiceVal;
    }

    public void setMaxInvoiceVal(double maxInvoiceVal) {
        this.maxInvoiceVal = maxInvoiceVal;
    }
//Added by Balachander V on 18.12.2018 to show promotion for particular customer only

    public String getCustomercode() {
        return customercode;
    }

    public void setCustomercode(String customercode) {
        this.customercode = customercode;
    }
    //Ended by Balachander V on 18.12.2018 to show promotion for particular customer only

    public String getRestrictLineItem() {
        return restrictLineItem;
    }

    public void setRestrictLineItem(String restrictLineItem) {
        this.restrictLineItem = restrictLineItem;
    }

    public String getCombineArticles() {
        return combineArticles;
    }

    public void setCombineArticles(String combineArticles) {
        this.combineArticles = combineArticles;
    }

    public String getEligibleNoOfDays() {
        return eligibleNoOfDays;
    }

    public void setEligibleNoOfDays(String eligibleNoOfDays) {
        this.eligibleNoOfDays = eligibleNoOfDays;
    }

    public String getCustomerItemAllowed() {
        return customerItemAllowed;
    }

    public void setCustomerItemAllowed(String customerItemAllowed) {
        this.customerItemAllowed = customerItemAllowed;
    }

    public String getUcpValueCheck() {
        return ucpValueCheck;
    }

    public void setUcpValueCheck(String ucpValueCheck) {
        this.ucpValueCheck = ucpValueCheck;
    }

}
