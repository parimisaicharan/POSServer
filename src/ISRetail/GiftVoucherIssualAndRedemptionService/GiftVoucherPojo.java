/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.GiftVoucherIssualAndRedemptionService;

/**
 *
 * @author ewitsupport
 */
public class GiftVoucherPojo {
      private String giftVoucherNo;
    private String  gvRedemptionStatus;
    private String custMobileNo;
    private int fromDate;
    private int toDate;
    private String gvActiveCount;
    private String RefNo;
    private String siteId;
    private String sms_Sentstatus;
    private String redeemedGvCode;

    public String getRedeemedGvCode() {
        return redeemedGvCode;
    }

    public void setRedeemedGvCode(String redeemedGvCode) {
        this.redeemedGvCode = redeemedGvCode;
    }

    

    public String getGiftVoucherNo() {
        return giftVoucherNo;
    }

    public void setGiftVoucherNo(String giftVoucherNo) {
        this.giftVoucherNo = giftVoucherNo;
    }

    public String getGvRedemptionStatus() {
        return gvRedemptionStatus;
    }

    public void setGvRedemptionStatus(String gvRedemptionStatus) {
        this.gvRedemptionStatus = gvRedemptionStatus;
    }

    public String getCustMobileNo() {
        return custMobileNo;
    }

    public void setCustMobileNo(String custMobileNo) {
        this.custMobileNo = custMobileNo;
    }

    public int getFromDate() {
        return fromDate;
    }

    public void setFromDate(int fromDate) {
        this.fromDate = fromDate;
    }

    public int getToDate() {
        return toDate;
    }

    public void setToDate(int toDate) {
        this.toDate = toDate;
    }

    public String getGvActiveCount() {
        return gvActiveCount;
    }

    public void setGvActiveCount(String gvActiveCount) {
        this.gvActiveCount = gvActiveCount;
    }

    public String getRefNo() {
        return RefNo;
    }

    public void setRefNo(String RefNo) {
        this.RefNo = RefNo;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSms_Sentstatus() {
        return sms_Sentstatus;
    }

    public void setSms_Sentstatus(String sms_Sentstatus) {
        this.sms_Sentstatus = sms_Sentstatus;
    }
 
}
