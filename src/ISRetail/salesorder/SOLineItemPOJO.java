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
 * POJO
 * 
 */
package ISRetail.salesorder;

import ISRetail.Webservices.*;
import ISRetail.salesreturns.AckDefectReasonPOJO;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class SOLineItemPOJO {

    private String site;
    private String region;
    private String material;
    private String materialDescription;
    private String articleType;
    private String mdseCategory;
    private String uom;
    private String batchIndicator;
    private String minShelfLife;
    private String warrantyPeriod;
    private String division;
    private ConditionTypePOJO UCP;
    private ArrayList<ConditionTypePOJO> discountTypes;
    private ArrayList<ConditionTypePOJO> taxDetails;
    private ArrayList<PromotionConditionTypePOJO> promotionTypes;
    private ConditionTypePOJO discountSelected;
    private PromotionConditionTypePOJO promotionSelected;
    private int quantity;
    private String foc = "";
    private String customerItem;
    private String batchId;
    private String styleConsultant;
    private String eyeSide;
    private String typeOfLens;
    private String modelNo;
    private String brandColor;
    private String approxValue;
    private String anyVisibleDefect;
    private String comments;
    private String expDate;
    private String discountRefNo;
    private String productDetail;
    private String natureOfService;
    private boolean offerItem = false;
    private ConditionTypePOJO offerPromotion;
    private double taxableValue;
    private double netamount;
    private int plannedDelTime;
    private int returnedqty;
    private double returnednetamount;
    private String returnreason;
    private String otherretreason;
    private ArrayList<AckDefectReasonPOJO> ackdefectreasonpojos;
    private ArrayList<ConditionTypePOJO> otherCharges;
    private boolean otherChargesPresent;
    private String storageLoc;
    private ConditionTypePOJO ULPdiscountSelected;//ULP Discount
    // Code Added by BALA for  empid on 10.10.2017
    private String empid;
    // Code Added by BALA for  empid on 10.10.2017
    private String frombatch;
    private String lineItemEmpid;
    private String frameColor;
    private String lensTech;
    private String lensColor;
    private String gradient;
    private String cancelOtp;
    private String audioUniqueID;
    private String audioRPTA;
    private String audioLPTA;
    private String audioProvDiag;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getMdseCategory() {
        return mdseCategory;
    }

    public void setMdseCategory(String mdseCategory) {
        this.mdseCategory = mdseCategory;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getBatchIndicator() {
        return batchIndicator;
    }

    public void setBatchIndicator(String batchIndicator) {
        this.batchIndicator = batchIndicator;
    }

    public String getMinShelfLife() {
        return minShelfLife;
    }

    public void setMinShelfLife(String minShelfLife) {
        this.minShelfLife = minShelfLife;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public ConditionTypePOJO getUCP() {
        return UCP;
    }

    public void setUCP(ConditionTypePOJO UCP) {
        this.UCP = UCP;
    }

    public ArrayList<ConditionTypePOJO> getDiscountTypes() {
        return discountTypes;
    }

    public void setDiscountTypes(ArrayList<ConditionTypePOJO> discountTypes) {
        this.discountTypes = discountTypes;
    }

    public ArrayList<ConditionTypePOJO> getTaxDetails() {
        return taxDetails;
    }

    public void setTaxDetails(ArrayList<ConditionTypePOJO> taxDetails) {
        this.taxDetails = taxDetails;
    }

    public ConditionTypePOJO getDiscountSelected() {
        return discountSelected;
    }

    public void setDiscountSelected(ConditionTypePOJO discountSelected) {
        this.discountSelected = discountSelected;
    }

    public PromotionConditionTypePOJO getPromotionSelected() {
        return promotionSelected;
    }

    public void setPromotionSelected(PromotionConditionTypePOJO promotionSelected) {
        this.promotionSelected = promotionSelected;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFoc() {
        return foc;
    }

    public void setFoc(String foc) {
        this.foc = foc;
    }

    public String isCustomerItem() {
        return getCustomerItem();
    }

    public void setCustomerItem(String customerItem) {
        this.customerItem = customerItem;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getStyleConsultant() {
        return styleConsultant;
    }

    public void setStyleConsultant(String styleConsultant) {
        this.styleConsultant = styleConsultant;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getBrandColor() {
        return brandColor;
    }

    public void setBrandColor(String brandColor) {
        this.brandColor = brandColor;
    }

    public String getApproxValue() {
        return approxValue;
    }

    public void setApproxValue(String approxValue) {
        this.approxValue = approxValue;
    }

    public String getAnyVisibleDefect() {
        return anyVisibleDefect;
    }

    public void setAnyVisibleDefect(String anyVisibleDefect) {
        this.anyVisibleDefect = anyVisibleDefect;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public double getTaxableValue() {
        return taxableValue;
    }

    public void setTaxableValue(double taxableValue) {
        this.taxableValue = taxableValue;
    }

    public String getDiscountRefNo() {
        return discountRefNo;
    }

    public void setDiscountRefNo(String discountRefNo) {
        this.discountRefNo = discountRefNo;
    }

    public String getTypeOfLens() {
        return typeOfLens;
    }

    public void setTypeOfLens(String typeOfLens) {
        this.typeOfLens = typeOfLens;
    }

    public String getEyeSide() {
        return eyeSide;
    }

    public void setEyeSide(String eyeSide) {
        this.eyeSide = eyeSide;
    }

    public String getCustomerItem() {
        return customerItem;
    }

    public ArrayList<PromotionConditionTypePOJO> getPromotionTypes() {
        return promotionTypes;
    }

    public void setPromotionTypes(ArrayList<PromotionConditionTypePOJO> promotionTypes) {
        this.promotionTypes = promotionTypes;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public String getNatureOfService() {
        return natureOfService;
    }

    public void setNatureOfService(String natureOfService) {
        this.natureOfService = natureOfService;
    }

    public boolean isOfferItem() {
        return offerItem;
    }

    public void setOfferItem(boolean offerItem) {
        this.offerItem = offerItem;
    }

    public ConditionTypePOJO getOfferPromotion() {
        return offerPromotion;
    }

    public void setOfferPromotion(ConditionTypePOJO offerPromotion) {
        this.offerPromotion = offerPromotion;
    }

    public double getNetamount() {
        return netamount;
    }

    public void setNetamount(double netamount) {
        this.netamount = netamount;
    }

    public int getReturnedqty() {
        return returnedqty;
    }

    public void setReturnedqty(int returnedqty) {
        this.returnedqty = returnedqty;
    }

    public double getReturnednetamount() {
        return returnednetamount;
    }

    public void setReturnednetamount(double returnednetamount) {
        this.returnednetamount = returnednetamount;
    }

    public String getReturnreason() {
        return returnreason;
    }

    public void setReturnreason(String returnreason) {
        this.returnreason = returnreason;
    }

    public ArrayList<AckDefectReasonPOJO> getAckdefectreasonpojos() {
        return ackdefectreasonpojos;
    }

    public void setAckdefectreasonpojos(ArrayList<AckDefectReasonPOJO> ackdefectreasonpojos) {
        this.ackdefectreasonpojos = ackdefectreasonpojos;
    }

    public int getPlannedDelTime() {
        return plannedDelTime;
    }

    public void setPlannedDelTime(int plannedDelTime) {
        this.plannedDelTime = plannedDelTime;
    }

    public ArrayList<ConditionTypePOJO> getOtherCharges() {
        return otherCharges;
    }

    public void setOtherCharges(ArrayList<ConditionTypePOJO> otherCharges) {
        this.otherCharges = otherCharges;
    }

    public boolean isOtherChargesPresent() {
        return otherChargesPresent;
    }

    public void setOtherChargesPresent(boolean otherChargesPresent) {
        this.otherChargesPresent = otherChargesPresent;
    }

    public String getStorageLoc() {
        return storageLoc;
    }

    public void setStorageLoc(String storageLoc) {
        this.storageLoc = storageLoc;
    }

    public String getOtherretreason() {
        return otherretreason;
    }

    public void setOtherretreason(String otherretreason) {
        this.otherretreason = otherretreason;
    }

    //Added by Dileep - 16.06.2014
    public ConditionTypePOJO getULPdiscountSelected() {
        return ULPdiscountSelected;
    }

    public void setULPdiscountSelected(ConditionTypePOJO ULPdiscountSelected) {
        this.ULPdiscountSelected = ULPdiscountSelected;
    }

    //End: Added by Dileep - 16.06.2014
//    public String getStoreCode() {
//        return storeCode;
//    }
//
//    public void setStoreCode(String storeCode) {
//        this.storeCode = storeCode;
//    }
//
//    public int getFiscalYear() {
//        return fiscalYear;
//    }
//
//    public void setFiscalYear(int fiscalYear) {
//        this.fiscalYear = fiscalYear;
//    }
//
//    public String getSaleorderno() {
//        return saleorderno;
//    }
//
//    public void setSaleorderno(String saleorderno) {
//        this.saleorderno = saleorderno;
//    }
//
//    public int getSno() {
//        return Sno;
//    }
//
//    public void setSno(int Sno) {
//        this.Sno = Sno;
//    }
    //private ArrayList<ConditionTypePOJO> promotionTypes;
    // Code Added by BALA for  empid on 10.10.2017
    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }
    // Code Ended by BALA for  empid on 10.10.2017

    public String getFrombatch() {
        return frombatch;
    }

    public void setFrombatch(String frombatch) {
        this.frombatch = frombatch;
    }

    public String getLineItemEmpid() {
        return lineItemEmpid;
    }

    public void setLineItemEmpid(String lineItemEmpid) {
        this.lineItemEmpid = lineItemEmpid;
    }

    public String getFrameColor() {
        return frameColor;
    }

    public void setFrameColor(String frameColor) {
        this.frameColor = frameColor;
    }

    public String getLensTech() {
        return lensTech;
    }

    public void setLensTech(String lensTech) {
        this.lensTech = lensTech;
    }

    public String getLensColor() {
        return lensColor;
    }

    public void setLensColor(String lensColor) {
        this.lensColor = lensColor;
    }

    public String getGradient() {
        return gradient;
    }

    public void setGradient(String gradient) {
        this.gradient = gradient;
    }

    public String getCancelOtp() {
        return cancelOtp;
    }

    public void setCancelOtp(String cancelOtp) {
        this.cancelOtp = cancelOtp;
    }

    public String getAudioUniqueID() {//added by charan for audiology
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
