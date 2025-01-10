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
 * This class file is used to encapsulate the SaleOrder Cancellation Bean
 * There is a set of Getter and Setter Methods
 * 
 * 
 */

package ISRetail.creditnote;

import ISRetail.Webservices.DataObject;
import ISRetail.paymentdetails.AdvanceReceiptPOJO;
import ISRetail.salesorder.SalesOrderHeaderPOJO;
import java.util.ArrayList;

public class SaleorderCancellationBean implements DataObject{
    private SalesOrderHeaderPOJO salesOrderHeader;   
    private CreditNotePOJO creditNotePojo;     
    private ArrayList<AdvanceReceiptPOJO> advanceReceiptPOJOs;
    //Code added on May 26th 2010
    private NRCreditNotePOJO nRCreditNotePOJO;
    
    public void setSalesOrderHeader(SalesOrderHeaderPOJO salesOrderHeader) {
        this.salesOrderHeader = salesOrderHeader;
    }    

    public CreditNotePOJO getCreditNotePojo() {
        return creditNotePojo;
    }

    public void setCreditNotePojo(CreditNotePOJO creditNotePojo) {
        this.creditNotePojo = creditNotePojo;
    }

    
    public SalesOrderHeaderPOJO getSalesOrderHeader() {
        return salesOrderHeader;
    }

    public ArrayList<AdvanceReceiptPOJO> getAdvanceReceiptPOJOs() {
        return advanceReceiptPOJOs;
    }

    public void setAdvanceReceiptPOJOs(ArrayList<AdvanceReceiptPOJO> advanceReceiptPOJOs) {
        this.advanceReceiptPOJOs = advanceReceiptPOJOs;
    }

    public NRCreditNotePOJO getNRCreditNotePOJO() {
        return nRCreditNotePOJO;
    }

    public void setNRCreditNotePOJO(NRCreditNotePOJO nRCreditNotePOJO) {
        this.nRCreditNotePOJO = nRCreditNotePOJO;
    }


}
