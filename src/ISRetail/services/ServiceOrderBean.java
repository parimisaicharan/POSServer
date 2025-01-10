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
package ISRetail.services;

import ISRetail.Webservices.DataObject;
import ISRetail.salesorder.SOLineItemPOJO;
import java.util.ArrayList;


public class ServiceOrderBean implements DataObject{
    private ServicesHeaderPOJO servicesHeaderPOJO;
    private ArrayList<SOLineItemPOJO> sOLineItemPOJOs;

    public ServicesHeaderPOJO getServicesHeaderPOJO() {
        return servicesHeaderPOJO;
    }

    public void setServicesHeaderPOJO(ServicesHeaderPOJO servicesHeaderPOJO) {
        this.servicesHeaderPOJO = servicesHeaderPOJO;
    }

    public ArrayList<SOLineItemPOJO> getSOLineItemPOJOs() {
        return sOLineItemPOJOs;
    }

    public void setSOLineItemPOJOs(ArrayList<SOLineItemPOJO> sOLineItemPOJOs) {
        this.sOLineItemPOJOs = sOLineItemPOJOs;
    }

}
