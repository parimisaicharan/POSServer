/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package posstaging;

import ISRetail.Helpers.ConvertDate;
import ISRetail.Helpers.XMLCalendar;
import in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.ArticleCharacteristics;
import in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.ArticleMaster;
import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.ws.BindingProvider;
import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.article.ArticleCharacteristicsPOJO;
import ISRetail.article.ArticleDO;
import ISRetail.article.ArticleDiscountPOJO;
import ISRetail.article.ArticleMasterPOJO;
import ISRetail.article.ArticleOtherChargesPOJO;
import ISRetail.article.ArticleTaxPOJO;
import ISRetail.article.ArticleUCPPOJO;
import ISRetail.article.ConditionTypeMasterPOJO;
import ISRetail.article.PromotionHeaderPOJO;
import ISRetail.article.PromotionOfferItemPOJO;
import ISRetail.article.PromotionSellingItemPOJO;
import ISRetail.employee.EmployeeMasterDO;
import ISRetail.employee.EmployeeMasterPOJO;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteCreditSaleReference;
import ISRetail.plantdetails.SiteForceReleasePOJO;
import ISRetail.plantdetails.SiteHolidayCalenderPOJO;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.plantdetails.SiteMasterPOJO;
import ISRetail.serverconsole.PosConfigParamDO;
import ISRetail.serverconsole.ServerConsole;
import ISRetail.stock.StockMasterDO;
import ISRetail.stock.StockMasterPOJO;
import ISRetail.utility.validations.Validations;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class ArticleWebService {

    private String articleFlag;
    private String articleUcpFlag;
    private String articleTaxFlag;
    private String articleDiscountFlag;
    private String articleCharacteristicsFlag;
    private String stockMasterFlag;
    private String siteMasterFlag;
    private String employeeMasterFlag;
    private String otherChargesFlag;
    private String promotionsFlag;
    private String gvMasterFlag;
    private String conditionTypeMasterFlag;
    private String systemDateFlag;
    private in.co.titan.highfrequencydata.DTHighFrequencyDataResponse result;
    private JTextArea statusArea;
    private String mysite;
    private String fromDate;
    private String toDate;
    private String isAutolReq;
    private static Logger logger = Logger.getLogger(ArticleWebService.class.getName());

    public ArticleWebService(JTextArea statusArea) {
        this.statusArea = statusArea;
    }

    public ArticleWebService() {
    }

    public void callWebService(String site) throws Exception {
        mysite = site;
        in.co.titan.highfrequencydata.MIOBSYNHighFrequencyDataService service;
        in.co.titan.highfrequencydata.MIOBSYNHighFrequencyData port;
        in.co.titan.highfrequencydata.DTHighFrequencyData mtHighFrequencyData;
        Map<String, Object> ctxt;
        try { // Call Web Service Operation
            service = new in.co.titan.highfrequencydata.MIOBSYNHighFrequencyDataService();
            //  port = service.getMIOBSYNHighFrequencyDataPort();
            port = service.getHTTPSPort();//Changed by Balachander V on 19.12.2018 to get HTTPS port from new WSDL file
            // TODO initialize WS operation arguments here\

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
            ctxt = ((BindingProvider) port).getRequestContext();
            //ctxt.put(JAXWSProperties.CONNECT_TIMEOUT, connectionTimeout);
            ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, request_timeout);
            ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
            ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());
            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port() + "/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_HighFrequencyData&version=3.0&Sender.Service=BS_TITAN_POS&Interf");
//            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "​http://pirdev:50000/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_HighFrequencyData&version=3.0&Sender.Service=BS_TITAN_POS&Interface=http://titan.co.in/HighFrequencyData");
            mtHighFrequencyData = new in.co.titan.highfrequencydata.DTHighFrequencyData();
            // TODO process result here
            mtHighFrequencyData.setSite(site);
            mtHighFrequencyData.setSITESEARCH(site);

            if (Validations.isFieldNotEmpty(this.getArticleCharacteristicsFlag())) {
                mtHighFrequencyData.setFArticleCharacteristics(this.getArticleCharacteristicsFlag());
            }
            if (Validations.isFieldNotEmpty(this.getArticleFlag())) {
                mtHighFrequencyData.setFArticleMaster(this.getArticleFlag());
            }
            if (Validations.isFieldNotEmpty(this.articleDiscountFlag)) {
                mtHighFrequencyData.setFDiscount(this.articleDiscountFlag);
            }
            if (Validations.isFieldNotEmpty(this.getStockMasterFlag())) {
                mtHighFrequencyData.setFStockMaster(this.getStockMasterFlag());
            }
            if (Validations.isFieldNotEmpty(this.getArticleTaxFlag())) {
                mtHighFrequencyData.setFTax(this.getArticleTaxFlag());
            }
            if (Validations.isFieldNotEmpty(this.getArticleUcpFlag())) {
                mtHighFrequencyData.setFUCP(this.getArticleUcpFlag());
            }
            if (Validations.isFieldNotEmpty(this.getSiteMasterFlag())) {
                mtHighFrequencyData.setFSite(this.getSiteMasterFlag());
            }
            if (Validations.isFieldNotEmpty(this.employeeMasterFlag)) {
                mtHighFrequencyData.setFEmployee(this.employeeMasterFlag);
            }
            if (Validations.isFieldNotEmpty(this.otherChargesFlag)) {
                mtHighFrequencyData.setFOthers(this.otherChargesFlag);
            }
            if (Validations.isFieldNotEmpty(this.getPromotionsFlag())) {
                mtHighFrequencyData.setFPromotion(this.getPromotionsFlag());
            }
            if (Validations.isFieldNotEmpty(this.gvMasterFlag)) {
                mtHighFrequencyData.setFGV(this.gvMasterFlag);
            }
            if (Validations.isFieldNotEmpty(this.conditionTypeMasterFlag)) {
                mtHighFrequencyData.setFCondMap(this.conditionTypeMasterFlag);
            }
//            String dateString = getCurrentDateFormatted();// aaded by charan to send from & to dates for downloading masters on 05.02.24
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            try {
//                // Parse the string into a Date object
//                Date date = dateFormat.parse(dateString);
//                // Create a GregorianCalendar and set the time using the parsed Date
//                GregorianCalendar gregorianCalendar = new GregorianCalendar();
//                gregorianCalendar.setTime(date);
//                System.err.println("gregorianCalendar:"+gregorianCalendar);
//                // Create a XMLGregorianCalendar from the GregorianCalendar
//                XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
//                xmlGregorianCalendar.setTime(0, 0, 0);
//                // Set the timezone to UTC to ensure the date remains the same
//                xmlGregorianCalendar.setTimezone(0);
//                // Set mtHighFrequencyData fromDate using the XMLGregorianCalendar
//                mtHighFrequencyData.setFromDate(xmlGregorianCalendar);
//                mtHighFrequencyData.setToDate(xmlGregorianCalendar);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
                System.err.println("Fromdate: "+fromDate);
//            if (fromDate != null) {
//                try {
//                    java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(ConvertDate.getDateNumeric(fromDate, "dd/MM/yyyy"));
//                    if (createdDate != null) {
//                        XMLCalendar xmlDate = new XMLCalendar(createdDate);
//                        mtHighFrequencyData.setFromDate(xmlDate);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            if (toDate != null) {
//                try {
//                    java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(ConvertDate.getDateNumeric(toDate, "dd/MM/yyyy"));
//                    if (createdDate != null) {
//                        XMLCalendar xmlDate = new XMLCalendar(createdDate);
//                        mtHighFrequencyData.setToDate(xmlDate);
//                    }
//                } catch (Exception e) {
//                }
//            }
            statusArea.append("\nRequest Sent...");
            //System.err.println(" INFO :  Web service name : MI_OB_SYN_HighFrequencyData : From Date : " + fromDate + " To Date" + toDate);
            System.err.println(" INFO :  Web service name : MI_OB_SYN_HighFrequencyData : From Date : " + mtHighFrequencyData.getFromDate() + " To Date" + mtHighFrequencyData.getToDate());
            System.err.println(" INFO :  Sending request for : Store Code : " + mtHighFrequencyData.getSite() + " Site : " + mtHighFrequencyData.getSITESEARCH() + " Article Characteristics : " + mtHighFrequencyData.getFArticleCharacteristics() + " Article Master : " + mtHighFrequencyData.getFArticleMaster() + " Discount : " + mtHighFrequencyData.getFDiscount() + " Stock Master : " + mtHighFrequencyData.getFStockMaster() + " Tax : " + mtHighFrequencyData.getFTax() + " UCP : " + mtHighFrequencyData.getFUCP() + " Force Release : " + mtHighFrequencyData.getFSite() + " Employee : " + mtHighFrequencyData.getFEmployee() + " Others : " + mtHighFrequencyData.getFOthers() + " Promotions : " + mtHighFrequencyData.getFPromotion() + " Gift Voucher : " + mtHighFrequencyData.getFGV() + " Condition Master : " + mtHighFrequencyData.getFCondMap() + "from date: " + mtHighFrequencyData.getFromDate() + "to date: " + mtHighFrequencyData.getToDate());
            result = port.miOBSYNHighFrequencyData(mtHighFrequencyData);
            System.err.println(" INFO :  Received : Store Code : " + result.getSiteMaster().getSiteid() + " Site : " + result.getSiteMaster().getSiteid() + " Article Characteristics : " + result.getArticleCharacteristics().size() + " Article Master : " + result.getArticleMaster().size() + " Discount : " + result.getStdDiscount().size() + " Stock Master : " + result.getStockMaster().size() + " Tax : " + result.getTAX().size() + " UCP : " + result.getUCP().size() + " Force Release : " + result.getSiteFrel().size() + " Employee : " + result.getEmployeeMaster().size() + " Others : " + result.getOthers().size() + " Promotions : " + result.getPromotionHeader().size() + " Gift Voucher : " + result.getGVStock().size() + " Condition Master : " + result.getConditionMap().size());
        } catch (Exception ex) {
            // TODO handle custom exceptions here
            logger.error("Error while downloading master data : " + ex.getMessage());
            ex.printStackTrace();
            throw ex;
        } finally {
            service = null;
            port = null;
            mtHighFrequencyData = null;
            ctxt = null;
        }

    }

    private static String getCurrentDateFormatted() {
        // Get the current date
        Date currentDate = new Date();

        // Format the date in "yyyy-MM-dd" format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(currentDate);

        return formattedDate;
    }

    /**
     * ******************************* METHOD TO GET ARTICLE MASTER FROM
     * WEBSERVICE RESULT *********************************
     */
    public ArrayList<ArticleMasterPOJO> getArticleMaster() {
        try {
            ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.ArticleMaster> articleMasters = (ArrayList<ArticleMaster>) result.getArticleMaster();
            if (articleMasters != null) {
                ArrayList<ArticleMasterPOJO> masterPOJOs = new ArrayList<ArticleMasterPOJO>();
                Iterator iterator = articleMasters.iterator();
                while (iterator.hasNext()) {
                    in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.ArticleMaster articleMaster = (ArticleMaster) iterator.next();
                    ArticleMasterPOJO articleMasterPOJO = new ArticleMasterPOJO();
                    articleMasterPOJO.setStorecode(articleMaster.getSITE());
                    articleMasterPOJO.setMaterialcode(articleMaster.getMATNR());
                    articleMasterPOJO.setDivision(articleMaster.getSPART());
                    articleMasterPOJO.setMaterialdescription(articleMaster.getMAKTX());
                    articleMasterPOJO.setArticletype(articleMaster.getMTART());
                    articleMasterPOJO.setMerchcategory(articleMaster.getMATKL());
                    articleMasterPOJO.setUom(articleMaster.getMEINS());
                    articleMasterPOJO.setBatchindicator(articleMaster.getXCHPF());
                    articleMasterPOJO.setHsn_sac_code(articleMaster.getHSNSACCODE());  //Added By Bala 16.06.2017 for GST
                    if (articleMaster.getPLANDELTIME() != null) {
                        articleMasterPOJO.setPlanneddeltime(articleMaster.getPLANDELTIME().intValue());
                    }
                    articleMasterPOJO.setMinshelflife(articleMaster.getMINSHELLIFE());
                    articleMasterPOJO.setWarrantyperiod(articleMaster.getWARRANTYPER());
                    articleMasterPOJO.setCategory(articleMaster.getPRDHA());
                    if (articleMaster.getDELETIONIND() != null) {
                        articleMasterPOJO.setDeletionind(articleMaster.getDELETIONIND());
                    } else {
                        articleMasterPOJO.setDeletionind("");
                    }
                    articleMasterPOJO.setUpdatestatus(articleMaster.getUPDATESTATUS());
                    if (articleMaster.getCREATEDBY() != null) {
                        articleMasterPOJO.setCreatedby(articleMaster.getCREATEDBY());
                    } else {
                        articleMasterPOJO.setCreatedby("");
                    }
                    if (articleMaster.getCREATEDATE() != null) {
                        articleMasterPOJO.setCreateddate(ConvertDate.getDateNumeric(articleMaster.getCREATEDATE().toGregorianCalendar().getTime()));
                    } else {
                        articleMasterPOJO.setCreateddate(0);
                    }
                    if (articleMaster.getCREATETIME() != null) {
                        articleMasterPOJO.setCreatedtime(ConvertDate.getTimeString(articleMaster.getCREATETIME().toGregorianCalendar().getTime()));
                    } else {
                        articleMasterPOJO.setCreatedtime("");
                    }
                    articleMasterPOJO.setModifiedby(articleMaster.getMODIFBY());
                    if (articleMaster.getMODIFDATE() != null) {
                        articleMasterPOJO.setModifieddate(ConvertDate.getDateNumeric(articleMaster.getMODIFDATE().toGregorianCalendar().getTime()));
                    } else {
                        articleMasterPOJO.setModifieddate(0);
                    }
                    if (articleMaster.getMODIFTIME() != null) {
                        articleMasterPOJO.setModifiedtime(ConvertDate.getTimeString(articleMaster.getMODIFTIME().toGregorianCalendar().getTime()));
                    } else {
                        articleMasterPOJO.setModifiedtime("");
                    }

                    masterPOJOs.add(articleMasterPOJO);
                }

                return masterPOJOs;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ******************************* METHOD TO GET ARTICLE MASTER
     * CHARACTERSTICS FROM WEBSERVICE RESULT *********************************
     */
    public ArrayList<ArticleCharacteristicsPOJO> getArticleCharacterstics() {
        try {
            ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.ArticleCharacteristics> articleCharacteristicses = (ArrayList<ArticleCharacteristics>) result.getArticleCharacteristics();
            if (articleCharacteristicses != null) {
                ArrayList<ArticleCharacteristicsPOJO> characteristicsPOJOs = new ArrayList<ArticleCharacteristicsPOJO>();
                Iterator iterator = articleCharacteristicses.iterator();
                while (iterator.hasNext()) {
                    in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.ArticleCharacteristics characteristics = (ArticleCharacteristics) iterator.next();
                    ArticleCharacteristicsPOJO articleCharacteristicsPOJO = new ArticleCharacteristicsPOJO();
                    articleCharacteristicsPOJO.setStorecode(characteristics.getSITE());
                    articleCharacteristicsPOJO.setMaterialcode(characteristics.getMATNR());
                    articleCharacteristicsPOJO.setCharacteristics(characteristics.getATNAM());
                    articleCharacteristicsPOJO.setValueno(characteristics.getATZHL());
                    articleCharacteristicsPOJO.setValue(characteristics.getATWRT());
                    articleCharacteristicsPOJO.setUpdatestatus(characteristics.getUPDATESTATUS());
                    articleCharacteristicsPOJO.setCreatedby("admin");
                    articleCharacteristicsPOJO.setCreateddate(20080920);

                    characteristicsPOJOs.add(articleCharacteristicsPOJO);
                }

                return characteristicsPOJOs;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ******************************* METHOD TO GET ARTICLE UCP FROM
     * WEBSERVICE RESULT *********************************
     */
    public ArrayList<ArticleUCPPOJO> getArticleUCP() {
        try {
            ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.UCP> ucps = (ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.UCP>) result.getUCP();
            if (ucps != null) {
                ArrayList<ArticleUCPPOJO> uCPPOJOs = new ArrayList<ArticleUCPPOJO>();
                Iterator iterator = ucps.iterator();
                while (iterator.hasNext()) {
                    in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.UCP ucp = (in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.UCP) iterator.next();
                    ArticleUCPPOJO articleUCPPOJO = new ArticleUCPPOJO();
                    articleUCPPOJO.setCondrecno(ucp.getCondrecno());
                    articleUCPPOJO.setCondtype(ucp.getCondtype());
                    articleUCPPOJO.setCurrency(ucp.getCurrency());
                    if (ucp.getDeletionind() != null) {
                        articleUCPPOJO.setDeletionind(ucp.getDeletionind());
                    } else {
                        articleUCPPOJO.setDeletionind("");
                    }
                    articleUCPPOJO.setFromdate(ConvertDate.getDateNumeric(ucp.getFromdate().toGregorianCalendar().getTime()));
                    articleUCPPOJO.setMaterialcode(ucp.getArticle());
                    articleUCPPOJO.setStorecode(ucp.getSite());
                    articleUCPPOJO.setTodate(ConvertDate.getDateNumeric(ucp.getTodate().toGregorianCalendar().getTime()));
                    if (ucp.getAmount() != null) {
                        articleUCPPOJO.setUcpamount(ucp.getAmount().doubleValue());
                    }
                    articleUCPPOJO.setUpdatestatus(ucp.getUpdatestatus());
                    if (ucp.getPriority() != null) {
                        articleUCPPOJO.setPriority(ucp.getPriority());
                    } else {
                        articleUCPPOJO.setPriority("");
                    }
                    uCPPOJOs.add(articleUCPPOJO);
                }

                return uCPPOJOs;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ******************************* METHOD TO GET ARTICLE DISCOUNT FROM
     * WEBSERVICE RESULT *********************************
     */
    public ArrayList<ArticleDiscountPOJO> getArticleDiscount() {
        try {
            ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.StdDiscount> discounts = (ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.StdDiscount>) result.getStdDiscount();
            if (discounts != null) {
                ArrayList<ArticleDiscountPOJO> discountPOJOs = new ArrayList<ArticleDiscountPOJO>();
                Iterator iterator = discounts.iterator();
                while (iterator.hasNext()) {
                    in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.StdDiscount discount = (in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.StdDiscount) iterator.next();
                    ArticleDiscountPOJO articleDiscountPOJO = new ArticleDiscountPOJO();
                    articleDiscountPOJO.setCondrecno(discount.getCondrecno());
                    articleDiscountPOJO.setCondtype(discount.getCondtype());
                    articleDiscountPOJO.setCalculationtype(discount.getCalctype());
                    if (articleDiscountPOJO.getDeletionind() != null) {
                        articleDiscountPOJO.setDeletionind(articleDiscountPOJO.getDeletionind());
                    } else {
                        articleDiscountPOJO.setDeletionind("");
                    }
                    articleDiscountPOJO.setFromdate(ConvertDate.getDateNumeric(discount.getFromdate().toGregorianCalendar().getTime()));
                    articleDiscountPOJO.setMerchcat(discount.getMerchcat());
                    articleDiscountPOJO.setStorecode(discount.getSite());
                    articleDiscountPOJO.setTodate(ConvertDate.getDateNumeric(discount.getTodate().toGregorianCalendar().getTime()));
                    if (discount.getAmount() != null) {
                        if (discount.getCalctype() != null) {
                            if (discount.getCalctype().equalsIgnoreCase("A")) {
                                articleDiscountPOJO.setDiscount(discount.getAmount().doubleValue() / 10);
                            } else {
                                articleDiscountPOJO.setDiscount(discount.getAmount().doubleValue());
                            }
                        }
                    }
                    articleDiscountPOJO.setUpdatestatus(discount.getUpdatestatus());
                    discountPOJOs.add(articleDiscountPOJO);
                }

                return discountPOJOs;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ******************************* METHOD TO GET DELAY DISCOUNT FROM
     * WEBSERVICE RESULT *********************************
     */
    public ArrayList<ArticleDiscountPOJO> getArticleDelayDiscount() {
        try {
            ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.DelayDiscount> discounts = (ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.DelayDiscount>) result.getDelayDiscount();
            if (discounts != null) {
                ArrayList<ArticleDiscountPOJO> discountPOJOs = new ArrayList<ArticleDiscountPOJO>();
                Iterator iterator = discounts.iterator();
                while (iterator.hasNext()) {
                    in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.DelayDiscount discount = (in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.DelayDiscount) iterator.next();
                    ArticleDiscountPOJO articleDiscountPOJO = new ArticleDiscountPOJO();
                    articleDiscountPOJO.setCondrecno(discount.getCondrecno());
                    articleDiscountPOJO.setCondtype(discount.getCondtype());
                    articleDiscountPOJO.setCalculationtype(discount.getCalctype());
                    if (articleDiscountPOJO.getDeletionind() != null) {
                        articleDiscountPOJO.setDeletionind(articleDiscountPOJO.getDeletionind());
                    } else {
                        articleDiscountPOJO.setDeletionind("");
                    }
                    articleDiscountPOJO.setFromdate(ConvertDate.getDateNumeric(discount.getFromdate().toGregorianCalendar().getTime()));
                    articleDiscountPOJO.setCompCode(discount.getCompcode());
                    articleDiscountPOJO.setSaleOrg(discount.getSalesorg());
                    articleDiscountPOJO.setTodate(ConvertDate.getDateNumeric(discount.getTodate().toGregorianCalendar().getTime()));
                    if (discount.getAmount() != null) {
                        if (discount.getCalctype() != null) {
                            if (discount.getCalctype().equalsIgnoreCase("A")) {
                                articleDiscountPOJO.setDiscount(discount.getAmount().doubleValue() / 10);
                            } else {
                                articleDiscountPOJO.setDiscount(discount.getAmount().doubleValue());
                            }
                        }
                    }

                    discountPOJOs.add(articleDiscountPOJO);
                }

                return discountPOJOs;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ******************************* METHOD TO GET ARTICLE OtherCharges FROM
     * WEBSERVICE RESULT *********************************
     */
    public ArrayList<ArticleOtherChargesPOJO> getArticleOtherCharges() {
        try {
            ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.Others> otherses = (ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.Others>) result.getOthers();
            if (otherses != null) {
                ArrayList<ArticleOtherChargesPOJO> discountPOJOs = new ArrayList<ArticleOtherChargesPOJO>();
                Iterator iterator = otherses.iterator();
                while (iterator.hasNext()) {
                    in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.Others others = (in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.Others) iterator.next();
                    ArticleOtherChargesPOJO articleDiscountPOJO = new ArticleOtherChargesPOJO();
                    articleDiscountPOJO.setCondrecno(others.getCondrecno());
                    articleDiscountPOJO.setCondtype(others.getCondtype());
                    articleDiscountPOJO.setCalculationtype(others.getCalctype());
                    if (articleDiscountPOJO.getDeletionind() != null) {
                        articleDiscountPOJO.setDeletionind(articleDiscountPOJO.getDeletionind());
                    } else {
                        articleDiscountPOJO.setDeletionind("");
                    }
                    articleDiscountPOJO.setFromdate(ConvertDate.getDateNumeric(others.getFromdate().toGregorianCalendar().getTime()));
                    articleDiscountPOJO.setMerchcat(others.getMerchcat());
                    articleDiscountPOJO.setStorecode(others.getSite());
                    articleDiscountPOJO.setTodate(ConvertDate.getDateNumeric(others.getTodate().toGregorianCalendar().getTime()));
                    if (others.getAmount() != null) {
                        if (others.getCalctype() != null) {
                            if (others.getCalctype().equalsIgnoreCase("A")) {
                                articleDiscountPOJO.setOtherChargeValue(others.getAmount().doubleValue() / 10);
                            } else {
                                articleDiscountPOJO.setOtherChargeValue(others.getAmount().doubleValue());
                            }
                        }
                    }
                    articleDiscountPOJO.setUpdatestatus(others.getUpdatestatus());
                    discountPOJOs.add(articleDiscountPOJO);
                }

                return discountPOJOs;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ******************************* METHOD TO GET STOCK MASTER FROM
     * WEBSERVICE RESULT *********************************
     */
    public ArrayList<StockMasterPOJO> getArticleStock() {
        try {
            ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.StockMaster> stock = (ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.StockMaster>) result.getStockMaster();
            if (stock != null) {
                ArrayList<StockMasterPOJO> masterPOJOs = new ArrayList<StockMasterPOJO>();
                Iterator iterator = stock.iterator();
                while (iterator.hasNext()) {
                    in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.StockMaster stockMaster = (in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.StockMaster) iterator.next();
                    StockMasterPOJO stockMasterPOJO = new StockMasterPOJO();
                    stockMasterPOJO.setMaterialcode(stockMaster.getMATNR());
                    stockMasterPOJO.setStorecode(stockMaster.getSITE());
                    stockMasterPOJO.setUpdatestatus(stockMaster.getUPDATESTATUS());
                    if (stockMaster.getMENGE() != null) {
                        stockMasterPOJO.setQuantity(stockMaster.getMENGE().intValue());
                    }
                    stockMasterPOJO.setBatch(stockMaster.getCHARG());
                    stockMasterPOJO.setStorageloc(stockMaster.getLGORT());
                    masterPOJOs.add(stockMasterPOJO);
                }

                return masterPOJOs;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ******************************* METHOD TO GET STOCK MASTER FROM
     * WEBSERVICE RESULT *********************************
     */
    public ArrayList<StockMasterPOJO> getArticleStockGV() {
        try {
            ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.GVStock> stock = (ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.GVStock>) result.getGVStock();
            if (stock != null) {
                ArrayList<StockMasterPOJO> masterPOJOs = new ArrayList<StockMasterPOJO>();
                Iterator iterator = stock.iterator();
                while (iterator.hasNext()) {
                    in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.GVStock gVStock = (in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.GVStock) iterator.next();
                    StockMasterPOJO stockMasterPOJO = new StockMasterPOJO();
                    stockMasterPOJO.setMaterialcode(gVStock.getMATNR());
                    stockMasterPOJO.setStorecode(gVStock.getSITE());
                    stockMasterPOJO.setUpdatestatus(gVStock.getUPDATESTATUS());
                    if (gVStock.getSERNO() != null) {
                        stockMasterPOJO.setGvSerialNo(gVStock.getSERNO());
                    }
                    stockMasterPOJO.setGvStatus(gVStock.getSHORTSTATUS());
                    stockMasterPOJO.setStorageloc("");
                    if (gVStock.getINACTIVE() != null) {
                        stockMasterPOJO.setRecordStatus(gVStock.getINACTIVE());
                    } else {
                        stockMasterPOJO.setRecordStatus("");
                    }
                    masterPOJOs.add(stockMasterPOJO);
                }

                return masterPOJOs;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ******************************* METHOD TO GET ARTICLE TAX FROM
     * WEBSERVICE RESULT *********************************
     */
    public ArrayList<ArticleTaxPOJO> getArticleTax() {
        try {
            ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.TAX> discounts = (ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.TAX>) result.getTAX();
            if (discounts != null) {
                ArrayList<ArticleTaxPOJO> taxPOJOs = new ArrayList<ArticleTaxPOJO>();
                Iterator iterator = discounts.iterator();
                while (iterator.hasNext()) {
                    in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.TAX tax = (in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.TAX) iterator.next();
                    ArticleTaxPOJO articleTaxPOJO = new ArticleTaxPOJO();
                    articleTaxPOJO.setCondrecno(tax.getCondrecno());
                    articleTaxPOJO.setCondtype(tax.getCondtype());
                    articleTaxPOJO.setCalculationtype(tax.getCalctype());
                    articleTaxPOJO.setHsn_sac_code(tax.getHSNSACCODE()); // Added by BALA on 17.6.2016 for GST
                    if (tax.getDeletionind() != null) {
                        articleTaxPOJO.setDeletionind(tax.getDeletionind());
                    } else {
                        articleTaxPOJO.setDeletionind("");
                    }
                    if (tax.getFromdate() != null) {
                        articleTaxPOJO.setFromdate(ConvertDate.getDateNumeric(tax.getFromdate().toGregorianCalendar().getTime()));
                    }
                    articleTaxPOJO.setMerchcat(tax.getMerchcat());
                    articleTaxPOJO.setState(tax.getRegion());
                    articleTaxPOJO.setStorecode(tax.getSite());
                    articleTaxPOJO.setTodate(ConvertDate.getDateNumeric(tax.getTodate().toGregorianCalendar().getTime()));
                    if (tax.getAmount() != null) {
                        if (tax.getCalctype() != null) {
                            if (tax.getCalctype().equalsIgnoreCase("A")) {
                                articleTaxPOJO.setTax(tax.getAmount().doubleValue() / 10);
                            } else {
                                articleTaxPOJO.setTax(tax.getAmount().doubleValue());
                            }
                        }
                    }

                    articleTaxPOJO.setUpdatestatus(tax.getUpdatestatus());
                    taxPOJOs.add(articleTaxPOJO);
                }

                return taxPOJOs;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ******************************* METHOD TO GET ARTICLE PROMOTION HEADER
     * FROM WEBSERVICE RESULT *********************************
     */
    public ArrayList<PromotionHeaderPOJO> getArticlePromotionHeader() {
        try {
            ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.PromotionHeader> promotionHeaders = (ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.PromotionHeader>) result.getPromotionHeader();
            if (promotionHeaders != null) {
                ArrayList<PromotionHeaderPOJO> promotionHeaderPOJOs = new ArrayList<PromotionHeaderPOJO>();
                Iterator iterator = promotionHeaders.iterator();
                while (iterator.hasNext()) {
                    in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.PromotionHeader promotionHeader = (in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.PromotionHeader) iterator.next();
                    PromotionHeaderPOJO promotionHeaderPOJO = new PromotionHeaderPOJO();
                    if (promotionHeader.getSTATUS() != null) {
                        promotionHeaderPOJO.setActive(promotionHeader.getSTATUS());
                    } else {
                        promotionHeaderPOJO.setActive("");
                    }
                    promotionHeaderPOJO.setCondtypeid(promotionHeader.getCONDTYPE());
                    if (promotionHeader.getCREATEDDATE() != null) {
                        promotionHeaderPOJO.setCreateddate(ConvertDate.getDateNumeric(promotionHeader.getCREATEDDATE().toGregorianCalendar().getTime()));
                    } else {
                        promotionHeaderPOJO.setCreateddate(0);
                    }
                    if (promotionHeader.getCREATEDTIME() != null) {
                        promotionHeaderPOJO.setCreatedtime(ConvertDate.getTimeString(promotionHeader.getCREATEDTIME().toGregorianCalendar().getTime()));
                    } else {
                        promotionHeaderPOJO.setCreatedtime("");
                    }
                    promotionHeaderPOJO.setCreateduser(promotionHeader.getCREATEDBY());

                    promotionHeaderPOJO.setDescription(promotionHeader.getPRODESCRIPTION());
                    promotionHeaderPOJO.setFri(promotionHeader.getFRI());
                    if (promotionHeader.getFROMDATE() != null) {
                        promotionHeaderPOJO.setFromdate(ConvertDate.getDateNumeric(promotionHeader.getFROMDATE().toGregorianCalendar().getTime()));
                    }
                    if (promotionHeader.getMODIFIEDDATE() != null) {
                        promotionHeaderPOJO.setModifieddate(ConvertDate.getDateNumeric(promotionHeader.getMODIFIEDDATE().toGregorianCalendar().getTime()));
                    }
                    if (promotionHeader.getMODIFIEDTIME() != null) {
                        promotionHeaderPOJO.setModifiedtime(ConvertDate.getTimeString(promotionHeader.getMODIFIEDTIME().toGregorianCalendar().getTime()));
                    }
                    promotionHeaderPOJO.setModifieduser(promotionHeader.getMODIFIEDBY());
                    promotionHeaderPOJO.setMon(promotionHeader.getMON());
                    //promotionHeaderPOJO.setOpttype(promotionHeader.getOPTTYPE());
                    promotionHeaderPOJO.setOpttype(1);
                    promotionHeaderPOJO.setPromotionid(promotionHeader.getPROMOTIONID());
                    promotionHeaderPOJO.setSat(promotionHeader.getSAT());
                    promotionHeaderPOJO.setStorecode(promotionHeader.getSITE());
                    promotionHeaderPOJO.setSun(promotionHeader.getSUN());
                    promotionHeaderPOJO.setThu(promotionHeader.getTHURS());
                    if (promotionHeader.getTODATE() != null) {
                        promotionHeaderPOJO.setTodate(ConvertDate.getDateNumeric(promotionHeader.getTODATE().toGregorianCalendar().getTime()));
                    }
                    promotionHeaderPOJO.setTue(promotionHeader.getTUES());
                    promotionHeaderPOJO.setUpdatestatus(promotionHeader.getUPDATESTATUS());
                    promotionHeaderPOJO.setWed(promotionHeader.getWED());
                    promotionHeaderPOJO.setIsComboOffer(promotionHeader.getCOMBIND());
                    if (promotionHeader.getMAXPROMVALUE() != null) {
                        promotionHeaderPOJO.setPromMaxValue(promotionHeader.getMAXPROMVALUE().doubleValue());
                    }
                    //Code Added on Jan 28th 2010 for promotion applicabitlity download in promotion header
                    if (promotionHeader.getAPPLICABLETO() != null) {
                        promotionHeaderPOJO.setApplicable_to(promotionHeader.getAPPLICABLETO());
                    } else {
                        promotionHeaderPOJO.setApplicable_to("2");
                    }
                    //Code Added by Mr. Thangaraj for Promotion Approval on 29.02.2016

                    if (promotionHeader.getAPPROVED() != null && !promotionHeader.getAPPROVED().isEmpty()) {
                        promotionHeaderPOJO.setApproved(promotionHeader.getAPPROVED());
                    }

                    if (promotionHeader.getUSAGELIMIT() != null && !promotionHeader.getUSAGELIMIT().isEmpty()) {
                        promotionHeaderPOJO.setUsage_limit(promotionHeader.getUSAGELIMIT());
                    }

                    //End of Code Added by Mr. Thangaraj for Promotion Approval on 29.02.2016
                    //Code Added By Mr. Thangaraj on 25.05.2017 for unique Promotion 
                    if (Validations.isFieldNotEmpty(promotionHeader.getCOMBINEPROMO())) {
                        promotionHeaderPOJO.setUnique_promo_ind(promotionHeader.getCOMBINEPROMO());
                    }
                    if (Validations.isFieldNotEmpty(promotionHeader.getNonReturnable())) {
                        promotionHeaderPOJO.setSalereturn_ind(promotionHeader.getNonReturnable());
                    }
                    //End of Code Added By Mr. Thangaraj on 25.05.2017 for unique Promotion 
                    if (Validations.isFieldNotEmpty(promotionHeader.getMAXINVOICEVAL())) {
                        promotionHeaderPOJO.setMaxInvoiceVal(promotionHeader.getMAXINVOICEVAL().doubleValue());
                    }
                    if (Validations.isFieldNotEmpty(promotionHeader.getCustomerID())) {//Added by Balachander V on 18.12.2018 to show promotion for particular customer only
                        promotionHeaderPOJO.setCustomercode(promotionHeader.getCustomerID());
                    }//Ended by Balachander V on 18.12.2018 to show promotion for particular customer only
                    if (Validations.isFieldNotEmpty(promotionHeader.getRestrictLineItem())) {//Added by Balachander V on 10.10.2019 to validate promo applied line item count
                        promotionHeaderPOJO.setRestrictLineItem(promotionHeader.getRestrictLineItem());
                    }
                    if (Validations.isFieldNotEmpty(promotionHeader.getCombineArticles())) {
                        promotionHeaderPOJO.setCombineArticles(promotionHeader.getCombineArticles());
                    }
                    if (Validations.isFieldNotEmpty(promotionHeader.getPreviousOrderValue())) {
                        promotionHeaderPOJO.setEligibleNoOfDays(promotionHeader.getPreviousOrderValue());
                    }
                    if (Validations.isFieldNotEmpty(promotionHeader.getCUSTOMERARTICLESALLOWED())) {//Added by Balachander V on 14.01.2020 to validate customer item allowed in current order
                        promotionHeaderPOJO.setCustomerItemAllowed(promotionHeader.getCUSTOMERARTICLESALLOWED());
                    }
                    if (Validations.isFieldNotEmpty(promotionHeader.getUCPVALUECHECK())) {//Added by Balachander V on 14.01.2020 to validate UCP based on Frame or lens or contact lens 
                        promotionHeaderPOJO.setUcpValueCheck(promotionHeader.getUCPVALUECHECK());
                    }
                    if(Validations.isFieldNotEmpty(promotionHeader.getMAXPROMOVALUECAP())){//VERSION 101
                      promotionHeaderPOJO.setPromMaxCap(Double.parseDouble(promotionHeader.getMAXPROMOVALUECAP()));
                    }
                    promotionHeaderPOJOs.add(promotionHeaderPOJO);
                }
                return promotionHeaderPOJOs;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ******************************* METHOD TO GET ARTICLE PROMOTION SELLING
     * ITEM FROM WEBSERVICE RESULT *********************************
     */
    public ArrayList<PromotionSellingItemPOJO> getArticlePromotionSellingItem() {
        try {
            ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.PromSellItem> promSellItems = (ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.PromSellItem>) result.getPromSellItem();
            if (promSellItems != null) {
                ArrayList<PromotionSellingItemPOJO> promotionSellingItemPOJOs = new ArrayList<PromotionSellingItemPOJO>();
                Iterator iterator = promSellItems.iterator();
                while (iterator.hasNext()) {
                    in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.PromSellItem promSellItem = (in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.PromSellItem) iterator.next();
                    PromotionSellingItemPOJO promotionSellingItemPOJO = new PromotionSellingItemPOJO();
                    promotionSellingItemPOJO.setCalcmethod(promSellItem.getCALCMETHOD());
                    //  promotionSellingItemPOJO.setCondtypeid(promSellItem.get)
                    if (promSellItem.getDAMAGECOUNT() != null) {
                        promotionSellingItemPOJO.setDamagedcount(promSellItem.getDAMAGECOUNT().intValue());
                    }
                    if (promSellItem.getDAMAGEQTY() != null) {
                        promotionSellingItemPOJO.setDamagedqty(promSellItem.getDAMAGEQTY().intValue());
                    }
                    if (promSellItem.getAMOUNT() != null) {
                        promotionSellingItemPOJO.setOfferamount(promSellItem.getAMOUNT().doubleValue());
                    }
                    if (promSellItem.getTINTVAL() != null) {
                        promotionSellingItemPOJO.setTintValue(promSellItem.getTINTVAL().doubleValue());
                    }
                    promotionSellingItemPOJO.setTintCalculationType(promSellItem.getTINTCALC());
                    if (promSellItem.getOFFERMAXRATE() != null) {
                        promotionSellingItemPOJO.setOffermaxrate(promSellItem.getOFFERMAXRATE().doubleValue());
                    }
                    promotionSellingItemPOJO.setPromotionid(promSellItem.getPROMOTIONID());
                    if (promSellItem.getSELLART() != null) {
                        promotionSellingItemPOJO.setSellingarticle(promSellItem.getSELLART());
                    } else {
                        promotionSellingItemPOJO.setSellingarticle("");
                    }
                    promotionSellingItemPOJO.setSellinglineno(promSellItem.getPROMOTIONLINENO());
                    if (promSellItem.getSELLMAXRATE() != null) {
                        promotionSellingItemPOJO.setSellingmaxrate(promSellItem.getSELLMAXRATE().doubleValue());
                    }
                    if (promSellItem.getSELLMINRATE() != null) {
                        promotionSellingItemPOJO.setSellingminrate(promSellItem.getSELLMINRATE().doubleValue());
                    }
                    promotionSellingItemPOJO.setSellingmerch(promSellItem.getSELLMERCH());
                    if (promSellItem.getSELLQTY() != null) {
                        promotionSellingItemPOJO.setSellingqty(promSellItem.getSELLQTY().intValue());
                    }
                    promotionSellingItemPOJO.setStorecode(promSellItem.getSITE());
                    promotionSellingItemPOJO.setUpdatestatus(promSellItem.getUPDATIONSTATUS());
                    if (promSellItem.getSTATUS() != null) {
                        promotionSellingItemPOJO.setDeletionind(promSellItem.getSTATUS());
                    } else {
                        promotionSellingItemPOJO.setDeletionind("");
                    }
                    promotionSellingItemPOJOs.add(promotionSellingItemPOJO);
                }

                return promotionSellingItemPOJOs;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ******************************* METHOD TO GET ARTICLE PROMOTION SELLING
     * ITEM FROM WEBSERVICE RESULT *********************************
     */
    public ArrayList<PromotionOfferItemPOJO> getArticlePromotionOfferItem() {
        try {
            ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.Promofferitem> promofferitems = (ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.Promofferitem>) result.getPromofferitem();
            if (promofferitems != null) {
                ArrayList<PromotionOfferItemPOJO> promotionOfferItemPOJOs = new ArrayList<PromotionOfferItemPOJO>();
                Iterator iterator = promofferitems.iterator();
                while (iterator.hasNext()) {
                    in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.Promofferitem promofferitem = (in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.Promofferitem) iterator.next();
                    PromotionOfferItemPOJO promotionOfferItemPOJO = new PromotionOfferItemPOJO();
                    promotionOfferItemPOJO.setCalcmethod(promofferitem.getCALCMETHOD());
                    if (promofferitem.getSTATUS() != null) {
                        promotionOfferItemPOJO.setDeletionind(promofferitem.getSTATUS());
                    } else {
                        promotionOfferItemPOJO.setDeletionind("");
                    }
                    promotionOfferItemPOJO.setFreegoodscategory(promofferitem.getFREECAT());
                    try {
                        promotionOfferItemPOJO.setFyear(Integer.parseInt(promofferitem.getFYEAR()));
                    } catch (Exception e) {
                    }
                    if (promofferitem.getAMOUNT() != null) {
                        promotionOfferItemPOJO.setOfferamount(promofferitem.getAMOUNT().doubleValue());
                    }

                    if (promofferitem.getOFFERMINRATE() != null) {
                        promotionOfferItemPOJO.setOfferMinRate(promofferitem.getOFFERMINRATE().doubleValue());
                    }
                    if (promofferitem.getTINTVAL() != null) {
                        promotionOfferItemPOJO.setTintValue(promofferitem.getTINTVAL().doubleValue());
                    }
                    promotionOfferItemPOJO.setTintCalculationType(promofferitem.getTINTCALC());
                    if (promofferitem.getOFFERART() != null) {
                        promotionOfferItemPOJO.setOfferarticle(promofferitem.getOFFERART());
                    } else {
                        promotionOfferItemPOJO.setOfferarticle(promofferitem.getOFFERART());
                    }
                    promotionOfferItemPOJO.setOfferlineno(promofferitem.getPROMOTIONLINENO());
                    if (promofferitem.getOFFERMAXRATE() != null) {
                        promotionOfferItemPOJO.setOffermaxrate(promofferitem.getOFFERMAXRATE().doubleValue());
                    }
                    promotionOfferItemPOJO.setOffermerch(promofferitem.getOFFERMERCH());
                    if (promofferitem.getOFFERQTY() != null) {
                        promotionOfferItemPOJO.setOfferqty(promofferitem.getOFFERQTY().intValue());
                    }
                    promotionOfferItemPOJO.setPromotionid(promofferitem.getPROMOTIONID());
                    promotionOfferItemPOJO.setStorecode(promofferitem.getSITE());
                    promotionOfferItemPOJO.setUpdatestatus(promofferitem.getUPDATESTATUS());
                    promotionOfferItemPOJOs.add(promotionOfferItemPOJO);
                }
                return promotionOfferItemPOJOs;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ******************************* METHOD TO GET SITE FROM WEBSERVICE
     * RESULT *********************************
     */
    public SiteMasterPOJO getSite() {

        try {
            in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.SiteMaster sitemaster = result.getSiteMaster();
            if (sitemaster != null) {
                SiteMasterPOJO siteMasterPOJO1 = new SiteMasterPOJO();
                if (sitemaster.getCplimit() != null) {
                    siteMasterPOJO1.setCash_payout_limit(sitemaster.getCplimit().doubleValue());
                }
                siteMasterPOJO1.setCfa_contact_email(sitemaster.getCfaEmail());
                siteMasterPOJO1.setCfa_contact_telephone(sitemaster.getCfaTelno());
                siteMasterPOJO1.setSite_contact_fax(sitemaster.getSFax());
                siteMasterPOJO1.setCompanycode(sitemaster.getCompcode());
                siteMasterPOJO1.setCorresponding_cfa_id(sitemaster.getCfaid());
                siteMasterPOJO1.setCorresponding_regoffice_name(sitemaster.getRegoffname());
                siteMasterPOJO1.setCreditnotevalidityduration(sitemaster.getCreditvalperiod());
                siteMasterPOJO1.setCreditnotevalidityindicator(sitemaster.getCreditvaltype());
                siteMasterPOJO1.setCstno(sitemaster.getCstno());
                siteMasterPOJO1.setCurrency(sitemaster.getCurr());
                siteMasterPOJO1.setFiscalyear(sitemaster.getFyear());
                if (sitemaster.getInnagdate() != null) {
                    siteMasterPOJO1.setInaugurationdate(ConvertDate.getDateNumeric(sitemaster.getInnagdate().toGregorianCalendar().getTime()));
                }
                siteMasterPOJO1.setIpaddress(sitemaster.getIpaddress());
                siteMasterPOJO1.setMarquee(sitemaster.getMarquee());
                siteMasterPOJO1.setMinadvancepercentage(sitemaster.getMinadvper());
                siteMasterPOJO1.setNum_rangelogic(sitemaster.getNRLogic());
                siteMasterPOJO1.setPan_no(sitemaster.getPanno());
                if (sitemaster.getPassvalid() != null) {
                    siteMasterPOJO1.setPasswordvaliditydate(ConvertDate.getDateNumeric(sitemaster.getPassvalid().toGregorianCalendar().getTime()));
                }
                //siteMasterPOJO1.setPasswordvalidityindicator(sitemaster.getP);
                siteMasterPOJO1.setPospatchversion(sitemaster.getPatchversion());
                if (sitemaster.getPostdate() != null) {
                    siteMasterPOJO1.setPostingdate(ConvertDate.getDateNumeric(sitemaster.getPostdate().toGregorianCalendar().getTime()));
                }
                siteMasterPOJO1.setPostingindicator(sitemaster.getPostind());
                siteMasterPOJO1.setPosversion(sitemaster.getPosversion());
                siteMasterPOJO1.setPromotionordiscount(sitemaster.getOffertype());
                siteMasterPOJO1.setQuotationvalidityperiod(sitemaster.getQuotvalidperoid());
                siteMasterPOJO1.setQuotationvalidityindicator(sitemaster.getQuotvalidtype());
                siteMasterPOJO1.setRegdoffaddress(sitemaster.getRegoffadd());
                siteMasterPOJO1.setRegoff_contact_email(sitemaster.getRegmail());
                siteMasterPOJO1.setRegoff_contact_telephone(sitemaster.getRegtel());
                if (sitemaster.getRoundoffval() != null) {
                    siteMasterPOJO1.setRoundoffvalue(sitemaster.getRoundoffval().doubleValue());
                }
                siteMasterPOJO1.setSite_contact_email(sitemaster.getSEmail());
                siteMasterPOJO1.setSite_contact_telephone(sitemaster.getSTelno());
                siteMasterPOJO1.setSiteaddress_area(sitemaster.getSArea());
                siteMasterPOJO1.setSiteaddress_city(sitemaster.getSCity());
                siteMasterPOJO1.setSiteaddress_country(sitemaster.getSCounty());
                siteMasterPOJO1.setSiteaddress_housenumber(sitemaster.getSHsno());
                siteMasterPOJO1.setSiteaddress_postalcode(sitemaster.getSPostcd());
                siteMasterPOJO1.setSiteaddress_state(sitemaster.getSState());
                siteMasterPOJO1.setSiteaddress_street(sitemaster.getSStreet());
                siteMasterPOJO1.setSitedescription(sitemaster.getSitedesc());
                siteMasterPOJO1.setSiteid(sitemaster.getSiteid());
                //siteMasterPOJO1.setSystemdate(systemdate);
                siteMasterPOJO1.setTax_calculation_indicator(sitemaster.getTaxcalcInd());
                siteMasterPOJO1.setTransaction_pwd(sitemaster.getTranspswd());
                siteMasterPOJO1.setVat_tin_no(sitemaster.getVatno());
                siteMasterPOJO1.setZone(sitemaster.getZone());
                siteMasterPOJO1.setDeletionInd(sitemaster.getBlockind());
                siteMasterPOJO1.setLbt_no(sitemaster.getLstno()); //Added by Dileep - 24.07.2014
                siteMasterPOJO1.setStore_revw_link(sitemaster.getReviewLink());
                siteMasterPOJO1.setTaxFlowIndicator(sitemaster.getCompositeScheme());//Added by Balachander V on 04.05.2019 to enable or disable tax flow based on indicator value for all billings
                return siteMasterPOJO1;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ******************************* METHOD TO GET SITE FORCE RELEASE FROM
     * WEBSERVICE RESULT *********************************
     */
    public ArrayList<SiteForceReleasePOJO> getSiteForceRelease() {
        ArrayList<SiteForceReleasePOJO> siteForceReleasePOJOs = null;
        try {
            ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.SiteFrel> siteFrels = (ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.SiteFrel>) result.getSiteFrel();
            if (siteFrels != null) {
                Iterator iterator = siteFrels.iterator();
                siteForceReleasePOJOs = new ArrayList<SiteForceReleasePOJO>();
                while (iterator.hasNext()) {
                    SiteForceReleasePOJO siteForceReleasePOJO = new SiteForceReleasePOJO();
                    in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.SiteFrel siteFrel = (in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.SiteFrel) iterator.next();
                    try {
                        if (siteFrel.getCURRENTCOUNT() != null) {
                            siteForceReleasePOJO.setCurrentcount(Integer.parseInt(siteFrel.getCURRENTCOUNT()));
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (siteFrel.getFORCERELQUANTITY() != null) {
                            siteForceReleasePOJO.setForcerelease_quantity(Integer.parseInt(siteFrel.getFORCERELQUANTITY()));
                        }
                    } catch (Exception e) {
                    }
                    if (siteFrel.getFORMDATE() != null) {
                        siteForceReleasePOJO.setFromdate(ConvertDate.getDateNumeric(siteFrel.getFORMDATE().toGregorianCalendar().getTime()));
                    }
                    siteForceReleasePOJO.setSiteId(siteFrel.getSITEID());
                    try {
                        if (siteFrel.getSINO() != null) {
                            siteForceReleasePOJO.setSlno(Integer.parseInt(siteFrel.getSINO()));
                        }
                    } catch (Exception e) {
                    }
                    if (siteFrel.getTODATE() != null) {
                        siteForceReleasePOJO.setTodate(ConvertDate.getDateNumeric(siteFrel.getTODATE().toGregorianCalendar().getTime()));
                    }
                    siteForceReleasePOJO.setUpdateStatus(siteFrel.getUPDATESTATUS());
                    if (siteFrel.getCHECKBOX() != null) {
                        siteForceReleasePOJO.setRecordStatus(siteFrel.getCHECKBOX());
                    } else {
                        siteForceReleasePOJO.setRecordStatus("");
                    }

                    siteForceReleasePOJOs.add(siteForceReleasePOJO);
                }
                return siteForceReleasePOJOs;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * ******************************* METHOD TO GET SITE HOLIDAY CALENDER FROM
     * WEBSERVICE RESULT *********************************
     */
    public ArrayList<SiteHolidayCalenderPOJO> getSiteHolidayCalender() {
        ArrayList<SiteHolidayCalenderPOJO> siteHolidayCalenderPOJOs = null;
        try {
            ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.SiteHcal> siteHcals = (ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.SiteHcal>) result.getSiteHcal();
            siteHolidayCalenderPOJOs = new ArrayList<SiteHolidayCalenderPOJO>();
            if (siteHcals != null) {
                Iterator iterator = siteHcals.iterator();
                while (iterator.hasNext()) {
                    SiteHolidayCalenderPOJO siteHolidayCalenderPOJO = new SiteHolidayCalenderPOJO();
                    in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.SiteHcal siteHcal = (in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.SiteHcal) iterator.next();
                    if (siteHcal.getFDATE() != null) {
                        siteHolidayCalenderPOJO.setDate(ConvertDate.getDateNumeric(siteHcal.getFDATE().toGregorianCalendar().getTime()));
                    }
                    siteHolidayCalenderPOJO.setDescription(siteHcal.getDESCRIPTION());
                    siteHolidayCalenderPOJO.setSiteId(siteHcal.getSITEID());
                    try {
                        if (siteHcal.getSINO() != null) {
                            siteHolidayCalenderPOJO.setSlno(Integer.parseInt(siteHcal.getSINO()));
                        }
                    } catch (Exception e) {
                    }
                    siteHolidayCalenderPOJO.setUpdateStatus(siteHcal.getUPDATESTATUS());
                    siteHolidayCalenderPOJO.setYear(siteHcal.getFYEAR());
                    siteHolidayCalenderPOJOs.add(siteHolidayCalenderPOJO);
                }
                return siteHolidayCalenderPOJOs;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * ******************************* METHOD TO GET SITE CREDIT SALE REFERENCE
     * FROM WEBSERVICE RESULT *********************************
     */
    public ArrayList<SiteCreditSaleReference> getSiteCreditSaleReference() {
        ArrayList<SiteCreditSaleReference> siteCreditSaleReferences = null;
        try {
            if (result.getSiteCsal() != null) {
                ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.SiteCsal> siteCsals = (ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.SiteCsal>) result.getSiteCsal();
                siteCreditSaleReferences = new ArrayList<SiteCreditSaleReference>();
                if (siteCsals != null) {
                    Iterator iterator = siteCsals.iterator();
                    while (iterator.hasNext()) {
                        SiteCreditSaleReference siteCreditSaleReference = new SiteCreditSaleReference();
                        in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.SiteCsal siteCsal = (in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.SiteCsal) iterator.next();
                        try {
                            if (siteCsal.getCONDNO() != null) {
                                siteCreditSaleReference.setConditionno(Integer.parseInt(siteCsal.getCONDNO()));
                            }
                        } catch (Exception e) {
                        }
                        if (siteCsal.getFROMDATE() != null) {
                            siteCreditSaleReference.setFromdate(ConvertDate.getDateNumeric(siteCsal.getFROMDATE().toGregorianCalendar().getTime()));
                        }
                        siteCreditSaleReference.setInstitutionname(siteCsal.getName1());
                        siteCreditSaleReference.setSapcustomerno(siteCsal.getKUNNR());
                        siteCreditSaleReference.setSiteId(siteCsal.getSITEID());
                        try {
                            if (siteCsal.getSINO() != null) {
                                siteCreditSaleReference.setSlno(Integer.parseInt(siteCsal.getSINO()));
                            }
                        } catch (Exception e) {
                        }
                        if (siteCsal.getTODATE() != null) {
                            siteCreditSaleReference.setTodate(ConvertDate.getDateNumeric(siteCsal.getTODATE().toGregorianCalendar().getTime()));
                        }
                        siteCreditSaleReference.setUpdatestatus(siteCsal.getUPDATESTATUS());
                        if (siteCsal.getCHECKBOX() != null) {
                            siteCreditSaleReference.setRecordStatus(siteCsal.getCHECKBOX());
                        } else {
                            siteCreditSaleReference.setRecordStatus("");
                        }
                        siteCreditSaleReferences.add(siteCreditSaleReference);
                    }
                }
                return siteCreditSaleReferences;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * ******************************* METHOD TO GET EMPLOYEE MASTER FROM
     * WEBSERVICE RESULT *********************************
     */
    public ArrayList<EmployeeMasterPOJO> getEmployeeMaster() {
        try {
            ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.EmployeeMaster> employeeMasters = (ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.EmployeeMaster>) result.getEmployeeMaster();
            ArrayList<EmployeeMasterPOJO> employeeMasterPOJOs = new ArrayList<EmployeeMasterPOJO>();
            if (employeeMasters != null) {
                Iterator iterator = employeeMasters.iterator();
                while (iterator.hasNext()) {
                    EmployeeMasterPOJO employeeMasterPOJO = new EmployeeMasterPOJO();
                    in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.EmployeeMaster employeeMaster = (in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.EmployeeMaster) iterator.next();
                    employeeMasterPOJO.setDesignation(employeeMaster.getDESIGNATION());
                    employeeMasterPOJO.setEmpid(employeeMaster.getEMPID());
                    employeeMasterPOJO.setFirstName(employeeMaster.getFIRSTNAME());
                    try {
                        if (employeeMaster.getFISCALYEAR() != null) {
                            employeeMasterPOJO.setFiscalyear(Integer.parseInt(employeeMaster.getFISCALYEAR()));
                        }
                    } catch (Exception e) {
                    }
                    employeeMasterPOJO.setLastName(employeeMaster.getLASTNAME());
                    employeeMasterPOJO.setStatus(employeeMaster.getSTATUS());
                    employeeMasterPOJO.setStorecode(employeeMaster.getSTORECODE());
                    employeeMasterPOJO.setTitle(employeeMaster.getTITLE());
                    employeeMasterPOJO.setUserId(employeeMaster.getUSERID());
                    employeeMasterPOJO.setPassWord("init");
                    employeeMasterPOJO.setPassWordValidto(99991231);
                    if (employeeMaster.getDESIGNATION().equalsIgnoreCase("SM")) {
                        employeeMasterPOJO.setRoleIdAssigned(2);
                    } else if (employeeMaster.getDESIGNATION().equalsIgnoreCase("CH")) {
                        employeeMasterPOJO.setRoleIdAssigned(3);
                    } else if (employeeMaster.getDESIGNATION().equalsIgnoreCase("SC")) {
                        employeeMasterPOJO.setRoleIdAssigned(4);
                    } else if (employeeMaster.getDESIGNATION().equalsIgnoreCase("OP")) {
                        employeeMasterPOJO.setRoleIdAssigned(0);
                    } else if (employeeMaster.getDESIGNATION().equalsIgnoreCase("CP")) {
                        employeeMasterPOJO.setRoleIdAssigned(5);
                    } else if (employeeMaster.getDESIGNATION().equalsIgnoreCase("SO")) {
                        employeeMasterPOJO.setRoleIdAssigned(2);
                    } else {
                        employeeMasterPOJO.setRoleIdAssigned(0);
                    }
                    employeeMasterPOJOs.add(employeeMasterPOJO);
                }
                return employeeMasterPOJOs;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ******************************* METHOD TO GET EMPLOYEE MASTER FROM
     * WEBSERVICE RESULT *********************************
     */
    public ArrayList<ConditionTypeMasterPOJO> getConditionTypeMaster() {
        try {
            ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.ConditionMap> conditionMaps = (ArrayList<in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.ConditionMap>) result.getConditionMap();
            ArrayList<ConditionTypeMasterPOJO> conditionTypeMasterPOJOs = new ArrayList<ConditionTypeMasterPOJO>();
            if (conditionMaps != null) {
                Iterator iterator = conditionMaps.iterator();
                while (iterator.hasNext()) {
                    ConditionTypeMasterPOJO conditionTypeMasterPOJO = new ConditionTypeMasterPOJO();
                    in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.ConditionMap conditionMap = (in.co.titan.highfrequencydata.DTHighFrequencyDataResponse.ConditionMap) iterator.next();
                    conditionTypeMasterPOJO.setBaseTax(conditionMap.getBasetax());
                    conditionTypeMasterPOJO.setCondType(conditionMap.getStandcond());
                    conditionTypeMasterPOJO.setCondTypeDescriptiion(conditionMap.getVtext());
                    conditionTypeMasterPOJO.setConditionCategory(conditionMap.getCondcat());
                    conditionTypeMasterPOJO.setCountry(conditionMap.getLand());
                    if (conditionMap.getMaxamount() != null) {
                        conditionTypeMasterPOJO.setMaxAmount(conditionMap.getMaxamount().doubleValue());
                    }
                    conditionTypeMasterPOJO.setPosCondType(conditionMap.getManualcond());
                    conditionTypeMasterPOJO.setRegion(conditionMap.getRegio());
                    conditionTypeMasterPOJO.setTaxCode(conditionMap.getTaxind());
                    conditionTypeMasterPOJO.setUpdateStatus(conditionMap.getStatus());
                    conditionTypeMasterPOJOs.add(conditionTypeMasterPOJO);
                }
                return conditionTypeMasterPOJOs;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setErrorLog(String shortname, String key2, String key3, String key4, String key5, String key6, String desc) {
        MsdeConnection mc;
        Connection con = null;
        try {
            mc = new MsdeConnection();
            con = mc.createConnection();
            ErrorLogDO errorLogDO = new ErrorLogDO();
            ErrorLogPOJO errorLogPOJO = new ErrorLogPOJO();
            errorLogPOJO.setShortname(shortname);
            errorLogPOJO.setKey1(ServerConsole.siteID);
            if (key2 != null) {
                errorLogPOJO.setKey2(key2);
            }
            if (key3 != null) {
                errorLogPOJO.setKey3(key3);
            }
            if (key4 != null) {
                errorLogPOJO.setKey4(key4);
            }
            if (key5 != null) {
                errorLogPOJO.setKey5(key5);
            }
            if (key6 != null) {
                errorLogPOJO.setKey6(key6);
            }
            errorLogPOJO.setStatus("F");
            if (desc != null) {
                errorLogPOJO.setDescription(desc);
            }
            if (errorLogDO.updateArticleLog(con, errorLogPOJO) <= 0) {
                errorLogDO.saveArticlesLog(con, errorLogPOJO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int downloadDataFromISR(Connection con) {
        int noOfRowsUpdated = 0;
        ErrorLogPOJO errorLogPOJO;
        getStatusArea().append("\n");
        int rec = 0;
        try {

            if (this.siteMasterFlag != null) {
                if (this.siteMasterFlag.equalsIgnoreCase("X")) {
                    SiteMasterPOJO siteMasterPOJO1 = getSite();
                    try {
                        SiteMasterDO siteMasterDO = new SiteMasterDO();
                        if (siteMasterPOJO1 != null) {
                            if (siteMasterPOJO1.getDeletionInd() != null && siteMasterPOJO1.getDeletionInd().equalsIgnoreCase("X")) {
                                JOptionPane.showMessageDialog(null, "Site " + mysite + " is blocked!", "Error Message", JOptionPane.ERROR_MESSAGE);
                                System.exit(0);
                                ServerConsole.stopServerSocket();
                            } else {
                                System.out.println("inside block : SiteMaster :");
                                if (siteMasterDO.updateSite(con, siteMasterPOJO1) == 0) {
                                    siteMasterDO.insertSite(con, siteMasterPOJO1);
                                    noOfRowsUpdated = noOfRowsUpdated + 1;
                                }
                                logger.error(" INFO :  SiteMaster :Updated");
                                getStatusArea().append("\nSiteMaster :Updated ");

                            }
                        }

                    } catch (SQLException e) {
                        logger.error("EXCEPTION : SiteMaster :  " + e.getMessage());
                        String errormsg = e.getMessage();
                        setErrorLog("SIT", null, null, null, null, null, errormsg);
                    }

                    try {
                        ArrayList<SiteForceReleasePOJO> aspojos = this.getSiteForceRelease();
                        if (aspojos != null) {
                            noOfRowsUpdated = noOfRowsUpdated + aspojos.size();
                            Iterator iterator = aspojos.iterator();
                            SiteMasterDO siteMasterDO = new SiteMasterDO();
                            // siteMasterDO.deleteAllRowsInForceRelease(con);
                            while (iterator.hasNext()) {
                                SiteForceReleasePOJO adpojo = (SiteForceReleasePOJO) iterator.next();
                                try {
                                    if (siteMasterDO.updateSiteForceRelease(con, adpojo) == 0) {
                                        siteMasterDO.insertSiteForceRelease(con, adpojo);
                                    }
                                } catch (SQLException e) {
                                    logger.error("EXCEPTION : Site Force Release :  " + e.getMessage());
                                    String errormsg = e.getMessage();
                                    setErrorLog("SIF", String.valueOf(adpojo.getSlno()), null, null, null, null, errormsg);
                                }
                            }
                            if (aspojos.size() > 0) {
                                logger.error("INFO : Site Force Release : Updated Records :" + aspojos.size());
                                getStatusArea().append("\nSite Force Release :Updated Records :" + aspojos.size());
                            }
                        }
                    } catch (Exception e) {
                    }

                    try {
                        ArrayList<SiteCreditSaleReference> aspojos = this.getSiteCreditSaleReference();
                        if (aspojos != null) {
                            noOfRowsUpdated = noOfRowsUpdated + aspojos.size();
                            Iterator iterator = aspojos.iterator();
                            SiteMasterDO siteMasterDO = new SiteMasterDO();
                            //  siteMasterDO.deleteAllRowsInCreditSaleReference(con);
                            while (iterator.hasNext()) {
                                SiteCreditSaleReference adpojo = (SiteCreditSaleReference) iterator.next();
                                try {
                                    if (siteMasterDO.updateSiteCreditSaleReference(con, adpojo) == 0) {
                                        siteMasterDO.insertSiteCreditSaleReference(con, adpojo);
                                    }
                                } catch (SQLException e) {
                                    String errormsg = e.getMessage();
                                    setErrorLog("SIC", String.valueOf(adpojo.getSlno()), null, null, null, null, errormsg);
                                }
                            }
                            if (aspojos.size() > 0) {
                                logger.error(" INFO :  Site Credit Sale Reference :Updated Records :" + aspojos.size());
                                getStatusArea().append("\nSite Credit Sale Reference :Updated Records :" + aspojos.size());
                            }
                        }
                    } catch (Exception e) {
                        logger.error("EXCEPTION : Site Credit Sale Reference  :  " + e.getMessage());
                        e.printStackTrace();
                    }

                    try {
                        ArrayList<SiteHolidayCalenderPOJO> aspojos = this.getSiteHolidayCalender();
                        if (aspojos != null) {
                            noOfRowsUpdated = noOfRowsUpdated + aspojos.size();
                            Iterator iterator = aspojos.iterator();
                            SiteMasterDO siteMasterDO = new SiteMasterDO();
                            //  siteMasterDO.deleteAllRowsInHolidayCalender(con);
                            while (iterator.hasNext()) {
                                SiteHolidayCalenderPOJO adpojo = (SiteHolidayCalenderPOJO) iterator.next();
                                try {
                                    if (siteMasterDO.updateSiteHolidayCalender(con, adpojo) == 0) {
                                        siteMasterDO.insertSiteHolidayCalender(con, adpojo);
                                    }
                                } catch (SQLException e) {
                                    String errormsg = e.getMessage();
                                    setErrorLog("SIH", String.valueOf(adpojo.getSlno()), null, null, null, null, errormsg);
                                }
                            }
                            if (aspojos.size() > 0) {
                                logger.error(" INFO :  Holiday Calendar :Updated Records :" + aspojos.size());
                                getStatusArea().append("\nHoliday Calendar :Updated Records :" + aspojos.size());
                            }
                        }
                    } catch (Exception e) {
                        logger.error("EXCEPTION : Holiday Calendar  :  " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
            if (articleFlag != null) {
                if (articleFlag.equalsIgnoreCase("X")) {
                    try {
                        ArrayList<ArticleMasterPOJO> ampojos = this.getArticleMaster();
                        if (ampojos != null) {
                            noOfRowsUpdated = noOfRowsUpdated + ampojos.size();
                            Iterator iterator = ampojos.iterator();
                            ArticleDO ado = new ArticleDO();
                            rec = 0;
                            while (iterator.hasNext()) {
                                ArticleMasterPOJO ampojo = (ArticleMasterPOJO) iterator.next();
                                try {
                                    if (ado.updateArticle(con, ampojo) <= 0) {
                                        ado.saveArticle(con, ampojo);
                                    }
                                    rec++;
                                } catch (SQLException e) {
                                    String errormsg = e.getMessage();
                                    setErrorLog("ART", ampojo.getMaterialcode(), null, null, null, null, errormsg);
                                }
                            }
                            // noOfRowsUpdated=noOfRowsUpdated+i;
                            if (rec > 0) {
                                logger.error(" INFO :  Article :Updated Records :" + rec);
                                getStatusArea().append("\nArticle :Updated Records :" + rec);
                            }

                        }
                    } catch (Exception e) {
                        logger.error("EXCEPTION : Article  :  " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
            if (articleCharacteristicsFlag != null) {
                if (articleCharacteristicsFlag.equalsIgnoreCase("X")) {
                    try {
                        ArrayList<ArticleCharacteristicsPOJO> acpojos = this.getArticleCharacterstics();
                        if (acpojos != null) {
                            noOfRowsUpdated = noOfRowsUpdated + acpojos.size();
                            Iterator iterator = acpojos.iterator();
                            ArticleDO ado = new ArticleDO();
                            rec = 0;
                            while (iterator.hasNext()) {
                                ArticleCharacteristicsPOJO acpojo = (ArticleCharacteristicsPOJO) iterator.next();
                                try {
                                    if (ado.updateArticleCharacterstic(con, acpojo) <= 0) {
                                        ado.saveArticleCharacterstic(con, acpojo);
                                    }
                                    rec++;
                                } catch (SQLException e) {
                                    String errormsg = e.getMessage();
                                    setErrorLog("ARC", acpojo.getMaterialcode(), acpojo.getCharacteristics(), acpojo.getValueno(), null, null, errormsg);
                                }
                            }
                            ado.insertBrandAndColorMasterVer2(con);
                            if (rec > 0) {
                                logger.error(" INFO :  Article Characteristics :Updated Records :" + rec);
                                getStatusArea().append("\nArticle Characteristics :Updated Records :" + rec);
                            }

                        }
                    } catch (Exception e) {
                        logger.error("EXCEPTION : Article Characteristics :  " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            if (articleUcpFlag != null) {
                if (articleUcpFlag.equalsIgnoreCase("X")) {
                    try {
                        ArrayList<ArticleUCPPOJO> aucppojos = this.getArticleUCP();
                        boolean autoDownload = false;
                        if (aucppojos != null) {
                            noOfRowsUpdated = noOfRowsUpdated + aucppojos.size();
                            Iterator iterator = aucppojos.iterator();
                            ArticleDO ado = new ArticleDO();
                            while (iterator.hasNext()) {
                                ArticleUCPPOJO aucpcpojo = (ArticleUCPPOJO) iterator.next();
                                if (Validations.isFieldNotEmpty(isAutolReq) && isAutolReq.equalsIgnoreCase("X")) {
                                    autoDownload = true;
                                } else {
                                    autoDownload = false;
                                }

                                try {
                                    if (ado.updateArticleUCP(con, aucpcpojo, autoDownload) <= 0) {
                                        ado.saveArticleUCP(con, aucpcpojo, autoDownload);
                                    }
                                } catch (SQLException e) {
                                    String errormsg = e.getMessage();
                                    setErrorLog("UCP", aucpcpojo.getCondtype(), aucpcpojo.getMaterialcode(), aucpcpojo.getCondrecno(), null, null, errormsg);
                                }
                            }
                            if (aucppojos.size() > 0) {
                                logger.error(" INFO :  UCP :Updated Records    :" + aucppojos.size());
                                getStatusArea().append("\nUCP :Updated Records    :" + aucppojos.size());
                            }

                        }
                    } catch (Exception e) {
                        logger.error("EXCEPTION : UCP :  " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
            if (articleDiscountFlag != null) {
                if (articleDiscountFlag.equalsIgnoreCase("X")) {
                    try {
                        ArrayList<ArticleDiscountPOJO> adpojos = this.getArticleDiscount();
                        if (adpojos != null) {
                            noOfRowsUpdated = noOfRowsUpdated + adpojos.size();
                            Iterator iterator = adpojos.iterator();
                            ArticleDO ado = new ArticleDO();
                            while (iterator.hasNext()) {
                                ArticleDiscountPOJO adpojo = (ArticleDiscountPOJO) iterator.next();
                                try {
                                    if (ado.updateArticleDiscount(con, adpojo) <= 0) {
                                        ado.saveArticleDiscount(con, adpojo);
                                    }
                                } catch (SQLException e) {
                                    String errormsg = e.getMessage();
                                    setErrorLog("DIS", adpojo.getCondtype(), adpojo.getMerchcat(), adpojo.getCondrecno(), null, null, errormsg);
                                }
                            }
                            if (adpojos.size() > 0) {
                                logger.error(" INFO :  Discount :Updated Records  :" + adpojos.size());
                                getStatusArea().append("\nDiscount :Updated Records  :" + adpojos.size());
                            }
                        }
                    } catch (Exception e) {
                        logger.error("EXCEPTION : Discount :  " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            if (otherChargesFlag != null) {
                if (otherChargesFlag.equalsIgnoreCase("X")) {
                    try {
                        ArrayList<ArticleOtherChargesPOJO> adpojos = this.getArticleOtherCharges();
                        if (adpojos != null) {
                            noOfRowsUpdated = noOfRowsUpdated + adpojos.size();
                            Iterator iterator = adpojos.iterator();
                            ArticleDO ado = new ArticleDO();
                            while (iterator.hasNext()) {
                                ArticleOtherChargesPOJO adpojo = (ArticleOtherChargesPOJO) iterator.next();
                                try {
                                    if (ado.updateArticleOtherCharges(con, adpojo) <= 0) {
                                        ado.saveArticleOtherCharges(con, adpojo);
                                    }
                                } catch (Exception e) {
                                    String errormsg = e.getMessage();
                                    setErrorLog("OTH", adpojo.getCondtype(), adpojo.getMerchcat(), adpojo.getCondrecno(), null, null, errormsg);
                                }
                            }
                            if (adpojos.size() > 0) {
                                logger.error(" INFO :  Other Charges :Updated Records :" + adpojos.size());
                                getStatusArea().append("\nOther Charges :Updated Records :" + adpojos.size());
                            }
                        }
                    } catch (Exception e) {
                        logger.error("EXCEPTION : Other Charges  :  " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            if (articleTaxFlag != null) {
                if (articleTaxFlag.equalsIgnoreCase("X")) {
                    try {
                        ArrayList<ArticleTaxPOJO> atpojos = this.getArticleTax();
                        if (atpojos != null) {
                            noOfRowsUpdated = noOfRowsUpdated + atpojos.size();
                            Iterator iterator = atpojos.iterator();
                            ArticleDO ado = new ArticleDO();
                            while (iterator.hasNext()) {
                                ArticleTaxPOJO adpojo = (ArticleTaxPOJO) iterator.next();
                                try {
                                    if (ado.updateArticleTax(con, adpojo) == 0) {

                                        ado.saveArticleTax(con, adpojo);

                                    }
                                } catch (SQLException e) {
                                    String errormsg = e.getMessage();
                                    setErrorLog("TAX", adpojo.getCondtype(), "INDIA", adpojo.getState(), adpojo.getMerchcat(), adpojo.getCondrecno(), errormsg);

                                }
                            }
                            if (atpojos.size() > 0) {
                                logger.error(" INFO :  Tax :Updated Records :" + atpojos.size());
                                getStatusArea().append("\nTax :Updated Records :" + atpojos.size());
                            }

                        }
                    } catch (Exception e) {
                        logger.error("EXCEPTION : Tax :  " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            if (stockMasterFlag != null) {
                if (stockMasterFlag.equalsIgnoreCase("X")) {
                    try {
                        ArrayList<StockMasterPOJO> aspojos = this.getArticleStock();
                        if (aspojos != null) {
                            noOfRowsUpdated = noOfRowsUpdated + aspojos.size();
                            Iterator iterator = aspojos.iterator();
                            StockMasterDO sm = new StockMasterDO();
                            while (iterator.hasNext()) {
                                StockMasterPOJO adpojo = (StockMasterPOJO) iterator.next();
                                //System.out.println("Material: "+adpojo.getMaterialcode()+" Batch :"+adpojo.getBatch()+" Quantity : "+adpojo.getQuantity());
                                try {
                                    if (sm.updateStockDetails(adpojo, con) == 0) {
                                        sm.saveStockDetails(adpojo, con);

                                    }
                                } catch (SQLException e) {
                                    String errormsg = e.getMessage();

                                    setErrorLog("STM", adpojo.getStorageloc(), adpojo.getMaterialcode(), adpojo.getBatch(), null, null, errormsg);
                                }

                            }
                            if (aspojos.size() > 0) {
                                logger.error(" INFO :  Stock :Updated Records :" + aspojos.size());
                                getStatusArea().append("\nStock :Updated Records :" + aspojos.size());
                            }
                        }
                    } catch (Exception e) {
                        logger.error("EXCEPTION : Stock :  " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            if (gvMasterFlag != null) {
                if (gvMasterFlag.equalsIgnoreCase("X")) {
                    try {
                        ArrayList<StockMasterPOJO> aspojos = this.getArticleStockGV();
                        if (aspojos != null) {
                            noOfRowsUpdated = noOfRowsUpdated + aspojos.size();
                            Iterator iterator = aspojos.iterator();
                            StockMasterDO sm = new StockMasterDO();
                            while (iterator.hasNext()) {
                                StockMasterPOJO adpojo = (StockMasterPOJO) iterator.next();
                                try {
                                    if (sm.updateGVStockDetails(adpojo, con) == 0) {

                                        sm.saveGVStockDetails(adpojo, con);

                                    }
                                } catch (SQLException e) {
                                    String errormsg = e.getMessage();
                                    setErrorLog("GVS", adpojo.getMaterialcode(), adpojo.getGvSerialNo(), adpojo.getGvStatus(), null, null, errormsg);
                                }
                            }
                            if (aspojos.size() > 0) {
                                logger.error(" INFO :  GV : Updated Records :" + aspojos.size());
                                getStatusArea().append("\nGV :Updated Records :" + aspojos.size());
                            }

                        }
                    } catch (Exception e) {
                        logger.error("EXCEPTION : GV :  " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            if (employeeMasterFlag != null) {
                if (employeeMasterFlag.equalsIgnoreCase("X")) {
                    try {
                        ArrayList<EmployeeMasterPOJO> aspojos = this.getEmployeeMaster();
                        if (aspojos != null) {
                            noOfRowsUpdated = noOfRowsUpdated + aspojos.size();
                            Iterator iterator = aspojos.iterator();
                            EmployeeMasterDO employeeMasterDO = new EmployeeMasterDO();
                            while (iterator.hasNext()) {
                                EmployeeMasterPOJO em = (EmployeeMasterPOJO) iterator.next();
                                try {
                                    if (employeeMasterDO.updateEmployee(con, em) == 0) {
                                        employeeMasterDO.insertEmployee(con, em);
                                    }
                                } catch (SQLException e) {
                                    String errormsg = e.getMessage();
                                    setErrorLog("EMP", em.getEmpid(), null, null, null, null, errormsg);
                                }

                            }
                            if (aspojos.size() > 0) {
                                logger.error(" INFO :  Employee :Updated Records :" + aspojos.size());
                                getStatusArea().append("\nEmployee :Updated Records :" + aspojos.size());
                            }
                        }
                    } catch (Exception e) {
                        logger.error("EXCEPTION : Employee :  " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            if (promotionsFlag != null) {
                if (promotionsFlag.equalsIgnoreCase("X")) {
                    try {
                        ArrayList<PromotionHeaderPOJO> phpojos = this.getArticlePromotionHeader();
                        if (phpojos != null) {
                            Iterator iterator = phpojos.iterator();
                            noOfRowsUpdated = noOfRowsUpdated + phpojos.size();
                            ArticleDO articleDO = new ArticleDO();
                            while (iterator.hasNext()) {
                                PromotionHeaderPOJO em = (PromotionHeaderPOJO) iterator.next();
                                try {
                                    if (articleDO.updateArticlePromotionHeader(con, em) == 0) {
                                        articleDO.saveArticlePromotionHeader(con, em);
                                    }
                                } catch (SQLException e) {
                                    String errormsg = e.getMessage();
                                    setErrorLog("PRH", em.getPromotionid(), null, null, null, null, errormsg);
                                }

                            }
                            if (phpojos.size() > 0) {
                                logger.error(" INFO :  Promotion :Updated Records :" + phpojos.size());
                                getStatusArea().append("\nPromotion :Updated Records :" + phpojos.size());
                            }
                        }
                    } catch (Exception e) {
                        logger.error("EXCEPTION : Promotion Header :  " + e.getMessage());
                        e.printStackTrace();
                    }

                    try {
                        ArrayList<PromotionSellingItemPOJO> pspojos = this.getArticlePromotionSellingItem();
                        if (pspojos != null) {
                            Iterator iterator = pspojos.iterator();
                            ArticleDO articleDO = new ArticleDO();
                            while (iterator.hasNext()) {
                                PromotionSellingItemPOJO em = (PromotionSellingItemPOJO) iterator.next();
                                try {
                                    if (articleDO.updateArticlePromotionSellingItem(con, em) == 0) {
                                        articleDO.saveArticlePromotionSellingItem(con, em);
                                    }
                                } catch (SQLException e) {
                                    String errormsg = e.getMessage();
                                    setErrorLog("PRS", em.getPromotionid(), em.getSellinglineno(), null, null, null, errormsg);
                                }

                            }

                        }
                    } catch (Exception e) {
                        logger.error("EXCEPTION : Promotion selling lineitem:  " + e.getMessage());
                        e.printStackTrace();
                    }

                    try {
                        ArrayList<PromotionOfferItemPOJO> pspojos = this.getArticlePromotionOfferItem();
                        if (pspojos != null) {
                            Iterator iterator = pspojos.iterator();
                            ArticleDO articleDO = new ArticleDO();
                            while (iterator.hasNext()) {
                                PromotionOfferItemPOJO em = (PromotionOfferItemPOJO) iterator.next();
                                try {
                                    if (articleDO.updateArticlePromotionOfferItem(con, em) == 0) {
                                        articleDO.saveArticlePromotionOfferItem(con, em);
                                    }
                                } catch (SQLException e) {
                                    String errormsg = e.getMessage();
                                    setErrorLog("PRO", em.getPromotionid(), em.getOfferlineno(), null, null, null, errormsg);
                                }

                            }

                        }
                    } catch (Exception e) {
                        logger.error("EXCEPTION : Promotion Offer :  " + e.getMessage());
                        e.printStackTrace();
                    }

                }
            }

            if (getConditionTypeMasterFlag() != null) {
                if (getConditionTypeMasterFlag().equalsIgnoreCase("X")) {
                    try {
                        ArrayList<ConditionTypeMasterPOJO> aspojos = this.getConditionTypeMaster();
                        if (aspojos != null) {
                            noOfRowsUpdated = noOfRowsUpdated + aspojos.size();
                            Iterator iterator = aspojos.iterator();
                            ArticleDO articleDO = new ArticleDO();
                            while (iterator.hasNext()) {
                                ConditionTypeMasterPOJO em = (ConditionTypeMasterPOJO) iterator.next();
                                try {
                                    if (articleDO.updateArticleConditionTypeMaster(con, em) == 0) {
                                        articleDO.saveArticleConditionTypeMaster(con, em);
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    String errormsg = e.getMessage();
                                    setErrorLog("CMT", em.getCondType(), em.getCountry(), em.getRegion(), null, null, errormsg);

                                }
                            }
                            if (aspojos.size() > 0) {
                                logger.error(" INFO :  Condition Type master :Updated Records :" + aspojos.size());
                                getStatusArea().append("\nCondition Type master :Updated Records :" + aspojos.size());
                            }
                        }
                    } catch (Exception e) {
                        logger.error("EXCEPTION : Condition Type master :  " + e.getMessage());
                        e.printStackTrace();
                    }

                    // ******************** Delay in delivery discount
                    try {
                        ArrayList<ArticleDiscountPOJO> adpojos = this.getArticleDelayDiscount();
                        if (adpojos != null) {
                            noOfRowsUpdated = noOfRowsUpdated + adpojos.size();
                            Iterator iterator = adpojos.iterator();
                            ArticleDO ado = new ArticleDO();
                            while (iterator.hasNext()) {
                                ArticleDiscountPOJO adpojo = (ArticleDiscountPOJO) iterator.next();
                                try {
                                    if (ado.updateArticleDelayDiscount(con, adpojo) <= 0) {
                                        ado.saveArticleDelayDiscount(con, adpojo);
                                    }
                                } catch (SQLException e) {
                                    String errormsg = e.getMessage();
                                    setErrorLog("DDL", adpojo.getCondtype(), adpojo.getCompCode(), adpojo.getSaleOrg(), adpojo.getCondrecno(), null, errormsg);

                                }
                            }
                            if (adpojos.size() > 0) {
                                logger.error(" INFO :  Delay in delivery discount :Updated Records :" + adpojos.size());
                                getStatusArea().append("\nDelay in delivery discount :Updated Records :" + adpojos.size());
                            }

                        }

                    } catch (Exception e) {
                        logger.error("EXCEPTION : Delay in delivery discount :  " + e.getMessage());
                        e.printStackTrace();
                    }

                }
            }

            if (getSystemDateFlag() != null && getSystemDateFlag().equalsIgnoreCase("X")) {
                CheckSystemDate checkSystemDate = new CheckSystemDate();
                checkSystemDate.getBusinessDateFromWebservice();
                if (checkSystemDate.getSystemDate() != 0) {
                    new SiteMasterDO().updateSystemAndPostingDates(con, checkSystemDate.getSystemDate(), checkSystemDate.getBusinessDate(), checkSystemDate.getFiscalYear());
                    noOfRowsUpdated++;
                    logger.error(" INFO :  SystemDate :Updated");
                    getStatusArea().append("\nSystemDate :Updated ");
                }
            }

//        try {
//            MsdeConnection connection = new MsdeConnection();
//            Connection con = connection.createConnection();
//            CheckSystemDate checkSystemDate = new CheckSystemDate();
//            checkSystemDate.getBusinessDateFromWebservice();
//            new SiteMasterDO().updateSystemAndPostingDates(con, checkSystemDate.getSystemDate(), checkSystemDate.getBusinessDate(), checkSystemDate.getFiscalYear());
//        } catch (Exception e) {
//
//        }
        } catch (Exception e) {
            logger.error("Exception in downloadDataFromISR method : " + e.getMessage());
            e.printStackTrace();
        }

        return noOfRowsUpdated;
    }

    public String getArticleFlag() {
        return articleFlag;
    }

    public void setArticleFlag(String articleFlag) {
        this.articleFlag = articleFlag;
    }

    public String getArticleUcpFlag() {
        return articleUcpFlag;
    }

    public void setArticleUcpFlag(String articleUcpFlag) {
        this.articleUcpFlag = articleUcpFlag;
    }

    public String getArticleTaxFlag() {
        return articleTaxFlag;
    }

    public void setArticleTaxFlag(String articleTaxFlag) {
        this.articleTaxFlag = articleTaxFlag;
    }

    public String getArticleDiscountFlag() {
        return articleDiscountFlag;
    }

    public void setArticleDiscountFlag(String articleDiscountFlag) {
        this.articleDiscountFlag = articleDiscountFlag;
    }

    public String getArticleCharacteristicsFlag() {
        return articleCharacteristicsFlag;
    }

    public void setArticleCharacteristicsFlag(String articleCharacteristicsFlag) {
        this.articleCharacteristicsFlag = articleCharacteristicsFlag;
    }

    public String getStockMasterFlag() {
        return stockMasterFlag;
    }

    public void setStockMasterFlag(String stockMasterFlag) {
        this.stockMasterFlag = stockMasterFlag;
    }

    public String getSiteMasterFlag() {
        return siteMasterFlag;
    }

    public void setSiteMasterFlag(String siteMasterFlag) {
        this.siteMasterFlag = siteMasterFlag;
    }

    public String getEmployeeMasterFlag() {
        return employeeMasterFlag;
    }

    public void setEmployeeMasterFlag(String employeeMasterFlag) {
        this.employeeMasterFlag = employeeMasterFlag;
    }

    public String getOtherChargesFlag() {
        return otherChargesFlag;
    }

    public void setOtherChargesFlag(String otherChargesFlag) {
        this.otherChargesFlag = otherChargesFlag;
    }

    public String getPromotionsFlag() {
        return promotionsFlag;
    }

    public void setPromotionsFlag(String promotionsFlag) {
        this.promotionsFlag = promotionsFlag;
    }

    public String getGvMasterFlag() {
        return gvMasterFlag;
    }

    public void setGvMasterFlag(String gvMasterFlag) {
        this.gvMasterFlag = gvMasterFlag;
    }

    public JTextArea getStatusArea() {
        return statusArea;
    }

    public void setStatusArea(JTextArea statusArea) {
        this.statusArea = statusArea;
    }

    public String getConditionTypeMasterFlag() {
        return conditionTypeMasterFlag;
    }

    public void setConditionTypeMasterFlag(String conditionTypeMasterFlag) {
        this.conditionTypeMasterFlag = conditionTypeMasterFlag;
    }

    public String getSystemDateFlag() {
        return systemDateFlag;
    }

    public void setSystemDateFlag(String systemDateFlag) {
        this.systemDateFlag = systemDateFlag;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getIsAutoReq() {
        return isAutolReq;
    }

    public void setIsAutoReq(String isAutolReq) {
        this.isAutolReq = isAutolReq;
    }
}
