/*
 * NewJPanel.java
 *
 * Created on September 25, 2008, 4:42 PM
 */

package ISRetail.plantdetails;

import ISRetail.Helpers.ConvertDate;
import ISRetail.msdeconnection.MsdeConnection;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author  Administrator
 */
public class SiteMaster extends javax.swing.JPanel {
   
    /** Creates new form NewJPanel */
    public SiteMaster() {
        initComponents();
        
        try
        {
            MsdeConnection connection = new  MsdeConnection();
            Connection con =connection.createConnection();
            SiteMasterDO siteMasterDO = new SiteMasterDO();            
            setSiteDate(siteMasterDO.getSite(con));
            setForceReleaseTable(siteMasterDO.getSiteForceRelease(con));
            setCreditSaleTable(siteMasterDO.getSiteCreditSaleReference(con));
            setHolidayDetails(siteMasterDO.getSiteHolidayCalender(con));
            setToolTipToComponents();
        }catch(Exception e){
           e.printStackTrace(); 
        }
    }
    
    /*************************************    METHOD TO SET SITE DATA        *************************************/
    public void setSiteDate(SiteMasterPOJO siteMasterPOJO1){
        if(siteMasterPOJO1 != null){         
        if(siteMasterPOJO1.getSitedescription() != null)
        siteDescription.setText(siteMasterPOJO1.getSitedescription());
        siteDescription.setCaretPosition(0);
        if(siteMasterPOJO1.getFiscalyear() != null)
        fiscalYear.setText(siteMasterPOJO1.getFiscalyear());
        fiscalYear.setCaretPosition(0);
        if(siteMasterPOJO1.getNum_rangelogic() != null)
        numRangeLogic.setText(siteMasterPOJO1.getNum_rangelogic());
        numRangeLogic.setCaretPosition(0);
        if(siteMasterPOJO1.getCstno() != null)
        cstNo.setText(siteMasterPOJO1.getCstno());
        cstNo.setCaretPosition(0);
        if(siteMasterPOJO1.getVat_tin_no() != null)
        vatno.setText(siteMasterPOJO1.getVat_tin_no());
        vatno.setCaretPosition(0);
        if(siteMasterPOJO1.getPan_no() != null)
        panNo.setText(siteMasterPOJO1.getPan_no());
        panNo.setCaretPosition(0);
        if(siteMasterPOJO1.getZone() != null)
        zone.setText(siteMasterPOJO1.getZone());
        zone.setCaretPosition(0);
        if(siteMasterPOJO1.getCorresponding_cfa_id() != null)
        cfaId.setText(siteMasterPOJO1.getCorresponding_cfa_id());
        cfaId.setCaretPosition(0);
        if(siteMasterPOJO1.getCfa_contact_telephone() != null)
        cfaTelephoneNo.setText(siteMasterPOJO1.getCfa_contact_telephone());
        cfaTelephoneNo.setCaretPosition(0);
        if(siteMasterPOJO1.getCfa_contact_email() != null)
        cfaEmail.setText(siteMasterPOJO1.getCfa_contact_email());
        cfaEmail.setCaretPosition(0);
        if(siteMasterPOJO1.getPostingdate() != 0)
        postingDate.setText(ConvertDate.getNumericToDate(siteMasterPOJO1.getPostingdate()));
        postingDate.setCaretPosition(0);
        if(siteMasterPOJO1.getQuotationvalidityperiod() != null)
        quotationValidityPeriod.setText(siteMasterPOJO1.getQuotationvalidityperiod());
        quotationValidityPeriod.setCaretPosition(0);
        if(siteMasterPOJO1.getQuotationvalidityindicator() != null)
        quotationValidityIndicator.setText(siteMasterPOJO1.getQuotationvalidityindicator());
        quotationValidityPeriod.setCaretPosition(0);
        if(siteMasterPOJO1.getCreditnotevalidityduration() != null)
        creditNoteValidityPeriod.setText(siteMasterPOJO1.getCreditnotevalidityduration());
        creditNoteValidityPeriod.setCaretPosition(0);
        if(siteMasterPOJO1.getCreditnotevalidityindicator() != null)
        creditNoteValidityIndicator.setText(siteMasterPOJO1.getCreditnotevalidityindicator());
        creditNoteValidityIndicator.setCaretPosition(0);
        if(siteMasterPOJO1.getMinadvancepercentage() != null)
        minAdvancePercentage.setText(siteMasterPOJO1.getMinadvancepercentage());
        minAdvancePercentage.setCaretPosition(0);
        if(siteMasterPOJO1.getCorresponding_regoffice_name() != null)
        regdOffName.setText(siteMasterPOJO1.getCorresponding_regoffice_name());
        regdOffName.setCaretPosition(0);
        if(siteMasterPOJO1.getRegdoffaddress() != null)
        regdOffAddress.setText(siteMasterPOJO1.getRegdoffaddress());
        regdOffAddress.setCaretPosition(0);
        if(siteMasterPOJO1.getRegoff_contact_telephone() != null)
        regdOffTelephone.setText(siteMasterPOJO1.getRegoff_contact_telephone());
        regdOffTelephone.setCaretPosition(0);
        if(siteMasterPOJO1.getRegoff_contact_email() != null)
        regdOffEmail.setText(siteMasterPOJO1.getRegoff_contact_email());
        regdOffEmail.setCaretPosition(0);
        }
    }
    
     /*************************************    METHOD TO SET SITE FORCE RELEASE        *************************************/
    public void setForceReleaseTable(ArrayList<SiteForceReleasePOJO> siteForceReleasePOJOs){
        if(siteForceReleasePOJOs != null){
            Iterator iterator = siteForceReleasePOJOs.iterator();
            while(iterator.hasNext()){
                SiteForceReleasePOJO siteForceReleasePOJO = (SiteForceReleasePOJO) iterator.next();
                Vector v =new Vector();
                v.addElement(siteForceReleasePOJO.getSlno());
                v.addElement(ConvertDate.getNumericToDate(siteForceReleasePOJO.getFromdate()));
                v.addElement(ConvertDate.getNumericToDate(siteForceReleasePOJO.getTodate()));
                v.addElement(siteForceReleasePOJO.getForcerelease_quantity());
                v.addElement(siteForceReleasePOJO.getCurrentcount());
                if(siteForceReleasePOJO.getRecordStatus()  != null){
                if(siteForceReleasePOJO.getRecordStatus().equalsIgnoreCase("X"))
                    v.addElement(true);
                else
                    v.addElement(false);
                }else{
                    v.addElement(false);
                }
                
                ((DefaultTableModel) forceReleaseTable.getModel()).addRow(v);
                int rowCount = forceReleaseTable.getRowCount();
                ((DefaultTableModel) forceReleaseTable.getModel()).fireTableRowsInserted(rowCount, rowCount);
            }
        }
    }
    
     /*************************************    METHOD TO SET SITE CREDIT SALE DETAILS        *************************************/
     public void setCreditSaleTable(ArrayList<SiteCreditSaleReference> siteCreditSaleReferences){
        if(siteCreditSaleReferences != null){
            Iterator iterator = siteCreditSaleReferences.iterator();
            while(iterator.hasNext()){
                SiteCreditSaleReference siteCreditSaleReference = (SiteCreditSaleReference) iterator.next();
                Vector v =new Vector();
                v.addElement(siteCreditSaleReference.getSlno());
                v.addElement(ConvertDate.getNumericToDate(siteCreditSaleReference.getFromdate()));
                v.addElement(ConvertDate.getNumericToDate(siteCreditSaleReference.getTodate()));
                v.addElement(siteCreditSaleReference.getSapcustomerno());
                v.addElement(siteCreditSaleReference.getInstitutionname());
                if(siteCreditSaleReference.getRecordStatus() != null){
                if(siteCreditSaleReference.getRecordStatus().equalsIgnoreCase("X"))
                    v.addElement(true);
                else
                    v.addElement(false);
                }else{
                    v.addElement(false);
                }
                
                ((DefaultTableModel) creditSaleTable.getModel()).addRow(v);
                int rowCount = creditSaleTable.getRowCount();
                ((DefaultTableModel) creditSaleTable.getModel()).fireTableRowsInserted(rowCount, rowCount);
            }
        }
    }
    
     
       /*************************************    METHOD TO SET SITE HOLIDAY DETAILS       *************************************/
     
     
     public void setHolidayDetails(ArrayList<SiteHolidayCalenderPOJO> siteHolidayCalenderPOJOs){
        if(siteHolidayCalenderPOJOs != null){
            Iterator iterator = siteHolidayCalenderPOJOs.iterator();
            while(iterator.hasNext()){
                SiteHolidayCalenderPOJO siteHolidayCalenderPOJO = (SiteHolidayCalenderPOJO) iterator.next();
                Vector v =new Vector();
                v.addElement(siteHolidayCalenderPOJO.getSlno());
                v.addElement(siteHolidayCalenderPOJO.getYear());
                v.addElement(ConvertDate.getNumericToDate(siteHolidayCalenderPOJO.getDate()));
                v.addElement(siteHolidayCalenderPOJO.getDescription());
               
                ((DefaultTableModel) holidayCalenderTable.getModel()).addRow(v);
                int rowCount = holidayCalenderTable.getRowCount();
                ((DefaultTableModel) holidayCalenderTable.getModel()).fireTableRowsInserted(rowCount, rowCount);
            }
        }
    }
    public void setToolTipToComponents(){
         try{
                siteDescription.setToolTipText(siteDescription.getText());
                numRangeLogic.setToolTipText(numRangeLogic.getText());
                regdOffName.setToolTipText(regdOffName.getText());
                fiscalYear.setToolTipText(fiscalYear.getText());
                cstNo.setToolTipText(cstNo.getText());
                vatno.setToolTipText(vatno.getText());
                panNo.setToolTipText(panNo.getText());
                zone.setToolTipText(zone.getText());
                cfaTelephoneNo.setToolTipText(cfaTelephoneNo.getText());
                cfaEmail.setToolTipText(cfaEmail.getText());
                postingDate.setToolTipText(postingDate.getText());
                quotationValidityPeriod.setToolTipText(quotationValidityPeriod.getText());
                quotationValidityIndicator.setToolTipText(quotationValidityIndicator.getText());
                creditNoteValidityIndicator.setToolTipText(creditNoteValidityIndicator.getText());
                creditNoteValidityPeriod.setToolTipText(creditNoteValidityPeriod.getText());
                minAdvancePercentage.setToolTipText(minAdvancePercentage.getText());
                regdOffTelephone.setToolTipText(regdOffTelephone.getText());
                regdOffEmail.setToolTipText(regdOffEmail.getText());
                regdOffAddress.setToolTipText(regdOffAddress.getText());
        }catch(Exception e){}
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jDesktopPane2 = new javax.swing.JDesktopPane();
        jLabel1 = new javax.swing.JLabel();
        numRangeLogic = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cstNo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        vatno = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        zone = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cfaId = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cfaTelephoneNo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cfaEmail = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        postingDate = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        quotationValidityIndicator = new javax.swing.JTextField();
        quotationValidityPeriod = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        creditNoteValidityPeriod = new javax.swing.JTextField();
        creditNoteValidityIndicator = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        minAdvancePercentage = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        regdOffName = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        regdOffAddress = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        regdOffTelephone = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        regdOffEmail = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        panNo = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        fiscalYear = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jDesktopPane3 = new javax.swing.JDesktopPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        forceReleaseTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jDesktopPane4 = new javax.swing.JDesktopPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        creditSaleTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jDesktopPane5 = new javax.swing.JDesktopPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        holidayCalenderTable = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        siteDescription = new javax.swing.JTextField();

        setBackground(new java.awt.Color(153, 204, 255));
        setName("SiteMaster"); // NOI18N

        jDesktopPane1.setBackground(new java.awt.Color(153, 204, 255));

        jTabbedPane1.setBackground(new java.awt.Color(153, 204, 255));
        jTabbedPane1.setFont(new java.awt.Font("Verdana", 1, 12));

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));

        jDesktopPane2.setBackground(new java.awt.Color(153, 204, 255));

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel1.setText("Number Range Logic");
        jLabel1.setBounds(10, 30, 170, 20);
        jDesktopPane2.add(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        numRangeLogic.setEditable(false);
        numRangeLogic.setFont(new java.awt.Font("Verdana", 1, 12));
        numRangeLogic.setToolTipText(numRangeLogic.getText());
        numRangeLogic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        numRangeLogic.setBounds(200, 30, 40, 18);
        jDesktopPane2.add(numRangeLogic, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel2.setText("CST No.");
        jLabel2.setBounds(10, 90, 170, 20);
        jDesktopPane2.add(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        cstNo.setEditable(false);
        cstNo.setFont(new java.awt.Font("Verdana", 1, 12));
        cstNo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        cstNo.setBounds(200, 90, 140, 18);
        jDesktopPane2.add(cstNo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel3.setText("VAT No.");
        jLabel3.setBounds(10, 120, 170, 20);
        jDesktopPane2.add(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        vatno.setEditable(false);
        vatno.setFont(new java.awt.Font("Verdana", 1, 12));
        vatno.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        vatno.setBounds(200, 120, 140, 18);
        jDesktopPane2.add(vatno, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel4.setText("Zone");
        jLabel4.setBounds(10, 180, 170, 20);
        jDesktopPane2.add(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        zone.setEditable(false);
        zone.setFont(new java.awt.Font("Verdana", 1, 12));
        zone.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        zone.setBounds(200, 180, 70, 18);
        jDesktopPane2.add(zone, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel5.setText("CFA ID");
        jLabel5.setBounds(10, 210, 170, 20);
        jDesktopPane2.add(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);

        cfaId.setEditable(false);
        cfaId.setFont(new java.awt.Font("Verdana", 1, 12));
        cfaId.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        cfaId.setBounds(200, 210, 140, 18);
        jDesktopPane2.add(cfaId, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel6.setText("CFA Telephone ");
        jLabel6.setBounds(10, 240, 170, 20);
        jDesktopPane2.add(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);

        cfaTelephoneNo.setEditable(false);
        cfaTelephoneNo.setFont(new java.awt.Font("Verdana", 1, 12));
        cfaTelephoneNo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        cfaTelephoneNo.setBounds(200, 240, 190, 18);
        jDesktopPane2.add(cfaTelephoneNo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel7.setText("CFA Email");
        jLabel7.setBounds(10, 270, 170, 20);
        jDesktopPane2.add(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);

        cfaEmail.setEditable(false);
        cfaEmail.setFont(new java.awt.Font("Verdana", 1, 12));
        cfaEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        cfaEmail.setBounds(200, 270, 190, 18);
        jDesktopPane2.add(cfaEmail, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel8.setText("Posting Date");
        jLabel8.setBounds(10, 300, 170, 20);
        jDesktopPane2.add(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);

        postingDate.setEditable(false);
        postingDate.setFont(new java.awt.Font("Verdana", 1, 12));
        postingDate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        postingDate.setBounds(200, 300, 90, 18);
        jDesktopPane2.add(postingDate, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel9.setText("Quotation Validity Period");
        jLabel9.setBounds(10, 330, 170, 20);
        jDesktopPane2.add(jLabel9, javax.swing.JLayeredPane.DEFAULT_LAYER);

        quotationValidityIndicator.setEditable(false);
        quotationValidityIndicator.setFont(new java.awt.Font("Verdana", 1, 12));
        quotationValidityIndicator.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        quotationValidityIndicator.setBounds(250, 330, 30, 18);
        jDesktopPane2.add(quotationValidityIndicator, javax.swing.JLayeredPane.DEFAULT_LAYER);

        quotationValidityPeriod.setEditable(false);
        quotationValidityPeriod.setFont(new java.awt.Font("Verdana", 1, 12));
        quotationValidityPeriod.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        quotationValidityPeriod.setBounds(200, 330, 50, 18);
        jDesktopPane2.add(quotationValidityPeriod, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel10.setText("CreditNote Validity Period");
        jLabel10.setBounds(10, 360, 210, 20);
        jDesktopPane2.add(jLabel10, javax.swing.JLayeredPane.DEFAULT_LAYER);

        creditNoteValidityPeriod.setEditable(false);
        creditNoteValidityPeriod.setFont(new java.awt.Font("Verdana", 1, 12));
        creditNoteValidityPeriod.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        creditNoteValidityPeriod.setBounds(200, 360, 50, 18);
        jDesktopPane2.add(creditNoteValidityPeriod, javax.swing.JLayeredPane.DEFAULT_LAYER);

        creditNoteValidityIndicator.setEditable(false);
        creditNoteValidityIndicator.setFont(new java.awt.Font("Verdana", 1, 12));
        creditNoteValidityIndicator.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        creditNoteValidityIndicator.setBounds(250, 360, 30, 18);
        jDesktopPane2.add(creditNoteValidityIndicator, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel11.setText("Min. Advance Percentage");
        jLabel11.setBounds(10, 390, 210, 20);
        jDesktopPane2.add(jLabel11, javax.swing.JLayeredPane.DEFAULT_LAYER);

        minAdvancePercentage.setEditable(false);
        minAdvancePercentage.setFont(new java.awt.Font("Verdana", 1, 12));
        minAdvancePercentage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        minAdvancePercentage.setBounds(200, 390, 50, 18);
        jDesktopPane2.add(minAdvancePercentage, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel12.setText("Reg. Office Name");
        jLabel12.setBounds(420, 30, 150, 20);
        jDesktopPane2.add(jLabel12, javax.swing.JLayeredPane.DEFAULT_LAYER);

        regdOffName.setEditable(false);
        regdOffName.setFont(new java.awt.Font("Verdana", 1, 12));
        regdOffName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        regdOffName.setBounds(580, 30, 190, 18);
        jDesktopPane2.add(regdOffName, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel13.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel13.setText("Regd. Office Address");
        jLabel13.setBounds(420, 120, 150, 20);
        jDesktopPane2.add(jLabel13, javax.swing.JLayeredPane.DEFAULT_LAYER);

        regdOffAddress.setBackground(new java.awt.Color(235, 235, 228));
        regdOffAddress.setColumns(20);
        regdOffAddress.setEditable(false);
        regdOffAddress.setFont(new java.awt.Font("Verdana", 1, 12));
        regdOffAddress.setRows(5);
        regdOffAddress.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        jScrollPane1.setViewportView(regdOffAddress);

        jScrollPane1.setBounds(580, 120, 190, 60);
        jDesktopPane2.add(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel14.setText("Reg. Office Telephone");
        jLabel14.setBounds(420, 60, 150, 20);
        jDesktopPane2.add(jLabel14, javax.swing.JLayeredPane.DEFAULT_LAYER);

        regdOffTelephone.setEditable(false);
        regdOffTelephone.setFont(new java.awt.Font("Verdana", 1, 12));
        regdOffTelephone.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        regdOffTelephone.setBounds(580, 60, 190, 18);
        jDesktopPane2.add(regdOffTelephone, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel15.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel15.setText("Reg. Office Email");
        jLabel15.setBounds(420, 90, 150, 20);
        jDesktopPane2.add(jLabel15, javax.swing.JLayeredPane.DEFAULT_LAYER);

        regdOffEmail.setEditable(false);
        regdOffEmail.setFont(new java.awt.Font("Verdana", 1, 12));
        regdOffEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        regdOffEmail.setBounds(580, 90, 190, 18);
        jDesktopPane2.add(regdOffEmail, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel16.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel16.setText("PAN No.");
        jLabel16.setBounds(10, 150, 170, 20);
        jDesktopPane2.add(jLabel16, javax.swing.JLayeredPane.DEFAULT_LAYER);

        panNo.setEditable(false);
        panNo.setFont(new java.awt.Font("Verdana", 1, 12));
        panNo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        panNo.setBounds(200, 150, 140, 18);
        jDesktopPane2.add(panNo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel17.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel17.setText("Fiscal Year");
        jLabel17.setBounds(10, 60, 170, 20);
        jDesktopPane2.add(jLabel17, javax.swing.JLayeredPane.DEFAULT_LAYER);

        fiscalYear.setEditable(false);
        fiscalYear.setFont(new java.awt.Font("Verdana", 1, 12));
        fiscalYear.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        fiscalYear.setBounds(200, 60, 70, 18);
        jDesktopPane2.add(fiscalYear, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jDesktopPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 805, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jDesktopPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Site Data", jPanel1);

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));

        jDesktopPane3.setBackground(new java.awt.Color(153, 204, 255));

        forceReleaseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Slno.", "From Date", "To Date", "Force Release Qty", "Current Count", "Deactive"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(forceReleaseTable);
        forceReleaseTable.getColumnModel().getColumn(0).setMinWidth(40);
        forceReleaseTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        forceReleaseTable.getColumnModel().getColumn(0).setMaxWidth(30);
        forceReleaseTable.getColumnModel().getColumn(1).setMinWidth(70);
        forceReleaseTable.getColumnModel().getColumn(1).setPreferredWidth(70);
        forceReleaseTable.getColumnModel().getColumn(1).setMaxWidth(60);
        forceReleaseTable.getColumnModel().getColumn(2).setMinWidth(100);
        forceReleaseTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        forceReleaseTable.getColumnModel().getColumn(2).setMaxWidth(100);
        forceReleaseTable.getColumnModel().getColumn(3).setMinWidth(80);
        forceReleaseTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        forceReleaseTable.getColumnModel().getColumn(4).setMinWidth(120);
        forceReleaseTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        forceReleaseTable.getColumnModel().getColumn(5).setMinWidth(50);
        forceReleaseTable.getColumnModel().getColumn(5).setPreferredWidth(50);
        forceReleaseTable.getColumnModel().getColumn(5).setMaxWidth(50);

        jScrollPane2.setBounds(20, 60, 720, 260);
        jDesktopPane3.add(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jDesktopPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 805, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jDesktopPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Force Release", jPanel2);

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));

        jDesktopPane4.setBackground(new java.awt.Color(153, 204, 255));

        creditSaleTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Slno.", "From Date", "To Date", "Institution ID", "Institution Name", "Deactive"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(creditSaleTable);
        creditSaleTable.getColumnModel().getColumn(0).setMinWidth(40);
        creditSaleTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        creditSaleTable.getColumnModel().getColumn(0).setMaxWidth(30);
        creditSaleTable.getColumnModel().getColumn(1).setMinWidth(70);
        creditSaleTable.getColumnModel().getColumn(1).setPreferredWidth(70);
        creditSaleTable.getColumnModel().getColumn(1).setMaxWidth(60);
        creditSaleTable.getColumnModel().getColumn(2).setMinWidth(100);
        creditSaleTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        creditSaleTable.getColumnModel().getColumn(2).setMaxWidth(100);
        creditSaleTable.getColumnModel().getColumn(3).setMinWidth(80);
        creditSaleTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        creditSaleTable.getColumnModel().getColumn(4).setMinWidth(120);
        creditSaleTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        creditSaleTable.getColumnModel().getColumn(5).setMinWidth(50);
        creditSaleTable.getColumnModel().getColumn(5).setPreferredWidth(50);
        creditSaleTable.getColumnModel().getColumn(5).setMaxWidth(50);

        jScrollPane3.setBounds(20, 60, 710, 260);
        jDesktopPane4.add(jScrollPane3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jDesktopPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 805, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jDesktopPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Credit Sale Reference", jPanel3);

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));

        jDesktopPane5.setBackground(new java.awt.Color(153, 204, 255));

        holidayCalenderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Slno.", "Year", "Date", "Description"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(holidayCalenderTable);
        holidayCalenderTable.getColumnModel().getColumn(0).setMinWidth(40);
        holidayCalenderTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        holidayCalenderTable.getColumnModel().getColumn(0).setMaxWidth(30);
        holidayCalenderTable.getColumnModel().getColumn(1).setMinWidth(70);
        holidayCalenderTable.getColumnModel().getColumn(1).setPreferredWidth(70);
        holidayCalenderTable.getColumnModel().getColumn(1).setMaxWidth(60);
        holidayCalenderTable.getColumnModel().getColumn(2).setMinWidth(100);
        holidayCalenderTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        holidayCalenderTable.getColumnModel().getColumn(2).setMaxWidth(100);
        holidayCalenderTable.getColumnModel().getColumn(3).setMinWidth(80);
        holidayCalenderTable.getColumnModel().getColumn(3).setPreferredWidth(80);

        jScrollPane4.setBounds(20, 60, 720, 260);
        jDesktopPane5.add(jScrollPane4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jDesktopPane5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 805, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jDesktopPane5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Holiday Calender", jPanel4);

        jTabbedPane1.setBounds(10, 100, 810, 470);
        jDesktopPane1.add(jTabbedPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel18.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel18.setText("Site Description");
        jLabel18.setBounds(20, 30, 170, 20);
        jDesktopPane1.add(jLabel18, javax.swing.JLayeredPane.DEFAULT_LAYER);

        siteDescription.setEditable(false);
        siteDescription.setFont(new java.awt.Font("Verdana", 1, 12));
        siteDescription.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        siteDescription.setBounds(210, 30, 220, 18);
        jDesktopPane1.add(siteDescription, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jDesktopPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 879, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jDesktopPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
     public static void addActionToButton(JButton button, final JTabbedPane jTabbedPane1, final SiteMaster siteMaster) {
        button.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionPerformed(evt);
            }

            private void buttonActionPerformed(ActionEvent evt) {
                for (int i = 0; i < jTabbedPane1.getComponentCount(); i++) {
                    if (jTabbedPane1.getComponentAt(i).getName().equalsIgnoreCase("SiteMaster")) {
                        if (jTabbedPane1.isEnabledAt(i)) {
                            jTabbedPane1.setSelectedIndex(i);
                        }
                    }
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cfaEmail;
    private javax.swing.JTextField cfaId;
    private javax.swing.JTextField cfaTelephoneNo;
    private javax.swing.JTextField creditNoteValidityIndicator;
    private javax.swing.JTextField creditNoteValidityPeriod;
    private javax.swing.JTable creditSaleTable;
    private javax.swing.JTextField cstNo;
    private javax.swing.JTextField fiscalYear;
    private javax.swing.JTable forceReleaseTable;
    private javax.swing.JTable holidayCalenderTable;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JDesktopPane jDesktopPane2;
    private javax.swing.JDesktopPane jDesktopPane3;
    private javax.swing.JDesktopPane jDesktopPane4;
    private javax.swing.JDesktopPane jDesktopPane5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField minAdvancePercentage;
    private javax.swing.JTextField numRangeLogic;
    private javax.swing.JTextField panNo;
    private javax.swing.JTextField postingDate;
    private javax.swing.JTextField quotationValidityIndicator;
    private javax.swing.JTextField quotationValidityPeriod;
    private javax.swing.JTextArea regdOffAddress;
    private javax.swing.JTextField regdOffEmail;
    private javax.swing.JTextField regdOffName;
    private javax.swing.JTextField regdOffTelephone;
    private javax.swing.JTextField siteDescription;
    private javax.swing.JTextField vatno;
    private javax.swing.JTextField zone;
    // End of variables declaration//GEN-END:variables
    
}
