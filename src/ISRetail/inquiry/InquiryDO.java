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
 * This class file is used as a Data Object between Inquiry and Database
 * This is used for transaction of Inquiry Data from and to the database
 * 
 */
package ISRetail.inquiry;

import ISRetail.Helpers.ConvertDate;
import ISRetail.Helpers.XMLCalendar;

import ISRetail.Webservices.DataObject;

import ISRetail.Webservices.Webservice;

import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;

import ISRetail.utility.validations.GenerateNextPosDocNumber;
import ISRetail.utility.validations.Validations;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.math.BigInteger;
import java.sql.*;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.SOAPFaultException;

public class InquiryDO implements Webservice {

    /*
    public int saveInquiry(InquiryPOJO inquirypojoobj, Connection conn) throws SQLException {
    String insertstr = "insert into tbl_inquiry values (?,?,?,?,?,?,?,?,?,?,?)";
    PreparedStatement pstmt = conn.prepareStatement(insertstr);
    pstmt.setString(1, inquirypojoobj.getInquiryno());
    pstmt.setString(2, inquirypojoobj.getCustomercode());
    pstmt.setString(3, inquirypojoobj.getStorecode());
    pstmt.setInt(4, inquirypojoobj.getFiscalyear());
    pstmt.setString(5, inquirypojoobj.getDatasheetno());
    pstmt.setString(6, inquirypojoobj.getCreatedby());
    pstmt.setInt(7, inquirypojoobj.getCreateddate());
    pstmt.setString(8, inquirypojoobj.getCreatedtime());
    pstmt.setString(9, inquirypojoobj.getModifiedby());
    pstmt.setInt(10, inquirypojoobj.getModifieddate());
    pstmt.setString(11, inquirypojoobj.getModifiedtime());
    int res = pstmt.executeUpdate();
    return res;
    }
     */
    /*
    public int editInquiry(InquiryPOJO inquirypojoobj, Connection conn) throws SQLException {
    String insertstr = "update tbl_inquiry set modifiedby=?,modifieddate=?,modifiedtime=?  where inquiryno=?";
    PreparedStatement pstmt = conn.prepareStatement(insertstr);
    pstmt.setString(1, inquirypojoobj.getModifiedby());
    pstmt.setInt(2, inquirypojoobj.getModifieddate());
    pstmt.setString(3, inquirypojoobj.getModifiedtime());
    pstmt.setString(4, inquirypojoobj.getInquiryno());
    int res = pstmt.executeUpdate();
    return res;
    }
     */

    /*
    public String generateInquiryNo(Connection con, String firstFixedString) {
    String nextInqNo = null;
    try {
    String prevInqNo = null, prevInqLastNoPart = null;
    Statement pstmt = con.createStatement();
    ResultSet rs = pstmt.executeQuery("select inquiryno from tbl_posdoclastnumbers");
    if (rs.next()) {
    prevInqNo = rs.getString(1);
    }
    if (Validations.isFieldNotEmpty(prevInqNo)) {//if lastNo exists
    if (prevInqNo.length() > firstFixedString.length()) {
    prevInqLastNoPart = prevInqNo.substring(firstFixedString.length());
    prevInqNo = firstFixedString + prevInqLastNoPart;
    }
    } else {
    String inqNoLastPart = "";
    for (int i = firstFixedString.length(); i < 10; i++) {
    inqNoLastPart = inqNoLastPart + "0";
    }
    prevInqNo = firstFixedString + inqNoLastPart;
    }
    nextInqNo = new GenerateNextPosDocNumber().generatenumber(prevInqNo, firstFixedString.length());
    } catch (Exception e) {
    e.printStackTrace();
    }
    return nextInqNo;
    }
     */

//    public String getLastInquiryNo(Connection con) {
//        try {
//           Statement pstmt = con.createStatement();
//            String searchstatement = "select inquiryno from tbl_posdoclastnumbers";
//            ResultSet rs = pstmt.executeQuery(searchstatement);
//            rs.next();
//            return rs.getString(1);
//
//        } catch (SQLException sQLException) {
//            sQLException.printStackTrace();
//            return null;
//        }
//
//    }
    /*
    public int updateLastInquiryNo(Connection con, String lastDocNo) throws SQLException {
    String query = "update tbl_posdoclastnumbers set inquiryno =? ";
    PreparedStatement pstmt = con.prepareStatement(query);
    pstmt.setString(1, lastDocNo);
    int res = pstmt.executeUpdate();
    return res;
    }*/
    public InquiryPOJO getInquiryByInqNo(Connection conn, String InqNo) {
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select * from tbl_inquiry where inquiryno=?");
            pstmt.setString(1, InqNo);
            rs = pstmt.executeQuery();
            InquiryPOJO inquirypojoobj = new InquiryPOJO();
            if (rs.next()) {
                inquirypojoobj.setCustomercode(rs.getString("customercode"));
                inquirypojoobj.setDatasheetno(rs.getString("datasheetno"));
                inquirypojoobj.setInquiryno(InqNo);
                inquirypojoobj.setFiscalyear(rs.getInt("fiscalyear"));
                inquirypojoobj.setCreatedby(rs.getString("createdby"));
                inquirypojoobj.setCreateddate(rs.getInt("createddate"));
                inquirypojoobj.setCreatedtime(rs.getString("createdtime"));
                inquirypojoobj.setModifiedby(rs.getString("modifiedby"));
                inquirypojoobj.setModifieddate(rs.getInt("modifieddate"));
                inquirypojoobj.setModifiedtime(rs.getString("modifiedtime"));


                inquirypojoobj.setStorecode(rs.getString("storecode"));
                return inquirypojoobj;
            } else {
                return null;
            }



        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }

    }

    public String getCustomerCodeFromInquiry(String inquiryNo, Connection conn) {
        String custCode = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            String query = "select customercode from tbl_inquiry where inquiryno=?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, inquiryNo);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                custCode = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return custCode;
    }

    public int archiveAllInquiryTables(Connection con, int fiscalYear) throws SQLException {
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
            int res = characteristicDO.archiveAllCharacteristicsForFiscalYear(fiscalYear, con, "INQ");
            recDeleted = recDeleted + res;
            res = clinicalHistoryDO.archiveAllClinicalHistoryForFiscalYear(fiscalYear, con, "INQ");
            recDeleted = recDeleted + res;
            res = prescriptionDO.archiveAllPrescriptionForFiscalYear(fiscalYear, con, "INQ");
            recDeleted = recDeleted + res;
            res = contactLensDO.archiveAllContactLensForFiscalYear(fiscalYear, con, "INQ");
            recDeleted = recDeleted + res;
            query = "delete from tbl_inquiry where fisaclyear= ?";
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
        String updatedInISR = "F";


        try { // Call Web Service Operation

            if (obj instanceof InquiryMasterBean) {
                InquiryMasterBean masterBean = (InquiryMasterBean) obj;

                in.co.titan.inquiry.MIOBASYNInquiryService service = new in.co.titan.inquiry.MIOBASYNInquiryService();
                in.co.titan.inquiry.MIOBASYNInquiry port = service.getMIOBASYNInquiryPort();
                Map<String, Object> ctxt = ((BindingProvider) port).getRequestContext();
                //ctxt.put(JAXWSProperties.CONNECT_TIMEOUT, connectionTimeout);
                ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, 60000);
                in.co.titan.inquiry.DTInquiry mtInquiry = new in.co.titan.inquiry.DTInquiry();

                ContactLensPOJO clensPojo = masterBean.getClensPojo();
                ClinicalHistoryPOJO cliniPojo = masterBean.getCliniPojo();
                PrescriptionPOJO specPojo = masterBean.getPresPojo();
                String scenarioType = null;

                if (cliniPojo != null && specPojo != null && clensPojo != null) {
                    scenarioType = "C3";
                } else if (cliniPojo != null && specPojo != null) {
                    scenarioType = "CP";
                } else if (cliniPojo != null && clensPojo != null) {
                    scenarioType = "CC";
                } else if (specPojo != null && clensPojo != null) {
                    scenarioType = "PC";
                } else if (cliniPojo != null) {
                    scenarioType = "CH";
                } else if (specPojo != null) {
                    scenarioType = "PR";
                } else if (clensPojo != null) {
                    scenarioType = "CL";
                } else {
                    scenarioType = " ";
                }

                MsdeConnection msdeconn = new MsdeConnection();
                Connection con = msdeconn.createConnection();
                //   PlantDetails plantDetails = new PlantDetails(con);
                InquiryPOJO inqPojo = masterBean.getInqPojo();
                if (inqPojo != null) {
                    mtInquiry.setInquiryNO(inqPojo.getInquiryno());
                    // mtClinicalHistoryOUT.setFYear(String.valueOf(inqPojo.getFiscalyear()));

                    BigInteger fiscalYear = new BigInteger(Integer.toString(inqPojo.getFiscalyear()));
                    mtInquiry.setFYear(fiscalYear);
                    mtInquiry.setStoreCode(inqPojo.getStorecode());
                    mtInquiry.setCreatedBy(inqPojo.getCreatedby());
                    mtInquiry.setSITESEARCH(inqPojo.getStorecode());
                    try {
                        java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(inqPojo.getCreateddate());
                        Calendar createdTime = ConvertDate.getSqlTimeFromString(inqPojo.getCreatedtime());
                        if (createdDate != null) {
                            XMLCalendar xmlDate = new XMLCalendar(createdDate);
                            if (xmlDate != null) {
                                mtInquiry.setDocumentDate(xmlDate);
                                if (createdTime != null) {
                                    xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                                    if (xmlDate != null) {
                                        mtInquiry.setCreatedTime(xmlDate);
                                    }
                                }
                                if (xmlDate != null) {
                                    mtInquiry.setCreatedDate(xmlDate);
                                }
                            }
                        }
                    } catch (Exception e) {

                    }
                    if (!Validations.isFieldNotEmpty(scenarioType)) {
                        scenarioType = " ";
                    }
                    if (cliniPojo != null && (scenarioType.trim().equals("C3") || scenarioType.trim().equals("CH") || scenarioType.trim().equals("CP") || scenarioType.trim().equals("CC"))) {

                        mtInquiry.setCustomerCode(cliniPojo.getCustomercode());
                        mtInquiry.setDataSheetNO(cliniPojo.getDatasheetno());
                        if (Validations.isFieldNotEmpty(cliniPojo.getExaminedby())) {
                            mtInquiry.setCHExaminedBY(cliniPojo.getExaminedby());
                        }
                        if (Validations.isFieldNotEmpty(cliniPojo.getPasthistory())) {
                            mtInquiry.setCHPastHistory(cliniPojo.getPasthistory());
                        }
                        if (Validations.isFieldNotEmpty(cliniPojo.getComments())) {
                            mtInquiry.setCHComments(cliniPojo.getComments());
                        }
                        if (Validations.isFieldNotEmpty(cliniPojo.getSlitlampexamination())) {
                            mtInquiry.setCHSLExamination(cliniPojo.getSlitlampexamination());
                        }
                    }
                    if (specPojo != null && (scenarioType.trim().equals("C3") || scenarioType.trim().equals("PR") || scenarioType.trim().equals("CP") || scenarioType.trim().equals("PC"))) {
                        mtInquiry.setCustomerCode(specPojo.getCustomercode());
                        mtInquiry.setDataSheetNO(specPojo.getDatasheetno());
                        mtInquiry.setPRExaminedBY(specPojo.getExaminedby());
                        if (Validations.isFieldNotEmpty(specPojo.getDoctor_name())) {
                            mtInquiry.setPRDocName(specPojo.getDoctor_name());
                        }
                        if (Validations.isFieldNotEmpty(specPojo.getDoctor_contactno())) {
                            mtInquiry.setPRDocCNO(specPojo.getDoctor_contactno());
                        }
                        if (Validations.isFieldNotEmpty(specPojo.getDoctor_area())) {
                            mtInquiry.setPRDocArea(specPojo.getDoctor_area());
                        }
                        if (Validations.isFieldNotEmpty(specPojo.getDoctor_city())) {
                            mtInquiry.setPRDocCity(specPojo.getDoctor_city());
                        }
                        if (Validations.isFieldNotEmpty(specPojo.getOthername())) {
                            mtInquiry.setPRExamBYOthers(specPojo.getOthername());
                        }
                        if (Validations.isFieldNotEmpty(specPojo.getFollowup())) {
                            mtInquiry.setPRFollowUP(specPojo.getFollowup());
                        }
                        try {
                            if (specPojo.getFollowupdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(specPojo.getFollowupdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        mtInquiry.setPRFollowDT(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Spec Rx FollowupdateNot Set" + e);
                        }
                        if (Validations.isFieldNotEmpty(specPojo.getReferred_to())) {
                            mtInquiry.setPRRefered(specPojo.getReferred_to());
                        }
                        if (Validations.isFieldNotEmpty(specPojo.getComments())) {
                            mtInquiry.setPRComments(specPojo.getComments());
                        }

                        if (Validations.isFieldNotEmpty(specPojo.getRight_Sale())) {
                            mtInquiry.setPRPresSale(specPojo.getRight_Sale());
                        } else if (Validations.isFieldNotEmpty(specPojo.getLeft_Sale())) {
                            mtInquiry.setPRPresSale(specPojo.getLeft_Sale());
                        }
                        if (Validations.isFieldNotEmpty(specPojo.getRight_Sale())) {
                            mtInquiry.setPRPresSale(specPojo.getRight_Sale());
                            if (specPojo.getRight_Sale().trim().equalsIgnoreCase("BP")) {
                                if (Validations.isFieldNotEmpty(specPojo.getRight_B_P_Dist()) && Validations.isFieldNotEmpty(specPojo.getRight_B_P_Inter())) {
                                    mtInquiry.setPRPresBP("DI");
                                }//21
                                else if (Validations.isFieldNotEmpty(specPojo.getRight_B_P_Dist()) && Validations.isFieldNotEmpty(specPojo.getRight_B_P_Near())) {
                                    mtInquiry.setPRPresBP("DN");
                                }//21
                                else if (Validations.isFieldNotEmpty(specPojo.getRight_B_P_Inter()) && Validations.isFieldNotEmpty(specPojo.getRight_B_P_Near())) {
                                    mtInquiry.setPRPresBP("IN");
                                }
                            }
                        } else if (Validations.isFieldNotEmpty(specPojo.getLeft_Sale())) {
                            mtInquiry.setPRPresSale(specPojo.getLeft_Sale());
                            if (specPojo.getLeft_Sale().trim().equalsIgnoreCase("BP")) {
                                if (Validations.isFieldNotEmpty(specPojo.getLeft_B_P_Dist()) && Validations.isFieldNotEmpty(specPojo.getLeft_B_P_Inter())) {
                                    mtInquiry.setPRPresBP("DI");
                                }//21
                                else if (Validations.isFieldNotEmpty(specPojo.getLeft_B_P_Dist()) && Validations.isFieldNotEmpty(specPojo.getLeft_B_P_Near())) {
                                    mtInquiry.setPRPresBP("DN");
                                }//21
                                else if (Validations.isFieldNotEmpty(specPojo.getLeft_B_P_Inter()) && Validations.isFieldNotEmpty(specPojo.getLeft_B_P_Near())) {
                                    mtInquiry.setPRPresBP("IN");
                                }
                            }
                        }
                    }
                    if (clensPojo != null && (scenarioType.trim().equals("C3") || scenarioType.trim().equals("CL") || scenarioType.trim().equals("CC") || scenarioType.trim().equals("PC"))) {
                        mtInquiry.setCustomerCode(clensPojo.getCustomercode());
                        mtInquiry.setDataSheetNO(clensPojo.getDatasheetno());
                        mtInquiry.setCLExaminedBY(clensPojo.getExaminedby());
                        if (Validations.isFieldNotEmpty(clensPojo.getDoctor_name())) {
                            mtInquiry.setCLDocName(clensPojo.getDoctor_name());
                        }
                        if (Validations.isFieldNotEmpty(clensPojo.getDoctor_contactno())) {
                            mtInquiry.setCLDocCNO(clensPojo.getDoctor_contactno());
                        }
                        if (Validations.isFieldNotEmpty(clensPojo.getDoctor_area())) {
                            mtInquiry.setCLDocArea(clensPojo.getDoctor_area());
                        }
                        if (Validations.isFieldNotEmpty(clensPojo.getDoctor_city())) {
                            mtInquiry.setCLDocCity(clensPojo.getDoctor_city());
                        }

                        if (Validations.isFieldNotEmpty(clensPojo.getOthername())) {
                            mtInquiry.setCLExamOthers(clensPojo.getOthername());
                        }
                        if (Validations.isFieldNotEmpty(clensPojo.getCl_fp_specialcomments())) {
                            mtInquiry.setCLComments(clensPojo.getCl_fp_specialcomments());
                        }
                        if (Validations.isFieldNotEmpty(clensPojo.getFollowup())) {
                            mtInquiry.setCLFollowUP(clensPojo.getFollowup());
                        }
                        try {
                            if (clensPojo.getFollowupdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(clensPojo.getFollowupdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        mtInquiry.setCLFollowDT(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("C Lens FollowupdateNot Set" + e);
                        }

                        if (Validations.isFieldNotEmpty(clensPojo.getOor_slitlamp_examination())) {
                            mtInquiry.setCLSLExamination(clensPojo.getOor_slitlamp_examination());
                        }
                        if (Validations.isFieldNotEmpty(clensPojo.getRight_trails())) {
                            mtInquiry.setCLRightTrial(clensPojo.getRight_trails());
                        }
                        if (Validations.isFieldNotEmpty(clensPojo.getLeft_trails())) {
                            mtInquiry.setCLLeftTrial(clensPojo.getLeft_trails());
                        }

                        if (Validations.isFieldNotEmpty(clensPojo.getCaresys_aquasoft_mps())) {
                            mtInquiry.setCLAquaSoftMPS(clensPojo.getCaresys_aquasoft_mps());
                        }
                        if (Validations.isFieldNotEmpty(clensPojo.getCaresys_complete_mps())) {
                            mtInquiry.setCLCompleteMPS(clensPojo.getCaresys_complete_mps());
                        }
                        if (Validations.isFieldNotEmpty(clensPojo.getCaresys_gp_solution())) {
                            mtInquiry.setCLGPSolutions(clensPojo.getCaresys_gp_solution());
                        }
                        if (Validations.isFieldNotEmpty(clensPojo.getCaresys_lubricant_mps())) {
                            mtInquiry.setCLLubricant(clensPojo.getCaresys_lubricant_mps());
                        }
                        if (Validations.isFieldNotEmpty(clensPojo.getCaresys_moisture_mps())) {
                            mtInquiry.setCLMoistureLOC(clensPojo.getCaresys_moisture_mps());
                        }
                        if (Validations.isFieldNotEmpty(clensPojo.getCaresys_renu_mps())) {
                            mtInquiry.setCLReNuMPS(clensPojo.getCaresys_renu_mps());
                        }
                        if (Validations.isFieldNotEmpty(clensPojo.getCaresys_rewetting_mps())) {
                            mtInquiry.setCLRewetting(clensPojo.getCaresys_rewetting_mps());
                        }
                        if (Validations.isFieldNotEmpty(clensPojo.getCaresys_silkens_mps())) {
                            mtInquiry.setCLSilkensMPS(clensPojo.getCaresys_silkens_mps());
                        }
                        if (Validations.isFieldNotEmpty(clensPojo.getCaresys_solo_mps())) {
                            mtInquiry.setCLSoloMPS(clensPojo.getCaresys_solo_mps());
                        }


                    }

                    //  if (plantDetails != null) {
                    //  mtInquiry.setSalesOrg(plantDetails.getSalesOrg());
                    // mtInquiry.setDistributionChannel(plantDetails.getDistrChannel());
                    // mtInquiry.setDivision(plantDetails.getDivision());
                    // mtInquiry.setDocumentType("ZIN");
                    // }

                    String companyCode = new SiteMasterDO().getSiteCompanyCode(con);
                    mtInquiry.setCompanyCode(companyCode);
                    mtInquiry.setParameterID("ENQUE");
                    mtInquiry.setFlagCat(scenarioType);
                    mtInquiry.setFlagMode(updateType);

                    /**********RIGHT EYE**********************************************************************/
                    //  in.co.titan.inquiry.DTInquiry  ightEye = new in.co.titan.inquiry.DTInquiry().getCHRightEYE();
                    /**********LEFT EYE**********************************************************************/
                    // in.co.titan.inquiry.DTInquiry.CHLeftEYE leftEye = new in.co.titan.inquiry.DTInquiry.CHLeftEYE();
                    /************CH RIGHT*************/
                    if (cliniPojo != null && (scenarioType.trim().equals("C3") || scenarioType.trim().equals("CH") || scenarioType.trim().equals("CP") || scenarioType.trim().equals("CC"))) {

                        Map CH_RightEyes = Inquiry_POS.getAndsetCHCharacteristics(cliniPojo, "R");
                        if (CH_RightEyes != null) {
                            if (CH_RightEyes.keySet() != null) {
                                in.co.titan.inquiry.DTInquiry.CHRightEYE item = null;
                                String desc = null, val = null;
                                Iterator chRight = CH_RightEyes.keySet().iterator();
                                if (chRight != null) {
                                    while (chRight.hasNext()) {
                                        try {
                                            desc = String.valueOf(chRight.next());
                                            val = String.valueOf(CH_RightEyes.get(desc));
                                            if (Validations.isFieldNotEmpty(val)) {

                                                item = new in.co.titan.inquiry.DTInquiry.CHRightEYE();
                                                item.setDescription(desc);
                                                item.setValue(val);
                                                mtInquiry.getCHRightEYE().add(item);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }

                        /************CH LEFT*************/
                        Map CH_LeftEyes = Inquiry_POS.getAndsetCHCharacteristics(cliniPojo, "L");
                        if (CH_LeftEyes != null) {
                            if (CH_LeftEyes.keySet() != null) {
                                in.co.titan.inquiry.DTInquiry.CHLeftEYE item = null;
                                String desc = null, val = null;
                                Iterator chLeft = CH_LeftEyes.keySet().iterator();
                                if (chLeft != null) {
                                    while (chLeft.hasNext()) {
                                        try {
                                            desc = String.valueOf(chLeft.next());
                                            val = String.valueOf(CH_LeftEyes.get(desc));
                                            if (Validations.isFieldNotEmpty(val)) {

                                                item = new in.co.titan.inquiry.DTInquiry.CHLeftEYE();
                                                item.setDescription(desc);
                                                item.setValue(val);
                                                mtInquiry.getCHLeftEYE().add(item);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }


                    if (specPojo != null && (scenarioType.trim().equals("C3") || scenarioType.trim().equals("PR") || scenarioType.trim().equals("CP") || scenarioType.trim().equals("PC"))) {

                        /************PR RIGHT*************/
                        Map PR_RighttEyes = Inquiry_POS.getAndsetPRCharacteristics(specPojo, null, "R");
                        if (PR_RighttEyes != null) {
                            if (PR_RighttEyes.keySet() != null) {
                                in.co.titan.inquiry.DTInquiry.PRRightEYE item = null;
                                String desc = null, val = null;
                                Iterator prRight = PR_RighttEyes.keySet().iterator();
                                if (prRight != null) {
                                    while (prRight.hasNext()) {
                                        try {
                                            desc = String.valueOf(prRight.next());
                                            val = String.valueOf(PR_RighttEyes.get(desc));
                                            if (Validations.isFieldNotEmpty(val)) {

                                                item = new in.co.titan.inquiry.DTInquiry.PRRightEYE();
                                                item.setDescription(desc);
                                                item.setValue(val);
                                                mtInquiry.getPRRightEYE().add(item);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }


                        /************PR LEFT*************/
                        Map PR_LeftEyes = Inquiry_POS.getAndsetPRCharacteristics(specPojo, null, "L");
                        if (PR_LeftEyes != null) {
                            if (PR_LeftEyes.keySet() != null) {
                                in.co.titan.inquiry.DTInquiry.PRLeftEYE item = null;
                                String desc = null, val = null;
                                Iterator prLeft = PR_LeftEyes.keySet().iterator();
                                if (prLeft != null) {
                                    while (prLeft.hasNext()) {
                                        try {
                                            desc = String.valueOf(prLeft.next());
                                            val = String.valueOf(PR_LeftEyes.get(desc));
                                            if (Validations.isFieldNotEmpty(val)) {

                                                item = new in.co.titan.inquiry.DTInquiry.PRLeftEYE();
                                                item.setDescription(desc);
                                                item.setValue(val);
                                                mtInquiry.getPRLeftEYE().add(item);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }



                    if (clensPojo != null && (scenarioType.trim().equals("C3") || scenarioType.trim().equals("CL") || scenarioType.trim().equals("CC") || scenarioType.trim().equals("PC"))) {

                        /*********CL RIGHT *****************/
                        Map CL_RightEyes = Inquiry_POS.getAndsetCLCharacteristics(clensPojo, "R");
                        if (CL_RightEyes != null) {
                            if (CL_RightEyes.keySet() != null) {
                                in.co.titan.inquiry.DTInquiry.CLRightEYE item = null;
                                String desc = null, val = null;
                                Iterator clRight = CL_RightEyes.keySet().iterator();
                                if (clRight != null) {
                                    while (clRight.hasNext()) {
                                        try {
                                            desc = String.valueOf(clRight.next());
                                            val = String.valueOf(CL_RightEyes.get(desc));
                                            if (Validations.isFieldNotEmpty(val)) {

                                                item = new in.co.titan.inquiry.DTInquiry.CLRightEYE();
                                                item.setDescription(desc);
                                                item.setValue(val);
                                                mtInquiry.getCLRightEYE().add(item);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }

                        /************CL lEFT*************/
                        Map CL_LeftEyes = Inquiry_POS.getAndsetCLCharacteristics(clensPojo, "L");
                        if (CL_LeftEyes != null) {
                            if (CL_LeftEyes.keySet() != null) {
                                in.co.titan.inquiry.DTInquiry.CLLeftEYE item = null;
                                String desc = null, val = null;
                                Iterator clLeft = CL_LeftEyes.keySet().iterator();
                                if (clLeft != null) {
                                    while (clLeft.hasNext()) {
                                        try {
                                            desc = String.valueOf(clLeft.next());
                                            val = String.valueOf(CL_LeftEyes.get(desc));
                                            if (Validations.isFieldNotEmpty(val)) {
                                                item = new in.co.titan.inquiry.DTInquiry.CLLeftEYE();
                                                item.setDescription(desc);
                                                item.setValue(val);
                                                mtInquiry.getCLLeftEYE().add(item);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                    System.out.println("inquiry Store code  "+mtInquiry.getStoreCode());
                    if(mtInquiry.getStoreCode()!=null && mtInquiry.getStoreCode().trim().length()>0){
                        ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
                        ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());
                        ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port()+"/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_Inquiry&version=3.0&Sender.Service=x&Interface=x%5Ex");

                        // ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "test_enteg");
                        // ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "enteg321");

                        port.miOBASYNInquiry(mtInquiry);
                        //   updatedInISR = "T";
                        Map responseContext = ((BindingProvider) port).getResponseContext();
                        Integer responseCode = (Integer) responseContext.get(MessageContext.HTTP_RESPONSE_CODE);
                        if (responseCode.intValue() == 200) {
                            updatedInISR = "T";
                        } else {
                            updatedInISR = "F";
                        }
                    }else{
                           updatedInISR = "F";
                    }
                    //End of Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                    //Code commented by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                    /*((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
                    ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());

                    // ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "test_enteg");
                    // ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "enteg321");

                    port.miOBASYNInquiry(mtInquiry);
                    //   updatedInISR = "T";
                    Map responseContext = ((BindingProvider) port).getResponseContext();
                    Integer responseCode = (Integer) responseContext.get(MessageContext.HTTP_RESPONSE_CODE);
                    if (responseCode.intValue() == 200) {
                        updatedInISR = "T";
                    } else {
                        updatedInISR = "F";
                    }*/
                }

            }
        } catch (SOAPFaultException ex) {
            // TODO handle custom exceptions here
            // SOAPFault faults=ex.getFault();
            // Iterator itr=faults.getDetail().getDetailEntries();
            // String faultCode=faults.getFaultCode();
            //  String faultString=faults.getFaultString();
            updatedInISR = "F";
            ex.printStackTrace();
        } catch (Exception ex) {
            // TODO handle custom exceptions here
            ex.printStackTrace();
            updatedInISR = "F";
        }
        // TODO  
        return updatedInISR;
    }
}
