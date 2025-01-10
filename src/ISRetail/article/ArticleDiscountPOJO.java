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
 * Article discount POJO
 * 
 * 
 */
package ISRetail.article;

public class ArticleDiscountPOJO {
private String storecode;
private String condtype;
private String merchcat;
private String condrecno;
private double discount;
private String calculationtype;
private int fromdate;
private int todate;
private String updatestatus;
private String deletionind;
private String condName;
private String compCode;
private String saleOrg;

    public String getStorecode() {
        return storecode;
    }

    public void setStorecode(String storecode) {
        this.storecode = storecode;
    }

    public String getCondtype() {
        return condtype;
    }

    public void setCondtype(String condtype) {
        this.condtype = condtype;
    }

    public String getMerchcat() {
        return merchcat;
    }

    public void setMerchcat(String merchcat) {
        this.merchcat = merchcat;
    }

    public String getCondrecno() {
        return condrecno;
    }

    public void setCondrecno(String condrecno) {
        this.condrecno = condrecno;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getCalculationtype() {
        return calculationtype;
    }

    public void setCalculationtype(String calculationtype) {
        this.calculationtype = calculationtype;
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

    public String getUpdatestatus() {
        return updatestatus;
    }

    public void setUpdatestatus(String updatestatus) {
        this.updatestatus = updatestatus;
    }

    public String getDeletionind() {
        return deletionind;
    }

    public void setDeletionind(String deletionind) {
        this.deletionind = deletionind;
    }

    public String getCondName() {
        return condName;
    }

    public void setCondName(String condName) {
        this.condName = condName;
    }

    public String getCompCode() {
        return compCode;
    }

    public void setCompCode(String compCode) {
        this.compCode = compCode;
    }

    public String getSaleOrg() {
        return saleOrg;
    }

    public void setSaleOrg(String saleOrg) {
        this.saleOrg = saleOrg;
    }

}
