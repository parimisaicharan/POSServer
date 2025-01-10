/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.CashReceipt;

import ISRetail.Webservices.DataObject;
import ISRetail.paymentdetails.AdvanceReceiptDetailsPOJO;
import java.util.Collection;

/**
 *
 * @author naveenn
 */
public class CashReceiptCreationBean implements DataObject{
    private CashReceiptPOJO cashReceiptPOJO;

    public Collection<AdvanceReceiptDetailsPOJO> getAdvanceReceiptDetailsPOJO() {
        return advanceReceiptDetailsPOJO;
    }

    public void setAdvanceReceiptDetailsPOJO(Collection<AdvanceReceiptDetailsPOJO> advanceReceiptDetailsPOJO) {
        this.advanceReceiptDetailsPOJO = advanceReceiptDetailsPOJO;
    }

    public CashReceiptPOJO getCashReceiptPOJO() {
        return cashReceiptPOJO;
    }

    public void setCashReceiptPOJO(CashReceiptPOJO cashReceiptPOJO) {
        this.cashReceiptPOJO = cashReceiptPOJO;
    }
    private Collection<AdvanceReceiptDetailsPOJO> advanceReceiptDetailsPOJO;
    
    
}
