/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.poscontrolreport;

import ISRetail.Helpers.ConvertDate;
import ISRetail.serverconsole.ServerConsole;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.UIManager;

/**
 *
 * @author enteg
 */
public class POS_ControlReportPrint implements Printable, ActionListener {

    ArrayList<POSControlReportPOJO> posControlPOJOs = null;

    public POS_ControlReportPrint(ArrayList<POSControlReportPOJO> posControlPOJOs) {
        try {
            this.posControlPOJOs = posControlPOJOs;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int print(Graphics g, PageFormat pf, int page) throws
            PrinterException {
        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        pf.setOrientation(PageFormat.LANDSCAPE);
        this.drawDetails(g, pf);

        return PAGE_EXISTS;
    }

    public void drawDetails(Graphics g, PageFormat pf) {
        String deliveryDate = "", DATE_FORMAT_NOW, DATE_FORMAT, printstr, invDates = "", soDate1;
        Font plainfont8, plainfont10, plainfont12, boldfont8, boldfont10, boldfont12;
        StringBuffer strbuffer;
        FontMetrics metrics;
        SimpleDateFormat sdf;
        java.util.Date soDate;
        Calendar cal;
        //pf.setOrientation(PageFormat.LANDSCAPE);
        
        try {
             pf.setOrientation(PageFormat.LANDSCAPE);
            plainfont8 = new Font("Times New Roman", Font.PLAIN, 8);
            plainfont10 = new Font("Times New Roman", Font.PLAIN, 10);
            plainfont12 = new Font("Times New Roman", Font.PLAIN, 12);
            boldfont8 = new Font("Times New Roman", Font.BOLD, 8);
            boldfont10 = new Font("Times New Roman", Font.BOLD, 10);
            boldfont12 = new Font("Times New Roman", Font.BOLD, 12);
            int centerpos = (int) pf.getImageableWidth() / 2;
            int widthToPrint;
            printstr = new String();
            /*****************/
            //date format
            DATE_FORMAT_NOW = "dd/MM/yyyy h:mm:ss a";
            sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
            DATE_FORMAT = "dd/MM/yyyy";
            /*****************/
            /**********border line **************/
            g.drawLine(0, 1, (int) pf.getImageableWidth(), 1);//top border
            g.drawLine(0, 1, 0, (int) pf.getImageableHeight()- 20);//left border
            g.drawLine(0, (int) pf.getImageableHeight() - 20, (int) pf.getImageableWidth(), (int) pf.getImageableHeight() - 20);//bottom pborder
            g.drawLine((int) pf.getImageableWidth(), 1, (int) pf.getImageableWidth(), (int) pf.getImageableHeight() - 20);//right border

            //*********Heading
            g.setFont(boldfont12);
            metrics = g.getFontMetrics(boldfont12);
            int hlen = (int) (metrics.stringWidth("POS CONTROL REPORT") / 2);
            int cenpos = centerpos - hlen;
            int lyPos = 20, ryPos1 = 45, ryPos2 = 0, custSignYPos = 0;
            g.drawString("POS CONTROL REPORT", cenpos + 10, lyPos);

            lyPos = lyPos + 25;
            g.setFont(plainfont10);
            int tableHt = 0;

            g.drawString("SITE ID: " + ServerConsole.siteID, 38, lyPos);
            lyPos = lyPos + 15;
            sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
            cal = Calendar.getInstance();
            soDate = new java.util.Date();
            try {
                soDate1 = ServerConsole.businessDate;
                soDate = ConvertDate.getStringToDate(soDate1, "dd/MM/yyyy");
                Calendar createdTime = Calendar.getInstance();
                cal.setTime(soDate);
                cal.set(Calendar.HOUR_OF_DAY, createdTime.get(Calendar.HOUR_OF_DAY));
                cal.set(Calendar.MINUTE, createdTime.get(Calendar.MINUTE));
                cal.set(Calendar.SECOND, createdTime.get(Calendar.SECOND));

            } catch (Exception e) {
                e.printStackTrace();
            }
            g.drawString("DATE   : " + sdf.format(cal.getTime()), 38, lyPos);
            lyPos = lyPos + 15;
            g.setFont(boldfont10);
            g.drawLine(19, lyPos,(centerpos * 2)-20, lyPos);//horizontal top table
            lyPos = lyPos + 20;
            tableHt = tableHt + 20;
            g.drawString("S.NO.", 21, lyPos);
            g.drawString("TRANSACTION", 60, lyPos);
            g.drawString("LAST", centerpos-105, lyPos);
            g.drawString("ISR DATA", centerpos-30, lyPos);
            g.drawString("POSTING DATE", centerpos + 40, lyPos);
            g.drawString("TRANSACTION", centerpos + 153-25, lyPos);
            g.drawString("TRANSACTION", centerpos + 248-25, lyPos);
            lyPos = lyPos + 10;
            tableHt = tableHt + 10;
           g.drawString("DOCUMENT NO.", centerpos-130, lyPos);
           g.drawString("DATE", centerpos + 153+15-10-5, lyPos);
           g.drawString("TIME", centerpos + 248+15-10-5, lyPos);
            lyPos = lyPos + 2;
            tableHt = tableHt + 2;
            g.drawLine(19, lyPos, (centerpos * 2)-20, lyPos);
            if (posControlPOJOs != null) {
                g.setFont(plainfont10);
                for (POSControlReportPOJO posControlPOJO : posControlPOJOs) {
                    if (posControlPOJO != null) {
                        tableHt = tableHt + 15;
                        lyPos = lyPos + 15;
                        g.drawString("" + posControlPOJO.getSno(),21, lyPos);
                        g.drawString("" + posControlPOJO.getTransaction(),52, lyPos);
                         
                       if(posControlPOJO.getSno()<=13){
                        g.drawString("" + posControlPOJO.getLastNo(), centerpos-125 , lyPos);
                         if(posControlPOJO.getSno()!=10){
                          if(posControlPOJO.getPostingdate()!=null)
                        {
                        g.drawString("" + posControlPOJO.getPostingdate(), centerpos+50, lyPos);
                        }else
                        {
                         g.drawString("", centerpos+50, lyPos);
                        }
                         if(posControlPOJO.getTransactiondate()!=null)
                        { 
                        g.drawString("" + posControlPOJO.getTransactiondate(),centerpos+153-20, lyPos);
                         }else
                         {
                        g.drawString("",centerpos+153-20, lyPos);
                         }
                        
                        if(posControlPOJO.getTransactiontime()!=null)
                        {
                        g.drawString("" + posControlPOJO.getTransactiontime(),centerpos+253-30, lyPos);
                        }else
                        {
                         g.drawString("",centerpos+253-30, lyPos);
                        }
                         }
                       
                       }else{
                             g.drawString("" + posControlPOJO.getDatafromisr(), centerpos-20, lyPos);
                       }
                       // g.drawString("" + posControlPOJO.getDatafromisr(), , lyPos);
                                              
                        g.drawLine(19, lyPos + 2,(centerpos * 2)-20, lyPos + 2);//lowest bottom line

                    }
                }
            } else {
                lyPos = lyPos + 15;
                tableHt = tableHt + 15;
            }
            lyPos = lyPos + 2;
            tableHt = tableHt + 2;
            g.drawLine(19, lyPos - tableHt,19, lyPos);//left vertical line
            g.drawLine(46, lyPos - tableHt, 46, lyPos);//left vertical line after sno
            g.drawLine(centerpos-140, lyPos - tableHt, centerpos-140 , lyPos);//left vertical line after transaction
            g.drawLine(centerpos-40, lyPos - tableHt, centerpos-40 , lyPos);//left vertical line after last doc no
            g.drawLine(centerpos+30 , lyPos - tableHt, centerpos+30 , lyPos);//left vertical line after data from isr
            g.drawLine(centerpos+130-5 , lyPos - tableHt, centerpos+130-5 , lyPos);//left vertical line after posting date
            g.drawLine((centerpos * 2)-120 , lyPos - tableHt, (centerpos * 2)-120 , lyPos);//left vertical line after  transaction date
            g.drawLine((centerpos * 2)-20, lyPos - tableHt, (centerpos * 2)-20, lyPos);//right vertical line
            //  g.drawLine(38, lyPos, centerpos * 2, lyPos);//lowest bottom line
            lyPos=lyPos+30;
            
             g.setFont(boldfont10);
             g.drawString("Authorized Signatory",450, lyPos);
            
            g.setFont(plainfont10);


        //***********************Next line


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            deliveryDate = null;
            DATE_FORMAT_NOW = null;
            DATE_FORMAT = null;
            printstr = null;
            invDates = null;
            soDate1 = null;
            plainfont8 = null;
            plainfont10 = null;
            plainfont12 = null;
            boldfont8 = null;
            boldfont10 = null;
            boldfont12 = null;
            strbuffer = null;
            metrics = null;
        }
    }

    public void actionPerformed(ActionEvent e) {
        try {
            PrinterJob job = PrinterJob.getPrinterJob();

            job.setPrintable(this);
            JTable table = new JTable();

            Printable tableprint = table.getPrintable(JTable.PrintMode.FIT_WIDTH, new MessageFormat("My Table"),
                    new MessageFormat("Page - {0}"));

            boolean ok = job.printDialog();
            if (ok) {
                try {
                    job.print();
                } catch (PrinterException ex) {
                /* The job did not successfully complete */
                }
            }
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
        }
    }

    public static void main(String args[]) {

        UIManager.put("swing.boldMetal", Boolean.FALSE);
        JFrame f = new JFrame("Hello World Printer");
        f.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        JButton printButton = new JButton("Print Acknowledgement");

        f.add("Center", printButton);

        f.pack();
        f.setVisible(true);
    }
}
