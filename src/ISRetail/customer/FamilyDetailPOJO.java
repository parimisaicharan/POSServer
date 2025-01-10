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
 * POJO Class for Family Details of the Customer
 * 
 * 
 */

package ISRetail.customer;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Administrator
 */
public class FamilyDetailPOJO implements Serializable{
private String customercode;
private int familymemberno;
private String name;
private String relationship;
private int dob;
private String contactno;
private String wearingspetacles;

    public String getCustomercode() {
        return customercode;
    }

    public void setCustomercode(String customercode) {
        this.customercode = customercode;
    }

    public int getFamilymemberno() {
        return familymemberno;
    }

    public void setFamilymemberno(int familymemberno) {
        this.familymemberno = familymemberno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public int getDob() {
        return dob;
    }

    public void setDob(int dob) {
        this.dob = dob;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getWearingspetacles() {
        return wearingspetacles;
    }

    public void setWearingspetacles(String wearingspetacles) {
        this.wearingspetacles = wearingspetacles;
    }


}
