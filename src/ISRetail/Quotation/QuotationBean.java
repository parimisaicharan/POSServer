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
 * This class file is used to encapsulate the QuotationPOJO and QuotationDetails for Sending across the Webservice
 * This represent the Quotation Object that is to be send across the Webservice
 * This Object consist of the Quotion Header as POJO and Quotation Detials(Line Items) as Collection
 * There is a set of Getter and Setter Methods
 * 
 * 
 */

package ISRetail.Quotation;
import ISRetail.Webservices.DataObject;
import java.util.Collection;

public class QuotationBean implements DataObject {
    
    private QuotationHeaderPOJO quotationHeaderPOJO ; 
    private Collection<QuotationDetailsPOJO> quotationDetailsPOJOs;
    

    public QuotationHeaderPOJO getQuotationHeaderPOJO() {
        return quotationHeaderPOJO;
    }

    public void setQuotationHeaderPOJO(QuotationHeaderPOJO quotationHeaderPOJO) {
        this.quotationHeaderPOJO = quotationHeaderPOJO;
    }

    public Collection<QuotationDetailsPOJO> getQuotationDetailsPOJOs() {
        return quotationDetailsPOJOs;
    }

    public void setQuotationDetailsPOJOs(Collection<QuotationDetailsPOJO> quotationDetailsPOJOs) {
        this.quotationDetailsPOJOs = quotationDetailsPOJOs;
    }
}
