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
 * 
 * POJO Class for Data Archival Record
 * 
 * 
 */

package ISRetail.dataarchival;

/**
 *
 * @author enteg
 */
public class DataArchiveRecordPOJO {
private String archivalid ;
private String siteid;
private int sino;
private int archivalfromdate;
private int archivaltodate ;
private int archivaldate;
private int statusupdateddate;
private String statusupdatedtime ;
private String status ;
private String statusmessage ;
private String sqlStatement ;

    public String getArchivalid() {
        return archivalid;
    }

    public void setArchivalid(String archivalid) {
        this.archivalid = archivalid;
    }

    public String getSiteid() {
        return siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    public int getSino() {
        return sino;
    }

    public void setSino(int sino) {
        this.sino = sino;
    }

    public int getArchivalfromdate() {
        return archivalfromdate;
    }

    public void setArchivalfromdate(int archivalfromdate) {
        this.archivalfromdate = archivalfromdate;
    }

    public int getArchivaltodate() {
        return archivaltodate;
    }

    public void setArchivaltodate(int archivaltodate) {
        this.archivaltodate = archivaltodate;
    }

    public int getArchivaldate() {
        return archivaldate;
    }

    public void setArchivaldate(int archivaldate) {
        this.archivaldate = archivaldate;
    }

    public int getStatusupdateddate() {
        return statusupdateddate;
    }

    public void setStatusupdateddate(int statusupdateddate) {
        this.statusupdateddate = statusupdateddate;
    }

    public String getStatusupdatedtime() {
        return statusupdatedtime;
    }

    public void setStatusupdatedtime(String statusupdatedtime) {
        this.statusupdatedtime = statusupdatedtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSqlStatement() {
        return sqlStatement;
    }

    public void setSqlStatement(String sqlStatement) {
        this.sqlStatement = sqlStatement;
    }

    public String getStatusmessage() {
        return statusmessage;
    }

    public void setStatusmessage(String statusmessage) {
        this.statusmessage = statusmessage;
    }
 

}
