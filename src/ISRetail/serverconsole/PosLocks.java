/*
 * PosStagingTableStatus.java
 *
 * Created on December 4, 2008, 12:53 PM
 */
package ISRetail.serverconsole;

import ISRetail.Helpers.ConvertDate;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.rowlock.RowLockDO;
import ISRetail.rowlock.RowLockPOJO;
import ISRetail.utility.validations.Validations;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author  enteg
 */
public class PosLocks extends javax.swing.JPanel {

    /** Creates new form PosStagingTableStatus */
    private final Color selectedColor = new Color(255, 255, 150);//Color of Textfields when focu gained
    private final Color originalColor = new Color(255, 255, 255);//Color of Textfields when focu lost
    public PosLocks() {
        try {
            initComponents();
            UnlockButton.setEnabled(false);
            LockTable.getTableHeader().setBackground(new Color(88, 109, 148));
            LockTable.getTableHeader().setForeground(new Color(201, 232, 252));
            LockTable.getTableHeader().setFont(new java.awt.Font("Verdana", 1, 12));
            LockTable.getColumnModel().getColumn(8).setCellEditor(new DefaultCellEditor(unlockCheckBox));//Chk box to reinitiate
            LockTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

                public void valueChanged(ListSelectionEvent e) {
                    int selectedRow = LockTable.getSelectedRow();

                    int selectedCol = LockTable.getSelectedColumn();
                    if (selectedRow > -1) {
                        if (selectedCol == 8) {
                            makeReinitiateButtonEnabled();
                        }
                    }
                }
            });

            performSearchForFilters();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }

    public void displayLockReport(String searchstatement, Connection con) {
        RowLockDO rowLockDO;
        ArrayList<RowLockPOJO> lockList;
        Iterator itr;
        Vector vectToAddToTable;
        try {
            rowLockDO = new RowLockDO();
            lockList = rowLockDO.getRowLockList(con, searchstatement);
            ((DefaultTableModel) LockTable.getModel()).setRowCount(0);
            if (lockList != null) {
                itr = lockList.iterator();
                if (itr != null) {
                    while (itr.hasNext()) {
                        RowLockPOJO lockPOJO = (RowLockPOJO) itr.next();
                        vectToAddToTable = new Vector();
                        vectToAddToTable.add(lockPOJO.getSno());
                        vectToAddToTable.add(lockPOJO.getTransactionname());
                        vectToAddToTable.add(lockPOJO.getTransactionid1());
                        vectToAddToTable.add(lockPOJO.getTid1_storecode());
                        vectToAddToTable.add(lockPOJO.getTid1_fiscalyear());
                        vectToAddToTable.add(lockPOJO.getCreatedby());
                        vectToAddToTable.add(ConvertDate.getNumericToDate(lockPOJO.getCreateddate()));
                        vectToAddToTable.add(ConvertDate.getformattedTimeFromTime(lockPOJO.getCreatedtime()));
                        vectToAddToTable.add(false);
                        ((DefaultTableModel) LockTable.getModel()).addRow(vectToAddToTable);
                        int tabRowCount = LockTable.getRowCount();
                        ((DefaultTableModel) LockTable.getModel()).fireTableRowsInserted(tabRowCount, tabRowCount);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rowLockDO = null;
            lockList = null;
            itr = null;
            vectToAddToTable = null;
        }
    }

    public void performSearchForFilters() {
        StringBuffer searchStatement;
        SiteMasterDO siteMasterDO;
        MsdeConnection msdeConn;
        Connection con;
        try {
            siteMasterDO = new SiteMasterDO();
            msdeConn = new MsdeConnection();
            con = msdeConn.createConnection();
            int frm_Date = 0, to_Date = 0;
            boolean searchStatementPresent = false, betweenDates = false;
            searchStatement = new StringBuffer("select sno, transactionname,transactionid1,tid1_storecode,tid1_fiscalyear,createdby,createddate,createdtime from tbl_pos_rowlock ");

            displayLockReport(searchStatement.toString(), con);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            searchStatement = null;
            siteMasterDO = null;
        }

    }

    public void invokeShortKeys(KeyEvent evt) {
        try {
            if (evt != null) {
                if (evt.getKeyCode() == KeyEvent.VK_F5) {
                    RefreshBtnActionPerformed(null);
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
                    JOptionPane.showMessageDialog(this, "'From Date' Should not be after 'To Date'", "Error", JOptionPane.ERROR_MESSAGE);
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
            for (int row = 0; row < LockTable.getRowCount(); row++) {
                if (LockTable.getSelectedColumn() == 8) {
                    chk = LockTable.getValueAt(row, 8);
                    try {
                        chkWarSel = (Boolean) chk;
                        anyOneSelected = chkWarSel.booleanValue();
                        if (anyOneSelected) {
                            UnlockButton.setEnabled(true);
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!anyOneSelected) {
                UnlockButton.setEnabled(false);
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

        unlockCheckBox = new javax.swing.JCheckBox();
        MainDesktopPane = new javax.swing.JDesktopPane();
        MainPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        LockTable = new javax.swing.JTable();
        UnlockButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        selectAllCheckBox = new javax.swing.JCheckBox();
        firstSeparator = new javax.swing.JSeparator();
        RefreshBtn = new javax.swing.JButton();

        unlockCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        unlockCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                unlockCheckBoxItemStateChanged(evt);
            }
        });
        unlockCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unlockCheckBoxActionPerformed(evt);
            }
        });
        unlockCheckBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                unlockCheckBoxKeyReleased(evt);
            }
        });
        unlockCheckBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                unlockCheckBoxMouseClicked(evt);
            }
        });

        setBackground(new java.awt.Color(153, 204, 255));

        MainDesktopPane.setBackground(new java.awt.Color(153, 204, 255));
        MainDesktopPane.setFont(new java.awt.Font("Verdana", 1, 10));

        MainPanel.setBackground(new java.awt.Color(237, 247, 252));
        MainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(88, 109, 148), new java.awt.Color(201, 232, 252)));

        LockTable.setBackground(new java.awt.Color(237, 247, 252));
        LockTable.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(201, 232, 252), new java.awt.Color(88, 109, 148)));
        LockTable.setFont(new java.awt.Font("Verdana", 1, 12));
        LockTable.setForeground(new java.awt.Color(88, 109, 148));
        LockTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.No.", "Transaction Name", "Transaction ID", "Store Code", "Fiscal Year", "Locked By", "Locked Date", "Locked Time", "Select to Unlock"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        LockTable.setGridColor(new java.awt.Color(88, 109, 148));
        LockTable.getTableHeader().setReorderingAllowed(false);
        LockTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                LockTableKeyReleased(evt);
            }
        });
        LockTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LockTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(LockTable);
        LockTable.getColumnModel().getColumn(0).setPreferredWidth(20);
        LockTable.getColumnModel().getColumn(1).setPreferredWidth(185);
        LockTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        LockTable.getColumnModel().getColumn(6).setPreferredWidth(120);
        LockTable.getColumnModel().getColumn(7).setPreferredWidth(120);
        LockTable.getColumnModel().getColumn(8).setPreferredWidth(150);

        UnlockButton.setFont(new java.awt.Font("Verdana", 1, 12));
        UnlockButton.setText("Unlock");
        UnlockButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UnlockButtonActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(206, 218, 238));
        jLabel1.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel1.setForeground(new java.awt.Color(88, 109, 148));
        jLabel1.setText(" Locked Objects");
        jLabel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(88, 109, 148), 2, true));
        jLabel1.setMaximumSize(new java.awt.Dimension(127, 18));
        jLabel1.setMinimumSize(new java.awt.Dimension(127, 18));
        jLabel1.setOpaque(true);

        selectAllCheckBox.setFont(new java.awt.Font("Verdana", 1, 10));
        selectAllCheckBox.setText("Select All ");
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
                .add(MainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1094, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(MainPanelLayout.createSequentialGroup()
                        .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 949, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(MainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(selectAllCheckBox, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(UnlockButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        MainPanelLayout.setVerticalGroup(
            MainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, MainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(MainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(MainPanelLayout.createSequentialGroup()
                        .add(selectAllCheckBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(UnlockButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 528, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        MainPanel.setBounds(6, 40, 1130, 600);
        MainDesktopPane.add(MainPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        firstSeparator.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(201, 232, 252), new java.awt.Color(88, 109, 148)));
        firstSeparator.setBounds(10, 15, 1120, 3);
        MainDesktopPane.add(firstSeparator, javax.swing.JLayeredPane.DEFAULT_LAYER);

        RefreshBtn.setFont(new java.awt.Font("Verdana", 1, 12));
        RefreshBtn.setText("Refresh");
        RefreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshBtnActionPerformed(evt);
            }
        });
        RefreshBtn.setBounds(10, 20, 90, 20);
        MainDesktopPane.add(RefreshBtn, javax.swing.JLayeredPane.DEFAULT_LAYER);

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
    private void LockTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LockTableKeyReleased
        // TODO add your handling code here:

        int selectedRow = LockTable.getSelectedRow();
        int selectedCol = LockTable.getSelectedColumn();
        if (selectedRow > -1) {
            if (selectedCol == 8) {
                makeReinitiateButtonEnabled();
            }
        }
        invokeShortKeys(evt);
}//GEN-LAST:event_LockTableKeyReleased

    private void LockTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LockTableMouseClicked
        // TODO add your handling code here:
        int selectedRow = LockTable.getSelectedRow();
        int selectedCol = LockTable.getSelectedColumn();
        if (selectedRow > -1) {
            if (selectedCol == 8) {
                makeReinitiateButtonEnabled();
            }
        }
}//GEN-LAST:event_LockTableMouseClicked

        private void UnlockButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UnlockButtonActionPerformed
            // TODO add your handling code here:
            Object chk;
            Boolean chkWarSel;
            RowLockDO rowLockDO;
            int sNo;
            Connection con;
            MsdeConnection msdeConn;
            try {
                rowLockDO = new RowLockDO();
                msdeConn = new MsdeConnection();
                con = msdeConn.createConnection();
                int res = 0;
                for (int row = 0; row < LockTable.getRowCount(); row++) {
                    chk = LockTable.getValueAt(row, 8);
                    chkWarSel = (Boolean) chk;
                    if (chkWarSel) {
                        sNo = Integer.parseInt(String.valueOf(LockTable.getValueAt(row, 0)));
                        res = rowLockDO.deletefRowLockEntry(con, sNo);
                    }
                }
                performSearchForFilters();
                UnlockButton.setEnabled(false);
                performSearchForFilters();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                chk = null;
                chkWarSel = null;
                rowLockDO = null;
                msdeConn = null;
                con = null;
            }
}//GEN-LAST:event_UnlockButtonActionPerformed

        private void unlockCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unlockCheckBoxActionPerformed
            // TODO add your handling code here:
            try {
                int selectedRow = LockTable.getSelectedRow();
                int selectedCol = LockTable.getSelectedColumn();
                if (selectedRow > -1 && selectedCol == 8) {
                    makeReinitiateButtonEnabled();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
}//GEN-LAST:event_unlockCheckBoxActionPerformed

        private void unlockCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_unlockCheckBoxItemStateChanged
            // TODO add your handling code here:
            int selectedRow = LockTable.getSelectedRow();
            int selectedCol = LockTable.getSelectedColumn();
            if (selectedRow > -1) {
                if (selectedCol == 8) {
                    makeReinitiateButtonEnabled();
                }
            }
}//GEN-LAST:event_unlockCheckBoxItemStateChanged

        private void unlockCheckBoxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_unlockCheckBoxKeyReleased
            // TODO add your handling code here:
            if (evt != null) {
                int selectedRow = LockTable.getSelectedRow();
                int selectedCol = LockTable.getSelectedColumn();
                if (selectedRow > -1) {
                    if (selectedCol == 8) {
                        makeReinitiateButtonEnabled();
                    }
                }
                invokeShortKeys(evt);
            }
}//GEN-LAST:event_unlockCheckBoxKeyReleased

        private void unlockCheckBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_unlockCheckBoxMouseClicked
            // TODO add your handling code here:
            int selectedRow = LockTable.getSelectedRow();
            int selectedCol = LockTable.getSelectedColumn();
            if (selectedRow > -1) {
                if (selectedCol == 8) {
                    makeReinitiateButtonEnabled();
                }
            }
}//GEN-LAST:event_unlockCheckBoxMouseClicked

        private void selectAllCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_selectAllCheckBoxItemStateChanged
            // TODO add your handling code here:
            try {
                UnlockButton.setEnabled(false);
                for (int row = 0; row < LockTable.getRowCount(); row++) {
                    if (LockTable.getValueAt(row, 6) != null) {
                        if (selectAllCheckBox.isSelected()) {
                            LockTable.setValueAt(true, row, 8);
                            UnlockButton.setEnabled(true);
                        } else {
                            LockTable.setValueAt(false, row, 8);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
                    
        }//GEN-LAST:event_selectAllCheckBoxItemStateChanged

        private void RefreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshBtnActionPerformed
            // TODO add your handling code here:
            performSearchForFilters();
        }//GEN-LAST:event_RefreshBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable LockTable;
    private javax.swing.JDesktopPane MainDesktopPane;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JButton RefreshBtn;
    private javax.swing.JButton UnlockButton;
    private javax.swing.JSeparator firstSeparator;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JCheckBox selectAllCheckBox;
    private javax.swing.JCheckBox unlockCheckBox;
    // End of variables declaration//GEN-END:variables
}

