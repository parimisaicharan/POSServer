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
 * This class is used to Create a Invoice Creation Web Service Call
 * 
 * 
 */
package ISRetail.SalesOrderBilling;

import in.co.titan.invoicecreation.DTInvoiceCreation.PaymentDetails;
import javax.xml.ws.BindingProvider;
import ISRetail.Helpers.ConvertDate;
import ISRetail.Helpers.XMLCalendar;
import ISRetail.Webservices.ConditionTypePOJO;
import ISRetail.Webservices.DataObject;
import ISRetail.Webservices.Webservice;
import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.article.ArticleDO;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.paymentdetails.AdvanceReceiptDetailsPOJO;
import ISRetail.paymentdetails.AdvanceReceiptPOJO;
import ISRetail.salesorder.SOLineItemPOJO;
import ISRetail.utility.validations.Validations;
import com.sun.xml.ws.client.BindingProviderProperties;
import in.co.titan.invoicecreation.DTInvoiceCreation.ExcessPayment;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.xml.ws.handler.MessageContext;

public class InvoiceCreationwebservice implements Webservice {

    private int updateStatusCreditNote;
    private int count = 0;
    private int updateStatus;

    /**
     * To execute a call to the Invoice Creation Web service
     */
    public String execute(DataObject obj, String updateType) {

        SalesOrderBillingPOJO header = null;
        ArrayList<SOLineItemPOJO> invoiceorderdetailspojoobjlist = null;
        MsdeConnection msdeconn = new MsdeConnection();
        Connection con = msdeconn.createConnection();
        in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceCondition invoiceCondition;
        Vector<AdvanceReceiptDetailsPOJO> v = null;
        Vector<AdvanceReceiptPOJO> adv = null;
        ArticleDO articleDO = null;
        InvoicecreationBean masterBean;
        in.co.titan.invoicecreation.MIOBASYNInvoiceCreationService service;
        in.co.titan.invoicecreation.MIOBASYNInvoiceCreation port;
        Map<String, Object> ctxt;
        in.co.titan.invoicecreation.DTInvoiceCreation mtInvoiceCreation;
        java.util.Date createdDate;
        Calendar createdTime;
        ArrayList<in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceCondition> billingConditions;
        ArrayList<in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceItem> billingItems;
        ArrayList<in.co.titan.invoicecreation.DTInvoiceCreation.PaymentDetails> billingPayments;
        Iterator billingDetailsList;
        Iterator iterator;
        Iterator iterator1;
        AdvanceReceiptDetailsPOJO advpojo;
        Iterator iter;
        Object payobj;
        //COde Added by Bala on 05.10.2017 for Comfort call date and time
        java.util.Date comfortDate = null;
        Calendar comfortTime;
        //Code Ended by Bala on 05.10.2017 for Comfort call date and time
        boolean ucpcheck = true;
        if (obj instanceof InvoicecreationBean) {
            try {
                masterBean = new InvoicecreationBean();
                masterBean = (InvoicecreationBean) obj;
                header = masterBean.getHeader();
                invoiceorderdetailspojoobjlist = masterBean.getSOLineItemPOJOs();
                v = (Vector<AdvanceReceiptDetailsPOJO>) masterBean.getAdvanceReceiptDetailsPOJOs();
                try {
                    adv = (Vector<AdvanceReceiptPOJO>) masterBean.getAdvanceReceiptPOJO();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Call Web Service Operation
                service = new in.co.titan.invoicecreation.MIOBASYNInvoiceCreationService();
                //  port = service.getMIOBASYNInvoiceCreationPort();
                port = service.getHTTPSPort();
                ctxt = ((BindingProvider) port).getRequestContext();
                ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, 60000);
                ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
                ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());
                ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port() + "/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_InvoiceCreations&version=3.0&Sender.Service=BS_TITAN_POS&Interface=http%3A%2F%2Ftitan.co.in%2FInvoiceCreation%5EMI_OB_ASYN_InvoiceCreation");
                mtInvoiceCreation = new in.co.titan.invoicecreation.DTInvoiceCreation();
                mtInvoiceCreation.setBillType(header.getSaletype());
                mtInvoiceCreation.setCreditSaleRef(header.getCreditreference());
                mtInvoiceCreation.setSITESEARCH(header.getStorecode());
                mtInvoiceCreation.setPOSOrderStatus(header.getOrderStatus());
                try {
                    if (header.getInvoicedate() != 0) {
                        java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(header.getInvoicedate());
                        if (followDate != null) {
                            XMLCalendar xmlDate = new XMLCalendar(followDate);
                            if (xmlDate != null) {
                                mtInvoiceCreation.setPOSInvDate((xmlDate));
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    createdDate = ConvertDate.getUtilDateFromNumericDate(header.getCreateddate());
                    createdTime = ConvertDate.getSqlTimeFromString(header.getCreatedtime());
                    if (createdDate != null) {
                        XMLCalendar xmlDate = new XMLCalendar(createdDate);
                        if (createdTime != null) {
                            xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                            if (xmlDate != null) {
                                mtInvoiceCreation.setCreatedTime(xmlDate);
                            }
                        }
                        if (xmlDate != null) {
                            mtInvoiceCreation.setCreatedDate(xmlDate);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mtInvoiceCreation.setPOSOrderStatus(header.getOrderStatus());
                mtInvoiceCreation.setCreatedName(header.getCreatedby());
                mtInvoiceCreation.setFYear(String.valueOf(header.getFiscalyear()));
                mtInvoiceCreation.setPOSInvoiceNO(header.getInvoiceno());
                mtInvoiceCreation.setSalesOrderNO(header.getSalesorderno());
                //Code Added on 03-02-2010 for free fields
                mtInvoiceCreation.setMembershipCardNo(header.getFreefield1());

                //Referral Voucher and RefRedeemno will send depends on RefRedeemno from invoice table
//                if(Validations.isFieldNotEmpty(header.getFreefield2())){//Added by dileep - 17.04.2014
                if (Validations.isFieldNotEmpty(header.getVistaRefValNo())) {//Added by dileep - 17.04.2014
                    mtInvoiceCreation.setReferalVoucherNo(header.getFreefield2());
                }
                //mtInvoiceCreation.setFree3(header.getFreefield3());
                //mtInvoiceCreation.setFree4(String.valueOf(header.getFreefield4()));
                //mtInvoiceCreation.setFree5(String.valueOf(header.getFreefield5()));
                // added for ULP development in POS
                mtInvoiceCreation.setUniLoyaltyNo(header.getFreefield3());
                mtInvoiceCreation.setExLoyaltyNo(header.getFreefield4());
                mtInvoiceCreation.setExLoyaltyType(header.getFreefield5());
                if (Validations.isFieldNotEmpty(header.getVistaRefValNo())) {//Added by dileep - 17.04.2014
                    mtInvoiceCreation.setVouRedmNo(header.getVistaRefValNo());
                }
                //COde Added by Bala on 05.10.2017 for Comfort call date and time
                if (header.getComfortdate() != 0) {
                    try {
                        comfortDate = ConvertDate.getUtilDateFromNumericDate(header.getComfortdate());
                        comfortTime = ConvertDate.getSqlTimeFromString(header.getComforttime());
                        if (comfortDate != null) {
                            XMLCalendar xmlDate = new XMLCalendar(comfortDate);
                            if (comfortTime != null) {
                                xmlDate.setTime(comfortTime.get(Calendar.HOUR_OF_DAY), comfortTime.get(Calendar.MINUTE), comfortTime.get(Calendar.SECOND));
                                if (xmlDate != null) {
                                    mtInvoiceCreation.setComfortCallTime(xmlDate);
                                } else {
                                    mtInvoiceCreation.setComfortCallTime(xmlDate);
                                }
                            }
                            if (xmlDate != null) {
                                mtInvoiceCreation.setComfortCallDate(xmlDate);
                            } else {
                                mtInvoiceCreation.setComfortCallDate(xmlDate);
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (header.getEmpid() != null) {
                    mtInvoiceCreation.setEmpID(header.getEmpid());
                } else {
                    mtInvoiceCreation.setEmpID(header.getEmpid());
                }
                if (Validations.isFieldNotEmpty(header.getRefname())) {
                    mtInvoiceCreation.setReferralName(header.getRefname());
                }
                if (Validations.isFieldNotEmpty(header.getRefmobileno())) {
                    mtInvoiceCreation.setReferralMobileNo(header.getRefmobileno());
                }
                if (Validations.isFieldNotEmpty(header.getRefinvoiceno())) {
                    mtInvoiceCreation.setRefInvNo(header.getRefinvoiceno());
                }
                if (Validations.isFieldNotEmpty(header.getZopperInsuranceId())) {
                    mtInvoiceCreation.setInsuranceID(header.getZopperInsuranceId());
                }

                //Code Ended by Bala on 05.10.2017 for Comfort call date and time
                billingConditions = new ArrayList<in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceCondition>();
                billingItems = new ArrayList<in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceItem>();
                billingPayments = new ArrayList<in.co.titan.invoicecreation.DTInvoiceCreation.PaymentDetails>();

                if (invoiceorderdetailspojoobjlist != null) {
                    billingDetailsList = invoiceorderdetailspojoobjlist.iterator();
                    if (billingDetailsList != null) {
                        in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceItem soItem = null;
                        SOLineItemPOJO detailsPojo = null;
                        String priority = null, regular = null;
                        int i = 0;
                        DecimalFormat df = new DecimalFormat("#0.00");
                        while (billingDetailsList.hasNext()) {
                            int j = 0;
                            detailsPojo = (SOLineItemPOJO) billingDetailsList.next();
                            if (detailsPojo != null) {
                                soItem = new in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceItem();
                                BigDecimal amt = new BigDecimal(detailsPojo.getNetamount());
                                soItem.setItemNO(Integer.toString(++i));
                                soItem.setItemCode(detailsPojo.getMaterial().toUpperCase());
                                if (detailsPojo.getBatchId() != null) {
                                    soItem.setBatch(detailsPojo.getBatchId().toUpperCase());
                                }
                                soItem.setSLNO(Integer.toString(i));
                                soItem.setExpDate(detailsPojo.getExpDate());
                                soItem.setEmployeeNoDiscount(detailsPojo.getDiscountRefNo());
                                soItem.setTaxableValue(new BigDecimal(String.valueOf(detailsPojo.getTaxableValue())));

                                if (detailsPojo.getUCP() != null && detailsPojo.getUCP().getNetAmount() != 0) {
                                    if (detailsPojo.getUCP().getDummyconditiontype() != null) {
                                        invoiceCondition = setSoConDition(detailsPojo.getUCP(), i, ++j, "U", detailsPojo.getMaterial().toUpperCase());
                                        if (invoiceCondition != null) {
                                            billingConditions.add(invoiceCondition);
                                        }
                                    }
                                } else {
                                    ucpcheck = false;
                                }
                                if (detailsPojo.getDiscountSelected() != null) {
                                    if (detailsPojo.getDiscountSelected().getDummyconditiontype() != null) {

                                        invoiceCondition = setSoConDition(detailsPojo.getDiscountSelected(), i, ++j, "D", detailsPojo.getMaterial().toUpperCase());
                                        if (invoiceCondition != null) {
                                            billingConditions.add(invoiceCondition);
                                        }
                                    }
                                }

                                //Added by Dileep - 16.06.2014
                                if (detailsPojo.getULPdiscountSelected() != null) {
                                    if (detailsPojo.getULPdiscountSelected().getDummyconditiontype() != null) {

                                        invoiceCondition = setSoConDition(detailsPojo.getULPdiscountSelected(), i, ++j, "D", detailsPojo.getMaterial().toUpperCase());
                                        if (invoiceCondition != null) {
                                            billingConditions.add(invoiceCondition);
                                        }
                                    }
                                }
                                //End: Added by Dileep - 16.06.2014

                                if (detailsPojo.getOfferPromotion() != null) {
                                    if (detailsPojo.getOfferPromotion().getDummyconditiontype() != null) {

                                        invoiceCondition = setSoConDition(detailsPojo.getOfferPromotion(), i, ++j, "P", detailsPojo.getMaterial().toUpperCase());
                                        if (invoiceCondition != null) {
                                            if (invoiceCondition.getPOSCondType() != null) {
                                                billingConditions.add(invoiceCondition);
                                            }
                                        }
                                    }
                                }
                                if (detailsPojo.getTaxDetails() != null) {
                                    iterator = detailsPojo.getTaxDetails().iterator();
                                    while (iterator.hasNext()) {
                                        ConditionTypePOJO conditionTypePOJO = (ConditionTypePOJO) iterator.next();
                                        if (conditionTypePOJO != null) {
                                            if (conditionTypePOJO.getDummyconditiontype() != null) {

                                                invoiceCondition = setSoConDition(conditionTypePOJO, i, ++j, "T", detailsPojo.getMaterial().toUpperCase());
                                                if (invoiceCondition != null) {
                                                    billingConditions.add(invoiceCondition);
                                                }
                                            }
                                        }
                                    }
                                }
                                //Code added on 03-02-2010 for capturing promotionid in lineitem level
                                if (detailsPojo.getPromotionSelected() != null) {
                                    if (Validations.isFieldNotEmpty(detailsPojo.getPromotionSelected().getPromotionID())) {
                                        soItem.setPromotionID(detailsPojo.getPromotionSelected().getPromotionID());
                                    }
                                }

                                if (header.getRoundoff() > 0 || header.getRoundoff() < 0) {
                                    if (header.getLineitemno() == i) {
                                        articleDO = new ArticleDO();

                                        invoiceCondition = setRoundoff(header, ++j, articleDO.getRoundOffCondType(con));
                                        if (invoiceCondition != null) {
                                            if (Validations.isFieldNotEmpty(invoiceCondition.getPOSCondType())) {
                                                billingConditions.add(invoiceCondition);
                                            }
                                        }
                                    }
                                }
                                ConditionTypePOJO delayDiscount = new SalesOrderBillingDO().getSOCondition(con, header.getInvoiceno(), i, "E", false);
                                if (delayDiscount != null) {
                                    invoiceCondition = setSoConDition(delayDiscount, i, ++j, "E", detailsPojo.getMaterial().toUpperCase());
                                    if (invoiceCondition != null) {
                                        billingConditions.add(invoiceCondition);
                                    }
                                }

                                if (detailsPojo.isOtherChargesPresent()) {
                                    if (detailsPojo.getOtherCharges() != null) {
                                        iterator1 = detailsPojo.getOtherCharges().iterator();
                                        while (iterator1.hasNext()) {
                                            ConditionTypePOJO conditionTypePOJO = (ConditionTypePOJO) iterator1.next();
                                            if (conditionTypePOJO != null) {
                                                if (conditionTypePOJO.getDummyconditiontype() != null) {
                                                    invoiceCondition = setSoConDition(conditionTypePOJO, i, ++j, "O", detailsPojo.getMaterial().toUpperCase());
                                                    if (invoiceCondition != null) {
                                                        billingConditions.add(invoiceCondition);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                billingItems.add(soItem);
                            }
                        }

                    }
                    mtInvoiceCreation.getInvoiceItem().addAll(billingItems);
                    mtInvoiceCreation.getInvoiceCondition().addAll(billingConditions);
                    iter = v.iterator();
                    payobj = new Object();
                    count = 1;
                    while (iter.hasNext()) {

                        advpojo = (AdvanceReceiptDetailsPOJO) iter.next();

                        PaymentDetails itemtable = new in.co.titan.invoicecreation.DTInvoiceCreation.PaymentDetails();
                        itemtable.setPOSAdvRecNO(header.getInvoiceno());
                        try {
                            itemtable.setSiteID(header.getStorecode());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (advpojo.getModeofpayment().equalsIgnoreCase("CHQ")) {
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                            itemtable.setNO(advpojo.getInstrumentno());
                            try {
                                if (advpojo.getInstrumentdate() != 0) {
                                    java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                    if (followDate != null) {
                                        XMLCalendar xmlDate = new XMLCalendar(followDate);
                                        if (xmlDate != null) {
                                            itemtable.setDate(xmlDate);
                                        }

                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            itemtable.setBank(advpojo.getBank());
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setBrType(String.valueOf(advpojo.getBranchname()));

                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("CAS")) {

                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));

                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("COD")) {

                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));

                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("HCAS")) {

                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));

                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("INS")) {

                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));

                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("CRC")) {
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));

                            itemtable.setNO(advpojo.getInstrumentno());
                            try {
                                itemtable.setSiteID(header.getStorecode());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                if (advpojo.getInstrumentdate() != 0) {
                                    java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                    if (followDate != null) {
                                        XMLCalendar xmlDate = new XMLCalendar(followDate);
                                        if (xmlDate != null) {
                                            itemtable.setDate(xmlDate);
                                        }

                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (Validations.isFieldNotEmpty(advpojo.getValidationtext())) {//added by charan for titan co brand
                                itemtable.setCNText(advpojo.getValidationtext());
                            }
                            itemtable.setAuthCode(advpojo.getAuthorizationcode());
                            itemtable.setBank(advpojo.getBank());
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            /*cardtype*/ itemtable.setBrType(String.valueOf(advpojo.getCardtype()));

                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                            if(Validations.isFieldNotEmpty(advpojo.getAuthorizedperson())){
                            itemtable.setLPAppNos(advpojo.getAuthorizedperson());}
                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("HCC")) {
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));

                            itemtable.setNO(advpojo.getInstrumentno());
                            try {
                                itemtable.setSiteID(header.getStorecode());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                if (advpojo.getInstrumentdate() != 0) {
                                    java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                    if (followDate != null) {
                                        XMLCalendar xmlDate = new XMLCalendar(followDate);
                                        if (xmlDate != null) {
                                            itemtable.setDate(xmlDate);
                                        }

                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            itemtable.setAuthCode(advpojo.getAuthorizationcode());
                            itemtable.setBank(advpojo.getBank());
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            /*cardtype*/ itemtable.setBrType(String.valueOf(advpojo.getCardtype()));

                            itemtable.setPaymentMode(advpojo.getModeofpayment());

                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("BAJ")) {
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));

                            itemtable.setNO(advpojo.getInstrumentno());
                            try {
                                itemtable.setSiteID(header.getStorecode());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            itemtable.setAuthCode(advpojo.getAuthorizationcode());
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());

                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("PTM")) {
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));

                            itemtable.setNO(advpojo.getInstrumentno());
                            try {
                                itemtable.setSiteID(header.getStorecode());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());

                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("CRN")) {
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                            // itemtable.setNo(advpojo.getInstrumentno());
                            itemtable.setNO(advpojo.getInstrumentno());
                            //   itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));

                            try {
                                if (advpojo.getInstrumentdate() != 0) {
                                    java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                    if (followDate != null) {
                                        XMLCalendar xmlDate = new XMLCalendar(followDate);
                                        if (xmlDate != null) {
                                            itemtable.setDate(xmlDate);
                                        }

                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            itemtable.setCNCrAmount(new BigDecimal(String.valueOf(advpojo.getCreditamount())));
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setCNFYear(new BigInteger(String.valueOf(advpojo.getFiscalyear())));
                            /*CNreference*/ itemtable.setCNText(advpojo.getCreditnotereference());

                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("DBC")) {
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                            //itemtable.setNo(advpojo.getInstrumentno());
                            itemtable.setNO(advpojo.getInstrumentno());
                            // itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));
                            try {
                                if (advpojo.getInstrumentdate() != 0) {
                                    java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                    if (followDate != null) {
                                        XMLCalendar xmlDate = new XMLCalendar(followDate);
                                        if (xmlDate != null) {
                                            itemtable.setDate(xmlDate);
                                        }

                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            itemtable.setAuthCode(advpojo.getAuthorizationcode());
                            itemtable.setBank(advpojo.getBank());
                            /*cardtype*/ itemtable.setBrType(advpojo.getCardtype());
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));

                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("EML")) {

                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                            itemtable.setELLoanAmt(new BigDecimal(String.valueOf(advpojo.getLoanamount())));
                            // itemtable.setNo(advpojo.getInstrumentno());
                            itemtable.setNO(advpojo.getInstrumentno());
                            itemtable.setELName(advpojo.getEmployeename());
                            itemtable.setELDept(advpojo.getDepartment());
                            itemtable.setElLetRef(advpojo.getLetterreference());

                            /*Authorperson*/ itemtable.setELAuth(String.valueOf(advpojo.getAuthorizedperson()));
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));

                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                            itemtable.setAuthCode(advpojo.getAuthorizationcode());//Added by Balachander V on 16.2.2022 suggested by Naveen to send the Tenure period in Authorization code filed
                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("FRC")) {
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                            /*currencytype*/ itemtable.setBrType(String.valueOf(advpojo.getCurrencytype()));
                            itemtable.setFEExRate(new BigDecimal(String.valueOf(advpojo.getExchangerate())));
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            /*noofunits*/ itemtable.setFEUnits(new BigDecimal(String.valueOf(advpojo.getNoofunits())));

                            itemtable.setPaymentMode(advpojo.getModeofpayment());

                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("GFV")) {
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));

                            /*gvno*/  //itemtable.setNo(advpojo.getInstrumentno());
                            itemtable.setNO(advpojo.getInstrumentno());
                            if (!advpojo.getGVFrom().equalsIgnoreCase("") && !advpojo.getGVTO().equalsIgnoreCase("")) {
                                itemtable.setGVForm(advpojo.getGVFrom().toUpperCase());

                                itemtable.setGVTO(advpojo.getGVTO().toUpperCase());
                            }
                            //   itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));

                            try {
                                if (advpojo.getInstrumentdate() != 0) {
                                    java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                    if (followDate != null) {
                                        XMLCalendar xmlDate = new XMLCalendar(followDate);
                                        if (xmlDate != null) {
                                            itemtable.setDate(xmlDate);
                                        }

                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            itemtable.setGVDenAmount(new BigDecimal(String.valueOf(advpojo.getDenomination())));
                            /*validtext*/ itemtable.setGVText(advpojo.getValidationtext());
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("LOY")) {
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                            //itemtable.setNo(advpojo.getInstrumentno());
                            itemtable.setNO(advpojo.getInstrumentno());
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setLPAppNos(advpojo.getApprovalno());
                            //    itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));

                            try {
                                if (advpojo.getInstrumentdate() != 0) {
                                    java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                    if (followDate != null) {
                                        XMLCalendar xmlDate = new XMLCalendar(followDate);
                                        if (xmlDate != null) {
                                            itemtable.setDate(xmlDate);
                                        }

                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            itemtable.setLPPoints(String.valueOf(advpojo.getLoyalitypoints()));
                            itemtable.setLPRedPoints(String.valueOf(advpojo.getRedeemedpoints()));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("RSE")) {
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("GFC")) {
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                            itemtable.setNO(advpojo.getInstrumentno());
                            itemtable.setAuthCode(advpojo.getAuthorizationcode());
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                            // aditional fields
                            itemtable.setGVForm(advpojo.getGVFrom());
                            itemtable.setGVTO(advpojo.getGVTO());
                            itemtable.setGVText(advpojo.getValidationtext());

                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("FTD")) {
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                            itemtable.setNO(advpojo.getInstrumentno());
                            itemtable.setAuthCode(advpojo.getAuthorizationcode());
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());

                        } else if (advpojo.getModeofpayment().startsWith("EP")) {
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("GFH")) {
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                            itemtable.setNO(advpojo.getInstrumentno());
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("SWG")) {//Code Added by Bala for new payment mode on 05.04.2019
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                            itemtable.setNO(advpojo.getInstrumentno());
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        }//Code Ended by Bala for new payment mode on 05.04.2019
                        else if (advpojo.getModeofpayment().equalsIgnoreCase("UPI")) {//Code Added by Balachander V for new payment mode on 10.07.2019
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                            itemtable.setNO(advpojo.getInstrumentno());
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        }//Code Ended by Balachander V for new payment mode on 10.07.2019
                        else if (advpojo.getModeofpayment().equalsIgnoreCase("TNU")) {
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                            itemtable.setNO(advpojo.getInstrumentno());
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setLPAppNos(advpojo.getApprovalno());
                            try {
                                if (advpojo.getInstrumentdate() != 0) {
                                    java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                    if (followDate != null) {
                                        XMLCalendar xmlDate = new XMLCalendar(followDate);
                                        if (xmlDate != null) {
                                            itemtable.setDate(xmlDate);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            itemtable.setLPPoints(String.valueOf(advpojo.getLoyalitypoints()));
                            itemtable.setLPRedPoints(String.valueOf(advpojo.getRedeemedpoints()));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("ZPI")) {
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                            if (Validations.isFieldNotEmpty(advpojo.getInstrumentno())) {
                                itemtable.setNO(advpojo.getInstrumentno());
                            }
                            if (Validations.isFieldNotEmpty(advpojo.getLoanamount())) {
                                itemtable.setLPPoints(String.valueOf((int)advpojo.getLoanamount()));
                            }
                            if (Validations.isFieldNotEmpty(advpojo.getApprovalno())) {
                                itemtable.setLPAppNos(String.valueOf(advpojo.getApprovalno()));
                            }
                            if (Validations.isFieldNotEmpty(advpojo.getAuthorizationcode())) {
                                itemtable.setAuthCode(advpojo.getAuthorizationcode());
                            }
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        } else if (Validations.isFieldNotEmpty(advpojo.getModeofpayment())) {//Code Added  by Balachander V for Auto payment mode in POS on 18.4.2020
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                            itemtable.setNO(advpojo.getInstrumentno());
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        }//Code Ended by Balachander V for Auto payment mode in POS on 18.4.2020
                        mtInvoiceCreation.getPaymentDetails().add(itemtable);
                        count = count + 1;

                    }
                    Iterator iteradv = adv.iterator();
                    AdvanceReceiptPOJO advpojoobj;
                    AdvanceReceiptDetailsPOJO adv1 = null;
                    int var1 = v.size();
                    while (iteradv.hasNext()) {
                        var1 = var1 + 1;
                        advpojoobj = (AdvanceReceiptPOJO) iteradv.next();

                        PaymentDetails itemtableadv = new in.co.titan.invoicecreation.DTInvoiceCreation.PaymentDetails();
                        int srno = v.indexOf(adv1) + 1;

                        itemtableadv.setSrNO(new BigInteger(String.valueOf(var1)));
                        itemtableadv.setAmount(new BigDecimal(String.valueOf(advpojoobj.getAmount())));
                        itemtableadv.setPaymentMode("ADV");
                        itemtableadv.setSiteID(advpojoobj.getStorecode());
                        itemtableadv.setPOSAdvRecNO(header.getInvoiceno());
                        mtInvoiceCreation.getPaymentDetails().add(itemtableadv);
                        count = count + 1;
                    }

                    if (header.getExcessReftype() != null) {
                        if (header.getExcessReftype().toString().length() > 0) {

                            if (header.getCreditNoteNo() != null && header.getCreditNoteNo().length() > 0) {

                                ExcessPayment itemtableExces = new in.co.titan.invoicecreation.DTInvoiceCreation.ExcessPayment();
                                if (header.getExcessReftype().toString().equals("EXCESS AMOUNT")) {
                                    itemtableExces.setFlag("C");
                                }
                                if (header.getExcessReftype().toString().equals("ADV EXCESS")) {
                                    itemtableExces.setFlag("A");
                                }
//                            if (header.getExcessReftype().toString().equals("EXCESS AMOUNT")) {
//                                itemtableExces.setFlag("C");
//                            }
//                            if (header.getExcessReftype().toString().equals("EXCESS AMOUNT")) {
//                                itemtableExces.setFlag("G");
//                            }
                                itemtableExces.setAmount(new BigDecimal(String.valueOf(header.getExcessamount())));
                                itemtableExces.setSiteID(header.getStorecode());
                                itemtableExces.setSrNO(new BigInteger(String.valueOf(1)));
                                itemtableExces.setPOSAdvRecNo(header.getCreditNoteNo());
                                try {
                                    if (header.getCreditnoteexpirydate() != 0) {
                                        java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(header.getCreditnoteexpirydate());
                                        if (followDate != null) {
                                            XMLCalendar xmlDate = new XMLCalendar(followDate);
                                            if (xmlDate != null) {
                                                itemtableExces.setDate((xmlDate));
                                            }

                                        }
                                    }

                                } catch (Exception e) {
                                    System.out.println("DocumentDate date not Set" + e);
                                }
                                itemtableExces.setPaymentMode("CRN");
                                //itemtableExces.set
                                mtInvoiceCreation.getExcessPayment().add(itemtableExces);
                            }
                            //Code added on July 6th 2010 for capturing NR credit note details
                            if (header.getNrcreditnoteno() != null && header.getNrcreditnoteno().length() > 0) {

                                ExcessPayment itemtableExces1 = new in.co.titan.invoicecreation.DTInvoiceCreation.ExcessPayment();
                                if (header.getExcessReftype().toString().equals("EXCESS AMOUNT")) {
                                    itemtableExces1.setFlag("C");
                                }
                                if (header.getExcessReftype().toString().equals("ADV EXCESS")) {
                                    itemtableExces1.setFlag("A");
                                }
//                            if (header.getExcessReftype().toString().equals("EXCESS AMOUNT")) {
//                                itemtableExces.setFlag("C");
//                            }
//                            if (header.getExcessReftype().toString().equals("EXCESS AMOUNT")) {
//                                itemtableExces.setFlag("G");
//                            }
                                itemtableExces1.setAmount(new BigDecimal(String.valueOf(header.getNrexcessamt())));
                                itemtableExces1.setSiteID(header.getStorecode());
                                if (header.getCreditNoteNo() != null && header.getCreditNoteNo().length() > 0) {
                                    itemtableExces1.setSrNO(new BigInteger(String.valueOf(2)));
                                } else {
                                    itemtableExces1.setSrNO(new BigInteger(String.valueOf(1)));
                                }
                                itemtableExces1.setPOSAdvRecNo(header.getNrcreditnoteno());
                                try {
                                    if (header.getCreditnoteexpirydate() != 0) {
                                        java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(header.getCreditnoteexpirydate());
                                        if (followDate != null) {
                                            XMLCalendar xmlDate = new XMLCalendar(followDate);
                                            if (xmlDate != null) {
                                                itemtableExces1.setDate((xmlDate));
                                            }

                                        }
                                    }

                                } catch (Exception e) {
                                    System.out.println("DocumentDate date not Set" + e);
                                }
                                itemtableExces1.setPaymentMode("NCR");

                                //itemtableExces.set
                                mtInvoiceCreation.getExcessPayment().add(itemtableExces1);

                            }
                            //End of Code added on July 6th 2010 for capturing NR credit note details
                        }
                    }
                }

                //Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                System.out.println("Invoice Creation Store Code " + mtInvoiceCreation.getSITESEARCH());
                if (mtInvoiceCreation.getSITESEARCH() != null && mtInvoiceCreation.getSITESEARCH().trim().length() > 0 && ucpcheck) {

                    port.miOBASYNInvoiceCreation(mtInvoiceCreation);
                    Map responseContext = ((BindingProvider) port).getResponseContext();
                    Integer responseCode = (Integer) responseContext.get(MessageContext.HTTP_RESPONSE_CODE);
                    if (responseCode.intValue() == 200) {
                        return "true";
                    } else {
                        return "false";
                    }
                } else {
                    return "false";
                }
                //End of Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 

                //Code commented by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                /*port.miOBASYNInvoiceCreation(mtInvoiceCreation);
                Map responseContext = ((BindingProvider) port).getResponseContext();
                Integer responseCode = (Integer) responseContext.get(MessageContext.HTTP_RESPONSE_CODE);
                if (responseCode.intValue() == 200) {
                    return "true";
                } else {
                    return "false";
                }*/
            } catch (Exception ex) {
                ex.printStackTrace();

                return "false";
            } finally {
                header = null;
                invoiceorderdetailspojoobjlist = null;
                msdeconn = null;
                con = null;
                invoiceCondition = null;
                v = null;
                adv = null;
                articleDO = null;
                masterBean = null;
                service = null;
                port = null;
                ctxt = null;
                mtInvoiceCreation = null;
                createdDate = null;
                createdTime = null;
                billingConditions = null;
                billingItems = null;
                billingPayments = null;
                billingDetailsList = null;
                iterator = null;
                iterator1 = null;
                advpojo = null;
                iter = null;
            }
        } else {
            return "false";
        }

    }

    /**
     * To get the SO Condition
     */
    public in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceCondition setSoConDition(ConditionTypePOJO conditionTypePOJO, int refLineItemNo, int condLineItemNo, String typeOfCondition, String matrialcode) {
        in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceCondition sOCondition = null;
        DecimalFormat df = new DecimalFormat("0.00");
        if (conditionTypePOJO != null) {
            sOCondition = new in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceCondition();
            sOCondition.setRefLineItem(String.valueOf(refLineItemNo));
            sOCondition.setCondLineItemNO(String.valueOf(condLineItemNo));
            if (conditionTypePOJO.getPromotionId() != null) {
                sOCondition.setPromotionID((conditionTypePOJO.getPromotionId()));
            }
            sOCondition.setFreeGoodsCat(conditionTypePOJO.getFreeGoodsCategory());
            sOCondition.setPOSCondType(conditionTypePOJO.getDummyconditiontype());
            sOCondition.setAmount(new BigDecimal(df.format(conditionTypePOJO.getValue())));
            sOCondition.setCalcType(conditionTypePOJO.getCalculationtype());
            sOCondition.setNetAmount(new BigDecimal(df.format(conditionTypePOJO.getNetAmount())));
            sOCondition.setTypeOfCondition(typeOfCondition);
            sOCondition.setItemCode(matrialcode);
            //Added by Dileep - 16.06.2014
            sOCondition.setULPNo(conditionTypePOJO.getUlpno());
            if (Validations.isFieldNotEmpty(conditionTypePOJO.getLoyaltyPoints())) {
                if (conditionTypePOJO.getLoyaltyPoints() != 0) {
                    sOCondition.setLoyaltyPoints(String.valueOf(conditionTypePOJO.getLoyaltyPoints()));
                }
            }
            if (Validations.isFieldNotEmpty(conditionTypePOJO.getLoyaltyRedeemedPoints())) {
                if (conditionTypePOJO.getLoyaltyRedeemedPoints() != 0) {
                    sOCondition.setLoyaltyRedeemedPoints(String.valueOf(conditionTypePOJO.getLoyaltyRedeemedPoints()));
                }
            }
            if (Validations.isFieldNotEmpty(conditionTypePOJO.getRrno())) {
                sOCondition.setLoyaltyApprovalCode(conditionTypePOJO.getRrno());
            }

            try {
                if (Validations.isFieldNotEmpty(conditionTypePOJO.getAsOnDate())) {
                    if (conditionTypePOJO.getAsOnDate() != 0) {
                        java.util.Date asondate = ConvertDate.getUtilDateFromNumericDate(conditionTypePOJO.getAsOnDate());
                        if (asondate != null) {
                            XMLCalendar xmlDate1 = new XMLCalendar(asondate);
                            if (xmlDate1 != null) {
                                sOCondition.setAsOnDate((xmlDate1));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Loyalty points as on date not Set" + e);
            }
            //End: Added by Dileep - 16.06.2014
        }
        return sOCondition;
    }

    /**
     * To get the Round OFF Value
     */
    public in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceCondition setRoundoff(SalesOrderBillingPOJO SOBPOJOobj, int condLineItemNo, String roundOffCondType) {
        in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceCondition sOCondition = null;
        DecimalFormat df = new DecimalFormat("0.00");
        if (SOBPOJOobj != null) {
            sOCondition = new in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceCondition();
            sOCondition.setRefLineItem(String.valueOf(SOBPOJOobj.getLineitemno()));
            sOCondition.setCondLineItemNO(String.valueOf(condLineItemNo));
            sOCondition.setPOSCondType(roundOffCondType);
            sOCondition.setNetAmount(new BigDecimal(df.format(SOBPOJOobj.getRoundoff())));
            sOCondition.setTypeOfCondition("R");
            sOCondition.setItemCode(SOBPOJOobj.getMatrialcode());
        }
        return sOCondition;
    }
}
