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
 * POJO for SitePOSArchivalData
 * 
 * 
 * 
 */

package ISRetail.plantdetails;

/**
 *
 * @author Administrator
 */
public class SitePosArichvalDataPOJO {
private String siteId;
private int fromdate;
private int todate;
private int dateofarcival;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
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

    public int getDateofarcival() {
        return dateofarcival;
    }

    public void setDateofarcival(int dateofarcival) {
        this.dateofarcival = dateofarcival;
    }

}
