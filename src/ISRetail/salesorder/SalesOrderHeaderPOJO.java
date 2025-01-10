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

import java.util.Iterator;

public class SalesOrderHeaderPOJO {

//private  int  slno;
    private String storeCode;
    private int fiscalYear;
    private String saleorderno;
    private int saleorderdate;
    private String customercode;
    private String datasheetno;
//private  String  styleconsultant;
    private String printordertype;
    private String division;
//private  double  segmentsize;
//private  double  segmentheight;

    private String segmentSize;
    private String segmentHeight;

    private String ed;
    private String distancepd;
    private String nearpd;
    private String fittinglabInstruction;
    private String lensvendorInstruction;
    private int priority;
    private int plannedDate;
    private int proposedDate;
    private int targetcompletedate;
    private String verified;
    private String verifiedby;
    private double amount;
    private String oldSaleorderNo;
    private String reasonforReturn;
    private String saletype;
    private String releaseStatus;
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
//private String companyCode;
    private String parameterId;
    private String creditsalereference = "";

    private String procreq;
//Code added on June 1st 2010
    private String procreq_left;
    private int approveddate;
    private int releasedate;
    private String insforuser = "";
    private String staffresponsible = ""; //code added on 21 Jun 2011 for name capture in Sale Return
//End of code added on June 1st 2010
//Added by dileep - 17.04.2014
    private String referralVoucherNO;
    private String referralRedeemNO;
    private String ReferencePromoOrder;
    private String externalInvoiceNo;//Added by Balachander V on 18.2.2020 to get External Invoice No.
    private String cancelOtp;
    private String deliveryMode;//Added by Balachander V on 05.12.2020 to get delivery type
    private String recon_reason;//Added by Balachander V on 10.2.2022 to get reason for recomendation
    private String sampleTintType;//Added by Balachander V on 22.07.2023
    private String sampleTintRemarks;//Added by Balachander V on 22.07.2023
    private String whStock;//added by charan for WareHouse Stock Check
    private String custname;
    private String mobileno;
    private String gstn_no;
//private String advCancelledStatus;
//
//private String CreditNoteNo;

//    public int getSlno() {
//        return slno;
//    }
//
//    public void setSlno(int slno) {
//        this.slno = slno;
//    }
    public String getSaleorderno() {
        return saleorderno;
    }

    public Iterator iterator() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void setSaleorderno(String saleorderno) {
        this.saleorderno = saleorderno;
    }

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

//    public String getStyleconsultant() {
//        return styleconsultant;
//    }
//
//    public void setStyleconsultant(String styleconsultant) {
//        this.styleconsultant = styleconsultant;
//    }
//    public String getPrintoption() {
//        return printoption;
//    }
//
//    public void setPrintoption(String printoption) {
//        this.printoption = printoption;
//    }
//    public double getSegmentsize() {
//        return segmentsize;
//    }
//
//    public void setSegmentsize(double segmentsize) {
//        this.segmentsize = segmentsize;
//    }
//
//    public double getSegmentheight() {
//        return segmentheight;
//    }
//
//    public void setSegmentheight(double segmentheight) {
//        this.segmentheight = segmentheight;
//    }
    public String getEd() {
        return ed;
    }

    public void setEd(String ed) {
        this.ed = ed;
    }

    public String getDistancepd() {
        return distancepd;
    }

    public void setDistancepd(String distancepd) {
        this.distancepd = distancepd;
    }

    public String getNearpd() {
        return nearpd;
    }

    public void setNearpd(String nearpd) {
        this.nearpd = nearpd;
    }

    public String getFittinglabInstruction() {
        return fittinglabInstruction;
    }

    public void setFittinglabInstruction(String fittinglabInstruction) {
        this.fittinglabInstruction = fittinglabInstruction;
    }

    public String getLensvendorInstruction() {
        return lensvendorInstruction;
    }

    public void setLensvendorInstruction(String lensvendorInstruction) {
        this.lensvendorInstruction = lensvendorInstruction;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getVerifiedby() {
        return verifiedby;
    }

    public void setVerifiedby(String verifiedby) {
        this.verifiedby = verifiedby;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getSaleorderdate() {
        return saleorderdate;
    }

    public void setSaleorderdate(int saleorderdate) {
        this.saleorderdate = saleorderdate;
    }

    public int getTargetcompletedate() {
        return targetcompletedate;
    }

    public void setTargetcompletedate(int targetcompletedate) {
        this.targetcompletedate = targetcompletedate;
    }

    public int getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(int plannedDate) {
        this.plannedDate = plannedDate;
    }

    public int getProposedDate() {
        return proposedDate;
    }

    public void setProposedDate(int proposedDate) {
        this.proposedDate = proposedDate;
    }

    public String getPrintordertype() {
        return printordertype;
    }

    public void setPrintordertype(String printordertype) {
        this.printordertype = printordertype;
    }

    public String getOldSaleorderNo() {
        return oldSaleorderNo;
    }

    public String getReasonforReturn() {
        return reasonforReturn;
    }

    public String getSaletype() {
        return saletype;
    }

    public String getReleaseStatus() {
        return releaseStatus;
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

    public String getSegmentSize() {
        return segmentSize;
    }

    public void setSegmentSize(String segmentSize) {
        this.segmentSize = segmentSize;
    }

    public String getSegmentHeight() {
        return segmentHeight;
    }

    public void setSegmentHeight(String segmentHeight) {
        this.segmentHeight = segmentHeight;
    }

    public void setOldSaleorderNo(String oldSaleorderNo) {
        this.oldSaleorderNo = oldSaleorderNo;
    }

    public void setReasonforReturn(String reasonforReturn) {
        this.reasonforReturn = reasonforReturn;
    }

    public void setSaletype(String saletype) {
        this.saletype = saletype;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
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

//    public String getCompanyCode() {
//        return companyCode;
//    }
//
//    public void setCompanyCode(String companyCode) {
//        this.companyCode = companyCode;
//    }
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

    public String getCreditsalereference() {
        return creditsalereference;
    }

    public void setCreditsalereference(String creditsalereference) {
        this.creditsalereference = creditsalereference;
    }

    public String getProcreq() {
        return procreq;
    }

    public void setProcreq(String procreq) {
        this.procreq = procreq;
    }

    public String getProcreq_left() {
        return procreq_left;
    }

    public void setProcreq_left(String procreq_left) {
        this.procreq_left = procreq_left;
    }

    public int getApproveddate() {
        return approveddate;
    }

    public void setApproveddate(int approveddate) {
        this.approveddate = approveddate;
    }

    public int getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(int releasedate) {
        this.releasedate = releasedate;
    }

    public String getInsforuser() {
        return insforuser;
    }

    public void setInsforuser(String insforuser) {
        this.insforuser = insforuser;
    }

    /**
     * @return the staffresponsible
     */
    public String getStaffresponsible() {
        return staffresponsible;
    }

    /**
     * @param staffresponsible the staffresponsible to set
     */
    public void setStaffresponsible(String staffresponsible) {
        this.staffresponsible = staffresponsible;
    }

    public String getReferralVoucherNO() {
        return referralVoucherNO;
    }

    public void setReferralVoucherNO(String referralVoucherNO) {
        this.referralVoucherNO = referralVoucherNO;
    }

    public String getReferralRedeemNO() {
        return referralRedeemNO;
    }

    public void setReferralRedeemNO(String referralRedeemNO) {
        this.referralRedeemNO = referralRedeemNO;
    }

//    public String getAdvCancelledStatus() {
//        return advCancelledStatus;
//    }
//
//    public void setAdvCancelledStatus(String advCancelledStatus) {
//        this.advCancelledStatus = advCancelledStatus;
//    }
//
//
//
//    public String getCreditNoteNo() {
//        return CreditNoteNo;
//    }
//
//    public void setCreditNoteNo(String CreditNoteNo) {
//        this.CreditNoteNo = CreditNoteNo;
//    }
    public String getReferencePromoOrder() {
        return ReferencePromoOrder;
    }

    public void setReferencePromoOrder(String ReferencePromoOrder) {
        this.ReferencePromoOrder = ReferencePromoOrder;
    }

    public String getExternalInvoiceNo() {
        return externalInvoiceNo;
    }

    public void setExternalInvoiceNo(String externalInvoiceNo) {
        this.externalInvoiceNo = externalInvoiceNo;
    }

    public String getCancelOtp() {
        return cancelOtp;
    }

    public void setCancelOtp(String cancelOtp) {
        this.cancelOtp = cancelOtp;
    }

    public String getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(String deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public String getRecon_reason() {
        return recon_reason;
    }

    public void setRecon_reason(String recon_reason) {
        this.recon_reason = recon_reason;
    }

    public String getSampleTintType() {
        return sampleTintType;
    }

    public void setSampleTintType(String sampleTintType) {
        this.sampleTintType = sampleTintType;
    }

    public String getSampleTintRemarks() {
        return sampleTintRemarks;
    }

    public void setSampleTintRemarks(String sampleTintRemarks) {
        this.sampleTintRemarks = sampleTintRemarks;
    }

    public String getWhStock() {//added by charan for WareHouse Stock Check
        return whStock;
    }

    public void setWhStock(String whStock) {
        this.whStock = whStock;
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
