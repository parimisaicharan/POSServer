/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.serverconsole;

/**
 *
 * @author e1775501
 */
public class InvoiceData {//added by charan

    private String invoiceNo;
    private String documentNo;

    public InvoiceData(String invoiceNo, String documentNo) {
        this.invoiceNo = invoiceNo;
        this.documentNo = documentNo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public String getDocumentNo() {
        return documentNo;
    }
}
