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
 * This class is used to Create a Direct Billing Web Service Call
 * 
 * 
 */
package ISRetail.billing;

import ISRetail.Helpers.ConvertDate;
import ISRetail.Helpers.XMLCalendar;
import ISRetail.Webservices.ConditionTypePOJO;
import ISRetail.Webservices.DataObject;

import ISRetail.Webservices.Webservice;
import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.article.ArticleDO;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.paymentdetails.AdvanceReceiptDetailsPOJO;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.salesorder.SOLineItemPOJO;
import ISRetail.utility.db.PopulateData;
import ISRetail.utility.validations.Validations;
import com.sun.xml.ws.client.BindingProviderProperties;
import in.co.titan.quickbilling.DTQuickBilling.ExcessPayment;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

public class Quickbillingwebservices implements Webservice {

    private int updateStatusCreditNote;
    private String status = null;
    private int updateStatus;
    BillingDO BDOobj = new BillingDO();

    public String execute(DataObject obj, String updateType) {
        MsdeConnection msdeconn = new MsdeConnection();
        Connection con = msdeconn.createConnection();
        BillingHeaderPOJO header = null;
        ArticleDO articleDO = null;
        ArrayList<SOLineItemPOJO> invoiceorderdetailspojoobjlist = null;
        Vector<AdvanceReceiptDetailsPOJO> v = null;
        in.co.titan.quickbilling.DTQuickBilling.SOCondition invoiceCondition;
        Properties institutionNames = null;
        PopulateData populateData = null;
        in.co.titan.quickbilling.MIOBASYNQuickBillingService service;
        in.co.titan.quickbilling.MIOBASYNQuickBilling port;
        in.co.titan.quickbilling.DTQuickBilling mtQuickBilling;
        Map<String, Object> ctxt;
        boolean ucpcheck = true;
        if (obj instanceof DirectBillingBean) {
            DirectBillingBean masterBean = new DirectBillingBean();
            masterBean = (DirectBillingBean) obj;
            header = masterBean.getHeader();
            invoiceorderdetailspojoobjlist = masterBean.getSOLineItemPOJOs();
            v = (Vector<AdvanceReceiptDetailsPOJO>) masterBean.getAdvanceReceiptDetailsPOJOs();
            populateData = new PopulateData();
            institutionNames = populateData.populateCreditsalereference(con, null);
            try {

                service = new in.co.titan.quickbilling.MIOBASYNQuickBillingService();
//                port = service.getMIOBASYNQuickBillingPort();
                // port = service.getHTTPPort();

                port = service.getHTTPSPort();
                System.out.println("Port No in QuickBIlling on 2nd Sep" + port);

                // TODO initialize WS operation arguments here
                ctxt = ((BindingProvider) port).getRequestContext();
                ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, 60000);
                mtQuickBilling = new in.co.titan.quickbilling.DTQuickBilling();
                ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
                ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());
                ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port() + "/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_QuickBilling&version=3.0&Sender.Service=BS_TITAN_POS&Interface=http%3A%2F%2Ftitan.co.in%2FQuickBilling%5EMI_OB_ASYN_QuickBilling");
                mtQuickBilling.setCompanyCode(Getcompanycode());
                mtQuickBilling.setSaleOrderNO(header.getSaleorderno());
                mtQuickBilling.setSITESEARCH(header.getStorecode());
                mtQuickBilling.setPOSInvoiceNO(header.getInvoiceno());
                mtQuickBilling.setCustomerNO(header.getCustomercode());
                mtQuickBilling.setDataSheetNo(header.getDatasheetno());
                mtQuickBilling.setInstiID(header.getCreditsalereference());
                mtQuickBilling.setFYear(String.valueOf(header.getFiscalyear()));
                mtQuickBilling.setDeliverySite(header.getStorecode());
                mtQuickBilling.setCreatedName(header.getCreatedby());
                mtQuickBilling.setPOSOrderStatus(header.getOrderStatus());
                if (Validations.isFieldNotEmpty(header.getReferencePromoOrder())) {
                    mtQuickBilling.setReferencePromoOrder(header.getReferencePromoOrder());
                }

                if (Validations.isFieldNotEmpty(header.getCreditsalereference())) {
                    String institutionID = (String) institutionNames.get(header.getCreditsalereference());
                    mtQuickBilling.setCreditSaleRef(institutionID);
                }
                mtQuickBilling.setSaleType(header.getSaletype());
                try {
                    if (header.getInvoicedate() != 0) {
                        java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(header.getInvoicedate());
                        if (followDate != null) {
                            XMLCalendar xmlDate = new XMLCalendar(followDate);
                            if (xmlDate != null) {
                                mtQuickBilling.setDocumentDate((xmlDate));
                            }

                        }
                    }
                } catch (Exception e) {
                    System.out.println("DocumentDate date not Set" + e);
                }
                try {
                    java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(header.getCreateddate());
                    Calendar createdTime = ConvertDate.getSqlTimeFromString(header.getCreatedtime());
                    if (createdDate != null) {
                        XMLCalendar xmlDate = new XMLCalendar(createdDate);
                        if (createdTime != null) {
                            xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                            if (xmlDate != null) {
                                mtQuickBilling.setCreatedTime(xmlDate);
                            }
                        }
                        if (xmlDate != null) {
                            mtQuickBilling.setCreatedDate(xmlDate);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mtQuickBilling.setMembershipCardNo(header.getFreefield1());
                mtQuickBilling.setReferalVoucherNo(header.getFreefield2());
                //mtQuickBilling.setFree3(header.getFreefield3());
                //mtQuickBilling.setFree4(String.valueOf(header.getFreefield4()));
                //mtQuickBilling.setFree5(String.valueOf(header.getFreefield5()));
                // added for ULP developmenet in POS
                mtQuickBilling.setUniLoyaltyNo(header.getFreefield3());
                mtQuickBilling.setExLoyaltyNo(header.getFreefield4());
                mtQuickBilling.setExLoyaltyType(header.getFreefield5());
                mtQuickBilling.setVouRedmNo(header.getVistaRefValNo());
                if (Validations.isFieldNotEmpty(header.getcustname())) {//Added by surekha k for saleorder service
                    mtQuickBilling.setCustomerName(header.getcustname());
                    System.err.println("mtQuickBilling.getCustomerName:" + mtQuickBilling.getCustomerName());
                }
                if (Validations.isFieldNotEmpty(header.getMobileno())) {
                    mtQuickBilling.setMobileNo(header.getMobileno());
                    System.err.println("mtQuickBilling.getMobileNo:" + mtQuickBilling.getMobileNo());
                }
                if (Validations.isFieldNotEmpty(header.getGst_no())) {//end of code added by
                    mtQuickBilling.setGSTINNo(header.getGst_no());
                    System.err.println("mtQuickBilling.getGSTINNo:" + mtQuickBilling.getGSTINNo());
                }
                // Code Added by BALA for  empid on 10.10.2017
                mtQuickBilling.setEmpID(header.getEmpid());
                if (header.getEmpid() != null) {
                    mtQuickBilling.setEmpID(header.getEmpid());
                }
                // Code Added by BALA for  empid on 10.10.2017

                if (Validations.isFieldNotEmpty(header.getRefname())) {
                    mtQuickBilling.setReferralName(header.getRefname());
                }
                if (Validations.isFieldNotEmpty(header.getRefmobileno())) {
                    mtQuickBilling.setReferralMobileNo(header.getRefmobileno());
                }
                if (Validations.isFieldNotEmpty(header.getRefinvoiceno())) {
                    mtQuickBilling.setRefInvNo(header.getRefinvoiceno());
                }

                ArrayList<in.co.titan.quickbilling.DTQuickBilling.SOCondition> billingConditions = new ArrayList<in.co.titan.quickbilling.DTQuickBilling.SOCondition>();
                ArrayList<in.co.titan.quickbilling.DTQuickBilling.SOItem> billingItems = new ArrayList<in.co.titan.quickbilling.DTQuickBilling.SOItem>();
                ArrayList<in.co.titan.quickbilling.DTQuickBilling.PaymentDetails> billingPayments = new ArrayList<in.co.titan.quickbilling.DTQuickBilling.PaymentDetails>();
                if (invoiceorderdetailspojoobjlist != null) {
                    Iterator billingDetailsList = invoiceorderdetailspojoobjlist.iterator();
                    if (billingDetailsList != null) {
                        in.co.titan.quickbilling.DTQuickBilling.SOItem soItem = null;
                        SOLineItemPOJO detailsPojo = null;
                        String priority = null, regular = null;
//                        in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceItem invoiceCondition;
                        int i = 0;
                        DecimalFormat df = new DecimalFormat("#0.00");

                        while (billingDetailsList.hasNext()) {
                            int j = 0;

                            detailsPojo = (SOLineItemPOJO) billingDetailsList.next();
                            if (detailsPojo != null) {
                                if (i == 0) {
                                    String division = new ArticleDO().getDivisionByMaterial(con, invoiceorderdetailspojoobjlist.get(0).getMaterial());
                                    mtQuickBilling.setDivision(String.valueOf(division));/*pending*/
                                }
                                soItem = new in.co.titan.quickbilling.DTQuickBilling.SOItem();
                                BigDecimal amt = new BigDecimal(detailsPojo.getNetamount());
                                //soItem.setItemNO(Integer.toString(++i));
                                soItem.setPOSLineItem(new BigInteger(Integer.toString(++i)));
                                soItem.setItemcode(detailsPojo.getMaterial().toUpperCase());
                                String marchendising = BDOobj.getMerchCatbymaterialcode(con, detailsPojo.getMaterial());
                                detailsPojo.setMdseCategory(marchendising);
                                if (new BillingDO().isGvArticle(con, detailsPojo)) {
                                    soItem.setGVFlag("X");
                                    soItem.setGVStatus("OPEN");
                                    try {
                                        if (detailsPojo.getExpDate() != null) {
                                            int datenumeric = ConvertDate.getDateNumeric(detailsPojo.getExpDate(), "dd/MM/yyyy");
                                            java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(datenumeric);
                                            if (followDate != null) {
                                                XMLCalendar xmlDate = new XMLCalendar(followDate);
                                                if (xmlDate != null) {
                                                    soItem.setExpDate(xmlDate.toString());
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    soItem.setExpDate(detailsPojo.getExpDate());
                                }
                                if (detailsPojo.getBatchId() != null) {
                                    soItem.setBatchId(detailsPojo.getBatchId().toUpperCase());
                                }

                                soItem.setQuantity(new BigInteger(String.valueOf(detailsPojo.getQuantity())));
                                soItem.setStyleConsultant(detailsPojo.getStyleConsultant());
                                soItem.setTaxableValue(new BigDecimal(df.format(detailsPojo.getTaxableValue())));
                                soItem.setFOC(detailsPojo.getFoc());
                                soItem.setEmployeeNoDiscount(detailsPojo.getDiscountRefNo());

//                                System.err.println("******************* "+ucpvalue);
                                if (detailsPojo.getUCP() != null && detailsPojo.getUCP().getNetAmount() != 0) {// Code edited by BALA - ucp value validation added
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
                                            billingConditions.add(invoiceCondition);
                                        }
                                    }
                                }
                                if (detailsPojo.getTaxDetails() != null) {
                                    Iterator iterator = detailsPojo.getTaxDetails().iterator();
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
                                if (detailsPojo.getPromotionSelected() != null) {
                                    if (Validations.isFieldNotEmpty(detailsPojo.getPromotionSelected().getPromotionID())) {
                                        soItem.setPromotionID(detailsPojo.getPromotionSelected().getPromotionID());
                                    }
                                }
                                soItem.setEmpID(detailsPojo.getLineItemEmpid());//Added by Balachander V on 1.1.2020
                                //added by charan for audiology
                                soItem.setAudioUniqueID(detailsPojo.getAudioUniqueID());

                                //added by charan for audiology
                                soItem.setAudioRPTA(detailsPojo.getAudioRPTA());

                                //added by charan for audiology
                                soItem.setAudioLPTA(detailsPojo.getAudioLPTA());

                                //added by charan for audiology
                                soItem.setAudioProvDiag(detailsPojo.getAudioProvDiag());
                                billingItems.add(soItem);
                            }
                        }
                        if (header.getRoundoff() > 0 || header.getRoundoff() < 0) {
                            articleDO = new ArticleDO();
                            invoiceCondition = setRoundoff(header, articleDO.getRoundOffCondType(con));
                            if (invoiceCondition != null) {
                                if (Validations.isFieldNotEmpty(invoiceCondition.getPOSCondtype())) {
                                    billingConditions.add(invoiceCondition);
                                }
                            }
                        }
                    }
                    mtQuickBilling.getSOItem().addAll(billingItems);

                    mtQuickBilling.getSOCondition().addAll(billingConditions);

                    Iterator iter = v.iterator();
                    Object payobj = new Object();

                    AdvanceReceiptDetailsPOJO advpojo;

                    while (iter.hasNext()) {

                        advpojo = (AdvanceReceiptDetailsPOJO) iter.next();

                        in.co.titan.quickbilling.DTQuickBilling.PaymentDetails itemtable = new in.co.titan.quickbilling.DTQuickBilling.PaymentDetails();

                        itemtable.setPOSAdvRecNO(header.getInvoiceno());
                        try {
                            itemtable.setSiteID(header.getStorecode());
                        } catch (Exception e) {
                            System.out.println(e);
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
                                System.out.println("instrument date not Set" + e);
                            }
                            itemtable.setBank(advpojo.getBank());
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            /*branchname*/ itemtable.setBrType(String.valueOf(advpojo.getBranchname()));

                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("CAS")) {
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));

                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("INS")) {
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("COD")) {//22.09.2015 Thangaraj
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("HCAS")) {//22.09.2015 Thangaraj
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("CRC")) {
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));

                            itemtable.setNO(advpojo.getInstrumentno());
                            try {
                                itemtable.setSiteID(advpojo.getStorecode());
                            } catch (Exception e) {

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

                            }

                            itemtable.setAuthCode(advpojo.getAuthorizationcode());
                            itemtable.setBank(advpojo.getBank());
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            /*cardtype*/ itemtable.setBrType(String.valueOf(advpojo.getCardtype()));
                            if (Validations.isFieldNotEmpty(advpojo.getValidationtext())) {//added by charan for titan co brand
                                itemtable.setCNText(advpojo.getValidationtext());
                            }
                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                            if (Validations.isFieldNotEmpty(advpojo.getAuthorizedperson())) {
                                itemtable.setLPAppNos(advpojo.getAuthorizedperson());
                            }
                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("BAJ")) {
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));

                            itemtable.setNO(advpojo.getInstrumentno());
                            try {
                                itemtable.setSiteID(advpojo.getStorecode());
                            } catch (Exception e) {

                            }

                            itemtable.setAuthCode(advpojo.getAuthorizationcode());
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());

                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("PTM")) {
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));

                            itemtable.setNO(advpojo.getInstrumentno());
                            try {
                                itemtable.setSiteID(advpojo.getStorecode());
                            } catch (Exception e) {

                            }
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());

                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("HCC")) {
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));

                            itemtable.setNO(advpojo.getInstrumentno());
                            try {
                                itemtable.setSiteID(advpojo.getStorecode());
                            } catch (Exception e) {

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

                            }

                            itemtable.setAuthCode(advpojo.getAuthorizationcode());
                            itemtable.setBank(advpojo.getBank());
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            /*cardtype*/ itemtable.setBrType(String.valueOf(advpojo.getCardtype()));

                            itemtable.setPaymentMode(advpojo.getModeofpayment());

                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("CRN")) {
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
                                System.out.println("instrument date not Set" + e);
                            }
                            itemtable.setCNCRAmount(new BigDecimal(String.valueOf(advpojo.getCreditamount())));
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setCNFYear(new BigInteger(String.valueOf(advpojo.getFiscalyear())));
                            /*CNreference*/ itemtable.setCNText(advpojo.getCreditnotereference());

                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("DBC")) {
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
                                System.out.println("instrument date not Set" + e);
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
                            itemtable.setNO(advpojo.getInstrumentno());
                            itemtable.setELName(advpojo.getEmployeename());
                            itemtable.setELDept(advpojo.getDepartment());
                            itemtable.setELLetRef(advpojo.getLetterreference());
                            /*Authorperson*/ itemtable.setELAuth(String.valueOf(advpojo.getAuthorizedperson()));
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));

                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                            itemtable.setAuthCode(advpojo.getAuthorizationcode());//Added by Balachander V on 16.2.2022 suggested by Naveen to send the Tenure period in Authorization code filed
                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("FRC")) {
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                            /*currencytype*/ itemtable.setBrType(String.valueOf(advpojo.getCurrencytype()));
                            itemtable.setFEEXRate(new BigDecimal(String.valueOf(advpojo.getExchangerate())));
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

                                itemtable.setGVTo(advpojo.getGVTO().toUpperCase());
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
                                System.out.println("instrument date not Set" + e);
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
                                System.out.println("instrument date not Set" + e);
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
                            // set aditional fields
                            itemtable.setGVForm(advpojo.getGVFrom());
                            itemtable.setGVTo(advpojo.getGVTO());
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
                            itemtable.setOtherCard(advpojo.getOthercardtype());//Added by Balachander V on 5.2.2020 to send CardType for GFH payment mode
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
                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("TNU")) {//Added by Balachander V to send the Tata Neu Point details to SAP
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                            //itemtable.setNo(advpojo.getInstrumentno());
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
                                System.out.println("instrument date not Set" + e);
                            }
                            itemtable.setLPPoints(String.valueOf(advpojo.getLoyalitypoints()));
                            itemtable.setLPRedPoints(String.valueOf(advpojo.getRedeemedpoints()));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        } //Code Ended by Balachander V for new payment mode on 10.07.2019
                        else if (Validations.isFieldNotEmpty(advpojo.getModeofpayment())) {//Code Added  by Balachander V for Auto payment mode in POS on 18.4.2020
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                            itemtable.setNO(advpojo.getInstrumentno());
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        }//Code Ended by Balachander V for Auto payment mode in POS on 18.4.2020
                        mtQuickBilling.getPaymentDetails().add(itemtable);
                    }
                    if (header.getExcessReftype() != null) {
                        if (header.getExcessReftype().toString().length() > 0) {
                            if (header.getCreditNoteno() != null && header.getCreditNoteno().length() > 0) {
                                ExcessPayment itemtableExces = new in.co.titan.quickbilling.DTQuickBilling.ExcessPayment();
//                            if (header.getExcessReftype().toString().equals("CHQ EXCESS")) {
//                                itemtableExces.setFlag("C");
//                            }
                                if (header.getExcessReftype().toString().equals("EXCESS AMOUNT")) {
                                    itemtableExces.setFlag("C");
                                }
//                            if (header.getExcessReftype().toString().equals("ADV EXCESS")) {
//                                itemtableExces.setFlag("A");
//                            }
//                            if (header.getExcessReftype().toString().equals("CRN EXCESS")) {
//                                itemtableExces.setFlag("C");
//                            }

//                            if (header.getExcessReftype().toString().equals("EXCESS AMOUNT")) {
//                                itemtableExces.setFlag("C");
//                            }                            
////                            if (header.getExcessReftype().toString().equals("GFV EXCESS")) {
////                                itemtableExces.setFlag("G");
////                            }
//                            if (header.getExcessReftype().toString().equals("EXCESS AMOUNT")) {
//                                itemtableExces.setFlag("G");
//                            }                            
                                itemtableExces.setAmount(new BigDecimal(String.valueOf(header.getExcessamount())));
                                itemtableExces.setSiteID(header.getStorecode());
                                itemtableExces.setSrNO(new BigInteger(String.valueOf(1)));
                                itemtableExces.setPOSAdvRecNO(header.getCreditNoteno());
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
                                mtQuickBilling.getExcessPayment().add(itemtableExces);
                            }
                            //************************************************

                            // NR credit note details capturing coded on July 7th 2010
                            if (header.getNrcreditnoteno() != null && header.getNrcreditnoteno().length() > 0) {
                                ExcessPayment itemtableExces1 = new in.co.titan.quickbilling.DTQuickBilling.ExcessPayment();

                                if (header.getExcessReftype().toString().equals("EXCESS AMOUNT")) {
                                    itemtableExces1.setFlag("C");
                                }

                                itemtableExces1.setAmount(new BigDecimal(String.valueOf(header.getNrexcessamt())));
                                itemtableExces1.setSiteID(header.getStorecode());
                                if (header.getCreditNoteno() != null && header.getCreditNoteno().length() > 0) {
                                    itemtableExces1.setSrNO(new BigInteger(String.valueOf(2)));//hard coded
                                } else {
                                    itemtableExces1.setSrNO(new BigInteger(String.valueOf(1)));//hard coded
                                }
                                itemtableExces1.setPOSAdvRecNO(header.getNrcreditnoteno());
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
                                mtQuickBilling.getExcessPayment().add(itemtableExces1);
                            }
                            //End of NR credit note details capturing  
                        }
                    }
                    //Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                    System.out.println("Quick Billing Storecode " + mtQuickBilling.getSITESEARCH());
                    //if(mtQuickBilling.getSITESEARCH()!=null && mtQuickBilling.getSITESEARCH().trim().length()>0&&mtQuickBilling.getDivision()!=null&&mtQuickBilling.getDivision().trim().length()>0){
                    System.out.println("**********************************************" + mtQuickBilling.getCompanyCode());

                    if (Validations.isFieldNotEmpty(mtQuickBilling.getSITESEARCH()) && Validations.isFieldNotEmpty(mtQuickBilling.getDivision()) && ucpcheck) {
                        if (mtQuickBilling.getCompanyCode().equalsIgnoreCase("DTIL") || mtQuickBilling.getCompanyCode().equalsIgnoreCase("TIL")) {
                            // port.miOBASYNQuickBilling(mtQuickBilling);
                            port.miOBASYNQuickBilling(mtQuickBilling);
                            Map responseContext = ((BindingProvider) port).getResponseContext();
                            Integer responseCode = (Integer) responseContext.get(MessageContext.HTTP_RESPONSE_CODE);
                            if (responseCode.intValue() == 200) {
                                status = "true";
                            } else {
                                System.out.println("Check");
                                status = "false";
                            }
                        } else {
                            System.out.println("company");
                            status = "false";
                        }
                    } else {
                        System.out.println("Faild");
                        status = "false";
                    }
                    //End of Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 

                    //Code commented by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                    /*port.miOBASYNQuickBilling(mtQuickBilling);
                    Map responseContext = ((BindingProvider) port).getResponseContext();
                    Integer responseCode = (Integer) responseContext.get(MessageContext.HTTP_RESPONSE_CODE);
                    if (responseCode.intValue() == 200) {
                        status = "true";
                    } else {
                        status = "false";
                    }*/
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return status;
    }

    public in.co.titan.quickbilling.DTQuickBilling.SOCondition setSoConDition(ConditionTypePOJO conditionTypePOJO, int refLineItemNo, int condLineItemNo, String typeOfCondition, String matrialcode) {
        in.co.titan.quickbilling.DTQuickBilling.SOCondition sOCondition = null;
        DecimalFormat df = new DecimalFormat("0.00");
        if (conditionTypePOJO != null) {
            sOCondition = new in.co.titan.quickbilling.DTQuickBilling.SOCondition();
            sOCondition.setRefLineItem(new BigInteger(Integer.toString(refLineItemNo)));
            sOCondition.setCondLineItemNO(new BigInteger(Integer.toString(condLineItemNo)));
            if (conditionTypePOJO.getPromotionId() != null) {
                sOCondition.setPromotionID(conditionTypePOJO.getPromotionId());
            }
            sOCondition.setFreeGoodsCat(conditionTypePOJO.getFreeGoodsCategory());
            sOCondition.setPOSCondtype(conditionTypePOJO.getDummyconditiontype());
            sOCondition.setAmount(new BigDecimal(df.format(conditionTypePOJO.getValue())));
            sOCondition.setCalcType(conditionTypePOJO.getCalculationtype());
            sOCondition.setNetAmount(new BigDecimal(df.format(conditionTypePOJO.getNetAmount())));
            sOCondition.setTypeOfCondition(typeOfCondition);
            sOCondition.setItemCode(matrialcode);
            if (Validations.isFieldNotEmpty(conditionTypePOJO.getPromotionLineno())) {
                sOCondition.setPromotionLineNo(String.valueOf(conditionTypePOJO.getPromotionLineno()));
            }
            sOCondition.setPromotionGroupID(String.valueOf(conditionTypePOJO.getPromotionRandomNo()));
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

        }
        return sOCondition;
    }

    public in.co.titan.quickbilling.DTQuickBilling.SOCondition setRoundoff(BillingHeaderPOJO SOBPOJOobj, String roundOffCondType) {
        in.co.titan.quickbilling.DTQuickBilling.SOCondition sOCondition = null;
        DecimalFormat df = new DecimalFormat("0.00");
        if (SOBPOJOobj != null) {
            sOCondition = new in.co.titan.quickbilling.DTQuickBilling.SOCondition();
            sOCondition.setRefLineItem(new BigInteger(Integer.toString(SOBPOJOobj.getLineitemno())));
            sOCondition.setPOSCondtype(roundOffCondType);
            sOCondition.setNetAmount(new BigDecimal(df.format(SOBPOJOobj.getRoundoff())));
            sOCondition.setTypeOfCondition("R");
            sOCondition.setItemCode(SOBPOJOobj.getMatrialcode());
        }
        return sOCondition;
    }

    public String Getcompanycode() {
        String companycode = null;
        try {
            MsdeConnection msdeconn = new MsdeConnection();
            Connection con = msdeconn.createConnection();
            SiteMasterDO SMDOobj = new SiteMasterDO();
            companycode = SMDOobj.getSiteCompanyCode(con);
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return companycode;
    }
}
