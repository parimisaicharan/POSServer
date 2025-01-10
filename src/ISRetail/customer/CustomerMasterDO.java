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
 * This class file is used as a Data Object between Customer Form and Database
 * This is used for transaction of Customer Data from and to the database
 * 
 */
package ISRetail.customer;

import ISRetail.Helpers.ConvertDate;
import ISRetail.Helpers.XMLCalendar;
import ISRetail.Webservices.DataObject;
import ISRetail.Webservices.Webservice;
import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.serverconsole.PosConfigParamDO;
import ISRetail.utility.validations.Validations;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

public class CustomerMasterDO implements Webservice {

    public CustomerMasterPOJO getCustomer(Connection conn, String ccode) {
        PreparedStatement pstmt;
        PreparedStatement pstmt1;
        ResultSet rs;
        ResultSet rs1;

        try {
            pstmt = conn.prepareStatement("select * from tbl_customermaster where customercode=?");
            pstmt1 = conn.prepareStatement("select * from tbl_customermastermain where customercode=?");
            pstmt.setString(1, ccode);
            pstmt1.setString(1, ccode);
            rs = pstmt.executeQuery();
            rs1 = pstmt1.executeQuery();
            CustomerMasterPOJO customermasterpojoobj = new CustomerMasterPOJO();
            if (rs1.next()) {
                customermasterpojoobj.setCustomercode(rs1.getString("customercode"));
                customermasterpojoobj.setStorecode(rs1.getString("storecode"));
                customermasterpojoobj.setFiscalYear(rs1.getInt("fiscalyear"));
                customermasterpojoobj.setCustomerfirstname(rs1.getString("firstname"));
                customermasterpojoobj.setCustomerlastname(rs1.getString("lastname"));
                customermasterpojoobj.setGender(rs1.getString("gender"));
                customermasterpojoobj.setDob(rs1.getInt("dob"));
                customermasterpojoobj.setMobileno(rs1.getString("mobileno"));
                customermasterpojoobj.setTelephoneno(rs1.getString("telephoneno"));
                customermasterpojoobj.setEmail(rs1.getString("email"));
                customermasterpojoobj.setCreatedBy(rs1.getString("createdby"));
                customermasterpojoobj.setCreatedDate(rs1.getInt("createddate"));
                customermasterpojoobj.setCreatedTime(rs1.getString("createdtime"));
                customermasterpojoobj.setModifiedBy(rs1.getString("modifiedby"));
                customermasterpojoobj.setModifiedDate(rs1.getInt("modifieddate"));
                customermasterpojoobj.setModifiedTime(rs1.getString("modifiedtime"));
            }
            if (rs.next()) {
                customermasterpojoobj.setCustomercode(rs.getString("customercode"));
                customermasterpojoobj.setLoyalityno(rs.getString("ulpno"));
                customermasterpojoobj.setDatasheetno(rs.getString("datasheetno"));
                customermasterpojoobj.setTitle(rs.getString("title"));
                customermasterpojoobj.setAge(rs.getInt("age"));
                customermasterpojoobj.setHouseno(rs.getString("address"));
                customermasterpojoobj.setStreet(rs.getString("street"));
                customermasterpojoobj.setArea(rs.getString("area"));
                customermasterpojoobj.setCity(rs.getString("city"));
                customermasterpojoobj.setPincode(rs.getString("pincode"));
                customermasterpojoobj.setState(rs.getString("state"));
                customermasterpojoobj.setCountry(rs.getString("country"));
                customermasterpojoobj.setOfficeno(rs.getString("officeno"));
                customermasterpojoobj.setOfficenoextn(rs.getString("officenoextn"));
                customermasterpojoobj.setFaxno(rs.getString("faxno"));
                customermasterpojoobj.setFaxextn(rs.getString("faxextn"));
                customermasterpojoobj.setEducation(rs.getString("education"));
                customermasterpojoobj.setOccuapation(rs.getString("occupation"));
                customermasterpojoobj.setCustomercategory(rs.getString("customercategory"));
                customermasterpojoobj.setComments(rs.getString("comments"));
                customermasterpojoobj.setUpdateonoffer(rs.getString("updateonoffer"));
                customermasterpojoobj.setSendmeupdateson(rs.getString("preferredupdatemode"));
                customermasterpojoobj.setCompany(rs.getString("company"));
                customermasterpojoobj.setDesignation(rs.getString("designation"));
                customermasterpojoobj.setOfficebuildingno(rs.getString("officeaddress"));
                customermasterpojoobj.setOfficestreet(rs.getString("officestreet"));
                customermasterpojoobj.setOfficearea(rs.getString("officearea"));
                customermasterpojoobj.setOfficecity(rs.getString("officecity"));
                customermasterpojoobj.setOfficepincode(rs.getString("officepincode"));
                customermasterpojoobj.setOfficestate(rs.getString("officestate"));
                customermasterpojoobj.setOfficecountry(rs.getString("officecountry"));
                customermasterpojoobj.setMariagestatus(rs.getString("maritalstatus"));
                customermasterpojoobj.setMarrigeanniversary(rs.getString("marrigeanniversary"));
                customermasterpojoobj.setNoofpeople(rs.getInt("noofpeople"));
                customermasterpojoobj.setHobbies(rs.getString("hobbies"));
                customermasterpojoobj.setFavoritebrand(rs.getString("favoritebrand"));
                customermasterpojoobj.setRestaurant(rs.getString("restaurant"));
                customermasterpojoobj.setShoppingdestination(rs.getString("shoppingdestination"));
                customermasterpojoobj.setHolidayspot(rs.getString("holidayspot"));
                customermasterpojoobj.setPreferredairline(rs.getString("preferredairline"));
                customermasterpojoobj.setFavouritecar(rs.getString("favouritecar"));
                customermasterpojoobj.setAdmiredperson(rs.getString("admiredperson"));
                customermasterpojoobj.setFreefield1(rs.getString("freefield1"));
                customermasterpojoobj.setFreefield2(rs.getString("freefield2"));
                customermasterpojoobj.setFreefield3(rs.getString("freefield3"));
                customermasterpojoobj.setFreefield4(rs.getString("freefield4"));
                customermasterpojoobj.setFreefield5(rs.getString("freefield5"));
                customermasterpojoobj.setFreefield6(rs.getString("freefield6"));
                customermasterpojoobj.setFreefield7(rs.getString("freefield7"));
                customermasterpojoobj.setFreefield8(rs.getString("freefield8"));
                customermasterpojoobj.setFreefield9(rs.getString("freefield9"));
                customermasterpojoobj.setFreefield10(rs.getString("freefield10"));
                customermasterpojoobj.setPriority_telephoneno(rs.getString("priority_telephoneno"));
                customermasterpojoobj.setPriority_officeno(rs.getString("priority_officeno"));
                customermasterpojoobj.setPriority_mobileno(rs.getString("priority_mobileno"));
                customermasterpojoobj.setPriority_email(rs.getString("priority_email"));
                customermasterpojoobj.setPriority_faxno(rs.getString("priority_faxno"));
                customermasterpojoobj.setReference_tv(rs.getString("reference_tv"));
                customermasterpojoobj.setReference_hoarding(rs.getString("reference_hoarding"));
                customermasterpojoobj.setReference_friends(rs.getString("reference_friends"));
                customermasterpojoobj.setReference_newspaper(rs.getString("reference_newspaper"));
                customermasterpojoobj.setReference_leaflet(rs.getString("reference_leaflet"));
                customermasterpojoobj.setReference_others(rs.getString("reference_others"));
                customermasterpojoobj.setRelationship(rs.getString("reference_relation"));
                customermasterpojoobj.setCust_ref_no(rs.getString("reference_customer_no"));
                //Start - GSTN NO. Code added by Thangaraj 25.05.2017
                customermasterpojoobj.setGstn_no(rs.getString("gstn_no"));
                //End - GSTN NO. Code added by Thangaraj 25.05.2017
                //Added by Balachander V on 5.9.2019 to send SFDC
                customermasterpojoobj.setTabid(rs.getString("tabid"));
                //Ended by Balachander V on 5.9.2019 to send SFDC
                customermasterpojoobj.setEnrollmentOtp(rs.getString("enrollmentOtp"));//Added By Balachander V to send enrollment OTP to SAP
            }
            return customermasterpojoobj;
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            pstmt1 = null;
            rs1 = null;
            rs = null;
        }
    }

    public String getCustomerFullName(Connection conn, String customerCode) {
        PreparedStatement pstmt;
        PreparedStatement pstmt1;
        ResultSet rs;
        ResultSet rs1;
        String custName = "";
        try {
            pstmt = conn.prepareStatement("select title from tbl_customermaster where customercode=?");
            pstmt1 = conn.prepareStatement("select  firstname,lastname from tbl_customermastermain where customercode=?");
            pstmt.setString(1, customerCode);
            pstmt1.setString(1, customerCode);
            rs = pstmt.executeQuery();
            rs1 = pstmt1.executeQuery();
            CustomerMasterPOJO customermasterpojoobj = new CustomerMasterPOJO();
            String titleId = "", titleName = "";
            if (rs1.next()) {
                custName = rs1.getString("firstname") + " " + rs1.getString("lastname");
            }
            if (rs.next()) {
                titleId = rs.getString("title");
                if (titleId != null) {
                    Statement stmt = conn.createStatement();
                    ResultSet rsTitle = stmt.executeQuery("select description from dbo.tbl_parameter where parametertype='TITL' and id='" + titleId + "'");
                    if (rsTitle.next()) {
                        titleName = rsTitle.getString("description");
                    }
                }
            }
            custName = titleName + custName;
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        } finally {
            pstmt = null;
            pstmt1 = null;
            rs1 = null;
            rs = null;
        }

        return custName;
    }

    public CustomerMasterPOJO getCustomerAddress(Connection conn, String ccode) {
        PreparedStatement pstmt;
        PreparedStatement pstmt1;
        ResultSet rs;
        ResultSet rs1;
        try {
            pstmt = conn.prepareStatement("select title,officeno,address,street,area,city,pincode,state,country from tbl_customermaster where customercode=?");
            pstmt1 = conn.prepareStatement("select  customercode,firstname,lastname,mobileno,telephoneno from tbl_customermastermain where customercode=?");
            pstmt.setString(1, ccode);
            pstmt1.setString(1, ccode);
            rs = pstmt.executeQuery();
            rs1 = pstmt1.executeQuery();
            CustomerMasterPOJO customermasterpojoobj = new CustomerMasterPOJO();
            if (rs1.next()) {
                customermasterpojoobj.setCustomercode(rs1.getString("customercode"));
                customermasterpojoobj.setCustomerfirstname(rs1.getString("firstname"));
                customermasterpojoobj.setCustomerlastname(rs1.getString("lastname"));
                customermasterpojoobj.setMobileno(rs1.getString("mobileno"));
                customermasterpojoobj.setTelephoneno(rs1.getString("telephoneno"));

            }
            if (rs.next()) {

                customermasterpojoobj.setTitle(rs.getString("title"));
                customermasterpojoobj.setHouseno(rs.getString("address"));
                customermasterpojoobj.setStreet(rs.getString("street"));
                customermasterpojoobj.setArea(rs.getString("area"));
                customermasterpojoobj.setCity(rs.getString("city"));
                customermasterpojoobj.setPincode(rs.getString("pincode"));
                customermasterpojoobj.setState(rs.getString("state"));
                customermasterpojoobj.setCountry(rs.getString("country"));
                customermasterpojoobj.setOfficeno(rs.getString("officeno"));
            }

            return customermasterpojoobj;

        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            pstmt1 = null;
            rs1 = null;
            rs = null;
        }
    }

    public boolean checkCustomerInDatabase(Connection conn, String ccode) {
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select customercode from tbl_customermastermain where customercode=?");
            pstmt.setString(1, ccode);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return false;
        } finally {
            pstmt = null;
            rs = null;
        }

    }

    public int updateLastCustomerCodeInPosDocTable(Connection conn, String customercode) throws SQLException {
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement("update tbl_posdoclastnumbers set customercode ='" + customercode + "'");
            int res = pstmt.executeUpdate();
            return res;
        } catch (Exception e) {
            conn.rollback();
            return 0;
        } finally {
            pstmt = null;
        }

    }

    public String execute(DataObject dataObject, String updatetype) {
        CustomerMasterPOJO obj = null;
        ArrayList<FamilyDetailPOJO> list = null;
        CustomerMasterBean bean = null;
        PosConfigParamDO posConfigParamDO = new PosConfigParamDO();
        String val_timeout = "60000";
        MsdeConnection msdeConnection;
        Connection con = null;
        try {
            if (dataObject instanceof CustomerMasterBean) {
                bean = (CustomerMasterBean) dataObject;
                obj = bean.getCustomer();
                list = (ArrayList<FamilyDetailPOJO>) bean.getFamily();
                msdeConnection = new MsdeConnection();
                con = msdeConnection.createConnection();
                val_timeout = posConfigParamDO.getValForThePosConfigKey(con, "customer_timeout");
                System.out.println("val_timeout:" + val_timeout);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                msdeConnection = null;
                posConfigParamDO = null;
                con.close();
                con = null;
            } catch (SQLException ex) {

            }
        }
        try { // Call Web Service Operation
            in.co.titan.customermaster.MIOBASYNCustomerMasterService service = new in.co.titan.customermaster.MIOBASYNCustomerMasterService();
            // in.co.titan.customermaster.MIOBASYNCustomerMaster port = service.getMIOBASYNCustomerMasterPort();
            in.co.titan.customermaster.MIOBASYNCustomerMaster port = service.getHTTPSPort();// TODO initialize WS operation arguments here

            Map<String, Object> ctxt = ((BindingProvider) port).getRequestContext();
            //ctxt.put(JAXWSProperties.CONNECT_TIMEOUT, connectionTimeout);
            // ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT,60000); 
            //Code added on Nov 22nd 2010 for parametrizing the timeout to avoid resending of customer record to some extend
            Integer timoutvalue = Integer.parseInt(val_timeout);
            ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, timoutvalue);
            System.out.println("timeout value:::" + timoutvalue.toString());

            ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
            ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());
            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port() + "/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_CustomerMaster&version=3.0&Sender.Service=BS_TITAN_POS&Interface=http%3A%2F%2Ftitan.co.in%2FCustomerMaster%5EMI_OB_ASYN_CustomerMaster");
            in.co.titan.customermaster.DTCustomerMaster zcust = new in.co.titan.customermaster.DTCustomerMaster();
            // TODO process result here
            zcust.setSITESEARCH(obj.getStorecode());
            zcust.setCUSTOMERNO(obj.getCustomercode());
            zcust.setAGE(String.valueOf(obj.getAge()));
            zcust.setANNIVER(obj.getMarrigeanniversary());
            zcust.setAREA(obj.getArea());
            zcust.setCITY(obj.getCity());
            zcust.setCOMMENTS(obj.getComments());
            zcust.setCOMPANY(obj.getCompany());
            zcust.setCOUNTRY(obj.getCountry());
            zcust.setCUSTOMERCAT(obj.getCustomercategory());
            zcust.setCUSTOMERNO(obj.getCustomercode());
            zcust.setCUSTREFNO(obj.getCust_ref_no());
            zcust.setDATASHEETNO(obj.getDatasheetno());
            zcust.setDESIG(obj.getDesignation());
//            zcust.setLOYLITYNO(obj.getLoyalityno());
            zcust.setUniLoyaltyNo(obj.getLoyalityno());
            try {
                if (obj.getDob() != 0) {
                    java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(obj.getDob());
                    if (followDate != null) {
                        XMLCalendar xmlDate = new XMLCalendar(followDate);
                        if (xmlDate != null) {
                            zcust.setDOB(xmlDate);
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            zcust.setEDUCATION(obj.getEducation());
            zcust.setEMAIL(obj.getEmail());
            zcust.setFAVAIR(obj.getPreferredairline());
            zcust.setFAVBRAND(obj.getFavoritebrand());
            zcust.setFAVCAR(obj.getFavouritecar());
            zcust.setFAVHOLY(obj.getHolidayspot());
            zcust.setFAVPERSON(obj.getAdmiredperson());
            zcust.setFAVREST(obj.getRestaurant());
            zcust.setFAVSHOPPING(obj.getShoppingdestination());
            try {
                if (obj.getFaxno() != null) {
                    if (obj.getFaxno().length() > 0) {
                        String telestr[] = obj.getFaxno().split("-");
                        if (telestr.length == 1) {
                            zcust.setFAX(telestr[1]);
                        } else if (telestr.length == 2) {
                            zcust.setFAX(telestr[2]);
                        } else {
                            zcust.setFAX(telestr[1] + "-" + telestr[2]);
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //zcust.setFAX(obj.getFaxno());
            zcust.setFAXEXTN(obj.getFaxextn());
            zcust.setFAXPRI(obj.getPriority_faxno());
            zcust.setFIRSTNAME(obj.getCustomerfirstname());
            zcust.setFREE1(obj.getFreefield1());
            zcust.setFREE10(obj.getFreefield10());
            zcust.setFREE2(obj.getFreefield2());
            zcust.setFREE3(obj.getFreefield3());
            zcust.setFREE4(obj.getFreefield4());
            zcust.setFREE5(obj.getFreefield5());
            zcust.setFREE6(obj.getFreefield6());
            zcust.setFREE7(obj.getFreefield7());
            zcust.setFREE8(obj.getFreefield8());
            zcust.setFREE9(obj.getFreefield9());
            zcust.setGENDER(obj.getGender());
            zcust.setHOBBIES(obj.getHobbies());
            zcust.setHOUSENO(obj.getHouseno());
            zcust.setLASTNAME(obj.getCustomerlastname());
            //zcust.setMACCGROUP("ZEWR");

            zcust.setCREATNAME(obj.getCreatedBy());

            try {
                java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(obj.getCreatedDate());
                Calendar createdTime = ConvertDate.getSqlTimeFromString(obj.getCreatedTime());
                if (createdDate != null) {
                    XMLCalendar xmlDate = new XMLCalendar(createdDate);
                    if (createdTime != null) {
                        xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                        if (xmlDate != null) {
                            zcust.setCREATTIME(xmlDate);
                        }
                    }
                    if (xmlDate != null) {
                        zcust.setCREATEDATE(xmlDate);
                    }

                }
            } catch (Exception e) {

            }
            zcust.setCHANGNAME(obj.getModifiedBy());

            try {
                java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(obj.getModifiedDate());
                Calendar createdTime = ConvertDate.getSqlTimeFromString(obj.getModifiedTime());
                if (createdDate != null) {

                    XMLCalendar xmlDate = new XMLCalendar(createdDate);

                    if (createdTime != null) {
                        xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                        if (xmlDate != null) {
                            zcust.setCHANGTIME(xmlDate);
                        }
                    }
                    if (xmlDate != null) {
                        zcust.setCHANGDATE(xmlDate);
                    }

                }
            } catch (Exception e) {

            }
            zcust.setGJAHR(String.valueOf(obj.getFiscalYear()));
            zcust.setMAILPRI(obj.getPriority_email());
            String companyCode = null;
            try {
                MsdeConnection msdeconn1 = new MsdeConnection();
                Connection con1 = msdeconn1.createConnection();
                companyCode = new SiteMasterDO().getSiteCompanyCode(con1);
            } catch (Exception e) {

            }
            zcust.setMANDT("");
            zcust.setMARRIED(obj.getMariagestatus());
            if (Validations.isFieldNotEmpty(companyCode)) {
                zcust.setMCOMPCODE(companyCode);
            }

            //  zcust.setMCURRENCY("INR");

            /*    if (plantDetails != null) {
            if (plantDetails.getDistrChannel() != null) {
            zcust.setMDISCHAN(plantDetails.getDistrChannel());
            }
            }*/

 /*
            if (plantDetails != null) {
            if (plantDetails.getDivision() != null) {
            zcust.setMDIVISION(plantDetails.getDivision());
            }
            }*/
            //zcust.setMOBILENO(obj.getMobileno());
            try {
                if (obj.getMobileno() != null) {
                    if (obj.getMobileno().length() > 0) {
                        String mobstr[] = obj.getMobileno().split("-");
                        if (mobstr.length == 1) {
                            zcust.setMOBILENO(mobstr[0]);
                        } else if (mobstr.length == 2) {
                            zcust.setMOBILENO(mobstr[1]);
                        }

                    }
                }
            } catch (Exception e) {

            }
            zcust.setMOBPRI(obj.getPriority_mobileno());
            //  zcust.setMPRICEPROC("1");
            // zcust.setMRECONACC("152000");
            /*    if (plantDetails != null) {
            if (plantDetails.getSalesOrg() != null) {
            zcust.setMSALESORG(plantDetails.getSalesOrg());
            }
            }*/
            // zcust.setMSHIPCOND("01");
            zcust.setNOOFMEMBER(String.valueOf(obj.getNoofpeople()));
            /*       boolean flag = true;
            try {
            String occupationval = (String) occupationkeyvalues.get(obj.getOccuapation());
            if (occupationval.length() > 0) {
            zcust.setOCCUPATION(occupationval);
            flag = false;
            }
            } catch (NullPointerException nu) {
            }
            try {
            if (obj.getOccuapation().length() > 0 && flag) {
            zcust.setOCCUPATION(obj.getOccuapation());
            }
            } catch (Exception e) {
            }*/
            zcust.setOCCUPATION(obj.getOccuapation());
            zcust.setOFFADDAREA(obj.getOfficearea());
            zcust.setOFFADDBUILDNO(obj.getOfficebuildingno());
            zcust.setOFFADDCITY(obj.getOfficecity());
            zcust.setOFFADDCOUNT(obj.getOfficecountry());
            zcust.setOFFADDPIN(obj.getOfficepincode());
            zcust.setOFFADDSTATE(obj.getOfficestate());
            zcust.setOFFADDSTREET(obj.getOfficestreet());
            //zcust.setOFFICENO(obj.getOfficeno());
            if (obj.getOfficeno() != null) {
                if (obj.getOfficeno().length() > 0) {
                    String telestr[] = obj.getOfficeno().split("-");
                    if (telestr.length == 1) {
                        zcust.setOFFICENO(telestr[1]);
                    } else if (telestr.length == 2) {
                        zcust.setOFFICENO(telestr[2]);
                    } else {
                        zcust.setOFFICENO(telestr[1] + "-" + telestr[2]);
                    }

                }
            }
            zcust.setOFFPRI(obj.getPriority_officeno());

            zcust.setPINCODE(obj.getPincode());
            zcust.setREFFRIEND(obj.getReference_friends());
            zcust.setREFHOARD(obj.getReference_hoarding());
            zcust.setREFLET(obj.getReference_leaflet());
            zcust.setREFOTHER(obj.getReference_others());
            zcust.setREFPAPER(obj.getReference_newspaper());
            zcust.setREFTV(obj.getReference_tv());
            zcust.setRELATIONSHIP(obj.getRelationship());
            zcust.setSENDUPDATES(obj.getSendmeupdateson());
            zcust.setSTATE(obj.getState());
            zcust.setSTORECODE(obj.getStorecode());
            zcust.setSTREET(obj.getStreet());
            zcust.setTELEXTN(obj.getOfficenoextn());
            if (obj.getTelephoneno() != null) {
                if (obj.getTelephoneno().length() > 0) {
                    String telestr[] = obj.getTelephoneno().split("-");
                    if (telestr.length == 1) {
                        zcust.setTELNO(telestr[1]);
                    } else if (telestr.length == 2) {
                        zcust.setTELNO(telestr[2]);
                    } else {
                        zcust.setTELNO(telestr[1] + "-" + telestr[2]);
                    }

                }
            }
            //zcust.setTELNO(obj.getTelephoneno());
            // zcust.setTELNO("44444-4526253");
            zcust.setTELPRI(obj.getPriority_telephoneno());
            zcust.setTITLE(obj.getTitle());
            zcust.setUPDATEONOFFER(obj.getUpdateonoffer());
            zcust.setUPDATETYPE(updatetype);
            if (list != null) {
                Iterator itr = list.iterator();

                //    ZSDFMCUSTOMERCREATE.ICRM icrm = new ZSDFMCUSTOMERCREATE.ICRM();
                // ArrayList<in.co.titan.customermaster.DTCustomerMaster.CRMITEM> icrm = new ArrayList<CRMITEM>();
                while (itr.hasNext()) {
                    in.co.titan.customermaster.DTCustomerMaster.CRMITEM custrel = new in.co.titan.customermaster.DTCustomerMaster.CRMITEM();
                    FamilyDetailPOJO fpojo = (FamilyDetailPOJO) itr.next();
                    custrel.setCUSTOMERNO(fpojo.getCustomercode());
                    try {
                        if (fpojo.getDob() != 0) {
                            java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(fpojo.getDob());
                            if (followDate != null) {
                                XMLCalendar xmlDate = new XMLCalendar(followDate);
                                if (xmlDate != null) {
                                    custrel.setDOB(xmlDate);
                                }

                            }
                        }
                    } catch (Exception e) {

                    }

                    //custrel.setDOB("19880623");
                    custrel.setMANDT("");
                    custrel.setNAME(fpojo.getName());
                    custrel.setRELATIONSHIP(fpojo.getRelationship());
                    try {
                        custrel.setSLNO(String.valueOf(fpojo.getFamilymemberno()));
                    } catch (Exception e) {
                    }
                    custrel.setTELNO(fpojo.getContactno());
                    custrel.setWEARINGSPECS(fpojo.getWearingspetacles());
                    zcust.getCRMITEM().add(custrel);

                }
            }
            //Start - GSTN NO. Code added by Thangaraj 25.05.2017
            if (Validations.isFieldNotEmpty(obj.getGstn_no())) {
                zcust.setGSTNNO(obj.getGstn_no());
            }
            //End - GSTN NO. Code added by Thangaraj 25.05.2017
            if (Validations.isFieldNotEmpty(obj.getTabid())) {//Added by Balachander V on 5.9.2019 to send SFDC ID
                zcust.setTabId(obj.getTabid());
            }
            if (Validations.isFieldNotEmpty(obj.getEnrollmentOtp())) {//Added by Balachander V on 5.7.2022 to send Enrollment OTP to SAP
                zcust.setEnrolmentOTP(obj.getEnrollmentOtp());
            }
            System.out.println("Customer Master StoreCode  " + zcust.getSTORECODE() + "----" + zcust.getMCOMPCODE());
            //Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available   setMCOMPCODE
            if (zcust.getSTORECODE() != null && zcust.getSTORECODE().trim().length() > 0 && zcust.getMCOMPCODE() != null && zcust.getMCOMPCODE().trim().length() > 0) {
                port.miOBASYNCustomerMaster(zcust);

                Map responseContext = ((BindingProvider) port).getResponseContext();
                Integer responseCode
                        = (Integer) responseContext.get(MessageContext.HTTP_RESPONSE_CODE);
                System.out.println("Customer response --------> " + responseCode);
                System.out.println("Customer code --------> " + zcust.getCUSTOMERNO());
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
            /*port.miOBASYNCustomerMaster(zcust);
        Map responseContext = ((BindingProvider) port).getResponseContext();
        Integer responseCode =
        (Integer) responseContext.get(MessageContext.HTTP_RESPONSE_CODE);
        System.out.println("Customer response --------> " + responseCode);
        System.out.println("Customer code --------> " + zcust.getCUSTOMERNO());
        if (responseCode.intValue() == 200) {
        return "true";
        } else {
        return "false";
        }*/
        } catch (Exception ex) {
            ex.printStackTrace();
            // TODO handle custom exceptions here
            return "false";
        }

        // Call Web Service Operation
        //MsdeConnection msdeconn = new MsdeConnection();
        //Connection con = msdeconn.createConnection();
        //PopulateData statedata = new PopulateData();
    }
}
