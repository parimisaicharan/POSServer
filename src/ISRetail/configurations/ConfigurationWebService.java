/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.configurations;

import ISRetail.Helpers.ConvertDate;
import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.serverconsole.PosConfigParamDO;
import ISRetail.serverconsole.ServerConsole;
import ISRetail.utility.validations.Validations;
import com.sap.document.sap.rfc.functions.ZSDTRPOSPARAM;
import com.sap.document.sap.rfc.functions.ZSDVWCONDMAP;
import com.sap.document.sap.rfc.functions.ZSTSDBRANDCOLOR;
import com.sap.document.sap.rfc.functions.ZSTSDBRANDMASTER;
import com.sap.document.sap.rfc.functions.ZSTSDCHARVALUES;
import com.sap.document.sap.rfc.functions.ZSTSDCITYMASTER;
import com.sap.document.sap.rfc.functions.ZSTSDCOUNTRYMASTER;
import com.sap.document.sap.rfc.functions.ZSTSDCUSDROPVALS;
import com.sap.document.sap.rfc.functions.ZSTSDSTATEMASTER;
import com.sap.document.sap.rfc.functions.T001W;
import com.sap.document.sap.rfc.functions.ZSTSDCLATTRIBUTESBASECUR;
import com.sap.document.sap.rfc.functions.ZSTSDCLATTRIBUTESPACK;
import com.sap.document.sap.rfc.functions.ZSTSDCLATTRIBUTESPRODTYPE;
import com.sap.document.sap.rfc.functions.ZSTSDCLATTRIBUTESPRODUCT;
import com.sap.document.sap.rfc.functions.ZSTSDDELIVDATEARTICLE;
import com.sap.document.sap.rfc.functions.ZSTSDDELIVDATEMERCHCAT;
import com.sap.document.sap.rfc.functions.ZSTSDDOCTORDETAILS;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JTextArea;
import javax.xml.ws.BindingProvider;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public class ConfigurationWebService {

    boolean calledParameterTable = false;
    JTextArea statusArea;

    public ConfigurationWebService() {
    }

    public ConfigurationWebService(ConfigurationDetails configurationDetails, com.sap.document.sap.rfc.functions.ZSDFMGETDROPDOWNSResponse result, JTextArea statusArea) throws Exception {
        try {
            this.statusArea = statusArea;
            MsdeConnection msde = new MsdeConnection();
            Connection con = msde.createConnection();
            con.setAutoCommit(false);
            try {
                statusArea.append("\nDownloading data !!!");
                if (configurationDetails.isIV_FLAG_CUSTOMERCATEGORY()) {
                    deleteCustCatValsFromParameterTable(con);
                }
                if (configurationDetails.isIV_FLAG_CURRENCYTYPE()) {
                    deleteCurrTypeValsFromParameterTable(con);
                }
                if (configurationDetails.isIV_FLAG_DESIGNATION()) {
                    deleteDesignationValsFromParameterTable(con);
                }
                if (configurationDetails.isIV_FLAG_EDUCATION()) {
                    deleteEducationValsFromParameterTable(con);
                }
                if (configurationDetails.isIV_FLAG_MARTIALSTATUS()) {
                    deleteMaritalStatusValsFromParameterTable(con);
                }
                if (configurationDetails.isIV_FLAG_RELATIONSHIP()) {
                    deleteRelationshipValsFromParameterTable(con);
                }
                if (configurationDetails.isIV_FLAG_RETREASON()) {
                    deleteReturnReasonValsFromParameterTable(con);
                }
                if (configurationDetails.isIV_FLAG_TITLE()) {
                    deleteTitleValsFromParameterTable(con);
                }
                if (configurationDetails.isIV_FLAG_UPDATIONMODE()) {
                    deleteUpdateModeValsFromParameterTable(con);
                }
                if (configurationDetails.isIV_FLAG_OCCUPATION()) {
                    deleteOccupationValsFromParameterTable(con);
                }
                if (configurationDetails.isIV_FLAG_PAYMENTMODE()) {
                    deletePaymentmodesValsFromParameterTable(con);
                }
                if (configurationDetails.isIV_FLAG_FOLLOWUP()) {
                    deleteFollowupValsFromParameterTable(con);
                }

                if (configurationDetails.isIV_FLAG_CARDTYPES()) {
                    deleteCardTypesValsFromParameterTable(con);
                }
                if (configurationDetails.isIV_FLAG_COMMPRIORITY()) {
                    deleteCompPriorityValsFromParameterTable(con);
                }
                if (configurationDetails.isIV_FLAG_ADVCANRES()) {
                    deleteAdvCanRreasonsFromParameterTable(con);
                }
                if (configurationDetails.isIV_FLAG_CANCELREASON()) {
                    deleteCancelRreasonsFromParameterTable(con);
                }
                if (configurationDetails.isIV_FLAG_DEFECTREASON()) {
                    deleteDefectRreasonsFromParameterTable(con);
                }
                if (configurationDetails.isIV_FLAG_BILLCANCREASON()) {
                    deleteBillingCancelReasonsFromParameterTable(con);
                }
                if (configurationDetails.isIV_FLAG_SGL_ATTRIBUTES()) {
                    deleteSunglassAttributesFromParameterTable(con);
                }
                if (configurationDetails.isIV_FLAG_MASTERS()) {//Added by Balachander V on 4.12.2020 to delete delivery mode from SAP
                    deleteDeliveryModeFromParameterTable(con);
                }
                if (configurationDetails.isIV_FLAG_RETREASON()) {//Added by Balachander V on 22.2.2022 to delete Reason for recommendation from SAP
                    deleteReasonRecommendationValsFromParameterTable(con);
                }
                if (configurationDetails.isIV_FLAG_DELIV_MERCH()) {
                    deleteDetailsfromMerchDeliveryTATTable(con);
                }
                if (configurationDetails.isIV_FLAG_DELIV_ARTICLE()) {
                    deleteArticleDetailsfromDeliveryTATTable(con);
                }
                if (configurationDetails.isIV_FLAG_CITY() && !configurationDetails.isIV_FLAG_STATE() && !configurationDetails.isIV_FLAG_COUNTRY()) {
                    insertValsToCityMaster(con, result);
                }
                if (configurationDetails.isIV_FLAG_STATE() && !configurationDetails.isIV_FLAG_COUNTRY()) {
                    insertValsToStateTable(con, result);
                }
                if (configurationDetails.isIV_FLAG_COUNTRY()) {
                    insertValsToCountryTable(con, result);
                }
                if (configurationDetails.isIV_FLAG_CHARVALUES()) {
                    insertToCharacteristicsValueMasterTable(con, result);
                }
                if (configurationDetails.isIV_FLAG_BRANDTINT()) {
                    insertToBrandTintMasterTable(con, result);
                }
                if (configurationDetails.isIV_FLAG_POSPARAM()) {
                    insertToArticleParamTable(con, result);
                }
                if (configurationDetails.isIV_FLAG_EMPLOYEEMASTER()) {
                    //insertToEmployeeMaster(con, result);
                }
                if (configurationDetails.isIV_FLAG_SITES()) {
                    insertToSiteListMaster(con, result);
                }
                if (configurationDetails.isIV_FLAG_DOCTOR_NAME()) {
                    insertToDoctorDetailsTable(con, result);
                }
                if (configurationDetails.isIV_FLAG_DELIV_MERCH()) {
                    insertinToMerchDeliveryDateTable(con, result);
                }
                if (configurationDetails.isIV_FLAG_DELIV_ARTICLE()) {
                    insertinToDeliveryArticleTable(con, result);
                }
                if (configurationDetails.isIV_FLAG_cONTACT_LENS_PRODUCT_ATTRIBUTES()) {
                    insertInToCL_Product_DetailsTable(con, result);
                    insertInToCL_Pack_DetailsTable(con, result);
                }
                if (configurationDetails.isIV_FLAG_CONTACT_LENS_TYPES()) {
                    insertInToCL_Types_Table(con, result);
                }
                if (configurationDetails.isIV_FLAG_CONTACT_LENS_BASE_CURVE()) {
                    insertInToCL_Base_Curve_Table(con, result);
                }
                if (calledParameterTable) {
                    insertValsToParameterTable(con, result);
                }
                con.commit();
                statusArea.append("\nDownloading Completed !!!");
            } catch (Exception e) {
                con.rollback();
                statusArea.append("\nFailure in Download !!!--- Error -" + e);
                e.printStackTrace();

            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void deleteCustCatValsFromParameterTable(Connection con) throws Exception {
        Statement statement1 = null;
        try {
            statement1 = con.createStatement();
            int result = statement1.executeUpdate("delete from tbl_parameter where parametertype = 'CUSC'");
            calledParameterTable = true;
        } catch (Exception e) {
        }

    }

    public void deleteAdvCanRreasonsFromParameterTable(Connection con) throws Exception {
        Statement statement1 = null;
        try {
            statement1 = con.createStatement();
            int result = statement1.executeUpdate("delete from tbl_parameter where parametertype = 'ARCR'");

            calledParameterTable = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteCancelRreasonsFromParameterTable(Connection con) throws Exception {
        Statement statement1 = null;
        try {
            statement1 = con.createStatement();
            int result = statement1.executeUpdate("delete from tbl_parameter where parametertype = 'CRES'");

            calledParameterTable = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteDefectRreasonsFromParameterTable(Connection con) throws Exception {
        Statement statement1 = null;
        try {
            statement1 = con.createStatement();
            int result = statement1.executeUpdate("delete from tbl_parameter where parametertype = 'DEFR'");

            calledParameterTable = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteBillingCancelReasonsFromParameterTable(Connection con) throws Exception {
        Statement statement1 = null;
        try {
            statement1 = con.createStatement();
            int result = statement1.executeUpdate("delete from tbl_parameter where parametertype = 'BCRN'");

            calledParameterTable = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteCompPriorityValsFromParameterTable(Connection con) throws Exception {
        Statement statement1 = null;
        try {
            statement1 = con.createStatement();
            int result = statement1.executeUpdate("delete from tbl_parameter where parametertype = 'COMP'");

            calledParameterTable = true;

        } catch (Exception e) {
        }

    }

    public void deleteOccupationValsFromParameterTable(Connection con) throws Exception {
        Statement statement1 = null;
        try {
            statement1 = con.createStatement();
            int result = statement1.executeUpdate("delete from tbl_parameter where parametertype = 'OCCM'");

            calledParameterTable = true;

        } catch (Exception e) {
        }

    }

    public void deleteCardTypesValsFromParameterTable(Connection con) throws Exception {
        Statement statement1 = null;
        try {
            statement1 = con.createStatement();
            int result = statement1.executeUpdate("delete from tbl_parameter where parametertype = 'CARD'");

            calledParameterTable = true;

        } catch (Exception e) {
        }

    }

    public void deletePaymentmodesValsFromParameterTable(Connection con) throws Exception {
        Statement statement1 = null;
        try {
            statement1 = con.createStatement();
            int result = statement1.executeUpdate("delete from tbl_parameter where parametertype = 'PAYM'");

            calledParameterTable = true;

        } catch (Exception e) {
        }

    }

    public void deleteFollowupValsFromParameterTable(Connection con) throws Exception {
        Statement statement1 = null;
        try {
            statement1 = con.createStatement();
            int result = statement1.executeUpdate("delete from tbl_parameter where parametertype = 'FOLL'");

            calledParameterTable = true;

        } catch (Exception e) {
        }

    }

    public void deleteCurrTypeValsFromParameterTable(Connection con) throws Exception {
        Statement statement1 = null;
        try {
            statement1 = con.createStatement();
            int result = statement1.executeUpdate("delete from tbl_parameter where parametertype = 'CURR'");

            calledParameterTable = true;

        } catch (Exception e) {
        }

    }

    public void deleteDesignationValsFromParameterTable(Connection con) throws Exception {
        Statement statement1 = null;
        try {
            statement1 = con.createStatement();
            int result = statement1.executeUpdate("delete from tbl_parameter where parametertype = 'DESM'");

            calledParameterTable = true;

        } catch (Exception e) {
        }

    }

    public void deleteEducationValsFromParameterTable(Connection con) throws Exception {
        Statement statement1 = null;
        try {
            statement1 = con.createStatement();
            int result = statement1.executeUpdate("delete from tbl_parameter where parametertype = 'EDUC'");

            calledParameterTable = true;

        } catch (Exception e) {
        }

    }

    public void deleteMaritalStatusValsFromParameterTable(Connection con) throws Exception {
        Statement statement1 = null;
        try {
            statement1 = con.createStatement();
            int result = statement1.executeUpdate("delete from tbl_parameter where parametertype = 'MART'");

            calledParameterTable = true;

        } catch (Exception e) {
        }

    }

    public void deleteRelationshipValsFromParameterTable(Connection con) throws Exception {
        Statement statement1 = null;
        try {
            statement1 = con.createStatement();
            int result = statement1.executeUpdate("delete from tbl_parameter where parametertype = 'RELM'");

            calledParameterTable = true;

        } catch (Exception e) {
        }

    }

    public void deleteReturnReasonValsFromParameterTable(Connection con) throws Exception {
        Statement statement1 = null;
        try {
            statement1 = con.createStatement();
            int result = statement1.executeUpdate("delete from tbl_parameter where parametertype = 'RETM'");
            calledParameterTable = true;
        } catch (Exception e) {
        }

    }

    public void deleteReasonRecommendationValsFromParameterTable(Connection con) throws Exception {
        Statement statement1 = null;
        try {
            statement1 = con.createStatement();
            int result = statement1.executeUpdate("delete from tbl_parameter where parametertype = 'RERC'");
            calledParameterTable = true;
        } catch (Exception e) {
        }

    }

    public void deleteTitleValsFromParameterTable(Connection con) throws Exception {
        Statement statement1 = null;
        try {
            statement1 = con.createStatement();
            int result = statement1.executeUpdate("delete from tbl_parameter where parametertype = 'TITL'");

            calledParameterTable = true;

        } catch (Exception e) {
        }

    }

    public void deleteUpdateModeValsFromParameterTable(Connection con) throws Exception {
        Statement statement1 = null;
        try {
            statement1 = con.createStatement();
            int result = statement1.executeUpdate("delete from tbl_parameter where parametertype = 'UPDM'");

            calledParameterTable = true;

        } catch (Exception e) {
        }

    }

    public void deleteSunglassAttributesFromParameterTable(Connection con) throws Exception {
        Statement statement1 = null;
        try {
            statement1 = con.createStatement();
            int result = statement1.executeUpdate("delete from tbl_parameter where parametertype like '%mj%'");
            calledParameterTable = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteDeliveryModeFromParameterTable(Connection con) throws Exception {
        Statement statement1 = null;
        try {
            statement1 = con.createStatement();
            int result = statement1.executeUpdate("delete from tbl_parameter where parametertype like '%delm%'");
            int result1 = statement1.executeUpdate("delete from tbl_parameter where parametertype like '%rtch%'");
            calledParameterTable = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteDetailsfromMerchDeliveryTATTable(Connection con) throws Exception {
        Statement statement1 = null;
        try {
            statement1 = con.createStatement();
            int result = statement1.executeUpdate("delete from tbl_deliverydate");
            //calledParameterTable = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteArticleDetailsfromDeliveryTATTable(Connection con) throws Exception {
        Statement statement1 = null;
        try {
            statement1 = con.createStatement();
            int result = statement1.executeUpdate("delete from tbl_deliveryDate_Articles");
            //calledParameterTable = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertToArticleParamTable(Connection con, com.sap.document.sap.rfc.functions.ZSDFMGETDROPDOWNSResponse result) throws Exception {
        PreparedStatement statement = null;
        try {
            Statement statement1 = con.createStatement();
            int resultDeletion = statement1.executeUpdate("delete from tbl_articleparam");
            ArrayList<com.sap.document.sap.rfc.functions.ZSDTRPOSPARAM> articleParams = (ArrayList<ZSDTRPOSPARAM>) result.getETPOSPARAM().getItem();
            Iterator paramIterator = articleParams.iterator();
            int i = 1;
            while (paramIterator.hasNext()) {
                ZSDTRPOSPARAM zstsdParams = (ZSDTRPOSPARAM) paramIterator.next();
                String query = "insert into tbl_articleparam  values (?,?)";
                statement = con.prepareStatement(query);
                statement.setString(1, zstsdParams.getPARAM1());
                statement.setString(2, zstsdParams.getPARAM2());
                statement.executeUpdate();
            }
            if (articleParams.size() > 0) {
                statusArea.append("\nArticle Param :Updated Records :" + articleParams.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ****************************** Setting Brand Tint Values *
     */
    public void insertToBrandTintMasterTable(Connection con, com.sap.document.sap.rfc.functions.ZSDFMGETDROPDOWNSResponse result) throws Exception {
        PreparedStatement statement = null;
        try {
            Statement statement1 = con.createStatement();
            int resultDeletion = statement1.executeUpdate("delete from tbl_brandcolorMaster");
            resultDeletion = statement1.executeUpdate("delete from tbl_brandMaster");

            ArrayList<ZSTSDBRANDMASTER> brandList = (ArrayList<ZSTSDBRANDMASTER>) result.getETBRANDMASTER().getItem();
            Iterator brandIterator = brandList.iterator();
            int i = 1;
            while (brandIterator.hasNext()) {
                ZSTSDBRANDMASTER zstsdBrands = (ZSTSDBRANDMASTER) brandIterator.next();
                String query = "insert into tbl_brandmaster  values (?,?,?)";
                statement = con.prepareStatement(query);
                statement.setString(1, zstsdBrands.getBRAND());
                statement.setString(2, zstsdBrands.getBRANDDESC());
                statement.setString(3, zstsdBrands.getSTATUSFIELD());
                statement.executeUpdate();
            }
            if (brandList.size() > 0) {
                statusArea.append("\nBrand :Updated Records :" + brandList.size());
            }

            ArrayList<ZSTSDBRANDCOLOR> brandColorlist = (ArrayList<ZSTSDBRANDCOLOR>) result.getETBRANDCOLOR().getItem();
            Iterator colorIterator = brandColorlist.iterator();
            i = 1;
            while (colorIterator.hasNext()) {
                ZSTSDBRANDCOLOR zstsdBrandColors = (ZSTSDBRANDCOLOR) colorIterator.next();
                String query = "insert into tbl_brandcolormaster  values (?,?,?,?,?)";
                statement = con.prepareStatement(query);
                statement.setString(1, zstsdBrandColors.getBRAND());
                statement.setString(2, zstsdBrandColors.getPRODUCTKEY());
                statement.setString(3, zstsdBrandColors.getCOLORKEY());
                statement.setString(4, zstsdBrandColors.getCOLOR());
                statement.setString(5, zstsdBrandColors.getSTATUSFIELD());
                statement.executeUpdate();

            }
            if (brandColorlist.size() > 0) {
                statusArea.append("\nBrand Color :Updated Records :" + brandColorlist.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ****************************** Setting employee masterValues *
     */
    public void insertToEmployeeMaster(Connection con, com.sap.document.sap.rfc.functions.ZSDFMGETDROPDOWNSResponse result) throws Exception {
        PreparedStatement statement = null;
        try {
            Statement statement1 = con.createStatement();
            int resultDeletion = statement1.executeUpdate("delete from tbl_employeemaster  ");

            ArrayList<ZSTSDCHARVALUES> list = (ArrayList<ZSTSDCHARVALUES>) result.getETCHARVALUES().getItem();
            Iterator iterator = list.iterator();
            int i = 1;
            while (iterator.hasNext()) {
                ZSTSDCHARVALUES zstsdcharvalues = (ZSTSDCHARVALUES) iterator.next();
                String query = "insert into tbl_employeemaster  values (?,?,?,?,?,?,?,?,?)";
                statement = con.prepareStatement(query);
                statement.setString(1, "");//empid
                statement.setString(2, "");//storecode
                statement.setString(3, "");//fiscalyear
                statement.setString(5, "");//title
                statement.setString(4, "");//firstname
                statement.setString(5, "");//lastname
                statement.setString(6, "");//designation
                statement.setString(7, "");//userid
                statement.setString(8, "");//status
                statement.executeUpdate();
            }
            if (list.size() > 0) {
                statusArea.append("\nEmployee :Updated Records :" + list.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ****************************** Setting characterstic Values *
     */
    public void insertToCharacteristicsValueMasterTable(Connection con, com.sap.document.sap.rfc.functions.ZSDFMGETDROPDOWNSResponse result) throws Exception {
        PreparedStatement statement = null;
        try {
            Statement statement1 = con.createStatement();
            int resultDeletion = statement1.executeUpdate("delete from tbl_characteristicvaluemaster  ");

            ArrayList<ZSTSDCHARVALUES> list = (ArrayList<ZSTSDCHARVALUES>) result.getETCHARVALUES().getItem();
            Iterator iterator = list.iterator();
            int i = 1;
            while (iterator.hasNext()) {
                ZSTSDCHARVALUES zstsdcharvalues = (ZSTSDCHARVALUES) iterator.next();
                boolean needToFetch = true;
                if (zstsdcharvalues.getVALUECHAR() != null) {
                    if (zstsdcharvalues.getVALUECHAR().trim().startsWith("-0.00")) {
                        needToFetch = false;//to avoid fetching "-0.00" coming from ISR(which is maintained by mistake in ISR
                    }
                }
                if (needToFetch) {
                    String query = "insert into tbl_characteristicvaluemaster  values (?,?,?,?,?)";
                    statement = con.prepareStatement(query);
                    statement.setInt(1, i++);
                    statement.setString(2, zstsdcharvalues.getCHARACT());
                    statement.setString(3, zstsdcharvalues.getVALUECHAR());
                    statement.setString(4, "System");
                    statement.setInt(5, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
                    statement.executeUpdate();
                }

            }
            if (list.size() > 0) {
                statusArea.append("\nCharacteristic Values :Updated Records :" + list.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ******************************* METHOD TO INSERT COUNTRY
     * *************************************
     */
    public void insertValsToCountryTable(Connection con, com.sap.document.sap.rfc.functions.ZSDFMGETDROPDOWNSResponse result) throws Exception {
        Iterator iterator;
        PreparedStatement statement = null;
        Statement statement1 = null;
        try {
            ArrayList<ZSTSDCOUNTRYMASTER> list = (ArrayList<ZSTSDCOUNTRYMASTER>) result.getETCOUNTRYMASTER().getItem();
            iterator = list.iterator();

            statement1 = con.createStatement();
            statement1.executeUpdate("delete from tbl_citymaster");
            statement1 = con.createStatement();
            statement1.executeUpdate("delete from tbl_statemaster");
            statement1 = con.createStatement();
            statement1.executeUpdate("delete from tbl_countrymaster");

            while (iterator.hasNext()) {
                ZSTSDCOUNTRYMASTER zstsdcountrymaster = (ZSTSDCOUNTRYMASTER) iterator.next();
                /*   String updateQuery = "update  tbl_countrymaster set description = ? where countryid = ?"; 
                statement = con.prepareStatement(updateQuery); 
                statement.setString(1, CellListDisplayWithValidations.convertToTitleCase(zstsdcountrymaster.getLANDX50()));
                statement.setString(2, zstsdcountrymaster.getLAND1());
                int countryUpdateResult = statement.executeUpdate();*/

                String query = "insert into tbl_countrymaster values (?,?) ";
                statement = con.prepareStatement(query);
                statement.setString(2, Validations.convertToTitleCase(zstsdcountrymaster.getLANDX50().trim()));
                statement.setString(1, zstsdcountrymaster.getLAND1().trim());
                statement.executeUpdate();

            }
            if (list.size() > 0) {
                statusArea.append("\nCountry Master :Updated Records :" + list.size());
            }
            ArrayList<ZSTSDSTATEMASTER> list2 = (ArrayList<ZSTSDSTATEMASTER>) result.getETSTATEMASTER().getItem();
            iterator = list2.iterator();
            while (iterator.hasNext()) {
                ZSTSDSTATEMASTER zstsdstatemaster = (ZSTSDSTATEMASTER) iterator.next();
                /*    String updateQuery = "update  tbl_statemaster set description = ? where stateid = ? and countryid = ?"; 
                statement = con.prepareStatement(updateQuery);
                statement.setString(1, zstsdstatemaster.getBEZEI());
                statement.setString(2, zstsdstatemaster.getBLAND());
                statement.setString(3, zstsdstatemaster.getLAND1());
                int countryUpdateResult = statement.executeUpdate();*/

                String query = "insert into tbl_statemaster values (?,?,?) ";
                statement = con.prepareStatement(query);
                statement.setString(1, zstsdstatemaster.getBLAND());
                statement.setString(2, zstsdstatemaster.getLAND1());
                statement.setString(3, zstsdstatemaster.getBEZEI());
                statement.executeUpdate();

            }
            if (list.size() > 0) {
                statusArea.append("\nState Master :Updated Records :" + list.size());
            }
            ArrayList<ZSTSDCITYMASTER> list1 = (ArrayList<ZSTSDCITYMASTER>) result.getETCITYMASTER().getItem();
            iterator = list1.iterator();
            while (iterator.hasNext()) {
                ZSTSDCITYMASTER zstsdcitymaster = (ZSTSDCITYMASTER) iterator.next();
                /* String updateQuery = "update  tbl_citymaster set description = ? where stateid = ? and countryid = ? and cityid = ?"; 
                statement = con.prepareStatement(updateQuery); 
                statement.setString(1, CellListDisplayWithValidations.convertToTitleCase(zstsdcitymaster.getCITYDESCRIPTION()));
                statement.setString(2, zstsdcitymaster.getBLAND());
                statement.setString(3, zstsdcitymaster.getLAND1());
                statement.setString(4, zstsdcitymaster.getCITYNUMBER());
                int countryUpdateResult = statement.executeUpdate();*/

                String query = "insert into tbl_citymaster values (?,?,?,?) ";
                statement = con.prepareStatement(query);
                statement.setString(1, zstsdcitymaster.getCITYNUMBER().trim());
                statement.setString(2, zstsdcitymaster.getLAND1().trim());
                statement.setString(3, zstsdcitymaster.getBLAND().trim());
                statement.setString(4, Validations.convertToTitleCase(zstsdcitymaster.getCITYDESCRIPTION().trim()));
                statement.executeUpdate();

            }
            if (list.size() > 0) {
                statusArea.append("\nCity Master :Updated Records :" + list.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ****************************** METHOD TO SET STATE
     * ******************************
     */
    public void insertValsToStateTable(Connection con, com.sap.document.sap.rfc.functions.ZSDFMGETDROPDOWNSResponse result) throws Exception {
        PreparedStatement statement = null;
        Statement statement1 = null;
        try {
            ArrayList<ZSTSDSTATEMASTER> list = (ArrayList<ZSTSDSTATEMASTER>) result.getETSTATEMASTER().getItem();
            Iterator iterator = list.iterator();
            statement1 = con.createStatement();
            statement1.executeUpdate("delete from tbl_citymaster");
            statement1 = con.createStatement();
            statement1.executeUpdate("delete from tbl_statemaster");

            while (iterator.hasNext()) {
                ZSTSDSTATEMASTER zstsdstatemaster = (ZSTSDSTATEMASTER) iterator.next();
                /*    String updateQuery = "update  tbl_statemaster set description = ? where stateid = ? and countryid = ?"; 
                statement = con.prepareStatement(updateQuery);
                statement.setString(1, zstsdstatemaster.getBEZEI());
                statement.setString(2, zstsdstatemaster.getBLAND());
                statement.setString(3, zstsdstatemaster.getLAND1());
                int countryUpdateResult = statement.executeUpdate();*/

                String query = "insert into tbl_statemaster values (?,?,?) ";
                statement = con.prepareStatement(query);
                statement.setString(1, zstsdstatemaster.getBLAND());
                statement.setString(2, zstsdstatemaster.getLAND1());
                statement.setString(3, zstsdstatemaster.getBEZEI());
                statement.executeUpdate();

            }
            ArrayList<ZSTSDCITYMASTER> list1 = (ArrayList<ZSTSDCITYMASTER>) result.getETCITYMASTER().getItem();
            iterator = list1.iterator();
            while (iterator.hasNext()) {
                ZSTSDCITYMASTER zstsdcitymaster = (ZSTSDCITYMASTER) iterator.next();
                /* String updateQuery = "update  tbl_citymaster set description = ? where stateid = ? and countryid = ? and cityid = ?"; 
                statement = con.prepareStatement(updateQuery); 
                statement.setString(1, CellListDisplayWithValidations.convertToTitleCase(zstsdcitymaster.getCITYDESCRIPTION()));
                statement.setString(2, zstsdcitymaster.getBLAND());
                statement.setString(3, zstsdcitymaster.getLAND1());
                statement.setString(4, zstsdcitymaster.getCITYNUMBER());
                int countryUpdateResult = statement.executeUpdate();*/

                String query = "insert into tbl_citymaster values (?,?,?,?) ";
                statement = con.prepareStatement(query);
                statement.setString(1, zstsdcitymaster.getCITYNUMBER().trim());
                statement.setString(2, zstsdcitymaster.getLAND1().trim());
                statement.setString(3, zstsdcitymaster.getBLAND().trim());
                statement.setString(4, Validations.convertToTitleCase(zstsdcitymaster.getCITYDESCRIPTION().trim()));
                statement.executeUpdate();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ****************************** METHOD TO SET CITY
     * ******************************
     */
    public void insertValsToCityMaster(Connection con, com.sap.document.sap.rfc.functions.ZSDFMGETDROPDOWNSResponse result) throws Exception {
        Iterator iterator;
        PreparedStatement statement = null;
        Statement statement1 = null;

        try {
            ArrayList<ZSTSDCITYMASTER> list = (ArrayList<ZSTSDCITYMASTER>) result.getETCITYMASTER().getItem();
            iterator = list.iterator();
            statement1 = con.createStatement();
            statement1.executeUpdate("delete from tbl_citymaster");
            while (iterator.hasNext()) {
                ZSTSDCITYMASTER zstsdcitymaster = (ZSTSDCITYMASTER) iterator.next();
                /* String updateQuery = "update  tbl_citymaster set description = ? where stateid = ? and countryid = ? and cityid = ?"; 
                statement = con.prepareStatement(updateQuery); 
                statement.setString(1, CellListDisplayWithValidations.convertToTitleCase(zstsdcitymaster.getCITYDESCRIPTION()));
                statement.setString(2, zstsdcitymaster.getBLAND());
                statement.setString(3, zstsdcitymaster.getLAND1());
                statement.setString(4, zstsdcitymaster.getCITYNUMBER());
                int countryUpdateResult = statement.executeUpdate();*/

                String query = "insert into tbl_citymaster values (?,?,?,?) ";
                statement = con.prepareStatement(query);
                statement.setString(1, zstsdcitymaster.getCITYNUMBER().trim());
                statement.setString(2, zstsdcitymaster.getLAND1().trim());
                statement.setString(3, zstsdcitymaster.getBLAND().trim());
                statement.setString(4, Validations.convertToTitleCase(zstsdcitymaster.getCITYDESCRIPTION().trim()));
                statement.executeUpdate();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ****************************** METHOD TO SET PARAMETER TABLE
     * ******************************
     */
    public void insertValsToParameterTable(Connection con, com.sap.document.sap.rfc.functions.ZSDFMGETDROPDOWNSResponse result) throws Exception {
        PreparedStatement statement = null;

        try {
            ArrayList<ZSTSDCUSDROPVALS> list = (ArrayList<ZSTSDCUSDROPVALS>) result.getETMASTERS().getItem();
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                ZSTSDCUSDROPVALS zstsdcusdropvals = (ZSTSDCUSDROPVALS) iterator.next();
                String sortid = "1";
                try {
                    sortid = zstsdcusdropvals.getSORTEDFIELD();
                } catch (Exception e) {//e.printStackTrace();
                }
                String query = "insert into tbl_parameter values (?,?,?,?,?,?) ";
                statement = con.prepareStatement(query);
                statement.setString(1, zstsdcusdropvals.getTABTYPEID().trim());
                System.out.println("zstsdcusdropvals.getTABTYPEID().trim()" + zstsdcusdropvals.getTABTYPEID().trim());
                char ch = 160;
                String ff = zstsdcusdropvals.getKEYFIELDID().replace(ch, ' ').trim();
                statement.setString(2, ff);
                if (Validations.isFieldNotEmpty(zstsdcusdropvals.getKEYFIELDDESC().trim()) && zstsdcusdropvals.getTABTYPEID().trim().startsWith("MJ")) {//Coded by Balachander V on 8.5.2020 to convert description to Uppercase Only for Maujim Frames
                    statement.setString(3, zstsdcusdropvals.getKEYFIELDDESC().trim());
                } else {
                    statement.setString(3, Validations.convertToTitleCase(zstsdcusdropvals.getKEYFIELDDESC().trim()));
                }//Coded by Balachander V on 8.5.2020 to convert description to Uppercase Only for Maujim Frames
                statement.setString(4, sortid);
                statement.setString(5, zstsdcusdropvals.getSTATUSFIELD().trim());
                statement.setString(6, zstsdcusdropvals.getDELETEFIELD().trim());
                statement.executeUpdate();

            }
            if (list.size() > 0) {
                statusArea.append("\nPOS Parameter Table :Updated Records :" + list.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    public void insertValsToConditionTypeMasterTable(Connection con, com.sap.document.sap.rfc.functions.ZSDFMGETDROPDOWNSResponse result) throws Exception {
//        PreparedStatement statement = null;
//        Statement statement1 = null;
//
//        try {
//            ArrayList<ZSDVWCONDMAP> list = (ArrayList<ZSDVWCONDMAP>) result.getETCONDMAP().getItem();
//            Iterator iterator = list.iterator();
//            statement1 = con.createStatement();
//            statement1.executeUpdate("delete from tbl_conditiontypemaster");
//            while (iterator.hasNext()) {
//                ZSDVWCONDMAP zstsdcusdropvals = (ZSDVWCONDMAP) iterator.next();
//                int sortid = 1;
//
//
//                String query = "insert into tbl_conditiontypemaster values (?,?,?,?,?,?,?,?) ";
//                statement = con.prepareStatement(query);
//                statement.setString(1, zstsdcusdropvals.getSTANDCOND());
//                statement.setString(2, zstsdcusdropvals.getVTEXT());
//                statement.setString(3, zstsdcusdropvals.getMANUALCOND());
//                statement.setDouble(4, zstsdcusdropvals.getMAXAMOUNT().doubleValue());
//                statement.setString(5, zstsdcusdropvals.getTAXIND());
//                statement.setString(6, zstsdcusdropvals.getBASETAX());
//                statement.setString(7, zstsdcusdropvals.getCONDCAT());
//                statement.setString(8, "");
//                statement.executeUpdate();
//
//            }
//            if (list.size() > 0) {
//                statusArea.append("\nCondition Type Master :Updated Records :" + list.size());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//**********************insert to sitemastertable
    /**
     * ****************************** Setting employee masterValues *
     */
    public void insertToSiteListMaster(Connection con, com.sap.document.sap.rfc.functions.ZSDFMGETDROPDOWNSResponse result) throws Exception {
        PreparedStatement statement = null;
        try {
            Statement statement1 = con.createStatement();
            int resultDeletion = statement1.executeUpdate("delete from tbl_sitelist  ");

            ArrayList<T001W> list = (ArrayList<T001W>) result.getETSITELIST().getItem();
            Iterator iterator = list.iterator();
            int i = 1;
            while (iterator.hasNext()) {
                T001W t001wSite = (T001W) iterator.next();
                String query = "insert into tbl_sitelist  values (?,?)";
                statement = con.prepareStatement(query);
                statement.setString(1, t001wSite.getWERKS());//site id
                statement.setString(2, t001wSite.getNAME1());//sitename
                statement.executeUpdate();
            }
            if (list.size() > 0) {
                statusArea.append("\nSite List :Updated Records :" + list.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int downloadMasterData() {
        //BackgroundPropertiesFromFile bf=new BackgroundPropertiesFromFile();
        // bf.getXIConnectionDetails();
        try { // Call Web Service Operation
            in.co.titan.configurationdetails.MIOBSYNConfigurationDetailsService service = new in.co.titan.configurationdetails.MIOBSYNConfigurationDetailsService();
            //in.co.titan.configurationdetails.MIOBSYNConfigurationDetails port = service.getMIOBSYNConfigurationDetailsPort();
            in.co.titan.configurationdetails.MIOBSYNConfigurationDetails port = service.getHTTPSPort();
            // TODO initialize WS operation arguments here
            int request_timeout = 60000;
            Connection c = null;
            String timeout = "";
            try {
                c = new MsdeConnection().createConnection();
                timeout = PosConfigParamDO.getValForThePosConfigKey(c, "webservice_request_timeout");
                if (Validations.isFieldNotEmpty(timeout)) {
                    request_timeout = Integer.parseInt(timeout);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    c.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                timeout = null;
            }
            Map<String, Object> ctxt = ((BindingProvider) port).getRequestContext();
            ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, request_timeout);

            ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
            ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());//http://pirdev:50000

            com.sap.document.sap.rfc.functions.ZSDFMGETDROPDOWNS parameters = new com.sap.document.sap.rfc.functions.ZSDFMGETDROPDOWNS();
            // TODO process result here
            com.sap.document.sap.rfc.functions.ZSDFMGETDROPDOWNSResponse result = port.miOBSYNConfigurationDetails(parameters);

            MsdeConnection msde = new MsdeConnection();
            Connection con = msde.createConnection();
            Iterator iterator;
            PreparedStatement statement = null;
            Statement statement1 = null;

            /**
             * ****************************** Setting Values to Country Master
             * **********************************
             */
            try {
                ArrayList<ZSTSDCOUNTRYMASTER> list = (ArrayList<ZSTSDCOUNTRYMASTER>) result.getETCOUNTRYMASTER().getItem();
                iterator = list.iterator();

                statement1 = con.createStatement();
                statement1.executeUpdate("delete from tbl_countrymaster");
                while (iterator.hasNext()) {
                    ZSTSDCOUNTRYMASTER zstsdcountrymaster = (ZSTSDCOUNTRYMASTER) iterator.next();
                    /*   String updateQuery = "update  tbl_countrymaster set description = ? where countryid = ?"; 
                    statement = con.prepareStatement(updateQuery); 
                    statement.setString(1, CellListDisplayWithValidations.convertToTitleCase(zstsdcountrymaster.getLANDX50()));
                    statement.setString(2, zstsdcountrymaster.getLAND1());
                    int countryUpdateResult = statement.executeUpdate();*/

                    String query = "insert into tbl_countrymaster values (?,?) ";
                    statement = con.prepareStatement(query);
                    statement.setString(2, Validations.convertToTitleCase(zstsdcountrymaster.getLANDX50()));
                    statement.setString(1, zstsdcountrymaster.getLAND1());
                    statement.executeUpdate();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            /**
             * ****************************** End ------------Setting Values to
             * Country Master **********************************
             */
            /**
             * ****************************** Setting Values to State Master
             * **********************************
             */
            try {
                ArrayList<ZSTSDSTATEMASTER> list = (ArrayList<ZSTSDSTATEMASTER>) result.getETSTATEMASTER().getItem();
                iterator = list.iterator();
                statement1 = con.createStatement();
                statement1.executeUpdate("delete from tbl_statemaster");
                while (iterator.hasNext()) {
                    ZSTSDSTATEMASTER zstsdstatemaster = (ZSTSDSTATEMASTER) iterator.next();
                    /*    String updateQuery = "update  tbl_statemaster set description = ? where stateid = ? and countryid = ?"; 
                    statement = con.prepareStatement(updateQuery);
                    statement.setString(1, zstsdstatemaster.getBEZEI());
                    statement.setString(2, zstsdstatemaster.getBLAND());
                    statement.setString(3, zstsdstatemaster.getLAND1());
                    int countryUpdateResult = statement.executeUpdate();*/

                    String query = "insert into tbl_statemaster values (?,?,?) ";
                    statement = con.prepareStatement(query);
                    statement.setString(1, zstsdstatemaster.getBLAND());
                    statement.setString(2, zstsdstatemaster.getLAND1());
                    statement.setString(3, zstsdstatemaster.getBEZEI());
                    statement.executeUpdate();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            /**
             * ****************************** End ------------Setting Values to
             * State Master **********************************
             */
            /**
             * ****************************** Setting Values to City Master
             * **********************************
             */
            try {
                ArrayList<ZSTSDCITYMASTER> list = (ArrayList<ZSTSDCITYMASTER>) result.getETCITYMASTER().getItem();
                iterator = list.iterator();
                statement1 = con.createStatement();
                statement1.executeUpdate("delete from tbl_citymaster");
                while (iterator.hasNext()) {
                    ZSTSDCITYMASTER zstsdcitymaster = (ZSTSDCITYMASTER) iterator.next();
                    /* String updateQuery = "update  tbl_citymaster set description = ? where stateid = ? and countryid = ? and cityid = ?"; 
                    statement = con.prepareStatement(updateQuery); 
                    statement.setString(1, CellListDisplayWithValidations.convertToTitleCase(zstsdcitymaster.getCITYDESCRIPTION()));
                    statement.setString(2, zstsdcitymaster.getBLAND());
                    statement.setString(3, zstsdcitymaster.getLAND1());
                    statement.setString(4, zstsdcitymaster.getCITYNUMBER());
                    int countryUpdateResult = statement.executeUpdate();*/

                    String query = "insert into tbl_citymaster values (?,?,?,?) ";
                    statement = con.prepareStatement(query);
                    statement.setString(1, zstsdcitymaster.getCITYNUMBER());
                    statement.setString(2, zstsdcitymaster.getLAND1());
                    statement.setString(3, zstsdcitymaster.getBLAND());
                    statement.setString(4, Validations.convertToTitleCase(zstsdcitymaster.getCITYDESCRIPTION()));
                    statement.executeUpdate();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            /**
             * ****************************** End ------------Setting Values to
             * City Master **********************************
             */
            /**
             * ****************************** Setting Values to dropdowns
             * Master **********************************
             */
            try {
                ArrayList<ZSTSDCUSDROPVALS> list = (ArrayList<ZSTSDCUSDROPVALS>) result.getETMASTERS().getItem();
                iterator = list.iterator();
                statement1 = con.createStatement();
                statement1.executeUpdate("delete from tbl_parameter");
                while (iterator.hasNext()) {
                    ZSTSDCUSDROPVALS zstsdcusdropvals = (ZSTSDCUSDROPVALS) iterator.next();
                    int sortid = 1;
                    /* String updateQuery = "update  tbl_parameter set description = ?,sortid = ?,blocked = ?, deleted = ? where parametertype = ? and id = ? "; 
                    statement = con.prepareStatement(updateQuery); 
                    try
                    {
                    sortid = Integer.parseInt(zstsdcusdropvals.getSORTEDFIELD());
                    }catch(Exception e){}
                    statement.setString(1, CellListDisplayWithValidations.convertToTitleCase(zstsdcusdropvals.getKEYFIELDDESC()));
                    statement.setInt(2,  sortid);
                    statement.setString(3, zstsdcusdropvals.getSTATUSFIELD());
                    statement.setString(4, zstsdcusdropvals.getDELETEFIELD());
                    statement.setString(5, zstsdcusdropvals.getTABTYPEID());
                    statement.setString(6, zstsdcusdropvals.getKEYFIELDID());
                    int countryUpdateResult = statement.executeUpdate();*/
                    try {
                        sortid = Integer.parseInt(zstsdcusdropvals.getSORTEDFIELD());
                    } catch (Exception e) {
                    }
                    String query = "insert into tbl_parameter values (?,?,?,?,?,?) ";
                    statement = con.prepareStatement(query);
                    statement.setString(1, zstsdcusdropvals.getTABTYPEID());
                    statement.setString(2, zstsdcusdropvals.getKEYFIELDID());
                    statement.setString(3, Validations.convertToTitleCase(zstsdcusdropvals.getKEYFIELDDESC()));
                    statement.setInt(4, sortid);
                    statement.setString(5, zstsdcusdropvals.getSTATUSFIELD());
                    statement.setString(6, zstsdcusdropvals.getDELETEFIELD());
                    statement.executeUpdate();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            /**
             * ****************************** End ------------Setting Values to
             * dropdowns Master **********************************
             */
            try {
                ArrayList<ZSTSDCHARVALUES> list = (ArrayList<ZSTSDCHARVALUES>) result.getETCHARVALUES().getItem();
                iterator = list.iterator();
                int i = 1;
                while (iterator.hasNext()) {
                    ZSTSDCHARVALUES zstsdcharvalues = (ZSTSDCHARVALUES) iterator.next();
                    String query = "insert into tbl_characteristicvaluemaster  values (?,?,?,?,?)";
                    statement = con.prepareStatement(query);
                    statement.setInt(1, i++);
                    statement.setString(2, zstsdcharvalues.getCHARACT());
                    statement.setString(3, zstsdcharvalues.getVALUECHAR());
                    statement.setString(4, "System");
                    statement.setInt(5, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
                    statement.executeUpdate();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            /**
             * ****************************** Setting characterstic Values
             * **********************************
             */
            /**
             * ****************************** END -------------- Setting
             * characterstic Values **********************************
             */
            return 1;
        } catch (Exception ex) {
            // TODO handle custom exceptions heredeleteDeliveryModeFromParameterTable
            ex.printStackTrace();
            return 0;
        }

    }

    public void insertToDoctorDetailsTable(Connection con, com.sap.document.sap.rfc.functions.ZSDFMGETDROPDOWNSResponse result) throws Exception {
        PreparedStatement statement = null;
        Statement statement1 = null;
        ArrayList<com.sap.document.sap.rfc.functions.ZSTSDDOCTORDETAILS> doctorDetails = null;
        String query = null;
        try {
            statement1 = con.createStatement();
            statement1.executeUpdate("delete from tbl_doctor_details");
            doctorDetails = (ArrayList<ZSTSDDOCTORDETAILS>) result.getETDOCTORDETAILS().getItem();
            query = "insert into tbl_doctor_details values (?,?)";
            for (ZSTSDDOCTORDETAILS zstsdParams : doctorDetails) {
                statement = con.prepareStatement(query);
                statement.setString(1, zstsdParams.getSITEID());
                statement.setString(2, zstsdParams.getDOCTORNAME());
                statement.executeUpdate();
            }
            if (doctorDetails.size() > 0) {
                statusArea.append("\nDoctor Details :Updated Records :" + doctorDetails.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            statement = null;
            statement1 = null;
            doctorDetails = null;
            query = null;
        }
    }

    public void insertinToMerchDeliveryDateTable(Connection con, com.sap.document.sap.rfc.functions.ZSDFMGETDROPDOWNSResponse result) throws Exception {
        PreparedStatement statement = null;
        Statement statement1 = null;
        ArrayList<com.sap.document.sap.rfc.functions.ZSTSDDELIVDATEMERCHCAT> DeliveryDateMerchDetails = null;
        String query = null;
        try {
            statement1 = con.createStatement();
            statement1.executeUpdate("delete from tbl_deliverydate");
            DeliveryDateMerchDetails = (ArrayList<ZSTSDDELIVDATEMERCHCAT>) result.getETDELIVMERCHCAT().getItem();
            query = "INSERT INTO tbl_deliverydate VALUES(?,?,?,?,?,?)";
            for (ZSTSDDELIVDATEMERCHCAT deliveryDateMerchcat : DeliveryDateMerchDetails) {
                if (deliveryDateMerchcat.getSITE() != null && !deliveryDateMerchcat.getSITE().equalsIgnoreCase("")) {
                    statement = con.prepareStatement(query);
                    statement.setString(1, deliveryDateMerchcat.getSITE());
                    statement.setString(2, "PEWH");
                    statement.setString(3, deliveryDateMerchcat.getMERCHCAT());
                    statement.setInt(4, deliveryDateMerchcat.getLOGISTICDAYS());
                    statement.setString(5, deliveryDateMerchcat.getPROCESSDAYS().toString());
                    statement.setString(6, "FSV");
                    statement.executeUpdate();
                }
            }
            if (DeliveryDateMerchDetails.size() > 0) {
                statusArea.append("\nDelivery Date Merch TAT Details :Updated Records :" + DeliveryDateMerchDetails.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            statement = null;
            statement1 = null;
            DeliveryDateMerchDetails = null;
            query = null;
        }
    }

    public void insertinToDeliveryArticleTable(Connection con, com.sap.document.sap.rfc.functions.ZSDFMGETDROPDOWNSResponse result) throws Exception {
        PreparedStatement statement = null;
        Statement statement1 = null;
        ArrayList<com.sap.document.sap.rfc.functions.ZSTSDDELIVDATEARTICLE> DeliveryArticleDetails = null;
        String query = null;
        try {
            statement1 = con.createStatement();
            statement1.executeUpdate("delete FROM tbl_deliveryDate_Articles");
            DeliveryArticleDetails = (ArrayList<ZSTSDDELIVDATEARTICLE>) result.getETDELIVARTICLE().getItem();
            query = "INSERT INTO tbl_deliveryDate_Articles VALUES(?,?,?)";
            for (ZSTSDDELIVDATEARTICLE deliveryDateArticle : DeliveryArticleDetails) {
                if (deliveryDateArticle.getSKUCODE() != null && !deliveryDateArticle.getSKUCODE().equalsIgnoreCase("")) {
                    statement = con.prepareStatement(query);
                    statement.setString(1, deliveryDateArticle.getSKUCODE());
                    statement.setInt(2, deliveryDateArticle.getPROCESSDAYS());
                    statement.setString(3, "");
                    statement.executeUpdate();
                }
            }
            if (DeliveryArticleDetails.size() > 0) {
                statusArea.append("\nDelivery Date Article TAT Details :Updated Records :" + DeliveryArticleDetails.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            statement = null;
            statement1 = null;
            DeliveryArticleDetails = null;
            query = null;
        }
    }
    
    public void insertInToCL_Product_DetailsTable(Connection con, com.sap.document.sap.rfc.functions.ZSDFMGETDROPDOWNSResponse result) throws Exception {
        PreparedStatement statement = null;
        Statement statement1 = null;
        ArrayList<com.sap.document.sap.rfc.functions.ZSTSDCLATTRIBUTESPRODUCT> productdetails = null;
        String query = null;
        try {
            statement1 = con.createStatement();
            statement1.executeUpdate("delete from tbl_cl_productdetails");
            productdetails = (ArrayList<ZSTSDCLATTRIBUTESPRODUCT>) result.getETCLATTRPROD().getItem();
            query = "insert into tbl_cl_productdetails values (?,?,?,?)";
            for (ZSTSDCLATTRIBUTESPRODUCT zstsdParams : productdetails) {
                statement = con.prepareStatement(query);
                statement.setString(1, zstsdParams.getBRAND());
                statement.setString(2, zstsdParams.getPRODTYPE());
                statement.setString(3, zstsdParams.getPRODUCTKEY());
                statement.setString(4, zstsdParams.getPRODUCTDESC());
                statement.executeUpdate();
            }
            if (productdetails.size() > 0) {
                statusArea.append("\nCL Product Details :Updated Records :" + productdetails.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            statement = null;
            statement1 = null;
            productdetails = null;
            query = null;
        }
    }

    public void insertInToCL_Pack_DetailsTable(Connection con, com.sap.document.sap.rfc.functions.ZSDFMGETDROPDOWNSResponse result) throws Exception {
        PreparedStatement statement = null;
        Statement statement1 = null;
        ArrayList<com.sap.document.sap.rfc.functions.ZSTSDCLATTRIBUTESPACK> packdetails = null;
        String query = null;
        try {
            statement1 = con.createStatement();
            statement1.executeUpdate("delete from tbl_cl_packdetails");
            packdetails = (ArrayList<ZSTSDCLATTRIBUTESPACK>) result.getETCLATTRPACK().getItem();
            query = "insert into tbl_cl_packdetails values (?,?,?,?)";
            for (ZSTSDCLATTRIBUTESPACK zstsdParams : packdetails) {
                statement = con.prepareStatement(query);
                statement.setString(1, zstsdParams.getBRAND());
                statement.setString(2, zstsdParams.getPRODUCTKEY());
                statement.setString(3, zstsdParams.getPACKKEY());
                statement.setString(4, zstsdParams.getPACKSIZE());
                statement.executeUpdate();
            }
            if (packdetails.size() > 0) {
                statusArea.append("\nCL Pack Details :Updated Records :" + packdetails.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            statement = null;
            statement1 = null;
            packdetails = null;
            query = null;
        }
    }

    public void insertInToCL_Base_Curve_Table(Connection con, com.sap.document.sap.rfc.functions.ZSDFMGETDROPDOWNSResponse result) throws Exception {
        PreparedStatement statement = null;
        Statement statement1 = null;
        ArrayList<com.sap.document.sap.rfc.functions.ZSTSDCLATTRIBUTESBASECUR> basecurve_details = null;
        String query = null;
        try {
            statement1 = con.createStatement();
            statement1.executeUpdate("delete from tbl_cl_basecurve");
            basecurve_details = (ArrayList<ZSTSDCLATTRIBUTESBASECUR>) result.getETCLATTRBASECUR().getItem();
            query = "insert into tbl_cl_basecurve values (?,?,?,?)";
            for (ZSTSDCLATTRIBUTESBASECUR zstsdParams : basecurve_details) {
                statement = con.prepareStatement(query);
                statement.setString(1, zstsdParams.getBRAND());
                statement.setString(2, zstsdParams.getPRODUCTKEY());
                statement.setString(3, zstsdParams.getBASECURVEKEY());
                statement.setString(4, zstsdParams.getBASECURVEVAL());
                statement.executeUpdate();
            }
            if (basecurve_details.size() > 0) {
                statusArea.append("\nCL Base Curve Details :Updated Records :" + basecurve_details.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            statement = null;
            statement1 = null;
            basecurve_details = null;
            query = null;
        }
    }

    public void insertInToCL_Types_Table(Connection con, com.sap.document.sap.rfc.functions.ZSDFMGETDROPDOWNSResponse result) throws Exception {
        PreparedStatement statement = null;
        Statement statement1 = null;
        ArrayList<com.sap.document.sap.rfc.functions.ZSTSDCLATTRIBUTESPRODTYPE> cl_types = null;
        String query = null;
        try {
            statement1 = con.createStatement();
            statement1.executeUpdate("delete from tbl_cl_types");
            cl_types = (ArrayList<ZSTSDCLATTRIBUTESPRODTYPE>) result.getETCLATTRPRODTYPE().getItem();
            query = "insert into tbl_cl_types values (?,?,?)";
            for (ZSTSDCLATTRIBUTESPRODTYPE zstsdParams : cl_types) {
                statement = con.prepareStatement(query);
                statement.setString(1, zstsdParams.getBRAND());
                statement.setString(2, zstsdParams.getPRODTYPE());
                statement.setString(3, zstsdParams.getPRODTYPEDESC());
                statement.executeUpdate();
            }
            if (cl_types.size() > 0) {
                statusArea.append("\nCL Type Details :Updated Records :" + cl_types.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            statement = null;
            statement1 = null;
            cl_types = null;
            query = null;
        }
    }
}
