/*
 * PosStagingTableStatus.java
 *
 * Created on December 4, 2008, 12:53 PM
 */
package ISRetail.serverconsole;

import ISRetail.Helpers.ConvertDate;
import ISRetail.components.MaxLengthDigitSlashDocument;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.utility.validations.Validations;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import posstaging.PosStagingDO;
import posstaging.PosStagingPOJO;

/**
 *
 * @author  enteg
 */
public class StagingTableStatus extends javax.swing.JPanel {

    /** Creates new form PosStagingTableStatus */
    private final Color selectedColor = new Color(255, 255, 150);//Color of Textfields when focu gained
    private final Color originalColor = new Color(255, 255, 255);//Color of Textfields when focu lost
    Properties sNoMessageIdList;
 ServerConsole serverConsole;
    public StagingTableStatus( ServerConsole serverConsole) {
        try {
            this.serverConsole =serverConsole ;
            initComponents();
            TransLabel.setVisible(false);
            TransCombo.setVisible(false);
            createdFrmDatePicker.setVisible(false);
            createdToDatePicker.setVisible(false);
            ReinitiateButton.setEnabled(false);

            stagingTable.getTableHeader().setBackground(new Color(88, 109, 148));
            stagingTable.getTableHeader().setForeground(new Color(201, 232, 252));
            stagingTable.getTableHeader().setFont(new java.awt.Font("Verdana", 1, 12));
            stagingTable.getColumnModel().getColumn(8).setCellRenderer(new StagingStatusCellRenderer());
            stagingTable.getColumnModel().getColumn(9).setCellRenderer(new StagingStatusCellRenderer());
            stagingTable.getColumnModel().getColumn(10).setCellEditor(new DefaultCellEditor(ReinitiateCheckBox));//Chk box to reinitiate
            stagingTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

                public void valueChanged(ListSelectionEvent e) {
                    int selectedRow = stagingTable.getSelectedRow();

                    int selectedCol = stagingTable.getSelectedColumn();
                    if (selectedRow > -1) {
                        makeCheckSelectCancelCheckBoxDisabled(selectedRow);
                        if (selectedCol == 10) {
                            makeReinitiateButtonEnabled();
                        }
                    }
                }
            });

            createdFrom.setText(ServerConsole.businessDate);
            createdto.setText(ServerConsole.businessDate);
            performSearchForFilters();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }
 public void makeSelectAllCheckBoxEnabled() {
        try {
            //*****To enable checkwarranty button if any of the warrantu check box is selected- START
            boolean anyOneCancelled = false;
               for (int row = 0; row < stagingTable.getRowCount(); row++) {
                    if (stagingTable.getValueAt(row, 8) != null) {
                        if (String.valueOf(stagingTable.getValueAt(row, 8)).trim().equalsIgnoreCase("Cancelled")) {
                            anyOneCancelled=true;
                            break;
                        }
                    }
                }
            if (anyOneCancelled) {
                selectAllCheckBox.setEnabled(true);
            }else{
                selectAllCheckBox.setEnabled(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public void makeCheckSelectCancelCheckBoxDisabled(int selectedRow) {
        String status;
        try {
            if (selectedRow > -1) {
                status = String.valueOf(stagingTable.getValueAt(selectedRow, 8));
                if (status != null) {
                    if (status.trim().equalsIgnoreCase("Cancelled")) {
                        ReinitiateCheckBox.setEnabled(true);
                    } else {
                        ReinitiateCheckBox.setEnabled(false);
                        ReinitiateCheckBox.setSelected(false);
                        stagingTable.setValueAt(false, selectedRow, 10);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            status = null;
        }
    }

    public void displayStagingReport(String searchstatement, Connection con) {
        PosStagingDO stagingDO;
        ArrayList<PosStagingPOJO> stagingList;
        Iterator itr;
        Vector vectToAddToTable;
        try {
            stagingDO = new PosStagingDO();
            stagingList = stagingDO.getStagingTableStaus(con, searchstatement);
            ((DefaultTableModel) stagingTable.getModel()).setRowCount(0);
            if (stagingList != null) {
                itr = stagingList.iterator();
                int cnt = 0;
                if (itr != null) {
                    sNoMessageIdList = new Properties();
                    while (itr.hasNext()) {
                        PosStagingPOJO stagingPOJO = (PosStagingPOJO) itr.next();
                        vectToAddToTable = new Vector();
                        cnt++;
                        vectToAddToTable.add(cnt);

                        if (stagingPOJO.getInterfaceID() != null) {
                            if (stagingPOJO.getInterfaceID().trim().equalsIgnoreCase("PW_IF21")) {
                                if (stagingPOJO.getTransactionID2() != null && stagingPOJO.getTransactionID2().trim().length() > 0) {
                                     vectToAddToTable.add(stagingPOJO.getInterfaceName());
                                    vectToAddToTable.add(stagingPOJO.getTransactionID2());
                                   
                                } else {
                                    vectToAddToTable.add("ACknowledgement Updation");
                                    vectToAddToTable.add(stagingPOJO.getTransactionID());
                                   
                                }
                            } else {
                                vectToAddToTable.add(stagingPOJO.getInterfaceName());
                                vectToAddToTable.add(stagingPOJO.getTransactionID());
                                
                            }
                        }
                        if (stagingPOJO.getUpdateType() != null) {
                            if (stagingPOJO.getUpdateType().trim().equalsIgnoreCase("U")) {
                                vectToAddToTable.add("Change");
                            } else if (stagingPOJO.getUpdateType().trim().equalsIgnoreCase("I")){
                                vectToAddToTable.add("Creation");
                            }else if (stagingPOJO.getUpdateType().trim().equalsIgnoreCase("F")){
                                vectToAddToTable.add("Force Release");
                            }
                        }else{
                             vectToAddToTable.add("");
                        }
                        vectToAddToTable.add(ConvertDate.getNumericToDate(stagingPOJO.getCreatedDate()));
                        vectToAddToTable.add(ConvertDate.getformattedTimeFromTime(stagingPOJO.getCreatedTime()));
                        vectToAddToTable.add(ConvertDate.getNumericToDate(stagingPOJO.getLastAttemptDate()));
                        vectToAddToTable.add(ConvertDate.getformattedTimeFromTime(stagingPOJO.getLastAttemptTime()));
                        if (stagingPOJO.getUpdateStatus() != null) {
                            if (stagingPOJO.getUpdateStatus().trim().equalsIgnoreCase("send")) {
                                vectToAddToTable.add("Completed");
                            } else {
                                vectToAddToTable.add(stagingPOJO.getUpdateStatus());
                            }
                        }
                        if (stagingPOJO.getSapIdStatus() != null) {
                            if (stagingPOJO.getSapIdStatus().trim().equalsIgnoreCase("C")) {
                                vectToAddToTable.add("Completed");//Response got back as completed in ISR
                            } else if (stagingPOJO.getSapIdStatus().trim().equalsIgnoreCase("N")) {
                                vectToAddToTable.add("Waiting"); //Request Not send to ISR to get status back
                               //  vectToAddToTable.add("Not Completed"); //Request Not send to ISR to get status back
                            }else if (stagingPOJO.getSapIdStatus().trim().equalsIgnoreCase("X")) {
                                vectToAddToTable.add("Not Completed");//Response got back as not completed ISR
                            }
                        }
                        vectToAddToTable.add(false);
                        sNoMessageIdList.put(String.valueOf(cnt), stagingPOJO.getMessageID());
                        ((DefaultTableModel) stagingTable.getModel()).addRow(vectToAddToTable);
                        int tabRowCount = stagingTable.getRowCount();
                        ((DefaultTableModel) stagingTable.getModel()).fireTableRowsInserted(tabRowCount, tabRowCount);
                    }
                }
            }
            makeSelectAllCheckBoxEnabled();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stagingDO = null;
            stagingList = null;
            itr = null;
            vectToAddToTable = null;
        }
    }

    public void performSearchForFilters() {
        StringBuffer searchStatement;
        MsdeConnection msdeConn;
        Connection con;
        try {
            msdeConn = new MsdeConnection();
            con = msdeConn.createConnection();
            int frm_Date = 0, to_Date = 0;
            boolean searchStatementPresent = false, betweenDates = false;
            searchStatement = new StringBuffer("select tbl_pos_staging.interfaceid interfaceid,updatetype,priority,interfacename,messageid,transactionid1,transactionid2,transactionid3,attempts,createdby,createddate,createdtime,lastattemptdate,lastattempttime,updatestatus,sapidstatus from tbl_pos_staging,tbl_pos_stagingmaster where tbl_pos_staging.interfaceid=tbl_pos_stagingmaster.interfaceid ");
            if (Validations.isFieldNotEmpty(StatusCombo.getSelectedItem())) {
                if (String.valueOf(StatusCombo.getSelectedItem()).equalsIgnoreCase("Completed")) {
                    searchStatement.append("  and updatestatus = 'send' ");
                } else {
                    searchStatement.append("  and updatestatus = '" + StatusCombo.getSelectedItem() + "' ");
                }
                searchStatementPresent = true;
            }
            
             if (Validations.isFieldNotEmpty(ISRStatusCombo.getSelectedItem())) {
                if (String.valueOf(ISRStatusCombo.getSelectedItem()).equalsIgnoreCase("Completed")) {
                    searchStatement.append("  and sapidstatus = 'C' ");
                } if (String.valueOf(ISRStatusCombo.getSelectedItem()).equalsIgnoreCase("Not Completed")) {
                    searchStatement.append("  and sapidstatus = 'X' ");
                }if (String.valueOf(ISRStatusCombo.getSelectedItem()).equalsIgnoreCase("Waiting")) {
                    searchStatement.append("  and sapidstatus = 'N' ");
                }
                searchStatementPresent = true;
            }
            //datesearch
            betweenDates = false;
            int currentDate = ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy");
            frm_Date = 0;
            try {
                frm_Date = ConvertDate.getDateNumeric(createdFrom.getText(), "dd/MM/yyyy");
            } catch (Exception e) {
            }
            to_Date = 0;
            try {
                to_Date = ConvertDate.getDateNumeric(createdto.getText(), "dd/MM/yyyy");
            } catch (Exception e) {
            }
            if ((frm_Date != 0 && to_Date != 0) || (frm_Date != 0) || (to_Date != 0)) {
                if (frm_Date != 0 && to_Date != 0) {
                } else if (frm_Date != 0) {
                    to_Date = ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy");
                    searchStatementPresent = true;
                } else if (to_Date != 0) {
                    frm_Date = ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy");
                    searchStatementPresent = true;
                }
                betweenDates = true;
                searchStatement.append(" and createddate between " + frm_Date + " and " + to_Date);
                searchStatementPresent = true;

            }



            searchStatement.append(" order by createddate desc,createdtime desc");
            ((DefaultTableModel) stagingTable.getModel()).setRowCount(0);
            if (searchStatementPresent == true && searchStatement != null) {
                displayStagingReport(searchStatement.toString(), con);
            } else if ( Validations.isFieldNotEmpty(StatusCombo.getSelectedItem()) || Validations.isFieldNotEmpty(ISRStatusCombo.getSelectedItem()) || !isDateFieldEmpty(createdFrom.getText()) || !isDateFieldEmpty(createdFrom.getText())) {
                searchStatement = new StringBuffer("select tbl_pos_staging.interfaceid interfaceid,updatetype,priority,interfacename,messageid,transactionid1,transactionid2,transactionid3,attempts,createdby,createddate,createdtime,lastattemptdate,lastattempttime,updatestatus,sapidstatus from tbl_pos_staging,tbl_pos_stagingmaster where tbl_pos_staging.interfaceid=tbl_pos_stagingmaster.interfaceid order by createddate desc,createdtime desc");
                displayStagingReport(searchStatement.toString(), con);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            searchStatement = null;
        }

    }

    public void invokeShortKeys(KeyEvent evt) {
        try {
            if (evt != null) {
                if (evt.getKeyCode() == KeyEvent.VK_F5) {
                    RefreshBtnActionPerformed(null);
                } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (createdFrmDatePicker.isVisible()) {
                        createdFrmDatePicker.setVisible(false);
                    } else if (createdToDatePicker.isVisible()) {
                        createdToDatePicker.setVisible(false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public boolean checkFrmAndToDates(JFormattedTextField searchFromDate, JFormattedTextField searchToDate) {
        boolean valid = true;
        try {
            if (Validations.isFieldNotEmpty(searchFromDate.getText()) && Validations.isFieldNotEmpty(searchToDate.getText())) {
                if (!Validations.isToDateAfterFromDate(searchFromDate.getText(), searchToDate.getText())) {
                    JOptionPane.showMessageDialog(this, "'From date' Should be less than or equal to 'To date'", "Error Message", JOptionPane.ERROR_MESSAGE);
                    valid = false;
                }
            }
        } catch (Exception e) {
        } finally {
        }
        return valid;
    }

    public boolean isDateFieldEmpty(String val) {
        boolean isEmpty = false;
        try {
            if (val == null) {
                isEmpty = true;
            } else if (val.trim().equals("")) {
                isEmpty = true;
            } else if (val.contains("D") || val.contains("M") || val.contains("Y") || val.contains("/")) {
                isEmpty = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return isEmpty;
    }

    public void makeReinitiateButtonEnabled() {
        Object chk;
        Boolean chkWarSel;
        try {
            //*****To enable checkwarranty button if any of the warrantu check box is selected- START
            boolean anyOneSelected = false;
            for (int row = 0; row < stagingTable.getRowCount(); row++) {
                if (stagingTable.getSelectedColumn() == 10) {
                    chk = stagingTable.getValueAt(row, 10);
                    try {
                        chkWarSel = (Boolean) chk;
                        anyOneSelected = chkWarSel.booleanValue();
                        if (anyOneSelected) {
                            ReinitiateButton.setEnabled(true);
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!anyOneSelected) {
                ReinitiateButton.setEnabled(false);
            }//*****To enable checkwarranty button if any of the warrantu check box is selected- END
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            chk = null;
            chkWarSel = null;
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ReinitiateCheckBox = new javax.swing.JCheckBox();
        MainDesktopPane = new javax.swing.JDesktopPane();
        MainPanel = new javax.swing.JPanel();
        stagingScrollPane = new javax.swing.JScrollPane();
        stagingTable = new javax.swing.JTable();
        ReinitiateButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        selectAllCheckBox = new javax.swing.JCheckBox();
        crteatedToLabel = new javax.swing.JLabel();
        CreatedFrmLabel = new javax.swing.JLabel();
        StatusLabel = new javax.swing.JLabel();
        StatusCombo = new javax.swing.JComboBox();
        createdFrom = new javax.swing.JFormattedTextField();
        createdto = new javax.swing.JFormattedTextField();
        RefreshBtn = new javax.swing.JButton();
        firstSeparator = new javax.swing.JSeparator();
        createdToDatePickerBtn = new javax.swing.JButton();
        createdFrmDatePickerBtn = new javax.swing.JButton();
        createdToDatePicker = new ISRetail.components.DatePicker12();
        createdFrmDatePicker = new ISRetail.components.DatePicker12();
        ISRStatusLabel = new javax.swing.JLabel();
        ISRStatusCombo = new javax.swing.JComboBox();
        TransLabel = new javax.swing.JLabel();
        TransCombo = new javax.swing.JComboBox();

        ReinitiateCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ReinitiateCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ReinitiateCheckBoxItemStateChanged(evt);
            }
        });
        ReinitiateCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReinitiateCheckBoxActionPerformed(evt);
            }
        });
        ReinitiateCheckBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ReinitiateCheckBoxKeyReleased(evt);
            }
        });
        ReinitiateCheckBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ReinitiateCheckBoxMouseClicked(evt);
            }
        });

        setBackground(new java.awt.Color(153, 204, 255));

        MainDesktopPane.setBackground(new java.awt.Color(153, 204, 255));
        MainDesktopPane.setFont(new java.awt.Font("Verdana", 1, 10));
        MainDesktopPane.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                MainDesktopPaneMouseMoved(evt);
            }
        });
        MainDesktopPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MainDesktopPaneMouseClicked(evt);
            }
        });

        MainPanel.setBackground(new java.awt.Color(237, 247, 252));
        MainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(88, 109, 148), new java.awt.Color(201, 232, 252)));

        stagingScrollPane.setAutoscrolls(true);

        stagingTable.setBackground(new java.awt.Color(237, 247, 252));
        stagingTable.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(201, 232, 252), new java.awt.Color(88, 109, 148)));
        stagingTable.setFont(new java.awt.Font("Verdana", 1, 12));
        stagingTable.setForeground(new java.awt.Color(88, 109, 148));
        stagingTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.No.", "Transaction Name", "Transaction ID", "Update Mode", "Created Date", "Created Time", "Last Attempted Date", "Last Attempted Time", "POS to PI Status", "ISR Status", "Select to Renitiate"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        stagingTable.setGridColor(new java.awt.Color(88, 109, 148));
        stagingTable.getTableHeader().setReorderingAllowed(false);
        stagingTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                stagingTableMouseMoved(evt);
            }
        });
        stagingTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                stagingTableKeyReleased(evt);
            }
        });
        stagingTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stagingTableMouseClicked(evt);
            }
        });
        stagingScrollPane.setViewportView(stagingTable);
        stagingTable.getColumnModel().getColumn(0).setMinWidth(40);
        stagingTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        stagingTable.getColumnModel().getColumn(0).setMaxWidth(40);
        stagingTable.getColumnModel().getColumn(1).setPreferredWidth(185);
        stagingTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        stagingTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        stagingTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        stagingTable.getColumnModel().getColumn(5).setPreferredWidth(120);
        stagingTable.getColumnModel().getColumn(6).setPreferredWidth(140);
        stagingTable.getColumnModel().getColumn(7).setPreferredWidth(140);
        stagingTable.getColumnModel().getColumn(8).setPreferredWidth(130);
        stagingTable.getColumnModel().getColumn(9).setPreferredWidth(120);
        stagingTable.getColumnModel().getColumn(10).setPreferredWidth(150);

        ReinitiateButton.setFont(new java.awt.Font("Verdana", 1, 12));
        ReinitiateButton.setText("Re-Initialize");
        ReinitiateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReinitiateButtonActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(206, 218, 238));
        jLabel1.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel1.setForeground(new java.awt.Color(88, 109, 148));
        jLabel1.setText("  POS Staging Table");
        jLabel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(88, 109, 148), 2, true));
        jLabel1.setMaximumSize(new java.awt.Dimension(127, 18));
        jLabel1.setMinimumSize(new java.awt.Dimension(127, 18));
        jLabel1.setOpaque(true);

        selectAllCheckBox.setFont(new java.awt.Font("Verdana", 1, 10));
        selectAllCheckBox.setText("Select All Cancelled");
        selectAllCheckBox.setOpaque(false);
        selectAllCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                selectAllCheckBoxItemStateChanged(evt);
            }
        });

        org.jdesktop.layout.GroupLayout MainPanelLayout = new org.jdesktop.layout.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(MainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(MainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(stagingScrollPane)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, MainPanelLayout.createSequentialGroup()
                        .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 949, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(MainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(ReinitiateButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(selectAllCheckBox))))
                .add(22, 22, 22))
        );
        MainPanelLayout.setVerticalGroup(
            MainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(MainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(MainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(MainPanelLayout.createSequentialGroup()
                        .add(selectAllCheckBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(ReinitiateButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(stagingScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        MainPanel.setBounds(6, 135, 1130, 500);
        MainDesktopPane.add(MainPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        crteatedToLabel.setFont(new java.awt.Font("Verdana", 1, 12));
        crteatedToLabel.setText("Created To");
        crteatedToLabel.setBounds(290, 20, 100, 16);
        MainDesktopPane.add(crteatedToLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        CreatedFrmLabel.setFont(new java.awt.Font("Verdana", 1, 12));
        CreatedFrmLabel.setText("Created From");
        CreatedFrmLabel.setBounds(20, 20, 110, 16);
        MainDesktopPane.add(CreatedFrmLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        StatusLabel.setFont(new java.awt.Font("Verdana", 1, 12));
        StatusLabel.setText("POS to PI Status");
        StatusLabel.setBounds(20, 50, 130, 16);
        MainDesktopPane.add(StatusLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        StatusCombo.setFont(new java.awt.Font("Verdana", 1, 12));
        StatusCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Waiting", "Completed", "Cancelled" }));
        StatusCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StatusComboActionPerformed(evt);
            }
        });
        StatusCombo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                StatusComboFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                StatusComboFocusLost(evt);
            }
        });
        StatusCombo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                StatusComboKeyReleased(evt);
            }
        });
        StatusCombo.setBounds(150, 50, 120, 20);
        MainDesktopPane.add(StatusCombo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        createdFrom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        createdFrom.setDocument(new MaxLengthDigitSlashDocument(10));
        createdFrom.setFont(new java.awt.Font("Verdana", 1, 12));
        createdFrom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                createdFromfocusGainedForTextFields(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                createdFromfocusLostForTextFields(evt);
            }
        });
        createdFrom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                keyReleasedForSearch(evt);
            }
        });
        createdFrom.setBounds(150, 20, 100, 20);
        MainDesktopPane.add(createdFrom, javax.swing.JLayeredPane.DEFAULT_LAYER);

        createdto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        createdto.setDocument(new MaxLengthDigitSlashDocument(10));
        createdto.setFont(new java.awt.Font("Verdana", 1, 12));
        createdto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                createdtofocusGainedForTextFields(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                createdtofocusLostForTextFields(evt);
            }
        });
        createdto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                keyReleasedForSearch(evt);
            }
        });
        createdto.setBounds(430, 20, 100, 20);
        MainDesktopPane.add(createdto, javax.swing.JLayeredPane.DEFAULT_LAYER);

        RefreshBtn.setFont(new java.awt.Font("Verdana", 1, 12));
        RefreshBtn.setText("Refresh");
        RefreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshBtnActionPerformed(evt);
            }
        });
        RefreshBtn.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                focusGainedForBtnsWithBorder(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                focusLostForBtnsWithBorder(evt);
            }
        });
        RefreshBtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                keyReleasedForButtons(evt);
            }
        });
        RefreshBtn.setBounds(10, 107, 110, 25);
        MainDesktopPane.add(RefreshBtn, javax.swing.JLayeredPane.DEFAULT_LAYER);

        firstSeparator.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(201, 232, 252), new java.awt.Color(88, 109, 148)));
        firstSeparator.setBounds(10, 103, 1120, 2);
        MainDesktopPane.add(firstSeparator, javax.swing.JLayeredPane.DEFAULT_LAYER);

        createdToDatePickerBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ISRetail/images/small/datepicker.jpg"))); // NOI18N
        createdToDatePickerBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(88, 109, 148), new java.awt.Color(88, 109, 148)));
        createdToDatePickerBtn.setBorderPainted(false);
        createdToDatePickerBtn.setContentAreaFilled(false);
        createdToDatePickerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                datePickerButtonAction(evt);
            }
        });
        createdToDatePickerBtn.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                focusGainedForButtons(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                focusLostForButtonsWithNoBorder(evt);
            }
        });
        createdToDatePickerBtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                keyReleasedForButtons(evt);
            }
        });
        createdToDatePickerBtn.setBounds(530, 20, 20, 20);
        MainDesktopPane.add(createdToDatePickerBtn, javax.swing.JLayeredPane.DEFAULT_LAYER);

        createdFrmDatePickerBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ISRetail/images/small/datepicker.jpg"))); // NOI18N
        createdFrmDatePickerBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(88, 109, 148), new java.awt.Color(88, 109, 148)));
        createdFrmDatePickerBtn.setBorderPainted(false);
        createdFrmDatePickerBtn.setContentAreaFilled(false);
        createdFrmDatePickerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                datePickerButtonAction(evt);
            }
        });
        createdFrmDatePickerBtn.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                focusGainedForButtons(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                focusLostForButtonsWithNoBorder(evt);
            }
        });
        createdFrmDatePickerBtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                keyReleasedForButtons(evt);
            }
        });
        createdFrmDatePickerBtn.setBounds(250, 20, 21, 20);
        MainDesktopPane.add(createdFrmDatePickerBtn, javax.swing.JLayeredPane.DEFAULT_LAYER);

        createdToDatePicker.setDate(createdto);
        createdToDatePicker.setBounds(330, 40, 220, 190);
        MainDesktopPane.add(createdToDatePicker, javax.swing.JLayeredPane.POPUP_LAYER);

        createdFrmDatePicker.setDate(createdFrom);
        createdFrmDatePicker.setBounds(50, 40, 219, 190);
        MainDesktopPane.add(createdFrmDatePicker, javax.swing.JLayeredPane.POPUP_LAYER);

        ISRStatusLabel.setFont(new java.awt.Font("Verdana", 1, 12));
        ISRStatusLabel.setText("ISR Status");
        ISRStatusLabel.setBounds(290, 50, 130, 16);
        MainDesktopPane.add(ISRStatusLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        ISRStatusCombo.setFont(new java.awt.Font("Verdana", 1, 12));
        ISRStatusCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Completed", "Waiting", "Not Completed" }));
        ISRStatusCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ISRStatusComboActionPerformed(evt);
            }
        });
        ISRStatusCombo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ISRStatusComboFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ISRStatusComboFocusLost(evt);
            }
        });
        ISRStatusCombo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ISRStatusComboKeyReleased(evt);
            }
        });
        ISRStatusCombo.setBounds(430, 50, 120, 20);
        MainDesktopPane.add(ISRStatusCombo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        TransLabel.setFont(new java.awt.Font("Verdana", 1, 12));
        TransLabel.setText("Transaction");
        TransLabel.setBounds(20, 80, 110, 16);
        MainDesktopPane.add(TransLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        TransCombo.setFont(new java.awt.Font("Verdana", 1, 12));
        TransCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Customer", "Inquiry", "Sales Order", "Service Order", "Quotation", "Advance Receipt", "Billing", "Quick Billing", "Sale Order Cancel", "Advance Reversal", "Invoice Cancel", "Acknowledgement", "Sales Return", "Cash Payout" }));
        TransCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TransComboActionPerformed(evt);
            }
        });
        TransCombo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TransComboFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                TransComboFocusLost(evt);
            }
        });
        TransCombo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TransComboKeyReleased(evt);
            }
        });
        TransCombo.setBounds(150, 80, 120, 20);
        MainDesktopPane.add(TransCombo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(MainDesktopPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1192, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(223, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(MainDesktopPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 649, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
        private void createdFromfocusGainedForTextFields(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_createdFromfocusGainedForTextFields
            // TODO add your handling code here:
            try {
                if (evt != null) {
                    if ( evt.getComponent() != null) {
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
                        if (Validations.isFieldNotEmpty(createdFrom.getText())) {
                            if (createdFrom.getText().equalsIgnoreCase("DD/MM/YYYY")) {
                                createdFrom.setText("");
                            } else if (createdFrom.getText().length() > 0) {
                                performSearchForFilters();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
            
}//GEN-LAST:event_createdFromfocusGainedForTextFields

        private void createdFromfocusLostForTextFields(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_createdFromfocusLostForTextFields
            // TODO add your handling code here:
            Date curDate, checkDate;
            JTextField tf;
            String formattedDate;
            try {
                if (evt != null) {
                    if ( evt.getComponent() != null) {
                        try {
                            tf = (JTextField) evt.getComponent();
                            tf.setBackground(originalColor);
                        } catch (Exception e) {
                        }
                        curDate = ConvertDate.getStringToDate(ServerConsole.businessDate, "dd/MM/yyyy");
                        if (createdFrom.getText().length() > 0) {
                            if (!createdFrom.getText().trim().equals("DD/MM/YYYY")) {
                                formattedDate=Validations.validateAndSetDate(createdFrom.getText());
                                if (formattedDate!=null) {
                                    createdFrom.setText(formattedDate);
                                    checkDate = ConvertDate.getStringToDate(createdFrom.getText(), "dd/MM/yyyy");
                                    if (checkDate.after(curDate)) {
                                        JOptionPane.showMessageDialog(this, "From date should be less than or equal to current date! ", "Error Message", JOptionPane.ERROR_MESSAGE);
                                        createdFrom.setText("");
                                        createdFrom.requestFocus();
                                    } else if (checkFrmAndToDates(createdFrom, createdto)) {
                                        performSearchForFilters();
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(this, "Enter 'From date' in 'DD/MM/YYYY' or 'DDMMYYYY' format!", "Error Message", JOptionPane.ERROR_MESSAGE);
                                    createdFrom.setText("");
                                    createdFrom.requestFocus();
                                    performSearchForFilters();
                                }
                            }
                        }else{
                        createdFrom.setText("DD/MM/YYYY");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                curDate = null;
                checkDate = null;
                formattedDate=null;
            }
}//GEN-LAST:event_createdFromfocusLostForTextFields

        private void keyReleasedForSearch(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyReleasedForSearch
            // TODO add your handling code here:
            performSearchForFilters();
            if (evt != null) {
                invokeShortKeys(evt);
            }
}//GEN-LAST:event_keyReleasedForSearch

        private void createdtofocusGainedForTextFields(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_createdtofocusGainedForTextFields
            // TODO add your handling code here:
            try {
                if (evt != null) {
                    if ( evt.getComponent() != null) {
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

                        if (Validations.isFieldNotEmpty(createdto.getText())) {
                            if (createdto.getText() != null) {
                                if (createdto.getText().equalsIgnoreCase("DD/MM/YYYY")) {
                                    createdto.setText("");
                                } else if (createdto.getText().length() > 0) {
                                    performSearchForFilters();
                                }
                            }
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
}//GEN-LAST:event_createdtofocusGainedForTextFields

        private void createdtofocusLostForTextFields(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_createdtofocusLostForTextFields
            // TODO add your handling code here:
            Date curDate, checkDate;
            JTextField tf;
            String formattedDate;
            try {
                if (evt != null) {
                    if ( evt.getComponent() != null) {
                        try {
                            tf = (JTextField) evt.getComponent();
                            tf.setBackground(originalColor);
                        } catch (Exception e) {
                        }
                        curDate = ConvertDate.getStringToDate(ServerConsole.businessDate, "dd/MM/yyyy");
                        if (createdto.getText().length() > 0) {
                            if (!createdto.getText().trim().equals("DD/MM/YYYY")) {
                                 formattedDate=Validations.validateAndSetDate(createdto.getText());
                                if (formattedDate!=null) {
                                    createdto.setText(formattedDate);
                                    checkDate = ConvertDate.getStringToDate(createdto.getText(), "dd/MM/yyyy");
                                    if (checkDate.after(curDate)) {
                                        JOptionPane.showMessageDialog(this, "To date should be less than or equal to current date! ", "Error Message", JOptionPane.ERROR_MESSAGE);
                                        createdto.setText("");
                                        createdto.requestFocus();
                                    } else if (!Validations.isFieldNotEmpty(createdFrom.getText())) {
                                        JOptionPane.showMessageDialog(this, "Enter From date!", "Error Message", JOptionPane.ERROR_MESSAGE);
                                    } else if (createdFrom.getText().contains("D") || createdFrom.getText().contains("M") || createdFrom.getText().contains("Y")) {
                                        JOptionPane.showMessageDialog(this, "Enter From date!", "Error Message", JOptionPane.ERROR_MESSAGE);
                                    } else if (checkFrmAndToDates(createdFrom, createdto)) {
                                        performSearchForFilters();
                                    }

                                } else {
                                    JOptionPane.showMessageDialog(this, "Enter 'To date' in 'DD/MM/YYYY' or 'DDMMYYYY' format ! ", "Error", JOptionPane.ERROR_MESSAGE);
                                    createdto.setText("");
                                    createdto.requestFocus();
                                    performSearchForFilters();
                                }
                            }
                        }else{
                        createdto.setText("DD/MM/YYYY");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                curDate = null;
                checkDate = null;
                formattedDate=null;
            }
}//GEN-LAST:event_createdtofocusLostForTextFields
public static void setfromtodate(String currentdate)
{
   if(createdFrom!=null&&createdto!=null)
   {
    createdFrom.setText(currentdate);
    createdto.setText(currentdate);
   }
}        
        
        private void RefreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshBtnActionPerformed
            // TODO add your handling code here: 
            selectAllCheckBox.setSelected(false);
            performSearchForFilters();
}//GEN-LAST:event_RefreshBtnActionPerformed

        private void datePickerButtonAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_datePickerButtonAction
            // TODO add your handling code here:
            try {
                if (evt != null) {
                    if (evt.getSource() != null) {
                        if (evt.getSource().equals(createdFrmDatePickerBtn)) {
                            createdFrmDatePicker.setVisible(true);
                            createdFrmDatePicker.requestFocus();
                        } else if (evt.getSource().equals(createdToDatePickerBtn)) {
                            createdToDatePicker.setVisible(true);
                            createdToDatePicker.requestFocus();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
}//GEN-LAST:event_datePickerButtonAction

        private void focusGainedForButtons(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_focusGainedForButtons
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
}//GEN-LAST:event_focusGainedForButtons

        private void focusLostForButtonsWithNoBorder(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_focusLostForButtonsWithNoBorder
            // TODO add your handling code here:
            try {
                if (evt != null) {
                    if (!evt.isTemporary() && evt.getComponent() != null) {
                        JButton btn = (JButton) evt.getComponent();
                        btn.setBorderPainted(false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
            
}//GEN-LAST:event_focusLostForButtonsWithNoBorder

        private void StatusComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StatusComboActionPerformed
            // TODO add your handling code here:
            performSearchForFilters();
        }//GEN-LAST:event_StatusComboActionPerformed

        private void StatusComboFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_StatusComboFocusGained
            // TODO add your handling code here:
            if (evt != null) {
                if (evt.getComponent() != null) {
                    StatusCombo.setBackground(selectedColor);
                }
            }
        }//GEN-LAST:event_StatusComboFocusGained

        private void StatusComboFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_StatusComboFocusLost
            // TODO add your handling code here:
            if (evt != null) {
                if (!evt.isTemporary() && evt.getComponent() != null) {
                    StatusCombo.setBackground(originalColor);
                }
            }
        }//GEN-LAST:event_StatusComboFocusLost

        private void StatusComboKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusComboKeyReleased
            // TODO add your handling code here:
            invokeShortKeys(evt);
        }//GEN-LAST:event_StatusComboKeyReleased

        private void keyReleasedForButtons(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyReleasedForButtons
            // TODO add your handling code here:
            invokeShortKeys(evt);
        }//GEN-LAST:event_keyReleasedForButtons

        private void focusGainedForBtnsWithBorder(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_focusGainedForBtnsWithBorder
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
        }//GEN-LAST:event_focusGainedForBtnsWithBorder

        private void focusLostForBtnsWithBorder(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_focusLostForBtnsWithBorder
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
        }//GEN-LAST:event_focusLostForBtnsWithBorder

        private void stagingTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stagingTableKeyReleased
            // TODO add your handling code here:

            int selectedRow = stagingTable.getSelectedRow();
            int selectedCol = stagingTable.getSelectedColumn();
            if (selectedRow > -1) {
                makeCheckSelectCancelCheckBoxDisabled(selectedRow);
                if (selectedCol == 10) {
                    makeReinitiateButtonEnabled();
                }
            }
            invokeShortKeys(evt);
        }//GEN-LAST:event_stagingTableKeyReleased

        private void stagingTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stagingTableMouseClicked
            // TODO add your handling code here:
            int selectedRow = stagingTable.getSelectedRow();
            int selectedCol = stagingTable.getSelectedColumn();
            if (selectedRow > -1) {
                makeCheckSelectCancelCheckBoxDisabled(selectedRow);
                if (selectedCol == 10) {
                    makeReinitiateButtonEnabled();
                }
            }
        }//GEN-LAST:event_stagingTableMouseClicked

        private void ReinitiateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReinitiateButtonActionPerformed
            // TODO add your handling code here:
            Object chk;
            Boolean chkWarSel;
            PosStagingDO posStagingDO;
            String messageID, sNo;
            Connection con;
            MsdeConnection msdeConn;
            try {
                posStagingDO = new PosStagingDO();
                msdeConn = new MsdeConnection();
                con = msdeConn.createConnection();
                int res = 0;
                for (int row = 0; row < stagingTable.getRowCount(); row++) {
                    chk = stagingTable.getValueAt(row, 10);
                    chkWarSel = (Boolean) chk;
                    if (chkWarSel) {
                        sNo = String.valueOf(stagingTable.getValueAt(row, 0));
                        if (sNoMessageIdList != null) {
                            messageID = sNoMessageIdList.getProperty(sNo);
                            res = posStagingDO.updateStagingTableToReinitiateManually(con, messageID);
                        }
                    }
                }
                performSearchForFilters();
                ReinitiateButton.setEnabled(false);
                selectAllCheckBox.setSelected(false);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                chk = null;
                chkWarSel = null;
                posStagingDO = null;
                messageID = null;
                sNo = null;
                msdeConn = null;
                con = null;
            }
}//GEN-LAST:event_ReinitiateButtonActionPerformed

        private void ReinitiateCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReinitiateCheckBoxActionPerformed
            // TODO add your handling code here:
            try {
                int selectedRow = stagingTable.getSelectedRow();
                int selectedCol = stagingTable.getSelectedColumn();
                if (selectedRow > -1 && selectedCol == 10) {
                    makeReinitiateButtonEnabled();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }//GEN-LAST:event_ReinitiateCheckBoxActionPerformed

        private void ReinitiateCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ReinitiateCheckBoxItemStateChanged
            // TODO add your handling code here:
            int selectedRow = stagingTable.getSelectedRow();
            int selectedCol = stagingTable.getSelectedColumn();
            if (selectedRow > -1) {
                makeCheckSelectCancelCheckBoxDisabled(selectedRow);
                if (selectedCol == 10) {
                    makeReinitiateButtonEnabled();
                }
            }
        }//GEN-LAST:event_ReinitiateCheckBoxItemStateChanged

        private void ReinitiateCheckBoxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ReinitiateCheckBoxKeyReleased
            // TODO add your handling code here:
            if (evt != null) {
                int selectedRow = stagingTable.getSelectedRow();
                int selectedCol = stagingTable.getSelectedColumn();
                if (selectedRow > -1) {
                    makeCheckSelectCancelCheckBoxDisabled(selectedRow);
                    if (selectedCol == 10) {
                        makeReinitiateButtonEnabled();
                    }
                }
                invokeShortKeys(evt);
            }
        }//GEN-LAST:event_ReinitiateCheckBoxKeyReleased

        private void ReinitiateCheckBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReinitiateCheckBoxMouseClicked
            // TODO add your handling code here:
            int selectedRow = stagingTable.getSelectedRow();
            int selectedCol = stagingTable.getSelectedColumn();
            if (selectedRow > -1) {
                makeCheckSelectCancelCheckBoxDisabled(selectedRow);
                if (selectedCol == 10) {
                    makeReinitiateButtonEnabled();
                }
            }
        }//GEN-LAST:event_ReinitiateCheckBoxMouseClicked

        private void selectAllCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_selectAllCheckBoxItemStateChanged
            // TODO add your handling code here:
            try {
                // added to refresh the table data before select all cancelled
                performSearchForFilters();
                ReinitiateButton.setEnabled(false);
                for (int row = 0; row < stagingTable.getRowCount(); row++) {
                    if (stagingTable.getValueAt(row, 8) != null) {
                        if (String.valueOf(stagingTable.getValueAt(row, 8)).trim().equalsIgnoreCase("Cancelled")) {
                            if (selectAllCheckBox.isSelected()) {
                                stagingTable.setValueAt(true, row, 10);
                                ReinitiateButton.setEnabled(true);
                            } else {
                                stagingTable.setValueAt(false, row, 10);
                            }

                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
                    
        }//GEN-LAST:event_selectAllCheckBoxItemStateChanged

        private void MainDesktopPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MainDesktopPaneMouseClicked
            // TODO add your handling code here:
             if (createdFrmDatePicker.isVisible()) {
                        createdFrmDatePicker.setVisible(false);
                    } else if (createdToDatePicker.isVisible()) {
                        createdToDatePicker.setVisible(false);
                    }
        }//GEN-LAST:event_MainDesktopPaneMouseClicked

        private void ISRStatusComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ISRStatusComboActionPerformed
            // TODO add your handling code here:
             performSearchForFilters();
}//GEN-LAST:event_ISRStatusComboActionPerformed

        private void ISRStatusComboFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ISRStatusComboFocusGained
            // TODO add your handling code here:
             if (evt != null) {
                if ( evt.getComponent() != null) {
                    ISRStatusCombo.setBackground(selectedColor);
                }
            }
}//GEN-LAST:event_ISRStatusComboFocusGained

        private void ISRStatusComboFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ISRStatusComboFocusLost
            // TODO add your handling code here:
             if (evt != null) {
                if (!evt.isTemporary() && evt.getComponent() != null) {
                    ISRStatusCombo.setBackground(originalColor);
                }
            }
}//GEN-LAST:event_ISRStatusComboFocusLost

        private void ISRStatusComboKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ISRStatusComboKeyReleased
            // TODO add your handling code here:
                        invokeShortKeys(evt);
}//GEN-LAST:event_ISRStatusComboKeyReleased

        private void TransComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TransComboActionPerformed
            // TODO add your handling code here:
             performSearchForFilters();
}//GEN-LAST:event_TransComboActionPerformed

        private void TransComboFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TransComboFocusGained
            // TODO add your handling code here:
            if (evt != null) {
                if ( evt.getComponent() != null) {
                    ISRStatusCombo.setBackground(selectedColor);
                }
            }
}//GEN-LAST:event_TransComboFocusGained

        private void TransComboFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TransComboFocusLost
            // TODO add your handling code here:
                if (evt != null) {
                if (!evt.isTemporary() && evt.getComponent() != null) {
                    ISRStatusCombo.setBackground(originalColor);
                }
            }
}//GEN-LAST:event_TransComboFocusLost

        private void TransComboKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TransComboKeyReleased
            // TODO add your handling code here:
              invokeShortKeys(evt);
}//GEN-LAST:event_TransComboKeyReleased

        private void MainDesktopPaneMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MainDesktopPaneMouseMoved
            // TODO add your handling code here:
            
        evt.setSource(serverConsole.MainTabbedPane);
        serverConsole.myMainTabbedPaneMouseMoved(evt);
        }//GEN-LAST:event_MainDesktopPaneMouseMoved

        private void stagingTableMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stagingTableMouseMoved
            // TODO add your handling code here:
            
             evt.setSource(serverConsole.MainTabbedPane);
        evt.translatePoint(stagingScrollPane.getX(), stagingScrollPane.getY());
        serverConsole.myMainTabbedPaneMouseMoved(evt);
        }//GEN-LAST:event_stagingTableMouseMoved

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CreatedFrmLabel;
    private javax.swing.JComboBox ISRStatusCombo;
    private javax.swing.JLabel ISRStatusLabel;
    private javax.swing.JDesktopPane MainDesktopPane;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JButton RefreshBtn;
    private javax.swing.JButton ReinitiateButton;
    private javax.swing.JCheckBox ReinitiateCheckBox;
    private javax.swing.JComboBox StatusCombo;
    private javax.swing.JLabel StatusLabel;
    private javax.swing.JComboBox TransCombo;
    private javax.swing.JLabel TransLabel;
    private ISRetail.components.DatePicker12 createdFrmDatePicker;
    private javax.swing.JButton createdFrmDatePickerBtn;
    private static javax.swing.JFormattedTextField createdFrom;
    private ISRetail.components.DatePicker12 createdToDatePicker;
    private javax.swing.JButton createdToDatePickerBtn;
    private static javax.swing.JFormattedTextField createdto;
    private javax.swing.JLabel crteatedToLabel;
    private javax.swing.JSeparator firstSeparator;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JCheckBox selectAllCheckBox;
    private javax.swing.JScrollPane stagingScrollPane;
    private javax.swing.JTable stagingTable;
    // End of variables declaration//GEN-END:variables
}

