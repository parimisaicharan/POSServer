/*
 * ArticleMaster.java
 *
 * Created on June 11, 2008, 6:28 PM
 */
package ISRetail.stock;

import ISRetail.article.*;
import ISRetail.Helpers.ConvertDate;
import ISRetail.components.CustomCellRenderer;
import ISRetail.components.CustomNonEditableCellRenderer;
import ISRetail.components.JTextFieldFixed;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.serverconsole.ServerConsole;
import ISRetail.utility.validations.Validations;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 *
 * @author  Administrator
 */
public class StockMaster extends javax.swing.JPanel {

    Thread thread = null;
    Timer timer;
    final static int interval = 1000;
    int i = 0, authentication_attempts = 0;
    StockMasterThread stockMasterThread = null;
    JTextArea statusArea;
    private final Color selectedColor = new Color(255, 255, 150);//Color of Textfields when focu gained
    private final Color originalColor = new Color(255, 255, 255);//Color of Textfields when focu lost
    private final Color lightYellowColor = new Color(255, 253, 226);
    CustomNonEditableCellRenderer rightAlignedCellRendr;
    CustomCellRenderer leftAlignedCellRendr;
    private ArrayList<String> merchcatList = null;
    String missedMateriallist = "";    
    MsdeConnection msdeconn1 = null;
    Connection con1 = null;

    /** Creates new form ArticleMaster */
    public StockMaster() {
        initComponents();
         msdeconn1 = new MsdeConnection();
        con1 = msdeconn1.createConnection();
        merchcatList = getMerchCategoryList(con1,"BlockStockDownload_merchcat");
        //***********stock MAster********/
        MaterialListTable.getTableHeader().setBackground(new Color(88, 109, 148));
        MaterialListTable.getTableHeader().setForeground(new Color(201, 232, 252));
        MaterialListTable.getTableHeader().setFont(new java.awt.Font("Verdana", 1, 12));
        DownloadStockActionButton.setEnabled(false);
        rightAlignedCellRendr = new CustomNonEditableCellRenderer();// to format the table cells, for which right alignment is required
        leftAlignedCellRendr = new CustomCellRenderer();// to format the table cells, for which left alignment is required
        rightAlignedCellRendr.setHorizontalAlignment(JLabel.RIGHT);
        MaterialListTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(matListTable_materialCode));
        MaterialListTable.getColumnModel().getColumn(0).setCellRenderer(rightAlignedCellRendr);
        MaterialListTable.getColumnModel().getColumn(1).setCellRenderer(leftAlignedCellRendr);
        MaterialListTable.getColumnModel().getColumn(1).getCellEditor().addCellEditorListener(new CellEditorListener() {

            public void editingStopped(ChangeEvent e) {
                validateMatListMaterialOnEditing();
            }

            public void editingCanceled(ChangeEvent e) {
                validateMatListMaterialOnEditing();
            }
        });


        /************** */
        statusArea = new JTextArea();
        AuthFrame.setVisible(false);
        timer = new Timer(interval, new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                i = i + 1;
                jProgressBar1.setValue(i);
                if (thread != null) {
                    if (thread.getState() == Thread.State.TERMINATED) {
                        Toolkit.getDefaultToolkit().beep();
                        timer.stop();
                        if (stockMasterThread.getI() == 0) {
                            String str = "<html>" + "<font color=\"#FF0000\">" + "<b>" +
                                    "DOWNLOAD FAILED." + "</b>" + "</font>" + "</html>";
                            jLabel1.setText(str);
                            jProgressBar1.setValue(50);
                            ok.setEnabled(true);
                            ok.requestFocus();
                        } else if (stockMasterThread.getI() == 1) {
                            String str = "<html>" + "<font color=\"#008000\">" + "<b>" +
                                    "SUCCESSFULLY DOWNLOADED." + "</b>" + "</font>" + "</html>";
                            jLabel1.setText(str);
                            jProgressBar1.setValue(50);
                            ok.setEnabled(true);
                            ok.requestFocus();
                        } else if (stockMasterThread.getI() == 5) {
                            String str = "<html>" + "<font color=\"#FF0000\">" + "<b>" +
                                    "NO VALUES ARE DOWNLOADED." + "</b>" + "</font>" + "</html>";
                            jLabel1.setText(str);
                            jProgressBar1.setValue(50);
                            ok.setEnabled(true);
                            ok.requestFocus();
                        } else if (stockMasterThread.getI() == 2) {
                            String str = "<html>" + "<font color=\"#FF0000\">" + "<b>" +
                                    "Check Your Network for Internet Connection!" + "</b>" + "</font>" + "</html>";
                            jLabel1.setText(str);
                            jProgressBar1.setValue(50);
                            ok.setEnabled(true);
                            ok.requestFocus();
                        } else if (stockMasterThread.getI() == 3) {
                            String str = "<html>" + "<font color=\"#FF0000\">" + "<b>" +
                                    "Incorrect Server Address \nContact Administrator to Fix the Problem!" + "</b>" + "</font>" + "</html>";
                            jLabel1.setText(str);
                            jProgressBar1.setValue(50);
                            ok.setEnabled(true);
                            ok.requestFocus();
                        } else if (stockMasterThread.getI() == 4) {
                            String str = "<html>" + "<font color=\"#FF0000\">" + "<b>" +
                                    "Unknown Error\nContact Administrator to Fix the Problem!" + "</b>" + "</font>" + "</html>";
                            jLabel1.setText(str);
                            jProgressBar1.setValue(50);
                            ok.setEnabled(true);
                            ok.requestFocus();
                        }
                        DownloadStockActionButton.setEnabled(true);
                        //MaterialListTable.getModel()).setrowcount(0)
                    }
                }

            }
        });
    }

    public static void addActionToButton(JButton button, final JTabbedPane jTabbedPane1, final StockMaster articlemaster) {
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

    public void validateMatListMaterialOnEditing() {
        int selectedRow = MaterialListTable.getSelectedRow();
        int selectedCol = MaterialListTable.getSelectedColumn();
        try {
            validateMaterialfortheRow(selectedRow, selectedCol, true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        makeDownloadButtonEnabled();
    }

    public boolean validateMaterialfortheRow(int selectedRow, int selectedCol, boolean OnEdit) {        
        String materialId = "";
        Connection con;
        MsdeConnection msdeConn = null;
        boolean valid = true;
        try {
            System.err.println(materialId);
            stopCelleditingOfTableEditingCell(MaterialListTable);
            msdeConn = new MsdeConnection();
            con = msdeConn.createConnection();
            if (selectedCol == 1 && selectedRow > -1) {
                materialId = String.valueOf(MaterialListTable.getValueAt(selectedRow, selectedCol));                
                System.err.println(materialId);
                if (!Validations.isFieldNotEmpty(materialId)) {
                    if (!OnEdit) {
                        valid = false;
                        JOptionPane.showMessageDialog(this, "Enter Material code For row-" + (selectedRow + 1), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (!Validations.isFieldNotEmpty(ArticleDO.getMerchCatForMaterial(con, materialId))) {                    
                    valid = false;                    
                    JOptionPane.showMessageDialog(this, "Invalid Material !", "Error", JOptionPane.ERROR_MESSAGE);
                }

                if (valid) {
                    if (Validations.isFieldNotEmpty(merchcatList) && merchcatList.size() > 0) {
                        if (Validations.presentInArrayList(merchcatList, ArticleDO.getMerchCatForMaterial(con, materialId))) {
                            valid = false;
                            JOptionPane.showMessageDialog(this, "Customer article stock can't able to download", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                if (!valid) {

                    MaterialListTable.setValueAt("", selectedRow, 1);
                    MaterialListTable.editCellAt(selectedRow, 1);
                    MaterialListTable.requestFocus();
                    MaterialListTable.setRowSelectionInterval(selectedRow, selectedRow);
                    MaterialListTable.setColumnSelectionInterval(1, 1);
                }                
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            materialId = null;
            con = null;
            msdeConn = null;
        }
        return valid;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        matListTable_materialCode = new JTextFieldFixed(18);
        StockMasterMainPane = new javax.swing.JDesktopPane();
        MaterialListTablePane = new javax.swing.JScrollPane();
        MaterialListTable = new javax.swing.JTable();
        addrow = new javax.swing.JButton();
        deleterow = new javax.swing.JButton();
        bordeLabelForTable = new javax.swing.JLabel();
        stockStatusPane = new javax.swing.JScrollPane();
        StockStatusArea = new javax.swing.JTextArea();
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
        headstatLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        bordeLabelForTable1 = new javax.swing.JLabel();
        DownloadStockActionButton = new javax.swing.JButton();

        matListTable_materialCode.setText("");
        matListTable_materialCode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                matListTable_materialCodefocusGained_soPrevTable_TextFields(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                matListTable_materialCodefocusLost_soPrevTable_TextFields(evt);
            }
        });
        matListTable_materialCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                matListTable_materialCodeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                matListTable_materialCodeKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                matListTable_materialCodeKeyTyped(evt);
            }
        });

        setBackground(new java.awt.Color(153, 204, 255));
        setName("Article Master"); // NOI18N

        StockMasterMainPane.setBackground(new java.awt.Color(153, 204, 255));
        StockMasterMainPane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(88, 109, 148), 1, true), "Stock Master Download", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 12), new java.awt.Color(0, 0, 0)));

        MaterialListTablePane.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                MaterialListTablePaneKeyReleased(evt);
            }
        });

        MaterialListTable.setBackground(new java.awt.Color(235, 231, 231));
        MaterialListTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(88, 109, 148), 1, true));
        MaterialListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sl. No.", "Material ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        MaterialListTable.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                MaterialListTableFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                MaterialListTableFocusLost(evt);
            }
        });
        MaterialListTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                MaterialListTableKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                MaterialListTableKeyTyped(evt);
            }
        });
        MaterialListTablePane.setViewportView(MaterialListTable);
        MaterialListTable.getColumnModel().getColumn(0).setMinWidth(50);
        MaterialListTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        MaterialListTable.getColumnModel().getColumn(0).setMaxWidth(50);

        MaterialListTablePane.setBounds(40, 58, 450, 200);
        StockMasterMainPane.add(MaterialListTablePane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        addrow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ISRetail/images/small/add.png"))); // NOI18N
        addrow.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(88, 109, 148), new java.awt.Color(88, 109, 148)));
        addrow.setBorderPainted(false);
        addrow.setContentAreaFilled(false);
        addrow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addrowActionPerformed(evt);
            }
        });
        addrow.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                addrowchangeBordorOnFocus(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                addrowchangeBorderOnFocusLost(evt);
            }
        });
        addrow.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                addrowKeyReleased(evt);
            }
        });
        addrow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addrowMouseClicked(evt);
            }
        });
        addrow.setBounds(430, 25, 30, 30);
        StockMasterMainPane.add(addrow, javax.swing.JLayeredPane.DEFAULT_LAYER);

        deleterow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ISRetail/images/small/delete.png"))); // NOI18N
        deleterow.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(88, 109, 148), new java.awt.Color(88, 109, 148)));
        deleterow.setBorderPainted(false);
        deleterow.setContentAreaFilled(false);
        deleterow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleterowActionPerformed(evt);
            }
        });
        deleterow.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                deleterowchangeBordorOnFocus(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                deleterowchangeBorderOnFocusLost(evt);
            }
        });
        deleterow.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                deleterowKeyReleased(evt);
            }
        });
        deleterow.setBounds(460, 25, 30, 30);
        StockMasterMainPane.add(deleterow, javax.swing.JLayeredPane.DEFAULT_LAYER);

        bordeLabelForTable.setBackground(new java.awt.Color(237, 247, 252));
        bordeLabelForTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        bordeLabelForTable.setOpaque(true);
        bordeLabelForTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                bordeLabelForTableKeyReleased(evt);
            }
        });
        bordeLabelForTable.setBounds(35, 54, 460, 210);
        StockMasterMainPane.add(bordeLabelForTable, javax.swing.JLayeredPane.DEFAULT_LAYER);

        StockStatusArea.setBackground(new java.awt.Color(237, 247, 252));
        StockStatusArea.setColumns(20);
        StockStatusArea.setEditable(false);
        StockStatusArea.setRows(5);
        StockStatusArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        StockStatusArea.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                StockStatusAreaFocusGained(evt);
            }
        });
        stockStatusPane.setViewportView(StockStatusArea);

        stockStatusPane.setBounds(38, 383, 453, 130);
        StockMasterMainPane.add(stockStatusPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        downloadingFrame.setTitle("Downloading Stock...");

        jProgressBar1.setStringPainted(true);
        jProgressBar1.setBounds(20, 20, 350, 18);
        jDesktopPane2.add(jProgressBar1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("jLabel1");
        jLabel1.setBounds(20, 40, 340, 20);
        jDesktopPane2.add(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        ok.setFont(new java.awt.Font("Verdana", 1, 12));
        ok.setText("OK");
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });
        ok.setBounds(160, 70, 80, 23);
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
        StockMasterMainPane.add(downloadingFrame, javax.swing.JLayeredPane.POPUP_LAYER);

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
            .add(jDesktopPane9, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
        );

        AuthFrame.setBounds(110, 160, 280, 120);
        StockMasterMainPane.add(AuthFrame, javax.swing.JLayeredPane.POPUP_LAYER);

        headstatLabel.setFont(new java.awt.Font("Verdana", 1, 12));
        headstatLabel.setForeground(new java.awt.Color(88, 109, 148));
        headstatLabel.setText("Downloading Status");
        headstatLabel.setBounds(35, 360, 390, 14);
        StockMasterMainPane.add(headstatLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel4.setForeground(new java.awt.Color(88, 109, 148));
        jLabel4.setText("Enter Material Codes to Pull the ISR Active Stock");
        jLabel4.setBounds(40, 35, 360, 14);
        StockMasterMainPane.add(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        bordeLabelForTable1.setBackground(new java.awt.Color(237, 247, 252));
        bordeLabelForTable1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        bordeLabelForTable1.setOpaque(true);
        bordeLabelForTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                bordeLabelForTable1KeyReleased(evt);
            }
        });
        bordeLabelForTable1.setBounds(35, 380, 460, 137);
        StockMasterMainPane.add(bordeLabelForTable1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        DownloadStockActionButton.setFont(new java.awt.Font("Verdana", 1, 12));
        DownloadStockActionButton.setText("Download");
        DownloadStockActionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DownloadStockActionButtonActionPerformed(evt);
            }
        });
        DownloadStockActionButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                DownloadStockActionButtonKeyReleased(evt);
            }
        });
        DownloadStockActionButton.setBounds(40, 315, 220, 30);
        StockMasterMainPane.add(DownloadStockActionButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(StockMasterMainPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 539, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(734, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(StockMasterMainPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 577, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
        // TODO add your handling code here:
        if(Validations.isFieldNotEmpty(missedMateriallist)){
            
        JOptionPane.showMessageDialog(this, "Article "+missedMateriallist.toString()+ " cannot be downloaded. Try after sometime.", "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        downloadingFrame.setVisible(false);
        DownloadStockActionButton.setEnabled(false);
        ((DefaultTableModel) MaterialListTable.getModel()).setRowCount(0);
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
                                    performDownloadStcokMaster();
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
                                        authentication_attempts = 0;
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "Password has expired", "Error", JOptionPane.ERROR_MESSAGE);
                                AuthPassword.setText("");
                                AuthFrame.setVisible(false);
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

    public void performDownloadStcokMaster() {

        ArrayList<MaterialListPOJO> materialLists = null,materialList = null ;
        MaterialListPOJO matPojo = null;
        StockMasterService stockMasterService;
        String matID = null;
        StockMasterDO stockMaster = null;
        String materialcode = null;
        missedMateriallist = "";
        try {
            
            StockStatusArea.setText("");
            DownloadStockActionButton.setEnabled(false);
            MsdeConnection msdeConnection = new MsdeConnection();
            Connection connection = msdeConnection.createConnection();

            if (connection != null) {
                
                stockMasterService = new StockMasterService(StockStatusArea);

                materialLists = new ArrayList<MaterialListPOJO>();
                
                for (int count = 0; count < MaterialListTable.getRowCount(); count++) {
                    matID = String.valueOf(MaterialListTable.getValueAt(count, 1));
                    if (matID != null && matID.trim().length() > 0) {
                        matPojo = new MaterialListPOJO();
                        matPojo.setSlNo(count);
                        matPojo.setMaterialCode(matID.toUpperCase());
                        materialLists.add(matPojo);
                    }
                }
                if (materialLists != null && materialLists.size() > 0) {
                    stockMaster = new StockMasterDO();
                    materialList  = new ArrayList<MaterialListPOJO>();
                    materialList = stockMaster.getNCMaterialList(connection,materialLists);
                    
                    if (materialList != null && materialList.size() > 0) {
                        
                        for (MaterialListPOJO mlpojo : materialLists) {
                            materialcode = mlpojo.getMaterialCode();
                            System.out.println("----- "+materialList.contains(mlpojo));
                            if(!(materialList.contains(mlpojo))) {
                                if(missedMateriallist == ""){
                                missedMateriallist = materialcode;
                                }else{
                                    missedMateriallist = missedMateriallist+","+ materialcode;
                                }
                            }
                        }
                        
                        
                        String str = "<html>" + "<font color=\"#008000\">" + "<b>"
                                + "Downloading is in process......." + "</b>" + "</font>" + "</html>";
                        jLabel1.setText(str);
                        downloadingFrame.setVisible(true);
                        ok.setEnabled(false);
                        jProgressBar1.setValue(0);
                        timer.start();
                        stockMasterThread = new StockMasterThread(stockMasterService, ServerConsole.siteID, materialList, false, StockStatusArea);
                        thread = new Thread(stockMasterThread);
                        thread.setDaemon(true);
                        thread.start();
                    } else {
                        ((DefaultTableModel) MaterialListTable.getModel()).setRowCount(0);
                        JOptionPane.showMessageDialog(this, "Stock cannot be downloaded for this Articles. Try after sometime.", "Error Message", JOptionPane.ERROR_MESSAGE);
                        
                    }
                }

            } else {
                JOptionPane.showMessageDialog(this, "Unable to Connect to POS Database!", "Error Message", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Download Failed.");
        } finally {
            materialLists = null;
            matID = null;
            matPojo = null;
            stockMasterService = null;
        }

    }

        private void addrowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addrowActionPerformed
            boolean valid = true;
            for (int i = 0; i < MaterialListTable.getRowCount(); i++) {
                valid = validateMaterialfortheRow(i, 1, false);//check mat code
                if (!valid) {
                    break;
                }

            }
            if (valid) {


                addRow();

            }
        }//GEN-LAST:event_addrowActionPerformed

    public void addRow() {
        int tabRowCount = 0;
        Vector vect_addRowToTable;
        try {
            tabRowCount = MaterialListTable.getRowCount();
            if (tabRowCount <= 0) {
                tabRowCount = 0;
            }
            vect_addRowToTable = new Vector();
            int slNo = ++tabRowCount;
            vect_addRowToTable.addElement(slNo);//1 st slNo
            vect_addRowToTable.addElement("");//item code
            ((DefaultTableModel) MaterialListTable.getModel()).addRow(vect_addRowToTable);
            tabRowCount = MaterialListTable.getRowCount();
            ((DefaultTableModel) MaterialListTable.getModel()).fireTableRowsInserted(tabRowCount, tabRowCount);
            if (MaterialListTable.getRowCount() > 0) {
                int lastRow = MaterialListTable.getRowCount() - 1;
                MaterialListTable.requestFocus();
                MaterialListTable.setRowSelectionInterval(lastRow, lastRow);
                MaterialListTable.setColumnSelectionInterval(1, 1);
                if (MaterialListTable.getRowCount() > 12) {
                    MaterialListTable.scrollRectToVisible(new Rectangle(0, MaterialListTable.getRowHeight() * MaterialListTable.getRowCount() - 12, MaterialListTable.getWidth(), MaterialListTable.getHeight()));
                }

            }
            makeDownloadButtonEnabled();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            vect_addRowToTable = null;
        }

    }
        private void addrowchangeBordorOnFocus(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_addrowchangeBordorOnFocus
            // TODO add your handling code here:


            addrow.setBorderPainted(true);

        }//GEN-LAST:event_addrowchangeBordorOnFocus

        private void addrowchangeBorderOnFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_addrowchangeBorderOnFocusLost
            // TODO add your handling code here:
            addrow.setBorderPainted(false);

        }//GEN-LAST:event_addrowchangeBorderOnFocusLost

        private void addrowKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addrowKeyReleased
            invokeShortKeys(evt);
        }//GEN-LAST:event_addrowKeyReleased

        private void addrowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addrowMouseClicked
            // TODO add your handling code here:
        }//GEN-LAST:event_addrowMouseClicked

        private void deleterowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleterowActionPerformed
            // TODO add your handling code here:
            try {
                int slnolocal = MaterialListTable.getSelectedRow();
                stopCelleditingOfTableEditingCell(MaterialListTable);
                if (slnolocal > -1) {
                    ((DefaultTableModel) MaterialListTable.getModel()).removeRow(slnolocal);
                    int slNo = 0;
                    for (int i = 0; i <
                            MaterialListTable.getRowCount(); i++) {
                        slNo = i + 1;
                        MaterialListTable.setValueAt(slNo, i, 0);
                    }

                }
                makeDownloadButtonEnabled();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }//GEN-LAST:event_deleterowActionPerformed

        private void deleterowchangeBordorOnFocus(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_deleterowchangeBordorOnFocus
            // TODO add your handling code here:
            deleterow.setBorderPainted(true);

        }//GEN-LAST:event_deleterowchangeBordorOnFocus

        private void deleterowchangeBorderOnFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_deleterowchangeBorderOnFocusLost
            // TODO add your handling code here:

            deleterow.setBorderPainted(false);

        }//GEN-LAST:event_deleterowchangeBorderOnFocusLost

        private void matListTable_materialCodefocusGained_soPrevTable_TextFields(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_matListTable_materialCodefocusGained_soPrevTable_TextFields
            // TODO add your handling code here:
            JTextField tf;
            if (evt != null) {
                if (!evt.isTemporary()) {
                    try {
                        tf = (JTextField) evt.getComponent();
                        if (tf.isEditable()) {
                            tf.setBackground(selectedColor);
                        }
                    } catch (Exception e) {
                    } finally {
                        tf = null;
                    }
                }
            }
}//GEN-LAST:event_matListTable_materialCodefocusGained_soPrevTable_TextFields

        private void matListTable_materialCodefocusLost_soPrevTable_TextFields(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_matListTable_materialCodefocusLost_soPrevTable_TextFields
            // TODO add your handling code here:
            if (evt != null) {
                if (!evt.isTemporary()) {
                    JTextField tf;
                    try {
                        tf = (JTextField) evt.getComponent();
                        if (tf.isEditable()) {
                            tf.setBackground(originalColor);
                        }
                    } catch (Exception e) {
                    } finally {
                        tf = null;
                    }
                }
            }
}//GEN-LAST:event_matListTable_materialCodefocusLost_soPrevTable_TextFields

        private void matListTable_materialCodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_matListTable_materialCodeKeyReleased
            // TODO add your handling code here:
            invokeShortKeys(evt);
}//GEN-LAST:event_matListTable_materialCodeKeyReleased

        private void deleterowKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_deleterowKeyReleased
            // TODO add your handling code here:
            invokeShortKeys(evt);
        }//GEN-LAST:event_deleterowKeyReleased

        private void bordeLabelForTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bordeLabelForTableKeyReleased
            // TODO add your handling code here:
            invokeShortKeys(evt);
        }//GEN-LAST:event_bordeLabelForTableKeyReleased

        private void MaterialListTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MaterialListTableKeyReleased
            // TODO add your handling code here:

            invokeShortKeys(evt);
        }//GEN-LAST:event_MaterialListTableKeyReleased

        private void MaterialListTablePaneKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MaterialListTablePaneKeyReleased
            // TODO add your handling code here:
            invokeShortKeys(evt);
        }//GEN-LAST:event_MaterialListTablePaneKeyReleased

        private void StockStatusAreaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_StockStatusAreaFocusGained
            // TODO add your handling code here:
        }//GEN-LAST:event_StockStatusAreaFocusGained

        private void MaterialListTableFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_MaterialListTableFocusGained
            // TODO add your handling code here:

            MaterialListTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148), 1));
            MaterialListTable.getParent().setBackground(lightYellowColor);

        }//GEN-LAST:event_MaterialListTableFocusGained

        private void MaterialListTableFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_MaterialListTableFocusLost
            // TODO add your handling code here:
            MaterialListTable.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            MaterialListTable.getParent().setBackground(new Color(235, 231, 231));


        }//GEN-LAST:event_MaterialListTableFocusLost

        private void MaterialListTableKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MaterialListTableKeyTyped
            // TODO add your handling code here:
            if (MaterialListTable.getSelectedRow() > -1 && MaterialListTable.getSelectedColumn() == 1) {
                evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
            }
        }//GEN-LAST:event_MaterialListTableKeyTyped

        private void matListTable_materialCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_matListTable_materialCodeKeyPressed
            // TODO add your handling code here:
        }//GEN-LAST:event_matListTable_materialCodeKeyPressed

        private void matListTable_materialCodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_matListTable_materialCodeKeyTyped
            // TODO add your handling code here:
            if (MaterialListTable.getSelectedRow() > -1 && MaterialListTable.getSelectedColumn() == 1) {
                evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
            }
        }//GEN-LAST:event_matListTable_materialCodeKeyTyped

        private void bordeLabelForTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bordeLabelForTable1KeyReleased
            // TODO add your handling code here:
        }//GEN-LAST:event_bordeLabelForTable1KeyReleased

        private void DownloadStockActionButtonKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DownloadStockActionButtonKeyReleased
            // TODO add your handling code here:
            invokeShortKeys(evt);
}//GEN-LAST:event_DownloadStockActionButtonKeyReleased

        private void DownloadStockActionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DownloadStockActionButtonActionPerformed
            // TODO add your handling code here:
            stopCelleditingOfTableEditingCell(MaterialListTable);
            if (makeDownloadButtonEnabled()) {
                AuthFrame.setVisible(true);
                AuthPassword.requestFocus();
            } else {
                JOptionPane.showMessageDialog(this, "No materials are entered in the table to pull stock !", "Wraning", JOptionPane.WARNING_MESSAGE);
                MaterialListTable.requestFocus();
            }
}//GEN-LAST:event_DownloadStockActionButtonActionPerformed
    public boolean makeDownloadButtonEnabled() {
        boolean canDownload = false;
        try {

            DownloadStockActionButton.setEnabled(false);
            if (MaterialListTable.getRowCount() > 0) {
                for (int cnt = 0; cnt < MaterialListTable.getRowCount(); cnt++) {
                    if (MaterialListTable.getValueAt(cnt, 1) != null && String.valueOf(MaterialListTable.getValueAt(cnt, 1)).trim().length() > 0) {
                        DownloadStockActionButton.setEnabled(true);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return canDownload;
    }

    public void invokeShortKeys(KeyEvent evt) {
        try {
            if (evt != null) {

                if (evt.getKeyCode() == KeyEvent.VK_F11) {
                    if (addrow.isVisible()) {
                        if (addrow.isEnabled()) {
                            addrowActionPerformed(null);
                        }

                    }
                } else if (evt.getKeyCode() == KeyEvent.VK_F12) {
                    if (deleterow.isEnabled()) {
                        deleterowActionPerformed(null);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public static void stopCelleditingOfTableEditingCell(JTable table) {
        try {
            int edRow = table.getEditingRow();
            int edCol = table.getEditingColumn();
            if (edRow > -1 && edCol > -1) {
                if (table.isEditing()) {
                    if (table.getCellEditor(edRow, edCol) != null) {
                        table.getCellEditor(edRow, edCol).stopCellEditing();
                    }

                }
            }
        } catch (Exception e) {
        }
    }

    public ArrayList<String> getMerchCategoryList(Connection con, String val) {
        Statement statement1 = null;
        ResultSet rs = null;
        ArrayList<String> merchcategories = null;
        try {
            statement1 = con.createStatement();
            rs = statement1.executeQuery("select configval from dbo.tbl_pos_configparam where configkey like '" + val + "%'");
            merchcategories = new ArrayList<String>();
            while (rs.next()) {
                if (Validations.isFieldNotEmpty(rs.getString("configval"))) {
                    merchcategories.add(rs.getString("configval"));
                }
            }
            System.out.println("getMerchCategoryList---------> array list size =   " + merchcategories.size());
            return merchcategories;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            statement1 = null;
            rs = null;
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JInternalFrame AuthFrame;
    private javax.swing.JPasswordField AuthPassword;
    private javax.swing.JButton DownloadStockActionButton;
    private javax.swing.JTable MaterialListTable;
    private javax.swing.JScrollPane MaterialListTablePane;
    private javax.swing.JDesktopPane StockMasterMainPane;
    private javax.swing.JTextArea StockStatusArea;
    private javax.swing.JButton addrow;
    private javax.swing.JButton auth_OKbutton;
    private javax.swing.JButton auth_caneclbutton;
    private javax.swing.JLabel bordeLabelForTable;
    private javax.swing.JLabel bordeLabelForTable1;
    private javax.swing.JButton deleterow;
    private javax.swing.JInternalFrame downloadingFrame;
    private javax.swing.JLabel headstatLabel;
    private javax.swing.JDesktopPane jDesktopPane2;
    private javax.swing.JDesktopPane jDesktopPane9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JTextField matListTable_materialCode;
    private javax.swing.JButton ok;
    private javax.swing.JScrollPane stockStatusPane;
    // End of variables declaration//GEN-END:variables
}
