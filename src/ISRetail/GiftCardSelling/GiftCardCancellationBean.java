/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ISRetail.GiftCardSelling;

import ISRetail.Webservices.DataObject;
import ISRetail.paymentdetails.AdvanceReceiptDetailsDO;
import ISRetail.paymentdetails.AdvanceReceiptDetailsPOJO;
import java.util.Collection;

/**
 *
 * @author Administrator
 */
public class GiftCardCancellationBean implements  DataObject{

    private GiftCardSellingPOJO giftCardSellingPOJO ; 
    private Collection<AdvanceReceiptDetailsPOJO> advanceReceiptDetailsPOJOs;
    

    public GiftCardSellingPOJO getGiftCardSellingPOJO() {
        return giftCardSellingPOJO;
    }

    public void setGiftCardSellingPOJO(GiftCardSellingPOJO giftCardSellingPOJO) {
        this.giftCardSellingPOJO = giftCardSellingPOJO;
    }

    public Collection<AdvanceReceiptDetailsPOJO> getAdvanceReceiptDetailsPOJOs() {
        return advanceReceiptDetailsPOJOs;
    }

    public void setAdvanceReceiptDetailsPOJOs(Collection<AdvanceReceiptDetailsPOJO> advanceReceiptDetailsPOJOs) {
        this.advanceReceiptDetailsPOJOs = advanceReceiptDetailsPOJOs;
    }


}
