/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VersionManagement;

/**
 *
 * @author Administrator
 */
public class VersionHeaderPOJO {
private String siteId;    
private String versionId;
private String versiondescription;
private int dateofrelease;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

   public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getVersiondescription() {
        return versiondescription;
    }

    public void setVersiondescription(String versiondescription) {
        this.versiondescription = versiondescription;
    }

    public int getDateofrelease() {
        return dateofrelease;
    }

    public void setDateofrelease(int dateofrelease) {
        this.dateofrelease = dateofrelease;
    }

   
    


}
