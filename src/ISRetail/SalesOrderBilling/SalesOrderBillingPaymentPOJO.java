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
 * This is a POJO class for SaleOrder Billing Payment
 * 
 * 
 * 
 */

package ISRetail.SalesOrderBilling;

import java.sql.Date;


public class SalesOrderBillingPaymentPOJO {
    private int slno;
    private String advancerecepitno;
    private int docdate;
    private Double amount;

    public int getSlno() {
        return slno;
    }

    public void setSlno(int slno) {
        this.slno = slno;
    }

    public String getAdvancerecepitno() {
        return advancerecepitno;
    }

    public void setAdvancerecepitno(String advancerecepitno) {
        this.advancerecepitno = advancerecepitno;
    }



    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getDocdate() {
        return docdate;
    }

    public void setDocdate(int docdate) {
        this.docdate = docdate;
    }

}
