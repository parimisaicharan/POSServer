/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.stock;

import ISRetail.article.*;
import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.msdeconnection.MsdeConnection;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JTextArea;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPFaultException;

/**
 *
 * @author enteg
 */
public class StockMasterService {

    JTextArea statusArea;

    public StockMasterService(JTextArea statusArea) {
        this.statusArea = statusArea;
    }

    public ArrayList<StockMasterPOJO> callWebService(String site, ArrayList<MaterialListPOJO> materialLists, boolean allFlag) throws Exception {
        ArrayList<StockMasterPOJO> stockPojos = null;
        try { // Call Web Service Operation
            in.co.titan.highfrequencystock.MIOBSYNHighFrequencyStockService service = new in.co.titan.highfrequencystock.MIOBSYNHighFrequencyStockService();
            in.co.titan.highfrequencystock.MIOBSYNHighFrequencyStock port = service.getMIOBSYNHighFrequencyStockPort();
            // TODO initialize WS operation arguments here
            Map<String, Object> ctxt = ((BindingProvider) port).getRequestContext();
            //ctxt.put(JAXWSProperties.CONNECT_TIMEOUT, connectionTimeout);
            ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, 720000);
            ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
            ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());
            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port()+"/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_HighFrequencyStock&version=3.0&Sender.Service=x&Interface=x%5Ex");

            in.co.titan.highfrequencystock.DTHighFrequencyStock mtHighFrequencyStock = new in.co.titan.highfrequencystock.DTHighFrequencyStock();
            ArrayList<in.co.titan.highfrequencystock.DTHighFrequencyStock.MaterialList> matList;
            in.co.titan.highfrequencystock.DTHighFrequencyStock.MaterialList matRec;
            ArrayList<in.co.titan.highfrequencystock.DTHighFrequencyStockResponse.Stock> stockList = null;
            // TODO process result here
            statusArea.append("\nSending Request....");
            mtHighFrequencyStock.setSite(site);
            mtHighFrequencyStock.setSITESEARCH(site);
            if (allFlag) {
                statusArea.append("\nPulling Stock For all materials....");
                mtHighFrequencyStock.setFlagAll("X");
            } else {
                mtHighFrequencyStock.setFlagAll("");
                statusArea.append("\nPulling stock for a list of materials....");
                if (materialLists != null) {
                    matList = new ArrayList<in.co.titan.highfrequencystock.DTHighFrequencyStock.MaterialList>();
                    for (MaterialListPOJO mlpojo : materialLists) {
                        if (mlpojo != null) {
                            matRec = new in.co.titan.highfrequencystock.DTHighFrequencyStock.MaterialList();
                            matRec.setMaterial(mlpojo.getMaterialCode());
                            matList.add(matRec);
                            statusArea.append("\nSending the Material : " + mlpojo.getMaterialCode());
                        }
                    }
                    mtHighFrequencyStock.getMaterialList().addAll(matList);
                }
            }

            in.co.titan.highfrequencystock.DTHighFrequencyStockResponse result = port.miOBSYNHighFrequencyStock(mtHighFrequencyStock);
            System.out.println("Result = " + result);
            if (result != null) {
                stockList = (ArrayList<in.co.titan.highfrequencystock.DTHighFrequencyStockResponse.Stock>) result.getStock();
                stockPojos = getArticleStockForStockMaterFromISR(result);
            }
        } catch (SOAPFaultException ex) {
            // TODO handle custom exceptions here
            ex.printStackTrace();
            statusArea.append("\nDownload Failed Error: " + ex.getMessage());
            statusArea.append("\nDownload Failed Error: " + ex.getFault().getFaultCode());
        } catch (Exception ex) {
            // TODO handle custom exceptions here
            ex.printStackTrace();
            statusArea.append("\nDownload Failed Error: " + ex.getMessage());
        } finally {

        }
        return stockPojos;

    }

    /*********************************  METHOD TO GET STOCK MASTER FROM WEBSERVICE RESULT     **********************************/
    public ArrayList<StockMasterPOJO> getArticleStockForStockMaterFromISR(in.co.titan.highfrequencystock.DTHighFrequencyStockResponse result) {
        try {
            ArrayList<in.co.titan.highfrequencystock.DTHighFrequencyStockResponse.Stock> stock = (ArrayList<in.co.titan.highfrequencystock.DTHighFrequencyStockResponse.Stock>) result.getStock();
            if (stock != null) {
                ArrayList<StockMasterPOJO> masterPOJOs = new ArrayList<StockMasterPOJO>();
                Iterator iterator = stock.iterator();
                while (iterator.hasNext()) {
                    in.co.titan.highfrequencystock.DTHighFrequencyStockResponse.Stock stockMaster = (in.co.titan.highfrequencystock.DTHighFrequencyStockResponse.Stock) iterator.next();
                    StockMasterPOJO stockMasterPOJO = new StockMasterPOJO();
                    stockMasterPOJO.setMaterialcode(stockMaster.getMATNR());
                    stockMasterPOJO.setStorecode(stockMaster.getSITE());
                    if (stockMaster.getUPDATESTATUS() == null) {
                        stockMasterPOJO.setUpdatestatus("C");

                    } else {
                        stockMasterPOJO.setUpdatestatus(stockMaster.getUPDATESTATUS());
                    }
                    if (stockMaster.getMENGE() != null) {
                        stockMasterPOJO.setQuantity(stockMaster.getMENGE().intValue());
                    }
                    stockMasterPOJO.setBatch(stockMaster.getCHARG());
                    stockMasterPOJO.setStorageloc(stockMaster.getLGORT());
                    if (stockMaster.getMENGE() != null) {
                       // statusArea.append("\nReceiving the Material : " + stockMaster.getMATNR() + "  Batch:" + stockMaster.getCHARG() + "  Stock=" + stockMaster.getMENGE());
                        System.out.println("\nReceiving the Material :" + stockMaster.getMATNR() + "  Batch:" + stockMaster.getCHARG() + "  Stock=" + stockMaster.getMENGE());
                    } 
//                    else {
//                        statusArea.append("\nReceiving the Material : " + stockMaster.getMATNR() + "  Batch:" + stockMaster.getCHARG() + " No Stock");
//                    }
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

    public int updateStockMasterFromISRToPos(ArrayList<StockMasterPOJO> stockList) {
        MsdeConnection mc;
        Connection con;
        Iterator iterator;
        StockMasterDO sm;
        StockMasterPOJO adpojo;
        int noRecUpdated = 0;
        try {
            mc = new MsdeConnection();
            con = mc.createConnection();
            iterator = stockList.iterator();
            sm = new StockMasterDO();
            while (iterator.hasNext()) {
                adpojo = (StockMasterPOJO) iterator.next();
                try {
                    con.setAutoCommit(false);
                    sm.deleteStock(adpojo, con);
                    if (adpojo.getQuantity() > 0) {
                        if (sm.updateStockDetails(adpojo, con) == 0) {
                            sm.saveStockDetails(adpojo, con);
                            noRecUpdated++;
                            //statusArea.append("\nUpdated Stock for Material: " + adpojo.getMaterialcode() + "  Batch:" + adpojo.getBatch() + "   Stock=" + adpojo.getQuantity());
                            System.out.println("\nUpdated Stock for Material: " + adpojo.getMaterialcode() + "  Batch:" + adpojo.getBatch() + "   Stock=" + adpojo.getQuantity());
                        }
                    }
                    con.commit();
                } catch (SQLException e) {
                    if (adpojo.getQuantity() > 0) {
                        statusArea.append("\nError in Updating Stock for Material: " + adpojo.getMaterialcode() + "  Batch:" + adpojo.getBatch() + "   Stock=" + adpojo.getQuantity() + "Error is :" + e.getMessage());
                    }
                    try {
                        con.rollback();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            statusArea.append("\nStock :Updated Records :" + noRecUpdated);
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mc = null;
            con = null;
            iterator = null;
            sm = null;
            adpojo = null;
        }
        return noRecUpdated;
    }
    
}
