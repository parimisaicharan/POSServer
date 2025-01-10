/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.GiftVoucherIssualAndRedemptionService;

import ISRetail.Helpers.ConvertDate;
import ISRetail.Helpers.NetConnection;
import ISRetail.Helpers.XMLCalendar;
import ISRetail.Webservices.XIConnectionDetailsPojo;
import com.sun.net.httpserver.Authenticator;

import com.sun.xml.ws.client.BindingProviderProperties;
import in.co.titan.giftvoucherissual.DTGiftVoucherIssual;
import in.co.titan.giftvoucherissual.DTGiftVoucherIssual.GiftVoucherIssual;
import in.co.titan.giftvoucherissual.SIOBASYNGiftVoucherIssual;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

/**
 *
 * @author ewitsupport
 */
public class GiftVoucherIssualService {

    public GiftVoucherIssualService() {
    }
    public int sendGiftVoucherDetails(GiftVoucherPojo gvPojo) {
        int response = 0;
        GiftVoucherPojo gfpojo = null;
        in.co.titan.giftvoucherissual.SIOBASYNGiftVoucherIssualService service = null;
        in.co.titan.giftvoucherissual.SIOBASYNGiftVoucherIssual port = null;
        in.co.titan.giftvoucherissual.DTGiftVoucherIssual dtGiftVoucher = null;
        in.co.titan.giftvoucherissual.DTGiftVoucherIssual.GiftVoucherIssual giftVoucherIssual = null;
        in.co.titan.giftvoucherissual.DTGiftVoucherIssual result = null;
        Map<String, Object> ctxt;
        try {
            service = new in.co.titan.giftvoucherissual.SIOBASYNGiftVoucherIssualService();
            port = service.getHTTPSPort();
            dtGiftVoucher = new in.co.titan.giftvoucherissual.DTGiftVoucherIssual();
            ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
            ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());
            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port() + "/XISOAPAdapter/MessageServlet?senderParty=&senderService=BS_TITAN_POS&receiverParty=&receiverService=&interface=SI_OB_ASYN_GiftVoucherIssual&interfaceNamespace=http://titan.co.in/GiftVoucherIssual");
            // ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port() + "/XISOAPAdapter/MessageServlet?senderParty=&senderService=BS_TITAN_POS&receiverParty=&receiverService=&interface=SI_OB_ASYN_GiftVoucherIssual&interfaceNamespace=http%3A%2F%2Ftitan.co.in%2FGiftVoucherIssual");
            ctxt = ((BindingProvider) port).getRequestContext();
            ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, 60000);
            giftVoucherIssual = new in.co.titan.giftvoucherissual.DTGiftVoucherIssual.GiftVoucherIssual();
            giftVoucherIssual.setGiftVoucherNo(gvPojo.getGiftVoucherNo());
            giftVoucherIssual.setTransactionCount(gvPojo.getGvActiveCount());
            giftVoucherIssual.setGVStatus(gvPojo.getGvRedemptionStatus());
            giftVoucherIssual.setMobileNo(gvPojo.getCustMobileNo());
            giftVoucherIssual.setSite(gvPojo.getSiteId());
            giftVoucherIssual.setInvoiceNo(gvPojo.getRefNo());
            giftVoucherIssual.setRedeemedGVNumber(gvPojo.getRedeemedGvCode());
            try {
                if (gvPojo.getFromDate() != 0) {
                    java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(gvPojo.getFromDate());
                    if (createdDate != null) {
                        XMLCalendar xmlDate = new XMLCalendar(createdDate);
                        giftVoucherIssual.setValidFrom(xmlDate);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (gvPojo.getToDate() != 0) {
                    java.util.Date toDate = ConvertDate.getUtilDateFromNumericDate(gvPojo.getToDate());
                    if (toDate != null) {
                        XMLCalendar xmlDate = new XMLCalendar(toDate);
                        if (xmlDate != null) {
                            giftVoucherIssual.setValidTo(xmlDate);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            dtGiftVoucher.getGiftVoucherIssual().add(giftVoucherIssual);
            port.siOBASYNGiftVoucherIssual(dtGiftVoucher);
            Map responseContext = ((BindingProvider) port).getResponseContext();
            Integer responseCode = (Integer) responseContext.get(MessageContext.HTTP_RESPONSE_CODE);
            if (responseCode.intValue() == 200) {
                response = responseCode;
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
