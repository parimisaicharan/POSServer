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
 * POJO Class for Condition Type Master POJO
 * 
 * 
 */
package ISRetail.article;


public class ConditionTypeMasterPOJO {
    
    private String condType;
    private String condTypeDescriptiion;
    private String posCondType;
    private double maxAmount;
    private String taxCode;
    private String baseTax;
    private String updateStatus;
    private String conditionCategory;
    private String region;
    private String country;
    public String getCondType() {
        return condType;
    }

    public void setCondType(String condType) {
        this.condType = condType;
    }

    public String getCondTypeDescriptiion() {
        return condTypeDescriptiion;
    }

    public void setCondTypeDescriptiion(String condTypeDescriptiion) {
        this.condTypeDescriptiion = condTypeDescriptiion;
    }

    public String getPosCondType() {
        return posCondType;
    }

    public void setPosCondType(String posCondType) {
        this.posCondType = posCondType;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getBaseTax() {
        return baseTax;
    }

    public void setBaseTax(String baseTax) {
        this.baseTax = baseTax;
    }

    public String getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(String updateStatus) {
        this.updateStatus = updateStatus;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getConditionCategory() {
        return conditionCategory;
    }

    public void setConditionCategory(String conditionCategory) {
        this.conditionCategory = conditionCategory;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    

}
