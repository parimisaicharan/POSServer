/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.poscontrolreport;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.MessageFormat;
import java.util.ArrayList;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author enteg
 */
public class POS_ControlReportPrintPreview extends JApplet {

    private Button print;
    ArrayList<POSControlReportPOJO> posControlPOJOs = null;

    public POS_ControlReportPrintPreview() {
    }

    public POS_ControlReportPrintPreview(ArrayList<POSControlReportPOJO> posControlPOJOs) {
        this.posControlPOJOs = posControlPOJOs;
    }

    class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            try {
                JFrame f = new JFrame("POS Control Report");
                int result = JOptionPane.showConfirmDialog(f, "Do you want to print ? ", "Confirmation", JOptionPane.YES_NO_OPTION);

                // here starting the printing
                if (result == JOptionPane.YES_OPTION) {

                    PrinterJob job = PrinterJob.getPrinterJob();
                    // job.setPrintable(new POS_ControlReportPrint(posControlPOJOs));
                    JTable table = new JTable();
                    //Printable tableprint = table.getPrintable(JTable.PrintMode.FIT_WIDTH, new MessageFormat("My Table"),
                    //new MessageFormat("Page - {0}"));




                    PageFormat pf = job.defaultPage();
                    pf.setOrientation(PageFormat.LANDSCAPE);



                    Book bk = new Book();
                    bk.append(new POS_ControlReportPrint(posControlPOJOs), pf);

                    job.setPageable(bk);





                    boolean ok = job.printDialog();
                    if (ok) {
                        try {
                            job.print();
                        } catch (PrinterException ex) {
                            /* The job did not successfully complete */
                        }
                    } else {
                        job.cancel();
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void init() {

       setForeground(Color.white);
       setBackground(Color.white);

        print = new Button("Print");
        Container window = getContentPane();// Gets size of window
        window.setLayout(new FlowLayout(90, 300, 700)); // Sets the button layout
        getContentPane().setBackground(Color.RED);
        print.addActionListener(new ButtonListener()); //Adds a listener, so action can be performed if clicked
        //print.setBackground(Color.GRAY);   //Makes color yellow
        print.setBackground(Color.GRAY);
        //txt.setBackground(Color.gray);
        window.add(print);
    }

    @Override
    public void paint(Graphics n) {
        
        Graphics2D g = (Graphics2D) n;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setPaint(Color.BLACK);

        PageFormat pf = new PageFormat();
        g.translate(50, 50);
        pf.setOrientation(PageFormat.LANDSCAPE);
        // here for calling the method for drawing the stirng 
        POS_ControlReportPrint printDetais = new POS_ControlReportPrint(this.posControlPOJOs);
        printDetais.drawDetails(g, pf);

    }

    public static void main(String s[]) {

        JFrame f = new JFrame("ShapesDemo2D");
        f.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        f.pack();
        f.setSize(new Dimension(800, 800));
        f.show();
    }
}
