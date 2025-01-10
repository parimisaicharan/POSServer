/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.CashReceipt;

import ISRetail.paymentdetails.AdvanceReceiptDetailsPOJO;
import java.util.Collection;

/**
 *
 * @author naveenn
 */
public class CashReceiptCancellationBean {
    private CashReceiptPOJO CashreceiptPOJO;
    private Collection<AdvanceReceiptDetailsPOJO> advanceReceiptDetails;

    public CashReceiptPOJO getCashreceiptPOJO() {
        return CashreceiptPOJO;
    }

    public void setCashreceiptPOJO(CashReceiptPOJO CashreceiptPOJO) {
        this.CashreceiptPOJO = CashreceiptPOJO;
    }

    public Collection<AdvanceReceiptDetailsPOJO> getAdvanceReceiptDetails() {
        return advanceReceiptDetails;
    }

    public void setAdvanceReceiptDetails(Collection<AdvanceReceiptDetailsPOJO> advanceReceiptDetails) {
        this.advanceReceiptDetails = advanceReceiptDetails;
    }
    
}
