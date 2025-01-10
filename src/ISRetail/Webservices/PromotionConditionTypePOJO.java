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
 * Promotion ConditionTypePOJO class to hold the Condtion type data
 * 
 * 
 */
package ISRetail.Webservices;


public class PromotionConditionTypePOJO {
private String conditionType;
private String dummyConditionType;
private String conditionName;
private double value;
private String calculationType;
private double maxAmt;
private String offerMerch;
private String offerArticle;
private double offerValue;
private String offerCalType;
private int sellingQty;
private int offerQty;
private double offerMaxRate;
//private double SellingMaxRate;

private String promotionID;
private String freeGoodsCategory;

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    public String getDummyConditionType() {
        return dummyConditionType;
    }

    public void setDummyConditionType(String dummyConditionType) {
        this.dummyConditionType = dummyConditionType;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCalculationType() {
        return calculationType;
    }

    public void setCalculationType(String calculationType) {
        this.calculationType = calculationType;
    }

    public double getMaxAmt() {
        return maxAmt;
    }

    public void setMaxAmt(double maxAmt) {
        this.maxAmt = maxAmt;
    }

    public String getOfferMerch() {
        return offerMerch;
    }

    public void setOfferMerch(String offerMerch) {
        this.offerMerch = offerMerch;
    }

    public String getOfferArticle() {
        return offerArticle;
    }

    public void setOfferArticle(String offerArticle) {
        this.offerArticle = offerArticle;
    }

    public double getOfferValue() {
        return offerValue;
    }

    public void setOfferValue(double offerValue) {
        this.offerValue = offerValue;
    }

    public String getOfferCalType() {
        return offerCalType;
    }

    public void setOfferCalType(String offerCalType) {
        this.offerCalType = offerCalType;
    }

    public int getSellingQty() {
        return sellingQty;
    }

    public void setSellingQty(int sellingQty) {
        this.sellingQty = sellingQty;
    }

    public int getOfferQty() {
        return offerQty;
    }

    public void setOfferQty(int offerQty) {
        this.offerQty = offerQty;
    }

 
    public String getPromotionID() {
        return promotionID;
    }

    public void setPromotionID(String promotionID) {
        this.promotionID = promotionID;
    }

    public String getFreeGoodsCategory() {
        return freeGoodsCategory;
    }

    public void setFreeGoodsCategory(String freeGoodsCategory) {
        this.freeGoodsCategory = freeGoodsCategory;
    }

    public double getOfferMaxRate() {
        return offerMaxRate;
    }

    public void setOfferMaxRate(double offerMaxRate) {
        this.offerMaxRate = offerMaxRate;
    }

}
