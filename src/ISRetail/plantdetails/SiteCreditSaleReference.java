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
 * POJO for SiteCreditSaleReference
 * 
 * 
 * 
 */

package ISRetail.plantdetails;

/**
 *
 * @author Administrator
 */
public class SiteCreditSaleReference {
   private int slno; 
   private String siteId;
   private String sapcustomerno;
   private int conditionno;
   private String institutionname;
   private int fromdate;
   private int todate;
   private String updatestatus;
   private String recordStatus;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSapcustomerno() {
        return sapcustomerno;
    }

    public void setSapcustomerno(String sapcustomerno) {
        this.sapcustomerno = sapcustomerno;
    }

    public int getConditionno() {
        return conditionno;
    }

    public void setConditionno(int conditionno) {
        this.conditionno = conditionno;
    }

    public String getInstitutionname() {
        return institutionname;
    }

    public void setInstitutionname(String institutionname) {
        this.institutionname = institutionname;
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

    public int getSlno() {
        return slno;
    }

    public void setSlno(int slno) {
        this.slno = slno;
    }

    public String getUpdatestatus() {
        return updatestatus;
    }

    public void setUpdatestatus(String updatestatus) {
        this.updatestatus = updatestatus;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }


}
