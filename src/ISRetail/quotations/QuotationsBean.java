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
 * This class file is used to encapsulate the Invoice POJO and Invoice Details for Sending across the Webservice
 * This represent the InvoiceObject that is to be send across the Webservice
 * This Object consist of the InvoiceHeader as POJO and InvoiceDetails (Line Items) as Collection
 * There is a set of Getter and Setter Methods
 * 
 * 
 */
package ISRetail.quotations;
import ISRetail.Webservices.DataObject;
import ISRetail.customer.CustomerMasterBean;
import ISRetail.inquiry.ClinicalHistoryPOJO;
import ISRetail.inquiry.ContactLensPOJO;
import ISRetail.inquiry.PrescriptionPOJO;
import java.util.Collection;

public class QuotationsBean implements DataObject {
    
    private QuotationsHeaderPOJO quotationsHeaderPOJO ; 
    private Collection quotationsDetailsPOJOs;
    private CustomerMasterBean customerPojo;
    private ClinicalHistoryPOJO cliniPojo;
    private PrescriptionPOJO presPojo;
    private ContactLensPOJO clensPojo;

    public QuotationsHeaderPOJO getQuotationHeaderPOJO() {
        return quotationsHeaderPOJO;
    }

    public void setQuotationsHeaderPOJO(QuotationsHeaderPOJO quotationHeaderPOJO) {
        this.quotationsHeaderPOJO = quotationHeaderPOJO;
    }

    public Collection<QuotationsDetailsPOJO> getQuotationDetailsPOJOs() {
        return quotationsDetailsPOJOs;
    }

    public void setQuotationDetailsPOJOs(Collection quotationsDetailsPOJOs) {
        this.quotationsDetailsPOJOs = quotationsDetailsPOJOs;
    }

    public CustomerMasterBean getCustomerPojo() {
        return customerPojo;
    }

    public void setCustomerPojo(CustomerMasterBean customerPojo) {
        this.customerPojo = customerPojo;
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
