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
 * This class file is used to encapsulate the Inquiry Master Bean
 * This represent the Inquiry Object that is to be send across the Webservice
 * There is a set of Getter and Setter Methods
 * 
 * 
 */

package ISRetail.inquiry;

import ISRetail.Webservices.DataObject;
/**
 *
 * @author sukumar
 */
public class InquiryMasterBean implements DataObject{
    
    private InquiryPOJO inqPojo;
    private ClinicalHistoryPOJO cliniPojo;
    private PrescriptionPOJO presPojo;
    private ContactLensPOJO clensPojo;
    

    public InquiryPOJO getInqPojo() {
        return inqPojo;
    }

    public void setInqPojo(InquiryPOJO inqPojo) {
        this.inqPojo = inqPojo;
    }

    public ClinicalHistoryPOJO getCliniPojo() {
        return cliniPojo;
    }

    public void setCliniPojo(ClinicalHistoryPOJO cliniPojo) {
        this.cliniPojo = cliniPojo;
    }

    public PrescriptionPOJO getPresPojo() {
        return presPojo;
    }

    public void setPresPojo(PrescriptionPOJO presPojo) {
        this.presPojo = presPojo;
    }

    public ContactLensPOJO getClensPojo() {
        return clensPojo;
    }

    public void setClensPojo(ContactLensPOJO clensPojo) {
        this.clensPojo = clensPojo;
    }

  

}
