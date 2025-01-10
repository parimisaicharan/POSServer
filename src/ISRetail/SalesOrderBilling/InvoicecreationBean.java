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
package ISRetail.SalesOrderBilling;

import ISRetail.Webservices.*;
import ISRetail.SalesOrderBilling.SalesOrderBillingPOJO;
import ISRetail.paymentdetails.AdvanceReceiptDetailsPOJO;
import ISRetail.paymentdetails.AdvanceReceiptPOJO;

import ISRetail.salesorder.SOLineItemPOJO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


public class InvoicecreationBean implements DataObject,Serializable {
        private ArrayList <SOLineItemPOJO> sOLineItemPOJOs ;
        private SalesOrderBillingPOJO Header;
        private Collection<AdvanceReceiptDetailsPOJO> advanceReceiptDetailsPOJOs;  
        private Collection<AdvanceReceiptPOJO> advanceReceiptPOJO ;

    public ArrayList<SOLineItemPOJO> getSOLineItemPOJOs() {
        return sOLineItemPOJOs;
    }

    public void setSOLineItemPOJOs(ArrayList<SOLineItemPOJO> sOLineItemPOJOs) {
        this.sOLineItemPOJOs = sOLineItemPOJOs;
    }


    public SalesOrderBillingPOJO getHeader() {
        return Header;
    }

    public void setHeader(SalesOrderBillingPOJO Header) {
        this.Header = Header;
    }

    public Collection<AdvanceReceiptDetailsPOJO> getAdvanceReceiptDetailsPOJOs() {
        return advanceReceiptDetailsPOJOs;
    }

    public void setAdvanceReceiptDetailsPOJOs(Collection<AdvanceReceiptDetailsPOJO> advanceReceiptDetailsPOJOs) {
        this.advanceReceiptDetailsPOJOs = advanceReceiptDetailsPOJOs;
    }

    public Collection<AdvanceReceiptPOJO> getAdvanceReceiptPOJO() {
        return advanceReceiptPOJO;
    }

    public void setAdvanceReceiptPOJO(Collection<AdvanceReceiptPOJO> advanceReceiptPOJO) {
        this.advanceReceiptPOJO = advanceReceiptPOJO;
    }



    

}
