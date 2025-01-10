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
 * This class file is used to encapsulate the Direct BillingDetails for Sending across the Webservice
 * This represent the DirectBilling Object that is to be send across the Webservice
 * There is a set of Getter and Setter Methods
 * 
 * 
 */
package ISRetail.billing;

import ISRetail.Webservices.DataObject;
import ISRetail.billing.BillingHeaderPOJO;
import ISRetail.paymentdetails.AdvanceReceiptDetailsPOJO;

import ISRetail.salesorder.SOLineItemPOJO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


public class DirectBillingBean implements DataObject, Serializable {

    private ArrayList<SOLineItemPOJO> sOLineItemPOJOs;
    private BillingHeaderPOJO Header;
    private Collection<AdvanceReceiptDetailsPOJO> advanceReceiptDetailsPOJOs;

    public ArrayList<SOLineItemPOJO> getSOLineItemPOJOs() {
        return sOLineItemPOJOs;
    }

    public void setSOLineItemPOJOs(ArrayList<SOLineItemPOJO> sOLineItemPOJOs) {
        this.sOLineItemPOJOs = sOLineItemPOJOs;
    }

    public BillingHeaderPOJO getHeader() {
        return Header;
    }

    public void setHeader(BillingHeaderPOJO Header) {
        this.Header = Header;
    }

    public Collection<AdvanceReceiptDetailsPOJO> getAdvanceReceiptDetailsPOJOs() {
        return advanceReceiptDetailsPOJOs;
    }

    public void setAdvanceReceiptDetailsPOJOs(Collection<AdvanceReceiptDetailsPOJO> advanceReceiptDetailsPOJOs) {
        this.advanceReceiptDetailsPOJOs = advanceReceiptDetailsPOJOs;
    }
}
