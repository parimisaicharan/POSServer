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
 * User Interface for Manual Down Loading Article and other Master data from Server
 * Also Provides a Stock Master Download
 * 
 * 
 */
package ISRetail.article;

import ISRetail.Helpers.ConvertDate;
import ISRetail.components.CustomCellRenderer;
import ISRetail.components.CustomNonEditableCellRenderer;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.serverconsole.PosConfigParamDO;
import ISRetail.serverconsole.ServerConsole;
import ISRetail.stock.StockMaster;
import ISRetail.utility.validations.Validations;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import posstaging.ArticleWebService;
import posstaging.CheckSystemDate;

/**
 *
 * @author  Administrator
 */
public class ArticleMaster extends javax.swing.JPanel {

    ServerConsole serverConsole;
    Thread thread = null;
    Timer timer;
    final static int interval = 1000;
    int i = 0, authentication_attempts = 0;
    ArticleThread articleThread = null;
    JTextArea statusArea;
    private final Color selectedColor = new Color(255, 255, 150);//Color of Textfields when focu gained
    private final Color originalColor = new Color(255, 255, 255);//Color of Textfields when focu lost
    private final Color lightYellowColor = new Color(255, 253, 226);
    CustomNonEditableCellRenderer rightAlignedCellRendr;
    CustomCellRenderer leftAlignedCellRendr;
    private String master_data_download_freq;
    private String manual_stock_master_download = "Y";

    /** Creates new form ArticleMaster */
    public ArticleMaster(ServerConsole serverConsole) {
        this.serverConsole = serverConsole;
        initComponents();

        statusArea = new JTextArea();
        AuthFrame.setVisible(false);
        DonloadActionButton.setEnabled(false);

        //  stockMaster1.setVisible(false);
        // start : master data download development
        Connection  c = null;
        try {
            c = new MsdeConnection().createConnection();
            master_data_download_freq = PosConfigParamDO.getValForThePosConfigKey(c,"master_data_download_freq");
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
        /*Stock Master Data Download */ 
        try {
            c = new MsdeConnection().createConnection();
            
            manual_stock_master_download = PosConfigParamDO.getValForThePosConfigKey(c,"manual_stock_master_download");
            if(manual_stock_master_download.equalsIgnoreCase("Y"))
            {
                stockMasterFlag.setEnabled(true);
            }
            else
            {
                stockMasterFlag.setEnabled(false);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException ex) { 
                ex.printStackTrace();
            }
        }
        /*Stock Master Data Download */
        
        //if(Validations.isFieldNotEmpty(master_data_download_freq)){
            selectAll.setVisible(false);
        //}

            // set master data download disable where value is -1
       if (Integer.parseInt(master_data_download_freq) == -1) {
            DonloadActionButton.setEnabled(false);
            articleMasterFalg.setEnabled(false);
            conditionTypeMaster.setEnabled(false);
            articleCharacteristicsFlag.setEnabled(false);
            articleUcpFlag.setEnabled(false);
            articleDiscountFlag.setEnabled(false);
            articleTaxFlag.setEnabled(false);
            stockMasterFlag.setEnabled(false);
            siteMasterFlag.setEnabled(false);
            employeeMasterFlag.setEnabled(false);
            otherChargesFlag.setEnabled(false);
            promotionFlag.setEnabled(false);
            gvMasterFlag.setEnabled(false);
            fromDateVal.setEditable(false);
            toDateVal.setEditable(false);
            toDatePicker.setEnabled(false);
            fromDatePicker.setEnabled(false);
        }


        // end : master data download development
        timer = new Timer(interval, new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                i = i + 1;
                jProgressBar1.setValue(i);
                if (thread != null) {
                    if (thread.getState() == Thread.State.TERMINATED) {
                        Toolkit.getDefaultToolkit().beep();
                        timer.stop();
                        if (articleThread.getI() == 0) {
                            String str = "<html>" + "<font color=\"#FF0000\">" + "<b>" +
                                    "DOWNLOAD FAILED." + "</b>" + "</font>" + "</html>";
                            jLabel1.setText(str);
                            jProgressBar1.setValue(50);
                            DonloadActionButton.setEnabled(true);
                            ok.setEnabled(true);
                            ok.requestFocus();
                        } else if (articleThread.getI() == 1) {
                            String str = "<html>" + "<font color=\"#008000\">" + "<b>" +
                                    "SUCCESSFULLY DOWNLOADED." + "</b>" + "</font>" + "</html>";
                            jLabel1.setText(str);
                            jProgressBar1.setValue(50);
                            DonloadActionButton.setEnabled(true);
                            ok.setEnabled(true);
                            ok.requestFocus();
                        } else if (articleThread.getI() == 5) {
                            String str = "<html>" + "<font color=\"#FF0000\">" + "<b>" +
                                    "NO VALUES ARE DOWNLOADED." + "</b>" + "</font>" + "</html>";
                            jLabel1.setText(str);
                            jProgressBar1.setValue(50);
                            DonloadActionButton.setEnabled(true);
                            ok.setEnabled(true);
                            ok.requestFocus();
                        } else if (articleThread.getI() == 2) {
                            String str = "<html>" + "<font color=\"#FF0000\">" + "<b>" +
                                    "Check Your Network for Internet Connection!" + "</b>" + "</font>" + "</html>";
                            jLabel1.setText(str);
                            jProgressBar1.setValue(50);
                            DonloadActionButton.setEnabled(true);
                            ok.setEnabled(true);
                            ok.requestFocus();
                        } else if (articleThread.getI() == 3) {
                            String str = "<html>" + "<font color=\"#FF0000\">" + "<b>" +
                                    "Incorrect Server Address \nContact Administrator to Fix the Problem!" + "</b>" + "</font>" + "</html>";
                            jLabel1.setText(str);
                            jProgressBar1.setValue(50);
                            DonloadActionButton.setEnabled(true);
                            ok.setEnabled(true);
                            ok.requestFocus();
                        } else if (articleThread.getI() == 4) {
                            String str = "<html>" + "<font color=\"#FF0000\">" + "<b>" +
                                    "Unknown Error\nContact Administrator to Fix the Problem!" + "</b>" + "</font>" + "</html>";
                            jLabel1.setText(str);
                            jProgressBar1.setValue(50);
                            DonloadActionButton.setEnabled(true);
                            ok.setEnabled(true);
                            ok.requestFocus();
                        }
                        clearAllFlags();
                    }
                }

            }
        });
        

    }

    public static void addActionToButton(JButton button, final JTabbedPane jTabbedPane1, final ArticleMaster articlemaster) {
        button.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionPerformed(evt);
            }

            private void buttonActionPerformed(ActionEvent evt) {
                boolean x = true;
                for (int i = 0; i < jTabbedPane1.getComponentCount(); i++) {
                    if (jTabbedPane1.getComponentAt(i).getName().equalsIgnoreCase("Article Master")) {
                        jTabbedPane1.setSelectedIndex(i);
                        x = false;
                    }
                }
                if (x) {

                    jTabbedPane1.setSelectedComponent(articlemaster);

                }
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        MasterPane = new javax.swing.JDesktopPane();
        downloadingFrame = new javax.swing.JInternalFrame();
        jDesktopPane2 = new javax.swing.JDesktopPane();
        jProgressBar1 = new JProgressBar(0,50);
        jLabel1 = new javax.swing.JLabel();
        ok = new javax.swing.JButton();
        AuthFrame = new javax.swing.JInternalFrame();
        jDesktopPane9 = new javax.swing.JDesktopPane();
        jLabel3 = new javax.swing.JLabel();
        AuthPassword = new javax.swing.JPasswordField(30);
        auth_OKbutton = new javax.swing.JButton();
        auth_caneclbutton = new javax.swing.JButton();
        articleMasterFalg = new javax.swing.JCheckBox();
        articleCharacteristicsFlag = new javax.swing.JCheckBox();
        articleTaxFlag = new javax.swing.JCheckBox();
        articleDiscountFlag = new javax.swing.JCheckBox();
        articleUcpFlag = new javax.swing.JCheckBox();
        promotionFlag = new javax.swing.JCheckBox();
        otherChargesFlag = new javax.swing.JCheckBox();
        gvMasterFlag = new javax.swing.JCheckBox();
        conditionTypeMaster = new javax.swing.JCheckBox();
        siteMasterFlag = new javax.swing.JCheckBox();
        employeeMasterFlag = new javax.swing.JCheckBox();
        stockMasterFlag = new javax.swing.JCheckBox();
        DonloadActionButton = new javax.swing.JButton();
        articleStatusPane = new javax.swing.JScrollPane();
        articleStatusArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        selectAll = new javax.swing.JCheckBox();
        bordeLabelForTable1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        toDateVal = new javax.swing.JFormattedTextField();
        fromDateVal = new javax.swing.JFormattedTextField();
        toDateButton = new javax.swing.JButton();
        fromDatebutton = new javax.swing.JButton();
        toDatePicker = new ISRetail.components.DatePicker12();
        fromDatePicker = new ISRetail.components.DatePicker12();
        stockMaster1 = new ISRetail.stock.StockMaster();

        setBackground(new java.awt.Color(153, 204, 255));
        setName("Article Master"); // NOI18N
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });

        jDesktopPane1.setBackground(new java.awt.Color(153, 204, 255));
        jDesktopPane1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jDesktopPane1MouseMoved(evt);
            }
        });

        MasterPane.setBackground(new java.awt.Color(153, 204, 255));
        MasterPane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(88, 109, 148), 1, true), "High Frequency Master Download", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 12), new java.awt.Color(0, 0, 0)));

        downloadingFrame.setTitle("Downloading MasterData");

        jProgressBar1.setStringPainted(true);
        jProgressBar1.setBounds(20, 30, 340, 18);
        jDesktopPane2.add(jProgressBar1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("jLabel1");
        jLabel1.setBounds(20, 50, 340, 20);
        jDesktopPane2.add(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        ok.setFont(new java.awt.Font("Verdana", 1, 12));
        ok.setText("OK");
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });
        ok.setBounds(140, 80, 90, 23);
        jDesktopPane2.add(ok, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.jdesktop.layout.GroupLayout downloadingFrameLayout = new org.jdesktop.layout.GroupLayout(downloadingFrame.getContentPane());
        downloadingFrame.getContentPane().setLayout(downloadingFrameLayout);
        downloadingFrameLayout.setHorizontalGroup(
            downloadingFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jDesktopPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
        );
        downloadingFrameLayout.setVerticalGroup(
            downloadingFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jDesktopPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
        );

        downloadingFrame.setBounds(50, 130, 390, 180);
        MasterPane.add(downloadingFrame, javax.swing.JLayeredPane.POPUP_LAYER);

        AuthFrame.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        AuthFrame.setTitle(" Authentication..");
        AuthFrame.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                AuthFrameFocusGained(evt);
            }
        });

        jDesktopPane9.setBackground(new java.awt.Color(153, 204, 255));
        jDesktopPane9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jDesktopPane9FocusGained(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel3.setText("Authentication Code :");
        jLabel3.setBounds(10, 20, 150, 14);
        jDesktopPane9.add(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        AuthPassword.setFont(new java.awt.Font("Verdana", 1, 12));
        AuthPassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        AuthPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                AuthPasswordfocusGainedForTextFields(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                focusLostForTxtField(evt);
            }
        });
        AuthPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                keyReleasedForPwd(evt);
            }
        });
        AuthPassword.setBounds(160, 20, 100, 18);
        jDesktopPane9.add(AuthPassword, javax.swing.JLayeredPane.DEFAULT_LAYER);

        auth_OKbutton.setFont(new java.awt.Font("Verdana", 1, 12));
        auth_OKbutton.setText("OK");
        auth_OKbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                auth_OKbuttonActionPerformed(evt);
            }
        });
        auth_OKbutton.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                auth_OKbuttonFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                auth_OKbuttonFocusLost(evt);
            }
        });
        auth_OKbutton.setBounds(70, 50, 60, 20);
        jDesktopPane9.add(auth_OKbutton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        auth_caneclbutton.setFont(new java.awt.Font("Verdana", 1, 12));
        auth_caneclbutton.setText("Cancel");
        auth_caneclbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                auth_caneclbuttonActionPerformed(evt);
            }
        });
        auth_caneclbutton.setBounds(140, 50, 80, 20);
        jDesktopPane9.add(auth_caneclbutton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.jdesktop.layout.GroupLayout AuthFrameLayout = new org.jdesktop.layout.GroupLayout(AuthFrame.getContentPane());
        AuthFrame.getContentPane().setLayout(AuthFrameLayout);
        AuthFrameLayout.setHorizontalGroup(
            AuthFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jDesktopPane9, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
        );
        AuthFrameLayout.setVerticalGroup(
            AuthFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jDesktopPane9, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
        );

        AuthFrame.setBounds(130, 190, 280, 120);
        MasterPane.add(AuthFrame, javax.swing.JLayeredPane.POPUP_LAYER);

        articleMasterFalg.setFont(new java.awt.Font("Verdana", 1, 12));
        articleMasterFalg.setText("Article Master");
        articleMasterFalg.setOpaque(false);
        articleMasterFalg.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDownloadButtonEnabled(evt);
            }
        });
        articleMasterFalg.setBounds(30, 40, 190, 20);
        MasterPane.add(articleMasterFalg, javax.swing.JLayeredPane.POPUP_LAYER);

        articleCharacteristicsFlag.setFont(new java.awt.Font("Verdana", 1, 12));
        articleCharacteristicsFlag.setText("Article Characteristics");
        articleCharacteristicsFlag.setOpaque(false);
        articleCharacteristicsFlag.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDownloadButtonEnabled(evt);
            }
        });
        articleCharacteristicsFlag.setBounds(30, 70, 210, 20);
        MasterPane.add(articleCharacteristicsFlag, javax.swing.JLayeredPane.POPUP_LAYER);

        articleTaxFlag.setFont(new java.awt.Font("Verdana", 1, 12));
        articleTaxFlag.setText("Taxes");
        articleTaxFlag.setOpaque(false);
        articleTaxFlag.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDownloadButtonEnabled(evt);
            }
        });
        articleTaxFlag.setBounds(30, 130, 210, 20);
        MasterPane.add(articleTaxFlag, javax.swing.JLayeredPane.DEFAULT_LAYER);

        articleDiscountFlag.setFont(new java.awt.Font("Verdana", 1, 12));
        articleDiscountFlag.setText("Discounts");
        articleDiscountFlag.setOpaque(false);
        articleDiscountFlag.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDownloadButtonEnabled(evt);
            }
        });
        articleDiscountFlag.setBounds(30, 160, 200, 20);
        MasterPane.add(articleDiscountFlag, javax.swing.JLayeredPane.DEFAULT_LAYER);

        articleUcpFlag.setFont(new java.awt.Font("Verdana", 1, 12));
        articleUcpFlag.setText("UCP");
        articleUcpFlag.setOpaque(false);
        articleUcpFlag.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDownloadButtonEnabled(evt);
            }
        });
        articleUcpFlag.setBounds(30, 100, 200, 20);
        MasterPane.add(articleUcpFlag, javax.swing.JLayeredPane.DEFAULT_LAYER);

        promotionFlag.setFont(new java.awt.Font("Verdana", 1, 12));
        promotionFlag.setText("Promotions");
        promotionFlag.setOpaque(false);
        promotionFlag.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDownloadButtonEnabled(evt);
            }
        });
        promotionFlag.setBounds(30, 190, 210, 20);
        MasterPane.add(promotionFlag, javax.swing.JLayeredPane.DEFAULT_LAYER);

        otherChargesFlag.setFont(new java.awt.Font("Verdana", 1, 12));
        otherChargesFlag.setText("Other Charges");
        otherChargesFlag.setOpaque(false);
        otherChargesFlag.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDownloadButtonEnabled(evt);
            }
        });
        otherChargesFlag.setBounds(310, 190, 190, 20);
        MasterPane.add(otherChargesFlag, javax.swing.JLayeredPane.DEFAULT_LAYER);

        gvMasterFlag.setFont(new java.awt.Font("Verdana", 1, 12));
        gvMasterFlag.setText("GV Stock Master");
        gvMasterFlag.setOpaque(false);
        gvMasterFlag.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDownloadButtonEnabled(evt);
            }
        });
        gvMasterFlag.setBounds(310, 70, 210, 20);
        MasterPane.add(gvMasterFlag, javax.swing.JLayeredPane.DEFAULT_LAYER);

        conditionTypeMaster.setFont(new java.awt.Font("Verdana", 1, 12));
        conditionTypeMaster.setText("Condition Type Master");
        conditionTypeMaster.setOpaque(false);
        conditionTypeMaster.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDownloadButtonEnabled(evt);
            }
        });
        conditionTypeMaster.setBounds(310, 100, 210, 20);
        MasterPane.add(conditionTypeMaster, javax.swing.JLayeredPane.DEFAULT_LAYER);

        siteMasterFlag.setFont(new java.awt.Font("Verdana", 1, 12));
        siteMasterFlag.setText("Site Master");
        siteMasterFlag.setOpaque(false);
        siteMasterFlag.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDownloadButtonEnabled(evt);
            }
        });
        siteMasterFlag.setBounds(310, 130, 210, 20);
        MasterPane.add(siteMasterFlag, javax.swing.JLayeredPane.DEFAULT_LAYER);

        employeeMasterFlag.setFont(new java.awt.Font("Verdana", 1, 12));
        employeeMasterFlag.setText("Employee Master");
        employeeMasterFlag.setOpaque(false);
        employeeMasterFlag.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDownloadButtonEnabled(evt);
            }
        });
        employeeMasterFlag.setBounds(310, 160, 210, 20);
        MasterPane.add(employeeMasterFlag, javax.swing.JLayeredPane.DEFAULT_LAYER);

        stockMasterFlag.setFont(new java.awt.Font("Verdana", 1, 12));
        stockMasterFlag.setText("Stock Master");
        stockMasterFlag.setOpaque(false);
        stockMasterFlag.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDownloadButtonEnabled(evt);
            }
        });
        stockMasterFlag.setBounds(310, 40, 190, 20);
        MasterPane.add(stockMasterFlag, javax.swing.JLayeredPane.DEFAULT_LAYER);

        DonloadActionButton.setFont(new java.awt.Font("Verdana", 1, 12));
        DonloadActionButton.setText("Download ");
        DonloadActionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DonloadActionButtonActionPerformed(evt);
            }
        });
        DonloadActionButton.setBounds(35, 320, 240, 30);
        MasterPane.add(DonloadActionButton, javax.swing.JLayeredPane.POPUP_LAYER);

        articleStatusArea.setBackground(new java.awt.Color(237, 247, 252));
        articleStatusArea.setColumns(20);
        articleStatusArea.setEditable(false);
        articleStatusArea.setRows(5);
        articleStatusArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        articleStatusArea.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                articleStatusAreaFocusGained(evt);
            }
        });
        articleStatusPane.setViewportView(articleStatusArea);

        articleStatusPane.setBounds(38, 392, 453, 130);
        MasterPane.add(articleStatusPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel2.setBackground(new java.awt.Color(232, 247, 251));
        jLabel2.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel2.setForeground(new java.awt.Color(88, 109, 148));
        jLabel2.setText("Downloading Status");
        jLabel2.setBounds(35, 370, 490, 14);
        MasterPane.add(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        selectAll.setFont(new java.awt.Font("Verdana", 1, 12));
        selectAll.setText("Select All");
        selectAll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        selectAll.setBorderPainted(true);
        selectAll.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        selectAll.setOpaque(false);
        selectAll.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                selectAllItemStateChanged(evt);
            }
        });
        selectAll.setBounds(30, 230, 90, 20);
        MasterPane.add(selectAll, javax.swing.JLayeredPane.DEFAULT_LAYER);

        bordeLabelForTable1.setBackground(new java.awt.Color(237, 247, 252));
        bordeLabelForTable1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        bordeLabelForTable1.setOpaque(true);
        bordeLabelForTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                bordeLabelForTable1KeyReleased(evt);
            }
        });
        bordeLabelForTable1.setBounds(35, 388, 460, 137);
        MasterPane.add(bordeLabelForTable1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel4.setText("To Date");
        jLabel4.setBounds(280, 270, 60, 20);
        MasterPane.add(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel5.setText("From Date");
        jLabel5.setInheritsPopupMenu(false);
        jLabel5.setBounds(30, 270, 70, 20);
        MasterPane.add(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);

        toDateVal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        toDateVal.setText("DD/MM/YYYY");
        toDateVal.setFont(new java.awt.Font("Verdana", 1, 12));
        toDateVal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                commonFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                commonFocusLost(evt);
            }
        });
        toDateVal.setBounds(350, 270, 100, 21);
        MasterPane.add(toDateVal, javax.swing.JLayeredPane.DEFAULT_LAYER);

        fromDateVal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        fromDateVal.setText("DD/MM/YYYY");
        fromDateVal.setFont(new java.awt.Font("Verdana", 1, 12));
        fromDateVal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                commonFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                commonFocusLost(evt);
            }
        });
        fromDateVal.setBounds(100, 270, 100, 20);
        MasterPane.add(fromDateVal, javax.swing.JLayeredPane.DEFAULT_LAYER);

        toDateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ISRetail/images/small/datepicker.jpg"))); // NOI18N
        toDateButton.setBorderPainted(false);
        toDateButton.setContentAreaFilled(false);
        toDateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toDateButtonActionPerformed(evt);
            }
        });
        toDateButton.setBounds(450, 270, 20, 25);
        MasterPane.add(toDateButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        fromDatebutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ISRetail/images/small/datepicker.jpg"))); // NOI18N
        fromDatebutton.setBorderPainted(false);
        fromDatebutton.setContentAreaFilled(false);
        fromDatebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fromDatebuttonActionPerformed(evt);
            }
        });
        fromDatebutton.setBounds(200, 270, 20, 25);
        MasterPane.add(fromDatebutton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        toDatePicker.setDate(toDateVal);
        toDatePicker.setBounds(260, 290, 220, 206);
        MasterPane.add(toDatePicker, javax.swing.JLayeredPane.DRAG_LAYER);
        toDatePicker.setVisible(false);

        fromDatePicker.setDate(fromDateVal);
        fromDatePicker.setBounds(10, 290, 220, 206);
        MasterPane.add(fromDatePicker, javax.swing.JLayeredPane.DRAG_LAYER);
        fromDatePicker.setVisible(false);

        MasterPane.setBounds(10, 0, 530, 577);
        jDesktopPane1.add(MasterPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        stockMaster1.setBounds(530, 0, 560, 590);
        jDesktopPane1.add(stockMaster1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jDesktopPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1163, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(231, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jDesktopPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 642, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    private void DonloadActionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DonloadActionButtonActionPerformed
        // TODO add your handling code here:
        // added to change to date as current date
        if (Validations.isFieldNotEmpty(fromDateVal.getText()) && !fromDateVal.getText().equalsIgnoreCase("DD/MM/YYYY")) {
            if (Validations.isFieldNotEmpty(toDateVal.getText()) && !toDateVal.getText().equalsIgnoreCase("DD/MM/YYYY")) {
            } else {
                toDateVal.setText(fromDateVal.getText());
            }
        }
        AuthFrame.setVisible(true);
        DonloadActionButton.setEnabled(false);
        AuthPassword.requestFocus();
          
}//GEN-LAST:event_DonloadActionButtonActionPerformed

    public void clearAllFlags() {
        DonloadActionButton.setEnabled(false);
        articleMasterFalg.setSelected(false);
        conditionTypeMaster.setSelected(false);
        articleCharacteristicsFlag.setSelected(false);
        articleUcpFlag.setSelected(false);
        articleDiscountFlag.setSelected(false);
        articleTaxFlag.setSelected(false);
        stockMasterFlag.setSelected(false);
        siteMasterFlag.setSelected(false);
        employeeMasterFlag.setSelected(false);
        otherChargesFlag.setSelected(false);
        promotionFlag.setSelected(false);
        gvMasterFlag.setSelected(false);
    }

    public void clrRemainingFlags(java.awt.event.ItemEvent evt){

        if (Validations.isFieldNotEmpty(master_data_download_freq)) {

            if (!evt.getSource().equals(DonloadActionButton)) {
                DonloadActionButton.setSelected(false);
            }

            if (!evt.getSource().equals(articleMasterFalg)) {
                articleMasterFalg.setSelected(false);
            }

            if (!evt.getSource().equals(conditionTypeMaster)) {
                conditionTypeMaster.setSelected(false);
            }

            if (!evt.getSource().equals(articleCharacteristicsFlag)) {
                articleCharacteristicsFlag.setSelected(false);
            }

            if (!evt.getSource().equals(articleUcpFlag)) {
                articleUcpFlag.setSelected(false);
            }

            if (!evt.getSource().equals(articleDiscountFlag)) {
                articleDiscountFlag.setSelected(false);
            }

            if (!evt.getSource().equals(articleTaxFlag)) {
                articleTaxFlag.setSelected(false);
            }

            if (!evt.getSource().equals(stockMasterFlag)) {
                stockMasterFlag.setSelected(false);
            }

            if (!evt.getSource().equals(siteMasterFlag)) {
                siteMasterFlag.setSelected(false);
            }

            if (!evt.getSource().equals(employeeMasterFlag)) {
                employeeMasterFlag.setSelected(false);
            }

            if (!evt.getSource().equals(otherChargesFlag)) {
                otherChargesFlag.setSelected(false);
            }

            if (!evt.getSource().equals(promotionFlag)) {
                promotionFlag.setSelected(false);
            }

            if (!evt.getSource().equals(gvMasterFlag)) {
                gvMasterFlag.setSelected(false);
            }
        }
    }

    public void setSelection(boolean flag) {
        DonloadActionButton.setEnabled(flag);
        articleMasterFalg.setSelected(flag);
        conditionTypeMaster.setSelected(flag);
        articleCharacteristicsFlag.setSelected(flag);
        articleUcpFlag.setSelected(flag);
        articleDiscountFlag.setSelected(flag);
        articleTaxFlag.setSelected(flag);
        stockMasterFlag.setSelected(flag);
        siteMasterFlag.setSelected(flag);
        employeeMasterFlag.setSelected(flag);
        otherChargesFlag.setSelected(flag);
        promotionFlag.setSelected(flag);
        gvMasterFlag.setSelected(flag);
    }

    public void downloadData() {
        articleStatusArea.setText("");
        if (articleMasterFalg.isSelected() || conditionTypeMaster.isSelected() || articleCharacteristicsFlag.isSelected() || articleUcpFlag.isSelected() || articleDiscountFlag.isSelected() || articleTaxFlag.isSelected() || stockMasterFlag.isSelected() || siteMasterFlag.isSelected() || employeeMasterFlag.isSelected() || otherChargesFlag.isSelected() || promotionFlag.isSelected() || gvMasterFlag.isSelected()) {
            try {
                MsdeConnection msdeConnection = new MsdeConnection();
                Connection connection = msdeConnection.createConnection();

                if (connection != null) {
                    String str = "<html>" + "<font color=\"#008000\">" + "<b>" +
                            "Downloading is in process......." + "</b>" + "</font>" + "</html>";
                    jLabel1.setText(str);
                    downloadingFrame.setVisible(true);
                    DonloadActionButton.setEnabled(false);
                    ok.setEnabled(false);
                    jProgressBar1.setValue(0);
                    timer.start();
                    ArticleWebService articleWebService = new ArticleWebService(articleStatusArea);
                    if (articleMasterFalg.isSelected()) {
                        articleWebService.setArticleFlag("X");
                    } else {
                        articleWebService.setArticleFlag("");
                    }
                    if (articleCharacteristicsFlag.isSelected()) {
                        articleWebService.setArticleCharacteristicsFlag("X");
                    } else {
                        articleWebService.setArticleCharacteristicsFlag("");
                    }
                    if (articleUcpFlag.isSelected()) {
                        articleWebService.setArticleUcpFlag("X");
                    } else {
                        articleWebService.setArticleUcpFlag("");
                    }
                    if (articleDiscountFlag.isSelected()) {
                        articleWebService.setArticleDiscountFlag("X");
                    } else {
                        articleWebService.setArticleDiscountFlag("");
                    }
                    if (articleTaxFlag.isSelected()) {
                        articleWebService.setArticleTaxFlag("X");
                    } else {
                        articleWebService.setArticleTaxFlag("");
                    }
                    if (stockMasterFlag.isSelected()) {
                        articleWebService.setStockMasterFlag("X");
                    } else {
                        articleWebService.setStockMasterFlag("");
                    }
                    if (siteMasterFlag.isSelected()) {
                        articleWebService.setSiteMasterFlag("X");
                    } else {
                        articleWebService.setSiteMasterFlag("");
                    }
                    if (employeeMasterFlag.isSelected()) {
                        articleWebService.setEmployeeMasterFlag("X");
                    } else {
                        articleWebService.setEmployeeMasterFlag("");
                    }
                    if (otherChargesFlag.isSelected()) {
                        articleWebService.setOtherChargesFlag("X");
                    } else {
                        articleWebService.setOtherChargesFlag("");
                    }
                    if (promotionFlag.isSelected()) {
                        articleWebService.setPromotionsFlag("X");
                    } else {
                        articleWebService.setPromotionsFlag("");
                    }

                    if (gvMasterFlag.isSelected()) {
                        articleWebService.setGvMasterFlag("X");
                    } else {
                        articleWebService.setGvMasterFlag("");
                    }
                    if (conditionTypeMaster.isSelected()) {
                        articleWebService.setConditionTypeMasterFlag("X");
                    } else {
                        articleWebService.setConditionTypeMasterFlag("");
                    }

                    if (Validations.isFieldNotEmpty(fromDateVal.getText()) && !fromDateVal.getText().equalsIgnoreCase("DD/MM/YYYY")) {
                        articleWebService.setFromDate(fromDateVal.getText());
                        if (Validations.isFieldNotEmpty(toDateVal.getText()) && !toDateVal.getText().equalsIgnoreCase("DD/MM/YYYY")) {
                            articleWebService.setToDate(toDateVal.getText());
                        } else {
                            if(Validations.isFieldNotEmpty(master_data_download_freq)){
                                articleWebService.setToDate(fromDateVal.getText());
                            } else {
                                articleWebService.setToDate(ServerConsole.businessDate);
                            }
                            
                        }
                    }

                    articleThread = new ArticleThread(articleWebService, articleStatusArea);
                    thread = new Thread(articleThread);
                    thread.setDaemon(true);
                    thread.start();



                } else {
                    JOptionPane.showMessageDialog(this, "Unable to Connect to POS Database!", "Error Message", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Download Failed.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please Select atleast one option to download !", "Error Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
        // TODO add your handling code here:
        downloadingFrame.setVisible(false);
}//GEN-LAST:event_okActionPerformed

    private void AuthPasswordfocusGainedForTextFields(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AuthPasswordfocusGainedForTextFields
        // TODO add your handling code here:
        try {
            if (evt != null) {
                if (!evt.isTemporary() && evt.getComponent() != null) {
                    JTextField tf;
                    try {
                        tf = (JTextField) evt.getComponent();
                        if (tf.isEditable()) {
                            tf.setBackground(selectedColor);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        tf = null;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
}//GEN-LAST:event_AuthPasswordfocusGainedForTextFields
    }
        private void focusLostForTxtField(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_focusLostForTxtField
            // TODO add your handling code here:
            JTextField tf;
            try {
                if (evt != null) {
                    if (!evt.isTemporary() && evt.getComponent() != null) {
                        try {
                            tf = (JTextField) evt.getComponent();
                            tf.setBackground(originalColor);
                        } catch (Exception e) {
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                tf = null;
            }
        }//GEN-LAST:event_focusLostForTxtField

        private void keyReleasedForPwd(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyReleasedForPwd
            // TODO add your handling code here:
            if (evt != null) {
                if (evt.getSource().equals(AuthPassword)) {
                    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                        auth_OKbuttonActionPerformed(null);
                    }
                }
            }
        }//GEN-LAST:event_keyReleasedForPwd

        private void auth_OKbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_auth_OKbuttonActionPerformed
            // TODO add your handling code here:
            MsdeConnection msdeconn;
            Connection con;
            SiteMasterDO smdo;
            String passwdvaliditydate = "", transacpwd, passwd;
            try {
                if (AuthPassword.getPassword().length != 0) {
                    authentication_attempts++;
                    if (authentication_attempts <= 3) {
                        msdeconn = new MsdeConnection();
                        con = msdeconn.createConnection();
                        smdo = new SiteMasterDO();
                        int passwordvaliditydate = 0;
                        passwordvaliditydate = smdo.getForceReleaseTransactionPwdvalidity(con);
                        if (passwordvaliditydate != 0) {
                            passwdvaliditydate = String.valueOf(ConvertDate.getNumericToDate(passwordvaliditydate));
                            if (Validations.isToDateAfterFromDate(ServerConsole.businessDate, passwdvaliditydate)) {
                                transacpwd = smdo.getForceReleaseTransactionPwd(con);
                                passwd = new String(AuthPassword.getPassword());
                                if (passwd.equals(transacpwd)) {
                                    authentication_attempts = 0;
                                    AuthPassword.setText("");
                                    AuthFrame.setVisible(false);
                                    DonloadActionButton.setEnabled(true);
                                    downloadData();
                                } else {
                                    if (authentication_attempts <= 2) {
                                        JOptionPane.showMessageDialog(this, "Authentication Failed " + authentication_attempts + "Times", "Error", JOptionPane.ERROR_MESSAGE);
                                        AuthPassword.setText("");
                                        AuthPassword.requestFocus();
                                    }
                                    if (authentication_attempts == 3) {
                                        JOptionPane.showMessageDialog(this, "Maximum attempts has been Crossed !! ", "Error", JOptionPane.ERROR_MESSAGE);
                                        AuthPassword.setText("");
                                        AuthFrame.setVisible(false);
                                        DonloadActionButton.setEnabled(true);
                                        authentication_attempts = 0;
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "Password has expired", "Error", JOptionPane.ERROR_MESSAGE);
                                AuthPassword.setText("");
                                AuthFrame.setVisible(false);
                                DonloadActionButton.setEnabled(true);
                                authentication_attempts = 0;
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter the authentication code", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                msdeconn = null;
                con = null;
                smdo = null;
                passwdvaliditydate = null;
                transacpwd = null;
                passwd = null;
            }
        }//GEN-LAST:event_auth_OKbuttonActionPerformed

        private void auth_OKbuttonFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_auth_OKbuttonFocusGained
            // TODO add your handling code here:
            JButton buttonInstanceTosetBorder;
            try {
                if (evt != null) {
                    if (!evt.isTemporary() && evt.getComponent() != null) {
                        buttonInstanceTosetBorder = (JButton) evt.getComponent();
                        buttonInstanceTosetBorder.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(88, 109, 148), new java.awt.Color(88, 109, 148)));
                        buttonInstanceTosetBorder.setBorderPainted(true);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                buttonInstanceTosetBorder = null;
            }
        }//GEN-LAST:event_auth_OKbuttonFocusGained

        private void auth_OKbuttonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_auth_OKbuttonFocusLost
            // TODO add your handling code here:
            JButton btn;
            try {
                if (evt != null) {
                    if (!evt.isTemporary() && evt.getComponent() != null) {
                        btn = (JButton) evt.getComponent();
                        btn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(88, 109, 148), 1, true));
                        btn.setBorderPainted(true);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                btn = null;
            }
        }//GEN-LAST:event_auth_OKbuttonFocusLost

        private void auth_caneclbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_auth_caneclbuttonActionPerformed
            // TODO add your handling code here:
            try {
                int res = JOptionPane.showConfirmDialog(this, "Do you Want to Cancel??", "Confirmation?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (res == JOptionPane.YES_OPTION) {
                    AuthFrame.setVisible(false);
                    DonloadActionButton.setEnabled(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }//GEN-LAST:event_auth_caneclbuttonActionPerformed

        private void jDesktopPane9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jDesktopPane9FocusGained
            // TODO add your handling code here:
            AuthPassword.requestFocus();
        }//GEN-LAST:event_jDesktopPane9FocusGained

        private void AuthFrameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AuthFrameFocusGained
            // TODO add your handling code here:
            AuthPassword.requestFocus();

        }//GEN-LAST:event_AuthFrameFocusGained

        private void setDownloadButtonEnabled(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_setDownloadButtonEnabled
            // TODO add your handling code here:
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                clrRemainingFlags(evt);
                DonloadActionButton.setEnabled(true);
            } else if (articleMasterFalg.isSelected() || conditionTypeMaster.isSelected() || articleCharacteristicsFlag.isSelected() || articleUcpFlag.isSelected() || articleDiscountFlag.isSelected() || articleTaxFlag.isSelected() || stockMasterFlag.isSelected() || siteMasterFlag.isSelected() || employeeMasterFlag.isSelected() || otherChargesFlag.isSelected() || promotionFlag.isSelected() || gvMasterFlag.isSelected()) {
                DonloadActionButton.setEnabled(true);
            } else {
                DonloadActionButton.setEnabled(false);
            }
        }//GEN-LAST:event_setDownloadButtonEnabled

        private void articleStatusAreaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_articleStatusAreaFocusGained
            // TODO add your handling code here:
}//GEN-LAST:event_articleStatusAreaFocusGained

        private void selectAllItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_selectAllItemStateChanged
            // TODO add your handling code here:
            if (selectAll.isSelected()) {
                setSelection(true);
            } else {
                setSelection(false);
            }
        }//GEN-LAST:event_selectAllItemStateChanged

        private void bordeLabelForTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bordeLabelForTable1KeyReleased
            // TODO add your handling code here:
        }//GEN-LAST:event_bordeLabelForTable1KeyReleased

        private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
            // TODO add your handling code here:


            evt.setSource(evt.getComponent().getParent());

        }//GEN-LAST:event_formMouseMoved

        private void jDesktopPane1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDesktopPane1MouseMoved
            // TODO add your handling code here:


            evt.setSource(serverConsole.MainTabbedPane);
            serverConsole.myMainTabbedPaneMouseMoved(evt);
        }//GEN-LAST:event_jDesktopPane1MouseMoved

        private void fromDatebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fromDatebuttonActionPerformed
            // TODO add your handling code here:
            fromDatePicker.setVisible(true);
        }//GEN-LAST:event_fromDatebuttonActionPerformed

        private void toDateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toDateButtonActionPerformed
            // TODO add your handling code here:
            toDatePicker.setVisible(true);
        }//GEN-LAST:event_toDateButtonActionPerformed

        private void commonFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_commonFocusGained
            // TODO add your handling code here:

            fromDatePicker.setVisible(false);
            toDatePicker.setVisible(false);
            if (evt != null) {
                //Changing Color on focus lost
                if (evt.getComponent() != null) {
                    JTextField tempfield = null;
                    if (evt.getComponent() instanceof JTextField) {
                        tempfield = (JTextField) evt.getComponent();
                    }
                    if (tempfield != null) {
                        if (tempfield.isEditable()) {
                            evt.getComponent().setBackground(selectedColor);
                        }
                    } else {
                        JButton btn;
                        JRadioButton rBtn;
                        JCheckBox cBtn;
                        try {
                            if (evt.getComponent() instanceof JButton) {
                                btn = (JButton) evt.getComponent();
                                btn.setBorderPainted(false);
                            } else if (evt.getComponent() instanceof JRadioButton) {
                                rBtn = (JRadioButton) evt.getComponent();
                                rBtn.setBorderPainted(false);
                            } else if (evt.getComponent() instanceof JCheckBox) {
                                cBtn = (JCheckBox) evt.getComponent();
                                cBtn.setBorderPainted(false);
                            } else {
                                evt.getComponent().setBackground(originalColor);
                            }
                        } catch (Exception e) {
                        } finally {
                            btn = null;
                            rBtn = null;
                            cBtn = null;
                        }

                    }


                }
                if (evt.getSource() != null) {
                    if (evt.getSource().equals(fromDateVal)) {
                        if (fromDateVal.getText().equals("DD/MM/YYYY")) {
                            fromDateVal.setText("");
                        }
                    } else if (evt.getSource().equals(toDateVal)) {
                        if (toDateVal.getText().equals("DD/MM/YYYY")) {
                            toDateVal.setText("");
                        }
                    }
                }
            }
        }//GEN-LAST:event_commonFocusGained

        private void commonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_commonFocusLost
            // TODO add your handling code here:
            if (evt != null) {
                //Changing Color on focus lost
                if (evt.getComponent() != null) {
                    JTextField tempfield = null;
                    if (evt.getComponent() instanceof JTextField) {
                        tempfield = (JTextField) evt.getComponent();
                    }
                    if (tempfield != null) {
                        if (tempfield.isEditable()) {
                            evt.getComponent().setBackground(originalColor);
                        }
                    } else {
                        JButton btn;
                        JRadioButton rBtn;
                        JCheckBox cBtn;
                        try {
                            if (evt.getComponent() instanceof JButton) {
                                btn = (JButton) evt.getComponent();
                                btn.setBorderPainted(false);
                            } else if (evt.getComponent() instanceof JRadioButton) {
                                rBtn = (JRadioButton) evt.getComponent();
                                rBtn.setBorderPainted(false);
                            } else if (evt.getComponent() instanceof JCheckBox) {
                                cBtn = (JCheckBox) evt.getComponent();
                                cBtn.setBorderPainted(false);
                            } else {
                                evt.getComponent().setBackground(originalColor);
                            }
                        } catch (Exception e) {
                        } finally {
                            btn = null;
                            rBtn = null;
                            cBtn = null;
                        }

                    }
                }
                String tempVal = null;
                if (evt.getSource() != null) {



                    if (evt.getSource().equals(fromDateVal)) {
                        if ((!Validations.isFieldNotEmpty(fromDateVal.getText()) || fromDateVal.getText().equalsIgnoreCase("DD/MM/YYYY"))) {
                            fromDateVal.setText("DD/MM/YYYY");
                        } else {
                            tempVal = Validations.validateAndSetDate(fromDateVal.getText(), this);
                            if (tempVal != null) {
                                fromDateVal.setText(tempVal);
                                if (!Validations.isToDateAfterFromDate(tempVal, ServerConsole.businessDate)) {
                                    fromDateVal.setText("");
                                    fromDateVal.requestFocus();
                                    JOptionPane.showMessageDialog(this, "Invalid date!", "Error Message", JOptionPane.ERROR_MESSAGE);
                                    return;
                                } else if (!Validations.isToDateAfterFromDate(fromDateVal.getText(), toDateVal.getText())) {
                                    toDateVal.setText("");
                                    toDateVal.requestFocus();
                                    JOptionPane.showMessageDialog(this, "To date should not be less than from date!", "Error Message", JOptionPane.ERROR_MESSAGE);
                                    return;
                                } 
                            } else {
                                fromDateVal.requestFocus();
                                fromDateVal.setText("");
                                //JOptionPane.showMessageDialog(this, "Invalid  date format.\nEnter in 'DD/MM/YYYY' or 'DDMMYYYY'!", "Error Message", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            if(!Validations.isFieldNotEmpty(toDateVal.getText()) || toDateVal.getText().equalsIgnoreCase("DD/MM/YYYY")) {
                                toDateVal.setText(fromDateVal.getText());
                            }
                            
                        }
                    } else if (evt.getSource().equals(toDateVal)) {
                        if ((!Validations.isFieldNotEmpty(toDateVal.getText()) || toDateVal.getText().equalsIgnoreCase("DD/MM/YYYY"))) {
                            toDateVal.setText("DD/MM/YYYY");
                        } else {
                            tempVal = Validations.validateAndSetDate(toDateVal.getText(), this);
                            if (tempVal != null) {
                                toDateVal.setText(tempVal);
                                if (!Validations.isToDateAfterFromDate(tempVal, ServerConsole.businessDate)) {
                                    toDateVal.setText("");
                                    toDateVal.requestFocus();
                                    JOptionPane.showMessageDialog(this, "Invalid date!", "Error Message", JOptionPane.ERROR_MESSAGE);
                                    return;
                                } else if ((Validations.isFieldNotEmpty(fromDateVal.getText()) && !fromDateVal.getText().equalsIgnoreCase("DD/MM/YYYY")) && !Validations.isToDateAfterFromDate(fromDateVal.getText(), toDateVal.getText())) {
                                    fromDateVal.setText("");
                                    fromDateVal.requestFocus();
                                    JOptionPane.showMessageDialog(this, "From date should not be more than To date!", "Error Message", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            } else {
                                toDateVal.requestFocus();
                                toDateVal.setText("");
                                //JOptionPane.showMessageDialog(this, "Invalid date format.\nEnter in 'DD/MM/YYYY' or 'DDMMYYYY'!", "Error Message", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            if (!Validations.isFieldNotEmpty(fromDateVal.getText()) || fromDateVal.getText().equalsIgnoreCase("DD/MM/YYYY")) {
                                fromDateVal.setText(toDateVal.getText());
                            }
                        }
                    }

                    if (Validations.isFieldNotEmpty(master_data_download_freq)) {
                        if (evt.getSource().equals(toDateVal) || evt.getSource().equals(fromDateVal)) {
                            if (Validations.isFieldNotEmpty(toDateVal.getText()) && !toDateVal.getText().equalsIgnoreCase("DD/MM/YYYY") && Validations.isFieldNotEmpty(fromDateVal.getText()) && !fromDateVal.getText().equalsIgnoreCase("DD/MM/YYYY")) {
                                long daysdiff = Validations.getDiffbtweendates(fromDateVal.getText(), toDateVal.getText());
                                if (daysdiff >= Integer.parseInt(master_data_download_freq)) {
                                    if (evt.getSource().equals(toDateVal)) {
                                        toDateVal.setText("");
                                        toDateVal.requestFocus();
                                    }
                                    if (evt.getSource().equals(fromDateVal)) {
                                        fromDateVal.setText("");
                                        fromDateVal.requestFocus();
                                    }
                                    JOptionPane.showMessageDialog(this, "Master data download allowed only for " + Integer.parseInt(master_data_download_freq) + " days ! ", "Error Message", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            }
                        }
                    }

                }

            }
        }//GEN-LAST:event_commonFocusLost
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JInternalFrame AuthFrame;
    private javax.swing.JPasswordField AuthPassword;
    private javax.swing.JButton DonloadActionButton;
    private javax.swing.JDesktopPane MasterPane;
    private javax.swing.JCheckBox articleCharacteristicsFlag;
    private javax.swing.JCheckBox articleDiscountFlag;
    private javax.swing.JCheckBox articleMasterFalg;
    private javax.swing.JTextArea articleStatusArea;
    private javax.swing.JScrollPane articleStatusPane;
    private javax.swing.JCheckBox articleTaxFlag;
    private javax.swing.JCheckBox articleUcpFlag;
    private javax.swing.JButton auth_OKbutton;
    private javax.swing.JButton auth_caneclbutton;
    private javax.swing.JLabel bordeLabelForTable1;
    private javax.swing.JCheckBox conditionTypeMaster;
    private javax.swing.JInternalFrame downloadingFrame;
    private javax.swing.JCheckBox employeeMasterFlag;
    private ISRetail.components.DatePicker12 fromDatePicker;
    private javax.swing.JFormattedTextField fromDateVal;
    private javax.swing.JButton fromDatebutton;
    private javax.swing.JCheckBox gvMasterFlag;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JDesktopPane jDesktopPane2;
    private javax.swing.JDesktopPane jDesktopPane9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JButton ok;
    private javax.swing.JCheckBox otherChargesFlag;
    private javax.swing.JCheckBox promotionFlag;
    private javax.swing.JCheckBox selectAll;
    private javax.swing.JCheckBox siteMasterFlag;
    private ISRetail.stock.StockMaster stockMaster1;
    private javax.swing.JCheckBox stockMasterFlag;
    private javax.swing.JButton toDateButton;
    private ISRetail.components.DatePicker12 toDatePicker;
    private javax.swing.JFormattedTextField toDateVal;
    // End of variables declaration//GEN-END:variables
}
