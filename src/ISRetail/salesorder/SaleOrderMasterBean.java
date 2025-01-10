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

package ISRetail.salesorder;


import ISRetail.Webservices.*;
import ISRetail.customer.CustomerMasterBean;
import ISRetail.inquiry.ClinicalHistoryPOJO;
import ISRetail.inquiry.ContactLensPOJO;
import ISRetail.inquiry.PrescriptionPOJO;
import ISRetail.salesorder.SalesOrderHeaderPOJO;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author sukumar
 * class represending the sales order 
 */
public class SaleOrderMasterBean implements DataObject,Serializable{
    
    private CustomerMasterBean customerPojo;
    private ClinicalHistoryPOJO cliniPojo;
    private PrescriptionPOJO presPojo;
    private ContactLensPOJO clensPojo;
    private SalesOrderHeaderPOJO salesOrderHeader; 
    private ArrayList <SOLineItemPOJO> salesOrderDetails ;
 
    
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

    public SalesOrderHeaderPOJO getSalesOrderHeader() {
        return salesOrderHeader;
    }

    public void setSalesOrderHeader(SalesOrderHeaderPOJO salesOrderHeader) {
        this.salesOrderHeader = salesOrderHeader;
    }

    public ArrayList<SOLineItemPOJO> getSalesOrderDetails() {
        return salesOrderDetails;
    }

    public void setSalesOrderDetails(ArrayList<SOLineItemPOJO> salesOrderDetails) {
        this.salesOrderDetails = salesOrderDetails;
    }

    

    public CustomerMasterBean getCustomerPojo() {
        return customerPojo;
    }

    public void setCustomerPojo(CustomerMasterBean customerPojo) {
        this.customerPojo = customerPojo;
    }

   
    

  
}
