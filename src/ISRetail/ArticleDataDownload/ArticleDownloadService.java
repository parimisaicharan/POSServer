/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ISRetail.ArticleDataDownload;

import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.article.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import ISRetail.Helpers.ConvertDate;
import ISRetail.serverconsole.ServerConsole;

/**
 *
 * @author eyeplus
 */

public class ArticleDownloadService {
//      private JTextArea statusArea;
      private in.co.titan.highfrequencyarticledata.DTHighFrequencyArticleDataResponse result;



    public ArticleDownloadService() {
//        this.statusArea = statusArea;
    }


     public void callWebService(String site, ArrayList<ArticleListPOJO> articleLists) throws Exception {


         try { // Call Web Service Operation
             in.co.titan.highfrequencyarticledata.MIOBSYNHighFrequencyArticleDataService service = new in.co.titan.highfrequencyarticledata.MIOBSYNHighFrequencyArticleDataService();
//             in.co.titan.highfrequencyarticledata.MIOBSYNHighFrequencyArticleData port = service.getMIOBSYNHighFrequencyArticleDataPort();
in.co.titan.highfrequencyarticledata.MIOBSYNHighFrequencyArticleData port = service.getHTTPSPort();
             //in.co.titan.highfrequencyarticledata.MIIBSYNHighFrequencyArticleDataService service = new in.co.titan.highfrequencyarticledata.MIOBSYNHighFrequencyArticleDataService();
             //in.co.titan.highfrequencyarticledata.MIOBSYNHighFrequencyArticleData port = service.getMIOBSYNHighFrequencyArticleDataPort();
             // TODO initialize WS operation arguments here
             String endpointaddress = XIConnectionDetailsPojo.getXi_server_port()+"/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_HighFrequencyArticleData&version=3.0&Sender.Service=BS_TITAN_POS&";
             Map<String, Object> ctxt = ((BindingProvider) port).getRequestContext();
//                ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, 300000);
                ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
                ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());
                ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointaddress);


             in.co.titan.highfrequencyarticledata.DTHighFrequencyArticleData mtHighFrequencyArticleData = new in.co.titan.highfrequencyarticledata.DTHighFrequencyArticleData();
              ArrayList<in.co.titan.highfrequencyarticledata.DTHighFrequencyArticleData.Article> articleList;
                in.co.titan.highfrequencyarticledata.DTHighFrequencyArticleData.Article ArticleRecords;
                 System.err.println("Sending Request....");
                mtHighFrequencyArticleData.setSITESEARCH(site);
                mtHighFrequencyArticleData.setSite(site);
//                 getStatusArea().append("\nPulling stock for a list of materials....");
                if (articleLists != null) {
                    articleList = new ArrayList<in.co.titan.highfrequencyarticledata.DTHighFrequencyArticleData.Article>();
                    for (ArticleListPOJO artpojo : articleLists) {
                        if (artpojo != null) {
                            ArticleRecords = new in.co.titan.highfrequencyarticledata.DTHighFrequencyArticleData.Article();
                            ArticleRecords.setArticleCode(artpojo.getMaterialCode());
                            if(artpojo.isArticleFlag()){
                                ArticleRecords.setArticleMaster("X");
                            }
                            if(artpojo.isUCPFlag()){
                                ArticleRecords.setUCP("X");
                            }
                            if(artpojo.isTAXFlag()){
                                ArticleRecords.setTAX("X");
                            }
                            articleList.add(ArticleRecords);
                            System.err.println("Sending the Material list : " + artpojo.getMaterialCode());
                        }
                    }
                    mtHighFrequencyArticleData.getArticle().addAll(articleList);
                }

             // TODO process result here
              result = port.miOBSYNHighFrequencyArticleData(mtHighFrequencyArticleData);
              System.out.println("*************************");
             System.out.println("Result = "+result);
             System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
         } catch (Exception ex) {
             // TODO handle custom exceptions here
             ex.printStackTrace();
         }
    }

      /*********************************  METHOD TO GET ARTICLE MASTER FROM WEBSERVICE RESULT     **********************************/
    public ArrayList<ArticleMasterPOJO> getArticleMaster() {
        ArticleMasterPOJO articleMasterPOJO = null;
        try {
            if (result.getArticleMaster() != null) {
                ArrayList<ArticleMasterPOJO> masterPOJOs = new ArrayList<ArticleMasterPOJO>();
                for (in.co.titan.highfrequencyarticledata.DTHighFrequencyArticleDataResponse.ArticleMaster articleMaster : result.getArticleMaster()) {
                    articleMasterPOJO = new ArticleMasterPOJO();
                    articleMasterPOJO.setStorecode(articleMaster.getSITE());
                    articleMasterPOJO.setMaterialcode(articleMaster.getMATNR());
                    articleMasterPOJO.setDivision(articleMaster.getSPART());
                    articleMasterPOJO.setMaterialdescription(articleMaster.getMAKTX());
                    articleMasterPOJO.setArticletype(articleMaster.getMTART());
                    articleMasterPOJO.setMerchcategory(articleMaster.getMATKL());
                    articleMasterPOJO.setUom(articleMaster.getMEINS());
                    articleMasterPOJO.setBatchindicator(articleMaster.getXCHPF());
                    articleMasterPOJO.setBatchindicator(articleMaster.getHSNSACCODE()); // Added by BALA on 16.6.2017 for GST
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
        }finally{

        }
    }

    /*********************************  METHOD TO GET ARTICLE MASTER CHARACTERSTICS FROM WEBSERVICE RESULT     **********************************/
    public ArrayList<ArticleCharacteristicsPOJO> getArticleCharacterstics() {
        try {
            if (result.getArticleCharacteristics() != null) {
                ArrayList<ArticleCharacteristicsPOJO> characteristicsPOJOs = new ArrayList<ArticleCharacteristicsPOJO>();
                ArticleCharacteristicsPOJO articleCharacteristicsPOJO = null;
                for (in.co.titan.highfrequencyarticledata.DTHighFrequencyArticleDataResponse.ArticleCharacteristics characteristics : result.getArticleCharacteristics()) {
                    articleCharacteristicsPOJO = new ArticleCharacteristicsPOJO();
                    articleCharacteristicsPOJO.setStorecode(characteristics.getSITE());
                    articleCharacteristicsPOJO.setMaterialcode(characteristics.getMATNR());
                    articleCharacteristicsPOJO.setCharacteristics(characteristics.getATNAM());
                    articleCharacteristicsPOJO.setValueno(characteristics.getATZHL());
                    articleCharacteristicsPOJO.setValue(characteristics.getATWRT());
                    articleCharacteristicsPOJO.setUpdatestatus(characteristics.getUPDATESTATUS());
                    articleCharacteristicsPOJO.setCreatedby("admin");
                    articleCharacteristicsPOJO.setCreateddate(ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
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

    /*********************************  METHOD TO GET ARTICLE UCP FROM WEBSERVICE RESULT     **********************************/
    public ArrayList<ArticleUCPPOJO> getArticleUCP() {
        try {
            if (result.getUCP() != null) {
                ArrayList<ArticleUCPPOJO> uCPPOJOs = new ArrayList<ArticleUCPPOJO>();
                ArticleUCPPOJO articleUCPPOJO = null;
                for (in.co.titan.highfrequencyarticledata.DTHighFrequencyArticleDataResponse.UCP ucp : result.getUCP()) {
                    articleUCPPOJO = new ArticleUCPPOJO();
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

     /*********************************  METHOD TO GET ARTICLE TAX FROM WEBSERVICE RESULT     **********************************/
    public ArrayList<ArticleTaxPOJO> getArticleTax() {
        try {
            if (result.getTAX() != null) {
                ArrayList<ArticleTaxPOJO> taxPOJOs = new ArrayList<ArticleTaxPOJO>();
                ArticleTaxPOJO articleTaxPOJO = null;
                for (in.co.titan.highfrequencyarticledata.DTHighFrequencyArticleDataResponse.TAX tax : result.getTAX()) {
                    articleTaxPOJO = new ArticleTaxPOJO();
                    articleTaxPOJO.setCondrecno(tax.getCondrecno());
                    articleTaxPOJO.setCondtype(tax.getCondtype());
                    articleTaxPOJO.setCalculationtype(tax.getCalctype());
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

     public int downloadDataFromISR(Connection con) {
        int noOfRowsUpdated = 0;
//         if (result.getArticleMaster() != null) {
//                ArrayList<ArticleMasterPOJO> ampojos = null;
//                ArticleDO ado = null;
//                try {
//                    ampojos = this.getArticleMaster();
//                    if (ampojos != null) {
//                        noOfRowsUpdated = noOfRowsUpdated + ampojos.size();
//                        ado = new ArticleDO();
//                        for (ArticleMasterPOJO ampojo : ampojos) {
//                            try {
//                                if (ado.updateArticle(con, ampojo) <= 0) {
//                                    ado.saveArticle(con, ampojo);
//                                }
//                            } catch (SQLException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        if (ampojos.size() > 0) {
//                            System.err.println("Article :Updated Records :" + ampojos.size());
//                        }
//                    }
//                } catch (Exception e) {
//                } finally {
//                    ampojos = null;
//                    ado = null;
//                }
//
//        }
//         if (result.getArticleCharacteristics() != null) {
//                ArrayList<ArticleCharacteristicsPOJO> acpojos = null;
//                ArticleDO ado = null;
//                try {
//                    acpojos = this.getArticleCharacterstics();
//                    if (acpojos != null) {
//                        noOfRowsUpdated = noOfRowsUpdated + acpojos.size();
//                        ado = new ArticleDO();
//                        for (ArticleCharacteristicsPOJO acpojo : acpojos) {
//                            try {
//                                if (ado.updateArticleCharacterstic(con, acpojo) <= 0) {
//                                    ado.saveArticleCharacterstic(con, acpojo);
//                                }
//                            } catch (SQLException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        ado.insertBrandAndColorMasterVer2(con);
//                        if (acpojos.size() > 0) {
//                            System.err.println("Article Characteristics :Updated Records :" + acpojos.size());
//                        }
//                    }
//                } catch (Exception e) {
//                } finally {
//                    acpojos = null;
//                    ado = null;
//                }
//
//        }

         if (result.getUCP() != null) {
                ArrayList<ArticleUCPPOJO> aucppojos = null;
                ArticleDO ado = null;
                try {
                    aucppojos = this.getArticleUCP();
                    if (aucppojos != null) {
                        noOfRowsUpdated = noOfRowsUpdated + aucppojos.size();
                        ado = new ArticleDO();
                        for (ArticleUCPPOJO aucpcpojo : aucppojos) {
                            try {
                                if (ado.updateArticleUCP(con, aucpcpojo, false) <= 0) {
                                    ado.saveArticleUCP(con, aucpcpojo, false);
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (aucppojos.size() > 0) {
                            System.err.println("UCP :Updated Records    :" + aucppojos.size());
                        }
                    }
                } catch (Exception e) {
                } finally {
                    aucppojos = null;
                    ado = null;
                }
            
        }

//      if (result.getTAX() != null) {
//                ArrayList<ArticleTaxPOJO> atpojos = null;
//                ArticleDO ado = null;
//                try {
//                    atpojos = this.getArticleTax();
//                    if (atpojos != null) {
//                        noOfRowsUpdated = noOfRowsUpdated + atpojos.size();
//                        ado = new ArticleDO();
//                        for (ArticleTaxPOJO adpojo : atpojos) {
//                            try {
//                                if (ado.updateArticleTax(con, adpojo) == 0) {
//                                    ado.saveArticleTax(con, adpojo);
//                                }
//                            } catch (SQLException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        if (atpojos.size() > 0) {
//                            System.err.println("Tax :Updated Records :" + atpojos.size());
//                        }
//
//                    }
//                } catch (Exception e) {
//                } finally {
//                    atpojos = null;
//                    ado = null;
//                }
//        }
        return noOfRowsUpdated;
     }

    /**
     * @return the statusArea
     */
//    public JTextArea getStatusArea() {
//        return statusArea;
//    }
//
//    /**
//     * @param statusArea the statusArea to set
//     */
//    public void setStatusArea(JTextArea statusArea) {
//        this.statusArea = statusArea;
//    }

}
