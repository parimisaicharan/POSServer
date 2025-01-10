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
package ISRetail.services;
import ISRetail.Helpers.ConvertDate;
import ISRetail.Helpers.XMLCalendar;
import ISRetail.Webservices.ConditionTypePOJO;
import ISRetail.Webservices.DataObject;
import ISRetail.Webservices.Webservice;
import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.article.ArticleDO;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.salesorder.SOLineItemPOJO;
import ISRetail.utility.validations.Validations;
import com.sun.xml.ws.client.BindingProviderProperties;
import in.co.titan.serviceorder.DTServiceOrder.SoItem;
import java.math.BigDecimal;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import javax.xml.ws.BindingProvider;

public class ServicesHeaderDO  implements Webservice {
    
/*
 public int saveServicesheader(ServicesHeaderPOJO servicesheaderpojoobj, Connection conn) throws SQLException

    {
   int res = 0;
        
        
        try {

            String insertstr = "insert into tbl_serviceorderheader values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(insertstr);
            

              
                pstmt.setString(1, servicesheaderpojoobj.getStoreCode());
                pstmt.setInt(2, servicesheaderpojoobj.getFiscalYear());
                pstmt.setString(3, servicesheaderpojoobj.getServiceorderno());
                pstmt.setString(4,servicesheaderpojoobj.getCustomercode());
                pstmt.setInt(5,servicesheaderpojoobj.getServiceorderdate());
            
                pstmt.setInt(6, servicesheaderpojoobj.getDeliveryDate());
                
                pstmt.setDouble(7, servicesheaderpojoobj.getTotalAmount());
                pstmt.setString(8, servicesheaderpojoobj.getReasonforcancellation());
                pstmt.setInt(9, servicesheaderpojoobj.getDateofcancellation());
                pstmt.setString(10, servicesheaderpojoobj.getOrderstatus());
                pstmt.setString(11,servicesheaderpojoobj.getCreatedBy());
                pstmt.setInt(12,servicesheaderpojoobj.getCreatedDate());
                pstmt.setString(13,servicesheaderpojoobj.getCreatedTime());
                pstmt.setString(14,servicesheaderpojoobj.getModifiedBy());
                pstmt.setInt(15,servicesheaderpojoobj.getModifiedDate());
                pstmt.setString(16,servicesheaderpojoobj.getModifiedTime());
                pstmt.setString(17,servicesheaderpojoobj.getCancelledBy());
                pstmt.setString(18,servicesheaderpojoobj.getCancelledTime());
                
                 
                        
                        res = pstmt.executeUpdate();


            }
        catch(Exception ex){
               ex.printStackTrace();
               throw new SQLException();
            }
      
        
        return res;
    }
    
 */
    
      /* Service order Serial number retrieving from Database*/
   
 /*   public String getMaxServiceorderNo(Connection con) {
        try {
            Statement pstmt = con.createStatement();
            String searchstatement = "select saleorderno from tbl_posdoclastnumbers ";

            ResultSet rs = pstmt.executeQuery(searchstatement);

            if (rs.next()) {
                return rs.getString("saleorderno");
                
            } else {
                return null;
            }

        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        }

    }
  */ 
    /* New Service order Serial Number */
   /*
    public void updateLastSaleorder(Connection con, String saleorderno) {
        try {
            Statement pstmt = con.createStatement();
            String searchstatement = "update tbl_posdoclastnumbers set saleorderno='" + saleorderno + "'";
            pstmt.executeUpdate(searchstatement);
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }
    }
    */ 

    public ArrayList getServiceDisplayList(Connection conn, String searchstatement) {
        Statement pstmt;
        ResultSet rs;  
        try {
            pstmt = conn.createStatement();
            //String searchstatement = "select * from customermasternew where  " + wherestatement;
            rs = pstmt.executeQuery(searchstatement);
            ArrayList<ServicesHeaderPOJO> servicelist = new ArrayList<ServicesHeaderPOJO>();
            while (rs.next()) {
                ServicesHeaderPOJO serviceheaderpojoobj = new ServicesHeaderPOJO();
                serviceheaderpojoobj.setServiceorderno(rs.getString("serviceorderno"));
                serviceheaderpojoobj.setServiceorderdate(rs.getInt("serviceorderdate"));
                serviceheaderpojoobj.setCustomercode(rs.getString("customercode"));                
                // customermasterpojoobj.setCreatedon(rs.getDate("createdon"));
                serviceheaderpojoobj.setFirstname(rs.getString("firstname"));                
                servicelist.add(serviceheaderpojoobj);
            }
            return servicelist;
        } catch (SQLException sQLException) {
        sQLException.printStackTrace();
            return null;
        }finally {
            pstmt= null;
            rs =null;
        }
    }

     
    public ArrayList getServiceDisplayListBetweenTwoDate(Connection conn, String searchstatement, int createfromdate, int createtodate) {
        PreparedStatement pstmt;
        ResultSet rs;  
        try {
            //Statement pstmt = conn.createStatement();
            pstmt = conn.prepareStatement(searchstatement);
            pstmt.setInt(1, createfromdate);
            pstmt.setInt(2, createtodate);
            rs = pstmt.executeQuery();
            ArrayList<ServicesHeaderPOJO> servicelist = new ArrayList<ServicesHeaderPOJO>();
            //ArrayList<CustomerMasterPOJO> customerlist = new ArrayList<CustomerMasterPOJO>();
            while (rs.next()) {
                ServicesHeaderPOJO serviceheaderpojoobj = new ServicesHeaderPOJO();
                serviceheaderpojoobj.setServiceorderno(rs.getString("serviceorderno"));
                serviceheaderpojoobj.setServiceorderdate(rs.getInt("serviceorderdate"));
                serviceheaderpojoobj.setCustomercode(rs.getString("customercode"));
                serviceheaderpojoobj.setFirstname(rs.getString("firstname"));             
                servicelist.add(serviceheaderpojoobj);
            }
            return servicelist;
        } catch (SQLException sQLException) {

            return null;
        }finally {
            pstmt= null;
            rs =null;
        }
    }

      /*
      * The following function is used to get serviceorderdetails  and customer details
      */
    ServicesHeaderPOJO searchSaleorderno(Connection con, String salorderno_for_serarch) {
    
        ServicesHeaderPOJO SERCHSH=new ServicesHeaderPOJO();
        PreparedStatement statement;
        ResultSet rs;  
    
    try {
        
            statement = null;
            statement = con.prepareStatement("select s.serviceorderno ,s.customercode,s.serviceorderdate,s.deliverydate,c.customercode ,c.firstname,c.mobileno from tbl_serviceorderheader as  s ,tbl_customermastermain as c where s.serviceorderno=? and s.customercode=c.customercode");
            statement.setString(1, salorderno_for_serarch);
            rs=statement.executeQuery();
            if(rs.next())
            {
            SERCHSH.setServiceorderno(rs.getString("serviceorderno"));
            SERCHSH.setCustomercode(rs.getString("customercode"));
            SERCHSH.setDeliveryDate(rs.getInt("deliverydate"));
            SERCHSH.setFirstname(rs.getString("firstname"));
            SERCHSH.setMobileno(rs.getString("mobileno"));
           
            return SERCHSH;
            }else
                return null;
        }catch(Exception e)
        {
          e.printStackTrace();
          return null;
        }finally {
            statement= null;
            rs =null;
        }

        }
     
     /*******Getting Bill amount from serviceorder to advance receipt **********************/
     public double getBillamountforadvrecpt1(String serviceordernum,Connection con) {
         Statement pstmt;
         ResultSet rs;  
        try {
            pstmt = con.createStatement();
            String searchstatement = "select totalamount from tbl_serviceorderheader where serviceorderno='"+serviceordernum+"'";
            rs = pstmt.executeQuery(searchstatement);
            if (rs.next()) {
                return rs.getDouble("totalamount");
            } else {
                return 0;
            }
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return 0;
        }finally {
            pstmt= null;
            rs =null;
        }
    }
    
     
     public ServicesHeaderPOJO getServiceOrderByServiceOrderNo(Connection con,String serviceOrderNo){
         Statement pstmt;
         ResultSet rs;  
        try {
            ServicesHeaderPOJO servicesHeaderPOJO = null;
            pstmt = con.createStatement();
            rs = pstmt.executeQuery("select * from tbl_serviceorderheader where serviceorderno = '"+serviceOrderNo+"'");
            if (rs.next()) {
                servicesHeaderPOJO = new ServicesHeaderPOJO();
                servicesHeaderPOJO.setStoreCode(rs.getString("storecode"));
                servicesHeaderPOJO.setFiscalYear(rs.getInt("fiscalyear"));
                servicesHeaderPOJO.setServiceorderno(rs.getString("serviceorderno"));
                servicesHeaderPOJO.setCustomercode(rs.getString("customercode"));
                servicesHeaderPOJO.setServiceorderdate(rs.getInt("serviceorderdate"));
                servicesHeaderPOJO.setDeliveryDate(rs.getInt("deliverydate"));
                servicesHeaderPOJO.setTotalAmount(rs.getDouble("totalamount"));
                servicesHeaderPOJO.setOrderstatus(rs.getString("orderstatus"));
                servicesHeaderPOJO.setCreatedDate(rs.getInt("createddate"));
                servicesHeaderPOJO.setCreatedBy(rs.getString("createdby"));
                servicesHeaderPOJO.setCreatedTime(rs.getString("createdtime"));
                servicesHeaderPOJO.setModifiedDate(rs.getInt("modifieddate"));
                servicesHeaderPOJO.setModifiedBy(rs.getString("modifiedby"));
                servicesHeaderPOJO.setModifiedTime(rs.getString("modifiedtime"));
                servicesHeaderPOJO.setDataSheetNo(rs.getString("datasheetno"));
                servicesHeaderPOJO.setOrderstatus(rs.getString("orderstatus"));
            } 
            return servicesHeaderPOJO;
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        }finally {
            pstmt= null;
            rs =null;
        }
     }
   public int archiveAllServicesTables(Connection con,int fiscalYear) throws SQLException{
        int recDeleted = 0;
        PreparedStatement pstmt;
        String query;
        try {
            query = "delete from tbl_serviceorder_condition where fisaclyear= ?";
            pstmt = con.prepareStatement(query);
            int res = pstmt.executeUpdate();
            recDeleted = recDeleted + res;
            
            query = "delete from tbl_serviceorderlineitems where fisaclyear= ?";
            pstmt = con.prepareStatement(query);
            res = pstmt.executeUpdate();
            recDeleted = recDeleted + res;
            
            query = "delete from tbl_serviceorderheader where fisaclyear= ?";
            pstmt = con.prepareStatement(query);
            res = pstmt.executeUpdate();
            recDeleted = recDeleted + res;
        } catch (Exception e) {
            e.printStackTrace();
            recDeleted=0;
            throw new SQLException();
        } finally {
            query = null;
        }
        return recDeleted;
    }
   
   public ServicesHeaderPOJO getServiceordercancellationDetailsBySaleorderNo(Connection conn, String saleorderno) {
        PreparedStatement pstmt;        
        ResultSet rs;  
        try {
            pstmt = conn.prepareStatement("select * from tbl_serviceorderheader where serviceorderno=? ");
            pstmt.setString(1, saleorderno);
            rs = pstmt.executeQuery();
            ServicesHeaderPOJO salesorderheaderpojoobj = new ServicesHeaderPOJO();
            if (rs.next()) {


                salesorderheaderpojoobj.setStoreCode(rs.getString("storecode"));
                salesorderheaderpojoobj.setFiscalYear(rs.getInt("fiscalyear"));
                salesorderheaderpojoobj.setServiceorderno(rs.getString("serviceorderno"));
                salesorderheaderpojoobj.setDateofcancellation(rs.getInt("dateofcancellation"));
                salesorderheaderpojoobj.setCustomercode(rs.getString("customercode"));
                salesorderheaderpojoobj.setDataSheetNo(rs.getString("datasheetno"));
                salesorderheaderpojoobj.setReasonforcancellation(rs.getString("reasonforcancellation"));
                salesorderheaderpojoobj.setOrderstatus(rs.getString("orderstatus"));
                salesorderheaderpojoobj.setCancelledTime(rs.getString("cancelledtime"));
                salesorderheaderpojoobj.setCancelledBy(rs.getString("cancelledby"));                
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


    public String execute(DataObject obj, String updateType) {
        ServicesHeaderPOJO servicesHeaderPOJO = null;
        ArrayList<SOLineItemPOJO> sOLineItemPOJOs = null;
        ServiceOrderBean serviceOrderBean = null;
        SiteMasterDO siteMasterDO = null;
        MsdeConnection msdeConnection = null;
        Connection connection= null;
        Iterator iterator = null;
        SOLineItemPOJO sOLineItemPOJO = null;
        String updateStatus = null;
        try{
          
            if(obj != null && obj instanceof ServiceOrderBean ){
                serviceOrderBean = (ServiceOrderBean) obj;
                servicesHeaderPOJO = serviceOrderBean.getServicesHeaderPOJO();
                sOLineItemPOJOs = serviceOrderBean.getSOLineItemPOJOs();
                in.co.titan.serviceorder.DTServiceOrder.SoCondition sOCondition = null;
            try { 
                
                // Call Web Service Operation
                in.co.titan.serviceorder.MIOBASYNServiceOrderService service = new in.co.titan.serviceorder.MIOBASYNServiceOrderService();
                //in.co.titan.serviceorder.MIOBASYNServiceOrder port = service.getMIOBASYNServiceOrderPort();
                in.co.titan.serviceorder.MIOBASYNServiceOrder port = service.getHTTPSPort();
                // TODO initialize WS operation arguments here
                Map<String, Object> ctxt = ((BindingProvider) port).getRequestContext();
                ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, 60000);
                ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());//XIConnectionDetailsPojo.getUsername()"test_enteg"
                ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());//"enteg321"
               // ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port()+"/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_VersionManagement&version=3.0&Sender.Service=x&Interface=x%5Ex");
                ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port()+"/XISOAPAdapter/MessageServlet?senderParty=&senderService=BS_TITAN_POS&receiverParty=&receiverService=&interface=MI_OB_ASYN_ServiceOrder&interfaceNamespace=http://titan.co.in/ServiceOrder");
                in.co.titan.serviceorder.DTServiceOrder mtServiceOrder = new in.co.titan.serviceorder.DTServiceOrder();
                 ArrayList<in.co.titan.serviceorder.DTServiceOrder.SoCondition> soConditions = new ArrayList<in.co.titan.serviceorder.DTServiceOrder.SoCondition>();
                ArrayList<in.co.titan.serviceorder.DTServiceOrder.SoItem> soItems = new ArrayList<in.co.titan.serviceorder.DTServiceOrder.SoItem>();
                if(servicesHeaderPOJO != null){
                      msdeConnection = new MsdeConnection();
                      connection = msdeConnection.createConnection();
                      siteMasterDO = new SiteMasterDO();
                      if(!updateType.equalsIgnoreCase("I")){
                      mtServiceOrder.setChangedBy(servicesHeaderPOJO.getModifiedBy());
                      try {
                          java.util.Date changeDate = ConvertDate.getUtilDateFromNumericDate(servicesHeaderPOJO.getModifiedDate());
                          Calendar changeTime = ConvertDate.getSqlTimeFromString(servicesHeaderPOJO.getModifiedTime());
                          if (changeDate != null) {
                              XMLCalendar xmlDate = new XMLCalendar(changeDate);
                              if (xmlDate != null) {
                                if (changeTime != null) {
                                    xmlDate.setTime(changeTime.get(Calendar.HOUR_OF_DAY), changeTime.get(Calendar.MINUTE), changeTime.get(Calendar.SECOND));
                                    if (xmlDate != null) {
                                        mtServiceOrder.setChangedTime(xmlDate);
                                    }
                                }
                                if (xmlDate != null) {
                                    mtServiceOrder.setChangedDate(xmlDate);
                                }
                            }
                        }
                      } catch (Exception e) {

                      }
                      }
                      
                      mtServiceOrder.setCompanyCode(siteMasterDO.getSiteCompanyCode(connection));
                      
                      mtServiceOrder.setCreatedBy(servicesHeaderPOJO.getCreatedBy());
                       try {
                          java.util.Date createDate = ConvertDate.getUtilDateFromNumericDate(servicesHeaderPOJO.getCreatedDate());
                          Calendar createTime = ConvertDate.getSqlTimeFromString(servicesHeaderPOJO.getCreatedTime());
                          if (createDate != null) {
                              XMLCalendar xmlDate = new XMLCalendar(createDate);
                              if (xmlDate != null) {
                                if (createTime != null) {
                                    xmlDate.setTime(createTime.get(Calendar.HOUR_OF_DAY), createTime.get(Calendar.MINUTE), createTime.get(Calendar.SECOND));
                                    if (xmlDate != null) {
                                        mtServiceOrder.setCreatedTime(xmlDate);
                                    }
                                }
                                if (xmlDate != null) {
                                    mtServiceOrder.setCreatedDate(xmlDate);
                                }
                            }
                        }
                      } catch (Exception e) {

                      }
                      
                      mtServiceOrder.setCustomerNo(servicesHeaderPOJO.getCustomercode());
                      if(servicesHeaderPOJO.getDataSheetNo() != null)
                      mtServiceOrder.setDataSheetNo(servicesHeaderPOJO.getDataSheetNo()); 
                      try {
                            if (servicesHeaderPOJO.getDeliveryDate() != 0) {
                                java.util.Date deliveryDate = ConvertDate.getUtilDateFromNumericDate(servicesHeaderPOJO.getDeliveryDate());                               
                                if (deliveryDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(deliveryDate);
                                    if (xmlDate != null) {
                                        mtServiceOrder.setDeliveryDate(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                          e.printStackTrace();
                        }
                       try {
                            if (servicesHeaderPOJO.getDeliveryDate() != 0) {
                                java.util.Date serviceOrderDate = ConvertDate.getUtilDateFromNumericDate(servicesHeaderPOJO.getServiceorderdate());                               
                                if (serviceOrderDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(serviceOrderDate);
                                    if (xmlDate != null) {
                                        mtServiceOrder.setDocumentDate(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                       mtServiceOrder.setFlagMode(updateType);
                       try{
                       mtServiceOrder.setFyear(String.valueOf(servicesHeaderPOJO.getFiscalYear()));
                       }catch(Exception e){
                       }
                       mtServiceOrder.setParameterId("");//Parameter id should set
                       mtServiceOrder.setServiceOrderNo(servicesHeaderPOJO.getServiceorderno());
                       mtServiceOrder.setStoreCode(servicesHeaderPOJO.getStoreCode());
                       mtServiceOrder.setSITESEARCH(servicesHeaderPOJO.getStoreCode());
                       mtServiceOrder.setPOSOrderStatus(servicesHeaderPOJO.getOrderstatus());
                       if(sOLineItemPOJOs != null){
                           int i = 0;
                           int j = 0;
                           iterator = sOLineItemPOJOs.iterator();
                           while(iterator.hasNext()){
                               sOLineItemPOJO = (SOLineItemPOJO) iterator.next();
                               if(sOLineItemPOJO != null){
                               SoItem soItem = new SoItem();
                               j = 0;
                               if(i==0){
                                if (Validations.isFieldNotEmpty(sOLineItemPOJO.getMaterial())) {
                                    soItem.setItemCode(sOLineItemPOJO.getMaterial().toUpperCase());
                                    String division = new ArticleDO().getDivisionByMaterial(connection, sOLineItemPOJO.getMaterial());
                                    if (Validations.isFieldNotEmpty(division)) {
                                        mtServiceOrder.setDivision(division);
                                    }
                                }
                               }
                               i++;
                               if(Validations.isFieldNotEmpty(sOLineItemPOJO.getAnyVisibleDefect())){
                               soItem.setAnyVisibleDefect(sOLineItemPOJO.getAnyVisibleDefect());}
                               if(Validations.isFieldNotEmpty(sOLineItemPOJO.getApproxValue())){
                               soItem.setAppValue(sOLineItemPOJO.getApproxValue());}
                               if(Validations.isFieldNotEmpty(sOLineItemPOJO.getBatchId())){
                               soItem.setBatch(sOLineItemPOJO.getBatchId());}
                               if(Validations.isFieldNotEmpty(sOLineItemPOJO.getBrandColor())){
                               soItem.setBrandColor(sOLineItemPOJO.getBrandColor());}
                               if(Validations.isFieldNotEmpty(sOLineItemPOJO.getComments())){
                               soItem.setComments(sOLineItemPOJO.getComments());}
                               if(Validations.isFieldNotEmpty(sOLineItemPOJO.isCustomerItem())){
                               soItem.setCustomerItem(sOLineItemPOJO.isCustomerItem());}
                               if(Validations.isFieldNotEmpty(sOLineItemPOJO.getDiscountRefNo())){
                               soItem.setEmployeeDiscount(sOLineItemPOJO.getDiscountRefNo());}
                               if(Validations.isFieldNotEmpty(sOLineItemPOJO.getMaterial())){
                               soItem.setItemCode(sOLineItemPOJO.getMaterial());}
                               if(Validations.isFieldNotEmpty(sOLineItemPOJO.getModelNo())){
                               soItem.setModelNo(sOLineItemPOJO.getModelNo());}
                               if(Validations.isFieldNotEmpty(sOLineItemPOJO.getNatureOfService())){
                               soItem.setNatureOfService(sOLineItemPOJO.getNatureOfService());}
                               soItem.setPosLineItem(String.valueOf(i));
                               if(Validations.isFieldNotEmpty(sOLineItemPOJO.getProductDetail())){
                               soItem.setProductDetails(sOLineItemPOJO.getProductDetail());}
                               try{
                                  soItem.setQuantity(new BigDecimal(sOLineItemPOJO.getQuantity()));
                               }catch(Exception e){                                  
                               }
                               try{
                                  soItem.setTaxableValue(String.valueOf(sOLineItemPOJO.getTaxableValue())); // Should be bigdecimel
                               }catch(Exception e){                                  
                               }
                               
                                if (sOLineItemPOJO.getUCP() != null) {
                                    if (sOLineItemPOJO.getUCP().getDummyconditiontype() != null) {
                                        sOCondition = setSoConDition(sOLineItemPOJO.getUCP(), i, ++j, "U");
                                        if (sOCondition != null) {
                                            if (sOCondition.getPosCondType() != null) {
                                                soConditions.add(sOCondition);
                                            }
                                        }
                                    }
                                }
                                if (sOLineItemPOJO.getDiscountSelected() != null) {
                                    if (sOLineItemPOJO.getDiscountSelected().getDummyconditiontype() != null) {
                                        sOCondition = setSoConDition(sOLineItemPOJO.getDiscountSelected(), i, ++j, "D");
                                        if (sOCondition != null) {
                                            if (sOCondition.getPosCondType()!= null) {
                                                soConditions.add(sOCondition);
                                            }
                                        }
                                    }
                                }
                                if (sOLineItemPOJO.getOfferPromotion() != null) {
                                    if (sOLineItemPOJO.getOfferPromotion().getDummyconditiontype() != null) {
                                        sOCondition = setSoConDition(sOLineItemPOJO.getOfferPromotion(), i, ++j, "P");
                                        if (sOCondition != null) {
                                            if (sOCondition.getPosCondType() != null) {
                                                soConditions.add(sOCondition);
                                            }
                                        }
                                    }
                                }
                                if (sOLineItemPOJO.getTaxDetails() != null) {
                                    Iterator iterator1 = sOLineItemPOJO.getTaxDetails().iterator();
                                    while (iterator1.hasNext()) {
                                        ConditionTypePOJO conditionTypePOJO = (ConditionTypePOJO) iterator1.next();
                                        if (conditionTypePOJO != null) {
                                            if (conditionTypePOJO.getDummyconditiontype() != null) {

                                                sOCondition = setSoConDition(conditionTypePOJO, i, ++j, "T");
                                                if (sOCondition != null) {
                                                    if (sOCondition.getPosCondType() != null) {
                                                        soConditions.add(sOCondition);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                soItems.add(soItem);
                               }
                           }
                       }
                       
                }
            
                
                mtServiceOrder.getSoItem().addAll(soItems);
                mtServiceOrder.getSoCondition().addAll(soConditions);
                
                //Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                System.out.println("Service Order Storecode "+mtServiceOrder.getStoreCode());
                if(mtServiceOrder.getStoreCode()!=null && mtServiceOrder.getStoreCode().trim().length()>0){
                    port.miOBASYNServiceOrder(mtServiceOrder);
                    updateStatus = "true";    
                }else{
                    updateStatus = "false";
                }
                //End of Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 

                //Code commented by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                /*port.miOBASYNServiceOrder(mtServiceOrder);
                updateStatus = "true";*/
            } catch (Exception ex) {
               ex.printStackTrace();
                updateStatus = "false";
            }
            }
        }catch(Exception e){
            
        }
        
        return updateStatus;
    }
    
    public in.co.titan.serviceorder.DTServiceOrder.SoCondition setSoConDition(ConditionTypePOJO conditionTypePOJO, int refLineItemNo, int condLineItemNo, String typeOfCondition) {
        in.co.titan.serviceorder.DTServiceOrder.SoCondition sOCondition = null;
        DecimalFormat df = new DecimalFormat("0.00");
        if (conditionTypePOJO != null) {
            sOCondition = new in.co.titan.serviceorder.DTServiceOrder.SoCondition();
            try{
            sOCondition.setRefLineItem(String.valueOf(refLineItemNo));
            }catch(Exception e){
                
            }
            try{
            sOCondition.setCondLineItemNo(String.valueOf(condLineItemNo));
            }catch(Exception e){
                
            }
            //  if(conditionTypePOJO.getPromotionId()!=null)
            //     sOCondition.setPromotionID(conditionTypePOJO.getPromotionId());

           
            if (Validations.isFieldNotEmpty(conditionTypePOJO.getDummyconditiontype())) {
                sOCondition.setPosCondType(conditionTypePOJO.getDummyconditiontype());
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
                sOCondition.setTypeOfCondition(typeOfCondition);
            }



        }
        return sOCondition;
    }
    
        
     
}
  