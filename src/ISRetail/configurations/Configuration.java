/*
 * Configuration.java
 *
 * Created on June 27, 2008, 11:55 AM
 */
package ISRetail.configurations;

import ISRetail.Helpers.ConvertDate;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.serverconsole.ServerConsole;
import ISRetail.utility.validations.Validations;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 *
 * @author Administrator
 */
public class Configuration extends javax.swing.JPanel {

    ServerConsole serverConsole;
    Timer timer;
    final static int interval = 2000;
    int i = 0;
    int result = 0, authentication_attempts = 0;
    ;
    Thread thread = null;
    ConfigurationThread configurationThread = null;
    JTextArea statusArea;
    private final Color selectedColor = new Color(255, 255, 150);//Color of Textfields when focu gained
    private final Color originalColor = new Color(255, 255, 255);//Color of Textfields when focu lost

    /**
     * Creates new form Configuration
     */
    public Configuration(ServerConsole serverConsole) {
        this.serverConsole = serverConsole;
        initComponents();
        statusArea = new JTextArea();
        AuthFrame.setVisible(false);
        authentication_attempts = 0;
        timer = new Timer(interval, new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                i = i + 1;
                jProgressBar1.setValue(i);
                if (thread != null) {
                    if (thread.getState() == Thread.State.TERMINATED) {
                        Toolkit.getDefaultToolkit().beep();
                        timer.stop();
                        if (configurationThread.getI() == 0) {
                            String str = "<html>" + "<font color=\"#FF0000\">" + "<b>"
                                    + "DOWNLOAD FAILED." + "</b>" + "</font>" + "</html>";
                            jLabel1.setText(str);
                            jProgressBar1.setValue(50);
                            DonloadActionButton.setEnabled(true);
                            ok.setEnabled(true);
                            ok.requestFocus();
                            clearAll();
                        } else if (configurationThread.getI() == 1) {
                            String str = "<html>" + "<font color=\"#008000\">" + "<b>"
                                    + "SUCCESSFULLY DOWNLOADED." + "</b>" + "</font>" + "</html>";
                            jLabel1.setText(str);
                            jProgressBar1.setValue(50);
                            DonloadActionButton.setEnabled(true);
                            ok.setEnabled(true);
                            ok.requestFocus();
                            clearAll();
                        } else if (configurationThread.getI() == 5) {
                            String str = "<html>" + "<font color=\"#FF0000\">" + "<b>"
                                    + "NO VALUES ARE DOWNLOADED." + "</b>" + "</font>" + "</html>";
                            jLabel1.setText(str);
                            jProgressBar1.setValue(50);
                            DonloadActionButton.setEnabled(true);
                            ok.setEnabled(true);
                            ok.requestFocus();
                            clearAll();
                        } else if (configurationThread.getI() == 2) {
                            String str = "<html>" + "<font color=\"#FF0000\">" + "<b>"
                                    + "Check Your Network for Internet Connection!" + "</b>" + "</font>" + "</html>";
                            jLabel1.setText(str);
                            jProgressBar1.setValue(50);
                            DonloadActionButton.setEnabled(true);
                            ok.setEnabled(true);
                            ok.requestFocus();
                            clearAll();
                        } else if (configurationThread.getI() == 3) {
                            String str = "<html>" + "<font color=\"#FF0000\">" + "<b>"
                                    + "Incorrect Server Address \nContact Administrator to Fix the Problem!" + "</b>" + "</font>" + "</html>";
                            jLabel1.setText(str);
                            jProgressBar1.setValue(50);
                            DonloadActionButton.setEnabled(true);
                            ok.setEnabled(true);
                            ok.requestFocus();
                            clearAll();
                        } else if (configurationThread.getI() == 4) {
                            String str = "<html>" + "<font color=\"#FF0000\">" + "<b>"
                                    + "Unknown Error\nContact Administrator to Fix the Problem!" + "</b>" + "</font>" + "</html>";
                            jLabel1.setText(str);
                            jProgressBar1.setValue(50);
                            DonloadActionButton.setEnabled(true);
                            ok.setEnabled(true);
                            ok.requestFocus();
                            clearAll();
                        }
                    }
                }

            }
        });

    }

    public static void addActionToButton(JButton button, final JTabbedPane jTabbedPane1, final Configuration configuration) {
        button.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionPerformed(evt);
            }

            private void buttonActionPerformed(ActionEvent evt) {
                boolean x = true;
                for (int i = 0; i < jTabbedPane1.getComponentCount(); i++) {
                    if (jTabbedPane1.getComponentAt(i).getName().equalsIgnoreCase("Configuration")) {
                        jTabbedPane1.setSelectedIndex(i);
                        x = false;
                    }
                }
                if (x) {
                    jTabbedPane1.setSelectedComponent(configuration);

                }
            }
        });
    }

    public ConfigurationDetails getObjectFromScreen() {
        ConfigurationDetails details = new ConfigurationDetails();
        if (citymaster.isSelected()) {
            details.setIV_FLAG_CITY(true);
        }
        if (titlemaster.isSelected()) {
            details.setIV_FLAG_TITLE(true);
        }
        if (relationshipmaster.isSelected()) {
            details.setIV_FLAG_RELATIONSHIP(true);
        }
        if (statemaster.isSelected()) {
            details.setIV_FLAG_STATE(true);
        }
        if (countrymaster.isSelected()) {
            details.setIV_FLAG_COUNTRY(true);
        }
        if (designationmaster.isSelected()) {
            details.setIV_FLAG_DESIGNATION(true);
        }
        if (educationmaster.isSelected()) {
            details.setIV_FLAG_EDUCATION(true);
        }
        if (occupationmaster.isSelected()) {
            details.setIV_FLAG_OCCUPATION(true);
        }
        if (maritalstatusmaster.isSelected()) {
            details.setIV_FLAG_MARTIALSTATUS(true);
        }
        if (customercategorymaster.isSelected()) {
            details.setIV_FLAG_CUSTOMERCATEGORY(true);
        }
        if (preferredcommaster.isSelected()) {
            details.setIV_FLAG_UPDATIONMODE(true);
        }
        if (returnreason.isSelected()) {
            details.setIV_FLAG_RETREASON(true);
        }
        if (paymentMode.isSelected()) {
            details.setIV_FLAG_PAYMENTMODE(true);
        }
        if (cardtypemaster.isSelected()) {
            details.setIV_FLAG_CARDTYPES(true);
        }
        if (currencyTypes.isSelected()) {
            details.setIV_FLAG_CURRENCYTYPE(true);
        }
        if (followUpPeriod.isSelected()) {
            details.setIV_FLAG_FOLLOWUP(true);
        }

        if (com_priority.isSelected()) {
            details.setIV_FLAG_COMMPRIORITY(true);
        }
        if (advance_CanReasons.isSelected()) {
            details.setIV_FLAG_ADVCANRES(true);
        }
//        if (presCharValues.isSelected()) {
//            details.setIV_FLAG_CHARVALUES(true);
//        }
        if (deliveryMode.isSelected()) {
            details.setIV_FLAG_MASTERS(true);
        }
        if (cancelReasons.isSelected()) {
            details.setIV_FLAG_CANCELREASON(true);
        }
        if (defectReasons.isSelected()) {
            details.setIV_FLAG_DEFECTREASON(true);
        }

        if (invoiceCancelReasons.isSelected()) {
            details.setIV_FLAG_BILLCANCREASON(true);
        }
        if (articleParams.isSelected()) {
            details.setIV_FLAG_POSPARAM(true);
        }
        if (siteListMaster.isSelected()) {
            details.setIV_FLAG_SITES(true);
        }
        if (sunglass_attribures.isSelected()) {
            details.setIV_FLAG_SGL_ATTRIBUTES(true);
        }
        if (deliveryMode.isSelected()) {
            details.setIV_FLAG_MASTERS(true);
        }
        if (doctorDetails.isSelected()) {
            details.setIV_FLAG_DOCTOR_NAME(true);
        }
        if (deliverytat.isSelected()) {
            details.setIV_FLAG_DELIV_MERCH(true);
            details.setIV_FLAG_DELIV_ARTICLE(true);
        }
        if (cl_attributes.isSelected()) {
            details.setIV_FLAG_CONTACT_LENS_PRODUCT_ATTRIBUTES(true);
            details.setIV_FLAG_CONTACT_LENS_PACK_ATTRIBUTES(true);
            details.setIV_FLAG_CONTACT_LENS_BASE_CURVE(true);
            details.setIV_FLAG_CONTACT_LENS_TYPES(true);
        }
        if (cl_brands.isSelected()) {
            details.setIV_FLAG_BRANDTINT(true);
        }
        return details;
    }

    public void clearAll() {
        try {
            setSelection(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSelection(boolean flag) {
        citymaster.setSelected(flag);
        titlemaster.setSelected(flag);
        relationshipmaster.setSelected(flag);
        statemaster.setSelected(flag);
        countrymaster.setSelected(flag);
        designationmaster.setSelected(flag);
        educationmaster.setSelected(flag);
        occupationmaster.setSelected(flag);
        maritalstatusmaster.setSelected(flag);
        customercategorymaster.setSelected(flag);
        preferredcommaster.setSelected(flag);
        returnreason.setSelected(flag);
        cardtypemaster.setSelected(flag);
        paymentMode.setSelected(flag);
        currencyTypes.setSelected(flag);
        followUpPeriod.setSelected(flag);
        presCharValues.setSelected(flag);
        deliveryMode.setSelected(flag);
        com_priority.setSelected(flag);
        advance_CanReasons.setSelected(flag);
        defectReasons.setSelected(flag);
        cancelReasons.setSelected(flag);
        invoiceCancelReasons.setSelected(flag);
        articleParams.setSelected(flag);
        siteListMaster.setSelected(flag);
        sunglass_attribures.setSelected(flag);
        doctorDetails.setSelected(flag);
        deliverytat.setSelected(flag);
        cl_attributes.setSelected(flag);
        cl_brands.setSelected(flag);
        selectAll.setSelected(flag);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        DonloadActionButton = new javax.swing.JButton();
        preferredcommaster = new javax.swing.JCheckBox();
        titlemaster = new javax.swing.JCheckBox();
        relationshipmaster = new javax.swing.JCheckBox();
        citymaster = new javax.swing.JCheckBox();
        statemaster = new javax.swing.JCheckBox();
        designationmaster = new javax.swing.JCheckBox();
        educationmaster = new javax.swing.JCheckBox();
        occupationmaster = new javax.swing.JCheckBox();
        maritalstatusmaster = new javax.swing.JCheckBox();
        customercategorymaster = new javax.swing.JCheckBox();
        countrymaster = new javax.swing.JCheckBox();
        cardtypemaster = new javax.swing.JCheckBox();
        returnreason = new javax.swing.JCheckBox();
        paymentMode = new javax.swing.JCheckBox();
        currencyTypes = new javax.swing.JCheckBox();
        followUpPeriod = new javax.swing.JCheckBox();
        com_priority = new javax.swing.JCheckBox();
        advance_CanReasons = new javax.swing.JCheckBox();
        presCharValues = new javax.swing.JCheckBox();
        deliveryMode = new javax.swing.JCheckBox();
        defectReasons = new javax.swing.JCheckBox();
        cancelReasons = new javax.swing.JCheckBox();
        invoiceCancelReasons = new javax.swing.JCheckBox();
        articleParams = new javax.swing.JCheckBox();
        downloadingFrame = new javax.swing.JInternalFrame();
        jDesktopPane2 = new javax.swing.JDesktopPane();
        jProgressBar1 = new JProgressBar(0,50);
        jLabel1 = new javax.swing.JLabel();
        ok = new javax.swing.JButton();
        selectAll = new javax.swing.JCheckBox();
        siteListMaster = new javax.swing.JCheckBox();
        AuthFrame = new javax.swing.JInternalFrame();
        jDesktopPane9 = new javax.swing.JDesktopPane();
        jLabel3 = new javax.swing.JLabel();
        AuthPassword = new javax.swing.JPasswordField(30);
        auth_OKbutton = new javax.swing.JButton();
        auth_caneclbutton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        StatusPane = new javax.swing.JScrollPane();
        StatusArea = new javax.swing.JTextArea();
        bordeLabelForTable1 = new javax.swing.JLabel();
        sunglass_attribures = new javax.swing.JCheckBox();
        doctorDetails = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        deliverytat = new javax.swing.JCheckBox();
        cl_attributes = new javax.swing.JCheckBox();
        cl_brands = new javax.swing.JCheckBox();

        setBackground(new java.awt.Color(153, 204, 255));
        setName("Configuration"); // NOI18N
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });

        jDesktopPane1.setBackground(new java.awt.Color(153, 204, 255));
        jDesktopPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)), "Configuration Data Download", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 12))); // NOI18N

        DonloadActionButton.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        DonloadActionButton.setText("Download ");
        DonloadActionButton.setEnabled(false);
        DonloadActionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DonloadActionButtonActionPerformed(evt);
            }
        });
        jDesktopPane1.add(DonloadActionButton);
        DonloadActionButton.setBounds(10, 420, 260, 30);

        preferredcommaster.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        preferredcommaster.setText("Preferred Update Mode Master");
        preferredcommaster.setOpaque(false);
        preferredcommaster.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(preferredcommaster);
        preferredcommaster.setBounds(20, 340, 240, 25);

        titlemaster.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        titlemaster.setText("Title Master");
        titlemaster.setOpaque(false);
        titlemaster.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(titlemaster);
        titlemaster.setBounds(20, 40, 180, 25);

        relationshipmaster.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        relationshipmaster.setText("Relationship Master");
        relationshipmaster.setOpaque(false);
        relationshipmaster.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(relationshipmaster);
        relationshipmaster.setBounds(20, 70, 210, 25);

        citymaster.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        citymaster.setText("City Master");
        citymaster.setOpaque(false);
        citymaster.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(citymaster);
        citymaster.setBounds(20, 100, 140, 25);

        statemaster.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        statemaster.setText("State Master");
        statemaster.setOpaque(false);
        statemaster.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(statemaster);
        statemaster.setBounds(20, 130, 140, 25);

        designationmaster.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        designationmaster.setText("Designation Master");
        designationmaster.setOpaque(false);
        designationmaster.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(designationmaster);
        designationmaster.setBounds(20, 190, 190, 25);

        educationmaster.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        educationmaster.setText("Education Master");
        educationmaster.setOpaque(false);
        educationmaster.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(educationmaster);
        educationmaster.setBounds(20, 220, 190, 25);

        occupationmaster.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        occupationmaster.setText("Occupation Master");
        occupationmaster.setOpaque(false);
        occupationmaster.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(occupationmaster);
        occupationmaster.setBounds(20, 250, 180, 25);

        maritalstatusmaster.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        maritalstatusmaster.setText("Marital Status Master");
        maritalstatusmaster.setOpaque(false);
        maritalstatusmaster.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(maritalstatusmaster);
        maritalstatusmaster.setBounds(20, 280, 190, 25);

        customercategorymaster.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        customercategorymaster.setText("Customer Category Master");
        customercategorymaster.setOpaque(false);
        customercategorymaster.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(customercategorymaster);
        customercategorymaster.setBounds(20, 310, 230, 25);

        countrymaster.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        countrymaster.setText("Country Master");
        countrymaster.setOpaque(false);
        countrymaster.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(countrymaster);
        countrymaster.setBounds(20, 160, 140, 25);

        cardtypemaster.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        cardtypemaster.setText("Card Types Master");
        cardtypemaster.setOpaque(false);
        cardtypemaster.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(cardtypemaster);
        cardtypemaster.setBounds(280, 130, 190, 25);

        returnreason.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        returnreason.setText("Return Reasons");
        returnreason.setOpaque(false);
        returnreason.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(returnreason);
        returnreason.setBounds(280, 190, 140, 25);

        paymentMode.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        paymentMode.setText("Payment Modes Master");
        paymentMode.setOpaque(false);
        paymentMode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(paymentMode);
        paymentMode.setBounds(280, 70, 190, 25);

        currencyTypes.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        currencyTypes.setText("Currency Types");
        currencyTypes.setOpaque(false);
        currencyTypes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(currencyTypes);
        currencyTypes.setBounds(280, 100, 170, 25);

        followUpPeriod.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        followUpPeriod.setText("Follow Up Period Master");
        followUpPeriod.setOpaque(false);
        followUpPeriod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(followUpPeriod);
        followUpPeriod.setBounds(280, 370, 190, 25);

        com_priority.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        com_priority.setText("Communication Priority ");
        com_priority.setOpaque(false);
        com_priority.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(com_priority);
        com_priority.setBounds(20, 370, 210, 25);

        advance_CanReasons.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        advance_CanReasons.setText("Advance Receipt Cancel Reasons");
        advance_CanReasons.setOpaque(false);
        advance_CanReasons.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(advance_CanReasons);
        advance_CanReasons.setBounds(280, 160, 270, 25);

        presCharValues.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        presCharValues.setText("Characteristic Values");
        presCharValues.setOpaque(false);
        presCharValues.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(presCharValues);
        presCharValues.setBounds(280, 40, 190, 20);

        deliveryMode.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        deliveryMode.setText("Delivery Mode");
        deliveryMode.setOpaque(false);
        deliveryMode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        deliveryMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deliveryModeActionPerformed(evt);
            }
        });
        jDesktopPane1.add(deliveryMode);
        deliveryMode.setBounds(530, 70, 120, 20);
        deliveryMode.setVisible(true);

        defectReasons.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        defectReasons.setText("Defect Reasons");
        defectReasons.setOpaque(false);
        defectReasons.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(defectReasons);
        defectReasons.setBounds(280, 220, 170, 20);

        cancelReasons.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        cancelReasons.setText("SalesOrder Cancel Reasons");
        cancelReasons.setOpaque(false);
        cancelReasons.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(cancelReasons);
        cancelReasons.setBounds(280, 250, 220, 20);

        invoiceCancelReasons.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        invoiceCancelReasons.setText("Invoice Cancel Reasons");
        invoiceCancelReasons.setOpaque(false);
        invoiceCancelReasons.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(invoiceCancelReasons);
        invoiceCancelReasons.setBounds(280, 280, 220, 20);

        articleParams.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        articleParams.setText("Article Params");
        articleParams.setOpaque(false);
        articleParams.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(articleParams);
        articleParams.setBounds(280, 310, 220, 20);

        downloadingFrame.setTitle("Downloading MasterData");

        jProgressBar1.setStringPainted(true);
        jDesktopPane2.add(jProgressBar1);
        jProgressBar1.setBounds(20, 30, 350, 17);

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("jLabel1");
        jDesktopPane2.add(jLabel1);
        jLabel1.setBounds(20, 60, 340, 40);

        ok.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        ok.setText("OK");
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });
        jDesktopPane2.add(ok);
        ok.setBounds(150, 90, 90, 23);

        org.jdesktop.layout.GroupLayout downloadingFrameLayout = new org.jdesktop.layout.GroupLayout(downloadingFrame.getContentPane());
        downloadingFrame.getContentPane().setLayout(downloadingFrameLayout);
        downloadingFrameLayout.setHorizontalGroup(
            downloadingFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jDesktopPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
        );
        downloadingFrameLayout.setVerticalGroup(
            downloadingFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jDesktopPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 151, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );

        jDesktopPane1.setLayer(downloadingFrame, javax.swing.JLayeredPane.POPUP_LAYER);
        jDesktopPane1.add(downloadingFrame);
        downloadingFrame.setBounds(120, 130, 390, 180);

        selectAll.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
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
        jDesktopPane1.add(selectAll);
        selectAll.setBounds(20, 400, 90, 20);

        siteListMaster.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        siteListMaster.setText("Site List");
        siteListMaster.setOpaque(false);
        siteListMaster.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(siteListMaster);
        siteListMaster.setBounds(280, 340, 190, 20);

        AuthFrame.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        AuthFrame.setTitle(" Authentication..");
        AuthFrame.setVisible(true);
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

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel3.setText("Authentication Code :");
        jDesktopPane9.add(jLabel3);
        jLabel3.setBounds(10, 20, 150, 14);

        AuthPassword.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        AuthPassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        AuthPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                AuthPasswordfocusGainedForTextFields(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                AuthPasswordfocusLostForTxtField(evt);
            }
        });
        AuthPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                AuthPasswordkeyReleasedForPwd(evt);
            }
        });
        jDesktopPane9.add(AuthPassword);
        AuthPassword.setBounds(160, 20, 100, 18);

        auth_OKbutton.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
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
        jDesktopPane9.add(auth_OKbutton);
        auth_OKbutton.setBounds(70, 50, 60, 20);

        auth_caneclbutton.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        auth_caneclbutton.setText("Cancel");
        auth_caneclbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                auth_caneclbuttonActionPerformed(evt);
            }
        });
        jDesktopPane9.add(auth_caneclbutton);
        auth_caneclbutton.setBounds(140, 50, 80, 20);

        org.jdesktop.layout.GroupLayout AuthFrameLayout = new org.jdesktop.layout.GroupLayout(AuthFrame.getContentPane());
        AuthFrame.getContentPane().setLayout(AuthFrameLayout);
        AuthFrameLayout.setHorizontalGroup(
            AuthFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jDesktopPane9, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
        );
        AuthFrameLayout.setVerticalGroup(
            AuthFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jDesktopPane9, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
        );

        jDesktopPane1.setLayer(AuthFrame, javax.swing.JLayeredPane.POPUP_LAYER);
        jDesktopPane1.add(AuthFrame);
        AuthFrame.setBounds(120, 160, 280, 120);

        jLabel2.setBackground(new java.awt.Color(232, 247, 251));
        jLabel2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(88, 109, 148));
        jLabel2.setText("Downloading Status");
        jDesktopPane1.add(jLabel2);
        jLabel2.setBounds(10, 450, 460, 20);

        StatusArea.setEditable(false);
        StatusArea.setBackground(new java.awt.Color(237, 247, 252));
        StatusArea.setColumns(20);
        StatusArea.setRows(5);
        StatusArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        StatusArea.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                StatusAreaFocusGained(evt);
            }
        });
        StatusPane.setViewportView(StatusArea);

        jDesktopPane1.add(StatusPane);
        StatusPane.setBounds(10, 470, 460, 110);

        bordeLabelForTable1.setBackground(new java.awt.Color(237, 247, 252));
        bordeLabelForTable1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        bordeLabelForTable1.setOpaque(true);
        bordeLabelForTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                bordeLabelForTable1KeyReleased(evt);
            }
        });
        jDesktopPane1.add(bordeLabelForTable1);
        bordeLabelForTable1.setBounds(10, 470, 458, 110);

        sunglass_attribures.setBackground(new java.awt.Color(153, 204, 255));
        sunglass_attribures.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        sunglass_attribures.setText("Sunglass Attributes");
        sunglass_attribures.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(sunglass_attribures);
        sunglass_attribures.setBounds(530, 40, 170, 25);

        doctorDetails.setBackground(new java.awt.Color(153, 204, 255));
        doctorDetails.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        doctorDetails.setText("Doctor Details");
        doctorDetails.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(doctorDetails);
        doctorDetails.setBounds(530, 120, 120, 25);

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel4.setText("/");
        jDesktopPane1.add(jLabel4);
        jLabel4.setBounds(655, 71, 8, 16);

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel5.setText("Return Channel Name");
        jDesktopPane1.add(jLabel5);
        jLabel5.setBounds(552, 90, 150, 16);

        deliverytat.setBackground(new java.awt.Color(153, 204, 255));
        deliverytat.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        deliverytat.setText("Delivery TAT");
        deliverytat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                setDowmLoadButtonEnabled(evt);
            }
        });
        jDesktopPane1.add(deliverytat);
        deliverytat.setBounds(530, 150, 120, 25);

        cl_attributes.setBackground(new java.awt.Color(153, 204, 255));
        cl_attributes.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        cl_attributes.setText("Contact Lens Attributes");
        cl_attributes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cl_attributessetDowmLoadButtonEnabled(evt);
            }
        });
        cl_attributes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cl_attributesActionPerformed(evt);
            }
        });
        jDesktopPane1.add(cl_attributes);
        cl_attributes.setBounds(530, 180, 180, 25);

        cl_brands.setBackground(new java.awt.Color(153, 204, 255));
        cl_brands.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        cl_brands.setText("Contact Lens Brands");
        cl_brands.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cl_brandssetDowmLoadButtonEnabled(evt);
            }
        });
        cl_brands.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cl_brandsActionPerformed(evt);
            }
        });
        jDesktopPane1.add(cl_brands);
        cl_brands.setBounds(530, 210, 180, 25);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jDesktopPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 719, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jDesktopPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    private void DonloadActionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DonloadActionButtonActionPerformed
        // TODO add your handling code here:
        AuthFrame.setVisible(true);
        DonloadActionButton.setEnabled(false);
        AuthPassword.requestFocus();
        StatusArea.setText("");

}//GEN-LAST:event_DonloadActionButtonActionPerformed
    public void downloadData() {
        try {
            boolean download = true;
            if (statemaster.isSelected() && !citymaster.isSelected()) {
                download = false;
                JOptionPane.showMessageDialog(this, "Please select the City master", "Message", JOptionPane.ERROR_MESSAGE);
            } else if (countrymaster.isSelected() && (!citymaster.isSelected() || !statemaster.isSelected())) {
                download = false;
                JOptionPane.showMessageDialog(this, "Please select the state master and city master", "Message", JOptionPane.ERROR_MESSAGE);
            }
            if (download) {
                String str = "<html>" + "<font color=\"#008000\">" + "<b>"
                        + "Downloading is in process......." + "</b>" + "</font>" + "</html>";

                jLabel1.setText(str);
                downloadingFrame.setVisible(true);
                jProgressBar1.setValue(0);
                DonloadActionButton.setEnabled(false);
                ok.setEnabled(false);
                timer.start();
                ConfigurationDetails details = getObjectFromScreen();

                configurationThread = new ConfigurationThread(details, StatusArea);
                thread = new Thread(configurationThread);
                thread.setDaemon(true);
                thread.start();
                //   details.sendRequest();

                //  JOptionPane.showMessageDialog(this, "Completed");
            }
            /*  ConfigurationWebService configurationWebService = new ConfigurationWebService();
        int result1 = configurationWebService.downloadMasterData();
        if(result1 > 0)
            JOptionPane.showMessageDialog(this, "Completed");*/

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

    }
    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
        // TODO add your handling code here:
        downloadingFrame.setVisible(false);
    }//GEN-LAST:event_okActionPerformed

    private void selectAllItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_selectAllItemStateChanged
        // TODO add your handling code here:
        if (selectAll.isSelected()) {
            setSelection(true);
        } else {
            setSelection(false);
        }
    }//GEN-LAST:event_selectAllItemStateChanged

    private void setDowmLoadButtonEnabled(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_setDowmLoadButtonEnabled
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            DonloadActionButton.setEnabled(true);
        } else if (citymaster.isSelected() || titlemaster.isSelected() || relationshipmaster.isSelected() || statemaster.isSelected() || countrymaster.isSelected() || designationmaster.isSelected() || educationmaster.isSelected() || occupationmaster.isSelected() || maritalstatusmaster.isSelected() || customercategorymaster.isSelected() || preferredcommaster.isSelected() || returnreason.isSelected() || cardtypemaster.isSelected() || paymentMode.isSelected() || currencyTypes.isSelected() || followUpPeriod.isSelected() || presCharValues.isSelected() || deliveryMode.isSelected() || com_priority.isSelected() || advance_CanReasons.isSelected() || defectReasons.isSelected() || cancelReasons.isSelected() || invoiceCancelReasons.isSelected() || articleParams.isSelected() || sunglass_attribures.isSelected() || doctorDetails.isSelected() || deliverytat.isSelected() || cl_attributes.isSelected() || cl_brands.isSelected() || selectAll.isSelected()) {
            DonloadActionButton.setEnabled(true);
        } else {
            DonloadActionButton.setEnabled(false);
        }

    }//GEN-LAST:event_setDowmLoadButtonEnabled

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
        private void AuthPasswordfocusLostForTxtField(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AuthPasswordfocusLostForTxtField
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
        }//GEN-LAST:event_AuthPasswordfocusLostForTxtField

        private void AuthPasswordkeyReleasedForPwd(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AuthPasswordkeyReleasedForPwd
            // TODO add your handling code here:
            if (evt != null) {
                if (evt.getSource().equals(AuthPassword)) {
                    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                        auth_OKbuttonActionPerformed(null);
                    }
                }
            }
        }//GEN-LAST:event_AuthPasswordkeyReleasedForPwd

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

        private void StatusAreaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_StatusAreaFocusGained
            // TODO add your handling code here:
}//GEN-LAST:event_StatusAreaFocusGained

        private void bordeLabelForTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bordeLabelForTable1KeyReleased
            // TODO add your handling code here:
        }//GEN-LAST:event_bordeLabelForTable1KeyReleased

        private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
            // TODO add your handling code here:

            evt.setSource(serverConsole.MainTabbedPane);
            serverConsole.myMainTabbedPaneMouseMoved(evt);
        }//GEN-LAST:event_formMouseMoved

    private void deliveryModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deliveryModeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deliveryModeActionPerformed

    private void cl_attributessetDowmLoadButtonEnabled(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cl_attributessetDowmLoadButtonEnabled
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            DonloadActionButton.setEnabled(true);
        } else if (citymaster.isSelected() || titlemaster.isSelected() || relationshipmaster.isSelected() || statemaster.isSelected() || countrymaster.isSelected() || designationmaster.isSelected() || educationmaster.isSelected() || occupationmaster.isSelected() || maritalstatusmaster.isSelected() || customercategorymaster.isSelected() || preferredcommaster.isSelected() || returnreason.isSelected() || cardtypemaster.isSelected() || paymentMode.isSelected() || currencyTypes.isSelected() || followUpPeriod.isSelected() || presCharValues.isSelected() || deliveryMode.isSelected() || com_priority.isSelected() || advance_CanReasons.isSelected() || defectReasons.isSelected() || cancelReasons.isSelected() || invoiceCancelReasons.isSelected() || articleParams.isSelected() || sunglass_attribures.isSelected() || doctorDetails.isSelected() || cl_attributes.isSelected() || cl_brands.isSelected() || selectAll.isSelected()) {
            DonloadActionButton.setEnabled(true);
        } else {
            DonloadActionButton.setEnabled(false);
        }
    }//GEN-LAST:event_cl_attributessetDowmLoadButtonEnabled

    private void cl_attributesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cl_attributesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cl_attributesActionPerformed

    private void cl_brandssetDowmLoadButtonEnabled(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cl_brandssetDowmLoadButtonEnabled
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            DonloadActionButton.setEnabled(true);
        } else if (citymaster.isSelected() || titlemaster.isSelected() || relationshipmaster.isSelected() || statemaster.isSelected() || countrymaster.isSelected() || designationmaster.isSelected() || educationmaster.isSelected() || occupationmaster.isSelected() || maritalstatusmaster.isSelected() || customercategorymaster.isSelected() || preferredcommaster.isSelected() || returnreason.isSelected() || cardtypemaster.isSelected() || paymentMode.isSelected() || currencyTypes.isSelected() || followUpPeriod.isSelected() || presCharValues.isSelected() || deliveryMode.isSelected() || com_priority.isSelected() || advance_CanReasons.isSelected() || defectReasons.isSelected() || cancelReasons.isSelected() || invoiceCancelReasons.isSelected() || articleParams.isSelected() || sunglass_attribures.isSelected() || doctorDetails.isSelected() || cl_attributes.isSelected() || cl_brands.isSelected() || selectAll.isSelected()) {
            DonloadActionButton.setEnabled(true);
        } else {
            DonloadActionButton.setEnabled(false);
        }
    }//GEN-LAST:event_cl_brandssetDowmLoadButtonEnabled

    private void cl_brandsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cl_brandsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cl_brandsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JInternalFrame AuthFrame;
    private javax.swing.JPasswordField AuthPassword;
    private javax.swing.JButton DonloadActionButton;
    private javax.swing.JTextArea StatusArea;
    private javax.swing.JScrollPane StatusPane;
    private javax.swing.JCheckBox advance_CanReasons;
    private javax.swing.JCheckBox articleParams;
    private javax.swing.JButton auth_OKbutton;
    private javax.swing.JButton auth_caneclbutton;
    private javax.swing.JLabel bordeLabelForTable1;
    private javax.swing.JCheckBox cancelReasons;
    private javax.swing.JCheckBox cardtypemaster;
    private javax.swing.JCheckBox citymaster;
    private javax.swing.JCheckBox cl_attributes;
    private javax.swing.JCheckBox cl_brands;
    private javax.swing.JCheckBox com_priority;
    private javax.swing.JCheckBox countrymaster;
    private javax.swing.JCheckBox currencyTypes;
    private javax.swing.JCheckBox customercategorymaster;
    private javax.swing.JCheckBox defectReasons;
    private javax.swing.JCheckBox deliveryMode;
    private javax.swing.JCheckBox deliverytat;
    private javax.swing.JCheckBox designationmaster;
    private javax.swing.JCheckBox doctorDetails;
    private javax.swing.JInternalFrame downloadingFrame;
    private javax.swing.JCheckBox educationmaster;
    private javax.swing.JCheckBox followUpPeriod;
    private javax.swing.JCheckBox invoiceCancelReasons;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JDesktopPane jDesktopPane2;
    private javax.swing.JDesktopPane jDesktopPane9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JCheckBox maritalstatusmaster;
    private javax.swing.JCheckBox occupationmaster;
    private javax.swing.JButton ok;
    private javax.swing.JCheckBox paymentMode;
    private javax.swing.JCheckBox preferredcommaster;
    private javax.swing.JCheckBox presCharValues;
    private javax.swing.JCheckBox relationshipmaster;
    private javax.swing.JCheckBox returnreason;
    private javax.swing.JCheckBox selectAll;
    private javax.swing.JCheckBox siteListMaster;
    private javax.swing.JCheckBox statemaster;
    private javax.swing.JCheckBox sunglass_attribures;
    private javax.swing.JCheckBox titlemaster;
    // End of variables declaration//GEN-END:variables
}
