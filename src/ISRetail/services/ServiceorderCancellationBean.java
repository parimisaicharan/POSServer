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
package ISRetail.services;

import ISRetail.creditnote.*;
import ISRetail.Webservices.DataObject;
import ISRetail.paymentdetails.AdvanceReceiptPOJO;
import java.util.ArrayList;

public class ServiceorderCancellationBean implements DataObject {

    private ServicesHeaderPOJO serviceOrderHeader;
    private CreditNotePOJO creditNotePojo;
    private ArrayList<AdvanceReceiptPOJO> advanceReceiptPOJOs;
    
    private NRCreditNotePOJO nRCreditNotePOJO;

    public CreditNotePOJO getCreditNotePojo() {
        return creditNotePojo;
    }

    public void setCreditNotePojo(CreditNotePOJO creditNotePojo) {
        this.creditNotePojo = creditNotePojo;
    }

    public ServicesHeaderPOJO getServiceOrderHeader() {
        return serviceOrderHeader;
    }

    public void setServiceOrderHeader(ServicesHeaderPOJO serviceOrderHeader) {
        this.serviceOrderHeader = serviceOrderHeader;
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
