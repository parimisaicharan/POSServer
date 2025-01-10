/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.GiftCardSelling;

import ISRetail.paymentdetails.AdvanceReceiptDetailsPOJO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author Administrator
 */
public class GiftCardSellingDetailsDO {

    int casno = 0;
    double casamt = 0;

    public int saveGiftCardSellingDetails(Vector advancereceiptpojoobjlist, Connection conn, AdvanceReceiptDetailsPOJO detobj) throws SQLException {

        int res = 0;
        try {

            // AdvanceReceiptPOJO advancereceiptpojoobj=new AdvanceReceiptPOJO();

            // Iterator it = advancereceiptpojoobjlist.iterator();
            int lineitemno = 1, n = advancereceiptpojoobjlist.size();
            // while (it.hasNext()) {
            for (int i = 0; i < n; i++) {
                if (advancereceiptpojoobjlist.get(i) instanceof AdvanceReceiptDetailsPOJO) {
                    AdvanceReceiptDetailsPOJO advancereceiptdetailspojoobj = (AdvanceReceiptDetailsPOJO) advancereceiptpojoobjlist.get(i);//it.next();
                    //  String insertstr = "insert into AdvanceReceiptDetails values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                    if (!advancereceiptdetailspojoobj.getModeofpayment().equalsIgnoreCase("CAS")) {
                        try {
                            String insertstr = "insert into tbl_paymentdetails1 values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


                            //String insertstr ="insert into tbl_paymentdetails(storecode, values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";



                            PreparedStatement pstmt = conn.prepareStatement(insertstr);

                            pstmt.setString(1, detobj.getStorecode());
                            pstmt.setInt(2, detobj.getFiscalyear());
                            pstmt.setString(3, detobj.getDocumentno());

//pstmt.setInt(4,advancereceiptdetailspojoobj.getLineitemno());
                            pstmt.setInt(4, lineitemno);
                            pstmt.setString(5, detobj.getPaymentfrom());
                            pstmt.setString(6, advancereceiptdetailspojoobj.getModeofpayment());
                            pstmt.setString(7, advancereceiptdetailspojoobj.getInstrumentno());
                            pstmt.setInt(8, advancereceiptdetailspojoobj.getInstrumentdate());
                            pstmt.setString(9, advancereceiptdetailspojoobj.getAuthorizationcode());
                            pstmt.setString(10, advancereceiptdetailspojoobj.getBank());
                            pstmt.setString(11, advancereceiptdetailspojoobj.getCardtype());
                            pstmt.setString(12, advancereceiptdetailspojoobj.getBranchname());
                            pstmt.setString(13, advancereceiptdetailspojoobj.getGVFrom());
                            pstmt.setString(14, advancereceiptdetailspojoobj.getGVTO());
                            pstmt.setDouble(15, advancereceiptdetailspojoobj.getDenomination());
                            pstmt.setString(16, advancereceiptdetailspojoobj.getValidationtext());
//pstmt.setDouble(17,advancereceiptdetailspojoobj.getCreditamount());
//pstmt.setInt(18,advancereceiptdetailspojoobj.getCn_fiscalyear());
//pstmt.setString(19,advancereceiptdetailspojoobj.getCreditnotereference());
                            pstmt.setString(17, advancereceiptdetailspojoobj.getCurrencytype());
                            pstmt.setDouble(18, advancereceiptdetailspojoobj.getExchangerate());
                            pstmt.setDouble(19, advancereceiptdetailspojoobj.getNoofunits());
                            pstmt.setString(20, advancereceiptdetailspojoobj.getEmployeename());
                            pstmt.setString(21, advancereceiptdetailspojoobj.getDepartment());
                            pstmt.setString(22, advancereceiptdetailspojoobj.getLetterreference());
                            pstmt.setString(23, advancereceiptdetailspojoobj.getAuthorizedperson());
                            pstmt.setDouble(24, advancereceiptdetailspojoobj.getLoanamount());
                            pstmt.setInt(25, advancereceiptdetailspojoobj.getLoyalitypoints());
                            pstmt.setInt(26, advancereceiptdetailspojoobj.getAsondate());
                            pstmt.setInt(27, advancereceiptdetailspojoobj.getRedeemedpoints());
                            pstmt.setString(28, advancereceiptdetailspojoobj.getApprovalno());
                            pstmt.setString(29, advancereceiptdetailspojoobj.getOthercardtype());
                            pstmt.setDouble(30, advancereceiptdetailspojoobj.getAmount());
                            lineitemno++;


                            res = pstmt.executeUpdate();
                            pstmt.close();
                            pstmt = null;
                            insertstr = null;
                        } catch (SQLException e) {
                            throw new SQLException();
                        }

                    } else if (advancereceiptdetailspojoobj.getModeofpayment().equalsIgnoreCase("CAS") && casno == 0) {
                        try {
                            String insertstr = "insert into tbl_paymentdetails1 values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                            PreparedStatement pstmt = conn.prepareStatement(insertstr);

                            pstmt.setString(1, detobj.getStorecode());
                            pstmt.setInt(2, detobj.getFiscalyear());
                            pstmt.setString(3, detobj.getDocumentno());

//pstmt.setInt(4,advancereceiptdetailspojoobj.getLineitemno());
                            pstmt.setInt(4, lineitemno);
//pstmt.setString(5,advancereceiptdetailspojoobj.getPaymentfrom());
                            pstmt.setString(5, detobj.getPaymentfrom());
                            pstmt.setString(6, advancereceiptdetailspojoobj.getModeofpayment());
                            pstmt.setString(7, advancereceiptdetailspojoobj.getInstrumentno());
                            pstmt.setInt(8, advancereceiptdetailspojoobj.getInstrumentdate());
                            pstmt.setString(9, advancereceiptdetailspojoobj.getAuthorizationcode());
                            pstmt.setString(10, advancereceiptdetailspojoobj.getBank());
                            pstmt.setString(11, advancereceiptdetailspojoobj.getCardtype());
                            pstmt.setString(12, advancereceiptdetailspojoobj.getBranchname());
                            pstmt.setString(13, advancereceiptdetailspojoobj.getGVFrom());
                            pstmt.setString(14, advancereceiptdetailspojoobj.getGVTO());
                            pstmt.setDouble(15, advancereceiptdetailspojoobj.getDenomination());
                            pstmt.setString(16, advancereceiptdetailspojoobj.getValidationtext());
//pstmt.setDouble(17,advancereceiptdetailspojoobj.getCreditamount());
//pstmt.setInt(18,advancereceiptdetailspojoobj.getCn_fiscalyear());
//pstmt.setString(19,advancereceiptdetailspojoobj.getCreditnotereference());
                            pstmt.setString(17, advancereceiptdetailspojoobj.getCurrencytype());
                            pstmt.setDouble(18, advancereceiptdetailspojoobj.getExchangerate());
                            pstmt.setDouble(19, advancereceiptdetailspojoobj.getNoofunits());
                            pstmt.setString(20, advancereceiptdetailspojoobj.getEmployeename());
                            pstmt.setString(21, advancereceiptdetailspojoobj.getDepartment());
                            pstmt.setString(22, advancereceiptdetailspojoobj.getLetterreference());
                            pstmt.setString(23, advancereceiptdetailspojoobj.getAuthorizedperson());
                            pstmt.setDouble(24, advancereceiptdetailspojoobj.getLoanamount());
                            pstmt.setInt(25, advancereceiptdetailspojoobj.getLoyalitypoints());
                            pstmt.setInt(26, advancereceiptdetailspojoobj.getAsondate());
                            pstmt.setInt(27, advancereceiptdetailspojoobj.getRedeemedpoints());
                            pstmt.setString(28, advancereceiptdetailspojoobj.getApprovalno());
                            pstmt.setString(29, advancereceiptdetailspojoobj.getOthercardtype());
                            pstmt.setDouble(30, advancereceiptdetailspojoobj.getAmount());


                            casamt = advancereceiptdetailspojoobj.getAmount();
                            lineitemno++;
                            res = pstmt.executeUpdate();
                            pstmt.close();
                            pstmt = null;
                            insertstr = null;

                            casno = 1;
                        } catch (SQLException e) {
                            throw new SQLException();
                        }
                    } else if (advancereceiptdetailspojoobj.getModeofpayment().equalsIgnoreCase("CAS") && casno > 0) {

                        Statement pstmt1 = conn.createStatement();

                        double amt = casamt + advancereceiptdetailspojoobj.getAmount();

                        try {
                            String query = "update tbl_paymentdetails1 set amount =" + amt + " where documentno='" + detobj.getDocumentno() + "' and modeofpayment='CAS'";
                            int res1 = pstmt1.executeUpdate(query);
                            if (res1 > 0) {
                                casamt = amt;
                            } else {
                            }
                            query = null;
                        } catch (Exception e) {
                            throw new SQLException();
                        }
                        pstmt1.close();
                        pstmt1 = null;

                    }
                } else if (advancereceiptpojoobjlist.get(i) instanceof ArrayList) {
                    ArrayList<AdvanceReceiptDetailsPOJO> advanceReceiptDetailsPOJOs = (ArrayList<AdvanceReceiptDetailsPOJO>) advancereceiptpojoobjlist.get(i);
                    for (AdvanceReceiptDetailsPOJO advancereceiptdetailspojoobj : advanceReceiptDetailsPOJOs) {
                        try {
                            String insertstr = "insert into tbl_paymentdetails1 values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                            PreparedStatement pstmt = conn.prepareStatement(insertstr);
                            pstmt.setString(1, detobj.getStorecode());
                            pstmt.setInt(2, detobj.getFiscalyear());
                            pstmt.setString(3, detobj.getDocumentno());
                            pstmt.setInt(4, lineitemno);
                            pstmt.setString(5, detobj.getPaymentfrom());
                            pstmt.setString(6, advancereceiptdetailspojoobj.getModeofpayment());
                            pstmt.setString(7, advancereceiptdetailspojoobj.getInstrumentno());
                            pstmt.setInt(8, advancereceiptdetailspojoobj.getInstrumentdate());
                            pstmt.setString(9, advancereceiptdetailspojoobj.getAuthorizationcode());
                            pstmt.setString(10, advancereceiptdetailspojoobj.getBank());
                            pstmt.setString(11, advancereceiptdetailspojoobj.getCardtype());
                            pstmt.setString(12, advancereceiptdetailspojoobj.getBranchname());
                            pstmt.setString(13, advancereceiptdetailspojoobj.getGVFrom());
                            pstmt.setString(14, advancereceiptdetailspojoobj.getGVTO());
                            pstmt.setDouble(15, advancereceiptdetailspojoobj.getDenomination());
                            pstmt.setString(16, advancereceiptdetailspojoobj.getValidationtext());
                            pstmt.setString(17, advancereceiptdetailspojoobj.getCurrencytype());
                            pstmt.setDouble(18, advancereceiptdetailspojoobj.getExchangerate());
                            pstmt.setDouble(19, advancereceiptdetailspojoobj.getNoofunits());
                            pstmt.setString(20, advancereceiptdetailspojoobj.getEmployeename());
                            pstmt.setString(21, advancereceiptdetailspojoobj.getDepartment());
                            pstmt.setString(22, advancereceiptdetailspojoobj.getLetterreference());
                            pstmt.setString(23, advancereceiptdetailspojoobj.getAuthorizedperson());
                            pstmt.setDouble(24, advancereceiptdetailspojoobj.getLoanamount());
                            pstmt.setInt(25, advancereceiptdetailspojoobj.getLoyalitypoints());
                            pstmt.setInt(26, advancereceiptdetailspojoobj.getAsondate());
                            pstmt.setInt(27, advancereceiptdetailspojoobj.getRedeemedpoints());
                            pstmt.setString(28, advancereceiptdetailspojoobj.getApprovalno());
                            pstmt.setString(29, advancereceiptdetailspojoobj.getOthercardtype());
                            pstmt.setDouble(30, advancereceiptdetailspojoobj.getAmount());
                            lineitemno++;
                            res = pstmt.executeUpdate();
                            pstmt.close();
                            pstmt = null;
                            insertstr = null;
                        } catch (SQLException e) {
                            throw new SQLException();
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException();
        }
        return res;
    }
}
