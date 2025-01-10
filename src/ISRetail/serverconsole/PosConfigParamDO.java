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
package ISRetail.serverconsole;

import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.utility.validations.Validations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author enteg
 */
public class PosConfigParamDO {

    public static String getValForThePosConfigKey(Connection con, String configKey) throws SQLException {
        //fiscal year i scur fiscal year- one new record should be iserted at the time of fiscal year updation
        PreparedStatement pstmt;
        ResultSet rs;
        String configVal = null;
        try {
            pstmt = con.prepareStatement("select configval from tbl_pos_configparam where configkey= ?");
            pstmt.setString(1, configKey);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                configVal = rs.getString("configval");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return configVal;
    }
    
//    public static ArrayList<String> getConfigValList(Connection con, String val) {
//        Statement statement1 = null;
//        ResultSet rs = null;
//        ArrayList<String> ConfigValList = null;
//        try {
//            statement1 = con.createStatement();
//            rs = statement1.executeQuery("select configval from dbo.tbl_pos_configparam where configkey like '" + val + "%'");
//            ConfigValList = new ArrayList<String>();
//            while (rs.next()) {
//                if (Validations.isFieldNotEmpty(rs.getString("configval"))) {
//                    ConfigValList.add(rs.getString("configval"));
//                }
//            }
//            System.out.println("getMerchCategoryList---------> array list size =   " + ConfigValList.size());
//            return ConfigValList;
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return null;
//        } finally {
//            statement1 = null;
//            rs = null;
//        }
//    }
    
    public static void updateValForThePosConfigKey(Connection con, String configKey, String value) throws SQLException {
        //fiscal year i scur fiscal year- one new record should be iserted at the time of fiscal year updation
        PreparedStatement pstmt;
        try {
            pstmt = con.prepareStatement("update tbl_pos_configparam set configval = ? where configkey = ?");
            pstmt.setString(1, value);
            pstmt.setString(2, configKey);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
        }
    }

    public static int getBusinessDateReqFreq(Connection con) {
        int freq = 0;
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "business_date_req_freq"));
            freq = freq * 1000;
        } catch (Exception e) {
            e.printStackTrace();
            freq = 4 * 60 * 60 * 1000;//4 hr 
        } finally {
        }
        return freq;
    }

    public static int getBusinessDateReqFreqNormal(Connection con) {
        int freq = 0;
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "business_date_req_normal_freq"));
            System.out.println("business_date_req_normal_freq maintained in db" + freq);
            freq = freq * 1000;
        } catch (Exception e) {
            e.printStackTrace();
            freq = 60 * 1000;//1 min
        } finally {
        }
        return freq;
    }

    public static int getMasterDataReqFreq(Connection con) {
        int freq = 0;
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "master_data_req_freq"));
            freq = freq * 1000;
        } catch (Exception e) {
            e.printStackTrace();
            freq = 4 * 60 * 60 * 1000;//4 hr
        } finally {
        }
        return freq;

    }

    public static int getStockMasterDataReqFreq(Connection con) {
        int freq = 0;
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "stock_download_freq"));
            freq = freq * 1000;
        } catch (Exception e) {
            e.printStackTrace();
            freq = 4 * 60 * 60 * 1000;//4 hr
        } finally {
        }
        return freq;

    }

    public static int getConfigDataReqFreq(Connection con) {
        int freq = 0;
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "config_data_req_freq"));
            freq = freq * 1000;
        } catch (Exception e) {
            e.printStackTrace();
            freq = 4 * 60 * 60 * 1000;//4 hr
        } finally {
        }
        return freq;

    }

    public static int getTransactionSendingFreq(Connection con) {
        int freq = 0;
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "transaction_send_freq"));
            freq = freq * 1000;
        } catch (Exception e) {
            e.printStackTrace();
            freq = 20 * 1000;//20 sec
        } finally {
        }
        return freq;

    }

    public static int getTransactionRespReqFreq(Connection con) {
        int freq = 0;
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "trans_resp_req_freq"));
            freq = freq * 1000;
        } catch (Exception e) {
            e.printStackTrace();
            freq = 5 * 60 * 1000;//5 Min
        } finally {
        }
        return freq;

    }

    //***Time interval to reinitiate a transaction, after it is cancelled for some reason 
    //This interval is not used for the thread to go for sleeping, instead it will be used in the query..to fetch staging data
    //See next method(getUnitOfReinitiate_freq_for_cancelledTrans) also to get unit(HOUR,MIN,SEC) for this intervel, when used in query
    public static int getPeriodToResetTransAfterCancelled(Connection con) {
        int freq = 0;
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "period_to_reset_trans_after_cancelled"));
            //freq=freq*1000; commented on 24 Jun 2011 For Auto Reinitiate Cancelled Transactions

        } catch (Exception e) {
            e.printStackTrace();
            //freq = 2*60*60*1000;//2 Hr-- commented on 24 Jun 2011 For Auto Reinitiate Cancelled Transactions
            freq = 2 * 60 * 60;//2 Hr
        } finally {
        }
        return freq;
    }

    public static int getVersionCheckReqFreq(Connection con) {
        int freq = 0;
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "version_check_req_freq"));
            freq = freq * 1000;
        } catch (Exception e) {
            e.printStackTrace();
            freq = 5 * 60 * 1000;//5 min
        } finally {
        }
        return freq;

    }

    public static int getServerIsUpCheckFreq(Connection con) {
        int freq = 0;
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "server_up_check_freq"));
            freq = freq * 1000;
        } catch (Exception e) {
            e.printStackTrace();
            freq = 2 * 60 * 1000;//2 min
        } finally {
        }
        return freq;

    }

    public static int getErrorReportCheckFreq(Connection con) {
        int freq = 0;
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "error_report_check_freq"));
            freq = freq * 1000;
        } catch (Exception e) {
            e.printStackTrace();
            freq = 2 * 60 * 1000;//2 min
        } finally {
        }
        return freq;

    }

    public static int getSystemDateCheckFreq(Connection con) {
        int freq = 0;
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "system_date_req_freq"));
            freq = freq * 1000;
        } catch (Exception e) {
            e.printStackTrace();
            // freq = 2*60*1000;//2 min
            freq = 15 * 1000;
        } finally {
        }
        return freq;

    }

    public static int getDatabaseBackupReqFreq() {
        int freq = 0;
        MsdeConnection msdeConnection = new MsdeConnection();
        Connection con = msdeConnection.createConnection();
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "database_backup_req_freq"));

            if (freq == 0) {
                freq = 2;//2 hr
            } else if (freq > 0 && freq < 3600) {
                freq = freq / 1800;
            } else if (freq >= 3600) {
                freq = freq / 3600;
            }
            if (freq > 23) {
                freq = 2;
            }
            //freq=freq*1000;
        } catch (Exception e) {
            e.printStackTrace();
            //   freq = 12*60*60*1000;//12 hr
            freq = 2;//2hr
        } finally {
        }
        return freq;

    }

    public static int getBOD_Activity_Freq(Connection con) {
        int freq = 0;
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "beg_of_day_activities"));
            freq = freq * 1000;
        } catch (Exception e) {
            e.printStackTrace();
            freq = 12 * 60 * 60 * 1000;//12 hr
        } finally {
        }
        return freq;
    }

    public static int getArchivalReq_Freq(Connection con) {
        int freq = 0;
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "archival_req_freq"));
            freq = freq * 1000;
        } catch (Exception e) {
            e.printStackTrace();
            freq = 12 * 60 * 60 * 1000;//12 hr
        } finally {
        }
        return freq;
    }

    //****Frequency to Execution of Errorlog- use only 1 of these 3

    public static int getErrorLogFreq(Connection con) {
        int freq = 0;
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "error_log_send_freq"));
            freq = freq * 1000;
        } catch (Exception e) {
            e.printStackTrace();
            freq = 12 * 60 * 60 * 1000;//12 hr
        } finally {
        }
        return freq;
    }

    //****Frequency to re-run ,If Interruption(Exception) occured  during the execution of any of the scheduler -- use only 1 of these 3

    public static int getReRunFreq_For_Any_SchedulerErr(Connection con) {
        int freq = 0;
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "re_run_freq_for_any_err"));
            freq = freq * 1000;
        } catch (Exception e) {
            e.printStackTrace();
            freq = 1 * 60 * 60 * 1000;//12 hr
        } finally {
        }
        return freq;
    }

    //********For Business date checking.. values will be in days

    public static int getMin_date_diff_with_internet(Connection con) {
        int freq = 0;
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "min_date_diff_with_internet"));
        } catch (Exception e) {
            e.printStackTrace();
            freq = 2;
        } finally {
        }
        return freq;
    }

    public static int getMin_date_diff_without_internet(Connection con) {
        int freq = 0;
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "min_date_diff_without_internet"));
        } catch (Exception e) {
            e.printStackTrace();
            freq = 1;
        } finally {
        }
        return freq;
    }

    public static int getMax_date_diff_with_internet(Connection con) {
        int freq = 0;
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "max_date_diff_with_internet"));
        } catch (Exception e) {
            e.printStackTrace();
            freq = 9;//default
        } finally {
        }
        return freq;
    }

    public static int getMax_date_diff_without_internet(Connection con) {
        int freq = 0;
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "max_date_diff_without_internet"));
        } catch (Exception e) {
            e.printStackTrace();
            freq = 3;//default
        } finally {
        }
        return freq;
    }

   //Code added on 03-02-2010 for getting the frequency of pos staging error report mail sending option
    public static int getPosStagingErrorReportFreq(Connection con) {
        int freq = 0;
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "posstg_errorreport_freq"));
            freq = freq * 1000;
        } catch (Exception e) {
            e.printStackTrace();
            freq = 1 * 30 * 60 * 1000;//6 hr
        } finally {
        }
        return freq;

    }

    public static int getArticleDataDownlaodFreq(Connection con) {
        int freq = 0;
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "revised_articledata_req_freq"));
            freq = freq * 1000;
        } catch (Exception e) {
            e.printStackTrace();
            freq = 4 * 60 * 60 * 1000;//4 hr
        } finally {
        }
        return freq;

    }

    public static int getPOS_ClientUpdateReqFreq(Connection con) {
        int freq = 0;
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "POSClient_check_req_freq"));
            freq = freq * 1000;
        } catch (Exception e) {
            e.printStackTrace();
            freq = 5 * 60 * 1000;//5 min
        } finally {
        }
        return freq;

    }
     public static int getInvoiceDetailsSentReqFreq(Connection con) {
        int freq = 0;
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "karnival_invoicecopysent_freq"));
            freq = freq * 1000;
        } catch (Exception e) {
            e.printStackTrace();
            freq = 5 * 60 * 1000;//5 min
        } finally {
        }
        return freq;
    }
     public static int getGVDetailsSentToSAPReqFreq(Connection con) {
        int freq = 0;
        try {
            freq = Integer.valueOf(getValForThePosConfigKey(con, "gvSent_freq"));
            freq = freq * 1000;
        } catch (Exception e) {
            e.printStackTrace();
            freq = 5 * 60 * 1000;//5 min
        } finally {
        }
        return freq;
    }

}
