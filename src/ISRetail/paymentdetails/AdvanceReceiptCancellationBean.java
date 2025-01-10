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
 * This class file is used to encapsulate the AdvanceReceiptPOJO For Cancellation and Sending across the Webservice
 * This represent the AdvanceReceipt Object that is to be send across the Webservice
 * This Object consist of the AdvanceReceipt Header as POJO 
 * There is a set of Getter and Setter Methods
 * 
 * 
 */

package ISRetail.paymentdetails;

import ISRetail.Webservices.DataObject;

/**
 *
 * @author Administrator
 */
public class AdvanceReceiptCancellationBean implements DataObject{
private AdvanceReceiptPOJO advanceReceiptPOJO ;

    public AdvanceReceiptPOJO getAdvanceReceiptPOJO() {
        return advanceReceiptPOJO;
    }

    public void setAdvanceReceiptPOJO(AdvanceReceiptPOJO advanceReceiptPOJO) {
        this.advanceReceiptPOJO = advanceReceiptPOJO;
    }
}
