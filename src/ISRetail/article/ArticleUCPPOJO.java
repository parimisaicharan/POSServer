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
 * POJO Class for Article Master UCP to hold the UCP values for a particular Article
 * 
 * 
 */

package ISRetail.article;

/**
 *
 * @author Administrator
 */
public class ArticleUCPPOJO {
private String storecode;
private String condtype;
private String materialcode;
private String condrecno;
private double ucpamount;
private String currency;
private int fromdate;
private int todate;
private String updatestatus;
private String deletionind;
private String priority;
private String condName;
private int modifieddate;
private String modifiedtime;

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

    public String getMaterialcode() {
        return materialcode;
    }

    public void setMaterialcode(String materialcode) {
        this.materialcode = materialcode;
    }

    public String getCondrecno() {
        return condrecno;
    }

    public void setCondrecno(String condrecno) {
        this.condrecno = condrecno;
    }

    public double getUcpamount() {
        return ucpamount;
    }

    public void setUcpamount(double ucpamount) {
        this.ucpamount = ucpamount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCondName() {
        return condName;
    }

    public void setCondName(String condName) {
        this.condName = condName;
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

}
