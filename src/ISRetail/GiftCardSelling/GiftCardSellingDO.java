/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.GiftCardSelling;

import ISRetail.Helpers.ConvertDate;
import ISRetail.Helpers.Decimalvalue;
//import ISRetail.Screens.MainForm;
import ISRetail.Helpers.XMLCalendar;
import ISRetail.Webservices.DataObject;
import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.customer.CustomerMasterDO;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.paymentdetails.AdvanceReceiptDO;
import ISRetail.paymentdetails.AdvanceReceiptDetailsDO;
import ISRetail.paymentdetails.AdvanceReceiptDetailsPOJO;
import ISRetail.paymentdetails.AdvanceReceiptPOJO;
import ISRetail.utility.validations.Validations;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import javax.jws.WebService;
import javax.swing.table.DefaultTableModel;
import javax.xml.ws.BindingProvider;
import ISRetail.Webservices.DataObject;
import ISRetail.Webservices.Webservice;
import ISRetail.Webservices.XIConnectionDetailsPojo;
import com.sun.xml.ws.client.BindingProviderProperties;
import in.co.titan.giftcardsales.DTGiftCardSales;
import in.co.titan.giftcardsales.DTGiftCardSales.ITEMPAYMENT;
//import in.co.titan.giftcardcancel.DTGiftCardCancel.ITEMPAYMENT;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import javax.mail.MessageContext;

/**
 *
 * @author Administrator
 */
public class GiftCardSellingDO implements Webservice {

    public int saveGiftcardSelling(GiftCardSellingPOJO giftCardSellingPOJO, Connection con) throws SQLException {
        int result_gcs = 0;
        PreparedStatement statement = null;
        try {
            // statement = con.prepareStatement("insert into tbl_billingheader values  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            //5 new free fields have been added in tbl_billingheader on 24-11-2009
            statement = con.prepareStatement("insert into tbl_giftcard_selling values  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            statement.setString(1, giftCardSellingPOJO.getStorecode());
            statement.setInt(2, giftCardSellingPOJO.getFiscalyear());
            statement.setString(3, giftCardSellingPOJO.getGiftcardno());
            statement.setInt(4, giftCardSellingPOJO.getGiftcarddate());
            statement.setString(5, giftCardSellingPOJO.getCustomercode());
            statement.setDouble(6, giftCardSellingPOJO.getAmount());
            statement.setString(7, giftCardSellingPOJO.getCancelledstatus());
            statement.setString(8, giftCardSellingPOJO.getCreatedby());
            statement.setInt(9, giftCardSellingPOJO.getCreateddate());
            statement.setString(10, giftCardSellingPOJO.getCreatedtime());
            statement.setString(11, giftCardSellingPOJO.getModifiedby());
            statement.setInt(12, giftCardSellingPOJO.getModifieddate());
            statement.setString(13, giftCardSellingPOJO.getModifiedtime());

            //code added by dileep - 20.09.2013
            statement.setString(14, giftCardSellingPOJO.getTransactiontype());
            statement.setString(15, giftCardSellingPOJO.getBatchid());
            statement.setString(16, giftCardSellingPOJO.getTransactionid());
            statement.setString(17, giftCardSellingPOJO.getGiftcardinvoiceno());
            statement.setInt(18, giftCardSellingPOJO.getGiftcardexpirydate());

            //End code added by dileep - 20.09.2013
            result_gcs = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            statement.close();
            statement = null;
        }
        return result_gcs;
    }

    public GiftCardSellingPOJO getSearchlist(Connection conn, String sb) {
        //    System.out.println("entry ------------------made");
        Statement pstmt = null;
        ResultSet rs = null;
        try {
            GiftCardSellingPOJO giftCardSellingPOJO = new GiftCardSellingPOJO();

            pstmt = (Statement) conn.createStatement();
            //   System.out.println("sb val----------------------:"+sb);
            rs = pstmt.executeQuery(sb);
            while (rs.next()) {

                giftCardSellingPOJO.setGiftcardno(rs.getString("giftcardno"));
                giftCardSellingPOJO.setCustomercode(rs.getString("customercode"));
                giftCardSellingPOJO.setAmount(rs.getDouble("totalamount"));
                giftCardSellingPOJO.setCancelledstatus(rs.getString("cancelledstatus"));
                return giftCardSellingPOJO;
            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (Exception ev) {
            ev.printStackTrace();
            return null;
        } finally {
            try {
                pstmt.close();
                pstmt = null;
                rs = null;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
    }

    public ArrayList getGiftCardlistforgcsearch(Connection conn, String searchstatement) {
        Statement pstmt = null;
        ResultSet rs = null;
        GiftCardSellingPOJO giftCardSellingPOJO;
        try {
            pstmt = conn.createStatement();
            rs = pstmt.executeQuery(searchstatement);
            ArrayList<GiftCardSellingPOJO> giftcardlist = new ArrayList<GiftCardSellingPOJO>();
            int i = 0;
            while (rs.next()) {
                giftCardSellingPOJO = new GiftCardSellingPOJO();
                giftCardSellingPOJO.setGiftcardno(rs.getString("giftcardno"));
                // giftCardSellingPOJO.setFiscalyear(rs.getInt("fiscalyear"));
                giftCardSellingPOJO.setCustomercode(rs.getString("customercode"));
                giftCardSellingPOJO.setGiftcarddate(rs.getInt("giftcarddate"));
                //  giftCardSellingPOJO.setCustomername(rs.getString("firstname"));
                giftCardSellingPOJO.setAmount(rs.getDouble("totalamount"));
                giftcardlist.add(giftCardSellingPOJO);
            }
            return giftcardlist;
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        } finally {
            giftCardSellingPOJO = null;
            try {
                pstmt.close();
            } catch (SQLException e) {
            }
            pstmt = null;
            rs = null;
        }

    }

    public boolean getGiftCardStatus(Connection con, String giftcardno) {
        Statement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = (Statement) con.createStatement();
            rs = pstmt.executeQuery("select giftcardno from tbl_giftcard_selling where giftcardno='" + giftcardno + "' and cancelledstatus='N'");
            while (rs.next()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                pstmt.close();
                pstmt = null;
                rs = null;
            } catch (SQLException ex) {
            }
        }
    }

    public boolean updateGiftCardStatus(Connection con, String giftcardno, String status) {
        Statement pstmtup = null;
        try {

            pstmtup = (Statement) con.createStatement();
            int up = pstmtup.executeUpdate("update tbl_giftcard_selling set cancelledstatus='" + status + "' where giftcardno='" + giftcardno + "'");

            if (up > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                pstmtup.close();
                pstmtup = null;
                //   rs = null;
            } catch (SQLException ex) {
            }
        }
    }

    public GiftCardSellingPOJO getGiftCardheaderforwebservices(Connection conn, String searchstatement, String refcreditnote, String nrcreditnote) {
        Statement pstmt = null;
        ResultSet rs = null;
        GiftCardSellingPOJO giftCardSellingPOJO = null;
        System.out.println("SEARCHSTAMT::::::::;" + searchstatement);
        try {
            pstmt = conn.createStatement();
            rs = pstmt.executeQuery(searchstatement);

            int i = 0;
            while (rs.next()) {
                giftCardSellingPOJO = new GiftCardSellingPOJO();
                giftCardSellingPOJO.setStorecode(rs.getString("storecode"));
                giftCardSellingPOJO.setFiscalyear(rs.getInt("fiscalyear"));
                giftCardSellingPOJO.setGcdocumentno(rs.getString("gcdocumentno"));
                giftCardSellingPOJO.setRefno(rs.getString("refno"));
                giftCardSellingPOJO.setGiftcardno(rs.getString("giftcardno"));
                giftCardSellingPOJO.setGiftcarddate(rs.getInt("giftcarddate"));
                giftCardSellingPOJO.setAuthorizationcode(rs.getString("authorizationcode"));
                giftCardSellingPOJO.setCustomercode(rs.getString("customercode"));
                giftCardSellingPOJO.setAmount(rs.getDouble("totalamount"));
                giftCardSellingPOJO.setCancelledstatus(rs.getString("cancelledstatus"));
                giftCardSellingPOJO.setCreatedby(rs.getString("createdby"));
                giftCardSellingPOJO.setCreateddate(rs.getInt("createddate"));
                giftCardSellingPOJO.setCreatedtime(rs.getString("createdtime"));
                giftCardSellingPOJO.setModifiedby(rs.getString("modifiedby"));
                giftCardSellingPOJO.setModifieddate(rs.getInt("modifieddate"));
                giftCardSellingPOJO.setModifiedtime(rs.getString("modifiedtime"));

                //code added by dileep - 20.09.2013
                giftCardSellingPOJO.setTransactiontype(rs.getString("transactiontype"));
                giftCardSellingPOJO.setBatchid(rs.getString("batchid"));
                giftCardSellingPOJO.setTransactionid(rs.getString("transactionid"));
                giftCardSellingPOJO.setGiftcardinvoiceno(rs.getString("giftcardinvoiceno"));
                giftCardSellingPOJO.setGiftcardexpirydate(rs.getInt("giftcardexpirydate"));
                if (refcreditnote != null && refcreditnote.length() > 0) {
                    PreparedStatement statment = conn.prepareStatement("select creditnoteno,amount,refno,reftype,expirydate from tbl_creditnote where creditnoteno=? and category='RF'");
                    statment.setString(1, refcreditnote);
                    ResultSet resultrefCredit = statment.executeQuery();
                    while (resultrefCredit.next()) {
                        giftCardSellingPOJO.setRefCreditNoteno(refcreditnote);
                        giftCardSellingPOJO.setRefExcessReftype(resultrefCredit.getString("reftype"));
                        giftCardSellingPOJO.setRefExcessamount(resultrefCredit.getDouble("amount"));
                        giftCardSellingPOJO.setRefCreditnoteexpirydate(resultrefCredit.getInt("expirydate"));
                    }
                }
                if (nrcreditnote != null && nrcreditnote.length() > 0) {
                    PreparedStatement statment = conn.prepareStatement("select creditnoteno,amount,refno,reftype,expirydate from tbl_creditnote where creditnoteno=? and category='NR'");
                    statment.setString(1, nrcreditnote);
                    ResultSet resultnrCredit = statment.executeQuery();
                    while (resultnrCredit.next()) {
                        giftCardSellingPOJO.setNrCreditNoteno(nrcreditnote);
                        giftCardSellingPOJO.setNrExcessReftype(resultnrCredit.getString("reftype"));
                        giftCardSellingPOJO.setNrExcessamount(resultnrCredit.getDouble("amount"));
                        giftCardSellingPOJO.setNrCreditnoteexpirydate(resultnrCredit.getInt("expirydate"));
                    }
                }

                //End code added by dileep - 20.09.2013
            }
            return giftCardSellingPOJO;

        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        } finally {
            giftCardSellingPOJO = null;
            try {
                pstmt.close();
            } catch (SQLException e) {
            }
            pstmt = null;
            rs = null;
        }

    }

    public GiftCardSellingPOJO getGiftCardCancelList(Connection conn, String gcdocno) {

        ResultSet rs;
        ResultSet rs1;
        Statement pstmt;
        Statement pstmt1;
        try {
            //  AdvanceReceiptPOJO advpojo = new AdvanceReceiptPOJO();
            GiftCardSellingPOJO gfspojo = new GiftCardSellingPOJO();
            pstmt = (Statement) conn.createStatement();
            pstmt1 = (Statement) conn.createStatement();
            //ResultSet rs = pstmt.executeQuery("select * from tbl_advancereceiptheader where documentno='"+advancereceiptno+"' ");
            rs = pstmt.executeQuery("select * from dbo.tbl_giftcard_selling where gcdocumentno =(select refno from dbo.tbl_giftcard_selling where gcdocumentno='" + gcdocno + "')");
            while (rs.next()) {

                gfspojo.setStorecode(rs.getString("storecode"));
                gfspojo.setFiscalyear(rs.getInt("fiscalyear"));
                gfspojo.setGcdocumentno(gcdocno);// giftcard Reversal number
                gfspojo.setRefno(rs.getString("gcdocumentno"));//giftcard creation no
                gfspojo.setGiftcardno(rs.getString("giftcardno"));
                gfspojo.setGiftcarddate(rs.getInt("giftcarddate"));
                gfspojo.setAuthorizationcode(rs.getString("authorizationcode"));
                gfspojo.setCustomercode(rs.getString("customercode"));
                gfspojo.setAmount(rs.getDouble("totalamount"));
                gfspojo.setCancelledstatus(rs.getString("cancelledstatus"));
                gfspojo.setSaprefo(rs.getString("saprefno"));
                gfspojo.setCreatedby(rs.getString("createdby"));
                gfspojo.setCreateddate(rs.getInt("createddate"));
                gfspojo.setCreatedtime(rs.getString("createdtime"));
                gfspojo.setModifiedby(rs.getString("modifiedby"));
                gfspojo.setModifieddate(rs.getInt("modifieddate"));
                gfspojo.setModifiedtime(rs.getString("modifiedtime"));

                //code added by dileep - 20.09.2013
                gfspojo.setTransactiontype(rs.getString("transactiontype"));
                gfspojo.setBatchid(rs.getString("batchid"));
                gfspojo.setTransactionid(rs.getString("transactionid"));
                gfspojo.setGiftcardinvoiceno(rs.getString("giftcardinvoiceno"));
                gfspojo.setGiftcardexpirydate(rs.getInt("giftcardexpirydate"));

                //End code added by dileep - 20.09.2013
                return gfspojo;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt1 = null;
            rs1 = null;
            pstmt = null;
            rs = null;
        }
        return null;
    }

    public int updateSapRefId(Connection con, String sapRefNo, String sapAccNo, String gcdocNo, String storeCode, int fiscalYear) {
        int result = 0;
        PreparedStatement stmtUpdate;
        try {
            stmtUpdate = con.prepareStatement("UPDATE tbl_giftcard_selling SET saprefno = ?,accsaprefno=? where gcdocumentno=? and storecode = ? and fiscalyear = ?");
            stmtUpdate.setString(1, sapRefNo);
            stmtUpdate.setString(2, sapAccNo);
            stmtUpdate.setString(3, gcdocNo);
            stmtUpdate.setString(4, storeCode);
            stmtUpdate.setInt(5, fiscalYear);
            result = stmtUpdate.executeUpdate();
        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            stmtUpdate = null;
        }
        return result;
    }

    public String execute(DataObject dataObject, String updateType) {
        GiftCardSellingBean bean = null;
        GiftCardCancellationBean bean1 = null;
        GiftCardSellingPOJO giftCardSellingPOJO = null;
        Vector<AdvanceReceiptDetailsPOJO> v = null;
        try { // Call Web Service Operation

            if (dataObject instanceof GiftCardSellingBean) {

                bean = (GiftCardSellingBean) dataObject;
                giftCardSellingPOJO = bean.getGiftCardSellingPOJO();

                v = (Vector<AdvanceReceiptDetailsPOJO>) bean.getAdvanceReceiptDetailsPOJOs();

                in.co.titan.giftcardsales.MIOBASYNGiftCardSalesService service = new in.co.titan.giftcardsales.MIOBASYNGiftCardSalesService();
                // in.co.titan.giftcardsales.MIOBASYNGiftCardSales port = service.getMIOBASYNGiftCardSalesPort();
                in.co.titan.giftcardsales.MIOBASYNGiftCardSales port = service.getHTTPSPort();

                Map<String, Object> ctxt = ((BindingProvider) port).getRequestContext();
                //ctxt.put(JAXWSProperties.CONNECT_TIMEOUT, connectionTimeout);
                ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, 60000);

                ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
                ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());
                ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port() + "/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_GiftCardSales&version=3.0&Sender.Service=X&Interface=Z%5EY");
                // TODO initialize WS operation arguments here
                in.co.titan.giftcardsales.DTGiftCardSales mtGiftCardSales = new in.co.titan.giftcardsales.DTGiftCardSales();

                mtGiftCardSales.setSITEID(giftCardSellingPOJO.getStorecode());
                mtGiftCardSales.setSITESEARCH(giftCardSellingPOJO.getStorecode());
                mtGiftCardSales.setGJAHR(String.valueOf(giftCardSellingPOJO.getFiscalyear()));
                mtGiftCardSales.setCREATNAME(giftCardSellingPOJO.getCreatedby());
                try {
                    java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(giftCardSellingPOJO.getGiftcarddate());
                    Calendar createdTime = ConvertDate.getSqlTimeFromString(giftCardSellingPOJO.getCreatedtime());
                    if (createdDate != null) {
                        XMLCalendar xmlDate = new XMLCalendar(createdDate);
                        if (createdTime != null) {
                            xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                            if (xmlDate != null) {
                                mtGiftCardSales.setCREATTIME(xmlDate);
                            }
                        }
                        if (xmlDate != null) {
                            mtGiftCardSales.setCREATDATE(xmlDate);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mtGiftCardSales.setGCDOCNUM(giftCardSellingPOJO.getGcdocumentno());
                mtGiftCardSales.setGCNUM(giftCardSellingPOJO.getGiftcardno());
                mtGiftCardSales.setAUTHCODE(giftCardSellingPOJO.getAuthorizationcode());
                mtGiftCardSales.setAMOUNT(new BigDecimal(String.valueOf(giftCardSellingPOJO.getAmount())));

                if (giftCardSellingPOJO.getCustomercode() != null) {

                    mtGiftCardSales.setCUSTOMERNO(giftCardSellingPOJO.getCustomercode());
                }

                //code added by dileep - 20.09.2013
                if (giftCardSellingPOJO.getTransactiontype() != null) {
                    mtGiftCardSales.setTRANTYPE(giftCardSellingPOJO.getTransactiontype());
                }

                if (giftCardSellingPOJO.getTransactionid() != null) {
                    mtGiftCardSales.setTRANID(giftCardSellingPOJO.getTransactionid());
                }

                if (giftCardSellingPOJO.getBatchid() != null) {
                    mtGiftCardSales.setBATCHID(giftCardSellingPOJO.getBatchid());
                }

                if (giftCardSellingPOJO.getGiftcardinvoiceno() != null) {
                    mtGiftCardSales.setGCINVNO(giftCardSellingPOJO.getGiftcardinvoiceno());
                }

                try {
                    if (giftCardSellingPOJO.getGiftcardexpirydate() != 0) {
                        java.util.Date expirydate = ConvertDate.getUtilDateFromNumericDate(giftCardSellingPOJO.getGiftcardexpirydate());
                        if (expirydate != null) {
                            XMLCalendar xmlDate = new XMLCalendar(expirydate);
                            if (xmlDate != null) {
                                mtGiftCardSales.setGCEXPDT(xmlDate);
                            }

                        }
                    }
                } catch (Exception e) {
                    System.out.println("instrument date not Set" + e);
                }

                //End code added by dileep - 20.09.2013
                try {
                    if (giftCardSellingPOJO.getGiftcarddate() != 0) {
                        java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(giftCardSellingPOJO.getGiftcarddate());
                        if (followDate != null) {
                            XMLCalendar xmlDate = new XMLCalendar(followDate);
                            if (xmlDate != null) {
                                mtGiftCardSales.setGCDATE(xmlDate);
                            }

                        }
                    }
                } catch (Exception e) {
                    System.out.println("instrument date not Set" + e);
                }
                Iterator iter = v.iterator();
                AdvanceReceiptDetailsPOJO advpojo;
                while (iter.hasNext()) {

                    advpojo = (AdvanceReceiptDetailsPOJO) iter.next();
                    in.co.titan.giftcardsales.DTGiftCardSales.ITEMPAYMENT itemtable = new ITEMPAYMENT();

                    itemtable.setPOSADVRECNO(giftCardSellingPOJO.getGcdocumentno());
                    try {
                        itemtable.setSITEID(advpojo.getStorecode());
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                    //    itemtable.setRemarks("Advsave");
                    if (advpojo.getModeofpayment().equalsIgnoreCase("CHQ")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                        itemtable.setSRNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNO(advpojo.getInstrumentno());
                        //   itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));

                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        itemtable.setDATE(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }
                        itemtable.setBANK(advpojo.getBank());
                        itemtable.setAMOUNT(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        /*branchname*/ itemtable.setBRTYPE(String.valueOf(advpojo.getBranchname()));

                        itemtable.setPAYMENTMODE(advpojo.getModeofpayment());
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("CAS")) {

                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                        itemtable.setSRNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));

                        itemtable.setAMOUNT(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        itemtable.setPAYMENTMODE(advpojo.getModeofpayment());
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("CRC")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        itemtable.setSRNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNO(advpojo.getInstrumentno());
                        try {
                            itemtable.setSITEID(advpojo.getStorecode());
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        //      itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));
                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        itemtable.setDATE(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }

                        itemtable.setAUTHCODE(advpojo.getAuthorizationcode());
                        itemtable.setBANK(advpojo.getBank());
                        itemtable.setAMOUNT(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        /*cardtype*/ itemtable.setBRTYPE(String.valueOf(advpojo.getCardtype()));

                        itemtable.setPAYMENTMODE(advpojo.getModeofpayment());
                        if (Validations.isFieldNotEmpty(advpojo.getAuthorizedperson())) {
                            itemtable.setLPAPPNOS(advpojo.getAuthorizedperson());
                        }
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("HCC")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        itemtable.setSRNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNO(advpojo.getInstrumentno());
                        try {
                            itemtable.setSITEID(advpojo.getStorecode());
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        //      itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));
                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        itemtable.setDATE(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }

                        itemtable.setAUTHCODE(advpojo.getAuthorizationcode());
                        itemtable.setBANK(advpojo.getBank());
                        itemtable.setAMOUNT(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        /*cardtype*/ itemtable.setBRTYPE(String.valueOf(advpojo.getCardtype()));

                        itemtable.setPAYMENTMODE(advpojo.getModeofpayment());

                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("CRN")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                        itemtable.setSRNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNO(advpojo.getInstrumentno());
                        //   itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));

                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        itemtable.setDATE(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }
                        itemtable.setCNCRAMOUNT(new BigDecimal(String.valueOf(advpojo.getCreditamount())));
                        itemtable.setAMOUNT(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        itemtable.setCNFYEAR(new BigInteger(String.valueOf(advpojo.getFiscalyear())));
                        /*CNreference*/ itemtable.setCNTEXT(advpojo.getCreditnotereference());

                        itemtable.setPAYMENTMODE(advpojo.getModeofpayment());
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("DBC")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        itemtable.setSRNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNO(advpojo.getInstrumentno());
                        // itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));
                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        itemtable.setDATE(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }
                        itemtable.setAUTHCODE(advpojo.getAuthorizationcode());
                        itemtable.setBANK(advpojo.getBank());
                        /*cardtype*/ itemtable.setBRTYPE(advpojo.getCardtype());
                        itemtable.setAMOUNT(new BigDecimal(String.valueOf(advpojo.getAmount())));

                        itemtable.setPAYMENTMODE(advpojo.getModeofpayment());
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("EML")) {

                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        itemtable.setSRNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setELLOANAMT(new BigDecimal(String.valueOf(advpojo.getLoanamount())));
                        itemtable.setNO(advpojo.getInstrumentno());
                        itemtable.setELNAME(advpojo.getEmployeename());
                        itemtable.setELDEPT(advpojo.getDepartment());
                        itemtable.setELLETREF(advpojo.getLetterreference());

                        /*Authorperson*/ itemtable.setELAUTH(String.valueOf(advpojo.getAuthorizedperson()));
                        itemtable.setAMOUNT(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        itemtable.setAUTHCODE(advpojo.getAuthorizationcode());//Added by Balachander V on 16.2.2022 suggested by Naveen to send the Tenure period in Authorization code filed

                        itemtable.setPAYMENTMODE(advpojo.getModeofpayment());
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("FRC")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        itemtable.setSRNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        /*currencytype*/ itemtable.setBRTYPE(String.valueOf(advpojo.getCurrencytype()));
                        itemtable.setFEEXRATE(new BigDecimal(String.valueOf(advpojo.getExchangerate())));
                        itemtable.setAMOUNT(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        /*noofunits*/ itemtable.setFEUNITS(new BigDecimal(String.valueOf(advpojo.getNoofunits())));

                        itemtable.setPAYMENTMODE(advpojo.getModeofpayment());

                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("GFV")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        itemtable.setSRNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));

                        /*gvno*/ itemtable.setNO(advpojo.getInstrumentno());
                        if (!advpojo.getGVFrom().equalsIgnoreCase("") && !advpojo.getGVTO().equalsIgnoreCase("")) {
                            itemtable.setGVFORM(advpojo.getGVFrom());

                            itemtable.setGVTO(advpojo.getGVTO());
                        }
                        //   itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));

                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        itemtable.setDATE(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }
                        itemtable.setGVDENAMOUNT(new BigDecimal(String.valueOf(advpojo.getDenomination())));
                        /*validtext*/ itemtable.setGVTEXT(advpojo.getValidationtext());
                        itemtable.setAMOUNT(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        itemtable.setPAYMENTMODE(advpojo.getModeofpayment());
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("LOY")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        itemtable.setSRNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNO(advpojo.getInstrumentno());
                        itemtable.setAMOUNT(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        itemtable.setLPAPPNOS(advpojo.getApprovalno());
                        //    itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));

                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        itemtable.setDATE(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }

                        itemtable.setLPPOINTS(String.valueOf(advpojo.getLoyalitypoints()));
                        itemtable.setLPREDPOINT(String.valueOf(advpojo.getRedeemedpoints()));

                        itemtable.setPAYMENTMODE(advpojo.getModeofpayment());

                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("PPW")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        itemtable.setSRNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));

                        /*gvno*/ itemtable.setNO(advpojo.getInstrumentno());

                        //   itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));
                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        itemtable.setDATE(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }
                        itemtable.setGVDENAMOUNT(new BigDecimal(String.valueOf(advpojo.getDenomination())));
                        /*validtext*/ itemtable.setGVTEXT(advpojo.getValidationtext());
                        itemtable.setAMOUNT(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        itemtable.setPAYMENTMODE(advpojo.getModeofpayment());
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("TNU")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                        itemtable.setSRNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNO(advpojo.getInstrumentno());
                        itemtable.setAMOUNT(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        itemtable.setLPAPPNOS(advpojo.getApprovalno());
                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        itemtable.setDATE(xmlDate);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }
                        itemtable.setLPPOINTS(String.valueOf(advpojo.getLoyalitypoints()));
                        itemtable.setLPREDPOINT(String.valueOf(advpojo.getRedeemedpoints()));
                        itemtable.setPAYMENTMODE(advpojo.getModeofpayment());
                    }
                    mtGiftCardSales.getITEMPAYMENT().add(itemtable);
                }
                if (giftCardSellingPOJO.getRefCreditNoteno() != null && giftCardSellingPOJO.getRefCreditNoteno().length() > 0) {
                    DTGiftCardSales.EXCESSPAYMENT refexcesspayment = new DTGiftCardSales.EXCESSPAYMENT();
                    if (giftCardSellingPOJO.getRefExcessReftype().equalsIgnoreCase("EXCESS AMOUNT")) {
                        refexcesspayment.setFlag("C");
                    }
                    refexcesspayment.setAmount(new BigDecimal(String.valueOf(giftCardSellingPOJO.getRefExcessamount())));
                    refexcesspayment.setSiteID(giftCardSellingPOJO.getStorecode());
                    refexcesspayment.setSrNO(new BigInteger(String.valueOf(1)));
                    refexcesspayment.setPOSAdvRecNO(giftCardSellingPOJO.getRefCreditNoteno());
                    try {
                        if (giftCardSellingPOJO.getRefCreditnoteexpirydate() != 0) {
                            java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(giftCardSellingPOJO.getRefCreditnoteexpirydate());
                            if (followDate != null) {
                                XMLCalendar xmlDate = new XMLCalendar(followDate);
                                if (xmlDate != null) {
                                    refexcesspayment.setDate((xmlDate));
                                }
                            }
                        }

                    } catch (Exception e) {
                        System.out.println("DocumentDate date not Set" + e);
                    }
                    refexcesspayment.setPaymentMode("CRN");
                    mtGiftCardSales.getEXCESSPAYMENT().add(refexcesspayment);
                }
                if (giftCardSellingPOJO.getNrCreditNoteno() != null && giftCardSellingPOJO.getNrCreditNoteno().length() > 0) {
                    DTGiftCardSales.EXCESSPAYMENT nrexcesspayment = new DTGiftCardSales.EXCESSPAYMENT();
                    if (giftCardSellingPOJO.getNrExcessReftype().equalsIgnoreCase("EXCESS AMOUNT")) {
                        nrexcesspayment.setFlag("C");
                    }
                    nrexcesspayment.setAmount(new BigDecimal(String.valueOf(giftCardSellingPOJO.getNrExcessamount())));
                    nrexcesspayment.setSiteID(giftCardSellingPOJO.getStorecode());
                    if (giftCardSellingPOJO.getRefCreditNoteno() != null && giftCardSellingPOJO.getRefCreditNoteno().length() > 0) {
                        nrexcesspayment.setSrNO(new BigInteger(String.valueOf(2)));
                    } else {
                        nrexcesspayment.setSrNO(new BigInteger(String.valueOf(1)));
                    }
                    nrexcesspayment.setPOSAdvRecNO(giftCardSellingPOJO.getNrCreditNoteno());
                    try {
                        if (giftCardSellingPOJO.getNrCreditnoteexpirydate() != 0) {
                            java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(giftCardSellingPOJO.getNrCreditnoteexpirydate());
                            if (followDate != null) {
                                XMLCalendar xmlDate = new XMLCalendar(followDate);
                                if (xmlDate != null) {
                                    nrexcesspayment.setDate((xmlDate));
                                }
                            }
                        }

                    } catch (Exception e) {
                        System.out.println("DocumentDate date not Set" + e);
                    }
                    nrexcesspayment.setPaymentMode("NCR");
                    mtGiftCardSales.getEXCESSPAYMENT().add(nrexcesspayment);
                }
                if (mtGiftCardSales.getSITEID() != null && mtGiftCardSales.getSITEID().trim().length() > 0) {

                    System.out.println("Service Name : " + service.getServiceName());

                    port.miOBASYNGiftCardSales(mtGiftCardSales);
                    Map responseContext = ((BindingProvider) port).getResponseContext();
                    Integer responseCode = (Integer) responseContext.get(javax.xml.ws.handler.MessageContext.HTTP_RESPONSE_CODE);

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
                /*port.miOBASYNGiftCardSales(mtGiftCardSales);
                Map responseContext = ((BindingProvider)port).getResponseContext();
                Integer responseCode = (Integer)responseContext.get(javax.xml.ws.handler.MessageContext.HTTP_RESPONSE_CODE);
                if(responseCode.intValue() == 200){
                return "true";
                }else{
                return "false";
                }*/
            } else if (dataObject instanceof GiftCardCancellationBean) {

                try { // Call Web Service Operation
                    // TODO initialize WS operation arguments here
                } catch (Exception ex) {
                    // TODO handle custom exceptions here
                }

                bean1 = (GiftCardCancellationBean) dataObject;
                giftCardSellingPOJO = bean1.getGiftCardSellingPOJO();

                v = (Vector<AdvanceReceiptDetailsPOJO>) bean1.getAdvanceReceiptDetailsPOJOs();

                in.co.titan.giftcardcancel.MIOBASYNGiftCardCancelService service = new in.co.titan.giftcardcancel.MIOBASYNGiftCardCancelService();
                // in.co.titan.giftcardcancel.MIOBASYNGiftCardCancel port = service.getMIOBASYNGiftCardCancelPort();
                in.co.titan.giftcardcancel.MIOBASYNGiftCardCancel port = service.getHTTPSPort();

                Map<String, Object> ctxt = ((BindingProvider) port).getRequestContext();
                //ctxt.put(JAXWSProperties.CONNECT_TIMEOUT, connectionTimeout);
                ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, 60000);

                ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
                ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());
                ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port() + "/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_GiftCardCancel&version=3.0&Sender.Service=x&Interface=z%5Ey");
                // TODO initialize WS operation arguments here
                in.co.titan.giftcardcancel.DTGiftCardCancel mtGiftCardCancel = new in.co.titan.giftcardcancel.DTGiftCardCancel();

                mtGiftCardCancel.setSITEID(giftCardSellingPOJO.getStorecode());
                mtGiftCardCancel.setSITESEARCH(giftCardSellingPOJO.getStorecode());
                mtGiftCardCancel.setGJAHR(String.valueOf(giftCardSellingPOJO.getFiscalyear()));
                mtGiftCardCancel.setCREATNAME(giftCardSellingPOJO.getCreatedby());
                try {
                    java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(giftCardSellingPOJO.getGiftcarddate());
                    Calendar createdTime = ConvertDate.getSqlTimeFromString(giftCardSellingPOJO.getCreatedtime());
                    if (createdDate != null) {
                        XMLCalendar xmlDate = new XMLCalendar(createdDate);
                        if (createdTime != null) {
                            xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                            if (xmlDate != null) {
                                mtGiftCardCancel.setCREATTIME(xmlDate);
                            }
                        }
                        if (xmlDate != null) {
                            mtGiftCardCancel.setCREATDATE(xmlDate);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mtGiftCardCancel.setGCDOCNUM(giftCardSellingPOJO.getGcdocumentno());
                mtGiftCardCancel.setREFGCDOCNUM(giftCardSellingPOJO.getRefno());//Cancellation scenario--giftcard creation no is sent as ref no
                mtGiftCardCancel.setGCNUM(giftCardSellingPOJO.getGiftcardno());
                mtGiftCardCancel.setAUTHCODE(giftCardSellingPOJO.getAuthorizationcode());
                mtGiftCardCancel.setAMOUNT(new BigDecimal(String.valueOf(giftCardSellingPOJO.getAmount())));

                if (giftCardSellingPOJO.getCustomercode() != null) {

                    mtGiftCardCancel.setCUSTOMERNO(giftCardSellingPOJO.getCustomercode());
                }

                //code added by dileep - 20.09.2013
                if (giftCardSellingPOJO.getTransactiontype() != null) {
                    mtGiftCardCancel.setTRANTYPE(giftCardSellingPOJO.getTransactiontype());
                }

                if (giftCardSellingPOJO.getTransactionid() != null) {
                    mtGiftCardCancel.setTRANID(giftCardSellingPOJO.getTransactionid());
                }

                if (giftCardSellingPOJO.getBatchid() != null) {
                    mtGiftCardCancel.setBATCHID(giftCardSellingPOJO.getBatchid());
                }

                //End code added by dileep - 20.09.2013
                try {
                    if (giftCardSellingPOJO.getGiftcarddate() != 0) {
                        java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(giftCardSellingPOJO.getGiftcarddate());
                        if (followDate != null) {
                            XMLCalendar xmlDate = new XMLCalendar(followDate);
                            if (xmlDate != null) {
                                mtGiftCardCancel.setGCDATE(xmlDate);
                            }

                        }
                    }
                } catch (Exception e) {
                    System.out.println("instrument date not Set" + e);
                }
                Iterator iter = v.iterator();
                AdvanceReceiptDetailsPOJO advpojo;
                while (iter.hasNext()) {

                    advpojo = (AdvanceReceiptDetailsPOJO) iter.next();
                    in.co.titan.giftcardcancel.DTGiftCardCancel.ITEMPAYMENT itemtable = new in.co.titan.giftcardcancel.DTGiftCardCancel.ITEMPAYMENT();

                    itemtable.setPOSADVRECNO(giftCardSellingPOJO.getGcdocumentno());
                    try {
                        itemtable.setSITEID(advpojo.getStorecode());
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                    //    itemtable.setRemarks("Advsave");
                    if (advpojo.getModeofpayment().equalsIgnoreCase("CHQ")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                        itemtable.setSRNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNO(advpojo.getInstrumentno());
                        //   itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));

                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        itemtable.setDATE(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }
                        itemtable.setBANK(advpojo.getBank());
                        itemtable.setAMOUNT(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        /*branchname*/ itemtable.setBRTYPE(String.valueOf(advpojo.getBranchname()));

                        itemtable.setPAYMENTMODE(advpojo.getModeofpayment());
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("CAS")) {

                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                        itemtable.setSRNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));

                        itemtable.setAMOUNT(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        itemtable.setPAYMENTMODE(advpojo.getModeofpayment());
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("CRC")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        itemtable.setSRNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNO(advpojo.getInstrumentno());
                        try {
                            itemtable.setSITEID(advpojo.getStorecode());
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        //      itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));
                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        itemtable.setDATE(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }

                        itemtable.setAUTHCODE(advpojo.getAuthorizationcode());
                        itemtable.setBANK(advpojo.getBank());
                        itemtable.setAMOUNT(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        /*cardtype*/ itemtable.setBRTYPE(String.valueOf(advpojo.getCardtype()));

                        itemtable.setPAYMENTMODE(advpojo.getModeofpayment());
                        if (Validations.isFieldNotEmpty(advpojo.getAuthorizedperson())) {
                            itemtable.setLPAPPNOS(advpojo.getAuthorizedperson());
                        }
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("CRN")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                        itemtable.setSRNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNO(advpojo.getInstrumentno());
                        //   itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));

                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        itemtable.setDATE(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }
                        itemtable.setCNCRAMOUNT(new BigDecimal(String.valueOf(advpojo.getCreditamount())));
                        itemtable.setAMOUNT(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        itemtable.setCNFYEAR(new BigInteger(String.valueOf(advpojo.getFiscalyear())));
                        /*CNreference*/ itemtable.setCNTEXT(advpojo.getCreditnotereference());

                        itemtable.setPAYMENTMODE(advpojo.getModeofpayment());
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("DBC")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        itemtable.setSRNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNO(advpojo.getInstrumentno());
                        // itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));
                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        itemtable.setDATE(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }
                        itemtable.setAUTHCODE(advpojo.getAuthorizationcode());
                        itemtable.setBANK(advpojo.getBank());
                        /*cardtype*/ itemtable.setBRTYPE(advpojo.getCardtype());
                        itemtable.setAMOUNT(new BigDecimal(String.valueOf(advpojo.getAmount())));

                        itemtable.setPAYMENTMODE(advpojo.getModeofpayment());
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("EML")) {

                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        itemtable.setSRNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setELLOANAMT(new BigDecimal(String.valueOf(advpojo.getLoanamount())));
                        itemtable.setNO(advpojo.getInstrumentno());
                        itemtable.setELNAME(advpojo.getEmployeename());
                        itemtable.setELDEPT(advpojo.getDepartment());
                        itemtable.setELLETREF(advpojo.getLetterreference());

                        /*Authorperson*/ itemtable.setELAUTH(String.valueOf(advpojo.getAuthorizedperson()));
                        itemtable.setAMOUNT(new BigDecimal(String.valueOf(advpojo.getAmount())));

                        itemtable.setPAYMENTMODE(advpojo.getModeofpayment());
                        itemtable.setAUTHCODE(advpojo.getAuthorizationcode());//Added by Balachander V on 16.2.2022 suggested by Naveen to send the Tenure period in Authorization code filed
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("FRC")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        itemtable.setSRNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        /*currencytype*/ itemtable.setBRTYPE(String.valueOf(advpojo.getCurrencytype()));
                        itemtable.setFEEXRATE(new BigDecimal(String.valueOf(advpojo.getExchangerate())));
                        itemtable.setAMOUNT(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        /*noofunits*/ itemtable.setFEUNITS(new BigDecimal(String.valueOf(advpojo.getNoofunits())));

                        itemtable.setPAYMENTMODE(advpojo.getModeofpayment());

                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("GFV")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        itemtable.setSRNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));

                        /*gvno*/ itemtable.setNO(advpojo.getInstrumentno());
                        if (!advpojo.getGVFrom().equalsIgnoreCase("") && !advpojo.getGVTO().equalsIgnoreCase("")) {
                            itemtable.setGVFORM(advpojo.getGVFrom());

                            itemtable.setGVTO(advpojo.getGVTO());
                        }
                        //   itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));

                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        itemtable.setDATE(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }
                        itemtable.setGVDENAMOUNT(new BigDecimal(String.valueOf(advpojo.getDenomination())));
                        /*validtext*/ itemtable.setGVTEXT(advpojo.getValidationtext());
                        itemtable.setAMOUNT(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        itemtable.setPAYMENTMODE(advpojo.getModeofpayment());
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("LOY")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        itemtable.setSRNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNO(advpojo.getInstrumentno());
                        itemtable.setAMOUNT(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        itemtable.setLPAPPNOS(advpojo.getApprovalno());
                        //    itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));

                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        itemtable.setDATE(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }

                        itemtable.setLPPOINTS(String.valueOf(advpojo.getLoyalitypoints()));
                        itemtable.setLPREDPOINT(String.valueOf(advpojo.getRedeemedpoints()));

                        itemtable.setPAYMENTMODE(advpojo.getModeofpayment());

                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("TNU")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        itemtable.setSRNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNO(advpojo.getInstrumentno());
                        itemtable.setAMOUNT(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        itemtable.setLPAPPNOS(advpojo.getApprovalno());
                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        itemtable.setDATE(xmlDate);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }
                        itemtable.setLPPOINTS(String.valueOf(advpojo.getLoyalitypoints()));
                        itemtable.setLPREDPOINT(String.valueOf(advpojo.getRedeemedpoints()));
                        itemtable.setPAYMENTMODE(advpojo.getModeofpayment());
                    }
                    mtGiftCardCancel.getITEMPAYMENT().add(itemtable);
                }
                System.out.println("Giftcard cancel Storecode " + mtGiftCardCancel.getSITEID());
                if (mtGiftCardCancel.getSITEID() != null && mtGiftCardCancel.getSITEID().trim().length() > 0) {
                    port.miOBASYNGiftCardCancel(mtGiftCardCancel);
                    Map responseContext = ((BindingProvider) port).getResponseContext();
                    Integer responseCode = (Integer) responseContext.get(javax.xml.ws.handler.MessageContext.HTTP_RESPONSE_CODE);
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
/*                port.miOBASYNGiftCardCancel(mtGiftCardCancel);
                Map responseContext = ((BindingProvider) port).getResponseContext();
                Integer responseCode = (Integer) responseContext.get(javax.xml.ws.handler.MessageContext.HTTP_RESPONSE_CODE);
                if (responseCode.intValue() == 200) {
                return "true";
                } else {
                return "false";
                }
                 */
            } else {

                return "false";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }

    }
}
