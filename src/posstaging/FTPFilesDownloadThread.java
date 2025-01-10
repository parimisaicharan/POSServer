/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package posstaging;

import ISRetail.Helpers.NetConnection;
import ISRetail.ServerRmi.StartRMIServer;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.serverconsole.PosConfigParamDO;
import ISRetail.utility.validations.Validations;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
//import org.apache.commons.net.PrintCommandListener;

/**
 *
 * @author eyeplus
 */
public class FTPFilesDownloadThread implements Runnable {

    private JTextArea statusArea;

    public FTPFilesDownloadThread(JTextArea statusArea) {
        this.statusArea = statusArea;
    }

    public FTPFilesDownloadThread() {

    }

    public void run() {
        while (true) {
            FTPClient fc = null;
            MsdeConnection msdeConnection = null;
            Connection con = null;
            String ftphostname = "";
            String ftpusername = "";
            String ftppassword = "";
            String copyfiles = "";
            String path = null;
            String storecode = "";
            String dashboardurl = "";
            HashMap<String, String> filepath = null;
            PosStagingDO psd = null;
            SiteMasterDO siteMasterDO = null;
            int freq = 0;
            String amazonDownloadEnabled = "";
            String s3AccessKey = "";
            String s3SecretKey = "";
            String inetConnecitonCheckingurl = "";
            String s3BucketName = "";
            String s3Region = "";
            String s3SendFilePath = "";
            boolean amazonConnected = false;
            String keyFileName = "";
            try {
                Thread.sleep(20000);
                fc = new FTPClient();

                msdeConnection = new MsdeConnection();
                con = msdeConnection.createConnection();
                statusArea = new JTextArea();
                statusArea.append("\n\n*******Started - checking for POS latest update******");
                statusArea.append("\nPOS Client update checking.......");
//                System.out.println("POS Client update checking.......");
                copyfiles = PosConfigParamDO.getValForThePosConfigKey(con, "ftp_send_files_posserver");
                freq = PosConfigParamDO.getPOS_ClientUpdateReqFreq(con);
                if (Validations.isFieldNotEmpty(copyfiles) && copyfiles.equalsIgnoreCase("Y")) {
                    psd = new PosStagingDO();
                    ftphostname = PosConfigParamDO.getValForThePosConfigKey(con, "ftphostname");
                    ftpusername = PosConfigParamDO.getValForThePosConfigKey(con, "ftpusername");
                    ftppassword = PosConfigParamDO.getValForThePosConfigKey(con, "ftppassword");
                    path = psd.getsourcelocation(con);
                    filepath = psd.getFilePath(con);
                    siteMasterDO = new SiteMasterDO();
                    storecode = siteMasterDO.getSiteId(con);
                    dashboardurl = PosConfigParamDO.getValForThePosConfigKey(con, "TitanEyeDashBoard_url");
                    amazonDownloadEnabled = PosConfigParamDO.getValForThePosConfigKey(con, "AmazonServer_enabled");
                    s3AccessKey = PosConfigParamDO.getValForThePosConfigKey(con, "AmazonS3AccessKey");
                    s3SecretKey = PosConfigParamDO.getValForThePosConfigKey(con, "AmazonS3SecretKey");
                    inetConnecitonCheckingurl = PosConfigParamDO.getValForThePosConfigKey(con, "url_xi");
                    s3BucketName = PosConfigParamDO.getValForThePosConfigKey(con, "AmazonS3BucketName");
                    s3Region = PosConfigParamDO.getValForThePosConfigKey(con, "AmazonS3Region");
                    s3SendFilePath = PosConfigParamDO.getValForThePosConfigKey(con, "AmazonS3SendFilesPath");
                    String downloadedFileName = "";
                    keyFileName = PosConfigParamDO.getValForThePosConfigKey(con, "AmazonS3SendFileName");
                    //fc.connect("121.242.228.204");
                    if (!Validations.isFieldNotEmpty(amazonDownloadEnabled) || amazonDownloadEnabled.equalsIgnoreCase("N")) {
                        fc.connect(ftphostname.trim());
                    }
                    statusArea.append("\nPOS Client update available.Downloading started.......");
                    if (FTPReply.isPositiveCompletion(fc.getReplyCode()) || Validations.isFieldNotEmpty(amazonDownloadEnabled) && amazonDownloadEnabled.equalsIgnoreCase("Y")) {
                        if (!Validations.isFieldNotEmpty(amazonDownloadEnabled) || amazonDownloadEnabled.equalsIgnoreCase("N")) {
                            fc.login(ftpusername.trim(), ftppassword.trim());
                            fc.setFileType(FTPClient.BINARY_FILE_TYPE);
                            fc.enterLocalPassiveMode();
//                        fc.setConnectTimeout(900000);
                        }
                        if (path != null) {
                            boolean upgrade = false, IsDownloaded = false;
                            String abpath = new File(new File("").getAbsolutePath()).getParent();
                            String copylocation = abpath + path;
                            System.out.println("path : " + path + " abpath :" + abpath + " copylocation : " + copylocation);
                            if (new File(copylocation).exists()) {
                                deleteFolderandSubFolder(new File(copylocation));
                            }
                            if (!new File(copylocation).exists()) {
                                System.out.println("Folder Created : " + new File(copylocation).mkdirs());
                            }

//                            System.out.println("response" + response);
                            if (true) {
                                FTPFile[] listFiles = null;
                                if (!Validations.isFieldNotEmpty(amazonDownloadEnabled) || amazonDownloadEnabled.equalsIgnoreCase("N")) {

                                    fc.changeWorkingDirectory("\\ewposs\\ewposs\\SendFiles\\Files\\");

                                    listFiles = fc.listFiles();
                                    System.out.println("Working directry after : " + fc.printWorkingDirectory());

                                    System.out.println("listFiles :::: " + listFiles.length);
//                                        if(listFiles.length == DestinationLoc.size()){      
                                    statusArea.append("\nSource Download : FTP \n Patch Download Started !!!");
                                    for (int i = 0; i < listFiles.length; i++) {
                                        FTPFile fTPFile = listFiles[i];
                                        downloadedFileName = fTPFile.getName();
                                        System.out.println("File Names " + fTPFile.getName());
                                        System.out.println("File Type " + fTPFile.getType());
//                                    fc.setControlKeepAliveTimeout(900000);
                                        FileOutputStream fout = new FileOutputStream(copylocation + "\\" + fTPFile.getName());
                                        boolean filecopied = fc.retrieveFile(fTPFile.getName(), fout);
                                        File file = new File(copylocation + "\\" + fTPFile.getName());
                                        if (file.exists()) {
//                                        double bytes = file.length();
                                            System.out.println("bytes : " + fTPFile.getSize() + "  --------" + file.length());
                                            if (filecopied && fTPFile.getSize() == file.length()) {
                                                upgrade = true;
                                                IsDownloaded = true;
                                            } else {
                                                upgrade = false;
                                                IsDownloaded = false;
                                                i--;
                                                System.out.println("*******Download faild *********" + fTPFile.getName() + " size : " + fTPFile.getSize() + " filecopied " + filecopied);
                                                break;
                                            }

                                        }
                                        System.out.println("*******New File is downloaded *********" + fTPFile.getName() + " size : " + fTPFile.getSize() + " filecopied " + filecopied);
                                        fout.flush();
                                        fout.close();
                                    }
                                } else if (Validations.isFieldNotEmpty(amazonDownloadEnabled) || amazonDownloadEnabled.equalsIgnoreCase("Y")) {
                                    try {
                                        int networkRes = NetConnection.checkInternetConnection();
                                        if (networkRes == 0 || networkRes == 1) {
                                            amazonConnected = true;
                                        }
//                                if (downloadFTPDO.performConnectionChecking()) {
                                        //  AWSCredentials awsCredentials = new BasicAWSCredentials("AKIA5LL5T5KMEIY4MJPE", "zv0XC54fAaZFfKf9WC73jDHAAwf6ta4JkzhujN2J");
                                        if (amazonConnected) {
                                            S3Object object;
                                            AWSCredentials awsCredentials = new BasicAWSCredentials(s3AccessKey, s3SecretKey);
                                            AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                                                    .withRegion(s3Region)
                                                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                                                    .build();
                                            if (amazonS3.doesBucketExistV2(s3BucketName)) {
                                                try {
                                                    if (Validations.isFieldNotEmpty(s3SendFilePath)) {
                                                        object = amazonS3.getObject(s3BucketName + s3SendFilePath, keyFileName);
                                                    } else {
                                                        object = amazonS3.getObject(s3BucketName, keyFileName);
                                                    }
                                                    statusArea.append("\nSource Download : Amazon S3 \n Patch Download Started !!!");
                                                    S3ObjectInputStream s3is = object.getObjectContent();
                                                    if (!new File(copylocation).exists()) {
                                                        new File(copylocation).mkdirs();
                                                    } else {
                                                        File delfile[] = new File(copylocation).listFiles();
                                                        for (int i = 0; i < delfile.length; i++) {
                                                            System.out.println(delfile[i].getName());
                                                            delfile[i].delete();
                                                        }
                                                    }
                                                    // ftpconnected = true;
                                                    FileOutputStream fos = new FileOutputStream(copylocation + keyFileName, false);
//                                                    FileOutputStream fos = new FileOutputStream(copylocation, true);
//                                    new File(zipFilePath + "\\NewVersionFiles\\").mkdirs();
                                                    byte[] read_buf = new byte[1024];
                                                    int read_len = 0;
                                                    while ((read_len = s3is.read(read_buf)) > 0) {
                                                        fos.write(read_buf, 0, read_len);
                                                    }
                                                    s3is.close();
                                                    fos.close();
                                                    IsDownloaded = true;
                                                } catch (Exception e) {
                                                    JOptionPane.showMessageDialog(null, "Patch Update Files Not Available.\n Please Contact POS Support Team !!", "Patch Update Info", JOptionPane.INFORMATION_MESSAGE);
                                                    IsDownloaded = false;
                                                    e.printStackTrace();
                                                }

                                            }
                                        } else {
                                            statusArea.append("\n No Network Connection !!!\n Unable to Download the Files from Server !!!");
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    //Amazon Code End
                                } else {

                                    JOptionPane.showConfirmDialog(null, "Access Denied to Download the Files.\n Please Contact POS Support Team!!", "Error Message", JOptionPane.ERROR_MESSAGE);
                                    IsDownloaded = false;
                                }

                                StartRMIServer rc = new StartRMIServer();
                                String s = rc.getRunningClients();
                                System.out.println("***********" + s);

                                String loc1 = "\\POSServer\\";
                                String loc2 = "\\POSClient\\";
                                String lib = "lib";
                                String wsdlfiles = "wsdlfiles";
//                                String textFile = ".txt";
//                                String propFile = ".properties";
                                for (String filename : filepath.keySet()) {
                                    upgrade = true;
                                    String path1 = filepath.get(filename) + filename;
                                    File sourcefile = new File(copylocation + "\\" + filename);
                                    // File sourcefile = new File(copylocation + "\\" + ".jar");
                                    String sourcePath = sourcefile.getAbsolutePath();
                                    int indexOfString = 0;
                                    // if (sourcePath.endsWith("FTPDownload.zip")) {//Commented by Balachander V on 17.7.2020
//                                            indexOfString = sourcePath.lastIndexOf("\\FTP");
//                                        } else {
//                                            indexOfString = sourcePath.lastIndexOf("\\POS");
//                                        }
//                                        if (Validations.isFieldNotEmpty(indexOfString > 0)) {
//                                            sourcePath = sourcePath.substring(0, indexOfString + 1);
//                                            path1 = filepath.get(filename) + filename;
                                    // boolean n = FTPFilesDownloadThread.ExtractZipFile(sourcePath);//Commented by Balachander V on 17.7.2020
                                    if ((Validations.isFieldNotEmpty(downloadedFileName) && downloadedFileName.endsWith(".zip")) || Validations.isFieldNotEmpty(keyFileName) && keyFileName.endsWith(".zip") && Validations.isFieldNotEmpty(amazonDownloadEnabled) && amazonDownloadEnabled.equalsIgnoreCase("Y")) {
                                        boolean n = FTPFilesDownloadThread.ExtractZipFile(copylocation);
                                        //  }
                                    } else {
                                        upgrade = false;
                                    }
                                }
                                while (upgrade) {
                                    if (s.trim().length() == 0) {  //           
                                        for (String filename : filepath.keySet()) {
                                            File sourcefile = new File(copylocation + "\\" + filename);
                                            String path1 = filepath.get(filename) + filename;
                                            String dl = filepath.get(filename);
                                            if (dl.contains(lib)) {
                                                if (!new File(abpath + loc1 + filepath.get(filename)).exists()) {
                                                    System.out.println("Folder Created : " + new File(abpath + loc1 + filepath.get(filename)).mkdirs());
                                                }
                                                String destlocation = abpath + loc1 + path1;
                                                File destfile = new File(destlocation);
                                                upgrade = copyFiles(sourcefile, destfile);
                                                if (!new File(abpath + loc2 + filepath.get(filename)).exists()) {
                                                    System.out.println("Folder Created : " + new File(abpath + loc2 + filepath.get(filename)).mkdirs());
                                                }
                                                String destlocation1 = abpath + loc2 + path1;
                                                File destfile1 = new File(destlocation1);
                                                upgrade = copyFiles(sourcefile, destfile1);
                                                sourcefile.delete();
                                            } else if (dl.contains(wsdlfiles)) {
                                                if (!new File(abpath + loc1 + filepath.get(filename)).exists()) {
                                                    System.out.println("Folder Created : " + new File(abpath + loc1 + filepath.get(filename)).mkdirs());
                                                }
                                                String destlocation = abpath + loc1 + path1;
                                                File destfile = new File(destlocation);
                                                upgrade = copyFiles(sourcefile, destfile);
                                                if (!new File(abpath + loc2 + filepath.get(filename)).exists()) {
                                                    System.out.println("Folder Created : " + new File(abpath + loc2 + filepath.get(filename)).mkdirs());
                                                }
                                                String destlocation1 = abpath + loc2 + path1;
                                                File destfile1 = new File(destlocation1);
                                                upgrade = copyFiles(sourcefile, destfile1);
                                                sourcefile.delete();
                                            } else {
                                                sourcefile = new File(copylocation + "\\" + filename);
                                                // sourcefile.delete();
                                                sourcefile = new File(copylocation + "\\" + filename);
                                                if (!new File(abpath + filepath.get(filename)).exists()) {
                                                    System.out.println("Folder Created : " + new File(abpath + filepath.get(filename)).mkdirs());
                                                }
                                                String destlocation = abpath + path1;
                                                File destfile = new File(destlocation);
                                                upgrade = copyFiles(sourcefile, destfile);
                                                sourcefile.delete();

                                            }
                                        }
                                    } else {
                                        //statusArea.append("\nLatest updates are available for EyePlus !.Please close POS Client at " + s);
                                        JOptionPane.showMessageDialog(null, "Latest updates are available for POS Client!.Please close POS Client at " + s);
                                        s = rc.getRunningClients();
                                        System.out.println("******* " + s);
                                    }
                                }

                                if ((Validations.isFieldNotEmpty(listFiles) && listFiles.length > 0) || IsDownloaded && Validations.isFieldNotEmpty(amazonDownloadEnabled) && amazonDownloadEnabled.equalsIgnoreCase("Y")) {
                                    int res = updateDashboard(storecode, dashboardurl);
                                    System.out.println("**************************************");
                                    System.out.println("**************************************");
                                    System.out.println("Response ***************** : " + res);
                                    System.out.println("**************************************");
                                    System.out.println("**************************************");
                                    PosConfigParamDO.updateValForThePosConfigKey(con, "ftp_send_files_posserver", "N");
                                    statusArea.append("\nLatest Updates Applied Successfully !!. \nYou may start POS Client!!");
                                    JOptionPane.showMessageDialog(null, "Latest Updates Applied Successfully !!. \nYou may start POS Client!!", "Information Message", JOptionPane.INFORMATION_MESSAGE);

                                }
//                                    }else{
//                                            statusArea.append("\nIssue with maintained path");
//                                        }

//                                fc.logout();
//                                fc.disconnect();
                                try {
                                    if (new File(copylocation).exists()) {
                                        deleteFolderandSubFolder(new File(copylocation));
                                    }
//                                    copylocation = copylocation + "/";
//                                    File deleteDir = new File(copylocation);
//                                    FileUtils.deleteDirectory(deleteDir);
                                } catch (Exception e) {
//                                    copylocation = copylocation + "/";
//                                    File deleteDir = new File(copylocation);
//                                    FileUtils.deleteDirectory(deleteDir);
                                    e.printStackTrace();
                                }
                            } else {
                                fc.makeDirectory("\\ewposs\\ewposs\\SendFiles\\Files\\");
                                System.out.println("No Fiels folder available in FTP");
                            }
//                                } else {
//                                    System.out.println("No path available in the path file");
//                                }
                        } else {
                            System.out.println("Copy file Location is not maintained in DB");
                        }
//                        } else {
//                            fc.makeDirectory("\\ewposs\\SendFiles\\");
//                        }
                    } else {
                        fc.disconnect();
                        System.out.println("Ftp not connected");
                    }
                } else {
                    statusArea.append("\nLatest updates not available.......");
                    System.out.println("Ftp download from POS Server not activated");
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
//            statusArea.append("\nClient update Threat - Sleeping for " + (freq / 1000) + " Seconds"); 
                try {
                    statusArea.append("\nLatest update checker - sleeing for " + (freq / 1000) + " Seconds");
                    Thread.sleep(freq);

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                fc = null;
                msdeConnection = null;
                con = null;
                ftphostname = null;
                ftpusername = null;
                ftppassword = null;
            }
        }
    }

    private boolean copyFileUsingStream(File source, File dest) throws IOException {

        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }

        } finally {
            is.close();
            os.close();
        }
        return false;
    }

    private int updateDashboard(String siteid, String urlstr) {
        int responsecode = 0;
        try {

            String versionid = "1";

            HttpURLConnection urlConn;
            //String urlStr = null;

            urlstr = urlstr + "VersionUpdate?storecode=";
            urlstr = urlstr + siteid;
            urlstr = urlstr + "&versionno=";
            urlstr = urlstr + (Integer.parseInt(versionid));
            System.out.println(urlstr);

            URL url = new URL(urlstr);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setReadTimeout(60000);
            urlConn.connect();
            responsecode = urlConn.getResponseCode();
            System.out.println(responsecode);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return responsecode;
    }

    public static boolean ExtractZipFile(String srcFilePath) {
        boolean extracted = false;
        try {
            //deleteFTPObj();
            //  Download File Path
            int j = 0, k = 0;
            String zipFile = "";
            File downFilePath = new File(srcFilePath);
            if (downFilePath.exists()) {
                File filename[] = downFilePath.listFiles();
                for (int i = 0; i < filename.length; i++) {
                    if (filename[i].getName().endsWith(".zip")) {
                        zipFile = filename[i].getName();
                        System.out.println("1" + filename[i].getName());
                    }
                }
            }

            ZipFile objZipFile = new ZipFile(new File(srcFilePath + "\\" + zipFile));
            for (Enumeration entries = objZipFile.entries(); entries.hasMoreElements();) {

                j++;
                ZipEntry zipEntry = (ZipEntry) entries.nextElement();
                String zipEntryName = zipEntry.getName();
                System.out.println(zipEntryName);
                StringTokenizer st = new StringTokenizer(zipEntryName, "/");

                while (st.hasMoreElements()) {
                    k++;
                    zipEntryName = st.nextToken();
                    System.out.println(j + "---" + k + "---" + zipEntryName);
                }

                FileOutputStream out = new FileOutputStream(srcFilePath + "\\" + zipEntryName);
                InputStream in = objZipFile.getInputStream(zipEntry);

                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                // Close streams
                out.close();
                in.close();

            }
            extracted = true;
        } catch (ZipException e) {
            extracted = false;
            e.printStackTrace();
        } catch (IOException e) {
            extracted = false;
            e.printStackTrace();
        }
        return extracted;
    }

    private boolean copyFiles(File source, File dest) throws IOException {
        boolean status = false;
        try {
            FileUtils.copyFile(source, dest);
            status = true;
        } catch (Exception e) {

            e.printStackTrace();

        }
        return status;
    }

    static void deleteFolderandSubFolder(File file) {
        for (File subFile : file.listFiles()) {
            if (subFile.isDirectory()) {
                deleteFolderandSubFolder(subFile);
            } else {
                subFile.delete();
            }
        }
        file.delete();
    }
}
