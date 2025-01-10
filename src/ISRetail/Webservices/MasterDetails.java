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
 * This is a class to get the DataObject for all Transactions for calling the Webservices
 * 
 * 
 * 
 */
package ISRetail.Webservices;

import ISRetail.CashReceipt.CashReceiptCancellationBean;
import ISRetail.CashReceipt.CashReceiptCreationBean;
import ISRetail.CashReceipt.CashReceiptDO;
import ISRetail.CashReceipt.CashReceiptPOJO;
import ISRetail.GiftCardSelling.GiftCardCancellationBean;
import ISRetail.GiftCardSelling.GiftCardSellingBean;
import ISRetail.quotations.QuotationsBean;
import ISRetail.quotations.QuotationsDetailsDO;
import ISRetail.quotations.QuotationsHeaderDO;
import ISRetail.SalesOrderBilling.InvoiceCancellationBean;
import ISRetail.SalesOrderBilling.InvoicecreationBean;
import ISRetail.SalesOrderBilling.SalesOrderBillingDO;
import ISRetail.SalesOrderBilling.SalesOrderBillingPOJO;
import ISRetail.billing.BillingDO;
import ISRetail.billing.BillingHeaderPOJO;
import ISRetail.billing.DirectBillingBean;
import ISRetail.cashpayout.CashPayoutBean;
import ISRetail.cashpayout.CashPayoutDO;
import ISRetail.creditnote.CreditNoteDO;
import ISRetail.creditnote.CreditNotePOJO;
import ISRetail.creditnote.SaleorderCancellationBean;
import ISRetail.salesorder.SOLineItemPOJO;
import ISRetail.paymentdetails.AdvanceReceiptCancellationBean;
import ISRetail.paymentdetails.AdvanceReceiptBean;
import ISRetail.salesorder.SaleOrderMasterBean;
import ISRetail.customer.CustomerMasterBean;
import ISRetail.customer.CustomerMasterDO;
import ISRetail.customer.FamilyDetailDO;
import ISRetail.inquiry.ClinicalHistoryDO;
import ISRetail.inquiry.ClinicalHistoryPOJO;
import ISRetail.inquiry.ContactLensDO;
import ISRetail.inquiry.ContactLensPOJO;
import ISRetail.inquiry.InquiryDO;
import ISRetail.inquiry.InquiryMasterBean;
import ISRetail.inquiry.InquiryPOJO;
import ISRetail.inquiry.PrescriptionDO;
import ISRetail.inquiry.PrescriptionPOJO;
import ISRetail.GiftCardSelling.GiftCardSellingDO;
import ISRetail.GiftCardSelling.GiftCardSellingPOJO;
import ISRetail.creditnote.NRCreditNotePOJO;
import ISRetail.paymentdetails.AdvanceReceiptDO;
import ISRetail.paymentdetails.AdvanceReceiptDetailsDO;
import ISRetail.paymentdetails.AdvanceReceiptDetailsPOJO;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.salesorder.SalesOrderDetailsDO;
import ISRetail.salesorder.SalesOrderHeaderDO;
import ISRetail.salesorder.SalesOrderHeaderPOJO;
import ISRetail.salesreturns.AcknowledgementFocSparesPOJO;
import ISRetail.salesreturns.AcknowledgementHeaderDO;
import ISRetail.salesreturns.AcknowledgementHeaderPOJO;
import ISRetail.salesreturns.AcknowledgementItemDO;
import ISRetail.salesreturns.SalesReturnHeaderDO;
import ISRetail.salesreturns.SalesReturnHeaderPOJO;
import ISRetail.salesreturns.SalesReturnMasterBean;
import ISRetail.services.ServiceAdvanceCreattionBean;
import ISRetail.services.ServiceInvoicecreationBean;
import ISRetail.services.ServiceOrderBean;
import ISRetail.services.ServiceorderCancellationBean;
import ISRetail.services.ServicesDetailDO;
import ISRetail.services.ServicesHeaderDO;
import ISRetail.services.ServicesHeaderPOJO;
import ISRetail.utility.validations.Validations;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class MasterDetails {

    /**
     * To get the Customer Data Object
     */
    public static DataObject getCusotmer(String customerCode, Connection con) throws SQLException {
        {
            CustomerMasterDO customerDo = null;
            CustomerMasterBean customer = null;
            DataObject dataObject = null;
            try {
                customer = new CustomerMasterBean();
                customerDo = new CustomerMasterDO();
                // getting the customer details 
                customer.setCustomer(customerDo.getCustomer(con, customerCode));
                // getting the family details
                customer.setFamily(new FamilyDetailDO().getFamilyDetalisByCustomer(con, customerCode));
                // assining the customer to the dataobject
                dataObject = customer;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                customerDo = null;
                customer = null;
            }
            // returning  the values 
            return dataObject;
        }

    }

    /**
     * To get the Inquiry Object
     */
    public static DataObject getInquiry(Connection con, String inquiryNumber) throws SQLException {

        // initing the variables
        InquiryPOJO inqPojo = null;
        ClinicalHistoryPOJO cliniPojo = null;
        PrescriptionPOJO presPojo = null;
        ContactLensPOJO clensPojo = null;
        InquiryMasterBean masterBean = null;
        DataObject obj = null;
        //Connection con = null ;
        PrescriptionDO prDo = null;
        ContactLensDO clDo = null;
        InquiryDO inDo = null;
        try {
            // getting the clinical histlry details
            cliniPojo = ClinicalHistoryDO.getClinicalHistoryByClinicalNumber(inquiryNumber, "INQ", con);
            prDo = new PrescriptionDO();
            // getting the prescription 
            presPojo = prDo.getPrescriptionByInquiryNo(inquiryNumber, "INQ", con);
            clDo = new ContactLensDO();
            // getting the contact lens details 

            clensPojo = clDo.getContactlensByInquiryNo(inquiryNumber, "INQ", con);
            inDo = new InquiryDO();
            // getting the eniquiry
            inqPojo = inDo.getInquiryByInqNo(con, inquiryNumber);
            // getting all the values to the MasteBean, which contain the whole inquiry details
            masterBean = new InquiryMasterBean();
            masterBean.setClensPojo(clensPojo);
            masterBean.setCliniPojo(cliniPojo);
            masterBean.setInqPojo(inqPojo);
            masterBean.setPresPojo(presPojo);

            //assigining the masterbean to the dataobject for standard
            obj = masterBean;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            inqPojo = null;
            cliniPojo = null;
            presPojo = null;
            clensPojo = null;
            prDo = null;
            clDo = null;
            inDo = null;
        }
        // returning the dataObject     
        return obj;
    }// end of getInquiry 

    /**
     * To get the Saleorder Data Object
     */
    public static DataObject getSaleOrder(Connection con, String saleOrder, String forcerelease) throws SQLException {
        ClinicalHistoryPOJO cliniPojo = null;
        PrescriptionPOJO presPojo = null;
        ContactLensPOJO clensPojo = null;
        SaleOrderMasterBean masterBean = null;
        PrescriptionDO prDo = null;
        ContactLensDO clDo = null;
        SalesOrderHeaderPOJO salesorderHeader = null;
        ArrayList<SOLineItemPOJO> salesOrderDetails = null;
        SalesOrderDetailsDO orderDetail;
        SalesOrderHeaderDO header;
        // getting the salesOrderHeaderDetails 
        DataObject obj = null;
        try {
            orderDetail = new SalesOrderDetailsDO();
            header = new SalesOrderHeaderDO();
            if (forcerelease != null) {
                if (forcerelease.equalsIgnoreCase("X")) {
                    salesorderHeader = header.getCreditSaleReferenceDetails(con, saleOrder);
                    masterBean = new SaleOrderMasterBean();
                    masterBean.setSalesOrderHeader(salesorderHeader);
                    obj = masterBean;
                    return obj;
                }
            } else {
                try {
                    salesorderHeader = header.getSaleOrderHeaderBySaleOrderNo(con, saleOrder);
                    salesOrderDetails = orderDetail.getSaleOrderDetailsBySaleOrderNoForWebService(con, saleOrder);
                    // getting the clinical histlry details
                    cliniPojo = ClinicalHistoryDO.getClinicalHistoryByClinicalNumber(saleOrder, "SO", con);
                    prDo = new PrescriptionDO();
                    // getting the prescription 
                    presPojo = prDo.getPrescriptionByInquiryNo(saleOrder, "SO", con);
                    clDo = new ContactLensDO();
                    // getting the contact lens details 
                    clensPojo = clDo.getContactlensByInquiryNo(saleOrder, "SO", con);

                    // setting the values to the master bean
                    masterBean = new SaleOrderMasterBean();
                    masterBean.setClensPojo(clensPojo);
                    masterBean.setCliniPojo(cliniPojo);
                    masterBean.setPresPojo(presPojo);
                    masterBean.setSalesOrderHeader(salesorderHeader);
                    masterBean.setSalesOrderDetails(salesOrderDetails);
                    //   masterBean.setPlantobj(new PlantDetails(con));
                    obj = masterBean;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cliniPojo = null;
            presPojo = null;
            clensPojo = null;
            prDo = null;
            clDo = null;
            salesorderHeader = null;
            salesOrderDetails = null;
            orderDetail = null;
            header = null;
        }
        return obj;
    }

    /**
     * To get the Advance Receipt Data Object
     */
    public static DataObject getAdvanceReceipt(Connection con, String advancereceiptno) throws SQLException {
        {
            AdvanceReceiptDO advheaderDo = null;
            AdvanceReceiptBean advrecpt = null;
            DataObject dataObject = null;
            //String searchstmt1 = "select tbl_advancereceiptheader.*,tbl_salesorderheader.customercode,tbl_salesorderheader.releasestatus,tbl_salesorderheader.creditsalereference,tbl_salesorderheader.saletype from tbl_advancereceiptheader left outer join tbl_salesorderheader on tbl_advancereceiptheader.refno = tbl_salesorderheader.saleorderno where documentno='" + advancereceiptno + "'";
            String searchstmt1 = "select tbl_advancereceiptheader.*,tbl_salesorderheader.customercode,tbl_salesorderheader.releasestatus,tbl_salesorderheader.creditsalereference,tbl_salesorderheader.saletype,tbl_salesorderheader.releasedate from tbl_salesorderheader left outer join tbl_advancereceiptheader on tbl_advancereceiptheader.refno = tbl_salesorderheader.saleorderno where documentno='" + advancereceiptno + "' union select tbl_advancereceiptheader.*, tbl_serviceorderheader.customercode,'','','',tbl_serviceorderheader.serviceorderdate from tbl_serviceorderheader left outer join tbl_advancereceiptheader  on tbl_advancereceiptheader.refno = tbl_serviceorderheader.serviceorderno where documentno='" + advancereceiptno + "'";
            String searchstmt = "select * from tbl_paymentdetails where documentno='" + advancereceiptno + "'";
            Vector advlist;
            try {
                advrecpt = new AdvanceReceiptBean();
                advheaderDo = new AdvanceReceiptDO();
                // getting the header details                 
                advrecpt.setAdvanceReceiptPOJO(advheaderDo.getAdvanceReceiptListforthread(con, searchstmt1));
                // getting the payment details            
                advlist = new Vector();
                advrecpt.setAdvanceReceiptDetailsPOJOs(new AdvanceReceiptDetailsDO().getAdvanceReceiptDetailList(con, advlist, searchstmt));
                dataObject = advrecpt;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                advheaderDo = null;
                searchstmt = null;
                searchstmt1 = null;
            }
            // returning  the values 
            return dataObject;
        }
    }

    /**
     * To get the Advance Receipt Data Object for Service Order
     */
    public static DataObject getServiceAdvanceReceipt(Connection con, String advancereceiptno) throws SQLException {
        {
            ServicesDetailDO advheaderDo = null;
            ServiceAdvanceCreattionBean advrecpt = null;
            String searchstmt1 = "select tbl_advancereceiptheader.*,tbl_serviceorderheader.customercode from tbl_advancereceiptheader left outer join tbl_serviceorderheader on tbl_advancereceiptheader.refno =tbl_serviceorderheader.serviceorderno where documentno='" + advancereceiptno + "'";
            String searchstmt = "select * from tbl_paymentdetails where documentno='" + advancereceiptno + "'";
            DataObject dataObject = null;
            try {
                advrecpt = new ServiceAdvanceCreattionBean();
                advheaderDo = new ServicesDetailDO();
                // getting the header details                 
                advrecpt.setAdvanceReceiptPOJO(advheaderDo.getAdvanceReceiptListforthread(con, searchstmt1));
                // getting the payment details            
                Vector advlist = new Vector();
                advrecpt.setAdvanceReceiptDetailsPOJOs(new AdvanceReceiptDetailsDO().getAdvanceReceiptDetailList(con, advlist, searchstmt));
                dataObject = advrecpt;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                advheaderDo = null;
                searchstmt = null;
                searchstmt1 = null;
            }

            // returning  the values 
            return dataObject;

        }

    }

    /**
     * To get the Advance Receipt Cancellation Data Object
     */
    public static DataObject getAdvanceReceiptCancellation(Connection con, String advancereceiptno) throws SQLException {
        {
            AdvanceReceiptDO advheaderDo = null;
            AdvanceReceiptCancellationBean advrecptcan = null;
            DataObject dataObject = null;
            try {
                advrecptcan = new AdvanceReceiptCancellationBean();
                advheaderDo = new AdvanceReceiptDO();
                // getting the header details 
                advrecptcan.setAdvanceReceiptPOJO(advheaderDo.getAdvanceReceiptCancelList(con, advancereceiptno));
                // assining the customer to the  detail
                dataObject = advrecptcan;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                advheaderDo = null;
            }

            // returning  the values 
            return dataObject;

        }

    }

    /**
     * To get the Cash Payout Data Object
     */
    public static DataObject getCashPayout(Connection con, String cashpayoutno) throws SQLException {
        {
            CashPayoutDO caspayoutDo = null;
            CashPayoutBean caspyt = null;
            DataObject dataObject = null;
            try {
                caspyt = new CashPayoutBean();
                caspayoutDo = new CashPayoutDO();
                // getting the header details 
                caspyt.setCashpayoutpojo(caspayoutDo.getCashPayoutListforthread(con, cashpayoutno));           // getting the cashpayout details
                Vector caslist = new Vector();
                caspyt.setCashpayoutdetailspojo(new CashPayoutDO().getCashpayoutdetailsforThread(cashpayoutno, caslist, con));
                // assining the customer to the  detail
                dataObject = caspyt;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                caspayoutDo = null;
            }

            // returning  the values 
            return dataObject;

        }

    }

    /**
     * To get the Saleorder Cancellation Advance Receipt Cancellation Data Object
     */
    public static DataObject getSaleOrderAdvanceReceiptCancelation(Connection con, String saleOrder, String creditnoteNo) throws SQLException {

        SaleorderCancellationBean masterBean = null;
        DataObject obj = null;
        SalesOrderHeaderPOJO salesorderHeader = null;
        CreditNotePOJO creditNotePOJO = null;
        NRCreditNotePOJO nRCreditNotePOJO = null;
        CreditNoteDO creditnotedoobj = new CreditNoteDO();
        SalesOrderHeaderDO header;
        AdvanceReceiptDO advanceReceiptDO;
        try {
            creditnotedoobj = new CreditNoteDO();
            //Code added on May 26 th 2010
            // nRCreditNotePOJO=new NRCreditNotePOJO();
            //End of code added on May 26th 2010
            header = new SalesOrderHeaderDO();
            advanceReceiptDO = new AdvanceReceiptDO();
            if (creditnoteNo != null) {
                creditNotePOJO = creditnotedoobj.getCreditNoteDetailsBysaleorderNo(con, creditnoteNo, "RF");
                if (creditNotePOJO != null) {
                    nRCreditNotePOJO = creditnotedoobj.getNRCreditNoteDetailsByRefNo(con, creditNotePOJO.getRefno(), creditNotePOJO.getReftype(), "NR");
                } else {
                    nRCreditNotePOJO = creditnotedoobj.getNRCreditNoteDetailsBysaleorderNo(con, creditnoteNo, "NR");
                }
                salesorderHeader = header.getSaleordercancellationDetailsBySaleorderNo(con, saleOrder);
            } else if (saleOrder != null) {
                salesorderHeader = header.getSaleordercancellationDetailsBySaleorderNo(con, saleOrder);
            }
            masterBean = new SaleorderCancellationBean();
            masterBean.setSalesOrderHeader(salesorderHeader);
            masterBean.setCreditNotePojo(creditNotePOJO);
            masterBean.setNRCreditNotePOJO(nRCreditNotePOJO);
            masterBean.setAdvanceReceiptPOJOs(advanceReceiptDO.getAdvanceCancelBasedOnSaleOrderNo(con, saleOrder));
            obj = masterBean;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            salesorderHeader = null;
            advanceReceiptDO = null;
            creditNotePOJO = null;
            creditnotedoobj = null;
            header = null;
        }
        return obj;
    }

    /**
     * To get the Serviceorder Cancellation Advance Receipt Cancellation Data Object
     */
    public static DataObject getServiceOrderAdvanceReceiptCancelation(Connection con, String saleOrder, String creditnoteNo) throws SQLException {

        ServiceorderCancellationBean masterBean = null;
        DataObject obj = null;
        ServicesHeaderPOJO servicesorderHeader = null;
        CreditNotePOJO creditNotePOJO = null;
        NRCreditNotePOJO nRCreditNotePOJO = null;
        CreditNoteDO creditnotedoobj = new CreditNoteDO();
        ServicesHeaderDO header;
        AdvanceReceiptDO advanceReceiptDO;
        try {
            creditnotedoobj = new CreditNoteDO();
            nRCreditNotePOJO = new NRCreditNotePOJO();
            header = new ServicesHeaderDO();
            advanceReceiptDO = new AdvanceReceiptDO();
            if (creditnoteNo != null) {
                creditNotePOJO = creditnotedoobj.getCreditNoteDetailsBysaleorderNo(con, creditnoteNo, "RF");
                nRCreditNotePOJO = creditnotedoobj.getNRCreditNoteDetailsBysaleorderNo(con, creditnoteNo, "NR");
                servicesorderHeader = header.getServiceordercancellationDetailsBySaleorderNo(con, saleOrder);
            } else if (saleOrder != null) {
                servicesorderHeader = header.getServiceordercancellationDetailsBySaleorderNo(con, saleOrder);
            }
            masterBean = new ServiceorderCancellationBean();
            masterBean.setServiceOrderHeader(servicesorderHeader);
            masterBean.setCreditNotePojo(creditNotePOJO);
            masterBean.setNRCreditNotePOJO(nRCreditNotePOJO);
            masterBean.setAdvanceReceiptPOJOs(advanceReceiptDO.getAdvanceCancelBasedOnSaleOrderNo(con, saleOrder));
            obj = masterBean;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            servicesorderHeader = null;
            creditNotePOJO = null;
            creditnotedoobj = null;
            header = null;
            advanceReceiptDO = null;
        }
        return obj;
    }

    /**
     * To get the Invoice Data Object
     */
    public static DataObject getInvoice(Connection con, String Invoice, String CreditNote) throws SQLException {
        SalesOrderBillingDO advheaderDo = null;
        InvoicecreationBean masterBean = null;
        DataObject obj = null;
        SalesOrderBillingPOJO InvoiceHeader = null;
        ArrayList<SOLineItemPOJO> invoiceDetails = null;
        SalesOrderBillingDO header;
        try {

            advheaderDo = new SalesOrderBillingDO();
            header = new SalesOrderBillingDO();

            InvoiceHeader = header.getHeaderForWebservice(con, Invoice, CreditNote);
            invoiceDetails = header.getSaleOrderDetailsByInvoiceno(con, Invoice);

            masterBean = new InvoicecreationBean();

            masterBean.setHeader(InvoiceHeader);
            masterBean.setSOLineItemPOJOs(invoiceDetails);
            //masterBean.setPlantobj(new PlantDetails(con));
            String searchstmt = "select * from tbl_paymentdetails where documentno='" + Invoice + "'";
            Vector advlist = new Vector();
            masterBean.setAdvanceReceiptDetailsPOJOs(new AdvanceReceiptDetailsDO().getAdvanceReceiptDetailList(con, advlist, searchstmt));
            String searchstmt1 = "select * from tbl_advancereceiptheader where refno='" + InvoiceHeader.getSalesorderno() + "' and cancelledstatus='N'";
            Vector advlistadv = new Vector();
            masterBean.setAdvanceReceiptPOJO(advheaderDo.getAdvanceReceiptList(con, advlistadv, searchstmt1));
            obj = masterBean;
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            advheaderDo = null;
            InvoiceHeader = null;
            invoiceDetails = null;
            header = null;
        }

        return obj;
    }

    /**
     * To get the Direct Billing Data Object
     */
    public static DataObject getDirectBilling(Connection con, String Invoice, String CreditNote) throws SQLException {

        DirectBillingBean masterBean = null;
        DataObject obj = null;
        BillingHeaderPOJO InvoiceHeader = null;
        ArrayList<SOLineItemPOJO> invoiceDetails = null;
        BillingDO header;
        SalesOrderBillingDO lineiten;
        AdvanceReceiptDetailsDO advanceReceiptDetailsDO;
        String searchstmt = "select * from tbl_paymentdetails where documentno='" + Invoice + "'";
        try {
            advanceReceiptDetailsDO = new AdvanceReceiptDetailsDO();
            header = new BillingDO();
            lineiten = new SalesOrderBillingDO();
            InvoiceHeader = header.searchInvoiceNo(con, Invoice, CreditNote);
            invoiceDetails = lineiten.getSaleOrderDetailsByInvoiceno(con, Invoice);
            masterBean = new DirectBillingBean();
            masterBean.setHeader(InvoiceHeader);
            masterBean.setSOLineItemPOJOs(invoiceDetails);
            Vector advlist = new Vector();
            masterBean.setAdvanceReceiptDetailsPOJOs(advanceReceiptDetailsDO.getAdvanceReceiptDetailList(con, advlist, searchstmt));
            obj = masterBean;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            InvoiceHeader = null;
            invoiceDetails = null;
            header = null;
            lineiten = null;
            advanceReceiptDetailsDO = null;
            searchstmt = null;
        }
        return obj;
    }

    /**
     * To get the Invoice Cancellation Data Object
     */
    public static DataObject getInvoiceCancellation(Connection con, String Invoice, String creditnoteno) throws SQLException {
        InvoiceCancellationBean masterBean = null;
        DataObject obj = null;
        BillingHeaderPOJO InvoiceHeader = null;
        SalesOrderBillingDO header;
        try {
            header = new SalesOrderBillingDO();
            InvoiceHeader = header.getHeaderForWebserviceForInvoiceCancel(con, Invoice, creditnoteno);
            masterBean = new InvoiceCancellationBean();
            masterBean.setHeader(InvoiceHeader);
            obj = masterBean;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            InvoiceHeader = null;
            header = null;
        }
        return obj;
    }

    /**
     * To get the Quotation Data Object
     */
    public static DataObject getQuotation(Connection con, String quotationno) throws SQLException {
        QuotationsHeaderDO quotationsheaderDo = null;
        QuotationsBean quotionBean = null;
        DataObject dataObject = null;
        ClinicalHistoryPOJO cliniPojo = null;
        PrescriptionPOJO presPojo = null;
        ContactLensPOJO clensPojo = null;
        PrescriptionDO prDo = null;
        ContactLensDO clDo = null;
        String searchstmt1 = "select * from tbl_quotationheader where quotationno='" + quotationno + "'";
        String searchstmt = "select * from tbl_quotationlineitems where quotationno='" + quotationno + "'";
        try {
            quotionBean = new QuotationsBean();
            quotationsheaderDo = new QuotationsHeaderDO();
            quotionBean.setQuotationsHeaderPOJO(quotationsheaderDo.getQuotationListforThread(con, searchstmt1));
            // getting the payment details            
            Vector advlist = new Vector();
            quotionBean.setQuotationDetailsPOJOs(new QuotationsDetailsDO().getQuotationDetailByQuotationNumberforThread(con, quotationno));
            // getting the clinical histlry details 
            cliniPojo = ClinicalHistoryDO.getClinicalHistoryByClinicalNumber(quotationno, "QO", con);
            prDo = new PrescriptionDO();
            // getting the prescription 
            presPojo = prDo.getPrescriptionByInquiryNo(quotationno, "QO", con);
            clDo = new ContactLensDO();
            // getting the contact lens details 
            clensPojo = clDo.getContactlensByInquiryNo(quotationno, "QO", con);
            quotionBean.setClensPojo(clensPojo);
            quotionBean.setCliniPojo(cliniPojo);
            quotionBean.setPresPojo(presPojo);
            // assining the customer to the  detail
            dataObject = quotionBean;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            quotationsheaderDo = null;
            cliniPojo = null;
            presPojo = null;
            clensPojo = null;
            prDo = null;
            clDo = null;
            searchstmt = null;
            searchstmt1 = null;
        }
        // returning  the values 
        return dataObject;
    }

    /**
     * To get the Sales Return Data Object
     */
    public static DataObject getSalesReturn(Connection con, String ackNo) throws SQLException {
        SalesReturnHeaderDO saleReturnHeaderDO;
        AcknowledgementHeaderDO acknowledgementHeaderDO;
        AcknowledgementItemDO acknowledgementItemDO;
        String storeCode;
        AcknowledgementHeaderPOJO acknowledgementHeaderPOJO = null;
        ArrayList<AcknowledgementFocSparesPOJO> acknowledgementFocSparesPOJOs = null;
        SalesReturnHeaderPOJO salesReturnHeaderPOJO = null;
        ArrayList<SOLineItemPOJO> ackLineItems = null;
        CreditNotePOJO creditNotePOJO = null;
        SalesOrderHeaderDO  SalesOrderHeaderDO=null;
        NRCreditNotePOJO nRCreditNotePOJO = null;
        SalesReturnMasterBean masterBean = null;
        String salesReturnNo = null;
        DataObject obj = null;
        CreditNoteDO creditNoteDO;
        String refSaleOrderNo=null;
               try {
            if (Validations.isFieldNotEmpty(ackNo)) {
                saleReturnHeaderDO = new SalesReturnHeaderDO();
                acknowledgementHeaderDO = new AcknowledgementHeaderDO();
                acknowledgementItemDO = new AcknowledgementItemDO();
                storeCode = new SiteMasterDO().getSiteId(con);
                masterBean = new SalesReturnMasterBean();
                acknowledgementHeaderPOJO = acknowledgementHeaderDO.getAcknowledgementDetailsByAckNo(con, ackNo);
                refSaleOrderNo=acknowledgementHeaderPOJO.getRefsaleorderno();
                //ackLineItems = acknowledgementItemDO.getAcknowledgementItemDetailsByAckNo(con, ackNo, null, 0,refSaleOrderNo);
                ackLineItems = acknowledgementItemDO.getAcknowledgementItemDetailsByAckNo(con, ackNo, null, 0);
                salesReturnHeaderPOJO = saleReturnHeaderDO.getSaleReturnByAckNo(con, ackNo, storeCode);
                if (salesReturnHeaderPOJO != null) {
                    salesReturnNo = salesReturnHeaderPOJO.getSalereturnno();
                    if (Validations.isFieldNotEmpty(salesReturnNo)) {
                        creditNoteDO = new CreditNoteDO();
                        //new method and category added and used to refer to RF category credit notes July 8th 2010
                        creditNotePOJO = creditNoteDO.getCreditNoteDetailsByRefNoandCategory(con, salesReturnNo, salesReturnHeaderPOJO.getStorecode(), salesReturnHeaderPOJO.getFiscalyear());
                        //Code added on July 8th 2010
                        nRCreditNotePOJO = creditNoteDO.getNRCreditNoteDetailsByRefNo(con, salesReturnNo, "SALES RETURN", "NR");

                    }
                } else {
                    acknowledgementFocSparesPOJOs = acknowledgementItemDO.getFOCDetailsByAckNo(con, ackNo, acknowledgementHeaderPOJO.getStorecode(), acknowledgementHeaderPOJO.getFiscalyear());
                }
            }
            masterBean.setAcknowledgementHeaderPOJO(acknowledgementHeaderPOJO);
            masterBean.setAckLneItemsPojo(ackLineItems);
            masterBean.setSalesReturnHeaderPOJO(salesReturnHeaderPOJO);
            masterBean.setCreditNotePojo(creditNotePOJO);
            masterBean.setNRCreditNotePOJO(nRCreditNotePOJO);
            masterBean.setAcknowledgementFocSparesPOJOs(acknowledgementFocSparesPOJOs);
            obj = masterBean;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            saleReturnHeaderDO = null;
            acknowledgementHeaderDO = null;
            acknowledgementItemDO = null;
            storeCode = null;
            acknowledgementHeaderPOJO = null;
            acknowledgementFocSparesPOJOs = null;
            salesReturnHeaderPOJO = null;
            ackLineItems = null;
            creditNotePOJO = null;
            salesReturnNo = null;
            creditNoteDO = null;
        }
        return obj;
    }

    /**
     * To get the Service Order Data Object
     */
    public static DataObject getServiceOrder(Connection con, String serviceorderno) throws SQLException {
        ServicesHeaderDO servicesHeaderDO = null;
        ServiceOrderBean serviceOrderBean = null;
        ServicesDetailDO servicesDetailDO = null;
        DataObject dataObject = null;
        try {
            serviceOrderBean = new ServiceOrderBean();
            servicesHeaderDO = new ServicesHeaderDO();
            servicesDetailDO = new ServicesDetailDO();
            serviceOrderBean.setServicesHeaderPOJO(servicesHeaderDO.getServiceOrderByServiceOrderNo(con, serviceorderno));
            serviceOrderBean.setSOLineItemPOJOs(servicesDetailDO.getServiceDetailByServiceOrderNumber(con, serviceorderno));
            dataObject = (DataObject) serviceOrderBean;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            servicesHeaderDO = null;
            servicesDetailDO = null;
        }
        // returning  the values 
        return dataObject;

    }

    /**
     * To get the Service Order Billing Data Object
     */
    public static DataObject getServiceOrderBilling(Connection con, String Invoice, String CreditNote) throws SQLException {

        SalesOrderBillingDO advheaderDo = null;
        ServiceInvoicecreationBean masterBean = null;
        DataObject obj = null;
        SalesOrderBillingPOJO InvoiceHeader = null;
        ArrayList<SOLineItemPOJO> invoiceDetails = null;
        SalesOrderBillingDO header = null;
        String searchstmt;
        String searchstmt1;
        try {
            advheaderDo = new SalesOrderBillingDO();
            header = new SalesOrderBillingDO();
            InvoiceHeader = header.getHeaderForWebserviceServiceBilling(con, Invoice, CreditNote);
            invoiceDetails = header.getSaleOrderDetailsByInvoiceno(con, Invoice);
            searchstmt = "select * from tbl_paymentdetails where documentno='" + Invoice + "'";
            searchstmt1 = "select * from tbl_advancereceiptheader where refno='" + InvoiceHeader.getSalesorderno() + "' and cancelledstatus='N'";
            masterBean = new ServiceInvoicecreationBean();
            masterBean.setHeader(InvoiceHeader);
            masterBean.setSOLineItemPOJOs(invoiceDetails);
            Vector advlist = new Vector();
            masterBean.setAdvanceReceiptDetailsPOJOs(new AdvanceReceiptDetailsDO().getAdvanceReceiptDetailList(con, advlist, searchstmt));
            Vector advlistadv = new Vector();
            masterBean.setAdvanceReceiptPOJO(advheaderDo.getAdvanceReceiptList(con, advlistadv, searchstmt1));
            obj = masterBean;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            advheaderDo = null;
            InvoiceHeader = null;
            invoiceDetails = null;
            header = null;
            searchstmt = null;
            searchstmt1 = null;
        }
        return obj;
    }

    //Code Added on 11-01-2010 for adding gift card selling
    public static DataObject getGiftCardSelling(Connection con, String gcdocno) throws SQLException {
        GiftCardSellingBean masterBean = null;
        DataObject obj = null;
        ArrayList<AdvanceReceiptDetailsPOJO> paymentlist = null;
        GiftCardSellingDO header;
        AdvanceReceiptDetailsDO advanceReceiptDetailsDO;
        GiftCardSellingPOJO giftCardSellingPOJO;
        String searchstmt;
        String searchstmt1;
        Vector displaypaymodelist = new Vector();
        try {
            header = new GiftCardSellingDO();
            advanceReceiptDetailsDO = new AdvanceReceiptDetailsDO();
            searchstmt = "select * from tbl_giftcard_selling where gcdocumentno='" + gcdocno + "'";
            giftCardSellingPOJO = header.getGiftCardheaderforwebservices(con, searchstmt);
            masterBean = new GiftCardSellingBean();
            masterBean.setGiftCardSellingPOJO(giftCardSellingPOJO);
            searchstmt1 = "select * from tbl_paymentdetails1 where documentno='" + gcdocno + "'";
            displaypaymodelist = advanceReceiptDetailsDO.getAdvanceReceiptDetailList(con, displaypaymodelist, searchstmt1);
            masterBean.setAdvanceReceiptDetailsPOJOs(displaypaymodelist);
            obj = masterBean;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            giftCardSellingPOJO = null;
            header = null;
        }
        return obj;
    }

    public static DataObject getGiftCardCancellation(Connection con, String gcdocno) throws SQLException {
        GiftCardCancellationBean masterBean = null;
        DataObject obj = null;
        ArrayList<AdvanceReceiptDetailsPOJO> paymentlist = null;
        GiftCardSellingDO header;
        AdvanceReceiptDetailsDO advanceReceiptDetailsDO;
        GiftCardSellingPOJO giftCardSellingPOJO;
        String searchstmt;
        String searchstmt1;
        Vector displaypaymodelist = new Vector();
        try {
            header = new GiftCardSellingDO();
            advanceReceiptDetailsDO = new AdvanceReceiptDetailsDO();
            //   searchstmt= "select * from tbl_giftcard_selling where gcdocumentno='"+gcdocno+"'";
            giftCardSellingPOJO = header.getGiftCardCancelList(con, gcdocno);
            masterBean = new GiftCardCancellationBean();
            masterBean.setGiftCardSellingPOJO(giftCardSellingPOJO);
            searchstmt1 = "select * from tbl_paymentdetails1 where documentno='" + giftCardSellingPOJO.getRefno().toString() + "'";
            displaypaymodelist = advanceReceiptDetailsDO.getAdvanceReceiptDetailList(con, displaypaymodelist, searchstmt1);
            masterBean.setAdvanceReceiptDetailsPOJOs(displaypaymodelist);
            obj = masterBean;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            giftCardSellingPOJO = null;
            header = null;
        }
        return obj;
    }

    //Code Added on 12-12-2011 for Cash Receipt
    public static DataObject getCashReceiptDetails(Connection con, String advanceno) {
        CashReceiptCreationBean masterbean = null;
        DataObject obj = null;
        CashReceiptDO cashreceiptDO = null;
        AdvanceReceiptDetailsDO advanceReceiptDetailsDO;
        String searchstmt = null;
        String qrystmt = null;
        CashReceiptPOJO cashReceiptPOJO = null;
        Vector cashdetails = new Vector();
        try {
            cashreceiptDO = new CashReceiptDO();
            masterbean = new CashReceiptCreationBean();
            advanceReceiptDetailsDO = new AdvanceReceiptDetailsDO();
            searchstmt = "SELECT * FROM tbl_cashreceiptheader WHERE documentno='" + advanceno + "'";
            cashReceiptPOJO = cashreceiptDO.getCashReceiptHeaderForWebservice(con, searchstmt);
            masterbean.setCashReceiptPOJO(cashReceiptPOJO);
            qrystmt = "SELECT * FROM tbl_cashreceiptdetails WHERE documentno='" + advanceno + "'";
            cashdetails = advanceReceiptDetailsDO.getAdvanceReceiptDetailList(con, cashdetails, qrystmt);
            masterbean.setAdvanceReceiptDetailsPOJO(cashdetails);
            obj = (DataObject) masterbean;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cashReceiptPOJO = null;
            cashreceiptDO = null;
            masterbean = null;
        }
        return obj;
    }
    public static DataObject getCashReceiptCancellation(Connection con, String advanceno) {
        DataObject obj = null;
        CashReceiptCancellationBean masterbean = null;
        String queryString = null;
        try {
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }
        return obj;
    }
} 
    


