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
package ISRetail.services;

import ISRetail.Helpers.ConvertDate;
import ISRetail.Helpers.XMLCalendar;
import ISRetail.SalesOrderBilling.SalesOrderBillingDO;
import ISRetail.SalesOrderBilling.SalesOrderBillingPOJO;

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
import in.co.titan.invoicecreation.DTInvoiceCreation.PaymentDetails;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

/**
 *
 * @author Muthukumar
 */
public class ServiceInvoiceCreationwebservice implements Webservice {

    private int updateStatusCreditNote;
    private int count = 0;
    private int updateStatus;
    private String status = null;

    public String execute(DataObject obj, String updateType) {
        String updatedInISR = "false";
        SalesOrderBillingPOJO header = null;
        ArrayList<SOLineItemPOJO> invoiceorderdetailspojoobjlist = null;
        MsdeConnection msdeconn = new MsdeConnection();
        Connection con = msdeconn.createConnection();
        in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceCondition invoiceCondition;
        Vector<AdvanceReceiptDetailsPOJO> v = null;
        Vector<AdvanceReceiptPOJO> adv = null;
        ArticleDO articleDO = null;

        /*Retrieving DIVISION from articleMaster*/
        // ArticleMasterDO  armdo=new ArticleMasterDO();








        if (obj instanceof ServiceInvoicecreationBean) {
            try {
                //String status = null;
                ServiceInvoicecreationBean masterBean = new ServiceInvoicecreationBean();
                masterBean = (ServiceInvoicecreationBean) obj;
                header = masterBean.getHeader();
                invoiceorderdetailspojoobjlist = masterBean.getSOLineItemPOJOs();
                v = (Vector<AdvanceReceiptDetailsPOJO>) masterBean.getAdvanceReceiptDetailsPOJOs();
                try {
                    adv = (Vector<AdvanceReceiptPOJO>) masterBean.getAdvanceReceiptPOJO();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                in.co.titan.invoicecreation.MIOBASYNInvoiceCreationService service = new in.co.titan.invoicecreation.MIOBASYNInvoiceCreationService();
                //in.co.titan.invoicecreation.MIOBASYNInvoiceCreation port = service.getMIOBASYNInvoiceCreationPort();
                in.co.titan.invoicecreation.MIOBASYNInvoiceCreation port = service.getHTTPSPort();
                // TODO initialize WS operation arguments here
                Map<String, Object> ctxt = ((BindingProvider) port).getRequestContext();
                //ctxt.put(JAXWSProperties.CONNECT_TIMEOUT, connectionTimeout);
                ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, 60000);
                ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
                ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());
               // ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port()+"/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_VersionManagement&version=3.0&Sender.Service=x&Interface=x%5Ex");
                ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port()+ "/XISOAPAdapter/MessageServlet?senderParty=&senderService=BS_TITAN_POS&receiverParty=&receiverService=&interface=MI_OB_ASYN_InvoiceCreation&interfaceNamespace=http://titan.co.in/InvoiceCreation");
                in.co.titan.invoicecreation.DTInvoiceCreation mtInvoiceCreation = new in.co.titan.invoicecreation.DTInvoiceCreation();
                // TODO process result here
                mtInvoiceCreation.setBillType("CASH");
                mtInvoiceCreation.setCreditSaleRef(header.getCreditreference());
                mtInvoiceCreation.setServiceFlag("X");
                mtInvoiceCreation.setPOSOrderStatus(header.getOrderStatus());
                mtInvoiceCreation.setSITESEARCH(header.getStorecode());
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
                    java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(header.getCreateddate());
                    Calendar createdTime = ConvertDate.getSqlTimeFromString(header.getCreatedtime());
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
                mtInvoiceCreation.setCreatedName(header.getCreatedby());
                mtInvoiceCreation.setFYear(String.valueOf(header.getFiscalyear()));
                mtInvoiceCreation.setPOSInvoiceNO(header.getInvoiceno());
                mtInvoiceCreation.setSalesOrderNO(header.getSalesorderno());
                mtInvoiceCreation.setPOSOrderStatus(header.getOrderStatus());
                ArrayList<in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceCondition> billingConditions = new ArrayList<in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceCondition>();
                ArrayList<in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceItem> billingItems = new ArrayList<in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceItem>();
                ArrayList<in.co.titan.invoicecreation.DTInvoiceCreation.PaymentDetails> billingPayments = new ArrayList<in.co.titan.invoicecreation.DTInvoiceCreation.PaymentDetails>();
                if (invoiceorderdetailspojoobjlist != null) {
                    Iterator billingDetailsList = invoiceorderdetailspojoobjlist.iterator();
                    if (billingDetailsList != null) {
                        in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceItem soItem = null;
                        SOLineItemPOJO detailsPojo = null;
                        String priority = null, regular = null;
//                        in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceItem invoiceCondition;
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
                                    soItem.setBatch(detailsPojo.getBatchId());
                                }
                                soItem.setSLNO(Integer.toString(i));
                                soItem.setExpDate(detailsPojo.getExpDate());
                                if (detailsPojo.getUCP() != null) {
                                    if (detailsPojo.getUCP().getDummyconditiontype() != null) {
                                        invoiceCondition = setSoConDition(detailsPojo.getUCP(), i, ++j, "U", detailsPojo.getMaterial().toUpperCase());
                                        if (invoiceCondition != null) {
                                            billingConditions.add(invoiceCondition);
                                        }
                                    }
                                }
                                if (detailsPojo.getDiscountSelected() != null) {
                                    if (detailsPojo.getDiscountSelected().getDummyconditiontype() != null) {

                                        invoiceCondition = setSoConDition(detailsPojo.getDiscountSelected(), i, ++j, "D", detailsPojo.getMaterial().toUpperCase());
                                        if (invoiceCondition != null) {
                                            billingConditions.add(invoiceCondition);
                                        }
                                    }
                                }
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



                                if (detailsPojo.isOtherChargesPresent()) {
                                    if (detailsPojo.getOtherCharges() != null) {
                                        Iterator iterator = detailsPojo.getOtherCharges().iterator();
                                        while (iterator.hasNext()) {
                                            ConditionTypePOJO conditionTypePOJO = (ConditionTypePOJO) iterator.next();
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
                    Iterator iter = v.iterator();
                    Object payobj = new Object();
                    AdvanceReceiptDetailsPOJO advpojo;
                    while (iter.hasNext()) {
                        count = 1;
                        advpojo = (AdvanceReceiptDetailsPOJO) iter.next();
                        PaymentDetails itemtable = new in.co.titan.invoicecreation.DTInvoiceCreation.PaymentDetails();
                        itemtable.setPOSAdvRecNO(header.getInvoiceno());
                        try {
                            itemtable.setSiteID(header.getStorecode());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //   itemtable.setPOSAdvRecNO("TINDA00001");
                        //    itemtable.setRemarks("Advsave");
                        if (advpojo.getModeofpayment().equalsIgnoreCase("CHQ")) {
                            advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                            itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
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
                            itemtable.setBank(advpojo.getBank());
                            itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                            /*branchname*/ itemtable.setBrType(String.valueOf(advpojo.getBranchname()));


                            itemtable.setPaymentMode(advpojo.getModeofpayment());
                        } else if (advpojo.getModeofpayment().equalsIgnoreCase("CAS")) {

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
                            //      itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));
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
                                itemtable.setGVForm(advpojo.getGVFrom());

                                itemtable.setGVTO(advpojo.getGVTO());
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

                        }
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
                        }
                        mtInvoiceCreation.getPaymentDetails().add(itemtable);
                        count = count + 1;
                    }
                    Iterator iteradv = adv.iterator();
                    AdvanceReceiptPOJO advpojoobj;
                    AdvanceReceiptDetailsPOJO adv1 = null;
                    while (iteradv.hasNext()) {
                        count = count + 1;
                        advpojoobj = (AdvanceReceiptPOJO) iteradv.next();
                        PaymentDetails itemtableadv = new in.co.titan.invoicecreation.DTInvoiceCreation.PaymentDetails();
                        int srno = v.indexOf(adv1) + 1;
                        itemtableadv.setSrNO(new BigInteger(String.valueOf(count)));
                        itemtableadv.setAmount(new BigDecimal(String.valueOf(advpojoobj.getAmount())));
                        itemtableadv.setPaymentMode("ADV");
                        itemtableadv.setSiteID(advpojoobj.getStorecode());
                        mtInvoiceCreation.getPaymentDetails().add(itemtableadv);
                    }

                    if (header.getExcessReftype() != null) {
                        if (header.getExcessReftype().toString().length() > 0) {
                            ExcessPayment itemtableExces = new in.co.titan.invoicecreation.DTInvoiceCreation.ExcessPayment();
                            if (header.getExcessReftype().toString().equals("EXCESS AMOUNT")) {
                                itemtableExces.setFlag("C");
                            }
                            if (header.getExcessReftype().toString().equals("ADV EXCESS")) {
                                itemtableExces.setFlag("A");
                            }
//                            if (header.getExcessReftype().toString().equals("CRN EXCESS")) {
//                                itemtableExces.setFlag("C");
//                            }
//                            
//                            if (header.getExcessReftype().toString().equals("GFV EXCESS")) {
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
                            mtInvoiceCreation.getExcessPayment().add(itemtableExces);

                        }
                    }
                }

                //Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                System.out.println("service order Invoice "+mtInvoiceCreation.getSITESEARCH());
                if(mtInvoiceCreation.getSITESEARCH()!=null && mtInvoiceCreation.getSITESEARCH().trim().length()>0){
                    port.miOBASYNInvoiceCreation(mtInvoiceCreation);
                    Map responseContext = ((BindingProvider) port).getResponseContext();
                    Integer responseCode = (Integer) responseContext.get(MessageContext.HTTP_RESPONSE_CODE);

                    if (responseCode.intValue() == 200) {
                        status = "true";
                    } else {
                        status = "false";
                    }    
                }else{
                    status = "false";
                }
                //End of Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 

                //Code commented by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                /*port.miOBASYNInvoiceCreation(mtInvoiceCreation);
                Map responseContext = ((BindingProvider) port).getResponseContext();
                Integer responseCode = (Integer) responseContext.get(MessageContext.HTTP_RESPONSE_CODE);

                if (responseCode.intValue() == 200) {
                    status = "true";
                } else {
                    status = "false";
                }*/
//                if (result != null) {
//                    status = result.getSAPBillingNO();
//                    //status=result.getSAPBillingNO();
//                    SalesOrderBillingDO SOBDOobj = new SalesOrderBillingDO();
//                    try {
//                        PreparedStatement stmtUpdate;
//                        stmtUpdate = con.prepareStatement("UPDATE tbl_billingheader SET saprefno = ?,accsaprefno=? where invoiceno=?");
//                        stmtUpdate.setString(1, result.getSAPBillingNO());
//                        stmtUpdate.setString(2, result.getPayDocNO());
//                        stmtUpdate.setString(3, header.getInvoiceno());
//                        updateStatus = stmtUpdate.executeUpdate();
//                    } catch (SQLException s) {
//                        s.printStackTrace();
//                    }
//                    if (result.getCreditNoteDocNO() != null) {
//                        if (result.getCreditNoteDocNO().toString().length() > 0) {
//                            try {
//                                PreparedStatement stmtUpdate;
//                                stmtUpdate = con.prepareStatement("UPDATE tbl_creditnote SET referencesapid = ? where refsalesorderno=? and status='OPEN'");
//                                stmtUpdate.setString(1, result.getCreditNoteDocNO());
//                                stmtUpdate.setString(2, header.getSalesorderno());
//                                updateStatusCreditNote = stmtUpdate.executeUpdate();
//                            } catch (Exception ed) {
//                                ed.printStackTrace();
//                            }
//                        }
//                    }
//                }
            } catch (Exception ex) {
                ex.printStackTrace();
                // TODO handle custom exceptions here
                return "false";
            }

        }

        return status;
    }

    public in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceCondition setSoConDition(ConditionTypePOJO conditionTypePOJO, int refLineItemNo, int condLineItemNo, String typeOfCondition, String matrialcode) {
        in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceCondition sOCondition = null;
        DecimalFormat df = new DecimalFormat("0.00");
        if (conditionTypePOJO != null) {
            sOCondition = new in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceCondition();
            sOCondition.setRefLineItem(String.valueOf(refLineItemNo));
            sOCondition.setCondLineItemNO(String.valueOf(condLineItemNo));
            //  if(conditionTypePOJO.getPromotionId()!=null)
            //   sOCondition.setPromotionID(conditionTypePOJO.getPromotionId());
            sOCondition.setFreeGoodsCat(conditionTypePOJO.getFreeGoodsCategory());
            sOCondition.setPOSCondType(conditionTypePOJO.getDummyconditiontype());
            sOCondition.setAmount(new BigDecimal(df.format(conditionTypePOJO.getValue())));
            sOCondition.setCalcType(conditionTypePOJO.getCalculationtype());
            sOCondition.setNetAmount(new BigDecimal(df.format(conditionTypePOJO.getNetAmount())));
            sOCondition.setTypeOfCondition(typeOfCondition);
            sOCondition.setItemCode(matrialcode);


        }
        return sOCondition;
    }

    public in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceCondition setRoundoff(SalesOrderBillingPOJO SOBPOJOobj, int condLineItemNo, String roundOffCondType) {
        in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceCondition sOCondition = null;
        DecimalFormat df = new DecimalFormat("0.00");
        if (SOBPOJOobj != null) {
            sOCondition = new in.co.titan.invoicecreation.DTInvoiceCreation.InvoiceCondition();
            sOCondition.setRefLineItem(String.valueOf(SOBPOJOobj.getLineitemno()));
            sOCondition.setCondLineItemNO(String.valueOf(condLineItemNo));
            //  if(conditionTypePOJO.getPromotionId()!=null)
            //     sOCondition.setPromotionID(conditionTypePOJO.getPromotionId());
            //sOCondition.setFreeGoodsCat(conditionTypePOJO.getFreeGoodsCategory());
            sOCondition.setPOSCondType(roundOffCondType);
            //sOCondition.setAmount(new BigDecimal(df.format(SOBPOJOobj.getRoundoff())));
            // sOCondition.setCalcType(conditionTypePOJO.getCalculationtype());
            sOCondition.setNetAmount(new BigDecimal(df.format(SOBPOJOobj.getRoundoff())));
            sOCondition.setTypeOfCondition("R");
            sOCondition.setItemCode(SOBPOJOobj.getMatrialcode());


        }
        return sOCondition;
    }
}
