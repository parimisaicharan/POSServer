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
 * POJO for SITE FORCE RELEASE
 * 
 * 
 * 
 */

package ISRetail.plantdetails;

/**
 *
 * @author Administrator
 */
public class SiteForceReleasePOJO {
private String siteId;
private int slno;
private int fromdate;
private int todate;
private int forcerelease_quantity;
private int currentcount;
private String updateStatus;
private String recordStatus;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public int getSlno() {
        return slno;
    }

    public void setSlno(int slno) {
        this.slno = slno;
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

    public int getForcerelease_quantity() {
        return forcerelease_quantity;
    }

    public void setForcerelease_quantity(int forcerelease_quantity) {
        this.forcerelease_quantity = forcerelease_quantity;
    }

    public int getCurrentcount() {
        return currentcount;
    }

    public void setCurrentcount(int currentcount) {
        this.currentcount = currentcount;
    }

    public String getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(String updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }



}
