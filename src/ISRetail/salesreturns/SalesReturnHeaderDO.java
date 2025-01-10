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
 * Data Object for SiteMaster 
 * 
 */
package ISRetail.salesreturns;

import ISRetail.Helpers.ConvertDate;
import ISRetail.Helpers.XMLCalendar;
import ISRetail.Webservices.ConditionTypePOJO;
import ISRetail.Webservices.DataObject;

import ISRetail.Webservices.Webservice;
import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.article.ArticleDO;
import ISRetail.creditnote.CreditNotePOJO;
import ISRetail.creditnote.NRCreditNotePOJO;
import ISRetail.employee.EmployeeMasterDO;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.plantdetails.SiteMasterPOJO;
import ISRetail.salesorder.SOLineItemPOJO;

import ISRetail.utility.db.PopulateData;
import ISRetail.utility.validations.GenerateNextPosDocNumber;
import ISRetail.utility.validations.Validations;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

/**
 *
 * @author Administrator
 */
public class SalesReturnHeaderDO implements Webservice {

    public int saveSalesReturnHeader(Connection con, SalesReturnHeaderPOJO salesreturnheaderpojoobj) throws SQLException {
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            pstmt = con.prepareStatement("insert into tbl_salereturn_header(storecode,fiscalyear,salereturnno,salereturndate,refacknumber,refackdate,createdby,createddate,createdtime) values(?,?,?,?,?,?,?,?,?)");
            pstmt.setString(1, salesreturnheaderpojoobj.getStorecode());
            pstmt.setInt(2, salesreturnheaderpojoobj.getFiscalyear());
            pstmt.setString(3, salesreturnheaderpojoobj.getSalereturnno());
            pstmt.setInt(4, salesreturnheaderpojoobj.getSalereturndate());
            pstmt.setString(5, salesreturnheaderpojoobj.getRefacknumber());
            pstmt.setInt(6, salesreturnheaderpojoobj.getRefackdate());
            pstmt.setString(7, salesreturnheaderpojoobj.getCreatedby());
            pstmt.setInt(8, salesreturnheaderpojoobj.getCreateddate());
            pstmt.setString(9, salesreturnheaderpojoobj.getCreatedtime());
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt.close();
        }
        return result;

    }

    public int updateSalesReturnHeader(Connection con, SalesReturnHeaderPOJO salesreturnheaderpojoobj) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("update tbl_salereturn_header set salereturndate=?,modifiedby=?,modifieddate=?,modifiedtime=? where salereturnno=? and refacknumber=? and storecode=?");
            pstmt.setInt(1, salesreturnheaderpojoobj.getSalereturndate());
            pstmt.setString(2, salesreturnheaderpojoobj.getModifiedby());
            pstmt.setInt(3, salesreturnheaderpojoobj.getModifieddate());
            pstmt.setString(4, salesreturnheaderpojoobj.getModifiedtime());
            pstmt.setString(5, salesreturnheaderpojoobj.getSalereturnno());
            pstmt.setString(6, salesreturnheaderpojoobj.getRefacknumber());
            pstmt.setString(7, salesreturnheaderpojoobj.getStorecode());
            int result = pstmt.executeUpdate();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            pstmt.close();
        }
    }

    public SalesReturnHeaderPOJO getSaleReturnByAckNo(Connection con, String ackNumber, String storeCode) throws SQLException {
        SalesReturnHeaderPOJO saleReturnHeaderPojo = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("select fiscalyear,salereturnno,salereturndate,refacknumber,refackdate,createdby,createddate,createdtime,returnchannel,cancelotp,sfccomplaintid from tbl_salereturn_header where refacknumber=? and storecode=?");
            pstmt.setString(1, ackNumber);
            pstmt.setString(2, storeCode);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {

                saleReturnHeaderPojo = new SalesReturnHeaderPOJO();
                saleReturnHeaderPojo.setStorecode(storeCode);
                saleReturnHeaderPojo.setFiscalyear(rs.getInt("fiscalyear"));
                saleReturnHeaderPojo.setSalereturnno(rs.getString("salereturnno"));
                saleReturnHeaderPojo.setSalereturndate(rs.getInt("salereturndate"));
                saleReturnHeaderPojo.setRefacknumber(rs.getString("refacknumber"));
                saleReturnHeaderPojo.setRefackdate(rs.getInt("refackdate"));
                saleReturnHeaderPojo.setCreatedby(rs.getString("createdby"));
                saleReturnHeaderPojo.setCreateddate(rs.getInt("createddate"));
                saleReturnHeaderPojo.setCreatedtime(rs.getString("createdtime"));
                saleReturnHeaderPojo.setSelectedstore(rs.getString("returnchannel"));
                saleReturnHeaderPojo.setCancelOtp(rs.getString("cancelotp"));
                saleReturnHeaderPojo.setSfcComplaintID(rs.getString("sfccomplaintid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt.close();
        }
        return saleReturnHeaderPojo;
    }

    public String checkSaleReturnExists(Connection con, String ackNumber, String storeCode) throws SQLException {
        String saleReturnNo = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("select salereturnno from tbl_salereturn_header where refacknumber=? and storecode=?");
            pstmt.setString(1, ackNumber);
            pstmt.setString(2, storeCode);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                saleReturnNo = rs.getString("salereturnno");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt.close();
        }
        return saleReturnNo;
    }

    public String generateSaleReturnNo(Connection con, String firstFixedString) throws SQLException {
        String nextSONo = null;
        Statement statement = null;
        try {
            String prevSONo = null, prevSoLastNoPart = null;
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select saleorderno from tbl_posdoclastnumbers");
            if (rs.next()) {
                prevSONo = rs.getString(1);
            }
            if (Validations.isFieldNotEmpty(prevSONo)) {//if lastNo exists
                if (prevSONo.length() > firstFixedString.length()) {
                    prevSoLastNoPart = prevSONo.substring(firstFixedString.length());
                    prevSONo = firstFixedString + prevSoLastNoPart;
                }
            } else {
                String inqNoLastPart = "";
                for (int i = firstFixedString.length(); i < 10; i++) {
                    inqNoLastPart = inqNoLastPart + "0";
                }
                prevSONo = firstFixedString + inqNoLastPart;
            }
            nextSONo = new GenerateNextPosDocNumber().generatenumber(prevSONo, firstFixedString.length());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            statement.close();
        }
        return nextSONo;
    }

    public int updateLastSaleReturnNo(Connection con, String lastDocNo) throws SQLException {
        PreparedStatement pstmt = null;
        int res = 0;
        try {
            String query = "update tbl_posdoclastnumbers set saleorderno =? ";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, lastDocNo);
            res = pstmt.executeUpdate();
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return 0;
        } finally {
            pstmt.close();
        }
        return res;

    }

    public SalesReturnHeaderPOJO getSaleReturnHeaderBySalaeReturNO(Connection con, String saleReturnNo, String storeCode) {
        SalesReturnHeaderPOJO saleReturnHeaderPojo = null;
        try {
            PreparedStatement pstmt = con.prepareStatement("select fiscalyear,salereturnno,salereturndate,refacknumber,refackdate,createdby,createddate,createdtime,sfccomplaintid from tbl_salereturn_header where salereturnno=? and storecode=?");
            pstmt.setString(1, saleReturnNo);
            if (!Validations.isFieldNotEmpty(storeCode)) {
                storeCode = new SiteMasterDO().getSiteId(con);
            }
            pstmt.setString(2, storeCode);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                saleReturnHeaderPojo = new SalesReturnHeaderPOJO();
                saleReturnHeaderPojo.setStorecode(storeCode);
                saleReturnHeaderPojo.setFiscalyear(rs.getInt("fiscalyear"));
                saleReturnHeaderPojo.setSalereturnno(rs.getString("salereturnno"));
                saleReturnHeaderPojo.setSalereturndate(rs.getInt("salereturndate"));
                saleReturnHeaderPojo.setRefacknumber(rs.getString("refacknumber"));
                saleReturnHeaderPojo.setRefackdate(rs.getInt("refackdate"));
                saleReturnHeaderPojo.setCreatedby(rs.getString("createdby"));
                saleReturnHeaderPojo.setCreateddate(rs.getInt("createddate"));
                saleReturnHeaderPojo.setCreatedtime(rs.getString("createdtime"));
                //saleReturnHeaderPojo.setSfcComplaintID(rs.getString("sfccomplaintid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saleReturnHeaderPojo;
    }

    public int archiveAllSalesReturnAndAckTables(Connection con, int fiscalYear) throws SQLException {
        int recDeleted = 0;
        PreparedStatement pstmt;
        String query;
        try {
            query = "delete from tbl_ack_defectreason where fisaclyear= ?";
            pstmt = con.prepareStatement(query);
            int res = pstmt.executeUpdate();
            recDeleted = recDeleted + res;

            query = "delete from tbl_ack_focspares where fisaclyear= ?";
            pstmt = con.prepareStatement(query);
            res = pstmt.executeUpdate();
            recDeleted = recDeleted + res;

            query = "delete from tbl_ack_condition where fisaclyear= ?";
            pstmt = con.prepareStatement(query);
            res = pstmt.executeUpdate();
            recDeleted = recDeleted + res;

            query = "delete from tbl_ack_lineitems where fisaclyear= ?";
            pstmt = con.prepareStatement(query);
            res = pstmt.executeUpdate();
            recDeleted = recDeleted + res;

            query = "delete from tbl_salereturn_header where fisaclyear= ?";
            pstmt = con.prepareStatement(query);
            res = pstmt.executeUpdate();
            recDeleted = recDeleted + res;

            query = "delete from tbl_ack_header where fisaclyear= ?";
            pstmt = con.prepareStatement(query);
            res = pstmt.executeUpdate();
            recDeleted = recDeleted + res;
        } catch (Exception e) {
            e.printStackTrace();
            recDeleted = 0;
            throw new SQLException();
        } finally {
            query = null;
        }
        return recDeleted;
    }

    public String execute(DataObject obj, String updateType) {
        String updatedInISR = "F";
        EmployeeMasterDO edo = null;

        try { // Call Web Service Operation
            if (obj instanceof SalesReturnMasterBean) {
                SalesReturnMasterBean masterBean = (SalesReturnMasterBean) obj;
                MsdeConnection msdeconn = new MsdeConnection();
                Connection con = msdeconn.createConnection();
                SiteMasterDO smdo = new SiteMasterDO();
                SiteMasterPOJO ampojoobj = new SiteMasterPOJO();
                ArticleDO articleDo = new ArticleDO();
                PopulateData popData = new PopulateData();
                Properties defReasonKeyValues = popData.populateSR_DefectResons(con);
                AcknowledgementHeaderPOJO acknowledgementHeaderPOJO = masterBean.getAcknowledgementHeaderPOJO();
                ArrayList<AcknowledgementFocSparesPOJO> acknowledgementFocSparesPOJOs = masterBean.getAcknowledgementFocSparesPOJOs();
                SalesReturnHeaderPOJO salesReturnHeaderPOJO = masterBean.getSalesReturnHeaderPOJO();
                ArrayList<SOLineItemPOJO> ackLineItems = masterBean.getAckLneItemsPojo();
                CreditNotePOJO creditNotePOJO = masterBean.getCreditNotePojo();
                NRCreditNotePOJO nRCreditNotePOJO = masterBean.getNRCreditNotePOJO();
                DecimalFormat decimalFormat = new DecimalFormat("#0.00");

                String materialCode = null, division = null, saleReturnNo = null, companyCode = null;
                if (salesReturnHeaderPOJO != null) {
                    saleReturnNo = salesReturnHeaderPOJO.getSalereturnno();
                }

                // TODO initialize WS operation arguments here
                try { // Call Web Service Operation

                    // TODO initialize WS operation arguments here
                    //  in.co.titan.salesreturn.DTSalesReturn mtSalesReturn = null;
                    // TODO process result here
                    //  in.co.titan.salesreturn.DTSalesReturnResponse result = port.miOBSYNSalesReturn(mtSalesReturn);
                } catch (Exception ex) {
                    // TODO handle custom exceptions here
                }

                in.co.titan.salesreturn.MIOBASYNSalesReturnService service = new in.co.titan.salesreturn.MIOBASYNSalesReturnService();
                //in.co.titan.salesreturn.MIOBASYNSalesReturn port = service.getMIOBASYNSalesReturnPort();//Commented by Balachander V on 26.12.2018
                in.co.titan.salesreturn.MIOBASYNSalesReturn port = service.getHTTPSPort();//Added by Balachander V on 26.12.2018 to get HTTPS POrt No from new WSDL file
                Map<String, Object> ctxt = ((BindingProvider) port).getRequestContext();
                //ctxt.put(JAXWSProperties.CONNECT_TIMEOUT, connectionTimeout);
                ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, 60000);
                // TODO initialize WS operation arguments here
                in.co.titan.salesreturn.DTSalesReturn mtSalesReturn = new in.co.titan.salesreturn.DTSalesReturn();
                // TODO process result here
                if (acknowledgementHeaderPOJO != null && acknowledgementHeaderPOJO.getAckstatus() != null && updateType != null) {
                    if (updateType.trim().equalsIgnoreCase("I")) {
                        if (salesReturnHeaderPOJO == null) {
                            acknowledgementHeaderPOJO.setAckstatus("HO");
                        } else if (!Validations.isFieldNotEmpty(salesReturnHeaderPOJO.getSalereturnno())) {
                            acknowledgementHeaderPOJO.setAckstatus("HO");
                        }
                    }
                    mtSalesReturn.setFlagCategory(acknowledgementHeaderPOJO.getAckstatus());
                    mtSalesReturn.setDeliverySite(acknowledgementHeaderPOJO.getStorecode());//this site
                    mtSalesReturn.setSITESEARCH(acknowledgementHeaderPOJO.getStorecode());//this site
                    mtSalesReturn.setAckNO(acknowledgementHeaderPOJO.getAcknumber());
                    if (acknowledgementHeaderPOJO.getAckstatus().trim().equalsIgnoreCase("HO")) {//ack
                        mtSalesReturn.setStoreCode(acknowledgementHeaderPOJO.getRefstorecode());//ref  store code
                        mtSalesReturn.setFyear(String.valueOf(acknowledgementHeaderPOJO.getFiscalyear()));
                        mtSalesReturn.setSalesOrderNo(acknowledgementHeaderPOJO.getRefsaleorderno());
                        mtSalesReturn.setCustomerNo(acknowledgementHeaderPOJO.getCustomercode());
                        mtSalesReturn.setInvoiceNO(acknowledgementHeaderPOJO.getRefposinvoiceno());////// 
                        mtSalesReturn.setAckNetAmount(new BigDecimal(decimalFormat.format(acknowledgementHeaderPOJO.getTotalnetamount())));
                        System.err.println("in here");
                        try {
                            if (acknowledgementHeaderPOJO.getRefposinvoicedate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(acknowledgementHeaderPOJO.getRefposinvoicedate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        mtSalesReturn.setInvoiceDate(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                        }
                        mtSalesReturn.setVisibleDefect(acknowledgementHeaderPOJO.getVisibledefects());
                        mtSalesReturn.setComments(acknowledgementHeaderPOJO.getComments());
                        try {
                            if (acknowledgementHeaderPOJO.getAcknowledgementdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(acknowledgementHeaderPOJO.getAcknowledgementdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        mtSalesReturn.setDocumentDate(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                        }
                        mtSalesReturn.setCreatedName(acknowledgementHeaderPOJO.getCreatedby());
                        if (!Validations.isFieldNotEmpty(mtSalesReturn.getReturnChannel()) && Validations.isFieldNotEmpty(acknowledgementHeaderPOJO.getSelectedStore())) {
                            mtSalesReturn.setReturnChannel(acknowledgementHeaderPOJO.getSelectedStore());
                        }
                        try {
                            java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(acknowledgementHeaderPOJO.getCreateddate());
                            Calendar createdTime = ConvertDate.getSqlTimeFromString(acknowledgementHeaderPOJO.getCreatedtime());
                            if (createdDate != null) {
                                XMLCalendar xmlDate = new XMLCalendar(createdDate);
                                if (createdTime != null) {
                                    xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                                    if (xmlDate != null) {
                                        mtSalesReturn.setCreatedTime(xmlDate);
                                    }
                                }
                                if (xmlDate != null) {
                                    mtSalesReturn.setCreatedDate(xmlDate);
                                }

                            }
                        } catch (Exception e) {

                        }

                        try {
                            if (acknowledgementHeaderPOJO.getRevertdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(acknowledgementHeaderPOJO.getRevertdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        mtSalesReturn.setRevertDate(xmlDate);
                                    }
                                }
                            }
                             if(Validations.isFieldNotEmpty(acknowledgementHeaderPOJO.getOtherchannelname()))
                            {
                                mtSalesReturn.setCHANNELNAME(acknowledgementHeaderPOJO.getOtherchannelname());
                            }
                            if (Validations.isFieldNotEmpty(salesReturnHeaderPOJO.getCancelOtp())) {
                                mtSalesReturn.setCancelledOTP(salesReturnHeaderPOJO.getCancelOtp());    
                            }
//                            if(Validations.isFieldNotEmpty(salesReturnHeaderPOJO.getSfcComplaintID())){//VERSION 100
//                                mtSalesReturn.setSFCaseId(salesReturnHeaderPOJO.getSfcComplaintID());
//                            }
//                            System.err.println("mtSalesReturn.getSFCaseId:"+mtSalesReturn.getSFCaseId());
                           
                        } catch (Exception e) {
                           // e.printStackTrace();
                        }

                    } else if (acknowledgementHeaderPOJO.getAckstatus().trim().equalsIgnoreCase("RF")) {//sales return -refund and replacement
                        if (salesReturnHeaderPOJO != null) {
                            mtSalesReturn.setStoreCode(acknowledgementHeaderPOJO.getRefstorecode());//ref  store code
                            mtSalesReturn.setFyear(String.valueOf(salesReturnHeaderPOJO.getFiscalyear()));
                            mtSalesReturn.setSalesReturnNo(salesReturnHeaderPOJO.getSalereturnno());
                            if (!Validations.isFieldNotEmpty(mtSalesReturn.getReturnChannel()) && Validations.isFieldNotEmpty(salesReturnHeaderPOJO.getSelectedstore())) {
                                mtSalesReturn.setReturnChannel(salesReturnHeaderPOJO.getSelectedstore());
                            }
                            mtSalesReturn.setSalesOrderNo(acknowledgementHeaderPOJO.getRefsaleorderno());
                            mtSalesReturn.setCustomerNo(acknowledgementHeaderPOJO.getCustomercode());
                            mtSalesReturn.setInvoiceNO(acknowledgementHeaderPOJO.getRefposinvoiceno());//////

                            try {
                                if (acknowledgementHeaderPOJO.getRefposinvoicedate() != 0) {
                                    java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(acknowledgementHeaderPOJO.getRefposinvoicedate());
                                    if (followDate != null) {
                                        XMLCalendar xmlDate = new XMLCalendar(followDate);
                                        if (xmlDate != null) {
                                            mtSalesReturn.setInvoiceDate(xmlDate);
                                        }

                                    }
                                }
                            } catch (Exception e) {
                            }
                            mtSalesReturn.setVisibleDefect(acknowledgementHeaderPOJO.getVisibledefects());
                            mtSalesReturn.setComments(acknowledgementHeaderPOJO.getComments());
                            mtSalesReturn.setApprovedBy(acknowledgementHeaderPOJO.getApprovedby());
                            try {
                                if (acknowledgementHeaderPOJO.getApproveddate() != 0) {
                                    java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(acknowledgementHeaderPOJO.getApproveddate());
                                    if (followDate != null) {
                                        XMLCalendar xmlDate = new XMLCalendar(followDate);
                                        if (xmlDate != null) {
                                            mtSalesReturn.setApprovedDate(xmlDate);
                                        }

                                    }
                                }
                            } catch (Exception e) {
                            }
                            //companycode
                            companyCode = smdo.getSiteCompanyCode(con);
                            mtSalesReturn.setCompanyCode(companyCode);
                            if (creditNotePOJO != null) {
                                if (Validations.isFieldNotEmpty(creditNotePOJO.getCreditnoteno())) {
                                    mtSalesReturn.setPOSInvoiceN0(creditNotePOJO.getCreditnoteno());//invoice note no genrated after creit note generation
                                    XMLCalendar xmlDate = null;
                                    try {
                                        if (creditNotePOJO.getExpirydate() != 0) {
                                            java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(creditNotePOJO.getExpirydate());
                                            if (followDate != null) {
                                                xmlDate = new XMLCalendar(followDate);
                                                if (xmlDate != null) {
                                                    mtSalesReturn.setCreExpDate((xmlDate));
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        System.out.println("DocumentDate date not Set" + e);
                                    }
                                    mtSalesReturn.setPOSCreditNoteAmt(new BigDecimal(decimalFormat.format(creditNotePOJO.getAmount())));
                                }
                            }
                            if (nRCreditNotePOJO != null) {
                                if (Validations.isFieldNotEmpty(nRCreditNotePOJO.getCreditnoteno())) {
                                    mtSalesReturn.setPOSNRCreditNoteNo(nRCreditNotePOJO.getCreditnoteno());//invoice note no genrated after creit note generation
                                    XMLCalendar xmlDate = null;
                                    try {
                                        if (nRCreditNotePOJO.getExpirydate() != 0) {
                                            java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(nRCreditNotePOJO.getExpirydate());
                                            if (followDate != null) {
                                                xmlDate = new XMLCalendar(followDate);
                                                if (xmlDate != null) {
                                                    mtSalesReturn.setCreExpDate((xmlDate));
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        System.out.println("DocumentDate date not Set" + e);
                                    }
                                    mtSalesReturn.setPOSNRCreditNoteAmt(new BigDecimal(decimalFormat.format(nRCreditNotePOJO.getAmount())));
                                }
                            }

                            mtSalesReturn.setCreatedName(salesReturnHeaderPOJO.getCreatedby());
                            try {
                                java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(salesReturnHeaderPOJO.getCreateddate());
                                Calendar createdTime = ConvertDate.getSqlTimeFromString(salesReturnHeaderPOJO.getCreatedtime());
                                if (createdDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(createdDate);
                                    if (createdTime != null) {
                                        xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                                        if (xmlDate != null) {
                                            mtSalesReturn.setCreatedTime(xmlDate);
                                        }
                                    }
                                    if (xmlDate != null) {
                                        mtSalesReturn.setCreatedDate(xmlDate);
                                    }

                                }
                                if (!Validations.isFieldNotEmpty(mtSalesReturn.getReturnChannel()) && Validations.isFieldNotEmpty(salesReturnHeaderPOJO.getSelectedstore())) {
                                    mtSalesReturn.setReturnChannel(salesReturnHeaderPOJO.getSelectedstore());
                                }
                            } catch (Exception e) {

                            }
                            try {
                                if (salesReturnHeaderPOJO.getSalereturndate() != 0) {
                                    java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(salesReturnHeaderPOJO.getSalereturndate());
                                    if (followDate != null) {
                                        XMLCalendar xmlDate = new XMLCalendar(followDate);
                                        if (xmlDate != null) {
                                            mtSalesReturn.setDocumentDate(xmlDate);
                                        }

                                    }
                                }
                            } catch (Exception e) {
                            }
                            mtSalesReturn.setModifiedBy(acknowledgementHeaderPOJO.getModifiedby());
                            //Code added on 21 Jun 2011 for name capture during sale return
                            mtSalesReturn.setReturnPersonName(acknowledgementHeaderPOJO.getStaffresp());
                            //Added by Balachander V on 08/08/2018 to send staff responsible id instead of staff name
                            if (Validations.isFieldNotEmpty(acknowledgementHeaderPOJO.getStaffresp())) {
                                String empName = acknowledgementHeaderPOJO.getStaffresp();
                                edo = new EmployeeMasterDO();
                                String empid = edo.getEmpid(con, empName);
                                mtSalesReturn.setReturnPersonName(empid);
                            }
                            if(Validations.isFieldNotEmpty(salesReturnHeaderPOJO.getSfcComplaintID())){//VERSION 100
                                mtSalesReturn.setSFCaseId(salesReturnHeaderPOJO.getSfcComplaintID());
                            }
                            System.err.println("mtSalesReturn.getSFCaseId:"+mtSalesReturn.getSFCaseId());
                            System.out.println("ISRetail.salesreturns.SalesReturnHeaderDO.execute()");
//Ended by Balachander V on 08/08/2018 to send staff responsible id instead of staff name
                            if (!Validations.isFieldNotEmpty(mtSalesReturn.getReturnChannel()) && Validations.isFieldNotEmpty(acknowledgementHeaderPOJO.getSelectedStore())) {
                                mtSalesReturn.setReturnChannel(acknowledgementHeaderPOJO.getSelectedStore());
                            }
                            try {
                                java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(acknowledgementHeaderPOJO.getModifieddate());
                                Calendar createdTime = ConvertDate.getSqlTimeFromString(acknowledgementHeaderPOJO.getModifiedtime());
                                if (createdDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(createdDate);
                                    if (createdTime != null) {
                                        xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                                        if (xmlDate != null) {
                                            mtSalesReturn.setModifiedTime(xmlDate);
                                        }
                                    }
                                    if (xmlDate != null) {
                                        mtSalesReturn.setModifiedDate(xmlDate);
                                    }

                                }
                            } catch (Exception e) {

                            }
                             if(Validations.isFieldNotEmpty(acknowledgementHeaderPOJO.getOtherchannelname()))
                            {
                                mtSalesReturn.setCHANNELNAME(acknowledgementHeaderPOJO.getOtherchannelname());
                            }
                            if (Validations.isFieldNotEmpty(salesReturnHeaderPOJO.getCancelOtp())) {
                                mtSalesReturn.setCancelledOTP(salesReturnHeaderPOJO.getCancelOtp());
                            }
                           
                        }
                    } else if (acknowledgementHeaderPOJO.getAckstatus().trim().equalsIgnoreCase("FR")) {//repair-FOc
                        mtSalesReturn.setFyear(String.valueOf(acknowledgementHeaderPOJO.getFiscalyear()));
                        mtSalesReturn.setSalesOrderNo(acknowledgementHeaderPOJO.getRefsaleorderno());
                        mtSalesReturn.setApprovedBy(acknowledgementHeaderPOJO.getApprovedby());
                        try {
                            if (acknowledgementHeaderPOJO.getApproveddate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(acknowledgementHeaderPOJO.getApproveddate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        mtSalesReturn.setApprovedDate(xmlDate);
                                    }
                                }
                            }
                        } catch (Exception e) {
                        }
                        mtSalesReturn.setCreatedName(acknowledgementHeaderPOJO.getCreatedby());
                        if (!Validations.isFieldNotEmpty(mtSalesReturn.getReturnChannel()) && Validations.isFieldNotEmpty(acknowledgementHeaderPOJO.getSelectedStore())) {
                            mtSalesReturn.setReturnChannel(acknowledgementHeaderPOJO.getSelectedStore());
                        }
                        try {
                            java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(acknowledgementHeaderPOJO.getCreateddate());
                            Calendar createdTime = ConvertDate.getSqlTimeFromString(acknowledgementHeaderPOJO.getCreatedtime());
                            if (createdDate != null) {
                                XMLCalendar xmlDate = new XMLCalendar(createdDate);
                                if (createdTime != null) {
                                    xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                                    if (xmlDate != null) {
                                        mtSalesReturn.setCreatedTime(xmlDate);
                                    }
                                }
                                if (xmlDate != null) {
                                    mtSalesReturn.setCreatedDate(xmlDate);
                                }

                            }
                        } catch (Exception e) {

                        }
                        mtSalesReturn.setModifiedBy(acknowledgementHeaderPOJO.getModifiedby());
                        //Code added on 21 Jun 2011 for name capture during sale return
                        //mtSalesReturn.setModifiedBy(acknowledgementHeaderPOJO.getModifiedby());
                        try {
                            java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(acknowledgementHeaderPOJO.getModifieddate());
                            Calendar createdTime = ConvertDate.getSqlTimeFromString(acknowledgementHeaderPOJO.getModifiedtime());
                            if (createdDate != null) {
                                XMLCalendar xmlDate = new XMLCalendar(createdDate);
                                if (createdTime != null) {
                                    xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                                    if (xmlDate != null) {
                                        mtSalesReturn.setModifiedTime(xmlDate);
                                    }
                                }
                                if (xmlDate != null) {
                                    mtSalesReturn.setModifiedDate(xmlDate);
                                }

                            }
                        } catch (Exception e) {

                        }
                         if(Validations.isFieldNotEmpty(acknowledgementHeaderPOJO.getOtherchannelname()))
                            {
                                mtSalesReturn.setCHANNELNAME(acknowledgementHeaderPOJO.getOtherchannelname());
                            }
                        if (Validations.isFieldNotEmpty(salesReturnHeaderPOJO.getCancelOtp())) {
                            mtSalesReturn.setCancelledOTP(salesReturnHeaderPOJO.getCancelOtp());
                        }
                       
                        if (acknowledgementFocSparesPOJOs != null) {
                            Iterator focItr = acknowledgementFocSparesPOJOs.iterator();
                            double focNetAmt = 0;
                            if (focItr != null) {
                                ArrayList<in.co.titan.salesreturn.DTSalesReturn.FOC> focItems = new ArrayList<in.co.titan.salesreturn.DTSalesReturn.FOC>();
                                in.co.titan.salesreturn.DTSalesReturn.FOC focItem = null;
                                AcknowledgementFocSparesPOJO acknowledgementFocSparesPOJO = null;
                                while (focItr.hasNext()) {
                                    acknowledgementFocSparesPOJO = (AcknowledgementFocSparesPOJO) focItr.next();
                                    if (acknowledgementFocSparesPOJO != null) {
                                        focItem = new in.co.titan.salesreturn.DTSalesReturn.FOC();
                                        focItem.setRefSlNo(String.valueOf(acknowledgementFocSparesPOJO.getReflineitemno()));
                                        focItem.setLineNo(String.valueOf(acknowledgementFocSparesPOJO.getLineitemno()));
                                        focItem.setItemCode(acknowledgementFocSparesPOJO.getMaterialcode());
                                        BigDecimal qty = new BigDecimal(decimalFormat.format(acknowledgementFocSparesPOJO.getQty()));
                                        focItem.setQuantity(qty);
                                        focItem.setSOItemCode(acknowledgementFocSparesPOJO.getRefmaterialcode());
                                        focItem.setUnitPrice(new BigDecimal(decimalFormat.format(acknowledgementFocSparesPOJO.getUnitprice())));
                                        focItem.setAmount(new BigDecimal(decimalFormat.format(acknowledgementFocSparesPOJO.getAmount())));
                                        focItems.add(focItem);
                                        focNetAmt = focNetAmt + acknowledgementFocSparesPOJO.getAmount();
                                    }
                                }
                                mtSalesReturn.getFOC().addAll(focItems);
                                mtSalesReturn.setAckFOCAmount(new BigDecimal(decimalFormat.format(focNetAmt)));
                            }
                        }
                    } else if (acknowledgementHeaderPOJO.getAckstatus().trim().equalsIgnoreCase("CR")) {//repair-chargable repair
                        mtSalesReturn.setFyear(String.valueOf(acknowledgementHeaderPOJO.getFiscalyear()));
                        try {
                            mtSalesReturn.setCreatedName(acknowledgementHeaderPOJO.getCreatedby());
                            if (!Validations.isFieldNotEmpty(mtSalesReturn.getReturnChannel()) && Validations.isFieldNotEmpty(acknowledgementHeaderPOJO.getSelectedStore())) {
                                mtSalesReturn.setReturnChannel(acknowledgementHeaderPOJO.getSelectedStore());
                            }
                            try {
                                java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(acknowledgementHeaderPOJO.getCreateddate());
                                Calendar createdTime = ConvertDate.getSqlTimeFromString(acknowledgementHeaderPOJO.getCreatedtime());
                                if (createdDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(createdDate);
                                    if (createdTime != null) {
                                        xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                                        if (xmlDate != null) {
                                            mtSalesReturn.setCreatedTime(xmlDate);
                                        }
                                    }
                                    if (xmlDate != null) {
                                        mtSalesReturn.setCreatedDate(xmlDate);
                                    }

                                }
                            } catch (Exception e) {

                            }
                            mtSalesReturn.setModifiedBy(acknowledgementHeaderPOJO.getModifiedby());
                            //Code added on 21 Jun 2011 for name capture during sale return
                            //mtSalesReturn.setModifiedBy(acknowledgementHeaderPOJO.getModifiedby());
                            java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(acknowledgementHeaderPOJO.getModifieddate());
                            Calendar createdTime = ConvertDate.getSqlTimeFromString(acknowledgementHeaderPOJO.getModifiedtime());
                            if (createdDate != null) {
                                XMLCalendar xmlDate = new XMLCalendar(createdDate);
                                if (createdTime != null) {
                                    xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                                    if (xmlDate != null) {
                                        mtSalesReturn.setModifiedTime(xmlDate);
                                    }
                                }
                                if (xmlDate != null) {
                                    mtSalesReturn.setModifiedDate(xmlDate);
                                }

                            }
                             if(Validations.isFieldNotEmpty(acknowledgementHeaderPOJO.getOtherchannelname()))
                            {
                                mtSalesReturn.setCHANNELNAME(acknowledgementHeaderPOJO.getOtherchannelname());
                            }
                            if (Validations.isFieldNotEmpty(salesReturnHeaderPOJO.getCancelOtp())) {
                                mtSalesReturn.setCancelledOTP(salesReturnHeaderPOJO.getCancelOtp());
                            }
                           
                        } catch (Exception e) {

                        }
                    } else if (acknowledgementHeaderPOJO.getAckstatus().trim().equalsIgnoreCase("RC")) {//Return to customer
                        mtSalesReturn.setFyear(String.valueOf(acknowledgementHeaderPOJO.getFiscalyear()));
                        mtSalesReturn.setCreatedName(acknowledgementHeaderPOJO.getCreatedby());
                        if (!Validations.isFieldNotEmpty(mtSalesReturn.getReturnChannel()) && Validations.isFieldNotEmpty(acknowledgementHeaderPOJO.getSelectedStore())) {
                            mtSalesReturn.setReturnChannel(acknowledgementHeaderPOJO.getSelectedStore());
                        }
                        try {
                            java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(acknowledgementHeaderPOJO.getCreateddate());
                            Calendar createdTime = ConvertDate.getSqlTimeFromString(acknowledgementHeaderPOJO.getCreatedtime());
                            if (createdDate != null) {
                                XMLCalendar xmlDate = new XMLCalendar(createdDate);
                                if (createdTime != null) {
                                    xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                                    if (xmlDate != null) {
                                        mtSalesReturn.setCreatedTime(xmlDate);
                                    }
                                }
                                if (xmlDate != null) {
                                    mtSalesReturn.setCreatedDate(xmlDate);
                                }

                            }
                        } catch (Exception e) {

                        }
                        mtSalesReturn.setModifiedBy(acknowledgementHeaderPOJO.getModifiedby());
                        //Code added on 21 Jun 2011 for name capture during sale return
                        //mtSalesReturn.setModifiedBy(acknowledgementHeaderPOJO.getModifiedby());
                        try {
                            java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(acknowledgementHeaderPOJO.getModifieddate());
                            Calendar createdTime = ConvertDate.getSqlTimeFromString(acknowledgementHeaderPOJO.getModifiedtime());
                            if (createdDate != null) {
                                XMLCalendar xmlDate = new XMLCalendar(createdDate);
                                if (createdTime != null) {
                                    xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                                    if (xmlDate != null) {
                                        mtSalesReturn.setModifiedTime(xmlDate);
                                    }
                                }
                                if (xmlDate != null) {
                                    mtSalesReturn.setModifiedDate(xmlDate);
                                }

                            }
                            if(Validations.isFieldNotEmpty(acknowledgementHeaderPOJO.getOtherchannelname()))
                            {
                                mtSalesReturn.setCHANNELNAME(acknowledgementHeaderPOJO.getOtherchannelname());
                            }
//                            if (Validations.isFieldNotEmpty(salesReturnHeaderPOJO.getCancelOtp())) {
//                                mtSalesReturn.setCancelledOTP(salesReturnHeaderPOJO.getCancelOtp());
//                            }
                            if (Validations.isFieldNotEmpty(acknowledgementHeaderPOJO.getSFComplaintID())) {
                                System.err.println("IN RETURN TO CUSTOMER");
                                mtSalesReturn.setSFCaseId(acknowledgementHeaderPOJO.getSFComplaintID());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mtSalesReturn.setCustReturnReason(acknowledgementHeaderPOJO.getReturntocustomerreason());
                    }
                    if (acknowledgementHeaderPOJO.getAckstatus().trim().equalsIgnoreCase("HO") || acknowledgementHeaderPOJO.getAckstatus().trim().equalsIgnoreCase("RF")) { //If Sales Return or ack only
                        if (ackLineItems != null) {
                            Iterator ackItems = ackLineItems.iterator();
                            if (ackItems != null) {
                                ArrayList<in.co.titan.salesreturn.DTSalesReturn.SOCondition> soConditions = new ArrayList<in.co.titan.salesreturn.DTSalesReturn.SOCondition>();
                                ArrayList<in.co.titan.salesreturn.DTSalesReturn.SOItem> soItems = new ArrayList<in.co.titan.salesreturn.DTSalesReturn.SOItem>();
                                ArrayList<in.co.titan.salesreturn.DTSalesReturn.DefectReason> defectReasons = new ArrayList<in.co.titan.salesreturn.DTSalesReturn.DefectReason>();

                                in.co.titan.salesreturn.DTSalesReturn.SOItem soItem = null;
                                in.co.titan.salesreturn.DTSalesReturn.SOCondition sOCondition = null;
                                in.co.titan.salesreturn.DTSalesReturn.DefectReason defectReason = null;
                                SOLineItemPOJO ackItemPojo = null;
                                int lineNo = 0;
                                while (ackItems.hasNext()) {
                                    ackItemPojo = (SOLineItemPOJO) ackItems.next();
                                    int j = 0;
                                    if (ackItemPojo != null) {
                                        if (!(Validations.isFieldNotEmpty(division)) && Validations.isFieldNotEmpty(ackItemPojo.getMaterial())) {
                                            division = articleDo.getDivisionByMaterial(con, ackItemPojo.getMaterial());
                                        }
                                        soItem = new in.co.titan.salesreturn.DTSalesReturn.SOItem();
                                        soItem.setBilledQty(String.valueOf(ackItemPojo.getQuantity()));
                                        soItem.setItemCode(ackItemPojo.getMaterial());
                                        if (Validations.isFieldNotEmpty(ackItemPojo.getFrombatch())) {
                                            soItem.setFromBatch(ackItemPojo.getFrombatch());
                                        }
                                        soItem.setLineNo(Integer.toString(++lineNo));
                                        soItem.setReturnQty(String.valueOf(ackItemPojo.getReturnedqty()));
                                        soItem.setAckBillAmount(new BigDecimal(decimalFormat.format(ackItemPojo.getNetamount())));
                                        soItem.setAckRetNetAmount(new BigDecimal(decimalFormat.format(ackItemPojo.getReturnednetamount())));
                                        soItem.setReturnReason(ackItemPojo.getReturnreason());
                                        if (Validations.isFieldNotEmpty(ackItemPojo.getOtherretreason())) {
                                            soItem.setOtherReturnReason(ackItemPojo.getOtherretreason());
                                        }
                                        BigDecimal taxableVal = new BigDecimal(decimalFormat.format(ackItemPojo.getTaxableValue()));
                                        soItem.setTaxableValue(taxableVal);
                                        soItems.add(soItem);
                                        if (ackItemPojo.getUCP() != null) {
                                            sOCondition = setSoConDition(ackItemPojo.getUCP(), lineNo, ++j, "U", ackItemPojo.getMaterial());
                                            if (sOCondition != null) {
                                                if (sOCondition.getPOSCondType() != null) {
                                                    soConditions.add(sOCondition);
                                                }
                                            }
                                        }
                                        if (ackItemPojo.isOtherChargesPresent()) {
                                            if (ackItemPojo.getOtherCharges() != null) {
                                                for (ConditionTypePOJO conditionTypePOJO : ackItemPojo.getOtherCharges()) {
                                                    if (conditionTypePOJO != null) {
                                                        if (conditionTypePOJO.getDummyconditiontype() != null) {
                                                            sOCondition = setSoConDition(conditionTypePOJO, lineNo, ++j, "O", ackItemPojo.getMaterial());
                                                            if (sOCondition != null) {
                                                                if (sOCondition.getPOSCondType() != null) {
                                                                    soConditions.add(sOCondition);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (ackItemPojo.getDiscountSelected() != null) {
                                            sOCondition = setSoConDition(ackItemPojo.getDiscountSelected(), lineNo, ++j, "D", ackItemPojo.getMaterial());
                                            if (sOCondition != null) {
                                                if (sOCondition.getPOSCondType() != null) {
                                                    soConditions.add(sOCondition);
                                                }
                                            }
                                        }
                                        //Added by Thangaraj - 07.07.2014
                                        if (ackItemPojo.getULPdiscountSelected() != null) {
                                            sOCondition = setSoConDition(ackItemPojo.getULPdiscountSelected(), lineNo, ++j, "D", ackItemPojo.getMaterial());
                                            if (sOCondition != null) {
                                                if (sOCondition.getPOSCondType() != null) {
                                                    soConditions.add(sOCondition);
                                                }
                                            }
                                        }
                                        //End: Added by Thangaraj - 07.07.2014
                                        if (ackItemPojo.getOfferPromotion() != null) {
                                            sOCondition = setSoConDition(ackItemPojo.getOfferPromotion(), lineNo, ++j, "P", ackItemPojo.getMaterial());
                                            if (sOCondition != null) {
                                                if (sOCondition.getPOSCondType() != null) {
                                                    soConditions.add(sOCondition);
                                                }
                                            }
                                        }
                                        if (ackItemPojo.getPromotionSelected() != null) {
                                            if (Validations.isFieldNotEmpty(ackItemPojo.getPromotionSelected().getPromotionID())) {
                                                soItem.setPromotionID(ackItemPojo.getPromotionSelected().getPromotionID());
                                            }
                                        }
                                        if (ackItemPojo.getTaxDetails() != null) {
                                            Iterator iterator = ackItemPojo.getTaxDetails().iterator();
                                            while (iterator.hasNext()) {
                                                ConditionTypePOJO conditionTypePOJO = (ConditionTypePOJO) iterator.next();
                                                sOCondition = setSoConDition(conditionTypePOJO, lineNo, ++j, "T", ackItemPojo.getMaterial());
                                                if (sOCondition != null) {
                                                    if (sOCondition.getPOSCondType() != null) {
                                                        soConditions.add(sOCondition);
                                                    }
                                                }
                                            }
                                        }
                                        ArrayList<AckDefectReasonPOJO> ackDefectPojos = ackItemPojo.getAckdefectreasonpojos();
                                        if (ackDefectPojos != null) {
                                            Iterator defItr = ackDefectPojos.iterator();
                                            if (defItr != null) {
                                                while (defItr.hasNext()) {
                                                    AckDefectReasonPOJO defPojo = (AckDefectReasonPOJO) defItr.next();
                                                    if (defPojo != null) {
                                                        String defReasonId = null, defReasonText = null;
                                                        if (Validations.isFieldNotEmpty(defPojo.getDefectreasonID())) {
                                                            defReasonId = defPojo.getDefectreasonID();
                                                            if (defReasonId != null) {
                                                                defReasonText = defReasonKeyValues.getProperty(defReasonId);
                                                                if (defReasonId.trim().equalsIgnoreCase("99")) {
                                                                    defReasonText = defPojo.getDefOtherReasonDesc();
                                                                }
                                                                if (defReasonText != null) {
                                                                    defectReason = new in.co.titan.salesreturn.DTSalesReturn.DefectReason();
                                                                    defectReason.setRefLineItem(new BigInteger(Integer.toString(lineNo)));
                                                                    defectReason.setItemCode(ackItemPojo.getMaterial());
                                                                    if (acknowledgementHeaderPOJO.getAckstatus().trim().equalsIgnoreCase("HO")) {
                                                                        defectReason.setDefectReason(defReasonId);
                                                                        defectReason.setOthers(defPojo.getDefOtherReasonDesc());
                                                                    } else if (acknowledgementHeaderPOJO.getAckstatus().trim().equalsIgnoreCase("RF")) {
                                                                        defectReason.setDefectReason("(" + defReasonId + ") " + defReasonText);
                                                                    }
                                                                    defectReasons.add(defectReason);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    }
                                }
                                if (acknowledgementHeaderPOJO.getAckstatus().trim().equalsIgnoreCase("RF")) { //If Sales Return only
                                    mtSalesReturn.setDivision(division);
                                }
                                mtSalesReturn.getDefectReason().addAll(defectReasons);
                                mtSalesReturn.getSOItem().addAll(soItems);
                                mtSalesReturn.getSOCondition().addAll(soConditions);
                            }
                        }
                    }
                }

                //Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available          
                System.out.println("Salereturn/Acknowledgement Storecode " + mtSalesReturn.getStoreCode());

                //if(mtSalesReturn.getStoreCode()!=null && mtSalesReturn.getStoreCode().trim().length()>0){
                if (mtSalesReturn.getSITESEARCH() != null && mtSalesReturn.getSITESEARCH().trim().length() > 0) {
                    ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
                    ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());
                    ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port() + "/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_SalesReturn&version=3.0&Sender.Service=BS_TITAN_POS&Interface=http%3A%2F%2Ftitan.co.in%2FSalesReturn%5EMI_OB_ASYN_SalesReturn");
                    port.miOBASYNSalesReturn(mtSalesReturn);
                    Map responseContext = ((BindingProvider) port).getResponseContext();
                    Integer responseCode = (Integer) responseContext.get(MessageContext.HTTP_RESPONSE_CODE);

                    if (responseCode.intValue() == 200) {
                        updatedInISR = "T";
                    } else {
                        return "F";
                    }
                } else {
                    return "F";
                }
                //End of Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 

                //Code commented by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                /*((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
                ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());

                port.miOBASYNSalesReturn(mtSalesReturn);
                Map responseContext = ((BindingProvider) port).getResponseContext();
                Integer responseCode = (Integer) responseContext.get(MessageContext.HTTP_RESPONSE_CODE);

                if (responseCode.intValue() == 200) {
                    updatedInISR = "T";
                } else {
                    return "F";
                }*/
//                if (result != null) {
//                    String invoiceDocNo = result.getInvoiceDocNo();
//                    String accountingDocNo = result.getAccountingDocNo();
//                    if (creditNotePOJO != null && Validations.isFieldNotEmpty(invoiceDocNo)) {
//                        new CreditNoteDO().UpdateSaleOrdersapreferenceid(con, creditNotePOJO.getCreditnoteno(), invoiceDocNo);
//                    }
//                    updatedInISR = "T";
//                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return updatedInISR;
    }

    public in.co.titan.salesreturn.DTSalesReturn.SOCondition setSoConDition(ConditionTypePOJO conditionTypePOJO, int refLineItemNo, int condLineItemNo, String typeOfCondition, String itemCode) {
        in.co.titan.salesreturn.DTSalesReturn.SOCondition sOCondition = null;
        DecimalFormat df = new DecimalFormat("0.00");
        if (conditionTypePOJO != null) {
            sOCondition = new in.co.titan.salesreturn.DTSalesReturn.SOCondition();
            sOCondition.setRefLineItem(new BigInteger(Integer.toString(refLineItemNo)));
            sOCondition.setCondLineItemNO(new BigInteger(Integer.toString(condLineItemNo)));
            if (conditionTypePOJO.getPromotionId() != null) {
                sOCondition.setPromotionID(conditionTypePOJO.getPromotionId());
            }

            if (Validations.isFieldNotEmpty(conditionTypePOJO.getDummyconditiontype())) {
                sOCondition.setPOSCondType(conditionTypePOJO.getDummyconditiontype());
            }

            if (Validations.isFieldNotEmpty(conditionTypePOJO.getValue())) {
                sOCondition.setAmount(new BigDecimal(df.format(conditionTypePOJO.getValue())));
            }

            if (Validations.isFieldNotEmpty(conditionTypePOJO.getCalculationtype())) {
                sOCondition.setCalcType(conditionTypePOJO.getCalculationtype());
            }

            if (Validations.isFieldNotEmpty(conditionTypePOJO.getNetAmount())) {
                sOCondition.setNetAmount(new BigDecimal(df.format(conditionTypePOJO.getNetAmount())));
            }

            if (Validations.isFieldNotEmpty(typeOfCondition)) {
                sOCondition.setTypeOfCondtion(typeOfCondition);
            }

            if (Validations.isFieldNotEmpty(itemCode)) {
                sOCondition.setItemCode(itemCode);
            }
            //Added by Thangaraj - 07.07.2014
            if (Validations.isFieldNotEmpty(conditionTypePOJO.getUlpno())) {
                sOCondition.setULPNo(conditionTypePOJO.getUlpno());
            }
            if (Validations.isFieldNotEmpty(conditionTypePOJO.getLoyaltyPoints())) {
                if (conditionTypePOJO.getLoyaltyPoints() > 0) {
                    sOCondition.setLoyaltyPoints(String.valueOf(conditionTypePOJO.getLoyaltyPoints()));
                }
            }
            try {
                java.util.Date asondate = ConvertDate.getUtilDateFromNumericDate(conditionTypePOJO.getAsOnDate());

                if (asondate != null) {
                    XMLCalendar xmlDate = new XMLCalendar(asondate);

                    if (xmlDate != null) {
                        sOCondition.setAsOnDate(xmlDate);
                    }

                }
            } catch (Exception e) {
            }
            if (Validations.isFieldNotEmpty(conditionTypePOJO.getLoyaltyRedeemedPoints())) {
                if (conditionTypePOJO.getLoyaltyRedeemedPoints() > 0) {
                    sOCondition.setLoyaltyRedeemedPoints(String.valueOf(conditionTypePOJO.getLoyaltyRedeemedPoints()));
                }
            }
            if (Validations.isFieldNotEmpty(conditionTypePOJO.getRrno())) {
                sOCondition.setLoyaltyApprovalCode(conditionTypePOJO.getRrno());
            }
            //End: Added by Thangaraj - 07.07.2014
        }
        return sOCondition;
    }
}
