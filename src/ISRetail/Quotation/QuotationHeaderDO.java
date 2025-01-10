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
 * This class file is used as a Data Object between Quotation Form and Database
 * This is used for transaction of Quotation data from and to the database
 * 
 * 
 * 
 */
package ISRetail.Quotation;


import ISRetail.Helpers.ConvertDate;
import ISRetail.Helpers.XMLCalendar;

import ISRetail.Webservices.ConditionTypePOJO;
import ISRetail.Webservices.DataObject;

import ISRetail.Webservices.Webservice;
import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.article.ArticleDO;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.plantdetails.SiteMasterPOJO;

import ISRetail.salesorder.SOLineItemPOJO;
import ISRetail.utility.validations.Validations;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;


public class QuotationHeaderDO implements Webservice{


   /**
   * To Save Quotation Header
   */    

   public int saveQuotationHeader(QuotationHeaderPOJO quotationheaderpojoobj, Connection conn) throws SQLException
   {
   int res = 0;       
   String insertstr;
   PreparedStatement pstmt;
        try {
            insertstr = "insert into tbl_quotationheader values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(insertstr);
                pstmt.setString(1, quotationheaderpojoobj.getStoreCode());
                pstmt.setInt(2, quotationheaderpojoobj.getFiscalYear());
                pstmt.setString(3, quotationheaderpojoobj.getQuotationno());
                pstmt.setInt(4,quotationheaderpojoobj.getQuotationDate());
                pstmt.setString(5,quotationheaderpojoobj.getCustomerCode());
                pstmt.setString(6,quotationheaderpojoobj.getDatasheetno());
                pstmt.setString(7, quotationheaderpojoobj.getStyleConsultant());
                
                
                pstmt.setInt(8,quotationheaderpojoobj.getValidity());
                pstmt.setDouble(9,quotationheaderpojoobj.getAmount());
                pstmt.setString(10,quotationheaderpojoobj.getStatus());
                pstmt.setString(11,quotationheaderpojoobj.getCreatedBy());
                pstmt.setInt(12,quotationheaderpojoobj.getCreatedDate());
                pstmt.setString(13,quotationheaderpojoobj.getCreatedTime());
                pstmt.setString(14,quotationheaderpojoobj.getModifiedBy());
                pstmt.setInt(15,quotationheaderpojoobj.getModifiedDate());
                pstmt.setString(16,quotationheaderpojoobj.getModifiedTime());
                
                 res = pstmt.executeUpdate();
            }
        catch(SQLException ex){
               throw new SQLException();                       
            }finally {
                pstmt = null;
                insertstr = null;
            }
        
        return res;
    }
    
    
   /**
   * To Get Quotation Last Number
   */    
    
   public String selectQuotationLastnoTable(Connection conn) throws SQLException{
        String result = "";
        String quotationnumber = "";
        SiteMasterDO smdo = new SiteMasterDO();
        String siteid = smdo.getSiteNumberLogicValue(conn);
        try {
            Statement pstmt = conn.createStatement();
            ResultSet rs = pstmt.executeQuery("select quotationno from tbl_posdoclastnumbers");
            if (rs.next()) {
                if (Validations.isFieldNotEmpty(rs.getString("quotationno"))) {
                    String qotindblastadvcancelnopos = rs.getString("quotationno");
                    quotationnumber = "Q";
                    quotationnumber = quotationnumber + siteid;
                    String qtlastpart = qotindblastadvcancelnopos.substring(quotationnumber.length());
                    String finalnumber = quotationnumber + qtlastpart;
                    //  return rs.getString("advancereceiptno");
                    return finalnumber;
                //return rs.getString("advancereceiptcancellationno");
                } else {                    
                    String quotation = "Q";
                    Statement pstmt1 = conn.createStatement();
                    //  PlantDetails plantDetails = new PlantDetails(conn);
                    //    if (plantDetails.getSiteCode() != null) {
                    //customerCode=plantDetails.getSiteCode();
                    //   advancereceipt=plantDetails.getSiteCode();
                    //to be done will append site specific value

                    //  customerCode = customerCode + "AA" ;

                    quotation = quotation + siteid;


                    //  advancereceipt = advancereceipt + "AA";
                    for (int i = quotation.length(); i < 10; i++) {
                        quotation = quotation + "0";
                    }
                    result = quotation;
                    pstmt1.executeUpdate("update tbl_posdoclastnumbers set quotationno ='" + quotation + "'");
                }
            }
            return result;
        } catch (Exception e) {
           throw new SQLException();
        }

    }
    
   /**
   * To Update the Last Quotation No in POSDOC LAST Number Table
   */   
   public void updateLastSaleorder(Connection con, String quotationNo) throws SQLException{
        try {
            Statement pstmt = con.createStatement();
            String searchstatement = "update tbl_posdoclastnumbers set quotationno='" + quotationNo + "'";
            pstmt.executeUpdate(searchstatement);
        } catch (SQLException sQLException) {
            throw new SQLException();
        }
    }
 
   /**
   * To Search Quotation No
   */   
   QuotationHeaderPOJO searchQuotationno(Connection con, String quotationno_for_search) {
    QuotationHeaderPOJO SERCHQH=new QuotationHeaderPOJO();    
    try {       
        
            PreparedStatement statement = null;
            statement = con.prepareStatement("select q.quotationno ,q.customercode,q.quotationdate,q.status,q.styleconsultant,c.customercode ,c.firstname,c.mobileno from tbl_quotationheader as  q ,tbl_customermastermain as c where q.quotationno=? and q.customercode=c.customercode");
            statement.setString(1, quotationno_for_search);
            ResultSet rs=statement.executeQuery();
            if(rs.next())
            {
            SERCHQH.setQuotationno(rs.getString("quotationno"));
            SERCHQH.setCustomerCode(rs.getString("customercode"));
            SERCHQH.setQuotationDate(rs.getInt("quotationdate"));
            SERCHQH.setStyleConsultant(rs.getString("styleconsultant"));
            SERCHQH.setCustomerName(rs.getString("firstname"));
            SERCHQH.setTelephoneno(rs.getString("mobileno"));
            SERCHQH.setStatus(rs.getString("status"));
            return SERCHQH;
            }else
                return null;
        }catch(Exception e)
        {
          e.printStackTrace();
          return null;
        }

        }
        
   /**
   * To Get List of Quotation Headers for Search and display
   */   
   public ArrayList getQuotationDisplayList(Connection conn, String searchstatement) {
        try {
            Statement pstmt = conn.createStatement();            
            ResultSet rs = pstmt.executeQuery(searchstatement);

            ArrayList<QuotationHeaderPOJO> quotationlist = new ArrayList<QuotationHeaderPOJO>();

            while (rs.next()) {
                QuotationHeaderPOJO quotationheaderpojoobj = new QuotationHeaderPOJO();
                quotationheaderpojoobj.setQuotationno(rs.getString("quotationno"));
                quotationheaderpojoobj.setQuotationDate(rs.getInt("quotationdate"));
                quotationheaderpojoobj.setCustomerCode(rs.getString("customercode"));
                quotationheaderpojoobj.setCustomerName(rs.getString("firstname"));
                
                quotationlist.add(quotationheaderpojoobj);
            }
            return quotationlist;
        } catch (SQLException sQLException) {
        sQLException.printStackTrace();
            return null;
        }
    }
       
   /**
   * To Get Quotation Header POJO FOr Sending to Web Service
   */   
   public QuotationHeaderPOJO getQuotationListforThread(Connection conn, String searchstatement) {
       Statement pstmt;
       ResultSet rs;
        try {
            QuotationHeaderPOJO quotationheaderpojoobj=new QuotationHeaderPOJO();
            pstmt = conn.createStatement();            
            rs = pstmt.executeQuery(searchstatement);
            while (rs.next()) {                
                quotationheaderpojoobj.setQuotationno(rs.getString("quotationno"));
                quotationheaderpojoobj.setQuotationDate(rs.getInt("quotationdate"));
                quotationheaderpojoobj.setCustomerCode(rs.getString("customercode"));
                quotationheaderpojoobj.setDatasheetno(rs.getString("datasheetno"));
                quotationheaderpojoobj.setValidity(rs.getInt("validity"));
                quotationheaderpojoobj.setStyleConsultant(rs.getString("styleconsultant"));               
            }
            return quotationheaderpojoobj;
        } catch (SQLException sQLException) {
        sQLException.printStackTrace();
            return null;
        }finally {
            pstmt = null;
            rs = null;
        }        
    }
  
   /**
   * To Get Quotation List for Display in search between dates
   */      
   public ArrayList getQuotationDisplayListBetweenTwoDate(Connection conn, String searchstatement, int createfromdate, int createtodate) {
        PreparedStatement pstmt;
        ResultSet rs;
        try {            
            pstmt = conn.prepareStatement(searchstatement);
            pstmt.setInt(1, createfromdate);
            pstmt.setInt(2, createtodate);
            rs = pstmt.executeQuery();
            ArrayList<QuotationHeaderPOJO> quotationlist = new ArrayList<QuotationHeaderPOJO>();         
            while (rs.next()) {
                QuotationHeaderPOJO quotationheaderpojoobj = new QuotationHeaderPOJO();
                quotationheaderpojoobj.setQuotationno(rs.getString("quotationno"));
                quotationheaderpojoobj.setQuotationDate(rs.getInt("quotationdate"));
                quotationheaderpojoobj.setCustomerCode(rs.getString("customercode"));
                quotationheaderpojoobj.setCustomerName(rs.getString("firstname"));                          
                quotationlist.add(quotationheaderpojoobj);
            }
            return quotationlist;
        } catch (SQLException sQLException) {
            return null;
        }finally {
            
        }
    }
    
   /**
   * To Get Quotation Quotation Condition Object for Webservice
   */          
   public in.co.titan.quotation.DTQuotation.QuotationCondition setQoConDition(ConditionTypePOJO conditionTypePOJO, int refLineItemNo, int condLineItemNo, String typeOfCondition) {
        in.co.titan.quotation.DTQuotation.QuotationCondition qOCondition = null;
        DecimalFormat df = new DecimalFormat("0.00");
        if (conditionTypePOJO != null) {
            qOCondition = new in.co.titan.quotation.DTQuotation.QuotationCondition();
            qOCondition.setRefLineItem(new BigInteger(Integer.toString(refLineItemNo)));
            qOCondition.setConditionLineItemNo(new BigInteger(Integer.toString(condLineItemNo)));
            if(Validations.isFieldNotEmpty(conditionTypePOJO.getDummyconditiontype())){
            qOCondition.setPOSCondType(conditionTypePOJO.getDummyconditiontype());
            }            
            if(Validations.isFieldNotEmpty(conditionTypePOJO.getValue())){
            qOCondition.setAmount(new BigDecimal(df.format(conditionTypePOJO.getValue())));
            }                      
            if(Validations.isFieldNotEmpty(conditionTypePOJO.getCalculationtype())){
                qOCondition.setCalcType(conditionTypePOJO.getCalculationtype());
            }            
            if(Validations.isFieldNotEmpty(conditionTypePOJO.getNetAmount())){
            qOCondition.setNetAmount(new BigDecimal(df.format(conditionTypePOJO.getNetAmount())));
            }            
            if(Validations.isFieldNotEmpty(typeOfCondition)){
            qOCondition.setTypeOfCondition(typeOfCondition);
            }
        }
        return qOCondition;
    }
    
   /**
   * To Send Quotation Data for the Web Service and ISR
   */      
   public String execute(DataObject dataObject, String updateType) {
        String updatedinISR="false";
        QuotationHeaderPOJO quotationobj = null;
        Vector<QuotationDetailsPOJO> quotationdetailsobj = null;
        MsdeConnection msdeconn = new MsdeConnection();
        Connection con = msdeconn.createConnection();
        /*Retrieving CompanyCode from siteMaster*/
        SiteMasterDO smdo = new SiteMasterDO();
        SiteMasterPOJO ampojoobj = new SiteMasterPOJO();
        String companyCode1 = smdo.getSiteCompanyCode(con);
        ampojoobj.setCompanycode(companyCode1);
        in.co.titan.quotation.MIOBASYNQuotationService service;
        in.co.titan.quotation.MIOBASYNQuotation port;
        Map<String,Object> ctxt;
        QuotationBean masterBean;
            try {
                if (dataObject instanceof QuotationBean) {
                    try {                         
                        // Call Web Service Operation
                        service = new in.co.titan.quotation.MIOBASYNQuotationService();
                        port = service.getMIOBASYNQuotationPort();                        
                        ctxt =((BindingProvider)port).getRequestContext();
                        ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, 60000);
                        ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
                        ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());
                        ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port()+"/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_Quotation&version=3.0&Sender.Service=x&Interface=x^x");
                        // TODO initialize WS operation arguments here
                        in.co.titan.quotation.DTQuotation mtQuotation = new in.co.titan.quotation.DTQuotation();
                        masterBean = new QuotationBean();
                        masterBean = (QuotationBean) dataObject;
                        mtQuotation.setCompanyCode(companyCode1);
                        quotationobj = masterBean.getQuotationHeaderPOJO();
                        quotationdetailsobj = (Vector<QuotationDetailsPOJO>) masterBean.getQuotationDetailsPOJOs();         
               
                        if(Validations.isFieldNotEmpty(companyCode1)){
                        mtQuotation.setCompanyCode(companyCode1); } //  COMPANY CODE (mandatory)                
                            if (quotationobj != null) {                    
                                if(Validations.isFieldNotEmpty(quotationobj.getStoreCode())){
                                mtQuotation.setStoreCode(quotationobj.getStoreCode()); 
                                mtQuotation.setSITESEARCH(quotationobj.getStoreCode()); 
                                }  // STORE CODE  (mandatory)                    
                                if(Validations.isFieldNotEmpty(quotationobj.getFiscalYear())){
                                mtQuotation.setFYear(String.valueOf(quotationobj.getFiscalYear()));  
                                }  // FISCAL YEAR  (mandatory)                                                     
                                mtQuotation.setQuotationNo(quotationobj.getQuotationno());  // SALE ORDERNO (mandatory)
                                try {
                                if (quotationobj.getQuotationDate() != 0) {
                                    java.util.Date date1 = ConvertDate.getUtilDateFromNumericDate(quotationobj.getQuotationDate());
                                    if (date1 != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(date1);
                                    if (xmlDate != null) {
                                    mtQuotation.setDocumentDate(xmlDate);
                                    }
                                }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try
                    {
                    if(quotationobj.getDatasheetno()!=null)
                    {
                    mtQuotation.setDataSheetNO(quotationobj.getDatasheetno());                   
                    }
                    }catch(Exception e)
                    {
                    
                    }
                    
                      try {
                        if (quotationobj.getValidity() != 0) {
                            java.util.Date date1 = ConvertDate.getUtilDateFromNumericDate(quotationobj.getValidity());
                            if (date1 != null) {
                                XMLCalendar xmlDate = new XMLCalendar(date1);
                                if (xmlDate != null) {
                                    mtQuotation.setValidityPeriod(xmlDate);
                                }

                            }
                        }
                    } catch (Exception e) {
e.printStackTrace();
                    }
                    if(Validations.isFieldNotEmpty(quotationobj.getCustomerCode())){
                    mtQuotation.setCustomerNo(quotationobj.getCustomerCode());
                    }
                                 
                    if(Validations.isFieldNotEmpty(quotationobj.getCreatedBy())){
                    mtQuotation.setCreatedBy(quotationobj.getCreatedBy());}
                    try {

                        java.util.Date createdDate = new java.util.Date();
                        Calendar createdTime = ConvertDate.getSqlTimeFromString(ConvertDate.getCurrentTimeToString());
                        if (createdDate != null) {
                            XMLCalendar xmlDate = new XMLCalendar(createdDate);
                            if (createdTime != null) {
                                xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                                if (xmlDate != null) {
                                    mtQuotation.setCreatedTime(xmlDate);

                                }
                            }
                            if (xmlDate != null) {
                                mtQuotation.setCreatedDate(xmlDate);
                            }

                        }
                    } catch (Exception e) {
                  e.printStackTrace();
                    }


                }
             
                /*************SALE ORDER LINE ITEMS *********************/
                ArrayList<in.co.titan.quotation.DTQuotation.QuotationCondition> qoConditions = new ArrayList<in.co.titan.quotation.DTQuotation.QuotationCondition>();
                ArrayList<in.co.titan.quotation.DTQuotation.QuotationItem> qoItems = new ArrayList<in.co.titan.quotation.DTQuotation.QuotationItem>();
                if (quotationdetailsobj != null) {
                    Iterator so_DetailsList = quotationdetailsobj.iterator();
                    if (so_DetailsList != null) {
                        in.co.titan.quotation.DTQuotation.QuotationItem qoItem = null;
                        SOLineItemPOJO detailsPojo = null;
                       
                        in.co.titan.quotation.DTQuotation.QuotationCondition quotationCondition;
                       
                        
                        int i = 0;
                        DecimalFormat df = new DecimalFormat("#0.00");
                        while (so_DetailsList.hasNext()) {
                            int j = 0;
                            detailsPojo = (SOLineItemPOJO) so_DetailsList.next();
                            if (detailsPojo != null) {
                                qoItem = new in.co.titan.quotation.DTQuotation.QuotationItem();
                                BigDecimal amt = new BigDecimal(detailsPojo.getNetamount());
                                qoItem.setPOSLineItem(new BigInteger(Integer.toString(++i)));
                                
                                if(Validations.isFieldNotEmpty(detailsPojo.getMaterial())){
                                qoItem.setItemCode(detailsPojo.getMaterial().toUpperCase());
                                String division = new ArticleDO().getDivisionByMaterial(con, detailsPojo.getMaterial());
                                if(Validations.isFieldNotEmpty(division))
                                    mtQuotation.setDivision(division);
                                }
                                if(Validations.isFieldNotEmpty(quotationobj.getStyleConsultant()))
                                {
                                
                                qoItem.setStyleConsultant(quotationobj.getStyleConsultant());
                                }
                                
                                if(Validations.isFieldNotEmpty((detailsPojo.getQuantity()))){
                                qoItem.setQuantity(new BigInteger(Integer.toString(detailsPojo.getQuantity())));}
                                
                              
                                if(Validations.isFieldNotEmpty(detailsPojo.getTaxableValue())){
                                qoItem.setTaxableValue(new BigDecimal(df.format(detailsPojo.getTaxableValue())));}
                                
                                 quotationCondition = setQoConDition(detailsPojo.getUCP(), i, ++j, "U");
                                if (quotationCondition != null) {
                                    if(quotationCondition.getPOSCondType() != null){
                                    qoConditions.add(quotationCondition);
                                    }
                                }
                               
                                if(detailsPojo.getTaxDetails() != null){
                                Iterator iterator = detailsPojo.getTaxDetails().iterator();
                                while (iterator.hasNext()) {
                                    ConditionTypePOJO conditionTypePOJO = (ConditionTypePOJO) iterator.next();
                                    quotationCondition = setQoConDition(conditionTypePOJO, i, ++j, "T");
                                    if (quotationCondition != null) {
                                         if(quotationCondition.getPOSCondType() != null){
                                            qoConditions.add(quotationCondition);
                                         }
                                    }
                                }
                                }
                                if(detailsPojo.isOtherChargesPresent()){
                                Iterator iterator = detailsPojo.getOtherCharges().iterator();
                                while (iterator.hasNext()) {
                                    ConditionTypePOJO conditionTypePOJO = (ConditionTypePOJO) iterator.next();
                                    quotationCondition = setQoConDition(conditionTypePOJO, i, ++j, "O");
                                    if (quotationCondition != null) {
                                         if(quotationCondition.getPOSCondType() != null){
                                        qoConditions.add(quotationCondition);
                                         }
                                    }
                                }
                                }

                               
                               
                               
                               

                                qoItems.add(qoItem);
                            }
                        }
                    }
                    mtQuotation.getQuotationItem().addAll(qoItems);
                    mtQuotation.getQuotationCondition().addAll(qoConditions);
                      
                        port.miOBASYNQuotation(mtQuotation);
                        
          Map responseContext = ((BindingProvider)port).getResponseContext();
          Integer responseCode = (Integer)responseContext.get(MessageContext.HTTP_RESPONSE_CODE);
            if(responseCode.intValue() == 200){
            updatedinISR= "true";
            }else{
             updatedinISR= "false";
            }
                     //     updatedinISR="true";
                        
                }  
                    } catch (Exception ex) {
                        // TODO handle custom exceptions here
                      return "false";
                    }

                }
                }catch(Exception e){
                    e.printStackTrace();
                     return "false";
                }
        return updatedinISR;
     }

}
