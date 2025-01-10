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
 * This class file is used to encapsulate the CashPayout Object for Sending across the Webservice
 * This represent the Cash Payout Object that is to be send across the Webservice
 * There is a set of Getter and Setter Methods
 * 
 * 
 */
package ISRetail.cashpayout;

import ISRetail.Webservices.DataObject;
import ISRetail.cashpayout.CashPayoutDetailsPOJO;
import ISRetail.cashpayout.CashPayoutPOJO;
import java.util.Collection;


public class CashPayoutBean  implements DataObject{

    private CashPayoutPOJO cashpayoutpojo;
    private Collection<CashPayoutDetailsPOJO> cashpayoutdetailspojo;

    public CashPayoutPOJO getCashpayoutpojo() {
        return cashpayoutpojo;
    }

    public void setCashpayoutpojo(CashPayoutPOJO cashpayoutpojo) {
        this.cashpayoutpojo = cashpayoutpojo;
    }

    public Collection<CashPayoutDetailsPOJO> getCashpayoutdetailspojo() {
        return cashpayoutdetailspojo;
    }

    public void setCashpayoutdetailspojo(Collection<CashPayoutDetailsPOJO> cashpayoutdetailspojo) {
        this.cashpayoutdetailspojo = cashpayoutdetailspojo;
    }
     
    
  

    
}
