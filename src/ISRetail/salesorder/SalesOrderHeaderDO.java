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
package ISRetail.salesorder;

import ISRetail.Helpers.ConvertDate;
import ISRetail.Helpers.XMLCalendar;
import ISRetail.Webservices.ConditionTypePOJO;
import ISRetail.Webservices.DataObject;
import ISRetail.Webservices.Webservice;
import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.article.ArticleDO;
import ISRetail.inquiry.CharacteristicDO;
import ISRetail.inquiry.ClinicalHistoryDO;
import ISRetail.inquiry.ClinicalHistoryPOJO;
import ISRetail.inquiry.ContactLensDO;
import ISRetail.inquiry.ContactLensPOJO;
import ISRetail.inquiry.Inquiry_POS;
import ISRetail.inquiry.PrescriptionDO;
import ISRetail.inquiry.PrescriptionPOJO;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.plantdetails.SiteMasterPOJO;
import ISRetail.serverconsole.ServerConsole;
import ISRetail.utility.validations.Validations;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

/**
 *
 * @author Administrator
 */
public class SalesOrderHeaderDO implements Webservice {

    /**
     * *****End of Getting Bill amount from salesorder to advance receipt
     * **************
     */
    /**
     * *******************update release status and sale type from advance
     * receipt********************
     */
    public SalesOrderHeaderPOJO getstatusandsaletypeandprintoption(Connection con, SalesOrderHeaderPOJO pojoobj, String saleorderno) {

        Statement pstmt;
        String searchstatement;
        ResultSet rs;
        try {
            pstmt = con.createStatement();
            searchstatement = "select saletype,releasestatus,printordertype,creditsalereference from tbl_salesorderheader where saleorderno='" + saleorderno + "'";
            rs = pstmt.executeQuery(searchstatement);
            while (rs.next()) {
                pojoobj.setSaletype(rs.getString("saletype"));
                pojoobj.setReleaseStatus(rs.getString("releasestatus"));
                pojoobj.setPrintordertype(rs.getString("printordertype"));
                pojoobj.setCreditsalereference(rs.getString("creditsalereference"));
                return pojoobj;
            }
            return null;
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        }

    }

    public SalesOrderHeaderPOJO getCreditSaleReferenceDetails(Connection con, String saleorderno) {
        SalesOrderHeaderPOJO salesorderheaderpojoobj = null;
        Statement pstmt;
        String searchstatement;
        ResultSet rs;
        try {
            pstmt = con.createStatement();
            searchstatement = "select tbl_salesorderheader.*,tbl_creditsalereference.institutionname  institutionname from tbl_salesorderheader left outer join tbl_creditsalereference on tbl_salesorderheader.creditsalereference = tbl_creditsalereference.sapcustomerno where saleorderno='" + saleorderno + "'";
            System.out.println("searchstmt" + searchstatement);
            rs = pstmt.executeQuery(searchstatement);
            if (rs.next()) {
                salesorderheaderpojoobj = new SalesOrderHeaderPOJO();
                salesorderheaderpojoobj.setStoreCode(rs.getString("storecode"));
                salesorderheaderpojoobj.setFiscalYear(rs.getInt("fiscalyear"));
                salesorderheaderpojoobj.setSaleorderno(rs.getString("saleorderno"));
                salesorderheaderpojoobj.setSaleorderdate(rs.getInt("saleorderdate"));
                salesorderheaderpojoobj.setCustomercode(rs.getString("customercode"));
                salesorderheaderpojoobj.setDatasheetno(rs.getString("datasheetno"));
                salesorderheaderpojoobj.setPrintordertype(rs.getString("printordertype"));
                salesorderheaderpojoobj.setSegmentSize(rs.getString("segmentsize"));
                salesorderheaderpojoobj.setSegmentHeight(rs.getString("segmentheight"));
                salesorderheaderpojoobj.setEd(rs.getString("institutionname"));                     ///////   Credit sale reference name(Institution name)
                salesorderheaderpojoobj.setDistancepd(rs.getString("distancepd"));
                salesorderheaderpojoobj.setNearpd(rs.getString("nearpd"));
                salesorderheaderpojoobj.setFittinglabInstruction(rs.getString("fittinglabinstruction"));
                salesorderheaderpojoobj.setLensvendorInstruction(rs.getString("lensvendor"));
                salesorderheaderpojoobj.setPriority(rs.getInt("priority"));
                salesorderheaderpojoobj.setPlannedDate(rs.getInt("planneddate"));
                salesorderheaderpojoobj.setProposedDate(rs.getInt("proposeddate"));
                salesorderheaderpojoobj.setTargetcompletedate(rs.getInt("targetdate"));
                salesorderheaderpojoobj.setVerifiedby(rs.getString("verifiedby"));
                salesorderheaderpojoobj.setAmount(rs.getDouble("amount"));
                salesorderheaderpojoobj.setSaletype(rs.getString("saletype"));
                salesorderheaderpojoobj.setReleaseStatus(rs.getString("releasestatus"));
                salesorderheaderpojoobj.setOrderStatus(rs.getString("orderstatus"));
                salesorderheaderpojoobj.setOldSaleorderNo(rs.getString("oldsaleordernumber"));
                salesorderheaderpojoobj.setReasonforReturn(rs.getString("reasonforreturn"));
                salesorderheaderpojoobj.setPrintordertype(rs.getString("printordertype"));
                salesorderheaderpojoobj.setCreditsalereference(rs.getString("creditsalereference"));
                salesorderheaderpojoobj.setCreatedBy(rs.getString("createdby"));
                salesorderheaderpojoobj.setCreatedDate(rs.getInt("createddate"));
                salesorderheaderpojoobj.setCreatedTime(rs.getString("createdtime"));
                salesorderheaderpojoobj.setModifiedBy(rs.getString("modifiedby"));
                salesorderheaderpojoobj.setModifiedDate(rs.getInt("modifieddate"));
                salesorderheaderpojoobj.setModifiedTime(rs.getString("modifiedtime"));
                salesorderheaderpojoobj.setProcreq(rs.getString("proc_req"));
                //Code added on June 1st 2010
                salesorderheaderpojoobj.setProcreq_left(rs.getString("proc_req_left"));
                salesorderheaderpojoobj.setInsforuser(rs.getString("instructionforuser"));
                salesorderheaderpojoobj.setApproveddate(rs.getInt("approveddate"));
                salesorderheaderpojoobj.setReleasedate(rs.getInt("releasedate"));
                //End of Code added on June 1st 2010
                //code added on 9 feb 2012 for division tag issue in payload
                salesorderheaderpojoobj.setDivision(new SalesOrderDetailsDO().getArticleDivisionBySaleOrderNo(con, saleorderno));
                //Added by Dileep - 17.04.2014
                salesorderheaderpojoobj.setReferralVoucherNO(rs.getString("vistarefgiftvocno"));
                salesorderheaderpojoobj.setReferralRedeemNO(rs.getString("vistarefvalno"));

            }

        } catch (SQLException sQLException) {
            sQLException.printStackTrace();

        }
        return salesorderheaderpojoobj;

    }

    /**
     * *******************update release status and sale type from advance
     * receipt********************
     */
    public int updatestatusandsaletype(String sonumber, String status, String saletype, String creditsalereference, Connection con) {
        Statement pstmt;
        String searchstatement;
        try {
            pstmt = con.createStatement();
            searchstatement = "update tbl_salesorderheader set saletype='" + saletype + "',releasestatus='" + status + "',creditsalereference='" + creditsalereference + "' where saleorderno='" + sonumber + "'";
            System.out.println("searchstmt" + searchstatement);
            int res = pstmt.executeUpdate(searchstatement);
            return res;
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }

        return 0;
    }

    /*Changed on 4.9.2008    saleorder display*/
 /* Sale order display section (Displaying data based on sales order number   */
    public SalesOrderHeaderPOJO getSaleOrderHeaderBySaleOrderNo(Connection conn, String saleorderno) {
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select * from tbl_salesorderheader where saleorderno=? ");
            pstmt.setString(1, saleorderno);
            rs = pstmt.executeQuery();
            SalesOrderHeaderPOJO salesorderheaderpojoobj = new SalesOrderHeaderPOJO();
            if (rs.next()) {

                //   salesorderheaderpojoobj.setSlno(rs.getInt("slno"));
                // salesorderheaderpojoobj.setSaleorderno(rs.getString("saleorderno"));
                salesorderheaderpojoobj.setStoreCode(rs.getString("storecode"));
                salesorderheaderpojoobj.setFiscalYear(rs.getInt("fiscalyear"));
                salesorderheaderpojoobj.setSaleorderno(rs.getString("saleorderno"));
                salesorderheaderpojoobj.setSaleorderdate(rs.getInt("saleorderdate"));
                salesorderheaderpojoobj.setCustomercode(rs.getString("customercode"));
                salesorderheaderpojoobj.setDatasheetno(rs.getString("datasheetno"));
                salesorderheaderpojoobj.setPrintordertype(rs.getString("printordertype"));
                salesorderheaderpojoobj.setSegmentSize(rs.getString("segmentsize"));
                salesorderheaderpojoobj.setSegmentHeight(rs.getString("segmentheight"));
                salesorderheaderpojoobj.setEd(rs.getString("ed"));
                salesorderheaderpojoobj.setDistancepd(rs.getString("distancepd"));
                salesorderheaderpojoobj.setNearpd(rs.getString("nearpd"));
                salesorderheaderpojoobj.setFittinglabInstruction(rs.getString("fittinglabinstruction"));
                salesorderheaderpojoobj.setLensvendorInstruction(rs.getString("lensvendor"));
                salesorderheaderpojoobj.setPriority(rs.getInt("priority"));
                salesorderheaderpojoobj.setPlannedDate(rs.getInt("planneddate"));
                salesorderheaderpojoobj.setProposedDate(rs.getInt("proposeddate"));
                salesorderheaderpojoobj.setTargetcompletedate(rs.getInt("targetdate"));
                salesorderheaderpojoobj.setVerifiedby(rs.getString("verifiedby"));
                salesorderheaderpojoobj.setAmount(rs.getDouble("amount"));
                salesorderheaderpojoobj.setSaletype(rs.getString("saletype"));
                salesorderheaderpojoobj.setReleaseStatus(rs.getString("releasestatus"));
                salesorderheaderpojoobj.setOrderStatus(rs.getString("orderstatus"));
                salesorderheaderpojoobj.setOldSaleorderNo(rs.getString("oldsaleordernumber"));
                salesorderheaderpojoobj.setReasonforReturn(rs.getString("reasonforreturn"));
                salesorderheaderpojoobj.setPrintordertype(rs.getString("printordertype"));
                salesorderheaderpojoobj.setCreditsalereference(rs.getString("creditsalereference"));
                salesorderheaderpojoobj.setCreatedBy(rs.getString("createdby"));
                salesorderheaderpojoobj.setCreatedDate(rs.getInt("createddate"));
                salesorderheaderpojoobj.setCreatedTime(rs.getString("createdtime"));
                salesorderheaderpojoobj.setModifiedBy(rs.getString("modifiedby"));
                salesorderheaderpojoobj.setModifiedDate(rs.getInt("modifieddate"));
                salesorderheaderpojoobj.setModifiedTime(rs.getString("modifiedtime"));
                salesorderheaderpojoobj.setProcreq(rs.getString("proc_req"));
                //Code added on June 1st 2010
                salesorderheaderpojoobj.setProcreq_left(rs.getString("proc_req_left"));
                salesorderheaderpojoobj.setInsforuser(rs.getString("instructionforuser"));
                salesorderheaderpojoobj.setApproveddate(rs.getInt("approveddate"));
                salesorderheaderpojoobj.setReleasedate(rs.getInt("releasedate"));
                //End of Code added on June 1st 2010
                //Added by Dileep - 17.04.2014
                salesorderheaderpojoobj.setReferralVoucherNO(rs.getString("vistarefgiftvocno"));
                salesorderheaderpojoobj.setReferralRedeemNO(rs.getString("vistarefvalno"));
                salesorderheaderpojoobj.setReferencePromoOrder(rs.getString("secondsale_refno"));
                salesorderheaderpojoobj.setExternalInvoiceNo(rs.getString("externalInvNo"));
                salesorderheaderpojoobj.setDeliveryMode(rs.getString("deliverymode"));
                salesorderheaderpojoobj.setRecon_reason(rs.getString("recon_reason"));
                salesorderheaderpojoobj.setSampleTintType(rs.getString("SampleTintType"));
                salesorderheaderpojoobj.setSampleTintRemarks(rs.getString("SampleTintRemarks"));
                salesorderheaderpojoobj.setWhStock(rs.getString("WHStock_Availability"));//added by chara for WareHouse stock
                salesorderheaderpojoobj.setcustname(rs.getString("customername"));//added by surekha for salesorder service
                salesorderheaderpojoobj.setMobileno(rs.getString("mobileno"));
                salesorderheaderpojoobj.setGst_no(rs.getString("gstn_no"));//end of code added by surekha k
                return salesorderheaderpojoobj;
            } else {
                return null;
            }
        } catch (SQLException sQLException) {
            System.out.println("exception in saleorderheader1 Searching=" + sQLException);
            sQLException.printStackTrace();
            return null;
        }

    }

    public SalesOrderHeaderPOJO getSaleordercancellationDetailsBySaleorderNo(Connection conn, String saleorderno) {
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select * from tbl_salesorderheader where saleorderno=? ");
            pstmt.setString(1, saleorderno);
            rs = pstmt.executeQuery();
            SalesOrderHeaderPOJO salesorderheaderpojoobj = new SalesOrderHeaderPOJO();
            if (rs.next()) {

                salesorderheaderpojoobj.setStoreCode(rs.getString("storecode"));
                salesorderheaderpojoobj.setFiscalYear(rs.getInt("fiscalyear"));
                salesorderheaderpojoobj.setSaleorderno(rs.getString("saleorderno"));
                salesorderheaderpojoobj.setSaleordercancellationdate(rs.getInt("dateofcancellation"));
                salesorderheaderpojoobj.setCustomercode(rs.getString("customercode"));
                salesorderheaderpojoobj.setDatasheetno(rs.getString("datasheetno"));
                salesorderheaderpojoobj.setReasonforcancellation(rs.getString("reasonforcancellation"));
                salesorderheaderpojoobj.setOrderStatus(rs.getString("orderstatus"));
                salesorderheaderpojoobj.setCancelledTime(rs.getString("cancelledtime"));
                salesorderheaderpojoobj.setCancelledby(rs.getString("cancelledby"));
                salesorderheaderpojoobj.setCancelledby(rs.getString("cancelledby"));
                //code added on 21 Jun 2011 for name capture in Sale Return
                salesorderheaderpojoobj.setStaffresponsible(rs.getString("staffresponsible"));
                salesorderheaderpojoobj.setCancelOtp(rs.getString("cancelotp"));
                return salesorderheaderpojoobj;
            } else {
                return null;
            }
        } catch (SQLException sQLException) {
            System.out.println("exception in saleorderheader1 Searching=" + sQLException);
            sQLException.printStackTrace();
            return null;
        }

    }


    /*=====================Validating OLD Sale orderNo ======================*/
    public String getoldSaleorderNo(Connection con, String salorderno) {
        Statement pstmt;
        String searchstatement;
        ResultSet rs;
        try {
            pstmt = con.createStatement();
            searchstatement = "select saleorderno from tbl_salesorderheader where saleorderno='" + salorderno + "' and orderstatus='BILLED'";
            rs = pstmt.executeQuery(searchstatement);
            if (rs.next()) {
                return rs.getString(1);
            } else {
                return null;
            }
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        }

    }

    /*   4.9.08    sale order display*/
    public int getSaleOrderDateBySaleOrderNo(Connection conn, String saleorderno) {

        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select saleorderdate from tbl_salesorderheader where saleorderno like'%" + saleorderno + "%'");
            rs = pstmt.executeQuery();
            SalesOrderHeaderPOJO salesorderheaderpojoobj = new SalesOrderHeaderPOJO();
            if (rs.next()) {

                int salesorderdate = rs.getInt(1);

                return salesorderdate;
            } else {
                return 0;
            }

        } catch (SQLException sQLException) {
            System.out.println("exception in tbl_saleorderheader1 Searching=" + sQLException);
            sQLException.printStackTrace();
            return 0;
        }
    }

    public int archiveAllSalesOrderTables(Connection con, int fiscalYear) throws SQLException {
        int recDeleted = 0;
        PreparedStatement pstmt;
        String query;
        ClinicalHistoryDO clinicalHistoryDO;
        PrescriptionDO prescriptionDO;
        ContactLensDO contactLensDO;
        CharacteristicDO characteristicDO;
        try {
            clinicalHistoryDO = new ClinicalHistoryDO();
            prescriptionDO = new PrescriptionDO();
            characteristicDO = new CharacteristicDO();
            contactLensDO = new ContactLensDO();
            int res = characteristicDO.archiveAllCharacteristicsForFiscalYear(fiscalYear, con, "SO");
            recDeleted = recDeleted + res;
            res = clinicalHistoryDO.archiveAllClinicalHistoryForFiscalYear(fiscalYear, con, "SO");
            recDeleted = recDeleted + res;
            res = prescriptionDO.archiveAllPrescriptionForFiscalYear(fiscalYear, con, "SO");
            recDeleted = recDeleted + res;
            res = contactLensDO.archiveAllContactLensForFiscalYear(fiscalYear, con, "SO");
            recDeleted = recDeleted + res;

            query = "delete from tbl_so_condition where fisaclyear= ?";
            pstmt = con.prepareStatement(query);
            res = pstmt.executeUpdate();
            recDeleted = recDeleted + res;

            query = "delete from tbl_salesorderlineitems where fisaclyear= ?";
            pstmt = con.prepareStatement(query);
            res = pstmt.executeUpdate();
            recDeleted = recDeleted + res;

            query = "delete from tbl_salesorderheader where fisaclyear= ?";
            pstmt = con.prepareStatement(query);
            res = pstmt.executeUpdate();
            recDeleted = recDeleted + res;
        } catch (Exception e) {
            e.printStackTrace();
            recDeleted = 0;
            throw new SQLException();
        } finally {
            query = null;
            clinicalHistoryDO = null;
            prescriptionDO = null;
            characteristicDO = null;
            contactLensDO = null;
        }
        return recDeleted;
    }

    public String execute(DataObject obj, String updateType) {
        String updatedInISR = "false";
        ClinicalHistoryPOJO cliniPojo = null;
        PrescriptionPOJO specPojo = null;
        ContactLensPOJO clensPojo = null;
        SalesOrderHeaderPOJO salesorderheaderpojoobj = null;

        ArrayList<SOLineItemPOJO> salesorderdetailspojoobjlist = null;

        MsdeConnection msdeconn = new MsdeConnection();
        Connection con = msdeconn.createConnection();

        /*Retrieving CompanyCode from siteMaster*/
        SiteMasterDO smdo = new SiteMasterDO();
        SiteMasterPOJO ampojoobj = new SiteMasterPOJO();
        String companyCode1 = smdo.getSiteCompanyCode(con);
        System.out.println("Company Code:" + companyCode1);
        ampojoobj.setCompanycode(companyCode1);
        boolean ucpcheck = true;

        /*Retrieving DIVISION from articleMaster*/
        // ArticleMasterDO  armdo=new ArticleMasterDO();
        if (obj instanceof SaleOrderMasterBean) {
            try { // Call Web Service Operation

                SaleOrderMasterBean masterBean = new SaleOrderMasterBean();
                masterBean = (SaleOrderMasterBean) obj;

                cliniPojo = masterBean.getCliniPojo();
                specPojo = masterBean.getPresPojo();
                clensPojo = masterBean.getClensPojo();
                salesorderheaderpojoobj = masterBean.getSalesOrderHeader();
                salesorderdetailspojoobjlist = masterBean.getSalesOrderDetails();

                in.co.titan.salesorder.MIOBASYNSalesOrderService service = new in.co.titan.salesorder.MIOBASYNSalesOrderService();
//                in.co.titan.salesorder.MIOBASYNSalesOrder port = service.getMIOBASYNSalesOrderPort();
                //in.co.titan.salesorder.MIOBASYNSalesOrder port = service.getHTTPPort();
                //in.co.titan.salesorder.MIOBASYNSalesOrder port = service.getHTTPPort();
                in.co.titan.salesorder.MIOBASYNSalesOrder port = service.getHTTPSPort();
                System.out.println("Sales Order Port No is 1st Sep" + port);
// TODO initialize WS operation arguments here
                Map<String, Object> ctxt = ((BindingProvider) port).getRequestContext();
                //ctxt.put(JAXWSProperties.CONNECT_TIMEOUT, connectionTimeout);
                ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, 60000);
                in.co.titan.salesorder.DTSalesOrder mtSalesOrder = new in.co.titan.salesorder.DTSalesOrder();

                String flagcat = null;
                if (cliniPojo != null && specPojo != null && clensPojo != null) {
                    flagcat = "C3";
                } else if (cliniPojo != null && specPojo != null) {
                    flagcat = "CP";
                } else if (cliniPojo != null && clensPojo != null) {
                    flagcat = "CC";
                } else if (specPojo != null && clensPojo != null) {
                    flagcat = "PC";
                } else if (cliniPojo != null) {
                    flagcat = "CH";
                } else if (specPojo != null) {
                    flagcat = "PR";
                } else if (clensPojo != null) {
                    flagcat = "CL";
                } else {
                    flagcat = " ";
                }

                if (flagcat != null) {
                    mtSalesOrder.setFlagCat(flagcat);//1
                }
                if (updateType != null) {
                    mtSalesOrder.setFlagMode(updateType);//2
                }

                mtSalesOrder.setCompanyCode(flagcat);

                //      if (plantobj != null) {
//                    mtSalesOrder.setDistributionChannel(plantobj.getDistrChannel());//3
//                    mtSalesOrder.setDivision(plantobj.getDivision());//4
//                    mtSalesOrder.setSalesOrganization(plantobj.getSalesOrg());//5
//                    mtSalesOrder.setSalesDocType("TEST");//6
                //mtSalesOrder.setSaleType(inquiryno)
                //     }
                mtSalesOrder.setParameterID("BKSAL");                  //   PARAMETER ID (mandatory)
                // mtSalesOrder.setDivision(salesorderheaderpojoobj.getDivision());
                // System.out.println("DIVISION:"+salesorderheaderpojoobj.getDivision());

                if (Validations.isFieldNotEmpty(companyCode1)) {
                    mtSalesOrder.setCompanyCode(companyCode1);
                } //  COMPANY CODE (mandatory)
                // start : code added by ravi thota on 21.11.2011 for rectify payload with company code null : added else part
                else {

                    String CompanyCode = new SiteMasterDO().getSiteCompanyCode(con);
                    if (Validations.isFieldNotEmpty(CompanyCode)) {
                        mtSalesOrder.setCompanyCode(CompanyCode);
                    }

                }
                // END: code added by ravi thota on 21.11.2011 for rectify payload with company code null
                if (salesorderheaderpojoobj != null) {

                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getStoreCode())) {
                        mtSalesOrder.setStoreCode(salesorderheaderpojoobj.getStoreCode());
                        mtSalesOrder.setSITESEARCH(salesorderheaderpojoobj.getStoreCode());
                    }  // STORE CODE  (mandatory)

                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getFiscalYear())) {
                        mtSalesOrder.setFYear(String.valueOf(salesorderheaderpojoobj.getFiscalYear()));
                    }       // FISCAL YEAR  (mandatory)

                    mtSalesOrder.setSaleOrderNO(salesorderheaderpojoobj.getSaleorderno());  // SALE ORDERNO (mandatory)
                    try {
                        if (salesorderheaderpojoobj.getSaleorderdate() != 0) {
                            java.util.Date date1 = ConvertDate.getUtilDateFromNumericDate(salesorderheaderpojoobj.getSaleorderdate());
                            if (date1 != null) {
                                XMLCalendar xmlDate = new XMLCalendar(date1);
                                if (xmlDate != null) {
                                    mtSalesOrder.setSaleOderDate(xmlDate);
                                }

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("setSaleOderDate Not Set" + e);
                    }

                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getCustomercode())) {
                        mtSalesOrder.setCustomerNO(salesorderheaderpojoobj.getCustomercode());
                    }
                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getReferencePromoOrder())) {
                        mtSalesOrder.setReferencePromoOrder(salesorderheaderpojoobj.getReferencePromoOrder());
                    }
                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getDatasheetno())) {
                        mtSalesOrder.setDataSheetNO(salesorderheaderpojoobj.getDatasheetno());
                    }
//                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getTint_Code())) {//Added by Balachander V on 18.9.2019 to set tintcode
//                        mtSalesOrder.setTintCode(salesorderheaderpojoobj.getTint_Code());
//                    }

                    try {
                        System.out.println("setSaleOderDate Not Set" + salesorderheaderpojoobj.getPrintordertype());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getPrintordertype())) {
                        mtSalesOrder.setOrderType(salesorderheaderpojoobj.getPrintordertype());
                    }//23 'Spectacle Rx' or 'Cotact lens' or 'Others;

                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getFittinglabInstruction())) {
                        mtSalesOrder.setForFittingLab(salesorderheaderpojoobj.getFittinglabInstruction());
                    }

                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getLensvendorInstruction())) {
                        mtSalesOrder.setForVendor(salesorderheaderpojoobj.getLensvendorInstruction());
                    }

                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getOrderStatus())) {
                        mtSalesOrder.setPOSOrderStatus(salesorderheaderpojoobj.getOrderStatus());
                    }
                    //

                    try {

                        java.util.Date date1 = ConvertDate.getUtilDateFromNumericDate(salesorderheaderpojoobj.getPlannedDate());
                        if (date1 != null) {
                            XMLCalendar xmlDate = new XMLCalendar(date1);
                            if (xmlDate != null) {
                                mtSalesOrder.setPlanDT(xmlDate);
                            }

                        }/*else
                    {
                    date1 = ConvertDate.getUtilDateFromNumericDate(ConvertDate.getDateNumeric(MainForm1.todaysDateFromISR,"dd/MM/yyyy"));
                    if (date1 != null) {
                    XMLCalendar xmlDate = new XMLCalendar(date1);
                    if (xmlDate != null) {
                    mtSalesOrder.setPlanDT(xmlDate);
                    }
                    }
                    }*/
                        //  }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Exception" + e);
                    }
                    try {
                        if (salesorderheaderpojoobj.getProposedDate() != 0) {
                            java.util.Date date1 = ConvertDate.getUtilDateFromNumericDate(salesorderheaderpojoobj.getProposedDate());
                            if (date1 != null) {
                                XMLCalendar xmlDate = new XMLCalendar(date1);
                                if (xmlDate != null) {
                                    mtSalesOrder.setProposeDT(xmlDate);
                                }

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("setProposeDT Not Set" + e);
                    }
                    try {
                        if (salesorderheaderpojoobj.getTargetcompletedate() != 0) {
                            java.util.Date date1 = ConvertDate.getUtilDateFromNumericDate(salesorderheaderpojoobj.getTargetcompletedate());
                            if (date1 != null) {
                                XMLCalendar xmlDate = new XMLCalendar(date1);
                                if (xmlDate != null) {
                                    mtSalesOrder.setTargetComplDate(xmlDate);
                                }

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("setTargetComplDate Not Set" + e);
                    }

                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getVerifiedby())) {
                        mtSalesOrder.setVerifiedBY(salesorderheaderpojoobj.getVerifiedby());
                        mtSalesOrder.setVerified("YES");
                    } else {
                        mtSalesOrder.setVerified("NO");
                    }

                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getOldSaleorderNo())) {
                        mtSalesOrder.setOldSaleOrderNO(salesorderheaderpojoobj.getOldSaleorderNo());
                    }//22 mtSalesOrder.setOldSaleOrderNO(salesorderheaderpojoobj.getOldasleordeno);

                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getReasonforReturn())) {
                        mtSalesOrder.setReasonForReturn(salesorderheaderpojoobj.getReasonforReturn());
                    }

                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getSaletype())) {
                        mtSalesOrder.setTypeOfSale(salesorderheaderpojoobj.getSaletype());
                        System.err.println("**********************" + salesorderheaderpojoobj.getSaletype());
                    }
                    //   mtSalesOrder.setCreditSaleRef(salesorderheaderpojoobj.getc); / SHOULD COME FROM ADVANCE RECIEPT

                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getReleaseStatus())) {
                        mtSalesOrder.setReleaseStatus(salesorderheaderpojoobj.getReleaseStatus());
                    }
                    //Added by dileep - 17.04.2014
                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getReferralVoucherNO())) {
                        mtSalesOrder.setRefVoucher(salesorderheaderpojoobj.getReferralVoucherNO());
                    }

                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getReferralRedeemNO())) {
                        mtSalesOrder.setVouRedmNo(salesorderheaderpojoobj.getReferralRedeemNO());
                    }//End: Added by dileep - 17.04.2014

                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getCreatedBy())) {
                        mtSalesOrder.setCreatedName(salesorderheaderpojoobj.getCreatedBy());
                    }

                    try {
                        java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(salesorderheaderpojoobj.getCreatedDate());
                        Calendar createdTime = ConvertDate.getSqlTimeFromString(salesorderheaderpojoobj.getCreatedTime());
                        if (createdDate != null) {
                            XMLCalendar xmlDate = new XMLCalendar(createdDate);
                            if (createdTime != null) {
                                xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                                if (xmlDate != null) {
                                    mtSalesOrder.setCreatedTime(xmlDate);
                                }
                            }
                            if (xmlDate != null) {
                                mtSalesOrder.setCreatedDate(xmlDate);
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (updateType.equalsIgnoreCase("U")) {
                        if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getModifiedBy())) {
                            mtSalesOrder.setModifiedBy(salesorderheaderpojoobj.getModifiedBy());
                        }

                        try {
                            java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(salesorderheaderpojoobj.getModifiedDate());
                            Calendar createdTime = ConvertDate.getSqlTimeFromString(salesorderheaderpojoobj.getModifiedTime());
                            if (createdDate != null) {
                                XMLCalendar xmlDate = new XMLCalendar(createdDate);
                                if (createdTime != null) {
                                    xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                                    if (xmlDate != null) {
                                        mtSalesOrder.setModifiedTime(xmlDate);
                                    }
                                }
                                if (xmlDate != null) {
                                    mtSalesOrder.setModifiedDate(xmlDate);
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getProcreq())) {
                        mtSalesOrder.setProcReq(salesorderheaderpojoobj.getProcreq());
                    }
                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getProcreq_left())) {
                        mtSalesOrder.setProcReqLeft(salesorderheaderpojoobj.getProcreq_left());
                    }
                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getInsforuser())) {
                        mtSalesOrder.setInstructionForUser(salesorderheaderpojoobj.getInsforuser());
                    }
                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getExternalInvoiceNo())) {//Added by Balachander V on 18.2.2020 to set External Invoice No
                        mtSalesOrder.setExternalInvoiceNo(salesorderheaderpojoobj.getExternalInvoiceNo());
                    }
                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getDeliveryMode())) {//Added by Balachander V on 4.12.2020 to set Delivery mode
                        mtSalesOrder.setDeliveryMode(salesorderheaderpojoobj.getDeliveryMode());
                    }
                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getRecon_reason())) {//Added by Balachander V on 10.2.2022 to send reason for recomendation to SAP
                        mtSalesOrder.setReasonRecom(salesorderheaderpojoobj.getRecon_reason());
                    }
                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getSampleTintType())) {//Added by Balachander V 
                        mtSalesOrder.setSampleTintType(salesorderheaderpojoobj.getSampleTintType());
                    }
                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getSampleTintRemarks())) {//Added by Balachander V 
                        mtSalesOrder.setSampleTintRemarks(salesorderheaderpojoobj.getSampleTintRemarks());
                    }
                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getWhStock())) {//Added by charan for Warehouse stock 
                        mtSalesOrder.setWHStock(salesorderheaderpojoobj.getWhStock());
                    }
                    System.err.println("CustName:" + salesorderheaderpojoobj.getcustname());
                    System.err.println("MobileNo:" + salesorderheaderpojoobj.getMobileno());
                    System.err.println("GstinNo:" + salesorderheaderpojoobj.getGst_no());
                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getcustname())) {//Added by sirekha k for saleorder service
                        mtSalesOrder.setCustomerName(salesorderheaderpojoobj.getcustname());
                    }
                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getMobileno())) {
                        mtSalesOrder.setMobileNo(salesorderheaderpojoobj.getMobileno());
                    }
                    if (Validations.isFieldNotEmpty(salesorderheaderpojoobj.getGst_no())) {//end of code added by
                        mtSalesOrder.setGstinno(salesorderheaderpojoobj.getGst_no());
                    }
                    try {
                        java.util.Date approvdate = ConvertDate.getUtilDateFromNumericDate(salesorderheaderpojoobj.getApproveddate());

                        if (approvdate != null) {
                            XMLCalendar xmlDate = new XMLCalendar(approvdate);
                            if (xmlDate != null) {
                                mtSalesOrder.setApprovedDate(xmlDate);
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        java.util.Date releaseDate = ConvertDate.getUtilDateFromNumericDate(salesorderheaderpojoobj.getReleasedate());

                        if (releaseDate != null) {
                            XMLCalendar xmlDate = new XMLCalendar(releaseDate);
                            if (xmlDate != null) {
                                mtSalesOrder.setReleaseDate(xmlDate);
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                //FLAGCAT
                //CH:ClinicalHistory
                //PR:Spectacle Prescription
                //CL:Contact Lens
                //CP:ClinicalHistory and Prescription
                //CC:ClinicalHistory and Contact Lens
                //PC:Prescription and ContactLens
                //C3:ClinicalHistory, Prescription and Contact Lens
                //FLAGMODE
                //I:Creation
                //U:Updation

                if (cliniPojo != null && (flagcat.trim().equals("C3") || flagcat.trim().equals("CH") || flagcat.trim().equals("CP") || flagcat.trim().equals("CC"))) {

                    /**
                     * ****** Setting Clinical History values ************
                     */
                    if (Validations.isFieldNotEmpty(cliniPojo.getExaminedby())) {
                        mtSalesOrder.setCHExaminedBY(cliniPojo.getExaminedby());
                    }

                    if (Validations.isFieldNotEmpty(cliniPojo.getPasthistory())) {
                        mtSalesOrder.setCHPastHistory(cliniPojo.getPasthistory());
                    }

                    if (Validations.isFieldNotEmpty(cliniPojo.getComments())) {
                        mtSalesOrder.setCHComments(cliniPojo.getComments());
                    }

                    if (Validations.isFieldNotEmpty(cliniPojo.getSlitlampexamination())) {
                        mtSalesOrder.setCHSLExamination(cliniPojo.getSlitlampexamination());
                    }

                    ArrayList<in.co.titan.salesorder.DTSalesOrder.CHRightEYE> ch_rightEyes = new ArrayList<in.co.titan.salesorder.DTSalesOrder.CHRightEYE>();
                    ArrayList<in.co.titan.salesorder.DTSalesOrder.CHLeftEYE> ch_leftEyes = new ArrayList<in.co.titan.salesorder.DTSalesOrder.CHLeftEYE>();
                    /**
                     * **********CH RIGHT************
                     */
                    Map CH_RightEyes = Inquiry_POS.getAndsetCHCharacteristics(cliniPojo, "R");
                    if (CH_RightEyes != null) {
                        if (CH_RightEyes.keySet() != null) {
                            in.co.titan.salesorder.DTSalesOrder.CHRightEYE righteye;
                            String desc = null, val = null;
                            Iterator chRight = CH_RightEyes.keySet().iterator();
                            if (chRight != null) {
                                while (chRight.hasNext()) {
                                    try {
                                        desc = String.valueOf(chRight.next());
                                        val = String.valueOf(CH_RightEyes.get(desc));
                                        if (Validations.isFieldNotEmpty(val)) {

                                            righteye = new in.co.titan.salesorder.DTSalesOrder.CHRightEYE();
                                            righteye.setDescription(desc);
                                            righteye.setValue(val);
                                            ch_rightEyes.add(righteye);

                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }

                    /**
                     * **********CH LEFT************
                     */
                    Map CH_LeftEyes = Inquiry_POS.getAndsetCHCharacteristics(cliniPojo, "L");
                    if (CH_LeftEyes != null) {
                        if (CH_LeftEyes.keySet() != null) {
                            in.co.titan.salesorder.DTSalesOrder.CHLeftEYE lefteye = null;
                            String desc = null, val = null;
                            Iterator chLeft = CH_LeftEyes.keySet().iterator();
                            if (chLeft != null) {
                                while (chLeft.hasNext()) {
                                    try {
                                        desc = String.valueOf(chLeft.next());
                                        val = String.valueOf(CH_LeftEyes.get(desc));
                                        if (Validations.isFieldNotEmpty(val)) {

                                            lefteye = new in.co.titan.salesorder.DTSalesOrder.CHLeftEYE();
                                            lefteye.setDescription(desc);
                                            lefteye.setValue(val);
                                            ch_leftEyes.add(lefteye);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }

                    mtSalesOrder.getCHLeftEYE().addAll(ch_leftEyes);
                    mtSalesOrder.getCHRightEYE().addAll(ch_rightEyes);
                }
                if (specPojo != null && (flagcat.trim().equals("C3") || flagcat.trim().equals("PR") || flagcat.trim().equals("CP") || flagcat.trim().equals("PC"))) {

                    /**
                     * ****** Setting Prescription values ************
                     */
                    if (Validations.isFieldNotEmpty(specPojo.getExaminedby())) {
                        mtSalesOrder.setPRExaminedBY(specPojo.getExaminedby());
                    }

                    if (Validations.isFieldNotEmpty(specPojo.getDoctor_name())) {
                        mtSalesOrder.setPRDocName(specPojo.getDoctor_name());
                    }

                    if (Validations.isFieldNotEmpty(specPojo.getDoctor_contactno())) {
                        mtSalesOrder.setPRDocCNO(specPojo.getDoctor_contactno());
                    }

                    if (Validations.isFieldNotEmpty(specPojo.getDoctor_area())) {
                        mtSalesOrder.setPRDocArea(specPojo.getDoctor_area());
                    }

                    if (Validations.isFieldNotEmpty(specPojo.getDoctor_city())) {
                        mtSalesOrder.setPRDocCity(specPojo.getDoctor_city());
                    }

                    if (Validations.isFieldNotEmpty(specPojo.getOthername())) {
                        mtSalesOrder.setPRExamBYOthers(specPojo.getOthername());
                    }

                    if (Validations.isFieldNotEmpty(specPojo.getFollowup())) {
                        mtSalesOrder.setPRFollowUP(specPojo.getFollowup());
                    }

                    if (Validations.isFieldNotEmpty(specPojo.getPres_verify())) {//Added by Balachander V on 30.01.2021 to Send Prescrition Verified details to SAP
                        mtSalesOrder.setPRVerified(specPojo.getPres_verify());
                    }

                    if (Validations.isFieldNotEmpty(specPojo.getPres_deviation())) {
                        mtSalesOrder.setPRChange(specPojo.getPres_deviation());
                    }
                    try {
                        if (specPojo.getFollowupdate() != 0) {
                            java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(specPojo.getFollowupdate());
                            if (followDate != null) {
                                XMLCalendar xmlDate = new XMLCalendar(followDate);
                                if (xmlDate != null) {
                                    mtSalesOrder.setPRFollowDT(xmlDate);
                                }

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Spec Rx FollowupdateNot Set" + e);
                    }

                    if (Validations.isFieldNotEmpty(specPojo.getReferred_to())) {
                        mtSalesOrder.setPRRefered(specPojo.getReferred_to());
                    }

                    if (Validations.isFieldNotEmpty(specPojo.getComments())) {
                        mtSalesOrder.setPRComments(specPojo.getComments());
                    }

                    if (Validations.isFieldNotEmpty(specPojo.getRight_Sale())) {
                        mtSalesOrder.setPRPresSale(specPojo.getRight_Sale());
                    } else if (Validations.isFieldNotEmpty(specPojo.getLeft_Sale())) {
                        mtSalesOrder.setPRPresSale(specPojo.getLeft_Sale());
                    }
                    if (Validations.isFieldNotEmpty(specPojo.getRight_Sale())) {
                        mtSalesOrder.setPRPresSale(specPojo.getRight_Sale());
                        if (specPojo.getRight_Sale().trim().equalsIgnoreCase("BP")) {
                            if (Validations.isFieldNotEmpty(specPojo.getRight_B_P_Dist()) && Validations.isFieldNotEmpty(specPojo.getRight_B_P_Inter())) {
                                mtSalesOrder.setPRPresBP("DI");
                            }//21
                            else if (Validations.isFieldNotEmpty(specPojo.getRight_B_P_Dist()) && Validations.isFieldNotEmpty(specPojo.getRight_B_P_Near())) {
                                mtSalesOrder.setPRPresBP("DN");
                            }//21
                            else if (Validations.isFieldNotEmpty(specPojo.getRight_B_P_Inter()) && Validations.isFieldNotEmpty(specPojo.getRight_B_P_Near())) {
                                mtSalesOrder.setPRPresBP("IN");
                            }
                        }
                    } else if (Validations.isFieldNotEmpty(specPojo.getLeft_Sale())) {
                        mtSalesOrder.setPRPresSale(specPojo.getLeft_Sale());
                        if (specPojo.getLeft_Sale().trim().equalsIgnoreCase("BP")) {
                            if (Validations.isFieldNotEmpty(specPojo.getLeft_B_P_Dist()) && Validations.isFieldNotEmpty(specPojo.getLeft_B_P_Inter())) {
                                mtSalesOrder.setPRPresBP("DI");
                            }//21
                            else if (Validations.isFieldNotEmpty(specPojo.getLeft_B_P_Dist()) && Validations.isFieldNotEmpty(specPojo.getLeft_B_P_Near())) {
                                mtSalesOrder.setPRPresBP("DN");
                            }//21
                            else if (Validations.isFieldNotEmpty(specPojo.getLeft_B_P_Inter()) && Validations.isFieldNotEmpty(specPojo.getLeft_B_P_Near())) {
                                mtSalesOrder.setPRPresBP("IN");
                            }
                        }
                    }
                    ArrayList<in.co.titan.salesorder.DTSalesOrder.PRRightEYE> pr_rightEyes = new ArrayList<in.co.titan.salesorder.DTSalesOrder.PRRightEYE>();
                    ArrayList<in.co.titan.salesorder.DTSalesOrder.PRLeftEYE> pr_leftEyes = new ArrayList<in.co.titan.salesorder.DTSalesOrder.PRLeftEYE>();

                    /**
                     * **********PR RIGHT************
                     */
                    Map PR_RighttEyes = Inquiry_POS.getAndsetPRCharacteristics(specPojo, salesorderheaderpojoobj, "R");
                    if (PR_RighttEyes != null) {
                        if (PR_RighttEyes.keySet() != null) {
                            in.co.titan.salesorder.DTSalesOrder.PRRightEYE righteye = null;
                            String desc = null, val = null;
                            Iterator prRight = PR_RighttEyes.keySet().iterator();
                            if (prRight != null) {
                                while (prRight.hasNext()) {
                                    try {
                                        desc = String.valueOf(prRight.next());
                                        val = String.valueOf(PR_RighttEyes.get(desc));
                                        //     System.out.println("###########################################val="+val);
                                        if (Validations.isFieldNotEmpty(val)) {

                                            righteye = new in.co.titan.salesorder.DTSalesOrder.PRRightEYE();
                                            righteye.setDescription(desc);
                                            righteye.setValue(val);
                                            pr_rightEyes.add(righteye);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }

                    /**
                     * **********PR LEFT************
                     */
                    Map PR_LeftEyes = Inquiry_POS.getAndsetPRCharacteristics(specPojo, salesorderheaderpojoobj, "L");
                    if (PR_LeftEyes != null) {
                        if (PR_LeftEyes.keySet() != null) {
                            in.co.titan.salesorder.DTSalesOrder.PRLeftEYE lefteye = null;
                            String desc = null, val = null;
                            Iterator prLeft = PR_LeftEyes.keySet().iterator();
                            if (prLeft != null) {
                                while (prLeft.hasNext()) {
                                    try {
                                        desc = String.valueOf(prLeft.next());
                                        val = String.valueOf(PR_LeftEyes.get(desc));
                                        if (Validations.isFieldNotEmpty(val)) {

                                            lefteye = new in.co.titan.salesorder.DTSalesOrder.PRLeftEYE();
                                            lefteye.setDescription(desc);
                                            lefteye.setValue(val);
                                            pr_leftEyes.add(lefteye);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }

                    mtSalesOrder.getPRLeftEYE().addAll(pr_leftEyes);
                    mtSalesOrder.getPRRightEYE().addAll(pr_rightEyes);
                }
                if (clensPojo != null && (flagcat.trim().equals("C3") || flagcat.trim().equals("CL") || flagcat.trim().equals("CC") || flagcat.trim().equals("PC"))) {

                    /**
                     * ****** Setting Contact Lens values ************
                     */
                    if (Validations.isFieldNotEmpty(clensPojo.getExaminedby())) {
                        mtSalesOrder.setCLExaminedBY(clensPojo.getExaminedby());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getDoctor_name())) {
                        mtSalesOrder.setCLDocName(clensPojo.getDoctor_name());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getDoctor_contactno())) {
                        mtSalesOrder.setCLDocCNO(clensPojo.getDoctor_contactno());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getDoctor_area())) {
                        mtSalesOrder.setCLDocArea(clensPojo.getDoctor_area());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getDoctor_city())) {
                        mtSalesOrder.setCLDocCity(clensPojo.getDoctor_city());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getOthername())) {
                        mtSalesOrder.setCLExamOthers(clensPojo.getOthername());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getCl_fp_specialcomments())) {
                        mtSalesOrder.setCLComments(clensPojo.getCl_fp_specialcomments());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getFollowup())) {
                        mtSalesOrder.setCLFollowUP(clensPojo.getFollowup());
                    }
                    if (Validations.isFieldNotEmpty(clensPojo.getPres_verify())) {
                        mtSalesOrder.setPRVerified(clensPojo.getPres_verify());
                    }
                    if (Validations.isFieldNotEmpty(clensPojo.getPres_deviation())) {
                        mtSalesOrder.setPRChange(clensPojo.getPres_deviation());
                    }
                    try {
                        if (clensPojo.getFollowupdate() != 0) {
                            java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(clensPojo.getFollowupdate());
                            if (followDate != null) {
                                XMLCalendar xmlDate = new XMLCalendar(followDate);
                                if (xmlDate != null) {
                                    mtSalesOrder.setCLFollowDT(xmlDate);
                                }

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("C Lens FollowupdateNot Set" + e);
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getOor_slitlamp_examination())) {
                        mtSalesOrder.setCLSLExamination(clensPojo.getOor_slitlamp_examination());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getRight_trails())) {
                        mtSalesOrder.setCLRightTrial(clensPojo.getRight_trails());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getLeft_trails())) {
                        mtSalesOrder.setCLLeftTrial(clensPojo.getLeft_trails());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getCaresys_aquasoft_mps())) {
                        mtSalesOrder.setCLAquaSoftMPS(clensPojo.getCaresys_aquasoft_mps());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getCaresys_complete_mps())) {
                        mtSalesOrder.setCLCompleteMPS(clensPojo.getCaresys_complete_mps());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getCaresys_gp_solution())) {
                        mtSalesOrder.setCLGPSolutions(clensPojo.getCaresys_gp_solution());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getCaresys_lubricant_mps())) {
                        mtSalesOrder.setCLLubricant(clensPojo.getCaresys_lubricant_mps());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getCaresys_moisture_mps())) {
                        mtSalesOrder.setCLMoistureLOC(clensPojo.getCaresys_moisture_mps());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getCaresys_renu_mps())) {
                        mtSalesOrder.setCLReNuMPS(clensPojo.getCaresys_renu_mps());
                    }

                    if (Validations.isFieldNotEmpty((clensPojo.getCaresys_rewetting_mps()))) {
                        mtSalesOrder.setCLRewetting(clensPojo.getCaresys_rewetting_mps());
                    }

                    if (Validations.isFieldNotEmpty((clensPojo.getCaresys_silkens_mps()))) {
                        mtSalesOrder.setCLSilkensMPS(clensPojo.getCaresys_silkens_mps());
                    }

                    if (Validations.isFieldNotEmpty((clensPojo.getCaresys_solo_mps()))) {
                        mtSalesOrder.setCLSoloMPS(clensPojo.getCaresys_solo_mps());
                    }

                    ArrayList<in.co.titan.salesorder.DTSalesOrder.CLRightEYE> cl_rightEyes = new ArrayList<in.co.titan.salesorder.DTSalesOrder.CLRightEYE>();
                    ArrayList<in.co.titan.salesorder.DTSalesOrder.CLLeftEYE> cl_leftEyes = new ArrayList<in.co.titan.salesorder.DTSalesOrder.CLLeftEYE>();

                    /**
                     * *******CL RIGHT ****************
                     */
                    Map CL_RightEyes = Inquiry_POS.getAndsetCLCharacteristics(clensPojo, "R");
                    if (CL_RightEyes != null) {
                        if (CL_RightEyes.keySet() != null) {
                            in.co.titan.salesorder.DTSalesOrder.CLRightEYE righteye = null;
                            String desc = null, val = null;
                            Iterator clRight = CL_RightEyes.keySet().iterator();
                            if (clRight != null) {
                                while (clRight.hasNext()) {
                                    try {
                                        desc = String.valueOf(clRight.next());
                                        val = String.valueOf(CL_RightEyes.get(desc));
                                        if (Validations.isFieldNotEmpty(val)) {

                                            righteye = new in.co.titan.salesorder.DTSalesOrder.CLRightEYE();
                                            righteye.setDescription(desc);
                                            righteye.setValue(val);
                                            cl_rightEyes.add(righteye);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }

                    /**
                     * **********CL lEFT************
                     */
                    Map CL_LeftEyes = Inquiry_POS.getAndsetCLCharacteristics(clensPojo, "L");
                    if (CL_LeftEyes != null) {
                        if (CL_LeftEyes.keySet() != null) {
                            in.co.titan.salesorder.DTSalesOrder.CLLeftEYE lefteye = null;
                            String desc = null, val = null;
                            Iterator clLeft = CL_LeftEyes.keySet().iterator();
                            if (clLeft != null) {
                                while (clLeft.hasNext()) {
                                    try {
                                        desc = String.valueOf(clLeft.next());
                                        val = String.valueOf(CL_LeftEyes.get(desc));
                                        if (Validations.isFieldNotEmpty(val)) {
                                            lefteye = new in.co.titan.salesorder.DTSalesOrder.CLLeftEYE();
                                            lefteye.setDescription(desc);
                                            lefteye.setValue(val);
                                            cl_leftEyes.add(lefteye);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }

                    mtSalesOrder.getCLLeftEYE().addAll(cl_leftEyes);
                    mtSalesOrder.getCLRightEYE().addAll(cl_rightEyes);
                }

                /**
                 * ***********SALE ORDER LINE ITEMS ********************
                 */
                ArrayList<in.co.titan.salesorder.DTSalesOrder.SOCondition> soConditions = new ArrayList<in.co.titan.salesorder.DTSalesOrder.SOCondition>();
                ArrayList<in.co.titan.salesorder.DTSalesOrder.SOItem> soItems = new ArrayList<in.co.titan.salesorder.DTSalesOrder.SOItem>();
                if (salesorderdetailspojoobjlist != null) {
                    Iterator so_DetailsList = salesorderdetailspojoobjlist.iterator();
                    if (so_DetailsList != null) {
                        in.co.titan.salesorder.DTSalesOrder.SOItem soItem = null;
                        SOLineItemPOJO detailsPojo = null;
                        String priority = null, regular = null;
                        in.co.titan.salesorder.DTSalesOrder.SOCondition sOCondition;
                        int i = 0;
                        DecimalFormat df = new DecimalFormat("#0.00");
                        while (so_DetailsList.hasNext()) {
                            int j = 0;
                            detailsPojo = (SOLineItemPOJO) so_DetailsList.next();
                            if (detailsPojo != null) {
                                soItem = new in.co.titan.salesorder.DTSalesOrder.SOItem();
                                BigDecimal amt = new BigDecimal(detailsPojo.getNetamount());
                                soItem.setPOSLineItem(new BigInteger(Integer.toString(++i)));

                                if (Validations.isFieldNotEmpty(detailsPojo.getMaterial())) {
                                    soItem.setItemCode(detailsPojo.getMaterial().toUpperCase());
                                    //String division = new ArticleDO().getDivisionByMaterial(con, detailsPojo.getMaterial()); // code Commented by ravi thota on 21.11.2011 
                                    String division;
                                    division = detailsPojo.getDivision();

                                    if (Validations.isFieldNotEmpty(division)) {
                                        mtSalesOrder.setDivision(division);
                                        System.out.println("if   Division*******" + division);// code added by ravi thota on 21.11.2011 for rectify payload with division null
                                    } else {
                                        division = new ArticleDO().getDivisionByMaterial(con, detailsPojo.getMaterial()); // code Commented by ravi thota on 21.11.2011 
                                        mtSalesOrder.setDivision(division);
                                        System.out.println("else   Division*******" + division);// code added by ravi thota on 21.11.2011 for rectify payload with division null
                                    }
                                }

                                if (Validations.isFieldNotEmpty(detailsPojo.getBatchId()) && Validations.isFieldNotEmpty(detailsPojo.getFrombatch()) && mtSalesOrder.getOrderType().equalsIgnoreCase("CL") && ServerConsole.contactlensdualpricingenabled.equalsIgnoreCase("Y")) {//Added by charan for contact lens dual pricing
                                    soItem.setBatch(detailsPojo.getBatchId());
                                    soItem.setFromBatch(detailsPojo.getFrombatch());
                                } else {
                                    if (Validations.isFieldNotEmpty(detailsPojo.getBatchId())) {
                                        soItem.setBatch(detailsPojo.getBatchId());
                                    }
                                }
                                if (salesorderheaderpojoobj.getPriority() == 1) {
                                    soItem.setPriority("X");
                                }
                                if (Validations.isFieldNotEmpty((detailsPojo.getQuantity()))) {
                                    soItem.setQuant(new BigInteger(Integer.toString(detailsPojo.getQuantity())));
                                }

                                if (Validations.isFieldNotEmpty(detailsPojo.getDiscountRefNo())) {
                                    soItem.setEmployeeNoDiscount(detailsPojo.getDiscountRefNo());
                                }

                                if (Validations.isFieldNotEmpty(detailsPojo.getTaxableValue())) {
                                    soItem.setTaxableValue(new BigDecimal(df.format(detailsPojo.getTaxableValue())));
                                }

                                if (Validations.isFieldNotEmpty(detailsPojo.getFoc())) {
                                    soItem.setFOC(detailsPojo.getFoc());
                                }

                                if (Validations.isFieldNotEmpty(detailsPojo.getEyeSide())) {
                                    soItem.setEye(detailsPojo.getEyeSide());
                                }

                                if (Validations.isFieldNotEmpty(detailsPojo.getTypeOfLens())) {
                                    soItem.setTypeOfLens(detailsPojo.getTypeOfLens());
                                }

                                if (Validations.isFieldNotEmpty(detailsPojo.getStyleConsultant())) {
                                    soItem.setStyleConsultantLineItem(detailsPojo.getStyleConsultant());
                                }

                                if (Validations.isFieldNotEmpty(detailsPojo.getCustomerItem())) {
                                    soItem.setCutomerItem(detailsPojo.getCustomerItem());
                                }

                                if (Validations.isFieldNotEmpty(detailsPojo.getModelNo())) {
                                    soItem.setModelNO(detailsPojo.getModelNo());
                                }

                                if (Validations.isFieldNotEmpty(detailsPojo.getBrandColor())) {
                                    soItem.setBrand(detailsPojo.getBrandColor());
                                }

                                if (Validations.isFieldNotEmpty(detailsPojo.getApproxValue())) {
                                    soItem.setAppValue(detailsPojo.getApproxValue());
                                }

                                if (Validations.isFieldNotEmpty(detailsPojo.getAnyVisibleDefect())) {
                                    soItem.setAnyVisibleDefect(detailsPojo.getAnyVisibleDefect());
                                }

                                if (Validations.isFieldNotEmpty(detailsPojo.getComments())) {
                                    soItem.setComments(detailsPojo.getComments());
                                }

                                if (detailsPojo.getPromotionSelected() != null) {
                                    if (Validations.isFieldNotEmpty(detailsPojo.getPromotionSelected().getPromotionID())) {
                                        soItem.setPromotionID(detailsPojo.getPromotionSelected().getPromotionID());
                                    }
                                }

                                if (detailsPojo.getUCP() != null && detailsPojo.getUCP().getNetAmount() != 0) {
                                    if (detailsPojo.getUCP().getDummyconditiontype() != null) {
                                        sOCondition = setSoConDition(detailsPojo.getUCP(), i, ++j, "U");
                                        if (sOCondition != null) {
                                            if (sOCondition.getPOSCondType() != null) {
                                                soConditions.add(sOCondition);
                                            }
                                        }
                                    }
                                } else {
                                    ucpcheck = false;
                                }
                                if (detailsPojo.getDiscountSelected() != null) {
                                    if (detailsPojo.getDiscountSelected().getDummyconditiontype() != null) {
                                        sOCondition = setSoConDition(detailsPojo.getDiscountSelected(), i, ++j, "D");
                                        if (sOCondition != null) {
                                            if (sOCondition.getPOSCondType() != null) {
                                                soConditions.add(sOCondition);
                                            }
                                        }
                                    }
                                }
                                if (detailsPojo.getOfferPromotion() != null) {
                                    if (detailsPojo.getOfferPromotion().getDummyconditiontype() != null) {
                                        sOCondition = setSoConDition(detailsPojo.getOfferPromotion(), i, ++j, "P");
                                        if (sOCondition != null) {
                                            if (sOCondition.getPOSCondType() != null) {
                                                soConditions.add(sOCondition);
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

                                                sOCondition = setSoConDition(conditionTypePOJO, i, ++j, "T");
                                                if (sOCondition != null) {
                                                    if (sOCondition.getPOSCondType() != null) {
                                                        soConditions.add(sOCondition);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (detailsPojo.isOtherChargesPresent()) {
                                    Iterator iterator = detailsPojo.getOtherCharges().iterator();
                                    while (iterator.hasNext()) {
                                        ConditionTypePOJO conditionTypePOJO = (ConditionTypePOJO) iterator.next();
                                        if (conditionTypePOJO != null) {
                                            if (conditionTypePOJO.getDummyconditiontype() != null) {
                                                sOCondition = setSoConDition(conditionTypePOJO, i, ++j, "O");
                                                if (sOCondition != null) {
                                                    if (sOCondition.getPOSCondType() != null) {
                                                        soConditions.add(sOCondition);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (Validations.isFieldNotEmpty(detailsPojo.getLineItemEmpid())) {//Added by Balachander V on 1.1.2020
                                    soItem.setEmpID(detailsPojo.getLineItemEmpid());
                                }
                                if (Validations.isFieldNotEmpty(detailsPojo.getFrameColor())) {//Added by Balachander V on 29.2.2020 to send Maui frames details
                                    soItem.setFrameColor(detailsPojo.getFrameColor());
                                }
                                if (Validations.isFieldNotEmpty(detailsPojo.getLensTech())) {
                                    soItem.setLensTech(detailsPojo.getLensTech());
                                }
                                if (Validations.isFieldNotEmpty(detailsPojo.getLensColor())) {
                                    soItem.setLensColor(detailsPojo.getLensColor());
                                }
                                if (Validations.isFieldNotEmpty(detailsPojo.getGradient())) {
                                    soItem.setGradient(detailsPojo.getGradient());
                                }//Ended by Balachander V on 29.2.2020 to send Maui frames details
                                if (Validations.isFieldNotEmpty(detailsPojo.getCancelOtp())) {
                                    //soItem.setc
                                }
                                if (Validations.isFieldNotEmpty(detailsPojo.getAudioUniqueID())) {//added by charan for audiology
                                    soItem.setAudioUniqueID(detailsPojo.getAudioUniqueID());
                                }
                                if (Validations.isFieldNotEmpty(detailsPojo.getAudioRPTA())) {//added by charan for audiology
                                    soItem.setAudioRPTA(detailsPojo.getAudioRPTA());
                                }
                                if (Validations.isFieldNotEmpty(detailsPojo.getAudioLPTA())) {//added by charan for audiology
                                    soItem.setAudioLPTA(detailsPojo.getAudioLPTA());
                                }
                                if (Validations.isFieldNotEmpty(detailsPojo.getAudioProvDiag())) {//added by charan for audiology
                                    soItem.setAudioProvDiag(detailsPojo.getAudioProvDiag());
                                }
                                soItems.add(soItem);
                            }
                        }
                    }
                    mtSalesOrder.getSOItem().addAll(soItems);
                    mtSalesOrder.getSOCondition().addAll(soConditions);
                }

                //Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                System.out.println("Saleorder Storecode  " + mtSalesOrder.getStoreCode() + "  " + Validations.isFieldNotEmpty(mtSalesOrder.getPRPresSale()) + " ucpcheck :: " + ucpcheck);
                boolean presScaleCheck = true;
                if (mtSalesOrder.getOrderType().equalsIgnoreCase("CL")) {
                    presScaleCheck = true;
                } else {
                    if (Validations.isFieldNotEmpty(mtSalesOrder.getPRPresSale())) {
                        presScaleCheck = true;
                    } else if ((!Validations.isFieldNotEmpty(mtSalesOrder.getPRPresSale())) && Validations.isFieldNotEmpty(mtSalesOrder.getOrderType().equalsIgnoreCase("OT"))) {
                        presScaleCheck = true;
                    } else {
                        presScaleCheck = false;
                    }
                }
                System.err.println("************" + presScaleCheck + "---" + mtSalesOrder.getOrderType() + "   " + mtSalesOrder.getOrderType().equalsIgnoreCase("CL"));
                if (mtSalesOrder.getStoreCode() != null && mtSalesOrder.getStoreCode().trim().length() > 0 && !mtSalesOrder.getDivision().equalsIgnoreCase("") && mtSalesOrder.getDivision() != null && presScaleCheck && ucpcheck) {

                    if (mtSalesOrder.getCompanyCode().equalsIgnoreCase("DTIL") || mtSalesOrder.getCompanyCode().equalsIgnoreCase("TIL")) {
                        ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());//XIConnectionDetailsPojo.getUsername()"test_enteg"
                        ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());//"enteg321"
                        ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port() + "/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_SalesOrder&amp;version=3.0&amp;Sender.Service=X&amp;Interface=Z%5EYhttp://pirdev:50000/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_SalesOrder&amp;version=3.0&amp;Sender.Service=X&amp;Interface=Z%5EY&version=3.0&Sender.Service=BS_TITAN_POS&Interface=http%3A%2F%2Ftitan.co.in%2FSalesOrder%5EMI_OB_ASYN_SalesOrder");
                        port.miOBASYNSalesOrder(mtSalesOrder);

                        Map responseContext = ((BindingProvider) port).getResponseContext();
                        Integer responseCode = (Integer) responseContext.get(MessageContext.HTTP_RESPONSE_CODE);
                        System.out.println(mtSalesOrder.getSaleOrderNO() + "+++++" + responseCode);

                        if (responseCode.intValue() == 200) {
                            updatedInISR = "true";
                        } else {
                            updatedInISR = "false";
                        }
                    }

                } else {
                    updatedInISR = "false";
                }
                //End of Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                //Code Commented by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                /*((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());//XIConnectionDetailsPojo.getUsername()"test_enteg"
                ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());//"enteg321"

                port.miOBASYNSalesOrder(mtSalesOrder);

                Map responseContext = ((BindingProvider) port).getResponseContext();
                Integer responseCode = (Integer) responseContext.get(MessageContext.HTTP_RESPONSE_CODE);
                System.out.println(mtSalesOrder.getSaleOrderNO() + "+++++" + responseCode);
                
                if (responseCode.intValue() == 200) {
                    updatedInISR = "true";
                } else {
                    updatedInISR = "false";
                }*/
            } catch (Exception ex) {
                // TODO handle custom exceptions here
                ex.printStackTrace();
            }

        }
        return updatedInISR;
    }

    public in.co.titan.salesorder.DTSalesOrder.SOCondition setSoConDition(ConditionTypePOJO conditionTypePOJO, int refLineItemNo, int condLineItemNo, String typeOfCondition) {
        in.co.titan.salesorder.DTSalesOrder.SOCondition sOCondition = null;
        DecimalFormat df = new DecimalFormat("0.00");
        if (conditionTypePOJO != null) {
            sOCondition = new in.co.titan.salesorder.DTSalesOrder.SOCondition();
            sOCondition.setRefLineItem(new BigInteger(Integer.toString(refLineItemNo)));
            sOCondition.setCondLineItemNO(new BigInteger(Integer.toString(condLineItemNo)));
            //  if(conditionTypePOJO.getPromotionId()!=null)
            //     sOCondition.setPromotionID(conditionTypePOJO.getPromotionId());

            if (Validations.isFieldNotEmpty(conditionTypePOJO.getFreeGoodsCategory())) {
                sOCondition.setFreeGoodsCat(conditionTypePOJO.getFreeGoodsCategory());
            }

            if (Validations.isFieldNotEmpty(conditionTypePOJO.getDummyconditiontype())) {
                sOCondition.setPOSCondType(conditionTypePOJO.getDummyconditiontype());
            }

            if (Validations.isFieldNotEmpty(conditionTypePOJO.getValue())) {
                sOCondition.setAmount(new BigDecimal(df.format(conditionTypePOJO.getValue())));
            }

            if (Validations.isFieldNotEmpty(conditionTypePOJO.getPromotionId())) {
                sOCondition.setPromotionID(conditionTypePOJO.getPromotionId());
            }

            if (Validations.isFieldNotEmpty(conditionTypePOJO.getFreeGoodsCategory())) {
                sOCondition.setFreeGoodsCat(conditionTypePOJO.getFreeGoodsCategory());
            }

            if (Validations.isFieldNotEmpty(conditionTypePOJO.getCalculationtype())) {
                sOCondition.setCalcType(conditionTypePOJO.getCalculationtype());
            }

            if (Validations.isFieldNotEmpty(conditionTypePOJO.getNetAmount())) {
                sOCondition.setNetAmount(new BigDecimal(df.format(conditionTypePOJO.getNetAmount())));
            }

            if (Validations.isFieldNotEmpty(typeOfCondition)) {
                sOCondition.setTypeOfCondition(typeOfCondition);
            }

            if (Validations.isFieldNotEmpty(conditionTypePOJO.getPromotionLineno())) {
                sOCondition.setPromotionLineNO(conditionTypePOJO.getPromotionLineno());
            }

            sOCondition.setPromotionGroupID(String.valueOf(conditionTypePOJO.getPromotionRandomNo()));

        }
        return sOCondition;
    }

    public static HashMap getSOLineItemDetailsByOrderNo(Connection con, String orderNo) {
        Statement stmt = null;
        ResultSet res = null;
        HashMap<String, String> hmap = new HashMap<String, String>();
        try {
            stmt = con.createStatement();
            res = stmt.executeQuery("Select materialcode,frombatch from tbl_salesorderlineitems where saleorderno='" + orderNo + "'");
            while (res.next()) {
                hmap.put(res.getString("materialcode"), res.getString("frombatch"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hmap;
    }
}
