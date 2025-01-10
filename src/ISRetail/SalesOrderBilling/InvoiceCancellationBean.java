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
 * This class file is used to encapsulate the Invoice Cancellation Object for Sending across the Webservice
 * This represent the Invoice Cancellation that is to be send across the Webservice
 * This Object consist of the Billing Header POJO 
 * There is a set of Getter and Setter Methods
 * 
 * 
 */

package ISRetail.SalesOrderBilling;

import ISRetail.Webservices.DataObject;
import ISRetail.billing.BillingHeaderPOJO;
import java.io.Serializable;

public class InvoiceCancellationBean implements DataObject,Serializable{
    private BillingHeaderPOJO Header;

    public BillingHeaderPOJO getHeader() {
        return Header;
    }

    public void setHeader(BillingHeaderPOJO Header) {
        this.Header = Header;
    }

}
