/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package posstaging;

import ISRetail.Helpers.ConvertDate;
import ISRetail.serverconsole.ServerConsole;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Administrator
 */
public class ErrorLogDO {

   

    public void saveArticlesLog(Connection con, ErrorLogPOJO errorLogPOJOs) {
        PreparedStatement pstmt1;
        try {
            int result = 0;

            pstmt1 = con.prepareStatement("insert into tbl_hf_log values (?,?,?,?,?,?,?,?,?,?,?)");
            pstmt1.setString(1, errorLogPOJOs.getShortname());
            pstmt1.setString(2, errorLogPOJOs.getKey1());
            pstmt1.setString(3, errorLogPOJOs.getKey2());
            pstmt1.setString(4, errorLogPOJOs.getKey3());
            pstmt1.setString(5, errorLogPOJOs.getKey4());
            pstmt1.setString(6, errorLogPOJOs.getKey5());
            pstmt1.setString(7, errorLogPOJOs.getKey6());
            pstmt1.setInt(8, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
            pstmt1.setString(9, ConvertDate.getCurrentTimeToString());
            pstmt1.setString(10, errorLogPOJOs.getStatus());
            pstmt1.setString(11, errorLogPOJOs.getDescription());
            result = pstmt1.executeUpdate();



        } catch (Exception e) {
        //   e.printStackTrace();
        }finally {
            pstmt1= null;
        }
    }

    public int updateArticleLog(Connection con, ErrorLogPOJO errorLogPOJO) {
        PreparedStatement pstmt1;
        int result = 0;
        try {
            pstmt1 = con.prepareStatement("update tbl_hf_log set shortname = ?,key1=?,key2=?,key3=?,key4=?,key5=?,key6=?,dateoflog=?,timeoflog=?,status=?,description=? where shortname='" + errorLogPOJO.getShortname() + "' and key1 = '" + errorLogPOJO.getKey1() + "' and key2 = '" + errorLogPOJO.getKey2() + "' and key3 ='" + errorLogPOJO.getKey3() + "' and key4 ='" + errorLogPOJO.getKey4() + "' and key5 ='" + errorLogPOJO.getKey5() + "' and key6 ='" + errorLogPOJO.getKey6() + "'");
            pstmt1.setString(1, errorLogPOJO.getShortname());
            pstmt1.setString(2, errorLogPOJO.getKey1());
            pstmt1.setString(3, errorLogPOJO.getKey2());
            pstmt1.setString(4, errorLogPOJO.getKey3());
            pstmt1.setString(5, errorLogPOJO.getKey4());
            pstmt1.setString(6, errorLogPOJO.getKey5());
            pstmt1.setString(7, errorLogPOJO.getKey6());
            pstmt1.setInt(8, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
            pstmt1.setString(9, ConvertDate.getCurrentTimeToString());
            pstmt1.setString(10, errorLogPOJO.getStatus());
            pstmt1.setString(11, errorLogPOJO.getDescription());

            result = pstmt1.executeUpdate();

        } catch (Exception e) {
        //  e.printStackTrace();
        }finally {
            pstmt1= null;
        }
        return result;
    }

    public ArrayList checkerrorlogandpass(Connection con) {
            Statement pstmt;
            Statement pstmt1;
            Statement pstmt2;
        try {
            pstmt = con.createStatement();
            pstmt1 = con.createStatement();
            pstmt2 = con.createStatement();
            ArrayList errorloglist = new ArrayList();
            ArrayList deletearraylist = new ArrayList();
           
            ErrorLogPOJO errorLogPOJO = null;

            ResultSet rs = pstmt.executeQuery("select * from tbl_hf_log");
            while (rs.next()) {
                if (rs.getString("shortname").equalsIgnoreCase("ART")) {
                    String key2 = rs.getString("key2");
                    String key3 = rs.getString("key3");
                    String key4 = rs.getString("key4");
                    String key5 = rs.getString("key5");
                    String key6 = rs.getString("key5");
                    int dateoflog = rs.getInt("dateoflog");
                    String timeoflog = rs.getString("timeoflog");
                    ResultSet rs1 = pstmt1.executeQuery("select b.key1,b.key2,b.key3,b.key4,b.key5,b.key6,b.dateoflog,b.timeoflog,b.description,a.data_syncdate,a.data_synctime from tbl_articlemaster as a,tbl_hf_log as b where a.materialcode='" + key2 + "' and b.key2='" + key2 + "' and b.key3='" + key3 + "' and b.key4='" + key4 + "' and b.key5='" + key5 + "' and b.key6='" + key6 + "' and a.data_syncdate>" + dateoflog + " union select b.key1,b.key2,b.key3,b.key4,b.key5,b.key6,b.dateoflog,b.timeoflog,b.description,a.data_syncdate,a.data_synctime from tbl_articlemaster as a,tbl_hf_log as b where a.materialcode='" + key2 + "'  and b.key3='" + key3 + "' and b.key4='" + key4 + "' and b.key5='" + key5 + "' and b.key6='" + key6 + "' and a.data_syncdate =" + dateoflog + " and a.data_synctime >" + timeoflog + "");
                    if (rs1.next()) {
                        errorLogPOJO = new ErrorLogPOJO();
                        errorLogPOJO = seterrorLogPOJO("ART", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs1.getString("timeoflog"), rs.getString("description"));
                        deletearraylist.add(errorLogPOJO);

                    } else {
                        errorLogPOJO = new ErrorLogPOJO();
                        errorLogPOJO = seterrorLogPOJO("ARC", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                        errorloglist.add(errorLogPOJO);
                    }
                } else if (rs.getString("shortname").equalsIgnoreCase("ARC")) {
                    String key2 = rs.getString("key2");
                    String key3 = rs.getString("key3");
                    String key4 = rs.getString("key4");
                    String key5 = rs.getString("key5");
                    String key6 = rs.getString("key5");
                    int dateoftlog = rs.getInt("dateoflog");
                    String timeoftlog = rs.getString("timeoflog");
                    ResultSet rs1 = pstmt1.executeQuery("select materialcode,characteristics,valueno from tbl_articlemaster_characteristics where materialcode='" + key2 + "' and characteristics='" + key3 + "' and valueno='" + key4 + "' and data_syncdate > " + dateoftlog + " union select materialcode,characteristics,valueno from tbl_articlemaster_characteristics where materialcode='" + key2 + "' and characteristics='" + key3 + "' and valueno='" + key4 + "' and data_syncdate = " + dateoftlog + " and data_synctime>'" + timeoftlog + "'");
                    if (rs1.next()) {
                        errorLogPOJO = new ErrorLogPOJO();
                        errorLogPOJO = seterrorLogPOJO("ARC", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                        deletearraylist.add(errorLogPOJO);
                    } else {
                        errorLogPOJO = new ErrorLogPOJO();
                        errorLogPOJO = seterrorLogPOJO("ARC", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                        errorloglist.add(errorLogPOJO);
                    }

                } else if (rs.getString("shortname").equalsIgnoreCase("UCP")) {
                    String key2 = rs.getString("key2");
                    String key3 = rs.getString("key3");
                    String key4 = rs.getString("key4");
                    String key5 = rs.getString("key5");
                    String key6 = rs.getString("key5");
                    int dateoftlog = rs.getInt("dateoflog");
                    String timeoftlog = rs.getString("timeoflog");
                    //  ResultSet rs1 = pstmt1.executeQuery("select b.key1,b.key2,b.key3,b.key4,b.key5,b.key6,b.dateoflog,b.timeoflog,b.description,a.data_syncdate,a.data_synctime from tbl_ucp as a,tbl_hf_log as b where a.condtype=b.key2  and a.materialcode=b.key3 and a.condrecno=b.key4  and a.data_syncdate > b.dateoflog union select b.key1,b.key2,b.key3,b.key4,b.key5,b.key6,b.dateoflog,b.timeoflog,b.description,a.data_syncdate,a.data_synctime from tbl_ucp as a,tbl_hf_log as b where a.condtype=b.key2  and a.materialcode=b.key3 and a.condrecno=b.key4 and a.data_syncdate = b.dateoflog and a.data_synctime>b.timeoflog");
                    ResultSet rs1 = pstmt1.executeQuery("select condtype,materialcode,condrecno from tbl_ucp where condtype='" + key2 + "' and materialcode='" + key3 + "' and condrecno='" + key4 + "'  and data_syncdate > " + dateoftlog + " union select condtype,materialcode,condrecno from tbl_ucp where condtype='" + key2 + "' and materialcode='" + key3 + "' and condrecno='" + key4 + "' and data_syncdate = " + dateoftlog + " and data_synctime>'" + timeoftlog + "'");
                    if (rs1.next()) {

                        errorLogPOJO = new ErrorLogPOJO();
                        errorLogPOJO = seterrorLogPOJO("UCP", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                        deletearraylist.add(errorLogPOJO);

                    } else {
                        errorLogPOJO = new ErrorLogPOJO();
                        errorLogPOJO = seterrorLogPOJO("UCP", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                        errorloglist.add(errorLogPOJO);


                    }



                } else if (rs.getString("shortname").equalsIgnoreCase("DIS")) {
                    String key2 = rs.getString("key2");
                    String key3 = rs.getString("key3");
                    String key4 = rs.getString("key4");
                    String key5 = rs.getString("key5");
                    String key6 = rs.getString("key5");
                    int dateoftlog = rs.getInt("dateoflog");
                    String timeoftlog = rs.getString("timeoflog");
                    ResultSet rs1 = pstmt1.executeQuery("select condtype,merchcat,condrecno from tbl_discounts where condtype='" + key2 + "' and merchcat='" + key3 + "' and condrecno='" + key4 + "'  and data_syncdate > " + dateoftlog + " union select condtype,merchcat,condrecno from tbl_discounts where condtype='" + key2 + "' and merchcat='" + key3 + "' and condrecno='" + key4 + "' and data_syncdate = " + dateoftlog + " and data_synctime>'" + timeoftlog + "'");

                    if (rs1.next()) {
                        errorLogPOJO = new ErrorLogPOJO();
                        errorLogPOJO = seterrorLogPOJO("DIS", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                        deletearraylist.add(errorLogPOJO);


                    } else {
                        errorLogPOJO = new ErrorLogPOJO();
                        errorLogPOJO = seterrorLogPOJO("DIS", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                        errorloglist.add(errorLogPOJO);
                    }

                } else if (rs.getString("shortname").equalsIgnoreCase("OTH")) {
                    String key2 = rs.getString("key2");
                    String key3 = rs.getString("key3");
                    String key4 = rs.getString("key4");
                    String key5 = rs.getString("key5");
                    String key6 = rs.getString("key5");
                    int dateoftlog = rs.getInt("dateoflog");
                    String timeoftlog = rs.getString("timeoflog");
                    ResultSet rs1 = pstmt1.executeQuery("select condtype,merchcat,condrecno from tbl_othercharges where condtype='" + key2 + "' and merchcat='" + key3 + "' and condrecno='" + key4 + "'  and data_syncdate > " + dateoftlog + " union select condtype,merchcat,condrecno from tbl_othercharges where condtype='" + key2 + "' and merchcat='" + key3 + "' and condrecno='" + key4 + "' and data_syncdate = " + dateoftlog + " and data_synctime>'" + timeoftlog + "'");

                    if (rs1.getRow() > 0) {
                        errorLogPOJO = new ErrorLogPOJO();
                        errorLogPOJO = seterrorLogPOJO("OTH", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                        deletearraylist.add(errorLogPOJO);

                    } else {
                        errorLogPOJO = new ErrorLogPOJO();
                        errorLogPOJO = seterrorLogPOJO("OTH", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                        errorloglist.add(errorLogPOJO);

                    }

                } else if (rs.getString("shortname").equalsIgnoreCase("TAX")) {
                    String key2 = rs.getString("key2");
                    String key3 = rs.getString("key3");
                    String key4 = rs.getString("key4");
                    String key5 = rs.getString("key5");
                    String key6 = rs.getString("key5");
                    int dateoftlog = rs.getInt("dateoflog");
                    String timeoftlog = rs.getString("timeoflog");
//                    ResultSet rs1 = pstmt1.executeQuery("select condtype,state,merchcat,condrecno from tbl_taxdetails where condtype='" + key2 + "' and state='" + key3 + "' and merchcat='" + key4 + "'  and data_syncdate > " + dateoftlog + " union select condtype,state,merchcat,condrecno from tbl_taxdetails where condtype='" + key2 + "' and state='" + key3 + "' and merchcat='" + key4 + "' and data_syncdate = " + dateoftlog + " and data_synctime>'" + timeoftlog + "'");
                      ResultSet rs1 = pstmt1.executeQuery("select condtype,state,hsn_sac_code,condrecno from tbl_gst_taxdetails where condtype='" + key2 + "' and state='" + key3 + "' and hsn_sac_code='" + key4 + "'  and data_syncdate > " + dateoftlog + " union select condtype,state,hsn_sac_code,condrecno from tbl_gst_taxdetails where condtype='" + key2 + "' and state='" + key3 + "' and hsn_sac_code='" + key4 + "' and data_syncdate = " + dateoftlog + " and data_synctime>'" + timeoftlog + "'"); // Added by BALA on 18.6.2017 for GST
                    if (rs1.getRow() > 0) {
                            errorLogPOJO = new ErrorLogPOJO();
                            errorLogPOJO = seterrorLogPOJO("TAX", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                            deletearraylist.add(errorLogPOJO);    
                     
                    } else {
                      
                            errorLogPOJO = new ErrorLogPOJO();
                            errorLogPOJO = seterrorLogPOJO("TAX", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                            errorloglist.add(errorLogPOJO);
                       
                    }
                
                } else if (rs.getString("shortname").equalsIgnoreCase("STM")) {
                    String key2 = rs.getString("key2");
                    String key3 = rs.getString("key3");
                    String key4 = rs.getString("key4");
                    String key5 = rs.getString("key5");
                    String key6 = rs.getString("key5");
                    int dateoftlog = rs.getInt("dateoflog");
                    String timeoftlog = rs.getString("timeoflog");
                    if (rs.getString("key4") != null) {
                        if (rs.getString("key4").length() > 0) {
                            ResultSet rs1 = pstmt1.executeQuery("select storageloc,materialcode,batch from tbl_stockmaster_batch where storageloc='" + key2 + "' and materialcode='" + key3 + "' and batch='" + key4 + "'  and data_syncdate > " + dateoftlog + " union select storageloc,materialcode,batch from tbl_stockmaster_batch where storageloc='" + key2 + "' and materialcode='" + key3 + "' and batch='" + key4 + "' and data_syncdate = " + dateoftlog + " and data_synctime>'" + timeoftlog + "'");
                            if (rs1.next()) {
                                    errorLogPOJO = new ErrorLogPOJO();
                                    errorLogPOJO = seterrorLogPOJO("STM", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                                    deletearraylist.add(errorLogPOJO);
                                 
                            } else {
                                    errorLogPOJO = new ErrorLogPOJO();
                                    errorLogPOJO = seterrorLogPOJO("STM", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                                    errorloglist.add(errorLogPOJO);
                                   }
                       
                        } else {
                          
                            ResultSet rs1 = pstmt1.executeQuery("select storageloc,materialcode from tbl_stockmaster where storageloc='" + key2 + "' and materialcode='" + key3 + "' and data_syncdate > " + dateoftlog + " union select storageloc,materialcode from tbl_stockmaster where storageloc='" + key2 + "' and materialcode='" + key3 + "' and data_syncdate = " + dateoftlog + " and data_synctime>'" + timeoftlog + "'");
                            if (rs1.next())
                            {
                               errorLogPOJO = new ErrorLogPOJO();
                               errorLogPOJO = seterrorLogPOJO("STM", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                               deletearraylist.add(errorLogPOJO);
                                    
                               
                            } else {
                                ResultSet rs2 = pstmt1.executeQuery("select b.key1,b.key2,b.key3,b.key4,b.key5,b.key6,b.dateoflog,b.timeoflog,b.description,a.data_syncdate,a.data_synctime from tbl_stockmaster as a,tbl_hf_log as b where a.storageloc=b.key2 and a.materialcode=b.key3 and ''=key4");
                               
                                    errorLogPOJO = new ErrorLogPOJO();
                                    errorLogPOJO = seterrorLogPOJO("STM", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                                    errorloglist.add(errorLogPOJO);
                                   }
//   }
                        }
                    }
                } else if (rs.getString("shortname").equalsIgnoreCase("GVS")) {
                    
                    String key2 = rs.getString("key2");
                    String key3 = rs.getString("key3");
                    String key4 = rs.getString("key4");
                    String key5 = rs.getString("key5");
                    String key6 = rs.getString("key5");
                    int dateoftlog = rs.getInt("dateoflog");
                    String timeoftlog = rs.getString("timeoflog");
                    ResultSet rs1 = pstmt1.executeQuery("select storageloc,materialcode,serialno from tbl_gvmaster where storageloc='" + key2 + "' and materialcode='" + key3 + "'and serialno='"+key4+"' and data_syncdate > " + dateoftlog + " union select storageloc,materialcode,serialno from tbl_gvmaster where storageloc='" + key2 + "' and materialcode='" + key3 + "'and serialno='"+key4+"' and data_syncdate = " + dateoftlog + " and data_synctime>'" + timeoftlog + "'");
                    if (rs1.next()) {
                        errorLogPOJO = new ErrorLogPOJO();
                        errorLogPOJO = seterrorLogPOJO("GVS", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                        deletearraylist.add(errorLogPOJO);
                         
                    } else {
                            errorLogPOJO = new ErrorLogPOJO();
                            errorLogPOJO = seterrorLogPOJO("GVS", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                            errorloglist.add(errorLogPOJO);
                           }
                
                } else if (rs.getString("shortname").equalsIgnoreCase("SIT")) {
                   String key2 = rs.getString("key2");
                    
                    int dateoftlog = rs.getInt("dateoflog");
                    String timeoftlog = rs.getString("timeoflog");

                    ResultSet rs1 = pstmt1.executeQuery("select siteid  from tbl_sitemaster where siteid='" + key2 + "' and data_syncdate > " + dateoftlog + " union select siteid from tbl_sitemaster as a,tbl_hf_log as b where siteid='" + key2 + "' and data_syncdate = " + dateoftlog + " and data_synctime>'" + timeoftlog + "'");

                    if (rs1.next()) {
                            errorLogPOJO = new ErrorLogPOJO();
                            errorLogPOJO = seterrorLogPOJO("SIT", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                            deletearraylist.add(errorLogPOJO);    
                        
                    } else {
                            errorLogPOJO = new ErrorLogPOJO();
                            errorLogPOJO = seterrorLogPOJO("SIT", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                            errorloglist.add(errorLogPOJO);
                        
                    }
               
                } else if (rs.getString("shortname").equalsIgnoreCase("SIF")) {
                    String key2 = rs.getString("key2");
                    String key3 = rs.getString("key3");
                    String key4 = rs.getString("key4");
                    String key5 = rs.getString("key5");
                    String key6 = rs.getString("key5");
                    int dateoftlog = rs.getInt("dateoflog");
                    String timeoftlog = rs.getString("timeoflog"); 

                   // ResultSet rs1 = pstmt1.executeQuery("select b.key1,b.key2,b.key3,b.key4,b.key5,b.key6,b.dateoflog,b.timeoflog,b.description,a.data_syncdate,a.data_synctime from tbl_siteforcerelease as a,tbl_hf_log as b where a.siteid=b.key1 and b.shortname='SIF' and a.slno=b.key2 and a.data_syncdate > b.dateoflog union select b.key1,b.key2,b.key3,b.key4,b.key5,b.key6,b.dateoflog,b.timeoflog,b.description,a.data_syncdate,a.data_synctime from tbl_siteforcerelease as a,tbl_hf_log as b where a.siteid=b.key1 and b.shortname='SIF' and a.slno=b.key2 and a.data_syncdate = b.dateoflog and a.data_synctime>b.timeoflog");
                   String s22="select siteid,slno from tbl_siteforcerelease where siteid='"+ServerConsole.siteID+"' and slno="+key2+" and data_syncdate > " + dateoftlog + " union select siteid,slno from tbl_siteforcerelease where siteid='" +ServerConsole.siteID+ "' and slno="+key2+" and data_syncdate = " + dateoftlog + " and data_synctime>'" + timeoftlog + "'";
                    ResultSet rs1 = pstmt1.executeQuery("select siteid,slno from tbl_siteforcerelease where siteid='"+ServerConsole.siteID+"'and slno="+key2+" and data_syncdate > " + dateoftlog + " union select siteid,slno from tbl_siteforcerelease where siteid='" +ServerConsole.siteID+ "'and slno="+key2+" and data_syncdate = " + dateoftlog + " and data_synctime >'" + timeoftlog + "'");
                    //ResultSet rs1 = pstmt1.executeQuery("select siteid,slno from tbl_siteforcerelease where siteid='"+ServerConsole.siteID+"'and slno="+key2+" and data_syncdate > " + dateoftlog );         
                            if (rs1.next()) {
                        errorLogPOJO = new ErrorLogPOJO();
                        errorLogPOJO = seterrorLogPOJO("SIF", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                        deletearraylist.add(errorLogPOJO); 
                    } else {
                            errorLogPOJO = new ErrorLogPOJO();
                           errorLogPOJO = seterrorLogPOJO("SIF", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                           errorloglist.add(errorLogPOJO);
                       
                    }
               
                } else if (rs.getString("shortname").equalsIgnoreCase("SIC")) {
                    String key2 = rs.getString("key2");
                    String key3 = rs.getString("key3");
                    String key4 = rs.getString("key4");
                    String key5 = rs.getString("key5");
                    String key6 = rs.getString("key5");
                    int dateoftlog = rs.getInt("dateoflog");
                    String timeoftlog = rs.getString("timeoflog"); 
                   String stmt55="select siteid,slno from tbl_creditsalereference where siteid='"+ServerConsole.siteID+"' and slno="+key2+" and data_syncdate >"+dateoftlog+" union select siteid,slno from tbl_creditsalereference where siteid='"+ServerConsole.siteID+"' and slno="+key2+" and data_syncdate = "+dateoftlog+" and data_synctime >'" +timeoftlog+ "'";
                   
                   ResultSet rs1 = pstmt1.executeQuery("select siteid,slno from tbl_creditsalereference where siteid='"+ServerConsole.siteID+"' and slno="+key2+" and data_syncdate >"+dateoftlog+" union select siteid,slno from tbl_creditsalereference where siteid='"+ServerConsole.siteID+"' and slno="+key2+" and data_syncdate = "+dateoftlog+" and data_synctime>'" +timeoftlog+ "'");
                  
                    if (rs1.next()) {
                            errorLogPOJO = new ErrorLogPOJO();
                            errorLogPOJO = seterrorLogPOJO("SIC", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                            deletearraylist.add(errorLogPOJO);  
                        
                    } else {
                       
                            errorLogPOJO = new ErrorLogPOJO();
                            errorLogPOJO = seterrorLogPOJO("SIC", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                            errorloglist.add(errorLogPOJO);
                                             
                    }
               
                } else if (rs.getString("shortname").equalsIgnoreCase("SIH")) {
                    String key2 = rs.getString("key2");
                    String key3 = rs.getString("key3");
                    String key4 = rs.getString("key4");
                    String key5 = rs.getString("key5");
                    String key6 = rs.getString("key5");
                    int dateoftlog = rs.getInt("dateoflog");
                    String timeoftlog = rs.getString("timeoflog"); 
                  
                    ResultSet rs1 = pstmt1.executeQuery("select siteid,slno from tbl_holidaycalendar where siteid='" + ServerConsole.siteID+ "'and slno="+key2+" and data_syncdate > "+dateoftlog+ " union select siteid,slno from tbl_holidaycalendar where siteid='" + ServerConsole.siteID + "'and slno="+key2+" and data_syncdate = " +dateoftlog+ " and data_synctime >'"+timeoftlog +"'");
                    if (rs1.next()) {
                            errorLogPOJO = new ErrorLogPOJO();
                            errorLogPOJO = seterrorLogPOJO("SIH", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                            deletearraylist.add(errorLogPOJO);
                    } else {
                       
                            errorLogPOJO = new ErrorLogPOJO();
                            errorLogPOJO = seterrorLogPOJO("SIH", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                            errorloglist.add(errorLogPOJO);
                      
                    }
             
                } else if (rs.getString("shortname").equalsIgnoreCase("EMP")) {
                    String key2 = rs.getString("key2");
                    String key3 = rs.getString("key3");
                    String key4 = rs.getString("key4");
                    String key5 = rs.getString("key5");
                    String key6 = rs.getString("key5");
                    int dateoftlog = rs.getInt("dateoflog");
                    String timeoftlog = rs.getString("timeoflog"); 
                    
                    ResultSet rs1 = pstmt1.executeQuery("select storecode,empid from tbl_employeemaster where storecode='" + key2 + "'and empid='"+key3+"' and data_syncdate > " + dateoftlog + " union select storecode,empid from tbl_employeemaster where storecode='" + key2 + "'and empid='"+key3+"' and data_syncdate = " + dateoftlog + " and data_synctime >'" + timeoftlog + "'");
                    if (rs1.next()) {
                            errorLogPOJO = new ErrorLogPOJO();
                            errorLogPOJO = seterrorLogPOJO("EMP", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                            deletearraylist.add(errorLogPOJO);
                    } else {
                       
                            errorLogPOJO = new ErrorLogPOJO();
                            errorLogPOJO = seterrorLogPOJO("EMP", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                            errorloglist.add(errorLogPOJO);
                       
                    }
               
                } else if (rs.getString("shortname").equalsIgnoreCase("PRH")) {
                    String key2 = rs.getString("key2");
                    String key3 = rs.getString("key3");
                    String key4 = rs.getString("key4");
                    String key5 = rs.getString("key5");
                    String key6 = rs.getString("key5");
                    int dateoftlog = rs.getInt("dateoflog");
                    String timeoftlog = rs.getString("timeoflog"); 

                 
                   ResultSet rs1 = pstmt1.executeQuery("select storecode,promotionid from tbl_promotion_header where storecode='" + key2 + "'and promotionid='"+key3+"' and data_syncdate > " + dateoftlog + " union select storecode,promotionid from tbl_promotion_header where storecode='" + key2 + "'and promotionid='"+key3+"' and data_syncdate = " + dateoftlog + " and data_synctime>'" + timeoftlog + "'");
                    if (rs1.next()) {
                             errorLogPOJO = new ErrorLogPOJO();
                            errorLogPOJO = seterrorLogPOJO("PRH", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                            deletearraylist.add(errorLogPOJO);
                    } else {
                        
                            errorLogPOJO = new ErrorLogPOJO();
                            errorLogPOJO = seterrorLogPOJO("PRH", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                            errorloglist.add(errorLogPOJO);
                       
                    }
                
                } else if (rs.getString("shortname").equalsIgnoreCase("PRS")) {
                    String key2 = rs.getString("key2");
                    String key3 = rs.getString("key3");
                    String key4 = rs.getString("key4");
                    String key5 = rs.getString("key5");
                    String key6 = rs.getString("key5");
                    int dateoftlog = rs.getInt("dateoflog");
                    String timeoftlog = rs.getString("timeoflog"); 
                 
                     ResultSet rs1 = pstmt1.executeQuery("select storecode,promotionid,sellinglineno from tbl_selling_item where storecode='" + key2 + "'and promotionid='"+key3+"'and sellinglineno='"+key4+"' and data_syncdate > " + dateoftlog + " union select storecode,promotionid,sellinglineno from tbl_selling_item where storecode='" + key2 + "'and promotionid='"+key3+"'and sellinglineno='"+key4+"' and data_syncdate = " + dateoftlog + " and data_synctime>'" + timeoftlog + "'");
                    if (rs1.next()) {
                        errorLogPOJO = new ErrorLogPOJO();
                        errorLogPOJO = seterrorLogPOJO("PRS", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                        deletearraylist.add(errorLogPOJO);
                    } else {
                        ResultSet rs2 = pstmt1.executeQuery("select b.key1,b.key2,b.key3,b.key4,b.key5,b.key6,b.dateoflog,b.timeoflog,b.description,a.data_syncdate,a.data_synctime from tbl_selling_item as a,tbl_hf_log as b where a.storecode=b.key1 and b.shortname='PRS' and a.promotionid=b.key2 and a.sellinglineno=b.key3");
                       
                            errorLogPOJO = new ErrorLogPOJO();
                            errorLogPOJO = seterrorLogPOJO("PRS", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                            errorloglist.add(errorLogPOJO);
                        
                        
                    }
              
                } else if (rs.getString("shortname").equalsIgnoreCase("PRO")) {
                    String key2 = rs.getString("key2");
                    String key3 = rs.getString("key3");
                    String key4 = rs.getString("key4");
                    String key5 = rs.getString("key5");
                    String key6 = rs.getString("key5");
                    int dateoftlog = rs.getInt("dateoflog");
                    String timeoftlog = rs.getString("timeoflog"); 
                  //  ResultSet rs1 = pstmt1.executeQuery("select b.key1,b.key2,b.key3,b.key4,b.key5,b.key6,b.dateoflog,b.timeoflog,b.description,a.data_syncdate,a.data_synctime from tbl_offer as a,tbl_hf_log as b where a.storecode=b.key1 and b.shortname='PRO' and a.promotionid=b.key2 and a.promotionlineno=b.key3 and a.data_syncdate > b.dateoflog union select b.key1,b.key2,b.key3,b.key4,b.key5,b.key6,b.dateoflog,b.timeoflog,b.description,a.data_syncdate,a.data_synctime from tbl_offer as a,tbl_hf_log as b where a.storecode=b.key1 and b.shortname='PRO' and a.promotionid=b.key2 and a.promotionlineno=b.key3 and a.data_syncdate = b.dateoflog and a.data_synctime>b.timeoflog");
                  ResultSet rs1 = pstmt1.executeQuery("select storecode,promotionid,promotionlineno from tbl_offer where storecode='" + key2 + "'and promotionid='"+key3+"'and promotionlineno='"+key4+"' and data_syncdate > " + dateoftlog + " union select storecode,promotionid,promotionlineno from tbl_offer where storecode='" + key2 + "'and promotionid='"+key3+"'and promotionlineno='"+key4+"' and data_syncdate = " + dateoftlog + " and data_synctime>'" + timeoftlog + "'");
                    if (rs1.next()) {
                       errorLogPOJO = new ErrorLogPOJO();
                        errorLogPOJO = seterrorLogPOJO("PRO", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                        deletearraylist.add(errorLogPOJO); 
                       
                    } else {
                        ResultSet rs2 = pstmt1.executeQuery("select b.key1,b.key2,b.key3,b.key4,b.key5,b.key6,b.dateoflog,b.timeoflog,b.description,a.data_syncdate,a.data_synctime from tbl_offer as a,tbl_hf_log as b where a.storecode=b.key1 and b.shortname='PRO' and a.promotionid=b.key2 and a.promotionlineno=b.key3");
                       
                            errorLogPOJO = new ErrorLogPOJO();
                            errorLogPOJO = seterrorLogPOJO("PRO", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                            errorloglist.add(errorLogPOJO);
                      
                    }
               
                } else if (rs.getString("shortname").equalsIgnoreCase("DDL")) {
                    String key2 = rs.getString("key2");
                    String key3 = rs.getString("key3");
                    String key4 = rs.getString("key4");
                    String key5 = rs.getString("key5");
                    String key6 = rs.getString("key5");
                    int dateoftlog = rs.getInt("dateoflog");
                    String timeoftlog = rs.getString("timeoflog"); 
                  
                      ResultSet rs1 = pstmt1.executeQuery("select condtype,companycode,salesorg,condrecno from tbl_delaydeliverydiscount where condtype='" + key2 + "' and companycode='" + key3 + "' and salesorg='" + key4 + "'and condrecno='" + key5 + "'  and data_syncdate > " + dateoftlog + " union select condtype,companycode,salesorg,condrecno from tbl_delaydeliverydiscount where condtype='" + key2 + "' and companycode='" + key3 + "' and salesorg='" + key4 + "'and condrecno='" + key5 +"' and data_syncdate = " + dateoftlog + " and data_synctime>'" + timeoftlog + "'");
                    if (rs1.next()) {
                            errorLogPOJO = new ErrorLogPOJO();
                            errorLogPOJO = seterrorLogPOJO("DDL", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                            deletearraylist.add(errorLogPOJO);
                    } else {
                        
                       
                            errorLogPOJO = new ErrorLogPOJO();
                            errorLogPOJO = seterrorLogPOJO("DDL", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                            errorloglist.add(errorLogPOJO);
                       
                    }
               
                } else if (rs.getString("shortname").equalsIgnoreCase("CMT")) {
                    String key2 = rs.getString("key2");
                    String key3 = rs.getString("key3");
                    String key4 = rs.getString("key4");
                    String key5 = rs.getString("key5");
                    String key6 = rs.getString("key5");
                    int dateoftlog = rs.getInt("dateoflog");
                    String timeoftlog = rs.getString("timeoflog"); 
                    String s2="select condtype,country,region from tbl_conditiontypemaster where condtype='"+key2+"' and country='"+key3+"' and region='"+key4+"'  and data_syncdate > "+dateoftlog+" union select condtype,country,region from tbl_conditiontypemaster where condtype='"+key2+"' and country='"+key3+"' and region='"+key4+"' and data_syncdate = " + dateoftlog + " and data_synctime>'" + timeoftlog + "'";
                    
                    ResultSet rs1 = pstmt1.executeQuery("select condtype,country,region from tbl_conditiontypemaster where condtype='"+key2+"' and country='"+key3+"' and region='"+key4+"'  and data_syncdate > "+dateoftlog+" union select condtype,country,region from tbl_conditiontypemaster where condtype='"+key2+"' and country='"+key3+"' and region='"+key4+"' and data_syncdate = " + dateoftlog + " and data_synctime>'" + timeoftlog + "'");
                    
                    if (rs1.next()) {
                            errorLogPOJO = new ErrorLogPOJO();
                            errorLogPOJO = seterrorLogPOJO("CMT", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                            deletearraylist.add(errorLogPOJO);
                           
                        
                    } else {
                       
                           errorLogPOJO = new ErrorLogPOJO();
                            errorLogPOJO = seterrorLogPOJO("CMT", rs.getString("key1"), rs.getString("key2"), rs.getString("key3"), rs.getString("key4"), rs.getString("key5"), rs.getString("key6"), rs.getInt("dateoflog"), rs.getString("timeoflog"), rs.getString("description"));
                            errorloglist.add(errorLogPOJO);
                      
                    }
                
                }

            }
            deleteErrorlogEntries(deletearraylist,con);
            return errorloglist;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ErrorLogPOJO seterrorLogPOJO(String shortname, String key1, String key2, String key3, String key4, String key5, String key6, int dateodlog, String timeoflog, String desc) {
        ErrorLogPOJO errorLogPOJO = new ErrorLogPOJO();
        errorLogPOJO.setShortname(shortname);
        errorLogPOJO.setKey1(key1);
        errorLogPOJO.setKey2(key2);
        errorLogPOJO.setKey3(key3);
        errorLogPOJO.setKey4(key4);
        errorLogPOJO.setKey5(key5);
        errorLogPOJO.setKey6(key6);
        errorLogPOJO.setDateoflog(dateodlog);
        errorLogPOJO.setTimeoflog(timeoflog);
        errorLogPOJO.setStatus("F");
        errorLogPOJO.setDescription(desc);
        return errorLogPOJO;
    }
    
    
    public void deleteErrorlogEntries(ArrayList deletionlist,Connection con)
    {
     try
     {
     Statement pstmt = con.createStatement();
    
    Iterator iter=deletionlist.iterator();
    while(iter.hasNext())
    {
    ErrorLogPOJO   errorLogPOJO = (ErrorLogPOJO) iter.next();
  //  rs= pstmt.executeQuery("delete from tbl_hf_log where shortname='"+errorLogPOJO.getShortname()+"' and key1='"+errorLogPOJO.getKey1()+"' and key2='"+errorLogPOJO.getKey2()+"' and key3='"+errorLogPOJO.getKey3()+"' and key4='"+errorLogPOJO.getKey4()+"' and key5='"+errorLogPOJO.getKey5()+"' and key6='"+errorLogPOJO.getKey6()+"' and dateoflog="+errorLogPOJO.getDateoflog()+" and timeoflog='"+errorLogPOJO.getTimeoflog()+"' and status='"+errorLogPOJO.getStatus()+"' and description='"+errorLogPOJO.getDescription()+"'");
    pstmt.executeUpdate("delete from tbl_hf_log where shortname='"+errorLogPOJO.getShortname()+"' and key1='"+errorLogPOJO.getKey1()+"' and key2='"+errorLogPOJO.getKey2()+"' and key3='"+errorLogPOJO.getKey3()+"' and key4='"+errorLogPOJO.getKey4()+"' and key5='"+errorLogPOJO.getKey5()+"' and key6='"+errorLogPOJO.getKey6()+"'");
    }
     }catch(Exception e)
     {
     e.printStackTrace();
     }
    
    
    }
    
}
