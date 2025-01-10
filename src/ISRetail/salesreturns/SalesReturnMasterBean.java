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
package ISRetail.salesreturns;

import ISRetail.Webservices.DataObject;
import ISRetail.creditnote.CreditNotePOJO;
import ISRetail.creditnote.NRCreditNotePOJO;
import ISRetail.salesorder.SOLineItemPOJO;
import java.util.ArrayList;

public class SalesReturnMasterBean implements DataObject{
    private AcknowledgementHeaderPOJO acknowledgementHeaderPOJO;
    private ArrayList <AcknowledgementFocSparesPOJO> acknowledgementFocSparesPOJOs;
    private SalesReturnHeaderPOJO salesReturnHeaderPOJO;
    private ArrayList <SOLineItemPOJO> ackLneItemsPojo ;
    private CreditNotePOJO creditNotePojo; 
    private NRCreditNotePOJO nRCreditNotePOJO; 

    public AcknowledgementHeaderPOJO getAcknowledgementHeaderPOJO() {
        return acknowledgementHeaderPOJO;
    }

    public void setAcknowledgementHeaderPOJO(AcknowledgementHeaderPOJO acknowledgementHeaderPOJO) {
        this.acknowledgementHeaderPOJO = acknowledgementHeaderPOJO;
    }


    

    public SalesReturnHeaderPOJO getSalesReturnHeaderPOJO() {
        return salesReturnHeaderPOJO;
    }

    public void setSalesReturnHeaderPOJO(SalesReturnHeaderPOJO salesReturnHeaderPOJO) {
        this.salesReturnHeaderPOJO = salesReturnHeaderPOJO;
    }



    public ArrayList<SOLineItemPOJO> getAckLneItemsPojo() {
        return ackLneItemsPojo;
    }

    public void setAckLneItemsPojo(ArrayList<SOLineItemPOJO> ackLneItemsPojo) {
        this.ackLneItemsPojo = ackLneItemsPojo;
    }



    public CreditNotePOJO getCreditNotePojo() {
        return creditNotePojo;
    }

    public void setCreditNotePojo(CreditNotePOJO creditNotePojo) {
        this.creditNotePojo = creditNotePojo;
    }

    public ArrayList<AcknowledgementFocSparesPOJO> getAcknowledgementFocSparesPOJOs() {
        return acknowledgementFocSparesPOJOs;
    }

    public void setAcknowledgementFocSparesPOJOs(ArrayList<AcknowledgementFocSparesPOJO> acknowledgementFocSparesPOJOs) {
        this.acknowledgementFocSparesPOJOs = acknowledgementFocSparesPOJOs;
    }

    public NRCreditNotePOJO getNRCreditNotePOJO() {
        return nRCreditNotePOJO;
    }

    public void setNRCreditNotePOJO(NRCreditNotePOJO nRCreditNotePOJO) {
        this.nRCreditNotePOJO = nRCreditNotePOJO;
    }

}
