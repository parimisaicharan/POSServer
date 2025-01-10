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
 * Condition Type POJO
 * 
 * 
 * 
 */

package ISRetail.Webservices;

public class ConditionTypePOJO implements Cloneable{
    private String conditiontype;
    private String dummyconditiontype;
    private double value;
    private String calculationtype;
    private double maxamt;
    private String conditionName;
    private boolean isCess;
    private String cessOnCondType;
    private double netAmount;
    private String promotionId;
    private String freeGoodsCategory;
    private int promotionRandomNo;
    private boolean isDamagedgoodsPromotion;
    private String conditionCategory;
    private int offerQtyAllowed;
    private String promotionLineno;
    //Added by Dileep - 16.06.2014
    private String ulpno;
    private double loyaltyPoints;
    private int asOnDate;
    private double loyaltyRedeemedPoints;
    private String rrno;
    //End: Added by Dileep - 16.06.2014

    @Override
  public Object clone()throws CloneNotSupportedException{
       return super.clone();
       
   }

    public String getConditiontype() {
        return conditiontype;
    }

    public void setConditiontype(String conditiontype) {
        this.conditiontype = conditiontype;
    }

    public String getDummyconditiontype() {
        return dummyconditiontype;
    }

    public void setDummyconditiontype(String dummyconditiontype) {
        this.dummyconditiontype = dummyconditiontype;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCalculationtype() {
        return calculationtype;
    }

    public void setCalculationtype(String calculationtype) {
        this.calculationtype = calculationtype;
    }

    public double getMaxamt() {
        return maxamt;
    }

    public void setMaxamt(double maxamt) {
        this.maxamt = maxamt;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    public boolean isIsCess() {
        return isCess;
    }

    public void setIsCess(boolean isCess) {
        this.isCess = isCess;
    }

    public String getCessOnCondType() {
        return cessOnCondType;
    }

    public void setCessOnCondType(String cessOnCondType) {
        this.cessOnCondType = cessOnCondType;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getFreeGoodsCategory() {
        return freeGoodsCategory;
    }

    public void setFreeGoodsCategory(String freeGoodsCategory) {
        this.freeGoodsCategory = freeGoodsCategory;
    }

    public int getPromotionRandomNo() {
        return promotionRandomNo;
    }

    public void setPromotionRandomNo(int promotionRandomNo) {
        this.promotionRandomNo = promotionRandomNo;
    }

    public boolean isIsDamagedgoodsPromotion() {
        return isDamagedgoodsPromotion;
    }

    public void setIsDamagedPromotion(boolean isDamagedPromotion) {
        this.isDamagedgoodsPromotion = isDamagedPromotion;
    }

    public String getConditionCategory() {
        return conditionCategory;
    }

    public void setConditionCategory(String conditionCategory) {
        this.conditionCategory = conditionCategory;
    }

    public int getOfferQtyAllowed() {
        return offerQtyAllowed;
    }

    public void setOfferQtyAllowed(int offerQtyAllowed) {
        this.offerQtyAllowed = offerQtyAllowed;
    }

    public String getPromotionLineno() {
        return promotionLineno;
    }

    public void setPromotionLineno(String promotionLineno) {
        this.promotionLineno = promotionLineno;
    }
     //Added by Dileep - 16.06.2014
    public int getAsOnDate() {
        return asOnDate;
    }

    public void setAsOnDate(int asOnDate) {
        this.asOnDate = asOnDate;
    }

    public double getLoyaltyRedeemedPoints() {
        return loyaltyRedeemedPoints;
    }

    public void setLoyaltyRedeemedPoints(double loyaltyRedeemedPoints) {
        this.loyaltyRedeemedPoints = loyaltyRedeemedPoints;
    }

    public String getRrno() {
        return rrno;
    }

    public void setRrno(String rrno) {
        this.rrno = rrno;
    }

    public String getUlpno() {
        return ulpno;
    }

    public void setUlpno(String ulpno) {
        this.ulpno = ulpno;
    }

    public double getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(double loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }
    //End: Added by Dileep - 16.06.2014

}
